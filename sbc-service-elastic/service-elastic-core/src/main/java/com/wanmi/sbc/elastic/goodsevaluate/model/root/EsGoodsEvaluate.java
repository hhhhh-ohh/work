package com.wanmi.sbc.elastic.goodsevaluate.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/28 15:00
 * @description <p> 商品评价 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(indexName = EsConstants.GOODS_EVALUATE)
public class EsGoodsEvaluate {

    /**
     * 评价id
     */
    @Id
    private String evaluateId;

    /**
     * 店铺Id
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 商品id(spuId)
     */
    private String goodsId;

    /**
     * 货品id(skuId)
     */
    private String goodsInfoId;

    /**
     * 商品名称
     */
    private String goodsInfoName;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品规格信息
     */
    private String specDetails;

    /**
     * 购买时间
     */

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime buyTime;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 会员Id
     */
    private String customerId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 会员登录账号|手机号
     */
    private String customerAccount;

    /**
     * 商品评分
     */
    private Integer evaluateScore;

    /**
     * 商品评价内容
     */
    private String evaluateContent;

    /**
     * 发表评价时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime evaluateTime;

    /**
     * 评论回复
     */
    private String evaluateAnswer;

    /**
     * 回复时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime evaluateAnswerTime;

    /**
     * 回复人账号
     */
    private String evaluateAnswerAccountName;

    /**
     * 回复员工Id
     */
    private String evaluateAnswerEmployeeId;

    /**
     * 历史商品评分
     */
    private Integer historyEvaluateScore;

    /**
     * 历史商品评价内容
     */
    private String historyEvaluateContent;

    /**
     * 历史发表评价时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime historyEvaluateTime;

    /**
     * 历史评论回复
     */
    private String historyEvaluateAnswer;

    /**
     * 历史回复时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime historyEvaluateAnswerTime;

    /**
     * 历史回复人账号
     */
    private String historyEvaluateAnswerAccountName;

    /**
     * 历史回复员工Id
     */
    private String historyEvaluateAnswerEmployeeId;

    /**
     * 点赞数
     */
    private Integer goodNum;

    /**
     * 是否匿名 0：否，1：是
     */
    private Integer isAnonymous;

    /**
     * 是否已回复 0：否，1：是
     */
    private Integer isAnswer;

    /**
     * 是否已经修改 0：否，1：是
     */
    private Integer isEdit;

    /**
     * 是否展示 0：否，1：是
     */
    private Integer isShow;

    /**
     * 是否晒单 0：否，1：是
     */
    private Integer isUpload;

    /**
     * 是否删除标志 0：否，1：是
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    /**
     * 删除人
     */
    private String delPerson;

    private List<EsGoodsEvaluateImage> goodsEvaluateImages;

    /**
     * 是否系统评价 0：否，1：是
     */
    private Integer isSys;

    /**
     * 商家类型
     */
    private StoreType storeType;
}
