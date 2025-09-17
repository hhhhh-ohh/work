package com.wanmi.sbc.setting.api.request.storeexpresscompanyrela;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除店铺快递公司关联表请求参数</p>
 * @author lq
 * @date 2019-11-05 16:12:13
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExpressCompanyRelaDelByIdListRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键UUIDList
	 */
	@Schema(description = "批量删除-主键UUIDList")
	@NotEmpty
	private List<Long> idList;
}