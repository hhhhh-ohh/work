package com.wanmi.sbc.message.storemessagedetail.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailQueryRequest;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>商家消息/公告动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
public class StoreMessageDetailWhereCriteriaBuilder {
    public static Specification<StoreMessageDetail> build(StoreMessageDetailQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 批量查询-joinIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getJoinIdList())) {
                Predicate joinIdListQuery = root.get("joinId").in(queryRequest.getJoinIdList());
                // 是否需要展示公告（用于首页消息，需同时展示公告和消息的场景）
                if (BooleanUtils.isTrue(queryRequest.getIsShowNotice())) {
                    // 如果指定了joinId列表，同时需要展示公告，这里或上(消息类型为公告)的条件
                    Predicate noticeMessageType = cbuild.equal(root.get("messageType"), StoreMessageType.NOTICE);
                    predicates.add(cbuild.or(joinIdListQuery, noticeMessageType));
                } else {
                    predicates.add(joinIdListQuery);
                }
            }

            // 主键id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 消息一级类型 0：消息 1：公告
            if (queryRequest.getMessageType() != null) {
                predicates.add(
                        cbuild.equal(root.get("messageType"), queryRequest.getMessageType()));
            }

            // 商家id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 消息标题
            if (StringUtils.isNotEmpty(queryRequest.getTitle())) {
                predicates.add(
                        cbuild.like(
                                root.get("title"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getTitle()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 消息内容
            if (StringUtils.isNotEmpty(queryRequest.getContent())) {
                predicates.add(
                        cbuild.like(
                                root.get("content"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getContent()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 路由参数，json格式
            if (StringUtils.isNotEmpty(queryRequest.getRouteParam())) {
                predicates.add(
                        cbuild.like(
                                root.get("routeParam"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getRouteParam()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:发送时间开始
            if (queryRequest.getSendTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("sendTime"), queryRequest.getSendTimeBegin()));
            }
            // 小于或等于 搜索条件:发送时间截止
            if (queryRequest.getSendTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("sendTime"), queryRequest.getSendTimeEnd()));
            }

            // 是否已读 0：未读 1：已读
            if (queryRequest.getIsRead() != null) {
                predicates.add(cbuild.equal(root.get("isRead"), queryRequest.getIsRead()));
            }

            // 关联的消息节点id或公告id
            if (queryRequest.getJoinId() != null) {
                predicates.add(cbuild.equal(root.get("joinId"), queryRequest.getJoinId()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("createPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCreatePerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:修改时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:修改时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeEnd()));
            }

            // 删除标识 0：未删除 1：删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
