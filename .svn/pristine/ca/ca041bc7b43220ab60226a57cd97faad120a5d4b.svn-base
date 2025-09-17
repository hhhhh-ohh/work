package com.wanmi.sbc.setting.thirdexpresscompany.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description 第三方代销平台物流公司条件构建
 * @author malianfeng
 * @date 2022/4/26 17:37
 */
public class ThirdExpressCompanyWhereCriteriaBuilder {
    public static Specification<ThirdExpressCompany> build(ThirdExpressCompanyQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键IDList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键ID
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 物流公司名称
            if (StringUtils.isNotEmpty(queryRequest.getExpressName())) {
                predicates.add(cbuild.like(root.get("expressName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getExpressName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 物流公司代码
            if (StringUtils.isNotEmpty(queryRequest.getExpressCode())) {
                predicates.add(cbuild.like(root.get("expressCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getExpressCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 所属第三方代销平台类型
            if (Objects.nonNull(queryRequest.getSellPlatformType())) {
                predicates.add(cbuild.equal(root.get("sellPlatformType"), queryRequest.getSellPlatformType()));
            }

            // 删除标志 默认0：未删除 1：删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[0]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
