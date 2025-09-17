package com.wanmi.sbc.message.storenoticescope.service;

import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeQueryRequest;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>商家公告发送范围动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
public class StoreNoticeScopeWhereCriteriaBuilder {
    public static Specification<StoreNoticeScope> build(StoreNoticeScopeQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 公告id
            if (queryRequest.getNoticeId() != null) {
                predicates.add(cbuild.equal(root.get("noticeId"), queryRequest.getNoticeId()));
            }

            // 范围分类 1：商家 2：供应商
            if (queryRequest.getScopeCate() != null) {
                predicates.add(cbuild.equal(root.get("scopeCate"), queryRequest.getScopeCate()));
            }

            // 范围类型 1：自定义
            if (queryRequest.getScopeType() != null) {
                predicates.add(cbuild.equal(root.get("scopeType"), queryRequest.getScopeType()));
            }

            // 目标id
            if (queryRequest.getScopeId() != null) {
                predicates.add(cbuild.equal(root.get("scopeId"), queryRequest.getScopeId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
