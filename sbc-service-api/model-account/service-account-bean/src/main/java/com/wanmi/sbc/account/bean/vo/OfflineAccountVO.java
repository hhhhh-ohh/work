package com.wanmi.sbc.account.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 线下账户entity
 * Created by zhangjin on 2017/3/19.
 */
@Schema
@Data
public class OfflineAccountVO extends BasicResponse {

    private static final long serialVersionUID = 4404926179814661342L;
    /**
     * 主键
     */
    @Schema(description = "线下账户id")
    private Long accountId;


    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息id")
    private Long companyInfoId;

    /**
     * 是否收到平台首次打款 0、否 1、是
     */
    @Schema(description = "是否收到平台首次打款")
    @NotNull
    private DefaultFlag isReceived = DefaultFlag.NO;

    /**
     * 是否主账号 0、否 1、是
     */
    @Schema(description = "是否主账号")
    @NotNull
    private DefaultFlag isDefaultAccount = DefaultFlag.NO;

    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String accountName;

    /**
     * 开户银行
     */
    @Schema(description = "开户银行")
    private String bankName;

    /**
     * 支行
     */
    @Schema(description = "支行")
    private String bankBranch;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @SensitiveWordsField(signType = SignWordType.BANKNO)
    private String bankNo;

    /**
     * 账号状态 0: 启用 1:禁用
     */
    @Schema(description = "账号状态", contentSchema = com.wanmi.sbc.account.bean.enums.AccountStatus.class)
    private Integer bankStatus;

    /**
     * 第三方店铺id
     */
    @Schema(description = "第三方店铺id")
    private String thirdId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime update_time;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
}
