package com.wanmi.sbc.customer;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectSwitchQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectSwitchByCompanyInfoIdRequest;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectSwitchListByCompanyInfoIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectSwitchByCompanyInfoIdResponse;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectSwitchListByCompanyInfoIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectSwitchVO;
import com.wanmi.sbc.account.request.InvoiceProjectSwitchRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.invoice.*;
import com.wanmi.sbc.customer.api.response.invoice.*;
import com.wanmi.sbc.customer.bean.dto.CustomerInvoiceDTO;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAddRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceDeleteRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceModifyRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.InvoiceConfigGetResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.util.Objects.nonNull;

/**
 * 赠票配置相关
 * Created by CHENLI on 2017/4/21.
 */
@RestController
@Validated
@RequestMapping("/customer")
@Tag(name = "CustomerInvoiceBaseController", description = "S2B web公用-客户增票信息API")
public class CustomerInvoiceBaseController {

    @Autowired
    private CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CustomerInvoiceProvider customerInvoiceProvider;

    @Autowired
    private InvoiceProjectSwitchQueryProvider invoiceProjectSwitchQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsCustomerInvoiceProvider esCustomerInvoiceProvider;

    /**
     * 根据会员ID查询会员增专票
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据会员ID查询未删除的会员发票")
    @RequestMapping(value = "/invoice",method = RequestMethod.GET)
    public BaseResponse<CustomerInvoiceListResponse> findCustomerInvoiceByCustomerId() {
        CustomerInvoiceByCustomerIdAndDelFlagRequest customerInvoiceByCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        customerInvoiceByCustomerIdAndDelFlagRequest.setCustomerId(commonUtil.getOperatorId());
        CustomerInvoiceListResponse response =
                customerInvoiceQueryProvider
                        .getByCustomerIdAndDelFlag(customerInvoiceByCustomerIdAndDelFlagRequest)
                        .getContext();

        return BaseResponse.success(response);
    }

    /**
     * 根据会员ID查询会员增专票
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据会员ID和发票类型查询未删除的会员发票")
    @GetMapping(value = "/invoice/{invoiceStyle}")
    public BaseResponse<CustomerInvoiceListResponse> findCustomerInvoiceByCustomerIdAndStyle(@PathVariable Integer invoiceStyle) {
        InvoiceStyle invoiceStyle1 = InvoiceStyle.fromValue(invoiceStyle);
        CustomerInvoiceByCustomerIdAndDelFlagRequest request = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        request.setInvoiceStyle(invoiceStyle1);
        CustomerInvoiceListResponse response =
                customerInvoiceQueryProvider
                        .getByCustomerIdAndDelFlagAndStyle(request)
                        .getContext();

        return BaseResponse.success(response);
    }

    /**
     * 新增会员增专票
     * @return employee
     */
    @Operation(summary = "新增会员增专票")
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse saveCustomerInvoice(@Valid @RequestBody CustomerInvoiceAddRequest saveRequest) {
        if(saveRequest.getCompanyName().length()>50){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL) || saveRequest.getInvoiceStyle().equals(InvoiceStyle.COMPANY)){
            if(Objects.isNull(saveRequest.getTaxpayerNumber()) || (Objects.nonNull(saveRequest.getTaxpayerNumber()) &&
                    (saveRequest.getTaxpayerNumber().length() > 20 || saveRequest.getTaxpayerNumber().length() < 15))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        String customerId = commonUtil.getOperatorId();
        CustomerInvoiceByCustomerIdAndDelFlagRequest byCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        byCustomerIdAndDelFlagRequest.setCustomerId(customerId);
        CustomerInvoiceListResponse customerInvoiceListResponse = customerInvoiceQueryProvider.getByCustomerIdAndDelFlag(byCustomerIdAndDelFlagRequest).getContext();
        if(!CollectionUtils.isEmpty(customerInvoiceListResponse.getCustomerInvoiceVOList())){
            int sCount=0;  //增票数量
            int iCount=0;   //个人发票数量
            int cCount=0;   //单位发票数量

            for(CustomerInvoiceVO customerInvoiceVO : customerInvoiceListResponse.getCustomerInvoiceVOList()){
                switch (customerInvoiceVO.getInvoiceStyle()){
                    case SPECIAL:
                        sCount++;
                        break;
                    case INDIVIDUAL:
                        iCount++;
                        break;
                    case COMPANY:
                        cCount++;
                        break;
                    default:
                        break;
                }
//                boolean duplicateFlag = saveRequest.getCompanyName().equals(customerInvoiceVO.getCompanyName())
//                            || (saveRequest.getTaxpayerNumber()!=null && saveRequest.getTaxpayerNumber().equals(customerInvoiceVO.getTaxpayerNumber())
//                );
                if((saveRequest.getCompanyName().equals(customerInvoiceVO.getCompanyName())
                        && customerInvoiceVO.getInvoiceStyle() != InvoiceStyle.SPECIAL
                        && saveRequest.getInvoiceStyle() != InvoiceStyle.SPECIAL)
                 ||(customerInvoiceVO.getInvoiceStyle() == InvoiceStyle.SPECIAL
                        && saveRequest.getInvoiceStyle() == customerInvoiceVO.getInvoiceStyle()
                        && saveRequest.getCompanyName().equals(customerInvoiceVO.getCompanyName()))
                ){
                    return BaseResponse.error("发票抬头已经存在");
                }
                if(saveRequest.getTaxpayerNumber()!=null && saveRequest.getTaxpayerNumber().equals(customerInvoiceVO.getTaxpayerNumber())){
                    if (saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL)) {
                        return BaseResponse.error("发票纳税人识别号已经存在");
                    }else if(saveRequest.getInvoiceStyle().equals(InvoiceStyle.COMPANY)){
                        return BaseResponse.error("发票单位税号已经存在");
                    }
                }

            }
            if(sCount>=1 && saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL)){
                return BaseResponse.error("每个客户只可保存一条增票资质");
            }
            if(iCount>=10 && saveRequest.getInvoiceStyle().equals(InvoiceStyle.INDIVIDUAL)){
                return BaseResponse.error("每个客户只可保存10条个人发票记录");
            }
            if(cCount>=10 && saveRequest.getInvoiceStyle().equals(InvoiceStyle.COMPANY)){
                return BaseResponse.error("每个客户只可保存10条单位发票记录");
            }
        }



        saveRequest.setCustomerId(customerId);
        saveRequest.setEmployeeId(null);
        BaseResponse<CustomerInvoiceAddResponse> customerInvoiceAddBaseResponse = customerInvoiceProvider.add(saveRequest);
        EsCustomerInvoiceAddRequest esCustomerInvoiceAddRequest = KsBeanUtil.convert(saveRequest, EsCustomerInvoiceAddRequest.class);
        esCustomerInvoiceAddRequest.setCustomerInvoiceId(customerInvoiceAddBaseResponse.getContext().getCustomerInvoiceId());
        esCustomerInvoiceProvider.add(esCustomerInvoiceAddRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改会员增专票
     * @param saveRequest
     * @return employee
     */
    @Operation(summary = "修改会员发票")
    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse updateCustomerInvoice(@Valid @RequestBody CustomerInvoiceModifyRequest saveRequest) {
        if(Objects.isNull(saveRequest.getCustomerInvoiceId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(Objects.isNull(saveRequest.getInvoiceStyle())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(saveRequest.getCompanyName().length()>50 || saveRequest.getCustomerInvoiceId()==null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String customerId = commonUtil.getOperatorId();
        final boolean[] flag = {false};
        if(saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL) || saveRequest.getInvoiceStyle().equals(InvoiceStyle.COMPANY)){
            if(Objects.isNull(saveRequest.getTaxpayerNumber()) || (Objects.nonNull(saveRequest.getTaxpayerNumber()) &&
                    (saveRequest.getTaxpayerNumber().length() > 20 || saveRequest.getTaxpayerNumber().length() < 15))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        if (saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL)) {
            CustomerInvoiceByTaxpayerNumberRequest customerInvoiceByTaxpayerNumberRequest =
                    new CustomerInvoiceByTaxpayerNumberRequest();
            customerInvoiceByTaxpayerNumberRequest.setTaxpayerNumber(
                    saveRequest.getTaxpayerNumber());
            customerInvoiceByTaxpayerNumberRequest.setUserId(customerId);
            BaseResponse<CustomerInvoiceByTaxpayerNumberResponse>
                    invoiceByTaxpayerNumberResponseBaseResponse =
                            customerInvoiceQueryProvider.getByTaxpayerNumber(
                                    customerInvoiceByTaxpayerNumberRequest);
            CustomerInvoiceByTaxpayerNumberResponse customerInvoiceByTaxpayerNumberResponse =
                    invoiceByTaxpayerNumberResponseBaseResponse.getContext();
            if (Objects.nonNull(customerInvoiceByTaxpayerNumberResponse)) {
                if (!customerInvoiceByTaxpayerNumberResponse
                        .getCustomerInvoiceId()
                        .equals(saveRequest.getCustomerInvoiceId())) {
                    flag[0] = true;
                }
            }
            if (flag[0]) {
                return BaseResponse.error("纳税人识别号已存在");
            }
        }

        CustomerInvoiceByCustomerIdAndDelFlagRequest customerInvoiceByCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        customerInvoiceByCustomerIdAndDelFlagRequest.setCustomerId(commonUtil.getOperatorId());
        List<CustomerInvoiceVO> customerInvoiceVOList =
                customerInvoiceQueryProvider
                        .getByCustomerIdAndDelFlag(customerInvoiceByCustomerIdAndDelFlagRequest)
                        .getContext().getCustomerInvoiceVOList();
        List<Long> customerInvoiceIdList = new ArrayList<>();
        customerInvoiceVOList.forEach(customerInvoiceVO -> {
            if(customerInvoiceVO.getCompanyName().equals(saveRequest.getCompanyName())
                    && !customerInvoiceVO.getCustomerInvoiceId().equals(saveRequest.getCustomerInvoiceId())
                    && ((saveRequest.getInvoiceStyle() != InvoiceStyle.SPECIAL && customerInvoiceVO.getInvoiceStyle() != InvoiceStyle.SPECIAL)
                        || (saveRequest.getInvoiceStyle() == InvoiceStyle.SPECIAL && customerInvoiceVO.getInvoiceStyle() == InvoiceStyle.SPECIAL))
            ){
                customerInvoiceIdList.add(customerInvoiceVO.getCustomerInvoiceId());
            }
        });
        if(!CollectionUtils.isEmpty(customerInvoiceIdList)){

            return BaseResponse.error("发票抬头已经存在");

        }
        CustomerInvoiceByIdAndDelFlagRequest idRequest = new CustomerInvoiceByIdAndDelFlagRequest();
        idRequest.setCustomerInvoiceId(saveRequest.getCustomerInvoiceId());
        CustomerInvoiceByIdAndDelFlagResponse response = customerInvoiceQueryProvider.getByIdAndDelFlag(idRequest).getContext();
        if(Objects.isNull(response)){
            return BaseResponse.error("会员发票不存在");
        }
        if (!StringUtils.equals(customerId, response.getCustomerId())){
            return BaseResponse.error("非法请求");
        }
        // 用户提交的增票认证
        CustomerInvoiceDTO submitInvoice = KsBeanUtil.copyPropertiesThird(saveRequest, CustomerInvoiceDTO.class);
        // 已经存在的增票认证
        CustomerInvoiceDTO queryInvoice = KsBeanUtil.copyPropertiesThird(response, CustomerInvoiceDTO.class);
        if (submitInvoice.equals(queryInvoice)) {
            // 增票认证信息相同时，避免重复审核
            if(saveRequest.getInvoiceStyle()==InvoiceStyle.SPECIAL){
                return BaseResponse.error("会员增值税专用发票信息无任何变动，请修改后再提交");
            }
            if(saveRequest.getInvoiceStyle()==InvoiceStyle.INDIVIDUAL){
                return BaseResponse.error("会员个人发票信息无任何变动，请修改后再提交");
            }
            if(saveRequest.getInvoiceStyle()==InvoiceStyle.COMPANY){
                return BaseResponse.error("会员单位发票信息无任何变动，请修改后再提交");
            }

        }
        saveRequest.setEmployeeId(null);
        saveRequest.setCustomerId(customerId);
        customerInvoiceProvider.modify(saveRequest);
        esCustomerInvoiceProvider.modify(KsBeanUtil.convert(saveRequest, EsCustomerInvoiceModifyRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据会员ID查询审核通过的会员增专票
     * @return ResponseEntity<CustomerInvoiceInfoResponse>
     */
    @Operation(summary = "根据会员ID查询审核通过的会员增专票")
    @RequestMapping(value = "/invoiceInfo",method = RequestMethod.GET)
    public BaseResponse<CustomerInvoiceByCustomerIdResponse> findInfoByCustomerId() {
        BaseResponse<InvoiceConfigGetResponse> customerInvoiceConfigResponseBaseResponse = auditQueryProvider.getInvoiceConfig();
        InvoiceConfigGetResponse customerInvoiceConfigResponse = customerInvoiceConfigResponseBaseResponse.getContext();
        if (Objects.nonNull(customerInvoiceConfigResponse) && DefaultFlag.YES.toValue() == customerInvoiceConfigResponse.getStatus()){
            CustomerInvoiceByCustomerIdRequest request = new CustomerInvoiceByCustomerIdRequest();
            request.setCustomerId(commonUtil.getOperatorId());
            BaseResponse<CustomerInvoiceByCustomerIdResponse> customerInvoiceByCustomerIdResponseBaseResponse = customerInvoiceQueryProvider.getByCustomerId(request);
            CustomerInvoiceByCustomerIdResponse invoiceInfoResponse = customerInvoiceByCustomerIdResponseBaseResponse.getContext();
            invoiceInfoResponse.setConfigFlag(true);
            return BaseResponse.success(invoiceInfoResponse);
        }
        return BaseResponse.success(CustomerInvoiceByCustomerIdResponse.builder().build());
    }

    /**
     * 根据商家公司ID查询会员增专票
     * @return ResponseEntity<CustomerInvoiceInfoResponse>
     */
    @Operation(summary = "根据商家公司ID查询会员增专票")
    @Parameter(name = "companyInfoId", description = "商家公司ID", required = true)
    @RequestMapping(value = "/invoiceInfo/{companyInfoId}",method = RequestMethod.GET)
    public BaseResponse<CustomerInvoiceByCustomerIdResponse> findInfoByCustomerId(@PathVariable("companyInfoId") Long companyInfoId) {
        InvoiceProjectSwitchByCompanyInfoIdRequest invoiceProjectSwitchByCompanyInfoIdRequest = new InvoiceProjectSwitchByCompanyInfoIdRequest();
        invoiceProjectSwitchByCompanyInfoIdRequest.setCompanyInfoId(companyInfoId);
        BaseResponse<InvoiceProjectSwitchByCompanyInfoIdResponse> baseResponse = invoiceProjectSwitchQueryProvider.getByCompanyInfoId(invoiceProjectSwitchByCompanyInfoIdRequest);
        InvoiceProjectSwitchByCompanyInfoIdResponse response = baseResponse.getContext();
        if (Objects.isNull(response)){
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020007);
        }
        //如果不支持开票
        if (response.getIsSupportInvoice() == DefaultFlag.NO){
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020008);
        }
        CustomerInvoiceByCustomerIdResponse invoiceInfoResponse = CustomerInvoiceByCustomerIdResponse.builder().build();
        //支持增值税发票
        if (DefaultFlag.YES == response.getIsValueAddedTaxInvoice()){
            CustomerInvoiceByCustomerIdRequest request = new CustomerInvoiceByCustomerIdRequest();
            request.setCustomerId(commonUtil.getOperatorId());
            BaseResponse<CustomerInvoiceByCustomerIdResponse> customerInvoiceByCustomerIdResponseBaseResponse = customerInvoiceQueryProvider.getByCustomerId(request);
             invoiceInfoResponse = customerInvoiceByCustomerIdResponseBaseResponse.getContext();
            invoiceInfoResponse.setConfigFlag(true);
        }
        //是否支持纸质发票
        if (DefaultFlag.YES == response.getIsPaperInvoice()){
            invoiceInfoResponse.setPaperInvoice(true);
        }
        return BaseResponse.success(invoiceInfoResponse);
    }

    /**
     * pc订单确认页面批量查询每个店铺下的发票状态
     * @return ResponseEntity<CustomerInvoiceInfoResponse>
     */
    @Operation(summary = "pc订单确认页面批量查询每个店铺下的发票状态")
    @RequestMapping(value = "/invoices",method = RequestMethod.POST)
    public BaseResponse<List<CustomerInvoiceByCustomerIdResponse>> findInvoices(@RequestBody InvoiceProjectSwitchRequest request) {
        List<Long> companyInfoIds = request.getCompanyInfoIds();
        CustomerInvoiceByCustomerIdRequest customerInvoiceByCustomerIdRequest = new CustomerInvoiceByCustomerIdRequest();
        customerInvoiceByCustomerIdRequest.setCustomerId(commonUtil.getOperatorId());
        BaseResponse<CustomerInvoiceByCustomerIdResponse> customerInvoiceByCustomerIdResponseBaseResponse = customerInvoiceQueryProvider.getByCustomerId(customerInvoiceByCustomerIdRequest);
        CustomerInvoiceByCustomerIdResponse invoice = customerInvoiceByCustomerIdResponseBaseResponse.getContext();
        InvoiceProjectSwitchListByCompanyInfoIdRequest invoiceProjectSwitchListByCompanyInfoIdRequest = new InvoiceProjectSwitchListByCompanyInfoIdRequest();
        invoiceProjectSwitchListByCompanyInfoIdRequest.setCompanyInfoIds(companyInfoIds);
        BaseResponse<InvoiceProjectSwitchListByCompanyInfoIdResponse> baseResponse = invoiceProjectSwitchQueryProvider.listByCompanyInfoId(invoiceProjectSwitchListByCompanyInfoIdRequest);
        InvoiceProjectSwitchListByCompanyInfoIdResponse invoiceProjectSwitchListByCompanyInfoIdResponse = baseResponse.getContext();
        if (Objects.isNull(invoiceProjectSwitchListByCompanyInfoIdResponse)
                || CollectionUtils.isEmpty(invoiceProjectSwitchListByCompanyInfoIdResponse.getInvoiceProjectSwitchVOList())){
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.success(companyInfoIds.stream().map(id -> {
            InvoiceProjectSwitchVO project = invoiceProjectSwitchListByCompanyInfoIdResponse.getInvoiceProjectSwitchVOList().stream().
                    filter(p -> Objects.equals(id,p.getCompanyInfoId())).findFirst()
                    .orElseGet(InvoiceProjectSwitchVO::new);
            CustomerInvoiceByCustomerIdResponse response = CustomerInvoiceByCustomerIdResponse.builder().build();
            //支持增值税发票
            if(project.getIsValueAddedTaxInvoice() == DefaultFlag.YES) {
                BeanUtils.copyProperties(invoice, response);
                response.setConfigFlag(true);
            }
            response.setCompanyInfoId(id);
            //是否开启发票
            response.setSupport(project.getIsSupportInvoice() == DefaultFlag.YES);
            //是否支持纸质发票
            response.setPaperInvoice(project.getIsPaperInvoice() == DefaultFlag.YES);
            return response;
        }).collect(Collectors.toList()));
    }

    /**
     * 根据发票ID查询会员发票
     *
     * @param customerInvoiceId
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据发票ID查询会员发票")
    @Parameter(name = "customerInvoiceId", description = "会员发票Id",
            required = true)
    @RequestMapping(value = "/invoiceInfo/id/{customerInvoiceId}",method = RequestMethod.GET)
    public BaseResponse<CustomerInvoiceByIdAndDelFlagResponse> findByCustomerInvoiceIdAndDelFlag(@PathVariable("customerInvoiceId") Long customerInvoiceId) {
        CustomerInvoiceByIdAndDelFlagRequest customerInvoiceByIdAndDelFlagRequest = new CustomerInvoiceByIdAndDelFlagRequest();
        customerInvoiceByIdAndDelFlagRequest.setCustomerInvoiceId(customerInvoiceId);
        BaseResponse<CustomerInvoiceByIdAndDelFlagResponse> customerInvoiceByIdAndDelFlagResponseBaseResponse = customerInvoiceQueryProvider.getByIdAndDelFlag(customerInvoiceByIdAndDelFlagRequest);
        CustomerInvoiceByIdAndDelFlagResponse customerInvoiceByIdAndDelFlagResponse = customerInvoiceByIdAndDelFlagResponseBaseResponse.getContext();
        return BaseResponse.success(customerInvoiceByIdAndDelFlagResponse);
    }

    /**
     * 删除 增专票信息
     *
     * @param customerInvoiceId
     * @return
     */
    @Operation(summary = "删除 增专票信息")
    @RequestMapping(value = "/invoices/{customerInvoiceId}", method = RequestMethod.DELETE)
    @GlobalTransactional
    public BaseResponse deleteCustomerInvoice(
            @PathVariable("customerInvoiceId") Long customerInvoiceId) {
        CustomerInvoiceDeleteOneRequest request = new CustomerInvoiceDeleteOneRequest();
        request.setCustomerInvoiceId(customerInvoiceId);
        request.setCustomerId(commonUtil.getOperatorId());
        customerInvoiceProvider.deleteOne(request);
        EsCustomerInvoiceDeleteRequest esRequest = new EsCustomerInvoiceDeleteRequest();
        esRequest.setCustomerInvoiceIds(Lists.newArrayList(customerInvoiceId));
        esCustomerInvoiceProvider.delete(esRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
