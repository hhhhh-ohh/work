package com.wanmi.sbc.customer.goodsfootmark.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.goodsfootmark.model.root.GoodsFootmark;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>我的足迹动态查询条件构建器</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
public class GoodsFootmarkWhereCriteriaBuilder {
    public static Specification<GoodsFootmark> build(GoodsFootmarkQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-footmarkIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getFootmarkIdList())) {
                predicates.add(root.get("footmarkId").in(queryRequest.getFootmarkIdList()));
            }

            // footmarkId
            if (queryRequest.getFootmarkId() != null) {
                predicates.add(cbuild.equal(root.get("footmarkId"), queryRequest.getFootmarkId()));
            }

            // 精确查询 - customerId
            if (Objects.nonNull(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 模糊查询 - goodsInfoId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.like(root.get("goodsInfoId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            // 删除标识,0:未删除1:已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 浏览次数
            if (queryRequest.getViewTimes() != null) {
                predicates.add(cbuild.equal(root.get("viewTimes"), queryRequest.getViewTimes()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
