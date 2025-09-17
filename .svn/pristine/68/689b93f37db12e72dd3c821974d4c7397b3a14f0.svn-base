package com.wanmi.sbc.marketing.electroniccoupon.service.criteria;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicSendRecordQueryRequest;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicSendRecord;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>卡密发放记录表动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
public class ElectronicSendRecordWhereCriteriaBuilder {
    public static Specification<ElectronicSendRecord> build(ElectronicSendRecordQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-记录idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 记录id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 订单号
            if (StringUtils.isNotEmpty(queryRequest.getOrderNo())) {
                predicates.add(cbuild.like(root.get("orderNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getOrderNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - sku编码
            if (StringUtils.isNotEmpty(queryRequest.getSkuNo())) {
                predicates.add(cbuild.like(root.get("skuNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getSkuNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商品名称
            if (StringUtils.isNotEmpty(queryRequest.getSkuName())) {
                predicates.add(cbuild.like(root.get("skuName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getSkuName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 收货人
            if (StringUtils.isNotEmpty(queryRequest.getAccount())) {
                predicates.add(cbuild.like(root.get("account"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAccount()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:发放时间开始
            if (queryRequest.getSendTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeBegin()));
            }
            // 小于或等于 搜索条件:发放时间截止
            if (queryRequest.getSendTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeEnd()));
            }

            // 卡券id
            if (queryRequest.getCouponId() != null) {
                predicates.add(cbuild.equal(root.get("couponId"), queryRequest.getCouponId()));
            }

            // 模糊查询 - 卡券名称
            if (StringUtils.isNotEmpty(queryRequest.getCouponName())) {
                predicates.add(cbuild.like(root.get("couponName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCouponName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 卡密id
            if (StringUtils.isNotEmpty(queryRequest.getCradId())) {
                predicates.add(cbuild.like(root.get("cradId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCradId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 卡券内容
            if (StringUtils.isNotEmpty(queryRequest.getCardContent())) {
                predicates.add(cbuild.like(root.get("cardContent"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCardContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 发放状态  0、发放成功 1、发送中 2、发送失败
            if (queryRequest.getSendState() != null) {
                predicates.add(cbuild.equal(root.get("sendState"), queryRequest.getSendState()));
            }

            // 发放失败原因  0、库存不足1、已过销售期 2、其他原因
            if (queryRequest.getFailReason() != null) {
                predicates.add(cbuild.equal(root.get("failReason"), queryRequest.getFailReason()));
            }

            // 是否删除 0 否  1 是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            //店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
