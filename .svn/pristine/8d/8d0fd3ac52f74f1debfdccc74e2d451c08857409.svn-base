package com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.service;

import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcatecertificate.WechatCateCertificateQueryRequest;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.model.root.WechatCateCertificate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>微信类目资质动态查询条件构建器</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
public class WechatCateCertificateWhereCriteriaBuilder {
    public static Specification<WechatCateCertificate> build(WechatCateCertificateQueryRequest queryRequest) {
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

            // 微信类目id
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
