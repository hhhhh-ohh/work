package com.wanmi.sbc.flashsale.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.vo.GiftCardTradeCommitVO;
import com.wanmi.sbc.order.bean.vo.PickSettingInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @ClassName FlashSaleTradeCommitRequest
 * @Description 秒杀抢购活动Request请求类
 * @Author xufeng
 * @Date 2021/7/9 17:38
 **/
@Schema
@Data
public class FlashSaleTradeCommitRequest extends BaseRequest {

    private static final long serialVersionUID = 1738395979653043783L;
    /**
     * 抢购商品Id
     */
    @Schema(description = "抢购商品Id")
    @NotNull
    private Long flashSaleGoodsId;

    /**
     * 抢购商品数量
     */
    @Schema(description = "抢购商品数量")
    private Integer flashSaleGoodsNum;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 秒杀商品会员抢购次数
     */
    @Schema(description = "秒杀商品会员抢购次数")
    private Integer flashSaleNum;

    @Schema(description = "终端登陆token")
    private String terminalToken;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * 订单收货地址id，必传
     */
    @Schema(description = "订单收货地址id")
    private String consigneeId;

    /**
     * 收货地址详细信息(包含省市区)，必传
     */
    @Schema(description = "收货地址详细信息(包含省市区)")
    private String consigneeAddress;

    @Valid
    @NotEmpty
    @NotNull
    @Schema(description = "订单信息")
    private List<StoreCommitInfoDTO> storeCommitInfoList;

    /**
     * 身份证号码18位 冗余长度防止没有trim
     */
    @Schema(description = "身份证号码18位")
    private String customerCardNo;
    /**
     * 身份证姓名
     */
    @Schema(description = "身份证姓名")
    private String customerCardName;

    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    @Schema(description = "订单来源")
    private OrderSource orderSource;

    /**
     * 是否视频号订单标识
     */
    @Schema(description = "是否视频号订单标识")
    private Boolean isChannelsFlag = Boolean.FALSE;

    /**
     * 场景值
     */
    @Schema(description = "场景值")
    private Integer sceneGroup;

    /**
     * 礼品卡使用详情
     */
    @Schema(description = "礼品卡使用详情")
    private List<GiftCardTradeCommitVO> giftCardTradeCommitVOList;

    /** 自提数据 */
    @Schema(description = "自提数据")
    private List<PickSettingInfoVO> pickUpInfos;
}
