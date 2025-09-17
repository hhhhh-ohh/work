package com.wanmi.sbc.elastic.api.provider.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.base.EsDeleteByIdAndIndexNameRequest;
import com.wanmi.sbc.elastic.api.request.base.EsIndexInitRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对Es公共性操作接口</p>
 * Created by daiyitian on 2021-1-29-下午6:23.
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsBaseProvider")
public interface EsBaseProvider {

    /**
     * 根据id和索引名称删除文档
     *
     * @param request 删除索引的入参 {@link EsDeleteByIdAndIndexNameRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/base/delete-by-id-and-index-name")
    BaseResponse deleteByIdAndIndex(@RequestBody @Valid EsDeleteByIdAndIndexNameRequest request);

    /**
     * @description  es 索引初始化操作  如果请求参数没有指定索引类型则初始化所有索引
     * @author  wur
     * @date: 2022/6/20 13:54
     * @param request
     * @return
     **/
    @PostMapping("/elastic/${application.elastic.version}/base/index-init")
    BaseResponse indexInit(@RequestBody @Valid EsIndexInitRequest request);
}
