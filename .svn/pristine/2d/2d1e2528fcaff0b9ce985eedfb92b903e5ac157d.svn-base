package com.wanmi.sbc.department;

import com.wanmi.sbc.aop.DepartmentIsolation;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.department.DepartmentProvider;
import com.wanmi.sbc.customer.api.provider.department.DepartmentQueryProvider;
import com.wanmi.sbc.customer.api.request.department.DepartmentAddRequest;
import com.wanmi.sbc.customer.api.request.department.DepartmentDelByIdRequest;
import com.wanmi.sbc.customer.api.request.department.DepartmentListRequest;
import com.wanmi.sbc.customer.api.request.department.DepartmentModifyLeaderRequest;
import com.wanmi.sbc.customer.api.request.department.DepartmentModifyRequest;
import com.wanmi.sbc.customer.api.request.department.DepartmentSortRequest;
import com.wanmi.sbc.customer.api.response.department.DepartmentAddResponse;
import com.wanmi.sbc.customer.api.response.department.DepartmentByIdResponse;
import com.wanmi.sbc.customer.api.response.department.DepartmentListResponse;
import com.wanmi.sbc.customer.api.response.department.DepartmentModifyResponse;
import com.wanmi.sbc.customer.bean.vo.DepartmentVO;
import com.wanmi.sbc.department.request.DepartmentExcelImportRequest;
import com.wanmi.sbc.department.service.DepartmentExcelService;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeModifyLeaderByIdRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Tag(name =  "部门管理管理API", description =  "DepartmentController")
@RestController
@Validated
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentQueryProvider departmentQueryProvider;

    @Autowired
    private DepartmentProvider departmentSaveProvider;

    @Autowired
    private DepartmentExcelService departmentExcelService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    @Operation(summary = "列表查询部门管理-数据隔离")
    @DepartmentIsolation
    @PostMapping("/get-department-tree")
    public BaseResponse<DepartmentListResponse> getDepartmentTree(@RequestBody @Valid DepartmentListRequest listReq) {
        listReq.setCompanyInfoId(Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        listReq.setDelFlag(DeleteFlag.NO.toValue());
        listReq.putSort("departmentGrade", SortType.ASC.toValue());
        listReq.putSort("departmentSort", SortType.ASC.toValue());
        return listReq.getBelongToDepartment() ? departmentQueryProvider.listDepartmentTree(listReq) : BaseResponse.success(new DepartmentListResponse(Collections.EMPTY_LIST,Collections.EMPTY_LIST,Collections.EMPTY_LIST,listReq.getIsMaster()));
    }

    @Operation(summary = "列表查询部门管理")
    @PostMapping("/list-department-tree")
    public BaseResponse<DepartmentListResponse> listDepartmentTree(@RequestBody @Valid DepartmentListRequest listReq) {
        listReq.setCompanyInfoId(Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        listReq.setDelFlag(DeleteFlag.NO.toValue());
        listReq.putSort("departmentGrade", SortType.ASC.toValue());
        listReq.putSort("departmentSort", SortType.ASC.toValue());
        return departmentQueryProvider.listDepartmentTree(listReq);
    }

    @Operation(summary = "新增部门管理")
    @PostMapping("/add")
    public BaseResponse<DepartmentAddResponse> add(@RequestBody @Valid DepartmentAddRequest addReq) {
        addReq.setCompanyInfoId(Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        BaseResponse<DepartmentAddResponse> baseResponse = departmentSaveProvider.add(addReq);
        operateLogMQUtil.convertAndSend("部门", "新增部门", "新增部门：" + addReq.getDepartmentName());
        return baseResponse;
    }

    @Operation(summary = "修改部门管理")
    @PutMapping("/modifyDepartmentName")
    public BaseResponse<DepartmentModifyResponse> modifyDepartmentName(@RequestBody @Valid DepartmentModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        BaseResponse<DepartmentModifyResponse> baseResponse = departmentSaveProvider.modifyDepartmentName(modifyReq);
        commonUtil.checkCompanyInfoId(baseResponse.getContext().getDepartmentVO().getCompanyInfoId());
        operateLogMQUtil.convertAndSend("部门", "修改部门", "新修改部门：" + modifyReq.getDepartmentName());
        return baseResponse;
    }

    @Operation(summary = "拖拽排序")
    @PutMapping("/sort")
    public BaseResponse sort(@RequestBody @Valid DepartmentSortRequest sortRequest) {
        BaseResponse baseResponse = departmentSaveProvider.sort(sortRequest);
        operateLogMQUtil.convertAndSend("部门", "修改部门排序", "拖拽排序");
        return baseResponse;
    }


    @Operation(summary = "根据id删除部门管理")
    @DeleteMapping("/{departmentId}")
    public BaseResponse deleteById(@PathVariable String departmentId) {
        if (departmentId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DepartmentDelByIdRequest delByIdReq = new DepartmentDelByIdRequest();
        delByIdReq.setDepartmentId(departmentId);
        //越权校验
        DepartmentListResponse response = departmentQueryProvider.listDepartment(DepartmentListRequest.builder()
                .departmentIsolationIdList(Collections.singletonList(departmentId))
                .build())
                .getContext();
        Long companyInfoId = null;
        if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getDepartmentVOS())) {
            companyInfoId  = response.getDepartmentVOS().get(0).getCompanyInfoId();
        }
        commonUtil.checkCompanyInfoId(companyInfoId);
        BaseResponse<DepartmentByIdResponse> baseResponse = departmentSaveProvider.deleteById(delByIdReq);
        operateLogMQUtil.convertAndSend("部门", "删除部门", "删除部门:" + baseResponse.getContext().getDepartmentVO().getDepartmentName());

        return baseResponse;
    }


    @Operation(summary = "设置部门主管")
    @PutMapping("/modify-leader")
    public BaseResponse modifyLeader(@RequestBody @Valid DepartmentModifyLeaderRequest request) {
        if(StringUtils.equals(request.getOldEmployeeId(), request.getNewEmployeeId())) {
            operateLogMQUtil.convertAndSend("部门", "设置部门主管", "部门主管修改无变化, ID:" + request.getNewEmployeeId());
            return BaseResponse.SUCCESSFUL();
        }
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        BaseResponse baseResponse = departmentSaveProvider.modifyLeader(request);
        EsEmployeeModifyLeaderByIdRequest modifyLeaderByIdRequest =
                KsBeanUtil.copyPropertiesThird(request, EsEmployeeModifyLeaderByIdRequest.class);
        modifyLeaderByIdRequest.setIsLeader(NumberUtils.INTEGER_ONE);
        esEmployeeProvider.modifyLeaderById(modifyLeaderByIdRequest);
        operateLogMQUtil.convertAndSend("部门", "设置部门主管", "新主管ID:" + request.getNewEmployeeId());

        return baseResponse;
    }



    /**
     * 下载部门导入模板
     */
    @Operation(summary = "下载部门导入模板")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        String file = departmentQueryProvider.exportTemplate().getContext().getFile();
        if (StringUtils.isNotBlank(file)) {
            try {
                String fileName = URLEncoder.encode("部门导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                log.error("导出部门模板文件内容解码失败", e);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        DepartmentListRequest listReq = new DepartmentListRequest();
        listReq.setCompanyInfoId(Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        listReq.setDelFlag(DeleteFlag.NO.toValue());
        List<DepartmentVO> existList = departmentQueryProvider.listDepartment(listReq).getContext().getDepartmentVOS();
        if (CollectionUtils.isNotEmpty(existList)) {
            // 若部门已存在，不允许通过excel批量初始化
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "部门已存在，不允许通过excel批量初始化");
        }
        return BaseResponse.success(departmentExcelService.upload(uploadFile, commonUtil.getOperatorId()));
    }

    /**
     * 确认导入部门
     *
     * @param ext 文件格式 {@link String}
     * @return
     */
    @Operation(summary = "确认导入部门")
    @Parameter(name = "ext", description = "文件名后缀", required = true)
    @RequestMapping(value = "/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        DepartmentExcelImportRequest standardExcelImplGoodsRequest = new DepartmentExcelImportRequest();
        standardExcelImplGoodsRequest.setExt(ext);
        standardExcelImplGoodsRequest.setCompanyInfoId(Objects.nonNull(commonUtil.getCompanyInfoId()) ? commonUtil.getCompanyInfoId() : 0L);
        standardExcelImplGoodsRequest.setUserId(commonUtil.getOperatorId());
        departmentExcelService.importDepartment(standardExcelImplGoodsRequest);
        //操作日志记录
        operateLogMQUtil.convertAndSend("部门", "批量导入", "批量导入");
        return BaseResponse.success(Boolean.TRUE);
    }


    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @RequestMapping(value = "/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        departmentExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }
}
