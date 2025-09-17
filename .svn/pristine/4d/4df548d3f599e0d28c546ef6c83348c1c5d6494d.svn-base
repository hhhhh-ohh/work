package com.wanmi.sbc.setting.expresscompanythirdrel.service;

import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelQueryRequest;
import com.wanmi.sbc.setting.expresscompanythirdrel.root.ExpressCompanyThirdRel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description 平台和第三方代销平台物流公司映射条件构造器
 * @author malianfeng
 * @date 2022/4/26 20:16
 */
public class ExpressCompanyThirdRelWhereCriteriaBuilder {
    public static Specification<ExpressCompanyThirdRel> build(ExpressCompanyThirdRelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-自增主键ID列表
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 批量查询-平台物流ID列表
            if (CollectionUtils.isNotEmpty(queryRequest.getExpressCompanyIdList())) {
                predicates.add(root.get("expressCompanyId").in(queryRequest.getExpressCompanyIdList()));
            }

            // 批量查询-第三方物流ID列表
            if (CollectionUtils.isNotEmpty(queryRequest.getThirdExpressCompanyIdList())) {
                predicates.add(root.get("expressCompanyId").in(queryRequest.getThirdExpressCompanyIdList()));
            }

            // 自增主键ID
            if (queryRequest.getExpressCompanyId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 平台物流ID
            if (queryRequest.getExpressCompanyId() != null) {
                predicates.add(cbuild.equal(root.get("expressCompanyId"), queryRequest.getExpressCompanyId()));
            }

            // 第三方平台物流ID
            if (queryRequest.getThirdExpressCompanyId() != null) {
                predicates.add(cbuild.equal(root.get("thirdExpressCompanyId"), queryRequest.getThirdExpressCompanyId()));
            }

            // 所属第三方代销平台类型
            if (Objects.nonNull(queryRequest.getSellPlatformType())) {
                predicates.add(cbuild.equal(root.get("sellPlatformType"), queryRequest.getSellPlatformType()));
            }

            // 删除标志 默认0：未删除 1：删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[0]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
