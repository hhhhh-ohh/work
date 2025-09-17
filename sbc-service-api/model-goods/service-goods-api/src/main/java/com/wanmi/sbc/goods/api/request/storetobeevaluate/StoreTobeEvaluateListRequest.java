package com.wanmi.sbc.goods.api.request.storetobeevaluate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺服务待评价列表查询请求参数</p>
 * @author lzw
 * @date 2019-03-20 17:01:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreTobeEvaluateListRequest extends BaseRequest {

	private static final long serialVersionUID = 8517593926729939212L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * 批量查询-orderNos
	 */
	@Schema(description = "批量查询-orderNos")
	private List<String> orderNos;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	private Long storeId;

	/**
	 * 店铺logo
	 */
	@Schema(description = "店铺logo")
	private String storeLogo;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	private String customerAccount;

	/**
	 * 商品自动评价日期
	 */
	@Schema(description = "商品自动评价日期")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate autoStoreEvaluateDate;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

}