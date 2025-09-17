package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.vo.GiftCardTradeCommitVO;
import com.wanmi.sbc.order.bean.vo.PickSettingInfoVO;
import com.wanmi.sbc.order.bean.vo.TradeItemGroupVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>客户端提交订单参数结构，包含除商品信息外的其他必要参数</p>
 * Created by of628-wenzhi on 2017-07-18-下午3:40.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeCommitRequest extends BaseRequest {

    private static final long serialVersionUID = -1555919128448507297L;

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

    /**
     * 门牌号
     */
    @Schema(description = "门牌号")
    private String houseNum;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal latitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal longitude;

    /**
     * 收货地址修改时间，可空
     */
    @Schema(description = "收货地址修改时间")
    private String consigneeUpdateTime;
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
    @Valid
    @NotEmpty
    @NotNull
    @Schema(description = "订单信息")
    private List<StoreCommitInfoDTO> storeCommitInfoList;

    /**
     * 选择的平台优惠券(通用券)id
     */
    @Schema(description = "选择的平台优惠券(通用券)id")
    private String commonCodeId;

    /**
     * 是否强制提交，用于营销活动有效性校验，true: 无效依然提交， false: 无效做异常返回
     */
    @Schema(description = "是否强制提交", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    public boolean forceCommit;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    /**
     * 下单用户
     */
    @Schema(description = "下单用户")
    private CustomerSimplifyOrderCommitVO customer;

    /**
     * 下单用户是否分销员
     */
    private DefaultFlag isDistributor = DefaultFlag.NO;

    @Override
    public void checkParam() {
        storeCommitInfoList.forEach(StoreCommitInfoDTO::checkParam);
    }

    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    @Schema(description = "订单来源")
    private OrderSource orderSource;

    /**
     * 分销渠道
     */
    @Schema(description = "分销渠道")
    private DistributeChannel distributeChannel;

    /**
     * 小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 平台分销设置开关
     */
    private DefaultFlag openFlag = DefaultFlag.NO;

    /**
     * 使用积分
     */
    @Schema(description = "使用积分")
    private Long points;

    /**
     * 分享人id
     */
    @Schema(description = "分享人id")
    private String shareUserId;

    /**
     * 尾款通知手机号
     */
    @Schema(description = "尾款通知手机号")
    private String tailNoticeMobile;

    /**
     * 是否是秒杀抢购商品订单
     */
    private Boolean isFlashSaleGoods;

    /**
     * 用户终端token用于区分同一用户存在多端登陆的情况
     */
    private String terminalToken;

    /**
     * 是否是预售商品定金
     */
    @Schema(description = "是否是预售商品定金")
    private Boolean isBookingSaleGoods = Boolean.FALSE;


    /**
     * 是否是尾款支付
     */
    @Schema(description = "是否是尾款支付")
    private Boolean isBookingSaleTailGoods = Boolean.FALSE;

    /**
     * 尾款支付订单号
     */
    @Schema(description = "尾款支付订单号")
    private String tid;

    /**
     * 订单营销信息快照
     */
    @Schema(description = "订单营销信息快照")
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 第三方平台 true:是，false:否
     */
    @Schema(description = "是否开启第三方平台")
    private Boolean isOpen;

    /**
     * 秒杀订单快照回传
     */
    @Schema(description = "订单组")
    private List<TradeItemGroupVO> tradeItemGroups;

    /** 自提数据 */
    @Schema(description = "自提数据")
    private List<PickSettingInfoVO> pickUpInfos;

    /***
     * 插件类型，用于路由
     */
    @Schema(description = "插件类型")
    private PluginType pluginType;

    /** 地域编码-多级中间用|分割 */
    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

    /**
     * 视频号标识
     */
    @Schema(description = "视频号标识")
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

    @Schema(description = "社区团购自提点ID")
    private String communityPickUpId;

    /**
     * 是否需要预约发货: 默认 0-不需要; 1-需要
     */
    private Integer appointmentShipmentFlag;

    /**
     * 预约发货时间
     */
    private String appointmentShipmentTime;
}
