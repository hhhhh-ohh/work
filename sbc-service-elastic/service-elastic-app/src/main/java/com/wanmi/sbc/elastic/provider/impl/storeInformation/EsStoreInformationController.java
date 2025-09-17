package com.wanmi.sbc.elastic.provider.impl.storeInformation;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.*;
import com.wanmi.sbc.elastic.storeInformation.service.EsStoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author yangzhen
 * @Description //es商家店铺信息
 * @Date 19:05 2020/12/7
 * @Param
 * @return
 **/
@RestController
@Validated
public class EsStoreInformationController implements EsStoreInformationProvider {

    @Autowired
    private EsStoreInformationService esStoreInformationSpuService;

    @Override
    public BaseResponse initStoreInformation(@RequestBody @Valid StoreInformationRequest request) {
        esStoreInformationSpuService.initStoreInformation(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initStoreInformationList(@RequestBody @Valid ESStoreInfoInitRequest request) {
        esStoreInformationSpuService.initStoreInformationList(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<Boolean> modifyStoreBasicInfo(@RequestBody @Valid StoreInfoModifyRequest request) {
        return BaseResponse.success(esStoreInformationSpuService.modifyStoreBasicInfo(request));
    }

    @Override
    public BaseResponse<Boolean> modifyStoreState(@RequestBody @Valid StoreInfoStateModifyRequest request) {
        return BaseResponse.success(esStoreInformationSpuService.modifyStoreState(request));
    }

    @Override
    public BaseResponse<Boolean> modifyStoreReject(@RequestBody @Valid StoreInfoRejectModifyRequest request) {
        return BaseResponse.success(esStoreInformationSpuService.modifyStoreReject(request));
    }

    @Override
    public BaseResponse<Boolean> modifyStoreContractInfo(@RequestBody @Valid StoreInfoContractRequest request) {
        return BaseResponse.success(esStoreInformationSpuService.modifyStoreContractInfo(request));
    }

    @Override
    public BaseResponse<Boolean> modifyContractAuditState(@RequestBody @Valid StoreInfoStateModifyRequest request) {
        return BaseResponse.success(esStoreInformationSpuService.modifyContractAuditState(request));
    }
}
