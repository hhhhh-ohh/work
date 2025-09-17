package com.wanmi.ares.report.goods.service;

import com.wanmi.ares.report.goods.dao.GoodsStoreCateMapper;
import com.wanmi.ares.report.goods.model.request.GoodsQueryRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EDZ
 * @className O2oGoodsStoreCateQueryService
 * @description TODO
 * @date 2021/9/27 11:24
 **/
@Service
public class GoodsStoreCateQueryService {

    private static final String SPLIT_CHAR = "|";

    @Autowired
    private GoodsStoreCateMapper goodsStoreCateMapper;

    public List<Long>  queryStoreCateChild(GoodsQueryRequest request){
        //查询类目路径
        String catePath = goodsStoreCateMapper.queryGoodsStoreCatePath(Integer.valueOf(request.getId()));
        if (StringUtils.isEmpty(catePath)) {
            new ArrayList<>();
        }
        String oldCatePath = catePath.concat(String.valueOf(request.getId()).concat(SPLIT_CHAR));
        List<Long> cateIds = goodsStoreCateMapper.queryStoreCateChild(oldCatePath);
        if (CollectionUtils.isEmpty(cateIds)) {
            cateIds = new ArrayList<>();
        }
        cateIds.add(Long.valueOf(request.getId()));

        return cateIds;
    }
}