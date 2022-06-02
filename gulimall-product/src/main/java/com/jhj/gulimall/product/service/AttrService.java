package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.gulimall.product.entity.AttrEntity;
import com.jhj.gulimall.product.vo.AttrGroupRelationVo;
import com.jhj.gulimall.product.vo.AttrRespVo;
import com.jhj.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:24
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

