package com.wanmi.sbc.goods.goodsproperty.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyPageRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyQueryRequest;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品属性动态查询条件构建器</p>
 *
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
public class GoodsPropertyWhereCriteriaBuilder {
    public static Specification<GoodsProperty> build(GoodsPropertyPageRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-属性idList
            if (CollectionUtils.isNotEmpty(queryRequest.getPropIdList())) {
                predicates.add(root.get("propId").in(queryRequest.getPropIdList()));
            }

            // 属性id
            if (queryRequest.getPropId() != null) {
                predicates.add(cbuild.equal(root.get("propId"), queryRequest.getPropId()));
            }

            // 模糊查询 - 属性名称
            if (StringUtils.isNotEmpty(queryRequest.getPropName())) {
                predicates.add(cbuild.like(root.get("propName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 拼音
            if (StringUtils.isNotEmpty(queryRequest.getPropPinYin())) {
                predicates.add(cbuild.like(root.get("propPinYin"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropPinYin()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 简拼
            if (StringUtils.isNotEmpty(queryRequest.getPropShortPinYin())) {
                predicates.add(cbuild.like(root.get("propShortPinYin"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPropShortPinYin()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品发布时属性是否必填
            if (queryRequest.getPropRequired() != null) {
                predicates.add(cbuild.equal(root.get("propRequired"), queryRequest.getPropRequired()));
            }

            // 是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等
            if (queryRequest.getPropCharacter() != null) {
                predicates.add(cbuild.equal(root.get("propCharacter"), queryRequest.getPropCharacter()));
            }

            // 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
            if (queryRequest.getPropType() != null) {
                predicates.add(cbuild.equal(root.get("propType"), queryRequest.getPropType()));
            }

            // 是否开启索引 0否 1是
            if (queryRequest.getIndexFlag() != null) {
                predicates.add(cbuild.equal(root.get("indexFlag"), queryRequest.getIndexFlag()));
            }

            // 删除标识,0:未删除1:已删除

            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            //排序
            cquery.orderBy(cbuild.desc(root.get("propSort")), cbuild.desc(root.get("createTime")));

            Predicate[] p = predicates.toArray(new Predicate[]{});
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
