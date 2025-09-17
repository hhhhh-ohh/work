package com.wanmi.sbc.customer.ledgeraccount.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountQueryRequest;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>清分账户动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
public class LedgerAccountWhereCriteriaBuilder {
    public static Specification<LedgerAccount> build(LedgerAccountQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 商户或接收方id
            if (StringUtils.isNotEmpty(queryRequest.getBusinessId())) {
                predicates.add(cbuild.equal(root.get("businessId"), queryRequest.getBusinessId()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getBusinessIds())) {
                predicates.add(root.get("businessId").in(queryRequest.getBusinessIds()));
            }

            // 模糊查询 - 外部系统商户标识
            if (StringUtils.isNotEmpty(queryRequest.getThirdMemNo())) {
                predicates.add(cbuild.like(root.get("thirdMemNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getThirdMemNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 营业执照名称
            if (StringUtils.isNotEmpty(queryRequest.getMerBlisName())) {
                predicates.add(cbuild.like(root.get("merBlisName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerBlisName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 注册地址
            if (StringUtils.isNotEmpty(queryRequest.getMerRegDistCode())) {
                predicates.add(cbuild.like(root.get("merRegDistCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerRegDistCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 详情地址
            if (StringUtils.isNotEmpty(queryRequest.getMerRegAddr())) {
                predicates.add(cbuild.like(root.get("merRegAddr"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerRegAddr()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 营业执照号
            if (StringUtils.isNotEmpty(queryRequest.getMerBlis())) {
                predicates.add(cbuild.like(root.get("merBlis"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerBlis()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:营业执照开始时间开始
            if (queryRequest.getMerBlisStDtBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("merBlisStDt"),
                        queryRequest.getMerBlisStDtBegin()));
            }
            // 小于或等于 搜索条件:营业执照开始时间截止
            if (queryRequest.getMerBlisStDtEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("merBlisStDt"),
                        queryRequest.getMerBlisStDtEnd()));
            }

            // 大于或等于 搜索条件:营业执照有效期结束时间开始
            if (queryRequest.getMerBlisExpDtBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("merBlisExpDt"),
                        queryRequest.getMerBlisExpDtBegin()));
            }
            // 小于或等于 搜索条件:营业执照有效期结束时间截止
            if (queryRequest.getMerBlisExpDtEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("merBlisExpDt"),
                        queryRequest.getMerBlisExpDtEnd()));
            }

            // 模糊查询 - 商户经营内容
            if (StringUtils.isNotEmpty(queryRequest.getMerBusiContent())) {
                predicates.add(cbuild.like(root.get("merBusiContent"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerBusiContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 法人姓名
            if (StringUtils.isNotEmpty(queryRequest.getLarName())) {
                predicates.add(cbuild.like(root.get("larName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLarName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 法人身份证号
            if (StringUtils.isNotEmpty(queryRequest.getLarIdCard())) {
                predicates.add(cbuild.like(root.get("larIdCard"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLarIdCard()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:身份证开始日期开始
            if (queryRequest.getLarIdCardStDtBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("larIdCardStDt"),
                        queryRequest.getLarIdCardStDtBegin()));
            }
            // 小于或等于 搜索条件:身份证开始日期截止
            if (queryRequest.getLarIdCardStDtEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("larIdCardStDt"),
                        queryRequest.getLarIdCardStDtEnd()));
            }

            // 大于或等于 搜索条件:身份证有效期结束时间开始
            if (queryRequest.getLarIdCardExpDtBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("larIdCardExpDt"),
                        queryRequest.getLarIdCardExpDtBegin()));
            }
            // 小于或等于 搜索条件:身份证有效期结束时间截止
            if (queryRequest.getLarIdCardExpDtEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("larIdCardExpDt"),
                        queryRequest.getLarIdCardExpDtEnd()));
            }

            // 模糊查询 - 商户联系人
            if (StringUtils.isNotEmpty(queryRequest.getMerContactName())) {
                predicates.add(cbuild.like(root.get("merContactName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerContactName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商户联系人手机号
            if (StringUtils.isNotEmpty(queryRequest.getMerContactMobile())) {
                predicates.add(cbuild.like(root.get("merContactMobile"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerContactMobile()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户名称
            if (StringUtils.isNotEmpty(queryRequest.getAcctName())) {
                predicates.add(cbuild.like(root.get("acctName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAcctName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户卡号
            if (StringUtils.isNotEmpty(queryRequest.getAcctNo())) {
                predicates.add(cbuild.like(root.get("acctNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAcctNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户证件号
            if (StringUtils.isNotEmpty(queryRequest.getAcctCertificateNo())) {
                predicates.add(cbuild.like(root.get("acctCertificateNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAcctCertificateNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户开户行号
            if (StringUtils.isNotEmpty(queryRequest.getOpenningBankCode())) {
                predicates.add(cbuild.like(root.get("openningBankCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getOpenningBankCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户开户行名称
            if (StringUtils.isNotEmpty(queryRequest.getOpenningBankName())) {
                predicates.add(cbuild.like(root.get("openningBankName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getOpenningBankName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 账户清算行号
            if (StringUtils.isNotEmpty(queryRequest.getClearingBankCode())) {
                predicates.add(cbuild.like(root.get("clearingBankCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getClearingBankCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 法人身份证正面
            if (StringUtils.isNotEmpty(queryRequest.getIdCardFrontPic())) {
                predicates.add(cbuild.like(root.get("idCardFrontPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getIdCardFrontPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 法人身份证背面
            if (StringUtils.isNotEmpty(queryRequest.getIdCardBackPic())) {
                predicates.add(cbuild.like(root.get("idCardBackPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getIdCardBackPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 银行卡
            if (StringUtils.isNotEmpty(queryRequest.getBankCardPic())) {
                predicates.add(cbuild.like(root.get("bankCardPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBankCardPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 营业执照
            if (StringUtils.isNotEmpty(queryRequest.getBusinessPic())) {
                predicates.add(cbuild.like(root.get("businessPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBusinessPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商户门头照
            if (StringUtils.isNotEmpty(queryRequest.getMerchantPic())) {
                predicates.add(cbuild.like(root.get("merchantPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMerchantPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商户内部照
            if (StringUtils.isNotEmpty(queryRequest.getShopinnerPic())) {
                predicates.add(cbuild.like(root.get("shopinnerPic"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getShopinnerPic()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败
            if (queryRequest.getAccountState() != null) {
                predicates.add(cbuild.equal(root.get("accountState"), queryRequest.getAccountState()));
            }

            // 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
            if (queryRequest.getLedgerState() != null) {
                predicates.add(cbuild.equal(root.get("ledgerState"), queryRequest.getLedgerState()));
            }

            // 模糊查询 - 开户驳回原因
            if (StringUtils.isNotEmpty(queryRequest.getAccountRejectReason())) {
                predicates.add(cbuild.like(root.get("accountRejectReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAccountRejectReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 分账驳回原因
            if (StringUtils.isNotEmpty(queryRequest.getLedgerRejectReason())) {
                predicates.add(cbuild.like(root.get("ledgerRejectReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLedgerRejectReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 账户类型 0、商户 1、接收方
            if (queryRequest.getAccountType() != null) {
                predicates.add(cbuild.equal(root.get("accountType"), queryRequest.getAccountType()));
            }

            // 接收方类型 0、平台 1、供应商 2、分销员
            if (queryRequest.getReceiverType() != null) {
                predicates.add(cbuild.equal(root.get("receiverType"), queryRequest.getReceiverType()));
            }

            // 模糊查询 - 自定义业务编号
            if (StringUtils.isNotEmpty(queryRequest.getContractId())) {
                predicates.add(cbuild.like(root.get("contractId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContractId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 电子合同受理号
            if (StringUtils.isNotEmpty(queryRequest.getEcApplyId())) {
                predicates.add(cbuild.like(root.get("ecApplyId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getEcApplyId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 待签约的电子合同链接
            if (StringUtils.isNotEmpty(queryRequest.getEcUrl())) {
                predicates.add(cbuild.like(root.get("ecUrl"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getEcUrl()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 电子合同文件
            if (StringUtils.isNotEmpty(queryRequest.getEcContent())) {
                predicates.add(cbuild.like(root.get("ecContent"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getEcContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否删除 0、未删除 1、已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
