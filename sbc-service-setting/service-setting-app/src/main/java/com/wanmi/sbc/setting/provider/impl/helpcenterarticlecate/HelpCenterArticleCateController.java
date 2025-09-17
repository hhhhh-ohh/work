package com.wanmi.sbc.setting.provider.impl.helpcenterarticlecate;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.HelpCenterArticleQueryRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.*;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;
import com.wanmi.sbc.setting.helpcenterarticle.service.HelpCenterArticleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateProvider;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateAddResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.HelpCenterArticleCateModifyResponse;
import com.wanmi.sbc.setting.helpcenterarticlecate.service.HelpCenterArticleCateService;
import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.validation.Valid;

/**
 * <p>帮助中心文章信息保存服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@RestController
@Validated
public class HelpCenterArticleCateController implements HelpCenterArticleCateProvider {
	@Autowired
	private HelpCenterArticleCateService helpCenterArticleCateService;

	@Autowired
	private HelpCenterArticleService helpCenterArticleService;

	@Override
	public BaseResponse<HelpCenterArticleCateAddResponse> add(@RequestBody @Valid HelpCenterArticleCateAddRequest helpCenterArticleCateAddRequest) {
		HelpCenterArticleCate helpCenterArticleCate = KsBeanUtil.convert(helpCenterArticleCateAddRequest, HelpCenterArticleCate.class);
		List<HelpCenterArticleCate> cateList = helpCenterArticleCateService.list(HelpCenterArticleCateQueryRequest.builder().delFlag(DeleteFlag.NO).build());

		if(CollectionUtils.isNotEmpty(cateList) && cateList.size() >= 30){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070112);
		} else if(CollectionUtils.isNotEmpty(cateList)) {
			cateList.forEach(cate->{
				if(cate.getCateName().equals(helpCenterArticleCateAddRequest.getCateName())){
					throw new SbcRuntimeException(SettingErrorCodeEnum.K070110);
				}
			});
		}
		helpCenterArticleCate.setCateSort(0);
		helpCenterArticleCate.setDefaultCate(DefaultFlag.NO);
		return BaseResponse.success(new HelpCenterArticleCateAddResponse(
				helpCenterArticleCateService.wrapperVo(helpCenterArticleCateService.add(helpCenterArticleCate))));
	}

	@Override
	public BaseResponse sort(@Valid HelpCenterArticleCateSortModifyRequest request) {
		helpCenterArticleCateService.updateCateSort(request.getHelpCenterArticleCateSortRequestList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<HelpCenterArticleCateModifyResponse> modify(@RequestBody @Valid HelpCenterArticleCateModifyRequest helpCenterArticleCateModifyRequest) {
		List<HelpCenterArticleCate> cateList = helpCenterArticleCateService.list(HelpCenterArticleCateQueryRequest.builder().delFlag(DeleteFlag.NO).build());
		if(CollectionUtils.isNotEmpty(cateList) && cateList.size() >= 30){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070112);
		} else if(CollectionUtils.isNotEmpty(cateList)) {
			cateList.forEach(cate->{
				if(cate.getCateName().equals(helpCenterArticleCateModifyRequest.getCateName()) && !cate.getId().equals(helpCenterArticleCateModifyRequest.getId())){
					throw new SbcRuntimeException(SettingErrorCodeEnum.K070110);
				}
			});
		}
		HelpCenterArticleCate helpCenterArticleCate = helpCenterArticleCateService.getOne(helpCenterArticleCateModifyRequest.getId());
		if(Objects.isNull(helpCenterArticleCate)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070108);
		}
		helpCenterArticleCate.setCateName(helpCenterArticleCateModifyRequest.getCateName());
		helpCenterArticleCate.setUpdateTime(LocalDateTime.now());
		return BaseResponse.success(new HelpCenterArticleCateModifyResponse(
				helpCenterArticleCateService.wrapperVo(helpCenterArticleCateService.modify(helpCenterArticleCate))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid HelpCenterArticleCateDelByIdRequest helpCenterArticleCateDelByIdRequest) {
		HelpCenterArticleCate helpCenterArticleCate = helpCenterArticleCateService.getOne(helpCenterArticleCateDelByIdRequest.getId());
		if(Objects.isNull(helpCenterArticleCate)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070108);
		}
		List<HelpCenterArticle>  articleList = helpCenterArticleService.list(HelpCenterArticleQueryRequest.builder().articleCateId(helpCenterArticleCateDelByIdRequest.getId()).delFlag(DeleteFlag.NO).build());
		if(CollectionUtils.isNotEmpty(articleList)){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070111);
		}
		helpCenterArticleCateService.deleteById(helpCenterArticleCateDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleCateDelByIdListRequest helpCenterArticleCateDelByIdListRequest) {
		helpCenterArticleCateService.deleteByIdList(helpCenterArticleCateDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

