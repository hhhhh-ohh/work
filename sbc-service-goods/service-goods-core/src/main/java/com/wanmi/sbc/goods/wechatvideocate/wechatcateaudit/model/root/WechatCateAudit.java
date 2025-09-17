package com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.model.root;

import com.wanmi.sbc.common.enums.AuditStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信类目审核状态实体类</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@Data
@Entity
@Table(name = "wechat_cate_audit")
@DynamicInsert
public class WechatCateAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 微信返回的审核id
	 */
	@Column(name = "audit_id")
	private String auditId;

	/**
	 * 微信类目id
	 */
	@Column(name = "wechat_cate_id")
	private Long wechatCateId;

	/**
	 * 映射的平台类目
	 */
	@Column(name = "cate_ids")
	private String cateIds;

	/**
	 * 审核状态，0：待审核，1：审核通过，2：审核不通过
	 */
	@Column(name = "audit_status")
	@Enumerated
	private AuditStatus auditStatus;

	/**
	 * 审核不通过原因
	 */
	@Column(name = "reject_reason")
	private String rejectReason;

	/**
	 * createTime
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * createPerson
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * updateTime
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	/**
	 * 商品资质
	 */
	@Column(name = "product_qualification_urls")
	private String productQualificationUrls;

}