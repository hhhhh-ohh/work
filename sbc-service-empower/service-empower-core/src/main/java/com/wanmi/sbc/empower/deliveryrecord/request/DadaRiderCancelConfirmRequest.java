package com.wanmi.sbc.empower.deliveryrecord.request;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaMessageNoticeRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/***
 * 达达骑手取消确认操作
 * @className DadaRiderCancelConfirmRequest
 * @author zhengyang
 * @date 2022/1/18 15:48
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DadaRiderCancelConfirmRequest extends DadaBaseRequest {

    /***
     * 订单号
     * 必填
     */
    @JSONField(name = "orderId")
    private String orderId;

    /***
     * 是否确认退货
     * 0:不同意，1:表示同意
     * 必填
     */
    @JSONField(name = "isConfirm")
    private int isConfirm;

    /***
     * 达达订单号
     * 非必填
     */
    @JSONField(name = "dadaOrderId")
    private Long dadaOrderId;

    /***
     * 审核不通过的图片列表
     * 非必填
     */
    @JSONField(name = "imgs")
    private List<String> imgs;

    /***
     * 拒绝原因
     * 非必填
     */
    @JSONField(name = "rejectReason")
    private String rejectReason;

    @Override
    public Object toJson() {
        DeliveryRecordDadaMessageNoticeRequest<String> request = new DeliveryRecordDadaMessageNoticeRequest();
        // 骑士取消确认默认为1
        request.setMessageType(1);
        request.setMessageBody(StringEscapeUtils.unescapeJavaScript(JSON.toJSONString(this)));
        return JSON.toJSONString(request);
    }
}
