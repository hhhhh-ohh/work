package com.wanmi.sbc.order.payingmemberrecordtemp.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempQueryRequest;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>付费记录临时表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
public class PayingMemberRecordTempWhereCriteriaBuilder {
    public static Specification<PayingMemberRecordTemp> build(PayingMemberRecordTempQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-记录idList
            if (CollectionUtils.isNotEmpty(queryRequest.getRecordIdList())) {
                predicates.add(root.get("recordId").in(queryRequest.getRecordIdList()));
            }

            // 记录id
            if (StringUtils.isNotEmpty(queryRequest.getRecordId())) {
                predicates.add(cbuild.equal(root.get("recordId"), queryRequest.getRecordId()));
            }

            // 付费会员等级id
            if (queryRequest.getLevelId() != null) {
                predicates.add(cbuild.equal(root.get("levelId"), queryRequest.getLevelId()));
            }

            // 模糊查询 - 付费会员等级名称
            if (StringUtils.isNotEmpty(queryRequest.getLevelName())) {
                predicates.add(cbuild.like(root.get("levelName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLevelName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员名称
            if (StringUtils.isNotEmpty(queryRequest.getCustomerName())) {
                predicates.add(cbuild.like(root.get("customerName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 支付数量
            if (queryRequest.getPayNum() != null) {
                predicates.add(cbuild.equal(root.get("payNum"), queryRequest.getPayNum()));
            }

            // 支付金额
            if (queryRequest.getPayAmount() != null) {
                predicates.add(cbuild.equal(root.get("payAmount"), queryRequest.getPayAmount()));
            }

            // 大于或等于 搜索条件:支付时间开始
            if (queryRequest.getPayTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("payTime"),
                        queryRequest.getPayTimeBegin()));
            }
            // 小于或等于 搜索条件:支付时间截止
            if (queryRequest.getPayTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("payTime"),
                        queryRequest.getPayTimeEnd()));
            }

            // 大于或等于 搜索条件:会员到期时间开始
            if (queryRequest.getExpirationDateBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("expirationDate"),
                        queryRequest.getExpirationDateBegin()));
            }
            // 小于或等于 搜索条件:会员到期时间截止
            if (queryRequest.getExpirationDateEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("expirationDate"),
                        queryRequest.getExpirationDateEnd()));
            }

            // 支付状态：0.未支付，1.已支付
            if (queryRequest.getPayState() != null) {
                predicates.add(cbuild.equal(root.get("payState"), queryRequest.getPayState()));
            }

            // 模糊查询 - 权益id集合
            if (StringUtils.isNotEmpty(queryRequest.getRightsIds())) {
                predicates.add(cbuild.like(root.get("rightsIds"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRightsIds()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            // 模糊查询 - createPerson
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

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
