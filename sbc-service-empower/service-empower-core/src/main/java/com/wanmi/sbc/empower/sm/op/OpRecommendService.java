package com.wanmi.sbc.empower.sm.op;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;
import com.wanmi.sbc.empower.api.response.sm.recommend.RecommendGoodsResponse;
import com.wanmi.sbc.empower.bean.enums.*;
import com.wanmi.sbc.empower.bean.vo.sm.recommend.RecommendPositionVO;
import com.wanmi.sbc.empower.sm.StratagemRecommendService;
import com.wanmi.sbc.empower.sm.op.request.OpBaseRequest;
import com.wanmi.sbc.empower.sm.op.request.OpRecommendGoodsRequest;
import com.wanmi.sbc.empower.sm.op.response.OpRecommendGoodsResponse;
import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @description  OP数谋智能推荐处理类
 * @author  wur
 * @date: 2022/11/17 10:46
 **/
@Slf4j
@Service
public class OpRecommendService implements StratagemRecommendService {

    @Autowired
    private StatisticsSettingProvider statisticsSettingProvider;

    private static final String CONTEXT = "context";

    /**
    *
     * @description
     * @author  wur
     * @date: 2022/11/17 10:47
     * @param request 
     * @return 
     **/
    @Override
    public BaseResponse<RecommendGoodsResponse> queryGoods(RecommendGoodsRequest request) {
        QmStatisticsSettingResponse config = this.queryConfig();
        RecommendGoodsResponse response = new RecommendGoodsResponse();
        if (Objects.isNull(config)) {
            return BaseResponse.success(response);
        }
        int tacticsType = TacticsType.HOT.toValue();
        // 非魔方则根据 则根据坑位查询具体的策略
        if (request.getPositionType() != PositionType.MAGIC_BOX.toValue() ) {
            RecommendPositionVO positionVO = this.getRecommendPosition(config, request.getPositionType());
            if (Objects.isNull(positionVO) || PositionOpenFlag.CLOSED.toValue() == positionVO.getIsOpen()) {
                return BaseResponse.success(new RecommendGoodsResponse());
            }
            tacticsType = positionVO.getTacticsType();
            // 用户兴趣推荐&&户未登录  则使用热门推荐
            if(positionVO.getTacticsType() == TacticsType.INTEREST.toValue() && StringUtils.isBlank(request.getCustomerId())) {
                tacticsType = TacticsType.HOT.toValue();
            }
            response.setPositionVO(positionVO);
        }
        // 查询商品信息
        OpRecommendGoodsRequest queryRequest = OpRecommendGoodsRequest.mapper(request);
        queryRequest.setTacticsType(tacticsType);
        OpBaseRequest opBaseRequest = new OpBaseRequest();
        opBaseRequest.setAppId(config.getAppKey());
        opBaseRequest.setData(JSON.toJSONString(queryRequest));
        opBaseRequest.setTimestamp(System.currentTimeMillis());
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(opBaseRequest), Map.class);
        List<String> goodsIdList = new ArrayList<>();
        try {
            String sign = OpSignUtil.sign(param, config.getAppSecret());
            opBaseRequest.setSign(sign);
            String params = JSON.toJSONString(opBaseRequest);
            log.debug("shumou post parms:{}", params);
            StringBuilder url = new StringBuilder(config.getApiUrl());
            url.append(OpApiConstant.QRY_RECOMMEND_GOODS_URL);
            String res = HttpUtils.post(url.toString(), params);
            log.debug("shumou post reponse:{}", res);
            if (res == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"数谋接口返回空值");
            }
            JSONObject obj = JSON.parseObject(res);
            if(obj.containsKey(CONTEXT)) {
                List<OpRecommendGoodsResponse> responseList = JSON.parseArray(obj.getString(CONTEXT), OpRecommendGoodsResponse.class);
                if (CollectionUtils.isNotEmpty(responseList)) {
                    goodsIdList = responseList.stream().map(OpRecommendGoodsResponse :: getSpuId).collect(Collectors.toList());
                }
            }
        } catch (IOException e) {
            log.error("数谋-商品查询接口调用异常失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"数谋接口调用异常");
        }
        response.setGoodsIdList(goodsIdList);
        return BaseResponse.success(response);
    }

    /**
     * @description   查询
     * @author  wur
     * @date: 2022/11/17 16:48
     * @param config
     * @param postingType
     * @return
     **/
    public RecommendPositionVO getRecommendPosition(QmStatisticsSettingResponse config, Integer postingType) {
        RecommendPositionVO recommendPositionVO = null;
        OpBaseRequest opBaseRequest = new OpBaseRequest();
        opBaseRequest.setAppId(config.getAppKey());
        opBaseRequest.setTimestamp(System.currentTimeMillis());
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(opBaseRequest), Map.class);
        try {
            String sign = OpSignUtil.sign(param, config.getAppSecret());
            opBaseRequest.setSign(sign);
            String params = JSON.toJSONString(opBaseRequest);
            log.debug("shumou post parms:{}", params);
            StringBuilder url = new StringBuilder(config.getApiUrl());
            url.append(OpApiConstant.QRY_RECOMMEND_POSITION_URL);
            String res = HttpUtils.post(url.toString(), params);
            log.debug("shumou post reponse:{}", res);
            if (res == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"数谋服务异常");
            }
            JSONObject obj = JSON.parseObject(res);
            if (obj.containsKey(CONTEXT)) {
                String context = obj.getString(CONTEXT);
                List<RecommendPositionVO> list =
                        JSON.parseArray(context, RecommendPositionVO.class);
                if (CollectionUtils.isNotEmpty(list)) {
                    recommendPositionVO =
                            list.stream()
                                    .filter(
                                            position ->
                                                    position.validatePositionType(postingType))
                                    .findFirst()
                                    .orElse(null);
                }
            }
        } catch (IOException e) {
            log.error("数谋-坑位接口调用异常失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"数谋接口调用异常");
        }
        return recommendPositionVO;
    }

    public QmStatisticsSettingResponse queryConfig() {
        QmStatisticsSettingResponse settingResponse =
                statisticsSettingProvider.getQmSetting().getContext();
        if (Objects.isNull(settingResponse) || Objects.isNull(settingResponse.getStatus()) || settingResponse.getStatus() == 0) {
            return null;
        }
        return settingResponse;
    }


    @Override
    public StratagemPlatformType getPlatformType() {
        return StratagemPlatformType.OP;
    }

    @Override
    public StratagemServiceType getServiceType() {
        return StratagemServiceType.RECOMMEND_SERVICE;
    }
}