package com.wanmi.sbc.intercepter;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * @author wur
 * @className RefererInterceptor
 * @description CSRF攻击处理
 * @date 2023/8/9 14:21
 **/
@Slf4j
public class RefererInterceptor implements HandlerInterceptor {

    private Boolean openCheck;

    private List<String> refererCheckUrlList;

    public RefererInterceptor() {
    }

    public RefererInterceptor(Boolean openCheck, List<String> refererCheckUrlList) {
        this.openCheck = openCheck;
        this.refererCheckUrlList = refererCheckUrlList;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!openCheck) {
            return true;
        }
        String requestReferer = request.getHeader("referer");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https://").append(request.getServerName());
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("http://").append(request.getServerName());
        if (StringUtils.isEmpty(requestReferer)
                || requestReferer.lastIndexOf(stringBuffer.toString()) == 0
                || requestReferer.lastIndexOf(stringBuffer1.toString()) == 0
                || refererCheckUrlList.contains(requestReferer)) {
            return true;
        }
        this.handleResponse(response, "操作失败");
        return false;
    }

    /**
     * 含有敏感词 提示信息
     *
     * @param response
     * @throws Exception
     */
    private void handleResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(BaseResponse.info(CommonErrorCodeEnum.K000001.getCode(), message)));
        response.getWriter().flush();
        response.getWriter().close();
    }
}