package com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root;


import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>微信小程序支付发货信息实体类</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Data
@Entity
@Table(name = "wx_pay_upload_shipping_info")
public class WxPayUploadShippingInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 支付类型，0：商品订单支付；1：授信还款；2：付费会员
	 */
	@Column(name = "pay_type")
	private Integer payType;

	/**
	 * 支付订单id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * 支付流水id
	 */
	@Column(name = "transaction_id")
	private String transactionId;

	/**
	 * 商户号
	 */
	@Column(name = "mch_id")
	private String mchId;

	/**
	 * 接口调用返回结果内容
	 */
	@Column(name = "result_context")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1：处理成功；2：处理失败
	 */
	@Column(name = "result_status")
	private Integer resultStatus;

	/**
	 * 处理失败次数
	 */
	@Column(name = "error_num")
	private Integer errorNum;

}
