package com.jhj.gulimall.product.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return entities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        List<CategoryEntity> level1Categorys = getLevel1Categorys();

        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> {
                    return k.getCatId().toString();
                },
                v -> {
                    List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
                    List<Catelog2Vo> collect = null;
                    if (entities != null) {
                        collect = entities.stream().map(item -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, item.getCatId().toString(), item.getName());
                            List<CategoryEntity> entities1 = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", item.getCatId()));
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
