package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户详细信息
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
public class CustomerDetailWithImgVO extends BasicResponse {

    private static final long serialVersionUID = 1L;


    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;


    @Schema(description = "头像路径")
    private String headimgurl;

    /**
     * 考虑到后面可能会有很多类似“企业会员”的标签，用List存放标签内容
     */
    @Schema(description = "会员标签")
    private List<String> customerLabelList = new ArrayList<>();
}
