package com.wanmi.sbc.setting.api.request.wechatvideo;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.SettingType;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>视频直播带货应用设置修改参数</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:00:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingModifyFieldRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id - 集合
	 */
	@Schema(description = "id - 集合")
	private List<Integer> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Integer id;

	/**
	 * 设置名称
	 */
	@Schema(description = "设置名称")
	private String name;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer settingSort;

	/**
	 * 设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景
	 */
	@Schema(description = "设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景")
	private SettingType settingType;

	/**
	 * 状态,0:未启用1:已启用2:禁用
	 */
	@Schema(description = "状态,0:未启用1:已启用2:禁用")
	private Integer status;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	private String failReason;


	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private Integer delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id
	 */
	@Schema(description = "设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id,businessLicence:营业执照")
	private String context;


	/**
	 * 修改条件字段
	 */
	@Schema(description = "修改条件字段")
	private List<String> whereFields;

	/**
	 * 修改数据字段
	 */
	@Schema(description = "修改数据字段")
	private List<String> updateFields;

}
