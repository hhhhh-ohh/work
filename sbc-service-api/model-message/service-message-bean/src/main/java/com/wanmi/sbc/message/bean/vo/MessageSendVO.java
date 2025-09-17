package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wanmi.sbc.message.bean.enums.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信任务表VO</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Schema
@Data
public class MessageSendVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long messageId;

	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	private String name;

	/**
	 * 消息类型 0优惠促销
	 */
	@Schema(description = "消息类型 0优惠促销")
	private MessageType messageType;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	private String title;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	private String content;

	/**
	 * 封面图
	 */
	@Schema(description = "封面图")
	private String imgUrl;

	/**
	 * 推送类型 0：全部会员、1：按会员等级、2：按标签、3：按人群、4：指定会员
	 */
	@Schema(description = "推送类型 0：全部会员、1：按会员等级、2：按标签、3：按人群、4：指定会员")
	private MessageSendType sendType;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 消息同步标识 0：push消息、1：运营计划
	 */
	@Schema(description = "消息同步标识 0：push消息、1：运营计划")
	private PushFlag pushFlag;

	/**
	 * 发送数
	 */
	@Schema(description = "发送数")
	private Integer sendSum;

	/**
	 * 打开数
	 */
	@Schema(description = "打开数")
	private Integer openSum;

	/**
	 * 推送时间类型 0：立即、1：定时
	 */
	@Schema(description = "推送时间类型 0：立即、1：定时")
	private SendType sendTimeType;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 推送人列表
	 */
	@Schema(description = "推送人列表")
	private List<MessageSendCustomerScopeVO> scopeVOList;

	/**
	 * 删除标识
	 */
	private DeleteFlag delFlag;

	/**
	 * 推送消息id
	 */
	@Schema(description = "推送消息id")
	private String pushId;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	private Long planId;

	/**
	 * 移动端落地页参数
	 */
	@Schema(description = "移动端落地页参数")
	private String routeParams;

	/**
	 * PC端落地页参数
	 */
	@Schema(description = "PC端落地页参数")
	private String pcRouteParams;


	/**
	 * 打开率
	 * @return
	 */
	public BigDecimal getOpenRate(){
		if(Objects.nonNull(sendSum)&& sendSum != 0 && Objects.nonNull(openSum)){
			return BigDecimal.valueOf(1.0*openSum/sendSum);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 任务状态
	 * @return
	 */
	public SendStatus getSendStatus(){
		if(sendTime != null){
			if(LocalDateTime.now().isBefore(sendTime)){
				return SendStatus.NO_BEGIN;
			}else if(LocalDateTime.now().isAfter(sendTime)){
				return SendStatus.END;
			}
		}
		return null;
	}

}