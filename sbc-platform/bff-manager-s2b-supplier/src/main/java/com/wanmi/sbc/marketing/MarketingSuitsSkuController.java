package com.wanmi.sbc.marketing;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.marketingsuitssku.MarketingSuitsSkuProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuitssku.MarketingSuitsSkuQueryProvider;
import com.wanmi.sbc.marketing.api.request.marketingsuitssku.MarketingSuitsSkuByIdRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuitssku.MarketingSuitsSkuDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuitssku.MarketingSuitsSkuListRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuitssku.MarketingSuitsSkuPageRequest;
import com.wanmi.sbc.marketing.api.response.marketingsuitssku.MarketingSuitsSkuByIdResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuitssku.MarketingSuitsSkuListResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuitssku.MarketingSuitsSkuPageResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;


@Tag(name =  "组合活动关联商品sku表管理API", description =  "MarketingSuitsSkuController")
@RestController
@Validated
@RequestMapping(value = "/marketingsuitssku")
public class MarketingSuitsSkuController {

    @Autowired
    private MarketingSuitsSkuQueryProvider marketingSuitsSkuQueryProvider;

    @Autowired
    private MarketingSuitsSkuProvider marketingSuitsSkuSaveProvider;


    @Operation(summary = "分页查询组合活动关联商品sku表")
    @PostMapping("/page")
    public BaseResponse<MarketingSuitsSkuPageResponse> getPage(@RequestBody @Valid MarketingSuitsSkuPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return marketingSuitsSkuQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询组合活动关联商品sku表")
    @PostMapping("/list")
    public BaseResponse<MarketingSuitsSkuListResponse> getList(@RequestBody @Valid MarketingSuitsSkuListRequest listReq) {
        return marketingSuitsSkuQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询组合活动关联商品sku表")
    @Parameter(name = "id", description = "组合Id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<MarketingSuitsSkuByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MarketingSuitsSkuByIdRequest idReq = new MarketingSuitsSkuByIdRequest();
        idReq.setId(id);
        return marketingSuitsSkuQueryProvider.getById(idReq);
    }

    @Operation(summary = "根据idList批量删除组合活动关联商品sku表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid MarketingSuitsSkuDelByIdListRequest delByIdListReq) {
        return marketingSuitsSkuSaveProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出组合活动关联商品sku表列表")
    @Parameter(name = "encrypted", description = "加密串", required = true)
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        MarketingSuitsSkuListRequest listReq = JSON.parseObject(decrypted, MarketingSuitsSkuListRequest.class);
//        listReq.putSort("id", "desc");
        List<MarketingSuitsSkuVO> dataRecords = marketingSuitsSkuQueryProvider.list(listReq).getContext().getMarketingSuitsSkuVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("组合活动关联商品sku表列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            exportDataList(dataRecords, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 导出列表数据具体实现
     */
    private void exportDataList(List<MarketingSuitsSkuVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("组合id", new SpelColumnRender<MarketingSuitsSkuVO>("suitsId")),
            new Column("促销活动id", new SpelColumnRender<MarketingSuitsSkuVO>("marketingId")),
            new Column("skuId", new SpelColumnRender<MarketingSuitsSkuVO>("skuId")),
            new Column("单个优惠价格（优惠多少）", new SpelColumnRender<MarketingSuitsSkuVO>("discountPrice")),
            new Column("sku数量", new SpelColumnRender<MarketingSuitsSkuVO>("num"))
        };
        excelHelper.addSheet("组合活动关联商品sku表列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
