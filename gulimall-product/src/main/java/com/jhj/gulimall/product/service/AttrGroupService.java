package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.gulimall.product.entity.AttrGroupEntity;
import com.jhj.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.jhj.gulimall.product.vo.SkuItemAttrGroupVo;
import com.jhj.gulimall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:23
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsBycatelogId(Long catelogId);

    List<SkuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

