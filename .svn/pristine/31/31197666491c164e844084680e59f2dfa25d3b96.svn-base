package com.wanmi.sbc.setting.openapisetting.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.setting.api.request.openapisetting.OpenApiSettingQueryRequest;
import com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 开放平台api设置动态查询条件构建器
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
public class OpenApiSettingWhereCriteriaBuilder {
    public static Specification<OpenApiSetting> build(OpenApiSettingQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 店铺名称
            if (StringUtils.isNotEmpty(queryRequest.getStoreName())) {
                predicates.add(
                        cbuild.like(
                                root.get("storeName"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getStoreName()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商家名称
            if (StringUtils.isNotEmpty(queryRequest.getSupplierName())) {
                predicates.add(
                        cbuild.like(
                                root.get("supplierName"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getSupplierName()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商家类型：0:供应商；1:商家；
            if (queryRequest.getStoreType() != null) {
                predicates.add(cbuild.equal(root.get("storeType"), queryRequest.getStoreType()));
            }

            // 大于或等于 搜索条件:签约开始日期开始
            if (queryRequest.getContractStartDateBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("contractStartDate"),
                                queryRequest.getContractStartDateBegin()));
            }
            // 小于或等于 搜索条件:签约开始日期截止
            if (queryRequest.getContractStartDateEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("contractStartDate"),
                                queryRequest.getContractStartDateEnd()));
            }

            // 大于或等于 搜索条件:签约结束日期开始
            if (queryRequest.getContractEndDateBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("contractEndDate"),
                                queryRequest.getContractEndDateBegin()));
            }
            // 小于或等于 搜索条件:签约结束日期截止
            if (queryRequest.getContractEndDateEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("contractEndDate"), queryRequest.getContractEndDateEnd()));
            }

            // 审核状态 0、待审核 1、已审核 2、审核未通过
            if (queryRequest.getAuditState() != null) {
                predicates.add(cbuild.equal(root.get("auditState"), queryRequest.getAuditState()));
            }

            // 模糊查询 - 审核未通过原因
            if (StringUtils.isNotEmpty(queryRequest.getAuditReason())) {
                predicates.add(
                        cbuild.like(
                                root.get("auditReason"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getAuditReason()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 禁用状态:0:启用；1:禁用
            if (queryRequest.getDisableState() != null) {
                predicates.add(
                        cbuild.equal(root.get("disableState"), queryRequest.getDisableState()));
            }

            // 模糊查询 - app_key
            if (StringUtils.isNotEmpty(queryRequest.getAppKey())) {
                predicates.add(
                        cbuild.like(
                                root.get("appKey"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getAppKey()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - app_secret
            if (StringUtils.isNotEmpty(queryRequest.getAppSecret())) {
                predicates.add(
                        cbuild.like(
                                root.get("appSecret"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getAppSecret()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 限流值
            if (queryRequest.getLimitingNum() != null) {
                predicates.add(
                        cbuild.equal(root.get("limitingNum"), queryRequest.getLimitingNum()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("createPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCreatePerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:修改时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:修改时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 修改人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("updatePerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getUpdatePerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }
            // 是否删除 0 否  1 是
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
