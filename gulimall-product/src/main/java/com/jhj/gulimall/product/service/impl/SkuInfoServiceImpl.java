package com.jhj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.Query;
import com.jhj.gulimall.product.dao.SkuInfoDao;
import com.jhj.gulimall.product.entity.SkuImagesEntity;
import com.jhj.gulimall.product.entity.SkuInfoEntity;
import com.jhj.gulimall.product.entity.SpuInfoDescEntity;
import com.jhj.gulimall.product.service.*;
import com.jhj.gulimall.product.vo.SkuItemAttrGroupVo;
import com.jhj.gulimall.product.vo.SkuItemSaleAttrVo;
import com.jhj.gulimall.product.vo.SkuItemVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService imagesService;

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            skuInfoEntityQueryWrapper.and((w) -> {
                w.eq("sku_id", key).or().like("sku_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId)) {
            skuInfoEntityQueryWrapper.eq("catalog_Id", catelogId);

        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId)) {
            skuInfoEntityQueryWrapper.eq("brand_id", brandId);

        }
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            skuInfoEntityQueryWrapper
                    .ge("price", min);


        }
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(min);
                if (bigDecimal.compareTo(new BigDecimal(0)) == 1)
                    skuInfoEntityQueryWrapper
                            .le("price", max);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                skuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> spu_id = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return spu_id;
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo=new SkuItemVo();

        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, threadPoolExecutor);


        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(skuItemSaleAttrVos);
        }, threadPoolExecutor);

        CompletableFuture<Void> descFuture =infoFuture.thenAcceptAsync((res)->{
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfoDescEntity);
        },threadPoolExecutor);

        CompletableFuture<Void> baseAttrFuture=infoFuture.thenAcceptAsync((res)->{
            List<SkuItemAttrGroupVo> attrGroupVos=attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(),res.getCatalogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        },threadPoolExecutor);


        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> images = imagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, threadPoolExecutor);

        CompletableFuture.allOf(saleAttrFuture,descFuture,baseAttrFuture,imageFuture).get();
        return skuItemVo;
    }

}
