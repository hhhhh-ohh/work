package com.wanmi.sbc.goods.adjustprice;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.adjust.PriceAdjustService;
import com.wanmi.sbc.goods.adjust.request.PriceAdjustConfirmRequest;
import com.wanmi.sbc.goods.api.provider.adjustprice.PriceAdjustmentImportProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailProvider;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceDetailDeleteRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.PriceAdjustmentTemplateExportRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.SupplyPriceAdjustDetailModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.bean.dto.PriceAdjustmentRecordDetailDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RestController
@Validated
@RequestMapping("/goods/price-adjust/supply-price")
@Tag(name =  "商品批量调整供货价API", description =  "PriceAdjustController")
public class PriceAdjustController {

    @Autowired
    private PriceAdjustmentImportProvider adjustmentImportProvider;

    @Autowired
    private PriceAdjustService priceAdjustService;

    @Autowired
    private CommonUtil commonUtil;

    private final static String TAMPLATE_FILE_NAME = "批量调整供货价导入模板.xlsx";

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private PriceAdjustmentRecordDetailProvider priceAdjustmentRecordDetailProvider;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Operation(summary = "修改供货价调价详情")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid SupplyPriceAdjustDetailModifyRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.modifySupplyPrice(request);
    }

    @Operation(summary = "供货价调价详情移除")
    @RequestMapping(method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody @Valid AdjustPriceDetailDeleteRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.delete(request);
    }

    /**
     * 供货价确认调价
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "供货价确认调价")
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public BaseResponse confirmAdjust(@RequestBody @Valid PriceAdjustConfirmRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        priceAdjustService.adjustPriceConfirm(request);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 供货价调整excel模板下载
     *
     * @param encrypted
     * @return
     */
    @Operation(summary = "供货价调整excel模板下载")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        PriceAdjustmentTemplateExportRequest request = PriceAdjustmentTemplateExportRequest.builder()
                .priceAdjustmentType(PriceAdjustmentType.SUPPLY).build();
        request.setStoreId(commonUtil.getStoreId());
        adjustmentImportProvider.exportAjustmentPriceTemplate(request).getContext().getFileOutputStream();
        priceAdjustService.downloadAdjustPriceTemplate(request, TAMPLATE_FILE_NAME);
    }


    /**
     * 供货价调整excel文件上传
     *
     * @param uploadFile
     * @return
     */
    @Operation(summary = "供货价调整excel文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(
                priceAdjustService.uploadPriceAdjustFile(
                        uploadFile,
                        PriceAdjustmentType.SUPPLY,
                        analysisFunctionBySupplyPriceAdjust));
    }

    /**
     * 确认导入模版
     */
    @Operation(summary = "确认导入模版")
    @Parameter(name = "ext", description = "后缀", required = true)
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse<String> implGoods() {
        String adjustNo = priceAdjustService.importPriceAdjustFile(importFunctionBySupplyPriceAdjust, PriceAdjustmentType.SUPPLY);
        return BaseResponse.success(adjustNo);
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @RequestMapping(value = "/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        priceAdjustService.downErrorFile(ext);
    }

    /**
     * 校验并解析市场价调价详情数据
     *
     * @param workbook
     * @return
     */
    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> analysisFunctionBySupplyPriceAdjust = (workbook) -> {
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        boolean isError = false;
        int maxCell = 4;
        //存储编码以及单元格对象，验证重复
        Map<String, Cell> skuNos = new HashMap<>();
        if(lastRowNum > Constants.NUM_5000){
            int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
            if ((lastRowNum - emptyRowNum) > Constants.NUM_5000) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"文件数据超过5000条，请修改");
            }
        }
        this.checkExcel(workbook);
        CellStyle style = workbook.createCellStyle();
        //循环除了第一行的所有行
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            //获得当前行
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            boolean isNotEmpty = false;
            Cell[] cells = new Cell[maxCell];
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
            //数据都为空，则跳过去
            if (!isNotEmpty) {
                continue;
            }
            //SKU编码
            String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 0));
            if (StringUtils.isBlank(goodsInfoNo)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "必填，请填写要调价的商品sku编码");
                isError = true;
            } else if (goodsInfoNo.length() > 20) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "长度必须1-20个字");
                isError = true;
            } else if (!ValidateUtil.isNotChs(goodsInfoNo)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "仅允许英文、数字、特殊字符");
                isError = true;
            } else if (skuNos.containsKey(goodsInfoNo)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 0), "文档中出现重复的SKU编码");
                isError = true;
            }

            //商品名称
            String goodsInfoName = ExcelHelper.getValue(getCell(cells, 1));
            if (StringUtils.isNotBlank(goodsInfoName)) {
                if (!ValidateUtil.isBetweenLen(goodsInfoName, 1, 40)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 1), "长度必须1-40个字");
                    isError = true;
                } else if (ValidateUtil.containsEmoji(goodsInfoName)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 1), "含有非法字符");
                    isError = true;
                }
            }

            //规格
            String spec = ExcelHelper.getValue(getCell(cells, 2));
            if (StringUtils.isNotBlank(spec)) {
                if (!ValidateUtil.isBetweenLen(spec, 1, 20)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 2), "长度必须1-20个字");
                    isError = true;
                } else if (!ValidateUtil.isChsEngNum(spec) && !ValidateUtil.isFloatNum(spec)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 2), "含有非法字符");
                    isError = true;
                }
            }

            //调整后供货价
            String supplyPrice = ExcelHelper.getValue(getCell(cells, 3));
            if (StringUtils.isBlank(supplyPrice)) {
                ExcelHelper.setError(style,workbook, getCell(cells, 3), "此项必填");
                isError = true;
            } else {
                if (!ValidateUtil.isNum(supplyPrice) && !ValidateUtil.isFloatNum(supplyPrice)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 3), "必须为0或正数，必须在0-9999999.99范围内");
                    isError = true;
                } else {
                    BigDecimal price = new BigDecimal(supplyPrice);
                    if (price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(new BigDecimal("9999999.99")) > 0) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 3), "必须为0或正数，必须在0-9999999.99范围内");
                        isError = true;
                    }
                }
            }

            if (!isError) {
                skuNos.put(goodsInfoNo, getCell(cells, 0));
            }

        }

        if (skuNos.isEmpty()){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030069);
        }

        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().delFlag
                        (DeleteFlag.NO.toValue()).goodsInfoNos(new ArrayList<>(skuNos.keySet())).storeId(commonUtil.getStoreId())
                .build()).getContext().getGoodsInfos();

        Map<String, GoodsInfoVO> nosMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));

        List<String> goodsIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());

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

        //商品是否存在
        for (Map.Entry<String, Cell> entry : skuNos.entrySet()) {
            Cell cell = skuNos.get(entry.getKey());
            if (!nosMap.containsKey(entry.getKey())) {
                ExcelHelper.setError(style,workbook, cell, "商品不存在");
                isError = true;
                continue;
            }

            if(auditGoodsIds.contains(nosMap.get(entry.getKey()).getGoodsId())){
                ExcelHelper.setError(style,workbook, cell, "商品已有待审核或禁售记录");
                isError = true;
            }
        }

        if (isError) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030069);
        }

        return Collections.emptyList();
    };

    /**
     * 校验并解析市场价调价详情数据
     *
     * @param workbook
     * @return
     */
    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> importFunctionBySupplyPriceAdjust = (workbook) -> {
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int maxCell = 4;
        //存储编码以及单元格对象，验证重复
        //SkuMap<Sku编号,调价详情对象>
        Map<String, PriceAdjustmentRecordDetailDTO> details = new LinkedHashMap<>();
        this.checkExcel(workbook);
        //循环除了第一行的所有行
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            //获得当前行
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            boolean isNotEmpty = false;
            Cell[] cells = new Cell[maxCell];
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
            //数据都为空，则跳过去
            if (!isNotEmpty) {
                continue;
            }
            PriceAdjustmentRecordDetailDTO detailDTO = new PriceAdjustmentRecordDetailDTO();
            //SKU编码
            String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 0));

            detailDTO.setGoodsInfoNo(goodsInfoNo);


            //商品名称
            String goodsInfoName = ExcelHelper.getValue(getCell(cells, 1));
            if (StringUtils.isNotBlank(goodsInfoName)) {
                detailDTO.setGoodsInfoName(goodsInfoName);
            }

            //规格
            String spec = ExcelHelper.getValue(getCell(cells, 2));
            if (StringUtils.isNotBlank(spec)) {
                detailDTO.setGoodsSpecText(spec);
            }
            //调整后供货价
            String supplyPrice = ExcelHelper.getValue(getCell(cells, 3));
            if (StringUtils.isNotBlank(supplyPrice)) {
                BigDecimal price = new BigDecimal(supplyPrice);
                detailDTO.setAdjustSupplyPrice(price);
            }
            details.put(detailDTO.getGoodsInfoNo(), detailDTO);
        }

        return new ArrayList<>(details.values());
    };

    /**
     * 验证EXCEL
     *
     * @param workbook
     */
    private void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row firstRow = sheet1.getRow(0);
            if (!(firstRow.getCell(0).getStringCellValue().contains("SKU编码") && firstRow.getCell(3).getStringCellValue().contains("调整后供货价"))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }
}
