package com.wanmi.sbc.goods.buycyclegoods.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsQueryRequest;
import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>周期购spu表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
public class BuyCycleGoodsWhereCriteriaBuilder {
    public static Specification<BuyCycleGoods> build(BuyCycleGoodsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-spuIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsIdList())) {
                predicates.add(root.get("goodsId").in(queryRequest.getGoodsIdList()));
            }

            // spuId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.equal(root.get("goodsId"), queryRequest.getGoodsId()));
            }


            // 模糊查询商品名称
            if (StringUtils.isNotEmpty(queryRequest.getLikeGoodsName())) {
                predicates.add(cbuild.like(root.get("goodsName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLikeGoodsName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 配送周期
            if (queryRequest.getDeliveryCycle() != null) {
                predicates.add(cbuild.equal(root.get("deliveryCycle"), queryRequest.getDeliveryCycle()));
            }

            // 模糊查询 - 用户可选送达日期
            if (StringUtils.isNotEmpty(queryRequest.getDeliveryDate())) {
                predicates.add(cbuild.like(root.get("deliveryDate"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeliveryDate()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 预留时间 日期
            if (queryRequest.getReserveDay() != null) {
                predicates.add(cbuild.equal(root.get("reserveDay"), queryRequest.getReserveDay()));
            }

            // 周期购商品状态
            if (queryRequest.getCycleState() != null) {
                predicates.add(cbuild.equal(root.get("cycleState"), queryRequest.getCycleState()));
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


            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
