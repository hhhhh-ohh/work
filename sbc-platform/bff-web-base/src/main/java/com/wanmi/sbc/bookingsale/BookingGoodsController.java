package com.wanmi.sbc.bookingsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.response.bookingsalegoods.BookingGoodsResponse;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGoodsForXsiteRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingLevelType;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * S2B的预售商品服务
 */
@RestController
@Validated
@RequestMapping("/booking/goods")
@Tag(description= "S2B的预售商品服务", name = "BookingGoodsController")
public class BookingGoodsController {


    @Autowired
    private BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    /**
     * 分页查询预售活动
     *
     * @param request 商品 {@link BookingGoodsInfoSimplePageRequest}
     * @return 预售活动分页
     */
    @Operation(summary = "分页查询预售活动")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<BookingGoodsResponse> page(@RequestBody @Valid BookingGoodsInfoSimplePageRequest request) {
        request.setHavSpecTextFlag(Boolean.TRUE);
        List<BookingSaleGoodsVO> bookingSaleGoodsVOS = bookingSaleGoodsQueryProvider.pageBookingGoodsInfo(request).getContext().getPage().getContent();
        // 按营销优先级过滤
        if(CollectionUtils.isNotEmpty(bookingSaleGoodsVOS)) {
            List<String> ids = bookingSaleGoodsVOS.stream().map(vo -> vo.getGoodsInfoId()).collect(Collectors.toList());
            MarketingGoodsForXsiteRequest marketingRequest = MarketingGoodsForXsiteRequest.builder()
                    .goodsInfoIds(ids)
                    .marketingLevelType(MarketingLevelType.APPOINTMENT_OR_BOOKING).build();
            // 获取满足当前优先级的商品id
            List<String> goodsInfoIds = marketingQueryProvider.queryForXsite(marketingRequest).getContext().getGoodsInfoIds();
            bookingSaleGoodsVOS =bookingSaleGoodsVOS.stream().filter(vo -> goodsInfoIds.contains(vo.getGoodsInfoId())).collect(Collectors.toList());

        }
        BookingGoodsResponse response = BookingGoodsResponse.builder().page(new MicroServicePage<>(bookingSaleGoodsVOS)).build();
        return BaseResponse.success(response);
    }

}
