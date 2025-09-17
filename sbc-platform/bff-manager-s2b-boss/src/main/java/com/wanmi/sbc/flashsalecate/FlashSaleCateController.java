package com.wanmi.sbc.flashsalecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.flashsalecate.FlashSaleCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalecate.FlashSaleCateSaveProvider;
import com.wanmi.sbc.goods.api.request.flashsalecate.*;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateAddResponse;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateListResponse;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateModifyResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "秒杀分类管理API", description =  "FlashSaleCateController")
@RestController
@Validated
@RequestMapping(value = "/flashsalecate")
public class FlashSaleCateController {

    @Autowired
    private FlashSaleCateQueryProvider flashSaleCateQueryProvider;

    @Autowired
    private FlashSaleCateSaveProvider flashSaleCateSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Operation(summary = "列表查询秒杀分类")
    @PostMapping("/list")
    public BaseResponse<FlashSaleCateListResponse> getList(@RequestBody @Valid FlashSaleCateListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("sort", "asc");
        return flashSaleCateQueryProvider.list(listReq);
    }

    @Operation(summary = "新增秒杀分类")
    @PostMapping("/add")
    public BaseResponse<FlashSaleCateAddResponse> add(@RequestBody @Valid FlashSaleCateAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        operateLogMQUtil.convertAndSend("营销", "秒杀分类", "新增秒杀分类: " + addReq.getCateName());
        return flashSaleCateSaveProvider.add(addReq);
    }

    @Operation(summary = "修改秒杀分类")
    @PutMapping("/modify")
    public BaseResponse<FlashSaleCateModifyResponse> modify(@RequestBody @Valid FlashSaleCateModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        operateLogMQUtil.convertAndSend("营销", "秒杀分类", "修改秒杀分类: " + modifyReq.getCateName());
        return flashSaleCateSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除秒杀分类")
    @Parameter(name = "cateId", description = "分类id", required = true)
    @DeleteMapping("/{cateId}")
    public BaseResponse deleteById(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FlashSaleCateDelByIdRequest delByIdReq = new FlashSaleCateDelByIdRequest();
        delByIdReq.setCateId(cateId);
        operateLogMQUtil.convertAndSend("营销", "秒杀分类", "删除秒杀分类Id: " + cateId);
        return flashSaleCateSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "秒杀分类拖拽排序")
    @PutMapping(value = "/editSort")
    public BaseResponse editSort(@RequestBody FlashSaleCateSortRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setUpdateTime(LocalDateTime.now());
        return flashSaleCateSaveProvider.editSort(request);
    }

}
