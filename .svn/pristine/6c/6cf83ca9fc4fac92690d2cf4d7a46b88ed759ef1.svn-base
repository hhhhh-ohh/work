package com.wanmi.sbc.order.api.response.payorder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Data
@Schema
public class FindPayOrderResponse extends BasicResponse {

    private static int STR_LENGTH = 4;
    private static Pattern NUMBER_PATERN = Pattern.compile("[^0-9]");

    /**
     * 支付单Id
     */
    @Schema(description = "支付单Id")
    private String payOrderId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;


    /**
     * 支付类型
     */
    @Schema(description = "支付类型" )
    private PayType payType;

    /**
     * 付款状态
     */
    @Schema(description = "支付单状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String comment;

    /**
     * 收款单账号
     * 收款账户前端展示 农业银行6329***7791 支付宝支付189**@163.com
     */
    @Schema(description = "收款单账号")
    private String receivableAccount;

    /**
     * 收款单账号
     * 收款账户前端正常直接展示
     */
    @Schema(description = "收款单账号")
    private String normalReceivableAccount;


    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String receivableNo;

    /**
     * 收款金额
     */
    @Schema(description = "收款金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付单积分
     */
    private Long payOrderPoints;

    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private BigDecimal totalPrice;

    /**
     * 收款时间
     */
    @Schema(description = "收款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime receiveTime;

    /**
     * 收款在线渠道
     */
    @Schema(description = "收款在线渠道")
    private String payChannel;

    @Schema(description = "收款在线渠道id")
    private Long payChannelId;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 附件
     */
    @Schema(description = "附件")
    private String encloses;

    /**
     * 团编号
     */
    private String grouponNo;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 是否平台自营
     */
    @Schema(description = "是否平台自营",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isSelf;

    /**
     * 应付积分
     */
    @Schema(description = "应付积分")
    private Long buyPoints;

    /**
     * 应付礼品卡
     */
    @Schema(description = "应付礼品卡")
    private BigDecimal giftCardPrice;

    /**
     * 流程状态
     */
    @Schema(description = "流程状态")
    private FlowState flowState;

    /**
     * 商品idList
     */
    @Schema(description = "商品idList")
    private List<String> goodsIdList;

    @Schema(description = "订单取消时间，如果为空或小于等于0则不提醒")
    private Long cancelTime;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型 0：供应商，1：商家，2：o2o直营店")
    private StoreType storeType;

    /**
     * 是否发券
     */
    @Schema(description = "是否发券")
    private Boolean sendCouponFlag = false;

    /**
     * 支付成功广告页
     */
    @Schema(description = "支付成功广告页")
    private PayAdvertisementVO payAdvertisementVO;

    /**
     * 收款账户脱敏 example :622000000000008888  -->  6220**********8888
     * @return
     */
    public String getReceivableAccount(){
        if(StringUtils.isNotEmpty(receivableAccount) && !StringUtils.equals("-", receivableAccount)){
            int postion = receivableAccount.indexOf(' ');
            //提取银行卡号
            String bankAccount = NUMBER_PATERN.matcher(receivableAccount).replaceAll("");
            //脱敏
            if(bankAccount.length() > STR_LENGTH) {
                bankAccount = bankAccount.substring(0, 4) + "**********" + bankAccount.substring(bankAccount.length() - 4);
            }
            receivableAccount = receivableAccount.substring(0,postion) + " " + bankAccount;
        }
        return receivableAccount;
    }
}
