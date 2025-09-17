package com.wanmi.sbc.message.vopmessage.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.message.bean.enums.VopLogType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>Vop日志实体类</p>
 * @author xufeng
 * @date 2022-05-20 15:53:00
 */
@Data
@Entity
@Table(name = "vop_log")
public class VopLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String logId;

	/**
	 * 类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）
	 */
	@Column(name = "log_type")
	private VopLogType vopLogType;

	/**
	 * 消息时间
	 */
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	@Column(name = "date")
	private LocalDate date;

	/**
	 * 商品id或者订单id
	 */
	@Column(name = "major_id")
	private String majorId;

	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;

	/**
	 * json信息
	 */
	@Column(name = "content")
	private String content;

	/**
	 * 删除标识 0：未删除、1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;


}