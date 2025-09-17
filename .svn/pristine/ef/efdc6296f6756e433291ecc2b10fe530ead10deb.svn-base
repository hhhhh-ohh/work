package com.wanmi.sbc.empower.apppush.service.umeng;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.apppush.AppPushCancelRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSendRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushCancelResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSendResponse;
import com.wanmi.sbc.empower.apppush.service.AppPushBaseService;
import com.wanmi.sbc.empower.apppush.service.AppPushSettingService;
import com.wanmi.sbc.empower.apppush.service.umeng.bean.PushEntry;
import com.wanmi.sbc.empower.apppush.service.umeng.bean.PushResultEntry;
import com.wanmi.sbc.empower.apppush.service.umeng.bean.QueryEntry;
import com.wanmi.sbc.empower.apppush.service.umeng.bean.QueryResultEntry;
import com.wanmi.sbc.empower.bean.constant.AppPushErrorCode;
import com.wanmi.sbc.empower.bean.enums.AppPushAppType;
import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.AppPushSendResultVO;
import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component(AppPushPlatformType.BEAN_NAME_UMENG)
public class AppPushUmengService implements AppPushBaseService {

    @Autowired
    private AppPushSettingService appPushSettingService;
    @Autowired
    private UmengPushClient umengPushClient;

    @Override
    public BaseResponse<AppPushSendResponse> send(@Valid AppPushSendRequest request) {
        AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
        if(appPushSetting == null){
            log.error("友盟配置为空");
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060015);
        }
        PushEntry pushEntry = KsBeanUtil.copyPropertiesThird(request, PushEntry.class);
        List<PushResultEntry> pushResultEntrys = new ArrayList<>();

        //安卓：批量上传device_token
        String androidFileId = umengPushClient.uploadContents(appPushSetting.getAndroidKeyId(),
                appPushSetting.getAndroidKeySecret(), request.getAndroidTokenList());
        //发送安卓消息
        PushResultEntry androidResult = umengPushClient.sendAndroidFilecast(appPushSetting.getAndroidKeyId(),
                appPushSetting.getAndroidKeySecret(), pushEntry, androidFileId);
        if (androidResult != null){
            androidResult.setAppType(AppPushAppType.ANDROID);
            pushResultEntrys.add(androidResult);
        }
        //IOS：批量上传device_token
        String iosFileId = umengPushClient.uploadContents(appPushSetting.getIosKeyId(),
                appPushSetting.getIosKeySecret(), request.getIosTokenList());
        //发送IOS消息
        PushResultEntry iosResult = umengPushClient.sendIOSFilecast(appPushSetting.getIosKeyId(),
                appPushSetting.getIosKeySecret(),pushEntry, iosFileId);
        if (iosResult != null){
            iosResult.setAppType(AppPushAppType.IOS);
            pushResultEntrys.add(iosResult);
        }

        List<AppPushSendResultVO> appPushSendResultVOList = pushResultEntrys.stream()
                .map(pushResultEntry -> KsBeanUtil.copyPropertiesThird(pushResultEntry, AppPushSendResultVO.class))
                .collect(Collectors.toList());
        return BaseResponse.success(AppPushSendResponse.builder().dataList(appPushSendResultVOList).build());
    }

    @Override
    public BaseResponse<AppPushQueryResponse> query(@Valid AppPushQueryRequest request) {
        AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
        if(appPushSetting == null){
            log.error("友盟配置为空");
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060015);
        }
        String appKey = null;
        String appMasterSecret = null;
        if (AppPushAppType.IOS.equals(request.getType())){
            appKey = appPushSetting.getIosKeyId();
            appMasterSecret = appPushSetting.getIosKeySecret();
        } else {
            appKey = appPushSetting.getAndroidKeyId();
            appMasterSecret = appPushSetting.getAndroidKeySecret();
        }
        QueryEntry queryEntry = QueryEntry.builder()
                .key(appKey)
                .taskId(request.getTaskId())
                .appMasterSecret(appMasterSecret)
                .build();
        QueryResultEntry queryResultEntry = umengPushClient.query(queryEntry);
        return BaseResponse.success(KsBeanUtil.copyPropertiesThird(queryResultEntry, AppPushQueryResponse.class));
    }

    @Override
    public BaseResponse<AppPushCancelResponse> cancel(@Valid AppPushCancelRequest request) {
        AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
        if(appPushSetting == null){
            log.error("友盟配置为空");
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060015);
        }
        String appKey = null;
        String appMasterSecret = null;
        if (AppPushAppType.IOS.equals(request.getType())){
            appKey = appPushSetting.getIosKeyId();
            appMasterSecret = appPushSetting.getIosKeySecret();
        } else {
            appKey = appPushSetting.getAndroidKeyId();
            appMasterSecret = appPushSetting.getAndroidKeySecret();
        }
        QueryEntry queryEntry = QueryEntry.builder()
                .key(appKey)
                .taskId(request.getTaskId())
                .appMasterSecret(appMasterSecret)
                .build();
        QueryResultEntry queryResultEntry = umengPushClient.cancel(queryEntry);
        return BaseResponse.success(KsBeanUtil.copyPropertiesThird(queryResultEntry, AppPushCancelResponse.class));
    }
}
