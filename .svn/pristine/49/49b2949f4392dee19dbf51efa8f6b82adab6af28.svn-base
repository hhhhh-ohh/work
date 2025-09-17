package com.wanmi.sbc.logistics;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.logisticslog.LogisticsLogProvider;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogNoticeForKuaidiHundredRequest;
import com.wanmi.sbc.empower.bean.dto.KuaidiHundredNoticeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 物流订阅回调
 * Created by dyt on 2020/04/17.
 */
@Tag(name = "LogisticsCallbackController", description = "交易回调")
@RestController
@Validated
@RequestMapping("/logisticsCallback")
@Slf4j
public class LogisticsCallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsCallbackController.class);

    @Autowired
    private LogisticsLogProvider logisticsLogProvider;

    /**
     * 物流订阅异步回调
     * @param request
     * @param response
     */
    @Operation(summary = "微信支付退款成功异步回调")
    @PostMapping(value = "/kuaidi100/{id}")
    public void callBackByKuaidi100(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) throws IOException {
        String param = request.getParameter("param");
        LOGGER.info(param);

        if(StringUtils.isBlank(param)){
            error(response, "参数为空");
            return;
        }
        param = StringEscapeUtils.unescapeHtml(param);
        try {
            KuaidiHundredNoticeDTO dto = JSON.parseObject(param, KuaidiHundredNoticeDTO.class);
            dto.setId(id);
            logisticsLogProvider.modifyForKuaidiHundred(LogisticsLogNoticeForKuaidiHundredRequest.builder().kuaidiHundredNoticeDTO(dto).build());
            success(response);
        }catch (SbcRuntimeException e){
            LOGGER.error("物流订阅异常", e);
            error(response, "物流订阅异常");
        }catch (Exception e){
            LOGGER.error("物流订阅异常", e);
            error(response, "物流订阅异常");
        }
    }

    private void success(HttpServletResponse response) throws IOException {
        response.getWriter().print(resultParam(true, "200", "成功"));
    }

    private void error(HttpServletResponse response, String message) throws IOException {
        response.getWriter().print(resultParam(false, "500", message));
    }

    private String resultParam(boolean result, String returnCode, String message) {
        JSONObject p = new JSONObject();
        p.put("result", result);
        p.put("returnCode", returnCode);
        p.put("message", message);
        return p.toJSONString();
    }
}
