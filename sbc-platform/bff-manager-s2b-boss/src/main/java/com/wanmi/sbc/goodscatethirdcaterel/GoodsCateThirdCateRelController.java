package com.wanmi.sbc.goodscatethirdcaterel;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelProvider;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.*;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.*;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "平台类目和第三方平台类目映射管理API", description =  "GoodsCateThirdCateRelController")
@RestController
@Validated
@RequestMapping(value = "/goodscatethirdcaterel")
public class GoodsCateThirdCateRelController {

    @Autowired
    private GoodsCateThirdCateRelQueryProvider goodsCateThirdCateRelQueryProvider;

    @Autowired
    private GoodsCateThirdCateRelProvider goodsCateThirdCateRelProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Operation(summary = "分页查询平台类目和第三方平台类目映射")
    @PostMapping("/page")
    public BaseResponse<GoodsCateThirdCateRelPageResponse> getPage(@RequestBody @Valid GoodsCateThirdCateRelPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return goodsCateThirdCateRelQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询平台类目和第三方平台类目映射")
    @PostMapping("/list")
    public BaseResponse<GoodsCateThirdCateRelListResponse> getList(@RequestBody @Valid GoodsCateThirdCateRelListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return goodsCateThirdCateRelQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询平台类目和第三方平台类目映射")
    @Parameter(name = "id", description = "商品分类id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<GoodsCateThirdCateRelByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsCateThirdCateRelByIdRequest idReq = new GoodsCateThirdCateRelByIdRequest();
        idReq.setId(id);
        return goodsCateThirdCateRelQueryProvider.getById(idReq);
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量新增平台类目和第三方平台类目映射")
    @PostMapping("/addBatch")
    public BaseResponse addBatch(@RequestBody @Valid GoodsCateThirdCateRelAddRequest addReq) {
        if(ThirdPlatformType.WECHAT_VIDEO.equals(addReq.getGoodsCateThirdCateRels().get(0).getThirdPlatformType())){
            goodsCateThirdCateRelProvider.addBatchForWechat(addReq);
        }else {
            goodsCateThirdCateRelProvider.addBatch(addReq);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改平台类目和第三方平台类目映射")
    @PutMapping("/modify")
    public BaseResponse<GoodsCateThirdCateRelModifyResponse> modify(@RequestBody @Valid GoodsCateThirdCateRelModifyRequest modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());
        return goodsCateThirdCateRelProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除平台类目和第三方平台类目映射")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsCateThirdCateRelDelByIdRequest delByIdReq = new GoodsCateThirdCateRelDelByIdRequest();
        delByIdReq.setId(id);
        return goodsCateThirdCateRelProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除平台类目和第三方平台类目映射")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid GoodsCateThirdCateRelDelByIdListRequest delByIdListReq) {
        return goodsCateThirdCateRelProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "删除微信类目映射")
    @DeleteMapping("/wechat")
    public BaseResponse deleteWechat(@RequestBody @Valid DeleteWechatCateMapRequest request) {
        return goodsCateThirdCateRelProvider.deleteWechat(request);
    }


}
