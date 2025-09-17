package com.wanmi.sbc.customer.payingmemberlevel.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelQueryRequest;
import com.wanmi.sbc.customer.payingmemberlevel.model.root.PayingMemberLevel;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>付费会员等级表动态查询条件构建器</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
public class PayingMemberLevelWhereCriteriaBuilder {
    public static Specification<PayingMemberLevel> build(PayingMemberLevelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-付费会员等级idList
            if (CollectionUtils.isNotEmpty(queryRequest.getLevelIdList())) {
                predicates.add(root.get("levelId").in(queryRequest.getLevelIdList()));
            }

            // 付费会员等级id
            if (queryRequest.getLevelId() != null) {
                predicates.add(cbuild.equal(root.get("levelId"), queryRequest.getLevelId()));
            }

            // 模糊查询 - 付费会员等级名称
            if (StringUtils.isNotEmpty(queryRequest.getLevelName())) {
                predicates.add(cbuild.like(root.get("levelName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLevelName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 付费会员等级昵称
            if (StringUtils.isNotEmpty(queryRequest.getLevelNickName())) {
                predicates.add(cbuild.like(root.get("levelNickName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLevelNickName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 付费会员等级状态 0.开启 1.暂停
            if (queryRequest.getLevelState() != null) {
                predicates.add(cbuild.equal(root.get("levelState"), queryRequest.getLevelState()));
            }

            // 付费会员等级背景类型：0.背景色 1.背景图
            if (queryRequest.getLevelBackGroundType() != null) {
                predicates.add(cbuild.equal(root.get("levelBackGroundType"), queryRequest.getLevelBackGroundType()));
            }

            // 模糊查询 - 付费会员等级背景详情
            if (StringUtils.isNotEmpty(queryRequest.getLevelBackGroundDetail())) {
                predicates.add(cbuild.like(root.get("levelBackGroundDetail"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLevelBackGroundDetail()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 付费会员等级字体颜色
            if (StringUtils.isNotEmpty(queryRequest.getLevelFontColor())) {
                predicates.add(cbuild.like(root.get("levelFontColor"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLevelFontColor()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 付费会员等级商家范围：0.自营商家 1.自定义选择
            if (queryRequest.getLevelStoreRange() != null) {
                predicates.add(cbuild.equal(root.get("levelStoreRange"), queryRequest.getLevelStoreRange()));
            }

            // 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
            if (queryRequest.getLevelDiscountType() != null) {
                predicates.add(cbuild.equal(root.get("levelDiscountType"), queryRequest.getLevelDiscountType()));
            }

            // 付费会员等级所有商品统一设置 折扣
            if (queryRequest.getLevelAllDiscount() != null) {
                predicates.add(cbuild.equal(root.get("levelAllDiscount"), queryRequest.getLevelAllDiscount()));
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

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
