package com.wanmi.sbc.goods.bean.vo.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信视频号带货商品VO</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Schema
@Data
public class WechatSkuVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 微信端sku_id
	 */
	@Schema(description = "微信端sku_id")
	private String wechatSkuId;

	/**
	 * 微信端商品id
	 */
	@Schema(description = "微信端商品id")
	private Long productId;

	private GoodsInfoVO goodsInfoVO;

	/**
	 * goodsId
	 */
	@Schema(description = "goodsId")
	private String goodsId;

	@Schema(description = "审核状态，1：待审核，2：审核中，3：审核不通过，4：审核通过")
	private EditStatus editStatus;

	@Schema(description = "0初始值5上架11自主下架13违规下架")
	private WechatShelveStatus wechatShelveStatus;

	/**
	 * 审核不通过原因
	 */
	@Schema(description = "审核不通过原因")
	private String rejectReason;
	/**
	 * 微信端商品图片
	 */
	private String img;



	@Schema(description = "微信端下架原因")
	private String downReason;

	/**
	 * createTime
	 */
	@Schema(description = "createTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}