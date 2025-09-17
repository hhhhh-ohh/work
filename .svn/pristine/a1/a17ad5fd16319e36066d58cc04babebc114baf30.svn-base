package com.wanmi.sbc.setting.api.request.helpcenterarticlecate;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除帮助中心文章信息请求参数</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleCateDelByIdListRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键IdList
	 */
	@Schema(description = "批量删除-主键IdList")
	@NotEmpty
	private List<Long> idList;
}
