package com.jhj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.Query;
import com.jhj.gulimall.product.dao.AttrDao;
import com.jhj.gulimall.product.entity.AttrEntity;
import com.jhj.gulimall.product.service.AttrService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

}
