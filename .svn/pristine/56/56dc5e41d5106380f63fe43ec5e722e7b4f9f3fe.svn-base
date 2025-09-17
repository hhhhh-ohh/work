package com.wanmi.sbc.customer.provider.impl.agent;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.agent.model.root.Agent;
import com.wanmi.sbc.customer.agent.model.root.AgentAuditAuth;
import com.wanmi.sbc.customer.agent.model.root.AgentAuditLog;
import com.wanmi.sbc.customer.agent.model.root.AgentUpdatePosterAuth;
import com.wanmi.sbc.customer.agent.service.AgentAuditAuthService;
import com.wanmi.sbc.customer.agent.service.AgentAuditLogService;
import com.wanmi.sbc.customer.agent.service.AgentService;
import com.wanmi.sbc.customer.agent.service.AgentUpdatePosterAuthService;
import com.wanmi.sbc.customer.agent.util.PosterGenerator;
import com.wanmi.sbc.customer.api.provider.agent.AgentProvider;
import com.wanmi.sbc.customer.api.request.agent.*;
import com.wanmi.sbc.customer.api.response.agent.CreatePosterResponse;
import com.wanmi.sbc.customer.api.response.agent.CreateQrCodeResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeAccountResponse;
import com.wanmi.sbc.customer.bean.enums.AgentErrorCodeEnum;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.service.CustomerDetailService;
import com.wanmi.sbc.customer.employee.service.EmployeeService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import com.wanmi.sbc.customer.payingmembercustomerrel.service.PayingMemberCustomerRelService;
import com.wanmi.sbc.customer.service.CustomerService;
import com.wanmi.sbc.customer.util.qrcode.GenerateQrCodeUtil;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressByIdRequest;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressByIdResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import io.seata.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
public class AgentController implements AgentProvider {

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentAuditLogService agentAuditLogService;

    @Autowired
    private PayingMemberCustomerRelService payingMemberCustomerRelService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private GenerateQrCodeUtil generateQrCodeUtil;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PosterGenerator posterGenerator;

    @Autowired
    private AgentAuditAuthService agentAuditAuthService;

    @Autowired
    private AgentUpdatePosterAuthService agentUpdatePosterAuthService;

    //@Value("${qr.code.address.prefix:https://quickchart.io/qr?text=https://www.swdxf.group}")
    @Value("${qr.code.address.prefix:https://www.swdxf.group/bindAgent}")
    private String qrCodeAddressPrefix;

    @Value("${silver.card.level.price:3.00}")
    private BigDecimal silverCardLevelPrice;

    @Value("${gold.card.level.price:4.00}")
    private BigDecimal goldCardLevelPrice;

    @Value("${diamond.card.level.price:5.00}")
    private BigDecimal diamondCardLevelPrice;

    @Value("${renewal.ratio:0.05}")
    private BigDecimal renewalRatio;

    @Value("${agent.audit.role.name:一户一码审核员}")
    private String agentAuditRoleName;

    @Transactional
    @Override
    public BaseResponse addAgent(@RequestBody @Valid AddAgentRequest request) {
        String customerId = request.getUserId();
        Agent agent;
        //  审核状态 0已创建 1待审核 2通过 3驳回
        Agent byCustomerId = agentService.findByCustomerIdOrderByCreateTimeDesc(customerId);
        if (byCustomerId != null) {
            // 存在
            agent = byCustomerId;

            // 更新对应参数
            agent.setAgentName(request.getAgentName());
            agent.setBankAccount(request.getBankAccount());
            agent.setBankOpen(request.getBankOpen());
            agent.setBusinessLicense(request.getBusinessLicense());
            agent.setContactPerson(request.getContactPerson());
            agent.setContactPhone(request.getContactPhone());
            agent.setShopName(request.getShopName());
            agent.setShopAddress(request.getShopAddress());
            agent.setShopAttribute(request.getShopAttribute());

            agent.setProvinceId(request.getProvinceId());
            agent.setCityId(request.getCityId());
            agent.setAreaId(request.getAreaId());
            agent.setStreetId(request.getStreetId());

            agent.setProvinceName(request.getProvinceName());
            agent.setCityName(request.getCityName());
            agent.setAreaName(request.getAreaName());
            agent.setStreetName(request.getStreetName());

            agent.setAgentUniqueCode(byCustomerId.getAgentUniqueCode());
            agent.setDelFlag(DeleteFlag.NO);
            agent.setAuditStatus(1);
            agent.setCustomerId(customerId);
            // 代理商类型：1 小B 2一级代理商 3二级代理商 4一级合作商
            agent.setType(1);

            agentService.updateByIdNative(agent);
        } else {
            // 处理代理商信息
            agent = KsBeanUtil.copyPropertiesThird(request, Agent.class);
            agent.setAgentUniqueCode(getAgentUniqueCode());
            agent.setDelFlag(DeleteFlag.NO);
            agent.setAuditStatus(1);
            agent.setCustomerId(customerId);
            //代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商
            agent.setType(1);

            agentService.save(agent, customerId);
        }

        List<AgentAuditLog> agentAuditLogList = agentAuditLogService.findByAgentIdAndAuditStatus(agent.getAgentId(), 1);
        // 保存代理商审核日志
        if (CollectionUtils.isNotEmpty(agentAuditLogList)) {
            // 编辑
            AgentAuditLog agentAuditLog = agentAuditLogList.get(0);
            agentAuditLog.setAgentId(agent.getAgentId());
            agentAuditLog.setAuditStatus(1);

            agentAuditLog.setAuditorId(customerId);
            agentAuditLog.setAuditorName(customerId);
            agentAuditLog.setAuditTime(LocalDateTime.now());
            agentAuditLog.setDelFlag(DeleteFlag.NO);
            agentAuditLogService.update(agentAuditLog);
        } else {
            // 新增
            AgentAuditLog agentAuditLog = new AgentAuditLog();

            agentAuditLog.setAgentId(agent.getAgentId());
            agentAuditLog.setAuditStatus(1);

            agentAuditLog.setAuditorId(customerId);
            agentAuditLog.setAuditorName(customerId);
            agentAuditLog.setAuditTime(LocalDateTime.now());
            agentAuditLog.setDelFlag(DeleteFlag.NO);
            agentAuditLogService.save(agentAuditLog);
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取代理商唯一码
     *
     * @return
     */
    public static String getAgentUniqueCode() {
        return "AG".concat(String.valueOf(System.currentTimeMillis()).substring(4, 10)).concat(RandomStringUtils.randomNumeric(3));
    }

    @Override
    public BaseResponse updateAgent(@RequestBody @Valid UpdateAgentRequest request) {
        Agent agent = KsBeanUtil.copyPropertiesThird(request, Agent.class);

        String employeeId = request.getUserId();
        agent.setUpdatePerson(employeeId);
        agent.setCustomerId(employeeId);

        agentService.update(agent);

        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteAgent(@RequestBody @Valid DeleteAgentRequest request) {
        String agentId = request.getAgentId();
        String employeeId = request.getUserId();

        agentService.delete(agentId, employeeId);

        return BaseResponse.SUCCESSFUL();
    }

    @Transactional
    @Override
    public BaseResponse auditAgent(@RequestBody @Valid AuditAgentRequest request) {
        Agent agent = agentService.findOne(request.getAgentId());
        if (agent == null) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100001);
        }

        // 判断用户是否注已经绑定过
        if (StringUtils.isBlank(request.getUserId())) {
            return BaseResponse.error("用户信息不能为空");
        }

        // 判断用户信息
        Customer customer = customerService.findById(request.getUserId());
        if (customer == null) {
            return BaseResponse.error("用户信息不存在");
        }

        String customerAccount = customer.getCustomerAccount();
        if (StringUtils.isBlank(request.getUserId())) {
            return BaseResponse.error("用户账户不能为空");
        }

        // 校验权限
        checkAuditAuth(customerAccount, agent.getAreaId());

        // PlatformAddressByIdRequest platformAddressByIdRequest =
        //         PlatformAddressByIdRequest.builder().id(String.valueOf(agent.getAreaId())).build();
        // BaseResponse<PlatformAddressByIdResponse> addressQueryProviderById = platformAddressQueryProvider.getById(platformAddressByIdRequest);
        // if (addressQueryProviderById.getContext() ==  null || addressQueryProviderById.getContext().getPlatformAddressVO() == null) {
        //     throw new SbcRuntimeException(AgentErrorCodeEnum.K100005);
        // }
        //
        // PlatformAddressVO platformAddressVO = addressQueryProviderById.getContext().getPlatformAddressVO();
        // String addrName = platformAddressVO.getAddrName();
        // if (StringUtils.isBlank(addrName)) {
        //     throw new SbcRuntimeException(AgentErrorCodeEnum.K100006);
        // }
        //
        // // 校验是否有权限操作
        // String employeeId = request.getUserId();
        // Optional<EmployeeAccountResponse> byEmployeeId = employeeService.findByEmployeeId(employeeId);
        // if (!byEmployeeId.isPresent() || StringUtils.isBlank(byEmployeeId.get().getRoleName())) {
        //     throw new SbcRuntimeException(AgentErrorCodeEnum.K100005);
        // }
        // if (!byEmployeeId.get().getRoleName().contains(addrName)) {
        //     throw new SbcRuntimeException(AgentErrorCodeEnum.K100006);
        // }


        // 代理商实体类
        // 驳回
        if (request.getAuditStatus() == 3) {
            agent.setRejectReason(request.getRejectReason());
        }
        // 通过
        if (request.getAuditStatus() == 2) {
            LocalDateTime validStart = LocalDateTime.now();
            LocalDateTime validEnd = validStart.plusYears(1L);
            // BigDecimal silverCardLevelPrice = new BigDecimal("3");
            // BigDecimal goldCardLevelPrice = new BigDecimal("4");
            // BigDecimal diamondCardLevelPrice = new BigDecimal("5");
            // BigDecimal renewalRatio = new BigDecimal("0.05");
            String url = qrCodeAddressPrefix + "?agentCode=" + agent.getAgentUniqueCode();
            String qrCodeName = agent.getAgentName() + "代理商唯一码";
            // String qrCodeAddress = generateQrCodeUtil.generateAndUploadQrCode(url, qrCodeName);
            // String qrCodeAddress = "https://quickchart.io/qr?text=" + url;
            String qrCodeAddress = posterGenerator.generateNewQrCode(url, agent);
            String posterAddress = posterGenerator.generateNewPoster(url, agent);

            // 新增修改参数
            agent.setBusinessLicense(request.getBusinessLicense());
            agent.setContactPerson(request.getContactPerson());
            agent.setSchoolName(request.getSchoolName());
            agent.setShopName(request.getShopName());

            agent.setValidStart(validStart);
            agent.setValidEnd(validEnd);
            agent.setSilverCardLevelPrice(silverCardLevelPrice);
            agent.setGoldCardLevelPrice(goldCardLevelPrice);
            agent.setDiamondCardLevelPrice(diamondCardLevelPrice);
            agent.setRenewalRatio(renewalRatio);
            agent.setQrCodeAddress(qrCodeAddress);
            agent.setPosterAddress(posterAddress);
        }

        agent.setType(request.getType());
        agent.setAuditStatus(request.getAuditStatus());
        agent.setUpdateTime(LocalDateTime.now());
        agent.setUpdatePerson(request.getUserId());
        agentService.updateByIdNative(agent);

        // 代理商审核记录实体类
        // 代理商id
        List<String> agentIdList = Lists.newArrayList(request.getAgentId());
        Integer auditStatus = 1;
        List<AgentAuditLog> agentAuditLogList = agentAuditLogService.findByAgentIdInAndAuditStatus(agentIdList, auditStatus);
        if (CollectionUtils.isEmpty(agentAuditLogList)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100009);
        }
        AgentAuditLog agentAuditLog = agentAuditLogList.get(0);
        agentAuditLog.setAuditStatus(request.getAuditStatus());
        agentAuditLog.setAuditOpinion(request.getAuditOpinion());
        // 驳回
        if (request.getAuditStatus() == 3) {
            agentAuditLog.setRejectReason(request.getRejectReason());
        }
        agentAuditLog.setAuditorId(request.getUserId());
        agentAuditLog.setAuditorName(customerAccount);
        agentAuditLog.setAuditTime(LocalDateTime.now());
        agentAuditLog.setDelFlag(DeleteFlag.NO);

        agentAuditLogService.update(agentAuditLog);

        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 校验权限
     *
     * @param contactPhone
     * @param areaId
     */
    public void checkAuditAuth(String contactPhone, Long areaId) {
        List<AgentAuditAuth> agentAuditAuthList = agentAuditAuthService.findAllByContactPhone(contactPhone);
        if (CollectionUtils.isEmpty(agentAuditAuthList)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100005);
        }

        Set<Long> areaIdSet = agentAuditAuthList.stream().map(AgentAuditAuth::getAreaId).collect(Collectors.toSet());
        if (!areaIdSet.contains(areaId)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100006);
        }
    }


    @Override
    public BaseResponse bindAgent(@RequestBody @Valid BindAgentRequest request) {
        if (StringUtils.isBlank(request.getAgentUniqueCode())) {
            return BaseResponse.error("系统唯一码不能为空");
        }

        // 获取代理商信息
        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        // 校验查询唯一码是否有效
        if (agent == null) {
            return BaseResponse.error("未查询到代理商信息");
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.error("唯一码已删除");
        }
        // 校验代理商是否审核通过
        if (agent.getAuditStatus() != 2) {
            return BaseResponse.error("唯一码未审核通过");
        }

        // 校验代理商是否在有效期内
        LocalDateTime now = LocalDateTime.now();
        if (agent.getValidStart() != null && now.isBefore(agent.getValidStart()) || agent.getValidEnd() != null && now.isAfter(agent.getValidEnd())) {
            return BaseResponse.error("唯一码未在有效期内");
        }

        // 判断用户是否注已经绑定过
        if (StringUtils.isBlank(request.getUserId())) {
            return BaseResponse.error("用户信息不能为空");
        }

        CustomerDetail customerDetail = customerDetailService.findAnyByCustomerId(request.getUserId());
        if (customerDetail == null) {
            return BaseResponse.error("用户信息不存在");
        }
        if (!StringUtils.equals(customerDetail.getAgentId(), "-1") && StringUtils.isNotBlank(customerDetail.getAgentId())) {
            return BaseResponse.error("用户信息已绑定代理商");
        }
        if (!StringUtils.equals(customerDetail.getAgentUniqueCode(), "-1") && StringUtils.isNotBlank(customerDetail.getAgentUniqueCode())) {
            return BaseResponse.error("用户信息已绑定代理商");
        }

//        // 调用客户信息判断是否是普通会员
//        Boolean isMember = payingMemberCustomerRelService.checkPayMember(request.getUserId());
//        if (!isMember) {
//            return BaseResponse.error("用户不是喵喵伴学会员");
//        }

        // 绑定操作
        customerDetail.setCustomerId(request.getUserId());
        customerDetail.setAgentId(agent.getAgentId());
        customerDetail.setAgentUniqueCode(agent.getAgentUniqueCode());

        customerDetailService.update(customerDetail);

        return BaseResponse.SUCCESSFUL();
    }


    @Override
    public BaseResponse extendVaildAgent(@RequestBody @Valid ExtendVaildAgentRequest request) {

        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        if (agent == null) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100001);
        }

        // 校验是否有权限操作
        String employeeId = request.getUserId();
        Optional<EmployeeAccountResponse> byEmployeeId = employeeService.findByEmployeeId(employeeId);
        if (!byEmployeeId.isPresent() || StringUtils.isBlank(byEmployeeId.get().getRoleName())) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100005);
        }
        if (byEmployeeId.get().getRoleName().contains(agentAuditRoleName)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100006);
        }

        // 设置有效期
        LocalDateTime validStart = agent.getValidStart();
        LocalDateTime validEnd = agent.getValidEnd();
        if (validStart == null) {
            validStart = LocalDateTime.now();
            validEnd = validStart.plusYears(request.getYears());
        } else {
            validStart = validEnd;
            validEnd = validStart.plusYears(request.getYears());
        }

        agent.setValidStart(validStart);
        agent.setValidEnd(validEnd);
        agent.setUpdateTime(LocalDateTime.now());
        agent.setUpdatePerson(request.getUserId());
        agentService.update(agent);

        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<CreateQrCodeResponse> createQrCode(@RequestBody @Valid CreateQrCodeRequest request) {
        // 获取代理商信息
        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        // 校验查询唯一码是否有效
        if (agent == null) {
            return BaseResponse.error("未知的代理商");
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.error("唯一码已删除");
        }

        // 创建二维码
        String url = qrCodeAddressPrefix + "?agentCode=" + agent.getAgentUniqueCode();

        String newQrCodeUrl;
        try {
            newQrCodeUrl = posterGenerator.generateNewQrCode(url, agent);
        } catch (Exception e) {
            log.error("生成二维码失败", e);
            return BaseResponse.error("生成二维码失败");
        }

        CreateQrCodeResponse response = new CreateQrCodeResponse();
        response.setQrCodeAddress(newQrCodeUrl);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CreatePosterResponse> createPoster(@RequestBody @Valid CreatePosterRequest request) {
        if (StringUtils.isBlank(request.getAgentUniqueCode())) {
            return BaseResponse.error("系统唯一码不能为空");
        }
        // 获取代理商信息
        Agent agent = agentService.findByAgentUniqueCode(request.getAgentUniqueCode());
        // 校验查询唯一码是否有效
        if (agent == null) {
            return BaseResponse.error("未知的代理商");
        }
        // 校验代理商是否已删除
        if (agent.getDelFlag() == DeleteFlag.YES) {
            return BaseResponse.error("唯一码已删除");
        }

        // 创建二维码
        String url = qrCodeAddressPrefix + "?agentCode=" + agent.getAgentUniqueCode();
        String qrCodeAddress = "https://quickchart.io/qr?text=" + url;

        String newPosterUrl;
        try {
            newPosterUrl = posterGenerator.generateNewPoster(url, agent);
        } catch (Exception e) {
            log.error("生成海报失败", e);
            return BaseResponse.error("海报生成失败");
        }

        CreatePosterResponse response = new CreatePosterResponse();
        response.setPosterAddress(newPosterUrl);
        return BaseResponse.success(response);
    }


    @Override
    public BaseResponse updateCityPoster(@RequestBody @Valid UpdateCityPosterAgentRequest request) {
        // 判断用户信息
        Customer customer = customerService.findById(request.getUserId());
        if (customer == null) {
            return BaseResponse.error("用户信息不存在");
        }
        if (StringUtils.isBlank(request.getUserId())) {
            return BaseResponse.error("用户账户不能为空");
        }

        String customerAccount = customer.getCustomerAccount();
        checkUpdateCityPoster(customerAccount, request.getCityId());

        List<Agent> agents = agentService.findByCityIdAndAuditStatus(request.getCityId(), 2);
        if (CollectionUtils.isEmpty(agents)) {
            return BaseResponse.SUCCESSFUL();
        }

        agents.forEach(agent -> {
            String url = qrCodeAddressPrefix + "?agentCode=" + agent.getAgentUniqueCode();
            String qrCodeAddress = posterGenerator.generateNewQrCode(url, agent);
            String posterAddress = posterGenerator.generateNewPoster(url, agent);

            agent.setQrCodeAddress(qrCodeAddress);
            agent.setPosterAddress(posterAddress);
            agent.setUpdateTime(LocalDateTime.now());
            agent.setUpdatePerson(request.getUserId());
            agentService.update(agent);
        });

        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 校验更新城市海报权限
     *
     * @param contactPhone
     * @param cityId
     */
    public void checkUpdateCityPoster(String contactPhone, Long cityId) {
        List<AgentUpdatePosterAuth> agentUpdatePosterAuthList = agentUpdatePosterAuthService.findAllByContactPhone(contactPhone);
        if (CollectionUtils.isEmpty(agentUpdatePosterAuthList)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100010);
        }

        Set<Long> cityIdSet = agentUpdatePosterAuthList.stream().map(AgentUpdatePosterAuth::getCityId).collect(Collectors.toSet());
        if (!cityIdSet.contains(cityId)) {
            throw new SbcRuntimeException(AgentErrorCodeEnum.K100011);
        }
    }
}
