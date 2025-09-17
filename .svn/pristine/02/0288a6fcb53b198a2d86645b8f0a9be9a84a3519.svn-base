package com.wanmi.sbc.marketing.bargaingoods.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import com.wanmi.sbc.marketing.bean.enums.BargainActivityState;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>砍价商品动态查询条件构建器</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
public class BargainGoodsWhereCriteriaBuilder {
    public static Specification<BargainGoods> build(BargainGoodsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            // 批量查询-bargainGoodsIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getBargainGoodsIdList())) {
                predicates.add(root.get("bargainGoodsId").in(queryRequest.getBargainGoodsIdList()));
            }

            // bargainGoodsId
            if (queryRequest.getBargainGoodsId() != null) {
                predicates.add(cbuild.equal(root.get("bargainGoodsId"), queryRequest.getBargainGoodsId()));
            }

            // SKU
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }

            // 商品名称
            if (StringUtils.isNotBlank(queryRequest.getGoodsInfoName())) {
                predicates.add(cbuild.like(root.get("goodsInfoName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品编号
            if (StringUtils.isNotBlank(queryRequest.getGoodsInfoNo())) {
                predicates.add(cbuild.like(root.get("goodsInfoNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 类目ids
            if (CollectionUtils.isNotEmpty(queryRequest.getCateIds())) {
                predicates.add(root.get("goodsCateId").in(queryRequest.getCateIds()));
            }

            // 多个店铺id
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            // 店铺id
            if (Objects.nonNull(queryRequest.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 剩余库存
            if (Boolean.TRUE.equals(queryRequest.getLeaveStock())) {
                predicates.add(cbuild.gt(root.get("leaveStock"), 0));
            }


            // 审核状态，0：待审核，1：已审核，2：审核失败
            if (queryRequest.getAuditStatus() != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), queryRequest.getAuditStatus()));
            }

            // 审核状态，0：待审核，1：已审核，2：审核失败
            if (CollectionUtils.isNotEmpty(queryRequest.getAuditStatusList())) {
                predicates.add(root.get("auditStatus").in(queryRequest.getAuditStatusList()));
            }

            // 活动进行中
            if (BargainActivityState.ONGOING_ACTIVITY == queryRequest.getBargainActivityState()) {
                LocalDateTime now = LocalDateTime.now();
                // 1、大于 活动开始时间开始;小于 活动开始时间截止
                predicates.add(cbuild.lessThan(root.get("beginTime"), now));
                predicates.add(cbuild.greaterThan(root.get("endTime"), now));
                // 2、手动终止活动
                predicates.add(cbuild.equal(root.get("stoped"), Boolean.FALSE));
                // 4、原始商品下架、删除、禁售排除
                predicates.add(cbuild.equal(root.get("goodsStatus"), DeleteFlag.YES));
            } else if (BargainActivityState.TERMINATED_ACTIVITY == queryRequest.getBargainActivityState()) {
                // 已结束
                // 1、大于 活动结束时间开始
                Predicate p1 = cbuild.lessThan(root.get("endTime"), LocalDateTime.now());
                // 2、手动终止活动
                Predicate p2 = cbuild.equal(root.get("stoped"), Boolean.TRUE);
                // 4、原始商品下架、删除、禁售，活动已结束
                Predicate p4 = cbuild.equal(root.get("goodsStatus"), DeleteFlag.NO);
                Predicate result = cbuild.or(p1, p2, p4);
                predicates.add(result);
            } else if (BargainActivityState.WAIT_ACTIVITY == queryRequest.getBargainActivityState()) {
                // 1、活动未开始
                predicates.add(cbuild.greaterThan(root.get("beginTime"), LocalDateTime.now()));
                // 2、手动终止活动
                predicates.add(cbuild.equal(root.get("stoped"), Boolean.FALSE));
                // 4、原始商品下架、删除、禁售排除
                predicates.add(cbuild.equal(root.get("goodsStatus"), DeleteFlag.YES));
            }

            // 商品可售状态
            if (Objects.nonNull(queryRequest.getGoodsStatus())) {
                predicates.add(cbuild.equal(root.get("goodsStatus"), queryRequest.getGoodsStatus()));
            }

            // 大于或等于 搜索条件:活动开始时间开始
            if (queryRequest.getBeginTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeBegin()));
            }
            // 小于或等于 搜索条件:活动开始时间截止
            if (queryRequest.getBeginTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeEnd()));
            }

            // 大于或等于 搜索条件:活动结束时间开始
            if (queryRequest.getEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeBegin()));
            }
            // 小于或等于 搜索条件:活动结束时间截止
            if (queryRequest.getEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeEnd()));
            }

            // 是否手动停止，0，否，1，是
            if (queryRequest.getStoped() != null) {
                predicates.add(cbuild.equal(root.get("stoped"), queryRequest.getStoped()));
            }
            // 大于或等于 搜索条件:createTime开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:createTime截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 大于或等于 搜索条件:updateTime开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:updateTime截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            if (queryRequest.getNotId() != null) {
                predicates.add(cbuild.notEqual(root.get("bargainGoodsId"), queryRequest.getNotId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
