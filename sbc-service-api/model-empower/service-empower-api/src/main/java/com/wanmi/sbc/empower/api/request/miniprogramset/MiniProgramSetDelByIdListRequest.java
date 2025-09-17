package com.wanmi.sbc.empower.api.request.miniprogramset;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除小程序配置请求参数</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetDelByIdListRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-小程序配置主键List
	 */
	@Schema(description = "批量删除-小程序配置主键List")
	@NotEmpty
	private List<Integer> idList;
}
