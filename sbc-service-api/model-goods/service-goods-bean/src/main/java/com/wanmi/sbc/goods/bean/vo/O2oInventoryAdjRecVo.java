package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/***
 * 库存调整记录Vo
 * @className O2oInventoryAdjRecVo
 * @author zhengyang
 * @date 2021/8/7 17:54
 **/
@Data
@Schema
public class O2oInventoryAdjRecVo extends BasicResponse {

	/**
	 * 调价单号
	 */
	@Schema(description = "调价单号")
	private String id;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 调价商品数
	 */
	@Schema(description = "调价商品数")
	private Long goodsNum;

	/**
	 * 生效时间
	 */
	@Schema(description = "生效时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime effectiveTime;

	/**
	 * 制单人名称
	 */
	@Schema(description = "制单人名称")
	private String creatorName;

	/**
	 * 制单人账号
	 */
	@Schema(description = "创建时间")
	private String creatorAccount;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "制单人账号")
	private DefaultFlag confirmFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;
}
