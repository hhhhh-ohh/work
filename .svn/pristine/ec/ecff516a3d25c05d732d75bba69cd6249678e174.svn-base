package com.wanmi.sbc.popupadministration;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.popupadministration.PopupAdministrationProvider;
import com.wanmi.sbc.setting.api.provider.popupadministration.PopupAdministrationQueryProvider;
import com.wanmi.sbc.setting.api.request.popupadministration.*;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationDeleteResponse;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationPauseResponse;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationResponse;
import com.wanmi.sbc.setting.api.response.popupadministration.PopupAdministrationStartResponse;
import com.wanmi.sbc.setting.bean.vo.PopupAdministrationVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>弹窗管理服务API</p>
 * @author weiwenhao
 * @date 2020-04-23
 */
@Tag(name =  "弹窗增删改管理服务API", description =  "PopupAdministrationController")
@RestController
@Validated
@RequestMapping("/popup_administration")
public class PopupAdministrationController {


    @Autowired
    private PopupAdministrationProvider popupAdministrationProvider;

    @Autowired
    private PopupAdministrationQueryProvider popupAdministrationQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;


    /**
     * 新增弹窗
     * @param request
     * @return
     */
    @Operation(summary = "新增弹窗")
    @PostMapping("/add")
    public BaseResponse<PopupAdministrationResponse> add(@RequestBody @Valid PopupAdministrationRequest request){
        request.setCreatePerson(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getPopupUrl()) || StringUtils.isBlank(request.getPopupName())
                || StringUtils.isBlank(request.getLaunchFrequency()) || CollectionUtils.isEmpty(request.getApplicationPageName())) {
            throw  new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMQUtil.convertAndSend("新增弹窗", "新增弹窗", "新增弹窗:"+request.getPopupName());
        return popupAdministrationProvider.add(request);
    }

    /**
     * 编辑弹窗
     * @param request
     * @return
     */
    @Operation(summary = "编辑弹窗")
    @PostMapping("/modify")
    public BaseResponse<PopupAdministrationResponse> modify(@RequestBody @Valid PopupAdministrationModifyRequest request){
        request.setUpdatePerson(commonUtil.getOperatorId());
        if (StringUtils.isBlank(request.getPopupName()) || CollectionUtils.isEmpty(request.getApplicationPageName())
                || StringUtils.isBlank(request.getLaunchFrequency())) {
            throw  new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMQUtil.convertAndSend("编辑弹窗", "编辑弹窗", "编辑弹窗:"+request.getPopupName());
        return popupAdministrationProvider.modify(request);
    }

    /**
     * 删除弹窗
     * @param request
     * @return
     */
    @Operation(summary = "删除弹窗")
    @PostMapping("/delete")
    public BaseResponse<PopupAdministrationDeleteResponse> delete(@RequestBody @Valid PopupAdministrationDeleteRequest request){
        operateLogMQUtil.convertAndSend("删除弹窗", "删除弹窗", "删除弹窗:"+request.getPopupId());
        return popupAdministrationProvider.delete(request);
    }

    /**
     * 暂停弹窗
     * @param popupAdministrationId
     * @return
     */
     @Operation(summary = "暂停弹窗")
     @Parameter(name = "popupAdministrationId", description = "弹窗id", required = true)
     @GetMapping("/pause_popup_administration/{popupAdministrationId}")
     public BaseResponse<PopupAdministrationPauseResponse> pausePopupAdministration(@PathVariable("popupAdministrationId") Long popupAdministrationId){
         if(Objects.isNull(popupAdministrationId)){
             throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
         }
         PopupAdministrationVO popupAdministrationVO = popupAdministrationQueryProvider.getPopupAdministrationById(PageManagementGetIdRequest.builder()
                 .popupId(popupAdministrationId).build()).getContext().getPopupAdministrationVO();
         if(Objects.isNull(popupAdministrationVO) || popupAdministrationVO.getDelFlag() == DeleteFlag.YES){
             throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
         }
         PopupAdministrationPauseRequest popupAdministrationPuase=
                 new PopupAdministrationPauseRequest(popupAdministrationId);
         operateLogMQUtil.convertAndSend("暂停弹窗", "暂停弹窗", "暂停弹窗:"+popupAdministrationId);
         return popupAdministrationProvider.pausePopupAdministration(popupAdministrationPuase);
     }


    /**
     * 启动弹窗
     * @param popupAdministrationId
     * @return
     */
    @Operation(summary = "启动弹窗")
    @Parameter(name = "popupAdministrationId", description = "弹窗id", required = true)
    @GetMapping("/start_popup_administration/{popupAdministrationId}")
    public BaseResponse<PopupAdministrationStartResponse> startPopupAdministration(@PathVariable("popupAdministrationId") Long popupAdministrationId){
        if(Objects.isNull(popupAdministrationId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PopupAdministrationVO popupAdministrationVO = popupAdministrationQueryProvider.getPopupAdministrationById(PageManagementGetIdRequest.builder()
                .popupId(popupAdministrationId).build()).getContext().getPopupAdministrationVO();
        if(Objects.isNull(popupAdministrationVO) || popupAdministrationVO.getDelFlag() == DeleteFlag.YES){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PopupAdministrationStartRequest popupAdministrationStartRequest=new PopupAdministrationStartRequest(popupAdministrationId);
        operateLogMQUtil.convertAndSend("启动弹窗", "启动弹窗", "启动弹窗:"+popupAdministrationId);
        return popupAdministrationProvider.startPopupAdministration(popupAdministrationStartRequest);
    }

    /**
     * 弹窗在应用页面的排序
     * @param request
     * @return
     */
    @Operation(summary = "弹窗在应用页面的排序")
    @PostMapping("/sort_popup_administration")
    public BaseResponse sortPopupAdministration(@RequestBody @Valid List<PopupAdministrationSortRequest> request){
        operateLogMQUtil.convertAndSend("弹窗在应用页面的排序", "弹窗在应用页面的排序", "弹窗在应用页面的排序:"+request);
        return popupAdministrationProvider.sortPopupAdministration(request);
    }

}
