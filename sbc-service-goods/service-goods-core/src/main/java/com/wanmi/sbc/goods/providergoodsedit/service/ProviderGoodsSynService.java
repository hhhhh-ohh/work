package com.wanmi.sbc.goods.providergoodsedit.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.suppliercommissiongoods.repository.SupplierCommissionGoodRepository;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
*
 * @description 商品代销设置服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Slf4j
@Service
public class ProviderGoodsSynService {

    @Autowired private GoodsRepository goodsRepository;

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private GoodsSpecRepository goodsSpecRepository;

    @Autowired private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired private GoodsImageRepository goodsImageRepository;

    @Autowired private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired private SupplierCommissionGoodRepository supplierCommissionGoodRepository;

    @Autowired private RedisUtil redisService;

    @Autowired private WechatSkuRepository wechatSkuRepository;

    @Autowired private MqSendProvider mqSendProvider;

    /**
     * @description   只同步供应商商品的基础信息
     * @author  wur
     * @date: 2021/9/23 13:45
     * @param provideGoods  供应商商品信息
     * @param goods     商家商品信息
     * @param provideGoodsInfoList  供应商商品信息
     * @param goodsInfoList     商家商品信息
     * @return
     **/
    @Transactional
    public void synGoodsInfo(GoodsSaveDTO provideGoods, GoodsSaveDTO goods, List<GoodsInfoSaveDTO> provideGoodsInfoList, List<GoodsInfoSaveDTO> goodsInfoList, boolean storeHasBrand) {
        log.info("供应商商品信息同步-- provideGoods={}", JSONObject.toJSONString(provideGoods));
        // 处理goods
        goods.setGoodsName(provideGoods.getGoodsName());
        //如果商家签约了品牌则更新 未签约则保留之前数据
        if (storeHasBrand) {
            goods.setBrandId(provideGoods.getBrandId());
        }
        goods.setGoodsSubtitle(provideGoods.getGoodsSubtitle());
        goods.setGoodsUnit(provideGoods.getGoodsUnit());
//        goods.setSaleType(provideGoods.getSaleType());
        goods.setGoodsImg(provideGoods.getGoodsImg());
        goods.setLabelIdStr(provideGoods.getLabelIdStr());
        goods.setGoodsVideo(provideGoods.getGoodsVideo());
        goods.setGoodsDetail(provideGoods.getGoodsDetail());
        goods.setThirdCateId(provideGoods.getThirdCateId());
        goods.setMoreSpecFlag(provideGoods.getMoreSpecFlag());
        goods.setStock(provideGoods.getStock());
        goods.setFreightTempId(provideGoods.getFreightTempId());
        goodsRepository.save(KsBeanUtil.copyPropertiesThird(goods, Goods.class));

        if (Objects.equals(Constants.ZERO,goods.getMoreSpecFlag())){
            goodsSpecRepository.deleteByGoodsId(goods.getGoodsId());
            goodsSpecDetailRepository.deleteByGoodsId(goods.getGoodsId());
        }

        //处理SKU
        if (CollectionUtils.isNotEmpty(provideGoodsInfoList) && CollectionUtils.isNotEmpty(goodsInfoList)) {
            Map<String, GoodsInfoSaveDTO> provideGoodsMap = provideGoodsInfoList.stream().collect(Collectors.toMap(GoodsInfoSaveDTO::getGoodsInfoId, g->g));
            goodsInfoList.forEach(goodsInfo -> {
                GoodsInfoSaveDTO provideGoodsInfo = provideGoodsMap.get(goodsInfo.getProviderGoodsInfoId());
                if(Objects.nonNull(provideGoodsInfo)) {
                    goodsInfo.setGoodsInfoBarcode(provideGoodsInfo.getGoodsInfoBarcode());
                    goodsInfo.setGoodsInfoImg(provideGoodsInfo.getGoodsInfoImg());
                    goodsInfo.setGoodsInfoName(provideGoods.getGoodsName());
                    goodsInfo.setGoodsCubage(provideGoodsInfo.getGoodsCubage());
                    goodsInfo.setThirdCateId(provideGoodsInfo.getThirdCateId());
                    goodsInfo.setGoodsWeight(provideGoodsInfo.getGoodsWeight());
                    goodsInfo.setStock(provideGoodsInfo.getStock());
                }
            });
            goodsInfoRepository.saveAll(KsBeanUtil.convertList(goodsInfoList, GoodsInfo.class));

            Map<String, String> mappingSku = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoSaveDTO::getProviderGoodsInfoId, GoodsInfoSaveDTO::getGoodsInfoId));
            Map<Long, Long> mappingSpec = new HashMap<>();
            Map<Long, Long> mappingDetail = new HashMap<>();
            //处理规格
            List<GoodsSpec> specs = goodsSpecRepository.findAllByGoodsIdAndAndDelFlag(provideGoods.getGoodsId(), DeleteFlag.NO);
            goodsSpecRepository.deleteByGoodsId(goods.getGoodsId());
            if (CollectionUtils.isNotEmpty(specs)) {
                specs.forEach(standardSpec -> {
                    GoodsSpec spec = new GoodsSpec();
                    BeanUtils.copyProperties(standardSpec, spec);
                    spec.setSpecId(null);
                    spec.setGoodsId(goods.getGoodsId());
                    mappingSpec.put(standardSpec.getSpecId(), goodsSpecRepository.save(spec).getSpecId());
                });
            }

            //处理规格值
            List<GoodsSpecDetail> details = goodsSpecDetailRepository.findAllByGoodsIdAAndDelFlag(provideGoods.getGoodsId(), DeleteFlag.NO);
            goodsSpecDetailRepository.deleteByGoodsId(goods.getGoodsId());
            if (CollectionUtils.isNotEmpty(details)) {
                details.forEach(specDetail -> {
                    GoodsSpecDetail detail = new GoodsSpecDetail();
                    BeanUtils.copyProperties(specDetail, detail);
                    detail.setSpecDetailId(null);
                    detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                    detail.setGoodsId(goods.getGoodsId());
                    mappingDetail.put(specDetail.getSpecDetailId(), goodsSpecDetailRepository.save(detail).getSpecDetailId());
                });
            }

            //处理规格值与Sku的关系
            List<GoodsInfoSpecDetailRel> rels = goodsInfoSpecDetailRelRepository.findByGoodsId(provideGoods.getGoodsId());
            goodsInfoSpecDetailRelRepository.deleteByGoodsId(goods.getGoodsId());
            if (CollectionUtils.isNotEmpty(rels)) {
                rels.forEach(standardRel -> {
                    if (mappingSku.containsKey(standardRel.getGoodsInfoId())) {
                        GoodsInfoSpecDetailRel rel = new GoodsInfoSpecDetailRel();
                        BeanUtils.copyProperties(standardRel, rel);
                        rel.setSpecDetailRelId(null);
                        rel.setSpecId(mappingSpec.get(standardRel.getSpecId()));
                        rel.setSpecDetailId(mappingDetail.get(standardRel.getSpecDetailId()));
                        rel.setGoodsInfoId(mappingSku.get(standardRel.getGoodsInfoId()));
                        rel.setGoodsId(goods.getGoodsId());
                        goodsInfoSpecDetailRelRepository.save(rel);
                    }
                });
            }
        }

        //处理图片
        List<GoodsImage> imageList = goodsImageRepository.findByGoodsId(provideGoods.getGoodsId());
        goodsImageRepository.deleteByGoodsId(goods.getGoodsId());
        if (CollectionUtils.isNotEmpty(imageList)) {
            imageList.forEach(standardImage -> {
                GoodsImage image = new GoodsImage();
                BeanUtils.copyProperties(standardImage, image);
                image.setImageId(null);
                image.setGoodsId(goods.getGoodsId());
                goodsImageRepository.save(image);
            });
        }

        //处理商品属性
        List<GoodsPropertyDetailRel> propDetailRelList =
                goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(
                        provideGoods.getGoodsId(), DeleteFlag.NO, GoodsPropertyType.GOODS);
        goodsPropertyDetailRelRepository.deleteByGoodsIdAndGoodsType(goods.getGoodsId(), GoodsPropertyType.GOODS);
        if (CollectionUtils.isNotEmpty(propDetailRelList)) {
            propDetailRelList.forEach(goodsPropDetailRel -> {
                GoodsPropertyDetailRel newGoodsRel = new GoodsPropertyDetailRel();
                KsBeanUtil.copyProperties(goodsPropDetailRel, newGoodsRel);
                newGoodsRel.setDetailRelId(null);
                newGoodsRel.setGoodsType(GoodsPropertyType.GOODS);
                newGoodsRel.setGoodsId(goods.getGoodsId());
                goodsPropertyDetailRelRepository.save(newGoodsRel);
            });
        }

        //更新redis商品基本数据
        String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + (goods.getGoodsId()));
        if (StringUtils.isNotBlank(goodsDetailInfo)) {
            redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + (goods.getGoodsId()));
        }

        //更新商品信息已同步
        supplierCommissionGoodRepository.updateSynStatus(provideGoods.getGoodsId(), goods.getStoreId(), DefaultFlag.YES);

        //
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
            GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
            sendRequest.setGoodsInfoIds(goodsInfoList.stream().map(GoodsInfoSaveDTO :: getGoodsInfoId).collect(Collectors.toList()));
            sendRequest.setFlag(GoodsEditFlag.INFO);
            sendRequest.setStoreId(goods.getStoreId());
            mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
            mqSendProvider.send(mqSendDTO);
        }
        log.info("供应商商品信息同步完成-- provideGoods={}", JSONObject.toJSONString(provideGoods));
    }

    /**
     * 更新运费模板
     * @param freightTempId
     * @param goodsIds
     */
    @Transactional
    public void updateFreightTemp(Long freightTempId, List<String> goodsIds) {
        goodsRepository.updateFreightTempIdByGoodsIds(freightTempId, goodsIds);
    }

    /**
     * 跟新视频号商品图片
     * @param goodsInfoId
     * @param img
     */
    @Transactional
    public void updateWechatImg(String goodsInfoId, String img) {
        wechatSkuRepository.updateImg(goodsInfoId, img);
    }

}
