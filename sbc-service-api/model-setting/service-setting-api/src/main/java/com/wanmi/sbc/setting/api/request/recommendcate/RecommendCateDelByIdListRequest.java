package com.wanmi.sbc.setting.api.request.recommendcate;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除笔记分类表请求参数</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateDelByIdListRequest extends SettingBaseRequest {

	private static final long serialVersionUID = 3186435446750295300L;

	/**
	 * 批量删除-分类idList
	 */
	@Schema(description = "批量删除-分类idList")
	@NotEmpty
	private List<Long> cateIdList;
}
