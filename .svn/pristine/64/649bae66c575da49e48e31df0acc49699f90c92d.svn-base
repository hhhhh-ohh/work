package com.wanmi.sbc.empower.api.response.deliveryrecord;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/***
 * 达达消息通知响应
 * @className DadaMessageResponse
 * @author zhengyang
 * @date 2022/1/19 14:34
 **/
@Data
public class DadaMessageResponse {

    public DadaMessageResponse(String status) {
        this.status = status;
    }

    public DadaMessageResponse(String status, Integer code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 结果状态
     */
    @Schema(description = "结果状态", required = true)
    private String status;

    /**
     * 结果码
     */
    @Schema(description = "结果码", required = true)
    private Integer code;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String msg;



    public static DadaMessageResponse SUCCESSFUL() {
        return new DadaMessageResponse("success", 0 ,"成功");
    }
}
