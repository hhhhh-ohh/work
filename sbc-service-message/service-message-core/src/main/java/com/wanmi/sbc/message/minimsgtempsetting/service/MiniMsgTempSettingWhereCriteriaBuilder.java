package com.wanmi.sbc.message.minimsgtempsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingQueryRequest;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>小程序订阅消息模版配置表动态查询条件构建器</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
public class MiniMsgTempSettingWhereCriteriaBuilder {
    public static Specification<MiniMsgTempSetting> build(MiniMsgTempSettingQueryRequest queryRequest) {
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

            // 模糊查询 - 模版ID
            if (StringUtils.isNotEmpty(queryRequest.getTemplateId())) {
                predicates.add(cbuild.like(root.get("templateId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTemplateId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 批量查询-templateIds
            if (CollectionUtils.isNotEmpty(queryRequest.getTemplateIds())) {
                predicates.add(root.get("templateId").in(queryRequest.getTemplateIds()));
            }

            // 模糊查询 - 模版名称
            if (StringUtils.isNotEmpty(queryRequest.getTemplateName())) {
                predicates.add(cbuild.like(root.get("templateName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTemplateName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 类目ID
            if (StringUtils.isNotEmpty(queryRequest.getCategoryId())) {
                predicates.add(cbuild.like(root.get("categoryId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCategoryId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 类目名称
            if (StringUtils.isNotEmpty(queryRequest.getCategoryName())) {
                predicates.add(cbuild.like(root.get("categoryName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCategoryName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 关键词
            if (StringUtils.isNotEmpty(queryRequest.getKeyword())) {
                predicates.add(cbuild.like(root.get("keyword"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getKeyword()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
            if (queryRequest.getTriggerNodeId() != null) {
                predicates.add(cbuild.equal(root.get("triggerNodeId"), queryRequest.getTriggerNodeId()));
            }

            // 模糊查询 - 触发节点名称
            if (StringUtils.isNotEmpty(queryRequest.getTriggerNodeName())) {
                predicates.add(cbuild.like(root.get("triggerNodeName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTriggerNodeName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 温馨提示
            if (StringUtils.isNotEmpty(queryRequest.getTips())) {
                predicates.add(cbuild.like(root.get("tips"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTips()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 温馨提示-提供修改
            if (StringUtils.isNotEmpty(queryRequest.getNewTips())) {
                predicates.add(cbuild.like(root.get("newTips"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getNewTips()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 要跳转的页面
            if (StringUtils.isNotEmpty(queryRequest.getToPage())) {
                predicates.add(cbuild.like(root.get("toPage"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getToPage()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 模版标题ID
            if (StringUtils.isNotEmpty(queryRequest.getTid())) {
                predicates.add(cbuild.like(root.get("tid"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTid()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合
            if (StringUtils.isNotEmpty(queryRequest.getKidList())) {
                predicates.add(cbuild.like(root.get("kidList"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getKidList()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }
            if (Objects.isNull(queryRequest.getInitFlag()) || Boolean.FALSE.equals(queryRequest.getInitFlag())){
                predicates.add(root.get("templateId").isNotNull());
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
