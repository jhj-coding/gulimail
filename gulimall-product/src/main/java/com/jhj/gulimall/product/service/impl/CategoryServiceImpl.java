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
