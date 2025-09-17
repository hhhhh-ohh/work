package com.wanmi.sbc.util;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lvzhenwei
 * @className WeakPasswordsCheckUtil
 * @description 弱密码库密码校验
 * @date 2023/3/29 10:48 上午
 **/
@Component
public class WeakPasswordsCheckUtil {

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    private static final String WEAK_PASSWORDS_CONFIG_KEY = "weak_passwords";

    /**
     * @description 弱密码校验
     * @author  lvzhenwei
     * @date 2023/3/29 10:56 上午
     * @param password
     * @return void
     **/
    public void weakPasswordsCheck(String password){
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigKey(WEAK_PASSWORDS_CONFIG_KEY);
        List<ConfigVO> configVOList = systemConfigQueryProvider.findByConfigKeyAndDelFlagNew(configQueryRequest).getContext().getConfigVOList();
        if(CollectionUtils.isNotEmpty(configVOList)){
            String weakPasswordsStr = configVOList.get(0).getContext();
            List<String> weakPasswordsList= Lists.newArrayList(weakPasswordsStr.split(","));
            weakPasswordsList.forEach(weakPasswords->{
                String weakPasswordsMd5 = MD5Util.md5Hex(weakPasswords);
                if(password.equals(weakPasswordsMd5)){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000025);
                }
            });
        }
    }
}
