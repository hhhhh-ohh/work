package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className updateEC003InfoRequest
 * @description TODO
 * @date 2022/9/13 17:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class UpdateEC003InfoRequest implements Serializable {

    @Schema(description = "文件后获取fileId")
    private String ecContentId;

    @Schema(description = "请求上送的订单号")
    private String ecNO;

    @Schema(description = "电子签约申请结果H5")
    private String ecUrl;

    @Schema(description = "电子签约申请受理编号")
    private String ecApplyId;
}
