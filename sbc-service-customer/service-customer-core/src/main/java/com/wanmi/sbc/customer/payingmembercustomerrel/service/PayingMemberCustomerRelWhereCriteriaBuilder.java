package com.wanmi.sbc.customer.payingmembercustomerrel.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelQueryRequest;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>客户与付费会员等级关联表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
public class PayingMemberCustomerRelWhereCriteriaBuilder {
    public static Specification<PayingMemberCustomerRel> build(PayingMemberCustomerRelQueryRequest queryRequest) {
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

            // 客户与付费会员等级关联id
            if (queryRequest.getLevelId() != null) {
                predicates.add(cbuild.equal(root.get("levelId"), queryRequest.getLevelId()));
            }

            // 模糊查询 - 会员id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:开通时间开始
            if (queryRequest.getOpenTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("openTime"),
                        queryRequest.getOpenTimeBegin()));
            }
            // 小于或等于 搜索条件:开通时间截止
            if (queryRequest.getOpenTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("openTime"),
                        queryRequest.getOpenTimeEnd()));
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

            // 总共优惠金额
            if (queryRequest.getDiscountAmount() != null) {
                predicates.add(cbuild.equal(root.get("discountAmount"), queryRequest.getDiscountAmount()));
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

            //查询 -customerIdLIst
            if(CollectionUtils.isNotEmpty(queryRequest.getCustomerIdList())){
                predicates.add(root.get("customerId").in(queryRequest.getCustomerIdList()));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
