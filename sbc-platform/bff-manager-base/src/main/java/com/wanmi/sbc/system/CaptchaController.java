package com.wanmi.sbc.system;

import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 图片验证码控制器
 * Created by aqlu on 15/12/4.
 */
@Tag(name = "CaptchaController", description = "图片验证码控制器")
@Controller
public class CaptchaController {
    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 获取验证码
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @param uuid 唯一key
     * @throws IOException IOException
     */
    @Operation(summary = "获取验证码")
    @RequestMapping(value = "/captcha", method = {RequestMethod.GET, RequestMethod.POST})
    public void captcha(HttpServletRequest req, HttpServletResponse resp, String uuid) throws IOException {
        resp.setContentType("image/png");
        resp.setHeader("cache", "no-cache");

        try (OutputStream os = resp.getOutputStream()) {
            verifyCodeService.generateCaptcha(uuid, os);
            os.flush();
        }

    }

    /**
     * 验证验证码 验证页面传过来的验证码是否与session保存的验证码相等
     *
     * @param req HttpServletRequest
     * @return 相符返回1，否则返回0
     */
    @Operation(summary = "验证验证码 验证页面传过来的验证码是否与session保存的验证码相等")
    @Parameter(name = "enterValue", description = "验证码", required = true)
    @RequestMapping(value = "/check/captcha", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse checkCaptcha(HttpServletRequest req, String enterValue, String uuid) {
        return verifyCodeService.validateCaptcha(uuid, enterValue, 10, TimeUnit.MINUTES) ?
                BaseResponse.SUCCESSFUL() : BaseResponse.FAILED();
    }
}
