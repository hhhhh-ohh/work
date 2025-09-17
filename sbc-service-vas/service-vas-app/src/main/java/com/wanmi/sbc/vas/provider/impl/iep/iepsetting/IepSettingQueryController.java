package com.wanmi.sbc.vas.provider.impl.iep.iepsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.iep.iepsetting.IepSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingListRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingPageRequest;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingQueryRequest;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingListResponse;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingPageResponse;
import com.wanmi.sbc.vas.api.response.iep.iepsetting.IepSettingTopOneResponse;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;
import com.wanmi.sbc.vas.iep.iepsetting.model.root.IepSetting;
import com.wanmi.sbc.vas.iep.iepsetting.service.IepSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>企业购设置查询服务接口实现</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@RestController
@Validated
public class IepSettingQueryController implements IepSettingQueryProvider {
	@Autowired
	private IepSettingService iepSettingService;

	@Override
	public BaseResponse<IepSettingPageResponse> page(@RequestBody @Valid IepSettingPageRequest iepSettingPageReq) {
		IepSettingQueryRequest queryReq = KsBeanUtil.convert(iepSettingPageReq, IepSettingQueryRequest.class);
		Page<IepSetting> iepSettingPage = iepSettingService.page(queryReq);
		Page<IepSettingVO> newPage = iepSettingPage.map(entity -> iepSettingService.wrapperVo(entity));
		MicroServicePage<IepSettingVO> microPage = new MicroServicePage<>(newPage, iepSettingPageReq.getPageable());
		IepSettingPageResponse finalRes = new IepSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<IepSettingListResponse> list(@RequestBody @Valid IepSettingListRequest iepSettingListReq) {
		IepSettingQueryRequest queryReq = KsBeanUtil.convert(iepSettingListReq, IepSettingQueryRequest.class);
		List<IepSetting> iepSettingList = iepSettingService.list(queryReq);
		List<IepSettingVO> newList = iepSettingList.stream().map(entity -> iepSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new IepSettingListResponse(newList));
	}

	@Override
	public BaseResponse<IepSettingByIdResponse> getById(@RequestBody @Valid IepSettingByIdRequest iepSettingByIdRequest) {
		IepSetting iepSetting =
		iepSettingService.getOne(iepSettingByIdRequest.getId());
		return BaseResponse.success(new IepSettingByIdResponse(iepSettingService.wrapperVo(iepSetting)));
	}

	@Override
	public BaseResponse<IepSettingTopOneResponse> findTopOne(){
		IepSetting iepSetting = iepSettingService.findTopOne();
		return BaseResponse.success(new IepSettingTopOneResponse(iepSettingService.wrapperVo(iepSetting)));
	}

	@Override
	public BaseResponse<IepSettingTopOneResponse> cacheIepSetting(){
		IepSetting iepSetting = iepSettingService.cacheIepSetting();
		return BaseResponse.success(new IepSettingTopOneResponse(iepSettingService.wrapperVo(iepSetting)));
	}

}

