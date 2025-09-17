package com.wanmi.sbc.vas.api.request.linkedmall.signature;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class SyncItemDeletionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "子渠道编码")
    private String subBizId;
    @Schema(description = "签名")
    private String signature;
    @Schema(description = "请求标识")
    private String requestId;
    @Schema(description = "客户业务 ID")
    private String bizId;
    @Schema(description = "签名方法")
    private String signatureMethod;
    @Schema(description = "商品列表")
    private String itemIdList;

    public List<LinkedMallDeletionItem> getItemIdListEntity(){
        return JSONArray.parseArray(itemIdList, LinkedMallDeletionItem.class);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LinkedMallDeletionItem implements Serializable {
        private static final long serialVersionUID = 1L;
//        时间戳
        private long gmt_create;
        private Long itemId;
        private String lmItemId;
        private List<Long> skuIdList;
    }

}
