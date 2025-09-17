package com.wanmi.sbc.customer.payingmemberprice.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceQueryRequest;
import com.wanmi.sbc.customer.payingmemberprice.model.root.PayingMemberPrice;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>付费设置表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
public class PayingMemberPriceWhereCriteriaBuilder {
    public static Specification<PayingMemberPrice> build(PayingMemberPriceQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-付费设置idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPriceIdList())) {
                predicates.add(root.get("priceId").in(queryRequest.getPriceIdList()));
            }

            // 付费设置id
            if (queryRequest.getPriceId() != null) {
                predicates.add(cbuild.equal(root.get("priceId"), queryRequest.getPriceId()));
            }

            // 付费设置id
            if (queryRequest.getLevelId() != null) {
                predicates.add(cbuild.equal(root.get("levelId"), queryRequest.getLevelId()));
            }

            // 付费设置数量 ，例如3个月
            if (queryRequest.getPriceNum() != null) {
                predicates.add(cbuild.equal(root.get("priceNum"), queryRequest.getPriceNum()));
            }

            // 付费设置总金额，例如上述3个月90元
            if (queryRequest.getPriceTotal() != null) {
                predicates.add(cbuild.equal(root.get("priceTotal"), queryRequest.getPriceTotal()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - createPerson
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
