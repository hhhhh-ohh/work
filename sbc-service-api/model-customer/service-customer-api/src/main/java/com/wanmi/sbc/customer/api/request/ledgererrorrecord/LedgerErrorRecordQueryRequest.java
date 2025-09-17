package com.wanmi.sbc.customer.api.request.ledgererrorrecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账接口错误记录通用查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-09 12:34:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerErrorRecordQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 业务类型 0、创建账户
	 */
	@Schema(description = "业务类型 0、创建账户")
	private Integer type;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	private String business;

	/**
	 * 重试次数
	 */
	@Schema(description = "重试次数")
	private Integer retryCount;

	/**
	 * 处理状态 0、待处理 1、处理中 2、处理成功 3、处理失败
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerErrorState
	 */
	@Schema(description = "处理状态 0、待处理 1、处理中 2、处理成功 3、处理失败")
	private Integer state;

	/**
	 * 批量状态
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerErrorState
	 */
	@Schema(description = "批量状态")
	private List<Integer> stateList;

}