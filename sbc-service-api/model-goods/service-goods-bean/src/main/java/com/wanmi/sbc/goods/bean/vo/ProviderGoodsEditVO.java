package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author wur
 * @className ProviderGoodsEditVO
 * @description TODO
 * @date 2021/9/14 16:44
 **/
@Schema
@Data
public class ProviderGoodsEditVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品Id")
    private String id;

    /**
     * 商品Id
     */
    @Schema(description = "商品Id")
    private String goodsId;

    /**
     * 操作类型：操作类型：0.商品信息变更 1.价格变更 2.状态变更 3.其他变更
     */
    @Schema(description = "操作类型：操作类型：0.商品信息变更 1.价格变更 2.状态变更 3.其他变更")
    private GoodsEditType enditType;

    /**
     * 修改内容
     */
    @Schema(description = "修改内容")
    private String enditContent;

    @Schema(description = "更新时间")
    @LastModifiedDate
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

}