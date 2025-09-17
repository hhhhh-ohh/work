package com.wanmi.sbc.goods.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.WebUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.ExcelLoopRowResult;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.common.GoodsCommonProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateLeafByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsDefaultByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckBindRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsSpecDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsSpecDetailDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.request.GoodsSupplierExcelImportRequest;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.sensitivewords.service.SensitiveWordService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品EXCEL处理服务
 * Created by dyt on 2017/8/17.
 */
@Slf4j
@Service
public class GoodsExcelService {

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsCommonProvider goodsCommonProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    /**
     * 下载Excel错误文档
     * @param userId 用户Id
     * @param ext 文件扩展名
     */
    public void downErrExcel(String userId, String ext){
        //商品导入错误文件resourceKey
        String resourceKey = Constants.GOODS_ERR_EXCEL_DIR.concat(userId);
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
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
        ){
            //下载错误文档时强制清除页面文档缓存
            HttpServletResponse response = HttpUtil.getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("expries",-1);
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
     * @param file 文件
     * @param goodsRequest 操作员id
     * @return 文件格式
     */
    public String upload(MultipartFile file, GoodsSupplierExcelImportRequest goodsRequest){
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        Integer goodsType = goodsRequest.getGoodsType();
        StoreType storeType = goodsRequest.getType();
        // 运费模板未配置,如果是平台/BOSS，不校验
        Platform platform = commonUtil.getOperator().getPlatform();
        FreightTemplateGoodsVO freightTemp = null;
        if (!Nutils.equals(Platform.PLATFORM, platform) && !Nutils.equals(Platform.BOSS, platform)) {
            // 如果是实体商品,则校验运费模版
            if(NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                freightTemp = freightTemplateGoodsQueryProvider
                        .getDefaultByStoreId(FreightTemplateGoodsDefaultByStoreIdRequest.builder()
                                .storeId(goodsRequest.getStoreId())
                                .build())
                        .getContext();
                Assert.assertNotNull(freightTemp, GoodsErrorCodeEnum.K030147);
            }
        }
        String resourceKey = Constants.GOODS_EXCEL_DIR.concat(goodsRequest.getUserId());
        //创建一个HashSet，防止卡券商品多次绑定同一个卡券
        Set<Long> electronicCouponSet = Sets.newHashSet();
        //创建Workbook工作薄对象，表示整个excel
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            Assert.assertNotNull(sheet, GoodsErrorCodeEnum.K030066);
            // 检测文档正确性
            this.checkExcel(workbook);
            this.checkExcelByGoods(workbook, goodsType == null? GoodsType.REAL_GOODS: GoodsType.fromValue(goodsType),storeType);

            // 获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            if(lastRowNum > Constants.NUM_5000){
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_5000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"文件数据超过5000条，请修改");
                }

            }
            StoreCateListByStoreIdRequest storeCateQueryRequest = new StoreCateListByStoreIdRequest();
            storeCateQueryRequest.setStoreId(goodsRequest.getStoreId());
            List<StoreCateResponseVO> storeCateList = storeCateQueryProvider.listByStoreId(storeCateQueryRequest)
                    .getContext().getStoreCateResponseVOList();
            // 获得默认的商品类型ID
            Long defaultStoreCateId = getDefaultStoreCateId(storeCateList);
            Map<Long, Long> storeCateSet = getStoreCateSet(storeCateList);

            //SkuMap<Spu编号,Spu对象>
            Map<String, BatchGoodsDTO> goodses = new LinkedHashMap<>();
            //SkuMap<Spu编号,Sku对象>
            Map<String, List<BatchGoodsInfoDTO>> skus = new LinkedHashMap<>();

            //SkuMap<Spu编号,图片对象>
            Map<String, List<String>> images = new LinkedHashMap<>();

            //存储编码以及单元格对象，验证重复
            Map<String, List<Cell>> spuNos = new HashMap<>();
            Map<String, List<Cell>> skuNos = new HashMap<>();

            //存储编码以及单元格对象，验证规格和规格值
            Map<String, Cell> spuNoSpecs = new HashMap<>();
            Map<String, Cell> spuNoSpecDetails = new HashMap<>();

            //规格值<Spu编号,规格项集合>
            Map<String, List<BatchGoodsSpecDTO>> allSpecs = new HashMap<>();

            //规格值<规格项模拟编号_规格值名称,规格值模拟编号>，保证在同一个Spu下、同一规格项下，不允许出现重复规格值
            Map<String, Long> specDetailMap = new HashMap<>();
            //规格值<Spu编号,规格值集合>
            Map<String, List<BatchGoodsSpecDetailDTO>> allSpecDetails = new HashMap<>();

            Set<Long> brandSet = getBrandSet(goodsRequest);

            List<GoodsCateVO> goodsCateVOList = getGoodsCateList(goodsRequest);

            Map<Long,GoodsCateVO> goodsCateVOMap = new HashMap<>();
            goodsCateVOList.forEach(goodsCateVO -> {
                goodsCateVOMap.put(goodsCateVO.getCateId(),goodsCateVO);
            });

            Map<Long, String> goodsCateMap = goodsCateVOList.stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCatePath));
//            Map<Long, String> goodsCateMap = getGoodsCateMap(goodsRequest);

            // 所有敏感词
            Set<String> badwords = sensitiveWordService.getAllBadWord();

            //规格列索引
            int[] specColNum = {11, 13, 15, 17, 19};
            int[] specDetailColNum = {12, 14, 16, 18, 20};
            StoreType type = goodsRequest.getType();
            int maxCell = 34;
            boolean isError = false;
            int maxErrorNum = 0;
            CellStyle style = workbook.createCellStyle();
            log.info("商品导入循环开始"+System.currentTimeMillis());
            //循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                if (Constants.IMPORT_GOODS_MAX_ERROR_NUM == maxErrorNum) {
                    break;
                }
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

                BatchGoodsDTO goods = new BatchGoodsDTO();
                if (StoreType.SUPPLIER.equals(type)) {
                    //默认销售类型 零售  产品要求是零售 原来是批发
                    goods.setSaleType(SaleType.RETAIL.toValue());
                    goods.setGoodsSource(StoreType.SUPPLIER.toValue());
                } else {
                    goods.setSaleType(SaleType.RETAIL.toValue());
                    goods.setGoodsSource(StoreType.PROVIDER.toValue());
                }
                //设置商品类型
                goods.setGoodsType(goodsType);
                //允许独立设价
                goods.setAllowPriceSet(1);
                //实体商品设置运费模板，虚拟商品设置-1
                if(NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                    goods.setFreightTempId(Objects.nonNull(freightTemp) ? freightTemp.getFreightTempId() : null);
                }else {
                    goods.setFreightTempId(-1L);
                }
                goods.setGoodsName(ExcelHelper.getValue(getCell(cells, 0)));
                goods.setGoodsNo(ExcelHelper.getValue(getCell(cells, 1)));
                List<GoodsVO> goodsList = null;
                List<GoodsAuditVO> goodsAuditVOList = null;
                if (StringUtils.isNotBlank(goods.getGoodsNo())) {
                    goodsList = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder()
                            .goodsNo(goods.getGoodsNo())
                            .delFlag(DeleteFlag.NO.toValue())
                            .auditStatusList(Lists.newArrayList(CheckStatus.CHECKED,CheckStatus.WAIT_CHECK,CheckStatus.FORBADE,CheckStatus.NOT_PASS))
                            .build()).getContext().getGoodsVOList();
                    goodsAuditVOList = goodsAuditQueryProvider.listByCondition(GoodsAuditQueryRequest.builder()
                            .goodsNo(goods.getGoodsNo())
                            .delFlag(DeleteFlag.NO.toValue())
                            .build()).getContext().getGoodsAuditVOList();
                }

                goods.setMockGoodsId(goods.getGoodsNo());
                goods.setStoreId(goodsRequest.getStoreId());
                goods.setCompanyInfoId(goodsRequest.getCompanyInfoId());
                goods.setSupplierName(goodsRequest.getSupplierName());
                goods.setCompanyType(goodsRequest.getCompanyType());
                goods.setAddedTimingFlag(Boolean.FALSE);
                if (StringUtils.isBlank(goods.getGoodsNo())) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "此项必填");
                    isError = true;
                    maxErrorNum++;
                    goods.setGoodsNo("DEMO".concat(String.valueOf(rowNum)));
                } else if (!ValidateUtil.isBetweenLen(goods.getGoodsNo(), 1, 20)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "长度必须1-20个字");
                    isError = true;
                    maxErrorNum++;
                } else if (!ValidateUtil.isNotChs(goods.getGoodsNo())) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "仅允许英文、数字、特殊字符");
                    isError = true;
                    maxErrorNum++;
                } else if (CollectionUtils.isNotEmpty(goodsList) || CollectionUtils.isNotEmpty(goodsAuditVOList)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "SPU编码重复");
                    isError = true;
                    maxErrorNum++;
                }

                //是否第一个第一个SPU商品
                boolean isFirstGoods = false;
                if (!goodses.containsKey(goods.getGoodsNo())) {
                    isFirstGoods = true;
                }

                //如果第一个SPU商品处理
                if (isFirstGoods) {
                    Long cateId = NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 2)).split("_")[0]);
                    goods.setCateId(NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 2)).split("_")[0]));
                    if (goods.getCateId() == 0 || (!goodsCateMap.containsKey(goods.getCateId()))) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 2), "请选择平台类目或平台类目不存在");
                        isError = true;
                        maxErrorNum++;
                    }else{
                        goods.setCateTopId(Long.valueOf(goodsCateMap.get(goods.getCateId()).split("\\|")[1]));
                    }
                    GoodsCateVO goodsCateVO = goodsCateVOMap.getOrDefault(cateId, new GoodsCateVO());
                    if(goodsCateVO.getContainsVirtual() == BoolFlag.NO && !NumberUtils.INTEGER_ZERO.equals(goodsType)){
                        ExcelHelper.setError(style, workbook, getCell(cells, 2), "该平台类目不支持创建虚拟商品");
                        isError = true;
                        maxErrorNum++;
                    }

                    Long storeCateId = NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 3)).split("_")[0]);
                    if (storeCateId == 0 || (!storeCateSet.containsKey(storeCateId))) {
                        storeCateId = defaultStoreCateId;
                    }
                    List<Long> storeCateIds = new ArrayList<>();
                    storeCateIds.add(storeCateId);
                    Long parentCateId = storeCateSet.get(storeCateId);
                    //获取店铺分类的父分类
                    if (Objects.nonNull(parentCateId) && parentCateId > 0) {
                        storeCateIds.add(parentCateId);
                    }
                    goods.setStoreCateIds(storeCateIds);

                    goods.setGoodsUnit(ExcelHelper.getValue(getCell(cells, 4)));
                    if (StringUtils.isBlank(goods.getGoodsUnit())) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 4), "此项必填");
                        isError = true;
                        maxErrorNum++;
                    } else if (ValidateUtil.isOverLen(goods.getGoodsUnit(), 10)) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 4), "长度必须1-10个字");
                        isError = true;
                        maxErrorNum++;
                    } else if (!ValidateUtil.isChsEng(goods.getGoodsUnit())) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 4), "仅允许中文、英文");
                        isError = true;
                        maxErrorNum++;
                    }


                    Long brandId = NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 5)).split("_")[0]);
                    if (brandId > 0 && brandSet.contains(brandId)) {
                        goods.setBrandId(brandId);
                    }
                }
                log.info("商品导入图片开始"+System.currentTimeMillis());
                String imageUrlStr = ExcelHelper.getValue(getCell(cells, 6));
                if (StringUtils.isNoneBlank(imageUrlStr)) {
                    String[] imageUrls = imageUrlStr.split("\\|");
                    List<String> imageArr = new ArrayList<>();
                    if (imageUrls.length > 0) {
                        if (!images.containsKey(goods.getGoodsNo())) {
                            if (imageUrls.length > 10) {
                                ExcelHelper.setError(style, workbook, getCell(cells, 6), "最多传10张图片");
                                isError = true;
                                maxErrorNum++;
                            }
                            boolean isFirst = true;
                            for (int i = 0; i < imageUrls.length; i++) {
                                if (imageUrls[i].length() > 255) {
                                    ExcelHelper.setError(style, workbook, getCell(cells, 6), "商品图片链接长度不能超过255");
                                    isError = true;
                                    maxErrorNum++;
                                }
                                if (WebUtil.isImage(imageUrls[i])) {
                                    if (isFirst) {
                                        goods.setGoodsImg(imageUrls[i]);
                                        isFirst = false;
                                    }
                                    imageArr.add(imageUrls[i]);
                                }
                            }
                            images.put(goods.getGoodsNo(), imageArr);
                        }
                    }
                }

                log.info("商品导入视频开始"+System.currentTimeMillis());
                //SPU视频链接地址
                goods.setGoodsVideo(ExcelHelper.getValue(getCell(cells, 7)));
                if (StringUtils.isNotBlank(goods.getGoodsVideo()) && !goods.getGoodsVideo().endsWith("mp4")) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 7), "视频仅支持mp4格式");
                    isError = true;
                    maxErrorNum++;
                }

                if (goods.getGoodsVideo().length() > 255) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 7), "商品视频链接长度不能超过255");
                    isError = true;
                    maxErrorNum++;
                }

                String details = ExcelHelper.getValue(getCell(cells, 8));
                if (StringUtils.isNotBlank(details)) {
                    //敏感词验证
                    String badWord = sensitiveWordService.getBadWordTxt(badwords, details);
                    if (badWord != null) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 8), "商品详情".concat(badWord));
                        isError = true;
                        maxErrorNum++;
                    }

                    goods.setGoodsDetail(Arrays.stream(details.split("\\|")).map(s -> {
                        if (s.startsWith("http")) {
                            return String.format("<img src='%s'/><br/>", s);
                        }
                        return String.format("<p>%s</p>", s);
                    }).collect(Collectors.joining()));
                }

                BatchGoodsInfoDTO goodsInfo = new BatchGoodsInfoDTO();
                goodsInfo.setMockGoodsId(goods.getMockGoodsId());
                goodsInfo.setMockSpecIds(new ArrayList<>());
                goodsInfo.setMockSpecDetailIds(new ArrayList<>());
                goodsInfo.setCateTopId(goods.getCateTopId());
                // SKU 设置商品类型
                goodsInfo.setGoodsType(goodsType);
                if (StoreType.SUPPLIER.equals(type)) {
                    goodsInfo.setGoodsSource(StoreType.SUPPLIER.toValue());
                } else {
                    goodsInfo.setGoodsSource(StoreType.PROVIDER.toValue());
                }
                log.info("商品导入SKU编码开始"+System.currentTimeMillis());
                //SKU编码
                String skuNo = ExcelHelper.getValue(getCell(cells, 9));
                if (StringUtils.isNotBlank(skuNo)) {
                    if (!ValidateUtil.isBetweenLen(skuNo, 1, 20)) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 9), "长度必须1-20个字");
                        isError = true;
                        maxErrorNum++;
                    } else if (!ValidateUtil.isNotChs(skuNo)) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 9), "仅允许英文、数字、特殊字符");
                        isError = true;
                        maxErrorNum++;
                    }
                } else {
                    skuNo = this.getSkuNo(skuNos.keySet());
                    //校验自生成的编码是否在数据库中存在，如果存在则重新生成
                    Boolean isExist = goodsInfoQueryProvider.skuNoExist(skuNo).getContext();
                    while (isExist) {
                        skuNo = this.getSkuNo(skuNos);
                        isExist = goodsInfoQueryProvider.skuNoExist(skuNo).getContext();
                    }
                    getCell(cells, 9).setCellValue(skuNo);
                }

                if (skuNos.containsKey(skuNo)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 9), "文档中出现重复的SKU编码");
                    isError = true;
                    maxErrorNum++;
                }

                goodsInfo.setGoodsInfoNo(skuNo);
                log.info("商品导入SKU图片开始"+System.currentTimeMillis());
                //SKU图片
                String skuImage = ExcelHelper.getValue(getCell(cells, 10));
                if (StringUtils.isNoneBlank(skuImage)) {
                    //如果不是图片链接
                    if (!WebUtil.isImage(skuImage)) {
                        goodsInfo.setGoodsInfoImg(null);
                    } else {
                        goodsInfo.setGoodsInfoImg(skuImage);
                        if (skuImage.length() > 255) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 10), "商品SKU图片链接长度不能超过255");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                } else {
                    goodsInfo.setGoodsInfoImg(null);
                }

                log.info("商品导入规格开始"+System.currentTimeMillis());
                //处理同一SPU下第一条的规格项
                if (isFirstGoods) {
                    for (int i : specColNum) {
                        BatchGoodsSpecDTO spec = new BatchGoodsSpecDTO();
                        spec.setMockGoodsId(goods.getMockGoodsId());
                        spec.setMockSpecId(NumberUtils.toLong(String.valueOf(rowNum).concat(String.valueOf(i))));
                        spec.setSpecName(ExcelHelper.getValue(cells[i]));
                        if (StringUtils.isNotBlank(ExcelHelper.getValue(cells[i + 1]))) {
                            if (StringUtils.isBlank(spec.getSpecName())) {
                                ExcelHelper.setError(style, workbook, cells[i], "此项必填");
                                isError = true;
                                maxErrorNum++;
                            } else if (ValidateUtil.isOverLen(spec.getSpecName(), 10)) {
                                ExcelHelper.setError(style, workbook, cells[i], "长度必须0-10个字");
                                isError = true;
                                maxErrorNum++;
                            } else if (ValidateUtil.containsEmoji(spec.getSpecName())) {
                                ExcelHelper.setError(style, workbook, cells[i], "含有非法字符");
                                isError = true;
                                maxErrorNum++;
                            } else {
                                //敏感词验证
                                String badWord = sensitiveWordService.getBadWordTxt(badwords, spec.getSpecName());
                                if (badWord != null) {
                                    ExcelHelper.setError(style, workbook, cells[i], "规格".concat(badWord));
                                    isError = true;
                                    maxErrorNum++;
                                }
                            }
                        }

                        allSpecs.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(spec)), (s1, s2) -> {
                            s1.addAll(s2);
                            return s1;
                        });
                    }
                    spuNoSpecs.put(goods.getGoodsNo(), cells[specColNum[0]]);
                } else {
                    Arrays.stream(specColNum).forEach(i -> cells[i].setCellValue(""));
                }
                log.info("商品导入处理规格开始"+System.currentTimeMillis());

                //处理规格值
                for (int i = 0; i < specDetailColNum.length; i++) {
                    BatchGoodsSpecDTO spec = allSpecs.get(goods.getGoodsNo()).get(i);
                    BatchGoodsSpecDetailDTO specDetail = new BatchGoodsSpecDetailDTO();
                    specDetail.setDetailName(ExcelHelper.getValue(cells[specDetailColNum[i]]));
                    specDetail.setMockGoodsId(goods.getMockGoodsId());
                    specDetail.setMockSpecId(spec.getMockSpecId());
                    specDetail.setMockSpecDetailId(NumberUtils.toLong(String.valueOf(rowNum).concat(String.valueOf(i))));

                    //设置SKU与规格的扁平数据
                    goodsInfo.getMockSpecIds().add(specDetail.getMockSpecId());

                    spuNoSpecDetails.put(goodsInfo.getGoodsInfoNo(), cells[specDetailColNum[0]]);

                    //不为空的规格项相应的规格值必须填写
                    if (StringUtils.isNotBlank(spec.getSpecName()) && StringUtils.isBlank(specDetail.getDetailName())) {
                        ExcelHelper.setError(style, workbook, cells[specDetailColNum[i]], "此项必填");
                        isError = true;
                        maxErrorNum++;
                    }

                    //明细不为空
                    if (StringUtils.isNotBlank(specDetail.getDetailName())) {
                        if (ValidateUtil.isOverLen(specDetail.getDetailName(), 20)) {
                            ExcelHelper.setError(style, workbook, cells[specDetailColNum[i]], "长度必须0-20个字");
                            isError = true;
                            maxErrorNum++;
                        } else if (ValidateUtil.containsEmoji(specDetail.getDetailName())) {
                            ExcelHelper.setError(style, workbook, cells[specDetailColNum[i]], "含有非法字符");
                            isError = true;
                            maxErrorNum++;
                        } else {
                            //敏感词验证
                            String badWord = sensitiveWordService.getBadWordTxt(badwords, specDetail.getDetailName());
                            if (badWord != null) {
                                ExcelHelper.setError(style, workbook, cells[specDetailColNum[i]], "规格值".concat(badWord));
                                isError = true;
                                maxErrorNum++;
                            }
                        }

                        String key =
                                String.valueOf(specDetail.getMockSpecId()).concat("_").concat(specDetail.getDetailName());
                        //保证在同一个Spu下、同一规格项下，不允许出现重复规格值
                        //存在同一个规格值，取其模拟Id
                        Long specDetailValue = specDetailMap.get(key);
                        if (specDetailValue != null) {
                            specDetail.setMockSpecDetailId(specDetailValue);
                        } else {
                            //不存在，则放明细模拟ID和明细
                            specDetailMap.put(key, specDetail.getMockSpecDetailId());
                            allSpecDetails.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(specDetail)), (s1,
                                                                                                                            s2) -> {
                                s1.addAll(s2);
                                return s1;
                            });
                        }

                        //设置SKU与规格值的扁平数据
                        goodsInfo.getMockSpecDetailIds().add(specDetail.getMockSpecDetailId());

                        if (allSpecDetails.get(goods.getGoodsNo()).stream().filter(goodsSpecDetail -> specDetail.getMockSpecId().equals(goodsSpecDetail.getMockSpecId())).count() > 20) {
                            ExcelHelper.setError(style, workbook, cells[specDetailColNum[i]], "在同一规格项内，不同的规格值不允许超过20个");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(goodsInfo.getMockSpecDetailIds())
                        && skus.getOrDefault(goods.getGoodsNo(), new ArrayList<>()).stream()
                        .map(BatchGoodsInfoDTO::getMockSpecDetailIds)
                        .filter(r -> r.size() == goodsInfo.getMockSpecDetailIds().size()
                                && r.containsAll(goodsInfo.getMockSpecDetailIds())).count() > 0) {
                    Arrays.stream(specDetailColNum).forEach(i -> {
                        ExcelHelper.setError(style, workbook, cells[i], "不允许出现所有规格值完全一致的商品");
                    });
                    isError = true;
                    maxErrorNum++;
                }

                // 从当前开始行号
                int index = 21;
                // 库存导入开始
                ExcelLoopRowResult<Integer> stockResult = importStock(goodsInfo,cells,maxErrorNum,workbook,style);
                if (Objects.nonNull(stockResult)) {
                    isError =  isError || stockResult.isError();
                    maxErrorNum = stockResult.getResult();
                    index++;
                }

                log.info("商品导入条形码开始"+System.currentTimeMillis());
                // 条形码
                goodsInfo.setGoodsInfoBarcode(ExcelHelper.getValue(getCell(cells, index)));
                if (StringUtils.isNotBlank(goodsInfo.getGoodsInfoBarcode()) && ValidateUtil.isOverLen(goodsInfo
                        .getGoodsInfoBarcode(), 20)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, index), "长度必须0-20个字");
                    isError = true;
                    maxErrorNum++;
                }
                index++;

                if (StoreType.SUPPLIER.equals(type)) {
                    //市场价
                    String marketPrice = ExcelHelper.getValue(getCell(cells, index));
                    log.info("商品导入市场价" + marketPrice);
                    if (StringUtils.isBlank(marketPrice)) {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                        isError = true;
                        maxErrorNum++;
                    } else if (!marketPrice.matches("\\d+\\.\\d{1,2}") && !marketPrice.matches("\\d+")) {
                        ExcelHelper.setError(style, workbook, cells[index], "必须为大于0并保留两位小数");
                        isError = true;
                        maxErrorNum++;
                    } else {
                        goodsInfo.setMarketPrice(new BigDecimal(marketPrice));
                        if (goodsInfo.getMarketPrice().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getMarketPrice()
                                .compareTo(new BigDecimal("9999999.99")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0-9999999.99范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                    index++;

                    //供货价
                    String supplyPrice = ExcelHelper.getValue(getCell(cells, index));
                    log.info("商品导入供货价:" + supplyPrice);
                    if (StringUtils.isEmpty(supplyPrice)){
                        goodsInfo.setSupplyPrice(null);
                    }else if (!supplyPrice.matches("\\d+\\.\\d{1,2}") && !supplyPrice.matches("\\d+")) {
                        ExcelHelper.setError(style, workbook, cells[index], "必须为大于0并保留两位小数");
                        isError = true;
                        maxErrorNum++;
                    } else {
                        goodsInfo.setSupplyPrice(new BigDecimal(supplyPrice));
                        if (goodsInfo.getSupplyPrice().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getSupplyPrice()
                                .compareTo(new BigDecimal("9999999.99")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0-9999999.99范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    }

                    index++;

                    //划线价
                    String linePrice = ExcelHelper.getValue(getCell(cells,index));
                    log.info("商品导入划线价:" + linePrice);
                    if  (StringUtils.isBlank(linePrice)) {
                        goodsInfo.setLinePrice(null);
                    }else if (!linePrice.matches("\\d+\\.\\d{1,2}") && !linePrice.matches("\\d+")){
                        ExcelHelper.setError(style, workbook, cells[index], "必须为大于0并保留两位小数");
                        isError = true;
                        maxErrorNum++;
                    } else if (new BigDecimal(linePrice).compareTo(goodsInfo.getMarketPrice()) < 0) {
                        ExcelHelper.setError(style, workbook, cells[index],"划线价不应小于市场价");
                        isError = true;
                        maxErrorNum++;
                    }else {
                        goodsInfo.setLinePrice(new BigDecimal(linePrice));
                        if (goodsInfo.getLinePrice().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getLinePrice()
                                .compareTo(new BigDecimal("9999999.99")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0-9999999.99范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                    index++;



                    //上下架
                    String addedFlagStr = ExcelHelper.getValue(getCell(cells, index));
                    log.info("商品导入上下架:" + addedFlagStr);
                    if ("上架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                    } else if ("下架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                    } else {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在[上架、下架]范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                    index++;

                    //如果是实体商品，则设置重量体积，否则默认为0
                    if (NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                        //重量
                        String weightStr = ExcelHelper.getValue(getCell(cells, index));
                        if (StringUtils.isNotBlank(weightStr)) {
                            if (NumberUtils.isCreatable(weightStr)) {
                                goodsInfo.setGoodsWeight(new BigDecimal(weightStr).setScale(3, RoundingMode.HALF_UP));
                                if (goodsInfo.getGoodsWeight().compareTo(new BigDecimal("0.001")) < 0 || goodsInfo.getGoodsWeight()
                                        .compareTo(new BigDecimal("9999.999")) > 0) {
                                    ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0.001-9999.999范围内");
                                    isError = true;
                                    maxErrorNum++;
                                }
                            } else {
                                ExcelHelper.setError(style, workbook, getCell(cells, index), "请填入正确的重量（数字）");
                                isError = true;
                                maxErrorNum++;
                            }
                        } else {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                            isError = true;
                            maxErrorNum++;
                        }
                        index++;
                        //体积
                        String cubageStr = ExcelHelper.getValue(cells, index);
                        if (StringUtils.isNotBlank(cubageStr)) {
                            if (NumberUtils.isCreatable(cubageStr)) {
                                goodsInfo.setGoodsCubage(new BigDecimal(cubageStr).setScale(6, RoundingMode.HALF_UP));
                                if (goodsInfo.getGoodsCubage().compareTo(new BigDecimal("0.000001")) < 0 || goodsInfo.getGoodsCubage()
                                        .compareTo(new BigDecimal("999.999999")) > 0) {
                                    ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0.000001-999.999999范围内");
                                    isError = true;
                                    maxErrorNum++;
                                }
                            } else {
                                ExcelHelper.setError(style, workbook, getCell(cells, index), "请填入正确的体积（数字）");
                                isError = true;
                                maxErrorNum++;
                            }
                        } else {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                            isError = true;
                            maxErrorNum++;
                        }
                        index++;
                    } else {
                        goodsInfo.setGoodsWeight(BigDecimal.ZERO);
                        goodsInfo.setGoodsCubage(BigDecimal.ZERO);
                    }


                    if (isFirstGoods) {
                        //商品名称
                        if (StringUtils.isBlank(goods.getGoodsName())) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "此项必填");
                            isError = true;
                            maxErrorNum++;
                        } else if (!ValidateUtil.isBetweenLen(goods.getGoodsName(), 1, 40)) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "长度必须1-40个字");
                            isError = true;
                            maxErrorNum++;
                        } else if (ValidateUtil.containsEmoji(goods.getGoodsName())) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "含有非法字符");
                            isError = true;
                            maxErrorNum++;
                        } else {
                            //敏感词验证
                            String badWord = sensitiveWordService.getBadWordTxt(badwords, goods.getGoodsName());
                            if (badWord != null) {
                                ExcelHelper.setError(style, workbook, getCell(cells, 0), "商品名称".concat(badWord));
                                isError = true;
                                maxErrorNum++;
                            }
                        }

                        //下单方式，实体商品默认全部
                        //如果不是实体商品，则只能立即购买
                        if(!NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                            goods.setGoodsBuyTypes(String.valueOf(Constants.ONE));
                        } else {
                            goods.setGoodsBuyTypes("0,1");
                        }

                        goodses.put(goods.getGoodsNo(), goods);
                    } else {
                        if (skus.getOrDefault(goods.getGoodsNo(), new ArrayList<>()).size() >= 200) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 1), "同一SPU的商品不允许超过200条");
                            isError = true;
                            maxErrorNum++;
                        }
                    }

                    log.info("index:" + index);
                    //积分价格
                    String buyPointStr = ExcelHelper.getValue(getCell(cells, goodsType == 0 ? 29 : 27));
                    log.info("商品导入积分:" + buyPointStr);
                    if (StringUtils.isNotBlank(buyPointStr)) {
                        goodsInfo.setBuyPoint(new BigDecimal(buyPointStr).longValue());
                    }else{
                        goodsInfo.setBuyPoint(0L);
                    }
                    if(goodsType == Constants.TWO) {
                        String electronicCouponsStr = ExcelHelper.getValue(getCell(cells, 28));
                        //如果没传电子卡券id，则参数错误
                        if (StringUtils.isEmpty(electronicCouponsStr)) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 28), "此项必填");
                            isError = true;
                            maxErrorNum++;
                        }
                        long id = NumberUtils.toLong(electronicCouponsStr.split("_")[0]);

                        int size = electronicCouponSet.size();

                        electronicCouponSet.add(id);

                        //如果没传电子卡券id查不到改电子卡券，则参数错误
                        ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder()
                                .id(id)
                                .build()).getContext().getElectronicCouponVO();
                        if (Objects.isNull(electronicCouponVO)) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 28), "电子卡券不存在");
                            isError = true;
                            maxErrorNum++;
                        }
                        //如果set的size没有变大，代表有重复的id
                        if (electronicCouponSet.size() == size) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 28), "该电子卡券已经被其他商品绑定！");
                            isError = true;
                            maxErrorNum++;
                        } else {
                            try {
                                goodsQueryProvider.checkBindElectronicCoupons(GoodsCheckBindRequest.builder()
                                        .electronicCouponsId(id)
                                        .build());
                            }catch (SbcRuntimeException e) {
                                ExcelHelper.setError(style, workbook, getCell(cells, 28), "该电子卡券已经被其他商品绑定！");
                                isError = true;
                                maxErrorNum++;
                            }
                        }

                        goodsInfo.setElectronicCouponsId(id);
                    }
                } else {
                    // 供货价
                    String supplyPrice = ExcelHelper.getValue(getCell(cells, index));
                    if(StringUtils.isBlank(supplyPrice)) {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                        isError = true;
                        maxErrorNum++;
                    }else if (!supplyPrice.matches("\\d+\\.\\d{1,2}") && !supplyPrice.matches("\\d+")) {
                        ExcelHelper.setError(style, workbook, cells[index], "必须为0或正数");
                        isError = true;
                        maxErrorNum++;
                    }else{
                        goodsInfo.setSupplyPrice(new BigDecimal(supplyPrice));
                        if (goodsInfo.getSupplyPrice().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getSupplyPrice()
                                .compareTo(new BigDecimal("9999999.99")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0-9999999.99范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                    index++;

                    //建议零售价
                    goodsInfo.setRetailPrice(BigDecimal.ZERO);

                    //上下架
                    String addedFlagStr = ExcelHelper.getValue(getCell(cells, index));

                    log.info("商品导入上下架开始"+System.currentTimeMillis());

                    if ("上架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                    } else if ("下架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                    } else {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在[上架、下架]范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                    index++;

                    //重量
                    String weightStr = ExcelHelper.getValue(getCell(cells, index));
                    if (StringUtils.isNotBlank(weightStr)) {
                        goodsInfo.setGoodsWeight(new BigDecimal(weightStr).setScale(3, RoundingMode.HALF_UP));
                        if (goodsInfo.getGoodsWeight().compareTo(new BigDecimal("0.001")) < 0 || goodsInfo.getGoodsWeight()
                                .compareTo(new BigDecimal("9999.999")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0.001-9999.999范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    } else {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                        isError = true;
                        maxErrorNum++;
                    }
                    index++;

                    //体积
                    String cubageStr = ExcelHelper.getValue(getCell(cells, index));
                    if (StringUtils.isNotBlank(cubageStr)) {
                        goodsInfo.setGoodsCubage(new BigDecimal(cubageStr).setScale(6, RoundingMode.HALF_UP));
                        if (goodsInfo.getGoodsCubage().compareTo(new BigDecimal("0.000001")) < 0 || goodsInfo.getGoodsCubage()
                                .compareTo(new BigDecimal("999.999999")) > 0) {
                            ExcelHelper.setError(style, workbook, getCell(cells, index), "必须在0.000001-999.999999范围内");
                            isError = true;
                            maxErrorNum++;
                        }
                    } else {
                        ExcelHelper.setError(style, workbook, getCell(cells, index), "此项必填");
                        isError = true;
                        maxErrorNum++;
                    }
                    index++;

                    if (isFirstGoods) {
                        //商品名称
                        if (StringUtils.isBlank(goods.getGoodsName())) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "此项必填");
                            isError = true;
                            maxErrorNum++;
                        } else if (!ValidateUtil.isBetweenLen(goods.getGoodsName(), 1, 40)) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "长度必须1-40个字");
                            isError = true;
                            maxErrorNum++;
                        } else if (ValidateUtil.containsEmoji(goods.getGoodsName())) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 0), "含有非法字符");
                            isError = true;
                            maxErrorNum++;
                        } else {
                            //敏感词验证
                            String badWord = sensitiveWordService.getBadWordTxt(badwords, goods.getGoodsName());
                            if (badWord != null) {
                                ExcelHelper.setError(style, workbook, getCell(cells, 0), "商品名称".concat(badWord));
                                isError = true;
                                maxErrorNum++;
                            }
                        }

                        goodses.put(goods.getGoodsNo(), goods);
                    } else {
                        if (skus.getOrDefault(goods.getGoodsNo(), new ArrayList<>()).size() >= 200) {
                            ExcelHelper.setError(style, workbook, getCell(cells, 1), "同一SPU的商品不允许超过200条");
                            isError = true;
                            maxErrorNum++;
                        }
                    }

                    //积分价格
                    String buyPointStr = ExcelHelper.getValue(getCell(cells, index));
                    if (StringUtils.isNotBlank(buyPointStr)) {
                        goodsInfo.setBuyPoint(new BigDecimal(buyPointStr).longValue());
                    }else{
                        goodsInfo.setBuyPoint(0L);
                    }
                }
                //供货价
                String attributePriceSilver = ExcelHelper.getValue(getCell(cells, 30));
                log.info("商品导入银卡价格:" + attributePriceSilver);
                if (StringUtils.isEmpty(attributePriceSilver)){
                    goodsInfo.setAttributePriceSilver(null);
                }else if (!attributePriceSilver.matches("\\d+\\.\\d{1,2}") && !attributePriceSilver.matches("\\d+")) {
                    ExcelHelper.setError(style, workbook, cells[30], "必须为大于0并保留两位小数");
                    isError = true;
                    maxErrorNum++;
                } else {
                    goodsInfo.setAttributePriceSilver(new BigDecimal(attributePriceSilver));
                    if (goodsInfo.getAttributePriceSilver().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getAttributePriceSilver()
                            .compareTo(new BigDecimal("9999999.99")) > 0) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 30), "必须在0-9999999.99范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                }
                String attributePriceGold = ExcelHelper.getValue(getCell(cells, 31));
                log.info("商品导入金卡价格:" + attributePriceGold);
                if (StringUtils.isEmpty(attributePriceGold)){
                    goodsInfo.setAttributePriceGold(null);
                }else if (!attributePriceGold.matches("\\d+\\.\\d{1,2}") && !attributePriceGold.matches("\\d+")) {
                    ExcelHelper.setError(style, workbook, cells[31], "必须为大于0并保留两位小数");
                    isError = true;
                    maxErrorNum++;
                } else {
                    goodsInfo.setAttributePriceGold(new BigDecimal(attributePriceGold));
                    if (goodsInfo.getAttributePriceGold().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getAttributePriceGold()
                            .compareTo(new BigDecimal("9999999.99")) > 0) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 31), "必须在0-9999999.99范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                }
                String attributePriceDiamond = ExcelHelper.getValue(getCell(cells, 32));
                log.info("商品导入钻石卡价格:" + attributePriceDiamond);
                if (StringUtils.isEmpty(attributePriceDiamond)){
                    goodsInfo.setAttributePriceDiamond(null);
                }else if (!attributePriceDiamond.matches("\\d+\\.\\d{1,2}") && !attributePriceDiamond.matches("\\d+")) {
                    ExcelHelper.setError(style, workbook, cells[32], "必须为大于0并保留两位小数");
                    isError = true;
                    maxErrorNum++;
                } else {
                    goodsInfo.setAttributePriceDiamond(new BigDecimal(attributePriceDiamond));
                    if (goodsInfo.getAttributePriceDiamond().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getAttributePriceDiamond()
                            .compareTo(new BigDecimal("9999999.99")) > 0) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 32), "必须在0-9999999.99范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                }
                String attributePriceDisount = ExcelHelper.getValue(getCell(cells, 33));
                log.info("商品导入折扣价格:" + attributePriceDisount);
                if (StringUtils.isEmpty(attributePriceDisount)){
                    goodsInfo.setAttributePriceDiscount(null);
                }else if (!attributePriceDisount.matches("\\d+\\.\\d{1,2}") && !attributePriceDisount.matches("\\d+")) {
                    ExcelHelper.setError(style, workbook, cells[33], "必须为大于0并保留两位小数");
                    isError = true;
                    maxErrorNum++;
                } else {
                    goodsInfo.setAttributePriceDiscount(new BigDecimal(attributePriceDisount));
                    if (goodsInfo.getAttributePriceDiscount().compareTo(BigDecimal.ZERO) < 0 || goodsInfo.getAttributePriceDiscount()
                            .compareTo(new BigDecimal("9999999.99")) > 0) {
                        ExcelHelper.setError(style, workbook, getCell(cells, 33), "必须在0-9999999.99范围内");
                        isError = true;
                        maxErrorNum++;
                    }
                }


                log.info("商品导入merge开始"+System.currentTimeMillis());

                skus.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(goodsInfo)), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });

                spuNos.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(getCell(cells, 1))), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });

                skuNos.merge(goodsInfo.getGoodsInfoNo(), new ArrayList(Collections.singletonList(getCell(cells, 9))), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });
                log.info("商品导入merge结束"+System.currentTimeMillis());
            }
            log.info("商品导入for循环结束"+System.currentTimeMillis());
            List<GoodsVO> goodsList = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder()
                    .delFlag(DeleteFlag.NO.toValue()).goodsNos(new ArrayList<>(spuNos.keySet())).build()).getContext().getGoodsVOList();

            //设置重复错误提示
            if (CollectionUtils.isNotEmpty(goodsList)) {
                goodsList.forEach(goods -> {
                    spuNos.get(goods.getGoodsNo()).forEach(cell -> {
                        //为单元格设置重复错误提示
                        ExcelHelper.setError(style, workbook, cell, "该编码重复");
                    });
                });
                isError = true;
                maxErrorNum++;
            }

            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().delFlag
                    (DeleteFlag.NO.toValue()).goodsInfoNos(new ArrayList<>(skuNos.keySet()))
                    .build()).getContext().getGoodsInfos();

            //设置重复错误提示
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                goodsInfos.forEach(sku -> {
                    skuNos.get(sku.getGoodsInfoNo()).forEach(cell -> {
                        //为单元格设置重复错误提示
                        ExcelHelper.setError(style, workbook, cell, "该编码重复");
                    });
                });
                isError = true;
                maxErrorNum++;
            }
            log.info("商品导入for循环开始"+System.currentTimeMillis());
            //针对多个SKU验证规格或规格值是否为空
            for (BatchGoodsDTO goods : goodses.values()) {
                List<BatchGoodsInfoDTO> goodsInfoList = skus.get(goods.getGoodsNo());
                if (goodsInfoList.size() > 1) {
                    boolean isFirst = false;
                    for (BatchGoodsInfoDTO goodsInfo : goodsInfoList) {
                        if (CollectionUtils.isEmpty(goodsInfo.getMockSpecIds()) && (!isFirst)) {
                            ExcelHelper.setError(style, workbook, spuNoSpecs.get(goods.getGoodsNo()), "规格不允许为空");
                            isFirst = true;
                            isError = true;
                            maxErrorNum++;
                        }

                        if (CollectionUtils.isEmpty(goodsInfo.getMockSpecDetailIds())) {
                            //为单元格设置重复错误提示
                            ExcelHelper.setError(style, workbook, spuNoSpecDetails.get(goodsInfo.getGoodsInfoNo()), "规格值不允许为空");
                            isError = true;
                            maxErrorNum++;
                        }
                    }
                }
            }
            log.info("商品导入for循环结束"+System.currentTimeMillis());

            if (isError) {
                this.errorExcel(goodsRequest.getUserId().concat(".").concat(fileExt), workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
            }
            log.info("商品导入for循环开始"+System.currentTimeMillis());
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
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024, e);
        }
        return fileExt;
    }

    /**
     * EXCEL错误文件-本地生成
     * @param newFileName 新文件名
     * @param wk Excel对象
     * @return 新文件名
     * @throws SbcRuntimeException
     */
    public String errorExcel(String newFileName, Workbook wk) throws SbcRuntimeException {
        String userId = commonUtil.getOperatorId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(Constants.GOODS_ERR_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }

    /**
     * EXCEL错误文件-本地生成
     * @param newFileName 新文件名
     * @param wk Excel对象
     * @return 新文件名
     * @throws SbcRuntimeException
     */
    public String errorExcel(String newFileName,String resourceKey, Workbook wk) throws SbcRuntimeException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(resourceKey)
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }

    /**
     * 验证EXCEL
     * @param workbook
     */
    public void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            Sheet sheet2 = workbook.getSheetAt(1);
            if(!(row.getCell(0).getStringCellValue().contains("商品名称") && sheet2.getSheetName().contains("数据"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    /**
     * 验证EXCEL
     * @param workbook 文件对象
     * @param goodsType 商品类型
     */
    public void checkExcelByGoods(Workbook workbook, GoodsType goodsType,StoreType storeType){
        try {
            Row row = workbook.getSheetAt(0).getRow(0);
            if(GoodsType.REAL_GOODS.equals(goodsType)) {
                if(storeType == StoreType.PROVIDER){
                    if(!(row.getCell(25).getStringCellValue().contains("重量") && row.getCell(26).getStringCellValue().contains("体积"))){
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
                    }
                } else {
                    if(!(row.getCell(27).getStringCellValue().contains("重量") && row.getCell(28).getStringCellValue().contains("体积"))){
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
                    }
                }
            } else if(GoodsType.VIRTUAL_GOODS.equals(goodsType)) {
                if(!(row.getCell(27).getStringCellValue().contains("积分价格") && row.getCell(21).getStringCellValue().contains("库存"))){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
                }
            } else if(GoodsType.ELECTRONIC_COUPON_GOODS.equals(goodsType)) {
                if(!(row.getCell(21).getStringCellValue().contains("有效卡密库存"))){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
                }
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    /**
     * 生成不存在skuNoList中的Sku编码
     * @param skuNoList 已存在的sku编码
     * @return sku编码
     */
    /**
     * 递归式，获取SkuNo，防止重复
     * @param existsMap
     * @return
     */
    public String getSkuNo(Map<String,List<Cell>> existsMap){
        String skuNo = getSkuNo();
        if(existsMap.containsKey(skuNo)){
            return getSkuNo(existsMap);
        }
        return skuNo;
    }

    /**
     * 递归式，获取SkuNo，防止重复
     * @param sets
     * @return
     */
    public String getSkuNo(Set<String> sets){
        String skuNo = getSkuNo();
        if(sets.contains(skuNo)){
            return getSkuNo(sets);
        }
        return skuNo;
    }

    /**
     * 获取Sku编码
     * @return Sku编码
     */
    private String getSkuNo() {
        return "8".concat(RandomString.get().randomNumeric(9));
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    /**
     * 下载Excel错误文档
     * @param resourceKey
     * @param ext 文件扩展名
     */
    public void getErrExcel(String resourceKey, String ext){
        //商品导入错误文件resourceKey
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext();
        if (yunGetResourceResponse == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        byte[] content = yunGetResourceResponse.getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try {
            //下载错误文档时强制清除页面文档缓存
            HttpServletResponse response = HttpUtil.getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("expries",-1);
            String fileName = URLEncoder.encode("错误表格.".concat(ext), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition",
                    String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));

            HttpUtil.getResponse().getOutputStream().write(content);
        } catch (Exception e) {
            log.error("下载EXCEL文件异常->", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }


    protected Long getDefaultStoreCateId(List<StoreCateResponseVO> storeCateList) {
        return storeCateList.stream()
                .filter(storeCate -> Objects.equals(DefaultFlag.YES, storeCate.getIsDefault()))
                .map(StoreCateResponseVO::getStoreCateId).findFirst().orElseThrow(() -> new SbcRuntimeException
                        (GoodsErrorCodeEnum.K030053));
    }

    protected Map<Long, Long> getStoreCateSet(List<StoreCateResponseVO> storeCateList) {
        return storeCateList.stream().collect(Collectors.toMap(StoreCateResponseVO::getStoreCateId,
                StoreCateResponseVO::getCateParentId));
    }

    protected Set<Long> getBrandSet(GoodsSupplierExcelImportRequest goodsRequest) {
        ContractBrandListRequest contractBrandQueryRequest = new ContractBrandListRequest();
        contractBrandQueryRequest.setStoreId(goodsRequest.getStoreId());
        return contractBrandQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList()
                .stream().filter(vo -> Objects.nonNull(vo.getGoodsBrand()))
                .map(ContractBrandVO::getGoodsBrand).map(GoodsBrandVO::getBrandId).collect(Collectors.toSet());
    }

    protected Map<Long, String> getGoodsCateMap(GoodsSupplierExcelImportRequest goodsRequest) {
        Map<Long, String> goodsCateMap = getGoodsCateList(goodsRequest)
                .stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCatePath));
        return goodsCateMap;
    }

    protected List<GoodsCateVO> getGoodsCateList(GoodsSupplierExcelImportRequest goodsRequest){
        List<GoodsCateVO> goodsCateVOList = goodsCateQueryProvider.listLeafByStoreId(
                GoodsCateLeafByStoreIdRequest.builder().storeId(goodsRequest.getStoreId()).build()).getContext().getGoodsCateList();
        return goodsCateVOList;
    }

    protected ExcelLoopRowResult<Integer> importStock(BatchGoodsInfoDTO goodsInfo, Cell[] cells,
                                                      int maxErrorNum, Workbook workbook, CellStyle style){
        log.info("商品导入库存开始{} ", System.currentTimeMillis());
        // 库存
        String stock = ExcelHelper.getValue(cells, 21);
        goodsInfo.setStock(StringUtils.isBlank(stock) ? 0 : new BigDecimal(stock).longValue());
        if (goodsInfo.getStock() < 0 || goodsInfo.getStock() > 9999999) {
            ExcelHelper.setError(style, workbook, getCell(cells, 21), "必须在0-9999999范围内");
            return ExcelLoopRowResult.build(Boolean.TRUE, (maxErrorNum+1));
        }
        return ExcelLoopRowResult.build(Boolean.FALSE, maxErrorNum);
    }
}
