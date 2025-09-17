package com.wanmi.sbc.elastic.api.provider.distributionmatter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDeleteByIdListRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatteAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatteByIdRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterInitRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/8 13:55
 * @description <p>商品素材  </p>
 */
@FeignClient(name = "${application.elastic.name}",contextId = "EsDistributionGoodsMatterProvider")
public interface EsDistributionGoodsMatterProvider {

    /**
     * 新增商品素材
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/add")
    BaseResponse add(@RequestBody @Valid EsDistributionGoodsMatteAddRequest request);

    /**
     * 修改商品
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/modify")
    BaseResponse modify(@RequestBody @Valid EsDistributionGoodsMatteAddRequest request);

    /**
     * 初始化分销素材
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/init")
    BaseResponse init(@RequestBody @Valid EsDistributionGoodsMatterInitRequest request);

    /**
     * 删除分销素材
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/delete-list")
    BaseResponse deleteList(@RequestBody @Valid EsDeleteByIdListRequest request);


    /**
     * 修改分销素材
     *
     * @param request
     * @return BaseResponse
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution-goods-matter/modify-recomend-num")
    BaseResponse modifyGoodsMatter(@RequestBody @Valid EsDistributionGoodsMatteByIdRequest request);
}
