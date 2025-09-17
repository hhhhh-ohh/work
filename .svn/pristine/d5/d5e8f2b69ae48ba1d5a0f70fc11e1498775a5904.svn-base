package com.wanmi.sbc.message.api.request.vopmessage;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.message.bean.enums.VopLogType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>Vop消息发送表保存服务Request</p>
 * @author xufeng
 * @date 2022-05-20 10:53:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopLogAddRequest extends BaseRequest {

	/**
	 * 类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）
	 */
	@Schema(description = "类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）")
	private VopLogType vopLogType;

	/**
	 * 商品id或者订单id
	 */
	@Schema(description = "商品id或者订单id")
	private String majorId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	private String content;

}