package com.wanmi.sbc.customer.ledgerreceiverrelrecord.service;

import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordQueryRequest;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root.LedgerReceiverRelRecord;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>分账绑定关系补偿记录动态查询条件构建器</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
public class LedgerReceiverRelRecordWhereCriteriaBuilder {
    public static Specification<LedgerReceiverRelRecord> build(LedgerReceiverRelRecordQueryRequest queryRequest) {
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

            // 清分账户id
            if (StringUtils.isNotEmpty(queryRequest.getAccountId())) {
                predicates.add(cbuild.equal(root.get("accountId"), queryRequest.getAccountId()));
            }

            // 账户类型 0、商户 1、接收方
            if (queryRequest.getBusinessType() != null) {
                predicates.add(cbuild.equal(root.get("businessType"), queryRequest.getBusinessType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
