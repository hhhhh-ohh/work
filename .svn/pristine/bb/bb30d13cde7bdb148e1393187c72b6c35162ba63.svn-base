package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsLabelProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsLabelDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsLabelUpdateNameRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsLabelUpdateSortRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsLabelUpdateVisibleRequest;
import com.wanmi.sbc.goods.api.provider.goodslabel.GoodsLabelQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodslabel.GoodsLabelSaveProvider;
import com.wanmi.sbc.goods.api.request.goodslabel.*;
import com.wanmi.sbc.goods.api.response.goodslabel.GoodsLabelAddResponse;
import com.wanmi.sbc.goods.api.response.goodslabel.GoodsLabelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodslabel.GoodsLabelListResponse;
import com.wanmi.sbc.goods.api.response.goodslabel.GoodsLabelModifyResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsLabelVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Tag(name =  "商品标签管理API", description =  "GoodsLabelController")
@RestController
@Validated
@RequestMapping(value = "/goodslabel")
public class GoodsLabelController {

    @Autowired
    private GoodsLabelQueryProvider goodsLabelQueryProvider;

    @Autowired
    private GoodsLabelSaveProvider goodsLabelSaveProvider;

    @Autowired
    private EsGoodsLabelProvider esGoodsLabelProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "根据店铺id列表查询商品标签")
    @PostMapping("/list")
    public BaseResponse<GoodsLabelListResponse> list() {
        if (Objects.equals(Platform.PROVIDER,commonUtil.getOperator().getPlatform())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        GoodsLabelListRequest request = GoodsLabelListRequest.builder()
                .delFlag(DeleteFlag.NO).build();
        request.putSort("labelSort", "asc");
        request.putSort("goodsLabelId", "desc");
        return goodsLabelQueryProvider.list(request);
    }

    @Operation(summary = "根据id查询商品标签")
    @GetMapping("/{goodsLabelId}")
    public BaseResponse<GoodsLabelByIdResponse> getById(@PathVariable Long goodsLabelId) {
        if (goodsLabelId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsLabelByIdRequest idReq = new GoodsLabelByIdRequest();
        idReq.setGoodsLabelId(goodsLabelId);
        return goodsLabelQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增商品标签")
    @PostMapping("/add")
    public BaseResponse<GoodsLabelAddResponse> add(@RequestBody @Valid GoodsLabelAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreateTime(LocalDateTime.now());
        addReq.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        addReq.setLabelSort(1);
        return goodsLabelSaveProvider.add(addReq);
    }

    @Operation(summary = "修改商品标签")
    @PutMapping("/modify")
    public BaseResponse<GoodsLabelModifyResponse> modify(@RequestBody @Valid GoodsLabelModifyRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreIdWithDefault());
        GoodsLabelModifyResponse response = goodsLabelSaveProvider.modify(modifyReq).getContext();
        //更新ES
        if (Objects.nonNull(response.getGoodsLabelVO())) {
            esGoodsLabelProvider.updateLabelName(EsGoodsLabelUpdateNameRequest.builder().
                    goodsLabelVO(response.getGoodsLabelVO()).build());
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "修改商品展示状态")
    @PutMapping("/modify-visible")
    public BaseResponse modifyVisible(@RequestBody @Valid GoodsLabelModifyVisibleRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreIdWithDefault());
        goodsLabelSaveProvider.modifyVisible(modifyReq);
        //更新ES
        GoodsLabelVO vo = new GoodsLabelVO();
        vo.setGoodsLabelId(modifyReq.getGoodsLabelId());
        vo.setLabelVisible(modifyReq.getLabelVisible());
        esGoodsLabelProvider.updateLabelVisible(EsGoodsLabelUpdateVisibleRequest.builder().goodsLabelVO(vo).build());
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "标签拖拽排序")
    @PutMapping(value = "/editSort")
    @MultiSubmit
    public BaseResponse editSort(@RequestBody GoodsLabelSortRequest request) {
        if (CollectionUtils.isEmpty(request.getLabelIdList())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setStoreId(commonUtil.getStoreIdWithDefault());
        List<GoodsLabelVO> list = goodsLabelSaveProvider.editSort(request).getContext().getList();
        esGoodsLabelProvider.updateGoodsLabelSort(EsGoodsLabelUpdateSortRequest.builder().goodsLabelVOList(list).build());
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据id删除商品标签")
    @DeleteMapping("/{goodsLabelId}")
    public BaseResponse deleteById(@PathVariable Long goodsLabelId) {
        if (goodsLabelId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsLabelDelByIdRequest delByIdReq = new GoodsLabelDelByIdRequest();
        delByIdReq.setGoodsLabelId(goodsLabelId);
        delByIdReq.setStoreId(commonUtil.getStoreIdWithDefault());
        goodsLabelSaveProvider.deleteById(delByIdReq);
        // 同步删除es对应关联的标签
        esGoodsLabelProvider.deleteSomeLabel(EsGoodsLabelDeleteByIdsRequest.builder().
                ids(Collections.singletonList(goodsLabelId)).build());
        return BaseResponse.SUCCESSFUL();
    }
}
