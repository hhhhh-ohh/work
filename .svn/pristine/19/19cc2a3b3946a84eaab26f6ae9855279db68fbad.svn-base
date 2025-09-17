package com.wanmi.sbc.crm.api.request.customgroup;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.dto.AutoTagDTO;
import com.wanmi.sbc.crm.bean.dto.RegionDTO;
import com.wanmi.sbc.crm.bean.enums.CustomerIdentity;
import com.wanmi.sbc.crm.bean.enums.CustomerType;
import com.wanmi.sbc.crm.bean.enums.GroupType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-11
 * \* Time: 13:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomGroupRequest extends BaseRequest {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createPerson;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updatePerson;

    @Schema(description = "自定义分组名")
    private String groupName;

    @Schema(description = "所在地区")
    private List<RegionDTO> regionList;

    @Schema(description = "会员类型")
    private List<CustomerType> customerType;

    @Schema(description = "会员身份")
    private List<CustomerIdentity> customerIdentity;

    @Schema(description = "最近一次消费统计时间")
    private Integer lastTradeTime;

    @Schema(description = "累计消费次数统计时间")
    private Integer tradeCountTime;
    @Schema(description = "累计消费次数大于值")
    private Long gtTradeCount;
    @Schema(description = "累计消费次数小于值")
    private Long ltTradeCount;

    @Schema(description = "累计消费金额统计时间")
    private Integer tradeAmountTime;
    @Schema(description = "累计消费金额大于值")
    private Long gtTradeAmount;
    @Schema(description = "累计消费金额小于值")
    private Long ltTradeAmount;

    @Schema(description = "笔单价统计时间")
    private Integer avgTradeAmountTime;
    @Schema(description = "笔单价大于值")
    private Long gtAvgTradeAmount;
    @Schema(description = "笔单价小于值")
    private Long ltAvgTradeAmount;

    @Schema(description = "会员成长值大于值")
    private Long gtCustomerGrowth;
    @Schema(description = "会员成长值小于值")
    private Long ltCustomerGrowth;


    @Schema(description = "会员积分大于值")
    private Long gtPoint;
    @Schema(description = "会员积分小于值")
    private Long ltPoint;


    @Schema(description = "会员余额大于值")
    private Long gtBalance;
    @Schema(description = "会员余额小于值")
    private Long ltBalance;

    @Schema(description = "会员等级")
    private List<Long> customerLevel;

    @Schema(description = "最近有下单时间")
    private Integer recentTradeTime;

    @Schema(description = "最近无下单时间")
    private Integer noRecentTradeTime;

    @Schema(description = "最近有付款时间")
    private Integer recentPayTradeTime;

    @Schema(description = "最近无付款时间")
    private Integer noRecentPayTradeTime;

    @Schema(description = "最近有加购时间")
    private Integer recentCartTime;

    @Schema(description = "最近无加购时间")
    private Integer noRecentCartTime;

    @Schema(description = "最近有浏览时间")
    private Integer recentFlowTime;

    @Schema(description = "最近无浏览时间")
    private Integer noRecentFlowTime;

    @Schema(description = "最近有收藏时间")
    private Integer  recentFavoriteTime;

    @Schema(description = "最近无收藏时间")
    private Integer noRecentFavoriteTime;

    @Schema(description = "会员标签")
    private List<Long> customerTag;

    @Schema(description = "性别：0：女，1：男")
    private Integer gender;

    @Schema(description = "年龄大于值")
    private Integer gtAge;

    @Schema(description = "年龄小于值")
    private Integer ltAge;

    @Schema(description = "入会时间大于值")
    private Integer gtAdmissionTime;

    @Schema(description = "入会时间小于值")
    private Integer ltAdmissionTime;

    @Schema(description = "自动标签")
    private List<AutoTagDTO> autoTags;

    @Schema(description = "自动标签--偏好类")
    private List<AutoTagDTO> preferenceAutoTags;

    @Schema(description = "人群类型，0-自定义分区，1-生命周期分区")
    private GroupType groupType;

    @Schema(description = "排序字段，按照由小到大排序")
    private Integer sortNum;
}
