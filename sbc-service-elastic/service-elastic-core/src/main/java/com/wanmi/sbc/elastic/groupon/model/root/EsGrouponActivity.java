package com.wanmi.sbc.elastic.groupon.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>拼团活动信息表实体类</p>
 *
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Data
@Document(indexName = EsConstants.GROUPON_ACTIVITY)
public class EsGrouponActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    @Id
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
    @Enumerated
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
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

}
