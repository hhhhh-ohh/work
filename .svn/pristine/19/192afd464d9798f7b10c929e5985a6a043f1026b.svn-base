package com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除新人专享设置请求参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseConfigDelByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Integer> idList;
}
