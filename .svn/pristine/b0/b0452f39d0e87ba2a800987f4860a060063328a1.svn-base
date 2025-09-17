package com.wanmi.sbc.marketing.drawactivity.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.bean.RedisHsetBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerPointsAvailableByIdRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityQueryRequest;
import com.wanmi.sbc.marketing.bean.constant.DrawConstant;
import com.wanmi.sbc.marketing.bean.constant.DrawRedisKeyConstant;
import com.wanmi.sbc.marketing.bean.dto.DrawPrizeDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;
import com.wanmi.sbc.marketing.bean.vo.DrawDetail;
import com.wanmi.sbc.marketing.bean.vo.DrawResultVO;
import com.wanmi.sbc.marketing.bean.vo.WebPrizeVO;
import com.wanmi.sbc.marketing.coupon.repository.CouponActivityRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import com.wanmi.sbc.marketing.drawactivity.repository.DrawActivityRepository;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.model.root.RedisDrawPrize;
import com.wanmi.sbc.marketing.drawprize.repository.DrawPrizeRepository;
import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import com.wanmi.sbc.marketing.drawrecord.repository.DrawRecordRepository;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>抽奖活动表业务逻辑</p>
 *
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Service("DrawActivityService")
@Slf4j
public class DrawActivityService {
    @Autowired
    private DrawActivityRepository drawActivityRepository;

    @Autowired
    private DrawPrizeRepository drawPrizeRepository;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private DrawRecordRepository drawRecordRepository;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponActivityRepository couponActivityRepository;

    @Autowired
    private DrawGiveOutPrizesService drawGiveOutPrizesService;

    /**
     * 添加抽奖次数
     *
     * @author wwc
     */
    @Transactional
    public void addDrawCount(Long id) {
        drawActivityRepository.addDrawCount(id);
    }

    /**
     * 添加中奖次数
     *
     * @author wwc
     */
    @Transactional
    public void addAwardCount(Long id) {
        drawActivityRepository.addAwardCount(id);
    }


    /**
     * 暂停抽奖活动
     *
     * @param id
     * @return
     */
    @Transactional
    public Integer pauseDrawActivityById(Long id) {
        int result = 0;
        try {
            result = drawActivityRepository.pauseOrStartDrawActivity(id, 1);
            couponActivityRepository.pauseActivityByDrawActivityId(id);
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } finally {
            DrawActivity drawActivity = getById(id);
            drawActivity.setPauseFlag(1);
            redisService.hset(DrawRedisKeyConstant.getActivityKey(id),
                    DrawRedisKeyConstant.ACTIVITY_FIELD_NAME, JSONObject.toJSONString(drawActivity));
        }
        return result;
    }

    /**
     * 启动抽奖活动
     *
     * @param id
     * @return
     */
    @Transactional
    public int startDrawActivityById(Long id) {
        int result = 0;
        try {
            result = drawActivityRepository.pauseOrStartDrawActivity(id, 0);
            couponActivityRepository.startActivityByDrawActivityId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } finally {
            DrawActivity drawActivity = getById(id);
            drawActivity.setPauseFlag(0);
            redisService.hset(DrawRedisKeyConstant.getActivityKey(drawActivity.getId()),
                    DrawRedisKeyConstant.ACTIVITY_FIELD_NAME, JSONObject.toJSONString(drawActivity));
        }
        return result;
    }


    /**
     * 新增抽奖活动表
     *
     * @author wwc
     */
    @Transactional(rollbackFor = {Exception.class})
    public DrawActivity add(DrawActivityAddRequest drawActivityAddRequest) {
        DrawActivity entity = new DrawActivity();
        KsBeanUtil.copyPropertiesThird(drawActivityAddRequest, entity);
        List<DrawPrizeDTO> drawPrizeDTOS = drawActivityAddRequest.getPrizeDTOList();
        entity.setDelFlag(DeleteFlag.NO);
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreatePerson(drawActivityAddRequest.getUserId());
        entity.setAwardCount(Constants.NUM_0L);
        entity.setDrawCount(Constants.NUM_0L);
        entity.setPauseFlag(Constants.no);

        //校验抽奖活动参数
        verifyActivity(entity, drawPrizeDTOS);
        DrawActivity drawActivity = drawActivityRepository.save(entity);

        //最终入库的奖品信息
        List<DrawPrize> allDrawPrize = Lists.newArrayList();
        //保存奖品和奖品的关联关系
        drawPrizeDTOS.forEach(drawPrizeDTO -> {
            drawPrizeDTO.setActivityId(drawActivity.getId());
            drawPrizeDTO.setCreatePerson(entity.getCreatePerson());
            drawPrizeDTO.setCreateTime(entity.getCreateTime());
            DrawPrize drawPrize = KsBeanUtil.convert(drawPrizeDTO, DrawPrize.class);
            //保存奖品
            DrawPrize savePrize = drawPrizeRepository.save(drawPrize);
            allDrawPrize.add(savePrize);
        });

        //初始化redis信息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                initRedisActivity(drawActivity, allDrawPrize);
            }
        });
        return drawActivity;
    }

    /**
     * 删除活动相关缓存
     *
     * @param activityId
     */
    public void deleteActivityCache(Long activityId) {
        log.info("开始清空redis缓存，关联抽奖活动编号：{}", activityId);
        redisService.delete(DrawRedisKeyConstant.getActivityKey(activityId));
        log.info("清空redis缓存完成，关联抽奖活动编号：{}", activityId);
    }


    /**
     * 初始化活动相关数据到redis中
     *
     * @param drawActivity
     * @param allDrawPrize
     */
    public void initRedisActivity(DrawActivity drawActivity, List<DrawPrize> allDrawPrize) {
        log.info("开始初始化抽奖活动相关缓存，抽奖活动基本信息=========>{}", JSONObject.toJSONString(drawActivity));
        log.info("开始初始化抽奖活动相关缓存，活动奖品信息=========>{}", JSONObject.toJSONString(allDrawPrize));
        //活动距离当前时间
        Duration duration = Duration.between(LocalDateTime.now(), drawActivity.getEndTime());
        //活动距离当前的失效时间 + 7天 一周后redis失效
        Long expireTime = (duration.toMillis() + Duration.ofDays(7).toMillis());

        //活动存储信息
        List<RedisHsetBean> drawInfoList = Lists.newArrayList();

        //1.存储活动基本信息
        RedisHsetBean drawInfo = new RedisHsetBean();
        drawInfo.setField(DrawRedisKeyConstant.ACTIVITY_FIELD_NAME);
        String activityJson = JSONObject.toJSONString(drawActivity);
        drawInfo.setValue(activityJson);
        drawInfoList.add(drawInfo);

        //2.存储奖品信息
        RedisHsetBean awardInfo = new RedisHsetBean();
        awardInfo.setField(DrawRedisKeyConstant.PRIZE_LIST_FIELD_NAME);
        String prizeListJson = JSONObject.toJSONString(KsBeanUtil.convertList(allDrawPrize, RedisDrawPrize.class));
        awardInfo.setValue(prizeListJson);
        drawInfoList.add(awardInfo);
        //3.存储奖品库存信息
        allDrawPrize.stream().forEach(prize -> {
            RedisHsetBean awardStock = new RedisHsetBean();
            awardStock.setField(DrawRedisKeyConstant.PRIZE_FIELD_NAME + prize.getId());
            awardStock.setValue(prize.getPrizeNum().toString());
            drawInfoList.add(awardStock);
        });
        String activityKey = DrawRedisKeyConstant.getActivityKey(drawActivity.getId());
        redisService.hsetPipeline(activityKey, drawInfoList);
        redisService.expireByMilliseconds(activityKey, expireTime);

        log.info("完成初始化抽奖活动相关缓存，关联抽奖活动编号：{}", drawActivity.getId());
    }


    /**
     * 活动创建和编辑 参数校验
     *
     * @param entity
     * @param drawPrizeDTOS
     */
    public void verifyActivity(DrawActivity entity, List<DrawPrizeDTO> drawPrizeDTOS) {

        if (Objects.nonNull(entity.getId())) {
            //编辑判断活动时间是否已开始
            DrawActivity oldActivity = drawActivityRepository.findById(entity.getId()).orElse(null);
            if (Objects.nonNull(oldActivity) && DeleteFlag.NO.equals(oldActivity.getDelFlag())) {
                LocalDateTime now = LocalDateTime.now();
                //时间是进行中
                if (now.isAfter(oldActivity.getStartTime()) && now.isBefore(oldActivity.getEndTime())) {
                    //如果状态是进行中
                    if (NumberUtils.INTEGER_ZERO.equals(oldActivity.getPauseFlag())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080081);
                    }
                    //如果状态是已暂停
                    if (NumberUtils.INTEGER_ONE.equals(oldActivity.getPauseFlag())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080074);
                    }
                } else if (LocalDateTime.now().isAfter(oldActivity.getEndTime())) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
                }
            } else {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
            }
        }
        //可抽奖次数次数数据范围：仅限1-999之间的整数
        if (Objects.isNull(entity.getDrawTimes()) || DrawConstant.MAX_DRAW_COUNT.compareTo(entity.getDrawTimes()) < 0
                || DrawConstant.MIN_DRAW_COUNT.compareTo(entity.getDrawTimes()) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080068);
        }

        //校验奖品数量
        if (CollectionUtils.isEmpty(drawPrizeDTOS)
                || drawPrizeDTOS.size() > 7) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080065);
        }
        //校验奖品概率
        BigDecimal totalPercent = drawPrizeDTOS.stream().map(DrawPrizeDTO::getWinPercent)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        if (new BigDecimal(Constants.MAX_DRAW).compareTo(totalPercent) < 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080067);
        }

        if (entity.getStartTime().isAfter(entity.getEndTime()) || LocalDateTime.now().isAfter(entity.getEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080066);
        }
    }

    /**
     * 修改抽奖活动表
     *
     * @author wwc
     */
    @Transactional(rollbackFor = {Exception.class})
    public DrawActivity modify(DrawActivity entity, List<DrawPrizeDTO> drawPrizeDTOS) {
        verifyActivity(entity, drawPrizeDTOS);
        //编辑活动信息
        drawActivityRepository.update(entity);
        //最终入库的奖品信息
        List<DrawPrize> allDrawPrize = Lists.newArrayList();

        //处理奖品
        //查询奖品
        List<DrawPrize> oldDrawPrizes = drawPrizeRepository.findAllByActivityIdAndDelFlag(entity.getId(),
                DeleteFlag.NO);
        List<Long> oldPrizeIdList = oldDrawPrizes.stream().map(DrawPrize::getId).collect(Collectors.toList());
        //删除数据库有现在没有的奖品数据
        List<Long> nowPrizeIdList = drawPrizeDTOS.stream().map(DrawPrizeDTO::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(nowPrizeIdList)) {
            oldPrizeIdList.removeAll(nowPrizeIdList);
            if (CollectionUtils.isNotEmpty(oldPrizeIdList)) {
                drawPrizeRepository.deleteByIdList(oldPrizeIdList);
            }
        }
        //判断有id的直接
        drawPrizeDTOS.forEach(prizeDTO -> {
            //初始奖品创建/编辑信息
            if (Objects.nonNull(prizeDTO.getId())) {
                prizeDTO.setUpdateTime(entity.getUpdateTime());
                prizeDTO.setUpdatePerson(entity.getUpdatePerson());
            } else {
                prizeDTO.setCreateTime(entity.getUpdateTime());
                prizeDTO.setCreatePerson(entity.getUpdatePerson());
            }

            prizeDTO.setActivityId(entity.getId());
            DrawPrize drawPrize = KsBeanUtil.convert(prizeDTO, DrawPrize.class);
            //编辑奖品
            DrawPrize savePrize = drawPrizeRepository.save(drawPrize);
            allDrawPrize.add(savePrize);
        });
        //初始化redis信息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //删除缓存
                deleteActivityCache(entity.getId());
                //重置缓存
                initRedisActivity(entity, allDrawPrize);
            }
        });
        return entity;
    }

    /**
     * 单个删除抽奖活动表
     *
     * @author wwc
     */
    @Transactional
    public void deleteById(Long id) {
        DrawActivity drawActivity = getById(id);
        //未开始的才可以删除
        if (LocalDateTime.now().isBefore(drawActivity.getStartTime())) {
            drawActivityRepository.deleteById(id);
            //删除缓存
            deleteActivityCache(id);
        } else if (LocalDateTime.now().isAfter(drawActivity.getEndTime())) {//结束时间之后     抛出  活动已结束
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
        } else if (LocalDateTime.now().isAfter(drawActivity.getStartTime())
                && LocalDateTime.now().isBefore(drawActivity.getEndTime())) {//已经开始的活动并且未结束   抛出  活动已经开始
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080061);
        }
    }

    /**
     * 批量删除抽奖活动表
     *
     * @author wwc
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        ids.forEach(id -> deleteById(id));
    }

    /**
     * C端用户查询抽奖活动详情
     *
     * @author wwc
     */
    public DrawDetail detailForWeb(Long activityId, String customerId) {
        //获取活动的redis key
        String activityKey = DrawRedisKeyConstant.getActivityKey(activityId);
        DrawDetail drawDetail = new DrawDetail();
        //redis中不存在活动说明活动已经结束了
        if (!redisService.hasKey(activityKey)) {
            DrawActivity drawActivity = drawActivityRepository.findById(activityId).orElse(null);
            //数据库也不存在/被删除
            if (Objects.isNull(drawActivity) || DeleteFlag.YES.equals(drawActivity.getDelFlag())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
            }
            KsBeanUtil.copyProperties(drawActivity, drawDetail);
            //查询奖品集合
            List<DrawPrize> drawPrizes = drawPrizeRepository.findAllByActivityIdAndDelFlag(activityId, DeleteFlag.NO);
            List<RedisDrawPrize> redisDrawPrizes = KsBeanUtil.convertList(drawPrizes, RedisDrawPrize.class);
            //组装奖品数据返回
            drawDetail.setPrizeVOList(KsBeanUtil.convertList(dealAwardList(redisDrawPrizes), WebPrizeVO.class));
            //再次初始化活动和奖品信息7天失效
            initRedisActivity(drawActivity,drawPrizes);
            return drawDetail;
        }

        //获取redis活动基本信息
        String drawInfo = redisService.hget(activityKey, DrawRedisKeyConstant.ACTIVITY_FIELD_NAME);
        //返回活动基础信息
        DrawActivity drawActivity = null;
        if (StringUtils.isNotEmpty(drawInfo)) {
            drawActivity = JSONObject.parseObject(drawInfo, DrawActivity.class);
            drawDetail.setFormType(drawActivity.getFormType());
            drawDetail.setDrawType(drawActivity.getDrawType());
            drawDetail.setConsumePoints(drawActivity.getConsumePoints());
            drawDetail.setActivityContent(drawActivity.getActivityContent());
            drawDetail.setActivityName(drawActivity.getActivityName());
            drawDetail.setStartTime(drawActivity.getStartTime());
            drawDetail.setEndTime(drawActivity.getEndTime());
            drawDetail.setJoinLevel(drawActivity.getJoinLevel());
            drawDetail.setDrawTimesType(drawActivity.getDrawTimesType());
            drawDetail.setMaxAwardTip(drawActivity.getMaxAwardTip());
            drawDetail.setNotAwardTip(drawActivity.getNotAwardTip());
            drawDetail.setPauseFlag(drawActivity.getPauseFlag());
        }
        String prizeList = redisService.hget(activityKey,
                DrawRedisKeyConstant.PRIZE_LIST_FIELD_NAME);
        //返回活动的奖品信息
        if (StringUtils.isNotEmpty(prizeList)) {
            List<RedisDrawPrize> redisDrawPrizes = JSONArray.parseArray(prizeList, RedisDrawPrize.class);
            drawDetail.setPrizeVOList(KsBeanUtil.convertList(dealAwardList(redisDrawPrizes), WebPrizeVO.class));
        }

        //用户已登陆
        if (Objects.nonNull(customerId)) {
            //判断用户是否有权限参加该活动
            if (!Objects.equals(Constants.STR_MINUS_1,drawActivity.getJoinLevel())){
                CustomerGetByIdResponse customer = customerQueryProvider
                        .getCustomerById(CustomerGetByIdRequest.builder().customerId(customerId).build())
                        .getContext();
                if (Objects.isNull(customer) || Objects.equals(DeleteFlag.YES,customer.getDelFlag())){
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080064);
                }
                if (!Arrays.asList(drawActivity.getJoinLevel().split(",")).contains(customer.getCustomerLevelId().toString())){
                    drawDetail.setMaxAwardTip("您没有抽奖权限哦");
                    return drawDetail;
                }
            }
            String time = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_5);

            //redis活动用户key
            String customerKey = DrawRedisKeyConstant.getCustomerKey(activityId, customerId);
            //是否存在这个用户key信息
            boolean exist = redisService.hasKey(customerKey);
            if (DrawTimesType.PER_DAY.equals(drawActivity.getDrawTimesType())) {
                //抽奖次数按天计算的
                //当前查看页面时间
                String drawCount =
                        redisService.hget(customerKey,
                                DrawRedisKeyConstant.USER_DRAW_TODAY_COUNT + time);
                if (StringUtils.isNotEmpty(drawCount)) {
                    drawDetail.setLeftDrawCount(Long.parseLong(drawCount));
                } else {
                    redisService.hset(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_TODAY_COUNT + time,
                            String.valueOf(drawActivity.getDrawTimes()));
                    drawDetail.setLeftDrawCount(drawActivity.getDrawTimes().longValue());
                }
            } else if (DrawTimesType.PER_WEEK.equals(drawActivity.getDrawTimesType())){
                String yearMonth = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_9);
                String weekTime = yearMonth+DateUtil.getWeekByWed(LocalDateTime.now());
                //抽奖次数按周计算的
                String drawCount =
                        redisService.hget(customerKey,
                                DrawRedisKeyConstant.USER_DRAW_WEEK_COUNT + weekTime);
                if (StringUtils.isNotEmpty(drawCount)) {
                    drawDetail.setLeftDrawCount(Long.parseLong(drawCount));
                } else {
                    redisService.hset(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_WEEK_COUNT + weekTime,
                            String.valueOf(drawActivity.getDrawTimes()));
                    drawDetail.setLeftDrawCount(drawActivity.getDrawTimes().longValue());
                }
            }else if (DrawTimesType.PER_MONTH.equals(drawActivity.getDrawTimesType())){
                String monthTime = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_9);
                //抽奖次数按月计算的
                String drawCount =
                        redisService.hget(customerKey,
                                DrawRedisKeyConstant.USER_DRAW_MONTH_COUNT + monthTime);
                if (StringUtils.isNotEmpty(drawCount)) {
                    drawDetail.setLeftDrawCount(Long.parseLong(drawCount));
                } else {
                    redisService.hset(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_MONTH_COUNT + monthTime,
                            String.valueOf(drawActivity.getDrawTimes()));
                    drawDetail.setLeftDrawCount(drawActivity.getDrawTimes().longValue());
                }
            }else if (DrawTimesType.PER_PERSON.equals(drawActivity.getDrawTimesType())){
                //抽奖次数按活动计算的
                String drawCount =
                        redisService.hget(customerKey,
                                DrawRedisKeyConstant.USER_DRAW_ACTIVITY_COUNT);
                if (StringUtils.isNotEmpty(drawCount)) {
                    drawDetail.setLeftDrawCount(Long.parseLong(drawCount));
                } else {
                    redisService.hset(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_ACTIVITY_COUNT,
                            String.valueOf(drawActivity.getDrawTimes()));
                    drawDetail.setLeftDrawCount(drawActivity.getDrawTimes().longValue());
                }
            }

            //初始化用户总中奖次数（不存在中奖次数初始为0）
            if (StringUtils.isEmpty(redisService.hget(customerKey,
                    DrawRedisKeyConstant.USER_DRAW_TOTAL_AWARD))) {
                redisService.hset(customerKey,
                        DrawRedisKeyConstant.USER_DRAW_TOTAL_AWARD,
                        "0");
            }

            if (DrawWinTimesType.PER_PERSON_PER_DAY.equals(drawActivity.getWinTimesType())
                    && StringUtils.isEmpty(redisService.hget(customerKey,
                    DrawRedisKeyConstant.USER_DRAW_TODAY_AWARD + time))) {
                //初始化用户今日中奖次数（不存在中奖次数初始为0）
                redisService.hset(customerKey,
                        DrawRedisKeyConstant.USER_DRAW_TODAY_AWARD + time,
                        drawActivity.getWinTimes().toString());
            }else if (DrawWinTimesType.PER_PERSON_PER_FREQUENCY.equals(drawActivity.getWinTimesType())
                    && StringUtils.isEmpty(redisService.hget(customerKey,
                    DrawRedisKeyConstant.USER_DRAW_ACTIVITY_AWARD))){
                //初始化用户本次活动中奖次数（不存在中奖次数初始为0）
                redisService.hset(customerKey,
                        DrawRedisKeyConstant.USER_DRAW_ACTIVITY_AWARD,
                        drawActivity.getWinTimes().toString());
            }
            //之前未存在用户key信息设置过期时间
            if (!exist) {
                //活动距离当前时间
                Duration duration = Duration.between(LocalDateTime.now(), drawActivity.getEndTime());
                //活动距离当前的失效时间 + 7天
                Long expireTime = (duration.toMillis() + Duration.ofDays(7).toMillis());
                redisService.expireByMilliseconds(customerKey, expireTime);
            }
            //如果活动需要消耗积分，获取当前客户可用积分
            if (Objects.equals(DrawType.POINTS,drawDetail.getDrawType())){
                Long pointsAvailable = customerQueryProvider.getPointsAvailable(new CustomerPointsAvailableByIdRequest
                        (customerId)).getContext().getPointsAvailable();
                if (pointsAvailable == null) {
                    pointsAvailable = 0L;
                }
                drawDetail.setLeftPointsAvailable(pointsAvailable);
            }
        }
        //未登录不返回抽奖次数
        return drawDetail;
    }


    /**
     * 处理奖品顺序
     *
     * @param prizes
     * @return
     */
    public List<RedisDrawPrize> dealAwardList(List<RedisDrawPrize> prizes) {
        prizes = prizes.stream().sorted(Comparator.comparing(RedisDrawPrize::getId)).collect(Collectors.toList());
        RedisDrawPrize award = new RedisDrawPrize();
        award.setActivityId(prizes.get(0).getActivityId());
        award.setPrizeName("谢谢参与");
        //根据奖品数量返回奖品
        switch (prizes.size()) {
            case 1:
                return Lists.newArrayList(prizes.get(0), award, prizes.get(0), award, prizes.get(0), award,
                        prizes.get(0), award);
            case 2:
                return Lists.newArrayList(prizes.get(0), award, prizes.get(1), award, prizes.get(0), award,
                        prizes.get(1), award);
            case 3:
                return Lists.newArrayList(prizes.get(0), award, prizes.get(1), award, prizes.get(2), award,
                        prizes.get(0), award);
            case 4:
                prizes.add(1, award);
                prizes.add(3, award);
                prizes.add(5, award);
                prizes.add(7, award);
                return prizes;
            case 5:
                prizes.add(1, award);
                prizes.add(3, award);
                prizes.add(5, award);
                return prizes;
            case 6:
                prizes.add(1, award);
                prizes.add(3, award);
                return prizes;
            case 7:
                prizes.add(1, award);
                return prizes;
            default:
                return prizes;
        }


    }

    /**
     * C端用户点击抽奖返回结果
     *
     * @param activityId
     * @param customerId
     */
    @Transactional(rollbackFor = {Exception.class})
    public DrawResultVO lotteryDraw(Long activityId, String customerId, String terminalToken) {
        //加redis锁防止单个用户多次点击
        String userLockKey = DrawRedisKeyConstant.getUserLockKey(activityId, terminalToken);

        if (!redisService.setNx(userLockKey)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
        }

        try {
            Long lessDrawCount = null;
            CustomerGetByIdRequest customerGetByIdRequest = new CustomerGetByIdRequest();
            customerGetByIdRequest.setCustomerId(customerId);
            CustomerVO customer = customerQueryProvider.getCustomerById(customerGetByIdRequest).getContext();
            if (Objects.isNull(customer)) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080064);
            }

            //获取活动的redis key
            String activityKey = DrawRedisKeyConstant.getActivityKey(activityId);
            //获取redis活动信息
            String drawInfo = redisService.hget(activityKey, DrawRedisKeyConstant.ACTIVITY_FIELD_NAME);
            //redis不存在则活动已不存在
            if (StringUtils.isEmpty(drawInfo)) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
            }

            //返回活动基础信息
            DrawActivity drawActivity = JSONObject.parseObject(drawInfo, DrawActivity.class);
            //校验活动有效性
            checkActivity(drawActivity);

            //校验客户抽奖资质
            if (!Objects.equals(Constants.STR_MINUS_1,drawActivity.getJoinLevel())){
                if (!Arrays.asList(drawActivity.getJoinLevel().split(",")).contains(customer.getCustomerLevelId().toString())){
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080083);
                }
            }

            String customerKey = DrawRedisKeyConstant.getCustomerKey(activityId, customerId);
            boolean hasCustomerKey = redisService.hasKey(customerKey);
            //不存在用户key说明用户没有查看详情就开始抽奖了
            if (!hasCustomerKey) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }

            //如果活动需要消耗积分，校验客户剩余积分
            if (Objects.equals(DrawType.POINTS,drawActivity.getDrawType())){
                //获取活动积分
                Long consumePoints = drawActivity.getConsumePoints();
                //查询会员可用积分
                Long pointsAvailable = customerQueryProvider.getPointsAvailable(new CustomerPointsAvailableByIdRequest
                        (customerId)).getContext().getPointsAvailable();
                if (pointsAvailable == null) {
                    pointsAvailable = 0L;
                }
                //订单使用积分超出会员可用积分
                if (consumePoints.compareTo(pointsAvailable) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080084);
                }
            }

            String time = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_5);

            //操作抽奖次数字段
            String countFieldName = getCountFieldName(drawActivity, time);
            //判断抽奖次数字段是否存在,不存在异常提示
            String countFieldValue = redisService.hget(customerKey, countFieldName);
            if (StringUtils.isEmpty(countFieldValue)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            //抽奖次数达到上限
            lessDrawCount = redisService.hIncr(customerKey, countFieldName, -1);
            if (lessDrawCount < 0) {
                redisService.hIncr(customerKey,
                        countFieldName
                        , 1);
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080192, new Object[] {drawActivity.getMaxAwardTip()});
            }

            //按照每日
            if (!DrawWinTimesType.UNLIMITED.equals(drawActivity.getWinTimesType())){
                String todayAward = "";
                if (DrawWinTimesType.PER_PERSON_PER_DAY.equals(drawActivity.getWinTimesType())) {
                    //获取今日剩余中奖次数
                    todayAward = redisService.hget(customerKey, DrawRedisKeyConstant.USER_DRAW_TODAY_AWARD + time);
                    //今日中奖次数字段返回值为空，表示没有今日剩余中奖字段不存在 异常
                }else if (DrawWinTimesType.PER_PERSON_PER_FREQUENCY.equals(drawActivity.getWinTimesType())){
                    //获取本次活动剩余中奖次数
                    todayAward = redisService.hget(customerKey, DrawRedisKeyConstant.USER_DRAW_ACTIVITY_AWARD);
                }

                if (StringUtils.isBlank(todayAward)) {
                    //回滚抽奖次数
                    redisService.hIncr(customerKey, countFieldName, 1);
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
                //剩余中奖次数小于等于0说明不能中奖了
                if (Long.parseLong(todayAward) <= 0) {
                    return afterDeal(drawActivity, customer, new RedisDrawPrize());
                }
            }

            //redis奖品信息
            List<RedisDrawPrize> drawPrizes = null;
            String prizeList = redisService.hget(activityKey, DrawRedisKeyConstant.PRIZE_LIST_FIELD_NAME);
            if (StringUtils.isNotEmpty(prizeList)) {
                drawPrizes = JSONArray.parseArray(prizeList, RedisDrawPrize.class);
            }
            //开始进行抽奖算法开奖
            Long prizeId = luckyPrize(drawPrizes, drawActivity);
            //奖品是否可拿
            DrawResultVO resultVO = confirmIfAward(prizeId, drawActivity, drawPrizes, customer);
            resultVO.setLessDrawCount(lessDrawCount);
            //如果是积分或者优惠券奖品，直接发放至客户账号
            drawGiveOutPrizesService.giveOutPrizes(resultVO.getPrizeId(), customer.getCustomerId());
            return resultVO;
        }catch (Exception e){
            log.info("C端用户点击抽奖错误日志："+e);
            e.printStackTrace();
            throw e;
        }finally {
            redisService.delete(userLockKey);
        }
    }

    private String getCountFieldName(DrawActivity drawActivity, String time) {
        String countFieldName = "";
        switch (drawActivity.getDrawTimesType()){
            case PER_DAY:
                countFieldName = DrawRedisKeyConstant.USER_DRAW_TODAY_COUNT + time;
                break;
            case PER_WEEK:
                String yearMonth = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_9);
                String weekTime = yearMonth+DateUtil.getWeekByWed(LocalDateTime.now());
                countFieldName = DrawRedisKeyConstant.USER_DRAW_WEEK_COUNT + weekTime;
                break;
            case PER_MONTH:
                String monthTime = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_9);
                countFieldName = DrawRedisKeyConstant.USER_DRAW_MONTH_COUNT + monthTime;
                break;
            case PER_PERSON:
                countFieldName = DrawRedisKeyConstant.USER_DRAW_ACTIVITY_COUNT;
                break;
            default:
                break;
        }
        return countFieldName;
    }


    /**
     * 中奖后确认奖品是否可拿
     *
     * @param prizeId
     * @param drawActivity
     * @return
     */
    public DrawResultVO confirmIfAward(Long prizeId, DrawActivity drawActivity, List<RedisDrawPrize> drawPrizes,
                                       CustomerVO customer) {
        String time = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_5);
        String customerKey = DrawRedisKeyConstant.getCustomerKey(drawActivity.getId(), customer.getCustomerId());
        //是否中奖
        boolean getAwardFlag = false;
        String activityKey = DrawRedisKeyConstant.getActivityKey(drawActivity.getId());
        try {
            if (Objects.nonNull(prizeId)) {
                if (DrawWinTimesType.PER_PERSON_PER_DAY.equals(drawActivity.getWinTimesType())) {
                    //判断今日中奖次数是否超出
                    Long todayAward = redisService.hIncr(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_TODAY_AWARD + time, -1);
                    if (todayAward < 0) {
                        prizeId = null;
                    }
                }else if (DrawWinTimesType.PER_PERSON_PER_FREQUENCY.equals(drawActivity.getWinTimesType())){
                    //判断本次活动中奖次数是否超出
                    Long todayAward = redisService.hIncr(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_ACTIVITY_AWARD, -1);
                    if (todayAward < 0) {
                        prizeId = null;
                    }
                }

                //中奖库存是否不足
                if (redisService.hIncr(activityKey,
                        DrawRedisKeyConstant.PRIZE_FIELD_NAME + prizeId, -1) < 0) {
                    redisService.hIncr(activityKey,
                            DrawRedisKeyConstant.PRIZE_FIELD_NAME + prizeId, 1);
                    prizeId = null;
                } else {
                    //总中奖次数加1
                    redisService.hIncr(customerKey, DrawRedisKeyConstant.USER_DRAW_TOTAL_AWARD, 1);
                    getAwardFlag = true;
                }
            }
            //生成中奖记录
            Long finalPrizeId = prizeId;
            RedisDrawPrize drawPrize = null;
            if (Objects.nonNull(finalPrizeId)) {
                drawPrize = drawPrizes.stream()
                        .filter(prize -> prize.getId().equals(finalPrizeId))
                        .findFirst()
                        .orElse(null);
            }
            return afterDeal(drawActivity, customer, drawPrize);
        } catch (Exception e) {
            //抽奖次数回滚
            String countFieldName = getCountFieldName(drawActivity, time);
            //抽奖次数加1
            redisService.hIncr(customerKey, countFieldName, 1);

            if (getAwardFlag) {
                //中奖次数回滚
                redisService.hIncr(customerKey,
                        DrawRedisKeyConstant.USER_DRAW_TOTAL_AWARD, -1);
                if (DrawWinTimesType.PER_PERSON_PER_DAY.equals(drawActivity.getWinTimesType())) {
                    redisService.hIncr(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_TODAY_AWARD + time, 1);
                }else if (DrawWinTimesType.PER_PERSON_PER_FREQUENCY.equals(drawActivity.getWinTimesType())){
                    redisService.hIncr(customerKey,
                            DrawRedisKeyConstant.USER_DRAW_ACTIVITY_AWARD, 1);
                }
                //回滚奖品库存
                redisService.hIncr(activityKey, DrawRedisKeyConstant.PRIZE_FIELD_NAME + prizeId, 1);
            }
            throw e;
        }
    }


    /**
     * 后续处理
     *
     * @param drawActivity
     * @param customer
     * @param prize
     */
    public DrawResultVO afterDeal(DrawActivity drawActivity, CustomerVO customer, RedisDrawPrize prize) {
        DrawResultVO drawResultVO = new DrawResultVO();
        //新增抽奖记录
        DrawRecord drawRecord = new DrawRecord();
        drawRecord.setDrawStatus(NumberUtils.INTEGER_ZERO);
        drawRecord.setActivityId(drawActivity.getId());
        drawRecord.setDeliverStatus(NumberUtils.INTEGER_ZERO);
        drawRecord.setRedeemPrizeStatus(NumberUtils.INTEGER_ZERO);
        drawRecord.setDrawTime(LocalDateTime.now());
        drawRecord.setCustomerId(customer.getCustomerId());
        drawRecord.setCustomerName(customer.getCustomerDetail().getCustomerName());
        drawRecord.setCustomerAccount(customer.getCustomerAccount());
        drawRecord.setCreatePerson(customer.getCustomerId());
        drawRecord.setCreateTime(LocalDateTime.now());
        if (Objects.nonNull(prize) && Objects.nonNull(prize.getId())) {
            //返回奖品编号
            drawResultVO.setPrizeId(prize.getId());
            drawRecord.setDrawRecordCode(generateRecordCode());
            drawRecord.setDrawStatus(NumberUtils.INTEGER_ONE);
            drawRecord.setPrizeId(prize.getId());
            drawRecord.setPrizeName(prize.getPrizeName());
            drawRecord.setPrizeUrl(prize.getPrizeUrl());
            drawRecord.setPrizeType(prize.getPrizeType());
            if (Objects.equals(DrawPrizeType.POINTS,prize.getPrizeType())
                    || Objects.equals(DrawPrizeType.COUPON,prize.getPrizeType())){
                //优惠券/积分奖品直接领取
                drawRecord.setRedeemPrizeStatus(Constants.ONE);
                drawRecord.setDeliverStatus(Constants.ONE);
                drawRecord.setDeliveryTime(LocalDateTime.now());
            }
        }
        drawRecordRepository.save(drawRecord);
        //如果为积分抽奖则扣减积分
        if (Objects.equals(DrawType.POINTS,drawActivity.getDrawType())){
            customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                    .customerId(customer.getCustomerId())
                    .type(OperateType.DEDUCT)
                    .serviceType(PointsServiceType.DRAW_CONSUME)
                    .points(drawActivity.getConsumePoints())
                    .content(JSONObject.toJSONString(Collections.singletonMap("drawActivityId", drawActivity.getId())))
                    .build());
        }
        //丢入mq处理后续操作
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MARKET_DRAW_STOCK_SUB);
        mqSendDTO.setData(JSONObject.toJSONString(drawRecord));
        mqSendProvider.send(mqSendDTO);
        return drawResultVO;
    }

    /**
     * 抽奖算法获得奖品
     *
     * @param drawPrizeList
     * @return
     */
    private Long luckyPrize(List<RedisDrawPrize> drawPrizeList, DrawActivity drawActivity) {
        int lastScope = 0;
        //由于每次抽奖数值弹出是随机顺序，所以此处要求奖品必须保证顺序
        HashMap<String, int[]> prizeScopes = new HashMap<>();


        for (RedisDrawPrize prize : drawPrizeList) {
            String prizeId = prize.getId().toString();
            //划分抽奖区间
            //防止float相乘误差，转成BigDecimal类型进行抽奖概率放大
            BigDecimal winPercent = new BigDecimal(prize.getWinPercent().toString());
            BigDecimal multiScope = new BigDecimal(DrawConstant.MULTISOPE);
            //2500 5000
            int currentScope = lastScope + winPercent.multiply(multiScope).intValue();
            prizeScopes.put(prizeId, new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }
        //prizeScopes = 1,[1,2500] 2，[2501,5000]

        //在range数值范围内随机弹出一个数
        int luckyNum = luckyNum(drawActivity);
        Long luckyPrizeId = null;
        //查找随机数所在奖品区间，不重复校验为null
        Set<Map.Entry<String, int[]>> entrySets = prizeScopes.entrySet();
        for (Map.Entry<String, int[]> luckyDog : entrySets) {
            String key = luckyDog.getKey();
            if (luckyNum >= luckyDog.getValue()[0] && luckyNum <= luckyDog.getValue()[1]) {
                if (Objects.nonNull(key)) {
                    luckyPrizeId = Long.parseLong(key);
                }
                break;
            }
        }

        return luckyPrizeId;
    }

    /**
     * 从抽奖range池里随机弹出一个数值
     *
     * @return 返回[1, 10000]数字代表抽奖生效，-1=抽奖失败
     */
    private int luckyNum(DrawActivity activity) {
        //对活动id进行分布式锁，锁获取失败则抽奖失败
        RLock rLock = redissonClient.getFairLock(DrawRedisKeyConstant.getActivityLockKey(activity.getId()));
        try {
            rLock.tryLock(5, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
        try {
            List<Integer> luckyNumList = redisService.getList(DrawRedisKeyConstant.getDrawScope(activity.getId()),
                    Integer.class);
            //外层已经判断奖品是否有库存，所以这时直接增加抽奖range数值范围就好
            //每轮在redis中初始化1-10000数字，在里面随机弹出值，直到弹完10000个数后再进行初始化
            if (CollectionUtils.isEmpty(luckyNumList)) {
                luckyNumList = initLuckyMaxNum();
            }
            Duration duration = Duration.between(LocalDateTime.now(), activity.getEndTime());
            BigDecimal expireMins = new BigDecimal(duration.toMinutes());
            BigDecimal multi = new BigDecimal(60L);
            long expireSeconds = expireMins.multiply(multi).longValue();
            //随机弹出1-10000数字
            int luckyIndex = new Random().nextInt(luckyNumList.size());
            int luckyNum = luckyNumList.get(luckyIndex);
            luckyNumList.remove(luckyIndex);
            //并更新回redis，更新成功后返回中奖数字，否则返回-1，必然失败。
            if (redisService.setObj(DrawRedisKeyConstant.getDrawScope(activity.getId()), luckyNumList, expireSeconds)) {
                return luckyNum;
            }

            return -1;
        } finally {
            rLock.unlock();
        }

    }


    /**
     * 初始化抽奖range数值范围至redis
     *
     * @return
     */
    private List<Integer> initLuckyMaxNum() {
        List<Integer> initLuckyMaxNum = new ArrayList<>();
        //初始化单轮抽奖range数值范围
        for (int i = 1; i <= DrawConstant.RANDOM_RANGE; i++) {
            initLuckyMaxNum.add(i);
        }

        return initLuckyMaxNum;
    }


    /**
     * 校验抽奖活动有效性
     */
    public void checkActivity(DrawActivity drawActivity) {
        LocalDateTime now = LocalDateTime.now();
        //抽奖活动校验
        if (now.isBefore(drawActivity.getStartTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080071);
        }
        if (now.isAfter(drawActivity.getEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
        }
        if (NumberUtils.INTEGER_ONE.equals(drawActivity.getPauseFlag())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080074);
        }
    }

    /**
     * 生成奖品记录编号
     * P+年+月+日+四位随机数
     */
    private String generateRecordCode() {
        return DrawConstant.PREFIX_RECORD_CODE + DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_5) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 单个查询抽奖活动表
     *
     * @author wwc
     */
    public DrawActivity getById(Long id) {
        return drawActivityRepository.findById(id).orElse(null);
    }


    /**
     * 分页查询抽奖活动表
     *
     * @author wwc
     */
    public Page<DrawActivity> page(DrawActivityQueryRequest queryReq) {
        return drawActivityRepository.findAll(
                DrawActivityWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询抽奖活动表
     *
     * @author wwc
     */
    public List<DrawActivity> list(DrawActivityQueryRequest queryReq) {
        return drawActivityRepository.findAll(
                DrawActivityWhereCriteriaBuilder.build(queryReq),
                queryReq.getSort());
    }

    /**
     * 将实体包装成VO
     *
     * @author wwc
     */
    public DrawActivityVO wrapperVo(DrawActivity drawActivity) {
        if (drawActivity != null) {
            DrawActivityVO drawActivityVO = new DrawActivityVO();
            KsBeanUtil.copyPropertiesThird(drawActivity, drawActivityVO);
            return drawActivityVO;
        }
        return null;
    }

    /**
     * 关闭抽奖活动
     * @param id
     * @return
     */
    @Transactional
    public Integer closeById(Long id) {
        int result = 0;
        DrawActivity drawActivity = getById(id);
        try {
            drawActivity.setEndTime(LocalDateTime.now());
            drawActivityRepository.save(drawActivity);
            couponActivityRepository.closeActivityByDrawActivityId(id);
        } catch (Exception e) {
            log.error("关闭抽奖活动异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } finally {
            drawActivity.setEndTime(LocalDateTime.now());
            redisService.hset(DrawRedisKeyConstant.getActivityKey(id),
                    DrawRedisKeyConstant.ACTIVITY_FIELD_NAME, JSONObject.toJSONString(drawActivity));
        }
        return result;
    }
}
