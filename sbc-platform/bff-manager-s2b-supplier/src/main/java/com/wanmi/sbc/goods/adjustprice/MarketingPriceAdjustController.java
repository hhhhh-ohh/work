package com.wanmi.sbc.goods.adjustprice;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.adjust.PriceAdjustService;
import com.wanmi.sbc.goods.adjust.request.PriceAdjustConfirmRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionConfigProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailProvider;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceDetailDeleteRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.MarketingPriceAdjustDetailModifyRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.PriceAdjustmentTemplateExportRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMarketingPriceByNosRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;
import com.wanmi.sbc.goods.bean.dto.PriceAdjustmentRecordDetailDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCommissionConfigVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>商品批量调整市场价操作BFF</p>
 * Created by of628-wenzhi on 2020-12-11-2:41 下午.
 */
@RestController
@Validated
@RequestMapping("/goods/price-adjust/marketing-price")
@Tag(name =  "商品批量调整市场价API", description =  "MarketingPriceAdjustController")
public class MarketingPriceAdjustController {

    @Autowired
    private PriceAdjustService priceAdjustService;

    @Autowired
    private PriceAdjustmentRecordDetailProvider priceAdjustmentRecordDetailProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    GoodsCommissionConfigProvider goodsCommissionConfigProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    private final static String TAMPLATE_FILE_NAME = "批量调整市场价导入模板.xlsx";


    /**
     * 市场价调整excel模板下载
     *
     * @param encrypted
     * @return
     */
    @Operation(summary = "市场价调整excel模板下载")
    @RequestMapping(value = "/template/{encrypted}", method = RequestMethod.GET)
    @Parameter(name = "encrypted", description = "鉴权加密串", required = true)
    public void template(@PathVariable String encrypted) {
        PriceAdjustmentTemplateExportRequest request = PriceAdjustmentTemplateExportRequest.builder()
                .priceAdjustmentType(PriceAdjustmentType.MARKET).build();
        priceAdjustService.downloadAdjustPriceTemplate(request, TAMPLATE_FILE_NAME);
    }

    /**
     * 市场价调整excel文件上传
     *
     * @param uploadFile
     * @return
     */
    @Operation(summary = "市场价调价excel文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(priceAdjustService.uploadPriceAdjustFile(uploadFile,PriceAdjustmentType.MARKET,analysisFunctionByMarketingPriceAdjust));
    }

    @Operation(summary = "市场价调价详情编辑")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid MarketingPriceAdjustDetailModifyRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.modifyMarketingPrice(request);
    }

    @Operation(summary = "市场价调价详情移除")
    @RequestMapping(method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody @Valid AdjustPriceDetailDeleteRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.delete(request);
    }

    @Operation(summary = "市场价调价确认导入")
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse<String> importDo() {
        return BaseResponse.success(priceAdjustService.importPriceAdjustFile(importFunctionByMarketingPriceAdjust,
                PriceAdjustmentType.MARKET));
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @RequestMapping(value = "/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        priceAdjustService.downErrorFile(ext);
    }

    /**
     * 市场价确认调价
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @Operation(summary = "市场价确认调价")
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse confirmAdjust(@RequestBody @Valid PriceAdjustConfirmRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        priceAdjustService.adjustPriceConfirm(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验并解析市场价调价详情数据
     *
     * @param workbook
     * @return
     */
    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> analysisFunctionByMarketingPriceAdjust = (workbook) -> {
        List<PriceAdjustmentRecordDetailDTO> dataList = new ArrayList<>();
        boolean isError = false;
        Sheet sheet = workbook.getSheetAt(0);
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        CellStyle style = workbook.createCellStyle();
        int maxCell = 6;
        Map<String, Cell> goodsInfoKeyMap = new HashMap<>();
        Map<String, Cell> goodsSaleTypeMap = new HashMap<>();
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (Objects.isNull(row)) {
                continue;
            }
            Cell[] cells = new Cell[maxCell];
            boolean isNotEmpty = false;
            for (int i = 0; i < maxCell; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    cell = row.createCell(i);
                }
                cells[i] = cell;
                if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                    isNotEmpty = true;
                }
            }
            //列数据都为空，则跳过去
            if (!isNotEmpty) {
                continue;
            }
            PriceAdjustmentRecordDetailDTO detail = new PriceAdjustmentRecordDetailDTO();
            detail.setGoodsInfoNo(ExcelHelper.getValue(getCell(cells, 0)).trim());
            detail.setGoodsInfoName(ExcelHelper.getValue(getCell(cells, 1)).trim());
            detail.setGoodsSpecText(ExcelHelper.getValue(getCell(cells, 2)));
            String marketingPriceVal = ExcelHelper.getValue(getCell(cells, 3));
            //校验SKU NO
            if (StringUtils.isBlank(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "此项必填");
                isError = true;
            } else if (!ValidateUtil.isBetweenLen(detail.getGoodsInfoNo(), 1, 20)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "长度必须1-20个字");
                isError = true;
            } else if (!ValidateUtil.isNotChs(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "仅允许英文、数字、特殊字符");
                isError = true;
            } else if (goodsInfoKeyMap.containsKey(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "SKU编码重复");
                isError = true;
            } else {
                //记录skuNo列，用于检查数据是否存在和排重
                goodsInfoKeyMap.put(detail.getGoodsInfoNo(), getCell(cells, 0));
            }
            //校验SKU name
            if (StringUtils.isNotBlank(detail.getGoodsInfoName())) {
                if (!ValidateUtil.isBetweenLen(detail.getGoodsInfoName(), 1, 40)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 1), "长度必须1-40个字");
                    isError = true;
                } else if (ValidateUtil.containsEmoji(detail.getGoodsInfoName())) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 1), "含有非法字符");
                    isError = true;
                }
            }
            //校验规格值
            if (StringUtils.isNotBlank(detail.getGoodsSpecText())) {
                if (!ValidateUtil.isBetweenLen(detail.getGoodsSpecText(), 0, 20)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 2), "长度必须0-20个字");
                    isError = true;
                } else if (!ValidateUtil.isChsEngNum(detail.getGoodsSpecText()) && !ValidateUtil.isFloatNum(detail.getGoodsSpecText())) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 2), "仅允许中英文、数字");
                    isError = true;
                }
            }
            //校验调整后的市场价
            if (StringUtils.isBlank(marketingPriceVal)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 3), "此项必填");
                isError = true;
            } else if (!ValidateUtil.isNum(marketingPriceVal) && !ValidateUtil.isFloatNum(marketingPriceVal)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 3), "只能填写0和正数，允许两位小数");
                isError = true;
            } else if (new BigDecimal(marketingPriceVal).compareTo(new BigDecimal("9999999.99")) > 0) {
                ExcelHelper.setError(style,workbook, getCell(cells, 3), "只能在0-9999999.99范围内");
                isError = true;
            } else {
                detail.setAdjustedMarketPrice(new BigDecimal(marketingPriceVal));
            }
            if (!isError) {
                dataList.add(detail);
            }
        }
        if (!isError) {

            //查询商家是否是智能设价
            GoodsCommissionConfigQueryRequest queryRequest = new GoodsCommissionConfigQueryRequest();
            queryRequest.setBaseStoreId(commonUtil.getStoreId());
            GoodsCommissionConfigVO commissionConfigVO = goodsCommissionConfigProvider.query(queryRequest).getContext().getCommissionConfigVO();
            Map<String, PriceAdjustmentRecordDetailDTO> recordDetailDTOMap = dataList.stream()
                    .collect(Collectors.toMap(PriceAdjustmentRecordDetailDTO::getGoodsInfoNo, Function.identity()));
            //检查sku是否存在
            List<GoodsInfoMarketingPriceDTO> marketingPriceDTOList = goodsInfoQueryProvider.listMarketingPriceByNos(GoodsInfoMarketingPriceByNosRequest.builder()
                    .goodsInfoNos(new ArrayList<>(goodsInfoKeyMap.keySet()))
                    .storeId(commonUtil.getStoreId()).build())
                    .getContext().getDataList();

            Map<String, GoodsInfoMarketingPriceDTO> goodsInfoMarketingPriceMap = marketingPriceDTOList.stream().collect(Collectors.toMap(GoodsInfoMarketingPriceDTO::getGoodsInfoNo, m -> m));
            List<String> skuNos = marketingPriceDTOList.stream().map(GoodsInfoMarketingPriceDTO::getGoodsInfoNo)
                    .collect(Collectors.toList());

            //获取待审核记录
            List<String> goodsIds = marketingPriceDTOList.stream().map(GoodsInfoMarketingPriceDTO::getGoodsId)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> auditGoodsIds = goodsAuditQueryProvider
                    .getListByOldGoodsIds(GoodsAuditQueryRequest
                            .builder()
                            .goodsIdList(goodsIds).build())
                    .getContext()
                    .getGoodsAuditVOList()
                    .stream()
                    .filter(v->Objects.equals(CheckStatus.WAIT_CHECK,v.getAuditStatus())
                            || Objects.equals(CheckStatus.FORBADE,v.getAuditStatus()))
                    .map(GoodsAuditVO::getOldGoodsId)
                    .collect(Collectors.toList());

            //获取商品记录
            List<GoodsVO> goodsVOList = goodsQueryProvider
                    .listByIds(GoodsListByIdsRequest.builder().goodsIds(goodsIds).build()).getContext().getGoodsVOList();
            for (Map.Entry<String, Cell> entry : goodsInfoKeyMap.entrySet()) {
                GoodsInfoMarketingPriceDTO priceDTO = goodsInfoMarketingPriceMap.get(entry.getKey());

                if (!skuNos.contains(entry.getKey())) {
                    ExcelHelper.setError(style,workbook, entry.getValue(), "该商品不存在！");
                    isError = true;
                } else {
                    if (CommissionSynPriceType.AI_SYN.toValue() == commissionConfigVO.getSynPriceType().toValue()
                            && StringUtils.isNotBlank(priceDTO.getProviderGoodsInfoId())){
                        ExcelHelper.setError(style,workbook, entry.getValue(), "该商品不支持修改市场价！");
                        isError = true;
                    }

                    Cell cell = goodsSaleTypeMap.get(priceDTO.getGoodsInfoNo());
                    PriceAdjustmentRecordDetailDTO record = recordDetailDTOMap.get(priceDTO.getGoodsInfoNo());
                    if (GoodsType.REAL_GOODS.toValue() != priceDTO.getGoodsType() && SaleType.WHOLESALE.equals(record.getSaleType())) {
                        ExcelHelper.setError(style,workbook, cell, "该商品不支持修改销售类型！");
                        isError = true;
                    }
                }

                if (!isError){
                    if(auditGoodsIds.contains(priceDTO.getGoodsId())){
                        ExcelHelper.setError(style,workbook, entry.getValue(), "商品已有待审核或禁售记录");
                        isError = true;
                    }

                    GoodsVO goodsVO = goodsVOList
                            .stream()
                            .filter(v -> Objects.equals(v.getGoodsId(), priceDTO.getGoodsId()))
                            .findFirst()
                            .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
                    if (!Objects.equals(GoodsPriceType.MARKET.toValue(),goodsVO.getPriceType())){
                        ExcelHelper.setError(style,workbook, entry.getValue(), "该商品设价方式不为按市场价设价！");
                        isError = true;
                    }
                }
            }
        }
        if (isError) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030069);
        }
        return dataList;
    };

    /**
     * 校验并解析市场价调价详情数据
     *
     * @param workbook
     * @return
     */
    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> importFunctionByMarketingPriceAdjust = (workbook) -> {
        List<PriceAdjustmentRecordDetailDTO> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        int maxCell = 6;
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (Objects.isNull(row)) {
                continue;
            }
            Cell[] cells = new Cell[maxCell];
            boolean isNotEmpty = false;
            for (int i = 0; i < maxCell; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    cell = row.createCell(i);
                }
                cells[i] = cell;
                if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                    isNotEmpty = true;
                }
            }
            //列数据都为空，则跳过去
            if (!isNotEmpty) {
                continue;
            }
            PriceAdjustmentRecordDetailDTO detail = new PriceAdjustmentRecordDetailDTO();
            detail.setGoodsInfoNo(ExcelHelper.getValue(getCell(cells, 0)).trim());
            detail.setGoodsInfoName(ExcelHelper.getValue(getCell(cells, 1)).trim());
            detail.setGoodsSpecText(ExcelHelper.getValue(getCell(cells, 2)));
            String marketingPriceVal = ExcelHelper.getValue(getCell(cells, 3));
            //校验是否以市场价销售
            detail.setPriceType(GoodsPriceType.MARKET);
            //校验调整后的市场价
            detail.setAdjustedMarketPrice(new BigDecimal(marketingPriceVal));
            dataList.add(detail);
        }
        return dataList;
    };


    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }
}
