package com.wanmi.sbc.goods.goodscommissionpriceconfig.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;

import jakarta.persistence.criteria.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description
 * @author  wur
 * @date: 2021/9/10 16:49
 **/
public class CommissionPriceConfigWhereCriteriaBuilder {

    public static Specification<GoodsCommissionPriceConfig> build(GoodsCommissionPriceConfigQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 商户ID
            if (Objects.nonNull(queryRequest.getBaseStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getBaseStoreId()));
            }

            // 目标类型
            if (Objects.nonNull(queryRequest.getTargetType())) {
                predicates.add(cbuild.equal(root.get("targetType"), queryRequest.getTargetType()));
            }

            // 目标类型唯一标识
            if (Objects.nonNull(queryRequest.getTargetId())) {
                predicates.add(cbuild.equal(root.get("targetId"), queryRequest.getTargetId()));
            }

            // 目标类型唯一标识集合
            if (CollectionUtils.isNotEmpty(queryRequest.getTargetIdList())) {
                predicates.add(root.get("targetId").in(queryRequest.getTargetIdList()));
            }

            // 启用状态
            if (Objects.nonNull(queryRequest.getEnableStatus())) {
                predicates.add(cbuild.equal(root.get("enableStatus"), queryRequest.getEnableStatus()));
            }

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
