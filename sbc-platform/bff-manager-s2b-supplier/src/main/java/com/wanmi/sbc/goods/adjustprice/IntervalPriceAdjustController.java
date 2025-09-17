package com.wanmi.sbc.goods.adjustprice;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
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
import com.wanmi.sbc.goods.api.request.adjustprice.IntervalPriceAdjustDetailModifyRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.PriceAdjustmentTemplateExportRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMarketingPriceByNosRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
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
import lombok.extern.slf4j.Slf4j;
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
 * <p>商品阶梯价批量调价Controller</p>
 * Created by of628-wenzhi on 2020-12-23-2:42 下午.
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/goods/price-adjust/interval-price")
@Tag(name =  "商品批量调整区间价API", description =  "LevelPriceAdjustController")
public class IntervalPriceAdjustController {
    private final static String TAMPLATE_FILE_NAME = "批量调整阶梯价导入模板.xlsx";

    @Autowired
    private PriceAdjustService priceAdjustService;

    @Autowired
    private PriceAdjustmentRecordDetailProvider priceAdjustmentRecordDetailProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GoodsCommissionConfigProvider goodsCommissionConfigProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    /**
     * 区间价调整excel模板下载
     *
     * @param encrypted
     * @return
     */
    @Operation(summary = "商品区间价调整excel模板下载")
    @RequestMapping(value = "/template/{encrypted}", method = RequestMethod.GET)
    @Parameter(name = "encrypted", description = "鉴权加密串", required = true)
    public void template(@PathVariable String encrypted) {
        PriceAdjustmentTemplateExportRequest request = PriceAdjustmentTemplateExportRequest.builder()
                .priceAdjustmentType(PriceAdjustmentType.STOCK).build();
        priceAdjustService.downloadAdjustPriceTemplate(request, TAMPLATE_FILE_NAME);
    }

    /**
     * 区间价调整excel文件上传
     *
     * @param uploadFile
     * @return
     */
    @Operation(summary = "商品区间价调价excel文件上传")
    @PostMapping(value = "/upload")
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        return BaseResponse.success(priceAdjustService.uploadPriceAdjustFile(uploadFile,PriceAdjustmentType.STOCK,analysisFunctionByIntervalPriceAdjust));
    }

    /**
     * 删除调价详情
     *
     * @param request
     * @return
     */
    @Operation(summary = "商品区间价调价详情移除")
    @DeleteMapping
    public BaseResponse delete(@RequestBody @Valid AdjustPriceDetailDeleteRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.delete(request);
    }

    /**
     * 确认导入
     *
     * @return
     */
    @Operation(summary = "商品区间价调价确认导入")
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse<String> importDo() {
        return BaseResponse.success(priceAdjustService.importPriceAdjustFile(importPriceAdjust,
                PriceAdjustmentType.STOCK));
    }

    /**
     * 编译调价详情
     *
     * @param request
     * @return
     */
    @Operation(summary = "商品区间价调价详情编辑")
    @PutMapping
    public BaseResponse edit(@RequestBody @Valid IntervalPriceAdjustDetailModifyRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        return priceAdjustmentRecordDetailProvider.modifyIntervalPrice(request);
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(name = "ext", description = "后缀", required = true),
            @Parameter(name = "decrypted", description = "解密", required = true)
    })
    @GetMapping(value = "/err/{ext}/{decrypted}")
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        priceAdjustService.downErrorFile(ext);
    }

    /**
     * 区间价确认调价
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @Operation(summary = "商品区间价确认调价")
    @PostMapping(value = "/do")
    @GlobalTransactional
    public BaseResponse confirmAdjust(@RequestBody @Valid PriceAdjustConfirmRequest request) {
        priceAdjustService.checkOperator(request.getAdjustNo());
        priceAdjustService.adjustPriceConfirm(request);
        return BaseResponse.SUCCESSFUL();
    }

    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> analysisFunctionByIntervalPriceAdjust = (workbook) -> {
        List<PriceAdjustmentRecordDetailDTO> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        int maxCell = 15;
        Map<String, Cell> goodsInfoKeyMap = new HashMap<>();
        boolean isError = false;
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
            //基础信息
            PriceAdjustmentRecordDetailDTO detail = new PriceAdjustmentRecordDetailDTO();
            detail.setGoodsInfoNo(ExcelHelper.getValue(getCell(cells, 0)).trim());
            detail.setGoodsInfoName(ExcelHelper.getValue(getCell(cells, 1)).trim());
            detail.setGoodsSpecText(ExcelHelper.getValue(getCell(cells, 2)));
            String marketingPriceVal = ExcelHelper.getValue(getCell(cells, 3)).trim();
            String isAloneFlagVal = ExcelHelper.getValue(getCell(cells, 4));

            //校验SKU NO
            if (StringUtils.isBlank(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(workbook, getCell(cells, 0), "此项必填",style);
                isError = true;
            } else if (!ValidateUtil.isBetweenLen(detail.getGoodsInfoNo(), 1, 20)) {
                ExcelHelper.setError(workbook, getCell(cells, 0), "长度必须1-20个字",style);
                isError = true;
            } else if (!ValidateUtil.isNotChs(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(workbook, getCell(cells, 0), "仅允许英文、数字、特殊字符",style);
                isError = true;
            } else if (goodsInfoKeyMap.containsKey(detail.getGoodsInfoNo())) {
                ExcelHelper.setError(workbook, getCell(cells, 0), "SKU编码重复",style);
                isError = true;
            } else {
                //记录skuNo列，用于检查数据是否存在和排重
                goodsInfoKeyMap.put(detail.getGoodsInfoNo(), getCell(cells, 0));
            }
            //校验SKU name
            if (StringUtils.isNotBlank(detail.getGoodsInfoName())) {
                if (!ValidateUtil.isBetweenLen(detail.getGoodsInfoName(), 1, 40)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "长度必须1-40个字",style);
                    isError = true;
                } else if (ValidateUtil.containsEmoji(detail.getGoodsInfoName())) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "含有非法字符",style);
                    isError = true;
                }
            }
            //校验规格值
            if (StringUtils.isNotBlank(detail.getGoodsSpecText())) {
                if (!ValidateUtil.isBetweenLen(detail.getGoodsSpecText(), 0, 20)) {
                    ExcelHelper.setError(workbook, getCell(cells, 2), "长度必须0-20个字",style);
                    isError = true;
                } else if (!ValidateUtil.isChsEngNum(detail.getGoodsSpecText()) && !ValidateUtil.isFloatNum(detail.getGoodsSpecText())) {
                    ExcelHelper.setError(workbook, getCell(cells, 2), "仅允许中英文、数字",style);
                    isError = true;
                }
            }
            //销售类型默认均为批发
            detail.setSaleType(SaleType.WHOLESALE);
            //校验调整后的市场价
            if (StringUtils.isNotBlank(marketingPriceVal)) {
                if ((!ValidateUtil.isNum(marketingPriceVal) && !ValidateUtil.isFloatNum(marketingPriceVal))) {
                    ExcelHelper.setError(workbook, getCell(cells, 3), "只能填写0和正数，允许两位小数",style);
                    isError = true;
                } else if (new BigDecimal(marketingPriceVal).compareTo(new BigDecimal("9999999.99")) > 0) {
                    ExcelHelper.setError(workbook, getCell(cells, 3), "只能在0-9999999.99范围内",style);
                    isError = true;
                } else {
                    detail.setAdjustedMarketPrice(new BigDecimal(marketingPriceVal));
                }
            }
            //校验是否独立设价
            if (StringUtils.isNotBlank(isAloneFlagVal)) {
                if (!"是".equals(isAloneFlagVal) && !"否".equals(isAloneFlagVal)) {
                    ExcelHelper.setError(workbook, getCell(cells, 4), "选项不合法",style);
                    isError = true;
                } else {
                    detail.setAloneFlag("是".equals(isAloneFlagVal));
                }
            }
            List<GoodsIntervalPriceDTO> intervalPriceList = new ArrayList<>(5);
            //校验区间信息
            List<Long> countList = new ArrayList<>();
            //记录下最后一个设置了区间信息的index，用于校验跨区间
            int index = 5;
            for (int i = 5; i < maxCell; i += 2) {
                //记录上一个已设置区间信息的索引
                int y = index;
                //默认当前区间设置了区间信息
                index = i;
                String count = ExcelHelper.getValue(cells[i]).trim();
                String price = ExcelHelper.getValue(cells[i + 1]).trim();
                count = StringUtils.isBlank(count) && i == 5 ? "1" : count;
                String countTemp = null;
                if (StringUtils.isBlank(count)) {
                    if (StringUtils.isNotBlank(price)) {
                        ExcelHelper.setError(workbook, cells[i], "区间未填写",style);
                        isError = true;
                    } else {
                        //当前未设置任何区间信息，重置为上一个已设置区间信息的index
                        index = y;
                    }
                } else if (!ValidateUtil.isNum(countTemp = new BigDecimal(count).longValue() + "") || Integer.parseInt(countTemp) > 999999) {
                    ExcelHelper.setError(workbook, cells[i], "只能填写0-999999的整数",style);
                    isError = true;
                } else if (countList.contains(Long.valueOf(countTemp))) {
                    ExcelHelper.setError(workbook, cells[i], "区间重复",style);
                    isError = true;
                } else {
                    countList.add(Long.valueOf(countTemp));
                }
                if (StringUtils.isBlank(price)) {
                    if (StringUtils.isNotBlank(count)) {
                        ExcelHelper.setError(workbook, cells[i + 1], "区间价未填写",style);
                        isError = true;
                    }
                } else if (!ValidateUtil.isNum(price) && !ValidateUtil.isFloatNum(price)) {
                    ExcelHelper.setError(workbook, cells[i + 1], "只能填写0和正数，允许两位小数",style);
                    isError = true;
                } else if (new BigDecimal(price).compareTo(new BigDecimal("9999999.99")) > 0) {
                    ExcelHelper.setError(workbook, cells[i + 1], "只能在0-9999999.99范围内",style);
                    isError = true;
                }
                if (!isError) {
                    GoodsIntervalPriceDTO intervalPrice = new GoodsIntervalPriceDTO();
                    intervalPrice.setPrice(StringUtils.isNotBlank(price) ? new BigDecimal(price) : null);
                    intervalPrice.setCount(StringUtils.isNotBlank(countTemp) ? Long.valueOf(countTemp) : null);
                    intervalPrice.setType(PriceType.SKU);
                    intervalPriceList.add(intervalPrice);
                }
            }
            log.info("countList size {}", countList.size());
            //校验跨区间
            for (int i = index - 2; i > 6; i -= 2) {
                String count = ExcelHelper.getValue(cells[i]).trim();
                String price = ExcelHelper.getValue(cells[i + 1]).trim();
                if (StringUtils.isBlank(count) && StringUtils.isBlank(price)) {
                    ExcelHelper.setError(workbook, cells[i], "订货区间请按顺序设置",style);
                    ExcelHelper.setError(workbook, cells[i + 1], "订货区间请按顺序设置",style);
                    isError = true;
                }
            }
            if (!isError) {
                detail.setIntervalPrice(JSONObject.toJSONString(intervalPriceList));
                dataList.add(detail);
            }

        }
        if (!isError) {

            //查询商家是否是智能设价
            GoodsCommissionConfigQueryRequest queryRequest = new GoodsCommissionConfigQueryRequest();
            queryRequest.setBaseStoreId(commonUtil.getStoreId());
            GoodsCommissionConfigVO commissionConfigVO = goodsCommissionConfigProvider.query(queryRequest).getContext().getCommissionConfigVO();

            //检查sku是否存在
            List<GoodsInfoMarketingPriceDTO> goodsInfoMarketingPriceList = goodsInfoQueryProvider.listMarketingPriceByNos(new GoodsInfoMarketingPriceByNosRequest(
                    new ArrayList<>(goodsInfoKeyMap.keySet()), commonUtil.getStoreId())).getContext().getDataList();
            Map<String, GoodsInfoMarketingPriceDTO> goodsInfoMarketingPriceMap = goodsInfoMarketingPriceList.stream().collect(Collectors.toMap(GoodsInfoMarketingPriceDTO::getGoodsInfoNo, m -> m));

            List<String> skuNos = goodsInfoMarketingPriceList.stream().map(GoodsInfoMarketingPriceDTO::getGoodsInfoNo)
                    .collect(Collectors.toList());

            //获取待审核记录
            List<String> goodsIds = goodsInfoMarketingPriceList.stream().map(GoodsInfoMarketingPriceDTO::getGoodsId)
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
                    ExcelHelper.setError(workbook, entry.getValue(), "该商品不存在！",style);
                    isError = true;
                } else {
                    if (CommissionSynPriceType.AI_SYN.toValue() == commissionConfigVO.getSynPriceType().toValue()
                            && StringUtils.isNotBlank(priceDTO.getProviderGoodsInfoId())){
                        ExcelHelper.setError(workbook, entry.getValue(), "该商品不支持修改市场价！",style);
                        isError = true;
                    }

                    if (GoodsType.REAL_GOODS.toValue() != priceDTO.getGoodsType()) {
                        ExcelHelper.setError(workbook, entry.getValue(), "该商品不支持调整阶梯价！",style);
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
                    if (!Objects.equals(GoodsPriceType.STOCK.toValue(),goodsVO.getPriceType())){
                        ExcelHelper.setError(style,workbook, entry.getValue(), "该商品设价方式不为按阶梯价设价！");
                        isError = true;
                    }
                    if (Objects.equals(goodsVO.getAllowPriceSet(),Constants.no)){
                        ExcelHelper.setError(style,workbook, entry.getValue(), "商品未开启spu独立设价！");
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


    private final Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> importPriceAdjust = (workbook) -> {
        List<PriceAdjustmentRecordDetailDTO> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        int maxCell = 15;
        Map<String, Cell> goodsInfoKeyMap = new HashMap<>();
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
            //基础信息
            PriceAdjustmentRecordDetailDTO detail = new PriceAdjustmentRecordDetailDTO();
            detail.setGoodsInfoNo(ExcelHelper.getValue(getCell(cells, 0)).trim());
            detail.setGoodsInfoName(ExcelHelper.getValue(getCell(cells, 1)).trim());
            detail.setGoodsSpecText(ExcelHelper.getValue(getCell(cells, 2)));
            String marketingPriceVal = ExcelHelper.getValue(getCell(cells, 3)).trim();
            String isAloneFlagVal = ExcelHelper.getValue(getCell(cells, 4));

            //记录skuNo列，用于检查数据是否存在和排重
            goodsInfoKeyMap.put(detail.getGoodsInfoNo(), getCell(cells, 0));

            //销售类型默认均为批发
            detail.setSaleType(SaleType.WHOLESALE);
            //调整后的市场价
            if(StringUtils.isNotBlank(marketingPriceVal)){
                detail.setAdjustedMarketPrice(new BigDecimal(marketingPriceVal));
            }
            //是否独立设价
            detail.setAloneFlag("是".equals(isAloneFlagVal));
            List<GoodsIntervalPriceDTO> intervalPriceList = new ArrayList<>(5);
            //校验区间信息
            List<Long> countList = new ArrayList<>();
            //记录下最后一个设置了区间信息的index，用于校验跨区间
            int index = 5;
            for (int i = 5; i < maxCell; i += 2) {
                //记录上一个已设置区间信息的索引
                int y = index;
                //默认当前区间设置了区间信息
                index = i;
                String count = ExcelHelper.getValue(cells[i]).trim();
                String price = ExcelHelper.getValue(cells[i + 1]).trim();
                count = StringUtils.isBlank(count) && i == 5 ? "1" : count;
                String countTemp = null;
                if (StringUtils.isBlank(count)) {
                    if (StringUtils.isBlank(price)) {
                        //当前未设置任何区间信息，重置为上一个已设置区间信息的index
                        index = y;
                    }
                } else {
                    countTemp = new BigDecimal(count).longValue() + "";
                    countList.add(Long.valueOf(countTemp));
                }
                GoodsIntervalPriceDTO intervalPrice = new GoodsIntervalPriceDTO();
                intervalPrice.setPrice(StringUtils.isNotBlank(price) ? new BigDecimal(price) : null);
                intervalPrice.setCount(StringUtils.isNotBlank(countTemp) ? Long.valueOf(countTemp) : null);
                intervalPrice.setType(PriceType.SKU);
                intervalPriceList.add(intervalPrice);
            }
            detail.setIntervalPrice(JSONObject.toJSONString(intervalPriceList));
            dataList.add(detail);
            log.info("countList size {}", countList.size());
        }
        return dataList;
    };

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }
}
