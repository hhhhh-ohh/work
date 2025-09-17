package com.wanmi.sbc.customer.ledger.lakala.builder;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.*;
import com.wanmi.sbc.empower.api.response.ledger.lakala.CardBinResponse;
import com.wanmi.sbc.empower.bean.enums.AttType;

import java.time.LocalDate;
import java.util.*;

/**
 * @author xuyunpeng
 * @className LakalaParamService
 * @description
 * @date 2022/7/8 2:21 PM
 **/
public class LakalaParamBuilder {

    public static final String YES = "是";

    public static final String CONTRACT_NAME = "分账合作协议";

    public static final String PUBLIC_ATTC_CODE = "57";

    public static final String PRIVATE_ATTC_CODE = "58";

    public static final String E_4 = "本商户";

    /**
     * 组装文件map
     * @param accountVO
     * @param files
     * @return
     */
    public static Map<AttType, LedgerFile> fileContents(LedgerAccountVO accountVO, List<LedgerFile> files) {
        Map<AttType, LedgerFile> map = new HashMap<>();
        for (LedgerFile file : files) {
            if (accountVO.getBusinessPic() != null && file.getId().equals(accountVO.getBusinessPic())) {
                map.put(AttType.BUSINESS_LICENCE, file);
            }
            if (accountVO.getMerchantPic() != null && file.getId().equals(accountVO.getMerchantPic())) {
                map.put(AttType.MERCHANT_PHOTO, file);
            }
            if (accountVO.getShopinnerPic() != null && file.getId().equals(accountVO.getShopinnerPic())) {
                map.put(AttType.SHOPINNER, file);
            }
            if (accountVO.getBankCardPic() != null && file.getId().equals(accountVO.getBankCardPic())) {
                map.put(AttType.BANK_CARD, file);
            }
            if (accountVO.getIdCardFrontPic() != null && file.getId().equals(accountVO.getIdCardFrontPic())) {
                map.put(AttType.ID_CARD_FRONT, file);
            }
            if (accountVO.getIdCardBackPic() != null && file.getId().equals(accountVO.getIdCardBackPic())) {
                map.put(AttType.ID_CARD_BEHIND, file);
            }
        }

        return map;
    }

    /**
     * 获取文件id
     * @param accountVO
     * @return
     */
    public static List<String> fileIds(LedgerAccountVO accountVO) {
        List<String> list = new ArrayList<>();
        if (accountVO.getBusinessPic() != null) {
            list.add(accountVO.getBusinessPic());
        }
        if (accountVO.getMerchantPic() != null) {
            list.add(accountVO.getMerchantPic());
        }
        if (accountVO.getShopinnerPic() != null) {
            list.add(accountVO.getShopinnerPic());
        }
        if (accountVO.getBankCardPic() != null) {
            list.add(accountVO.getBankCardPic());
        }
        if (accountVO.getIdCardFrontPic() != null) {
            list.add(accountVO.getIdCardFrontPic());
        }
        if (accountVO.getIdCardBackPic() != null) {
            list.add(accountVO.getIdCardBackPic());
        }
        return list;
    }

    /**
     * 组装参数-商户进件
     * @param accountVO
     * @param fileDataList
     * @return
     */
    public static LakalaAddMerRequest buildAddMerRequest(LedgerAccountVO accountVO, Set<FileData> fileDataList) {

        LakalaAddMerRequest request = LakalaAddMerRequest.builder().build();
        request.setMerRegName(accountVO.getMerBlisName());
        request.setMerRegDistCode(accountVO.getMerRegDistCode());
        request.setMerRegAddr(accountVO.getMerRegAddr());
        request.setMccCode(accountVO.getMccCode());
        request.setMerBlisName(accountVO.getMerBlisName());
        request.setMerBlis(accountVO.getMerBlis());
        request.setMerBlisStDt(DateUtil.format(accountVO.getMerBlisStDt(), DateUtil.FMT_DATE_1));
        request.setMerBlisExpDt(DateUtil.format(accountVO.getMerBlisExpDt(), DateUtil.FMT_DATE_1));
        request.setMerBusiContent(accountVO.getMerBusiContent());
        request.setLarName(accountVO.getLarName());
        request.setLarIdcard(accountVO.getLarIdCard());
        request.setLarIdcardStDt(DateUtil.format(accountVO.getLarIdCardStDt(), DateUtil.FMT_DATE_1));
        request.setLarIdcardExpDt(DateUtil.format(accountVO.getLarIdCardExpDt(), DateUtil.FMT_DATE_1));
        request.setMerContactMobile(accountVO.getMerContactMobile());
        request.setMerContactName(accountVO.getMerContactName());
        request.setOpenningBankCode(accountVO.getOpenningBankCode());
        request.setOpenningBankName(accountVO.getOpenningBankName());
        request.setClearingBankCode(accountVO.getClearingBankCode());
        request.setAcctNo(accountVO.getAcctNo());
        request.setAcctName(accountVO.getAcctName());
        request.setFileData(fileDataList);
        request.setContractNo(accountVO.getEcNo());

        return request;
    }

    /**
     * 组装参数-创建接收方
     * @param account
     * @param fileDataList
     * @param cardBinResponse
     * @return
     */
    public static LakalaApplySplitReceiverRequest buildReceiverRequest(LedgerAccountVO account, List<Attach> fileDataList, CardBinResponse cardBinResponse) {
        LakalaApplySplitReceiverRequest request = LakalaApplySplitReceiverRequest.builder().build();

        if (LedgerReceiverType.DISTRIBUTION.toValue() == account.getReceiverType()) {
            request.setReceiverName(account.getLarName());
            request.setContactMobile(account.getMerContactMobile());
            request.setAcctNo(account.getAcctNo());
            request.setAcctName(account.getLarName());
            request.setAcctTypeCode(PRIVATE_ATTC_CODE);
            request.setAcctCertificateNo(account.getLarIdCard());
            request.setAcctOpenBankName(account.getOpenningBankName());
            request.setAcctOpenBankCode(cardBinResponse.getBankCode());
            request.setAttachList(fileDataList);
        } else {
            request.setReceiverName(account.getMerBlisName());
            request.setContactMobile(account.getMerContactMobile());
            request.setLicenseNo(account.getMerBlis());
            request.setLicenseName(account.getMerBlisName());
            request.setLegalPersonName(account.getLarName());
            request.setLegalPersonCertificateNo(account.getLarIdCard());
            request.setAcctNo(account.getAcctNo());
            request.setAcctName(account.getAcctName());
            request.setAcctTypeCode(PUBLIC_ATTC_CODE);
            request.setAcctCertificateNo(account.getAcctCertificateNo());
            request.setAcctOpenBankCode(account.getOpenningBankCode());
            request.setAcctOpenBankName(account.getOpenningBankName());
            request.setAttachList(fileDataList);
        }

        return request;
    }

    public static Ec buildEc(LedgerAccount account, String receiveName, String merBusiContent, String systemCompanyName) {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        String day = String.valueOf(now.getDayOfMonth());

        Ec ec = new Ec();
        ec.setA1(account.getMerBlisName());
        ec.setA2(YES);
        ec.setA26(YES);
        ec.setA29(YES);
        ec.setA34(YES);
        ec.setA36(YES);
        ec.setA38(YES);
        ec.setA63("全国");
        ec.setA64(receiveName);
        ec.setA67(year);
        ec.setA68(month);
        ec.setA69(day);
        ec.setA70(year);
        ec.setA71(month);
        ec.setA72(day);

        ec.setB1(year);
        ec.setB2(month);
        ec.setB3(YES);

        ec.setB8(account.getMerBlisName());
        ec.setB9(merBusiContent);
        ec.setB10(account.getMerBlisName());
        ec.setB14(account.getMerBlis());
        ec.setB16(YES);
        ec.setB19(account.getOpenningBankName());
        ec.setB20(account.getAcctNo());

        ec.setB24(account.getLarName());
        ec.setB25(account.getLarIdCard());
        ec.setB26(account.getMerContactMobile());
        ec.setB27(account.getLarName());
        ec.setB28(account.getEmail());
        ec.setB29(account.getLarIdCard());
        ec.setB30(account.getMerContactMobile());
        ec.setB37(YES);
        ec.setB48(YES);
        ec.setB52(YES);
        ec.setD1(DateUtil.format(now, DateUtil.FMT_DATE_1));

        ec.setE1(receiveName);
        ec.setE2(CONTRACT_NAME);
        ec.setE3(String.valueOf(Constants.NUM_3));
        ec.setE4(E_4);
        ec.setE13(receiveName);
        ec.setE14(String.valueOf(Constants.NUM_70));
        ec.setE15(YES);
        ec.setE17(receiveName);
        ec.setE19(DateUtil.format(now, DateUtil.FMT_DATE_1));
        return ec;
    }

    public static Ec003 buildEc003(String supplierName, String systemCompanyName, String receiverName) {
        LocalDate now = LocalDate.now();

        Ec003 ec = new Ec003();
        ec.setA1(receiverName);
        ec.setA2(CONTRACT_NAME);
        ec.setA3(String.valueOf(Constants.NUM_3));
        ec.setA4(E_4);
        ec.setA13(receiverName);
        ec.setA14(String.valueOf(Constants.NUM_70));
        ec.setA15(YES);
        ec.setA17(receiverName);
        ec.setA19(DateUtil.format(LocalDate.now(), DateUtil.FMT_DATE_1));
        ec.setB1(DateUtil.format(now, DateUtil.FMT_DATE_1));
        return ec;
    }
}
