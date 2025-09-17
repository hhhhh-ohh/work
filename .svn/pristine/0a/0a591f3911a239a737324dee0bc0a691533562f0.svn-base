package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailQueryRequest;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>礼品卡详情动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
public class GiftCardDetailWhereCriteriaBuilder {
    public static Specification<GiftCardDetail> build(GiftCardDetailQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-礼品卡卡号，主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getGiftCardNoList())) {
                predicates.add(root.get("giftCardNo").in(queryRequest.getGiftCardNoList()));
            }

            // 礼品卡卡号，主键
            if (StringUtils.isNotEmpty(queryRequest.getGiftCardNo())) {
                predicates.add(cbuild.equal(root.get("giftCardNo"), queryRequest.getGiftCardNo()));
            }

            // 模糊查询 - 批次编号
            if (StringUtils.isNotEmpty(queryRequest.getBatchNo())) {
                predicates.add(cbuild.like(root.get("batchNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBatchNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 来源类型 0：制卡 1：发卡 2：购卡
            if (queryRequest.getSourceType() != null) {
                predicates.add(cbuild.equal(root.get("sourceType"), queryRequest.getSourceType()));
            }

            // 模糊查询 - 兑换码
            if (StringUtils.isNotEmpty(queryRequest.getExchangeCode())) {
                predicates.add(cbuild.like(root.get("exchangeCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getExchangeCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 已过期，过期时间 <= now()
            if (BooleanUtils.isTrue(queryRequest.getExpiredFlag())) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now()));
            }

            // 未过期，过期时间 > now() || 过期时间 is null
            if (BooleanUtils.isFalse(queryRequest.getExpiredFlag())) {
                Predicate greaterThan = cbuild.greaterThan(root.get("expirationTime"), LocalDateTime.now());
                Predicate isNull = cbuild.isNull(root.get("expirationTime"));
                predicates.add(cbuild.or(greaterThan, isNull));
            }

            // 大于或等于 搜索条件:有效期开始
            if (queryRequest.getExpirationTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("expirationTime"),
                        queryRequest.getExpirationTimeBegin()));
            }
            // 小于或等于 搜索条件:有效期截止
            if (queryRequest.getExpirationTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("expirationTime"),
                        queryRequest.getExpirationTimeEnd()));
            }

            // 大于或等于 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始
            if (queryRequest.getAcquireTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("acquireTime"),
                        queryRequest.getAcquireTimeBegin()));
            }
            // 小于或等于 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止
            if (queryRequest.getAcquireTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("acquireTime"),
                        queryRequest.getAcquireTimeEnd()));
            }

            // 大于或等于 搜索条件:激活时间开始
            if (queryRequest.getActivationTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("activationTime"),
                        queryRequest.getActivationTimeBegin()));
            }
            // 小于或等于 搜索条件:激活时间截止
            if (queryRequest.getActivationTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("activationTime"),
                        queryRequest.getActivationTimeEnd()));
            }

            // 模糊查询 - 归属会员，制卡兑换人/发卡接收人
            if (StringUtils.isNotEmpty(queryRequest.getBelongPerson())) {
                predicates.add(cbuild.like(root.get("belongPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBelongPerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 激活会员
            if (StringUtils.isNotEmpty(queryRequest.getActivationPerson())) {
                predicates.add(cbuild.like(root.get("activationPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivationPerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期
            if (queryRequest.getCardDetailStatus() != null) {
                predicates.add(cbuild.equal(root.get("cardDetailStatus"), queryRequest.getCardDetailStatus()));
            }

            // 非礼品卡详情状态列表
            if (queryRequest.getNotCardDetailStatusList() != null) {
                predicates.add(root.get("cardDetailStatus").in(queryRequest.getNotCardDetailStatusList()).not());
            }

            // 模糊查询 - 销卡人
            if (StringUtils.isNotEmpty(queryRequest.getCancelPerson())) {
                predicates.add(cbuild.like(root.get("cancelPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCancelPerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 发卡状态 0：待发 1：成功 2：失败
            if (queryRequest.getSendStatus() != null) {
                predicates.add(cbuild.equal(root.get("sendStatus"), queryRequest.getSendStatus()));
            }

            // 模糊查询 - 状态变更原因，目前仅针对销卡原因
            if (StringUtils.isNotEmpty(queryRequest.getStatusReason())) {
                predicates.add(cbuild.like(root.get("statusReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getStatusReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标记  0：正常，1：删除
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

            // 大于或等于 搜索条件:修改时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:修改时间截止
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
