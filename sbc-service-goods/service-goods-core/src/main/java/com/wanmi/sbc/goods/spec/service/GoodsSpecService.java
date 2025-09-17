package com.wanmi.sbc.goods.spec.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecQueryRequest;
import com.wanmi.sbc.goods.api.response.spec.GoodsDetailSpecInfoResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecExportVO;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.util.mapper.GoodsMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品规格接口服务
 * @author: daiyitian
 * @createDate: 2018/11/13 14:54
 * @version: 1.0
 */
@Service
public class GoodsSpecService {

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 根据商品id批量查询规格列表
     * @param goodsIds 商品id
     * @return 规格列表
     */
    public List<GoodsSpec> findByGoodsIds(List<String> goodsIds){
       return goodsSpecRepository.findByGoodsIds(goodsIds);
    }

    /**
     * 获取规格和规格值
     * @param goodsIds
     * @return
     */
    public Map<String, List<GoodsInfoSpecExportVO>> findGoodsInfoSpecForExport(List<String> goodsIds) {
        Map<String, List<GoodsInfoSpecExportVO>> map = new HashMap<>();
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelRepository.findByGoodsIds(goodsIds);
        if(CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelList)) {
            Map<Long, String> specMap = goodsSpecRepository.findByGoodsIds(goodsIds).stream().collect(Collectors.toMap(GoodsSpec::getSpecId, GoodsSpec::getSpecName));
            Map<Long, String> detailMap = goodsSpecDetailRepository.findByGoodsIds(goodsIds).stream().collect(Collectors.toMap(GoodsSpecDetail::getSpecDetailId, GoodsSpecDetail::getDetailName));
            map = goodsInfoSpecDetailRelList.stream().map(goodsInfoSpecDetailRel -> {
                GoodsInfoSpecExportVO exportVO = new GoodsInfoSpecExportVO();
                String specName = specMap.get(goodsInfoSpecDetailRel.getSpecId());
                String detailName = detailMap.get(goodsInfoSpecDetailRel.getSpecDetailId());
                exportVO.setGoodsInfoId(goodsInfoSpecDetailRel.getGoodsInfoId());
                exportVO.setSpecName(specName);
                exportVO.setSpecDetailName(detailName);
                return exportVO;
            }).collect(Collectors.groupingBy(GoodsInfoSpecExportVO::getGoodsInfoId));
        }
        return map;
    }

    public List<GoodsSpec> listSpecByNameAndGoodsIds(List<String> specNames, String goodsId){
        if (CollectionUtils.isEmpty(specNames)) {
            return goodsSpecRepository.findByGoodsId(goodsId);
        }
        return goodsSpecRepository.findByNameAndGoodsIds(specNames, goodsId);
    }

    public List<GoodsSpecDetail> listSpecDetailsByNameAndGoodsIds(List<String> detailNames, String goodsId){
        if (CollectionUtils.isEmpty(detailNames)) {
            return goodsSpecDetailRepository.findByGoodsId(goodsId);
        }
        return goodsSpecDetailRepository.findByNameAndGoodsIds(detailNames, goodsId);
    }

    /**
     * @description 解析处理规格文本，更新sku维度
     * @author daiyitian
     * @date 2021/4/15 15:19
     * @param goodsId 商品id
     * @param goodsInfoId 商品skuId
     * @param specTextList 规格值文本 如颜色:红色
     * @return void
     */
    @Transactional
    public void analysisSpecRelBySkuId(
            String goodsId, String goodsInfoId, List<String> specTextList) {
        List<GoodsSpec> specList = goodsSpecRepository.findByGoodsId(goodsId);
        // 验证规格与spu是否一致
        if (CollectionUtils.isEmpty(specList)) {
            if (CollectionUtils.isNotEmpty(specTextList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030185);
            }
            return;
        }
        if (CollectionUtils.isNotEmpty(specTextList)) {
            // 规格长度
            if (specTextList.size() != specList.size()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030185);
            }
            Map<String, Long> specNameMap =
                    specList.stream()
                            .collect(
                                    Collectors.toMap(GoodsSpec::getSpecName, GoodsSpec::getSpecId));
            List<String> newSpecNameList =
                    specTextList.stream()
                            .map(text -> text.split(":")[0])
                            .collect(Collectors.toList());
            // 规格名不一致
            if (!CollectionUtils.isEqualCollection(specNameMap.keySet(), newSpecNameList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030185);
            }

            List<GoodsInfoSpecDetailRel> skuSpecRelList =
                    goodsInfoSpecDetailRelRepository.findByGoodsId(goodsId);

            // 提取非自身的sku的规格值信息
            Map<String, List<String>> otherSkuRelMap =
                    skuSpecRelList.stream()
                            .filter(rel -> !rel.getGoodsInfoId().equals(goodsInfoId))
                            .collect(
                                    Collectors.groupingBy(
                                            GoodsInfoSpecDetailRel::getGoodsInfoId,
                                            Collectors.mapping(
                                                    GoodsInfoSpecDetailRel::getDetailName,
                                                    Collectors.toList())));
            List<String> newDetailsNameList =
                    specTextList.stream()
                            .map(text -> text.split(":")[1])
                            .collect(Collectors.toList());
            // 验证新规格与其他sku的规格值是否存在一致
            if (otherSkuRelMap.values().stream()
                    .anyMatch(
                            otherDetails ->
                                    CollectionUtils.isEqualCollection(
                                            otherDetails, newDetailsNameList))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030186);
            }

            // 组成 specId+""+规格值名称 用于组合比较，方便进行删除、新增
            Set<String> relDetail =
                    specTextList.stream()
                            .map(
                                    text -> {
                                        String[] textArr = text.split(":");
                                        return Objects.toString(specNameMap.get(textArr[0]))
                                                .concat(textArr[1]);
                                    })
                            .collect(Collectors.toSet());

            // 删除，sku关联规格值表
            skuSpecRelList.stream()
                    .filter(
                            rel ->
                                    rel.getGoodsInfoId().equals(goodsInfoId)
                                            && (!relDetail.contains(
                                                    Objects.toString(rel.getSpecId())
                                                            .concat(rel.getDetailName()))))
                    .forEach(
                            rel -> {
                                rel.setDelFlag(DeleteFlag.YES);
                                rel.setUpdateTime(LocalDateTime.now());
                            });

            // 新增，新规格值以及sku关联
            Map<String, Map<Long, Long>> specDetailMap =
                    goodsSpecDetailRepository.findByGoodsId(goodsId).stream()
                            .collect(
                                    Collectors.groupingBy(
                                            GoodsSpecDetail::getDetailName,
                                            Collectors.toMap(
                                                    GoodsSpecDetail::getSpecId,
                                                    GoodsSpecDetail::getSpecDetailId)));
            specTextList.forEach(
                    text -> {
                        String[] specArr = text.split(":");
                        String specName = specArr[0];
                        String specDetailName = specArr[1];
                        Long specId = specNameMap.get(specName);
                        // 新规格值，则新增
                        if ((!specDetailMap.containsKey(specDetailName))
                                || (!specDetailMap.get(specDetailName).containsKey(specId))) {
                            GoodsSpecDetail goodsSpecDetail =
                                    this.goodsSpecDetailRepository.save(
                                            this.wrapperSpecDetail(goodsId, specId, specDetailName));
                            this.goodsInfoSpecDetailRelRepository.save(
                                    this.wrapperSpecDetailRel(
                                            goodsId, goodsInfoId, goodsSpecDetail));
                        }
                    });
        }
    }

    /**
     * @description 封装转换规格值
     * @author  daiyitian
     * @date 2021/4/15 17:47
     * @param goodsId 商品id
     * @param specId 规格id
     * @param specDetailName 规格值名称
     * @return com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail
     **/
    private GoodsSpecDetail wrapperSpecDetail(String goodsId, Long specId, String specDetailName){
        GoodsSpecDetail detail = new GoodsSpecDetail();
        detail.setDetailName(specDetailName);
        detail.setGoodsId(goodsId);
        detail.setSpecId(specId);
        detail.setDelFlag(DeleteFlag.NO);
        detail.setCreateTime(LocalDateTime.now());
        detail.setUpdateTime(detail.getCreateTime());
        return detail;
    }

    /**
     * @description 封装转换规格值
     * @author  daiyitian
     * @date 2021/4/15 17:47
     * @param goodsId 商品id
     * @param goodsInfoId 商品skuId
     * @param detail 规格值实例
     * @return com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel sku规格值关联
     **/
    private GoodsInfoSpecDetailRel wrapperSpecDetailRel(String goodsId, String goodsInfoId, GoodsSpecDetail detail){
        GoodsInfoSpecDetailRel rel = new GoodsInfoSpecDetailRel();
        rel.setGoodsId(goodsId);
        rel.setGoodsInfoId(goodsInfoId);
        rel.setSpecId(detail.getSpecId());
        rel.setSpecDetailId(detail.getSpecDetailId());
        rel.setDetailName(detail.getDetailName());
        rel.setCreateTime(LocalDateTime.now());
        rel.setUpdateTime(rel.getCreateTime());
        rel.setDelFlag(DeleteFlag.NO);
        return rel;
    }

    /**
     *
     * 根据goodsId查询对应的规格信息，包括：规格、规格值、规格值对应的skuid
     * @description
     * @author  zhanggaolei
     * @date 2021/9/9 10:10 上午
     * @param request
     * @return com.wanmi.sbc.goods.api.response.spec.GoodsDetailSpecInfoResponse
     **/
    public GoodsDetailSpecInfoResponse goodsDetailSpecInfo(GoodsSpecQueryRequest request){
        List<GoodsSpec> specs = this.goodsSpecRepository.findByGoodsId(request.getGoodsId());
        List<GoodsSpecDetail> specDetails = this.goodsSpecDetailRepository.findByGoodsId(request.getGoodsId());
        List<GoodsInfoSpecDetailRel> specDetailRels = this.goodsInfoSpecDetailRelRepository.findByGoodsId(request.getGoodsId());
        if(CollectionUtils.isNotEmpty(specDetailRels)){
            List<String> goodsInfoIds = specDetailRels.stream().map(GoodsInfoSpecDetailRel::getGoodsInfoId).distinct().collect(Collectors.toList());
            List<String> retIds = goodsInfoService.checkGoodsInfoValidByGoodsInfoId(goodsInfoIds, request.getAddress(), request.getFlashStockFlag());
            if(CollectionUtils.isNotEmpty(retIds)){
                specDetailRels = specDetailRels.stream().filter(t -> retIds.contains(t.getGoodsInfoId())).collect(Collectors.toList());
            }else{
                specDetailRels = null;
            }
        }
        GoodsDetailSpecInfoResponse response = new GoodsDetailSpecInfoResponse();
        response.setGoodsSpecList(goodsMapper.goodsSpecsToGoodsSpecSimpleVOs(specs));
        response.setGoodsSpecDetailList(goodsMapper.goodsSpecDetailsToGoodsSpecDetailSimpleVOs(specDetails));
        response.setGoodsInfoSpecDetailRelList(goodsMapper.goodsInfoSpecDetailRelsToGoodsInfoSpecDetailRelSimpleVOs(specDetailRels));
        return response;
    }


}
