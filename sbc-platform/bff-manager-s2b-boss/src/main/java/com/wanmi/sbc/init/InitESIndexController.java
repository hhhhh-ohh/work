package com.wanmi.sbc.init;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.base.EsBaseProvider;
import com.wanmi.sbc.elastic.api.request.base.EsIndexInitRequest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * @description
 * @author  wur
 * @date: 2022/6/20 19:16
 * @return
 **/
@RestController
@Validated
@RequestMapping("/initEsIndex")
@Slf4j
@Tag(name =  "索引初始化操作", description =  "InitESIndexController")
public class InitESIndexController {

    @Autowired
    private EsBaseProvider esBaseProvider;

    @Operation(summary = "索引初始化操作")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResponse initOrderEmployee(@RequestBody @Valid EsIndexInitRequest request) {
        esBaseProvider.indexInit(request);
        return BaseResponse.SUCCESSFUL();
    }

}
