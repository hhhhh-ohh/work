package com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.model.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信类目资质实体类</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
@Data
@Entity
@Table(name = "wechat_cate_certificate")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class WechatCateCertificate implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 微信类目id
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 图片路径
	 */
	@Column(name = "certificate_url")
	private String certificateUrl;

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

}