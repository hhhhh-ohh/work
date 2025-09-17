package com.wanmi.sbc.goods.spec.service;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;

/**
 * @author: wanggang
 * @createDate: 2018/11/13 14:54
 * @version: 1.0
 */
@Service
public class GoodsInfoSpecDetailRelService {

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;


    /**
     * 根据spuid 和skuid查询
     * @param goodsId
     * @param goodsInfoId
     * @return
     */
    public List<GoodsInfoSpecDetailRel> findByGoodsIdAndGoodsInfoId(String goodsId, String goodsInfoId){
       return goodsInfoSpecDetailRelRepository.findByGoodsIdAndGoodsInfoId(goodsId, goodsInfoId);
    }

    /**
     * 根据多个SkuID查询
     * @param goodsInfoIds 多SkuID
     * @return
     */
    public List<GoodsInfoSpecDetailRel> findByGoodsInfoIds(List<String> goodsInfoIds){
        return goodsInfoSpecDetailRelRepository.findByGoodsInfoIds(goodsInfoIds);
    }

    /**
     * 根据多个SkuID返回格式化的规格
     *
     * @param goodsInfoIds 多SkuID
     * @return <skuId,specText>
     */
    public Map<String, String> textByGoodsInfoIds(List<String> goodsInfoIds) {
        return this.findByGoodsInfoIds(goodsInfoIds).stream().collect(
                Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId,
                        mapping(GoodsInfoSpecDetailRel::getDetailName, joining(" "))));
    }

    /**
     * 填充格式化的规格
     *
     * @param goodsInfoVOS 商品SKu列表
     */
    public void fillSpecDetail(List<GoodsInfoVO> goodsInfoVOS) {
        List<String> goodsInfoIds = goodsInfoVOS.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, String> specDetailMap = this.textByGoodsInfoIds(goodsInfoIds);
        goodsInfoVOS.forEach(i -> i.setSpecText(specDetailMap.get(i.getGoodsInfoId())));
        /*List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRelList = findByGoodsInfoIds(goodsInfoIds);
        List<Long> specDetailIds = goodsInfoSpecDetailRelList.stream()
                .map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList());
        List<GoodsSpecDetail> goodsSpecDetails = goodsSpecDetailRepository.findAllById(specDetailIds);
        Map<String, GoodsInfoSpecDetailRel> goodsInfoSpecDetailRelMap = goodsInfoSpecDetailRelList.stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, Function.identity()));
        Map<Long, GoodsSpecDetail> goodsSpecDetailMap = goodsSpecDetails.stream()
                .collect(Collectors.toMap(GoodsSpecDetail::getSpecDetailId, Function.identity()));
        goodsInfoVOS.forEach(i -> {
            GoodsInfoSpecDetailRel goodsInfoSpecDetailRel = goodsInfoSpecDetailRelMap.get(i.getGoodsInfoId());
            Long specDetailId = goodsInfoSpecDetailRel.getSpecDetailId();
            GoodsSpecDetail goodsSpecDetail = goodsSpecDetailMap.get(specDetailId);
            i.setSpecText(goodsSpecDetail.getDetailName());
        });*/
    }
}
