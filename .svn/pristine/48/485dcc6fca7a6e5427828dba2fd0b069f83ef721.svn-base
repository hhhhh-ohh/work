package com.wanmi.sbc.goods.api.provider.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandExcelImportRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateExcelImportRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandExcelImportResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateExcelImportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 导入商品品牌
 * @author malianfeng
 * @date 2022/8/30 14:59
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsBrandExcelProvider")
public interface GoodsBrandExcelProvider {

    /**
     * 导入商品品牌
     *
     * @param request 请求对象  {@link GoodsCateExcelImportRequest}
     * @return 返回实体 {@link GoodsCateExcelImportResponse}
     */
    @PostMapping("/goods/${application.goods.version}/brand/excel/import")
    BaseResponse<GoodsBrandExcelImportResponse> importGoodsBrand(@RequestBody @Valid GoodsBrandExcelImportRequest request);
}
