package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelSaveProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyTypeRequest;
import com.wanmi.sbc.customer.api.request.store.StoreAuditRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelInitRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandSaveRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandTransferByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsInitByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateInitByStoreIdRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author: songhanlin
 * @Date: Created In 下午2:42 2017/11/7
 * @Description: 避免循环依赖所使用的Service
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class StoreSelfService {

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private ContractBrandProvider contractBrandProvider;

    @Autowired
    private CompanyInfoProvider companyInfoProvider;

    @Autowired
    private StoreCateProvider storeCateProvider;

    @Autowired
    private FreightTemplateGoodsProvider freightTemplateGoodsProvider;

    @Autowired
    private StoreLevelSaveProvider storeLevelSaveProvider;

    @Autowired
    private EsGoodsBrandProvider esGoodsBrandProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 通过/驳回 审核
     *
     * @param request
     * @return
     */
    @GlobalTransactional
    @Transactional
    public StoreVO rejectOrPass(StoreAuditRequest request) {
        // 通过店铺
        StoreVO store = storeProvider.auditStore(request).getContext().getStoreVO();
        if (Objects.equals(store.getAuditState(), CheckState.CHECKED)) {
            // 迁移品牌
            BaseResponse<List<GoodsBrandVO>> listBaseResponse = contractBrandProvider.transferByStoreId(
                    ContractBrandTransferByStoreIdRequest.builder().storeId(store.getStoreId())
                            .build());
            // 迁移品牌同步es
            if (CollectionUtils.isNotEmpty(listBaseResponse.getContext())){
                for (GoodsBrandVO goodsBrandVO : listBaseResponse.getContext()) {
                    String py = Pinyin4jUtil.converterToFirstSpell(goodsBrandVO.getBrandName(),",");
                    if(StringUtils.isNotBlank(py)){
                        goodsBrandVO.setFirst(py.substring(0,1).toUpperCase());
                    }
                }
                EsGoodsBrandSaveRequest esRequest = EsGoodsBrandSaveRequest.builder().goodsBrandVOList(listBaseResponse.getContext()).build();
                esGoodsBrandProvider.addGoodsBrandList(esRequest);
            }

            // 初始化店铺商品分类--默认分类_bail
            storeCateProvider.initByStoreId(new StoreCateInitByStoreIdRequest(store.getStoreId()));
            // 非自营店铺初始化店铺等级
            if (store.getCompanyType() != null && store.getCompanyType() == BoolFlag.YES) {
                storeLevelSaveProvider.initStoreLevel(
                        StoreLevelInitRequest.builder()
                                .storeId(store.getStoreId())
                                .createPerson(commonUtil.getOperatorId())
                                .createTime(LocalDateTime.now())
                                .build());
            }
            CompanyTypeRequest typeRequest = new CompanyTypeRequest();
            typeRequest.setCompanyInfoId(store.getCompanyInfo().getCompanyInfoId());
            typeRequest.setCompanyType(request.getCompanyType());
            typeRequest.setApplyEnterTime(LocalDateTime.now());
            companyInfoProvider.modifyCompanyType(typeRequest);

            // 初始化店铺运费模板
            freightTemplateGoodsProvider.initByStoreId(
                    FreightTemplateGoodsInitByStoreIdRequest.builder()
                            .storeId(store.getStoreId())
                            .build());
        }
        return store;
    }
}
