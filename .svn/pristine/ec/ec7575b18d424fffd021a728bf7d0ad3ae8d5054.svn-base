package com.wanmi.sbc.setting.provider.impl.helpcenterarticlerecord;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord.HelpCenterArticleRecordQueryProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordPageRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordQueryRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordPageResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordListRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordListResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordByIdRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordByIdResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordExportRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordExportResponse;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordVO;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordPageVO;
import com.wanmi.sbc.setting.helpcenterarticlerecord.service.HelpCenterArticleRecordService;
import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>帮助中心文章记录查询服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@RestController
@Validated
public class HelpCenterArticleRecordQueryController implements HelpCenterArticleRecordQueryProvider {
	@Autowired
	private HelpCenterArticleRecordService helpCenterArticleRecordService;

	@Override
	public BaseResponse<HelpCenterArticleRecordPageResponse> page(@RequestBody @Valid HelpCenterArticleRecordPageRequest helpCenterArticleRecordPageReq) {
		HelpCenterArticleRecordQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticleRecordPageReq, HelpCenterArticleRecordQueryRequest.class);
		Page<HelpCenterArticleRecord> helpCenterArticleRecordPage = helpCenterArticleRecordService.page(queryReq);
		Page<HelpCenterArticleRecordVO> newPage = helpCenterArticleRecordPage.map(entity -> helpCenterArticleRecordService.wrapperVo(entity));
		MicroServicePage<HelpCenterArticleRecordVO> microPage = new MicroServicePage<>(newPage, helpCenterArticleRecordPageReq.getPageable());
		HelpCenterArticleRecordPageResponse finalRes = new HelpCenterArticleRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<HelpCenterArticleRecordListResponse> list(@RequestBody @Valid HelpCenterArticleRecordListRequest helpCenterArticleRecordListReq) {
		HelpCenterArticleRecordQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticleRecordListReq, HelpCenterArticleRecordQueryRequest.class);
		List<HelpCenterArticleRecord> helpCenterArticleRecordList = helpCenterArticleRecordService.list(queryReq);
		List<HelpCenterArticleRecordVO> newList = helpCenterArticleRecordList.stream().map(entity -> helpCenterArticleRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new HelpCenterArticleRecordListResponse(newList));
	}

	@Override
	public BaseResponse<HelpCenterArticleRecordByIdResponse> getById(@RequestBody @Valid HelpCenterArticleRecordByIdRequest helpCenterArticleRecordByIdRequest) {
		HelpCenterArticleRecord helpCenterArticleRecord =
		helpCenterArticleRecordService.getOne(helpCenterArticleRecordByIdRequest.getId());
		return BaseResponse.success(new HelpCenterArticleRecordByIdResponse(helpCenterArticleRecordService.wrapperVo(helpCenterArticleRecord)));
	}

}

