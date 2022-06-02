package com.jhj.gulimall.product.vo;

import lombok.Data;

/**
 * @Description AttrRespVo
 * @Author jhj
 * @Date 2022/6/210:28
 **/
@Data
public class AttrRespVo extends AttrVo{
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
