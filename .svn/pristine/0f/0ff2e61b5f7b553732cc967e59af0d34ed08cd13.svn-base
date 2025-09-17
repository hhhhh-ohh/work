package com.wanmi.sbc.goods.api.provider.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandTransferByStoreIdRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>对签约品牌操作接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@FeignClient(value = "${application.goods.name}", contextId = "ContractBrandProvider")
public interface ContractBrandProvider {

    /**
     * 根据店铺id迁移签约品牌
     *
     * @param request 包含店铺id的数据结构 {@link ContractBrandTransferByStoreIdRequest}
     * @return 操作结构 {@link BaseResponse}
     */
    @PostMapping("/goods/${application.goods.version}/brand/contract/transfer-by-store-id")
    BaseResponse<List<GoodsBrandVO>> transferByStoreId(@RequestBody @Valid ContractBrandTransferByStoreIdRequest request);


}
