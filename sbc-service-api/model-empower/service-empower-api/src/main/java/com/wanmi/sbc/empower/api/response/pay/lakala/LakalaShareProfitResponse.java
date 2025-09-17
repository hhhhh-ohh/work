package com.wanmi.sbc.empower.api.response.pay.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author edz
 * @className LakalaShareProfitResponse
 * @description TODO
 * @date 2022/7/14 21:20
 **/
@Data
@Schema
public class LakalaShareProfitResponse {

    @Schema(description = "业务响应码，原则当resCode的值为0000时才需要处理数据")
    private String resCode;

    @Schema(description = "响应码描述")
    private String resDesc;

    @Schema(description = "0 未分账 1 分账成功 2 分账中 3 分账失败 4 分账回退成功 5 分账回退中 6 分账回退失败")
    private String status;
}
