package com.jhj.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.Query;
import com.jhj.gulimall.product.dao.CategoryDao;
import com.jhj.gulimall.product.entity.CategoryEntity;
import com.jhj.gulimall.product.service.CategoryBrandRelationService;
import com.jhj.gulimall.product.service.CategoryService;
import com.jhj.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    RedissonClient redisson;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查询所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //组装树形结构
        List<CategoryEntity> level1Menus = entities.stream().filter(e -> {
            return e.getParentCid() == 0;
        }).map(e->{
            e.setChildren(getChildrend(e,entities));
            return e;
        }).sorted((e1,e2)->{
            return (e1.getSort()==null?0:e1.getSort())-(e2.getSort()==null?0:e2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {

        //TODO 检查
        // 逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths=new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 失效模式  清除多个缓存
     * @param category
     */
    @Caching(evict = {
            @CacheEvict(value = "category",key = "'level1Category'"),
            @CacheEvict(value = "category",key = "'getCatalogJson'")
    })
//    @CacheEvict(value = "category",allEntries = true) 删除该分区下所有数据
    //双写模式 写库写缓存
    //@CachePut
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    //每个需要缓存的数据需要制定放到哪个名字的缓存{缓存分区 可以写多个}
    /**
     * 自动生成key value使用jdk序列化机制 默认时间为-1
     *
     * 指定缓存的key key属性 可以写spel表达式(可参考官网) #root.method.name(方法名)
     * 时间 在配置文件中使用 spring.cache.redis.time-to-live= 毫秒值
     * 将数据保存为json格式
     * @return
     */
    @Cacheable(value={"category"},key = "'level1Category'",sync = true) //代表当前方法的结果需要缓存 如果缓存中有方法不调用 如果没有 方法调用 最后将结果放入缓存 sync加本地锁
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return entities;
    }

    @Cacheable(value = "category",key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        List<CategoryEntity> categoryEntityList=baseMapper.selectList(null);


        List<CategoryEntity> level1Categorys = getParent_cid(categoryEntityList,0L);

        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> {
                    return k.getCatId().toString();
                },
                v -> {
                    List<CategoryEntity> entities = getParent_cid(categoryEntityList,v.getCatId());
                    List<Catelog2Vo> collect = null;
                    if (entities != null) {
                        collect = entities.stream().map(item -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, item.getCatId().toString(), item.getName());
                            List<CategoryEntity> entities1 = getParent_cid(categoryEntityList,item.getCatId());
                            if (entities1!=null){
                                List<Catelog2Vo.Catelog3Vo> collect1 = entities1.stream().map(l3 -> {
                                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(item.getCatId().toString(),l3.getCatId().toString(),l3.getName().toString());
                                    return catelog3Vo;
                                }).collect(Collectors.toList());
                                catelog2Vo.setCatalog3List(collect1);
                            }
                            return catelog2Vo;
                        }).collect(Collectors.toList());
                    }
                    return collect;
                }));
        return parent_cid;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJson2() {

        String catalogJSON=stringRedisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)){
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDb();
            stringRedisTemplate.opsForValue().set("catalogJSON", JSON.toJSONString(catalogJsonFromDb));
            return catalogJsonFromDb;
        }

        return JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
    }
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {

        //锁的名字
        RLock lock = redisson.getLock("CatalogJson_lock");
        //加锁
        lock.lock();
        try{

        }finally {
            lock.unlock();
        }
        String catalogJSON=stringRedisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)){
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDb();
            stringRedisTemplate.opsForValue().set("catalogJSON", JSON.toJSONString(catalogJsonFromDb));
            return catalogJsonFromDb;
        }

        return JSON.parseObject(catalogJSON,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
    }
    //从数据查询
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDb() {

        List<CategoryEntity> categoryEntityList=baseMapper.selectList(null);


        List<CategoryEntity> level1Categorys = getParent_cid(categoryEntityList,0L);

        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> {
                    return k.getCatId().toString();
                },
                v -> {
                    List<CategoryEntity> entities = getParent_cid(categoryEntityList,v.getCatId());
                    List<Catelog2Vo> collect = null;
                    if (entities != null) {
                        collect = entities.stream().map(item -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, item.getCatId().toString(), item.getName());
                            List<CategoryEntity> entities1 = getParent_cid(categoryEntityList,item.getCatId());
                            if (entities1!=null){
                                List<Catelog2Vo.Catelog3Vo> collect1 = entities1.stream().map(l3 -> {
                                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(item.getCatId().toString(),l3.getCatId().toString(),l3.getName().toString());
                                    return catelog3Vo;
                                }).collect(Collectors.toList());
                                catelog2Vo.setCatalog3List(collect1);
                            }
                            return catelog2Vo;
                        }).collect(Collectors.toList());
                    }
                    return collect;
                }));
        return parent_cid;
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> categoryEntityList,Long parentCid) {
//        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
        List<CategoryEntity> collect = categoryEntityList.stream().filter(item -> {
            return item.getParentCid() == parentCid;
        }).collect(Collectors.toList());
        return collect;
    }

    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }
    //递归查找所有子菜单
    private List<CategoryEntity> getChildrend(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> categoryEntityList = all.stream().filter(e -> {
            return e.getParentCid() == root.getCatId();
        }).map(e->{
            e.setChildren(getChildrend(e,all));
            return e;
        }).sorted((e1,e2)->{
            return (e1.getSort()==null?0:e1.getSort())-(e2.getSort()==null?0:e2.getSort());
        }).collect(Collectors.toList());
        return categoryEntityList;
    }

}
