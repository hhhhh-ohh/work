package com.wanmi.sbc.setting.api.request.businessconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除招商页设置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:09:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigDelByIdListRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-招商页设置主键List
	 */
	@Schema(description = "批量删除-招商页设置主键List")
	@NotEmpty
	private List<Integer> businessConfigIdList;
}