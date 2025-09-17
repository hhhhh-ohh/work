package com.wanmi.sbc.setting.provider.impl.expresscompanythirdrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.*;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyListBySellTypeResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyThirdRelDetailListResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyThirdRelListResponse;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.setting.expresscompanythirdrel.root.ExpressCompanyThirdRel;
import com.wanmi.sbc.setting.expresscompanythirdrel.service.ExpressCompanyThirdRelService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 第三方平台物流公司查询服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@RestController
@Validated
public class ExpressCompanyThirdRelQueryController implements ExpressCompanyThirdRelQueryProvider {

    @Autowired private ExpressCompanyThirdRelService expressCompanyThirdRelService;

    @Override
    public BaseResponse<ExpressCompanyThirdRelListResponse> list(@RequestBody ExpressCompanyThirdRelQueryRequest request) {
        List<ExpressCompanyThirdRel> expressCompanyThirdRelList = expressCompanyThirdRelService.list(request);
        if (CollectionUtils.isNotEmpty(expressCompanyThirdRelList)) {
            List<ExpressCompanyThirdRelVO> newList = expressCompanyThirdRelList.stream()
                    .map(entity -> expressCompanyThirdRelService.wrapperVo(entity)).collect(Collectors.toList());
            return BaseResponse.success(new ExpressCompanyThirdRelListResponse(newList));
        }
        return BaseResponse.success(new ExpressCompanyThirdRelListResponse(Collections.emptyList()));
    }

    @Override
    public BaseResponse<ExpressCompanyThirdRelDetailListResponse> getWithDetail(ExpressCompanyThirdRelDetailQueryRequest request) {
        return BaseResponse.success(
                ExpressCompanyThirdRelDetailListResponse.builder()
                        .thirdRelDetailList(expressCompanyThirdRelService.listWithDetail(request))
                        .build());
    }

    @Override
    public BaseResponse<ExpressCompanyThirdRelDetailListResponse> listWithDetail(ThirdExpressCompanyListRequest request) {
        return BaseResponse.success(ExpressCompanyThirdRelDetailListResponse.builder()
                .thirdRelDetailList(expressCompanyThirdRelService.queryThirdExpress(request))
                .build());
    }

    @Override
    public BaseResponse<ExpressCompanyThirdRelDetailListResponse> getWithDetailByCode(ThirdExpressCompanyListByCodeRequest request) {
        return BaseResponse.success(
                ExpressCompanyThirdRelDetailListResponse.builder()
                        .thirdRelDetailList(expressCompanyThirdRelService.listWithDetailByCode(request))
                        .build());
    }

    @Override
    public BaseResponse<ExpressCompanyListBySellTypeResponse> getExpressCompanyBySellType(@Valid ExpressCompanyListBySellTypeRequest request) {
        List<ExpressCompanyThirdRelDetailDTO> expressCompanyThirdRelList = expressCompanyThirdRelService.queryExpress(request);
        if (CollectionUtils.isNotEmpty(expressCompanyThirdRelList)) {
            List<ExpressCompanyVO> expressCompanyVOList = expressCompanyThirdRelList.stream()
                    .map(entity -> expressCompanyThirdRelService.wrapperSystemVo(entity)).collect(Collectors.toList());
            return BaseResponse.success(new ExpressCompanyListBySellTypeResponse(expressCompanyVOList));
        }
        return BaseResponse.success(new ExpressCompanyListBySellTypeResponse(Collections.emptyList()));
    }
}
