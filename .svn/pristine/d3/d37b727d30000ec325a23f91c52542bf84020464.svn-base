package com.wanmi.sbc.setting.api.request.recommend;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

/**
 * <p>种草信息表分页查询请求参数</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPageRequest extends BaseQueryRequest {

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	@Length(max=40)
	private String title;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

	/**
	 * 内容状态 0:隐藏 1:公开
	 */
	@Schema(description = "内容状态 0:隐藏 1:公开")
	private Integer status;

	/**
	 * 排序类型 1:访问量 2:访客量 3:点赞数 4:转发数
	 */
	@Schema(description = "排序类型 1:访问量 2:访客量 3:点赞数 4:转发数")
	private Integer pageSortType;

	/**
	 * 是否置顶 0:否 1:是
	 */
	@Schema(description = "是否置顶 0:否 1:是")
	private Integer isTop;

	/**
	 * 升降序 1:升序 2:降序
	 */
	@Schema(description = "升降序 1:升序 2:降序")
	private Integer pageSort;

	/**
	 * 删除状态
	 */
	@Schema(description = "删除状态")
	private DeleteFlag delFlag;

	/**
	 * 保存状态 1:草稿 2:已发布 3:修改已发布
	 */
	@Schema(description = "保存状态 1:草稿 2:已发布 3:修改已发布")
	private List<Integer> saveStatusList;

	@Override
	public void checkParam() {
		if (Objects.nonNull(pageSortType) && Objects.isNull(pageSort)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}