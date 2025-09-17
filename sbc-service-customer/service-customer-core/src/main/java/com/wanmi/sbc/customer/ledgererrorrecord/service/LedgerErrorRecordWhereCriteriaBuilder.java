package com.wanmi.sbc.customer.ledgererrorrecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.ledgererrorrecord.LedgerErrorRecordQueryRequest;
import com.wanmi.sbc.customer.ledgererrorrecord.model.root.LedgerErrorRecord;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>分账接口错误记录动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-07-09 12:34:25
 */
public class LedgerErrorRecordWhereCriteriaBuilder {
    public static Specification<LedgerErrorRecord> build(LedgerErrorRecordQueryRequest queryRequest) {
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

            // 业务类型 0、创建账户
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 模糊查询 - 业务id
            if (StringUtils.isNotEmpty(queryRequest.getBusiness())) {
                predicates.add(cbuild.like(root.get("business"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBusiness()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 重试次数
            if (queryRequest.getRetryCount() != null) {
                predicates.add(cbuild.lt(root.get("retryCount"), queryRequest.getRetryCount()));
            }

            // 处理状态 0、待处理 1、处理中 2、处理成功 3、处理失败
            if (queryRequest.getState() != null) {
                predicates.add(cbuild.equal(root.get("state"), queryRequest.getState()));
            }

            //批量处理状态 0、待处理 1、处理中 2、处理成功 3、处理失败
            if (CollectionUtils.isNotEmpty(queryRequest.getStateList())) {
                predicates.add(root.get("state").in(queryRequest.getStateList()));
            }

            cquery.orderBy(cbuild.asc(root.get("createTime")));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
