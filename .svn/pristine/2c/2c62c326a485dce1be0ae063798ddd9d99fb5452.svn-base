package com.wanmi.sbc.store;

import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.SecurityUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoProvider;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoAllModifyRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeAccountNameExistsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeModifyAllRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerInfoUpdateRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreGetByStoreNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreModifyRequest;
import com.wanmi.sbc.customer.api.request.store.StoreSaveRequest;
import com.wanmi.sbc.customer.api.response.store.NoDeleteStoreByStoreNameResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationQueryProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoQueryPageRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.util.WeakPasswordsCheckUtil;
import com.wanmi.sbc.util.sms.SmsSendUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 店铺信息服务
 * Created by CHENLI on 2017/11/2.
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class StoreBaseService {
    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CompanyInfoProvider companyInfoProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private EsStoreInformationQueryProvider esStoreInformationQueryProvider;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Autowired
    private WeakPasswordsCheckUtil weakPasswordsCheckUtil;

    /**
     * s2b boss 修改商家
     *
     * @param saveRequest
     * @return
     */
    @Transactional
    public StoreVO updateStore(StoreSaveRequest saveRequest) {
        return this.baseUpdateStore(saveRequest, Boolean.TRUE);
    }


    /**
     * s2b supplier 修改商家
     *
     * @param saveRequest
     * @return
     */
    @Transactional
    public StoreVO updateStoreForSupplier(StoreSaveRequest saveRequest) {
        return this.baseUpdateStore(saveRequest, Boolean.FALSE);
    }


    /**
     * 修改店铺基本信息
     * 修改店铺基本信息 并且修改商家信息
     *
     * @param saveRequest
     * @return
     */
    @GlobalTransactional
    @Transactional
    public StoreVO baseUpdateStore(StoreSaveRequest saveRequest, Boolean isS2bBoss) {
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(saveRequest.getStoreId()))
                .getContext().getStoreVO();
        //店铺信息不存在
        if (Objects.isNull(store)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010105);
        }
        //供应商名称重复
        companyInfoQueryProvider.listCompanyInfo(
                CompanyListRequest.builder()
                        .equalSupplierName(saveRequest.getSupplierName())
                        .deleteFlag(DeleteFlag.NO).build())
                .getContext().getCompanyInfoVOList()
                .forEach(companyInfo -> {
                    if (!companyInfo.getCompanyInfoId().equals(saveRequest.getCompanyInfoId())) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010103);
                    }
                });
        //店铺名称重复
        NoDeleteStoreByStoreNameResponse response = storeQueryProvider.getNoDeleteStoreByStoreName(new
                NoDeleteStoreGetByStoreNameRequest(saveRequest
                .getStoreName())).getContext();

        if (response.getStoreId() != null && !response.getStoreId().equals(saveRequest.getStoreId())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010106);
        }

        //供应商不存在
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(saveRequest.getCompanyInfoId()).build()
        ).getContext();

        if (Objects.isNull(companyInfo) || DeleteFlag.YES.equals(companyInfo.getDelFlag())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010102);
        }
        KsBeanUtil.copyProperties(saveRequest, store);

        if (saveRequest.getStreetId() == null) {
            store.setStreetId(null);
        }

        // 省、市、区id任一个为null，都需要完善
        if(Objects.isNull(saveRequest.getProvinceId()) || Objects.isNull(saveRequest.getCityId())
                || Objects.isNull(saveRequest.getAreaId())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
        }

        PlatformAddressVerifyRequest platformAddressVerifyRequest = new PlatformAddressVerifyRequest();
        platformAddressVerifyRequest.setProvinceId(String.valueOf(saveRequest.getProvinceId()));
        platformAddressVerifyRequest.setCityId(String.valueOf(saveRequest.getCityId()));
        platformAddressVerifyRequest.setAreaId(String.valueOf(saveRequest.getAreaId()));
        if (saveRequest.getStreetId() == null) {
            platformAddressVerifyRequest.setStreetId(null);
        }else {
            platformAddressVerifyRequest.setStreetId(String.valueOf(saveRequest.getStreetId()));
        }

        KsBeanUtil.copyProperties(saveRequest, companyInfo);
        companyInfo.setContactName(saveRequest.getContactPerson());
        companyInfo.setContactPhone(saveRequest.getContactMobile());
        companyInfo.setDetailAddress(saveRequest.getAddressDetail());
        companyInfo.setStoreType(saveRequest.getStoreType());

        //s2bboss可以修改供应商账号 s2bsupplier不可以修改账号
        if (isS2bBoss) {
            if ( Boolean.TRUE.equals(platformAddressQueryProvider.verifyAddress(platformAddressVerifyRequest).getContext())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
            }
            //供应商的主账号
            EmployeeListRequest listRequest = new EmployeeListRequest();
            listRequest.setCompanyInfoId(companyInfo.getCompanyInfoId());
            listRequest.setIsMasterAccount(Constants.yes);
            EmployeeListVO employee = employeeQueryProvider.list(listRequest).getContext().getEmployeeList().get(0);
            //linkedMall,vop供应商不允许修改账号
            if(Objects.nonNull(store.getCompanySourceType()) && store.getCompanySourceType().toValue() > 0){
                saveRequest.setAccountName(employee.getAccountName());
            }
            //如果供应商账号变更
            if (!Objects.equals(employee.getAccountName(), saveRequest.getAccountName())) {
                //查询该变更账号是否重复

                AccountType accountType = StoreType.SUPPLIER.equals(store.getStoreType()) ?
                        AccountType.s2bSupplier : AccountType.s2bProvider;
                boolean isExists = employeeQueryProvider.accountNameIsExists(
                        EmployeeAccountNameExistsRequest.builder()
                                .accountName(saveRequest.getAccountName())
                                .accountType(accountType).build()
                ).getContext().isExists();

                if (isExists) {
                    if (AccountType.s2bProvider.equals(accountType)) {
                        //供应商账号已存在
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010136);
                    }
                    if (AccountType.s2bSupplier.equals(accountType)) {
                        //商家账号已存在
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010137);
                    }
                }
                //员工版本，不一致：对应登录账号强制下线
                employee.setVersionNo(System.currentTimeMillis()/1000);
                employee.setAccountName(saveRequest.getAccountName());
                employee.setEmployeeMobile(saveRequest.getAccountName());
            }

            //修改登录密码，并发短信通知
            if (saveRequest.getIsResetPwd()) {
                if (StringUtils.isEmpty(saveRequest.getAccountPassword())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                //先解密
                byte[] base64Pwd = Base64.getDecoder().decode(saveRequest.getAccountPassword());
                String password = new String(base64Pwd);

                //弱密码校验处理(前端是明文)
                weakPasswordsCheckUtil.weakPasswordsCheck(MD5Util.md5Hex(password));

                String encodePwd = SecurityUtil.getStoreLogpwd(String.valueOf(employee.getEmployeeId()),
                        MD5Util.md5Hex(password), employee.getEmployeeSaltVal());
                employee.setAccountPassword(encodePwd);
                //发短信通知
                smsSendUtil.send(SmsTemplate.EMPLOYEE_PASSWORD, new String[]{saveRequest.getAccountName()},
                        saveRequest.getAccountName(), password);
            }
            EmployeeModifyAllRequest modifyAllRequest = new EmployeeModifyAllRequest();
            KsBeanUtil.copyPropertiesThird(employee, modifyAllRequest);
            employeeProvider.modifyAllById(modifyAllRequest);
        }

        //更新供应商信息
        CompanyInfoAllModifyRequest request = new CompanyInfoAllModifyRequest();
        KsBeanUtil.copyPropertiesThird(companyInfo, request);
        companyInfoProvider.modifyAllCompanyInfo(request);
        store.setCompanyInfo(companyInfo);

//        GoodsModifySupplierNameRequest goodsModifySupplierNameRequest = new GoodsModifySupplierNameRequest();
//        goodsModifySupplierNameRequest.setSupplierName(saveRequest.getSupplierName());
//        goodsModifySupplierNameRequest.setCompanyInfoId(saveRequest.getCompanyInfoId());
//        goodsProvider.modifySupplierName(goodsModifySupplierNameRequest);

        //更新分账商户名称
        LedgerInfoUpdateRequest ledgerInfoUpdateRequest = new LedgerInfoUpdateRequest();
        ledgerInfoUpdateRequest.setCompanyInfoId(companyInfo.getCompanyInfoId().toString());
        ledgerInfoUpdateRequest.setName(companyInfo.getSupplierName());
        ledgerReceiverRelProvider.updateNameAndCode(ledgerInfoUpdateRequest);
        //更新店铺信息
        StoreModifyRequest request1 = KsBeanUtil.copyPropertiesThird(store, StoreModifyRequest.class);

        StoreVO savedStore = storeProvider.modifyStoreInfo(request1).getContext();
        // 接口日志记录需要获取companyInfo中的companyCode值
        savedStore.setCompanyInfo(companyInfo);
        return savedStore;
    }

    /**
     * 修改店铺运费计算方式
     *
     * @param storeId
     */
    @Transactional
    public void updateStoreFreightType(Long storeId, DefaultFlag freightTemplateType) {
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(storeId)).getContext().getStoreVO();
        if (store == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!Objects.equals(store.getFreightTemplateType(), freightTemplateType)) {
            store.setFreightTemplateType(freightTemplateType);

            StoreModifyRequest request = new StoreModifyRequest();

            KsBeanUtil.copyPropertiesThird(store, request);

            storeProvider.modifyStoreInfo(request);
        }
    }

    /**
     * @description 填充供应商名称
     * @Author qiyuanzhao
     * @Date 2022/7/11 15:33
     **/
    public void populateProviderName(List<GoodsInfoVO> goodsInfoVOList) {
        if (CollectionUtils.isEmpty(goodsInfoVOList)){
            return;
        }
        List<Long> providerIds = goodsInfoVOList.stream().map(GoodsInfoVO::getProviderId).collect(Collectors.toList());
        StoreInfoQueryPageRequest request = StoreInfoQueryPageRequest.builder().idList(providerIds).build();
        List<StoreVO> storeVOS = esStoreInformationQueryProvider.queryStoreByStoreIds(request).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeVOS)){
            return;
        }
        Map<Long, StoreVO> storeVOMap = storeVOS.stream().collect(Collectors.toMap(StoreVO::getStoreId, storeVO -> storeVO));

        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList){
            Long providerId = goodsInfoVO.getProviderId();
            StoreVO storeVO = storeVOMap.get(providerId);
            if (Objects.isNull(storeVO)){
                goodsInfoVO.setProviderName("-");
                continue;
            }
            goodsInfoVO.setProviderName(storeVO.getSupplierName());
        }
    }
}
