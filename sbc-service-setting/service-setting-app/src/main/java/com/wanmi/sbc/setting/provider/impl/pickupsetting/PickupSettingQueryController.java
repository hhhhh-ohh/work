package com.wanmi.sbc.setting.provider.impl.pickupsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingByIdRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingIdsRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingListRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingPageRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingQueryRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingIdsResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingListResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingPageResponse;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.setting.pickupemployeerela.service.PickupEmployeeRelaService;
import com.wanmi.sbc.setting.pickupsetting.model.root.PickupSetting;
import com.wanmi.sbc.setting.pickupsetting.service.PickupSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>pickup_setting查询服务接口实现</p>
 *
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@RestController
@Validated
public class PickupSettingQueryController implements PickupSettingQueryProvider {
    @Autowired
    private PickupSettingService pickupSettingService;

    @Autowired
    private PickupEmployeeRelaService pickupEmployeeRelaService;

    @Override
    public BaseResponse<PickupSettingPageResponse> page(@RequestBody @Valid PickupSettingPageRequest pickupSettingPageReq) {
        PickupSettingQueryRequest queryReq = KsBeanUtil.convert(pickupSettingPageReq, PickupSettingQueryRequest.class);
        queryReq.setSortMap(pickupSettingPageReq.getSortMap());
        Page<PickupSetting> pickupSettingPage = pickupSettingService.page(queryReq);

        Page<PickupSettingVO> newPage = pickupSettingPage.map(entity -> pickupSettingService.wrapperVo(entity));
        MicroServicePage<PickupSettingVO> microPage = new MicroServicePage<>(newPage, newPage.getPageable());
        PickupSettingPageResponse finalRes = new PickupSettingPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<PickupSettingListResponse> list(@RequestBody @Valid PickupSettingListRequest pickupSettingListReq) {
        PickupSettingQueryRequest queryReq = KsBeanUtil.convert(pickupSettingListReq, PickupSettingQueryRequest.class);

        if (Objects.isNull(queryReq)){
            return BaseResponse.success(new PickupSettingListResponse());
        }

        List<PickupSetting> pickupSettingList = pickupSettingService.list(queryReq);
        List<PickupSettingVO> newList = pickupSettingList.stream().map(entity -> pickupSettingService.wrapperVo(entity)).collect(Collectors.toList());

        return BaseResponse.success(new PickupSettingListResponse(newList));
    }

    @Override
    public BaseResponse<Long> total(@Valid PickupSettingPageRequest pickupSettingPageReq) {
        PickupSettingQueryRequest queryReq = KsBeanUtil.convert(pickupSettingPageReq, PickupSettingQueryRequest.class);
        return BaseResponse.success(pickupSettingService.total(queryReq));
    }

    @Override
    public BaseResponse<PickupSettingByIdResponse> getById(@Valid PickupSettingByIdRequest pickupSettingByIdRequest) {
        PickupSetting pickupSetting =
                pickupSettingService.getOne(pickupSettingByIdRequest.getId());


        return BaseResponse.success(new PickupSettingByIdResponse(pickupSettingService.wrapperVo(pickupSetting)
                , pickupEmployeeRelaService.getEmployeeInfo(pickupSettingByIdRequest.getId())));
    }

    @Override
    public BaseResponse<PickupSettingConfigResponse> pickupSettingConfigShow() {
        return BaseResponse.success(pickupSettingService.pickupSettingConfigShow());
    }

    @Override
    public BaseResponse<PickupSettingIdsResponse> getPickupIdsByEmployeeId(@RequestBody @Valid PickupSettingIdsRequest request) {

         return BaseResponse.success(pickupSettingService.getPickupIdsByEmployeeId(request));
    }

    @Override
    public BaseResponse<PickupSettingIdsResponse> getNoEmployeePickupIdsByStoreId(@Valid PickupSettingIdsRequest request) {
        return BaseResponse.success(pickupSettingService.getNoEmployeePickupIdsByStoreId(request));
    }

    @Override
    public BaseResponse<PickupSettingConfigResponse> getWhetherOpenMap() {
        return BaseResponse.success(pickupSettingService.getWhetherOpenMap());
    }

}

