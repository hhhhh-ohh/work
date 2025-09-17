package com.wanmi.sbc.share;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.storesharerecord.StoreShareRecordSaveProvider;
import com.wanmi.sbc.customer.api.request.storesharerecord.StoreShareRecordAddRequest;
import com.wanmi.sbc.customer.api.response.storesharerecord.StoreShareRecordAddResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


/**
 * @author zhangwenchang
 */
@Tag(name =  "商城分享管理API", description =  "StoreShareRecordController")
@RestController
@Validated
@RequestMapping(value = "/store/share/record")
public class StoreShareRecordController {

    @Autowired
    private StoreShareRecordSaveProvider storeShareRecordSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "新增商城分享")
    @PostMapping("/add")
    public BaseResponse<StoreShareRecordAddResponse> add(@RequestBody @Valid StoreShareRecordAddRequest addReq) {
        addReq.setCreateTime(LocalDateTime.now());
        addReq.setTerminalSource(commonUtil.getTerminal());
        addReq.setCustomerId(commonUtil.getOperatorId());
        if (addReq.getIndexType() == null) {
            return BaseResponse.SUCCESSFUL();
        }
        return storeShareRecordSaveProvider.add(addReq);
    }
}
