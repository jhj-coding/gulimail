package com.jhj.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description MergeVo
 * @Author jhj
 * @Date 2022/6/911:33
 **/
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
