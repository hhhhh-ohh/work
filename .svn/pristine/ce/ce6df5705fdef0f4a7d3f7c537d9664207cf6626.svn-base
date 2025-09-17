package com.wanmi.sbc.empower.miniprogramset.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetQueryRequest;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>小程序配置动态查询条件构建器</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
public class MiniProgramSetWhereCriteriaBuilder {
    public static Specification<MiniProgramSet> build(MiniProgramSetQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-小程序配置主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 小程序配置主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 小程序类型 0 微信小程序
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 模糊查询 - 备注
            if (StringUtils.isNotEmpty(queryRequest.getRemark())) {
                predicates.add(cbuild.like(root.get("remark"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRemark()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 状态,0:未启用1:已启用
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
            }

            // 模糊查询 - 小程序AppID(应用ID)
            if (StringUtils.isNotEmpty(queryRequest.getAppId())) {
                predicates.add(cbuild.like(root.get("appId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAppId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 小程序AppSecret(应用密钥)
            if (StringUtils.isNotEmpty(queryRequest.getAppSecret())) {
                predicates.add(cbuild.like(root.get("appSecret"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAppSecret()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
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

            // 模糊查询 - 更新人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
