package com.wanmi.sbc.goods.suppliercommissiongoods.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.DelSupplierCommissionGoodsRequest;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsQueryRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsDeleteVO;
import com.wanmi.sbc.goods.bean.vo.SupplierCommissionGoodsVO;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import com.wanmi.sbc.goods.suppliercommissiongoods.repository.SupplierCommissionGoodRepository;
import io.seata.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description 商家与代销商品关联表服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
public class SupplierCommissionGoodService {

    @Autowired
    private SupplierCommissionGoodRepository supplierCommissionGoodRepository;

    /**
     * @description   新增商品代销商品 信息
     * @author  wur
     * @date: 2021/9/14 10:55
     * @param goodsList
     * @return
     **/
    @Transactional
    public void addCommissionGoods(List<Goods> goodsList, String personId) {
        List<SupplierCommissionGood> commissionGoodsList = new ArrayList<>();
        for (Goods goods : goodsList) {
            if ( StringUtils.isBlank(goods.getProviderGoodsId())) {
                continue;
            }
            SupplierCommissionGood supplierCommissionGoods = supplierCommissionGoodRepository.findByStoreIdAndProviderGoodsIdAndDelFlag(goods.getStoreId(), goods.getProviderGoodsId(), DeleteFlag.NO);
            if (Objects.nonNull(supplierCommissionGoods)) {
                continue;
            }
            supplierCommissionGoods = new SupplierCommissionGood();
            supplierCommissionGoods.setGoodsId(goods.getGoodsId());
            supplierCommissionGoods.setProviderGoodsId(goods.getProviderGoodsId());
            supplierCommissionGoods.setStoreId(goods.getStoreId());
            supplierCommissionGoods.setDelFlag(DeleteFlag.NO);
            supplierCommissionGoods.setCreatePerson(personId);
            supplierCommissionGoods.setUpdatePerson(personId);
            supplierCommissionGoods.setUpdateTime(LocalDateTime.now());
            supplierCommissionGoods.setCreateTime(LocalDateTime.now());
            supplierCommissionGoods.setSynStatus(DefaultFlag.YES); //默认已同步
            supplierCommissionGoods.setUpdateFlag(DefaultFlag.NO);
            commissionGoodsList.add(supplierCommissionGoods);
        }
        supplierCommissionGoodRepository.saveAll(commissionGoodsList);
    }

    @Transactional
    public void delCommissionList(List<GoodsDeleteVO> goodsList) {
        Long storeId = null;
        List<String> goodsIdList = new ArrayList();
        for (GoodsDeleteVO goodsVo : goodsList) {
            storeId = goodsVo.getStoreId();
            goodsIdList.add(goodsVo.getGoodsId());
        }
        supplierCommissionGoodRepository.delCommissionGoodsList(goodsIdList, storeId);
    }

    /**
     * @description  分页查询
     * @author  wur
     * @date: 2021/9/14 14:09
     * @param queryReq
     * @return
     **/
    public Page<SupplierCommissionGood> page(SupplierCommissionGoodsQueryRequest queryReq){
        return supplierCommissionGoodRepository.findAll(
                SupplierCommissionGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * @description
     * @author  wur
     * @date: 2021/9/16 14:58
     * @param idList
     * @param updateFlag
     * @return
     **/
    public List<SupplierCommissionGood> findByIdAndUpdateFlag(List<String> idList, Long storeId, DefaultFlag updateFlag) {
        return supplierCommissionGoodRepository.findByIdAndUpdateFlag(idList, storeId, updateFlag);
    }


    @Transactional
    public void updateSynStatusById(List<String> idList, DefaultFlag synStatus) {
        supplierCommissionGoodRepository.updateSynStatusById(idList, synStatus);
    }

    @Transactional
    public void updateUpAndSynFlag(String goodsId){
        supplierCommissionGoodRepository.updateUpAndSynFlag(goodsId, DefaultFlag.YES, DefaultFlag.NO);
    }

    @Transactional
    public void updateUpFlag(String goodsId){
        supplierCommissionGoodRepository.updateUpFlag(goodsId, DefaultFlag.YES);
    }

    /**
     * @description
     * @author  wur
     * @date: 2021/9/15 14:58
     * @param supplierCommissionGoods
     * @return
     **/
    public SupplierCommissionGoodsVO wrapperVo(SupplierCommissionGood supplierCommissionGoods) {
        if(Objects.isNull(supplierCommissionGoods)) {
            return null;
        }
        SupplierCommissionGoodsVO commissionGoodsEditVO = new SupplierCommissionGoodsVO();
        KsBeanUtil.copyPropertiesThird(supplierCommissionGoods, commissionGoodsEditVO);
        return commissionGoodsEditVO;
    }

}
