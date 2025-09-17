package com.wanmi.sbc.order.leadertradedetail.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailQueryRequest;
import com.wanmi.sbc.order.leadertradedetail.model.root.LeaderTradeDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>团长订单动态查询条件构建器</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
public class LeaderTradeDetailWhereCriteriaBuilder {
    public static Specification<LeaderTradeDetail> build(LeaderTradeDetailQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 查询 - 团长ID
            if (StringUtils.isNotEmpty(queryRequest.getLeaderId())) {
                predicates.add(cbuild.equal(root.get("leaderId"), queryRequest.getLeaderId()));
            }

            // 查询 - 团长的会员ID
            if (StringUtils.isNotEmpty(queryRequest.getLeaderCustomerId())) {
                predicates.add(cbuild.equal(root.get("leaderCustomerId"), queryRequest.getLeaderCustomerId()));
            }

            // 查询 - 社区团购活动ID
            if (StringUtils.isNotEmpty(queryRequest.getCommunityActivityId())) {
                predicates.add(cbuild.equal(root.get("communityActivityId"), queryRequest.getCommunityActivityId()));
            }

            // 查询 - 订单会员ID
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.equal(root.get("customerId"), queryRequest.getCustomerId()));
            }

            // 模糊查询 - 会员名称
            if (StringUtils.isNotEmpty(queryRequest.getCustomerName())) {
                predicates.add(cbuild.like(root.get("customerName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员头像
            if (StringUtils.isNotEmpty(queryRequest.getCustomerPic())) {
                predicates.add(cbuild.like(root.get("customerPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 查询 - 订单ID
            if (StringUtils.isNotEmpty(queryRequest.getTradeId())) {
                predicates.add(cbuild.equal(root.get("tradeId"), queryRequest.getTradeId()));
            }

            // 查询 - 商品ID
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.equal(root.get("goodsInfoId"), queryRequest.getGoodsInfoId()));
            }

            // 模糊查询 - 商品规格
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoSpec())) {
                predicates.add(cbuild.like(root.get("goodsInfoSpec"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoSpec()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品数量
            if (queryRequest.getGoodsInfoNum() != null) {
                predicates.add(cbuild.equal(root.get("goodsInfoNum"), queryRequest.getGoodsInfoNum()));
            }

            // 跟团号
            if (queryRequest.getActivityTradeNo() != null) {
                predicates.add(cbuild.equal(root.get("activityTradeNo"), queryRequest.getActivityTradeNo()));
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

            // 是否删除 
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
