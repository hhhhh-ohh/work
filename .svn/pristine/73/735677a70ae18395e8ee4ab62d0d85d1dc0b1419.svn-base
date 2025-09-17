package com.wanmi.sbc.goods.grouponsharerecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordQueryRequest;
import com.wanmi.sbc.goods.grouponsharerecord.model.root.GrouponShareRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>拼团分享访问记录动态查询条件构建器</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
public class GrouponShareRecordWhereCriteriaBuilder {
    public static Specification<GrouponShareRecord> build(GrouponShareRecordQueryRequest queryRequest) {
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

            // 模糊查询 - 拼团活动ID
            if (StringUtils.isNotEmpty(queryRequest.getGrouponActivityId())) {
                predicates.add(cbuild.like(root.get("grouponActivityId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGrouponActivityId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员Id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - SPU id
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.like(root.get("goodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - SKU id
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.like(root.get("goodsInfoId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 店铺ID
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 公司信息ID
            if (queryRequest.getCompanyInfoId() != null) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), queryRequest.getCompanyInfoId()));
            }

            // 终端：1 H5，2pc，3APP，4小程序
            if (queryRequest.getTerminalSource() != null) {
                predicates.add(cbuild.equal(root.get("terminalSource"), queryRequest.getTerminalSource()));
            }

            // 分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片
            if (queryRequest.getShareChannel() != null) {
                predicates.add(cbuild.equal(root.get("shareChannel"), queryRequest.getShareChannel()));
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

            // 模糊查询 - 分享人，通过分享链接访问的时候
            if (StringUtils.isNotEmpty(queryRequest.getShareCustomerId())) {
                predicates.add(cbuild.like(root.get("shareCustomerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getShareCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 0分享拼团，1通过分享链接访问拼团
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
