package com.wanmi.sbc.setting.provider.impl.helpcenterarticlerecord;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord.HelpCenterArticleRecordProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordAddRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordAddResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordModifyRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticlerecord.HelpCenterArticleRecordModifyResponse;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordDelByIdRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordDelByIdListRequest;
import com.wanmi.sbc.setting.helpcenterarticlerecord.service.HelpCenterArticleRecordService;
import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章记录保存服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@RestController
@Validated
public class HelpCenterArticleRecordController implements HelpCenterArticleRecordProvider {
	@Autowired
	private HelpCenterArticleRecordService helpCenterArticleRecordService;

	@Override
	public BaseResponse<HelpCenterArticleRecordAddResponse> add(@RequestBody @Valid HelpCenterArticleRecordAddRequest helpCenterArticleRecordAddRequest) {
		HelpCenterArticleRecord helpCenterArticleRecord = KsBeanUtil.convert(helpCenterArticleRecordAddRequest, HelpCenterArticleRecord.class);
		return BaseResponse.success(new HelpCenterArticleRecordAddResponse(
				helpCenterArticleRecordService.wrapperVo(helpCenterArticleRecordService.add(helpCenterArticleRecord))));
	}

	@Override
	public BaseResponse<HelpCenterArticleRecordModifyResponse> modify(@RequestBody @Valid HelpCenterArticleRecordModifyRequest helpCenterArticleRecordModifyRequest) {
		HelpCenterArticleRecord helpCenterArticleRecord = KsBeanUtil.convert(helpCenterArticleRecordModifyRequest, HelpCenterArticleRecord.class);
		return BaseResponse.success(new HelpCenterArticleRecordModifyResponse(
				helpCenterArticleRecordService.wrapperVo(helpCenterArticleRecordService.modify(helpCenterArticleRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleRecordDelByIdRequest helpCenterArticleRecordDelByIdRequest) {
		HelpCenterArticleRecord helpCenterArticleRecord = KsBeanUtil.convert(helpCenterArticleRecordDelByIdRequest, HelpCenterArticleRecord.class);
		helpCenterArticleRecord.setDelFlag(DeleteFlag.YES);
		helpCenterArticleRecordService.deleteById(helpCenterArticleRecord);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleRecordDelByIdListRequest helpCenterArticleRecordDelByIdListRequest) {
		helpCenterArticleRecordService.deleteByIdList(helpCenterArticleRecordDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

