package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoSyncSpuRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSyncSpuResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.util.mapper.GoodsInfoMapper;
import com.wanmi.sbc.goods.util.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品扩展服务
 *
 * @author daiyitian
 * @date 2017/4/11
 */
@Slf4j
@Service
public class GoodsInfoExtraService {

    @Autowired private GoodsInfoService goodsInfoService;
    @Autowired private GoodsRepository goodsRepository;
    @Autowired private GoodsInfoMapper goodsInfoMapper;
    @Autowired private GoodsMapper goodsMapper;

    /**
     * sku同步spu
     * @param request 同步请求参数
     * @return 最新spu、sku数据
     */
    @Transactional
    public GoodsInfoSyncSpuResponse syncSpu(GoodsInfoSyncSpuRequest request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(request.getGoodsInfoIds());
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> skuList = goodsInfoService.findByParams(queryRequest);

        //提取供应商的spuId
        Set<String> providerSpuSet = skuList.stream()
                .filter(s -> !Objects.equals(GoodsSource.SELLER.toValue(), s.getGoodsSource()))
                .map(GoodsInfo::getGoodsId)
                .distinct()
                .collect(Collectors.toSet());

        //查询同一spu下的SKU
        List<String> spuIds = skuList.stream().map(GoodsInfo::getGoodsId).distinct().collect(Collectors.toList());
        queryRequest.setGoodsInfoIds(Collections.emptyList());
        queryRequest.setGoodsIds(spuIds);
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        Map<String, List<GoodsInfo>> skusGrMap = goodsInfoService.findByParams(queryRequest)
                .stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsId));
        //更新SPU库存
        List<GoodsVO> spuList = new ArrayList<>();
        skusGrMap.forEach((spuId,skus) -> {
            long stock = skus.stream().filter(i -> Objects.nonNull(i.getStock()))
                    .mapToLong(GoodsInfo::getStock).sum();
            goodsRepository.updateStockByGoodsId(spuId, stock);

            GoodsVO spu = new GoodsVO();
            spu.setGoodsId(spuId);
            spu.setStock(stock);
            spu.setGoodsSource(GoodsSource.SELLER.toValue());
            if(providerSpuSet.contains(spuId)){
                spu.setGoodsSource(GoodsSource.PROVIDER.toValue());
            }
            spuList.add(spu);
        });

        GoodsInfoSyncSpuResponse response = new GoodsInfoSyncSpuResponse();
        response.setSkuList(goodsInfoMapper.goodsInfosToGoodsInfoVOs(skuList));
        response.setSpuList(spuList);
        return response;
    }
}
