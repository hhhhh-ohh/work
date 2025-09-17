package com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除请求参数</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCorrelationModelSettingDelByIdListRequest extends VasBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Integer> idList;
}
