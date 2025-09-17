package com.wanmi.sbc.goodsrestrictedsale;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleSaveProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.*;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.*;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.Map;
import java.util.Objects;


@Tag(name =  "限售配置管理API", description =  "GoodsRestrictedSaleController")
@RestController
@Validated
@RequestMapping(value = "/goodsrestrictedsale")
public class GoodsRestrictedSaleController {

    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired
    private GoodsRestrictedSaleSaveProvider goodsRestrictedSaleSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Operation(summary = "分页查询限售配置")
    @PostMapping("/page")
    public BaseResponse<GoodsRestrictedSalePageResponse> getPage(@RequestBody @Valid GoodsRestrictedSalePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("restrictedId", "desc");
        pageReq.setStoreId(commonUtil.getStoreIdWithDefault());
        GoodsRestrictedSalePageResponse response = goodsRestrictedSaleQueryProvider.page(pageReq).getContext();
        return BaseResponse.success(response);
    }

    @Operation(summary = "列表查询限售配置")
    @PostMapping("/list")
    public BaseResponse<GoodsRestrictedSaleListResponse> getList(@RequestBody @Valid GoodsRestrictedSaleListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setStoreId(commonUtil.getStoreIdWithDefault());
        listReq.putSort("restrictedId", "desc");
        return goodsRestrictedSaleQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询限售配置")
    @Parameter(name = "restrictedId", description = "限售id", required = true)
    @GetMapping("/{restrictedId}")
    public BaseResponse<GoodsRestrictedSaleByIdResponse> getById(@PathVariable Long restrictedId) {
        if (restrictedId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsRestrictedSaleByIdRequest idReq = new GoodsRestrictedSaleByIdRequest();
        idReq.setRestrictedId(restrictedId);
        BaseResponse<GoodsRestrictedSaleByIdResponse> response = goodsRestrictedSaleQueryProvider
                .getById(GoodsRestrictedSaleByIdRequest.builder().restrictedId(restrictedId).build());
        GoodsRestrictedSaleVO goodsRestrictedSaleVO = response
                .getContext()
                .getGoodsRestrictedSaleVO();
        if (Objects.isNull(goodsRestrictedSaleVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        if (!Objects.equals(commonUtil.getStoreId(),goodsRestrictedSaleVO.getStoreId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return response;
    }

    @Operation(summary = "新增限售配置")
    @PostMapping("/add")
    public BaseResponse<GoodsRestrictedSaleAddResponse> add(@RequestBody @Valid GoodsRestrictedSaleAddRequest addReq) {
        List<String> customerIds = addReq.getCustomerIds();
        if(CollectionUtils.isNotEmpty(customerIds)){
            Map<String, LogOutStatus> logOutStatusMap = customerCacheService.getLogOutStatus(customerIds);
            boolean logOutFlag = customerIds.stream().anyMatch(customerId -> {
                LogOutStatus logOutStatus = logOutStatusMap.get(customerId);
                return Objects.nonNull(logOutStatus)
                        && !Objects.equals(logOutStatus, LogOutStatus.NORMAL);
            });
            if(logOutFlag){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010037);
            }
        }
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreateTime(LocalDateTime.now());
        addReq.setStoreId(commonUtil.getStoreIdWithDefault());
        return goodsRestrictedSaleSaveProvider.addBatch(addReq);
    }

    @Operation(summary = "修改限售配置")
    @PutMapping("/modify")
    public BaseResponse<GoodsRestrictedSaleModifyResponse> modify(@RequestBody @Valid GoodsRestrictedSaleModifyRequest modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());
        return goodsRestrictedSaleSaveProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除限售配置")
    @Parameter(name = "restrictedId", description = "限售id", required = true)
    @DeleteMapping("/{restrictedId}")
    public BaseResponse deleteById(@PathVariable Long restrictedId) {
        if (restrictedId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsRestrictedSaleVO goodsRestrictedSaleVO = goodsRestrictedSaleQueryProvider
                .getById(GoodsRestrictedSaleByIdRequest.builder().restrictedId(restrictedId).build())
                .getContext()
                .getGoodsRestrictedSaleVO();
        if (Objects.isNull(goodsRestrictedSaleVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        if (!Objects.equals(commonUtil.getStoreId(),goodsRestrictedSaleVO.getStoreId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        GoodsRestrictedSaleDelByIdRequest delByIdReq = new GoodsRestrictedSaleDelByIdRequest();
        delByIdReq.setRestrictedId(restrictedId);
        return goodsRestrictedSaleSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除限售配置")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid GoodsRestrictedSaleDelByIdListRequest delByIdListReq) {
        return goodsRestrictedSaleSaveProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出限售配置列表")
    @Parameter(name = "encrypted", description = "加密串", required = true)
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        GoodsRestrictedSaleListRequest listReq = JSON.parseObject(decrypted, GoodsRestrictedSaleListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("restrictedId", "desc");
        List<GoodsRestrictedSaleVO> dataRecords = goodsRestrictedSaleQueryProvider.list(listReq).getContext().getGoodsRestrictedSaleVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("限售配置列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<GoodsRestrictedSaleVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("店铺ID", new SpelColumnRender<GoodsRestrictedSaleVO>("storeId")),
            new Column("货品的skuId", new SpelColumnRender<GoodsRestrictedSaleVO>("goodsInfoId")),
            new Column("限售方式 0: 按订单 1：按会员", new SpelColumnRender<GoodsRestrictedSaleVO>("restrictedType")),
            new Column("是否每人限售标识 ", new SpelColumnRender<GoodsRestrictedSaleVO>("restrictedPrePersonFlag")),
            new Column("是否每单限售的标识", new SpelColumnRender<GoodsRestrictedSaleVO>("restrictedPreOrderFlag")),
            new Column("是否指定会员限售的标识", new SpelColumnRender<GoodsRestrictedSaleVO>("restrictedAssignFlag")),
            new Column("个人限售的方式(  0:终生限售  1:周期限售)", new SpelColumnRender<GoodsRestrictedSaleVO>("personRestrictedType")),
            new Column("个人限售的周期 (0:周   1:月  2:年)", new SpelColumnRender<GoodsRestrictedSaleVO>("personRestrictedCycle")),
            new Column("限售数量", new SpelColumnRender<GoodsRestrictedSaleVO>("restrictedNum")),
            new Column("起售数量", new SpelColumnRender<GoodsRestrictedSaleVO>("startSaleNum"))
        };
        excelHelper.addSheet("限售配置列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }
}
