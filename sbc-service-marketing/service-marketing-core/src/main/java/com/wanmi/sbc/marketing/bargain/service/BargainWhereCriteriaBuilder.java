package com.wanmi.sbc.marketing.bargain.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinGoodsInfo;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import com.wanmi.sbc.marketing.bean.enums.BargainStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>砍价动态查询条件构建器</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
public class BargainWhereCriteriaBuilder {
    public static Specification<BargainJoinGoodsInfo> build(BargainQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-bargainIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getBargainIdList())) {
                predicates.add(root.get("bargainId").in(queryRequest.getBargainIdList()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getCustomerIds())) {
                predicates.add(root.get("customerId").in(queryRequest.getCustomerIds()));
            }

            // bargainId
            if (queryRequest.getBargainId() != null) {
                predicates.add(cbuild.equal(root.get("bargainId"), queryRequest.getBargainId()));
            }

            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 砍价编号
            if (queryRequest.getBargainNo() != null) {
                predicates.add(cbuild.equal(root.get("bargainNo"), queryRequest.getBargainNo()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getBargainGoodsIds())) {
                predicates.add(root.get("bargainGoodsId").in(queryRequest.getBargainGoodsIds()));
            }

            // 大于或等于 搜索条件:发起时间开始
            if (queryRequest.getBeginTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeBegin()));
            }
            // 小于或等于 搜索条件:发起时间截止
            if (queryRequest.getBeginTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeEnd()));
            }

            // 大于或等于 搜索条件:结束时间开始
            if (queryRequest.getEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeBegin()));
            }
            // 小于或等于 搜索条件:结束时间截止
            if (queryRequest.getEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeEnd()));
            }

            // 模糊查询 - 发起人id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
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

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    public static Specification<BargainJoinGoodsInfo> buildJoinGoodsInfo(BargainQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<BargainJoinGoodsInfo, BargainGoods> bargainGoods = root.join("bargainGoods");
            // 批量查询-bargainIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getBargainIdList())) {
                predicates.add(root.get("bargainId").in(queryRequest.getBargainIdList()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getCustomerIds())) {
                predicates.add(root.get("customerId").in(queryRequest.getCustomerIds()));
            }

            // 模糊查询 - 会员账号
            if (StringUtils.isNotEmpty(queryRequest.getCustomerAccount())) {
                predicates.add(cbuild.like(root.get("customerAccount"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerAccount()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品名称
            if (StringUtils.isNotBlank(queryRequest.getGoodsInfoName())) {
                predicates.add(cbuild.like(bargainGoods.get("goodsInfoName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品编号
            if (StringUtils.isNotBlank(queryRequest.getGoodsInfoNo())) {
                predicates.add(cbuild.like(bargainGoods.get("goodsInfoNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (queryRequest.getBargainStatus() != null) {
                BargainStatus bargainStatus = queryRequest.getBargainStatus();
                if (BargainStatus.PROGRESS.equals(bargainStatus)) {
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.notEqual(root.get("joinNum"), root.get("targetJoinNum")));
                    predicates.add(cbuild.equal(bargainGoods.get("stoped"), Boolean.FALSE));
                    predicates.add(cbuild.gt(bargainGoods.get("leaveStock"), 0));
                    predicates.add(cbuild.equal(bargainGoods.get("goodsStatus"), DeleteFlag.YES));
                } else if (BargainStatus.STOP.equals(bargainStatus)) {
                    predicates.add(cbuild.and(
                            cbuild.isNull(root.get("orderId")),
                            cbuild.or(
                                    cbuild.and(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()), cbuild.notEqual(root.get("joinNum"), root.get("targetJoinNum"))),
                                    cbuild.equal(bargainGoods.get("stoped"), Boolean.TRUE),
                                    cbuild.lessThanOrEqualTo(bargainGoods.get("leaveStock"), 0),
                                    cbuild.notEqual(bargainGoods.get("goodsStatus"), DeleteFlag.YES),
                                    cbuild.lessThan(bargainGoods.get("endTime"), LocalDateTime.now())
                            )
                    ));
                } else if (BargainStatus.COMPLETED.equals(bargainStatus)) {
                    //如果订单Id是空则验证 活动时间
                    predicates.add(
                            cbuild.or(
                                    cbuild.isNotNull(root.get("orderId")),
                                    cbuild.and(
                                            cbuild.isNull(root.get("orderId")),
                                            cbuild.equal(root.get("joinNum"), root.get("targetJoinNum")),
                                            cbuild.equal(bargainGoods.get("stoped"), Boolean.FALSE),
                                            cbuild.gt(bargainGoods.get("leaveStock"), 0),
                                            cbuild.equal(bargainGoods.get("goodsStatus"), DeleteFlag.YES),
                                            cbuild.greaterThanOrEqualTo(bargainGoods.get("endTime"), LocalDateTime.now()))
                                    )
                    );
                }
            }

            // bargainId
            if (queryRequest.getBargainId() != null) {
                predicates.add(cbuild.equal(root.get("bargainId"), queryRequest.getBargainId()));
            }

            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 砍价编号
            if (queryRequest.getBargainNo() != null) {
                predicates.add(cbuild.equal(root.get("bargainNo"), queryRequest.getBargainNo()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getBargainGoodsIds())) {
                predicates.add(root.get("bargainGoodsId").in(queryRequest.getBargainGoodsIds()));
            }

            // 砍价商品skuIds
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }

            // 大于或等于 搜索条件:发起时间开始
            if (queryRequest.getBeginTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeBegin()));
            }
            // 小于或等于 搜索条件:发起时间截止
            if (queryRequest.getBeginTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("beginTime"),
                        queryRequest.getBeginTimeEnd()));
            }

            // 大于或等于 搜索条件:结束时间开始
            if (queryRequest.getEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeBegin()));
            }
            // 小于或等于 搜索条件:结束时间截止
            if (queryRequest.getEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTimeEnd()));
            }

            // 模糊查询 - 发起人id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
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

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
