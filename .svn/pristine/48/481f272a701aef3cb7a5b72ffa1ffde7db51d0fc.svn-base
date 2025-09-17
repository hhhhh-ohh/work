package com.wanmi.sbc.goods.validator;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandSaveRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * 品牌提交验证器
 * Created by daiyitian on 17/5/4.
 */
@Component
public class GoodsBrandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return GoodsBrandSaveRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GoodsBrandSaveRequest saveRequest = (GoodsBrandSaveRequest)target;
        //验证品牌名称
        if(Objects.isNull(saveRequest.getGoodsBrand())
                || StringUtils.isBlank(saveRequest.getGoodsBrand().getBrandName())
                || ValidateUtil.isOverLen(saveRequest.getGoodsBrand().getBrandName(), Constants.NUM_30)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证品牌昵称
        if(ValidateUtil.isOverLen(saveRequest.getGoodsBrand().getNickName(), Constants.NUM_30)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //限制拼音长度
        if(ValidateUtil.isOverLen(saveRequest.getGoodsBrand().getPinYin(), Constants.NUM_45)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //限制简拼长度
        if(ValidateUtil.isOverLen(saveRequest.getGoodsBrand().getSPinYin(), Constants.NUM_45)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
