package com.wanmi.sbc.empower.provider.impl.miniprogramset;

import com.wanmi.sbc.empower.api.request.miniprogramset.*;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetPageResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetListResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByIdResponse;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>小程序配置查询服务接口实现</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@RestController
@Validated
public class MiniProgramSetQueryController implements MiniProgramSetQueryProvider {
	@Autowired
	private MiniProgramSetService miniProgramSetService;

	@Override
	public BaseResponse<MiniProgramSetPageResponse> page(@RequestBody @Valid MiniProgramSetPageRequest miniProgramSetPageReq) {
		MiniProgramSetQueryRequest queryReq = KsBeanUtil.convert(miniProgramSetPageReq, MiniProgramSetQueryRequest.class);
		Page<MiniProgramSet> miniProgramSetPage = miniProgramSetService.page(queryReq);
		Page<MiniProgramSetVO> newPage = miniProgramSetPage.map(entity -> miniProgramSetService.wrapperVo(entity));
		MicroServicePage<MiniProgramSetVO> microPage = new MicroServicePage<>(newPage, miniProgramSetPageReq.getPageable());
		MiniProgramSetPageResponse finalRes = new MiniProgramSetPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<MiniProgramSetListResponse> list(@RequestBody @Valid MiniProgramSetListRequest miniProgramSetListReq) {
		MiniProgramSetQueryRequest queryReq = KsBeanUtil.convert(miniProgramSetListReq, MiniProgramSetQueryRequest.class);
		List<MiniProgramSet> miniProgramSetList = miniProgramSetService.list(queryReq);
		List<MiniProgramSetVO> newList = miniProgramSetList.stream().map(entity -> miniProgramSetService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new MiniProgramSetListResponse(newList));
	}

	@Override
	public BaseResponse<MiniProgramSetByIdResponse> getById(@RequestBody @Valid MiniProgramSetByIdRequest miniProgramSetByIdRequest) {
		MiniProgramSet miniProgramSet =
		miniProgramSetService.getOneById(miniProgramSetByIdRequest.getId());
		return BaseResponse.success(new MiniProgramSetByIdResponse(miniProgramSetService.wrapperVo(miniProgramSet)));
	}

	@Override
	public BaseResponse<MiniProgramSetByTypeResponse> getByType(@RequestBody @Valid MiniProgramSetByTypeRequest miniProgramSetByTypeRequest) {
		MiniProgramSet miniProgramSet =
				miniProgramSetService.getOneByType(miniProgramSetByTypeRequest.getType());
		return BaseResponse.success(new MiniProgramSetByTypeResponse(miniProgramSetService.wrapperVo(miniProgramSet)));
	}

}

