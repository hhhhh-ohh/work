package com.wanmi.sbc.elastic.goodsevaluate.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2020/12/28 15:17
 * @description <p> 评价截图 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EsGoodsEvaluateImage {

    /**
     * 图片Id
     */
    private String imageId;

    /**
     * 评价id
     */
    private String evaluateId;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 图片KEY
     */
    private String imageKey;

    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 原图地址
     */
    private String artworkUrl;

    /**
     * 是否展示 0：否，1：是
     */
    private Integer isShow;

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
}
