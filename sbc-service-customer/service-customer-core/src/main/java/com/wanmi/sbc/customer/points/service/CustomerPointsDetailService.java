package com.wanmi.sbc.customer.points.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailBatchAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailQueryRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsExpireResponse;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsStatisticsResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerPointsDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerStatisticsPointsVO;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.repository.CustomerDetailRepository;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.points.model.root.CustomerPointConstant;
import com.wanmi.sbc.customer.points.model.root.CustomerPointsDetail;
import com.wanmi.sbc.customer.points.repository.CustomerPointsDetailRepository;
import com.wanmi.sbc.customer.repository.CustomerRepository;
import com.wanmi.sbc.customer.service.CustomerService;
import com.wanmi.sbc.setting.api.provider.PointsBasicRuleQueryProvider;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>会员积分明细业务逻辑</p>
 */
@Service("CustomerPointsDetailService")
@Slf4j
public class CustomerPointsDetailService {
    @Autowired
    private CustomerPointsDetailRepository customerPointsDetailRepository;

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private PointsBasicRuleQueryProvider pointsBasicRuleQueryProvider;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    private RedissonClient redissonClient;



    private static final String POINTS_ISSUE_STATISTICS_KEY = "CUSTOMER:POINTS:ISSUE_STATISTICS";

    private static final String POINTS_AVAILABLE_STATISTICS_KEY = "CUSTOMER:POINTS:AVAILABLE_STATISTICS";

    /**
     * 新增会员积分明细
     */
    @Transactional
    public void increasePoints(CustomerPointsDetailAddRequest request, ConfigType configType) {
        // 查询积分体系开关
        boolean isPointsOpen = systemPointsConfigQueryProvider.isPointsOpen().getContext().isOpen();
        if (!isPointsOpen) return;// 未启用积分体系

        //获取当前积分统计总额
        CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();

        CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
        KsBeanUtil.copyPropertiesThird(request, pointsDetail);
        if (Objects.isNull(request.getOpTime())) {
            pointsDetail.setOpTime(LocalDateTime.now());
        }

        Long pointsIncrease = request.getPoints();
        if (pointsIncrease != null) {
            // 不为空则是订单完成获取的积分
            pointsDetail.setPoints(request.getPoints());
        } else {
            // 查询该类型积分基础规则
            List<ConfigVO> configs = pointsBasicRuleQueryProvider.listPointsBasicRule().getContext().getConfigVOList();
            ConfigVO config = configs.stream().filter(configVO -> configVO.getConfigType().equals(configType.toValue()))
                    .findFirst().orElse(null);
            if (Objects.isNull(config) || config.getStatus() == 0) return;// 该积分类型未开启

            JSONObject context = JSON.parseObject(config.getContext());
            if (StringUtils.isBlank(context.get("value").toString())) return;// 积分规则为空

            pointsIncrease = context.getLong("value");// 本次增长的积分

            // 查询会员已获取的同类型积分明细
            CustomerPointsDetailQueryRequest queryReq = new CustomerPointsDetailQueryRequest();
            queryReq.setCustomerId(request.getCustomerId());
            queryReq.setType(OperateType.GROWTH);
            queryReq.setServiceType(request.getServiceType());
            List<CustomerPointsDetail> pointsList = customerPointsDetailRepository.findAll(
                    CustomerPointsDetailWhereCriteriaBuilder.build(queryReq));

            if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                // 绑定微信成功获得积分，每个会员仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                // 添加收货地址后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                // 完善个人信息后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                Long storeId = Long.valueOf(JSON.parseObject(request.getContent()).get("storeId").toString());
                // 店铺仅第一次关注可获得积分
                List<CustomerPointsDetail> pointsByStoreId = pointsList.stream()
                        .filter(g -> {
                            JSONObject content = JSON.parseObject(g.getContent());
                            return Objects.nonNull(content) && content.getLong("storeId").equals(storeId);
                        }).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(pointsByStoreId)) return;// 曾关注此店铺获取过积分
            }
            Long pointsLimit = context.getLong("limit");// 判断该类型是否有积分限额
            if (pointsLimit != null) {
                Long pointsCount = pointsList.stream() // 已获取的该类型积分
                        .map(record -> record.getType() == OperateType.GROWTH ? record.getPoints() :
                                -record.getPoints())
                        .reduce(0L, (a, b) -> a + b);
                if (pointsCount >= pointsLimit) return;// 积分超出限额

                Long growthableValue = pointsLimit - pointsCount;
                pointsIncrease = growthableValue > pointsIncrease ? pointsIncrease : growthableValue;
            }
            pointsDetail.setPoints(pointsIncrease);
        }

        RLock rLock = redissonClient.getFairLock("incPoint:".concat(request.getCustomerId()));
        rLock.lock();
        try{
            Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (Objects.isNull(customer)) {
                return;
            }
            Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();
            Long pointsTotal = pointsOrigin + pointsIncrease;// 增长后会员总积分

            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
            pointsDetail.setCustomerAccount(customer.getCustomerAccount());
            pointsDetail.setCustomerName(customerDetail.getCustomerName());
            pointsDetail.setPointsAvailable(pointsTotal);
            customerPointsDetailRepository.save(pointsDetail);
            customerRepository.addPointsAvailable(request.getCustomerId(), pointsIncrease);

            Long pointsIssueCount = pointsStatistics.getPointsIssueStatictics() + pointsIncrease;
            Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + pointsIncrease;
            redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
            redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
        } catch (Exception e){
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 新增会员积分明细
     */
    @Transactional
    public void increasePoints(CustomerPointsDetailAddRequest request, ConfigType configType,boolean isPointsOpen,List<ConfigVO> configs) {
        // 查询积分体系开关
        if (!isPointsOpen) return;// 未启用积分体系

        CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
        KsBeanUtil.copyPropertiesThird(request, pointsDetail);
        if (Objects.isNull(request.getOpTime())) {
            pointsDetail.setOpTime(LocalDateTime.now());
        }

        Long pointsIncrease = request.getPoints();
        if (pointsIncrease != null) {
            // 不为空则是订单完成获取的积分
            pointsDetail.setPoints(request.getPoints());
        } else {
            // 查询该类型积分基础规则
            ConfigVO config = configs.stream().filter(configVO -> configVO.getConfigType().equals(configType.toValue()))
                    .findFirst().orElse(null);
            if (Objects.isNull(config) || config.getStatus() == 0) return;// 该积分类型未开启

            JSONObject context = JSON.parseObject(config.getContext());
            if (StringUtils.isBlank(context.get("value").toString())) return;// 积分规则为空

            pointsIncrease = context.getLong("value");// 本次增长的积分

            // 查询会员已获取的同类型积分明细
            CustomerPointsDetailQueryRequest queryReq = new CustomerPointsDetailQueryRequest();
            queryReq.setCustomerId(request.getCustomerId());
            queryReq.setType(OperateType.GROWTH);
            queryReq.setServiceType(request.getServiceType());
            List<CustomerPointsDetail> pointsList = customerPointsDetailRepository.findAll(
                    CustomerPointsDetailWhereCriteriaBuilder.build(queryReq));

            if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                // 绑定微信成功获得积分，每个会员仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                // 添加收货地址后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                // 完善个人信息后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                Long storeId = Long.valueOf(JSON.parseObject(request.getContent()).get("storeId").toString());
                // 店铺仅第一次关注可获得积分
                List<CustomerPointsDetail> pointsByStoreId = pointsList.stream()
                        .filter(g -> {
                            JSONObject content = JSON.parseObject(g.getContent());
                            return Objects.nonNull(content) && content.getLong("storeId").equals(storeId);
                        }).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(pointsByStoreId)) return;// 曾关注此店铺获取过积分
            }
            Long pointsLimit = context.getLong("limit");// 判断该类型是否有积分限额
            if (pointsLimit != null) {
                Long pointsCount = pointsList.stream() // 已获取的该类型积分
                        .map(record -> record.getType() == OperateType.GROWTH ? record.getPoints() :
                                -record.getPoints())
                        .reduce(0L, (a, b) -> a + b);
                if (pointsCount >= pointsLimit) return;// 积分超出限额

                Long growthableValue = pointsLimit - pointsCount;
                pointsIncrease = growthableValue > pointsIncrease ? pointsIncrease : growthableValue;
            }
            pointsDetail.setPoints(pointsIncrease);
        }
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        if (Objects.isNull(customer)) {
            return;
        }
        Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();
        Long pointsTotal = pointsOrigin + pointsIncrease;// 增长后会员总积分

        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
        pointsDetail.setCustomerAccount(customer.getCustomerAccount());
        pointsDetail.setCustomerName(customerDetail.getCustomerName());
        pointsDetail.setPointsAvailable(pointsTotal);
        customerPointsDetailRepository.save(pointsDetail);

        customer.setPointsAvailable(pointsTotal);
        customerRepository.save(customer);

        CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
        Long pointsIssueCount = pointsStatistics.getPointsIssueStatictics() + pointsIncrease;
        Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + pointsIncrease;
        redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
        redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
    }

    /**
     * 新增会员积分明细
     */
    @Transactional
    public void increasePoints(CustomerPointsDetailAddRequest request, ConfigType configType,boolean isPointsOpen,List<ConfigVO> configs,Customer customer) {
        // 查询积分体系开关
        if (!isPointsOpen) return;// 未启用积分体系

        CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
        KsBeanUtil.copyPropertiesThird(request, pointsDetail);
        if (Objects.isNull(request.getOpTime())) {
            pointsDetail.setOpTime(LocalDateTime.now());
        }

        Long pointsIncrease = request.getPoints();
        if (pointsIncrease != null) {
            // 不为空则是订单完成获取的积分
            pointsDetail.setPoints(request.getPoints());
        } else {
            // 查询该类型积分基础规则
            ConfigVO config = configs.stream().filter(configVO -> configVO.getConfigType().equals(configType.toValue()))
                    .findFirst().orElse(null);
            if (Objects.isNull(config) || config.getStatus() == 0) return;// 该积分类型未开启

            JSONObject context = JSON.parseObject(config.getContext());
            if (StringUtils.isBlank(context.get("value").toString())) return;// 积分规则为空

            pointsIncrease = context.getLong("value");// 本次增长的积分

            // 查询会员已获取的同类型积分明细
            CustomerPointsDetailQueryRequest queryReq = new CustomerPointsDetailQueryRequest();
            queryReq.setCustomerId(request.getCustomerId());
            queryReq.setType(OperateType.GROWTH);
            queryReq.setServiceType(request.getServiceType());
            List<CustomerPointsDetail> pointsList = customerPointsDetailRepository.findAll(
                    CustomerPointsDetailWhereCriteriaBuilder.build(queryReq));

            if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                // 绑定微信成功获得积分，每个会员仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                // 添加收货地址后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                // 完善个人信息后获得积分，每个用户仅可获得一次
                if (CollectionUtils.isNotEmpty(pointsList)) return;
            } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                Long storeId = Long.valueOf(JSON.parseObject(request.getContent()).get("storeId").toString());
                // 店铺仅第一次关注可获得积分
                List<CustomerPointsDetail> pointsByStoreId = pointsList.stream()
                        .filter(g -> {
                            JSONObject content = JSON.parseObject(g.getContent());
                            return Objects.nonNull(content) && content.getLong("storeId").equals(storeId);
                        }).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(pointsByStoreId)) return;// 曾关注此店铺获取过积分
            }
            Long pointsLimit = context.getLong("limit");// 判断该类型是否有积分限额
            if (pointsLimit != null) {
                Long pointsCount = pointsList.stream() // 已获取的该类型积分
                        .map(record -> record.getType() == OperateType.GROWTH ? record.getPoints() :
                                -record.getPoints())
                        .reduce(0L, (a, b) -> a + b);
                if (pointsCount >= pointsLimit) return;// 积分超出限额

                Long growthableValue = pointsLimit - pointsCount;
                pointsIncrease = growthableValue > pointsIncrease ? pointsIncrease : growthableValue;
            }
            pointsDetail.setPoints(pointsIncrease);
        }
        Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();
        Long pointsTotal = pointsOrigin + pointsIncrease;// 增长后会员总积分

        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
        pointsDetail.setCustomerAccount(customer.getCustomerAccount());
        pointsDetail.setCustomerName(customerDetail.getCustomerName());
        pointsDetail.setPointsAvailable(pointsTotal);
        customerPointsDetailRepository.save(pointsDetail);

        customer.setPointsAvailable(pointsTotal);
        customerRepository.save(customer);

        CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
        Long pointsIssueCount = pointsStatistics.getPointsIssueStatictics() + pointsIncrease;
        Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + pointsIncrease;
        redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
        redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
    }

    /**
     * 批量新增会员积分
     */
    @Transactional
    public void batchIncreasePoints (CustomerPointsBatchAdjustRequest request) {
        request.getPointsAdjustDTOList().forEach( pointsAdjustDTO -> {
            Long adjustNum = pointsAdjustDTO.getAdjustNum();
            if (adjustNum < 1L || adjustNum > 99999L) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            OperateType operateType = pointsAdjustDTO.getOperateType();
            String customerId = pointsAdjustDTO.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElse(null);
            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customerId);
            if(Objects.nonNull(customer) && Objects.nonNull(customerDetail) && DeleteFlag.NO.equals(customer.getDelFlag())
                    && LogOutStatus.NORMAL.equals(customer.getLogOutStatus()) && CustomerStatus.ENABLE.equals(customerDetail.getCustomerStatus())){
                Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();
                Long pointsTotal = pointsOrigin + adjustNum;
                CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
                pointsDetail.setCustomerId(customerId);
                pointsDetail.setCustomerAccount(customer.getCustomerAccount());
                pointsDetail.setCustomerName(customerDetail.getCustomerName());
                pointsDetail.setPointsAvailable(pointsTotal);
                pointsDetail.setType(operateType);
                pointsDetail.setPoints(adjustNum);
                pointsDetail.setOpTime(LocalDateTime.now());
                pointsDetail.setServiceType(PointsServiceType.MANUAL_INCREASE);
                customerPointsDetailRepository.save(pointsDetail);
                customerRepository.addPointsAvailable(customerId,adjustNum);

                CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
                Long pointsIssueCount = pointsStatistics.getPointsIssueStatictics() + adjustNum;
                Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + adjustNum;
                redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
                redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
            } else {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010161);
            }
        });
    }

    /**
     * 批量减少积分
     */
    @Transactional
    public void batchReducePoints (CustomerPointsBatchAdjustRequest request) {
        request.getPointsAdjustDTOList().forEach( pointsAdjustDTO -> {
            String customerId = pointsAdjustDTO.getCustomerId();
            Long adjustNum = pointsAdjustDTO.getAdjustNum();
            if (adjustNum < 1L || adjustNum > 99999L) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            OperateType operateType = pointsAdjustDTO.getOperateType();
            Customer customer = customerRepository.findById(customerId).orElse(null);
            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customerId);
            if (Objects.nonNull(customer) && Objects.nonNull(customerDetail) && DeleteFlag.NO.equals(customer.getDelFlag())
                    && LogOutStatus.NORMAL.equals(customer.getLogOutStatus()) && CustomerStatus.ENABLE.equals(customerDetail.getCustomerStatus())) {
                Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();
                Long pointsTotal = pointsOrigin - adjustNum;
                // 积分不够减时，直接赋0
                pointsTotal = pointsTotal > 0 ? pointsTotal : 0L;

                CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
                pointsDetail.setCustomerId(customerId);
                pointsDetail.setCustomerAccount(customer.getCustomerAccount());
                pointsDetail.setCustomerName(customerDetail.getCustomerName());
                pointsDetail.setPointsAvailable(pointsTotal);
                pointsDetail.setType(operateType);
                pointsDetail.setPoints(adjustNum);
                pointsDetail.setOpTime(LocalDateTime.now());
                pointsDetail.setServiceType(PointsServiceType.MANUAL_REDUCT);
                customerPointsDetailRepository.save(pointsDetail);
                customerRepository.reducePointsAvailable(customerId,adjustNum);

                CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
                Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() - adjustNum;
                redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
            } else {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010161);
            }
        });
    }

    /**
     * 批量覆盖积分
     */
    @Transactional
    public void batchCoverPoints (CustomerPointsBatchAdjustRequest request) {

        request.getPointsAdjustDTOList().forEach( pointsAdjustDTO -> {

            String customerId = pointsAdjustDTO.getCustomerId();
            Long adjustNum = pointsAdjustDTO.getAdjustNum();
            if (adjustNum < 1L || adjustNum > 99999L) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            OperateType operateType = pointsAdjustDTO.getOperateType();

            // 构造并新增用户积分明细
            Customer customer = customerRepository.findById(customerId).orElse(null);
            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customerId);
            if (Objects.nonNull(customer) && Objects.nonNull(customerDetail) && DeleteFlag.NO.equals(customer.getDelFlag())
                    && LogOutStatus.NORMAL.equals(customer.getLogOutStatus()) && CustomerStatus.ENABLE.equals(customerDetail.getCustomerStatus())) {
                Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) : customer.getPointsAvailable();


                CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
                pointsDetail.setCustomerId(customerId);
                pointsDetail.setCustomerAccount(customer.getCustomerAccount());
                pointsDetail.setCustomerName(customerDetail.getCustomerName());
                pointsDetail.setPointsAvailable(adjustNum);
                pointsDetail.setType(operateType);
                pointsDetail.setPoints(adjustNum);
                pointsDetail.setOpTime(LocalDateTime.now());
                pointsDetail.setServiceType(PointsServiceType.MANUAL_REPLACE);
                customerPointsDetailRepository.save(pointsDetail);
                customer.setPointsAvailable(adjustNum);
                customerRepository.replacePointsAvailable(adjustNum, customerId);

                CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
                if (adjustNum > pointsOrigin) {
                    Long pointsIssueCount = pointsStatistics.getPointsIssueStatictics() + adjustNum;
                    Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + adjustNum;
                    redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
                    redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
                } else {
                    Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() - adjustNum;
                    redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
                }
            } else {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010161);
            }
        });
    }

    /**
     * 保存扣除积分记录
     */
    @Transactional
    public void deductPoints(CustomerPointsDetailAddRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        if (Objects.isNull(customer)) {
            throw new SbcRuntimeException(this.getCustomerDeleteIndex(request.getCustomerId()), CustomerErrorCodeEnum.K010001);
        }
        int record = customerRepository.updateCustomerPoints(request.getCustomerId(), request.getPoints());
        if (record != 1) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010145);
        }
        CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
        KsBeanUtil.copyPropertiesThird(request, pointsDetail);
        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());

        pointsDetail.setOpTime(LocalDateTime.now());
        pointsDetail.setCustomerAccount(customer.getCustomerAccount());
        pointsDetail.setCustomerName(customerDetail.getCustomerName());
        pointsDetail.setPointsAvailable(customer.getPointsAvailable() - request.getPoints());
        customerPointsDetailRepository.save(pointsDetail);

        CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
        Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() - request.getPoints();
        redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
    }

    /**
     * 会员积分返还
     */
    @Transactional
    public void returnPoints(CustomerPointsDetailAddRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        if (Objects.isNull(customer)) {
            return;
        }
        customer.setPointsAvailable(customer.getPointsAvailable() + request.getPoints());
        customer.setPointsUsed(customer.getPointsUsed() - request.getPoints());
        customerRepository.save(customer);

        CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
        KsBeanUtil.copyPropertiesThird(request, pointsDetail);

        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
        pointsDetail.setOpTime(LocalDateTime.now());
        pointsDetail.setCustomerAccount(customer.getCustomerAccount());
        pointsDetail.setCustomerName(customerDetail.getCustomerName());
        pointsDetail.setPointsAvailable(customer.getPointsAvailable());
        customerPointsDetailRepository.save(pointsDetail);

        CustomerPointsStatisticsResponse pointsStatistics = this.queryIssueStatistics();
        Long pointsAvailableCount = pointsStatistics.getPointsAvailableStatictics() + request.getPoints();
        redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());
    }

    /**
     * 分页查询会员积分明细
     */
    public Page<CustomerPointsDetail> page(CustomerPointsDetailQueryRequest queryReq) {
        return customerPointsDetailRepository.findAll(
                CustomerPointsDetailWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询会员积分明细
     */
    public List<CustomerPointsDetail> list(CustomerPointsDetailQueryRequest queryReq) {
        return customerPointsDetailRepository.findAll(CustomerPointsDetailWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 查询总积分统计
     *
     * @return
     */
    public CustomerPointsStatisticsResponse queryIssueStatistics() {
        // 优先从redis中查询是否有积分统计数据
        String pointsIssueStatistics = redisService.getString(POINTS_ISSUE_STATISTICS_KEY);
        String pointsAvailableStatictics = redisService.getString(POINTS_AVAILABLE_STATISTICS_KEY);
        if (StringUtils.isNotBlank(pointsIssueStatistics) && StringUtils.isNotBlank(pointsAvailableStatictics)) {
            return CustomerPointsStatisticsResponse.builder()
                    .pointsIssueStatictics(Long.valueOf(pointsIssueStatistics))
                    .pointsAvailableStatictics(Long.valueOf(pointsAvailableStatictics))
                    .build();
        }
        // redis中无数据，查询数据库并统计
        Long pointsAvailableCount = 0L;
        Long pointsUsedCount = 0L;
        List pointsStatistics = customerPointsDetailRepository.getPointsStatistics();
        if (CollectionUtils.isNotEmpty(pointsStatistics)) {
            Object[] cells = (Object[]) pointsStatistics.get(0);
            pointsAvailableCount = (Long) cells[0];
            pointsUsedCount = (Long) cells[1];
        }
        Long pointsIssueCount = pointsAvailableCount + pointsUsedCount;
        // 存进redis
        redisService.setString(POINTS_ISSUE_STATISTICS_KEY, pointsIssueCount.toString());
        redisService.setString(POINTS_AVAILABLE_STATISTICS_KEY, pointsAvailableCount.toString());

        return CustomerPointsStatisticsResponse.builder()
                .pointsIssueStatictics(pointsIssueCount)
                .pointsAvailableStatictics(pointsAvailableCount)
                .build();
    }

    /**
     * 查询业务员下的会员积分统计
     *
     * @return
     */
    public CustomerPointsStatisticsResponse queryIssueStatisticsByCustomerIds(List<String> customerIds) {
        Long pointsAvailableCount = 0L;
        Long pointsUsedCount = 0L;
        List pointsStatistics = customerPointsDetailRepository.getPointsStatisticsByCustomerIds(customerIds);
        if (CollectionUtils.isNotEmpty(pointsStatistics)) {
            Object[] cells = (Object[]) pointsStatistics.get(0);
            pointsAvailableCount = (Long) cells[0];
            pointsUsedCount = (Long) cells[1];
        }
        Long pointsIssueCount = pointsAvailableCount + pointsUsedCount;

        return CustomerPointsStatisticsResponse.builder()
                .pointsIssueStatictics(pointsIssueCount)
                .pointsAvailableStatictics(pointsAvailableCount)
                .build();
    }

    /**
     * 根据会员Id查询会员即将过期的积分
     * <p>
     * 用于C端展示
     */
    public CustomerPointsExpireResponse queryWillExpirePoints(String customerId) {
        CustomerPointsExpireResponse response = new CustomerPointsExpireResponse();
        response.setCustomerId(customerId);
        // 1 查询积分配置
        SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigQueryProvider.querySystemPointsConfig().getContext();

        Integer pointsExpireMonth = pointsConfig.getPointsExpireMonth();
        Integer pointsExpireDay = pointsConfig.getPointsExpireDay();
        // 1.1 验证积分过期时间是否是0月0日（即积分不过期）
        if (pointsExpireMonth.equals(0) && pointsExpireDay.equals(0)) {
            response.setPointsExpireStatus(EnableStatus.DISABLE);
            return response;
        } else {
            response.setPointsExpireStatus(EnableStatus.ENABLE);
        }
        // 1.2 不为0月0日，与当前日期比对来确定过期时间是今年还是明年
        LocalDate expireDate = LocalDate.of(LocalDate.now().getYear(), pointsExpireMonth, pointsExpireDay);
        if (expireDate.isAfter(LocalDate.now())) {
            // 1.3 今年过期，查询截止今年初获得的积分和截止目前使用的积分
            response.setPointsExpireDate(expireDate);
            response.setWillExpirePoints(willExpirePoints(customerId));
        } else {
            response.setPointsExpireDate(expireDate.plus(1, ChronoUnit.YEARS));
            // 1.3 次年过期，过期积分则是积分余额
            Customer customer = customerRepository.findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO);
            response.setWillExpirePoints(customer.getPointsAvailable());
        }
        return response;
    }

    /**
     * 根据会员Id查询会员即将过期的积分
     * <p>
     * 用于定时任务
     */
    public CustomerPointsExpireResponse queryWillExpirePointsForCronJob(String customerId) {
        CustomerPointsExpireResponse response = new CustomerPointsExpireResponse();
        response.setCustomerId(customerId);
        response.setPointsExpireDate(LocalDate.now());
        response.setWillExpirePoints(willExpirePoints(customerId));
        return response;
    }

    @Transactional
    public void increasePoints(CustomerPointsDetailBatchAddRequest request) {
        if (CollectionUtils.isNotEmpty(request.getCustomerIdList())) {
            for (String customerId : request.getCustomerIdList()) {
                savePoint(customerId, request);
            }
        }
    }

    private void savePoint(String customerId, CustomerPointsDetailBatchAddRequest request) {
        RLock lock = redissonClient.getFairLock(CustomerPointConstant.POINT_LOCK_KEY);
        try {
            boolean flag = lock.tryLock(30000, 10000, TimeUnit.MILLISECONDS);
            if (flag) {
                Customer customer = customerRepository.findById(customerId).orElse(null);
                if (Objects.isNull(customer)) {
                    return;
                }
                CustomerPointsDetail pointsDetail = new CustomerPointsDetail();
                pointsDetail = KsBeanUtil.convert(request, CustomerPointsDetail.class);
                Long pointsOrigin = customer.getPointsAvailable() == null ? Long.valueOf(0) :
                        customer.getPointsAvailable();
                Long pointsTotal = pointsOrigin + pointsDetail.getPoints();// 增长后会员总积分

                CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
                pointsDetail.setCustomerId(customerId);
                pointsDetail.setCustomerAccount(customer.getCustomerAccount());
                pointsDetail.setCustomerName(customerDetail.getCustomerName());
                pointsDetail.setPointsAvailable(pointsTotal);
                pointsDetail.setOpTime(LocalDateTime.now());
                customerPointsDetailRepository.save(pointsDetail);

                customer.setPointsAvailable(pointsTotal);
                customerRepository.save(customer);

                redisService.delete(POINTS_ISSUE_STATISTICS_KEY);
                redisService.delete(POINTS_AVAILABLE_STATISTICS_KEY);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

    }

    public Page<CustomerStatisticsPointsVO> getPointsStatisticsGroupByCustomer(CustomerPointsDetailQueryRequest request) {
        return this.customerPointsDetailRepository.getPointsStatisticsGroupByCustomer(request.getOpTimeBegin(), request.getOpTimeEnd(), request.getType(), request.getPageable());
    }


    private Long willExpirePoints(String customerId) {
        LocalDateTime startOfYear = LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 0, 0, 0);
        Long gotPoints = customerPointsDetailRepository.queryWillExpirePoints(customerId, OperateType.GROWTH, startOfYear);
        Long usedPoints = customerPointsDetailRepository.queryWillExpirePoints(customerId, OperateType.DEDUCT, LocalDateTime.now());
        if (Objects.isNull(gotPoints)) gotPoints = 0L;
        if (Objects.isNull(usedPoints)) usedPoints = 0L;
        return gotPoints - usedPoints > 0 ? gotPoints - usedPoints : 0L;
    }


    /**
     * 将实体包装成VO
     */
    public CustomerPointsDetailVO wrapperVo(CustomerPointsDetail customerPointsDetail) {
        if (customerPointsDetail != null) {
            CustomerPointsDetailVO customerPointsDetailVO = new CustomerPointsDetailVO();
            KsBeanUtil.copyPropertiesThird(customerPointsDetail, customerPointsDetailVO);
            customerPointsDetailVO.setLogOutStatus(LogOutStatus.fromValue(
                    customerService.getCustomerLogOutStatus(customerPointsDetail.getCustomerId())
            ));
            return customerPointsDetailVO;
        }
        return null;
    }

    /**
     * 拼凑删除customerEs-提供给findOne去调
     *
     * @param id 编号
     * @return "es_customer_detail:{id}"
     */
    private Object getCustomerDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_CUSTOMER_DETAIL, id);
    }

    public void saveCustomerPointsDetail(CustomerPointsDetailAddRequest request,boolean isPointsOpen,List<ConfigVO> configs,Customer customer) {
        if (request.getType() == OperateType.GROWTH) {
            if (request.getServiceType() == PointsServiceType.REGISTER) {// 注册
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_REGISTER,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_BIND_WECHAT,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMPLETE_INFORMATION,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_ADD_DELIVERY_ADDRESS,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_FOLLOW_STORE,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.SHARE) {// 分享商品
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_GOODS,isPointsOpen,configs,customer);
            } else if (request.getServiceType() == PointsServiceType.EVALUATE) {// 评论商品
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMMENT_GOODS,isPointsOpen,configs,customer);
            } else if(request.getServiceType() == PointsServiceType.SHAREREGISTER){// 分享注册
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_REGISTER,isPointsOpen,configs,customer);
            } else if(request.getServiceType() == PointsServiceType.SHAREPURCHASE){// 分享购买
                this.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_BUY,isPointsOpen,configs,customer);
            } else { // 订单完成、会员导入
                this.increasePoints(request, null,isPointsOpen,configs,customer);
            }
        } else {
            // 扣除积分
            this.deductPoints(request);
        }
    }

}
