package com.wanmi.sbc.vas.api.request.iep.iepsetting;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除企业购设置请求参数</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingDelByIdListRequest extends VasBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除- id List
	 */
	@Schema(description = "批量删除- id List")
	@NotEmpty
	private List<String> idList;
}
