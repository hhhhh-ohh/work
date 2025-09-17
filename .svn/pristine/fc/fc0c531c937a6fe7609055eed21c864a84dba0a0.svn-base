package com.wanmi.sbc.elastic.distributionmatter.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.goods.bean.enums.MatterType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * 分销素材实体类
 * distribution_goods_matter
 *
 * @author houshuai
 */
@Document(indexName = EsConstants.DISTRIBUTION_GOODS_MATTER)
@Data
public class EsDistributionGoodsMatter {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 素材类型 0 图片 1 视频
     */
    @Enumerated
    private MatterType matterType;

    /**
     * 素材
     */
    private String matter;

    /**
     * 推荐语
     */
    private String recommend;

    /**
     * 推荐次数
     */
    private Integer recommendNum;

    /**
     * 发布者id
     */
    private String operatorId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标志
     */
    @Enumerated
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 商品信息
     */
    private EsObjectGoodsInfo esObjectGoodsInfo;


}
