package com.wanmi.sbc.department.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.api.provider.department.DepartmentProvider;
import com.wanmi.sbc.customer.api.request.department.DepartmentImportRequest;
import com.wanmi.sbc.customer.bean.dto.DepartmentImportDTO;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.department.request.DepartmentExcelImportRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 商品EXCEL处理服务
 * Created by dyt on 2017/8/17.
 */
@Slf4j
@Service
public class DepartmentExcelService {

    @Autowired
    private DepartmentProvider departmentProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;
    /**
     * 上传文件
     * @param file 文件
     * @param userId 操作员id
     * @return 文件格式
     */
    public String upload(MultipartFile file, String userId){
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt =
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }


        String resourceKey = Constants.DEPARTMENT_EXCEL_DIR.concat(userId);
        Set<String> importDepartmentString = new HashSet<>();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            //检测文档正确性
            this.checkExcel(workbook);

            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < Constants.TWO) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            if(lastRowNum > Constants.NUM_10001){
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_10001) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过10000条，请修改");
                }
            }
            boolean isError = false;
            String[] departmentNames;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //循环除了第一行的所有行
            for (int rowNum = 2; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                boolean isNotEmpty = false;
                Cell cell = row.getCell(0);
                if(cell == null) {
                    cell = row.createCell(0);
                }
                if(StringUtils.isNotBlank(ExcelHelper.getValue(cell))){
                    isNotEmpty = true;
                }
                //数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }
                String departmentName =  ExcelHelper.getValue(cell);
                if (StringUtils.isBlank(departmentName)) {
                    ExcelHelper.setError(workbook, cell, "此项必填",style);
                    isError = true;
                }  else if (ValidateUtil.containsEmoji(departmentName)) {
                    ExcelHelper.setError(workbook, cell, "含有非法字符",style);
                    isError = true;
                }
                departmentNames = departmentName.split("-");
                boolean isCheckDepartment =  Arrays.stream(departmentNames).anyMatch(s -> (StringUtils.isBlank(s) || s.length() > 20));
                if (isCheckDepartment) {
                    ExcelHelper.setError(workbook, cell, "格式不正确",style);
                    isError = true;
                }
                importDepartmentString.add(departmentName);
            }

            if (isError) {
                this.errorExcel(userId.concat(".").concat(fileExt), workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
            }

            if (CollectionUtils.isEmpty(importDepartmentString)){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010123);
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
            log.error("部门导入异常", e);
            throw e;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (Exception e) {
            log.error("部门导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return fileExt;
    }

    /**
     * 导入模板
     *
     * @return
     */
    public void importDepartment(DepartmentExcelImportRequest departmentExcelImportRequest) {
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.DEPARTMENT_EXCEL_DIR.concat(departmentExcelImportRequest.getUserId()))
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        Set<String> importDepartmentString = new HashSet<>();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            int lastRowNum = sheet.getLastRowNum();

            for (int rowNum = 2; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                boolean isNotEmpty = false;
                Cell cell = row.getCell(0);
                if(cell == null) {
                    cell = row.createCell(0);
                }
                if(StringUtils.isNotBlank(ExcelHelper.getValue(cell))){
                    isNotEmpty = true;
                }
                //数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }
                String departmentName =  ExcelHelper.getValue(cell);
                importDepartmentString.add(departmentName);
            }
            if (CollectionUtils.isEmpty(importDepartmentString)){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010123);
            }
            List<DepartmentImportDTO> addRequestList = new ArrayList<>();
            String departmentId = UUIDUtil.getUUID();
            for (String node : importDepartmentString) {
                String[] nodeData = node.split("-");
                if (nodeData.length < 1){
                    continue;
                }
                //部门-根节点
                String parentDepartmentName = nodeData[0];
                //
                String preParentDepartmentName;
                //
                String checkPreParentDepartmentName = parentDepartmentName;
                //上一级部门名称
                String preDepartmentName="";
                int allNodeLength = nodeData.length ;
                for (int i = 0; i < allNodeLength; i++) {

                    if (checkDepartmentIsExit(addRequestList,nodeData[i],parentDepartmentName,i)) {
                        checkPreParentDepartmentName = parentDepartmentName;
                        parentDepartmentName = parentDepartmentName.concat("-").concat(nodeData[i]);
                        preDepartmentName = nodeData[i];
                        continue;
                    }else{
                        preParentDepartmentName =  checkPreParentDepartmentName;
                    }
                    String parentDepartmentId = getParentDepartmentId(addRequestList,preParentDepartmentName,preDepartmentName,i);
                    String parentDepartmentIds = getParentDepartmentIds(addRequestList,preParentDepartmentName,preDepartmentName,i);
                    int  sort = getDepartmentSort(addRequestList,parentDepartmentName,i).intValue();
                    DepartmentImportDTO nodeAddRequest = DepartmentImportDTO.builder().departmentId(departmentId)
                            .departmentName(nodeData[i]).companyInfoId(departmentExcelImportRequest.getCompanyInfoId())
                            .departmentGrade(i+1).departmentSort(sort+1).parentDepartmentId(parentDepartmentId)
                            .employeeNum(0).parentDepartmentIds(parentDepartmentIds).delFlag(DeleteFlag.NO).createTime(LocalDateTime.now()).createPerson(departmentExcelImportRequest.getUserId()).parentDepartmentName(parentDepartmentName).build();
                    addRequestList.add(nodeAddRequest);
                    preDepartmentName = nodeData[i];
                    checkPreParentDepartmentName = parentDepartmentName;
                    parentDepartmentName = parentDepartmentName.concat("-").concat(nodeData[i]);

                    departmentId = UUIDUtil.getUUID();
                }

            }
            if (CollectionUtils.isNotEmpty(addRequestList)) {
                OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
                int maxSize = operatorInteger.apply(addRequestList.size());
                List<List<DepartmentImportDTO>> splitList = IterableUtils.splitList(addRequestList, maxSize);
                this.importDepartmentAsync(splitList);
            }

        } catch (SbcRuntimeException e) {
            log.error("部门导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("部门导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 并发导入
     * @param splitList
     * @return
     */
    private void importDepartmentAsync(List<List<DepartmentImportDTO>> splitList ){
        try {
            final CountDownLatch count = new CountDownLatch(splitList.size());
            ExecutorService executor = this.newThreadPoolExecutor(splitList.size());
            for (List<DepartmentImportDTO> departmentImportDTOList : splitList) {
                //部门导入
                executor.execute(()->{
                    try {
                        departmentProvider.addfromImport(new DepartmentImportRequest(departmentImportDTOList));
                    } catch (Exception e) {
                        log.error("导入异常：{}",e.getMessage());
                    } finally {
                        //无论是否报错始终执行countDown()，否则报错时主进程一直会等待线程结束
                        count.countDown();
                    }
                });
            }
            //主进程等待线程执行结束
            count.await();
            //关闭线程池
            executor.shutdown();
        } catch (Exception e) {
            log.error("部门导入异常：{}",e.getMessage());
        }
    }

    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("部门导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 根据当前部门名称、父部门层级名称 验证部门是否已存在
     * @param addRequestList
     * @param departmentName
     * @param parentDepartmentName
     * @param departmentGrade
     * @return
     */
    private Boolean checkDepartmentIsExit (List<DepartmentImportDTO> addRequestList ,final String departmentName,final String parentDepartmentName,final int departmentGrade){
      return  addRequestList.stream().anyMatch(v -> v.getDepartmentName().equals(departmentName) && v.getParentDepartmentName().equals(parentDepartmentName) && v.getDepartmentGrade().equals(departmentGrade+1));
    }

    /**
     * 根据前一级的部门层级名称、前一个部门名称、层级 查询出当前部门的父部门ID
     * @param addRequestList
     * @param parentDepartmentName
     * @param preDepartmentName
     * @param departmentGrade
     * @return
     */
    private String getParentDepartmentId( List<DepartmentImportDTO> addRequestList ,final String parentDepartmentName,final String preDepartmentName,final int departmentGrade){
       return departmentGrade == 0 ? "0" : addRequestList.stream().filter(v -> v.getParentDepartmentName().equals(parentDepartmentName) && v.getDepartmentName().equals(preDepartmentName)  && v.getDepartmentGrade().equals(departmentGrade))
                .map(DepartmentImportDTO::getDepartmentId).findFirst().orElse("0");
    }

    /**
     * 根据前一级部门的部门层级名称、前一个部门名称、层级 拼接出当前部门的层级数据结构（ 1-10-15 ）
     * @param addRequestList
     * @param parentDepartmentName
     * @param preDepartmentName
     * @param departmentGrade
     * @return
     */
    private String getParentDepartmentIds( List<DepartmentImportDTO> addRequestList ,final String parentDepartmentName,final String preDepartmentName,final int departmentGrade){
        return departmentGrade == 0 ? "0|" : addRequestList.stream().filter(v -> v.getParentDepartmentName().equals(parentDepartmentName) && v.getDepartmentName().equals(preDepartmentName) && v.getDepartmentGrade().equals(departmentGrade))
                .map(v ->
                    v.getParentDepartmentIds().concat(v.getDepartmentId()).concat("|")
                ).findFirst().orElse("");
    }

    /**
     * 根据当前部门的部门层级名称，当前层级汇总出同父层级的总数
     * @param addRequestList
     * @param parentDepartmentName
     * @param departmentGrade
     * @return
     */
    private Long getDepartmentSort( List<DepartmentImportDTO> addRequestList ,final String parentDepartmentName,final int departmentGrade){
        return  addRequestList.stream().filter(v -> v.getParentDepartmentName().equals(parentDepartmentName) && v.getDepartmentGrade().equals(departmentGrade+1))
                .count();
    }

    /**
     * 验证EXCEL
     * @param workbook
     */
    private void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row firstRow = sheet1.getRow(0);
            Row secondRow = sheet1.getRow(1);
            if(!(firstRow.getCell(0).getStringCellValue().contains("填写须知：\n" +
                    "<1>上下级部门间用\"-\"隔开，且从最上级部门开始，例如\"市场部-杭州分部\"；\n" +
                    "<2>每级部门名称不可超过")) || !(secondRow.getCell(0).getStringCellValue().equals("部门名称"))){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010123);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010123);
        }
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
                    .resourceKey(Constants.DEPARTMENT_ERR_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }

    /**
     * 下载Excel错误文档
     *
     * @param userId 用户Id
     * @param ext    文件扩展名
     */
    public void downErrExcel(String userId, String ext) {
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.DEPARTMENT_ERR_EXCEL_DIR.concat(userId))
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
}
