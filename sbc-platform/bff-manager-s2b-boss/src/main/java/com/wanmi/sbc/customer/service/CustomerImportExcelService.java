package com.wanmi.sbc.customer.service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerBatchRegisterRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountListRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerRegisterRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailImportAddRequest;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByCustomerAccountResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.dto.CustomerDetailDTO;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.request.CustomerImportExcelRequest;
import com.wanmi.sbc.customer.response.CustomerImportExcelResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 会员导入Excel处理服务
 *
 * @author minchen
 */
@Slf4j
@Service
public class CustomerImportExcelService {

    @Autowired
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private CustomerCacheService customerCacheService;

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
            if (lastRowNum > Constants.NUM_10000) {
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_10000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过10000条，请修改");
                }
            }
            int maxCell = 4;
            boolean isError = false;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//            用户上传表格中所有合法的手机号
            ArrayList<String> phones = new ArrayList<>();
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(1);
                if(Objects.isNull(cell)){
                    continue;
                }
                cell.setCellType(CellType.STRING);
                String phone = cell.getStringCellValue();

                if (SensitiveUtils.isMobilePhone(phone)) {
                    phones.add(phone);
                }
            }
            List<String> oldPhones=null;
            if (phones.size()>0){
                oldPhones = customerQueryProvider.getCustomersByPhones(phones).getContext().getCustomerPhones();
            }
            if(oldPhones!=null&&oldPhones.size()>0){
                isError= true;
            }
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
                // 用户名
                String customerName = null;
                if (getCell(cells, 0).getCellType() == CellType.BOOLEAN) {
                    // 返回布尔类型的值
                    customerName = ObjectUtils.toString(getCell(cells, 0).getBooleanCellValue()).trim();
                } else if (getCell(cells, 0).getCellType() == CellType.NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");
                    customerName = df.format(getCell(cells, 0).getNumericCellValue());
                } else {
                    // 返回字符串类型的值
                    customerName = ObjectUtils.toString(getCell(cells, 0).getStringCellValue()).trim();
                }
                if (StringUtils.isBlank(customerName)) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "此项必填",style);
                    isError = true;
                } else if (customerName.length() > 16) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "用户名长度过长",style);
                    isError = true;
                }
                // 用户账号
                Cell cell = row.getCell(1);
                cell.setCellType(CellType.STRING);
                String customerAccount = cell.getStringCellValue();
                if (StringUtils.isBlank(customerAccount)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "此项必填",style);
                    isError = true;
                } else if (!SensitiveUtils.isMobilePhone(customerAccount)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "请输入正确的手机号",style);
                    isError = true;
                }else if (oldPhones!=null&&oldPhones.size()>0&&oldPhones.contains(customerAccount)){
                    ExcelHelper.setError(workbook, getCell(cells, 1), "此账号已存在",style);
                    isError = true;
                }
                // 用户积分
                String points = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isNotBlank(points)) {
                    if(!NumberUtils.isCreatable(points)) {
                        ExcelHelper.setError(workbook, getCell(cells, 2), "请输入正确的数字",style);
                        isError = true;
                    }else {
                        long pointsAvailable = new BigDecimal(points).longValue();
                        if (pointsAvailable > 999999 || pointsAvailable < 0) {
                            ExcelHelper.setError(workbook, getCell(cells, 2), "必须在0-999999整数范围内,可为0",style);
                            isError = true;
                        }
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
                    .resourceKey(Constants.CUSTOMER_IMPORT_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFile(yunUploadResourceRequest);
        } catch (SbcRuntimeException e) {
            log.error("会员上传异常", e);
            throw e;
        } catch (IOException e) {
            log.error("上传文件到云失败{}", userId, e);
        } catch (Exception e) {
            log.error("会员上传异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return fileExt;
    }


    /**
     * 导入模板
     *
     * @author minchen
     */
    @Transactional
    public CustomerImportExcelResponse importCustomer(CustomerImportExcelRequest excelRequest) {
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.CUSTOMER_IMPORT_EXCEL_DIR.concat(excelRequest.getUserId()))
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
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int maxCell = 4;


            List<CustomerRegisterRequest> registerRequests = new ArrayList<>();
//            用户上传表格中所有合法的手机号
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

                CustomerDTO customerDTO = new CustomerDTO();
                // 用户名
                String customerName = null;
                if (getCell(cells, 0).getCellType() == CellType.BOOLEAN) {
                    // 返回布尔类型的值
                    customerName = ObjectUtils.toString(getCell(cells, 0).getBooleanCellValue()).trim();
                } else if (getCell(cells, 0).getCellType() == CellType.NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");
                    customerName = df.format(getCell(cells, 0).getNumericCellValue());
                } else {
                    // 返回字符串类型的值
                    customerName = ObjectUtils.toString(getCell(cells, 0).getStringCellValue()).trim();
                }
                CustomerDetailDTO detailDTO = new CustomerDetailDTO();
                detailDTO.setCustomerName(customerName);
                customerDTO.setCustomerDetail(detailDTO);

                // 用户账号
                Cell cell = row.getCell(1);
                cell.setCellType(CellType.STRING);
                String customerAccount = cell.getStringCellValue();
                customerDTO.setCustomerAccount(customerAccount);
                customerDTO.setCustomerPassword(customerAccount.substring(5, 11));

                // 用户积分
                String points = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isNotBlank(points)) {
                    if(NumberUtils.isCreatable(points)) {
                        Long pointsAvailable = new BigDecimal(points).longValue();
                        customerDTO.setPointsAvailable(pointsAvailable);
                    }
                } else {
                    customerDTO.setPointsAvailable(0L);
                }
                CustomerRegisterRequest registerRequest = new CustomerRegisterRequest();

                //平台导入，则会员默认已审核
                customerDTO.setCheckState(CheckState.CHECKED);
                registerRequest.setCustomerDTO(customerDTO);
                //手机号相同积分不累加，以第一个为准
                Optional<CustomerRegisterRequest> exist = registerRequests.stream().filter(r->
                        StringUtils.isNotBlank(customerDTO.getCustomerAccount())&&StringUtils.isNotBlank(r.getCustomerDTO().getCustomerAccount())&&
                        customerDTO.getCustomerAccount().equals(r.getCustomerDTO().getCustomerAccount())
                ).findFirst();
                if(!exist.isPresent()){
                    registerRequests.add(registerRequest);
                }
            }
            /**
             * 注册用户并发送验证码、导入
             */
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(registerRequests.size());
            List<List<CustomerRegisterRequest>> splitList = IterableUtils.splitList(registerRequests, maxSize);
            AtomicInteger sendMsgSuccessCount = new AtomicInteger(0);
            AtomicInteger sendMsgFailedCount = new AtomicInteger(0);

            this.importCustomerAsync(splitList,excelRequest.getSendMsgFlag(),sendMsgSuccessCount,sendMsgFailedCount);

            CustomerImportExcelResponse response = new CustomerImportExcelResponse();
            response.setSendMsgSuccessCount(sendMsgSuccessCount.get());
            response.setSendMsgFailedCount(sendMsgFailedCount.get());
            return response;
        } catch (SbcRuntimeException e) {
            log.error("会员导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("会员导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

    }

    /**
     * 异步编排
     * @param splitList
     * @return
     */
    private Map<String,String> findCustomerAsync(ExecutorService executor,List<List<CustomerRegisterRequest>> splitList){
        List<List<String>> customerAccountList = splitList.stream().map(split ->
                split.stream()
                        .map(customerRegisterRequest -> customerRegisterRequest.getCustomerDTO().getCustomerAccount())
                        .collect(Collectors.toList())
        ).collect(Collectors.toList());

        List<CompletableFuture<List<CustomerVO>>> futureList = customerAccountList.stream()
                .map(accountList -> CompletableFuture.supplyAsync(() -> {
                    CustomerByAccountListRequest request = new CustomerByAccountListRequest();
                    request.setCustomerAccountList(accountList);
                    CustomerByCustomerAccountResponse context =
                            customerSiteQueryProvider.getCustomerByCustomerAccountList(request).getContext();
                    return context.getCustomerVOList();
                }, executor)).collect(Collectors.toList());
        //主进程等待线程执行结束，并获取结果
        return futureList.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toConcurrentMap(CustomerVO::getCustomerAccount, CustomerVO::getCustomerId));
    }



    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("会员导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 导入
     * @param splitList
     * @param sendMsgFlag
     * @param sendMsgSuccessCount
     * @param sendMsgFailedCount
     */
    private void importCustomerAsync(List<List<CustomerRegisterRequest>> splitList,boolean sendMsgFlag,
                                     AtomicInteger sendMsgSuccessCount,
                                     AtomicInteger sendMsgFailedCount){
        ExecutorService executorService = this.newThreadPoolExecutor(splitList.size());
        Map<String, String> customerAsyncMap = this.findCustomerAsync(executorService, splitList);
        executorService.shutdown();
        List<List<CustomerRegisterRequest>> registerList  = Lists.newArrayList();
        List<List<CustomerRegisterRequest>> unRegisterList = Lists.newArrayList();
        splitList.forEach(customerList -> {
            Map<Boolean, List<CustomerRegisterRequest>> booleanListMap = customerList.stream()
                    .collect(Collectors.partitioningBy(request ->
                            Objects.nonNull(customerAsyncMap.get(request.getCustomerDTO().getCustomerAccount()))));

            List<CustomerRegisterRequest> trueList = booleanListMap.get(Boolean.TRUE);
            if (CollectionUtils.isNotEmpty(trueList)) {
                unRegisterList.add(trueList);

            }
            List<CustomerRegisterRequest> falseList = booleanListMap.get(Boolean.FALSE);
            if (CollectionUtils.isNotEmpty(falseList)) {
                registerList.add(falseList);
            }

        });
        if(CollectionUtils.isNotEmpty(unRegisterList)){
            this.batchAdd(unRegisterList, customerAsyncMap);
        }
        if(CollectionUtils.isNotEmpty(registerList)){
            CustomerBatchRegisterRequest registerRequest = CustomerBatchRegisterRequest.builder()
                    .customerRegisterRequestList(registerList)
                    .build();
            List<List<CustomerVO>> customerVOList = customerSiteProvider.batchRegister(registerRequest).getContext().getCustomerVOList();
            customerVOList.stream().flatMap(List::stream).forEach(customerVO -> managerBaseProducerService.sendMQForCustomerRegister(customerVO));
            if (sendMsgFlag) {
                this.sendMobileCode(customerVOList,sendMsgSuccessCount,sendMsgFailedCount);
            }
        }
        //
    }

    /**
     * 发送验证码和短信
     * @param registerList
     * @param sendMsgSuccessCount
     * @param sendMsgFailedCount
     */
    private void sendMobileCode(List<List<CustomerVO>> registerList,AtomicInteger sendMsgSuccessCount,
                                AtomicInteger sendMsgFailedCount){
        if (CollectionUtils.isNotEmpty(registerList)) {
            List<CompletableFuture<Integer>> futureList = new ArrayList<>();
            registerList.stream().flatMap(List::stream).forEach(registerCustomer -> {
                CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
                        customerCacheService.sendMobileCode(CacheKeyConstant.IMPORT_CUSTOMER,
                                registerCustomer.getCustomerAccount(),
                                SmsTemplate.CUSTOMER_IMPORT_SUCCESS));
                futureList.add(future);
            });

            for (CompletableFuture<Integer> future: futureList) {
                try {
                    if (Constants.yes.equals(future.get())) {
                        sendMsgSuccessCount.incrementAndGet();
                    } else {
                        sendMsgFailedCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    log.error("发送短信失败:{}", e);
                }
            }
        }
    }

    /**
     * 批量导入
     * @param unRegisterList
     * @param customerAsyncMap
     */
    private void batchAdd(List<List<CustomerRegisterRequest>> unRegisterList,Map<String, String> customerAsyncMap){
        List<List<CustomerPointsDetailAddRequest>> paramList = unRegisterList.stream().map(requestList ->
                requestList.stream()
                        .filter(registerRequest -> !registerRequest.getCustomerDTO().getPointsAvailable().equals(0L))
                        .map(registerRequest ->
                                CustomerPointsDetailAddRequest.builder()
                                        .customerId(customerAsyncMap.get(registerRequest.getCustomerDTO().getCustomerAccount()))
                                        .type(OperateType.GROWTH)
                                        .serviceType(PointsServiceType.CUSTOMER_IMPORT)
                                        .points(registerRequest.getCustomerDTO().getPointsAvailable())
                                        .build()
                        ).collect(Collectors.toList())
        ).collect(Collectors.toList());
        CustomerPointsDetailImportAddRequest addRequest = CustomerPointsDetailImportAddRequest.builder()
                .customerPointsDetailAddRequestList(paramList)
                .build();
        customerPointsDetailSaveProvider.batchAddCustomer(addRequest);
    }

    /**
     * 导入
     */
    /*private void importCustomerAsync(CustomerRegisterRequest registerRequest,boolean sendMsgFlag,
                                     AtomicInteger sendMsgSuccessCount,
                                     AtomicInteger sendMsgFailedCount){
        CustomerByAccountRequest customerRequest = new CustomerByAccountRequest();
        customerRequest.setCustomerAccount(registerRequest.getCustomerDTO().getCustomerAccount());
        CustomerVO res = customerSiteQueryProvider.getCustomerByCustomerAccount(customerRequest).getContext();
        // 会员已存在，添加积分明细
        if (Objects.nonNull(res)) {
            if (!registerRequest.getCustomerDTO().getPointsAvailable().equals(0L)) {
                customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                        .customerId(res.getCustomerId())
                        .type(OperateType.GROWTH)
                        .serviceType(PointsServiceType.CUSTOMER_IMPORT)
                        .points(registerRequest.getCustomerDTO().getPointsAvailable())
                        .build());
            }
        } else {
            // 会员不存在则注册,添加积分明细
            CustomerVO registerCustomer = customerSiteProvider.register(registerRequest).getContext();
            managerBaseProducerService.sendMQForCustomerRegister(registerCustomer);
            if (!registerRequest.getCustomerDTO().getPointsAvailable().equals(0L)) {
                customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                        .customerId(registerCustomer.getCustomerId())
                        .type(OperateType.GROWTH)
                        .serviceType(PointsServiceType.CUSTOMER_IMPORT)
                        .points(registerRequest.getCustomerDTO().getPointsAvailable())
                        .build());
            }
            // 是否需要发送短信
            if (sendMsgFlag) {
                CustomerSendMobileCodeRequest customerSendMobileCodeRequest = new CustomerSendMobileCodeRequest();
                customerSendMobileCodeRequest.setMobile(registerRequest.getCustomerDTO().getCustomerAccount());
                customerSendMobileCodeRequest.setRedisKey(CacheKeyConstant.IMPORT_CUSTOMER);
                customerSendMobileCodeRequest.setSmsTemplate(SmsTemplate.CUSTOMER_IMPORT_SUCCESS);
                if (Constants.yes.equals(customerSiteProvider.sendMobileCode(customerSendMobileCodeRequest).getContext().getResult())) {
                    sendMsgSuccessCount.incrementAndGet();
                } else {
                    // 验证码发送失败
                    sendMsgFailedCount.incrementAndGet();
                }
            }
        }
    }*/

    /**
     * EXCEL错误文件-本地生成
     *
     * @param newFileName 新文件名
     * @param wk          Excel对象
     * @return 新文件名
     * @throws SbcRuntimeException
     */
    private String errorExcel(String newFileName, Workbook wk) throws SbcRuntimeException {
        String userId = commonUtil.getOperatorId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(Constants.CUSTOMER_IMPORT_EXCEL_ERR_DIR.concat(userId))
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
    private void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            Sheet sheet2 = workbook.getSheetAt(1);
            if (!(row.getCell(0).getStringCellValue().contains("客户名称") && sheet2.getSheetName().contains("数据"))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
        }
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }
}
