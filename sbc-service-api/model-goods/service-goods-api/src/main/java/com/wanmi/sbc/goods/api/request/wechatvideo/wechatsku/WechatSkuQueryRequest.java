package com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>微信视频号带货商品通用查询请求参数</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSkuQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

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

	private Long companyInfoId;

	private List<Long> companyInfoIds;

	private String storeName;

	/**
	 * 微信端商品id
	 */
	@Schema(description = "微信端商品id")
	private Long productId;

	/**
	 * goodsInfoId
	 */
	@Schema(description = "goodsInfoId")
	private String goodsInfoId;

	private String goodsInfoName;

	/**
	 * goodsId
	 */
	@Schema(description = "goodsId")
	private String goodsId;

	private List<String> goodsIds;

	private List<String> goodsInfoIds;

	@Schema(description = "审核状态，1：待审核，2：审核中，3：审核不通过，4：审核通过")
	private EditStatus editStatus;

	private EditStatus notEditStatus;


	private WechatShelveStatus wechatShelveStatus;

	private List<WechatShelveStatus> wechatShelveStatusList;

	/**
	 * 搜索条件:createTime开始
	 */
	@Schema(description = "搜索条件:createTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:createTime截止
	 */
	@Schema(description = "搜索条件:createTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 是否删除，0，否，1是
	 */
	@Schema(description = "是否删除，0，否，1是")
	private DeleteFlag delFlag;

}