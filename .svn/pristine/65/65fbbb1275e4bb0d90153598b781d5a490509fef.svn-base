package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>会员积分明细VO</p>
 */
@Data
public class CustomerPointsDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private String customerId;

	/**
	 * 用户账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	private String customerAccount;

	/**
	 * 用户名
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String customerName;

	/**
	 * 操作类型 0:扣除 1:增长
	 */
	private OperateType type;

	/**
	 * 会员积分业务类型 0签到 1注册 2分享商品 3分享注册 4分享购买  5评论商品 6晒单 7上传头像/完善个人信息 8绑定微信
	 * 9添加收货地址 10关注店铺 11订单完成 12订单抵扣 13优惠券兑换 14积分兑换 15退单返还 16订单取消返还 17过期扣除
	 */
	private PointsServiceType serviceType;

	/**
	 * 积分数量
	 */
	private Long points;

	/**
	 * 内容备注
	 */
	private String content;

	/**
	 * 积分余额
	 */
	private Long pointsAvailable;

	/**
	 * 操作时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime opTime;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	private LogOutStatus logOutStatus;

}