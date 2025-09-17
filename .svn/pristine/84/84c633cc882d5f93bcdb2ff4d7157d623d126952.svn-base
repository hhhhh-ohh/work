package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchQueryRequest;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBatch;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>礼品卡批次动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-12-10 10:59:47
 */
public class GiftCardBatchWhereCriteriaBuilder {
    public static Specification<GiftCardBatch> build(GiftCardBatchQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {

            // 联表查询
            Join<GiftCardBatch, GiftCard> join = root.join("giftCard");

            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键IdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGiftCardBatchIdList())) {
                predicates.add(root.get("giftCardBatchId").in(queryRequest.getGiftCardBatchIdList()));
            }

            // 批量查询-批次id列表
            if (CollectionUtils.isNotEmpty(queryRequest.getBatchNoList())) {
                predicates.add(root.get("batchNo").in(queryRequest.getBatchNoList()));
            }

            // 主键Id
            if (queryRequest.getGiftCardBatchId() != null) {
                predicates.add(cbuild.equal(root.get("giftCardBatchId"), queryRequest.getGiftCardBatchId()));
            }

            // 礼品卡Id
            if (queryRequest.getGiftCardId() != null) {
                predicates.add(cbuild.equal(root.get("giftCardId"), queryRequest.getGiftCardId()));
            }

            // 礼品卡名称
            if (StringUtils.isNotBlank(queryRequest.getGiftCardName())) {
                predicates.add(cbuild.like(join.get("name"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGiftCardName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:过期时间开始
            if (queryRequest.getExpirationTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(join.get("expirationTime"),
                        queryRequest.getExpirationTimeBegin()));
            }
            // 小于或等于 搜索条件:过期时间截止
            if (queryRequest.getExpirationTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(join.get("expirationTime"),
                        queryRequest.getExpirationTimeEnd()));
            }

            // 兑换方式 0:卡密模式
            if (queryRequest.getExchangeMode() != null) {
                predicates.add(cbuild.equal(root.get("exchangeMode"), queryRequest.getExchangeMode()));
            }

            // 批次类型 0:制卡 1:发卡
            if (queryRequest.getBatchType() != null) {
                predicates.add(cbuild.equal(root.get("batchType"), queryRequest.getBatchType()));
            }

            // 批次数量(制/发卡数量)
            if (queryRequest.getBatchNum() != null) {
                predicates.add(cbuild.equal(root.get("batchNum"), queryRequest.getBatchNum()));
            }

            // 模糊查询 - 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
            if (StringUtils.isNotEmpty(queryRequest.getBatchNo())) {
                predicates.add(cbuild.like(root.get("batchNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBatchNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:制/发卡时间开始
            if (queryRequest.getGenerateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("generateTime"),
                        queryRequest.getGenerateTimeBegin()));
            }
            // 小于或等于 搜索条件:制/发卡时间截止
            if (queryRequest.getGenerateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("generateTime"),
                        queryRequest.getGenerateTimeEnd()));
            }

            // 精确查询 - 制/发卡人
            if (StringUtils.isNotEmpty(queryRequest.getGeneratePerson())) {
                predicates.add(cbuild.equal(root.get("generatePerson"), queryRequest.getGeneratePerson()));
            }

            // 模糊查询 - 起始卡号
            if (StringUtils.isNotEmpty(queryRequest.getStartCardNo())) {
                predicates.add(cbuild.like(root.get("startCardNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getStartCardNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 结束卡号
            if (StringUtils.isNotEmpty(queryRequest.getEndCardNo())) {
                predicates.add(cbuild.like(root.get("endCardNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getEndCardNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 审核状态 0:待审核 1:已审核通过 2:审核不通过
            if (queryRequest.getAuditStatus() != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), queryRequest.getAuditStatus()));
            }

            // 模糊查询 - 审核驳回原因
            if (StringUtils.isNotEmpty(queryRequest.getAuditReason())) {
                predicates.add(cbuild.like(root.get("auditReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAuditReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - excel导入的文件oss地址（仅批量发卡时存在）
            if (StringUtils.isNotEmpty(queryRequest.getExcelFilePath())) {
                predicates.add(cbuild.like(root.get("excelFilePath"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getExcelFilePath()))
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
