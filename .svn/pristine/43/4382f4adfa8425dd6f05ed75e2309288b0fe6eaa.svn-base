package com.wanmi.sbc.goods.api.provider.xsitegoodscate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateAddRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateDeleteRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateQueryByCateUuidRequest;
import com.wanmi.sbc.goods.api.response.xsitegoodscate.XsiteGoodsCateByCateUuidResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>魔方商品分类表服务Provider</p>
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@FeignClient(value = "${application.goods.name}", contextId = "XsiteGoodsCateProvider")
public interface XsiteGoodsCateProvider {

	/**
	 * 新增魔方商品分类表API
	 */
	@PostMapping("/goods/${application.goods.version}/xsitegoodscate/add")
	BaseResponse add(@RequestBody @Valid XsiteGoodsCateAddRequest xsiteGoodsCateAddRequest);

	/**
	 * 删除魔方商品分类表API
	 */
	@PostMapping("/goods/${application.goods.version}/xsitegoodscate/delete")
	BaseResponse delete(@RequestBody @Valid XsiteGoodsCateDeleteRequest xsiteGoodsCateDeleteRequest);

	/**
	 * 查询魔方商品分类表API
	 */
	@PostMapping("/goods/${application.goods.version}/xsitegoodscate/findByCateUuid")
	BaseResponse<XsiteGoodsCateByCateUuidResponse> findByCateUuid(@RequestBody @Valid XsiteGoodsCateQueryByCateUuidRequest xsiteGoodsCateQueryByCateUuidRequest);

}

