package com.wanmi.sbc.marketing.bargainjoin.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.bargainjoin.model.root.BargainJoin;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>帮砍记录动态查询条件构建器</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
public class BargainJoinWhereCriteriaBuilder {
    public static Specification<BargainJoin> build(BargainJoinQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-bargainJoinIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getBargainJoinIdList())) {
                predicates.add(root.get("bargainJoinId").in(queryRequest.getBargainJoinIdList()));
            }

            // bargainJoinId
            if (queryRequest.getBargainJoinId() != null) {
                predicates.add(cbuild.equal(root.get("bargainJoinId"), queryRequest.getBargainJoinId()));
            }

            // 砍价记录id
            if (queryRequest.getBargainId() != null) {
                predicates.add(cbuild.equal(root.get("bargainId"), queryRequest.getBargainId()));
            }

            // 模糊查询 - 砍价商品id
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.like(root.get("goodsInfoId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 砍价的发起人
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 帮砍人id
            if (StringUtils.isNotEmpty(queryRequest.getJoinCustomerId())) {
                predicates.add(cbuild.like(root.get("joinCustomerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getJoinCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 帮砍金额
            if (queryRequest.getBargainAmount() != null) {
                predicates.add(cbuild.equal(root.get("bargainAmount"), queryRequest.getBargainAmount()));
            }

            // 大于或等于 搜索条件:createTime开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:createTime截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
