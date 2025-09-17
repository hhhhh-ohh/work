package com.wanmi.sbc.marketing.giftcard.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>用户礼品卡实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:02:19
 */
@Data
@Entity
@Table(name = "uniforms_gift_card_binding")
public class UniformsGiftCardBinding implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 校服提货卡绑定Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uniforms_gift_card_binding_id")
	private Long uniformsGiftCardBindingId;

	/**
	 * 地市
	 */
	@Column(name = "city")
	private String city;

	/**
	 * 季节
	 */
	@Column(name = "season")
	private String season;

	/**
	 * 学段
	 */
	@Column(name = "school_period")
	private String schoolPeriod;

	/**
	 * 礼品卡Id
	 */
	@Column(name = "gift_card_id")
	private Long giftCardId;
}
