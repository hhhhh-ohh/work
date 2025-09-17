package com.wanmi.sbc.elastic.base.service;

import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.customer.service.EsStoreEvaluateSumService;
import com.wanmi.sbc.elastic.goods.service.EsGoodsInfoElasticService;
import com.wanmi.sbc.elastic.storeInformation.service.EsStoreInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/** ES公共服务 */
@Slf4j
@Service
public class ElasticBaseService {

    @Autowired private EsStoreInformationService esStoreInformationService;

    @Autowired private EsStoreEvaluateSumService esStoreEvaluateSumService;

    @Autowired private EsGoodsInfoElasticService esGoodsInfoElasticService;

    @Autowired private EsBaseService esBaseService;

    /** 删除索引 */
    public void deleteByIdAndIndexName(String id, String indexName) {
        switch (indexName) {
            case EsConstants.DOC_STORE_INFORMATION_COMPANY_ID: // 根据商家id删除
                esStoreInformationService.deleteByCompanyInfoId(NumberUtils.toLong(id));
                break;
            case EsConstants.STORE_EVALUATE_SUM: // 店铺评价根据storeId删
                esStoreEvaluateSumService.deleteByStoreId(NumberUtils.toLong(id));
                break;
            case EsConstants.DOC_GOODS_TYPE: // goods删除，goodsinfo需同步删除
                esGoodsInfoElasticService.deleteByGoods(Collections.singletonList(id));
                break;
            default:
                esBaseService.deleteIndexById(id, indexName);
                break;
        }
    }

}
