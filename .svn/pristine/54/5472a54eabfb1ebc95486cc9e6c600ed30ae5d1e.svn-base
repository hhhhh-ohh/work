package com.wanmi.sbc.customer;

import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueQueryProvider;
import com.wanmi.sbc.customer.api.request.CustomerEditRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerAddRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerEnterpriseCheckStateModifyRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.NoDeleteCustomerGetByAccountRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValuePageRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.NoDeleteCustomerGetByAccountResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailToEsVO;
import com.wanmi.sbc.customer.bean.vo.CustomerGrowthValueVO;
import com.wanmi.sbc.customer.validator.CustomerValidator;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerCheckStateModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailAddRequest;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsStoreCustomerRelaDTO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * 企业用户
 */
@Tag(name =  "企业用户API", description =  "enterpriseCustomer")
@RestController
@Validated
@RequestMapping(value = "/enterpriseCustomer")
public class EnterpriseCustomerBossController {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CustomerGrowthValueQueryProvider customerGrowthValueQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @InitBinder
    public void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof CustomerEditRequest) {
            binder.setValidator(customerValidator);
        }
    }

    /**
     * 保存企业会员
     *
     * @return
     */
    @Operation(summary = "保存企业会员")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> addEnterpriseCustomer(@Valid @RequestBody CustomerAddRequest request) {
        //账号已存在
        NoDeleteCustomerGetByAccountResponse customer = customerQueryProvider.getNoDeleteCustomerByAccount(new NoDeleteCustomerGetByAccountRequest
                (request.getCustomerAccount())).getContext();
        if (customer != null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010003);
        }
        if (Objects.isNull(request.getEnterpriseInfo()) || Objects.isNull(request.getEnterpriseInfo().getBusinessNatureType())
            || StringUtil.isBlank(request.getEnterpriseInfo().getEnterpriseName()) || StringUtil.isBlank(request.getEnterpriseInfo().getSocialCreditCode())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setOperator(commonUtil.getOperatorId());
        request.setCustomerType(CustomerType.PLATFORM);
        request.setEnterpriseCustomer(true);
        CustomerDetailToEsVO customerDetailToEsVO =  customerProvider.saveCustomer(request).getContext().getCustomerDetailToEsVO();


        //操作日志记录
        operateLogMQUtil.convertAndSend("客户", "新增企业会员",
                "新增企业会员：" + request.getCustomerAccount());

        EsCustomerDetailDTO customerDetailDTO =  KsBeanUtil.convert(customerDetailToEsVO, EsCustomerDetailDTO.class);
        if (Objects.nonNull(customerDetailToEsVO.getStoreCustomerRela())) {
            List<EsStoreCustomerRelaDTO> esStoreCustomerRelaList = new ArrayList<>();
            esStoreCustomerRelaList.add(KsBeanUtil.convert(customerDetailToEsVO.getStoreCustomerRela(), EsStoreCustomerRelaDTO.class));
            customerDetailDTO.setEsStoreCustomerRelaList(esStoreCustomerRelaList);
        }

        esCustomerDetailProvider.add(new EsCustomerDetailAddRequest(customerDetailDTO));

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    @Operation(summary = "分页查询会员成长值")
    @EmployeeCheck
    @RequestMapping(value = "/queryToGrowthValue", method = RequestMethod.POST)
    public ResponseEntity<MicroServicePage<CustomerGrowthValueVO>> queryGrowthValue(@RequestBody CustomerGrowthValuePageRequest customerGrowthValuePageRequest) {
        return ResponseEntity.ok(customerGrowthValueQueryProvider.page(customerGrowthValuePageRequest).getContext()
                .getCustomerGrowthValueVOPage());
    }

    /**
     * 审核企业会员
     *
     * @param request
     * @return
     */
    @Operation(summary = "审核企业会员")
    @RequestMapping(value = "/checkEnterpriseCustomer", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> checkEnterpriseCustomer(@RequestBody @Valid CustomerEnterpriseCheckStateModifyRequest request) {
        if(!commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerProvider.checkEnterpriseCustomer(request);

        EsCustomerCheckStateModifyRequest stateModifyRequest =  EsCustomerCheckStateModifyRequest.builder()
                .enterpriseCustomer(Boolean.TRUE).customerId(request.getCustomerId())
                .enterpriseCheckState(request.getEnterpriseCheckState()).enterpriseCheckReason(request.getEnterpriseCheckReason()).build();

        //获取企业会员
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (request.getCustomerId())).getContext();
        if (nonNull(customer)) {
            if (EnterpriseCheckState.CHECKED.equals(request.getEnterpriseCheckState())) {
                operateLogMQUtil.convertAndSend("企业会员", "审核企业会员", "审核企业会员：" + customer.getCustomerAccount());
            } else {
                operateLogMQUtil.convertAndSend("企业会员", "驳回企业会员", "驳回企业会员：" + customer.getCustomerAccount());
            }
        }
        esCustomerDetailProvider.modifyCustomerCheckState(stateModifyRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

}
