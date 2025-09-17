package com.wanmi.sbc.setting.recommend.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.setting.api.request.recommend.RecommendQueryRequest;
import com.wanmi.sbc.setting.recommend.model.root.Recommend;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>种草信息表动态查询条件构建器</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
public class RecommendWhereCriteriaBuilder {
    public static Specification<Recommend> build(RecommendQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 批量查询-saveStatusList
            if (CollectionUtils.isNotEmpty(queryRequest.getSaveStatusList())) {
                predicates.add(root.get("saveStatus").in(queryRequest.getSaveStatusList()));
            }

            // id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 标题
            if (StringUtils.isNotEmpty(queryRequest.getTitle())) {
                List<Predicate> predicates1 = new ArrayList<>();
                predicates1.add(cbuild.like(root.get("newTitle"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
                predicates1.add(cbuild.and(cbuild.like(root.get("title"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)),cbuild.isNull(root.get("newTitle"))));
                predicates.add(cbuild.or(predicates1.toArray(new Predicate[0])));
            }

            // 分类id
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            // 模糊查询 - 封面
            if (StringUtils.isNotEmpty(queryRequest.getCoverImg())) {
                predicates.add(cbuild.like(root.get("coverImg"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCoverImg()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 视频
            if (StringUtils.isNotEmpty(queryRequest.getVideo())) {
                predicates.add(cbuild.like(root.get("video"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getVideo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 视频封面类型 1;原视频封面 2:自定义封面
            if (queryRequest.getVideoCoverType() != null) {
                predicates.add(cbuild.equal(root.get("videoCoverType"), queryRequest.getVideoCoverType()));
            }

            // 模糊查询 - 视频封面图片
            if (StringUtils.isNotEmpty(queryRequest.getVideoCoverImg())) {
                predicates.add(cbuild.like(root.get("videoCoverImg"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getVideoCoverImg()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 发布时间是否展示 1:是 0:否
            if (queryRequest.getCreateTimeType() != null) {
                predicates.add(cbuild.equal(root.get("createTimeType"), queryRequest.getCreateTimeType()));
            }

            // 阅读数是否展示 1:是 0:否
            if (queryRequest.getReadType() != null) {
                predicates.add(cbuild.equal(root.get("readType"), queryRequest.getReadType()));
            }

            // 阅读数
            if (queryRequest.getReadNum() != null) {
                predicates.add(cbuild.equal(root.get("readNum"), queryRequest.getReadNum()));
            }

            // 点赞数是否展示 1:是 0:否
            if (queryRequest.getFabulousType() != null) {
                predicates.add(cbuild.equal(root.get("fabulousType"), queryRequest.getFabulousType()));
            }

            // 点赞数
            if (queryRequest.getFabulousNum() != null) {
                predicates.add(cbuild.equal(root.get("fabulousNum"), queryRequest.getFabulousNum()));
            }

            // 转发数是否展示 1:是 0:否
            if (queryRequest.getForwardType() != null) {
                predicates.add(cbuild.equal(root.get("forwardType"), queryRequest.getForwardType()));
            }

            // 转发数
            if (queryRequest.getForwardNum() != null) {
                predicates.add(cbuild.equal(root.get("forwardNum"), queryRequest.getForwardNum()));
            }

            // 访客数
            if (queryRequest.getVisitorNum() != null) {
                predicates.add(cbuild.equal(root.get("visitorNum"), queryRequest.getVisitorNum()));
            }

            // 是否置顶 0:否 1:是
            if (queryRequest.getIsTop() != null) {
                predicates.add(cbuild.equal(root.get("isTop"), queryRequest.getIsTop()));
            }

            // 大于或等于 搜索条件:置顶时间开始
            if (queryRequest.getTopTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("topTime"),
                        queryRequest.getTopTimeBegin()));
            }
            // 小于或等于 搜索条件:置顶时间截止
            if (queryRequest.getTopTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("topTime"),
                        queryRequest.getTopTimeEnd()));
            }

            // 内容状态 0:隐藏 1:公开
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
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

            // 是否删除标志 0：否，1：是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
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
