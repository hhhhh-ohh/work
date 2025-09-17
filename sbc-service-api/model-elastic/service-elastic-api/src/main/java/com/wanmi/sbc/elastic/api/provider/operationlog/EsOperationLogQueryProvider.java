package com.wanmi.sbc.elastic.api.provider.operationlog;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogInitRequest;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogListRequest;
import com.wanmi.sbc.elastic.api.response.operationlog.EsOperationLogPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2020/12/16 09:59
 * @description <p> 操作日志 </p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsOperationLogQueryProvider")
public interface EsOperationLogQueryProvider {

    /**
     * 根据条件查询操作日志
     * @param queryRequest {@link EsOperationLogListRequest}
     * @return {@link EsOperationLogPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/operation-log/queryOpLogByCriteria")
    BaseResponse<EsOperationLogPageResponse> queryOpLogByCriteria(@RequestBody EsOperationLogListRequest queryRequest);

    /**
     * 查询数据量
     * @param queryRequest
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/operation-log/count")
    BaseResponse<Long> count(@RequestBody EsOperationLogListRequest queryRequest);

    /**
     * 数据初始化
     * @param queryRequest
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/operation-log/init")
    BaseResponse init(@RequestBody @Validated EsOperationLogInitRequest queryRequest);

}
