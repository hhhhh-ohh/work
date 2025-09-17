package com.wanmi.sbc.setting.provider.impl.openapisetting;

import com.wanmi.sbc.setting.api.request.openapisetting.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.openapisetting.OpenApiSettingQueryProvider;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingPageResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingListResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingByIdResponse;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingVO;
import com.wanmi.sbc.setting.openapisetting.service.OpenApiSettingService;
import com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 开放平台api设置查询服务接口实现
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@RestController
@Validated
public class OpenApiSettingQueryController implements OpenApiSettingQueryProvider {
    @Autowired private OpenApiSettingService openApiSettingService;

    @Override
    public BaseResponse<OpenApiSettingPageResponse> page(
            @RequestBody @Valid OpenApiSettingPageRequest openApiSettingPageReq) {
        OpenApiSettingQueryRequest queryReq =
                KsBeanUtil.convert(openApiSettingPageReq, OpenApiSettingQueryRequest.class);
        Page<OpenApiSetting> openApiSettingPage = openApiSettingService.page(queryReq);
        Page<OpenApiSettingVO> newPage =
                openApiSettingPage.map(entity -> openApiSettingService.wrapperVo(entity));
        MicroServicePage<OpenApiSettingVO> microPage =
                new MicroServicePage<>(newPage, openApiSettingPageReq.getPageable());
        OpenApiSettingPageResponse finalRes = new OpenApiSettingPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<OpenApiSettingListResponse> list(
            @RequestBody @Valid OpenApiSettingListRequest openApiSettingListReq) {
        OpenApiSettingQueryRequest queryReq =
                KsBeanUtil.convert(openApiSettingListReq, OpenApiSettingQueryRequest.class);
        List<OpenApiSetting> openApiSettingList = openApiSettingService.list(queryReq);
        List<OpenApiSettingVO> newList =
                openApiSettingList.stream()
                        .map(entity -> openApiSettingService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new OpenApiSettingListResponse(newList));
    }

    @Override
    public BaseResponse<OpenApiSettingByIdResponse> getById(
            @RequestBody @Valid OpenApiSettingByIdRequest openApiSettingByIdRequest) {
        OpenApiSetting openApiSetting =
                openApiSettingService.getOne(
                        openApiSettingByIdRequest.getId());
        return BaseResponse.success(
                new OpenApiSettingByIdResponse(openApiSettingService.wrapperVo(openApiSetting)));
    }

    @Override
    public BaseResponse<OpenApiSettingByIdResponse> getByStoreId(@RequestBody @Valid OpenApiSettingByStoreIdRequest openApiSettingByStoreIdRequest) {
        OpenApiSetting openApiSetting =
                openApiSettingService.getByStoreId(
                        openApiSettingByStoreIdRequest.getStoreId());
        return BaseResponse.success(
                new OpenApiSettingByIdResponse(openApiSettingService.wrapperVo(openApiSetting)));
    }
}
