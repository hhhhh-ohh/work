package com.wanmi.sbc.goods.provider.impl.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateSimpleRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleByInfoIdRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleListRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSalePageRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedValidateRequest;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSaleByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSaleListResponse;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSalePageResponse;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSalePurchaseResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedPurchaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import com.wanmi.sbc.goods.goodsrestrictedsale.model.root.GoodsRestrictedSale;
import com.wanmi.sbc.goods.goodsrestrictedsale.service.GoodsRestrictedSaleService;
import com.wanmi.sbc.goods.spec.service.GoodsInfoSpecDetailRelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>限售配置查询服务接口实现</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@RestController
@Validated
public class GoodsRestrictedSaleQueryController implements GoodsRestrictedSaleQueryProvider {
	@Autowired
	private GoodsRestrictedSaleService goodsRestrictedSaleService;

	@Autowired
	private GoodsInfoSpecDetailRelService goodsInfoSpecDetailRelService;

	@Override
	public BaseResponse<GoodsRestrictedSalePageResponse> page(@RequestBody @Valid GoodsRestrictedSalePageRequest goodsRestrictedSalePageReq) {
		GoodsRestrictedSaleQueryRequest queryReq = new GoodsRestrictedSaleQueryRequest();
		KsBeanUtil.copyPropertiesThird(goodsRestrictedSalePageReq, queryReq);
		Page<GoodsRestrictedSale> goodsRestrictedSalePage = goodsRestrictedSaleService.page(queryReq);
		Page<GoodsRestrictedSaleVO> newPage = goodsRestrictedSalePage.map(entity -> goodsRestrictedSaleService.wrapperVo(entity));
		//对列表规格字段赋值
		newPage.getContent().forEach(list->{
			List<GoodsInfoVO> goodsInfos = new ArrayList<>();
			goodsInfos.add(list.getGoodsInfo());
			goodsInfoSpecDetailRelService.fillSpecDetail(goodsInfos);
		});
		MicroServicePage<GoodsRestrictedSaleVO> microPage = new MicroServicePage<>(newPage, goodsRestrictedSalePageReq.getPageable());
		GoodsRestrictedSalePageResponse finalRes = new GoodsRestrictedSalePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsRestrictedSaleListResponse> list(@RequestBody @Valid GoodsRestrictedSaleListRequest goodsRestrictedSaleListReq) {
		GoodsRestrictedSaleQueryRequest queryReq = new GoodsRestrictedSaleQueryRequest();
		KsBeanUtil.copyPropertiesThird(goodsRestrictedSaleListReq, queryReq);
		List<GoodsRestrictedSale> goodsRestrictedSaleList = goodsRestrictedSaleService.list(queryReq);
		List<GoodsRestrictedSaleVO> newList = goodsRestrictedSaleList.stream().map(entity -> goodsRestrictedSaleService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsRestrictedSaleListResponse(newList));
	}

	@Override
	public BaseResponse<GoodsRestrictedSaleByIdResponse> getById(@RequestBody @Valid GoodsRestrictedSaleByIdRequest goodsRestrictedSaleByIdRequest) {
		GoodsRestrictedSale goodsRestrictedSale = goodsRestrictedSaleService.getById(goodsRestrictedSaleByIdRequest.getRestrictedId());
		// 前端展示，限售地址需单独处理
		if (Objects.nonNull(goodsRestrictedSale) && CollectionUtils.isNotEmpty(goodsRestrictedSale.getGoodsRestrictedCustomerRelas())){
			goodsRestrictedSale.getGoodsRestrictedCustomerRelas().stream().filter(
					goodsRestrictedCustomerRela -> StringUtils.isNotBlank(goodsRestrictedCustomerRela.getAddressId()))
					.peek(goodsRestrictedCustomerRela->{
						String addressId = goodsRestrictedCustomerRela.getAddressId();
						if (addressId.contains("|")){
							int size = addressId.lastIndexOf("|") + 1;
							goodsRestrictedCustomerRela.setAddressId(addressId.substring(size));
						}
					}).collect(Collectors.toList());
		}
		GoodsRestrictedSaleVO goodsRestrictedSaleVO = goodsRestrictedSaleService.wrapperVo(goodsRestrictedSale);
		//对详情页规格字段赋值
		List<GoodsInfoVO> goodsInfos = new ArrayList<>();
		goodsInfos.add(goodsRestrictedSaleVO.getGoodsInfo());
		goodsInfoSpecDetailRelService.fillSpecDetail(goodsInfos);
		return BaseResponse.success(new GoodsRestrictedSaleByIdResponse(goodsRestrictedSaleVO));
	}

	@Override
	public BaseResponse validateToByImm(@RequestBody @Valid GoodsRestrictedValidateRequest request) {
        goodsRestrictedSaleService.validateGoodsRestricted(
                request.getGoodsInfoId(),
                request.getCustomerVO(),
                request.getBuyNum(),
                request.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse validateOrderRestricted(@RequestBody @Valid GoodsRestrictedBatchValidateRequest restrictedBatchValidateRequest) {
		goodsRestrictedSaleService.validateBatchGoodsRestricted(restrictedBatchValidateRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse validateOrderRestricted(@RequestBody @Valid GoodsRestrictedBatchValidateSimpleRequest request) {
		goodsRestrictedSaleService.validateBatchGoodsRestricted(request.getGoodsRestrictedValidateVOS(),request.getCustomerVO(),request.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<GoodsRestrictedSalePurchaseResponse> validatePurchaseRestricted(@Valid GoodsRestrictedBatchValidateRequest restrictedBatchValidateRequest) {
		List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS = goodsRestrictedSaleService.getGoodsRestrictedInfo(restrictedBatchValidateRequest);
		return BaseResponse.success(GoodsRestrictedSalePurchaseResponse.builder()
				.goodsRestrictedPurchaseVOS(goodsRestrictedPurchaseVOS)
				.build());
	}

	@Override
	public BaseResponse<GoodsRestrictedSaleByIdResponse> findInGoodsInfoId(@Valid GoodsRestrictedSaleByInfoIdRequest request) {
		GoodsRestrictedSaleByIdResponse response = goodsRestrictedSaleService.findInGoodsInfoId(request);
		return BaseResponse.success(response);
	}


}

