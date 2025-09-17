package com.wanmi.sbc.empower.logisticssetting.service.kuaidi100;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.request.logisticssetting.DeliveryQueryRequest;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsQueryBaseService;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsSettingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: songhanlin
 * @Date: Created In 上午11:24 2021/4/12
 * @Description: TODO
 */
@Slf4j
@Component(LogisticsType.EXPRESS_QUERY_SERVICE_KUAIDI100)
public class KuaiDi100ExpressService implements LogisticsQueryBaseService {

    @Autowired
    private LogisticsSettingService logisticsSettingService;

    /**
     * kuaidi100 请求地址
     */
    private static final String KUAIDI_URL = "http://poll.kuaidi100.com/poll/query.do";

    @Override
    public List<Map<Object, Object>> queryExpressInfo(DeliveryQueryRequest queryRequest) {
        List<Map<Object, Object>> orderList = new ArrayList<>();

        LogisticsSetting response = logisticsSettingService.getOneByLogisticsType(LogisticsType.KUAI_DI_100);
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        String customer = response.getCustomerKey();
        String kuaidiKey = response.getDeliveryKey();
        //查询参数

        String param;
        if(StringUtils.isNotBlank(queryRequest.getPhone())){
            param  = "{\"com\":\"" + queryRequest.getCompanyCode() +
                    "\",\"num\":\"" + queryRequest.getDeliveryNo() +
                    "\",\"phone\":\"" + queryRequest.getPhone() +
                    "\"}";
        }else{
            param  = "{\"com\":\"" + queryRequest.getCompanyCode() +
                    "\",\"num\":\"" + queryRequest.getDeliveryNo() +
                    "\"}";
        }

        //加密的签名
        String sign = (MD5Util.md5Hex(param + kuaidiKey + customer, "utf-8")).toUpperCase();
        //查询所需的参数
        HashMap<String, String> params = new HashMap<>();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);

        String result = null;
        try {
            log.info("kuaidi100 queryExpressInfo start , params = {}",params);
            result = HttpRequest.postData(KUAIDI_URL, params, "utf-8");
            log.info("kuaidi100 queryExpressInfo end , result = {}",result);
            // 格式化数据
            JSONObject jsonResult = JSONObject.parseObject(result);
            JSONArray kuaidiList = JSONArray.parseArray(jsonResult.get("data").toString());
            if (kuaidiList != null && kuaidiList.size() > 0) {
                for (int i = 0; i < kuaidiList.size(); i++) {
                    JSONObject jobj = JSON.parseObject(kuaidiList.get(i).toString(), JSONObject.class);
                    Map<Object, Object> map = new HashMap<>();
                    map.put("time", jobj.get("ftime"));
                    map.put("context", jobj.get("context"));
                    orderList.add(map);
                }
            }
        } catch (Exception e) {
            log.error("根据快递公司及快递单号查询物流详情异常：{}", result, e);
        }
        return orderList;
    }
}
