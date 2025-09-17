package com.wanmi.sbc.message;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendProvider;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendQueryProvider;
import com.wanmi.sbc.message.api.request.storenoticesend.*;
import com.wanmi.sbc.message.api.response.storenoticesend.*;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "商家公告管理API", description =  "StoreNoticeSendController")
@RestController
@Validated
@RequestMapping(value = "/storeNoticeSend")
public class StoreNoticeSendController {

    @Autowired
    private StoreNoticeSendQueryProvider storeNoticeSendQueryProvider;

    @Autowired
    private StoreNoticeSendProvider storeNoticeSendProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Value("${scan-task.within-time}")
    private int withinTime;

    @Operation(summary = "分页查询商家公告")
    @PostMapping("/page")
    public BaseResponse<StoreNoticeSendPageResponse> getPage(@RequestBody @Valid StoreNoticeSendPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return storeNoticeSendQueryProvider.page(pageReq);
    }

    @Operation(summary = "根据id查询商家公告")
    @GetMapping("/{id}")
    public BaseResponse<StoreNoticeSendByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreNoticeSendByIdRequest idReq = new StoreNoticeSendByIdRequest();
        idReq.setId(id);
        return storeNoticeSendQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增商家公告")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid StoreNoticeSendAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setWithinTime(withinTime);
        return storeNoticeSendProvider.add(addReq);
    }

    @Operation(summary = "修改商家公告")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid StoreNoticeSendModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setWithinTime(withinTime);
        return storeNoticeSendProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id撤回商家公告")
    @DeleteMapping("/withdraw/{id}")
    public BaseResponse withdrawById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreNoticeSendDelByIdRequest delByIdReq = new StoreNoticeSendDelByIdRequest();
        delByIdReq.setId(id);
        return storeNoticeSendProvider.withdrawById(delByIdReq);
    }

    @Operation(summary = "根据id删除商家公告")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreNoticeSendDelByIdRequest delByIdReq = new StoreNoticeSendDelByIdRequest();
        delByIdReq.setId(id);
        return storeNoticeSendProvider.deleteById(delByIdReq);
    }

}
