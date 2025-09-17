package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FollowFlag;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GoodsCustomerFollowVO extends BasicResponse {

    /**
     * 商品编号
     */
    private String goodsId;

    /**
     * SKU编号
     */
    private String goodsInfoId;

    /**
     * 全局购买数
     */
    private Long goodsNum;

    /**
     * 公司信息ID
     */
    private Long companyInfoId;

    /**
     * 采购创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     * 收藏标记
     */
    private FollowFlag followFlag;

    /**
     * 收藏时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime followTime;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 商品一级类目
     */
    private Long cateTopId;

    /**
     * 商品类目
     */
    private Long cateId;

    /**
     * 商品品牌
     */
    private Long brandId;

    /**
     * 终端来源
     */
    private TerminalSource terminalSource;

    /**
     * 是否周期购商品
     */
    private Integer isBuyCycle;
}
