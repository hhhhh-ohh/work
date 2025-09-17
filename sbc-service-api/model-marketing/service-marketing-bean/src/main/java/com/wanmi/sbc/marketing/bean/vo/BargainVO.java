package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.BargainStatus;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>砍价VO</p>
 *
 * @author
 * @date 2022-05-20 09:12:52
 */
@Schema
@Data
public class BargainVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainId
     */
    @Schema(description = "bargainId")
    private Long bargainId;

    /**
     * 砍价编号
     */
    @Schema(description = "砍价编号")
    private Long bargainNo;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    private Long bargainGoodsId;

    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    private String goodsInfoId;

    @Schema(description = "市场价")
    private BigDecimal marketPrice;


    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    /**
     * 发起时间
     */
    @Schema(description = "发起时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 发起人id
     */
    @Schema(description = "发起人id")
    private String customerId;

    /**
     * 发起人id
     */
    @Schema(description = "发起人账号")
    private String customerAccount;

    private String customerName;

    private String contactPhone;

    /**
     * 已砍人数
     */
    @Schema(description = "已砍人数")
    private Integer joinNum;

    /**
     * 目标砍价人数
     */
    @Schema(description = "目标砍价人数")
    private Integer targetJoinNum;

    /**
     * 已砍金额
     */
    @Schema(description = "已砍金额")
    private BigDecimal bargainedAmount;

    /**
     * 目标砍价金额
     */
    @Schema(description = "目标砍价金额")
    private BigDecimal targetBargainPrice;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    private Long storeId;

    /**
     * createTime
     */
    @Schema(description = "createTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "帮砍记录")
    private List<BargainJoinVO> bargainJoinVOs;

    @Schema(description = "砍价活动")
    private BargainGoodsVO bargainGoodsVO;

    @Schema(description = "砍价状态，0：进行中，1：砍价成功去下单，2：活动结束，3：订单已完成")
    private BargainStatus bargainStatus;

    @Schema(description = "是否是发启人标识")
    private Boolean createFlag = Boolean.FALSE;

    public BargainStatus getBargainStatus() {
        if (bargainStatus == null) {
            if (bargainGoodsVO != null && goodsInfoVO != null) {
                if(targetJoinNum.equals(joinNum) && StringUtils.isNotEmpty(orderId)) {
                    bargainStatus = BargainStatus.END;
                } else if (bargainGoodsVO.getLeaveStock() <= 0
                        || bargainGoodsVO.getStoped()
                        || Objects.equals(DeleteFlag.NO, bargainGoodsVO.getGoodsStatus())
                        || goodsInfoVO.getDelFlag().equals(DeleteFlag.YES)
                        || goodsInfoVO.getAddedFlag().equals(0)
                        || !goodsInfoVO.getAuditStatus().equals(CheckStatus.CHECKED)
                        || Objects.equals(Constants.no, goodsInfoVO.getVendibility(Boolean.TRUE))) {
                    bargainStatus = BargainStatus.STOP;
                } else if (bargainGoodsVO.getEndTime().isBefore(LocalDateTime.now())) {
                    bargainStatus = BargainStatus.STOP;
                } else if (targetJoinNum.equals(joinNum) ) {
                    bargainStatus = BargainStatus.COMPLETED;
                } else if (endTime.isBefore(LocalDateTime.now())) {
                    bargainStatus = BargainStatus.STOP;
                } else {
                    bargainStatus = BargainStatus.PROGRESS;
                }
            }
        }
        return bargainStatus;
    }

    public String getCustomerAccount() {
        if (StringUtils.isBlank(customerAccount)) {
            return customerAccount;
        }
        if (customerAccount.length() == 11) {
            return StringUtils.substring(customerAccount, 0, 3).concat("****").concat(StringUtils.substring(customerAccount, 7));
        } else {
            return StringUtils.substring(customerAccount, 0, 1).concat("****");
        }
    }
}