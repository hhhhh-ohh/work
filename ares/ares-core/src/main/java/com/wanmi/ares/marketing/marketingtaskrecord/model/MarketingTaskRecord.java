package com.wanmi.ares.marketing.marketingtaskrecord.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * <p>营销分析定时任务记录表实体类</p>
 * @author zhangwenchang
 * @date 2021-01-22 14:49:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingTaskRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 创建时间
	 */
	private LocalDate createTime;

	/**
	 * 营销类型
	 */
	private int marketingType;
}