package com.wanmi.sbc.account.finance.record.model.request;

import com.wanmi.sbc.account.api.request.finance.record.BasePageRequest;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.finance.record.model.entity.LakalaSettlement;
import com.wanmi.sbc.account.finance.record.model.entity.Settlement;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算查询实体
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class LakalaSettlementQueryRequest extends BasePageRequest {

    private static Logger logger = LoggerFactory.getLogger(LakalaSettlementQueryRequest.class);

    @Schema(description = "开始时间")
    private String startTime;

    /** 结束时间 */
    @Schema(description = "结束时间")
    private String endTime;

    /** 店铺Id */
    @Schema(description = "店铺Id")
    private Long storeId;

    /** 结算状态 {@link SettleStatus} */
    @Schema(description = "结算状态")
    private LakalaLedgerStatus settleStatus;

    /** 店铺名称 */
    @Schema(description = "店铺名称")
    private String storeName;

    /** 批量店铺ID */
    @Schema(description = "批量店铺ID")
    private List<Long> storeListId;

    /** 商家类型 0供应商 1商家 */
    @Schema(description = "商家类型")
    private StoreType storeType;

    /** 结算单号 */
    @Schema(description = "结算单号")
    private String settlementCode;


    public Specification<LakalaSettlement> getLakalaWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(startTime)) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), startTime));
            }
            if (StringUtils.isNotEmpty(endTime)) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), endTime));
            }
            //店铺ID
            if (storeId != null) {
                predicates.add(cbuild.equal(root.get("storeId"), storeId));
            }
            //批量店铺ID
            if (storeListId != null) {
                predicates.add(root.get("storeId").in(storeListId));
            }

            //结算状态
            if (settleStatus != null) {
                predicates.add(cbuild.equal(root.get("lakalaLedgerStatus"), settleStatus));
            }

            //结算单号
            if (StringUtils.isNotBlank(settlementCode) && !StringUtils.equals(settlementCode.trim(),"js")) {
                predicates.add(cbuild.equal(root.get("settleId"), Long.valueOf(settlementCode.replaceAll("js", ""))));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
