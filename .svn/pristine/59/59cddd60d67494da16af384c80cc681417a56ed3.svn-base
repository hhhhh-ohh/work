package com.wanmi.sbc.customer.ledgererrorrecord.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>分账接口错误记录实体类</p>
 * @author 许云鹏
 * @date 2022-07-09 12:34:25
 */
@Data
@Entity
@Table(name = "ledger_error_record")
public class LedgerErrorRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 业务类型 0、创建账户
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 业务id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * 重试次数
	 */
	@Column(name = "retry_count")
	private Integer retryCount;

	/**
	 * 处理状态 0、待处理 1、处理中 2、处理成功 3、处理失败
	 */
	@Column(name = "state")
	private Integer state;

	/**
	 * 错误信息
	 */
	@Column(name = "error_info")
	private String errorInfo;

}
