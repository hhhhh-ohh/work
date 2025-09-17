package com.wanmi.sbc.elastic.systemresource.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * 资源素材
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = EsConstants.SYSTEM_RESOURCE)
public class EsSystemResource implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 素材资源ID
     */
    @Id
    private Long resourceId;

    /**
     * 资源类型(0:图片,1:视频)
     */
    @Enumerated
    @Field(type = FieldType.Keyword)
    private ResourceType resourceType;

    /**
     * 素材分类ID
     */
    @Field(type = FieldType.Keyword)
    private Long cateId;

    /**
     * 店铺标识
     */
    @Field(type = FieldType.Keyword)
    private Long storeId;

    /**
     * 商家标识
     */
    @Field(type = FieldType.Keyword)
    private Long companyInfoId;

    /**
     * 素材KEY
     */
    @Field(type = FieldType.Keyword)
    private String resourceKey;

    /**
     * 素材名称
     */
    @Field(type = FieldType.Keyword)
    private String resourceName;

    /**
     * 素材地址
     */
    @Field(type = FieldType.Keyword)
    private String artworkUrl;

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
     * 删除标识,0:未删除1:已删除
     */
    @Enumerated
    @Field(type = FieldType.Keyword)
    private DeleteFlag delFlag;

    /**
     * oss服务器类型，对应system_config的config_type
     */
    @Field(type = FieldType.Keyword)
    private String serverType;

    /**
     * 排序
     */
    @Field(type = FieldType.Keyword)
    private Integer sort;



}
