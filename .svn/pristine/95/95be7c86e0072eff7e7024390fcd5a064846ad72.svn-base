package com.wanmi.sbc.goods.goodscommissionpriceconfig.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigStatusUpdateRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import com.wanmi.sbc.goods.bean.vo.CommissionPriceTargetVO;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionSynOperateService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.respository.GoodsCommissionPriceConfigRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description  代销智能设价加价比例设置服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
public class GoodsCommissionPriceConfigService {

    @Autowired private GoodsCommissionPriceConfigRepository commissionPriceConfigRepository;

    @Autowired private GoodsCommissionSynOperateService goodsCommissionSynOperateService;

    /**
     * 查询代销智能设价加价比例设置
     * @param queryRequest
     * @return
     */
    public List<GoodsCommissionPriceConfig> query(GoodsCommissionPriceConfigQueryRequest queryRequest) {
        return commissionPriceConfigRepository.findAll(CommissionPriceConfigWhereCriteriaBuilder.build(queryRequest));
    }

    /**
    *  设置代销智能设价加价比例状态
     * @description
     * @author  wur
     * @date: 2021/9/10 17:16
     * @param updateRequest
     * @return
     **/
    public void updateStatus(GoodsCommissionPriceConfigStatusUpdateRequest updateRequest) {
        GoodsCommissionPriceConfig commissionPriceConfig = commissionPriceConfigRepository.findById(updateRequest.getId());
        if (Objects.isNull(commissionPriceConfig)) {
            return;
        }
        commissionPriceConfig.setEnableStatus(updateRequest.getEnableStatus());
        commissionPriceConfig.setUpdateTime(LocalDateTime.now());
        commissionPriceConfig.setUpdatePerson(updateRequest.getUserId());
        commissionPriceConfigRepository.save(commissionPriceConfig);
        //类目需要同步价格
        if (CommissionPriceTargetType.CATE.toValue() == commissionPriceConfig.getTargetType().toValue()) {
            goodsCommissionSynOperateService.updatePriceByPriceConfig(commissionPriceConfig);
        }
    }

    /**
     * @description  修改新增
     * @author  wur
     * @date: 2021/9/10 17:54
     * @param updateRequest
     **/
    public void update(GoodsCommissionPriceConfigUpdateRequest updateRequest) {
        List<CommissionPriceTargetVO> commissionPriceTargetVOList = updateRequest.getCommissionPriceTargetVOList();
        List<String> targetIdList = commissionPriceTargetVOList.stream().map(CommissionPriceTargetVO :: getTargetId).collect(Collectors.toList());
        CommissionPriceTargetVO commissionPriceTargetVO = commissionPriceTargetVOList.stream().findFirst().get();

        boolean isUpdatePrice = CommissionPriceTargetType.CATE.toValue() == commissionPriceTargetVO.getTargetType().toValue();

        //更新
        GoodsCommissionPriceConfigQueryRequest queryRequest = GoodsCommissionPriceConfigQueryRequest.builder()
                .targetType(commissionPriceTargetVO.getTargetType())
                .targetIdList(targetIdList)
                .build();
        queryRequest.setBaseStoreId(updateRequest.getBaseStoreId());
        List<GoodsCommissionPriceConfig> commissionPriceConfigList = commissionPriceConfigRepository.findAll(CommissionPriceConfigWhereCriteriaBuilder.build(queryRequest));
        List<GoodsCommissionPriceConfig> newCommissionPriceConfigList = new ArrayList<>();
        List<GoodsCommissionPriceConfig> oldCommissionPriceConfigList = new ArrayList<>();
        List<GoodsCommissionPriceConfig> updatePriceCommissionPriceConfigList = new ArrayList<>();
        List<String> oldTargetId = CollectionUtils.isEmpty(commissionPriceConfigList) ? new ArrayList<>() : commissionPriceConfigList.stream().map(GoodsCommissionPriceConfig :: getTargetId).collect(Collectors.toList());

        for (CommissionPriceTargetVO priceTargetVO : commissionPriceTargetVOList) {
            if (oldTargetId.contains(priceTargetVO.getTargetId())) {
                for (GoodsCommissionPriceConfig priceConfig : commissionPriceConfigList) {
                    if (priceTargetVO.getTargetId().equals(priceConfig.getTargetId())) {
                        if (priceConfig.getAddRate().compareTo(priceTargetVO.getAddRate()) != 0) {
                            priceConfig.setAddRate(priceTargetVO.getAddRate());
                            updatePriceCommissionPriceConfigList.add(priceConfig);
                        }
                        priceConfig.setEnableStatus(priceTargetVO.getEnableStatus());
                        oldCommissionPriceConfigList.add(priceConfig);
                        continue;
                    }
                }
            } else {
                GoodsCommissionPriceConfig newPriceConfig = new GoodsCommissionPriceConfig();
                newPriceConfig.setDelFlag(DeleteFlag.NO);
                newPriceConfig.setEnableStatus(priceTargetVO.getEnableStatus());
                newPriceConfig.setStoreId(updateRequest.getBaseStoreId());
                newPriceConfig.setTargetId(priceTargetVO.getTargetId());
                newPriceConfig.setAddRate(priceTargetVO.getAddRate());
                newPriceConfig.setTargetType(priceTargetVO.getTargetType());
                newPriceConfig.setUpdatePerson(updateRequest.getUserId());
                newPriceConfig.setUpdateTime(LocalDateTime.now());
                newPriceConfig.setCreatePerson(updateRequest.getUserId());
                newPriceConfig.setCreateTime(LocalDateTime.now());
                newCommissionPriceConfigList.add(newPriceConfig);
            }
        }

        if (CollectionUtils.isNotEmpty(oldCommissionPriceConfigList)) {
            commissionPriceConfigRepository.saveAll(oldCommissionPriceConfigList);
            updatePriceCommissionPriceConfigList.addAll(oldCommissionPriceConfigList);
        }
        if (CollectionUtils.isNotEmpty(newCommissionPriceConfigList)) {
            commissionPriceConfigRepository.saveAll(newCommissionPriceConfigList);
            updatePriceCommissionPriceConfigList.addAll(newCommissionPriceConfigList);
        }

        //类目的加价比例只可以单条数据操作
        if (isUpdatePrice && CollectionUtils.isNotEmpty(updatePriceCommissionPriceConfigList)) {
            goodsCommissionSynOperateService.updatePriceByPriceConfig(updatePriceCommissionPriceConfigList.get(0));
        }
    }

}
