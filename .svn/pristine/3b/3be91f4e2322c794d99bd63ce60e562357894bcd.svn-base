package com.wanmi.sbc.pointsgoods.service;

import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreListByStoreIdsRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoodscate.PointsGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsAddListRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsAddRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsListRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateListRequest;
import com.wanmi.sbc.goods.bean.dto.PointsGoodsDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.pointsgoods.request.PointsGoodsImportExcelRequest;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 积分商品Excel处理服务
 *
 * @author yang
 * @since 2019/5/21
 */
@Slf4j
@Service
public class PointsGoodsImportExcelService {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private PointsGoodsCateQueryProvider pointsGoodsCateQueryProvider;

    @Autowired
    private PointsGoodsSaveProvider pointsGoodsSaveProvider;

    @Autowired
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 导入模板
     *
     * @author yang
     * @since 2019/5/21
     */
    @Transactional
    public List<String> implGoods(PointsGoodsImportExcelRequest pointsGoodsRequest) {
        List<String> skuIds = new ArrayList<>();
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.POINTS_GOODS_EXCEL_DIR.concat(pointsGoodsRequest.getUserId()))
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            //创建Workbook工作薄对象，表示整个excel
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            //检测文档正确性
            this.checkExcel(workbook);

            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            int maxCell = 8;

            Map<String, PointsGoodsDTO> pointsGoodsMap = new HashMap<>();
            List<String> goodsInfoNos = new ArrayList<>();

            //循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
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
                //数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                PointsGoodsDTO pointsGoods = new PointsGoodsDTO();
                String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isNotBlank(goodsInfoNo)) {
                    // 验证数据是否重复
                    goodsInfoNos.add(goodsInfoNo);
                }
                // 积分商品分类
                pointsGoods.setCateId(NumberUtils.toInt(ExcelHelper.getValue(getCell(cells, 1)).split("_")[0]));
                // 结算价
                String settlementPrice = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isNotBlank(settlementPrice)) {
                    if(ValidateUtil.isNumFlt(settlementPrice)) {
                        pointsGoods.setSettlementPrice(StringUtils.isBlank(settlementPrice)
                                ? BigDecimal.ZERO : new BigDecimal(settlementPrice));
                    }
                }

                // 兑换数量
                String num = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(num)) {
                    if(ValidateUtil.isNum(num)) {
                        Long convertStock = NumberUtils.toLong(num);
                        pointsGoods.setStock(convertStock);
                    }
                }
                // 兑换积分
                String points = ExcelHelper.getValue(getCell(cells, 4));
                if(StringUtils.isNotBlank(points)) {
                    if(ValidateUtil.isNum(points)) {
                        Long convertPoints = NumberUtils.toLong(points);
                        pointsGoods.setPoints(convertPoints);
                    }
                }

                // 是否推荐
                String recommendFlag = ExcelHelper.getValue(getCell(cells, 5));
                if (StringUtils.isNotBlank(recommendFlag)) {
                    if (StringUtils.equals(recommendFlag, "是")) {
                        pointsGoods.setRecommendFlag(BoolFlag.YES);
                    } else if (StringUtils.equals(recommendFlag, "否")) {
                        pointsGoods.setRecommendFlag(BoolFlag.NO);
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                // 兑换开始时间
                Cell cell6 = getCell(cells, 6);
                String beginDate = ExcelHelper.getValue(cell6);
                if (StringUtils.isNotBlank(beginDate)) {
                    if (getCell(cells, 6).getCellType().equals(CellType.NUMERIC)) {
                        // 判断Excel导入是否是日期格式
                        if (DateUtil.isCellDateFormatted(cell6)) {
                            Date javaDate = DateUtil.getJavaDate(Double.parseDouble(beginDate));
                            beginDate = sdf.format(javaDate);
                            // 将日期格式转为字符串
                            cell6.setCellType(CellType.STRING);
                            cell6.setCellValue(beginDate);
                        }
                    }
                    LocalDateTime beginTime = LocalDateTime.parse(beginDate, timeFormatter);
                    pointsGoods.setBeginTime(beginTime);
                }

                // 兑换结束时间
                Cell cell7 = getCell(cells, 7);
                String endDate = ExcelHelper.getValue(cell7);
                if (StringUtils.isNotBlank(endDate)) {
                    if (getCell(cells, 7).getCellType().equals(CellType.NUMERIC)) {
                        // 判断Excel导入是否是日期格式
                        if (DateUtil.isCellDateFormatted(getCell(cells, 7))) {
                            Date javaDate = DateUtil.getJavaDate(Double.parseDouble(endDate));
                            endDate = sdf.format(javaDate);
                            // 将日期格式转为字符串
                            cell7.setCellType(CellType.STRING);
                            cell7.setCellValue(endDate);
                        }
                    }
                    LocalDateTime endTime = LocalDateTime.parse(endDate, timeFormatter);
                    pointsGoods.setEndTime(endTime);
                }
                pointsGoodsMap.put(goodsInfoNo, pointsGoods);
            }

            Map<String, GoodsInfoVO> infoVOMap = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                    .goodsInfoNos(goodsInfoNos)
                    .delFlag(DeleteFlag.NO.toValue())
                    .build()).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));

            List<PointsGoodsAddRequest> pointsGoodsAddRequests = pointsGoodsMap.entrySet().stream().map(entry -> {
                GoodsInfoVO goodsInfoVO = infoVOMap.get(entry.getKey());
                PointsGoodsDTO pointsGoodsDTO = entry.getValue();
                return PointsGoodsAddRequest.builder()
                        .goodsId(goodsInfoVO.getGoodsId())
                        .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                        .cateId(pointsGoodsDTO.getCateId().equals(Constants.ZERO)?null:pointsGoodsDTO.getCateId())
                        .stock(pointsGoodsDTO.getStock())
                        .settlementPrice(pointsGoodsDTO.getSettlementPrice())
                        .points(pointsGoodsDTO.getPoints())
                        .recommendFlag(pointsGoodsDTO.getRecommendFlag())
                        .beginTime(pointsGoodsDTO.getBeginTime())
                        .endTime(pointsGoodsDTO.getEndTime())
                        .createPerson(pointsGoodsRequest.getUserId())
                        .createTime(LocalDateTime.now())
                        .delFlag(DeleteFlag.NO)
                        .status(EnableStatus.ENABLE)
                        .sales(NumberUtils.LONG_ZERO)
                        .build();
            }).collect(Collectors.toList());

            pointsGoodsSaveProvider.batchAdd(PointsGoodsAddListRequest.builder().pointsGoodsAddRequestList(pointsGoodsAddRequests).build());

            skuIds.addAll(pointsGoodsAddRequests.stream().map(PointsGoodsAddRequest::getGoodsInfoId).collect(Collectors.toList()));
        } catch (SbcRuntimeException e) {
            log.error("商品导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return skuIds;
    }


    /**
     * 下载Excel错误文档
     *
     * @param userId 用户Id
     * @param ext    文件扩展名
     */
    public void downErrExcel(String userId, String ext) {
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.POINTS_GOODS_ERR_EXCEL_DIR.concat(userId))
                .build()).getContext();
        if (yunGetResourceResponse == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        byte[] content = yunGetResourceResponse.getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try (
                InputStream is = new ByteArrayInputStream(content);
                ServletOutputStream os = HttpUtil.getResponse().getOutputStream()
        ) {
            //下载错误文档时强制清除页面文档缓存
            HttpServletResponse response = HttpUtil.getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("expries", -1);
            String fileName = URLEncoder.encode("错误表格.".concat(ext), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition",
                    String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));

            byte b[] = new byte[1024];
            //读取文件，存入字节数组b，返回读取到的字符数，存入read,默认每次将b数组装满
            int read = is.read(b);
            while (read != -1) {
                os.write(b, 0, read);
                read = is.read(b);
            }
            HttpUtil.getResponse().flushBuffer();
        } catch (Exception e) {
            log.error("下载EXCEL文件异常->", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 上传文件
     *
     * @param file   文件
     * @param userId 操作员id
     * @return 文件格式
     */
    public String upload(MultipartFile file, String userId) {
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        if (file.getSize() > Constants.IMPORT_GOODS_MAX_SIZE * 1024 * 1024) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030064, new Object[]{Constants.IMPORT_GOODS_MAX_SIZE});
        }

        String resourceKey = Constants.POINTS_GOODS_EXCEL_DIR.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            //创建Workbook工作薄对象，表示整个excel
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            //检测文档正确性
            this.checkExcel(workbook);

            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            if(lastRowNum > Constants.NUM_10000){
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_10000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过10000条，请修改");
                }
            }

            int maxCell = 8;

            boolean isError = false;
            List<String> goodsInfoNos = new ArrayList<>();
            CellStyle style = workbook.createCellStyle();

            Map<Integer, Cell[]> cellMap = new HashMap<>();

            //循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
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
                //数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isNotBlank(goodsInfoNo)) {
                    goodsInfoNos.add(goodsInfoNo);
                }
                cellMap.put(rowNum, cells);
            }

            //商品信息
            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                    .goodsInfoNos(goodsInfoNos)
                    .delFlag(DeleteFlag.NO.toValue())
                    .build()).getContext().getGoodsInfos();
            Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));
            //店铺信息
            List<Long> storeIds = goodsInfos.stream().map(GoodsInfoVO::getStoreId).distinct().collect(Collectors.toList());
            StoreListByStoreIdsRequest storeIdsRequest = new StoreListByStoreIdsRequest();
            storeIdsRequest.setStoreIds(storeIds);
            List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
            Map<Long, StoreVO> storeVOMap = storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            //员工信息
            List<Long> companyIds = storeVOList.stream().map(v -> v.getCompanyInfo().getCompanyInfoId()).distinct().collect(Collectors.toList());
            EmployeeListRequest employeeListRequest = new EmployeeListRequest();
            employeeListRequest.setCompanyInfoIds(companyIds);
            Map<Long, List<EmployeeListVO>> employeeMap = employeeQueryProvider.list(employeeListRequest)
                    .getContext().getEmployeeList().stream().collect(Collectors.groupingBy(EmployeeListVO::getCompanyInfoId));
            // 积分商品分类
            Map<Integer, Boolean> cateMap = pointsGoodsCateQueryProvider.list(PointsGoodsCateListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .build()).getContext().getPointsGoodsCateVOList().stream()
                    .collect(Collectors.toMap(PointsGoodsCateVO::getCateId, c -> Boolean.TRUE));

            List<String> goodsInfoIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            Map<String, List<PointsGoodsVO>> pointsGoodsMap = pointsGoodsQueryProvider.listForImport(PointsGoodsListRequest.builder()
                    .goodsInfoIds(goodsInfoIds)
                    .status(EnableStatus.ENABLE)
                    .delFlag(DeleteFlag.NO)
                    .build()).getContext().getPointsGoodsVOList().stream().collect(Collectors.groupingBy(PointsGoodsVO::getGoodsInfoId));

            goodsInfoNos.clear();

            for (Map.Entry<Integer, Cell[]> entry : cellMap.entrySet()) {
                Cell[] cells = entry.getValue();
                PointsGoodsDTO pointsGoods = new PointsGoodsDTO();
                GoodsInfoVO goodsInfoVO = null;
                String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isBlank(goodsInfoNo)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 0), "此项必填");
                    isError = true;
                } else {
                    // 验证数据是否重复
                    List<String> collect = goodsInfoNos.stream()
                            .filter(goodsInfoNo::equals)
                            .collect(Collectors.toList());
                    if (Objects.nonNull(collect) && collect.size() > 0) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 0), "数据重复导入");
                        isError = true;
                    }
                    goodsInfoNos.add(goodsInfoNo);
                    goodsInfoVO = goodsInfoVOMap.get(goodsInfoNo);
                    if (Objects.isNull(goodsInfoVO)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 0), "SKU编码错误，无对应商品");
                        isError = true;
                    } else {
                        pointsGoods.setGoodsId(goodsInfoVO.getGoodsId());
                        pointsGoods.setGoodsInfoId(goodsInfoVO.getGoodsInfoId());
                        // 判断店铺是否关店
                        StoreVO storeVO = storeVOMap.get(goodsInfoVO.getStoreId());
                        if (storeVO.getStoreState().equals(StoreState.CLOSED) ||
                                storeVO.getDelFlag().equals(DeleteFlag.YES)) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 0), "该货品店铺已关店");
                            isError = true;
                        } else {
                            // 判断店铺是否已禁用
                            List<EmployeeListVO> employeeList = employeeMap.get(storeVO.getCompanyInfo().getCompanyInfoId());
                            if (Objects.isNull(employeeList)) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 0), "店铺已禁用");
                                isError = true;
                            } else {
                                List<EmployeeListVO> listVOS = employeeList.stream()
                                        .filter(employeeListVO -> employeeListVO.getDelFlag().equals(DeleteFlag.NO))
                                        .filter(employeeListVO -> employeeListVO.getAccountState().equals(AccountState.ENABLE))
                                        .collect(Collectors.toList());
                                if (listVOS.size() == 0) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 0), "店铺已禁用");
                                    isError = true;
                                }
                            }
                        }
                        // 判断是否为跨境商品，不能将跨境商品添加为积分商品
                        if (StoreType.CROSS_BORDER == goodsInfoVO.getStoreType()) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 0), "不能将跨境商品添加为积分商品");
                            isError = true;
                        }
                    }
                }
                // 积分商品分类
                String cateId = ExcelHelper.getValue(getCell(cells, 1)).split("_")[0];
                if (StringUtils.isNotBlank(cateId)){
                    pointsGoods.setCateId(NumberUtils.toInt(cateId));
                    if (!cateMap.containsKey(pointsGoods.getCateId())) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 1), "分类不存在");
                        isError = true;
                    }
                }

                // 结算价
                String settlementPrice = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isNotBlank(settlementPrice)) {
                    if(!ValidateUtil.isNumFlt(settlementPrice)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 2), "仅允许数字");
                        isError = true;
                    } else {
                        pointsGoods.setSettlementPrice(StringUtils.isBlank(settlementPrice)
                                ? BigDecimal.ZERO : new BigDecimal(settlementPrice));
                        if (pointsGoods.getSettlementPrice().compareTo(BigDecimal.ZERO) < 0
                                || pointsGoods.getSettlementPrice().compareTo(new BigDecimal("9999999.99")) > 0) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 2), "必须在0-9999999.99范围内");
                            isError = true;
                        }
                    }
                } else {
                    ExcelHelper.setError(style,workbook, getCell(cells, 2), "此项必填");
                    isError = true;
                }

                // 兑换数量
                String num = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(num)) {
                    if(!ValidateUtil.isNum(num)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 3), "仅允许整数");
                        isError = true;
                    } else {
                        Long convertStock = NumberUtils.toLong(num);
                        pointsGoods.setStock(convertStock);
                        if (convertStock != 0) {
                            if (convertStock > 99999999) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 3), "必须在0-99999999整数范围内");
                                isError = true;
                            }
                            if (Objects.nonNull(goodsInfoVO) && convertStock > goodsInfoVO.getStock()) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 3), "数量不能大于现有商品库存，现有库存为" + goodsInfoVO.getStock());
                                isError = true;
                            }
                            if (Objects.isNull(goodsInfoVO)) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 3), "货品不存在");
                                isError = true;
                            }
                        } else {
                            ExcelHelper.setError(style,workbook, getCell(cells, 3), "必须在0-99999999整数范围内");
                            isError = true;
                        }
                    }
                }

                // 兑换积分
                String points = ExcelHelper.getValue(getCell(cells, 4));
                if(StringUtils.isNotBlank(points)) {
                    if(!ValidateUtil.isNum(points)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 4), "仅允许整数");
                        isError = true;
                    } else {
                        Long convertPoints = NumberUtils.toLong(points);
                        pointsGoods.setPoints(convertPoints);
                        if (StringUtils.isNotBlank(ExcelHelper.getValue(getCell(cells, 4)))) {
                            if (convertPoints > 99999999) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 4), "必须在0-99999999整数范围内,可为0");
                                isError = true;
                            }
                        } else {
                            ExcelHelper.setError(style,workbook, getCell(cells, 4), "此项必填");
                            isError = true;
                        }
                    }
                }

                // 是否推荐
                String recommendFlag = ExcelHelper.getValue(getCell(cells, 5));
                if (StringUtils.isBlank(recommendFlag)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 5), "此项必填");
                    isError = true;
                } else {
                    if (StringUtils.equals(recommendFlag, "是")) {
                        pointsGoods.setRecommendFlag(BoolFlag.YES);
                    } else if (StringUtils.equals(recommendFlag, "否")) {
                        pointsGoods.setRecommendFlag(BoolFlag.NO);
                    } else {
                        ExcelHelper.setError(style,workbook, getCell(cells, 5), "请填是或者否");
                        isError = true;
                    }
                }

                // 该商品已绑定的积分商品
                List<PointsGoodsVO> pointsGoodsVOList = null;
                if (StringUtils.isNotBlank(pointsGoods.getGoodsInfoId())) {
                    pointsGoodsVOList = pointsGoodsMap.get(pointsGoods.getGoodsInfoId());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                // 兑换开始时间
                Cell cell6 = getCell(cells, 6);
                String beginDate = ExcelHelper.getValue(cell6);
                if (StringUtils.isBlank(beginDate)) {
                    ExcelHelper.setError(style,workbook, cell6, "此项必填");
                    isError = true;
                } else {
                    try {
                        if (getCell(cells, 6).getCellType().equals(CellType.NUMERIC)) {
                            // 判断Excel导入是否是日期格式
                            if (DateUtil.isCellDateFormatted(cell6)) {
                                Date javaDate = DateUtil.getJavaDate(Double.parseDouble(beginDate));
                                beginDate = sdf.format(javaDate);
                                // 将日
                                // 期格式转为字符串
                                cell6.setCellType(CellType.STRING);
                                cell6.setCellValue(beginDate);
                            }
                        }
                        LocalDateTime beginTime = LocalDateTime.parse(beginDate, timeFormatter);
                        pointsGoods.setBeginTime(beginTime);
                        boolean before = beginTime.isBefore(LocalDateTime.now());
                        if (before) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 6), "兑换开始时间必须在当前时间之后");
                            isError = true;
                        }
                        // 已有该商品绑定了积分兑换
                        if (Objects.nonNull(pointsGoodsVOList)) {
                            for (PointsGoodsVO pointsGoodsVO : pointsGoodsVOList) {
                                // 开始时间等于已绑定积分商品的开始时间
                                if (pointsGoodsVO.getBeginTime().isEqual(beginTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 6),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                                // 开始时间等于已绑定积分商品的结束时间
                                if (pointsGoodsVO.getEndTime().isEqual(beginTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 6),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                                // 开始时间在已绑定积分商品的时间段内
                                if (pointsGoodsVO.getBeginTime().isBefore(beginTime)
                                        && pointsGoodsVO.getEndTime().isAfter(beginTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 6),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 6), "日期格式错误，请按'2018/12/12 12:12'格式输入");
                        isError = true;
                    }
                }

                // 兑换结束时间
                Cell cell7 = getCell(cells, 7);
                String endDate = ExcelHelper.getValue(cell7);
                if (StringUtils.isBlank(endDate)) {
                    ExcelHelper.setError(style,workbook, getCell(cells, 7), "此项必填");
                    isError = true;
                } else {
                    try {
                        if (getCell(cells, 7).getCellType().equals(CellType.NUMERIC)) {
                            // 判断Excel导入是否是日期格式
                            if (DateUtil.isCellDateFormatted(getCell(cells, 7))) {
                                Date javaDate = DateUtil.getJavaDate(Double.parseDouble(endDate));
                                endDate = sdf.format(javaDate);
                                // 将日期格式转为字符串
                                cell7.setCellType(CellType.STRING);
                                cell7.setCellValue(endDate);
                            }
                        }
                        LocalDateTime endTime = LocalDateTime.parse(endDate, timeFormatter);
                        pointsGoods.setEndTime(endTime);
                        if (Objects.nonNull(pointsGoods.getBeginTime())) {
                            boolean before = endTime.isBefore(pointsGoods.getBeginTime());
                            if (before) {
                                ExcelHelper.setError(style,workbook, getCell(cells, 7), "兑换结束时间必须在兑换开始时间之后");
                                isError = true;
                            }
                        }
                        // 已有该商品绑定了积分兑换
                        if (Objects.nonNull(pointsGoodsVOList)) {
                            for (PointsGoodsVO pointsGoodsVO : pointsGoodsVOList) {
                                // 结束时间等于已绑定积分商品的开始时间
                                if (pointsGoodsVO.getBeginTime().isEqual(endTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 7),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                                // 结束时间等于已绑定积分商品的结束时间
                                if (pointsGoodsVO.getEndTime().isEqual(endTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 7),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                                // 结束时间在已绑定积分商品的时间段内
                                if (pointsGoodsVO.getBeginTime().isBefore(endTime)
                                        && pointsGoodsVO.getEndTime().isAfter(endTime)) {
                                    ExcelHelper.setError(style,workbook, getCell(cells, 7),
                                            timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                    + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                    isError = true;
                                    break;
                                }
                                // 该商品绑定积分商品的时间段在该商品时间段内
                                if (Objects.nonNull(pointsGoods.getBeginTime())) {
                                    if (pointsGoodsVO.getBeginTime().isAfter(pointsGoods.getBeginTime())
                                            && pointsGoodsVO.getEndTime().isBefore(pointsGoods.getEndTime())) {
                                        ExcelHelper.setError(style,workbook, getCell(cells, 7),
                                                timeFormatter.format(pointsGoodsVO.getBeginTime()) + "-"
                                                        + timeFormatter.format(pointsGoodsVO.getEndTime()) + " 该时间段已存在活动");
                                        isError = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        ExcelHelper.setError(style,workbook, cell7, "日期格式错误，请按'2018/12/12 12:12'格式输入");
                        isError = true;
                    }
                }
            }

            if (isError) {
                errorExcel(userId.concat(".").concat(fileExt), workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
            }
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(file.getBytes())
                    .resourceName(file.getOriginalFilename())
                    .resourceKey(resourceKey)
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();

        } catch (SbcRuntimeException e) {
            log.error("商品上传异常", e);
            throw e;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } catch (Exception e) {
            log.error("商品上传异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

        return fileExt;
    }

    /**
     * EXCEL错误文件-本地生成
     *
     * @param newFileName 新文件名
     * @param wk          Excel对象
     * @return 新文件名
     * @throws SbcRuntimeException
     */
    public String errorExcel(String newFileName, Workbook wk) throws SbcRuntimeException {
        String userId = commonUtil.getOperator().getUserId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(Constants.POINTS_GOODS_ERR_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }

    /**
     * 验证EXCEL
     *
     * @param workbook
     */
    public void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            Sheet sheet2 = workbook.getSheetAt(1);
            if (!(row.getCell(0).getStringCellValue().contains("SKU编码") && sheet2.getSheetName().contains("数据"))) {
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
