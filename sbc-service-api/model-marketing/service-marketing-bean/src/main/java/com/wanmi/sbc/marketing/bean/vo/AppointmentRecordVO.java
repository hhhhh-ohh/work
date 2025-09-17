package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.SupplierVO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>预约抢购实体</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRecordVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;


    /**
     * 会员id
     */
    @NotBlank
    private String buyerId;

    /**
     * 活动id
     */
    @NotNull
    private Long appointmentSaleId;

    /**
     * skuId
     */
    @NotBlank
    private String goodsInfoId;

    /**
     * 商家信息
     */
    @NotNull
    private SupplierVO supplier;

    /**
     * 商家信息
     */
    @NotNull
    private CustomerVO customer;

    /**
     * 活动信息
     */
    @NotNull
    private AppointmentSaleInfoVO appointmentSaleInfo;

    /**
     * 活动商品信息
     */
    @NotNull
    private AppointmentSaleGoodsInfoVO appointmentSaleGoodsInfo;


    /**
     * 预约创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


	/**
	 * spu信息
	 */
	private GoodsVO goods;


	/**
	 * sku
	 */
    private GoodsInfoVO goodsInfo;


}