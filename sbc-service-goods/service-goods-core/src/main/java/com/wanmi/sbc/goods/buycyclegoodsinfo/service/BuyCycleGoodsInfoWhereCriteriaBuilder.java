package com.wanmi.sbc.goods.buycyclegoodsinfo.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoQueryRequest;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>周期购sku表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
public class BuyCycleGoodsInfoWhereCriteriaBuilder {
    public static Specification<BuyCycleGoodsInfo> build(BuyCycleGoodsInfoQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-skuIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIdList())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIdList()));
            }

            // skuId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.equal(root.get("goodsInfoId"), queryRequest.getGoodsInfoId()));
            }

            // 模糊查询 - spuId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.like(root.get("goodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 周期购id
            if (Objects.nonNull(queryRequest.getBuyCycleId())) {
                predicates.add(cbuild.equal(root.get("buyCycleId"), queryRequest.getBuyCycleId()));
            }

            // 批量查询-周期购id
            if (CollectionUtils.isNotEmpty(queryRequest.getBuyCycleIdList())) {
                predicates.add(root.get("buyCycleId").in(queryRequest.getBuyCycleIdList()));
            }

            // 最低期数
            if (queryRequest.getMinCycleNum() != null) {
                predicates.add(cbuild.equal(root.get("minCycleNum"), queryRequest.getMinCycleNum()));
            }

            // 每期价格
            if (queryRequest.getCyclePrice() != null) {
                predicates.add(cbuild.equal(root.get("cyclePrice"), queryRequest.getCyclePrice()));
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

            // 模糊查询 - createPerson
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 周期购商品状态 0：生效；1：失效
            if (queryRequest.getCycleState() != null) {
                predicates.add(cbuild.equal(root.get("cycleState"), queryRequest.getCycleState()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
