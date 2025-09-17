package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillQueryRequest;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBill;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>礼品卡交易流水动态查询条件构建器</p>
 * @author 吴瑞
 * @date 2022-12-12 09:46:04
 */
public class GiftCardBillWhereCriteriaBuilder {
    public static Specification<GiftCardBill> build(GiftCardBillQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 礼品卡id
            if (Objects.nonNull(queryRequest.getGiftCardId())) {
                predicates.add(cbuild.equal(root.get("giftCardId"), queryRequest.getGiftCardId()));
            }

            // 批量查询-giftCardBillIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGiftCardBillIdList())) {
                predicates.add(root.get("giftCardBillId").in(queryRequest.getGiftCardBillIdList()));
            }

            // 批量查询-businessTypeList
            if (CollectionUtils.isNotEmpty(queryRequest.getBusinessTypeList())) {
                predicates.add(root.get("businessType").in(queryRequest.getBusinessTypeList()));
            }

            // giftCardBillId
            if (queryRequest.getGiftCardBillId() != null) {
                predicates.add(cbuild.equal(root.get("giftCardBillId"), queryRequest.getGiftCardBillId()));
            }

            // 模糊查询 - 用户Id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 用户礼品卡Id
            if (queryRequest.getUserGiftCardId() != null) {
                predicates.add(cbuild.equal(root.get("userGiftCardId"), queryRequest.getUserGiftCardId()));
            }

            // 模糊查询 - 礼品卡卡号
            if (StringUtils.isNotEmpty(queryRequest.getGiftCardNo())) {
                predicates.add(cbuild.like(root.get("giftCardNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGiftCardNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡
            if (queryRequest.getBusinessType() != null) {
                predicates.add(cbuild.equal(root.get("businessType"), queryRequest.getBusinessType()));
            }

            // 模糊查询 - 业务Id
            if (StringUtils.isNotEmpty(queryRequest.getBusinessId())) {
                predicates.add(cbuild.like(root.get("businessId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBusinessId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 交易金额
            if (queryRequest.getTradeBalance() != null) {
                predicates.add(cbuild.equal(root.get("tradeBalance"), queryRequest.getTradeBalance()));
            }

            // 交易前余额
            if (queryRequest.getBeforeBalance() != null) {
                predicates.add(cbuild.equal(root.get("beforeBalance"), queryRequest.getBeforeBalance()));
            }

            // 交易后余额
            if (queryRequest.getAfterBalance() != null) {
                predicates.add(cbuild.equal(root.get("afterBalance"), queryRequest.getAfterBalance()));
            }

            // 大于或等于 搜索条件:交易时间开始
            if (queryRequest.getTradeTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("tradeTime"),
                        queryRequest.getTradeTimeBegin()));
            }
            // 小于或等于 搜索条件:交易时间截止
            if (queryRequest.getTradeTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("tradeTime"),
                        queryRequest.getTradeTimeEnd()));
            }

            // 交易人类型 0：C端用户 1：B端用户
            if (queryRequest.getTradePersonType() != null) {
                predicates.add(cbuild.equal(root.get("tradePersonType"), queryRequest.getTradePersonType()));
            }

            // 模糊查询 - 交易人
            if (StringUtils.isNotEmpty(queryRequest.getTradePerson())) {
                predicates.add(cbuild.like(root.get("tradePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTradePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
