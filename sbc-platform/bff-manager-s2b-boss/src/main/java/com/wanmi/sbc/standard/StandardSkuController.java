package com.wanmi.sbc.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByStandardIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuModifyRequest;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByIdResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByStandardIdResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品库SKU服务
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@Tag(name =  "商品库SKU服务",description = "StandardSkuController")
public class StandardSkuController {

    @Autowired
    private StandardSkuProvider standardSkuProvider;

    @Autowired
    private StandardSkuQueryProvider standardSkuQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 获取商品库SKU详情信息
     *
     * @param standardInfoId 商品库SKU编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品库SKU详情信息")
    @RequestMapping(value = "/standard/sku/{standardInfoId}", method = RequestMethod.GET)
    public BaseResponse<StandardSkuByIdResponse> info(@PathVariable String standardInfoId) {
        return standardSkuQueryProvider.getById(
                StandardSkuByIdRequest.builder().standardInfoId(standardInfoId).build()
        );
    }

    /**
     * 编辑商品库SKU
     */
    @Operation(summary = "编辑商品库SKU")
    @RequestMapping(value = "/standard/sku", method = RequestMethod.PUT)
    public BaseResponse edit(@Valid @RequestBody StandardSkuModifyRequest saveRequest) {
        if (saveRequest.getGoodsInfo().getGoodsInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMQUtil.convertAndSend("商品", "编辑商品", "编辑商品：" + saveRequest.getGoodsInfo().getGoodsInfoName());
        return standardSkuProvider.modify(saveRequest);
    }

    /**
     * 获取skuid
     * @param goodsId
     * @return
     */
    @Operation(summary = "获取skuId")
    @GetMapping(value = "/standard/findStandardSkuId/{goodsId}")
    public BaseResponse<String> findGoodsInfoId(@PathVariable("goodsId") String goodsId) {
        StandardSkuVO goodsInfo = this.getGoodsInfo(goodsId);
        return BaseResponse.success(goodsInfo.getGoodsInfoId());
    }


    /**
     * 获取商品信息
     * @param goodsId
     * @return
     */
    private StandardSkuVO getGoodsInfo(String goodsId){
        StandardSkuByStandardIdRequest changeRequest = StandardSkuByStandardIdRequest.builder()
                .standardId(goodsId)
                .build();
        StandardSkuByStandardIdResponse response = standardSkuQueryProvider.listByStandardId(changeRequest).getContext();
        List<StandardSkuVO> goodsInfoList = response.getGoodsInfo();
        if(CollectionUtils.isEmpty(goodsInfoList)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        //判断是否有效
        List<StandardSkuVO> standardSkuVOList = goodsInfoList.stream()
                .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getAddedFlag(), NumberUtils.INTEGER_ONE))
                .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getDelFlag(), DeleteFlag.NO))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(standardSkuVOList)){
            return goodsInfoList.get(NumberUtils.INTEGER_ZERO);
        }
        return Collections.min(standardSkuVOList, Comparator.comparing(StandardSkuVO::getMarketPrice));
    }



}
