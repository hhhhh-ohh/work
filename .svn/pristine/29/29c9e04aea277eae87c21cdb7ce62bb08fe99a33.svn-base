package com.wanmi.sbc.elastic.provider.impl.searchterms;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.searchterms.EsSearchAssociationalWordProvider;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordDelRequest;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordInitRequest;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordPageRequest;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordRequest;
import com.wanmi.sbc.elastic.searchterms.service.EsSearchAssociationalWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/17 15:35
 * @description <p> </p>
 */
@RestController
public class EsSearchAssociationalWordController implements EsSearchAssociationalWordProvider {

    @Autowired
    private EsSearchAssociationalWordService esSearchAssociationalWordService;

    @Override
    public BaseResponse add(@RequestBody @Valid EsSearchAssociationalWordRequest request) {
        esSearchAssociationalWordService.add(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteById(@RequestBody @Valid EsSearchAssociationalWordDelRequest request) {
        esSearchAssociationalWordService.deleteById(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse init(@RequestBody @Valid EsSearchAssociationalWordInitRequest request) {

        esSearchAssociationalWordService.init(request);
        return BaseResponse.SUCCESSFUL();
    }
}
