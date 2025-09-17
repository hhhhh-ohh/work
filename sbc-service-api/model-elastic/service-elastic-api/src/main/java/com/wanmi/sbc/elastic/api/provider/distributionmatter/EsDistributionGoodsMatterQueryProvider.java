package com.wanmi.sbc.elastic.api.provider.distributionmatter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionmatter.EsDistributionGoodsMatterPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/1/8 15:26
 * @description <p> 商品素材查询 </p>
 */
@FeignClient(name = "${application.elastic.name}",contextId = "EsDistributionGoodsMatterQueryProvider")
public interface EsDistributionGoodsMatterQueryProvider {

    /**
     * 分页查询商品素材
     *
     * @param  request 查询条件
     * @return  {@link EsDistributionGoodsMatterPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/new-for-boss")
    BaseResponse<EsDistributionGoodsMatterPageResponse> pageNewForBoss(@RequestBody EsDistributionGoodsMatterPageRequest request);
}
