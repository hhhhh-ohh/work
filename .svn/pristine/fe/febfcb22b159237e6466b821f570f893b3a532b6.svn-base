package com.wanmi.sbc.pickupsetting;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.request.store.ClosePickupSettingRequest;
import com.wanmi.sbc.customer.bean.enums.CompanyType;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingProvider;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.*;
import com.wanmi.sbc.setting.api.request.ConfigRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingListResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingPageResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.PickupEmployeeRelaVO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


@Tag(name =  "pickup_setting管理API", description =  "PickupSettingController")
@RestController
@Validated
@RequestMapping(value = "/pickupsetting")
public class PickupSettingController {

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired
    private PickupSettingProvider pickupSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PickupSettingService pickupSettingService;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private StoreProvider storeProvider;


    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public BaseResponse<PickupSettingPageResponse> getPage(@RequestBody @Valid PickupSettingPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");

        if (StringUtils.isNotBlank(pageReq.getPhone())){
            if (pageReq.getPhone().startsWith("(")){
                String[] split = pageReq.getPhone().split("\\)");
                pageReq.setAreaCode(split[0].substring(1));
                pageReq.setPhone(split[1]);
            }else if (pageReq.getPhone().startsWith("（")){
                String[] split = pageReq.getPhone().split("）");
                pageReq.setAreaCode(split[0].substring(1));
                pageReq.setPhone(split[1]);
            }else if(pageReq.getPhone().contains("-")){
                String[] split = pageReq.getPhone().split("-");
                pageReq.setAreaCode(split[0]);
                pageReq.setPhone(split[1]);
            }
        }

        BaseResponse<PickupSettingPageResponse> page = pickupSettingQueryProvider.page(pageReq);
        if (page.getContext().getPickupSettingVOS() != null) {
            List<PickupSettingVO> content = page.getContext().getPickupSettingVOS().getContent();
            if (CollectionUtils.isNotEmpty(content)) {
                pickupSettingService.getInfo(content);
                pickupSettingService.phoneDesensitization(content);
            }
        }
        return page;
    }

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<PickupSettingListResponse> getList(@RequestBody @Valid PickupSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return pickupSettingQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public BaseResponse<PickupSettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PickupSettingByIdRequest idReq = new PickupSettingByIdRequest();
        idReq.setId(id);
        PickupSettingByIdResponse response = pickupSettingQueryProvider.getById(idReq).getContext();
        pickupSettingService.getInfo(Collections.singletonList(response.getPickupSettingVO()));
        List<PickupEmployeeRelaVO> vos = pickupSettingService.getEmployeeInfo(response.getPickupEmployeeRelaVOS());
        response.setPickupEmployeeRelaVOS(vos);
        return BaseResponse.success(response);
    }

    @Operation(summary = "导出pickup_setting列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        Operator operator = commonUtil.getOperator();

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.PICKUP_SETTING_INFO);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();

    }

    @Operation(summary = "自提点审核")
    @PostMapping("/audit")
    public BaseResponse pickupSettingAudit(@RequestBody @Valid PickupSettingAuditRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());

        BaseResponse baseResponse = pickupSettingProvider.pickupSettingAudit(request);

        operateLogMqUtil.convertAndSend("设置", "自提点审核", "自提点审核");

        return baseResponse;
    }

    @Operation(summary = "自提点启停用")
    @PostMapping("/status")
    public BaseResponse pickupSettingStatus(@RequestBody @Valid PickupSettingAuditRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());

        BaseResponse baseResponse = pickupSettingProvider.pickupSettingAudit(request);

        operateLogMqUtil.convertAndSend("设置", "自提点启/停用", "自提点启/停用");

        return baseResponse;
    }

    @Operation(summary = "自提点设置")
    @PostMapping("/config")
    @GlobalTransactional
    public BaseResponse pickupSettingConfig(@RequestBody @Valid ConfigRequest request) {
        request.setUpdatePerson(commonUtil.getOperator().getUserId());

        operateLogMqUtil.convertAndSend("设置", "自提点开关", "自提点开关");

        pickupSettingProvider.pickupSettingConfig(request);

        if (EnableStatus.DISABLE.toValue() == request.getStatus()){
            //关闭商家自提设置
            if (ConfigType.SELF_MERCHANT.toValue().equals(request.getConfigType())){
                storeProvider.closePickupSetting(ClosePickupSettingRequest.builder().companyType(CompanyType.PLATFORM.toValue()).build());
            }else if (ConfigType.THIRD_MERCHANT.toValue().equals(request.getConfigType())){
                storeProvider.closePickupSetting(ClosePickupSettingRequest.builder().companyType(CompanyType.SUPPLIER.toValue()).build());
            }else if (ConfigType.STORE.toValue().equals(request.getConfigType())){
                //关闭门店自提
                storeProvider.closePickupSetting(ClosePickupSettingRequest.builder().storeType(StoreType.O2O.toValue()).build());
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询自提订单设置")
    @GetMapping("/config-show")
    public BaseResponse<PickupSettingConfigResponse> pickupSettingConfigShow() {
        return pickupSettingQueryProvider.pickupSettingConfigShow();
    }


    @Operation(summary = "修改高德地图设置")
    @PostMapping("/modify/map/config")
    @GlobalTransactional
    public BaseResponse modifyMapSetting(@RequestBody @Valid ConfigRequest request) {
        pickupSettingProvider.modifyMapSetting(request);
        if(request.getStatus()>1){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.SUCCESSFUL();
    }


}
