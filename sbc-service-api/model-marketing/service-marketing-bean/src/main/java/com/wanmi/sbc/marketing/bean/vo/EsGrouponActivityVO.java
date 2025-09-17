package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author houshuai
 * @date 2020/12/12 18:25
 * @description <p> 拼团信息 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EsGrouponActivityVO extends BasicResponse {

    private static final long serialVersionUID = -7536704871451727635L;
    /**
     * 活动ID
     */
    private String grouponActivityId;

    /**
     * 拼团人数
     */
    private Integer grouponNum;

    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 拼团分类ID
     */
    private String grouponCateId;

    /**
     * 是否自动成团
     */
    private boolean autoGroupon;

    /**
     * 是否包邮
     */
    private boolean freeDelivery;

    /**
     * spu编号
     */
    private String goodsId;

    /**
     * spu编码
     */
    private String goodsNo;

    /**
     * spu商品名称
     */
    private String goodsName;

    /**
     * 店铺ID
     */
    private String storeId;

    /**
     * 是否精选
     */
    private boolean sticky;

    /**
     * 活动审核状态，0：待审核，1：审核通过，2：审核不通过
     */
    private AuditStatus auditStatus;

    /**
     * 审核不通过原因
     */
    private String auditFailReason;

    /**
     * 已成团人数
     */
    private Integer alreadyGrouponNum = NumberUtils.INTEGER_ZERO;

    /**
     * 待成团人数
     */
    private Integer waitGrouponNum = NumberUtils.INTEGER_ZERO;

    /**
     * 团失败人数
     */
    private Integer failGrouponNum = NumberUtils.INTEGER_ZERO;

    /**
     * 是否删除，0：否，1：是
     */
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 预热时间
     */
    private Integer preTime;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

}
