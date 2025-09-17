package com.wanmi.sbc.marketing;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsByIdRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsListRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsPageRequest;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsByIdResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsListResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsPageResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsVO;
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


@Tag(name =  "组合商品主表管理API", description =  "MarketingSuitsController")
@RestController
@Validated
@RequestMapping(value = "/marketingsuits")
public class MarketingSuitsController {

    @Autowired
    private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired
    private MarketingSuitsProvider marketingSuitsSaveProvider;

    @Operation(summary = "分页查询组合商品主表")
    @PostMapping("/page")
    public BaseResponse<MarketingSuitsPageResponse> getPage(@RequestBody @Valid MarketingSuitsPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return marketingSuitsQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询组合商品主表")
    @PostMapping("/list")
    public BaseResponse<MarketingSuitsListResponse> getList(@RequestBody @Valid MarketingSuitsListRequest listReq) {
        return marketingSuitsQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询组合商品主表")
    @Parameter(name = "id", description = "组合Id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<MarketingSuitsByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MarketingSuitsByIdRequest idReq = new MarketingSuitsByIdRequest();
        idReq.setId(id);
        return marketingSuitsQueryProvider.getById(idReq);
    }

    @Operation(summary = "根据idList批量删除组合商品主表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid MarketingSuitsDelByIdListRequest delByIdListReq) {
        return marketingSuitsSaveProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出组合商品主表列表")
    @Parameter(name = "encrypted", description = "加密串", required = true)
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        MarketingSuitsListRequest listReq = JSON.parseObject(decrypted, MarketingSuitsListRequest.class);
        List<MarketingSuitsVO> dataRecords = marketingSuitsQueryProvider.list(listReq).getContext().getMarketingSuitsVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("组合商品主表列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<MarketingSuitsVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("促销id", new SpelColumnRender<MarketingSuitsVO>("marketingId")),
            new Column("套餐主图（图片url全路径）", new SpelColumnRender<MarketingSuitsVO>("mainImage")),
            new Column("套餐价格", new SpelColumnRender<MarketingSuitsVO>("suitsPrice"))
        };
        excelHelper.addSheet("组合商品主表列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
