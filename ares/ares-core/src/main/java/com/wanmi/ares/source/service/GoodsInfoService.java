package com.wanmi.ares.source.service;

import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.goods.dao.SkuMapper;
import com.wanmi.ares.request.GoodsInfoQueryRequest;
import com.wanmi.ares.source.model.root.GoodsInfo;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品sku基础信息service
 * Created by sunkun on 2017/9/22.
 */
@Slf4j
@Service
public class GoodsInfoService {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Autowired
//    private BasicDataElasticService basicDataElasticService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;




    public List<GoodsInfo> queryGoodsInfo(GoodsInfoQueryRequest request){
        return this.skuMapper.queryGoodsInfo(request);
    }

    public List<GoodsInfo> queryGoodsInfoDetail(GoodsInfoQueryRequest request){
        List<GoodsInfo> list = queryGoodsInfo(request);
        if(list != null && !list.isEmpty()){
            List<String> ids = list.stream().map(GoodsInfo::getId).collect(Collectors.toList());
            List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels = this.goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(ids,0);
            Map<String,String> detailNameMap = new HashMap<>();
            if(goodsInfoSpecDetailRels!=null && !goodsInfoSpecDetailRels.isEmpty()){
                detailNameMap = goodsInfoSpecDetailRels.stream().collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId,GoodsInfoSpecDetailRel::getDetailName));
                for(GoodsInfo goodsInfo : list){
                    goodsInfo.setDetailName(detailNameMap.get(goodsInfo.getId()));
                }
            }
        }
        return list;
    }

    public List<GoodsInfo> queryGoodsInfoDetail(List<GoodsInfo> list){
        List<String> ids = list.stream().map(GoodsInfo::getId).collect(Collectors.toList());
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels = this.goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(ids,0);
        Map<String,String> detailNameMap = new HashMap<>();
        if(goodsInfoSpecDetailRels!=null && !goodsInfoSpecDetailRels.isEmpty()){
            detailNameMap = goodsInfoSpecDetailRels.stream().collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId,GoodsInfoSpecDetailRel::getDetailName));
            for(GoodsInfo goodsInfo : list){
                goodsInfo.setDetailName(detailNameMap.get(goodsInfo.getId()));
            }
        }
        return list;
    }

}
