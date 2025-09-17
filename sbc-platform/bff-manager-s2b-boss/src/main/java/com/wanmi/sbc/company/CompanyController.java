package com.wanmi.sbc.company;

import com.wanmi.sbc.account.api.provider.company.CompanyAccountProvider;
import com.wanmi.sbc.account.api.provider.company.CompanyAccountQueryProvider;
import com.wanmi.sbc.account.api.request.company.CompanyAccountByCompanyInfoIdAndDefaultFlagRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountFindByAccountIdRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountRemitRequest;
import com.wanmi.sbc.account.api.response.company.CompanyAccountFindResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoProvider;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyAccountPageRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoModifyBusinessLicenceRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInformationSaveRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoByIdResponse;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoResponse;
import com.wanmi.sbc.customer.api.response.company.CompanyInformationModifyResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyAccountVO;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyAccountQueryRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyAccountResponse;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyInfoResponse;
import com.wanmi.sbc.elastic.bean.vo.companyAccount.EsCompanyAccountVO;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * 商家
 * @author sunkun
 * Created by sunkun on 2017/11/6.
 */
@RestController
@Validated
@RequestMapping("/company")
@Tag(name =  "CompanyController", description =  "S2B 平台端-商家管理API")
public class CompanyController {

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CompanyInfoProvider companyInfoProvider;

    @Autowired
    private CompanyAccountQueryProvider companyAccountQueryProvider;

    @Autowired
    private CompanyAccountProvider companyAccountProvider;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 商家列表
     * @param request   请求参数
     * @return          商家列表
     */
    @Operation(summary = "查询商家列表")
    @PostMapping(value = "/list")
    @ReturnSensitiveWords(functionName = "f_boss_company_list_sign_word")
    public BaseResponse<Page<EsCompanyInfoVO>> list(@RequestBody EsCompanyPageRequest request) {
        return getCompanyList(request);
    }

    /**
     * 查询供应商列表
     * @param request   请求参数
     * @return          商家列表
     */
    @Operation(summary = "查询供应商列表")
    @PostMapping(value = "/provider/list")
    public BaseResponse<Page<EsCompanyInfoVO>> providerList(@RequestBody EsCompanyPageRequest request) {
        return getCompanyList(request);
    }

    /**
     * 查询供应商结算商家列表
     * @param request   请求参数
     * @return          商家列表
     */
    @Operation(summary = "查询供应商结算商家列表")
    @PostMapping(value = "/provider/settlement/list")
    public BaseResponse<Page<EsCompanyInfoVO>> providerSettlementList(@RequestBody EsCompanyPageRequest request) {
        return getCompanyList(request);
    }

    /**
     * 查询开放权限商家列表
     * @param request   请求参数
     * @return          商家列表
     */
    @Operation(summary = "查询开放权限商家列表")
    @PostMapping(value = "/open/access")
    public BaseResponse<Page<EsCompanyInfoVO>> openAccess(@RequestBody EsCompanyPageRequest request) {
        return getCompanyList(request);
    }

    /**
     * 查询商家列表
     * @param request
     * @return
     */
    private BaseResponse<Page<EsCompanyInfoVO>> getCompanyList(EsCompanyPageRequest request) {
        request.setDeleteFlag(DeleteFlag.NO);
        request.setIsMasterAccount(NumberUtils.INTEGER_ONE);
        BaseResponse<EsCompanyInfoResponse> response = esStoreInformationQueryProvider.companyInfoPage(request);
        return BaseResponse.success(BaseResUtils
                .getResultFromRes(response,EsCompanyInfoResponse::getEsCompanyAccountPage));
    }

    /**
     * 工商信息修改
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改商家工商信息")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse<CompanyInformationModifyResponse> update(@Valid @RequestBody CompanyInformationSaveRequest request) {
        operateLogMqUtil.convertAndSend("设置","店铺信息","编辑店铺信息");
        return companyInfoProvider.modifyCompanyInformation(request);
    }

    /**
     * 查询公司信息
     *
     * @return
     */
    @Operation(summary = "查询商家公司信息")
    @Parameter(name = "id", description = "商家公司id", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_find_company_messages_sign_word")
    public BaseResponse<CompanyInfoResponse> findOne(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CompanyInfoResponse response = new CompanyInfoResponse();
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(id).build()
        ).getContext();
        KsBeanUtil.copyPropertiesThird(companyInfo, response);
        return BaseResponse.success(response);
    }

    /**
     * 商家列表(收款账户)
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询商家公司收款账户")
    @RequestMapping(value = "old/account", method = RequestMethod.POST)
    public BaseResponse<Page<CompanyAccountVO>> accountList(@RequestBody CompanyAccountPageRequest request) {
        request.setDeleteFlag(DeleteFlag.NO);
        return BaseResponse.success(companyInfoQueryProvider.pageCompanyAccount(request).getContext()
                .getCompanyAccountVOPage());
    }

    /**
     * 商家列表(收款账户) 从es搜索
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询商家公司收款账户")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public BaseResponse accountListFromEs(@RequestBody EsCompanyAccountQueryRequest request) {

        request.setCompanyInfoDelFlag(DeleteFlag.NO);
        request.setStoreDelFlag(DeleteFlag.NO);
        request.setEmployeeDelFlag(DeleteFlag.NO);
        request.setIsMasterAccount(NumberUtils.INTEGER_ONE);
        BaseResponse<EsCompanyAccountResponse> response = esStoreInformationQueryProvider.companyAccountPage(request);

        MicroServicePage<EsCompanyAccountVO> page = null;
        if(Objects.nonNull(response.getContext())){
             page = response.getContext().getEsCompanyAccountPage();
        }
        return BaseResponse.success(page);
    }


    /**
     * 商家账号明细
     *
     * @param companyInfoId
     * @return
     */
    @Operation(summary = "根据商家公司id查询商家收款账户列表")
    @Parameter(name = "companyInfoId", description = "商家公司id", required = true)
    @RequestMapping(value = "/account/detail/{companyInfoId}", method = RequestMethod.GET)
    public BaseResponse<List<com.wanmi.sbc.account.bean.vo.CompanyAccountVO>> accountDetail(@PathVariable Long companyInfoId) {
        return BaseResponse.success(companyAccountQueryProvider.listByCompanyInfoIdAndDefaultFlag(
                CompanyAccountByCompanyInfoIdAndDefaultFlagRequest.builder()
                        .companyInfoId(companyInfoId).defaultFlag(DefaultFlag.NO).build()
        ).getContext().getCompanyAccountVOList());
    }


    /**
     * 商家账号打款
     *
     * @param request
     * @return
     */
    @Operation(summary = "商家账号打款")
    @RequestMapping(value = "/account/remit", method = RequestMethod.PUT)
    public BaseResponse accountRemit(@RequestBody CompanyAccountRemitRequest request) {
        // 处理打款金额
        String remitPrice = new String(Base64.getUrlDecoder().decode(request.getRemitPriceCode()), StandardCharsets.UTF_8);
        request.setRemitPrice( new BigDecimal(remitPrice));
        if (request.getRemitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //操作日志记录
        CompanyAccountFindResponse findResponse =
                companyAccountQueryProvider.getByAccountId(new CompanyAccountFindByAccountIdRequest(request.getAccountId())).getContext();
        if (Objects.nonNull(findResponse)){
            CompanyInfoByIdResponse response =
                    companyInfoQueryProvider.getCompanyInfoById(new CompanyInfoByIdRequest(findResponse.getCompanyInfoId())).getContext();
            operateLogMqUtil.convertAndSend("财务", "确认打款", "确认打款：商家编号" + (Objects.nonNull(response) ?
                    response.getCompanyCode() : ""));
        }

        return companyAccountProvider.remit(request);
    }

    /**
     * 查询公司信息
     *
     * @return
     */
    @Operation(summary = "查询平台信息")
    @RequestMapping(value = "/boss", method = RequestMethod.POST)
    public BaseResponse<CompanyInfoResponse> bossCompany() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        if (companyInfoId == null) {
            return this.findOne(0L);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改公司营业执照
     * @return
     */
    @Operation(summary = "修改公司营业执照")
    @RequestMapping(value = "/business-licence", method = RequestMethod.POST)
    public BaseResponse modifyBusinessLicence(@RequestBody @Valid CompanyInfoModifyBusinessLicenceRequest request){
        request.setCompanyInfoId(0L);
        return companyInfoProvider.modifyBusinessLicence(request);
    }

}
