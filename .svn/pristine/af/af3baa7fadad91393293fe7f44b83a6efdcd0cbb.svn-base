package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;


import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 物流公司设置服务
 * Created by CHENLI on 2017/6/20.
 */
@Tag(name = "ExpressCompanyController", description = "物流公司设置 API")
@RestController
@Validated
public class ExpressCompanyController {

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

//    /**
//     * 查询所有列表
//     *
//     * @return
//     */
//    @Operation(summary = "查询所有列表")
//    @RequestMapping(value = "/expressCompanyList", method = RequestMethod.GET)
//    public BaseResponse<ExpressCompanyRopResponse> queryExpressCompanyList() {
//
//        CompositeResponse<ExpressCompanyRopResponse> response
//                = sdkClient.buildClientRequest().get(ExpressCompanyRopResponse.class, "expressCompany.list", "1.0.0");
//        return BaseResponse.success( response.getSuccessResponse());
//
//    }

//    /**
//     * 查询常用的物流公司
//     *
//     * @return
//     */
//    @Operation(summary = "查询常用的物流公司")
//    @RequestMapping(value = "/expressCompanyCheckedList", method = RequestMethod.GET)
//    public BaseResponse<List<ExpressCompany>> findCheckedExpressCompany() {
//        CompositeResponse<List> response = sdkClient.buildClientRequest().get(List.class, "expressCompany.listChecked", "1.0.0");
//        return BaseResponse.success(response.getSuccessResponse());
//
//    }

    /**
     * 根据ID查询物流公司信息
     * @param expressCompanyId
     * @return
     */
    @Operation(summary = "根据ID查询物流公司信息")
    @Parameter(
            name = "expressCompanyId", description = "物流公司Id", required = true)
    @RequestMapping(value = "/expressCompany/{expressCompanyId}", method = RequestMethod.GET)
    public BaseResponse<ExpressCompanyVO> findOne(@PathVariable Long expressCompanyId) {

//        ExpsComQueryRopRequest queryRopRequest = new ExpsComQueryRopRequest();
//        queryRopRequest.setExpressCompanyId( expressCompanyId);
//        CompositeResponse<com.wanmi.open.sdk.request.bean.ExpressCompany> response
//                = sdkClient.buildClientRequest().post( queryRopRequest,ExpressCompany.class, "expressCompany.detail", "1.0.0");
//        if ( !response.isSuccessful()) {
//            throw new SbcRuntimeException(ResultCode.FAILED);
//        }
        ExpressCompanyByIdRequest request = new ExpressCompanyByIdRequest();
        request.setExpressCompanyId(expressCompanyId);
        ExpressCompanyVO expressCompanyVO = expressCompanyQueryProvider.getById(request).getContext().getExpressCompanyVO();
        return BaseResponse.success(expressCompanyVO);
    }

}
