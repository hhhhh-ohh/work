package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.goods.bean.dto.WechatAuditDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema
public class WechatAuditRequest implements Serializable {

    @Size(min = 1)
    @Schema(description = "营业执照")
    @NotNull
    private List<String> businessLicence;

    private String operatorId;

    @Size(min = 1,max = 10,message = "每次最多可提审10个类目")
    @Valid
    @Schema(description = "微信类目")
    @NotNull
    private List<WechatAuditDTO> wechatAuditDTOS;


}
