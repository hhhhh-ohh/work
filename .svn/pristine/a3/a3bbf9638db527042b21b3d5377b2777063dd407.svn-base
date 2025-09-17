package com.wanmi.sbc.empower.provider.impl.wechatwaybill;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.ThirdLoginRelationVO;
import com.wanmi.sbc.empower.api.provider.wechatwaybill.WechatWaybillProvider;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogQueryRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.TraceWaybillRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.WechatWaybillStatusRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.WechatWaybillTokenRequest;
import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import com.wanmi.sbc.empower.logisticslog.service.LogisticsLogService;
import com.wanmi.sbc.empower.wechat.WechatApiUtil;
import com.wanmi.sbc.empower.wechat.constant.WechatApiConstant;
import com.wanmi.sbc.empower.wechatwaybill.service.WechatWaybillService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListCriteriaRequest;
import com.wanmi.sbc.order.api.response.trade.TradeListCriteriaResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;


/**
 * <p>微信物流信息服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@RestController
@Validated
@Slf4j
public class WechatWaybillController implements WechatWaybillProvider {
    @Autowired
    private WechatWaybillService wechatWaybillService;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private WechatApiUtil wechatApiUtil;

    @Autowired
    private LogisticsLogService logisticsLogService;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Override
    public BaseResponse testGetWaybillToken() {
//        String accessToken = getAccessToken();
//
//        String openid = "ocwFu5CLumJKczqweU7Fd9V-B9q4";
//        String waybill_id = "78714804358366";
//        String receiver_phone = "18516337459";
//        TraceWaybillRequest.GoodsInfo goods_info = new TraceWaybillRequest.GoodsInfo();
//        List<TraceWaybillRequest.GoodsDetail> detail_list = new ArrayList<>();
//
//        TraceWaybillRequest.GoodsDetail goodsDetail = new TraceWaybillRequest.GoodsDetail();
//        goodsDetail.setGoods_name("蓝牙充电款-新升级时间管理器蓝色");
////        goodsDetail.setGoods_img_url("70de3a2c-e328-4003-b450-5179ee25fe19");
//        goodsDetail.setGoods_img_url("https://swdxpt-mmbxtest.oss-cn-beijing.aliyuncs.com/f05ffd4e2f77df74c37d92912b880da6.jpg");
//        detail_list.add(goodsDetail);
//
//        goods_info.setDetail_list(detail_list);
//
//        String trans_id = "4200001930202308188259838343";
//
//        // 组装参数
//        TraceWaybillRequest traceWaybillRequest = TraceWaybillRequest.builder()
//                .openid(openid)
//                .waybill_id(waybill_id)
//                .receiver_phone(receiver_phone)
//                .goods_info(goods_info)
//                .trans_id(trans_id)
//                .build();
//
//        // 再获取  waybill_token
//        String waybillTokens = wechatWaybillService.traceWaybill(accessToken, traceWaybillRequest);


        String accessToken = getAccessToken();

        String openid = "o7MEC7gEebczMKkgkDTM3qGS_BH4";
        String waybill_id = "JD0189961597228";
        String receiver_phone = "15070918544";
        TraceWaybillRequest.GoodsInfo goods_info = new TraceWaybillRequest.GoodsInfo();
        List<TraceWaybillRequest.GoodsDetail> detail_list = new ArrayList<>();

        TraceWaybillRequest.GoodsDetail goodsDetail = new TraceWaybillRequest.GoodsDetail();
        goodsDetail.setGoods_name("护手霜1");
//        goodsDetail.setGoods_img_url("70de3a2c-e328-4003-b450-5179ee25fe19");
        goodsDetail.setGoods_img_url("https://swdxpt-mmbxtest.oss-cn-beijing.aliyuncs.com/274cd0dfcd1c9e8fc693da688fc5ab2e.jpeg");
        detail_list.add(goodsDetail);

        goods_info.setDetail_list(detail_list);

        String trans_id = "4200002714202506092289692262";

        // 组装参数
        TraceWaybillRequest traceWaybillRequest = TraceWaybillRequest.builder()
                .openid(openid)
                .waybill_id(waybill_id)
                .receiver_phone(receiver_phone)
                .goods_info(goods_info)
                .trans_id(trans_id)
                .build();

        // 再获取  waybill_token
        String waybillTokens = wechatWaybillService.traceWaybill(accessToken, traceWaybillRequest);

        return BaseResponse.success(waybillTokens);
    }

    @Override
    public BaseResponse testGetWaybillStatus() {
        String accessToken = getAccessToken();

        String openid = "o7MEC7gEebczMKkgkDTM3qGS_BH4";
        String waybill_id = "JD0189961597228";
        String receiver_phone = "15070918544";
        TraceWaybillRequest.GoodsInfo goods_info = new TraceWaybillRequest.GoodsInfo();
        List<TraceWaybillRequest.GoodsDetail> detail_list = new ArrayList<>();

        TraceWaybillRequest.GoodsDetail goodsDetail = new TraceWaybillRequest.GoodsDetail();
        goodsDetail.setGoods_name("护手霜1");
//        goodsDetail.setGoods_img_url("70de3a2c-e328-4003-b450-5179ee25fe19");
        goodsDetail.setGoods_img_url("https://swdxpt-mmbxtest.oss-cn-beijing.aliyuncs.com/274cd0dfcd1c9e8fc693da688fc5ab2e.jpeg");
        detail_list.add(goodsDetail);

        goods_info.setDetail_list(detail_list);

        String trans_id = "4200002714202506092289692262";

        // 组装参数
        TraceWaybillRequest traceWaybillRequest = TraceWaybillRequest.builder()
                .openid(openid)
                .waybill_id(waybill_id)
                .receiver_phone(receiver_phone)
                .goods_info(goods_info)
                .trans_id(trans_id)
                .build();

        // 再获取  waybill_token
        String waybillTokens = wechatWaybillService.traceWaybill(accessToken, traceWaybillRequest);

        // 再获取  status
        Integer status = wechatWaybillService.queryTraceStatus(accessToken, openid, waybillTokens);

        return BaseResponse.success(status);
    }

    public String getAccessToken(){

        String accessToken = "";

//        String appId = "wxc8830266d4d5e23a";
//        String appSecret = "abbb4cf8afb3cc6da00959d899f11d00";

        String appId = "wxae41611b52e955f3";
        String appSecret = "761aa32878a4cbe650c96a18894c789b";

        try {
            String url = String.format(WechatApiConstant.ACCESS_TOKEN_URL, WechatApiConstant.GRANT_TYPE, appId, appSecret);
            String accessTokenResStr = wechatApiUtil.doGet(url);
            log.info("获取access_token接口url:{}，返回结果：{}",url,accessTokenResStr);
            if (StringUtils.isNotEmpty(accessTokenResStr)) {
                JSONObject tokenResJson = JSONObject.parseObject(accessTokenResStr);
                accessToken = tokenResJson.getString("access_token");
            }
        } catch (Exception e){
            log.error("获取access_token参数接口异常：", e);
            return accessToken;
        }
        return accessToken;
    }



    @Override
    public BaseResponse getWaybillToken(WechatWaybillTokenRequest wechatWaybillTokenRequest) {

        // 先从缓存取
        String redisKey = RedisKeyConstant.WAYBILL_DETAIL_CACHE + wechatWaybillTokenRequest.getOrderNo();
        String waybillToken = redisService.getString(redisKey);
        if (StringUtils.isNotEmpty(waybillToken)) {
            return BaseResponse.success(waybillToken);
        }

        // 缓存没查到，接口调用
        // 查询订单信息
        TradeQueryDTO tradeQueryRequest = new TradeQueryDTO();
        tradeQueryRequest.setId(wechatWaybillTokenRequest.getOrderNo());


        TradeListCriteriaResponse tradeListCriteriaResponse = tradeQueryProvider.listCriteria(TradeListCriteriaRequest.builder()
                .tradePageDTO(tradeQueryRequest)
                .build()).getContext();

        // 校验返回结果
        if (CollectionUtils.isEmpty(tradeListCriteriaResponse.getTradeList())) {
            log.error("未获取到订单信息");
            return BaseResponse.success("");
        }
        TradeVO tradeVO = tradeListCriteriaResponse.getTradeList().get(0);

        // 组装参数
        String customerId = tradeVO.getBuyer().getId();
        String openid = tradeVO.getBuyer().getThirdLoginOpenId();
        String waybill_id = wechatWaybillTokenRequest.getWaybillId();
        String receiver_phone = tradeVO.getBuyer().getPhone();
        TraceWaybillRequest.GoodsInfo goods_info = assembleGoodsInfo(tradeVO);
        String trans_id = tradeVO.getTradeNo();

        // 校验参数
        if (StringUtils.isEmpty(openid)) {
            // 通过customId查询用户信息
            ThirdLoginRelationVO phoneRelation = thirdLoginRelationQueryProvider
                    .listThirdLoginRelationByCustomer(
                            ThirdLoginRelationByCustomerRequest.builder()
                                    .customerId(customerId)
                                    .thirdLoginType(ThirdLoginType.WECHAT)
                                    .delFlag(DeleteFlag.NO)
                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                    .build()
                    ).getContext().getThirdLoginRelation();
            if (Objects.isNull(phoneRelation)) {
                log.error("未获取到用户openid");
                return BaseResponse.success("");
            }
            String thirdLoginOpenId = phoneRelation.getThirdLoginOpenId();
            if (StringUtils.isEmpty(thirdLoginOpenId)) {
                log.error("未获取到用户openid");
                return BaseResponse.success("");
            }
            openid = thirdLoginOpenId;
        }
        if (StringUtils.isEmpty(waybill_id)) {
            log.info("运单号错误");
            return BaseResponse.success("");
        }
        if (StringUtils.isEmpty(receiver_phone)) {
            log.error("未获取到收件人手机号");
            return BaseResponse.success("");
        }
        if (CollectionUtils.isEmpty(goods_info.getDetail_list())) {
            log.error("未获取到订单商品信息");
            return BaseResponse.success("");
        }
        if (StringUtils.isEmpty(trans_id)) {
            log.error("未获取到订单交易流水号");
            return BaseResponse.success("");
        }
        // 组装参数
        TraceWaybillRequest traceWaybillRequest = TraceWaybillRequest.builder()
                .openid(openid)
                .waybill_id(waybill_id)
                .receiver_phone(receiver_phone)
                .goods_info(goods_info)
                .trans_id(trans_id)
                .build();

        // 先获取 accessToken
        String accessToken = wechatWaybillService.getAccessToken();
        // 再获取  waybill_token
        String waybillTokens = wechatWaybillService.traceWaybill(accessToken, traceWaybillRequest);
        // 再获取  waybill_token
        if (StringUtils.isEmpty(waybillTokens)) {
            log.error("未获取到运单号");
            return BaseResponse.success("");
        }

        // 存redis数据库
        redisService.setString(redisKey, waybillTokens);

        return BaseResponse.success(waybillTokens);
    }

    @Override
    public BaseResponse getWaybillStatus(WechatWaybillStatusRequest wechatWaybillStatusRequest) {


        // 查询订单信息
        TradeQueryDTO tradeQueryRequest = new TradeQueryDTO();
        tradeQueryRequest.setId(wechatWaybillStatusRequest.getOrderNo());


        TradeListCriteriaResponse tradeListCriteriaResponse = tradeQueryProvider.listCriteria(TradeListCriteriaRequest.builder()
                .tradePageDTO(tradeQueryRequest)
                .build()).getContext();

        // 校验返回结果
        if (CollectionUtils.isEmpty(tradeListCriteriaResponse.getTradeList())) {
            log.error("未获取到订单信息");
            return BaseResponse.success("");
        }
        TradeVO tradeVO = tradeListCriteriaResponse.getTradeList().get(0);

        // 组装参数
        // 组装参数
        String customerId = tradeVO.getBuyer().getId();
        String openid = tradeVO.getBuyer().getThirdLoginOpenId();


        // 校验参数
        if (StringUtils.isEmpty(openid)) {
            // 通过customId查询用户信息
            ThirdLoginRelationVO phoneRelation = thirdLoginRelationQueryProvider
                    .listThirdLoginRelationByCustomer(
                            ThirdLoginRelationByCustomerRequest.builder()
                                    .customerId(customerId)
                                    .thirdLoginType(ThirdLoginType.WECHAT)
                                    .delFlag(DeleteFlag.NO)
                                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                    .build()
                    ).getContext().getThirdLoginRelation();
            if (Objects.isNull(phoneRelation)) {
                log.error("未获取到用户openid");
                return BaseResponse.success("");
            }
            String thirdLoginOpenId = phoneRelation.getThirdLoginOpenId();
            if (StringUtils.isEmpty(thirdLoginOpenId)) {
                log.error("未获取到用户openid");
                return BaseResponse.success("");
            }
            openid = thirdLoginOpenId;
        }
        if (StringUtils.isEmpty(wechatWaybillStatusRequest.getWaybill_token())) {
            log.error("未获取查询id");
            return BaseResponse.success("");
        }

        // 先获取 accessToken
        String accessToken = wechatWaybillService.getAccessToken();
        // 调用运单轨迹查询接口
        Integer traceStatus = wechatWaybillService.queryTraceStatus(accessToken,
                openid, wechatWaybillStatusRequest.getWaybill_token());
        if (-1 == traceStatus) {
            return BaseResponse.success("");
        }

        return BaseResponse.success(traceStatus);
    }


    private TraceWaybillRequest.GoodsInfo assembleGoodsInfo(TradeVO tradeVO) {
        TraceWaybillRequest.GoodsInfo goodsInfo = new TraceWaybillRequest.GoodsInfo();
        List<TraceWaybillRequest.GoodsDetail> detail_list = new ArrayList<>();
        tradeVO.getTradeItems().forEach(tradeItem -> {
            TraceWaybillRequest.GoodsDetail goodsDetail = new TraceWaybillRequest.GoodsDetail();
            goodsDetail.setGoods_name(tradeItem.getSkuName());
            goodsDetail.setGoods_img_url((tradeItem.getPic()));
            detail_list.add(goodsDetail);
        });

        goodsInfo.setDetail_list(detail_list);
        return goodsInfo;
    }
}
