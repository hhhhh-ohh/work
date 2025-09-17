package com.wanmi.sbc.customer.communityleader.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.communityleader.*;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointQueryRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerAddRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
import com.wanmi.sbc.customer.communityleader.repository.CommunityLeaderRepository;
import com.wanmi.sbc.customer.communitypickup.service.CommunityLeaderPickupPointService;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.repository.CustomerDetailRepository;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.mq.ProducerService;
import com.wanmi.sbc.customer.service.CustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>社区团购团长表业务逻辑</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Service
public class CommunityLeaderService {
    @Autowired
    private CommunityLeaderRepository communityLeaderRepository;

    @Autowired
    private CommunityLeaderPickupPointService communityLeaderPickupPointService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    /**
     * 新增社区团购团长表
     * @author dyt
     */
    @Transactional
    public CommunityLeader add(CommunityLeaderAddRequest request) {
        CommunityLeader communityLeader = KsBeanUtil.convert(request, CommunityLeader.class);
        if (communityLeader == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        Customer customer = customerService.findByCustomerAccountAndDelFlag(request.getLeaderAccount());
        if (Objects.nonNull(customer)) {
            //分销账号是否存在
            CommunityLeaderQueryRequest queryRequest = new CommunityLeaderQueryRequest();
            queryRequest.setCustomerId(customer.getCustomerId());
            List<CommunityLeader> oldLeaders = this.list(queryRequest);
            if (CollectionUtils.isNotEmpty(oldLeaders)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010003);
            }
            communityLeader.setCustomerId(customer.getCustomerId());
        } else {
            //boss会员新增
            String customerId = customerService.bossSaveCustomer(getCustomer(request)).getCustomerId();
            communityLeader.setCustomerId(customerId);
        }
        communityLeader.setAssistFlag(Constants.no);
        communityLeader.setCheckStatus(request.getCheckStatus());
        //管理端默认审核通过
        if (LeaderCheckStatus.CHECKED.equals(communityLeader.getCheckStatus())) {
            communityLeader.setCheckTime(LocalDateTime.now());
        }
        communityLeader.setCreateTime(LocalDateTime.now());
        communityLeader.setUpdateTime(LocalDateTime.now());
        communityLeader.setDelFlag(DeleteFlag.NO);
        communityLeader = communityLeaderRepository.save(communityLeader);
        communityLeaderPickupPointService.add(request.getPickupPointList(), communityLeader);

        String leaderName = communityLeader.getLeaderName();
        if(Objects.nonNull(customer)){
            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customer.getCustomerId());
            if(Objects.nonNull(customerDetail)){
                leaderName = customerDetail.getCustomerName();
            }
        }

        // 更新会员资金
        if(communityLeader.getCheckStatus().equals(LeaderCheckStatus.CHECKED)){
            producerService.modifyIsLeaderWithCustomerFunds(
                    communityLeader.getCustomerId(),
                    leaderName,
                    communityLeader.getLeaderAccount());
        }

        return communityLeader;
    }

    /**
     * 修改社区团购团长表
     * @author dyt
     */
    @Transactional
    public void modify(CommunityLeaderModifyRequest request) {
        CommunityLeader leader = this.getOne(request.getLeaderId());
        KsBeanUtil.copyPropertiesThird(request, leader);
        if (LeaderCheckStatus.NOT_PASS.equals(leader.getCheckStatus())) {
            leader.setCheckStatus(LeaderCheckStatus.WAIT_CHECK);
        }
        leader.setUpdateTime(LocalDateTime.now());
        communityLeaderRepository.save(leader);
        communityLeaderPickupPointService.modify(request.getPickupPointList(), leader);
    }

    /**
     * 修改社区团购团长表
     * @author dyt
     */
    @Transactional
    public void check(CommunityLeaderCheckByIdRequest request) {
        CommunityLeader leader = this.getOne(request.getLeaderId());
        if(LeaderCheckStatus.FORBADE.equals(request.getCheckStatus())){
            leader.setDisableReason(request.getReason());
            leader.setDisableTime(LocalDateTime.now());
        }else {
            if (LeaderCheckStatus.CHECKED.equals(leader.getCheckStatus())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "审核状态发生变化，请刷新页面");
            }
            leader.setCheckReason(request.getReason());
            //一次审核通过时间
            if (LeaderCheckStatus.WAIT_CHECK.equals(leader.getCheckStatus())) {
                leader.setCheckTime(LocalDateTime.now());
            }
        }
        leader.setCheckStatus(request.getCheckStatus());
        communityLeaderRepository.save(leader);
        communityLeaderPickupPointService.updateCheckStatusByLeaderId(request.getCheckStatus(), leader.getLeaderId());

        String leaderName = leader.getLeaderName();
        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(leader.getCustomerId());
        if(Objects.nonNull(customerDetail)){
            leaderName = customerDetail.getCustomerName();
        }

        // 更新会员资金
        if(request.getCheckStatus().equals(LeaderCheckStatus.CHECKED)){
            producerService.modifyIsLeaderWithCustomerFunds(
                    leader.getCustomerId(),
                    leaderName,
                    leader.getLeaderAccount());
        }

    }

    /**
     * 修改社区团购团长表
     *
     * @author dyt
     */
    @Transactional
    public void assist(CommunityLeaderModifyAssistByIdRequest request) {
        communityLeaderRepository.updateAssistFlagByLeaderIdIn(request.getAssistFlag(), request.getLeaderIds());
    }

    /**
     * 验证账号是否存在
     * @param account 账号
     * @param notLeaderId 非自身团长id
     */
    public void exists(String account, String notLeaderId) {
        CommunityLeaderQueryRequest request = CommunityLeaderQueryRequest.builder().notLeaderId(notLeaderId).leaderAccount(account).build();
        if (communityLeaderRepository.count(CommunityLeaderWhereCriteriaBuilder.build(request)) > 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "您已经是团长，请刷新页面");
        }
    }

    /**
     * 单个删除社区团购团长表
     * @author dyt
     */
    @Transactional
    public void deleteById(String id) {
        communityLeaderRepository.updateDeleteFlagByLeaderIdIn(Collections.singletonList(id));
    }

    /**
     * 批量删除社区团购团长表
     * @author dyt
     */
    @Transactional
    public void deleteByIdList(List<String> ids) {
        communityLeaderRepository.updateDeleteFlagByLeaderIdIn(ids);
    }

    /**
     * 单个查询社区团购团长表
     * @author dyt
     */
    public CommunityLeader getOne(String id){
        return this.list(CommunityLeaderQueryRequest.builder().leaderId(id).build()).stream().findFirst()
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购团长不存在"));
    }

    /**
     * 单个查询社区团购团长表
     * @author dyt
     */
    public CommunityLeader getByCustomer(String customerId){
        return this.list(CommunityLeaderQueryRequest.builder().customerId(customerId).build()).stream().findFirst()
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购团长不存在"));
    }

    /**
     * 分页查询社区团购团长表
     * @author dyt
     */
    public Page<CommunityLeader> page(CommunityLeaderQueryRequest queryReq){
        return communityLeaderRepository.findAll(
                CommunityLeaderWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询社区团购团长表
     * @author dyt
     */
    public List<CommunityLeader> list(CommunityLeaderQueryRequest queryReq){
        return communityLeaderRepository.findAll(CommunityLeaderWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 查询符合条件的团长数量
     * @author dyt
     */
    public Long count(CommunityLeaderQueryRequest queryReq){
        return communityLeaderRepository.count(CommunityLeaderWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 为自提点填充帮卖
     * @param pickupPointList 自提点信息
     */
    public void fillAssistFlag(List<CommunityLeaderPickupPointVO> pickupPointList) {
        if (CollectionUtils.isNotEmpty(pickupPointList)) {
            List<String> leaderIds = pickupPointList.stream().map(CommunityLeaderPickupPointVO::getLeaderId).collect(Collectors.toList());
            Map<String, CommunityLeader> leaderMap = this.list(CommunityLeaderQueryRequest.builder()
                    .leaderIdList(leaderIds).build()).stream().collect(Collectors.toMap(CommunityLeader::getLeaderId, Function.identity()));
            pickupPointList.stream().filter(c -> leaderMap.containsKey(c.getLeaderId())).forEach(c -> {
                CommunityLeader leader = leaderMap.get(c.getLeaderId());
                c.setAssistFlag(leader.getAssistFlag());
            });
        }
    }

    /**
     * 将实体包装成VO
     * @author dyt
     */
    public CommunityLeaderVO wrapperVo(CommunityLeader communityLeader) {
        if (communityLeader != null){
            return KsBeanUtil.convert(communityLeader, CommunityLeaderVO.class);
        }
        return null;
    }

    /**
     * 根据帮卖标识获取团长id
     * @param request 自提点查询条件
     * @return 团长id
     */
    public List<String> getLeaderByAssistFlag(CommunityLeaderPickupPointQueryRequest request) {
        if (request.getAssistFlag() == null) {
            return Collections.emptyList();
        }
        return this.list(CommunityLeaderQueryRequest.builder()
                        .likeLeaderName(request.getLikeLeaderName())
                        .likeLeaderAccount(request.getLikeLeaderAccount())
                        .checkStatus(request.getCheckStatus())
                        .leaderId(request.getLeaderId())
                        .customerId(request.getCustomerId())
                        .assistFlag(request.getAssistFlag())
                        .build())
                .stream()
                .map(CommunityLeader::getLeaderId).collect(Collectors.toList());
    }

    /**
     * 新增会员设置初始值
     *
     * @param request
     * @return
     */
    private CustomerAddRequest getCustomer(CommunityLeaderAddRequest request) {
        CustomerAddRequest customerAddRequest = new CustomerAddRequest();
        customerAddRequest.setS2bSupplier(false);
        customerAddRequest.setEmployeeId(request.getUserId());
        customerAddRequest.setCustomerAccount(request.getLeaderAccount());
        customerAddRequest.setCustomerName(request.getLeaderName());
        customerAddRequest.setContactPhone(request.getPickupPointList().get(0).getContactNumber());
        customerAddRequest.setOperator("");
        customerAddRequest.setCustomerType(CustomerType.PLATFORM);
        return customerAddRequest;
    }
}

