package com.wanmi.sbc.empower.bean.dto.channel.base.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhengyang
 * @className GoodsSpecResponse
 * @description 京东规格详情返回值
 * @date 2021/5/28 16:09
 **/
@Data
public class GoodsSpecDto implements Serializable {
    /***
     * 京东规格名
     */
    private String groupName;

    /***
     * 京东规格详情
     */
    private List<GoodsSpecDetailDto> atts;
}
