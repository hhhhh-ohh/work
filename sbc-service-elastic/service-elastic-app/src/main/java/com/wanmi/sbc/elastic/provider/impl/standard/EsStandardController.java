package com.wanmi.sbc.elastic.provider.impl.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.standard.service.EsStandardService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES标品库服务实现
 */
@RestController
@Validated
public class EsStandardController implements EsStandardProvider {

    @Autowired
    private EsStandardService esStandardService;

    @Override
    public BaseResponse init(@RequestBody EsStandardInitRequest request) {
        if(CollectionUtils.isNotEmpty(request.getIdList())){
            request.setGoodsIds(request.getIdList());
            request.setPageNum(0);
            request.setPageSize(request.getIdList().size());
        }
        esStandardService.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse reInitEsStandardRelStoreIds(EsStandardInitRequest request) {
        esStandardService.reInitEsStandardRelStoreIds(request.getGoodsIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByIds(@RequestBody @Valid EsStandardDeleteByIdsRequest request) {
        esStandardService.deleteByIds(request.getGoodsIds());
        return BaseResponse.SUCCESSFUL();
    }
}
