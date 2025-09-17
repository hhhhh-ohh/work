package com.wanmi.sbc.elastic.provider.impl.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.base.EsBaseProvider;
import com.wanmi.sbc.elastic.api.request.base.EsDeleteByIdAndIndexNameRequest;
import com.wanmi.sbc.elastic.api.request.base.EsIndexInitRequest;
import com.wanmi.sbc.elastic.base.service.ElasticBaseService;
import com.wanmi.sbc.elastic.esindexhandle.service.EsIndexHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@Validated
public class EsBaseController implements EsBaseProvider {

    @Autowired
    private ElasticBaseService elasticBaseService;

    @Autowired private EsIndexHandleService esIndexHandleService;


    @Override
    public BaseResponse deleteByIdAndIndex(@RequestBody @Valid EsDeleteByIdAndIndexNameRequest request) {
        elasticBaseService.deleteByIdAndIndexName(request.getId(), request.getIndexName());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse indexInit(@Valid EsIndexInitRequest request) {
        if ( StringUtils.isNotEmpty(request.getIndexType())) {
            esIndexHandleService.initIndexByType(request.getIndexType());
        } else  {
            esIndexHandleService.initAllIndex();
        }
        return BaseResponse.SUCCESSFUL();
    }
}
