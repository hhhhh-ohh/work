package com.wanmi.sbc.vas.api.request.linkedmall.signature;

import com.alibaba.fastjson2.JSONArray;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ModificationItemRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "子渠道ID")
    private String subBizId;
    @Schema(description = "请求标识")
    private String requestId;
    @Schema(description = "客户业务 ID")
    private String bizId;
    @Schema(description = "商品列表")
    private String itemList;
    @Schema(description = "签名")
    private String signature;

    private String signatureMethod;

    public List<LinkedMallItemModification> getItemListEntity(){
        return JSONArray.parseArray(itemList, LinkedMallItemModification.class);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LinkedMallItemModification {
        private Boolean canSell;
//        扩展字段
        private String extJson;
//        时间戳
        private Long gmtModified;
        private Long itemId;
        private String lmItemId;
        private List<LinkedMallSku> skuList;
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class LinkedMallSku {
             private Boolean canSell;
             private long points;
             private long pointsAmount;
//             "销售价格（分）
             private Long priceCent;
             private Long skuId;

         }
    }

}
