package com.wanmi.sbc.goods.providergoodsedit.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.constant.GoodsEditMsg;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSpecDetailSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSpecSaveDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.ProviderGoodsEditVO;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.providergoodsedit.model.root.ProviderGoodsEditDetail;
import com.wanmi.sbc.goods.providergoodsedit.repository.ProviderGoodsEditDetailRepository;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import com.wanmi.sbc.goods.suppliercommissiongoods.repository.SupplierCommissionGoodRepository;
import com.wanmi.sbc.goods.suppliercommissiongoods.service.SupplierCommissionGoodService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @description 商品变更记录
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
@Slf4j
public class ProviderGoodsEditDetailService {

    @Autowired private ProviderGoodsEditDetailRepository providerGoodsEditDetailRepository;

    @Autowired private SupplierCommissionGoodRepository supplierCommissionGoodRepository;

    @Autowired private RedissonClient redissonClient;

    @Autowired private ProviderGoodsEditSynService providerGoodsEditSynService;

    @Autowired private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private SupplierCommissionGoodService supplierCommissionGoodService;

    private static final String ADD_EDIT_LOCK_KEY = "GOODS:ADD_EDIT:";


    /**
     * 根据代理商商品Id查询
     * @author  wur
     * @date: 2021/9/14 16:38
     * @param goodsIdList    商品Id
     * @return
     **/
    public List<ProviderGoodsEditDetail> queryByGoodsIdList(List<String> goodsIdList) {
        return providerGoodsEditDetailRepository.findAllByGoodsIdInAndDelFlag(goodsIdList, DeleteFlag.NO);
    }

    public List<ProviderGoodsEditDetail>  queryByGoodsIdAndEnditType(List<String> goodsIdList, GoodsEditType enditType) {
        return providerGoodsEditDetailRepository.findAllByGoodsIdInAndEnditTypeAndDelFlag(goodsIdList, enditType, DeleteFlag.NO);
    }

    /**
     * @description
     * @author  wur
     * @date: 2021/9/18 16:55
     * @param goodsId
     * @param enditType
     * @param editContent
     * @return
     **/
    public void addGoodsEdit(String goodsId, GoodsEditType enditType, String editContent) {
        RLock rLock = redissonClient.getFairLock(ADD_EDIT_LOCK_KEY + enditType.toValue() + ":" + goodsId);
        rLock.lock();
        try{
            //验证商品是否有绑定关系
            List<SupplierCommissionGood> commissionGoodsList = supplierCommissionGoodRepository.findByProviderGoodsIdAndDelFlag(goodsId, DeleteFlag.NO);
            if (CollectionUtils.isEmpty(commissionGoodsList)) {
                return;
            }
            //验证是否有此类行操作
            ProviderGoodsEditDetail providerGoodsEditDetail = providerGoodsEditDetailRepository.findByGoodsIdAndEnditTypeAndDelFlag(goodsId, enditType, DeleteFlag.NO);
            //跟新 新增
            if (Objects.isNull(providerGoodsEditDetail)) {
                providerGoodsEditDetail = new ProviderGoodsEditDetail();
                providerGoodsEditDetail.setGoodsId(goodsId);
                providerGoodsEditDetail.setEnditType(enditType);
                providerGoodsEditDetail.setEnditContent(editContent);
                providerGoodsEditDetail.setDelFlag(DeleteFlag.NO);
                providerGoodsEditDetail.setCreateTime(LocalDateTime.now());
                providerGoodsEditDetail.setUpdateTime(LocalDateTime.now());
            } else {
                providerGoodsEditDetail.setEnditContent(editContent);
                providerGoodsEditDetail.setUpdateTime(LocalDateTime.now());
            }
            providerGoodsEditDetailRepository.save(providerGoodsEditDetail);
            //如果是商品信息变更
            if(enditType.toValue() == GoodsEditType.INFO_EDIT.toValue()) {
                supplierCommissionGoodService.updateUpAndSynFlag(goodsId);
            } else {
                supplierCommissionGoodService.updateUpFlag(goodsId);
            }
        } catch (Exception e) {
            throw e;
        } finally{
            //释放锁
            rLock.unlock();
        }
    }

    /**
     * @description   异步处理商品禁销操作日志同步
     * @author  wur
     * @date: 2021/9/22 10:59
     * @param goodsIdList
     * @return
     **/
    @Async
    public void goodsForbade(List<String> goodsIdList) {
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return;
        }
        goodsIdList.forEach(
                goodsId -> {
                    this.addGoodsEdit(
                            goodsId,
                            GoodsEditType.STATUS_EDIT,
                            GoodsEditMsg.GOODS_FORBADE);
                });
    }

    /**
     * 异步处理商品上下架操作日志
     * @param goodsIdList   商品ID
     * @param addedFlag     上下架操作
     */
    @Async
    public void goodsAddedStatus(List<String> goodsIdList, Integer addedFlag) {
        String enditContent = GoodsEditMsg.GOODS_UP;
        if (AddedFlag.NO.toValue() == addedFlag) {
            enditContent = GoodsEditMsg.GOODS_DOWN;
        }
        String finalEditContent = enditContent;
        goodsIdList.forEach(goodsId->{
            this.addGoodsEdit( goodsId, GoodsEditType.STATUS_EDIT, finalEditContent);
        });
    }

    @Async
    public void goodsInfoAddedStatus(List<String> goodsIdList, Integer addedFlag) {
        String enditContent = GoodsEditMsg.GOODS_INFO_UP;
        if (AddedFlag.NO.toValue() == addedFlag) {
            enditContent = GoodsEditMsg.GOODS_INFO_DOWN;
        }
        String finalEditContent = enditContent;
        goodsIdList.forEach(goodsId->{
            this.addGoodsEdit( goodsId, GoodsEditType.STATUS_EDIT, finalEditContent);
        });
    }

    @Async
    public void goodsDel(List<String> goodsIdList) {
        goodsIdList.forEach(goodsId->{
            this.addGoodsEdit( goodsId, GoodsEditType.STATUS_EDIT, GoodsEditMsg.GOODS_DEL);
        });
    }

    public void goodsInfo(GoodsInfo newGoodsInfo, GoodsInfo oldGoodsInfo) {
        if(GoodsSource.PROVIDER.toValue() != newGoodsInfo.getGoodsSource()
                && GoodsSource.VOP.toValue() != newGoodsInfo.getGoodsSource()) {
            return;
        }
        boolean synFlag = false;
        //验证商品成本价
        if (oldGoodsInfo.getSupplyPrice().compareTo(newGoodsInfo.getSupplyPrice()) != 0) {
            this.addGoodsEdit( oldGoodsInfo.getGoodsId(), GoodsEditType.PRICE_EDIT, GoodsEditMsg.UPDATE_GOODS_PRICE);
            synFlag = true;
        }

        //上下架
        if(newGoodsInfo.getAddedFlag().compareTo(oldGoodsInfo.getAddedFlag()) != 0) {
            goodsInfoAddedStatus(Arrays.asList(oldGoodsInfo.getGoodsId()), newGoodsInfo.getAddedFlag());
        }

        //验证商品基本信息
        if(!Objects.equals(newGoodsInfo.getGoodsInfoImg(), oldGoodsInfo.getGoodsInfoImg())
                || !Objects.equals(newGoodsInfo.getGoodsInfoBarcode(), oldGoodsInfo.getGoodsInfoBarcode())) {
            this.addGoodsEdit( oldGoodsInfo.getGoodsId(), GoodsEditType.INFO_EDIT, GoodsEditMsg.UPDATE_GOODS_INFO);
            synFlag = true;
        }

        //库存变更
        if (!Objects.equals(newGoodsInfo.getStock(), oldGoodsInfo.getStock())) {
            synFlag = true;
        }
        // 信息同步
        log.info("同步Falg-----------------"+synFlag);
        if (synFlag) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    providerGoodsEditSynService.synSkuUpdate(newGoodsInfo);
                }
            });
        }
    }

    /**
     * @description  商品信息变更
     * @author  wur
     * @date: 2021/9/23 10:00
     * @param newGoodsInfo 新商品
     * @param oldGoodsInfo  老商品
     * @param editMap      信息变更集合
     * @param updateSupplyPrice   成本价变更集合
     * @return
     **/
    public void goodsInfoEdit(GoodsInfoSaveDTO newGoodsInfo, GoodsInfoSaveDTO oldGoodsInfo, Map<GoodsEditType, ArrayList<String>> editMap, List updateSupplyPrice) {
        if(GoodsSource.PROVIDER.toValue() != oldGoodsInfo.getGoodsSource()
                && GoodsSource.VOP.toValue() != oldGoodsInfo.getGoodsSource()) {
            return;
        }

        //验证商品成本价
        if (oldGoodsInfo.getSupplyPrice().compareTo(newGoodsInfo.getSupplyPrice()) != 0) {
            if (editMap.containsKey(GoodsEditType.PRICE_EDIT)) {
                if (!editMap.get(GoodsEditType.PRICE_EDIT).contains(GoodsEditMsg.UPDATE_GOODS_PRICE)) {
                    ArrayList<String> editMsg = editMap.get(GoodsEditType.PRICE_EDIT);
                    editMsg.add(GoodsEditMsg.UPDATE_GOODS_PRICE);
                    editMap.put(GoodsEditType.PRICE_EDIT, editMsg);
                }
            } else {
                editMap.put(GoodsEditType.PRICE_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_PRICE)));
            }
            updateSupplyPrice.add(oldGoodsInfo.getGoodsInfoId());
        }

        //验证商品基本信息
        if(GoodsSource.PROVIDER.toValue() == oldGoodsInfo.getGoodsSource()) {
            if(!Objects.equals(newGoodsInfo.getGoodsInfoImg(), oldGoodsInfo.getGoodsInfoImg())
                    || !Objects.equals(newGoodsInfo.getGoodsInfoBarcode(), oldGoodsInfo.getGoodsInfoBarcode())) {
                if (editMap.containsKey(GoodsEditType.INFO_EDIT)
                        && !editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS_INFO)) {
                    ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
                    editMsg.add(GoodsEditMsg.UPDATE_GOODS_INFO);
                    editMap.put(GoodsEditType.INFO_EDIT, editMsg);
                } else {
                    editMap.put(GoodsEditType.INFO_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_INFO)));
                }
            }

            //验证重量体积
            if(newGoodsInfo.getGoodsWeight().compareTo(oldGoodsInfo.getGoodsWeight()) != 0
                    || newGoodsInfo.getGoodsCubage().compareTo(oldGoodsInfo.getGoodsCubage()) != 0) {
                if (editMap.containsKey(GoodsEditType.OTHER_EDIT)) {
                    ArrayList<String> editMsg = editMap.get(GoodsEditType.OTHER_EDIT);
                    editMsg.add(GoodsEditMsg.UPDATE_GOODS_WEIGHT);
                    editMap.put(GoodsEditType.OTHER_EDIT, editMsg);
                } else {
                    editMap.put(GoodsEditType.OTHER_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_WEIGHT)));
                }
            }
        }
    }

    /**
     * @description  商品基础信息变更
     * @author  wur
     * @date: 2021/10/9 16:36
     * @param goods   商品信息
     * @param editMap  编辑
     * @return
     **/
    public void addUpdateGoods(GoodsSaveDTO goods, Map<GoodsEditType, ArrayList<String>> editMap) {
        if (Objects.isNull(editMap)) {
            return;
        }
        if(GoodsSource.PROVIDER.toValue() != goods.getGoodsSource()
                && GoodsSource.VOP.toValue() != goods.getGoodsSource()) {
            return;
        }

        //属性信息
        if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
            if (!editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS)) {
                editMsg.add(GoodsEditMsg.UPDATE_GOODS);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            }
        } else {
            ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS));
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        }
    }

    /**
     * @description   商品多规格变成单规格
     * @author  wur
     * @date: 2021/10/11 13:59
     * @param goods
     * @param editMap
     * @return
     **/
    public void updateGoodsSpec(GoodsSaveDTO goods, Map<GoodsEditType, ArrayList<String>> editMap) {
        if (Objects.isNull(editMap)) {
            return;
        }
        if(GoodsSource.PROVIDER.toValue() != goods.getGoodsSource()
                && GoodsSource.VOP.toValue() != goods.getGoodsSource()) {
            return;
        }
        //属性信息
        if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
            if (!editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS_INFO)) {
                editMsg.add(GoodsEditMsg.UPDATE_GOODS_INFO);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            }
        } else {
            ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_INFO));
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        }
    }

    /**
     * @description   商品规格更新
     * @author  wur
     * @date: 2021/10/11 13:59
     * @param goods
     * @param editMap
     * @param newGoodsSpec  新规格
     * @param oldGoodsSpec  就规格
     * @return
     **/
    public void addUpdateGoodsSpec(GoodsSaveDTO goods, Map<GoodsEditType, ArrayList<String>> editMap, GoodsSpecSaveDTO newGoodsSpec, GoodsSpec oldGoodsSpec) {
        if (Objects.isNull(editMap)) {
            return;
        }
        if(GoodsSource.PROVIDER.toValue() != goods.getGoodsSource()
                && GoodsSource.VOP.toValue() != goods.getGoodsSource()) {
            return;
        }
        if(newGoodsSpec.getSpecName().equals(oldGoodsSpec.getSpecName())) {
            return;
        }
        //属性信息
        if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
            if (!editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS_INFO)) {
                editMsg.add(GoodsEditMsg.UPDATE_GOODS_INFO);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            }
        } else {
            ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_INFO));
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        }
    }

    /**
     * @description   商品规格值更新
     * @author  wur
     * @date: 2021/10/11 13:59
     * @param goods
     * @param editMap
     * @param newGoodsSpecDetail  新规格
     * @param oldGoodsSpecDetail  就规格
     * @return
     **/
    public void addUpdateGoodsSpecDetail(GoodsSaveDTO goods, Map<GoodsEditType, ArrayList<String>> editMap, GoodsSpecDetailSaveDTO newGoodsSpecDetail, GoodsSpecDetail oldGoodsSpecDetail) {
        if (Objects.isNull(editMap)) {
            return;
        }
        if(GoodsSource.PROVIDER.toValue() != goods.getGoodsSource()) {
            return;
        }
        if(newGoodsSpecDetail.getDetailName().equals(oldGoodsSpecDetail.getDetailName())) {
            return;
        }
        //属性信息
        if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
            if (!editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS_INFO)) {
                editMsg.add(GoodsEditMsg.UPDATE_GOODS_INFO);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            }
        } else {
            ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_INFO));
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        }
    }

    /**
     * @description  商品属性对比
     * @author  wur
     * @date: 2021/10/13 20:10
     * @param oldGoods 商品
     * @param editMap  变更信息
     * @param propertyDetailRelList   新属性信息
     * @return
     **/
    public void goodsPropEdit(GoodsSaveDTO oldGoods, Map<GoodsEditType, ArrayList<String>> editMap, List<GoodsPropertyDetailRel> propertyDetailRelList) {
        if(GoodsSource.PROVIDER.toValue() != oldGoods.getGoodsSource()) {
            return;
        }
        List<GoodsPropertyDetailRel> oldPropertyDetailRelList = goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(oldGoods.getGoodsId(), DeleteFlag.NO, GoodsPropertyType.GOODS);
        if (CollectionUtils.isEmpty(oldPropertyDetailRelList) && CollectionUtils.isEmpty(propertyDetailRelList)) {
            return;
        }
        boolean isEdit = false;
        if (CollectionUtils.isNotEmpty(oldPropertyDetailRelList) && CollectionUtils.isEmpty(propertyDetailRelList)) {
            isEdit = true;
        }
        if (!isEdit && CollectionUtils.isEmpty(oldPropertyDetailRelList) && CollectionUtils.isNotEmpty(propertyDetailRelList)) {
            isEdit = true;
        }
        if (!isEdit) {
            if (oldPropertyDetailRelList.size() != propertyDetailRelList.size()) {
                isEdit = true;
            }
            if (!isEdit) {
                for (GoodsPropertyDetailRel old : oldPropertyDetailRelList) {
                    if (!isEdit) {
                        for (GoodsPropertyDetailRel newRel : propertyDetailRelList) {
                            if (old.getPropType().toValue() == newRel.getPropType().toValue()
                                    && old.getPropId().equals(newRel.getPropId())) {
                                if (old.getPropType().toValue() == GoodsPropertyEnterType.CHOOSE.toValue()) {
                                    isEdit = !old.getDetailId().equals(newRel.getDetailId());
                                } else if (old.getPropType().toValue() == GoodsPropertyEnterType.TEXT.toValue()) {
                                    isEdit = !old.getPropValueText().equals(newRel.getPropValueText());
                                } else if (old.getPropType().toValue() == GoodsPropertyEnterType.DATE.toValue()) {
                                    isEdit = old.getPropValueDate().compareTo(newRel.getPropValueDate()) != 0;
                                } else if (old.getPropType().toValue() == GoodsPropertyEnterType.PROVINCE.toValue()) {
                                    isEdit = !old.getPropValueProvince().equals(newRel.getPropValueProvince());
                                } else if (old.getPropType().toValue() == GoodsPropertyEnterType.COUNTRY.toValue()) {
                                    isEdit = !old.getPropValueCountry().equals(newRel.getPropValueCountry());
                                }
                            }
                        }
                    }
                }

            }
        }
        if (!isEdit) {
            return;
        }
        //属性信息
        if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
            editMsg.add(GoodsEditMsg.UPDATE_GOODS_PROP);
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        } else {
            ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_PROP));
            editMap.put(GoodsEditType.INFO_EDIT, editMsg);
        }
    }

    /**
     * @description  商品编辑
     * @author  wur
     * @date: 2021/9/22 17:23
     * @param newGoods  新商品
     * @param oldGoods  老商品
     * @param editMap   操作日志
     * @return
     **/
    public void goodsEdit(GoodsSaveDTO newGoods, GoodsSaveDTO oldGoods, Map<GoodsEditType, ArrayList<String>> editMap) {
        if(GoodsSource.PROVIDER.toValue() != oldGoods.getGoodsSource()
                && GoodsSource.VOP.toValue() != oldGoods.getGoodsSource()) {
            return;
        }

        //商品基础信息
        if(!Objects.equals(newGoods.getGoodsName(), oldGoods.getGoodsName())
                || !Objects.equals(newGoods.getGoodsSubtitle(), oldGoods.getGoodsSubtitle())
                || !Objects.equals(newGoods.getGoodsUnit(), oldGoods.getGoodsUnit())
                || !Objects.equals(newGoods.getSaleType(), oldGoods.getSaleType())
                || !Objects.equals(newGoods.getGoodsImg(),oldGoods.getGoodsImg())
                || !Objects.equals(newGoods.getBrandId(),oldGoods.getBrandId())
                || !Objects.equals(newGoods.getLabelIdStr(),oldGoods.getLabelIdStr())
                || !Objects.equals(newGoods.getGoodsVideo(),oldGoods.getGoodsVideo())){
            if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
                ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
                editMsg.add(GoodsEditMsg.UPDATE_GOODS);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            } else {
                editMap.put(GoodsEditType.INFO_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS)));
            }
        }

        //商品详情
        String newDetail = StringUtils.isEmpty(newGoods.getGoodsDetail()) ? newGoods.getGoodsDetail() : newGoods.getGoodsDetail().replaceAll(" ", "");
        String oldDetail = StringUtils.isEmpty(oldGoods.getGoodsDetail()) ? oldGoods.getGoodsDetail() : oldGoods.getGoodsDetail().replaceAll(" ", "");
        if (!Objects.equals(newDetail, oldDetail)) {
            if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
                ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
                editMsg.add(GoodsEditMsg.UPDATE_GOODS_DETAIL);
                editMap.put(GoodsEditType.INFO_EDIT, editMsg);
            } else {
                editMap.put(GoodsEditType.INFO_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_DETAIL)));
            }
        }

        //上下价状态
        if(!Objects.equals(newGoods.getAddedFlag(),oldGoods.getAddedFlag())) {
            String enditContent = GoodsEditMsg.GOODS_UP;
            if (AddedFlag.NO.toValue() == newGoods.getAddedFlag().intValue()) {
                enditContent = GoodsEditMsg.GOODS_DOWN;
            }
            if (editMap.containsKey(GoodsEditType.STATUS_EDIT)) {
                ArrayList<String> editMsg = editMap.get(GoodsEditType.STATUS_EDIT);
                editMsg.add(enditContent);
                editMap.put(GoodsEditType.STATUS_EDIT, editMsg);
            } else {
                editMap.put(GoodsEditType.STATUS_EDIT, new ArrayList<>(Arrays.asList(enditContent)));
            }
        }

        //运费模板
        if(!Objects.equals(newGoods.getFreightTempId(), oldGoods.getFreightTempId())) {
            if (editMap.containsKey(GoodsEditType.OTHER_EDIT)) {
                ArrayList<String> editMsg = editMap.get(GoodsEditType.OTHER_EDIT);
                editMsg.add(GoodsEditMsg.UPDATE_GOODS_FREIGHT);
                editMap.put(GoodsEditType.OTHER_EDIT, editMsg);
            } else {
                editMap.put(GoodsEditType.OTHER_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.UPDATE_GOODS_FREIGHT)));
            }
        }

        // 多规格
        if (!Constants.yes.equals(newGoods.getMoreSpecFlag()) && Constants.yes.equals(oldGoods.getMoreSpecFlag())) {
            this.updateGoodsSpec(oldGoods, editMap);
        }
    }

    /**
     * @description  删除SKU
     * @author  wur
     * @date: 2021/9/23 9:18
     * @param editMap
     * @return
     **/
    public void delSku(GoodsSaveDTO oldGoods, List<String> delInfoIds, Map<GoodsEditType, ArrayList<String>> editMap) {
        if(GoodsSource.PROVIDER.toValue() != oldGoods.getGoodsSource()
                && GoodsSource.VOP.toValue() != oldGoods.getGoodsSource()
                || CollectionUtils.isEmpty(delInfoIds)) {
            return;
        }

        if (editMap.containsKey(GoodsEditType.STATUS_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.STATUS_EDIT);
            editMsg.add(GoodsEditMsg.GOODS_INFO_DEL);
            editMap.put(GoodsEditType.STATUS_EDIT, editMsg);
        } else {
            editMap.put(GoodsEditType.STATUS_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.GOODS_INFO_DEL)));
        }
    }

    /**
     * @description    新增SKU
     * @author  wur
     * @date: 2021/9/23 9:19
     * @param newGoodsInfo
     * @return
     **/
    public void addSku(GoodsSaveDTO oldGoods, List<String> newGoodsInfo, Map<GoodsEditType, ArrayList<String>> editMap) {
        if(GoodsSource.PROVIDER.toValue() != oldGoods.getGoodsSource()
                && GoodsSource.VOP.toValue() != oldGoods.getGoodsSource()
                || CollectionUtils.isEmpty(newGoodsInfo)) {
            return;
        }

        if (editMap.containsKey(GoodsEditType.OTHER_EDIT)) {
            ArrayList<String> editMsg = editMap.get(GoodsEditType.OTHER_EDIT);
            editMsg.add(GoodsEditMsg.ADD_GOODS_INFO);
            editMap.put(GoodsEditType.OTHER_EDIT, editMsg);
        } else {
            editMap.put(GoodsEditType.OTHER_EDIT, new ArrayList<>(Arrays.asList(GoodsEditMsg.ADD_GOODS_INFO)));
        }
    }

    /**
     * @description  VO实体类转换
     * @author  wur
     * @date: 2021/9/15 9:27
     * @param goodsEditDetail
     * @return
     **/
    public ProviderGoodsEditVO wrapperVo(ProviderGoodsEditDetail goodsEditDetail) {
        if(Objects.isNull(goodsEditDetail)) {
            return null;
        }
        ProviderGoodsEditVO goodsEditVO = new ProviderGoodsEditVO();
        KsBeanUtil.copyPropertiesThird(goodsEditDetail, goodsEditVO);
        return goodsEditVO;
    }

    /**
     *
     * @description
     * @author  wur
     * @date: 2021/9/23 14:25
     * @param goods
     * @param oldGoodsInfo
     * @param newInfoNoMap
     * @param delInfoNoMap
     * @param editMap
     * @param updateSupplyPriceGoodsInfoId
     * @return
     **/
    public void synGoodsUpdate(GoodsSaveDTO goods, List<GoodsInfoSaveDTO> oldGoodsInfo, Map<String, GoodsInfoSaveDTO> newInfoNoMap, Map<String, GoodsInfoSaveDTO> delInfoNoMap, Map<GoodsEditType, ArrayList<String>> editMap, List updateSupplyPriceGoodsInfoId) {
        if(GoodsSource.PROVIDER.toValue() != goods.getGoodsSource()
                && GoodsSource.VOP.toValue() != goods.getGoodsSource()) {
            return;
        }
        List<String> newSkuNo = newInfoNoMap.isEmpty() ? new ArrayList<>() : new ArrayList<>(newInfoNoMap.keySet());
        List<String> newSkuNo2 = newInfoNoMap.isEmpty() ? new ArrayList<>() : new ArrayList<>(newInfoNoMap.keySet());
        List<String> delSkuNo = delInfoNoMap.isEmpty() ? new ArrayList<>() : new ArrayList<>(delInfoNoMap.keySet());
        // 处理新增和删除SKU日志
        if (CollectionUtils.isNotEmpty(newSkuNo)) {
            if (CollectionUtils.isNotEmpty(delSkuNo)) {
                newSkuNo.removeAll(delSkuNo);
                //如果有删除了又新增是规格变更导致
                if(newSkuNo.size() != newSkuNo2.size()) {
                    this.updateGoodsSpec(goods, editMap);
                }
            }
            this.addSku(goods, newSkuNo, editMap);
        }
        if (CollectionUtils.isNotEmpty(delSkuNo)) {
            if (CollectionUtils.isNotEmpty(newSkuNo2)) {
                delSkuNo.removeAll(newSkuNo2);
            }
            this.delSku(goods, delSkuNo, editMap);
        }

        //更新 已经代销商品的provideGoodsInfoId
        if (!newInfoNoMap.isEmpty() && !delInfoNoMap.isEmpty()) {
            newInfoNoMap.forEach((k, v)-> {
                if( delInfoNoMap.containsKey(k) ) {
                    this.goodsInfoEdit(v, delInfoNoMap.get(k), editMap, updateSupplyPriceGoodsInfoId);
                }
            });
        }

        //日志落地
        editMap.forEach((k, v)->{
            this.addGoodsEdit( goods.getGoodsId(), k, GoodsEditMsg.getEditContent(v));
        });

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //同步商品更新
                providerGoodsEditSynService.synGoodsUpdate(goods, oldGoodsInfo, updateSupplyPriceGoodsInfoId,
                        newInfoNoMap, delInfoNoMap, editMap);
            }
        });
    }

}
