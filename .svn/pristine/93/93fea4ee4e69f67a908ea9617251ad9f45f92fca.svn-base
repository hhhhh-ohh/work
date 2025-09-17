package com.wanmi.sbc.empower.wechat.transfer;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class TransferToUserRequest {
    @SerializedName("appid")
    public String appid;

    @SerializedName("out_bill_no")
    public String outBillNo;

    @SerializedName("transfer_scene_id")
    public String transferSceneId;

    @SerializedName("openid")
    public String openid;

    @SerializedName("user_name")
    public String userName;

    @SerializedName("transfer_amount")
    public Long transferAmount;

    @SerializedName("transfer_remark")
    public String transferRemark;

    @SerializedName("notify_url")
    public String notifyUrl;

    @SerializedName("user_recv_perception")
    public String userRecvPerception;

    @SerializedName("transfer_scene_report_infos")
    public List<TransferSceneReportInfo> transferSceneReportInfos;
}