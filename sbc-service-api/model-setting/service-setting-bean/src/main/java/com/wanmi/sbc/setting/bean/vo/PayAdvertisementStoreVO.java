package com.wanmi.sbc.setting.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄昭
 * @className PayAdvertisementStoreVO
 * @description TODO
 * @date 2022/4/13 10:56
 **/
@Schema
@Data
public class PayAdvertisementStoreVO implements Serializable {
    private static final long serialVersionUID = -3393655458495658284L;

    /**
     * 支付广告门店关联id
     */
    private Long id;

    /**
     * 支付广告id
     */
    private Long payAdvertisementId;

    /**
     * 门店id
     */
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;
}