package com.wanmi.sbc.setting.provider.impl.helpcenterarticle;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleProvider;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleAddResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleModifyResponse;
import com.wanmi.sbc.setting.helpcenterarticle.service.HelpCenterArticleService;
import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息保存服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@RestController
@Validated
public class HelpCenterArticleController implements HelpCenterArticleProvider {
	@Autowired
	private HelpCenterArticleService helpCenterArticleService;

	@Override
	public BaseResponse<HelpCenterArticleAddResponse> add(@RequestBody @Valid HelpCenterArticleAddRequest helpCenterArticleAddRequest) {
		HelpCenterArticle helpCenterArticle = KsBeanUtil.convert(helpCenterArticleAddRequest, HelpCenterArticle.class);
		return BaseResponse.success(new HelpCenterArticleAddResponse(
				helpCenterArticleService.wrapperVo(helpCenterArticleService.add(helpCenterArticle))));
	}

	@Override
	public BaseResponse<HelpCenterArticleModifyResponse> modify(@RequestBody @Valid HelpCenterArticleModifyRequest helpCenterArticleModifyRequest) {
		HelpCenterArticle helpCenterArticle = helpCenterArticleService.getOne(helpCenterArticleModifyRequest.getId());
		HelpCenterArticle newHelpCenterArticle = KsBeanUtil.convert(helpCenterArticleModifyRequest, HelpCenterArticle.class);
		newHelpCenterArticle.setViewNum(helpCenterArticle.getViewNum());
		newHelpCenterArticle.setSolveNum(helpCenterArticle.getSolveNum());
		newHelpCenterArticle.setUnresolvedNum(helpCenterArticle.getUnresolvedNum());
		newHelpCenterArticle.setDelFlag(helpCenterArticle.getDelFlag());
		newHelpCenterArticle.setCreateTime(helpCenterArticle.getCreateTime());
		newHelpCenterArticle.setCreatePerson(helpCenterArticle.getCreatePerson());
		return BaseResponse.success(new HelpCenterArticleModifyResponse(
				helpCenterArticleService.wrapperVo(helpCenterArticleService.modify(newHelpCenterArticle))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleDelByIdRequest helpCenterArticleDelByIdRequest) {
		HelpCenterArticle helpCenterArticle = helpCenterArticleService.getOne(helpCenterArticleDelByIdRequest.getId());
		if(Objects.isNull(helpCenterArticle)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070107);
		}
		if(helpCenterArticle.getArticleType() == DefaultFlag.NO){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070109);
		}
		helpCenterArticle.setDelFlag(DeleteFlag.YES);
		helpCenterArticle.setUpdatePerson(helpCenterArticleDelByIdRequest.getUpdatePerson());
		helpCenterArticle.setUpdateTime(LocalDateTime.now());
		helpCenterArticleService.deleteById(helpCenterArticle);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleDelByIdListRequest helpCenterArticleDelByIdListRequest) {
		helpCenterArticleService.deleteByIdList(helpCenterArticleDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse addViewNum(@Valid HelpCenterArticleByIdRequest request) {
		helpCenterArticleService.addViewNum(request.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse clickSolve(@Valid HelpCenterArticleChangeSolveTypeRequest request) {
		HelpCenterArticle helpCenterArticle = helpCenterArticleService.getOne(request.getId());
		if(Objects.isNull(helpCenterArticle)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070107);
		}
		helpCenterArticleService.clickSolve(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse clickUnresolved(@Valid HelpCenterArticleChangeSolveTypeRequest request) {
		HelpCenterArticle helpCenterArticle = helpCenterArticleService.getOne(request.getId());
		if(Objects.isNull(helpCenterArticle)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070107);
		}
		helpCenterArticleService.clickUnresolved(request);
		return BaseResponse.SUCCESSFUL();
	}

}

