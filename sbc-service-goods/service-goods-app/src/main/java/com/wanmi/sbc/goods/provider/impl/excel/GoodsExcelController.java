package com.wanmi.sbc.goods.provider.impl.excel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.info.service.GoodsExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.wanmi.sbc.goods.provider.impl.excel.GoodsExcelController
 *
 * @author lipeng
 * @dateTime 2018/11/7 下午3:19
 */
@RestController
@Validated
public class GoodsExcelController implements GoodsExcelProvider {

    @Autowired
    private GoodsExcelService goodsExcelService;

    /**
     * 获取商品excel模板
     * @return base64位文件流字符串 {@link GoodsExcelExportTemplateResponse}
     */
    @Override
    public BaseResponse<GoodsExcelExportTemplateResponse> exportTemplate() {
        return BaseResponse.success(GoodsExcelExportTemplateResponse.builder()
                .file(goodsExcelService.exportTemplate()).build());
    }

    @Override
    public BaseResponse<GoodsExcelExportTemplateResponse> getTemplate(@RequestBody MarketingTemplateRequest request) {
        String template = goodsExcelService.getTemplate(request);
        return BaseResponse.success(new GoodsExcelExportTemplateResponse(template));
    }

    /**
     * 获取代客下单商品导入模版
     * @return base64位文件流字符串 {@link GoodsExcelExportTemplateResponse}
     */
    @Override
    public BaseResponse<GoodsExcelExportTemplateResponse> getOrderForCustomerTemplate() {
        return BaseResponse.success(GoodsExcelExportTemplateResponse.builder()
                .file(goodsExcelService.getOrderForCustomerTemplate()).build());
    }
}
