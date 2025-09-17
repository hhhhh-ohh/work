package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkQueryProvider;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkSaveProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkAddRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkDelByIdListRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkDelByIdRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import com.wanmi.sbc.customer.vo.GoodsFootmarkInfoListVO;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Validated
@RequestMapping("/customer")
@Tag(name = "GoodsFootmarkController", description = "S2B web公用-我的足迹API")
public class CustomerFootmarkController {

    @Autowired
    private GoodsFootmarkQueryProvider goodsFootmarkQueryProvider;

    @Autowired
    private GoodsFootmarkSaveProvider goodsFootmarkSaveProvider;

    @Resource(name = "customerGoodsFootmarkListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest, EsGoodsInfoSimpleResponse> goodsListInterface;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "记录我的足迹")
    @RequestMapping(value = "/goodsfootmark/record/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse recordFootmark(@PathVariable String goodsInfoId) {
        // 构造消息，异步记录我的足迹
        GoodsFootmarkAddRequest goodsFootmarkAddRequest = new GoodsFootmarkAddRequest();
        goodsFootmarkAddRequest.setGoodsInfoId(goodsInfoId);
        goodsFootmarkAddRequest.setCustomerId(commonUtil.getOperatorId());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_FOOTMARK_OUTPUT);
        mqSendDTO.setData(goodsFootmarkAddRequest);
        mqSendProvider.send(mqSendDTO);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "统计30天内足迹商品数量（浏览商品数）")
    @GetMapping("/goodsfootmark/browseNum")
    public BaseResponse<Long> count() {
        // 查询最近30天的足迹记录数量
        GoodsFootmarkQueryRequest queryRequest = new GoodsFootmarkQueryRequest();
        queryRequest.setUpdateTimeBegin(LocalDateTime.now().minusDays(90));
        queryRequest.setUpdateTimeEnd(LocalDateTime.now());
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        queryRequest.setDelFlag(DeleteFlag.NO);
        return BaseResponse.success(goodsFootmarkQueryProvider.count(queryRequest).getContext().getCount());
    }

    @Operation(summary = "列表查询我的足迹")
    @PostMapping("/goodsfootmark/page")
    public BaseResponse<GoodsInfoListResponse> page(@RequestBody @Valid GoodsFootmarkQueryRequest queryRequest) {

        // 1. 查询条件封装
        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setPageNum(queryRequest.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(queryRequest.getPageSize());
        esGoodsInfoQueryRequest.setCustomerId(commonUtil.getOperatorId());

        // 2. 查询我的足迹商品列表
        MicroServicePage skuListMicroServicePage = goodsBaseService.skuListConvert(
                goodsListInterface.getList(esGoodsInfoQueryRequest), null).getGoodsInfoPage();

        // 3. 为商品额外填充足迹信息
        List<GoodsFootmarkInfoListVO> goodsFootmarkInfoListVOList = new ArrayList<>();
        for (Object item : skuListMicroServicePage.getContent()) {
            if (item instanceof GoodsInfoListVO) {
                GoodsInfoListVO goodsInfoListVO = (GoodsInfoListVO) item;
                // 拷贝属性
                GoodsFootmarkInfoListVO goodsFootmarkInfoListVO = KsBeanUtil.convert(goodsInfoListVO, GoodsFootmarkInfoListVO.class);
                // 将扩展属性填充至外层对象
                Map<String, String> extendMap = goodsInfoListVO.getExtendMap();
                GoodsFootmarkVO goodsFootmarkVO = JSON.parseObject(extendMap.get("goodsFootmarkVO"), GoodsFootmarkVO.class);
                if (Objects.nonNull(goodsFootmarkVO)) {
                    goodsFootmarkInfoListVO.setFootmarkId(goodsFootmarkVO.getFootmarkId());
                    goodsFootmarkInfoListVO.setFootmarkIdStr(goodsFootmarkVO.getFootmarkIdStr());
                    goodsFootmarkInfoListVO.setSortTime(goodsFootmarkVO.getSortTime());
                    // 清空extendMap
                    goodsFootmarkInfoListVO.setExtendMap(Collections.EMPTY_MAP);
                    goodsFootmarkInfoListVOList.add(goodsFootmarkInfoListVO);
                }
            }
        }

        // 4. 封装分页信息
        GoodsInfoListResponse response = new GoodsInfoListResponse();
        MicroServicePage<GoodsFootmarkInfoListVO> microServicePage =
                new MicroServicePage<>(goodsFootmarkInfoListVOList, queryRequest.getPageable(), skuListMicroServicePage.getTotal());
        response.setGoodsInfoPage(microServicePage);
        return BaseResponse.success(response);
    }

    @Operation(summary = "根据id删除我的足迹")
    @DeleteMapping("/goodsfootmark/{footmarkId}")
    public BaseResponse deleteById(@PathVariable Long footmarkId) {
        if (footmarkId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsFootmarkDelByIdRequest delByIdReq = new GoodsFootmarkDelByIdRequest();
        String customerId = commonUtil.getOperatorId();
        delByIdReq.setCustomerId(customerId);
        delByIdReq.setFootmarkId(footmarkId);
        return goodsFootmarkSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除我的足迹")
    @DeleteMapping("/goodsfootmark/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid GoodsFootmarkDelByIdListRequest delByIdListReq) {
        String customerId = commonUtil.getOperatorId();
        delByIdListReq.setCustomerId(customerId);
        return goodsFootmarkSaveProvider.deleteByIdList(delByIdListReq);
    }

}
