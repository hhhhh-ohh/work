package com.wanmi.sbc.marketing.drawrecord.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * <p>抽奖记录表动态查询条件构建器</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
public class DrawRecordWhereCriteriaBuilder {
    public static Specification<DrawRecord> build(DrawRecordQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-抽奖记录主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 抽奖记录主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }
            // 批量查询-抽奖记录编号List
            if (CollectionUtils.isNotEmpty(queryRequest.getDrawRecordCodeList())){
                predicates.add(root.get("drawRecordCode").in(queryRequest.getDrawRecordCodeList()));
            }

            // 抽奖活动id
            if (queryRequest.getActivityId() != null) {
                predicates.add(cbuild.equal(root.get("activityId"), queryRequest.getActivityId()));
            }

            // 模糊查询 - 抽奖记录编号
            if (StringUtils.isNotEmpty(queryRequest.getDrawRecordCode())) {
                predicates.add(cbuild.like(root.get("drawRecordCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDrawRecordCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:抽奖时间开始
            if (queryRequest.getDrawTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("drawTime"),
                        queryRequest.getDrawTimeBegin()));
            }
            // 小于或等于 搜索条件:抽奖时间截止
            if (queryRequest.getDrawTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("drawTime"),
                        queryRequest.getDrawTimeEnd()));
            }

            // 抽奖状态 0 未中奖 1 中奖
            if (queryRequest.getDrawStatus() != null &&  -1 != queryRequest.getDrawStatus()) {
                predicates.add(cbuild.equal(root.get("drawStatus"), queryRequest.getDrawStatus()));
            }

            // 奖项id
            if (queryRequest.getPrizeId() != null) {
                predicates.add(cbuild.equal(root.get("prizeId"), queryRequest.getPrizeId()));
            }

            // 模糊查询 - 奖品名称
            if (StringUtils.isNotEmpty(queryRequest.getPrizeName())) {
                predicates.add(cbuild.like(root.get("prizeName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPrizeName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 奖品图片
            if (StringUtils.isNotEmpty(queryRequest.getPrizeUrl())) {
                predicates.add(cbuild.like(root.get("prizeUrl"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPrizeUrl()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 兑奖状态 0未兑奖  1已兑奖
            if (queryRequest.getRedeemPrizeStatus() != null) {
                predicates.add(cbuild.equal(root.get("redeemPrizeStatus"), queryRequest.getRedeemPrizeStatus()));
                predicates.add(cbuild.equal(root.get("drawStatus"), Constants.ONE));
            }

            // 0未发货  1已发货
            if (queryRequest.getDeliverStatus() != null) {
                predicates.add(cbuild.equal(root.get("deliverStatus"), queryRequest.getDeliverStatus()));
            }

            // 模糊查询 - 会员Id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {

                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 模糊查询 - 会员登录账号|手机号
            if (StringUtils.isNotEmpty(queryRequest.getCustomerAccount())) {
                predicates.add(cbuild.like(root.get("customerAccount"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerAccount()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员名称
            if (StringUtils.isNotEmpty(queryRequest.getCustomerName())) {
                predicates.add(cbuild.like(root.get("customerName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 详细收货地址(包含省市区）
            if (StringUtils.isNotEmpty(queryRequest.getDetailAddress())) {
                predicates.add(cbuild.like(root.get("detailAddress"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDetailAddress()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 收货人
            if (StringUtils.isNotEmpty(queryRequest.getConsigneeName())) {
                predicates.add(cbuild.like(root.get("consigneeName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getConsigneeName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 收货人手机号码
            if (StringUtils.isNotEmpty(queryRequest.getConsigneeNumber())) {
                predicates.add(cbuild.like(root.get("consigneeNumber"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getConsigneeNumber()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:兑奖时间开始
            if (queryRequest.getRedeemPrizeTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("redeemPrizeTime"),
                        queryRequest.getRedeemPrizeTimeBegin()));
            }
            // 小于或等于 搜索条件:兑奖时间截止
            if (queryRequest.getRedeemPrizeTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("redeemPrizeTime"),
                        queryRequest.getRedeemPrizeTimeEnd()));
            }

            // 大于或等于 搜索条件:发货时间开始
            if (queryRequest.getDeliveryTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("deliveryTime"),
                        queryRequest.getDeliveryTimeBegin()));
            }
            // 小于或等于 搜索条件:发货时间截止
            if (queryRequest.getDeliveryTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("deliveryTime"),
                        queryRequest.getDeliveryTimeEnd()));
            }

            // 模糊查询 - 物流公司名称
            if (StringUtils.isNotEmpty(queryRequest.getLogisticsCompany())) {
                predicates.add(cbuild.like(root.get("logisticsCompany"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLogisticsCompany()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 物流单号
            if (StringUtils.isNotEmpty(queryRequest.getLogisticsNo())) {
                predicates.add(cbuild.like(root.get("logisticsNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLogisticsNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 物流公司标准编码
            if (StringUtils.isNotEmpty(queryRequest.getLogisticsCode())) {
                predicates.add(cbuild.like(root.get("logisticsCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLogisticsCode()))
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

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 编辑人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 编辑人
            if (Objects.nonNull(queryRequest.getPrizeType())) {
                predicates.add(cbuild.equal(root.get("prizeType"), DrawPrizeType.fromValue(queryRequest.getPrizeType())));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
