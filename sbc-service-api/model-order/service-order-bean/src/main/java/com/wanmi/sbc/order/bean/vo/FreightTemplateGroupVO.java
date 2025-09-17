package com.wanmi.sbc.order.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wur
 * @className FreightTemplateGroupVO
 * @description 单品运费模板分组信息  用于确认订单页运费详情展示使用
 * @date 2022/10/13 15:43
 **/
@Data
@Schema
public class FreightTemplateGroupVO {

    @Schema(description = "运费模板Id")
    private Long firstFreightTemplateId;

    @Schema(description = "运费模板Id")
    private List<Long> freightTemplateIds = new ArrayList<>();

}