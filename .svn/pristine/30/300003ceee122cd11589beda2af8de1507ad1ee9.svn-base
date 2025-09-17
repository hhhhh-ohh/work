package com.wanmi.sbc.pay;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.pay.gateway.*;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.pay.reponse.PayGatewayReponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * boss交易管理模块
 * Created by sunkun on 2017/8/8.
 */
@Tag(name = "PayManageController", description = "boss交易管理模块 Api")
@RestController
@Validated
@RequestMapping("/tradeManage")
public class PayManageController {

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private PaySettingProvider paySettingProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WxPayProvider wxPayProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;


    /**
     * 获取网关列表
     *
     * @return
     */
    @Operation(summary = "获取网关列表")
    @RequestMapping(value = "/gateways", method = RequestMethod.GET)
    public BaseResponse<List<PayGatewayVO>> gateways() {
        Long storeIdWithDefault = commonUtil.getStoreIdWithDefault();
        GatewayByStoreIdRequest request = GatewayByStoreIdRequest.builder().storeId(storeIdWithDefault).build();
        List<PayGatewayVO> payGatewayList = paySettingQueryProvider.listGatewayByStoreId(request).getContext().getPayGatewayVOList();
        // 如果没有网关列表 给店铺端按照boss开启的数据初始化数据
        if (CollectionUtils.isEmpty(payGatewayList)) {
            GatewayInitByStoreIdRequest gatewayInitByStoreIdRequest = GatewayInitByStoreIdRequest.builder().storeId(commonUtil.getStoreIdWithDefault()).build();
            payGatewayList = paySettingQueryProvider.initGatewayByStoreId(gatewayInitByStoreIdRequest).getContext().getPayGatewayVOList();

        }
        return BaseResponse.success(payGatewayList);
    }

    /**
     * 根据网关id查询支付渠道项
     *
     * @param id 网关id
     * @return
     */
    @Operation(summary = "根据网关id查询支付渠道项")
    @Parameter(name = "id", description = "网关Id", required = true)
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public BaseResponse<PayGatewayReponse> items(@PathVariable Long id) {
        PayGatewayResponse payGateway = paySettingQueryProvider.getGatewayById(new GatewayByIdRequest(id, commonUtil.getStoreIdWithDefault())).getContext();
        return BaseResponse.success(PayGatewayReponse.builder().
                id(payGateway.getId()).
                name(payGateway.getName().toString()).
                isOpen(payGateway.getIsOpen()).
                type(payGateway.getType()).
                payGatewayConfig(payGateway.getConfig()).
                channelItemList(payGateway.getPayChannelItemList()).
                alias(payGateway.getAlias()).build());
    }

    /**
     * 保存
     *
     * @param payGatewayRequest
     * @return
     */
    @Operation(summary = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResponse save(@RequestBody @Valid PayGatewaySaveRequest payGatewayRequest) {
        payGatewayRequest.setStoreId(commonUtil.getStoreIdWithDefault());
        if (PayGatewayEnum.WECHAT.toValue().equals(payGatewayRequest.getName())) {
            // 如果开启 则验证必填参数
            if (Objects.equals(IsOpen.YES, payGatewayRequest.getIsOpen())) {
                if (StringUtils.isBlank(payGatewayRequest.getPayGatewayConfig().getApiKey())
                        && (StringUtils.isBlank(payGatewayRequest.getPayGatewayConfig().getApiV3Key())
                        || StringUtils.isBlank(payGatewayRequest.getPayGatewayConfig().getMerchantSerialNumber())
                        || StringUtils.isBlank(payGatewayRequest.getPayGatewayConfig().getPrivateKey()))) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
            payGatewayRequest.getPayGatewayConfig().setAppId(null);
            payGatewayRequest.getPayGatewayConfig().setSecret(null);
            payGatewayRequest.getPayGatewayConfig().setOpenPlatformAppId(null);
            payGatewayRequest.getPayGatewayConfig().setOpenPlatformSecret(null);
            payGatewayRequest.getPayGatewayConfig().setOpenPlatformAccount(payGatewayRequest.getPayGatewayConfig().getAccount());
            payGatewayRequest.getPayGatewayConfig().setOpenPlatformApiKey(payGatewayRequest.getPayGatewayConfig().getApiKey());
            payGatewayRequest.getPayGatewayConfig().setOpenPlatformApiV3Key(payGatewayRequest.getPayGatewayConfig().getApiV3Key());
            payGatewayRequest.getPayGatewayConfig().setMerchantSerialNumber(payGatewayRequest.getPayGatewayConfig().getMerchantSerialNumber());
        }


        if (PayGatewayEnum.LAKALA.toValue().equals(payGatewayRequest.getName()) ||
                PayGatewayEnum.LAKALACASHIER.toValue().equals(payGatewayRequest.getName())) {
            List<PayGatewayVO> payGatewayVOList = paySettingQueryProvider.listGatewayByStoreId(GatewayByStoreIdRequest.builder().storeId(commonUtil.getStoreIdWithDefault()).build())
                    .getContext().getPayGatewayVOList();
            // 拉卡拉支付方式互斥校验
            if (PayGatewayEnum.LAKALA.toValue().equals(payGatewayRequest.getName())) {
                List<PayGatewayVO> payGatewayVOS = payGatewayVOList.stream().filter(p -> PayGatewayEnum.LAKALACASHIER.equals(p.getName())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(payGatewayVOS) && IsOpen.YES.equals(payGatewayVOS.get(0).getIsOpen())) {
                    throw new SbcRuntimeException(SettingErrorCodeEnum.K070116);
                }
            }

            if (PayGatewayEnum.LAKALACASHIER.toValue().equals(payGatewayRequest.getName())) {
                if (CollectionUtils.isNotEmpty(payGatewayVOList)) {
                    List<PayGatewayVO> payGatewayVOS = payGatewayVOList.stream().filter(p -> PayGatewayEnum.LAKALA.equals(p.getName())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(payGatewayVOS) && IsOpen.YES.equals(payGatewayVOS.get(0).getIsOpen())) {
                        throw new SbcRuntimeException(SettingErrorCodeEnum.K070116);
                    }
                    payGatewayVOS = payGatewayVOList.stream().filter(p -> !PayGatewayEnum.BALANCE.equals(p.getName()) &&
                            !PayGatewayEnum.CREDIT.equals(p.getName()) && !PayGatewayEnum.LAKALACASHIER.equals(p.getName())
                            && IsOpen.YES.equals(p.getIsOpen())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(payGatewayVOS)) {
                        throw new SbcRuntimeException(SettingErrorCodeEnum.K070117);
                    }
                }
            }

            //查询原来的配置 开关
            IsOpen isOpen = paySettingQueryProvider.getGatewayById(GatewayByIdRequest.builder()
                    .gatewayId(payGatewayRequest.getId())
                    .storeId(commonUtil.getStoreIdWithDefault())
                    .build()).getContext().getIsOpen();
            // 如果不相等，则需要刷店铺数据以及商品数据
            if (!payGatewayRequest.getIsOpen().equals(isOpen)) {
                if (redisUtil.hasKey(RedisKeyConstant.LAKALA_SETTING_ONCE)) {
                    throw new SbcRuntimeException(SettingErrorCodeEnum.K070101);
                }

                Long efficientTailOrder = tradeQueryProvider.findForEfficientTailOrder().getContext();
                if (efficientTailOrder != 0){
                    if (IsOpen.YES.equals(payGatewayRequest.getIsOpen())){
                        throw new SbcRuntimeException(SettingErrorCodeEnum.K070102);
                    } else {
                        throw new SbcRuntimeException(SettingErrorCodeEnum.K070103);
                    }
                }

                //获取当前时间
                LocalDateTime now = LocalDateTime.now();
                // 获取明天凌晨的时间
                LocalDateTime tom = LocalDateTime.of(now.plusDays(Constants.ONE).toLocalDate(), LocalTime.MIN);
                //设置过去时间。。明天凌晨重新可以操作
                redisUtil.setString(RedisKeyConstant.LAKALA_SETTING_ONCE, NumberUtils.INTEGER_ZERO.toString(), Duration.between(now,tom).getSeconds());
                MqSendDTO mqSendDTO = new MqSendDTO();
                mqSendDTO.setTopic(ProducerTopic.LAKALA_EDITE_STROE_STATE);
                mqSendDTO.setData(payGatewayRequest.getIsOpen().name());
                mqSendProvider.send(mqSendDTO);
            }
        }
        paySettingProvider.savePayGateway(payGatewayRequest);
        //操作日志记录
        String op = "";
        if (PayGatewayEnum.PING.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑聚合支付";
        }
        if (PayGatewayEnum.UNIONB2B.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑企业支付";
        }
        if (PayGatewayEnum.ALIPAY.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑支付宝支付";
        }
        if (PayGatewayEnum.WECHAT.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑微信支付";
        }
        if (PayGatewayEnum.UNIONPAY.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑银联支付";
        }
        if (PayGatewayEnum.BALANCE.toValue().equals(payGatewayRequest.getName())) {
            op = "余额支付";
        }
        if (PayGatewayEnum.CREDIT.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑授信支付";
        }
        if (PayGatewayEnum.LAKALA.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑拉卡拉支付";
        }
        if (PayGatewayEnum.LAKALACASHIER.toValue().equals(payGatewayRequest.getName())) {
            op = "编辑拉卡拉收银台支付";
        }
        operateLogMQUtil.convertAndSend("财务", op, op);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 上传微信支付证书
     *
     * @param multipartFiles
     * @param gatewayConfigId
     * @param type
     * @return
     * @throws IOException
     */
    @Operation(summary = "上传微信支付证书")
    @RequestMapping(value = "/uploadPayCertificate", method = RequestMethod.POST)
    public BaseResponse uploadPayCertificate(@RequestParam("uploadFile") List<MultipartFile> multipartFiles, Long
            gatewayConfigId, Integer type) throws IOException {

        //验证上传参数
        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        for (MultipartFile file : multipartFiles) {
            if (file == null || file.getSize() == 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        PayGatewayUploadPayCertificateRequest payGatewayUploadPayCertificateRequest = new PayGatewayUploadPayCertificateRequest();
        payGatewayUploadPayCertificateRequest.setId(gatewayConfigId);
        payGatewayUploadPayCertificateRequest.setPayCertificateType(type);
        payGatewayUploadPayCertificateRequest.setPayCertificate(multipartFiles.get(0).getBytes());
        return wxPayProvider.uploadPayCertificate(payGatewayUploadPayCertificateRequest);
    }
}
