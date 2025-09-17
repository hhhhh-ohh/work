package com.wanmi.sbc.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingProvider;
import com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "商品关联分析模型API", description =  "GoodsCorrelationModelSettingController")
@RestController
@Validated
@RequestMapping(value = "/goodscorrelationmodelsetting")
public class GoodsCorrelationModelSettingController {

    @Autowired
    private GoodsCorrelationModelSettingQueryProvider goodsCorrelationModelSettingQueryProvider;

    @Autowired
    private GoodsCorrelationModelSettingProvider goodsCorrelationModelSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<GoodsCorrelationModelSettingListResponse> getList() {
        return goodsCorrelationModelSettingQueryProvider.list();
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public BaseResponse<GoodsCorrelationModelSettingByIdResponse> getById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsCorrelationModelSettingByIdRequest idReq = new GoodsCorrelationModelSettingByIdRequest();
        idReq.setId(id);
        return goodsCorrelationModelSettingQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public BaseResponse<GoodsCorrelationModelSettingAddResponse> add(@RequestBody @Valid GoodsCorrelationModelSettingAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return goodsCorrelationModelSettingProvider.add(addReq);
    }

    @Operation(summary = "修改")
    @PutMapping("/modify")
    public BaseResponse<GoodsCorrelationModelSettingListResponse> modify(@RequestBody @Valid GoodsCorrelationModelSettingModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return goodsCorrelationModelSettingProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsCorrelationModelSettingDelByIdRequest delByIdReq = new GoodsCorrelationModelSettingDelByIdRequest();
        delByIdReq.setId(id);
        return goodsCorrelationModelSettingProvider.deleteById(delByIdReq);
    }


}
