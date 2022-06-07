package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.gulimall.product.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:23
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long id, List<String> images);
}

