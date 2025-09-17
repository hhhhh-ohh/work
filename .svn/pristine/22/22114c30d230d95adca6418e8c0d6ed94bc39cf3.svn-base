package com.wanmi.sbc.customer.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.api.provider.department.DepartmentQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.RoleInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.department.DepartmentListRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeImportRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeJobNoExistsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeMobileExistsRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoListRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeImportResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeMobileExistsResponse;
import com.wanmi.sbc.customer.api.response.employee.RoleInfoListResponse;
import com.wanmi.sbc.customer.bean.dto.EmployeeDTO;
import com.wanmi.sbc.customer.bean.enums.GenderType;
import com.wanmi.sbc.customer.bean.vo.DepartmentVO;
import com.wanmi.sbc.customer.bean.vo.RoleInfoVO;
import com.wanmi.sbc.customer.request.EmployeeExcelImportRequest;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeImportRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 员工Excel处理服务
 */
@Slf4j
@Service
public class EmployeeExcelService {

    @Autowired
    private DepartmentQueryProvider departmentQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RoleInfoQueryProvider roleInfoQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;


    /**
     * 导入模版
     * @param employeeExcelImportRequest
     */
    @GlobalTransactional
    public void importEmployee(EmployeeExcelImportRequest employeeExcelImportRequest){
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.EMPLOYEE_EXCEL_DIR.concat(employeeExcelImportRequest.getUserId()))
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        /*if (content.length > Constants.IMPORT_GOODS_MAX_SIZE * 1024 * 1024) {
            throw new SbcRuntimeException(GoodsImportErrorCode.FILE_MAX_SIZE, new Object[]{Constants.IMPORT_GOODS_MAX_SIZE});
        }*/
        if (content.length > Constants.NUM_30 * 1024 * 1024) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030064, new Object[]{Constants.IMPORT_GOODS_MAX_SIZE});
        }
        Map<String, String> departments = this.getDepartments();
        List<RoleInfoVO> roleInfos = this.getRoles();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            //检测文档正确性
            this.checkExcel(workbook);
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int maxCell = 8;
            List<EmployeeDTO> dtoArrayList = new ArrayList<>();
            //循环除了第一行的所有行
            for (int rowNum = 2; rowNum <= lastRowNum; rowNum++) {
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
                EmployeeDTO employeeDTO = new EmployeeDTO();
                //用户名
                String employeeName =  ExcelHelper.getValue(getCell(cells, 0));
                employeeDTO.setEmployeeName(employeeName);
                //手机号
                String employeeMobile = ExcelHelper.getValue(getCell(cells, 1));
                employeeDTO.setEmployeeMobile(employeeMobile);
                //工号
                String jobNo = ExcelHelper.getValue(getCell(cells, 2));
                employeeDTO.setJobNo(jobNo);
                //邮箱
                String email = ExcelHelper.getValue(getCell(cells, 3));
                employeeDTO.setEmail(email);
                //部门
                String nameStr = ExcelHelper.getValue(getCell(cells, 4));
                String idList = String.join(",", dealDepartmentName(Arrays.asList(nameStr.split(",")), departments));
                employeeDTO.setDepartmentIds(idList);
                //岗位
                String position = ExcelHelper.getValue(getCell(cells, 5));
                employeeDTO.setPosition(position);
                //角色
                String roles = ExcelHelper.getValue(getCell(cells, 6));

                if(StringUtils.isNotBlank(roles)){
                    List<String> roleNameList = Arrays.asList(roles.split(","));
                    String ids = this.dealRoleName(roleNameList, roleInfos)
                            .stream().map(Object::toString)
                            .collect(Collectors.joining(","));
                    employeeDTO.setRoleIds(ids);
                }
                employeeDTO.setIsEmployee(1);
                employeeDTO.setSex(GenderType.SECRET);
                employeeDTO.setCompanyInfoId(employeeExcelImportRequest.getCompanyInfoId());
                employeeDTO.setAccountType(employeeExcelImportRequest.getAccountType());
                dtoArrayList.add(employeeDTO);
            }
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(dtoArrayList.size());
            List<List<EmployeeDTO>> splitList = IterableUtils.splitList(dtoArrayList, maxSize);
            //导入
            this.importEmployeeAsync(splitList);
        }catch (SbcRuntimeException e) {
            log.error("部门导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("部门导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 异步编排导入,第二个线程需要依赖第一个线程的结果
     * @param splitList
     * @return
     */
    private void importEmployeeAsync(List<List<EmployeeDTO>> splitList){
        ExecutorService executor = this.newThreadPoolExecutor();
        List<CompletableFuture<List<EmployeeImportResponse>>> futureList = splitList.stream()
                .map(employeeDTOList -> CompletableFuture.supplyAsync(() -> this.importEmployee(employeeDTOList), executor))
                .peek(f -> f.thenAccept(this::importEsEmployee))
                .collect(Collectors.toList());
        //主进程等待线程执行结束
        futureList.forEach(CompletableFuture::join);
        //关闭线程池
        executor.shutdown();
    }


    /**
     * 导入数据到employee表
     * @param employeeDTOList
     * @return
     */
    private List<EmployeeImportResponse> importEmployee(List<EmployeeDTO> employeeDTOList){
        if(CollectionUtils.isEmpty(employeeDTOList)){
            return Collections.emptyList();
        }
        EmployeeImportRequest request = new EmployeeImportRequest(employeeDTOList);
        return employeeProvider.importEmployee(request).getContext();
    }

    /**
     * 入es
     * @param result
     */
    private void importEsEmployee(List<EmployeeImportResponse> result){
        List<EsEmployeeSaveRequest> employeeSaveRequests =
                KsBeanUtil.convertList(result, EsEmployeeSaveRequest.class);
        EsEmployeeImportRequest request = EsEmployeeImportRequest.builder()
                .employeeList(employeeSaveRequests)
                .build();
        esEmployeeProvider.importEmployee(request);
    }

    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("员工导入-%d").build();
        return new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(50), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }



    /**
     * 验证EXCEL
     * @param workbook
     */
    private void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row firstRow = sheet1.getRow(0);
            if(!(firstRow.getCell(0).getStringCellValue().contains("填写须知：\n" +
                    "<1>红色字段为必填字段，黑色字段为选填字段；\n" +
                    "<2>部门：请先到组织架构-部门管理添加好部门，再到本excel里填入，多部门用英文的“,”隔开，部门未填写时，归于全部部门；\n" +
                    "<3>角色：请先到组织架构-角色权限添加好角色，再到本excel里填入，多角色用英文的“,”隔开；"))){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
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
        String userId = commonUtil.getOperator().getUserId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(Constants.EMPLOYEE_ERR_EXCEL_DIR.concat(userId))
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
     * @param userId 用户Id
     * @param ext 文件扩展名
     */
    public void downErrExcel(String userId, String ext){
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.EMPLOYEE_ERR_EXCEL_DIR.concat(userId))
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
     * @param file 文件
     * @param request {@link EmployeeExcelImportRequest}
     * @return 文件格式
     */
    public String upload(MultipartFile file,EmployeeExcelImportRequest request){
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        /*if (file.getSize() > Constants.IMPORT_GOODS_MAX_SIZE * 1024 * 1024) {
            throw new SbcRuntimeException(GoodsImportErrorCode.FILE_MAX_SIZE, new Object[]{Constants.IMPORT_GOODS_MAX_SIZE});
        }*/
        String resourceKey = Constants.EMPLOYEE_EXCEL_DIR.concat(request.getUserId());
        Map<String, String> departments = this.getDepartments();
        List<RoleInfoVO> roleInfos = this.getRoles();
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
            if(lastRowNum > Constants.NUM_10000){
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_10000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过10000条，请修改");
                }
            }
            int maxCell = 8;
            boolean isError = false;
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
                //用户名
                String employeeName =  ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isBlank(employeeName)) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "此项必填",style);
                    isError = true;
                }  else if (employeeName.length() > 20) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "用户名长度过长",style);
                    isError = true;
                }
                //手机号
                String employeeMobile = ExcelHelper.getValue(getCell(cells, 1));
                if (StringUtils.isBlank(employeeMobile)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "此项必填",style);
                    isError = true;
                }  else if (!ValidateUtil.isPhone(employeeMobile)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "请输入正确的手机号",style);
                    isError = true;
                } else {
                    EmployeeMobileExistsResponse mobileExistsResponse = mobileIsExists(employeeMobile, request.getAccountType(), request.getCompanyInfoId());
                    if (mobileExistsResponse.isExists()) {
                        // 区分存在于其他商家，还是存在于当前商家
                        String errMsg = BooleanUtils.isTrue(mobileExistsResponse.getInOtherCompanyFlagIfPresent())
                                ? "此账户已在其他商家存在" : "此账户已存在";
                        ExcelHelper.setError(workbook, getCell(cells, 1), errMsg, style);
                        isError = true;
                    }
                }
                //工号
                String jobNo = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isNotBlank(jobNo)) {
                    if (jobNo.length() > 20) {
                        ExcelHelper.setError(workbook, getCell(cells, 2), "工号长度过长",style);
                        isError = true;
                    }else if(jobNoIsExists(jobNo, request.getAccountType(),
                            request.getCompanyInfoId())){
                        ExcelHelper.setError(workbook, getCell(cells, 2), "工号已被占用",style);
                        isError = true;
                    }
                }

                //邮箱
                String email = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(email)){
                    if (!SensitiveUtils.isEmail(email)) {
                        ExcelHelper.setError(workbook, getCell(cells, 3), "请输入正确的邮箱",style);
                        isError = true;
                    }
                }
                //部门
                String nameStr = ExcelHelper.getValue(getCell(cells, 4));
                if(StringUtils.isNotEmpty(nameStr)){
                    if(!checkDepartmentIsExist(Arrays.asList(nameStr.split(",")), departments)){
                        ExcelHelper.setError(workbook, getCell(cells, 4), "部门不存在",style);
                        isError = true;
                    }
                }

                //岗位
                String position = ExcelHelper.getValue(getCell(cells, 5));
                if (position.length() > 20) {
                    ExcelHelper.setError(workbook, getCell(cells, 5), "岗位长度过长",style);
                    isError = true;
                }

                //角色
                String roles = ExcelHelper.getValue(getCell(cells, 6));
                if(StringUtils.isNotBlank(roles)){
                    List<String> roleNameList = Arrays.asList(roles.split(","));
                    if(!checkRoleIsExist(roleNameList, roleInfos)){
                        ExcelHelper.setError(workbook, getCell(cells, 6), "角色不存在",style);
                        isError = true;
                    }
                }
            }

            if (isError) {
                errorExcel(request.getUserId().concat(".").concat(fileExt), workbook);
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
        }catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (SbcRuntimeException e) {
            log.error("部门导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("部门导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return fileExt;
        
    }

    /**
     * 获取部门列表
     * @return
     */
    public Map<String, String> getDepartments(){
        Map<String, String> map = new HashMap<>();
        DepartmentListRequest request = new DepartmentListRequest();
        boolean isO2o = commonUtil.getOperator().getPlatform() == Platform.STOREFRONT;
        request.setCompanyInfoId(isO2o ? -1L : Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        request.setDelFlag(DeleteFlag.NO.toValue());
        request.putSort("departmentGrade", SortType.ASC.toValue());
        request.putSort("departmentSort", SortType.ASC.toValue());
        request.putSort("createTime", SortType.ASC.toValue());
        List<DepartmentVO> departmentVOS = departmentQueryProvider.listDepartmentTree(request).getContext().getDepartmentVOS();
        if(CollectionUtils.isNotEmpty(departmentVOS)){
            departmentVOS.stream().forEach(departmentVO -> {
                String[] parentIds = departmentVO.getParentDepartmentIds().split("\\|");

                String name = Arrays.asList(parentIds).stream().filter(id -> !id.equals("0")).map(id -> {
                    String departmentName = departmentVOS.stream()
                            .filter(d -> d.getDepartmentId().equals(id))
                            .findFirst().get().getDepartmentName();
                    return departmentName;
                }).collect(Collectors.joining("-"));
                if(StringUtils.isNotEmpty(name)){
                    name = name.concat("-").concat(departmentVO.getDepartmentName());
                }else{
                    name = departmentVO.getDepartmentName();
                }
                map.put(departmentVO.getDepartmentId(), name);
            });
        }
        return map;
    }

    /**
     * 获取角色列表
     * @return
     */
    public List<RoleInfoVO> getRoles(){
        List<RoleInfoVO> list = new ArrayList<>();
        RoleInfoListRequest request = new RoleInfoListRequest();
        Long companyInfoId = null;
        if(commonUtil.getOperator().getPlatform() == Platform.STOREFRONT){
            companyInfoId = -1L;
        }else {
            companyInfoId = commonUtil.getCompanyInfoId();
        }
        request.setCompanyInfoId(companyInfoId);
        RoleInfoListResponse response = roleInfoQueryProvider.listByCompanyInfoId(request).getContext();
        if(Objects.nonNull(response)){
            list = response.getRoleInfoVOList();
        }
        return list;
    }

    /**
     * 校验部门是否存在
     * @param departmentNames
     * @param departments
     * @return
     */
    private boolean checkDepartmentIsExist(List<String> departmentNames, Map<String, String> departments){
        Map<String, String> map = new HashMap<>();
        if(departments.size() > 0){
            map = departmentNames.stream().filter(name -> departments.containsValue(name))
                    .collect(Collectors.toMap(String::toString, String::toString));
        }
        if(map.size() != departmentNames.size()){
            return false;
        }
        return true;
    }

    /**
     * 校验角色是否存在
     * @param roleNames
     * @param roleInfoVOS
     * @return
     */
    private boolean checkRoleIsExist(List<String> roleNames, List<RoleInfoVO> roleInfoVOS){
        List<Long> ids = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleInfoVOS)){
             ids = this.dealRoleName(roleNames, roleInfoVOS);
        }
        if(ids.size() != roleNames.size()){
            return false;
        }
        return true;
    }

    /**
     * 查找所属部门
     * @param departmentNames
     * @param departments
     * @return
     */
    private List<String> dealDepartmentName(List<String> departmentNames, Map<String, String> departments) {
        List<String> ids = departmentNames.stream().filter(StringUtils::isNotBlank)
                .map(name -> departments.entrySet().stream().filter(entry -> entry.getValue().equals(name))
                        .map(Map.Entry::getKey)
                        .findFirst().orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
        return ids;
    }

    /**
     *获取指定名称的角色id
     * @param roleNames
     * @param roleInfoVOS
     * @return
     */
    private List<Long> dealRoleName(List<String> roleNames, List<RoleInfoVO> roleInfoVOS){
        List<Long> roleIds = roleInfoVOS.stream()
                .filter(roleInfoVO -> roleNames.contains(roleInfoVO.getRoleName()))
                .map(RoleInfoVO::getRoleInfoId).collect(Collectors.toList());
        return roleIds;
    }

    /**
     * 校验工号是否存在
     * @param jobNo
     * @param accountType
     * @return
     */
    private boolean jobNoIsExists(String jobNo, AccountType accountType, Long companyInfoId){
        EmployeeJobNoExistsRequest request = EmployeeJobNoExistsRequest.builder()
                .jobNo(jobNo)
                .accountType(accountType)
                .companyInfoId(companyInfoId)
                .build();
        return employeeQueryProvider.jobNoIsExist(request).getContext().getExists();
    }

    /**
     * 校验手机号是否存在
     * @param mobile
     * @param accountType
     * @return
     */
    private EmployeeMobileExistsResponse mobileIsExists(String mobile, AccountType accountType, Long companyInfoId) {
        EmployeeMobileExistsRequest request = EmployeeMobileExistsRequest.builder()
                .mobile(mobile)
                .accountType(accountType)
                .companyInfoId(companyInfoId)
                .build();
        return employeeQueryProvider.mobileIsExists(request).getContext();
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }
}
