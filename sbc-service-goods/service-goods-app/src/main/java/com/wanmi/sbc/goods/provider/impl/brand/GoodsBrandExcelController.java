package com.wanmi.sbc.goods.provider.impl.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandExcelProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandExcelImportRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandExcelImportResponse;
import com.wanmi.sbc.goods.brand.service.GoodsBrandExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @description 品牌导入
 * @author malianfeng
 * @date 2022/8/31 10:30
 */
@RestController
@Validated
public class GoodsBrandExcelController implements GoodsBrandExcelProvider {

    @Autowired
    private GoodsBrandExcelService goodsBrandExcelService;

    /**
     * 导入商品品牌
     *
     * @param request {@link GoodsBrandExcelImportRequest}
     * @return 返回 {@link GoodsBrandExcelImportResponse}
     */
    @Override
    public BaseResponse<GoodsBrandExcelImportResponse> importGoodsBrand(@RequestBody @Valid GoodsBrandExcelImportRequest request) {
        GoodsBrandExcelImportResponse response = new GoodsBrandExcelImportResponse();
        response.setFlag(goodsBrandExcelService.importGoodsBrand(request.getGoodsBrandList()));
        return BaseResponse.success(response);
    }
}
