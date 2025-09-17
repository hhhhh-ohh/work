package com.wanmi.sbc.recommend;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.recommend.request.RecommendStatisticsRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.recommend.RecommendQueryProvider;
import com.wanmi.sbc.setting.api.provider.recommendcate.RecommendCateQueryProvider;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByPageCodeRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendPageRequest;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateListRequest;
import com.wanmi.sbc.setting.api.response.RecommendStatusConfigResponse;
import com.wanmi.sbc.setting.api.response.recommend.RecommendByIdResponse;
import com.wanmi.sbc.setting.api.response.recommend.RecommendPageResponse;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateListResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;


@Tag(name =  "种草列表及统计", description =  "RecommendController")
@RestController
@Validated
@RequestMapping(value = "/recommend")
public class RecommendController {

    @Autowired
    private RecommendQueryProvider recommendQueryProvider;

    @Autowired
    private RecommendCateQueryProvider recommendCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;


    /**
     * 分页查询种草信息表
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询种草信息表")
    @PostMapping("/page")
    public BaseResponse<RecommendPageResponse> getPage(@RequestBody @Valid RecommendPageRequest pageReq) {
        validateRecommendStatus();
        // 查询置顶或非置顶列表数据
        if(Objects.nonNull(pageReq.getIsTop())){
            if (Constants.ONE == pageReq.getIsTop()){
                pageReq.putSort("topTime",SortType.DESC.toValue());
            } else if (Constants.ZERO == pageReq.getIsTop()){
                pageReq.putSort("updateTime",SortType.DESC.toValue());
            }
        }else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        pageReq.setDelFlag(DeleteFlag.NO);
        // 保存状态 1:草稿 2:已发布 3:修改已发布
        List<Integer> saveStatusList = Lists.newArrayList();
        saveStatusList.add(Constants.TWO);
        saveStatusList.add(Constants.THREE);
        pageReq.setSaveStatusList(saveStatusList);
        // 内容状态 0:隐藏 1:公开
        pageReq.setStatus(Constants.ONE);
        return recommendQueryProvider.page(pageReq);
    }

    /**
     * 校验种草内容开关
     * @return
     */
    private void validateRecommendStatus() {
        Integer recommendStatus = auditQueryProvider.config().getContext().getRecommendStatus();
        if (Objects.nonNull(recommendStatus) && recommendStatus == Constants.ZERO) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070100);
        }
    }

    /**
     * 列表查询种草分类
     * @param listReq
     * @return
     */
    @Operation(summary = "列表查询种草分类表")
    @PostMapping("/cate/list")
    public BaseResponse<RecommendCateListResponse> getList(@RequestBody @Valid RecommendCateListRequest listReq) {
        validateRecommendStatus();
        listReq.setDelFlag(DeleteFlag.NO);
        return recommendCateQueryProvider.list(listReq);
    }

    /**
     * 种草开关设置
     * @return
     */
    @Operation(summary = "种草开关设置")
    @GetMapping("/config")
    public BaseResponse<RecommendStatusConfigResponse> config() {
        return BaseResponse.success(auditQueryProvider.config().getContext());
    }

    /**
     * 根据pageCode查询种草信息表
     * @return
     */
    @Operation(summary = "根据pageCode查询种草信息表")
    @GetMapping("/{pageCode}")
    public BaseResponse<RecommendByIdResponse> getByPageCode(@PathVariable String pageCode) {
        validateRecommendStatus();
        if (pageCode == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String customerId = commonUtil.getOperatorId();
        RecommendByPageCodeRequest request = new RecommendByPageCodeRequest();
        request.setPageCode(pageCode);
        request.setUserId(customerId);
        if (Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            request.setIsBoss(Boolean.TRUE);
        }
        return recommendQueryProvider.getByPageCode(request);
    }

    /**
     * 种草内容统计
     * @return
     */
    @Operation(summary = "种草内容统计")
    @PostMapping("/statistics")
    public BaseResponse statistics(@Valid @RequestBody RecommendStatisticsRequest recommendStatisticsRequest) {
        validateRecommendStatus();
        String pageCode = recommendStatisticsRequest.getPageCode();
        String customerId = commonUtil.getOperatorId();
        //校验种草内容是否存在
        RecommendByPageCodeRequest request = new RecommendByPageCodeRequest();
        request.setPageCode(pageCode);
        request.setUserId(customerId);
        recommendQueryProvider.getByPageCode(request);
        // 非点赞、非转发，统计PV、UV
        if (Objects.isNull(recommendStatisticsRequest.getThumbsUpFlag())&&Objects.isNull(recommendStatisticsRequest.getForwardFlag())){
            String pvKey = CacheKeyConstant.RECOMMEND_PV_KEY.concat(pageCode);
            // 统计PV
            redisUtil.incrKey(pvKey);
            if (StringUtils.isNotBlank(customerId)){
                String uvKey = CacheKeyConstant.RECOMMEND_UV_KEY.concat(pageCode);
                // 统计UV
                stringRedisTemplate.opsForSet().add(uvKey, customerId);
            }
        }
        // 统计点赞
        BoolFlag thumbsUpFlag = recommendStatisticsRequest.getThumbsUpFlag();
        if (StringUtils.isNotBlank(customerId) && Objects.nonNull(thumbsUpFlag)){
            String thumbsUpKey = CacheKeyConstant.RECOMMEND_THUMBS_UP_KEY.concat(pageCode);
            if (BoolFlag.YES == thumbsUpFlag){
                stringRedisTemplate.opsForSet().add(thumbsUpKey, customerId);
            } else if(BoolFlag.NO == thumbsUpFlag){
                stringRedisTemplate.opsForSet().remove(thumbsUpKey, customerId);
            }
        }
        // 统计转发
        BoolFlag forwardFlag = recommendStatisticsRequest.getForwardFlag();
        if (Objects.nonNull(forwardFlag)){
            String forwardKey = CacheKeyConstant.RECOMMEND_FORWARD_KEY.concat(pageCode);
            redisUtil.incrKey(forwardKey);
        }
        // 五分钟同步一次数据库
        String recommendSyncMysqlKey = CacheKeyConstant.RECOMMEND_SYNC_MYSQL_KEY.concat(pageCode);
        if (!redisUtil.hasKey(recommendSyncMysqlKey)){
            // 存取五分钟
            redisUtil.setString(recommendSyncMysqlKey, "1", Constants.FIVE*Constants.NUM_60);
            // 发送延时队列同步数据库
            sendRecommendSyncMysql(recommendStatisticsRequest.getPageCode());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 五分钟同步一次数据库-开启延迟消息
     * @param pageCode
     */
    public void sendRecommendSyncMysql(String pageCode){
        //发送延时队列，五分钟同步一次数据库
        Long millis = Constants.FIVE * Constants.NUM_60 * Constants.NUM_1000L;
        MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
        mqSendDelayDTO.setTopic(ProducerTopic.RECOMMEND_SYNC_MYSQL);
        mqSendDelayDTO.setData(pageCode);
        mqSendDelayDTO.setDelayTime(millis);
        mqSendProvider.sendDelay(mqSendDelayDTO);
    }

    /**
     * 获取某个内容的小程序码
     * @return
     */
    @RequestMapping(value = "/getQrCode/{pageCode}", method = RequestMethod.GET)
    public BaseResponse<String> getRecommendQrCode(@PathVariable String pageCode) {
        validateRecommendStatus();
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        // 小程序跳转链接
        String codeUrl = "?pageType=recommend&pageCode=".concat(pageCode);
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));
        //  存入redis，前端调用使用
        if (StringUtils.isNotBlank(code)) {
            redisUtil.setString(code, codeUrl, 15000000L);
        }
        // 组装获取小程序码参数
        request.setPage("pages/sharepage/sharepage");
        request.setScene("TF"+code);
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }
}
