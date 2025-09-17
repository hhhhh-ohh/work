package com.wanmi.sbc.elastic.api.request.communityleader;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王超
 *  分页查询社区团长信息
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsCommunityLeaderPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "token")
    private String token;

    /**
     * 批量查询-社区团长标识UUIDList
     */
    @Schema(description = "批量查询-社区团长标识")
    private List<String> leaderIdList;

    /**
     * 社区团长标识UUID
     */
    @Schema(description = "社区团长标识UUID")
    private String leaderId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String leaderName;

    /**
     * 会员登录账号|手机号
     */
    @Schema(description = "会员登录账号|手机号")
    private String leaderAccount;

    /**
     * 模糊-团长账号
     */
    @Schema(description = "模糊-团长账号")
    private String likeLeaderAccount;

    /**
     * 模糊-团长名称
     */
    @Schema(description = "模糊团长名称")
    private String likeLeaderName;

    /**
     * 是否帮卖 0:否 1:是
     */
    @Schema(description = "是否帮卖 0:否 1:是")
    private Integer assistFlag;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;




    /**
     * 是否删除标志 0：否，1：是
     */
    @Schema(description = "是否删除标志")
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 批量查询-社区团长标识UUIDList
     */
    @Schema(description = "批量查询-社区团长标识")
    private List<String> idList;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;

}