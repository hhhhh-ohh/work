package com.wanmi.sbc.marketing.giftcard.model.root;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardExchangeMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>礼品卡批次实体类</p>
 * @author 马连峰
 * @date 2022-12-10 10:59:47
 */
@Data
@Entity
@Table(name = "gift_card_batch")
public class GiftCardBatch extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gift_card_batch_id")
	private Long giftCardBatchId;

	/**
	 * 礼品卡Id
	 */
	@Column(name = "gift_card_id")
	private Long giftCardId;

	/**
	 * 兑换方式 0:卡密模式
	 */
	@Column(name = "exchange_mode")
	private GiftCardExchangeMode exchangeMode;

	/**
	 * 批次类型 0:制卡 1:发卡
	 */
	@Column(name = "batch_type")
	private GiftCardBatchType batchType;

	/**
	 * 批次数量(制/发卡数量)
	 */
	@Column(name = "batch_num")
	private Long batchNum;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Column(name = "batch_no")
	private String batchNo;

	/**
	 * 制/发卡时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "generate_time")
	private LocalDateTime generateTime;

	/**
	 * 制/发卡人
	 */
	@Column(name = "generate_person")
	private String generatePerson;

	/**
	 * 起始卡号
	 */
	@Column(name = "start_card_no")
	private String startCardNo;

	/**
	 * 结束卡号
	 */
	@Column(name = "end_card_no")
	private String endCardNo;

	/**
	 * 审核状态 0:待审核 1:已审核通过 2:审核不通过
	 */
	@Column(name = "audit_status")
	private AuditStatus auditStatus;

	/**
	 * 审核驳回原因
	 */
	@Column(name = "audit_reason")
	private String auditReason;

	/**
	 * excel导入的文件oss地址（仅批量发卡时存在）
	 */
	@Column(name = "excel_file_path")
	private String excelFilePath;

	/**
	 * 是否导出小程序一卡一码URL，0:不导出，1：导出
	 */
	@Column(name = "export_mini_code_type")
	@Enumerated
	private DefaultFlag exportMiniCodeType;

	/**
	 * 是否导出H5一卡一码URL，0:不导出，1：导出
	 */
	@Column(name = "export_web_code_type")
	@Enumerated
	private DefaultFlag exportWebCodeType;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "gift_card_id", insertable = false, updatable = false)
	@NotFound(action= NotFoundAction.IGNORE)
	private GiftCard giftCard;
}
