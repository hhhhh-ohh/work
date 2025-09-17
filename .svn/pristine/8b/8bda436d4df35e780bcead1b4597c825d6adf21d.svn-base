package com.wanmi.sbc.bargainjoin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.provider.bargainjoin.BargainJoinQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name =  "帮砍记录管理API", description =  "BargainJoinController")
@RestController
@Validated
@RequestMapping(value = "/bargainjoin")
public class BargainJoinController {

    @Autowired
    private BargainJoinQueryProvider bargainJoinQueryProvider;

    @Operation(summary = "分页查询帮砍记录")
    @PostMapping("/page")
    public BaseResponse<MicroServicePage<BargainJoinVO>> getPage(@RequestBody @Valid BargainJoinQueryRequest pageReq) {
        pageReq.putSort("bargainJoinId", "desc");
        return bargainJoinQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询帮砍记录")
    @PostMapping("/list")
    public BaseResponse<List<BargainJoinVO>> getList(@RequestBody @Valid BargainJoinQueryRequest listReq) {
        listReq.putSort("bargainJoinId", "desc");
        return bargainJoinQueryProvider.list(listReq);
    }


}
