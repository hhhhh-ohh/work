package com.wanmi.sbc.goods.adjust;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ExcelUtils;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.adjust.request.PriceAdjustConfirmRequest;
import com.wanmi.sbc.goods.api.provider.adjustprice.PriceAdjustmentImportProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordQueryProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailProvider;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailQueryProvider;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceConfirmRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceExecuteRequest;
import com.wanmi.sbc.goods.api.request.adjustprice.PriceAdjustmentTemplateExportRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyAllRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.EditLevelPriceRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoMarketingPriceByNosRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordAddRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordByAdjustNoRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordByIdRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordModifyScanTypeRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailAddBatchRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailListRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsLevelPriceDTO;
import com.wanmi.sbc.goods.bean.dto.PriceAdjustmentRecordDetailDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordDetailVO;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunFileDeleteRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>商品批量改价Store web层通用逻辑</p>
 * Created by of628-wenzhi on 2020-12-14-2:14 下午.
 */
@Slf4j
@Primary
@Service
public class PriceAdjustService {

    @Value("${yun.file.path.env.profile}")
    private String env;

    private final static String PRICE_ADJUST_DIR = "price_adjust_dir";

    private final static int IMPORT_COUNT_LIIT = 5000;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    protected PriceAdjustmentRecordDetailProvider detailProvider;

    @Autowired
    private PriceAdjustmentRecordProvider recordProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PriceAdjustmentImportProvider adjustmentImportProvider;

    @Autowired
    private PriceAdjustmentRecordQueryProvider priceAdjustmentRecordQueryProvider;

    @Autowired
    private PriceAdjustExcuteService priceAdjustExcuteService;

    @Autowired
    private PriceAdjustmentRecordProvider priceAdjustmentRecordProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Value("${scan-task.within-time}")
    private int withinTime;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private PriceAdjustmentRecordDetailQueryProvider detailQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsAuditProvider goodsAuditProvider;

    /**
     * 批量设价文件上传
     *
     * @param uploadFile
     * @return
     */
    public String uploadPriceAdjustFile(MultipartFile uploadFile,PriceAdjustmentType type,Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> analysisFunction) {
        if (uploadFile.isEmpty()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
        }

        String fileExt = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!ExcelUtils.isExcelExt(fileExt)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        //price_adjust_dir/{env}/{storeId}/{customerId}
        String resourceKey = String.format("%s/%s/%s/%s", PRICE_ADJUST_DIR, env, commonUtil.getStoreId(),
                commonUtil.getOperatorId());
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(uploadFile.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            //检测模板正确性
            this.checkExcel(workbook, type);


            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            // 不算第一行5000
            if (lastRowNum > IMPORT_COUNT_LIIT ) {
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > IMPORT_COUNT_LIIT) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030071, new Object[]{IMPORT_COUNT_LIIT});
                }
            }
            //解析数据
            try {
                analysisFunction.apply(workbook);
            } catch (SbcRuntimeException se) {
                if (GoodsErrorCodeEnum.K030069.getCode().equals(se.getErrorCode())
                        || CommonErrorCodeEnum.K999999.getCode().equals(se.getErrorCode())) {
                    //上传错误提示excel文件
                    uploadErrorFile(resourceKey, workbook);
                    se.setData(fileExt);
                    se.setErrorCode(GoodsErrorCodeEnum.K030069.getCode());
                }
                throw se;
            }
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(uploadFile.getBytes())
                    .resourceName(uploadFile.getOriginalFilename())
                    .resourceKey(resourceKey)
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);
        } catch (SbcRuntimeException e) {
            log.error("批量设价文件上传异常，resourceKey={}", resourceKey, e);
            if (CommonErrorCodeEnum.K000001.getCode().equals(e.getErrorCode())){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
            }
            throw e;
        } catch (IOException e) {
            log.error("批量设价文件上传失败,resourceKey={}", resourceKey, e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } catch (Exception e) {
            log.error("批量设价文件上传异常，resourceKey={},异常信息：{}", resourceKey, e.getMessage());
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
        }
        return fileExt;
    }


    /**
     * 批量设价文件确认导入
     *
     * @param analysisFunction 数据校验与解析逻辑
     * @param type             调价类型
     * @return
     */
    public String importPriceAdjustFile(Function<Workbook, List<PriceAdjustmentRecordDetailDTO>> analysisFunction,
                                        PriceAdjustmentType type) {
        // price_adjust_dir/{env}/{storeId}/{customerId}
        String resourceKey = String.format("%s/%s/%s/%s", PRICE_ADJUST_DIR, env, commonUtil.getStoreId(),
                commonUtil.getOperatorId());
        // 下载文件
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();

        if (Objects.isNull(content)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
        }
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            // 解析数据
            List<PriceAdjustmentRecordDetailDTO> details;
            try {
                details = analysisFunction.apply(workbook);
            } catch (SbcRuntimeException se) {
                if (GoodsErrorCodeEnum.K030069.getCode().equals(se.getErrorCode())
                        || CommonErrorCodeEnum.K999999.getCode().equals(se.getErrorCode())) {
                    // 云服务删除原文件
                    yunServiceProvider.deleteFile(new YunFileDeleteRequest(Collections.singletonList(resourceKey)));
                    //上传错误提示excel文件
                    String errorFileUrl = uploadErrorFile(resourceKey, workbook);
                    se.setData(errorFileUrl);
                    se.setErrorCode(GoodsErrorCodeEnum.K030069.getCode());
                }
                throw se;
            }

            // 改价记录入库
            String recordId = this.saveRecord(details.size(), type).getId();
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(details.size());
            List<List<PriceAdjustmentRecordDetailDTO>> splitList = IterableUtils.splitList(details, maxSize);
            this.importRecordAndDetailsAsync(splitList, type, recordId);

            // 云服务删除原文件
            yunServiceProvider.deleteFile(new YunFileDeleteRequest(Collections.singletonList(resourceKey)));
            return recordId;
        } catch (SbcRuntimeException e) {
            log.error("批量设价文件导入异常，resourceKey={}", resourceKey, e);
            if (CommonErrorCodeEnum.K000001.getCode().equals(e.getErrorCode())){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
            }
            throw e;
        } catch (Exception e) {
            log.error("批量设价文件导入异常，resourceKey={},异常信息：", resourceKey, e);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
        }
    }


    /**
     * 并发导入
     * @param details
     * @param type
     * @return
     */
    private void importRecordAndDetailsAsync(List<List<PriceAdjustmentRecordDetailDTO>> details,PriceAdjustmentType type,String recordId){
        try {
            Long storeId = commonUtil.getStoreId();
            final CountDownLatch count = new CountDownLatch(details.size());
            ExecutorService executor = this.newThreadPoolExecutor(details.size());
            for (List<PriceAdjustmentRecordDetailDTO> detailList : details) {
                executor.execute(()->{
                    try {
                        this.saveDetails(detailList, type, recordId, storeId);
                    } catch (Exception e) {
                        log.error("导入异常：", e);
                    } finally {
                        // 无论是否报错始终执行countDown()，否则报错时主进程一直会等待线程结束
                        count.countDown();
                    }
                });
            }
            count.await();
            executor.shutdown();
        } catch (Exception e) {
            log.error("异步编排导入异常：{}",e.getMessage());
        }
    }

    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("商品调价导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 确认调价通用逻辑
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void adjustPriceConfirm(PriceAdjustConfirmRequest request) {
        // 0.声明变量
        Long storeId = commonUtil.getStoreId();
        // 1.确认调价单
        AdjustPriceConfirmRequest confirmRequest = AdjustPriceConfirmRequest
                .builder()
                .adjustNo(request.getAdjustNo())
                .storeId(storeId)
                .effectiveTime(request.getStartTime())
                .build();
        // 1.1 如果是立刻确认，生效时间置为当前时间
        if (request.getIsNow()) {
            confirmRequest.setEffectiveTime(LocalDateTime.now());
        }

        //判断boss端二次商品审核开关是否开启
        confirmRequest.setAudit(checkSecondGoodsAudit());
        // 1.2 调价单确认
        detailProvider.adjustPriceConfirm(confirmRequest);

        // 2.根据是否立即执行，判断是立即执行调价还是定时任务执行调价
        if (confirmRequest.isAudit()){
            //生成二次审核记录
            generateGoodsAudit(request);
        }else {
            if (request.getIsNow()) {
                // 执行调价
                priceAdjustExcuteService.excuteAdjustPrice(request, storeId);
            } else {
                // 如果是定期调价，使用延时队列
                sendDelayMqForPriceAdjustment(request);
            }
        }
    }

    /**
     * 生成商品二次审核记录
     * @param request
     */
    private void generateGoodsAudit(PriceAdjustConfirmRequest request) {

        //获取调价单
        PriceAdjustmentRecordVO recordVO = priceAdjustmentRecordQueryProvider
                .getByAdjustNo(PriceAdjustmentRecordByAdjustNoRequest.builder().adjustNo(request.getAdjustNo()).build())
                .getContext()
                .getPriceAdjustmentRecordVO();

        //获取调价单详情
        List<PriceAdjustmentRecordDetailVO> detailVOList = detailQueryProvider
                .list(PriceAdjustmentRecordDetailListRequest.builder().priceAdjustmentNo(request.getAdjustNo()).build())
                .getContext()
                .getPriceAdjustmentRecordDetailVOList();

        //生成二次审核记录
        switch (recordVO.getPriceAdjustmentType()) {
            case MARKET:
                generateMarketPrice(detailVOList);
                break;
            case LEVEL:
                generateLevelPrice(detailVOList);
                break;
            case STOCK:
                generateStockPrice(detailVOList);
                break;
            case SUPPLY:
                generateSupplyPrice(detailVOList);
                break;
            default:
        }
    }

    /**
     * sku批量改价按供货价生成待审核记录
     * @param detailVOList
     */
    private void generateSupplyPrice(List<PriceAdjustmentRecordDetailVO> detailVOList) {
        List<String> goodsInfoIds = detailVOList
                .stream()
                .map(PriceAdjustmentRecordDetailVO::getGoodsInfoId)
                .collect(Collectors.toList());

        List<GoodsVO> goodsVOList = getGoodsVOS(goodsInfoIds);

        for (GoodsVO goodsVO : goodsVOList) {
            //循环生成商品待审核记录
            GoodsViewByIdResponse response = goodsBaseService.info(goodsVO.getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, null);
            for (GoodsInfoVO goodsInfo : goodsModifyRequest.getGoodsInfos()) {
                PriceAdjustmentRecordDetailVO detailVO = detailVOList
                        .stream()
                        .filter(v -> Objects.equals(v.getGoodsInfoId(), goodsInfo.getGoodsInfoId()))
                        .findFirst()
                        .orElse(null);
                if (Objects.nonNull(detailVO)){
                    goodsInfo.setSupplyPrice(detailVO.getAdjustSupplyPrice());
                }
            }
            goodsModifyRequest.setIsBatchEditPrice(BoolFlag.YES);
            goodsBaseService.providerEdit(goodsModifyRequest);
        }
    }

    /**
     * sku批量改价按批发价生成待审核记录
     * @param detailVOList
     */
    private void generateStockPrice(List<PriceAdjustmentRecordDetailVO> detailVOList) {
        List<String> goodsInfoIds = detailVOList
                .stream()
                .map(PriceAdjustmentRecordDetailVO::getGoodsInfoId)
                .collect(Collectors.toList());

        List<GoodsVO> goodsVOList = getGoodsVOS(goodsInfoIds);

        for (GoodsVO goodsVO : goodsVOList) {
            //循环生成商品待审核记录
            GoodsViewByIdResponse response = goodsBaseService.info(goodsVO.getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, null);
            GoodsModifyAllRequest goodsModifyAllRequest = KsBeanUtil.convert(goodsModifyRequest, GoodsModifyAllRequest.class);
            goodsModifyAllRequest.setIsBatchEditPrice(BoolFlag.YES);
            goodsBaseService.dealPrice(goodsModifyAllRequest);

            goodsAuditProvider.editStockPrice(EditLevelPriceRequest
                    .builder()
                    .oldGoodsId(goodsVO.getGoodsId())
                    .detailVOList(detailVOList)
                    .build());
        }
    }

    /**
     * sku批量改价按等级价生成待审核记录
     * @param detailVOList
     */
    private void generateLevelPrice(List<PriceAdjustmentRecordDetailVO> detailVOList) {
        List<String> goodsInfoIds = detailVOList
                .stream()
                .map(PriceAdjustmentRecordDetailVO::getGoodsInfoId)
                .collect(Collectors.toList());

        List<GoodsVO> goodsVOList = getGoodsVOS(goodsInfoIds);

        for (GoodsVO goodsVO : goodsVOList) {
            //循环生成商品待审核记录
            GoodsViewByIdResponse response = goodsBaseService.info(goodsVO.getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, null);
            GoodsModifyAllRequest goodsModifyAllRequest = KsBeanUtil.convert(goodsModifyRequest, GoodsModifyAllRequest.class);
            goodsModifyAllRequest.setIsBatchEditPrice(BoolFlag.YES);
            goodsBaseService.dealPrice(goodsModifyAllRequest);

            goodsAuditProvider.editLevelPrice(EditLevelPriceRequest
                            .builder()
                            .oldGoodsId(goodsVO.getGoodsId())
                            .detailVOList(detailVOList)
                            .build());
        }

    }

    /**
     * sku批量改价按市场价生成待审核记录
     * @param detailVOList
     */
    private void generateMarketPrice(List<PriceAdjustmentRecordDetailVO> detailVOList) {
        List<String> goodsInfoIds = detailVOList
                .stream()
                .map(PriceAdjustmentRecordDetailVO::getGoodsInfoId)
                .collect(Collectors.toList());

        //获取商品信息
        List<GoodsVO> goodsVOList = getGoodsVOS(goodsInfoIds);

        for (GoodsVO goodsVO : goodsVOList) {
            //循环生成商品待审核记录
            GoodsViewByIdResponse response = goodsBaseService.info(goodsVO.getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, null);
            for (GoodsInfoVO goodsInfo : goodsModifyRequest.getGoodsInfos()) {
                PriceAdjustmentRecordDetailVO detailVO = detailVOList
                        .stream()
                        .filter(v -> Objects.equals(v.getGoodsInfoId(), goodsInfo.getGoodsInfoId()))
                        .findFirst()
                        .orElse(null);
                if (Objects.nonNull(detailVO)){
                    goodsInfo.setMarketPrice(detailVO.getAdjustedMarketPrice());
                }
            }
            GoodsModifyAllRequest goodsModifyAllRequest = KsBeanUtil.convert(goodsModifyRequest, GoodsModifyAllRequest.class);
            goodsModifyAllRequest.setIsBatchEditPrice(BoolFlag.YES);
            goodsBaseService.dealPrice(goodsModifyAllRequest);
        }
    }

    /**
     * 获取商品信息
     * @param goodsInfoIds
     * @return
     */
    private List<GoodsVO> getGoodsVOS(List<String> goodsInfoIds) {
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider
                .getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(goodsInfoIds).build())
                .getContext()
                .getGoodsInfos();

        return goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder()
                        .goodsIds(goodsInfos
                            .stream()
                            .map(GoodsInfoVO::getGoodsId)
                            .distinct()
                            .collect(Collectors.toList()))
                        .build())
                .getContext()
                .getGoodsVOList();
    }

    /**
     * boss端是否开启二次审核开关
     * @return
     */
    private boolean checkSecondGoodsAudit() {
        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            return auditQueryProvider
                    .isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                            .builder()
                            .configType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT)
                            .build())
                    .getContext()
                    .isAudit();
        }else {
            return auditQueryProvider
                    .isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                            .builder()
                            .configType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT)
                            .build())
                    .getContext()
                    .isAudit();
        }

    }

    /**
     * 调价模板下载
     *
     * @param request
     */
    public void downloadAdjustPriceTemplate(PriceAdjustmentTemplateExportRequest request, String templateFileName) {
        request.setStoreId(commonUtil.getStoreId());
        String file = adjustmentImportProvider.exportAjustmentPriceTemplate(request).getContext().getFileOutputStream();
        if (StringUtils.isNotBlank(file)) {
            try {
                String fileName = URLEncoder.encode(templateFileName, StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 下载错误表格
     *
     * @return
     */
    public void downErrorFile(String ext) {

        if (!(ext.equalsIgnoreCase(Constants.XLS) || ext.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        String errorResourceKey = String.format("%s/%s/%s/%s", PRICE_ADJUST_DIR, env, commonUtil.getStoreId(),
                commonUtil.getOperatorId()).concat("_error");
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(errorResourceKey)
                .build()).getContext().getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try (
                InputStream is = new ByteArrayInputStream(content);
                ServletOutputStream os = HttpUtil.getResponse().getOutputStream()
        ) {
            //下载错误文档时强制清除页面文档缓存\
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
     * 越权校验
     *
     * @param adjustRecordNo
     */
    public void checkOperator(String adjustRecordNo) {
        Operator operator = commonUtil.getOperator();
        PriceAdjustmentRecordVO data = priceAdjustmentRecordQueryProvider.getById(new PriceAdjustmentRecordByIdRequest(adjustRecordNo,
                Long.valueOf(operator.getStoreId()))).getContext().getPriceAdjustmentRecordVO();

        if (Objects.isNull(data) || !data.getCreatePerson().equals(operator.getUserId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
    }

    /**
     * 保存调价记录
     *
     * @param skuSize
     * @param type
     */
    private PriceAdjustmentRecordVO saveRecord(long skuSize, PriceAdjustmentType type) {
        Operator operator = commonUtil.getOperator();
        PriceAdjustmentRecordAddRequest request = PriceAdjustmentRecordAddRequest.builder()
                .id(generatorService.generateAPId())
                .confirmFlag(0)
                .createPerson(operator.getUserId())
                .creatorAccount(operator.getAccount())
                .storeId(Long.valueOf(operator.getStoreId()))
                .creatorName(operator.getName())
                .priceAdjustmentType(type)
                .goodsNum(skuSize)
                .build();
        return recordProvider.add(request).getContext().getPriceAdjustmentRecordVO();
    }

    /**
     * 保存调价详情
     *
     * @param details
     */
    protected void saveDetails(List<PriceAdjustmentRecordDetailDTO> details, PriceAdjustmentType type, String recordId, Long storeId) {
        List<String> skuNos = details.stream().map(PriceAdjustmentRecordDetailDTO::getGoodsInfoNo).collect(Collectors.toList());
        Map<String, GoodsInfoMarketingPriceDTO> marketingPriceMap = goodsInfoQueryProvider.listMarketingPriceByNos(new GoodsInfoMarketingPriceByNosRequest(
                skuNos, storeId)).getContext().getDataList().stream().collect(Collectors.toMap(GoodsInfoMarketingPriceDTO::getGoodsInfoNo, Function.identity()));
        switch (type) {
            case MARKET:
                buildMarketPriceDetails(details, marketingPriceMap, recordId);
                break;
            case SUPPLY:
                buildSupplyPriceDetails(details, marketingPriceMap, recordId);
                break;
            case LEVEL:
                buildLevelPriceDetails(details, marketingPriceMap, recordId);
                break;
            case STOCK:
                buildIntervalPriceDetails(details, marketingPriceMap, recordId);
                break;
            default:
        }
        //批量保存调价详情
        details.forEach(v->v.setAuditStatus(Constants.ZERO));
        detailProvider.addBatch(new PriceAdjustmentRecordDetailAddBatchRequest(details));
    }

    private void buildIntervalPriceDetails(List<PriceAdjustmentRecordDetailDTO> details, Map<String, GoodsInfoMarketingPriceDTO> marketingPriceMap, String recordId) {
        details.forEach(i -> {
            GoodsInfoMarketingPriceDTO item = marketingPriceMap.get(i.getGoodsInfoNo());
            i.setGoodsInfoId(item.getGoodsInfoId());
            i.setGoodsId(item.getGoodsId());
            //设置SKU原市场价
            i.setOriginalMarketPrice(item.getMarketingPrice() != null ? item.getMarketingPrice() : BigDecimal.ZERO);
            //设置区间价
            List<GoodsIntervalPriceDTO> intervalPriceList = JSON.parseArray(i.getIntervalPrice(), GoodsIntervalPriceDTO.class);
            intervalPriceList.forEach(l -> {
                l.setGoodsId(item.getGoodsId());
                l.setGoodsInfoId(item.getGoodsInfoId());
            });
            i.setIntervalPrice(JSONObject.toJSONString(intervalPriceList));
            //设置状态
            i.setConfirmFlag(DefaultFlag.NO);
            i.setAdjustResult(PriceAdjustmentResult.UNDO);
            i.setPriceAdjustmentNo(recordId);
            i.setGoodsType(item.getGoodsType());
        });
        //相同SPU下的SKU，出现不同的销售类型则以第一个SKU的销售类型覆盖
        saleTypeReset(details);
    }


    private void buildLevelPriceDetails(List<PriceAdjustmentRecordDetailDTO> details, Map<String, GoodsInfoMarketingPriceDTO> marketingPriceMap, String recordId) {
        details.forEach(i -> {
            GoodsInfoMarketingPriceDTO item = marketingPriceMap.get(i.getGoodsInfoNo());
            i.setGoodsInfoId(item.getGoodsInfoId());
            i.setGoodsId(item.getGoodsId());
            //设置SKU原市场价
            i.setOriginalMarketPrice(item.getMarketingPrice() != null ? item.getMarketingPrice() : BigDecimal.ZERO);
            //设置级别价
            List<GoodsLevelPriceDTO> levelPriceList = JSON.parseArray(i.getLeverPrice(), GoodsLevelPriceDTO.class);
            levelPriceList.forEach(l -> {
                l.setGoodsId(item.getGoodsId());
                l.setGoodsInfoId(item.getGoodsInfoId());
            });
            i.setLeverPrice(JSONObject.toJSONString(levelPriceList));
            //设置状态
            i.setConfirmFlag(DefaultFlag.NO);
            i.setAdjustResult(PriceAdjustmentResult.UNDO);
            i.setPriceAdjustmentNo(recordId);
            i.setGoodsType(item.getGoodsType());
        });
        //相同SPU下的SKU，出现不同的销售类型则以第一个SKU的销售类型覆盖
        saleTypeReset(details);
    }

    /**
     * 市场价
     *
     * @param details
     * @return
     */
    private void buildMarketPriceDetails(List<PriceAdjustmentRecordDetailDTO> details, Map<String,
            GoodsInfoMarketingPriceDTO> marketingPriceMap, String recordId) {

        details.forEach(i -> {
            GoodsInfoMarketingPriceDTO item = marketingPriceMap.get(i.getGoodsInfoNo());
            i.setGoodsInfoId(item.getGoodsInfoId());
            i.setGoodsId(item.getGoodsId());
            //设置SKU原市场价
            i.setOriginalMarketPrice(item.getMarketingPrice() != null ? item.getMarketingPrice() : BigDecimal.ZERO);
            //设置差价
            i.setPriceDifference(i.getAdjustedMarketPrice().subtract(i.getOriginalMarketPrice()));
            i.setConfirmFlag(DefaultFlag.NO);
            i.setAdjustResult(PriceAdjustmentResult.UNDO);
            i.setPriceAdjustmentNo(recordId);
            i.setGoodsType(item.getGoodsType());
        });
        //相同SPU下的SKU，出现不同的销售类型则以第一个SKU的销售类型覆盖
        saleTypeReset(details);

    }

    /**
     * 供货价
     *
     * @param details
     * @param marketingPriceMap
     * @return
     */
    private void buildSupplyPriceDetails(List<PriceAdjustmentRecordDetailDTO> details, Map<String,
            GoodsInfoMarketingPriceDTO> marketingPriceMap, String recordId) {
        details.forEach(i -> {
            GoodsInfoMarketingPriceDTO item = marketingPriceMap.get(i.getGoodsInfoNo());
            i.setGoodsInfoId(item.getGoodsInfoId());
            i.setGoodsId(item.getGoodsId());
            i.setConfirmFlag(DefaultFlag.NO);
            i.setAdjustResult(PriceAdjustmentResult.UNDO);
            i.setPriceAdjustmentNo(recordId);
            i.setGoodsInfoName(item.getGoodsInfoName());
            i.setSupplyPrice(item.getSupplyPrice() == null ? BigDecimal.ZERO : item.getSupplyPrice());
            i.setPriceDifference(i.getAdjustSupplyPrice().subtract(i.getSupplyPrice()));

        });

    }

    /**
     * 上传改价错误提示文件
     *
     * @param resourceKey
     * @param workbook
     * @return
     */
    private String uploadErrorFile(String resourceKey, Workbook workbook) {
        String errorResourceKey = resourceKey.concat("_error");
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceKey(errorResourceKey)
                    .build();
            //文件校验错误，返回错误提示文件URL
            return yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
        } catch (IOException e) {
            log.error("批量改价错误文件上传至云空间失败，errorResourceKey:{}", errorResourceKey, e);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
        }
    }

    private void saleTypeReset(List<PriceAdjustmentRecordDetailDTO> details) {
        //相同SPU下的SKU，出现不同的销售类型则以第一个SKU的销售类型覆盖
        LinkedHashMap<String, List<PriceAdjustmentRecordDetailDTO>> detailsMap = details.stream().collect(Collectors.
                groupingBy(PriceAdjustmentRecordDetailDTO::getGoodsId, LinkedHashMap::new, Collectors.toList()));
        detailsMap.values().forEach(
                value -> {
                    PriceAdjustmentRecordDetailDTO firstData = value.get(0);
                    if (Objects.nonNull(firstData)) {
                        value.forEach(i -> i.setSaleType(firstData.getSaleType()));
                    }
                }
        );
    }

    /**
     * 验证EXCEL
     * @param workbook
     */
    public void checkExcel(Workbook workbook, PriceAdjustmentType type){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if (PriceAdjustmentType.MARKET == type && !row.getCell(3).getStringCellValue().contains("调整后市场价")){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }else if(PriceAdjustmentType.LEVEL == type && !(row.getCell(4).getStringCellValue().contains("sku保持独立设价")
                    && row.getCell(5).getStringCellValue().contains("调整后等级价"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }else if(PriceAdjustmentType.STOCK == type && !(row.getCell(4).getStringCellValue().contains("sku保持独立设价")
                    && row.getCell(5).getStringCellValue().contains("订货区间"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }else if(PriceAdjustmentType.SUPPLY == type && !row.getCell(3).getStringCellValue().contains("调整后供货价")){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        } catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    /***
     * 如果是定期调价，发送一条延时队列
     * @param request
     */
    protected void sendDelayMqForPriceAdjustment(PriceAdjustConfirmRequest request) {
        // 批量调价 小于任务扫描周期直接推送到队列
        Duration duration = Duration.between(LocalDateTime.now(), request.getStartTime());
        if (duration.toMinutes() < withinTime) {
            // 调价单详情
            PriceAdjustmentRecordVO recordVO = priceAdjustmentRecordQueryProvider.getByAdjustNo(
                    PriceAdjustmentRecordByAdjustNoRequest.builder().adjustNo(request.getAdjustNo()).build())
                    .getContext().getPriceAdjustmentRecordVO();
            MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
            mqSendDelayDTO.setTopic(ProducerTopic.GOODS_PRICE_ADJUST);
            AdjustPriceExecuteRequest adjustPriceExecuteRequest = AdjustPriceExecuteRequest.builder()
                    .adjustNo(recordVO.getId())
                    .storeId(recordVO.getStoreId())
                    .build();
            // 延迟时间（单位毫秒）
            long delayTime = duration.toMillis();
            mqSendDelayDTO.setData(JSON.toJSONString(adjustPriceExecuteRequest));
            mqSendDelayDTO.setDelayTime(delayTime);
            if (duration.toMinutes() < 0) {
                mqSendProvider.send(mqSendDelayDTO);
            } else {
                mqSendProvider.sendDelay(mqSendDelayDTO);
            }
            // 批量更新扫描标识为已扫描
            priceAdjustmentRecordProvider.modifyScanType(PriceAdjustmentRecordModifyScanTypeRequest.builder()
                    .ids(Collections.singletonList(recordVO.getId()))
                    .build());
        }
    }
}
