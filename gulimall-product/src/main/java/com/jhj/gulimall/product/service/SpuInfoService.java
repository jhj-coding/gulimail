package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.gulimall.product.entity.SpuInfoDescEntity;
import com.jhj.gulimall.product.entity.SpuInfoEntity;
import com.jhj.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:23
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    void saveSpuInfoDesc(SpuInfoDescEntity descEntity);
}

