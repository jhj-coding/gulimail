package com.jhj.gulimall.coupon.service.impl;

import com.jhj.common.to.MemberPrice;
import com.jhj.common.to.SkuRedutionTo;
import com.jhj.gulimall.coupon.entity.MemberPriceEntity;
import com.jhj.gulimall.coupon.entity.SkuLadderEntity;
import com.jhj.gulimall.coupon.service.MemberPriceService;
import com.jhj.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.Query;

import com.jhj.gulimall.coupon.dao.SkuFullReductionDao;
import com.jhj.gulimall.coupon.entity.SkuFullReductionEntity;
import com.jhj.gulimall.coupon.service.SkuFullReductionService;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Resource
    SkuLadderService skuLadderService;
    @Resource
    MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuRedutionTo skuRedutionTo) {
        SkuLadderEntity skuLadderEntity=new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuRedutionTo.getSkuId());
        skuLadderEntity.setFullCount(skuRedutionTo.getFullCount());
        skuLadderEntity.setDiscount(skuRedutionTo.getDiscount());
        skuLadderEntity.setAddOther(skuRedutionTo.getCountStatus());
        skuLadderService.save(skuLadderEntity);

        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuRedutionTo,skuFullReductionEntity);
        this.save(skuFullReductionEntity);

        List<MemberPrice> memberPrice = skuRedutionTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuRedutionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}