package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailQueryProvider;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail.PriceAdjustmentRecordDetailPageRequest;
import com.wanmi.sbc.goods.api.response.price.adjustment.PriceAdjustmentRecordDetailPageByNoResponse;
import com.wanmi.sbc.goods.api.response.price.adjustment.PriceAdjustmentRecordDetailPageResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Tag(name =  "调价单详情表管理API", description =  "PriceAdjustmentRecordDetailController")
@RestController
@Validated
@RequestMapping(value = "/price-adjustment-record-detail")
public class PriceAdjustmentRecordDetailController {

    @Autowired
    private PriceAdjustmentRecordDetailQueryProvider priceAdjustmentRecordDetailQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Operation(summary = "分页查询调价单详情表")
    @PostMapping("/page")
    public BaseResponse<PriceAdjustmentRecordDetailPageByNoResponse> getPage(@RequestBody @Valid PriceAdjustmentRecordDetailPageRequest pageReq) {
        pageReq.setBaseStoreId(commonUtil.getStoreId());
        BaseResponse<PriceAdjustmentRecordDetailPageByNoResponse> response = priceAdjustmentRecordDetailQueryProvider.page(pageReq);
        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            if (!Objects.equals(commonUtil.getStoreId(),response.getContext().getPriceAdjustmentRecordVO().getStoreId())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        //实时获取员工名称
        String employeeId = response.getContext().getPriceAdjustmentRecordVO().getCreatePerson();
        List<EmployeeListByIdsVO> employeeList = employeeQueryProvider
                .listByIds(EmployeeListByIdsRequest.builder().employeeIds(Collections.singletonList(employeeId)).build())
                .getContext()
                .getEmployeeList();

        if (CollectionUtils.isNotEmpty(employeeList) && StringUtils.isNotBlank(employeeList.get(0).getEmployeeName())){
            response
                    .getContext()
                    .getPriceAdjustmentRecordVO()
                    .setCreatorName(employeeList.get(0).getEmployeeName());
        }
        return response;
    }

    @Operation(summary = "确认调价分页查询调价单详情表")
    @PostMapping("/page/confirm")
    public BaseResponse<PriceAdjustmentRecordDetailPageResponse> pageForConfirm(@RequestBody @Valid PriceAdjustmentRecordDetailPageRequest pageReq) {
        pageReq.setBaseStoreId(commonUtil.getStoreId());
        return priceAdjustmentRecordDetailQueryProvider.pageForConfirm(pageReq);
    }

}
