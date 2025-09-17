package com.wanmi.sbc.customer.growthvalue.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.enums.RightsCouponSendType;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.growthvalue.builder.GrowthValueType;
import com.wanmi.sbc.customer.growthvalue.builder.GrowthValueTypeConfig;
import com.wanmi.sbc.customer.growthvalue.model.root.CustomerGrowthValue;
import com.wanmi.sbc.customer.growthvalue.repository.CustomerGrowthValueRepository;
import com.wanmi.sbc.customer.level.model.root.CustomerLevel;
import com.wanmi.sbc.customer.level.repository.CustomerLevelRepository;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRights;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRightsRel;
import com.wanmi.sbc.customer.levelrights.repository.CustomerLevelRightsRelRepository;
import com.wanmi.sbc.customer.levelrights.repository.CustomerLevelRightsRepository;
import com.wanmi.sbc.customer.message.StoreMessageBizService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.points.service.CustomerPointsDetailService;
import com.wanmi.sbc.customer.repository.CustomerRepository;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.provider.GrowthValueBasicRuleQueryProvider;
import com.wanmi.sbc.setting.api.provider.SystemGrowthValueConfigQueryProvider;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>客户成长值明细表业务逻辑</p>
 */
@Service("GrowthValueIncreaseService")
@Slf4j
public class GrowthValueIncreaseService implements InitializingBean {

    @Autowired
    private GrowthValueBasicRuleQueryProvider growthValueBasicRuleQueryProvider;

    @Autowired
    private SystemGrowthValueConfigQueryProvider systemGrowthValueConfigQueryProvider;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerLevelRepository customerLevelRepository;

    @Autowired
    private CustomerGrowthValueRepository customerGrowthValueRepository;

    @Autowired
    private CustomerLevelRightsRepository customerLevelRightsRepository;

    @Autowired
    private CustomerLevelRightsRelRepository customerLevelRightsRelRepository;

    @Autowired
    private List<GrowthValueType> growthValueTypeBuilders;

    private Map<GrowthValueServiceType, GrowthValueType> growthValueBuilderMap = new ConcurrentHashMap<>();

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private CustomerPointsDetailService customerPointsDetailService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 成长值增长
     *
     * @param request 成长值增长参数
     */
    @Transactional
    public void increaseGrowthValue(CustomerGrowthValueAddRequest request) {
        // 查询成长值体系开关
        Boolean isGrowthValueOpen = systemGrowthValueConfigQueryProvider.isGrowthValueOpen().getContext().isOpen();
        if (!isGrowthValueOpen) return;// 未启用成长值体系

        // 判断是否是降级
        if (request.getServiceType().equals(GrowthValueServiceType.DOWNLEVEL)) {
            downCustomerLevel(request);
            return;
        }
        CustomerGrowthValue growthValue = new CustomerGrowthValue();
        KsBeanUtil.copyPropertiesThird(request, growthValue);
        if (Objects.isNull(growthValue.getOpTime())) {
            growthValue.setOpTime(LocalDateTime.now());
        }
        growthValue.setType(OperateType.GROWTH);
        growthValue.setDelFlag(DeleteFlag.NO);
        // 查询本次应增加的成长值，不为空表示是订单完成增加的成长值
        Long growthValueIncrease = growthValue.getGrowthValue();
        if (Objects.isNull(growthValueIncrease)) {
            // 查询该类型成长值基础规则
            ConfigVO config = growthValueBasicRuleQueryProvider.findGrowthValueByConfigType(
                    GrowthValueTypeConfig.getConfigTypeByKey(growthValue.getServiceType())).getContext();
            if (Objects.isNull(config) || config.getStatus() != 1) return;// 该成长值类型未开启

            JSONObject context = JSON.parseObject(config.getContext());
            if (StringUtils.isBlank(context.get("value").toString())) return;// 成长值规则为空

            // 根据成长值业务类型查询对应的实现类
            GrowthValueType type = growthValueBuilderMap.get(growthValue.getServiceType());
            // 未找到对应实现类
            if (type == null) return;
            // 在对应的实现类中判断本次应增长的成长值
            growthValueIncrease = type.getIncreaseGrowthValue(context, growthValue);
            // 超过该类型成长值获取的次数或限额
            if (growthValueIncrease.equals(0L)) return;
            growthValue.setGrowthValue(growthValueIncrease);
        }
        // 判断是否是校服小助手订单
        if (request.getServiceType().equals(GrowthValueServiceType.SWDH5ORDERCOMPLETION)) {
            Integer numByOrderSn = customerGrowthValueRepository.getNumByOrderSn(request.getCustomerId(), request.getServiceType(), request.getTradeNo());
            // 防止重复增长积分
            if (numByOrderSn > 0) {
                return;
            }
        }
        customerGrowthValueRepository.save(growthValue);

        updateCustomerLevel(growthValue);
    }

    /**
     * 用户升级
     *
     * @param growthValueDetail
     */
    private void updateCustomerLevel(CustomerGrowthValue growthValueDetail) {
        Customer customer = customerRepository.findById(growthValueDetail.getCustomerId()).orElse(null);
        if (Objects.isNull(customer)) {
            return;
        }
        Long growthValueOrigin = customer.getGrowthValue() == null ? Long.valueOf(0) : customer.getGrowthValue();
        Long growthValueTotal = growthValueOrigin + growthValueDetail.getGrowthValue();// 增长后会员总成长值
        // 查询所有平台等级,按成长值降序
        List<CustomerLevel> allCustomerLevelList = customerLevelRepository.listLevelOrderByValueDesc();
        // 原成长值应处等级
        CustomerLevel originalRealLevel = allCustomerLevelList.stream()
                .filter(level -> growthValueOrigin >= level.getGrowthValue())
                .findFirst().orElseGet(CustomerLevel::new);
        // 成长值增长后应达到的等级
        CustomerLevel willReachLevel = allCustomerLevelList.stream()
                .filter(level -> growthValueTotal >= level.getGrowthValue())
                .findFirst().orElseGet(CustomerLevel::new);
        // 查询客户晋升的等级列表，用于发放权益
        List<CustomerLevel> levels = new ArrayList<>();
        if (customer.getCustomerLevelId() <= originalRealLevel.getCustomerLevelId()) {
            // 客户当前等级 <= 原成长值应处等级
            levels = increaseLevels(allCustomerLevelList, customer.getCustomerLevelId(), willReachLevel.getCustomerLevelId());
//            customer.setCustomerLevelId(willReachLevel.getCustomerLevelId());
        } else if (customer.getCustomerLevelId() >= willReachLevel.getCustomerLevelId()) {
            // 客户当前等级 >= 成长值增长后应达到的等级
            levels = increaseLevels(allCustomerLevelList, originalRealLevel.getCustomerLevelId(), customer.getCustomerLevelId());
//            customer.setCustomerLevelId(customer.getCustomerLevelId());
        } else if (customer.getCustomerLevelId() >= originalRealLevel.getCustomerLevelId()
                && customer.getCustomerLevelId() <= willReachLevel.getCustomerLevelId()) {
            // 原成长值应处等级 <= 客户当前等级 <=成长值增长后应达到的等级
            levels = increaseLevels(allCustomerLevelList, customer.getCustomerLevelId(), willReachLevel.getCustomerLevelId());
//            customer.setCustomerLevelId(willReachLevel.getCustomerLevelId());
        }
        levels.forEach(level -> issueLevelRights(growthValueDetail.getCustomerId(), level));
        customerRepository.updateGrowthValueAndCustomerLevelId(growthValueDetail.getCustomerId(), growthValueDetail.getGrowthValue(), willReachLevel.getCustomerLevelId());

        // 客户当前等级 <=成长值增长后应达到的等级 修改用户等级过期时间
        if(customer.getCustomerLevelId() < willReachLevel.getCustomerLevelId()){
            //更新有效期
            customerRepository.updateMemberShipExpiredTime(customer.getCustomerId(), LocalDateTime.now().plusYears(1));
        }

        // 拷贝用户信息，用于MQ消息发送
        Customer newestCustomer = new Customer();
        newestCustomer.setCustomerId(customer.getCustomerId());
        newestCustomer.setCustomerLevelId(willReachLevel.getCustomerLevelId());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_CUSTOMER_LEVEL_DETAIL_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(newestCustomer));
        mqSendProvider.send(mqSendDTO);

        // ============= 处理平台的消息发送：客户升级提醒 START =============
        storeMessageBizService.handleForCustomerUpdateLevel(originalRealLevel, willReachLevel, customer);
        // ============= 处理平台的消息发送：客户升级提醒 END =============
    }

    /**
     * 即将增长的等级
     *
     * @param allCustomerLevelList
     * @param lowerLevel
     * @param higherLevel
     * @return
     */
    private List<CustomerLevel> increaseLevels(List<CustomerLevel> allCustomerLevelList, Long lowerLevel, Long higherLevel) {
        return allCustomerLevelList.stream()
                .filter(level -> level.getCustomerLevelId() > lowerLevel && level.getCustomerLevelId() <= higherLevel)
                .collect(Collectors.toList());
    }

    /**
     * 发放等级券礼包
     *
     * @param customerId
     * @param level
     */
    private void issueLevelRights(String customerId, CustomerLevel level) {
        // 根据会员等级Id查询权益与等级的关联表 判断对应的等级权益是否有券礼包 查询达到等级发放的优惠券
        List<CustomerLevelRightsRel> rightsRels = customerLevelRightsRelRepository.findByCustomerLevelId(level.getCustomerLevelId());
        rightsRels.forEach(rightsRel -> {
            CustomerLevelRights rights = customerLevelRightsRepository.findByRightsId(rightsRel.getRightsId());
            // 1.权益为券礼包  2.优惠券活动id不为null  3.优惠券类型为达到等级即发放
            if (Objects.nonNull(rights) && rights.getRightsType() == LevelRightsType.COUPON_GIFT
                    && rights.getActivityId() != null) {
                String type = JSON.parseObject(rights.getRightsRule()).get("type").toString();
                if (RightsCouponSendType.ISSUE_ONCE.getStateId().equals(type)
                        || RightsCouponSendType.REPEAT.getStateId().equals(type)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", customerId);
                    map.put("activityId", rights.getActivityId());
                    // mq通知marketing模块发放优惠券
                    MqSendDTO mqSendDTO = new MqSendDTO();
                    mqSendDTO.setTopic(ProducerTopic.ISSUE_COUPONS);
                    mqSendDTO.setData(JSONObject.toJSONString(map));
                    mqSendProvider.send(mqSendDTO);
                }
            }
            // 2.权益赠送积分
            if (Objects.nonNull(rights) && rights.getRightsType() == LevelRightsType.SEND_POINTS) {
                Long points = Long.valueOf(JSON.parseObject(rights.getRightsRule()).get("points").toString());
                CustomerPointsDetailAddRequest request = CustomerPointsDetailAddRequest.builder()
                        .customerId(customerId)
                        .points(points)
                        .type(OperateType.GROWTH)
                        .serviceType(PointsServiceType.RIGHTS_SEND_POINTS)
                        .build();
                customerPointsDetailService.increasePoints(request, null);
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        growthValueTypeBuilders.forEach(builder -> {
            List<GrowthValueServiceType> serviceTypeList = builder.supportGrowthValueType();
            serviceTypeList.forEach(serviceType -> growthValueBuilderMap.put(serviceType, builder));
        });
    }
    /**
     * 用户降级
     *
     * @param request
     */
    private void downCustomerLevel(CustomerGrowthValueAddRequest request) {
        log.info("用户;{}降级开始", request.getCustomerId());
        CustomerGrowthValue growthValue = new CustomerGrowthValue();
        KsBeanUtil.copyPropertiesThird(request, growthValue);
        if (Objects.isNull(growthValue.getOpTime())) {
            growthValue.setOpTime(LocalDateTime.now());
        }
        growthValue.setType(OperateType.GROWTH);
        growthValue.setDelFlag(DeleteFlag.NO);
        growthValue.setContent("长时间未消费降级");
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        if (Objects.isNull(customer) || Objects.isNull(customer.getMembershipExpiredTime()) ||
                customer.getMembershipExpiredTime().isAfter(LocalDateTime.now().with(LocalTime.MAX)) ) {
            //会员没过期
            log.info("会员已降级,直接返回");
            return;
        }
        // 查询所有平台等级,按成长值降序
        List<CustomerLevel> allCustomerLevelList = customerLevelRepository.listLevelOrderByIdAsc();
        // 判断有效期时间内消费金额是否达到最高等级的所需成长值,达到则不降级,刷新会员有效期
        Boolean isAvoidRelegation = isAvoidRelegation(customer,allCustomerLevelList);
        if (isAvoidRelegation) {
            //保级,延长会员有效期
            customerRepository.updateMemberShipExpiredTime(request.getCustomerId(), LocalDateTime.now().plusYears(1));
            return;
        }
        // 找到用户当前等级的上一个等级,如果已经最低等级,则返回最低等级
        CustomerLevel previousLevel = findPreviousLevelStream(allCustomerLevelList, customer.getCustomerLevelId());
        if (previousLevel == null) {
            return;
        }
        long deductValue = previousLevel.getGrowthValue() - customer.getGrowthValue();
        growthValue.setGrowthValue(deductValue);
        //保存成长值记录
        customerGrowthValueRepository.save(growthValue);
        // 更新会员等级和积分
        customerRepository.downCustomerLevel(customer.getCustomerId(), previousLevel.getGrowthValue(), previousLevel.getCustomerLevelId());
        //更新有效期
        customerRepository.updateMemberShipExpiredTime(customer.getCustomerId(), LocalDateTime.now().plusYears(1));

    }

    /**
     * 找到用户当前等级的上一个等级,如果已经是最低等级,则返回最低等级
     *
     * @param allCustomerLevelList
     * @param currentLevelId
     */
    public CustomerLevel findPreviousLevelStream(List<CustomerLevel> allCustomerLevelList, Long currentLevelId) {
        if (allCustomerLevelList == null || allCustomerLevelList.isEmpty()) {
            return null;
        }
        if (currentLevelId == null || currentLevelId.equals(allCustomerLevelList.get(0).getCustomerLevelId())) {
            return allCustomerLevelList.get(0);
        }
        return allCustomerLevelList.stream()
                .filter(level -> level.getCustomerLevelId() < currentLevelId)
                .reduce((first, second) -> second) // 获取最后一个满足条件的
                .orElse(null);
    }

    /**
     * 判断是否消费额度能保级
     *
     * @param customer
     * @param allCustomerLevelList
     */
    private Boolean isAvoidRelegation(Customer customer, List<CustomerLevel> allCustomerLevelList ) {
        if (customer.getMembershipExpiredTime() == null){
            return false;
        }
        // 获得有效期时间一年前的时间点
        LocalDateTime oneYearBefore = customer.getMembershipExpiredTime().minusYears(1);
        List<GrowthValueServiceType> serviceTypes = new ArrayList<>();
        serviceTypes.add(GrowthValueServiceType.ORDERCOMPLETION);
        serviceTypes.add(GrowthValueServiceType.SWDH5ORDERCOMPLETION);
        //统计近一年的消费金额
        Integer growthValueThePastYear = customerGrowthValueRepository.sumGrowthValueThePastYear(customer.getCustomerId(), oneYearBefore, serviceTypes);
        if (growthValueThePastYear == null){
            return false;
        }
        //查询用户当前等级的所需成长值
        Long needGrowthValue = allCustomerLevelList.stream().filter(level -> level.getCustomerLevelId().equals(customer.getCustomerLevelId()))
                .findFirst().map(CustomerLevel::getGrowthValue).get();
        if (growthValueThePastYear >= needGrowthValue){
            return true;
        }
        return false;
    }
}
