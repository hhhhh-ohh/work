package com.wanmi.sbc.customer.ledgerfile.service;

import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileQueryRequest;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>分账文件动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
public class LedgerFileWhereCriteriaBuilder {
    public static Specification<LedgerFile> build(LedgerFileQueryRequest queryRequest) {
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

            // 文件内容
            if (queryRequest.getContent() != null) {
                predicates.add(cbuild.equal(root.get("content"), queryRequest.getContent()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
