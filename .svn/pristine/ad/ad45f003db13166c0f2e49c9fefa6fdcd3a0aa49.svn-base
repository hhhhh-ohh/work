package com.wanmi.sbc.message.storenoticescope.model.root;


import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>商家公告发送范围实体类</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Data
@Entity
@Table(name = "store_notice_scope")
public class StoreNoticeScope {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 公告id
	 */
	@Column(name = "notice_id")
	private Long noticeId;

	/**
	 * 范围分类 1：商家 2：供应商
	 */
	@Column(name = "scope_cate")
	private StoreNoticeReceiveScope scopeCate;

	/**
	 * 0：自定义
	 */
	@Column(name = "scope_type")
	private StoreNoticeTargetScope scopeType;

	/**
	 * 目标id
	 */
	@Column(name = "scope_id")
	private Long scopeId;

}
