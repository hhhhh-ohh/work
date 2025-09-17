package com.wanmi.sbc.goods.pointsgoods.service;

import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsQueryRequest;
import com.wanmi.sbc.goods.pointsgoods.model.root.SimplePointsGoods;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>积分商品表动态查询条件构建器</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
public class SimplePointsGoodsWhereCriteriaBuilder {
    public static Specification<SimplePointsGoods> build(PointsGoodsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 批量查询-积分商品idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPointsGoodsIds())) {
                predicates.add(root.get("pointsGoodsId").in(queryRequest.getPointsGoodsIds()));
            }

            // 积分商品id
            if (StringUtils.isNotEmpty(queryRequest.getPointsGoodsId())) {
                predicates.add(cbuild.equal(root.get("pointsGoodsId"), queryRequest.getPointsGoodsId()));
            }

            // SpuId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.equal(root.get("goodsId"), queryRequest.getGoodsId()));
            }

            // 批量查询-SpuId
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsIds())) {
                predicates.add(root.get("goodsId").in(queryRequest.getGoodsIds()));
            }

            // SkuId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.equal(root.get("goodsInfoId"), queryRequest.getGoodsInfoId()));
            }

            // 批量查询-SkuId
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }

            // 删除标识,0: 未删除 1: 已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 是否启用 0：停用，1：启用
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
