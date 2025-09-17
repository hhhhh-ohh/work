package com.wanmi.sbc.empower.logisticssetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>物流配置实体类</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Data
@Entity
@Table(name = "logistics_setting")
public class LogisticsSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 配置类型 0: 快递100 1:达达配送
	 */
	@Column(name = "logistics_type")
	@Enumerated
	private LogisticsType logisticsType;

	/**
	 * 用户申请授权key
	 */
	@Column(name = "customer_key")
	private String customerKey;

	/**
	 * 授权秘钥key
	 */
	@Column(name = "delivery_key")
	private String deliveryKey;

	/**
	 * 实时查询是否开启 0: 否, 1: 是
	 */
	@Column(name = "real_time_status")
	@Enumerated
	private DefaultFlag realTimeStatus;

	/**
	 * 是否开启订阅 0: 否, 1: 是
	 */
	@Column(name = "subscribe_status")
	@Enumerated
	private DefaultFlag subscribeStatus;

	/**
	 * 回调地址
	 */
	@Column(name = "callback_url")
	private String callbackUrl;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;


	/**
	 * 是否启用 0: 否, 1: 是，暂时给达达使用
	 */
	@Column(name = "enable_status")
	@Enumerated
	private EnableStatus enableStatus;


	/**
	 * 达达商户id
	 */
	@Column(name = "shop_no")
	private String shopNo;

}
