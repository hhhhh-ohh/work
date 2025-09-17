package com.wanmi.sbc.sensitivewords;

import com.wanmi.sbc.authority.AuthBaseService;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.CheckFunctionInterface;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wur
 * @className ReturnSensitiveWordsAspect
 * @description TODO
 * @date 2022/7/5 10:18
 **/
@Log4j2
@Component
public class SensitiveWordsCheckFunctionService implements CheckFunctionInterface {

    @Autowired
    private AuthBaseService authBaseService;

    @Override
    public Boolean checkFunction(Operator operator, List<String> functionList) {
        //权限处理
        List<String> function = authBaseService.findTodoFunctionIds(functionList);
        return CollectionUtils.isNotEmpty(function);
    }
}