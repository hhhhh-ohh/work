package com.wanmi.sbc.setting.provider.impl.helpcenterarticlecate;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateQueryProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCatePageRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateQueryRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCatePageResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateListRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateListResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateByIdRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateByIdResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateExportRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateExportResponse;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleCateVO;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleCatePageVO;
import com.wanmi.sbc.setting.helpcenterarticlecate.service.HelpCenterArticleCateService;
import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>帮助中心文章信息查询服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@RestController
@Validated
public class HelpCenterArticleCateQueryController implements HelpCenterArticleCateQueryProvider {
	@Autowired
	private HelpCenterArticleCateService helpCenterArticleCateService;

	@Override
	public BaseResponse<HelpCenterArticleCatePageResponse> page(@RequestBody @Valid HelpCenterArticleCatePageRequest helpCenterArticleCatePageReq) {
		HelpCenterArticleCateQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticleCatePageReq, HelpCenterArticleCateQueryRequest.class);
		Page<HelpCenterArticleCate> helpCenterArticleCatePage = helpCenterArticleCateService.page(queryReq);
		Page<HelpCenterArticleCateVO> newPage = helpCenterArticleCatePage.map(entity -> helpCenterArticleCateService.wrapperVo(entity));
		MicroServicePage<HelpCenterArticleCateVO> microPage = new MicroServicePage<>(newPage, helpCenterArticleCatePageReq.getPageable());
		HelpCenterArticleCatePageResponse finalRes = new HelpCenterArticleCatePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<HelpCenterArticleCateListResponse> list(@RequestBody @Valid HelpCenterArticleCateListRequest helpCenterArticleCateListReq) {
		HelpCenterArticleCateQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticleCateListReq, HelpCenterArticleCateQueryRequest.class);
		queryReq.putSort("cateSort","desc");
		List<HelpCenterArticleCate> helpCenterArticleCateList = helpCenterArticleCateService.list(queryReq);
		List<HelpCenterArticleCateVO> newList = helpCenterArticleCateList.stream().map(entity -> helpCenterArticleCateService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new HelpCenterArticleCateListResponse(newList));
	}

	@Override
	public BaseResponse<HelpCenterArticleCateListResponse> getCateList() {
		List<HelpCenterArticleCate> helpCenterArticleCateList = helpCenterArticleCateService.getCateList();
		List<HelpCenterArticleCateVO> newList = helpCenterArticleCateList.stream().map(entity -> helpCenterArticleCateService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new HelpCenterArticleCateListResponse(newList));
	}

	@Override
	public BaseResponse<HelpCenterArticleCateByIdResponse> getById(@RequestBody @Valid HelpCenterArticleCateByIdRequest helpCenterArticleCateByIdRequest) {
		HelpCenterArticleCate helpCenterArticleCate =
		helpCenterArticleCateService.getOne(helpCenterArticleCateByIdRequest.getId());
		return BaseResponse.success(new HelpCenterArticleCateByIdResponse(helpCenterArticleCateService.wrapperVo(helpCenterArticleCate)));
	}

}

