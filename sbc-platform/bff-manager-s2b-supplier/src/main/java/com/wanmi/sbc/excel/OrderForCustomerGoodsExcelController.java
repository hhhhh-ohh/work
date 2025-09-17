package com.wanmi.sbc.excel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.excel.service.OrderForCustomerGoodsExcelService;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.bean.vo.OrderForCustomerGoodsInfoExcelVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @author EDZ
 * @className OrderForCustomerGoodsExcelController
 * @description TODO
 * @date 2022/4/7 17:39
 **/
@Tag(name =  "OrderForCustomerGoodsExcelController", description =  "代客下单商品导入功能")
@RestController
@Validated
@RequestMapping("/order-for-customer")
public class OrderForCustomerGoodsExcelController {

    @Resource
    private GoodsExcelProvider goodsExcelProvider;

    @Resource
    private OrderForCustomerGoodsExcelService orderForCustomerGoodsExcelService;

    /**
     * 下载代客下单商品导入模版
     *
     */
    @Operation(summary = "下载代客下单商品导入模版")
    @GetMapping("/goods/template")
    public void downloadTemplate() {
        GoodsExcelExportTemplateResponse response = goodsExcelProvider.getOrderForCustomerTemplate().getContext();
        String file = response.getFile();
        if(StringUtils.isNotBlank(file)){
            try {
                String fileName = URLEncoder.encode("代客下单商品导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition",
                        String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse()
                        .getOutputStream()
                        .write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }

    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @PostMapping("/excel/upload")
    public BaseResponse<String> upload(@RequestPart("uploadFile") MultipartFile uploadFile
            ,@RequestPart List<OrderForCustomerGoodsInfoExcelVO> selectedSkus){
//        List<OrderForCustomerGoodsInfoExcelVO> selectedSkus = JSON.parseArray(selectedSkusStr, OrderForCustomerGoodsInfoExcelVO.class);
        String fileExt = orderForCustomerGoodsExcelService.uploadFile(uploadFile,selectedSkus);
        return BaseResponse.success(fileExt);
    }

    /**
     * 查询导入数据
     */
    @Operation(summary = "查询导入数据")
    @PostMapping("/goods/list")
    public BaseResponse<EsSkuPageResponse> getGoodsInfoList(@RequestBody EsSkuPageRequest queryRequest) {
        BaseResponse<EsSkuPageResponse> goodsInfoList = orderForCustomerGoodsExcelService.getGoodsInfoList(queryRequest);
        return goodsInfoList;
    }

    /**
     * 下载错误文件
     * @param ext
     */
    @Operation(summary = "下载错误文件")
    @GetMapping("/excel/err/{ext}")
    public void downErrExcel(@PathVariable String ext) {
        if (!StringUtils.equalsAnyIgnoreCase(ext, Constants.XLS,Constants.XLSX)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        orderForCustomerGoodsExcelService.downloadErrorFile(ext);
    }
}