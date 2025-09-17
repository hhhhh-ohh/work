package com.wanmi.sbc.elastic.provider.impl.operationlog;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.operationlog.EsOperationLogQueryProvider;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogInitRequest;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogListRequest;
import com.wanmi.sbc.elastic.api.response.operationlog.EsOperationLogPageResponse;
import com.wanmi.sbc.elastic.operationlog.service.EsOperationLogQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2020/12/16 10:26
 * @description <p> 操作日志 </p>
 */
@RestController
public class EsOperationLogQueryController implements EsOperationLogQueryProvider {

    @Autowired
    private EsOperationLogQueryService esOperationLogQueryService;

    @Override
    public BaseResponse<EsOperationLogPageResponse> queryOpLogByCriteria(@RequestBody EsOperationLogListRequest queryRequest) {

        return esOperationLogQueryService.queryOpLogByCriteria(queryRequest);
    }

    @Override
    public BaseResponse<Long> count(@RequestBody EsOperationLogListRequest queryRequest) {
        Long total = esOperationLogQueryService.count(queryRequest);
        return BaseResponse.success(total);
    }

    @Override
    public BaseResponse init(@RequestBody EsOperationLogInitRequest queryRequest) {
        esOperationLogQueryService.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
