package com.wanmi.sbc.empower.deliveryrecord.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.WebUtil;
import com.wanmi.sbc.empower.bean.constant.DeliveryErrorCode;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.bean.vo.DadaCityVO;
import com.wanmi.sbc.empower.bean.vo.DadaDeliverFeeVO;
import com.wanmi.sbc.empower.bean.vo.DadaOrderDetailVO;
import com.wanmi.sbc.empower.bean.vo.DadaReasonVO;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaBaseRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaCancelRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderFaultConfirmRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderQueryRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaOrderRequest;
import com.wanmi.sbc.empower.deliveryrecord.request.DadaRiderCancelConfirmRequest;
import com.wanmi.sbc.empower.deliveryrecord.response.DadaApiResponse;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsSettingService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * 达达配送接口
 * Created by dyt on 2017/3/20.
 */
@Slf4j
@Service
public class DadaApiService {

    @Value("${dada.dev.mode: true}")
    private Boolean devMode = Boolean.TRUE;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private LogisticsSettingService logisticsSettingService;

    /**
     * callBack
     * 查询物流运费
     *
     * @param request 请求参数
     * @return 运费响应
     */
    public DadaDeliverFeeVO queryDeliverFee(DadaOrderRequest request) {
        return this.commonOrderRequest(request, "/api/order/queryDeliverFee");
    }

    /**
     * 新增订单
     *
     * @param request 请求参数
     * @return 运费响应
     */
    public DadaDeliverFeeVO addOrder(DadaOrderRequest request) {
        return this.commonOrderRequest(request, "/api/order/addOrder");
    }

    /**
     * 重新发布订单
     *
     * @param request 请求参数
     * @return 运费响应
     */
    public DadaDeliverFeeVO reAddOrder(DadaOrderRequest request) {
        return this.commonOrderRequest(request, "/api/order/reAddOrder");
    }

    /**
     * 取消
     *
     * @return 扣除的违约金
     */
    public BigDecimal cancel(DadaCancelRequest request) {
        String resp = this.commonRequest(request, "/api/order/formalCancel");
        DadaApiResponse<HashMap<String, BigDecimal>> response = JSONObject.parseObject(resp,
                new TypeReference<DadaApiResponse<HashMap<String, BigDecimal>>>() {
                });
        throwResponse(response);
        return response.getResult().get("deduct_fee");
    }

    /**
     * 确认妥投异常返回完成
     */
    public void confirmFault(DadaOrderFaultConfirmRequest request) {
        String resp = this.commonRequest(request, "/api/order/confirm/goods");
        DadaApiResponse response = JSONObject.parseObject(resp, DadaApiResponse.class);
        throwResponse(response);
    }

    /***
     * 骑士取消确认
     * @param request
     */
    public void riderCancelConfirm(DadaRiderCancelConfirmRequest request) {
        String resp = this.commonRequest(request, "/api/message/confirm");
        DadaApiResponse response = JSONObject.parseObject(resp, DadaApiResponse.class);
        throwResponse(response);
    }

    /**
     * 查询订单详情
     *
     * @return 运费响应
     */
    public DadaOrderDetailVO queryOrder(DadaOrderQueryRequest request) {
        String resp = this.commonRequest(request, "/api/order/status/query");
        DadaApiResponse<DadaOrderDetailVO> response = JSONObject.parseObject(resp,
                new TypeReference<DadaApiResponse<DadaOrderDetailVO>>() {
                });
        throwResponse(response);
        return response.getResult();
    }

    /**
     * 查询问题理由列表
     *
     * @return 运费响应
     */
    public List<DadaReasonVO> reasonList() {
        DadaBaseRequest request = new DadaBaseRequest();
        String resp = this.commonRequest(request, "/api/order/cancel/reasons");
        DadaApiResponse<List<DadaReasonVO>> response = JSONObject.parseObject(resp,
                new TypeReference<DadaApiResponse<List<DadaReasonVO>>>() {
                });
        throwResponse(response);
        return response.getResult();
    }

    /**
     * 查询城市列表
     *
     * @return 城市列表
     */
    public List<DadaCityVO> cityList() {
        List<DadaCityVO> cityVOList = redisService.getList(CacheKeyConstant.DADA_CITY, DadaCityVO.class);
        if (CollectionUtils.isNotEmpty(cityVOList)) {
            return cityVOList;
        }
        DadaBaseRequest request = new DadaBaseRequest();
        String resp = this.commonRequest(request, "/api/cityCode/list");
        DadaApiResponse<List<DadaCityVO>> response = JSONObject.parseObject(resp,
                new TypeReference<DadaApiResponse<List<DadaCityVO>>>() {
                });
        throwResponse(response);
        cityVOList = response.getResult();
        redisService.setObj(CacheKeyConstant.DADA_CITY, cityVOList, 60*60*24*7);
        return cityVOList;
    }

    /**
     * 根据城市名称获取编码
     *
     * @param cityName
     * @return
     */
    public String getCityCodeByName(String cityName) {
        List<DadaCityVO> cityList = cityList();
        String t_cityName = cityName.replace("市", "");
        return cityList.stream()
                .filter(city -> t_cityName.equals(city.getCityName()) || cityName.contains(city.getCityName()))
                .map(DadaCityVO::getCityCode)
                .findFirst().orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050165));
    }

    /**
     * 公共订单业务请求
     *
     * @param request    订单提交提求
     * @param requestUrl 地址
     * @return 订单提交响应
     */
    private DadaDeliverFeeVO commonOrderRequest(DadaOrderRequest request, String requestUrl) {
        LogisticsSetting dadaSetting = findDadaSetting();
        request.setSourceId(dadaSetting.getShopNo());
        request.setCallback(dadaSetting.getCallbackUrl());
        String resp = this.commonRequest(request, requestUrl);
        DadaApiResponse<DadaDeliverFeeVO> response = JSONObject.parseObject(resp,
                new TypeReference<DadaApiResponse<DadaDeliverFeeVO>>() {
                });
        throwResponse(response);
        return response.getResult();
        //return new Gson().fromJson(resp, new TypeToken<DadaApiResponse<DeliverFeeVO>>() {}.getType());
    }

    /**
     * 公共业务请求
     *
     * @param request    订单提交提求
     * @param requestUrl 地址
     * @return 订单提交响应
     */
    private String commonRequest(DadaBaseRequest request, String requestUrl) {
        String resp;
        try {
            String realUrl = getUrl().concat(requestUrl);
            String requestJson = this.getRequestParams(request);
            if (log.isInfoEnabled()) {
                log.info("DadaApiService-> commonRequest call, url is {}, requestJson is {}", realUrl, requestJson);
            }
            resp = WebUtil.post(realUrl,requestJson, getHeadMap());
            if (StringUtils.isBlank(resp)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050166);
            }
        } catch (Exception e) {
            log.error("达达配送远程请求异常：", e);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050166);
        }

        if (log.isInfoEnabled()) {
            log.info("DadaApiService-> commonRequest response {}", resp);
        }
        return resp;
    }

    /**
     * 封装请求参数
     *
     * @param request
     * @return
     */
    private String getRequestParams(DadaBaseRequest request) {
        LogisticsSetting dadaSetting = findDadaSetting();
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("source_id", dadaSetting.getShopNo());
        requestParams.put("app_key", dadaSetting.getCustomerKey());
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        requestParams.put("format", "json");
        requestParams.put("v", "1.0");
        requestParams.put("body", request.toJson());
        requestParams.put("signature", this.getSign(requestParams, dadaSetting.getDeliveryKey()));
        return JSON.toJSONString(requestParams);
    }

    /**
     * 封装签名
     *
     * @param requestParams 参数
     * @return 签名字符串
     */
    private String getSign(Map<String, Object> requestParams,String appSecret) {
        //请求参数键值升序排序
        Map<String, Object> sortedParams = new TreeMap<>(requestParams);
        Set<Map.Entry<String, Object>> entrySets = sortedParams.entrySet();

        //拼参数字符串。MD5签名并校验
        StringBuilder signStr = new StringBuilder(appSecret);
        for (Map.Entry<String, Object> entry : entrySets) {
            signStr.append(entry.getKey()).append(entry.getValue());
        }
        signStr.append(appSecret);
        return MD5Util.md5Hex(signStr.toString(), "UTF-8").toUpperCase();
    }

    /**
     * 封装头信息，指定JSON
     *
     * @return 头信息
     */
    private Map<String, String> getHeadMap() {
        Map<String, String> hearMap = new HashMap<>();
        hearMap.put("Content-Type", WebUtil.CONTENT_TYPE_JSON);
        return hearMap;
    }

    /**
     * 验证响应结果
     *
     * @param response sdk公共响应结果
     */
    private void throwResponse(DadaApiResponse response) {
        //当不是成功时、已重发、已取消的情况，其他异常可回滚
        if (response.getCode() != Constants.ZERO && response.getCode() != Constants.NUM_2061 && response.getCode() != Constants.NUM_2076) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050167, new Object[]{response.getMsg()});
        }
    }

    //切换开发模式
    private String getUrl() {
        if (Boolean.FALSE.equals(devMode)) {
            return "http://newopen.imdada.cn";
        }
        return "http://newopen.qa.imdada.cn";
    }

    private LogisticsSetting findDadaSetting() {
       return logisticsSettingService.getOneByLogisticsType(LogisticsType.DADA);
    }
}
