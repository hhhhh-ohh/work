package com.wanmi.sbc.marketing.api.request.drawrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量删除抽奖记录表请求参数</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordDelByIdListRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-抽奖记录主键List
	 */
	@Schema(description = "批量删除-抽奖记录主键List")
	@NotEmpty
	private List<Long> idList;
}