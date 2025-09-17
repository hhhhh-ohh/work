package com.wanmi.sbc.setting.api.request.expresscompany;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除物流公司请求参数</p>
 * @author lq
 * @date 2019-11-05 16:10:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyDelByIdListRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键ID,自增List
	 */
	@Schema(description = "批量删除-主键ID,自增List")
	@NotEmpty
	private List<Long> expressCompanyIdList;
}