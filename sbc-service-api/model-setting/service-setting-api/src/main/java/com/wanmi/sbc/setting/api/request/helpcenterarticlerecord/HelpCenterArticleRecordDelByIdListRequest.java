package com.wanmi.sbc.setting.api.request.helpcenterarticlerecord;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除帮助中心文章记录请求参数</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleRecordDelByIdListRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键IdList
	 */
	@Schema(description = "批量删除-主键IdList")
	@NotEmpty
	private List<Long> idList;
}
