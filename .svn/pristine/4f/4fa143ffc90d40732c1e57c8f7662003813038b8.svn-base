package com.wanmi.sbc.setting.provider.impl.storeresourcecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storeresourcecate.StoreResourceCateQueryProvider;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckChildRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckResourceRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateListRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCatePageRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateQueryRequest;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateByIdResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateListResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCatePageResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceCateVO;
import com.wanmi.sbc.setting.systemresourcecate.model.root.SystemResourceCate;
import com.wanmi.sbc.setting.systemresourcecate.service.SystemResourceCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>店铺资源资源分类表查询服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:13:19
 */
@RestController
@Validated
public class StoreResourceCateQueryController implements StoreResourceCateQueryProvider {
	@Autowired
	private SystemResourceCateService systemResourceCateService;

	@Override
	public BaseResponse<SystemResourceCatePageResponse> page(@RequestBody @Valid SystemResourceCatePageRequest storeResourceCatePageReq) {
		SystemResourceCateQueryRequest queryReq = new SystemResourceCateQueryRequest();
		KsBeanUtil.copyPropertiesThird(storeResourceCatePageReq, queryReq);
		Page<SystemResourceCate> storeResourceCatePage = systemResourceCateService.page(queryReq);
		Page<SystemResourceCateVO> newPage = storeResourceCatePage.map(entity -> systemResourceCateService.wrapperVo(entity));
		MicroServicePage<SystemResourceCateVO> microPage = new MicroServicePage<>(newPage, storeResourceCatePageReq.getPageable());
		SystemResourceCatePageResponse finalRes = new SystemResourceCatePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<SystemResourceCateListResponse> list(@RequestBody @Valid SystemResourceCateListRequest storeResourceCateListReq) {
		SystemResourceCateQueryRequest queryReq = new SystemResourceCateQueryRequest();
		KsBeanUtil.copyPropertiesThird(storeResourceCateListReq, queryReq);
		queryReq.putSort("cateId", SortType.ASC.toValue());
		queryReq.putSort("createTime", SortType.DESC.toValue());
		queryReq.putSort("sort", SortType.ASC.toValue());
		queryReq.setDelFlag( DeleteFlag.NO);
		List<SystemResourceCate> storeResourceCateList = systemResourceCateService.list(queryReq);
		List<SystemResourceCateVO> newList = storeResourceCateList.stream().map(entity -> systemResourceCateService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new SystemResourceCateListResponse(newList));
	}

	@Override
	public BaseResponse<SystemResourceCateByIdResponse> getById(@RequestBody @Valid SystemResourceCateByIdRequest storeResourceCateByIdRequest) {
		SystemResourceCate storeResourceCate = systemResourceCateService.getById(storeResourceCateByIdRequest.getCateId());
		return BaseResponse.success(new SystemResourceCateByIdResponse(systemResourceCateService.wrapperVo(storeResourceCate)));
	}

	@Override
	public BaseResponse<Integer> checkChild(@RequestBody @Valid SystemResourceCateCheckChildRequest
													request) {
		return BaseResponse.success(systemResourceCateService.checkChild(request.getCateId(),request.getStoreId()));
	}


	@Override
	public BaseResponse<Integer> checkResource(@RequestBody @Valid SystemResourceCateCheckResourceRequest
													   request) {
		return BaseResponse.success(systemResourceCateService.checkResource(request.getCateId(),request.getStoreId()));
	}
}

