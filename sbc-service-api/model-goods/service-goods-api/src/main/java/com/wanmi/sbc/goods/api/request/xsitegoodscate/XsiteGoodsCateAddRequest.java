package com.wanmi.sbc.goods.api.request.xsitegoodscate;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.vo.XsiteGoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>魔方商品分类表新增参数</p>
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XsiteGoodsCateAddRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 页面唯一值
	 */
	@Schema(description = "pageCode")
	@NotNull
	private String pageCode;

	@NotEmpty
	List<XsiteGoodsCateVO> xsiteGoodsCateVOS;

}