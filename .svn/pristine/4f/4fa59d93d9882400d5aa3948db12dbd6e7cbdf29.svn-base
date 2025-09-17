package com.wanmi.sbc.marketing.communitystockorder.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderQueryRequest;
import com.wanmi.sbc.marketing.communitystockorder.model.root.CommunityStockOrder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>社区团购备货单动态查询条件构建器</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
public class CommunityStockOrderWhereCriteriaBuilder {
    public static Specification<CommunityStockOrder> build(CommunityStockOrderQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 活动id
            if (StringUtils.isNotEmpty(queryRequest.getActivityId())) {
                predicates.add(cbuild.equal(root.get("activityId"), queryRequest.getActivityId()));
            }

            // 商品id
            if (queryRequest.getSkuId() != null) {
                predicates.add(cbuild.equal(root.get("skuId"), queryRequest.getSkuId()));
            }

            // 模糊查询 - 商品名称
            if (StringUtils.isNotEmpty(queryRequest.getGoodsName())) {
                predicates.add(cbuild.like(root.get("goodsName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 规格
            if (StringUtils.isNotEmpty(queryRequest.getSpecName())) {
                predicates.add(cbuild.like(root.get("specName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getSpecName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 购买数量
            if (queryRequest.getNum() != null) {
                predicates.add(cbuild.equal(root.get("num"), queryRequest.getNum()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
