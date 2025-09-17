package com.wanmi.sbc.bookingsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleIsInProgressRequest;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleIsInProgressResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;


@Tag(description= "预售webAPI", name = "BookingSaleBaseController")
@RestController
@Validated
@RequestMapping(value = "/bookingsale")
public class BookingSaleBaseController {

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * @param goodsInfoId
     * @Description: 商品是否正在预售活动中
     */
    @Operation(summary = "商品是否正在预售活动中")
    @GetMapping("/{goodsInfoId}/isInProgress")
    public BaseResponse<BookingSaleIsInProgressResponse> isInProgress(@PathVariable String goodsInfoId) {

        String customerId = commonUtil.getOperatorId();
        BookingSaleIsInProgressResponse response = new BookingSaleIsInProgressResponse();
        if (StringUtils.isNotBlank(customerId)) {
            BookingSaleIsInProgressRequest request = BookingSaleIsInProgressRequest.builder().goodsInfoId(goodsInfoId).build();
            request.setUserId(customerId);
            return bookingSaleQueryProvider.isInProgress(request);
        }
        return BaseResponse.success(response);
    }

}
