package com.wanmi.sbc.marketing.provider.impl.communityGoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.communitysku.CommunitySkuQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityValidateRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.CommunitySkuRelQueryRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.UpdateSalesRequest;
import com.wanmi.sbc.marketing.api.response.communitysku.CommunitySkuListResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.marketing.communitysku.model.root.CommunitySkuRel;
import com.wanmi.sbc.marketing.communitysku.service.CommunitySkuRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@RestController
@Validated
public class CommunityGoodsQueryController implements CommunitySkuQueryProvider {
	@Autowired
	private CommunitySkuRelService communitySkuRelService;


	/**
	 * @param communitySkuRelQueryRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.communitysku.CommunitySkuListResponse>
	 * @description 社区购商品列表
	 * @author edz
	 * @date: 2023/7/24 17:31
	 */
	@Override
	public BaseResponse<CommunitySkuListResponse> list(@RequestBody @Valid CommunitySkuRelQueryRequest communitySkuRelQueryRequest) {
		List<CommunitySkuRel> communitySkuRels = communitySkuRelService.list(communitySkuRelQueryRequest);
		List<CommunitySkuRelVO> newList =
				communitySkuRels.stream().map(entity -> communitySkuRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CommunitySkuListResponse(newList));
	}

	/**
	 * @param updateSalesRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.common.base.BaseResponse>
	 * @description 社区团购库存处理
	 * @author bob
	 * @date: 2023/7/28 00:26
	 */
	@Override
	public BaseResponse<BaseResponse> updateSales(@RequestBody @Valid UpdateSalesRequest updateSalesRequest) {
		communitySkuRelService.updateSales(updateSalesRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse validate(@RequestBody @Valid CommunityActivityValidateRequest request) {
		communitySkuRelService.validate(request);
		return BaseResponse.SUCCESSFUL();
	}
}

