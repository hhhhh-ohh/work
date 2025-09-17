package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardInvalidStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardUseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>用户礼品卡通用查询请求参数</p>
 * @author 吴瑞
 * @date 2022-12-12 09:45:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-礼品卡IdList
	 */
	@Schema(description = "批量查询-礼品卡IdList")
	private List<Long> userGiftCardIdList;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long userGiftCardId;

	/**
	 * 礼品卡批次号
	 */
	@Schema(description = "礼品卡批次号")
	private String batchNo;

	/**
	 * 礼品卡名称
	 */
	@Schema(description = "礼品卡名称")
	private String giftCardName;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String customerId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardId;

	/**
	 * 礼品卡卡号
	 */
	@Schema(description = "礼品卡卡号")
	private String giftCardNo;

	/**
	 * 礼品卡面值
	 */
	@Schema(description = "礼品卡面值")
	private Long parValue;

	/**
	 * 礼品卡余额
	 */
	@Schema(description = "礼品卡余额")
	private BigDecimal balance;

	/**
	 * 礼品卡状态 0：待激活  1：已激活 2：已销卡
	 */
	@Schema(description = "礼品卡状态 0：待激活  1：已激活 2：已销卡")
	private Integer cardStatus;

	/**
	 * 过期时间类型 0：有失效时间 1：长期有效
	 */
	@Schema(description = "过期时间类型 0：有失效时间 1：长期有效")
	private Integer expirationType;

	/**
	 * 搜索条件:过期时间开始
	 */
	@Schema(description = "搜索条件:过期时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeBegin;
	/**
	 * 搜索条件:过期时间截止
	 */
	@Schema(description = "搜索条件:过期时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeEnd;

	/**
	 * 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始
	 */
	@Schema(description = "搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTimeBegin;
	/**
	 * 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止
	 */
	@Schema(description = "搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTimeEnd;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Schema(description = "归属会员，制卡兑换人/发卡接收人")
	private String belongPerson;

	/**
	 * 搜索条件:激活时间开始
	 */
	@Schema(description = "搜索条件:激活时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTimeBegin;
	/**
	 * 搜索条件:激活时间截止
	 */
	@Schema(description = "搜索条件:激活时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTimeEnd;

	/**
	 * 激活人
	 */
	@Schema(description = "激活人")
	private String activationPerson;

	/**
	 * 搜索条件:销卡时间开始
	 */
	@Schema(description = "搜索条件:销卡时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime cancelTimeBegin;
	/**
	 * 搜索条件:销卡时间截止
	 */
	@Schema(description = "搜索条件:销卡时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime cancelTimeEnd;

	/**
	 * 销卡人
	 */
	@Schema(description = "销卡人")
	private String cancelPerson;

	/**
	 * 销卡描述
	 */
	@Schema(description = "销卡描述")
	private String cancelDesc;

    /** ==============C 端使用============= */
    /**
     * 状态
	 * 可用 ： 状态已激活 && （expirationType = 0 || expirationType = 1 && expirationTime>=now） && balance > 0
	 * 不可用： 全部，0：已用完，1：已过期，2：已经销卡
	 * 待激活：状态 待激活
     */
    @Schema(description = "礼品卡类型， 0：可用， 1：不可用 2：待激活  空全部")
    private GiftCardUseStatus status;

    /** 礼品卡不可用类型
	 * 已用完：未销卡 && 未过期 && 余额=0
	 * 已过期：未销卡 && 过期
	 * 已销卡： 已经销卡
	 * */
    @Schema(description = "礼品卡不可用类型， 空全部，0：已用完，1：已过期，2：已经销卡")
    private GiftCardInvalidStatus invalidStatus;

	@Schema(description = "礼品卡类型")
	private GiftCardType giftCardType;
}