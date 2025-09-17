package com.wanmi.sbc.setting.country.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.setting.api.request.country.PlatformCountryQueryRequest;
import com.wanmi.sbc.setting.country.model.root.PlatformCountry;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>国家地区动态查询条件构建器</p>
 * @author chenli
 * @date 2021-04-27 16:10:00
 */
public class PlatformCountryWhereCriteriaBuilder {
    public static Specification<PlatformCountry> build(PlatformCountryQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键ID,自增List
            if (CollectionUtils.isNotEmpty(queryRequest.getCountryIdList())) {
                predicates.add(root.get("id").in(queryRequest.getCountryIdList()));
            }

            // 主键ID,自增
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 国家地区名称
            if (StringUtils.isNotEmpty(queryRequest.getName())) {
                predicates.add(cbuild.like(root.get("name"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 国家地区简称
            if (StringUtils.isNotEmpty(queryRequest.getShortName())) {
                predicates.add(cbuild.like(root.get("shortName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getShortName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
