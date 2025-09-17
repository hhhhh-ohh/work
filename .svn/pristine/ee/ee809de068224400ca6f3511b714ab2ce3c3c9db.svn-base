package com.wanmi.sbc.empower.apppush.service.umeng.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: sbc-micro-service
 * @description: Push参数
 * @create: 2020-01-06 19:22
 **/
@Schema
@Data
public class PushEntry {

    @Schema(description = "通知栏文字")
    private String ticker;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知文本")
    private String text;

    @Schema(description = "通知图片")
    private String image;

    @Schema(description = "通知跳转路由")
    private String router;

    @Schema(description = "发送时间（空则立即发送）")
    private LocalDateTime sendTime;

    /**
     * 服务器会根据这个标识避免重复发送
     * 有些情况下（例如网络异常）开发者可能会重复调用API导致
     * 消息多次下发到客户端。如果需要处理这种情况，可以考虑此参数。
     */
    @Schema(description = "开发者对消息的唯一标识")
    private String outBizNo;
}