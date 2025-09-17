package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordQueryProvider;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordByIdRequest;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordPageRequest;
import com.wanmi.sbc.goods.api.response.price.adjustment.PriceAdjustmentRecordByIdResponse;
import com.wanmi.sbc.goods.api.response.price.adjustment.PriceAdjustmentRecordPageResponse;
import com.wanmi.sbc.goods.bean.vo.PriceAdjustmentRecordVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "调价记录表管理API", description =  "PriceAdjustmentRecordController")
@RestController
@Validated
@RequestMapping(value = "/price-adjustment-record")
public class PriceAdjustmentRecordController {

    @Autowired
    private PriceAdjustmentRecordQueryProvider priceAdjustmentRecordQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Operation(summary = "分页查询调价记录表")
    @PostMapping("/page")
    public BaseResponse<PriceAdjustmentRecordPageResponse> getPage(@RequestBody @Valid PriceAdjustmentRecordPageRequest pageReq) {
        pageReq.putSort("createTime", "desc");
        // 查询已确认的记录
        pageReq.setConfirmFlag(DefaultFlag.YES.toValue());
        // 查询当前登录商家或供应商的记录
        pageReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<PriceAdjustmentRecordPageResponse> response = priceAdjustmentRecordQueryProvider.page(pageReq);
        //实时获取员工名称
        List<String> employeeIds = response.getContext().getPriceAdjustmentRecordVOPage().getContent()
                .stream()
                .map(PriceAdjustmentRecordVO::getCreatePerson)
                .distinct()
                .collect(Collectors.toList());
        List<EmployeeListByIdsVO> employeeList = employeeQueryProvider
                .listByIds(EmployeeListByIdsRequest.builder().employeeIds(employeeIds).build())
                .getContext()
                .getEmployeeList();
        if (CollectionUtils.isNotEmpty(employeeList)){
            Map<String, String> map = employeeList
                    .stream()
                    .collect(Collectors.toMap(EmployeeListByIdsVO::getEmployeeId, EmployeeListByIdsVO::getEmployeeName));
            response.getContext()
                    .getPriceAdjustmentRecordVOPage()
                    .getContent()
                    .forEach(v->v.setCreatorName(Objects.nonNull(map.get(v.getCreatePerson()))?map.get(v.getCreatePerson()):v.getCreatorName()));
        }
        return response;
    }

    @Operation(summary = "根据id查询调价记录表")
    @GetMapping("/{id}")
    public BaseResponse<PriceAdjustmentRecordByIdResponse> getById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PriceAdjustmentRecordByIdRequest idReq = new PriceAdjustmentRecordByIdRequest();
        idReq.setId(id);
        idReq.setStoreId(commonUtil.getStoreId());
        return priceAdjustmentRecordQueryProvider.getById(idReq);
    }

}
