package com.wanmi.sbc.empower.apppush.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingQueryRequest;
import com.wanmi.sbc.empower.apppush.model.root.AppPushSetting;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>消息推送配置动态查询条件构建器</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
public class AppPushSettingWhereCriteriaBuilder {
    public static Specification<AppPushSetting> build(AppPushSettingQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 推送平台类型：0：友盟
            if (queryRequest.getPlatformType() != null) {
                predicates.add(cbuild.equal(root.get("platformType"), queryRequest.getPlatformType()));
            }

            // 模糊查询 - 安卓应用的Appkey
            if (StringUtils.isNotEmpty(queryRequest.getAndroidKeyId())) {
                predicates.add(cbuild.like(root.get("androidKeyId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAndroidKeyId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 安卓应用的Message Secret
            if (StringUtils.isNotEmpty(queryRequest.getAndroidMsgSecret())) {
                predicates.add(cbuild.like(root.get("androidMsgSecret"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAndroidMsgSecret()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 安卓应用的服务器秘钥
            if (StringUtils.isNotEmpty(queryRequest.getAndroidKeySecret())) {
                predicates.add(cbuild.like(root.get("androidKeySecret"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAndroidKeySecret()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - IOS应用的Appkey
            if (StringUtils.isNotEmpty(queryRequest.getIosKeyId())) {
                predicates.add(cbuild.like(root.get("iosKeyId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getIosKeyId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - IOS应用的服务器秘钥
            if (StringUtils.isNotEmpty(queryRequest.getIosKeySecret())) {
                predicates.add(cbuild.like(root.get("iosKeySecret"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getIosKeySecret()))
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
