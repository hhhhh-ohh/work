package com.wanmi.sbc.empower.customerservice.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingQueryRequest;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>在线客服配置动态查询条件构建器</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
public class CustomerServiceSettingWhereCriteriaBuilder {
    public static Specification<CustomerServiceSetting> build(CustomerServiceSettingQueryRequest queryRequest) {
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

            // 店铺ID
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 推送平台类型：0：QQ客服 1：阿里云客服
            if (queryRequest.getPlatformType() != null) {
                predicates.add(cbuild.equal(root.get("platformType"), queryRequest.getPlatformType()));
            }

            // 在线客服是否启用 0 不启用， 1 启用
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
            }

            // 模糊查询 - 客服标题
            if (StringUtils.isNotEmpty(queryRequest.getServiceTitle())) {
                predicates.add(cbuild.like(root.get("serviceTitle"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getServiceTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 生效终端pc 0 不生效 1 生效
            if (queryRequest.getEffectivePc() != null) {
                predicates.add(cbuild.equal(root.get("effectivePc"), queryRequest.getEffectivePc()));
            }

            // 生效终端App 0 不生效 1 生效
            if (queryRequest.getEffectiveApp() != null) {
                predicates.add(cbuild.equal(root.get("effectiveApp"), queryRequest.getEffectiveApp()));
            }

            // 生效终端移动版 0 不生效 1 生效
            if (queryRequest.getEffectiveMobile() != null) {
                predicates.add(cbuild.equal(root.get("effectiveMobile"), queryRequest.getEffectiveMobile()));
            }

            // 模糊查询 - 客服key
            if (StringUtils.isNotEmpty(queryRequest.getServiceKey())) {
                predicates.add(cbuild.like(root.get("serviceKey"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getServiceKey()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 客服链接
            if (StringUtils.isNotEmpty(queryRequest.getServiceUrl())) {
                predicates.add(cbuild.like(root.get("serviceUrl"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getServiceUrl()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
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

            // 模糊查询 - 创建人
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

            // 模糊查询 - 更新人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
