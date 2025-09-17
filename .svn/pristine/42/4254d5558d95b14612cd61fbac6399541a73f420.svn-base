package com.wanmi.sbc.customer.ledgersupplier.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierQueryRequest;
import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>商户分账绑定数据动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
public class LedgerSupplierWhereCriteriaBuilder {
    public static Specification<LedgerSupplier> build(LedgerSupplierQueryRequest queryRequest) {
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

            // 模糊查询 - 清分账户id
            if (StringUtils.isNotEmpty(queryRequest.getLedgerAccountId())) {
                predicates.add(cbuild.like(root.get("ledgerAccountId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLedgerAccountId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商家id
            if (queryRequest.getCompanyInfoId() != null) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), queryRequest.getCompanyInfoId()));
            }

            // 模糊查询 - 商家名称
            if (StringUtils.isNotEmpty(queryRequest.getCompanyName())) {
                predicates.add(cbuild.like(root.get("companyName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCompanyName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商家编号
            if (StringUtils.isNotEmpty(queryRequest.getCompanyCode())) {
                predicates.add(cbuild.like(root.get("companyCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCompanyCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 平台绑定状态 0、未绑定 1、已绑定
            if (queryRequest.getPlatBindState() != null) {
                predicates.add(cbuild.equal(root.get("platBindState"), queryRequest.getPlatBindState()));
            }

            // 供应商绑定数
            if (queryRequest.getProviderNum() != null) {
                predicates.add(cbuild.equal(root.get("providerNum"), queryRequest.getProviderNum()));
            }

            // 分销员绑定数
            if (queryRequest.getDistributionNum() != null) {
                predicates.add(cbuild.equal(root.get("distributionNum"), queryRequest.getDistributionNum()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
