package com.wanmi.sbc.setting;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.request.yunservice.CreateSentinelConfigRequest;
import com.wanmi.sbc.setting.api.response.BossGoodsAuditResponse;
import com.wanmi.sbc.setting.api.response.CreateSentinelConfigResponse;
import com.wanmi.sbc.setting.api.response.baseconfig.BossMarketingMutexResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ClusterConfigVO;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.SentinelConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄昭
 * @className ConfigController
 * @description TODO
 * @date 2021/12/27 15:17
 **/
@Tag(name = "ConfigController", description = "商品设置 Api")
@RestController
@Validated
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    /**
     * 是否开启商家/供应商二次审核
     *
     * @return
     */
    @Operation(summary = "是否开启商家/供应商二次审核")
    @RequestMapping(value = "/secondary/audit", method = RequestMethod.POST)
    public BaseResponse<BossGoodsAuditResponse> isBossSecondaryAudit(@RequestBody @Valid GoodsSecondaryAuditRequest request) {

        return auditQueryProvider.isBossGoodsSecondaryAudit(request);
    }


    @Operation(summary = "生成接口限流配置文件")
    @RequestMapping(value = "/create/sentinelConfigure", method = RequestMethod.POST)
    public BaseResponse<CreateSentinelConfigResponse> createSentinelConfigure(@RequestBody @Valid CreateSentinelConfigRequest request) {
        List<SentinelConfigVO> dataList = new ArrayList<>();
        List<String> interfaceList = request.getInterfaceList();
        List<String> countList = request.getCountList();
        List<String> flowIdList = request.getFlowIdList();
        if (CollectionUtils.isEmpty(interfaceList) ||
                CollectionUtils.isEmpty(countList) || CollectionUtils.isEmpty(flowIdList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (interfaceList.size() != countList.size() || interfaceList.size() != flowIdList.size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        for (int i = 0;i<interfaceList.size();i++){
            SentinelConfigVO sentinelConfigVO = new SentinelConfigVO();
            sentinelConfigVO.setResource(interfaceList.get(i));
            sentinelConfigVO.setCount(Long.parseLong(countList.get(i)));
            ClusterConfigVO clusterConfigVO = new ClusterConfigVO();
            clusterConfigVO.setFlowId(Long.parseLong(flowIdList.get(i)));
            sentinelConfigVO.setClusterConfig(clusterConfigVO);
            dataList.add(sentinelConfigVO);
        }
        return BaseResponse.success(CreateSentinelConfigResponse.builder()
                .configInfo(JSONArray.parseArray(JSONObject.toJSONString(dataList))).build());
    }

    /**
     * 是否开启商家/供应商二次审核
     *
     * @return
     */
    @Operation(summary = "是否开启活动互斥")
    @GetMapping("/marketing/mutex")
    public BaseResponse<BossMarketingMutexResponse> isMarketingMutex() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        BossMarketingMutexResponse response = new BossMarketingMutexResponse();
        response.setStatus(Constants.no);
        //营销互斥不验证标识
        if (configVO != null) {
            response.setStatus(configVO.getStatus());
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "查询高德地图设置")
    @GetMapping("/map/config-show")
    public BaseResponse<PickupSettingConfigResponse> whetherOpenMap() {
        return pickupSettingQueryProvider.getWhetherOpenMap();
    }
}