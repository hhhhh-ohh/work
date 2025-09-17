package com.wanmi.sbc.elastic.goods.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = EsConstants.ES_GOODS_BRAND)
public class EsGoodsBrand implements Serializable {

    /**
     * 品牌编号
     */
    @Id
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 拼音
     */
    private String pinYin;

    /**
     * 简拼
     */
    private String sPinYin;

    /**
     * 店铺id(平台默认为0)
     */
    private Long storeId = 0L;

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
     * 删除标志
     */
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 品牌别名
     */
    private String nickName;


    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 首字母
     */
    private String first;

    /**
     * 是否推荐品牌
     */
    private DefaultFlag recommendFlag;

    /**
     * 品牌排序
     */
    private Long brandSort;
}
