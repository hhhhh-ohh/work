package com.wanmi.sbc.bargain;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
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
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询砍价")
    @PostMapping("/page")
    public BaseResponse<MicroServicePage<BargainVO>> getPage(@RequestBody @Valid BargainQueryRequest pageReq) {
        pageReq.putSort("createTime", "desc");
        Operator operator = commonUtil.getOperator();
        if (Platform.PLATFORM != operator.getPlatform()) {
            pageReq.setStoreId(commonUtil.getStoreId());
        }
        return bargainQueryProvider.pageForPlatForm(pageReq);
    }

    @Operation(summary = "根据id查询砍价")
    @GetMapping("/{bargainId}")
    public BaseResponse<BargainVO> getById(@PathVariable Long bargainId) {
        if (bargainId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BargainByIdRequest idReq = new BargainByIdRequest();
        idReq.setBargainId(bargainId);
        return bargainQueryProvider.getByIdForPlatForm(idReq);
    }


}
