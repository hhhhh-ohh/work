package com.wanmi.sbc.order.appointmentrecord.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AppointmentQueryRequest extends BaseQueryRequest {


    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private String buyerId;

    /**
     * 抢购开始时间到达
     */
    @Schema(description = "抢购开始时间到达")
    private LocalDateTime snapUpStartTimeBegin;

    /**
     * 是否只查询实物商品
     */
    @Schema(description = "是否只查询实物商品")
    private Boolean queryRealGoods;

    /**
     * 封装公共条件
     *
     * @return
     */
    private List<Criteria> getCommonCriteria() {
        List<Criteria> criterias = new ArrayList<>();
        if (StringUtils.isNotBlank(buyerId)) {
            criterias.add(Criteria.where("buyerId").is(this.buyerId));
        }
        if (Objects.nonNull(snapUpStartTimeBegin)) {
            criterias.add(Criteria.where("appointmentSaleInfo.snapUpStartTime").lte(snapUpStartTimeBegin));
            criterias.add(Criteria.where("appointmentSaleInfo.snapUpEndTime").gt(snapUpStartTimeBegin));
        }

        if(Boolean.TRUE.equals(queryRealGoods)){
            //只查询实物商品（兼容历史数据，历史数据如果有虚拟商品，则页面做禁止跳转）
            criterias.add(new Criteria().orOperator(
                    Criteria.where("appointmentSaleGoodsInfo.goodsType").is(GoodsType.REAL_GOODS.toValue()),
                    Criteria.where("appointmentSaleGoodsInfo.goodsType").exists(Boolean.FALSE))
            );
        }

        return criterias;
    }

    /**
     * 公共条件
     *
     * @return
     */
    public Criteria getWhereCriteria() {
        List<Criteria> criteriaList = this.getCommonCriteria();
        if (CollectionUtils.isEmpty(criteriaList)) {
            return new Criteria();
        }
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
    }


    @Override
    public String getSortColumn() {
        return "createTime";
    }

    @Override
    public String getSortRole() {
        return "desc";
    }

    @Override
    public String getSortType() {
        return "Date";
    }
}
