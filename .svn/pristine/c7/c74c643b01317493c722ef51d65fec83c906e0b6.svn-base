package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsPropertyByIdListRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsPropertyIndexRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsPropertyModifyRequest;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyProvider;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsproperty.*;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyModifyResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyPageResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;

/**
 * @author houshuai
 * @date 2021/4/21 19:20
 * @description <p> 商品属性 </p>
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name =  "商品属性服务", description =  "BossGoodsPropertyController")
public class BossGoodsPropertyController {

    @Autowired
    private GoodsPropertyQueryProvider goodsPropertyQueryProvider;

    @Autowired
    private GoodsPropertyProvider goodsPropertyProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 分页查询商品属性
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页商品属性")
    @PostMapping(value = "/prop-list")
    public BaseResponse<GoodsPropertyPageResponse> findGoodsPropertyPage(@RequestBody @Valid GoodsPropertyPageRequest request) {

        return goodsPropertyQueryProvider.findGoodsPropertyPage(request);
    }

    /**
     * 删除商品属性
     *
     * @param propId
     * @return
     */
    @Operation(summary = "删除商品属性")
    @DeleteMapping(value = "/delete-prop/{propId}")
    public BaseResponse deleteById(@PathVariable("propId") Long propId) {
        GoodsPropertyDelByIdRequest request = GoodsPropertyDelByIdRequest.builder()
                .propId(propId)
                .build();

        GoodsPropertyByIdRequest queryRequest = GoodsPropertyByIdRequest.builder()
                .propId(propId)
                .build();
        GoodsPropertyByIdResponse goodsPropertyByIdResponse = goodsPropertyQueryProvider.getGoodsPropertyById(queryRequest).getContext();

        BaseResponse baseResponse = goodsPropertyProvider.deleteById(request);
        //数据同步es
        EsGoodsPropertyByIdListRequest esRequest = EsGoodsPropertyByIdListRequest.builder()
                .propId(request.getPropId())
                .build();
        esGoodsInfoElasticProvider.deleteGoodsProperty(esRequest);
        operateLogMQUtil.convertAndSend("商品", "删除属性",
                "删除属性：[" + goodsPropertyByIdResponse.getPropName() + "],删除人：" + commonUtil.getOperator().getName());
        return baseResponse;
    }

    /**
     * 修改商品属性
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改商品属性")
    @PutMapping(value = "/modify-prop")
    public BaseResponse modifyGoodsProperty(@RequestBody @Valid GoodsPropertyModifyRequest request) {
        BaseResponse<GoodsPropertyModifyResponse> response = goodsPropertyProvider.modifyGoodsProperty(request);
        GoodsPropertyModifyResponse modifyResponse = response.getContext();
        //数据同步es
        EsGoodsPropertyModifyRequest modifyRequest = EsGoodsPropertyModifyRequest.builder().build();
        BeanUtils.copyProperties(request,modifyRequest);
        modifyRequest.setCateIdMap(modifyResponse.getCateIdMap());
        modifyRequest.setDetailIdMap(modifyResponse.getDetailIdMap());
        esGoodsInfoElasticProvider.modifyGoodsProperty(modifyRequest);
        operateLogMQUtil.convertAndSend("商品", "编辑属性",
                "编辑属性：[" + request.getPropName() + "],修改人：" + commonUtil.getOperator().getName());
        return response;
    }

    /**
     * 修改商品属性
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改商品属性是否索引")
    @PutMapping(value = "/modify-prop-index")
    public BaseResponse modifyIndexFlag(@RequestBody @Valid GoodsPropertyIndexRequest request) {
        BaseResponse baseResponse = goodsPropertyProvider.modifyIndexFlag(request);
        //同步es
        EsGoodsPropertyIndexRequest indexRequest = EsGoodsPropertyIndexRequest.builder()
                .propId(request.getPropId())
                .indexFlag(request.getIndexFlag())
                .build();
        esGoodsInfoElasticProvider.modifyPropIndexFlag(indexRequest);
        return baseResponse;
    }

    /**
     * 新增商品属性
     *
     * @param request
     * @return
     */
    @Operation(summary = "新增商品属性")
    @PostMapping(value = "/add-prop")
    public BaseResponse saveGoodsProperty(@RequestBody @Valid GoodsPropertyAddRequest request) {
        BaseResponse<Long> response = goodsPropertyProvider.saveGoodsProperty(request);
        operateLogMQUtil.convertAndSend("商品", "新增属性",
                "新增属性：[" + request.getPropName() + "],创建人：" + commonUtil.getOperator().getName());
        return response;
    }

    /**
     * 查看详情
     *
     * @param propId
     * @return
     */
    @Operation(summary = "查看属性详情")
    @GetMapping(value = "/find-prop-detail/{propId}")
    public BaseResponse getGoodsPropertyById(@PathVariable("propId") Long propId) {
        GoodsPropertyByIdRequest request = GoodsPropertyByIdRequest.builder()
                .propId(propId)
                .build();
        return goodsPropertyQueryProvider.getGoodsPropertyById(request);
    }
}
