package com.wanmi.sbc.customer.quicklogin.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.constant.CustomerLabel;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsListRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerIdsListResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailWithImgVO;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.repository.CustomerDetailRepository;
import com.wanmi.sbc.customer.quicklogin.model.root.ThirdLoginRelation;
import com.wanmi.sbc.customer.quicklogin.model.root.WeChatQuickLogin;
import com.wanmi.sbc.customer.quicklogin.repository.ThirdLoginRelationRepository;

import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: songhanlin
 * @Date: Created In 10:40 AM 2018/8/8
 * @Description: 第三方登录关系Service
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class ThirdLoginRelationService {

    @Autowired
    private ThirdLoginRelationRepository thirdLoginRelationRepository;

    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    private WeChatQuickLoginService weChatQuickLoginService;

    public ThirdLoginRelation findAllByCustomerIdAndThirdTypeAndDelFlagAndStoreId(String customerId, ThirdLoginType
            thirdLoginType,DeleteFlag delFlag, Long storeId) {
        return thirdLoginRelationRepository.findByCustomerIdAndThirdTypeAndDelFlagAndStoreId(customerId,
                thirdLoginType,delFlag,storeId).orElse(null);
    }

    /**
     * 查询最新绑定的第三方登录信息
     *
     * @param customerId     用户Id
     * @param thirdLoginType 第三方登录方式
     * @return
     */
    public ThirdLoginRelation findFirstByCustomerIdAndThirdType(String customerId, ThirdLoginType thirdLoginType) {
        return thirdLoginRelationRepository.findTop1ByCustomerIdAndThirdLoginTypeAndDelFlagOrderByBindingTimeDesc(customerId, thirdLoginType,DeleteFlag.NO).orElse(null);
    }

    /**
     * 根据 关联Id&第三方登录方式 查询所有第三方登录关系
     *
     * @param thirdLoginUid  第三方关联Id
     * @param thirdLoginType 第三方登录方式
     * @return
     */
    public ThirdLoginRelation findByUnionIdAndThirdTypeAndStoreId(String thirdLoginUid, ThirdLoginType
            thirdLoginType,Long storeId) {
        return thirdLoginRelationRepository.findTop1ByThirdLoginUidAndThirdLoginTypeAndStoreId(thirdLoginUid, thirdLoginType,storeId).orElse(null);
    }

    /**
     * 根据 关联Id&第三方登录方式 查询所有第三方登录关系
     *
     * @param thirdLoginUid  第三方关联Id
     * @param thirdLoginType 第三方登录方式
     * @return
     */
    public ThirdLoginRelation findByUnionIdAndThirdTypeAndDelFlagAndStoreId(String thirdLoginUid, ThirdLoginType
            thirdLoginType,DeleteFlag deleteFlag,Long storeId) {
        return thirdLoginRelationRepository.findTop1ByThirdLoginUidAndThirdLoginTypeAndDelFlagAndStoreId(thirdLoginUid, thirdLoginType,deleteFlag,storeId).orElse(null);
    }

    /**
     * 新增第三方登录关系
     *
     * @param thirdLoginRelation
     */
    @Transactional
    public void save(ThirdLoginRelation thirdLoginRelation) {
        // 检查账号是否已经绑定
        ThirdLoginRelation phone = thirdLoginRelationRepository.findByCustomerIdAndThirdTypeAndDelFlagAndStoreId(thirdLoginRelation.getCustomerId()
                ,thirdLoginRelation.getThirdLoginType()
                ,DeleteFlag.NO
                ,thirdLoginRelation.getStoreId()).orElse(null);
        if (Objects.nonNull(phone)) {
            // 已经绑定
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010048);
        }
        // 检查第三方登录方式是否已经被使用
       ThirdLoginRelation wx = thirdLoginRelationRepository.findTop1ByThirdLoginUidAndThirdLoginTypeAndDelFlagAndStoreId(thirdLoginRelation.getThirdLoginUid()
               ,thirdLoginRelation.getThirdLoginType()
               ,DeleteFlag.NO
               ,thirdLoginRelation.getStoreId()).orElse(null);
        if (Objects.nonNull(wx)) {
            // 已经绑定
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010049);
        }
        //解绑的账号可以进行更新
        List<ThirdLoginRelation> delList = thirdLoginRelationRepository.findAllByCustomerIdAndThirdTypeAndDelFlag
                (thirdLoginRelation.getCustomerId(), thirdLoginRelation.getThirdLoginType(),DeleteFlag.YES);
        thirdLoginRelation.setBindingTime(LocalDateTime.now());
        if(Objects.nonNull(delList)&&delList.size()>0){
            thirdLoginRelation.setDelFlag(DeleteFlag.NO);
            thirdLoginRelationRepository.updateInfo(thirdLoginRelation);
        }else{
            thirdLoginRelationRepository.save(thirdLoginRelation);
        }

        WeChatQuickLogin weChatQuickLogin = new WeChatQuickLogin();
        weChatQuickLogin.setDelFlag(DeleteFlag.NO);
        weChatQuickLogin.setOpenId(thirdLoginRelation.getThirdLoginOpenId());
        weChatQuickLogin.setUnionId(thirdLoginRelation.getThirdLoginUid());
        weChatQuickLoginService.save(weChatQuickLogin);
    }

    /**
     * 根据用户Id删除关系(解绑)
     *
     * @param customerId 用户Id
     */
    @Transactional
    public ThirdLoginRelation deleteByCustomerIdAndStoreId(String customerId, ThirdLoginType thirdLoginStringType,Long storeId) {
        ThirdLoginRelation thirdLoginRelation = thirdLoginRelationRepository.findByCustomerIdAndThirdTypeAndDelFlagAndStoreId(customerId,
                thirdLoginStringType,DeleteFlag.NO,storeId).orElse(null);
        if (Objects.isNull(thirdLoginRelation)) {
            // 已解绑/未绑定
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010044);
        }

        LocalDateTime now = LocalDateTime.now();

        // 解绑时间距离当前时间不到30天, 不允许解绑
        if (Objects.nonNull(thirdLoginRelation.getUnbindingTime()) && thirdLoginRelation.getUnbindingTime().isAfter(now.minusDays(Constants.NUM_30))) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010045);
        }

        thirdLoginRelation.setUnbindingTime(now);
        thirdLoginRelation.setDelFlag(DeleteFlag.YES);
        thirdLoginRelationRepository.save(thirdLoginRelation);
        return thirdLoginRelation;
    }


    /**
     *
     * 批量获取会员信息
     * 会员名称+会员头像
     */
    public BaseResponse<CustomerIdsListResponse> listWithImgByCustomerIds(@RequestBody @Valid CustomerIdsListRequest request) {

        // 批量查询客户详情信息
        List<CustomerDetail> customerDetailList =
                customerDetailRepository.findAnyByCustomerIds(new ArrayList<>(request.getCustomerIds()));
        // 批量查询客户第三方授权信息
        List<ThirdLoginRelation> relationList = thirdLoginRelationRepository.findAnyByCustomerIds(new ArrayList<>(request.getCustomerIds()));

        if (customerDetailList == null) {
            return BaseResponse.success(new CustomerIdsListResponse());
        }
        List<CustomerDetailWithImgVO> customerVOList = new ArrayList<>();
        customerDetailList.forEach(
                c->{
                    Optional<ThirdLoginRelation> relationOptional  =relationList.stream().filter(r->r.getCustomerId().equals(c.getCustomerId())).findFirst();
                    CustomerDetailWithImgVO vo = new  CustomerDetailWithImgVO();
                    vo.setCustomerId(c.getCustomerId());
                    vo.setCustomerName(c.getCustomerName());

                    String headImg = Objects.nonNull(c.getCustomer())?c.getCustomer().getHeadImg():"";

                    if(StringUtils.isBlank(headImg) &&
                            relationOptional.isPresent()){
                        vo.setHeadimgurl(relationOptional.get().getHeadimgurl());
                    }else{
                        vo.setHeadimgurl(headImg);
                    }

                    //为企业购会员
                    if (EnterpriseCheckState.CHECKED == c.getCustomer().getEnterpriseCheckState()){
                        vo.getCustomerLabelList().add(CustomerLabel.EnterpriseCustomer);
                    }
                    customerVOList.add(vo);
                }
        );
        CustomerIdsListResponse response = new CustomerIdsListResponse();
        response.setCustomerVOList(customerVOList);
        return BaseResponse.success(response);
    }

    public  void  updateInfo(ThirdLoginRelation thirdLoginRelation){
        thirdLoginRelationRepository.updateInfo(thirdLoginRelation);
    }
}
