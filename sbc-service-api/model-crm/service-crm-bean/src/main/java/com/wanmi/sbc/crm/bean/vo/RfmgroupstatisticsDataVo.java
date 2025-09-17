package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName RfmgroupstatisticsVo
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/10/15 16:53
 **/
@Schema
@Data
public class RfmgroupstatisticsDataVo extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 人群id
     */
    @Schema(description = "人群id")
    private Long groupId;

    /**
     * 人群名称
     */
    @Schema(description = "人群名称")
    private String groupName;

    /**
     * 人群定义
     */
    @Schema(description = "人群定义")
    private String groupDefinition;

    /**
     * 人群运营建议
     */
    @Schema(description = "人群运营建议")
    private String groupAdvise;

    /**
     * 会员人数
     */
    @Schema(description = "会员人数")
    private int customerNum;

    /**
     * 访问数
     */
    @Schema(description = "访问数")
    private int uvNum;

    /**
     * 成交数
     */
    @Schema(description = "成交数")
    private int tradeNum;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

}
