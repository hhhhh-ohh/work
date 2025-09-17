package com.wanmi.sbc.customer.provider.impl.agent;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.agent.model.root.Agent;
import com.wanmi.sbc.customer.agent.model.root.AgentAuditAuth;
import com.wanmi.sbc.customer.agent.model.root.CrmOaAuth;
import com.wanmi.sbc.customer.agent.service.AgentAuditAuthService;
import com.wanmi.sbc.customer.agent.service.AgentService;
import com.wanmi.sbc.customer.agent.service.OaCrmAuthService;
import com.wanmi.sbc.customer.api.provider.agent.AgentQueryProvider;
import com.wanmi.sbc.customer.api.request.agent.AgentGetByUniqueCodeRequest;
import com.wanmi.sbc.customer.api.request.agent.GetAgentRequest;
import com.wanmi.sbc.customer.api.response.agent.AgentGetByUniqueCodeResponse;
import com.wanmi.sbc.customer.api.response.agent.CrmOaAuthResponse;
import com.wanmi.sbc.customer.api.response.agent.GetAgentResponse;
import com.wanmi.sbc.customer.api.response.agent.GetUserAreaIdResponse;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.service.CustomerDetailService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class AgentQueryController implements AgentQueryProvider {

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentAuditAuthService agentAuditAuthService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private OaCrmAuthService oaCrmAuthService;


    @Override
    public BaseResponse<AgentGetByUniqueCodeResponse> getAgentGetByUniqueCode(@RequestBody @Valid AgentGetByUniqueCodeRequest request) {

        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        if (agent == null) {
            return BaseResponse.error("未查询到代理商信息");
        }

        AgentGetByUniqueCodeResponse agentGetByUniqueCodeResponse = KsBeanUtil.copyPropertiesThird(agent, AgentGetByUniqueCodeResponse.class);
        return BaseResponse.success(agentGetByUniqueCodeResponse);
    }

    @Override
    public BaseResponse<AgentGetByUniqueCodeResponse> queryVaildAgentByUniqueCode(@RequestBody @Valid AgentGetByUniqueCodeRequest request) {
        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        if (agent == null) {
            ;
            return BaseResponse.error("未查询到代理商信息");
        }

        // 校验查询唯一码是否有效
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.error("代理商信息已删除");
        }

        // 校验代理商是否审核通过
        if (agent.getAuditStatus() != 2) {
            return BaseResponse.error("唯一码未审核通过!");
        }

        // 校验代理商是否在有效期内
        LocalDateTime now = LocalDateTime.now();
        if (agent.getValidStart() != null && now.isBefore(agent.getValidStart())
                || agent.getValidEnd() != null && now.isAfter(agent.getValidEnd())) {
            return BaseResponse.error("唯一码未在有效期内!");
        }

        AgentGetByUniqueCodeResponse agentGetByUniqueCodeResponse = KsBeanUtil.copyPropertiesThird(agent, AgentGetByUniqueCodeResponse.class);
        return BaseResponse.success(agentGetByUniqueCodeResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> getAgent(@RequestBody GetAgentRequest request) {
        Agent agent = agentService.findOne(request.getAgentId());

        if (agent == null) {
            return BaseResponse.success(null);
        }

        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> getAgentByContactPhone(@RequestBody GetAgentRequest request) {
        Agent agent = agentService.findByContactPhoneOrderByCreateTimeDesc(request.getContactPhone());

        if (agent == null) {
            return BaseResponse.success(null);
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> getAgentByUniqueCode(@RequestBody GetAgentRequest request) {
        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());

        if (agent == null) {
            return BaseResponse.success(null);
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> queryAgent(@RequestBody GetAgentRequest request) {
        Agent agent = agentService.findByCustomerIdOrderByCreateTimeDesc(request.getUserId());

        if (agent == null) {
            return BaseResponse.success(null);
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<GetUserAreaIdResponse> queryUserAreaId(@RequestBody GetAgentRequest request) {
        Customer customer = customerService.findById(request.getUserId());

        if (customer == null) {
            return BaseResponse.success(null);
        }
        if (customer.getCustomerAccount() == null) {
            return BaseResponse.success(null);
        }
        if (customer.getCustomerAccount() == null) {
            return BaseResponse.success(null);
        }

        List<AgentAuditAuth> agentAuditAuths = agentAuditAuthService.findAllByContactPhone(customer.getCustomerAccount());
        if (CollectionUtils.isEmpty(agentAuditAuths)) {
            return BaseResponse.success(null);
        }

        List<Long> areaIdList = agentAuditAuths.stream()
                .map(AgentAuditAuth::getAreaId)
                .collect(Collectors.toList());

        GetUserAreaIdResponse getUserAreaIdResponse = new GetUserAreaIdResponse();
        getUserAreaIdResponse.setAreaIdList(areaIdList);
        return BaseResponse.success(getUserAreaIdResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> queryUserBindAgent(@RequestBody GetAgentRequest request) {
        Agent agent = agentService.queryUserBindAgent(request.getUserId());

        if (agent == null) {
            return BaseResponse.success(null);
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<GetAgentResponse> queryCustomerBindAgent(GetAgentRequest request) {
        CustomerDetail customerDetail = customerDetailService.findByCustomerId(request.getUserId());
        if (customerDetail == null
                || customerDetail.getAgentId() == null
                || customerDetail.getAgentUniqueCode() == null
                || customerDetail.getAgentId().equals("-1")
                || customerDetail.getAgentUniqueCode().equals("-1")) {
            return BaseResponse.success(null);
        }

        String agentUniqueCode = customerDetail.getAgentUniqueCode();
        Agent agent = agentService.findByAgentUniqueCode(agentUniqueCode);

        if (agent == null) {
            return BaseResponse.success(null);
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.success(null);
        }

        GetAgentResponse getAgentResponse = KsBeanUtil.copyPropertiesThird(agent, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponse);
    }

    @Override
    public BaseResponse<List<GetAgentResponse>> getOaAuthAgentList(@RequestBody GetAgentRequest getAgentRequest) {
        List<Agent> agentList = agentService.getAgentListByAreaId(getAgentRequest.getAreaId());
        if (CollectionUtils.isEmpty(agentList)) {
            return BaseResponse.success(new ArrayList<>());
        }
        //查询对应oa账号的权限
        String oaAccount = getAgentRequest.getOaAccount();
        if (oaAccount == null) {
            return BaseResponse.success(new ArrayList<>());
        }
        List<CrmOaAuth> crmOaAuthList = oaCrmAuthService.findOaCrmAuthByAccount(oaAccount);
        if (CollectionUtils.isEmpty(crmOaAuthList)) {
            return BaseResponse.success(new ArrayList<>());
        }
        //查询市级权限
        List<Long> cityIdList = crmOaAuthList.stream()
                .filter(crmOaAuth -> crmOaAuth.getAccountType() == 1)
                .flatMap(crmOaAuth -> Arrays.stream(crmOaAuth.getAreaCode().split(",")))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());
        //查询区县级权限
        List<Long> areaIdList = crmOaAuthList.stream()
                .filter(crmOaAuth -> crmOaAuth.getAccountType() == 2)
                .flatMap(crmOaAuth -> Arrays.stream(crmOaAuth.getAreaCode().split(",")))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());
        List<Agent> hasAuthAgentList = agentList.stream().filter(agent -> cityIdList.contains(agent.getCityId()) || areaIdList.contains(agent.getAreaId())).collect(Collectors.toList());
        List<GetAgentResponse> getAgentResponseList = KsBeanUtil.convert(hasAuthAgentList, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponseList);
    }

    @Override
    public BaseResponse<List<GetAgentResponse>> getAgentListByParentUniqueCode(@RequestBody GetAgentRequest getAgentRequest) {
        List<Agent> agentList = agentService.findSecondLevelAgentByParentUniqueCode(getAgentRequest.getAgentUniqueCode());
        List<GetAgentResponse> getAgentResponseList = KsBeanUtil.convert(agentList, GetAgentResponse.class);
        return BaseResponse.success(getAgentResponseList);
    }
    @Override
    public BaseResponse<CrmOaAuthResponse> crmOaAuth(@RequestParam(value = "oaAccount") String oaAccount){
        List<CrmOaAuth> crmOaAuthList = oaCrmAuthService.findOaCrmAuthByAccount(oaAccount);
        if (CollectionUtils.isEmpty(crmOaAuthList)) {
            return BaseResponse.success(new CrmOaAuthResponse());
        }
        //查询市级权限
        List<Long> cityIdList = crmOaAuthList.stream()
                .filter(crmOaAuth -> crmOaAuth.getAccountType() == 1)
                .flatMap(crmOaAuth -> Arrays.stream(crmOaAuth.getAreaCode().split(",")))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());
        //查询区县级权限
        List<Long> areaIdList = crmOaAuthList.stream()
                .filter(crmOaAuth -> crmOaAuth.getAccountType() == 2)
                .flatMap(crmOaAuth -> Arrays.stream(crmOaAuth.getAreaCode().split(",")))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());
        return BaseResponse.success(new CrmOaAuthResponse(cityIdList, areaIdList));
    }
}
