package com.wanmi.sbc.account.finance.record.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.request.finance.record.GetBySettlementUuidRequest;
import com.wanmi.sbc.account.api.request.finance.record.LakalaSettlementBatchUpdateStatusRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementToExcelRequest;
import com.wanmi.sbc.account.api.response.finance.record.SettlementToExcelResponse;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.account.finance.record.model.entity.LakalaSettlement;
import com.wanmi.sbc.account.finance.record.model.entity.Settlement;
import com.wanmi.sbc.account.finance.record.model.request.LakalaSettlementQueryRequest;
import com.wanmi.sbc.account.finance.record.model.request.SettlementQueryRequest;
import com.wanmi.sbc.account.finance.record.model.response.LakalaSettlementView;
import com.wanmi.sbc.account.finance.record.model.response.SettlementView;
import com.wanmi.sbc.account.finance.record.model.response.TotalSettlement;
import com.wanmi.sbc.account.finance.record.repository.LakalaSettlementRepository;
import com.wanmi.sbc.account.finance.record.repository.SettlementRepository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.request.store.StoreNameListByStoreIdsResquest;
import com.wanmi.sbc.customer.bean.vo.StoreNameVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 结算明细
 * Created by hht on 2017/12/7.
 */
@Service
@Slf4j
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private LakalaSettlementRepository lakalaSettlementRepository;


    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 保存结算单
     *
     * @param settlement 结算单对象
     * @return 结算单
     */
    @Transactional
    public Settlement saveSettlement(Settlement settlement) {
        return settlementRepository.save(settlement);
    }

    @Transactional
    public LakalaSettlement saveLakalaSettlement(LakalaSettlement settlement) {
        return lakalaSettlementRepository.save(settlement);
    }

    /**
     * 删除结算和结算明细
     *
     * @param storeId   店铺Id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Transactional
    public void deleteSettlement(Long storeId, String startTime, String endTime) {
        settlementRepository.deleteByStoreIdAndStartTimeAndEndTime(storeId, startTime, endTime);
    }

    @Transactional
    public LakalaSettlement deleteLakalaSettlement(Long storeId, String startTime, String endTime,Long supplierStoreId) {
        LakalaSettlement lakalaSettlement = null;

        if(supplierStoreId==null){
            lakalaSettlement =
                    lakalaSettlementRepository.findByStoreIdAndStartTimeAndEndTime(
                            storeId, startTime, endTime);
        }else{
            lakalaSettlement = lakalaSettlementRepository.findByStoreIdAndStartTimeAndEndTimeAndSupplierStoreId(storeId,startTime,endTime,supplierStoreId);
        }
        if (Objects.nonNull(lakalaSettlement)){
            lakalaSettlementRepository.delete(lakalaSettlement);
        }
        return lakalaSettlement;
    }

    /**
     * 分页查询结算单
     *
     * @param request 查询参数
     * @return 结算单分页对象
     */
    public Page<SettlementView> querySettlementPage(SettlementQueryRequest request) {
        request.setSortColumn("createTime");
        request.setSortType("DESC");
        //如果storeId和storeName都不为空
        if (request.getStoreId() != null && StringUtils.isNotBlank(request.getStoreName())) {
            //不相等只带入storeName，如果相等只查storeId
            if (request.getStoreId().toString().equals(request.getStoreName())) {
                request.setStoreName(null);
            } else {
                request.setStoreId(null);
            }
        }
        //按照店铺名称查询-  其实是要查供应商名称
        if (StringUtils.isNotBlank(request.getStoreName())) {
            ListStoreRequest listStoreRequest = ListStoreRequest.builder().storeName(request.getStoreName())
                    .storeType(request.getStoreType())
                    .delFlag(DeleteFlag.NO).build();
            List<StoreVO> storeList = storeQueryProvider.listStore(listStoreRequest).getContext().getStoreVOList();
            if (storeList != null && !storeList.isEmpty()) {
                request.setStoreListId(storeList.stream().mapToLong(StoreVO::getStoreId).boxed().collect(Collectors
                        .toList()));
            } else {
                return new PageImpl<>(new ArrayList<>());
            }
        }
        Page<Settlement> page = settlementRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
        Page<SettlementView> settlementViewPage = page.map(Settlement -> {
            SettlementView settlementViewBean = new SettlementView();
            BeanUtils.copyProperties(Settlement, settlementViewBean);
            return settlementViewBean;
        });

        if (CollectionUtils.isNotEmpty(page.getContent())) {
            List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(page
                    .getContent().stream().mapToLong
                            (Settlement::getStoreId).boxed().collect(Collectors.toList()))).getContext().getStoreVOList();
            settlementViewPage.getContent().stream().forEach(settlementView -> {
                settlementView.setSettlementCode(String.format("S%07d", settlementView.getSettleId()));
                Optional<StoreVO> optional = storeList.stream().filter(store -> store.getStoreId().longValue() ==
                        settlementView.getStoreId()).findFirst();
                settlementView.setStoreName(optional.map(StoreVO::getStoreName).orElse(null));
            });
        }
        return settlementViewPage;
    }

    public Page<LakalaSettlementView> queryLakalaSettlementPage(
            LakalaSettlementQueryRequest request) {
        // 如果storeId和storeName都不为空
        if (request.getStoreId() != null && StringUtils.isNotBlank(request.getStoreName())) {
            // 不相等只带入storeName，如果相等只查storeId
            if (request.getStoreId().toString().equals(request.getStoreName())) {
                request.setStoreName(null);
            } else {
                request.setStoreId(null);
            }
        }
        // 按照店铺名称查询-  其实是要查供应商名称
        if (StringUtils.isNotBlank(request.getStoreName())) {
            //  供应商 用的是storeName   商家用的是supplierName
            ListStoreRequest listStoreRequest =
                    ListStoreRequest.builder()
                            .storeName(request.getStoreName())
                            .storeType(request.getStoreType())
                            .delFlag(DeleteFlag.NO)
                            .build();
            if (StoreType.SUPPLIER.equals(request.getStoreType())) {
                listStoreRequest.setStoreName(StringUtils.EMPTY);
                listStoreRequest.setSupplierName(request.getStoreName());
            }
            List<StoreVO> storeList =
                    storeQueryProvider.listStore(listStoreRequest).getContext().getStoreVOList();
            if (storeList != null && !storeList.isEmpty()) {
                request.setStoreListId(
                        storeList.stream()
                                .mapToLong(StoreVO::getStoreId)
                                .boxed()
                                .collect(Collectors.toList()));
            } else {
                return new PageImpl<>(new ArrayList<>());
            }
        }
        Page<LakalaSettlement> page =
                lakalaSettlementRepository.findAll(
                        request.getLakalaWhereCriteria(), request.getPageRequest());
        Page<LakalaSettlementView> settlementViewPage =
                page.map(
                        settlement -> {
                            LakalaSettlementView settlementViewBean = new LakalaSettlementView();
                            BeanUtils.copyProperties(settlement, settlementViewBean);
                            return settlementViewBean;
                        });

        if (CollectionUtils.isNotEmpty(page.getContent())) {
            List<StoreVO> storeList =
                    storeQueryProvider
                            .listNoDeleteStoreByIds(
                                    new ListNoDeleteStoreByIdsRequest(
                                            page.getContent().stream()
                                                    .mapToLong(LakalaSettlement::getStoreId)
                                                    .boxed()
                                                    .collect(Collectors.toList())))
                            .getContext()
                            .getStoreVOList();
            settlementViewPage
                    .getContent()
                    .forEach(
                            settlementView -> {
                                settlementView.setSettlementCode(
                                        String.format("S%07d", settlementView.getSettleId()));
                                Optional<StoreVO> optional =
                                        storeList.stream()
                                                .filter(
                                                        store ->
                                                                store.getStoreId().longValue()
                                                                        == settlementView
                                                                                .getStoreId())
                                                .findFirst();
                                settlementView.setStoreName(
                                        optional.map(StoreVO::getSupplierName).orElse(null));
                                settlementView.setCompanyCode(
                                        optional.map(
                                                        storeVO ->
                                                                storeVO.getCompanyInfo()
                                                                        .getCompanyCode())
                                                .orElse(null));
                            });
        }
        return settlementViewPage;
    }

    /**
     * 根据单条结算单获取信息并转换为结算单视图
     *
     * @param settlement 结算单
     * @return 结算单视图
     */
    public SettlementView getSettlementById(Settlement settlement) {
        SettlementView view = new SettlementView();
        BeanUtils.copyProperties(settlement, view);
        view.setSettlementCode(String.format("S%07d", settlement.getSettleId()));
        List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(
                Collections.singletonList(settlement.getStoreId()))).getContext().getStoreVOList();
        if (storeList != null && !storeList.isEmpty()) {
            view.setStoreName(storeList.get(0).getStoreName());
            if (storeList.get(0).getCompanyInfo() != null) {
                view.setCompanyCode(storeList.get(0).getCompanyInfo().getCompanyCode());
            }
        }
        return view;
    }

    public LakalaSettlementView getLakalaSettlementById(LakalaSettlement settlement) {
        LakalaSettlementView view = new LakalaSettlementView();
        BeanUtils.copyProperties(settlement, view);
        view.setSettlementCode(String.format("js%07d", settlement.getSettleId()));
        List<StoreVO> storeList = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(
                Collections.singletonList(settlement.getStoreId()))).getContext().getStoreVOList();
        if (storeList != null && !storeList.isEmpty()) {
            view.setStoreName(storeList.get(0).getSupplierName());
            if (storeList.get(0).getCompanyInfo() != null) {
                view.setCompanyCode(storeList.get(0).getCompanyInfo().getCompanyCode());
            }
        }
        return view;
    }


    /**
     * 修改结算单的状态
     *
     * @param settleIdList 批量结算单Id数据
     * @param settleStatus 结算单新状态
     */
    @Transactional
    public void updateSettleStatus(List<Long> settleIdList, SettleStatus settleStatus) {
        settlementRepository.updateSettleStatusBatch(settleStatus, settleIdList);
    }

    @Transactional
    public void updateLakalaSettleStatus(LakalaSettlementBatchUpdateStatusRequest request) {
        log.info("------updateLakalaSettleStatus:{}", request);
        request.getLakalaLedgerStatusToSettlementUuidMap().forEach((k ,v) -> {
            if (CollectionUtils.isNotEmpty(v)){
                settlementRepository.updateLakalaSettleStatusBatch(k, v);
            }
        });
    }

    /**
     * 根据结算单id查询结算单
     *
     * @param settleId 结算单id
     * @return 结算单
     */
    public Settlement querySettlementById(Long settleId) {
        return settlementRepository.findById(settleId).orElse(null);
    }

    public LakalaSettlement queryLakalaSettlementById(Long settleId) {
        return lakalaSettlementRepository.findById(settleId).orElse(null);
    }

    /**
     * 待结算统计
     *
     * @param request 结算单条件参数
     * @return 待结算统计量
     */
    public Long countByTodo(SettlementQueryRequest request) {
        request.setSettleStatus(SettleStatus.NOT_SETTLED);
        return settlementRepository.count(request.getWhereCriteria());
    }

    /**
     * 计算店铺总的结算资金、待结算资金
     *
     * @param storeId 店铺Id
     * @return 店铺统计结构
     */
    public List<TotalSettlement> queryToTalSettlement(Long storeId) {
        return settlementRepository.queryToTalSettlement(storeId);
    }

    /**
     * 得到财务结算报表导出数据
     *
     * @param queryRequest
     * @return
     */
    public SettlementToExcelResponse getExportData(SettlementToExcelRequest queryRequest) {
        SettlementQueryRequest request = new SettlementQueryRequest();
        KsBeanUtil.copyPropertiesThird(queryRequest, request);
        SettlementToExcelResponse settlementViewExcel = new SettlementToExcelResponse();
        //未结算数据查询
        request.setSettleStatus(SettleStatus.NOT_SETTLED);
        List<SettlementViewVO> notSettledSettlements = getResSettle(request);
        if (CollectionUtils.isNotEmpty(notSettledSettlements)) {
            settlementViewExcel.setNotSettledSettlements(notSettledSettlements);
        } else {
            settlementViewExcel.setNotSettledSettlements(Collections.emptyList());
        }
        //已结算数据查询
        request.setSettleStatus(SettleStatus.SETTLED);
        List<SettlementViewVO> settledSettlements = getResSettle(request);
        if (CollectionUtils.isNotEmpty(settledSettlements)) {
            settlementViewExcel.setSettledSettlements(settledSettlements);
        } else {
            settlementViewExcel.setSettledSettlements(Collections.emptyList());
        }
        //暂不处理数据查询
        request.setSettleStatus(SettleStatus.SETTLE_LATER);
        List<SettlementViewVO> settleLaterSettlements = getResSettle(request);
        if (CollectionUtils.isNotEmpty(settleLaterSettlements)) {
            settlementViewExcel.setSettleLaterSettlements(settleLaterSettlements);
        } else {
            settlementViewExcel.setSettleLaterSettlements(Collections.emptyList());
        }
        return settlementViewExcel;
    }

    /**
     * 根据结算单状态查询
     *
     * @param request
     * @return
     */
    private List<SettlementViewVO> getResSettle(SettlementQueryRequest request) {
        List<SettlementViewVO> settlementViewList;
        request = setRequest(request);
        if (request.getPageSize() == Constants.ZERO) {
            settlementViewList = Collections.emptyList();
        } else {
            settlementViewList = this.querySettlementPage(request).getContent().stream().map(view -> {
                SettlementViewVO vo = new SettlementViewVO();
                KsBeanUtil.copyPropertiesThird(view, vo);
                return vo;
            }).collect(Collectors.toList());
        }

        return settlementViewList;
    }

    /**
     * 财务结算报表设置request查询参数
     *
     * @param request
     * @return
     */
    private SettlementQueryRequest setRequest(SettlementQueryRequest request) {
        if (request.getStoreId() != null && StringUtils.isNotBlank(request.getStoreName())) {
            //不相等只带入storeName，如果相等只查storeId
            if (request.getStoreId().toString().equals(request.getStoreName())) {
                request.setStoreName(null);
            } else {
                request.setStoreId(null);
            }
        }
        //按照店铺名称查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
            //  供应商 用的是storeName   商家用的是supplierName
            List<StoreVO> storeList = storeQueryProvider.listByName(
                    ListStoreByNameRequest.builder().storeName(request.getStoreName())
                            .storeType(request.getStoreType())
                            .build()).getContext().getStoreVOList();

            if (storeList != null && !storeList.isEmpty()) {
                request.setStoreListId(storeList.stream().mapToLong(StoreVO::getStoreId).boxed().collect(Collectors
                        .toList()));
            } else {
                request.setPageNum(Constants.ZERO);
                request.setPageSize(Constants.ZERO);
                return request;
            }
        }
        List<Settlement> settlementList = settlementRepository.findAll(request.getWhereCriteria());
        int total = settlementList.size();
        request.setPageNum(Constants.ZERO);
        request.setPageSize(total);
        return request;
    }

    /**
     * 根据结算单id查询结算单
     *
     * @param settleId 结算单id
     * @return 结算单
     */
    public Settlement getLastSettlementByStoreId(Long settleId) {
        return settlementRepository.getLastSettlementByStoreId(settleId).orElse(null);
    }

    public LakalaSettlement getLastLakalaSettlementByStoreId(Long settleId) {
        return lakalaSettlementRepository.getLastSettlementByStoreId(settleId).orElse(null);
    }


    /**
     * @Author yangzhen
     * @Description // 从mysql 中查询结算单数据，用于刷入es
     * @Date 17:10 2020/12/14
     **/
    public List<SettlementViewVO> initEsPage(SettlementQueryRequest request) {
        List<Long> settleIds = CollectionUtils.isNotEmpty(
                request.getIdList()) ? request.getIdList()
                : settlementRepository.listByPage(request.getPageRequest());

        if (CollectionUtils.isEmpty(settleIds)) {
            return Lists.newArrayList();
        }

        //查询结算单详情
        List<Settlement> settlementList = settlementRepository.findAllById(settleIds);

        //根据结算单id查询店铺id
        List<Long> storeIds = settlementRepository.getStoreIdsByIds(settleIds);
        //根据店铺id 查询店铺名称
        List<StoreNameVO> storeNameList = storeQueryProvider.listStoreNameByStoreIds(
                new StoreNameListByStoreIdsResquest(storeIds)).getContext()
                .getStoreNameList();
        Map<Long, StoreNameVO> storeNameMap = storeNameList.stream().collect(
                Collectors.toMap(StoreNameVO::getStoreId, Function.identity()));

        List<SettlementViewVO> settlementVOList = new ArrayList<>();
        settlementList.forEach(settlement -> {
            StoreNameVO storeNameVO = storeNameMap.get(settlement.getStoreId());
            SettlementViewVO settlementViewVO = KsBeanUtil.convert(settlement, SettlementViewVO.class);
            settlementViewVO.setSettleCode(String.format("S%07d", settlementViewVO.getSettleId()));
            if (Objects.nonNull(storeNameVO)) {
                settlementViewVO.setStoreName(storeNameVO.getStoreName());
            }
            settlementVOList.add(settlementViewVO);
        });
        return settlementVOList;
    }

    public List<LakalaSettlementViewVO> initEsLakalaPage(SettlementQueryRequest request) {
        List<Long> settleIds = CollectionUtils.isNotEmpty(
                request.getIdList()) ? request.getIdList()
                : lakalaSettlementRepository.listByPage(request.getPageRequest());

        if (CollectionUtils.isEmpty(settleIds)) {
            return Lists.newArrayList();
        }

        //查询结算单详情
        List<LakalaSettlement> settlementList = lakalaSettlementRepository.findAllById(settleIds);

        //根据结算单id查询店铺id
        List<Long> storeIds = lakalaSettlementRepository.getStoreIdsByIds(settleIds);
        //根据店铺id 查询店铺名称
        List<StoreNameVO> storeNameList = storeQueryProvider.listStoreNameByStoreIds(
                        new StoreNameListByStoreIdsResquest(storeIds)).getContext()
                .getStoreNameList();
        Map<Long, StoreNameVO> storeNameMap = storeNameList.stream().collect(
                Collectors.toMap(StoreNameVO::getStoreId, Function.identity()));

        List<LakalaSettlementViewVO> settlementVOList = new ArrayList<>();
        settlementList.forEach(settlement -> {
            StoreNameVO storeNameVO = storeNameMap.get(settlement.getStoreId());
            LakalaSettlementViewVO settlementViewVO = KsBeanUtil.convert(settlement, LakalaSettlementViewVO.class);
            settlementViewVO.setSettlementCode(String.format("js%07d", settlementViewVO.getSettleId()));
            if (Objects.nonNull(storeNameVO)) {
                settlementViewVO.setStoreName(storeNameVO.getStoreName());
            }
            settlementVOList.add(settlementViewVO);
        });
        return settlementVOList;
    }

    public List<LakalaSettlementVO> getBySettlementUuid(GetBySettlementUuidRequest request) {
        if (CollectionUtils.isEmpty(request.getSettlementUuids())) {
            return Lists.newArrayList();
        }
        List<LakalaSettlement> settlementList = lakalaSettlementRepository.findBySettleUuidIn(request.getSettlementUuids());
        return KsBeanUtil.convert(settlementList, LakalaSettlementVO.class);
    }

    /**
     * 拼凑删除es-提供给findOne去调
     * @param esIndex es索引
     * @param id 结算单编号
     * @return "es_settlement:{companyId}"
     */
    public Object getDeleteIndex(String esIndex, Long id){
        return String.format(EsConstants.DELETE_SPLIT_CHAR, esIndex, id);
    }
}
