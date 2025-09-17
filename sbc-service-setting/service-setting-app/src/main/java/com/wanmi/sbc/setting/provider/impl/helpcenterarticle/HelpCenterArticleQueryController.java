package com.wanmi.sbc.setting.provider.impl.helpcenterarticle;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateQueryRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;
import com.wanmi.sbc.setting.helpcenterarticlecate.service.HelpCenterArticleCateService;
import com.wanmi.sbc.setting.helpcenterarticlerecord.service.HelpCenterArticleRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleQueryProvider;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticlePageResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleListResponse;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.HelpCenterArticleByIdResponse;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleVO;
import com.wanmi.sbc.setting.helpcenterarticle.service.HelpCenterArticleService;
import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;
import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>帮助中心文章信息查询服务接口实现</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@RestController
@Validated
public class HelpCenterArticleQueryController implements HelpCenterArticleQueryProvider {
	@Autowired
	private HelpCenterArticleService helpCenterArticleService;

	@Autowired
	private HelpCenterArticleCateService helpCenterArticleCateService;

	@Autowired
	private EmployeeQueryProvider employeeQueryProvider;

	@Override
	public BaseResponse<HelpCenterArticlePageResponse> page(@RequestBody @Valid HelpCenterArticlePageRequest helpCenterArticlePageReq) {
		HelpCenterArticleQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticlePageReq, HelpCenterArticleQueryRequest.class);
		Page<HelpCenterArticle> helpCenterArticlePage = helpCenterArticleService.page(queryReq);
		Page<HelpCenterArticleVO> newPage = helpCenterArticlePage.map(entity -> helpCenterArticleService.wrapperVo(entity));
		List<Long> cateIdList = new ArrayList<>();
		List<String> employeeIds =  new ArrayList<>();
		newPage.getContent().forEach(helpCenterArticleVO -> {
			cateIdList.add(helpCenterArticleVO.getArticleCateId());
			employeeIds.add(helpCenterArticleVO.getCreatePerson());
		});
		Map<Long, HelpCenterArticleCate> helpCenterArticleCateMap =  new HashMap<>();
		if(CollectionUtils.isNotEmpty(cateIdList)){
			helpCenterArticleCateService.list(HelpCenterArticleCateQueryRequest.builder().idList(cateIdList).build()).forEach(helpCenterArticleCate -> {
				helpCenterArticleCateMap.put(helpCenterArticleCate.getId(),helpCenterArticleCate);
			});
		}
		Map<String,EmployeeListVO>  employeeListVOMap = new HashMap<>();
		List<String> disEmployeeIds = employeeIds.stream().distinct().collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(disEmployeeIds)){
			EmployeeListRequest employeeListRequest = new EmployeeListRequest();
			employeeListRequest.setEmployeeIds(disEmployeeIds);
			List<EmployeeListVO> employeeList = employeeQueryProvider.list(employeeListRequest).getContext().getEmployeeList();
			if(CollectionUtils.isNotEmpty(employeeList)){
				employeeList.forEach(employeeVO -> {
					employeeListVOMap.put(employeeVO.getEmployeeId(),employeeVO);
				});
			}
		}
		newPage.getContent().forEach(helpCenterArticleVO -> {
			helpCenterArticleVO.setArticleCateName(helpCenterArticleCateMap.get(helpCenterArticleVO.getArticleCateId()).getCateName());
			EmployeeListVO employeeListVO = employeeListVOMap.get(helpCenterArticleVO.getCreatePerson());
			if(Objects.nonNull(employeeListVO)){
				helpCenterArticleVO.setCreatePersonName(employeeListVO.getEmployeeName());
				helpCenterArticleVO.setCreatePersonAccount(employeeListVO.getAccountName());
			}
		});
		MicroServicePage<HelpCenterArticleVO> microPage = new MicroServicePage<>(newPage, helpCenterArticlePageReq.getPageable());
		HelpCenterArticlePageResponse finalRes = new HelpCenterArticlePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<HelpCenterArticleListResponse> list(@RequestBody @Valid HelpCenterArticleListRequest helpCenterArticleListReq) {
		HelpCenterArticleQueryRequest queryReq = KsBeanUtil.convert(helpCenterArticleListReq, HelpCenterArticleQueryRequest.class);
		List<HelpCenterArticle> helpCenterArticleList = helpCenterArticleService.list(queryReq);
		List<HelpCenterArticleVO> newList = helpCenterArticleList.stream().map(entity -> helpCenterArticleService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new HelpCenterArticleListResponse(newList));
	}

	@Override
	public BaseResponse<HelpCenterArticleByIdResponse> getById(@RequestBody @Valid HelpCenterArticleByIdRequest helpCenterArticleByIdRequest) {
		HelpCenterArticle helpCenterArticle =
		helpCenterArticleService.getOne(helpCenterArticleByIdRequest.getId());
		return BaseResponse.success(new HelpCenterArticleByIdResponse(helpCenterArticleService.wrapperVo(helpCenterArticle)));
	}

	@Override
	public BaseResponse changeTypeById(@Valid HelpCenterArticleChangeTypeByIdRequest helpCenterArticleByIdRequest) {
		HelpCenterArticle helpCenterArticle =
				helpCenterArticleService.getOne(helpCenterArticleByIdRequest.getId());
		if(helpCenterArticle.getDelFlag() == DeleteFlag.YES){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070107);
		}
		helpCenterArticle.setArticleType(helpCenterArticleByIdRequest.getArticleType());
		helpCenterArticleService.modify(helpCenterArticle);
		return BaseResponse.SUCCESSFUL();
	}

}

