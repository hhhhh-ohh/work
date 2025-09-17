package com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku;

import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DownRequest implements Serializable {

    @NotBlank
    private String goodsId;

    @NotNull
    private WechatShelveStatus wechatShelveStatus;

    private String downReason = "";
}
