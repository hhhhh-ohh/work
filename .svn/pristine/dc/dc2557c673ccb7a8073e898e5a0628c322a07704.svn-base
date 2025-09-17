package com.wanmi.sbc.goods.goodspropcaterel.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelQueryRequest;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品类目与属性关联动态查询条件构建器</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
public class GoodsPropCateRelWhereCriteriaBuilder {
    public static Specification<GoodsPropCateRel> build(GoodsPropCateRelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-关联表主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getRelIdList())) {
                predicates.add(root.get("relId").in(queryRequest.getRelIdList()));
            }

            // 关联表主键
            if (queryRequest.getRelId() != null) {
                predicates.add(cbuild.equal(root.get("relId"), queryRequest.getRelId()));
            }

            // 批量查询-属性idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPropIdList())) {
                predicates.add(root.get("propId").in(queryRequest.getPropIdList()));
            }

            // 属性id
            if (queryRequest.getPropId() != null) {
                predicates.add(cbuild.equal(root.get("propId"), queryRequest.getPropId()));
            }

            // 商品分类id
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            // 排序
            if (queryRequest.getRelSort() != null) {
                predicates.add(cbuild.equal(root.get("relSort"), queryRequest.getRelSort()));
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
