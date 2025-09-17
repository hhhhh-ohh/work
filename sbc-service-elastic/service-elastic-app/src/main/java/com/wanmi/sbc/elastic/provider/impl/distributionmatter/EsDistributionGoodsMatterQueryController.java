package com.wanmi.sbc.elastic.provider.impl.distributionmatter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionmatter.EsDistributionGoodsMatterPageResponse;
import com.wanmi.sbc.elastic.distributionmatter.service.EsDistributionGoodsMatterQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2021/1/8 15:35
 * @description <p> 商品素材查询 </p>
 */
@RestController
public class EsDistributionGoodsMatterQueryController implements EsDistributionGoodsMatterQueryProvider {

    @Autowired
    private EsDistributionGoodsMatterQueryService esDistributionGoodsMatterQueryService;

    /**
     * 分页查询商品素材
     *
     * @param  request 查询条件
     * @return  {@link EsDistributionGoodsMatterPageResponse}
     */
    @Override
    public BaseResponse<EsDistributionGoodsMatterPageResponse> pageNewForBoss(@RequestBody EsDistributionGoodsMatterPageRequest request) {
        return esDistributionGoodsMatterQueryService.pageNewForBoss(request);
    }
}
