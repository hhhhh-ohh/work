package com.wanmi.sbc.flashsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.flashsalecate.FlashSaleCateQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalecate.FlashSaleCateListRequest;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@Tag(name =  "秒杀分类管理API", description =  "FlashSaleCateController")
@RestController
@Validated
@RequestMapping(value = "/flashsalecate")
public class FlashSaleCateController {

    @Autowired
    private FlashSaleCateQueryProvider flashSaleCateQueryProvider;

    @Operation(summary = "列表查询秒杀分类")
    @PostMapping("/list")
    public BaseResponse<FlashSaleCateListResponse> getList(@RequestBody @Valid FlashSaleCateListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("sort", "desc");
        return flashSaleCateQueryProvider.list(listReq);
    }
}
