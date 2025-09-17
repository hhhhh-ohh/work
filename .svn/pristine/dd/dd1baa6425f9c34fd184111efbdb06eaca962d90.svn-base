package com.wanmi.sbc.goods.goodspropertydetailrel.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelQueryRequest;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品与属性值关联动态查询条件构建器</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
public class GoodsPropertyDetailRelWhereCriteriaBuilder {
    public static Specification<GoodsPropertyDetailRel> build(GoodsPropertyDetailRelQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-detailRelIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getDetailRelIdList())) {
                predicates.add(root.get("detailRelId").in(queryRequest.getDetailRelIdList()));
            }

            // detailRelId
            if (queryRequest.getDetailRelId() != null) {
                predicates.add(cbuild.equal(root.get("detailRelId"), queryRequest.getDetailRelId()));
            }

            // 模糊查询 - 商品id
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.like(root.get("goodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //批量商品id查询
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsIds())) {
                predicates.add(root.get("goodsId").in(queryRequest.getGoodsIds()));
            }

            // 商品分类id
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            // 属性id
            if (queryRequest.getPropId() != null) {
                predicates.add(cbuild.equal(root.get("propId"), queryRequest.getPropId()));
            }

            // 属性值id
            if (queryRequest.getDetailId() != null) {
                predicates.add(cbuild.equal(root.get("detailId"), queryRequest.getDetailId()));
            }

            // 商品类型 0商品 1商品库
            if (queryRequest.getGoodsType() != null) {
                predicates.add(cbuild.equal(root.get("goodsType"), queryRequest.getGoodsType()));
            }

            // 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
            if (queryRequest.getPropType() != null) {
                predicates.add(cbuild.equal(root.get("propType"), queryRequest.getPropType()));
            }

            // 模糊查询 - 输入方式为文本的属性值
            if (StringUtils.isNotEmpty(queryRequest.getPropValueText())) {
                predicates.add(cbuild.like(root.get("propValueText"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropValueText()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:输入方式为日期的属性值开始
            if (queryRequest.getPropValueDateBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("propValueDate"),
                        queryRequest.getPropValueDateBegin()));
            }
            // 小于或等于 搜索条件:输入方式为日期的属性值截止
            if (queryRequest.getPropValueDateEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("propValueDate"),
                        queryRequest.getPropValueDateEnd()));
            }

            // 模糊查询 - 输入方式为省市的属性值，省市id用逗号隔开
            if (StringUtils.isNotEmpty(queryRequest.getPropValueProvince())) {
                predicates.add(cbuild.like(root.get("propValueProvince"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropValueProvince()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 输入方式为国家或地区的属性值，国家和地区用逗号隔开
            if (StringUtils.isNotEmpty(queryRequest.getPropValueCountry())) {
                predicates.add(cbuild.like(root.get("propValueCountry"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropValueCountry()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否删除标志 0：否，1：是
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
