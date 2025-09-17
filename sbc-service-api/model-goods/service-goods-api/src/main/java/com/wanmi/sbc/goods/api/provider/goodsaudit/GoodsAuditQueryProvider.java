package com.wanmi.sbc.goods.api.provider.goodsaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goods.GoodsAuditViewByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditPageRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAuditViewByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditListResponse;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditPageResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>商品审核查询服务Provider</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsAuditQueryProvider")
public interface GoodsAuditQueryProvider {

	/**
	 * 分页查询商品审核API
	 *
	 * @author 黄昭
	 * @param goodsAuditPageReq 分页请求参数和筛选对象 {@link GoodsAuditPageRequest}
	 * @return 商品审核分页列表信息 {@link GoodsAuditPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/page")
	BaseResponse<GoodsAuditPageResponse> page(@RequestBody @Valid GoodsAuditPageRequest goodsAuditPageReq);

	/**
	 * 单个查询商品审核API
	 *
	 * @author 黄昭
	 * @param goodsAuditByIdRequest 单个查询商品审核请求参数 {@link GoodsAuditByIdRequest}
	 * @return 商品审核详情 {@link GoodsAuditByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/goodsaudit/get-by-id")
	BaseResponse<GoodsAuditByIdResponse> getById(@RequestBody @Valid GoodsAuditByIdRequest goodsAuditByIdRequest);

	/**
	 * 根据编号查询审核商品视图信息
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/get-view-id")
	BaseResponse<GoodsAuditViewByIdResponse> getViewById(@RequestBody @Valid GoodsAuditViewByIdRequest request);

	/**
	 * 查询待审核审核商品
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/get-wait-check")
	BaseResponse<GoodsAuditListResponse> getWaitCheckGoodsAudit(@RequestBody @Valid GoodsAuditQueryRequest request);

	/**
	 * 批量查询商品审核API
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/get-by-ids")
	BaseResponse<GoodsAuditListResponse> getByIds(@RequestBody GoodsAuditQueryRequest request);

	/**
	 * 通过oldGoodsIds获取商品
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/get-by-old-ids")
	BaseResponse<GoodsAuditListResponse> getListByOldGoodsIds(@RequestBody GoodsAuditQueryRequest request);

	/**
	 * 查询审核商品列表
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/goods/audit/list-by-condition")
	BaseResponse<GoodsAuditListResponse> listByCondition(@RequestBody GoodsAuditQueryRequest request);
}

