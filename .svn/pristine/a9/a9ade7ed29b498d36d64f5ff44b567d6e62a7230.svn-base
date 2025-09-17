package com.wanmi.sbc.message.storenoticesend.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>商家公告实体类</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Data
@Entity
@Table(name = "store_notice_send")
public class StoreNoticeSend extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 公告标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 公告内容
	 */
	@Column(name = "content")
	private String content;

	/**
	 * 接收范围 0：全部 1：商家 2：供应商
	 */
	@Column(name = "receive_scope")
	@Enumerated
	private StoreNoticeReceiveScope receiveScope;

	/**
	 * 商家范围 0：全部 1：自定义商家
	 */
	@Column(name = "supplier_scope")
	@Enumerated
	private StoreNoticeTargetScope supplierScope;

	/**
	 * 供应商范围 0：全部 1：自定义供应商
	 */
	@Column(name = "provider_scope")
	@Enumerated
	private StoreNoticeTargetScope providerScope;

	/**
	 * 推送时间类型 0：立即、1：定时
	 */
	@Column(name = "send_time_type")
	private SendType sendTimeType;

	/**
	 * 发送时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "send_time")
	private LocalDateTime sendTime;

	/**
	 * 公告发送状态 0：未发送 1：发送中 2：已发送 3：发送失败 4：已撤回
	 */
	@Column(name = "send_status")
	@Enumerated
	private StoreNoticeSendStatus sendStatus;

	/**
	 * 定时任务扫描标识 0：未扫面 1：已扫描
	 */
	@Column(name = "scan_flag")
	@Enumerated
	private BoolFlag scanFlag;

	/**
	 * 删除标识 0：未删除 1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
