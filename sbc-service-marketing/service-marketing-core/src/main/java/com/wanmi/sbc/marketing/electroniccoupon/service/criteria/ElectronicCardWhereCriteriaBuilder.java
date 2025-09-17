package com.wanmi.sbc.marketing.electroniccoupon.service.criteria;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardQueryRequest;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>电子卡密表动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
public class ElectronicCardWhereCriteriaBuilder {
    public static Specification<ElectronicCard> build(ElectronicCardQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-卡密IdList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 卡密Id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 卡券id
            if (queryRequest.getCouponId() != null) {
                predicates.add(cbuild.equal(root.get("couponId"), queryRequest.getCouponId()));
            }

            // 模糊查询 - 卡号
            if (StringUtils.isNotEmpty(queryRequest.getCardNumber())) {
                predicates.add(cbuild.like(root.get("cardNumber"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCardNumber()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 卡密
            if (StringUtils.isNotEmpty(queryRequest.getCardPassword())) {
                predicates.add(cbuild.like(root.get("cardPassword"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCardPassword()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 优惠码
            if (StringUtils.isNotEmpty(queryRequest.getCardPromoCode())) {
                predicates.add(cbuild.like(root.get("cardPromoCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCardPromoCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 卡密状态  0、未发送 1、已发送 2、已失效
            if (queryRequest.getCardState() != null) {
                predicates.add(cbuild.equal(root.get("cardState"), queryRequest.getCardState()));
            }

            // 未过销售期
            if (queryRequest.getSaleEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThan(root.get("saleEndTime"),
                        queryRequest.getSaleEndTimeBegin()));
            }
            // 已过销售期
            if (queryRequest.getSaleEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("saleEndTime"),
                        queryRequest.getSaleEndTimeEnd()));
            }

            // 模糊查询 - 批次id
            if (StringUtils.isNotEmpty(queryRequest.getRecordId())) {
                predicates.add(cbuild.like(root.get("recordId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRecordId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否删除 0 否  1 是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 是否绑定了订单
            if (queryRequest.getOrderBindFlag() != null) {
                if (Boolean.TRUE.equals(queryRequest.getOrderBindFlag())) {
                    predicates.add(cbuild.isNotNull(root.get("orderNo")));
                } else {
                    predicates.add(cbuild.isNull(root.get("orderNo")));
                }
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
