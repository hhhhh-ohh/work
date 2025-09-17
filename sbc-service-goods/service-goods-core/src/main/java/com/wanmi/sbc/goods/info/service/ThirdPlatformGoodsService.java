package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.goodscatethirdcaterel.service.GoodsCateThirdCateRelService;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.spec.service.GoodsSpecService;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.standardimages.repository.StandardImageRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSkuSpecDetailRelRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecDetailRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * linkedmall商品服务
 */
@Service
@Slf4j
public class ThirdPlatformGoodsService {

    private static final Pattern PATTERN = Pattern.compile("\"[\\s\\S]+?\"");

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsCateThirdCateRelService goodsCateThirdCateRelService;

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private StandardSpecRepository standardSpecRepository;

    @Autowired
    private StandardSpecDetailRepository standardSpecDetailRepository;

    @Autowired
    private StandardSkuSpecDetailRelRepository standardSkuSpecDetailRelRepository;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private StandardImageRepository standardImageRepository;

    @Autowired
    private RedisUtil redisService;

    /**
     * 删除linkedmall商品
     */
    @Transactional
    public ThirdPlatformGoodsDelResponse thirdPlatformGoodsDel(ThirdPlatformGoodsDelDTO thirdPlatformGoodsDelDTO, Boolean deleteAllSku) {
        GoodsSource goodsSource = thirdPlatformGoodsDelDTO.getGoodsSource() == null ? GoodsSource.LINKED_MALL : thirdPlatformGoodsDelDTO.getGoodsSource();
        ThirdPlatformType thirdPlatformType = thirdPlatformGoodsDelDTO.getThirdPlatformType() == null ? ThirdPlatformType.LINKED_MALL : thirdPlatformGoodsDelDTO.getThirdPlatformType();
        ArrayList<String> esGoodsInfoIds = new ArrayList<>();
        List<String> standardIds = new ArrayList<>();
        List<String> delStandardIds = new ArrayList<>();
        String thirdPlatformSpuId = thirdPlatformGoodsDelDTO.getItemId().toString();
        if(Boolean.TRUE.equals(deleteAllSku)){
            List<GoodsInfo> oldGoodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                    .delFlag(DeleteFlag.NO.toValue())
                    .thirdPlatformSpuId(thirdPlatformSpuId).build());
            thirdPlatformGoodsDelDTO.setSkuIdList(oldGoodsInfos.stream().map(GoodsInfo::getThirdPlatformSkuId).map(Long::parseLong).collect(Collectors.toList()));
        }
        if(CollectionUtils.isNotEmpty(thirdPlatformGoodsDelDTO.getSkuIdList())){
            for (Long thirdPlatformSkuId : thirdPlatformGoodsDelDTO.getSkuIdList()) {
                //删除linkedmall供应商-sku
                List<GoodsInfo> oldGoodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                        .delFlag(DeleteFlag.NO.toValue())
                        .goodsSource(goodsSource.toValue())
                        .thirdPlatformSpuId(thirdPlatformSpuId)
                        .thirdPlatformSkuId(Collections.singletonList(thirdPlatformSkuId.toString())).build());
                if (oldGoodsInfos != null && oldGoodsInfos.size() > 0) {
                    for (GoodsInfo oldGoodsInfo : oldGoodsInfos) {
                        oldGoodsInfo.setDelFlag(DeleteFlag.YES);
                    }
                    goodsInfoRepository.saveAll(oldGoodsInfos);
                    goodsInfoSpecDetailRelRepository.deleteByGoodsInfoIds(Collections.singletonList(oldGoodsInfos.get(0).getGoodsInfoId()), oldGoodsInfos.get(0).getGoodsId());
                    log.info("删除linkedmall供应商-sku，所属linkedmall商品spuId:{},skuId:{}", thirdPlatformGoodsDelDTO.getItemId(),thirdPlatformSkuId);
                }
                //删除商品库linkedmall-sku
                StandardSku oldStandardSku = standardSkuRepository.findByDelFlagAndGoodsSourceAndThirdPlatformSpuIdAndThirdPlatformSkuId(DeleteFlag.NO, goodsSource.toValue(), thirdPlatformSpuId, thirdPlatformSkuId.toString());
                if (oldStandardSku != null) {
                    oldStandardSku.setDelFlag(DeleteFlag.YES);
                    standardSkuRepository.save(oldStandardSku);
                    standardSkuSpecDetailRelRepository.deleteByGoodsInfoIds(Collections.singletonList(oldStandardSku.getGoodsInfoId()), oldStandardSku.getGoodsId());
                    log.info("删除商品库linkedmall-sku，所属linkedmall商品spuId:{},skuId:{}", thirdPlatformGoodsDelDTO.getItemId(),thirdPlatformSkuId);
                }
                //禁售商家导入的linkedmall-sku
                List<GoodsInfo> storeGoodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                        .delFlag(DeleteFlag.NO.toValue())
                        .vendibility(1)
                        .goodsSource(GoodsSource.SELLER.toValue())
                        .thirdPlatformType(thirdPlatformType.toValue())
                        .thirdPlatformSpuId(thirdPlatformSpuId)
                        .thirdPlatformSkuId(Collections.singletonList(thirdPlatformSkuId.toString())).build());
                if (storeGoodsInfos != null && storeGoodsInfos.size() > 0) {
                    for (GoodsInfo storeGoodsInfo : storeGoodsInfos) {
                        storeGoodsInfo.setVendibility(0);
                        storeGoodsInfo.setUpdateTime(LocalDateTime.now());
                        esGoodsInfoIds.add(storeGoodsInfo.getGoodsInfoId());
                    }
                    goodsInfoRepository.saveAll(storeGoodsInfos);
                }

            }
        }
        List<GoodsInfo> linkedMallGoodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                .delFlag(DeleteFlag.NO.toValue())
                .goodsSource(goodsSource.toValue())
                .thirdPlatformSpuId(thirdPlatformSpuId).build());

        //删除linkemall供应商——spu
        if (linkedMallGoodsInfos == null || linkedMallGoodsInfos.size() < 1) {
            List<Goods> oldGoodsList = goodsService.findAll(GoodsQueryRequest.builder()
                    .delFlag(DeleteFlag.NO.toValue())
                    .goodsSource(goodsSource.toValue())
                    .thirdPlatformSpuId(thirdPlatformSpuId)
                    .build());
            if (oldGoodsList != null && oldGoodsList.size() > 0) {
                Goods oldGoods = oldGoodsList.get(0);
                oldGoods.setDelFlag(DeleteFlag.YES);
                goodsRepository.save(oldGoods);
                goodsSpecRepository.deleteByGoodsId(oldGoods.getGoodsId());
                goodsSpecDetailRepository.deleteByGoodsId(oldGoods.getGoodsId());
                log.info("删除供应商linkedmall-spu，spuId:{}", thirdPlatformGoodsDelDTO.getItemId());
            }
        } else {
            //spu上下架状态
            Integer spuAddedFlag = 0;
            long count = linkedMallGoodsInfos.stream().filter(v -> v.getAddedFlag() == 1).count();
            if (count > 0) {
                if (linkedMallGoodsInfos.size() == count) {
                    spuAddedFlag = 1;
                } else {
                    spuAddedFlag = 2;
                }
            }
            //更新linkedamll供应商-spu上下架状态
            Goods linkedMallGoods = goodsRepository.findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag.NO, goodsSource.toValue(), thirdPlatformSpuId);
            if (linkedMallGoods != null && !spuAddedFlag.equals(linkedMallGoods.getAddedFlag())) {
                linkedMallGoods.setAddedFlag(spuAddedFlag);
                linkedMallGoods.setAddedTime(LocalDateTime.now());
                linkedMallGoods.setUpdateTime(LocalDateTime.now());
                goodsRepository.save(linkedMallGoods);
            }
        }
//删除商品库linkemall——spu
        List<StandardSku> linkedMallStandardSkus = standardSkuRepository.findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag.NO, goodsSource.toValue(), thirdPlatformSpuId);
        if (linkedMallStandardSkus == null || linkedMallStandardSkus.size() < 1) {
            StandardGoods oldStandardGoods = standardGoodsRepository.findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag.NO, goodsSource.toValue(), thirdPlatformSpuId);
            if (oldStandardGoods != null) {
                oldStandardGoods.setDelFlag(DeleteFlag.YES);
                standardGoodsRepository.save(oldStandardGoods);
                standardSpecRepository.deleteByGoodsId(oldStandardGoods.getGoodsId());
                standardSpecDetailRepository.deleteByGoodsId(oldStandardGoods.getGoodsId());
                log.info("删除商品库linkedmall-spu，spuId:{}", thirdPlatformGoodsDelDTO.getItemId());
                delStandardIds.add(oldStandardGoods.getGoodsId());
            }
        } else {
            //spu上下架状态
            Integer spuAddedFlag = 0;
            long count = linkedMallStandardSkus.stream().filter(v -> Integer.valueOf(1).equals(v.getAddedFlag())).count();
            if (count > 0) {
                if (linkedMallStandardSkus.size() == count) {
                    spuAddedFlag = 1;
                } else {
                    spuAddedFlag = 2;
                }
            }
            //更新商品库linkedamll-spu上下架状态
            StandardGoods linkedMallStandardGoods = standardGoodsRepository.findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag.NO, goodsSource.toValue(), thirdPlatformSpuId);
            if (linkedMallStandardGoods != null && !spuAddedFlag.equals(linkedMallStandardGoods.getAddedFlag())) {
                linkedMallStandardGoods.setAddedFlag(spuAddedFlag);
                linkedMallStandardGoods.setUpdateTime(LocalDateTime.now());
                standardGoodsRepository.save(linkedMallStandardGoods);
                standardIds.add(linkedMallStandardGoods.getGoodsId());
            }
        }
        //重新计算商家导入的linkedmall-spu可售状态
        List<Goods> storeLinkeMallGoods =
                goodsService.findAll(GoodsQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                        .goodsSource(GoodsSource.SELLER.toValue())
                        .thirdPlatformType(thirdPlatformType)
                        .thirdPlatformSpuId(thirdPlatformSpuId).build());
        if (storeLinkeMallGoods != null && storeLinkeMallGoods.size() > 0) {
            List<GoodsInfo> storeGoodsInfos =
                    goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().delFlag(DeleteFlag.NO.toValue()).goodsId(storeLinkeMallGoods.get(0).getGoodsId()).build().getWhereCriteria());
            if (CollectionUtils.isNotEmpty(storeGoodsInfos)) {
                Integer newVendibility = storeGoodsInfos.stream().anyMatch(v -> Integer.valueOf(1).equals(v.getVendibility())) ? 1 : 0;
                if (!newVendibility.equals(storeLinkeMallGoods.get(0).getVendibility())) {
                    for (Goods storeLinkeMallGood : storeLinkeMallGoods) {
                        storeLinkeMallGood.setVendibility(newVendibility);
                        storeLinkeMallGood.setUpdateTime(LocalDateTime.now());
                    }
                    goodsRepository.saveAll(storeLinkeMallGoods);
                }
            }
        }
        ThirdPlatformGoodsDelResponse response = new ThirdPlatformGoodsDelResponse();
        response.setStandardIds(standardIds);
        response.setDelStandardIds(delStandardIds);
        response.setGoodsInfoIds(esGoodsInfoIds);
        return response;
    }

    /**
     * 填充LM商品sku的库存
     *
     * @param goodsInfoList 商品SKu列表
     */
    public void fillLmStock(List<GoodsInfoVO> goodsInfoList) {
        // 如果是linkedmall商品，实时查库存
        List<Long> itemIds = goodsInfoList.stream()
                .filter(v -> Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(v.getGoodsSource()) && StringUtils.isNotBlank(v.getThirdPlatformSpuId()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(itemIds)) {
            return;
        }
        List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        if (CollectionUtils.isNotEmpty(stocks)) {
            for (GoodsInfoVO goodsInfoVO : goodsInfoList) {
                for (LinkedMallStockVO spuStock : stocks) {
                    Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                            .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfoVO.getThirdPlatformSpuId())
                                    && String.valueOf(v.getSkuId()).equals(goodsInfoVO.getThirdPlatformSkuId()))
                            .findFirst();
                    stock.ifPresent(sku -> {
                        goodsInfoVO.setStock(sku.getStock());
                        if (!(GoodsStatus.INVALID == goodsInfoVO.getGoodsStatus())) {
                            goodsInfoVO.setGoodsStatus(goodsInfoVO.getStock() > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                        }
                    });
                }
            }
        }
    }
}
