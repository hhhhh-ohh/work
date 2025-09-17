package com.wanmi.sbc.message.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName SmsTemplateParamDTO
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/12/3 15:08
 **/
@Data
public class SmsTemplateParamDTO {

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "名称 用于账户称号、商品名称、订单第一行商品名称")
    private String name;

    @Schema(description = "人数 张数 积分值 手机号")
    private String number;

    @Schema(description = "原因")
    private String remark;

    @Schema(description = "商品名称")
    private String product;

    @Schema(description = "金额")
    private String money;

    @Schema(description = "第二个金额")
    private String price;
}
