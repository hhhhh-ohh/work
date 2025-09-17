package com.wanmi.sbc.goods.goodspropertydetail.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailQueryRequest;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品属性值动态查询条件构建器</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
public class GoodsPropertyDetailWhereCriteriaBuilder {
    public static Specification<GoodsPropertyDetail> build(GoodsPropertyDetailQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-属性值idList
            if (CollectionUtils.isNotEmpty(queryRequest.getDetailIdList())) {
                predicates.add(root.get("detailId").in(queryRequest.getDetailIdList()));
            }

            // 属性值id
            if (queryRequest.getDetailId() != null) {
                predicates.add(cbuild.equal(root.get("detailId"), queryRequest.getDetailId()));
            }

            // 属性id外键
            if (queryRequest.getPropId() != null) {
                predicates.add(cbuild.equal(root.get("propId"), queryRequest.getPropId()));
            }

            // 批量查询-属性idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPropIdList())) {
                predicates.add(root.get("propId").in(queryRequest.getPropIdList()));
            }

            // 模糊查询 - 属性值
            if (StringUtils.isNotEmpty(queryRequest.getDetailName())) {
                predicates.add(cbuild.like(root.get("detailName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDetailName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 排序
            if (queryRequest.getDetailSort() != null) {
                predicates.add(cbuild.equal(root.get("detailSort"), queryRequest.getDetailSort()));
            }

            // 删除标识,0:未删除1:已删除
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

            // 模糊查询 - 修改人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:删除时间开始
            if (queryRequest.getDeleteTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("deleteTime"),
                        queryRequest.getDeleteTimeBegin()));
            }
            // 小于或等于 搜索条件:删除时间截止
            if (queryRequest.getDeleteTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("deleteTime"),
                        queryRequest.getDeleteTimeEnd()));
            }

            // 模糊查询 - 删除人
            if (StringUtils.isNotEmpty(queryRequest.getDeletePerson())) {
                predicates.add(cbuild.like(root.get("deletePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeletePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
