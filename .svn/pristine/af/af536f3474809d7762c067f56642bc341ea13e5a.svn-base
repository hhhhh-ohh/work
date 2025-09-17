package com.wanmi.sbc.goods.goodscommissionconfig.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.enums.CommissionFreightBearFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;
import com.wanmi.sbc.goods.bean.vo.GoodsCommissionConfigVO;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.respository.GoodsCommissionConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


/**
*
 * @description 商品代销设置服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
public class GoodsCommissionConfigService {

    @Autowired private GoodsCommissionConfigRepository goodsCommissionConfigRepository;

    @Autowired private GoodsCommissionSynOperateService goodsCommissionSynOperateService;

    /**
     * @description   根据商家Id查询代销配置
     *                如果商家没有设置会自动创建默认设置
     * @author  wur
     * @date: 2021/9/10 11:16
     * @param storeId  商家Id
     * @return 代销配置
     **/
    public GoodsCommissionConfig queryBytStoreId(Long storeId) {
        GoodsCommissionConfig commissionConfig = goodsCommissionConfigRepository.findByStoreId(storeId);
        if( Objects.nonNull(commissionConfig)) {
            return commissionConfig;
        }
        commissionConfig = new GoodsCommissionConfig();
        commissionConfig.setStoreId(storeId);
        commissionConfig.setDelFlag(DeleteFlag.NO);
        commissionConfig.setSynPriceType(CommissionSynPriceType.HAND_SYN);
        commissionConfig.setUnderFlag(DefaultFlag.YES);
        commissionConfig.setInfoSynFlag(DefaultFlag.NO);
        commissionConfig.setFreightBearFlag(CommissionFreightBearFlag.BUYER_BEAR);
        commissionConfig.setCreatePerson("System");
        commissionConfig.setCreateTime(LocalDateTime.now());
        goodsCommissionConfigRepository.save(commissionConfig);
        return commissionConfig;
    }

    /**
     * @description  跟新设置
     * @author  wur
     * @date: 2021/9/10 11:51
     * @param commissionConfigVO
     **/
    public void updateStoreId(GoodsCommissionConfigVO commissionConfigVO, String person) {
        GoodsCommissionConfig commissionConfig = goodsCommissionConfigRepository.findByStoreId(commissionConfigVO.getStoreId());
        if( Objects.isNull(commissionConfig)) {
            return;
        }
        CommissionSynPriceType oldSynPriceType = commissionConfig.getSynPriceType();
        BigDecimal oldAddRate = commissionConfig.getAddRate();
        GoodsCommissionConfig newCommissionConfig = KsBeanUtil.convert(commissionConfigVO, GoodsCommissionConfig.class);
        newCommissionConfig.setId(commissionConfig.getId());
        newCommissionConfig.setCreateTime(commissionConfig.getCreateTime());
        newCommissionConfig.setCreatePerson(commissionConfig.getCreatePerson());
        newCommissionConfig.setUpdateTime(LocalDateTime.now());
        newCommissionConfig.setUpdatePerson(person);
        newCommissionConfig.setDelFlag(commissionConfig.getDelFlag());
        if(CommissionSynPriceType.AI_SYN.toValue() == commissionConfigVO.getSynPriceType().toValue()) {
            newCommissionConfig.setUnderFlag(DefaultFlag.YES);
        }
        if (CommissionSynPriceType.HAND_SYN.toValue() == commissionConfigVO.getSynPriceType().toValue()) {
            newCommissionConfig.setAddRate(oldAddRate);
        }
        goodsCommissionConfigRepository.save(newCommissionConfig);
        //手动设价 -> 智能设价 需要将商家内的所有供应商商品按照加价比例重新计算商品价格
        if(CommissionSynPriceType.AI_SYN.toValue() == commissionConfigVO.getSynPriceType().toValue() && (
                Objects.isNull(oldAddRate) || oldAddRate.compareTo(commissionConfigVO.getAddRate()) != 0 || CommissionSynPriceType.HAND_SYN.toValue() == oldSynPriceType.toValue())) {
            goodsCommissionSynOperateService.updatePrice(newCommissionConfig);
        }
    }

    public void updateAddRate(GoodsCommissionConfigVO commissionConfigVO, String person) {
        GoodsCommissionConfig commissionConfig = goodsCommissionConfigRepository.findByStoreId(commissionConfigVO.getStoreId());
        if( Objects.isNull(commissionConfig)) {
            return;
        }
        commissionConfig.setAddRate(commissionConfigVO.getAddRate());
        commissionConfig.setCreateTime(commissionConfig.getCreateTime());
        commissionConfig.setCreatePerson(commissionConfig.getCreatePerson());
        commissionConfig.setUpdateTime(LocalDateTime.now());
        commissionConfig.setUpdatePerson(person);
        goodsCommissionConfigRepository.save(commissionConfig);
        if(CommissionSynPriceType.AI_SYN.toValue() == commissionConfig.getSynPriceType().toValue()) {
            goodsCommissionSynOperateService.updatePrice(commissionConfig);
        }
    }


    /**
     * @description
     * @author  wur
     * @date: 2021/9/10 16:31
     * @param commissionConfig
     * @return
     **/
    public GoodsCommissionConfigVO wrapperVo(GoodsCommissionConfig commissionConfig) {
        if(Objects.isNull(commissionConfig)) {
            return null;
        }
        GoodsCommissionConfigVO commissionConfigVO=new GoodsCommissionConfigVO();
        KsBeanUtil.copyPropertiesThird(commissionConfig, commissionConfigVO);
        return commissionConfigVO;
    }

}
