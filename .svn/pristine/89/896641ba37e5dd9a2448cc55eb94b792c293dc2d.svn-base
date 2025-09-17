package com.wanmi.sbc.goods.api.request.linkedmall;

import com.alibaba.fastjson2.JSONArray;
import com.wanmi.sbc.common.base.BaseRequest;
import com.alibaba.fastjson2.JSONObject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SyncItemRequest extends BaseRequest {

    private static final long serialVersionUID = 4890940402800824616L;

    @Schema(description = "子渠道ID")
    private String subBizId;
    @Schema(description = "请求标识")
    private String requestId;
    @Schema(description = "客户业务 ID")
    private String bizId;
    @Schema(description = "商品列表json")
    private String itemList;
    @Schema(description = "签名")
    private String signature;

    private String signatureMethod;

    public List<LinkedMallItem> getItemListEntity() {
        return JSONArray.parseArray(itemList, LinkedMallItem.class);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LinkedMallItem {
        private Boolean canSell;
        private Long categoryId;
        private Long itemId;
        /***
         * 扩展字段
         **/
        private String extJson;
        /***
         * 时间戳
         **/
        private Long gmtModified;
        private String itemTitle;
        private Long lmShopId;
        private String mainPicUrl;
        /***
         * 划线价
         **/
        private Long reservePrice;
        /***
         * 卖家ID
         **/
        private Long sellerId;
        private List<LinkedMallSku> skuList;
        private String taobaoShopName;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class LinkedMallSku {
            private Boolean canSell;
            /***
             * 销售价格（分）
             **/
            private Long priceCent;
            private Long skuId;
            private String skuTitle;
            private String skuPicUrl;
        }
    }
}
