package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.common.vaild.AddGroup;
import com.jhj.common.vaild.UpdateGroup;
import com.jhj.gulimall.product.entity.CategoryBrandRelationEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:22
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(@NotNull(message = "修改必须指定", groups = {UpdateGroup.class}) @Null(message = "新增不能指定id", groups = {AddGroup.class}) Long brandId, @NotEmpty(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class}) String name);

    void updateCategory(Long catId, String name);
}

