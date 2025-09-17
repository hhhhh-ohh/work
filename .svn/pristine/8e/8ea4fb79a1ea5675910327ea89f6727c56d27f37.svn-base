package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>用户礼品卡实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:02:19
 */
@Data
@Schema
public class UserGiftCardInfoVO extends UserGiftCardVO {
	private static final long serialVersionUID = 1L;

	@Schema(description = "卡名称")
	private String  name;

	@Schema(description = "封面类型 0：指定颜色 1:指定图片")
	private DefaultFlag backgroundType;

	/**
	 *  封面数值
	 */
	@Schema(description = "封面数值")
	private String backgroundDetail;

	/**
	 * 过期时间类型 0：长期有效 1：领取多少月内有效 2:指定具体时间
	 */
	@Schema(description = "过期时间类型 0：长期有效 1：领取多少月内有效 2:指定具体时间")
	private ExpirationType expirationType;

	/**
	 * 针对过期类型为1：领取多少月内有效 时，返回对应的余额数
	 */
	@Schema(description = "针对过期类型为1：领取多少月内有效 时，返回对应的余额数")
	private Long rangeMonth;

	@Schema(description = "使用描述")
	private String  useDesc;

	@Schema(description = "关联商品 0：全部 1: 部分商品可用 2：部分商品可用 3：部分商品可用 4：指定商品可用")
	private GiftCardScopeType scopeType;

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

	/**
	 * 客服类型
	 */
	@Schema(description = "0：电话 1：座机 2：微信")
	private GiftCardContactType contactType;

	/**
	 * 客服联系方式
	 */
	@Schema(description = "客服联系方式")
	private String contactPhone;

	/**
	 * 适用商品列表
	 */
	@Schema(description = "适用商品列表")
	private List<String> skuIdList;

	@Schema(description = "礼品卡类型")
	private GiftCardType giftCardType;

	@Schema(description = "适用商品数量 -1可选一种 -99可全选 其他代表N种")
	private Integer scopeGoodsNum;

	@Schema(description = "适用商品数量总数")
	private Integer totalGoodsNum;

	@Schema(description = "是否需要预约发货: 默认 0-不需要; 1-需要")
	private Integer  appointmentShipmentFlag;

	@Schema(description = "预约发货开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime appointmentShipmentStartTime;
}
