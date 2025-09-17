package com.wanmi.sbc.goods.provider.impl.goodstemplate;

import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.api.response.goodstemplate.goodsTemplateJoinResponse;
import com.wanmi.sbc.goods.goodstemplate.service.GoodsTemplateRelService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodstemplate.GoodsTemplateQueryProvider;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplatePageResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateListResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsTemplateVO;
import com.wanmi.sbc.goods.goodstemplate.service.GoodsTemplateService;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplate;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>GoodsTemplate查询服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@RestController
@Validated
public class GoodsTemplateQueryController implements GoodsTemplateQueryProvider {
	@Autowired
	private GoodsTemplateService goodsTemplateService;

	@Autowired
	private GoodsTemplateRelService goodsTemplateRelService;

	@Override
	public BaseResponse<GoodsTemplatePageResponse> page(@RequestBody @Valid GoodsTemplatePageRequest goodsTemplatePageReq) {
		GoodsTemplateQueryRequest queryReq = KsBeanUtil.convert(goodsTemplatePageReq, GoodsTemplateQueryRequest.class);
		Page<GoodsTemplate> goodsTemplatePage = goodsTemplateService.page(queryReq);
		Page<GoodsTemplateVO> newPage = goodsTemplatePage.map(entity -> goodsTemplateService.wrapperVo(entity));
		MicroServicePage<GoodsTemplateVO> microPage = new MicroServicePage<>(newPage, goodsTemplatePageReq.getPageable());
		GoodsTemplatePageResponse finalRes = new GoodsTemplatePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsTemplateListResponse> list(@RequestBody @Valid GoodsTemplateListRequest goodsTemplateListReq) {
		GoodsTemplateQueryRequest queryReq = KsBeanUtil.convert(goodsTemplateListReq, GoodsTemplateQueryRequest.class);
		List<GoodsTemplate> goodsTemplateList = goodsTemplateService.list(queryReq);
		List<GoodsTemplateVO> newList = goodsTemplateList.stream().map(entity -> goodsTemplateService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsTemplateListResponse(newList));
	}

	@Override
	public BaseResponse<GoodsTemplateByIdResponse> getById(@RequestBody @Valid GoodsTemplateByIdRequest goodsTemplateByIdRequest) {
		GoodsTemplate goodsTemplate =
		goodsTemplateService.getOne(goodsTemplateByIdRequest.getId());
		return BaseResponse.success(new GoodsTemplateByIdResponse(goodsTemplateService.wrapperVo(goodsTemplate)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid GoodsTemplateExportRequest request) {
		GoodsTemplateQueryRequest queryReq = KsBeanUtil.convert(request, GoodsTemplateQueryRequest.class);
		Long total = goodsTemplateService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<goodsTemplateJoinResponse> joinGoodsDetails(GoodsTemplateByIdRequest request) {
		List<String> goodsIdList = goodsTemplateRelService.joinGoodsDetails(request.getId());
		return BaseResponse.success(goodsTemplateJoinResponse.builder().goodsIdList(goodsIdList).templateId(request.getId()).build());
	}

	@Override
	public BaseResponse<GoodsTemplateByIdResponse> getByGoodsId(GoodsTemplateByGoodsIdRequest request) {
		return BaseResponse
				.success(GoodsTemplateByIdResponse
						.builder()
						.goodsTemplateVO(goodsTemplateService.getByGoodsId(request))
						.build());
	}
}

