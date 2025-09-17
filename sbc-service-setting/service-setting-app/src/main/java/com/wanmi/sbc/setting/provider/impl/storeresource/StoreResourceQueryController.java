package com.wanmi.sbc.setting.provider.impl.storeresource;

import com.wanmi.osd.OsdClient;
import com.wanmi.osd.bean.OsdClientParam;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storeresource.StoreResourceQueryProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourcePageRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceQueryRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceByIdResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceListResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourcePageResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.setting.systemconfig.model.root.SystemConfig;
import com.wanmi.sbc.setting.systemconfig.service.SystemConfigService;
import com.wanmi.sbc.setting.systemresource.model.root.SystemResource;
import com.wanmi.sbc.setting.systemresource.service.SystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>店铺资源库查询服务接口实现</p>
 *
 * @author lq
 * @date 2019-11-05 16:12:49
 */
@RestController
@Validated
public class StoreResourceQueryController implements StoreResourceQueryProvider {
    @Autowired
    private SystemResourceService storeResourceService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public BaseResponse<SystemResourcePageResponse> page(@RequestBody @Valid SystemResourcePageRequest storeResourcePageReq) {
        SystemResourceQueryRequest queryReq = new SystemResourceQueryRequest();
        KsBeanUtil.copyPropertiesThird(storeResourcePageReq, queryReq);
        queryReq.setDelFlag(DeleteFlag.NO);
        queryReq.putSort("createTime", SortType.DESC.toValue());
        queryReq.putSort("resourceId", SortType.ASC.toValue());
        // 查询可用云服务
        SystemConfig availableYun = systemConfigService.getAvailableYun();
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(availableYun.getConfigType())
                .context(availableYun.getContext())
                .build();
        Page<SystemResource> storeResourcePage = storeResourceService.page(queryReq);
        Page<SystemResourceVO> newPage = storeResourcePage.map(entity -> {
                    //获取url
                    String resourceUrl = OsdClient.instance().getResourceUrl(osdClientParam, entity.getArtworkUrl());
                    entity.setArtworkUrl(resourceUrl);
                    return storeResourceService.wrapperVo(entity);
                }
        );
        MicroServicePage<SystemResourceVO> microPage = new MicroServicePage<>(newPage, storeResourcePageReq.getPageable());
        SystemResourcePageResponse finalRes = new SystemResourcePageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<SystemResourceListResponse> list(@RequestBody @Valid SystemResourceListRequest storeResourceListReq) {
        SystemResourceQueryRequest queryReq = new SystemResourceQueryRequest();
        KsBeanUtil.copyPropertiesThird(storeResourceListReq, queryReq);
        List<SystemResource> storeResourceList = storeResourceService.list(queryReq);
        List<SystemResourceVO> newList = storeResourceList.stream().map(entity -> storeResourceService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(new SystemResourceListResponse(newList));
    }

    @Override
    public BaseResponse<SystemResourceByIdResponse> getById(@RequestBody @Valid SystemResourceByIdRequest storeResourceByIdRequest) {
        SystemResource storeResource = storeResourceService.getById(storeResourceByIdRequest.getResourceId());
        return BaseResponse.success(new SystemResourceByIdResponse(storeResourceService.wrapperVo(storeResource)));
    }

}

