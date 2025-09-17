package com.wanmi.sbc.account.finance.record.model.request;

import com.wanmi.sbc.account.api.request.finance.record.BasePageRequest;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.finance.record.model.entity.LakalaSettlement;
import com.wanmi.sbc.account.finance.record.model.entity.Settlement;
import com.wanmi.sbc.common.enums.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
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
@Data
public class SettlementQueryRequest extends BasePageRequest {

    private static Logger logger = LoggerFactory.getLogger(SettlementQueryRequest.class);

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 店铺Id
     */
    private Long storeId;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 商家类型 0供应商 1商家
     */
    private StoreType storeType;


    /**
     * 批量店铺ID
     */
    private List<Long> storeListId;

    private String token;

    /**
     * 批量结算单号ID
     */
    private List<Long> idList;

    private List<String> uuidList;

    public Specification<Settlement> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(idList)) {
                predicates.add(root.get("settleId").in(idList));
            }

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
            //商家类型
            if(storeType !=null){
                predicates.add(cbuild.equal(root.get("storeType"),storeType));
            }

            //结算状态
            if (settleStatus != null) {
                predicates.add(cbuild.equal(root.get("settleStatus"), settleStatus));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

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
                predicates.add(cbuild.equal(root.get("settleStatus"), settleStatus));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
