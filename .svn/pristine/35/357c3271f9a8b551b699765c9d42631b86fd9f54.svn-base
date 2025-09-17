package com.wanmi.sbc.returnorder.request;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.ReturnReason;
import com.wanmi.sbc.order.bean.enums.ReturnWay;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 修改退单请求
 * Created by jinwei on 25/4/2017.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemedyReturnRequest extends BaseRequest {

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    private String rid;

    /**
     * 退货原因
     */
    @Schema(description = "退货原因")
    private ReturnReason returnReason;

    /**
     * 退货方式
     */
    @Schema(description = "退货方式")
    private ReturnWay returnWay;

    /**
     * 退货说明
     */
    @Schema(description = "退货说明")
    private String description;

    /**
     * 附件信息
     */
    @Schema(description = "附件信息")
    private List<String> images;

    /**
     * 商品数量修改
     */
    @Schema(description = "商品数量修改")
    private List<ReturnItemNum> returnItemNums;

    /**
     * 修改申请价格
     */
    @Schema(description = "修改申请价格")
    private ReturnPriceRequest returnPriceRequest;

    /**
     * 修改申请积分
     */
    @Schema(description = "修改积分")
    private ReturnPointsRequest returnPointsRequest;

    /**
     * 退货原因（新，可维护）
     */
    @Schema(description = "退货原因（新，可维护）")
    private RefundCauseVO refundCause;

    /**
     * 退单的卖家备注
     */
    @CanEmpty
    @Length(max = 500)
    @Schema(description = "卖家备注")
    private String sellerRemark;

}
