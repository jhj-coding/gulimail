package com.jhj.gulimall.product.service.impl;

import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.Query;
import com.jhj.gulimall.product.entity.AttrEntity;
import com.jhj.gulimall.product.service.AttrService;
import com.jhj.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.jhj.gulimall.product.vo.SkuItemAttrGroupVo;
import com.jhj.gulimall.product.vo.SkuItemVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jhj.gulimall.product.dao.AttrGroupDao;
import com.jhj.gulimall.product.entity.AttrGroupEntity;
import com.jhj.gulimall.product.service.AttrGroupService;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    AttrService attrService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key= (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if (!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        if (catelogId==0){
            IPage<AttrGroupEntity> page=this.page(new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        }else{
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page=this.page(new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsBycatelogId(Long catelogId) {
        List<AttrGroupEntity> catelog_id = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<AttrGroupWithAttrsVo> collect = catelog_id.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(attrGroupEntity, attrGroupWithAttrsVo);
            List<AttrEntity> relationAttr = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(relationAttr);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SkuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {

        List<SkuItemAttrGroupVo> vos=this.baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catalogId);
        return vos;
    }

}
