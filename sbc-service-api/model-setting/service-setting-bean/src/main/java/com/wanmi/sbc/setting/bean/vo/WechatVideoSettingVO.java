package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.setting.bean.enums.SettingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>视频直播带货应用设置VO</p>
 * @author zhaiqiankun
 * @date 2022-04-07 14:54:30
 */
@Schema
@Data
public class WechatVideoSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照
	 */
	@Schema(description = "设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照")
	private SettingType settingType;

	/**
	 * 设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id
	 */
	@Schema(description = "设置数据，json格式 consigneeName:收件人姓名,contactMobile:联系方式,addressDetail:详细地址,tradeId:订单id,returnTradeId:退单id,businessLicence:营业执照")
	private String context;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用2:禁用")
	private Integer status;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	private String failReason;

	/**
	 * 控件是否禁用
	 */
	@Schema(description = "控件是否禁用")
	private boolean disable = true;
}