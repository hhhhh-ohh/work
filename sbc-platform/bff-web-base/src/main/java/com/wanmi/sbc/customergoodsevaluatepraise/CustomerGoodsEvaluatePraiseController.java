package com.wanmi.sbc.customergoodsevaluatepraise;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseQueryProvider;
import com.wanmi.sbc.goods.api.provider.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseSaveProvider;
import com.wanmi.sbc.goods.api.request.customergoodsevaluatepraise.*;
import com.wanmi.sbc.goods.api.response.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseAddResponse;
import com.wanmi.sbc.goods.api.response.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseByIdResponse;
import com.wanmi.sbc.goods.api.response.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseModifyResponse;
import com.wanmi.sbc.goods.api.response.customergoodsevaluatepraise.CustomerGoodsEvaluatePraisePageResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "会员商品评价点赞关联表管理API", description =  "CustomerGoodsEvaluatePraiseController")
@RestController
@Validated
@RequestMapping(value = "/customergoodsevaluatepraise")
public class CustomerGoodsEvaluatePraiseController {

    @Autowired
    private CustomerGoodsEvaluatePraiseQueryProvider customerGoodsEvaluatePraiseQueryProvider;

    @Autowired
    private CustomerGoodsEvaluatePraiseSaveProvider customerGoodsEvaluatePraiseSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询会员商品评价点赞关联表")
    @PostMapping("/page")
    public BaseResponse<CustomerGoodsEvaluatePraisePageResponse> page(@RequestBody @Valid CustomerGoodsEvaluatePraisePageRequest customerGoodsEvaluatePraisePageReq) {
        return customerGoodsEvaluatePraiseQueryProvider.page(customerGoodsEvaluatePraisePageReq);
    }

    @Operation(summary = "根据id查询会员商品评价点赞关联表")
    @GetMapping("/{id}")
    public BaseResponse<CustomerGoodsEvaluatePraiseByIdResponse> getById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerGoodsEvaluatePraiseByIdRequest idReq = new CustomerGoodsEvaluatePraiseByIdRequest();
        idReq.setId(id);
        return customerGoodsEvaluatePraiseQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增会员商品评价点赞关联表")
    @PostMapping("/add")
    public BaseResponse<CustomerGoodsEvaluatePraiseAddResponse> add(@RequestBody @Valid CustomerGoodsEvaluatePraiseAddRequest addReq) {
        addReq.setCustomerId(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return customerGoodsEvaluatePraiseSaveProvider.add(addReq);
    }

    @Operation(summary = "修改会员商品评价点赞关联表")
    @PutMapping("/modify")
    public BaseResponse<CustomerGoodsEvaluatePraiseModifyResponse> modify(@RequestBody @Valid CustomerGoodsEvaluatePraiseModifyRequest modifyReq) {
//        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
//        modifyReq.setUpdateTime(LocalDateTime.now());
        return customerGoodsEvaluatePraiseSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除会员商品评价点赞关联表")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerGoodsEvaluatePraiseDelByIdRequest delByIdReq = new CustomerGoodsEvaluatePraiseDelByIdRequest();
        delByIdReq.setId(id);
        return customerGoodsEvaluatePraiseSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除会员商品评价点赞关联表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid CustomerGoodsEvaluatePraiseDelByIdListRequest delByIdListReq) {
        return customerGoodsEvaluatePraiseSaveProvider.deleteByIdList(delByIdListReq);
    }

}
