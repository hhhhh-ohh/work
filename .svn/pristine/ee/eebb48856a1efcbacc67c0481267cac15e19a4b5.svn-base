package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardInvalidStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardUseStatus;
import com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>用户礼品卡动态查询条件构建器</p>
 * @author 吴瑞
 * @date 2022-12-12 09:45:09
 */
public class UserGiftCardWhereCriteriaBuilder {
    public static Specification<UserGiftCard> build(UserGiftCardQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-礼品卡IdList
            if (CollectionUtils.isNotEmpty(queryRequest.getUserGiftCardIdList())) {
                predicates.add(root.get("userGiftCardId").in(queryRequest.getUserGiftCardIdList()));
            }

            // 礼品卡Id
            if (queryRequest.getUserGiftCardId() != null) {
                predicates.add(
                        cbuild.equal(root.get("userGiftCardId"), queryRequest.getUserGiftCardId()));
            }

            //礼品卡批次号
            if (StringUtils.isNotBlank(queryRequest.getBatchNo())) {
                predicates.add(
                        cbuild.like(
                                root.get("batchNo"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getBatchNo()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 礼品卡名称模糊查询
            if (StringUtils.isNotBlank(queryRequest.getGiftCardName())) {
                predicates.add(
                        cbuild.like(
                                root.get("giftCardName"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getGiftCardName()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 用户Id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"),queryRequest.getCustomerId()));
            }

            // 礼品卡Id
            if (queryRequest.getGiftCardId() != null) {
                predicates.add(cbuild.equal(root.get("giftCardId"), queryRequest.getGiftCardId()));
            }

            // 模糊查询 - 礼品卡卡号
            if (StringUtils.isNotEmpty(queryRequest.getGiftCardNo())) {
                predicates.add(
                        cbuild.like(
                                root.get("giftCardNo"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getGiftCardNo()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 礼品卡面值
            if (queryRequest.getParValue() != null) {
                predicates.add(cbuild.equal(root.get("parValue"), queryRequest.getParValue()));
            }

            // 礼品卡余额
            if (queryRequest.getBalance() != null) {
                predicates.add(cbuild.equal(root.get("balance"), queryRequest.getBalance()));
            }

            // 礼品卡状态 0：待激活  1：已激活 2：已销卡
            if (queryRequest.getCardStatus() != null) {
                predicates.add(cbuild.equal(root.get("cardStatus"), queryRequest.getCardStatus()));
            }

            // 过期时间类型 0：有失效时间 1：长期有效
            if (queryRequest.getExpirationType() != null) {
                predicates.add(
                        cbuild.equal(root.get("expirationType"), queryRequest.getExpirationType()));
            }

            // 大于或等于 搜索条件:过期时间开始
            if (queryRequest.getExpirationTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("expirationTime"), queryRequest.getExpirationTimeBegin()));
            }
            // 小于或等于 搜索条件:过期时间截止
            if (queryRequest.getExpirationTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("expirationTime"), queryRequest.getExpirationTimeEnd()));
            }

            // 大于或等于 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始
            if (queryRequest.getAcquireTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("acquireTime"), queryRequest.getAcquireTimeBegin()));
            }
            // 小于或等于 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止
            if (queryRequest.getAcquireTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("acquireTime"), queryRequest.getAcquireTimeEnd()));
            }

            // 模糊查询 - 归属会员，制卡兑换人/发卡接收人
            if (StringUtils.isNotEmpty(queryRequest.getBelongPerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("belongPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getBelongPerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:激活时间开始
            if (queryRequest.getActivationTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("activationTime"), queryRequest.getActivationTimeBegin()));
            }
            // 小于或等于 搜索条件:激活时间截止
            if (queryRequest.getActivationTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("activationTime"), queryRequest.getActivationTimeEnd()));
            }

            // 模糊查询 - 激活人
            if (StringUtils.isNotEmpty(queryRequest.getActivationPerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("activationPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getActivationPerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:销卡时间开始
            if (queryRequest.getCancelTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("cancelTime"), queryRequest.getCancelTimeBegin()));
            }
            // 小于或等于 搜索条件:销卡时间截止
            if (queryRequest.getCancelTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("cancelTime"), queryRequest.getCancelTimeEnd()));
            }

            // 模糊查询 - 销卡人
            if (StringUtils.isNotEmpty(queryRequest.getCancelPerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("cancelPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCancelPerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 销卡描述
            if (StringUtils.isNotEmpty(queryRequest.getCancelDesc())) {
                predicates.add(
                        cbuild.like(
                                root.get("cancelDesc"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCancelDesc()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (Objects.nonNull(queryRequest.getStatus())) {
                if (Objects.equals(GiftCardUseStatus.USE, queryRequest.getStatus())) {
                    // 可用 ： 已激活 && 未过有效期 && 余额大于0
                    predicates.add(cbuild.equal(root.get("cardStatus"), GiftCardStatus.ACTIVATED));
                    // 过期时间
                    Predicate p1 = cbuild.equal(root.get("expirationType"), ExpirationType.FOREVER);
                    Predicate p2 = cbuild.greaterThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now());
                    predicates.add(cbuild.or(p1, p2));
                    //余额
                    Predicate p3 = cbuild.greaterThan(root.get("balance"), BigDecimal.ZERO);
                    Predicate p4 = cbuild.isNull(root.get("balance"));
                    predicates.add(cbuild.or(p3, p4));
                } else if (Objects.equals(GiftCardUseStatus.INVALID, queryRequest.getStatus())) {
                    //不可用： 全部，0：已用完，1：已过期，2：已经销卡
                    if (Objects.isNull(queryRequest.getInvalidStatus())) {
                        // 已销卡   || 余额<=0 || 已过期
                        Predicate p1 = cbuild.equal(root.get("cardStatus"), GiftCardStatus.CANCELED);
                        //拼装没有余额和已过期
                        Predicate p21 = cbuild.lessThanOrEqualTo(root.get("balance"), BigDecimal.ZERO);
                        Predicate p22 = cbuild.equal(root.get("cardStatus"), GiftCardStatus.ACTIVATED);
                        Predicate p2 = cbuild.and(p21, p22);
                        Predicate p3 = cbuild.and(cbuild.notEqual(root.get("expirationType"), ExpirationType.FOREVER),
                                        cbuild.lessThan(root.get("expirationTime"), LocalDateTime.now()));
                        predicates.add(cbuild.or(p1, p2, p3));
                    } else {
                        if(Objects.equals(GiftCardInvalidStatus.USE_OVER, queryRequest.getInvalidStatus())) {
                            //已用完 未销卡 && 未过期 && 余额<=0
                            predicates.add(cbuild.equal(root.get("cardStatus"), GiftCardStatus.ACTIVATED));
                            // 过期时间
                            Predicate p1 = cbuild.equal(root.get("expirationType"), ExpirationType.FOREVER);
                            Predicate p2 = cbuild.greaterThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now());
                            predicates.add(cbuild.or(p1, p2));
                            //余额
                            predicates.add(cbuild.lessThanOrEqualTo(root.get("balance"), BigDecimal.ZERO));
                        } else if (Objects.equals(GiftCardInvalidStatus.TIME_OVER, queryRequest.getInvalidStatus())) {
                            // 已过期 未销卡 && 过期
                            predicates.add(cbuild.notEqual(root.get("cardStatus"), GiftCardStatus.CANCELED));
                            predicates.add(cbuild.notEqual(root.get("expirationType"), ExpirationType.FOREVER));
                            predicates.add(cbuild.isNotNull(root.get("expirationTime")));
                            predicates.add(cbuild.lessThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now()));
                        } else if (Objects.equals(GiftCardInvalidStatus.CANCELED, queryRequest.getInvalidStatus())) {
                            // 已销卡
                            predicates.add(cbuild.equal(root.get("cardStatus"), GiftCardStatus.CANCELED));
                        }
                    }
                } else if (Objects.equals(GiftCardUseStatus.NOT_ACTIVE, queryRequest.getStatus())) {
                    // 待激活: 待激活  && 有效期 >= now
                    predicates.add(cbuild.equal(root.get("cardStatus"), GiftCardStatus.NOT_ACTIVE));
                    Predicate p1 = cbuild.isNull(root.get("expirationTime"));
                    Predicate p2 = cbuild.greaterThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now());
                    predicates.add(cbuild.or(p1, p2));
                }
            }

            if (Objects.nonNull(queryRequest.getGiftCardType())){
                predicates.add(cbuild.equal(root.get("giftCardType"), queryRequest.getGiftCardType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
