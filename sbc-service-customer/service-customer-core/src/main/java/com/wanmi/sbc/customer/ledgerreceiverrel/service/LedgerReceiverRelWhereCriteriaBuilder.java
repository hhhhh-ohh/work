package com.wanmi.sbc.customer.ledgerreceiverrel.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelQueryRequest;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>分账绑定关系动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
public class LedgerReceiverRelWhereCriteriaBuilder {
    public static Specification<LedgerReceiverRel> build(LedgerReceiverRelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 批量查询-supplierIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getSupplierIdList())) {
                predicates.add(root.get("supplierId").in(queryRequest.getSupplierIdList()));
            }

            // id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 商户分账数据id
            if (StringUtils.isNotEmpty(queryRequest.getLedgerSupplierId())) {
                predicates.add(cbuild.like(root.get("ledgerSupplierId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLedgerSupplierId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商户id
            if (queryRequest.getSupplierId() != null) {
                predicates.add(cbuild.equal(root.get("supplierId"), queryRequest.getSupplierId()));
            }

            // 接收方id
            if (StringUtils.isNotEmpty(queryRequest.getReceiverId())) {
                predicates.add(cbuild.equal(root.get("receiverId"), queryRequest.getReceiverId()));
            }

            // 模糊查询 - 接收方名称
            if (StringUtils.isNotEmpty(queryRequest.getReceiverName())) {
                predicates.add(cbuild.like(root.get("receiverName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getReceiverName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 接收方编码(供应商编码或分销员账号)
            if (StringUtils.isNotEmpty(queryRequest.getReceiverCode())) {
                predicates.add(cbuild.like(root.get("receiverCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getReceiverCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 接收方类型 0、平台 1、供应商 2、分销员
            if (queryRequest.getReceiverType() != null) {
                predicates.add(cbuild.equal(root.get("receiverType"), queryRequest.getReceiverType()));
            }

            // 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
            if (queryRequest.getAccountState() != null) {
                predicates.add(cbuild.equal(root.get("accountState"), queryRequest.getAccountState()));
            }

            // 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
            if (queryRequest.getBindState() != null) {
                predicates.add(cbuild.equal(root.get("bindState"), queryRequest.getBindState()));
            }

            // 大于或等于 搜索条件:绑定时间开始
            if (queryRequest.getBindTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("bindTime"),
                        queryRequest.getBindTimeBegin()));
            }
            // 小于或等于 搜索条件:绑定时间截止
            if (queryRequest.getBindTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("bindTime"),
                        queryRequest.getBindTimeEnd()));
            }

            // 模糊查询 - 外部绑定受理编号
            if (StringUtils.isNotEmpty(queryRequest.getApplyId())) {
                predicates.add(cbuild.like(root.get("applyId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getApplyId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识 0、未删除 1、已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
