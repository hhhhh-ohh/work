package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.PopupStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>弹窗管理VO</p>
 * @author weiwenhao
 * @date 2020-04-21
 */
@Schema
@Data
public class PopupAdministrationVO extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long popupId;

    /**
     * 弹窗名称
     */
    @Schema(description = "弹窗名称")
    private String popupName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 弹窗url
     */
    @Schema(description = "弹窗url")
    private String popupUrl;

    /**
     * 应用页面
     */
    @Schema(description = "应用页面")
    private String applicationPageName;

    /**
     * 跳转页
     */
    @Schema(description = "跳转页")
    private String jumpPage;

    /**
     * 投放频次
     */
    @Schema(description = "投放频次")
    private String launchFrequency;


    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;


    /**
     * 修改时间
      */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 弹窗状态
     */
    @Schema(description = "是否暂停（1：暂停，0：正常）")
    private BoolFlag isPause;

    /**
     * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
     */
    @Schema(description = "查询类型")
    private PopupStatus popupStatus;

    /**
     * 是否删除 0 否  1 是
     */
    @Schema(description = "是否删除 0 否  1 是")
    private DeleteFlag delFlag;

    /**
     * 获取活动状态
     * @return
     */
    public PopupStatus getPopupStatus() {
        if (beginTime != null && endTime != null) {
            if (LocalDateTime.now().isBefore(beginTime)) {
                return PopupStatus.NOT_START;
            } else if (LocalDateTime.now().isAfter(endTime)) {
                return PopupStatus.ENDED;
            } else if (isPause == BoolFlag.YES) {
                return PopupStatus.PAUSED;
            } else {
                return PopupStatus.STARTED;
            }
        }
        return null;
    }
}
