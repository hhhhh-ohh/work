package com.wanmi.sbc.elastic.esindexhandle.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wur
 * @className EsIndexHandle
 * @description TODO
 * @date 2022/6/20 9:42
 **/
@Slf4j
@Primary
@Service
public class EsIndexHandleService {

    @Autowired
    protected EsBaseService esBaseService;

    @WmResource("mapping/esActivityCoupon.json")
    private Resource esActivityCouponMapping;

    @WmResource("mapping/esCommonSetting.json")
    private Resource esCommonSettingMapping;

    @WmResource("mapping/esCouponActivity.json")
    private Resource esCouponActivityMapping;

    @WmResource("mapping/esCouponInfo.json")
    private Resource esCouponInfoMapping;

    @WmResource("mapping/esCouponScope.json")
    private Resource esCouponScopeMapping;

    @WmResource("mapping/esCustomerDetail.json")
    private Resource esCustomerDetailMapping;

    @WmResource("mapping/esCustomerInvoice.json")
    private Resource esCustomerInvoiceMapping;

    @WmResource("mapping/esDistributionCustomer.json")
    private Resource esDistributionCustomerMapping;

    @WmResource("mapping/esDistributionGoodsMatter.json")
    private Resource esDistributionGoodsMatterMapping;

    @WmResource("mapping/esDistributionRecord.json")
    private Resource esDistributionRecordMapping;

    @WmResource("mapping/esEmployee.json")
    private Resource esEmployeeMapping;

    @WmResource("mapping/esGoods.json")
    private Resource esGoodsMapping;

    @WmResource("mapping/esGoodsBrand.json")
    private Resource esGoodsBrandMapping;

    @WmResource("mapping/esGoodsEvaluate.json")
    private Resource esGoodsEvaluateMapping;

    @WmResource("mapping/esGoodsInfo.json")
    private Resource esSkuMapping;

    @WmResource("mapping/esGrouponActivity.json")
    private Resource esGrouponActivityMapping;

    @WmResource("mapping/esInviteNewRecord.json")
    private Resource esInviteNewRecordMapping;

    @WmResource("mapping/esOperationLog.json")
    private Resource esOperationLogMapping;

    @WmResource("mapping/esOrderInvoice.json")
    private Resource esOrderInvoiceMapping;

    @WmResource("mapping/esPointsGoodsInfo.json")
    private Resource esPointsGoodsInfoMapping;

    @WmResource("mapping/esSearchAssociationalWord.json")
    private Resource esSearchAssociationalWordMapping;

    @WmResource("mapping/EsSensitiveWords.json")
    private Resource esSensitiveWordsMapping;

    @WmResource("mapping/esSettlement.json")
    private Resource esSettlementMapping;

    @WmResource("mapping/esStandardGoods.json")
    private Resource esStandardGoodsMapping;

    @WmResource("mapping/esStoreEvaluateSum.json")
    private Resource esStoreEvaluateSumMapping;

    @WmResource("mapping/esSystemResource.json")
    private Resource esSystemResourceMapping;

    @WmResource("mapping/storeInformation.json")
    private Resource storeInformationMapping;

    /**
     * @description   全局清理和重建索引
     * @author  wur
     * @date: 2022/6/20 11:45
     * @return
     **/
    public void initAllIndex() {
        List<String> getAllType = getAllType();
        getAllType.forEach(this::initIndexByType);
    }

    /**
     * @description   清理和重建指定索引
     * @author  wur
     * @date: 2022/6/20 9:53
     * @param esType  索引类型
     * @return
     **/
    public void initIndexByType(String esType) {
        //验证指定的索引是否存在
        if(esBaseService.exists(esType)) {
            log.info("删除索引：{}",esType);
            esBaseService.deleteIndex(esType);
        }
        //重建商品索引
        Resource mappingResource = getMapping(esType);
        if (Objects.nonNull(mappingResource)) {
            esBaseService.existsOrCreate(esType, mappingResource, false);
        }
    }

    /**
     * @description  获取所有索引类型
     * @author  wur
     * @date: 2022/6/20 11:44
     * @return
     **/
    private List<String> getAllType() {
        List<String> typeList = new ArrayList<>();
        typeList.add(EsConstants.DOC_ACTIVITY_COUPON_TYPE);
        typeList.add(EsConstants.DOC_COUPON_ACTIVITY);
        typeList.add(EsConstants.DOC_COUPON_INFO_TYPE);
        typeList.add(EsConstants.DOC_COUPON_SCOPE_TYPE);
        typeList.add(EsConstants.DOC_CUSTOMER_DETAIL);
        typeList.add(EsConstants.ES_CUSTOMER_INVOICE);
        typeList.add(EsConstants.DISTRIBUTION_CUSTOMER);
        typeList.add(EsConstants.DISTRIBUTION_GOODS_MATTER);
        typeList.add(EsConstants.DISTRIBUTION_RECORD);
        typeList.add(EsConstants.DOC_EMPLOYEE_TYPE);
        typeList.add(EsConstants.DOC_GOODS_TYPE);
        typeList.add(EsConstants.ES_GOODS_BRAND);
        typeList.add(EsConstants.GOODS_EVALUATE);
        typeList.add(EsConstants.DOC_GOODS_INFO_TYPE);
        typeList.add(EsConstants.GROUPON_ACTIVITY);
        typeList.add(EsConstants.INVITE_NEW_RECORD);
        typeList.add(EsConstants.SYSTEM_OPERATION_LOG);
        typeList.add(EsConstants.ORDER_INVOICE);
        typeList.add(EsConstants.DOC_POINTS_GOODS_INFO_TYPE);
        typeList.add(EsConstants.SEARCH_ASSOCIATIONAL_WORD);
        typeList.add(EsConstants.SENSITIVE_WORDS);
        typeList.add(EsConstants.DOC_SETTLEMENT);
        typeList.add(EsConstants.DOC_STANDARD_GOODS);
        typeList.add(EsConstants.STORE_EVALUATE_SUM);
        typeList.add(EsConstants.SYSTEM_RESOURCE);
        typeList.add(EsConstants.DOC_STORE_INFORMATION_TYPE);
        return typeList;
    }

    /**
     * @description   根据
     * @author  wur
     * @date: 2022/6/20 9:48
     * @param esType
     * @return
     **/
    private Resource getMapping(String esType) {
        switch (esType) {
            case EsConstants.DOC_ACTIVITY_COUPON_TYPE:
                return esActivityCouponMapping;
            case EsConstants.DOC_COUPON_ACTIVITY:
                return esCouponActivityMapping;
            case EsConstants.DOC_COUPON_INFO_TYPE:
                return esCouponInfoMapping;
            case EsConstants.DOC_COUPON_SCOPE_TYPE:
                return esCouponScopeMapping;
            case EsConstants.DOC_CUSTOMER_DETAIL:
                return esCustomerDetailMapping;
            case EsConstants.ES_CUSTOMER_INVOICE:
                return esCustomerInvoiceMapping;
            case EsConstants.DISTRIBUTION_CUSTOMER:
                return esDistributionCustomerMapping;
            case EsConstants.DISTRIBUTION_GOODS_MATTER:
                return esDistributionGoodsMatterMapping;
            case EsConstants.DISTRIBUTION_RECORD:
                return esDistributionRecordMapping;
            case EsConstants.DOC_EMPLOYEE_TYPE:
                return esEmployeeMapping;
            case EsConstants.DOC_GOODS_TYPE:
                return esGoodsMapping;
            case EsConstants.ES_GOODS_BRAND:
                return esGoodsBrandMapping;
            case EsConstants.GOODS_EVALUATE:
                return esGoodsEvaluateMapping;
            case EsConstants.DOC_GOODS_INFO_TYPE:
                return esSkuMapping;
            case EsConstants.GROUPON_ACTIVITY:
                return esGrouponActivityMapping;
            case EsConstants.INVITE_NEW_RECORD:
                return esInviteNewRecordMapping;
            case EsConstants.SYSTEM_OPERATION_LOG:
                return esOperationLogMapping;
            case EsConstants.ORDER_INVOICE:
                return esOrderInvoiceMapping;
            case EsConstants.DOC_POINTS_GOODS_INFO_TYPE:
                return esPointsGoodsInfoMapping;
            case EsConstants.SEARCH_ASSOCIATIONAL_WORD:
                return esSearchAssociationalWordMapping;
            case EsConstants.SENSITIVE_WORDS:
                return esSensitiveWordsMapping;
            case EsConstants.DOC_SETTLEMENT:
                return esSettlementMapping;
            case EsConstants.DOC_STANDARD_GOODS:
                return esStandardGoodsMapping;
            case EsConstants.STORE_EVALUATE_SUM:
                return esStoreEvaluateSumMapping;
            case EsConstants.SYSTEM_RESOURCE:
                return esSystemResourceMapping;
            case EsConstants.DOC_STORE_INFORMATION_TYPE:
                return storeInformationMapping;
            default:
                break;
        }
        return null;
    }

}