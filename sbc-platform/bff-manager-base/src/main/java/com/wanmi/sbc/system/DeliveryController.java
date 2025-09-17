package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.logisticssetting.ExpressQueryProvider;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingProvider;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.logisticssetting.DeliveryQueryRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingAddRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingByLogisticsTypeRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingModifyRequest;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import com.wanmi.sbc.setting.api.request.systemconfig.LogisticsSaveRopRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.LogisticsRopResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by CHENLI on 2017/5/24.
 */
@Tag(name = "DeliveryController", description = "发货信息 Api")
@RestController
@Validated
public class DeliveryController {

    @Autowired
    private ExpressQueryProvider expressQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private LogisticsSettingProvider logisticsSettingProvider;

    @Autowired
    private LogisticsSettingQueryProvider logisticsSettingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 根据快递公司及快递单号查询物流详情
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "根据快递公司及快递单号查询物流详情，物流信息")
    @RequestMapping(value = "/deliveryInfos", method = RequestMethod.POST)
    public ResponseEntity<List<Map<Object, Object>>> queryExpressInfoUrl(@RequestBody DeliveryQueryRequest queryRequest) {
        return ResponseEntity.ok(expressQueryProvider.expressInfoQuery(queryRequest).getContext().getOrderList());
    }

    /**
     * 查询快递100信息
     *
     * @return
     */
    @Operation(summary = "查询快递配置信息")
    @RequestMapping(value = "/deliveryInfo", method = RequestMethod.GET)
    public ResponseEntity<LogisticsRopResponse> queryDelivery() {
        // 因为目前暂时只对接了快递100, 因此快递类型此处固定, 后期对接其他快递方式, 需要由前端传递物流类型进来
        LogisticsType logisticsType = LogisticsType.KUAI_DI_100;
        LogisticsSettingVO logisticsSettingVO
                = logisticsSettingQueryProvider.getByLogisticsType(
                LogisticsSettingByLogisticsTypeRequest.builder().logisticsType(logisticsType).build()
        ).getContext().getLogisticsSettingVO();

        LogisticsRopResponse response = LogisticsRopResponse.builder()
                .configId(logisticsSettingVO.getId())
                .customerKey(logisticsSettingVO.getCustomerKey())
                .deliveryKey(logisticsSettingVO.getDeliveryKey())
                .callBackUrl(logisticsSettingVO.getCallbackUrl())
                .status(logisticsSettingVO.getRealTimeStatus().toValue())
                .subscribeStatus(logisticsSettingVO.getSubscribeStatus().toValue()).build();

        return ResponseEntity.ok(response);
    }

    /**
     * 保存快递配置信息
     * <p>
     * 为了减少对前端的改动, 因此在保持对接口出入参不变的情况下, 在代码中做字段的转换.
     *
     * @param saveRopRequest
     * @return
     */
    @Operation(summary = "保存快递配置信息")
    @RequestMapping(value = "/deliveryInfo", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> saveDelivery(@RequestBody LogisticsSaveRopRequest saveRopRequest) {
        operateLogMQUtil.convertAndSend("设置", "保存物流接口", "保存物流接口");

        // 因为目前暂时只对接了快递100, 因此快递类型此处固定, 后期对接其他快递方式, 需要由前端传递物流类型进来
        LogisticsType logisticsType = LogisticsType.KUAI_DI_100;
        String customerId = commonUtil.getOperatorId();
        LogisticsSettingAddRequest request = LogisticsSettingAddRequest.builder()
                .callbackUrl(saveRopRequest.getCallBackUrl()).createPerson(customerId)
                .customerKey(saveRopRequest.getCustomerKey()).updatePerson(customerId)
                .deliveryKey(saveRopRequest.getDeliveryKey()).delFlag(DeleteFlag.NO)
                .logisticsType(logisticsType).realTimeStatus(DefaultFlag.fromValue(saveRopRequest.getStatus()))
                .subscribeStatus(DefaultFlag.fromValue(saveRopRequest.getSubscribeStatus())).build();

        return ResponseEntity.ok(logisticsSettingProvider.add(request));
    }

    /**
     * 修改快递配置
     * <p>
     * 为了减少对前端的改动, 因此在保持对接口出入参不变的情况下, 在代码中做字段的转换.
     *
     * @param saveRopRequest
     * @return
     */
    @Operation(summary = "修改快递配置")
    @RequestMapping(value = "/deliveryInfo", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> updateDelivery(@RequestBody LogisticsSaveRopRequest saveRopRequest) {
        operateLogMQUtil.convertAndSend("设置", "编辑物流接口", "编辑物流接口");
        String customerId = commonUtil.getOperatorId();
        // 因为目前暂时只对接了快递100, 因此快递类型此处固定, 后期对接其他快递方式, 需要由前端传递物流类型进来
        LogisticsType logisticsType = LogisticsType.KUAI_DI_100;

        LogisticsSettingModifyRequest request = LogisticsSettingModifyRequest.builder()
                .id(saveRopRequest.getConfigId())
                .callbackUrl(saveRopRequest.getCallBackUrl())
                .customerKey(saveRopRequest.getCustomerKey())
                .updatePerson(customerId)
                .deliveryKey(saveRopRequest.getDeliveryKey())
                .realTimeStatus(DefaultFlag.fromValue(saveRopRequest.getStatus()))
                .logisticsType(logisticsType)
                .subscribeStatus(DefaultFlag.fromValue(saveRopRequest.getSubscribeStatus())).build();
        return ResponseEntity.ok(logisticsSettingProvider.modify(request));
    }
}
