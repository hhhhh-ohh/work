package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsInfoDTO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.response.GoodsInfoPcListVO;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.bean.vo.EsGoodsBoostSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoPcConvertMapper;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsPcController
 * @description
 * @date 2021/10/13 11:12 上午
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsPcController", description = "S2B pc专用-商品信息API")
public class GoodsPcController {

    @Resource(name = "goodsPcListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest,EsGoodsInfoSimpleResponse> goodsListInterface;

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private GoodsInfoPcConvertMapper goodsInfoConvertMapper;

    @Autowired private CommonUtil commonUtil;

    /**
     * 商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品分页")
    @RequestMapping(value = "/pc/spuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> spuList(
            @RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 配置boost
        getEsGoodsBoost(queryRequest);
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        return BaseResponse.success(this.skuListConvert(goodsListInterface.getList(queryRequest)));
    }

    /**
     * 未登录时,查询商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "未登录时,查询商品分页")
    @RequestMapping(value = "/unLogin/pc/spuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> unLoginSpuList(
            @RequestBody EsGoodsInfoQueryRequest queryRequest) {

        // 配置boost
        getEsGoodsBoost(queryRequest);
        EsGoodsInfoSimpleResponse response = goodsListInterface.getList(queryRequest);
        if (CollectionUtils.isNotEmpty(queryRequest.getEsGoodsInfoDTOList())
                && CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            Map<String, List<EsGoodsInfoDTO>> buyCountMap =
                    queryRequest.getEsGoodsInfoDTOList().stream()
                            .collect(Collectors.groupingBy(EsGoodsInfoDTO::getGoodsInfoId));

            response.getEsGoodsInfoPage().getContent().stream()
                    .filter(
                            esGoodsInfo ->
                                    Objects.nonNull(esGoodsInfo.getGoodsInfo())
                                            && buyCountMap.containsKey(
                                                    esGoodsInfo.getGoodsInfo().getGoodsInfoId()))
                    .forEach(
                            esGoodsInfo -> {
                                GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
                                goodsInfo.setBuyCount(
                                        buyCountMap
                                                .get(esGoodsInfo.getGoodsInfo().getGoodsInfoId())
                                                .get(0)
                                                .getGoodsNum());
                            });
        }
        return BaseResponse.success(this.skuListConvert(response));
    }

    private void getEsGoodsBoost(EsGoodsInfoQueryRequest request) {
        // 查询并组装
        String context =
                systemConfigQueryProvider.findContextByConfigType().getContext().getContext();
        if (StringUtils.isNotBlank(context)) {
            try {
                EsGoodsBoostSettingVO esGoodsBoostSettingVO =
                        JSONObject.parseObject(context, EsGoodsBoostSettingVO.class);
                if (Objects.nonNull(esGoodsBoostSettingVO)) {
                    request.setGoodsSubtitleBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getGoodsSubtitleBoost())
                                    ? esGoodsBoostSettingVO.getGoodsSubtitleBoost()
                                    : null);
                    request.setBrandNameBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getBrandNameBoost())
                                    ? esGoodsBoostSettingVO.getBrandNameBoost()
                                    : null);
                    request.setCateNameBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getCateNameBoost())
                                    ? esGoodsBoostSettingVO.getCateNameBoost()
                                    : null);
                    request.setGoodsInfoNameBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getGoodsInfoNameBoost())
                                    ? esGoodsBoostSettingVO.getGoodsInfoNameBoost()
                                    : null);
                    request.setSpecTextBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getSpecTextBoost())
                                    ? esGoodsBoostSettingVO.getSpecTextBoost()
                                    : null);
                    request.setGoodsLabelNameBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getGoodsLabelNameBoost())
                                    ? esGoodsBoostSettingVO.getGoodsLabelNameBoost()
                                    : null);
                    request.setGoodsPropDetailNestNameBoost(
                            Objects.nonNull(esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost())
                                    ? esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost()
                                    : null);
                }
            } catch (Exception e) {
                log.error("es查询权重配比失败，{}", e);
            }
        }
    }

    protected GoodsInfoListResponse skuListConvert(EsGoodsInfoSimpleResponse response) {
        GoodsInfoListResponse goodsInfoListResponse = new GoodsInfoListResponse();
        if (goodsInfoListResponse != null
                && CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {

            Map<String,List<GoodsInfoListVO>> map = new LinkedHashMap<>();
            for (EsGoodsInfoVO esGoodsInfoVO : response.getEsGoodsInfoPage().getContent()) {
                GoodsInfoListVO goodsInfoListVO =
                        goodsInfoConvertMapper.goodsInfoNestVOToGoodsInfoListVO(
                                esGoodsInfoVO.getGoodsInfo());
                goodsInfoListVO.setGoodsLabelList(esGoodsInfoVO.getGoodsLabelList());
                goodsInfoListVO.setGoodsSubtitle(esGoodsInfoVO.getGoodsSubtitle());
                List<GoodsInfoListVO> listVOS = map.get(goodsInfoListVO.getGoodsId());
                if(CollectionUtils.isEmpty(listVOS)){
                    listVOS = new ArrayList<>();
                }
                listVOS.add(goodsInfoListVO);
                map.put(goodsInfoListVO.getGoodsId(),listVOS);
            }
            List<GoodsInfoPcListVO> content = new ArrayList<>();
            for(Map.Entry<String,List<GoodsInfoListVO>> entry : map.entrySet()){
                GoodsInfoPcListVO pcListVO = goodsInfoConvertMapper.goodsInfoListVOToGoodsInfoPcListVO(entry.getValue().get(0));
                pcListVO.setSkuList(entry.getValue().subList(1,entry.getValue().size()));
                content.add(pcListVO);
            }

            MicroServicePage<GoodsInfoPcListVO> page = new MicroServicePage(content);
            page.setTotal(response.getEsGoodsInfoPage().getTotal());
            page.setSize(response.getEsGoodsInfoPage().getSize());
            page.setNumber(response.getEsGoodsInfoPage().getNumber());
            goodsInfoListResponse.setGoodsInfoPage(page);
        }
        return goodsInfoListResponse;
    }
}
