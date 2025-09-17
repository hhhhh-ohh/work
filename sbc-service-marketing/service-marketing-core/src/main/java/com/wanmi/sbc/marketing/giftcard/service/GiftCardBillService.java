package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillForUserPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillQueryRequest;
import com.wanmi.sbc.marketing.bean.constant.MarketingCommonErrorCode;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBill;
import com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard;
import com.wanmi.sbc.marketing.giftcard.repository.GiftCardBillRepository;
import com.wanmi.sbc.marketing.giftcard.repository.UserGiftCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author lvzhenwei
 * @className GiftCardBillService
 * @description 礼品卡使用记录
 * @date 2022/12/10 5:42 下午
 **/
@Service
public class GiftCardBillService {

    @Autowired UserGiftCardRepository userGiftCardRepository;

    @Autowired GiftCardBillRepository giftCardBillRepository;


     /**
      * @description 分页查询会员礼品卡使用记录
      * @author  lvzhenwei
      * @date 2022/12/12 11:06 上午
      * @param queryReq
      * @return org.springframework.data.domain.Page<com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail>
      **/
    public Page<GiftCardBill> getGiftCardBillPageForUser(GiftCardBillForUserPageRequest queryReq){
        Optional<UserGiftCard> userGiftCardOpt = userGiftCardRepository.getByUserGiftCardId(queryReq.getUserGiftCardId());
        if(!userGiftCardOpt.isPresent() || (userGiftCardOpt.isPresent() && !queryReq.getCustomerId().equals(userGiftCardOpt.get().getCustomerId()))){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
        }
        GiftCardBillQueryRequest queryRequest = new GiftCardBillQueryRequest();
        queryRequest.setCustomerId(queryReq.getCustomerId());
        queryRequest.setUserGiftCardId(queryReq.getUserGiftCardId());
        return giftCardBillRepository.findAll(
                GiftCardBillWhereCriteriaBuilder.build(queryRequest),
                queryReq.getPageRequest());
    }

    /**
     * 分页查询礼品卡交易流水
     * @author 吴瑞
     */
    public Page<GiftCardBill> page(GiftCardBillQueryRequest queryReq){
        return giftCardBillRepository.findAll(
                GiftCardBillWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * @description 查询总数量
     * @author 吴瑞
     */
    public Long count(GiftCardBillQueryRequest queryReq) {
        return giftCardBillRepository.count(GiftCardBillWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * @description 将实体包装成VO
     * @author  lvzhenwei
     * @date 2022/12/12 11:29 上午
     * @param giftCardBill
     * @return com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO
     **/
    public GiftCardBillVO wrapperVo(GiftCardBill giftCardBill) {
        if (giftCardBill != null){
            GiftCardBillVO giftCardBillVO = KsBeanUtil.convert(giftCardBill, GiftCardBillVO.class);
            return giftCardBillVO;
        }
        return null;
    }

}
