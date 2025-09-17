package com.wanmi.sbc.marketing.electroniccoupon.model.root;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>卡密导入记录表实体类</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Data
@Entity
@Table(name = "electronic_import_record")
public class ElectronicImportRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 批次id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 卡券id
	 */
	@Column(name = "coupon_id")
	private Long couponId;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 销售开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "sale_start_time")
	private LocalDateTime saleStartTime;

	/**
	 * 销售结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "sale_end_time")
	private LocalDateTime saleEndTime;

}
