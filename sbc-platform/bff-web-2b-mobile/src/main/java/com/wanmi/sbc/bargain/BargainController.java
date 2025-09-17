package com.wanmi.sbc.bargain;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.provider.bargainjoin.BargainJoinSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargain.OriginateRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.JoinRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name =  "砍价管理API", description =  "BargainController")
@RestController
@Validated
@RequestMapping(value = "/bargain")
public class BargainController {

    @Autowired
    private BargainQueryProvider bargainQueryProvider;

    @Autowired
    private BargainSaveProvider bargainSaveProvider;

    @Autowired
    private BargainJoinSaveProvider bargainJoinSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "我的砍价-砍价列表")
    @PostMapping("/page")
    public BaseResponse<MicroServicePage<BargainVO>> getPage(@RequestBody @Valid BargainQueryRequest pageReq) {
        pageReq.setCustomerId(commonUtil.getOperatorId());
        pageReq.putSort("createTime", "desc");
        return bargainQueryProvider.page(pageReq);
    }

    @Operation(summary = "砍价活动 - 根据id查询砍价")
    @GetMapping("/{bargainId}")
    public BaseResponse<BargainVO> getById(@PathVariable Long bargainId) {
        if (bargainId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BargainByIdRequest idReq = new BargainByIdRequest();
        idReq.setBargainId(bargainId);
        idReq.setCustomerId(commonUtil.getOperatorId());
        return bargainQueryProvider.getById(idReq);
    }

    @Operation(summary = "发起砍价")
    @PostMapping("/originate")
    @MultiSubmit
    public BaseResponse<BargainVO> originate(@RequestBody @Valid OriginateRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        request.setCustomerAccount(commonUtil.getOperator().getAccount());
        return bargainSaveProvider.originate(request);
    }

    @Operation(summary = "帮砍")
    @PostMapping("/join")
    @MultiSubmit
    public BaseResponse<BargainJoinVO> join(@RequestBody @Valid JoinRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return bargainJoinSaveProvider.join(request);
    }


}
