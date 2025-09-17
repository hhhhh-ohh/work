package com.wanmi.sbc.setting.verifycode;

import com.wanmi.sbc.setting.bean.enums.VerifyType;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.AbstractCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 验证码Service类
 */
@Service
public class VerifyCodeService {

    private static final String PASS_FLAG = "@PASS@";

    private static final String CAPTCHA = "CAPTCHA";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 验证码接口服务类
     *
     * @author NP-HEHU
     * @date 2015-8-28 17:01:58
     */
    private static class MyCaptchaService extends AbstractCaptchaService {

        /**
         * 验证码接口服务构造方法
         */
        public MyCaptchaService() {
            String[] fontOption = {"Verdana", "Tahoma"};
            wordFactory = new MyWordFactory();
            fontFactory = new RandomFontFactory(25, fontOption);
            textRenderer = new BestFitTextRenderer();
            backgroundFactory = new SingleColorBackgroundFactory();
            colorFactory = new SingleColorFactory(new Color(0xCC, 0x00, 0x00));
            filterFactory = new CurvesRippleFilterFactory(colorFactory);
            width = 90;
            height = 100;

        }
    }

    /**
     * 验证码生成器
     *
     * @author NP-HEHU
     */
    private static class MyWordFactory extends RandomWordFactory {
        private static final int FOUR = 4;
        private static final int SIX = 6;

        /**
         * 验证码生成器构造方法
         */
        public MyWordFactory() {
            // characters = "absdekmnowx23456789";
            characters = "123456789";
            minLength = FOUR;
            maxLength = SIX;
        }
    }
}
