package com.wanmi.sbc.elastic.provider.impl.distributionmatter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterProvider;
import com.wanmi.sbc.elastic.api.request.distributionmatter.*;
import com.wanmi.sbc.elastic.distributionmatter.service.EsDistributionGoodsMatterService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/8 14:06
 * @description <p> 商品素材 </p>
 */
@RestController
public class EsDistributionGoodsMatterController implements EsDistributionGoodsMatterProvider {

    @Autowired
    private EsDistributionGoodsMatterService esDistributionGoodsMatterService;

    /**
     * 新增商品素材
     *
     * @param request {@link EsDistributionGoodsMatteAddRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse add(@RequestBody @Valid EsDistributionGoodsMatteAddRequest request) {
        return esDistributionGoodsMatterService.add(request);
    }

    /**
     * 修改商品素材
     *
     * @param request {@link EsDistributionGoodsMatteAddRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse modify(@RequestBody @Valid EsDistributionGoodsMatteAddRequest request) {
        esDistributionGoodsMatterService.modify(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始化商品素材
     *
     * @param request {@link EsDistributionGoodsMatterPageRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse init(@RequestBody @Valid EsDistributionGoodsMatterInitRequest request) {
        if(CollectionUtils.isNotEmpty(request.getIdList())){
            request.setPageNum(0);
            request.setPageSize(request.getIdList().size());
        }
        esDistributionGoodsMatterService.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除分销素材
     *
     * @param request {@link EsDeleteByIdListRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse deleteList(@RequestBody @Valid EsDeleteByIdListRequest request) {
        esDistributionGoodsMatterService.deleteList(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改分销素材
     *
     * @param request {@link EsDistributionGoodsMatteByIdRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse modifyGoodsMatter(@RequestBody @Valid EsDistributionGoodsMatteByIdRequest request) {
        esDistributionGoodsMatterService.modifyGoodsMatter(request);
        return BaseResponse.SUCCESSFUL();
    }
}
