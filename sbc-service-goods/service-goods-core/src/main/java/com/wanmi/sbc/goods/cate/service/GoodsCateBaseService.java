package com.wanmi.sbc.goods.cate.service;

import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.GoodsCateQueryRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsCateBaseService
 * @description
 * @date 2022/10/31 16:46
 **/
@Service
public class GoodsCateBaseService {

    @Autowired
    private GoodsCateRepository goodsCateRepository;
    /**
     * 获取所有子分类
     *
     * @param goodCateId 分类
     * @return 所有子分类
     */
    public List<Long> getChlidCateId(Long goodCateId) {
        List<Long> cateIds = new ArrayList<>();
        GoodsCate cate = goodsCateRepository.findById(goodCateId).orElse(null);
        if (Objects.nonNull(cate)) {
            GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
            cateRequest.setLikeCatePath(ObjectUtils.toString(cate.getCatePath()).concat(String.valueOf(cate.getCateId())).concat("|"));
            List<GoodsCate> t_cateList = goodsCateRepository.findAll(cateRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(t_cateList)) {
                cateIds.addAll(t_cateList.stream().map(GoodsCate::getCateId).collect(Collectors.toList()));
            }
        }
        return cateIds;
    }
}
