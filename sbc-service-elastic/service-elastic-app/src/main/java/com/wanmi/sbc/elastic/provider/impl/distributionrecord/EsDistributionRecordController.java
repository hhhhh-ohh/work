package com.wanmi.sbc.elastic.provider.impl.distributionrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordProvider;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordByTradeIdRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordInitRequest;
import com.wanmi.sbc.elastic.distributionrecord.service.EsDistributionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/5 10:11
 * @description <p> 分销记录 controller </p>
 */
@RestController
public class EsDistributionRecordController implements EsDistributionRecordProvider {

    @Autowired
    private EsDistributionRecordService esDistributionRecordService;

    /**
     *  添加分销记录
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse add(@RequestBody EsDistributionRecordAddRequest request) {
        esDistributionRecordService.add(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除分销记录
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse deleteByTradeId(@RequestBody @Valid EsDistributionRecordByTradeIdRequest request) {
        esDistributionRecordService.deleteByTradeId(request.getTradeId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始化分销记录
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse init(@RequestBody EsDistributionRecordInitRequest request) {
        esDistributionRecordService.init(request);
        return BaseResponse.SUCCESSFUL();
    }
}
