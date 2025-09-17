package com.wanmi.sbc.goods.suppliercommissiongoods.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsQueryRequest;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description
 * @author  wur
 * @date: 2021/9/10 16:49
 **/
public class SupplierCommissionGoodsWhereCriteriaBuilder {

    public static Specification<SupplierCommissionGood> build(SupplierCommissionGoodsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 商户ID
            if (Objects.nonNull(queryRequest.getBaseStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getBaseStoreId()));
            }

            // 商品
            if (Objects.nonNull(queryRequest.getGoodsId())) {
                predicates.add(cbuild.equal(root.get("goodsId"), queryRequest.getGoodsId()));
            }

            // 供应商商品
            if (Objects.nonNull(queryRequest.getProviderGoodsId())) {
                predicates.add(cbuild.equal(root.get("providerGoodsId"), queryRequest.getProviderGoodsId()));
            }

            // 商品信息同步状态
            if (Objects.nonNull(queryRequest.getSynStatus())) {
                predicates.add(cbuild.equal(root.get("synStatus"), queryRequest.getSynStatus()));
            }

            // 商品信息更新标识
            if (Objects.nonNull(queryRequest.getUpdateFlag())) {
                predicates.add(cbuild.equal(root.get("updateFlag"), queryRequest.getUpdateFlag()));
            }

            // 商品信息同步状态
            if (Objects.nonNull(queryRequest.getDelFlag())) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            } else {
                predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
