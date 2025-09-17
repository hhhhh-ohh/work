package com.wanmi.sbc.marketing.api.response.bookingsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.AppointmentSaleSimplifyVO;
import com.wanmi.sbc.marketing.bean.vo.BookingSaleSimplifyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleInProgressAllGoodsInfoIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1418792063815947238L;
    
    /**
     * 预约商品列表
     */
    private List<AppointmentSaleSimplifyVO> appointmentSaleSimplifyVOList;

    /**
     * 预售商品列表
     */
    private List<BookingSaleSimplifyVO> bookingSaleSimplifyVOList;
}