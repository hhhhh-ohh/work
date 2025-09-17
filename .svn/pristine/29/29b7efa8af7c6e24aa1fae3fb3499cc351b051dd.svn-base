package com.wanmi.sbc.empower.logisticssetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingQueryRequest;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>物流配置动态查询条件构建器</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
public class LogisticsSettingWhereCriteriaBuilder {
    public static Specification<LogisticsSetting> build(LogisticsSettingQueryRequest queryRequest) {
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

            // 配置类型 0: 快递100
            if (queryRequest.getLogisticsType() != null) {
                predicates.add(cbuild.equal(root.get("logisticsType"), queryRequest.getLogisticsType()));
            }

            // 模糊查询 - 用户申请授权key
            if (StringUtils.isNotEmpty(queryRequest.getCustomerKey())) {
                predicates.add(cbuild.like(root.get("customerKey"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerKey()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 授权秘钥key
            if (StringUtils.isNotEmpty(queryRequest.getDeliveryKey())) {
                predicates.add(cbuild.like(root.get("deliveryKey"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeliveryKey()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 实时查询是否开启 0: 否, 1: 是
            if (queryRequest.getRealTimeStatus() != null) {
                predicates.add(cbuild.equal(root.get("realTimeStatus"), queryRequest.getRealTimeStatus()));
            }

            // 是否开启订阅 0: 否, 1: 是
            if (queryRequest.getSubscribeStatus() != null) {
                predicates.add(cbuild.equal(root.get("subscribeStatus"), queryRequest.getSubscribeStatus()));
            }

            // 模糊查询 - 回调地址
            if (StringUtils.isNotEmpty(queryRequest.getCallbackUrl())) {
                predicates.add(cbuild.like(root.get("callbackUrl"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCallbackUrl()))
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
