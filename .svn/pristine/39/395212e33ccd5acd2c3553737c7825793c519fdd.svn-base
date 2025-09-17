package com.wanmi.sbc.empower.api.request.logisticslog;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流记录列表查询请求参数</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<String> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 快递单号
	 */
	@Schema(description = "快递单号")
	private String logisticNo;

	/**
	 * 购买人编号
	 */
	@Schema(description = "购买人编号")
	private String customerId;

	/**
	 * 是否结束
	 */
	@Schema(description = "是否结束")
	private Integer endFlag;

	/**
	 * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑
	 */
	@Schema(description = "监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑")
	private String status;

	/**
	 * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
	 */
	@Schema(description = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态")
	private String state;

	/**
	 * 监控状态相关消息，如:3天查询无记录，60天无变化
	 */
	@Schema(description = "监控状态相关消息，如:3天查询无记录，60天无变化")
	private String message;

	/**
	 * 快递公司编码是否出过错
	 */
	@Schema(description = "快递公司编码是否出过错")
	private String autoCheck;

	/**
	 * 本地物流公司标准编码
	 */
	@Schema(description = "本地物流公司标准编码")
	private String comOld;

	/**
	 * 快递纠正新编码
	 */
	@Schema(description = "快递纠正新编码")
	private String comNew;

	/**
	 * 是否签收标记
	 */
	@Schema(description = "是否签收标记")
	private String isCheck;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 出发地城市
	 */
	@Schema(description = "出发地城市")
	private String from;

	/**
	 * 目的地城市
	 */
	@Schema(description = "目的地城市")
	private String to;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsImg;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;

	/**
	 * 订阅申请状态
	 */
	@Schema(description = "订阅申请状态")
	private Integer successFlag;

	/**
	 * 搜索条件:签收时间开始
	 */
	@Schema(description = "搜索条件:签收时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTimeBegin;
	/**
	 * 搜索条件:签收时间截止
	 */
	@Schema(description = "搜索条件:签收时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime checkTimeEnd;

	/**
	 * 本地发货单号
	 */
	@Schema(description = "本地发货单号")
	private String deliverId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

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
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}