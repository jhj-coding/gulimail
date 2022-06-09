package com.jhj.gulimall.ware.vo;

import lombok.Data;

/**
 * @Description PurchaseItemDoneVo
 * @Author jhj
 * @Date 2022/6/914:38
 **/
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
