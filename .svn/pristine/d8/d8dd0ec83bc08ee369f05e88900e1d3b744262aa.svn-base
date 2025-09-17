package com.wanmi.sbc.marketing.electroniccoupon.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>电子卡券表实体类</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Data
@Entity
@Table(name = "electronic_coupon")
public class ElectronicCoupon {
	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 电子卡券名称
	 */
	@Column(name = "coupon_name")
	private String couponName;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 已发送数
	 */
	@Column(name = "send_num")
	private Long sendNum;

	/**
	 * 未发送数
	 */
	@Column(name = "not_send_num")
	private Long notSendNum;

	/**
	 * 失效数
	 */
	@Column(name = "invalid_num")
	private Long invalidNum;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 冻结库存
	 */
	@Column(name = "freeze_stock")
	private Long freezeStock;

	/**
	 * 绑定标识 0、未绑定 1、已绑定
	 */
	@Column(name = "binding_flag")
	private Boolean bindingFlag;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private LocalDateTime createTime;


}
