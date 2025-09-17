package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.DistributionLedgerRelRequest;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterProvider;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatteByIdRequest;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterProvider;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterQueryProvider;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatterPageRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.UpdateRecommendNumRequest;
import com.wanmi.sbc.goods.api.response.distributionmatter.DistributionGoodsMatterPageResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.ledgeraccount.MobileLedgerAccountBaseService;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "DistributionGoodsMatterController", description = "分销商品素材")
@RestController
@Validated
@RequestMapping("/distribution/goods-matter")
public class DistributionGoodsMatterController {

    @Autowired
    private DistributionGoodsMatterQueryProvider distributionGoodsMatterQueryProvider;

    @Autowired
    private DistributionGoodsMatterProvider distributionGoodsMatterProvider;

    @Autowired
    private EsDistributionGoodsMatterProvider esDistributionGoodsMatterProvider;

    @Autowired
    private MobileLedgerAccountBaseService mobileLedgerAccountBaseService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页分销商品素材", hidden = true)
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<DistributionGoodsMatterPageResponse> page(@RequestBody @Valid DistributionGoodsMatterPageRequest distributionGoodsMatterPageRequest) {
        BaseResponse<DistributionGoodsMatterPageResponse> response =
                distributionGoodsMatterQueryProvider.page(distributionGoodsMatterPageRequest);
        return response;
    }


    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "增加某个SKU分销素材分享次数")
    @RequestMapping(value = "/matter-recommend-num", method = RequestMethod.POST)
    public BaseResponse deleteList(@RequestBody @Valid UpdateRecommendNumRequest updateRecommendNumRequest) {
        distributionGoodsMatterProvider.updataRecomendNumById(updateRecommendNumRequest);
        //同步es
        EsDistributionGoodsMatteByIdRequest idRequest = new EsDistributionGoodsMatteByIdRequest();
        idRequest.setId(updateRecommendNumRequest.getId());
        esDistributionGoodsMatterProvider.modifyGoodsMatter(idRequest);
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "分页查询分销商品素材")
    @RequestMapping(value = "/page/new", method = RequestMethod.POST)
    public BaseResponse<DistributionGoodsMatterPageResponse> pageNew(@RequestBody DistributionGoodsMatterPageRequest distributionGoodsMatterPageRequest) {
        //获取登录人ID
        String customerId = commonUtil.getOperatorId();

        if (StringUtils.isNotBlank(customerId)) {
            distributionGoodsMatterPageRequest.setCustomerId(customerId);
        }
        distributionGoodsMatterPageRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED);
        if (mobileLedgerAccountBaseService.getGatewayOpen()) {
            List<Long> storeIds = ledgerReceiverRelQueryProvider
                    .findSupplierIdByDistribution(DistributionLedgerRelRequest.builder().customerId(commonUtil.getOperatorId()).build())
                    .getContext().getStoreIds();
            if (CollectionUtils.isEmpty(storeIds)) {
                DistributionGoodsMatterPageResponse response = new DistributionGoodsMatterPageResponse();
                response.setDistributionGoodsMatterPage(new MicroServicePage<>());
                return BaseResponse.success(response);
            }
            distributionGoodsMatterPageRequest.setStoreIds(storeIds);
        }
        return distributionGoodsMatterQueryProvider.pageNew(distributionGoodsMatterPageRequest);
    }
}
