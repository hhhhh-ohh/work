package com.wanmi.sbc.message.storemessagedetail.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>商家消息/公告实体类</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Data
@Entity
@Table(name = "store_message_detail")
public class StoreMessageDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 消息一级类型 0：消息 1：公告
	 */
	@Column(name = "message_type")
	@Enumerated
	private StoreMessageType messageType;

	/**
	 * 商家id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 消息标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 消息内容
	 */
	@Column(name = "content")
	private String content;

	/**
	 * 路由参数，json格式
	 */
	@Column(name = "route_param")
	private String routeParam;

	/**
	 * 发送时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "send_time")
	private LocalDateTime sendTime;

	/**
	 * 是否已读 0：未读 1：已读
	 */
	@Column(name = "is_read")
	@Enumerated
	private ReadFlag isRead;

	/**
	 * 关联的消息节点id或公告id
	 */
	@Column(name = "join_id")
	private Long joinId;

	/**
	 * 删除标识 0：未删除 1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
