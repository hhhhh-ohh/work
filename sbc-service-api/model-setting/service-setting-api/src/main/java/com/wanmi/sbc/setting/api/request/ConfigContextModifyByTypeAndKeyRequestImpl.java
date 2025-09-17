package com.wanmi.sbc.setting.api.request;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigContextModifyByTypeAndKeyRequestImpl extends ConfigContextModifyByTypeAndKeyRequest {

    @Override
    public void checkParam() {
        Context returnContext = JSON.parseObject(super.getContext(), Context.class);
        String title = returnContext.getTitle();
        if (StringUtils.isBlank(title) || CollectionUtils.isEmpty(returnContext.getImgUrl())
                || StringUtils.isBlank(returnContext.getImgUrl().get(0).getUrl())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (title.length() < Constants.ONE || title.length() > Constants.NUM_25){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    @Data
    private static class Context{
        private String title;
        private List<ImgUrl> imgUrl;
    }

    @Data
    private static class ImgUrl{
        private String url;
    }
}
