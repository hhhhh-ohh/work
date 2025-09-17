package com.wanmi.sbc.empower.logisticslogdetail.model.root;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wanmi.sbc.common.enums.DeleteFlag;

import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>物流记录明细实体类</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Data
@Entity
@Table(name = "logistics_log_detail")
public class LogisticsLogDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 内容上海分拨中心/装件入车扫
	 */
	@Column(name = "context")
	private String context;

	/**
	 * 时间，原始格式
	 */
	@Column(name = "time")
	private String time;

	/**
	 * 本数据元对应的签收状态
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 本数据元对应的行政区域的编码
	 */
	@Column(name = "area_code")
	private String areaCode;

	/**
	 * 本数据元对应的行政区域的名称
	 */
	@Column(name = "area_name")
	private String areaName;

	/**
	 * 物流记录id
	 */
	@Column(name = "logistics_log_id")
	private String logisticsLogId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 物流日志
	 */
	@JsonManagedReference
	@JoinColumn(name = "logistics_log_id", insertable = false, updatable = false)
	@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private LogisticsLog logisticsLog;

}
