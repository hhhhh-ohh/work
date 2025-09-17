package com.wanmi.sbc.employee;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeDisableOrEnableByCompanyIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeDisableOrEnableByCompanyIdResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.vo.EmployeeDisableOrEnableByCompanyIdVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeImportRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoStateModifyRequest;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSwitchRequest;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;

/***
 * 平台员工Service
 * 防腐层
 * @className EmployeeService
 * @author zhengyang
 * @date 2021/7/29 14:03
 **/
@Slf4j
@Service
public class EmployeeService {

    @Resource
    private EmployeeProvider employeeProvider;
    @Resource
    private StoreQueryProvider storeQueryProvider;
    @Resource
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;
    @Resource
    private PointsGoodsSaveProvider pointsGoodsSaveProvider;
    @Resource
    private CompanyInfoQueryProvider companyInfoQueryProvider;
    @Resource
    private OperateLogMQUtil operateLogMqUtil;
    @Resource
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    /***
     * 根据公司ID查询公司信息
     * @param companyInfoId 公司ID
     * @return              公司信息
     */
    public CompanyInfoByIdResponse getCompanyInfoById(Long companyInfoId) {
        if (Objects.isNull(companyInfoId)) {
            return null;
        }
        CompanyInfoByIdRequest companyInfoByIdRequest = new CompanyInfoByIdRequest();
        companyInfoByIdRequest.setCompanyInfoId(companyInfoId);
        return BaseResUtils.getContextFromRes(companyInfoQueryProvider.getCompanyInfoById(companyInfoByIdRequest));
    }

    /**
     * 启用/禁用 账号
     * @param request                     启用/禁用请求
     * @return                            公司业务员列表
     */
    @GlobalTransactional
    public BaseResponse<List<EmployeeDisableOrEnableByCompanyIdVO>> switchEmp(
            EmployeeDisableOrEnableByCompanyIdRequest request) {
        return switchEmp(request, getCompanyInfoById(request.getCompanyInfoId()));
    }

    /**
     * 启用/禁用 账号
     * @param request                     启用/禁用请求
     * @param companyInfoByIdResponse     公司信息响应
     * @return                            公司业务员列表
     */
    @GlobalTransactional
    public BaseResponse<List<EmployeeDisableOrEnableByCompanyIdVO>> switchEmp(
            EmployeeDisableOrEnableByCompanyIdRequest request,
            CompanyInfoByIdResponse companyInfoByIdResponse) {
        EmployeeDisableOrEnableByCompanyIdResponse response =
                employeeProvider.disableOrEnableByCompanyId(request).getContext();
        // 停用该店铺关联的积分商品
        StoreVO storeVO = storeQueryProvider.getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder()
                .companyInfoId(request.getCompanyInfoId())
                .build()).getContext().getStoreVO();
        List<PointsGoodsVO> pointsGoodsVOList = pointsGoodsQueryProvider.getByStoreId(PointsGoodsByStoreIdRequest.builder()
                .storeId(storeVO.getStoreId())
                .build()).getContext().getPointsGoodsVOList();
        pointsGoodsVOList.forEach(pointsGoodsVO -> pointsGoodsSaveProvider.modifyStatus(PointsGoodsSwitchRequest.builder()
                .pointsGoodsId(pointsGoodsVO.getPointsGoodsId())
                .status(EnableStatus.DISABLE)
                .build()));

        // 操作日志记录
        if (request.getAccountState() == AccountState.ENABLE) {
            operateLogMqUtil.convertAndSend("商家", "启用商家",
                    "启用商家：商家编号" + companyInfoByIdResponse.getCompanyCode());
        } else {
            operateLogMqUtil.convertAndSend("商家", "禁用商家",
                    "禁用商家：商家编号" + companyInfoByIdResponse.getCompanyCode());
        }
        // 更新es店铺信息
        esStoreInformationProvider.modifyStoreState(StoreInfoStateModifyRequest
                .builder()
                .storeId(storeVO.getStoreId())
                .accountState(request.getAccountState().toValue())
                .accountDisableReason(request.getAccountDisableReason()).build());
        //增加es_employee信息
        List<EsEmployeeSaveRequest> employeeSaveRequests =
                KsBeanUtil.convertList(response.getEmployeeList(), EsEmployeeSaveRequest.class);
        EsEmployeeImportRequest esEmployeeImportRequest = EsEmployeeImportRequest.builder()
                .employeeList(employeeSaveRequests)
                .build();
        esEmployeeProvider.importEmployee(esEmployeeImportRequest);
        return BaseResponse.success(response.getEmployeeList());
    }
}
