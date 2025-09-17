package com.wanmi.sbc.message.minimsgactivitysetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>小程序订阅消息配置表实体类</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Data
@Entity
@Table(name = "mini_program_subscribe_message_activity_setting")
public class MiniMsgActivitySetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 活动名称
	 */
	@Column(name = "activity_name")
	private String activityName;

	/**
	 * 开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "start_time")
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "end_time")
	private LocalDateTime endTime;

	/**
	 * 活动内容
	 */
	@Column(name = "context")
	private String context;

	/**
	 * 温馨提示
	 */
	@Column(name = "tips")
	private String tips;

	/**
	 * 要跳转的页面
	 */
	@Column(name = "to_page")
	private String toPage;

	/**
	 * 推送类型 0 立即发送  1 定时发送
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 定时发送时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "send_time")
	private LocalDateTime sendTime;

	/**
	 * 预计推送人数
	 */
	@Column(name = "pre_count")
	private Integer preCount;

	/**
	 * 实际推送人数
	 */
	@Column(name = "real_count")
	private Integer realCount;

	/**
	 * 推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败
	 */
	@Column(name = "send_status")
	private ProgramSendStatus sendStatus;

	/**
	 * 删除标识 0未删除 1删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 是否已经扫描到 false 否  true 是
	 */
	@Column(name = "scan_flag")
	private Boolean scanFlag;

}
