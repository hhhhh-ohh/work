package com.wanmi.sbc.goods.cate.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.cate.model.root.ContractCate;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by sunkun on 2017/10/31.
 */
@Data
public class ContractCateQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 8663584629048270522L;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 商品分类标识
     */
    private Long cateId;

    /**
     * 商品分类标识列表
     */
    private List<Long> cateIds;

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<ContractCate> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<GoodsCate, ContractCate> contractCateJoin = root.join("goodsCate");
            if (Objects.nonNull(storeId)) {
                predicates.add(cbuild.equal(root.get("storeId"), storeId));
            }
            if (cateId != null && cateId != 0) {
                predicates.add(cbuild.equal(contractCateJoin.get("cateId"), cateId));
            }
            if (CollectionUtils.isNotEmpty(cateIds)) {
                predicates.add(contractCateJoin.get("cateId").in(cateIds));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        }

                ;
    }
}
