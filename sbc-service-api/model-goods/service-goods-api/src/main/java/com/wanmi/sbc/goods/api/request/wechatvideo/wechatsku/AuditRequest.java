package com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku;

import com.wanmi.sbc.common.enums.EditStatus;
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
public class AuditRequest implements Serializable {

    @NotNull
    private EditStatus editStatus;

    @NotBlank
    private String goodsId;

    private String rejectReason = "";

    @NotNull
    private WechatShelveStatus wechatShelveStatus;
}
