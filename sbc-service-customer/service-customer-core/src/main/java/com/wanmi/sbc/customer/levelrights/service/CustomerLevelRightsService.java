package com.wanmi.sbc.customer.levelrights.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.enums.RightsCouponSendType;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRights;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRightsRel;
import com.wanmi.sbc.customer.levelrights.repository.CustomerLevelRightsRelRepository;
import com.wanmi.sbc.customer.levelrights.repository.CustomerLevelRightsRepository;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import com.wanmi.sbc.customer.payingmemberrightsrel.repository.PayingMemberRightsRelRepository;
import com.wanmi.sbc.customer.points.service.CustomerPointsDetailService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>会员等级权益表业务逻辑</p>
 */
@Service("CustomerLevelRightsService")
public class CustomerLevelRightsService {
    @Autowired
    private CustomerLevelRightsRepository customerLevelRightsRepository;

    @Autowired
    private CustomerLevelRightsRelRepository customerLevelRightsRelRepository;


    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private CustomerPointsDetailService customerPointsDetailService;

    @Autowired
    private PayingMemberRightsRelRepository payingMemberRightsRelRepository;

    /**
     * 新增会员等级权益表
     *
     * @author minchen
     */
    @Transactional
    public CustomerLevelRights add(CustomerLevelRights entity) {
        List<CustomerLevelRights> rightsList = customerLevelRightsRepository.findAllList();
        if (CollectionUtils.isNotEmpty(rightsList)) {
            // 权益名称不能已经存在
            if (rightsList.stream().anyMatch(rights ->
                    StringUtils.equals(entity.getRightsName(), rights.getRightsName()))) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010152);
            }
        }
        return customerLevelRightsRepository.save(entity);
    }

    /**
     * 修改会员等级权益表
     *
     * @author minchen
     */
    @Transactional
    public CustomerLevelRights modify(CustomerLevelRights entity) {
        // 根据id查询详情信息
        CustomerLevelRights rights = customerLevelRightsRepository.findByRightsId(entity.getRightsId());
        // 权益不存在
        if (Objects.isNull(rights)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010150);
        }
        // 除自身外, 权益名称不能重复
        if (customerLevelRightsRepository.findByRightsNameNotSelf(
                entity.getRightsId(), entity.getRightsName()).size() > 0) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010152);
        }
        // 已关联客户等级情况下不可禁用
        List<CustomerLevelRightsRel> rels = customerLevelRightsRelRepository.findByRightsId(rights.getRightsId());
        if (entity.getStatus() == 0 && CollectionUtils.isNotEmpty(rels)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010154);
        }
        //已关联付费会员等级不可禁用
        List<PayingMemberRightsRel> payMemberRels = payingMemberRightsRelRepository.findAllByRightsIdAndDelFlag(rights.getRightsId(), DeleteFlag.NO);
        if (entity.getStatus() == 0 && CollectionUtils.isNotEmpty(payMemberRels)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010155);
        }
        return customerLevelRightsRepository.save(entity);
    }

    /**
     * 单个删除会员等级权益表
     *
     * @author minchen
     */
    @Transactional
    public void deleteById(Integer id) {
        List<CustomerLevelRightsRel> rels = customerLevelRightsRelRepository.findByRightsId(id);
        if (CollectionUtils.isNotEmpty(rels)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010151);
        }
        //已关联付费会员等级不可删除
        List<PayingMemberRightsRel> payMemberRels = payingMemberRightsRelRepository.findAllByRightsIdAndDelFlag(id, DeleteFlag.NO);
        if (CollectionUtils.isNotEmpty(payMemberRels)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010156);
        }
        // 删除权益
        customerLevelRightsRepository.deleteRightsById(id);
    }

    /**
     * 单个查询会员等级权益表
     *
     * @author minchen
     */
    public CustomerLevelRights getById(Integer id) {
        return customerLevelRightsRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询会员等级权益表
     *
     * @author minchen
     */
    public Page<CustomerLevelRights> page(CustomerLevelRightsQueryRequest queryReq) {
        return customerLevelRightsRepository.findAll(
                CustomerLevelRightsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询会员等级权益表
     *
     * @author minchen
     */
    public List<CustomerLevelRights> list(CustomerLevelRightsQueryRequest queryReq) {
        return customerLevelRightsRepository.findAll(CustomerLevelRightsWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 拖拽排序
     *
     * @param queryReq
     */
    @Transactional
    public void editSort(CustomerLevelRightsQueryRequest queryReq) {
        List<Integer> idList = queryReq.getRightsIdList();
        for (int i = 0; i < idList.size(); i++) {
            CustomerLevelRights rights = customerLevelRightsRepository.findById(idList.get(i)).orElse(null);
            if (Objects.nonNull(rights)) {
                rights.setUpdateTime(LocalDateTime.now());
                rights.setSort(i + 1);
                customerLevelRightsRepository.save(rights);
            }
        }
    }

    /**
     * 将实体包装成VO
     *
     * @author minchen
     */
    public CustomerLevelRightsVO wrapperVo(CustomerLevelRights customerLevelRights) {
        if (customerLevelRights != null) {
            CustomerLevelRightsVO customerLevelRightsVO = new CustomerLevelRightsVO();
            KsBeanUtil.copyPropertiesThird(customerLevelRights, customerLevelRightsVO);
            return customerLevelRightsVO;
        }
        return null;
    }


    /**
     * 发放等级券礼包 (付费会员) 返回权益列表
     *
     * @param customerId
     * @param rightsIds
     */
    public List<CustomerLevelRights> issueLevelRightsForPayingMember(String customerId, List<Integer> rightsIds,Integer levelState,
                                                                     String recordId) {
        CustomerLevelRightsQueryRequest  customerLevelRightsQueryRequest = CustomerLevelRightsQueryRequest.builder()
                .rightsIdList(rightsIds)
                .delFlag(DeleteFlag.NO)
                .build();
        List<CustomerLevelRights> customerLevelRightsList = list(customerLevelRightsQueryRequest);
        customerLevelRightsList.forEach(rights -> {
            // 1.权益为券礼包  2.优惠券活动id不为null  3.优惠券类型为达到等级即发放
            if (rights.getRightsType() == LevelRightsType.COUPON_GIFT
                    && rights.getActivityId() != null) {
                String type = JSON.parseObject(rights.getRightsRule()).get("type").toString();
                boolean isSend;
                //立即生效
                if (NumberUtils.INTEGER_ZERO.equals(levelState)) {
                    isSend  = RightsCouponSendType.ISSUE_ONCE.getStateId().equals(type) || RightsCouponSendType.REPEAT.getStateId().equals(type);
                } else if (NumberUtils.INTEGER_ONE.equals(levelState)){
                    // 未生效，发放一次性发放券，其他形式不发
                    isSend  = RightsCouponSendType.ISSUE_ONCE.getStateId().equals(type);
                } else {
                    //其他状态就是参数错误！
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (isSend) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", customerId);
                    map.put("activityId", rights.getActivityId());
                    map.put("recordId",recordId);
                    // mq通知marketing模块发放优惠券
                    MqSendDTO mqSendDTO = new MqSendDTO();
                    mqSendDTO.setTopic(ProducerTopic.ISSUE_COUPONS);
                    mqSendDTO.setData(JSONObject.toJSONString(map));
                    mqSendProvider.send(mqSendDTO);
                }
            }
            // 2.权益赠送积分
            if (rights.getRightsType() == LevelRightsType.SEND_POINTS) {
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
        return customerLevelRightsList;
    }
}
