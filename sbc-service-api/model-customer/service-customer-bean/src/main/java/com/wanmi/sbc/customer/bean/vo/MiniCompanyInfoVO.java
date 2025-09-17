package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司信息
 * Created by CHENLI on 2017/5/12.
 */
@Schema
@Data
public class MiniCompanyInfoVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 一对多关系，多个SPU编号
     */
    @Schema(description = "多个SPU编号")
    private List<String> goodsIds = new ArrayList<>();

}
