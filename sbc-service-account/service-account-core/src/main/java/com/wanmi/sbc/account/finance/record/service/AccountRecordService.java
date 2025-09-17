package com.wanmi.sbc.account.finance.record.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.constant.AccountConstants;
import com.wanmi.sbc.account.api.request.finance.record.*;
import com.wanmi.sbc.account.bean.enums.OrderDeductionType;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.vo.ReconciliationVO;
import com.wanmi.sbc.account.finance.record.model.entity.PayItemRecord;
import com.wanmi.sbc.account.finance.record.model.entity.PaySummarize;
import com.wanmi.sbc.account.finance.record.model.entity.Reconciliation;
import com.wanmi.sbc.account.finance.record.model.entity.TotalRecord;
import com.wanmi.sbc.account.finance.record.model.response.*;
import com.wanmi.sbc.account.finance.record.repository.ReconciliationRepository;
import com.wanmi.sbc.account.finance.record.utils.FinanceUtils;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wanmi.sbc.common.util.DateUtil.FMT_TIME_1;

/**
 * <p>财务对账单Service</p>
 * Created by of628-wenzhi on 2017-12-08-上午10:35.
 */
@Service
@Slf4j
public class AccountRecordService {

    @Value("classpath:/download/accountRecord.xlsx")
    private Resource templateFile;

    @Value("classpath:/download/accountRecordForO2o.xlsx")
    private Resource o2oTemplateFile;

    @Autowired
    private ReconciliationRepository repository;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private RedisUtil redisService;


    public static final String EXCEL_NAME = "财务对账";


    /**
     * 对账单列表分页查询
     *
     * @param request 分页查询参数结构
     * @param type    0：收入 1：退款
     * @return 分页后的对账单列表
     */
    public Page<AccountRecord> page(AccountRecordPageRequest request, Byte type) {
        PageRequest pageable =  PageRequest.of(request.getPageNum(), request.getPageSize());
        LocalDateTime beginTime = DateUtil.parse(request.getBeginTime(), FMT_TIME_1);
        LocalDateTime endTime = DateUtil.parse(request.getEndTime(), FMT_TIME_1);
        List<Long> storeIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getKeywords())) {
            List<StoreVO> stores = storeQueryProvider.listByName(ListStoreByNameRequest.builder()
                            .storeName(request.getKeywords())
                            .storeType(request.getStoreType())
                            .build()
                    )
                    .getContext().getStoreVOList();
            if (CollectionUtils.isNotEmpty(stores)) {
                storeIds = stores.stream().mapToLong(StoreVO::getStoreId).boxed().collect(Collectors.toList());
            } else {
                return new PageImpl<>(Collections.emptyList(), pageable, 0);
            }
        }
        List<String> partitions = getPartitions(beginTime, endTime);
        //先根据商家和店铺分页抓取总金额
        Page<Object> pageRecord = repository.queryTotalRecord(beginTime, endTime, request.getSupplierId(),
                storeIds.size(), storeIds.isEmpty() ? Collections.singletonList(0L) : storeIds, type,partitions.get(0),
                partitions.get(1), pageable
        );
        List<AccountRecord> records = new ArrayList<>();
        List<TotalRecord> content = pageRecord.getContent().stream().map(obj -> {
            TotalRecord totalRecord = new TotalRecord();
            totalRecord.setSupplierId(((Integer) ((Object[]) obj)[0]).longValue());
            totalRecord.setStoreId(((Long) ((Object[]) obj)[1]));
            totalRecord.setTotalAmount(((BigDecimal) ((Object[]) obj)[2]).add((BigDecimal) ((Object[]) obj)[4]).add((BigDecimal) ((Object[]) obj)[5]));
            totalRecord.setTotalPoints(((BigDecimal) ((Object[]) obj)[3]));
            totalRecord.setTotalPointsPrice(((BigDecimal) ((Object[]) obj)[4]));
            totalRecord.setTotalGiftCardPrice(((BigDecimal) ((Object[]) obj)[5]));
            return totalRecord;
        }).collect(Collectors.toList());
        boolean flag = type == 1;
        if (!content.isEmpty()) {
            List<PayWay> payWays = Arrays.stream(PayWay.values()).collect(Collectors.toList());
            storeIds = content.stream().mapToLong(TotalRecord::getStoreId).boxed().collect(Collectors.toList());
            Map<Long, StoreVO> storeMap = storeQueryProvider.listByIds(new ListStoreByIdsRequest(storeIds)).getContext
                    ().getStoreVOList().stream()
                    .collect(
                            Collectors.toMap(StoreVO::getStoreId, Function.identity())
                    );

            //根据当前分页抓取不同支付方式的对应金额
            List<PayItemRecord> payItemRecords = repository.queryPayItemRecord(beginTime, endTime, storeIds, type,
                    partitions.get(0),partitions.get(1));
            //merge
            records = IteratorUtils.zip(content, payItemRecords, (a, b) -> Objects.equals(a.getStoreId(), b
                            .getStoreId()),
                    (total, items) -> {
                        String prefix = flag ? "-￥" : "￥";
                        AccountRecord record = new AccountRecord();
                        StoreVO store = storeMap.remove(total.getStoreId());
                        record.setStoreId(total.getStoreId());
                        record.setStoreName(store.getStoreName());
                        record.setStoreType(store.getStoreType());
                        record.setSupplierId(total.getSupplierId());
                        record.setSupplierName(store.getSupplierName());
                        record.setTotalAmount(prefix.concat(FinanceUtils.amountFormatter(total.getTotalAmount())));
                        Map<String, String> payItemAmountMap = items.stream().collect(
                                Collectors.toMap(k -> k.getPayWay().toValue(), k -> prefix.concat(FinanceUtils
                                        .amountFormatter(k.getAmount()))));
                        payItemAmountMap.put(AccountConstants.POINTS_USED, Objects.isNull(total.getTotalPoints()) ? "0" :
                                total.getTotalPoints().toString());
                        payItemAmountMap.put(AccountConstants.GIFT_CARD_PRICE, Objects.isNull(total.getTotalGiftCardPrice()) ? "0" :
                                prefix.concat(FinanceUtils.amountFormatter(total.getTotalGiftCardPrice())));
                        record.setPayItemAmountMap(payItemAmountMap);
                        payWays.forEach(p -> {
                            if (p == PayWay.POINT) {
                                record.getPayItemAmountMap().put(p.toValue(), prefix.concat(total.getTotalPointsPrice().toString()));
                                return;
                            }

                            if (!payItemAmountMap.containsKey(p.toValue())) {
                                record.getPayItemAmountMap().put(p.toValue(), prefix.concat("0.00"));
                            }
                        });
                        return record;
                    });

        }
        return new PageImpl<>(records, pageable, pageRecord.getTotalElements());
    }

    /**
     * 对账列表汇总
     *
     * @param request 请求参数
     * @param type    0：收入 1：退款
     * @return 返回 对象列表汇总
     */
    public List<AccountGather> summarizing(AccountRecordPageRequest request, Byte type) {
        LocalDateTime beginTime = DateUtil.parse(request.getBeginTime(), FMT_TIME_1);
        LocalDateTime endTime = DateUtil.parse(request.getEndTime(), FMT_TIME_1);
        List<String> partitions = getPartitions(beginTime, endTime);
        List<PaySummarize> summarizes = repository.summarizing(beginTime, endTime, request.getSupplierId(), type,
                partitions.get(0),partitions.get(1));

        // 各支付方式汇总金额
        BigDecimal sumAmount = summarizes.stream().map(PaySummarize::getSumAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 积分抵扣汇总金额，包括积分兑换和下单的积分抵现金额
        BigDecimal totalPointsPrice = summarizes.stream().map(PaySummarize::getSumPointsPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 礼品卡金额
        BigDecimal totalGiftCardPrice =
                summarizes.stream().map(PaySummarize::getSumGiftCardPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);

        // 总金额=各支付方式汇总金额+积分抵扣汇总金额
        BigDecimal totalAmount = sumAmount.add(totalPointsPrice).add(totalGiftCardPrice);

        Map<String, PaySummarize> summarizeMap = summarizes.stream().collect(Collectors.toMap(p -> p.getPayWay()
                        .toValue(),
                Function.identity()));

        String vas =
                redisService.hget(
                        "value_added_services",
                        VASConstants.VAS_O2O_SETTING.toValue());
        boolean isO2O = false;
        if(StringUtils.isNotEmpty(vas) && VASStatus.ENABLE.toValue().equalsIgnoreCase(vas)){
            isO2O = true;
        }
        List<PayWay> payWayList = Arrays.stream(PayWay.values()).collect(Collectors.toList());
        if (isO2O) {
             payWayList = payWayList.parallelStream().filter(payWay -> PayWay.CASH != payWay).collect(Collectors.toList());
        }

        NumberFormat fmt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        fmt.setMinimumFractionDigits(2);
        List<AccountGather> list =  payWayList.parallelStream()
                .map((PayWay payWay) -> {
                    AccountGather gather = new AccountGather();
                    gather.setPayWay(payWay.toValue());
                    PaySummarize data = summarizeMap.remove(payWay.toValue());

                    // 积分抵扣单独处理，积分抵扣=积分兑换+下单的积分抵扣
                    if (payWay == PayWay.POINT) {
                        gather.setPercentage(fmt.format(BigDecimal.ZERO.compareTo(totalAmount) != 0 ?
                                totalPointsPrice.divide(totalAmount, 6, RoundingMode.HALF_UP).doubleValue() : 0));
                        gather.setSumAmount("￥".concat(FinanceUtils.amountFormatter(totalPointsPrice)));

                        return gather;
                    }

                    if (!Objects.isNull(data)) {
                        gather.setPercentage(fmt.format(BigDecimal.ZERO.compareTo(totalAmount) != 0 ? data.getSumAmount()
                                .divide(totalAmount, 6, RoundingMode.HALF_UP)
                                .doubleValue() : 0));
                        gather.setSumAmount("￥".concat(FinanceUtils.amountFormatter(data.getSumAmount())));
                    } else {
                        gather.setPercentage("0.00%");
                        gather.setSumAmount("￥0.00");
                    }
                    return gather;
                }
        ).collect(Collectors.toList());

        // 积分数量统计，和支付类型无关，所以增加一个虚拟的支付方式，单独统计
        AccountGather gather = new AccountGather();
        gather.setPayWay(AccountConstants.POINTS_USED);

        Long sumPoints = repository.sumPoints(beginTime, endTime, request.getSupplierId(), type,
                partitions.get(0),partitions.get(1));
        gather.setSumAmount(Objects.isNull(sumPoints) ? "0" : sumPoints.toString());

        // 积分数量统计没有百分占比，所以设置为空
        gather.setPercentage("");

        // 前端依次展示，积分数量在积分抵扣之后
        list.add(4, gather);

        // 礼品卡统计
        AccountGather gather1 = new AccountGather();
        gather1.setPayWay(AccountConstants.GIFT_CARD_PRICE);
        String sumGiftCardPriceStr = totalGiftCardPrice.toString();
        gather1.setPercentage(fmt.format(BigDecimal.ZERO.compareTo(totalAmount) != 0 ?
                new BigDecimal(sumGiftCardPriceStr)
                .divide(totalAmount, 6, RoundingMode.HALF_UP)
                .doubleValue() : 0));
        gather1.setSumAmount("￥".concat(FinanceUtils.amountFormatter(new BigDecimal(sumGiftCardPriceStr))));
        list.add(gather1);

        return list;
    }

    /**
     * 分页查询收入/退款明细
     *
     * @param request 分页查询参数结构
     * @param type    0：收入 1：退款
     * @return 分页后的对账明细列表
     */
    public Page<AccountDetails> pageDetails(AccountDetailsPageRequest request, Byte type) {
        PageRequest pageable =  PageRequest.of(request.getPageNum(), request.getPageSize());
        request.setType(Integer.valueOf(type));
        Page<Reconciliation> results =  repository.findAll(
                ReconciliationWhereCriteriaBuilder.build(request),
                request.getPageRequest());
        results.forEach(g -> {
            // 提货卡金额包含了积分价钱，积分不在展示
            if (GiftCardType.PICKUP_CARD.equals(g.getGiftCardType())){
                g.setPoints(0L);
                g.setPointsPrice(BigDecimal.ZERO);
            }
        });
        List<Reconciliation> content = results.getContent();
        List<AccountDetails> accountDetails = new ArrayList<>();
        wraperAccountDetails(type, request.getStoreId(), content, accountDetails, true);
        return new PageImpl<>(accountDetails, pageable, results.getTotalElements());

    }

    /**
     * 查询需要导出的收入/退款明细
     *
     * @param request 分页查询参数结构
     * @param type    0：收入 1：退款
     * @return 分页后的对账明细列表
     */
    public List<AccountDetails> exportDetailsLoad(AccountDetailsExportRequest request, Byte type) {
        LocalDateTime beginTime = DateUtil.parse(request.getBeginTime(), FMT_TIME_1);
        LocalDateTime endTime = DateUtil.parse(request.getEndTime(), FMT_TIME_1);
        List<String> partitions = getPartitions(beginTime, endTime);

        //设置查询支付方式 points不为null 则代表积分查询
        Long points = null;
        BigDecimal amount = null;
        QueryPayType queryPayType = request.getQueryPayType();
        if(Objects.nonNull(queryPayType)){
            PayWay payWay = setPayWay(queryPayType);
            if(queryPayType.name().startsWith(PayWay.POINT.name())){
                points = 0L;
            }
            //同列表分页查询，如果不是积分支付，支付金额大于0
            if(Objects.nonNull(payWay) && !payWay.equals(PayWay.POINT)){
                amount = BigDecimal.ZERO;
            }
            //同列表分页查询，如果是积分支付，payWay不作为过滤条件，point大于0
            if(Objects.nonNull(payWay) && payWay.equals(PayWay.POINT)){
                payWay = null;
            }
            request.setPayWay(payWay);
        }
        Integer onlinePayType = null;
        if(Objects.nonNull(request.getPayType()) && request.getPayType() == PayType.ONLINE){
            onlinePayType = PayType.ONLINE.toValue();
        }
        Integer offlinePayType = null;
        if(Objects.nonNull(request.getPayType()) && request.getPayType() == PayType.OFFLINE){
            offlinePayType = PayType.OFFLINE.toValue();
        }
        BigDecimal giftCardPrice = null;
        if(Objects.nonNull(request.getOrderDeductionType()) && request.getOrderDeductionType() == OrderDeductionType.GIFT_CARD){
            giftCardPrice = BigDecimal.ZERO;
        }
        if(Objects.nonNull(request.getOrderDeductionType()) && request.getOrderDeductionType() == OrderDeductionType.POINT){
            points = 0L;
        }

        List<Reconciliation> detailList = repository.queryDetails(
                request.getStoreId(),
                beginTime,
                endTime,
                type,
                request.getPayWay(),
                request.getTradeNo(),
                partitions.get(0),
                partitions.get(1),
                points,
                amount,
                giftCardPrice,
                offlinePayType,
                onlinePayType
        );
        //同列表展示，云闪付0元订单为积分支付
        detailList = detailList.parallelStream().peek(reconciliation -> {
            BigDecimal amount1 = reconciliation.getAmount();
            PayWay payWay = reconciliation.getPayWay();
            if (PayWay.UNIONPAY.equals(payWay) && amount1.compareTo(BigDecimal.ZERO) == 0) {
                reconciliation.setPayWay(PayWay.POINT);
            }
        }).collect(Collectors.toList());
        List<AccountDetails> accountDetails = new ArrayList<>();
        wraperAccountDetails(type, request.getStoreId(), detailList, accountDetails, false);
        return accountDetails;
    }

    /**
     * 设置查询支付方式
     * @param queryPayType
     * @return
     */
    private PayWay setPayWay(QueryPayType queryPayType){
        PayWay payWay;
        switch (queryPayType) {
            case ALIPAY:
                //支付宝支付
                payWay = PayWay.ALIPAY;
                break;
            case WECHAT:
                //微信支付
                payWay = PayWay.WECHAT;
                break;
            case UNIONPAY_B2B:
                //企业银联支付
                payWay = PayWay.UNIONPAY_B2B;
                break;
            case UNIONPAY:
                //云闪付支付
                payWay = PayWay.UNIONPAY;
                break;
            case BALANCE:
                //余额支付
                payWay = PayWay.BALANCE;
                break;
            case CREDIT:
                //授信支付
                payWay = PayWay.CREDIT;
                break;
            case OFFLINE:
                //线下支付
                payWay = PayWay.CASH;
                break;
            case POINT:
                //纯积分支付
                payWay = PayWay.POINT;
                break;
            case POINT_ALIPAY:
                //积分+支付宝支付
                payWay = PayWay.ALIPAY;
                break;
            case POINT_WECHAT:
                //积分+微信支付
                payWay = PayWay.WECHAT;
                break;
            case POINT_UNIONPAY_B2B:
                //积分+企业银联支付
                payWay = PayWay.UNIONPAY_B2B;
                break;
            case POINT_UNIONPAY:
                //积分+云闪付
                payWay = PayWay.UNIONPAY;
                break;
            case POINT_BALANCE:
                //积分+余额支付
                payWay = PayWay.BALANCE;
                break;
            case POINT_CREDIT:
                //积分+授信支付
                payWay = PayWay.CREDIT;
                break;
            case POINT_OFFLINE:
                //积分+线下支付
                payWay = PayWay.CASH;
                break;
            default:
                payWay = null;
                break;
        }

        return payWay;
    }

    /**
     * 根据订单号和交易类型删除
     *
     * @param orderCode 订单编号
     * @param typeFlag  交易类型
     */
    @Transactional
    public void deleteByOrderCodeAndType(String orderCode, Byte typeFlag) {
        repository.deleteByOrderCodeAndType(orderCode, typeFlag);
    }

    /**
     * 根据退单号和交易类型删除
     *
     * @param returnOrderCode 订单编号
     * @param typeFlag        交易类型
     */
    @Transactional
    public void deleteByReturnOrderCodeAndType(String returnOrderCode, Byte typeFlag) {
        repository.deleteByReturnOrderCodeAndType(returnOrderCode, typeFlag);
    }

    /**
     * 新增对账单
     *
     * @param reconciliation 对账单
     */
    @Transactional
    @GlobalTransactional
    public void add(Reconciliation reconciliation) {
        reconciliation.setId(generatorService.generateRNid());
        repository.save(reconciliation);
    }

    /**
     * 批量转换Reconciliation实例转化为AccountDetails实例
     *
     * @param type           交易类型
     * @param storeId        店铺Id
     * @param detailList     AccountDetails新实例
     * @param accountDetails Reconciliation实例
     * @param flag           是否增加前缀￥
     */
    private void wraperAccountDetails(Byte type, Long storeId, List<Reconciliation> detailList,
                                      List<AccountDetails> accountDetails, boolean flag) {
        StoreVO store = storeQueryProvider.getById(new StoreByIdRequest(storeId)).getContext().getStoreVO();
        if (!detailList.isEmpty()) {
            detailList.forEach(
                    i -> {
                        AccountDetails details;
                        if (type == 0) {
                            details = new AccountDetails();

                        } else {
                            details = new RefundDetails();
                        }
                        BeanUtils.copyProperties(i, details);
                        String amount = FinanceUtils.amountFormatter(i.getAmount());

                        String pointsPrice = flag ? "￥".concat("0.00") : "0.00";

                        if (!Objects.isNull(i.getPointsPrice())
                                && !GiftCardType.PICKUP_CARD.equals(details.getGiftCardType())) {
                            if (flag) {
                                pointsPrice = "￥".concat(FinanceUtils.amountFormatter(i.getPointsPrice()));
                            } else {
                                pointsPrice = i.getPointsPrice().toString();
                            }
                        }

                        String giftCardPrice = flag ? "￥".concat("0.00") : "0.00";

                        if (!Objects.isNull(i.getGiftCardPrice())) {
                            if (flag) {
                                giftCardPrice = "￥".concat(FinanceUtils.amountFormatter(i.getGiftCardPrice()));
                            } else {
                                giftCardPrice = i.getGiftCardPrice().toString();
                            }
                        }

                        details.setAmount(flag ? "￥".concat(amount) : amount);
                        details.setStoreName(store.getStoreName());
                        details.setPoints(Objects.isNull(i.getPoints()) || GiftCardType.PICKUP_CARD.equals(details.getGiftCardType()) ? 0L : i.getPoints());
                        details.setPointsPrice(pointsPrice);
                        details.setGiftCardPrice(giftCardPrice);
                        details.setGiftCardType(Nutils.defaultVal(details.getGiftCardType(), GiftCardType.CASH_CARD));
                        accountDetails.add(details);
                    }
            );
        }
    }

    /**
     * 设置request查询参数
     *
     * @param request
     * @param type
     * @return
     */
    private void setRequest(AccountRecordPageRequest request, Byte type) {
        LocalDateTime beginTime = DateUtil.parse(request.getBeginTime(), FMT_TIME_1);
        LocalDateTime endTime = DateUtil.parse(request.getEndTime(), FMT_TIME_1);
        List<Long> storeIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getKeywords())) {
            List<StoreVO> stores = storeQueryProvider.listByName(ListStoreByNameRequest.builder()
                            .storeName(request.getKeywords())
                            .storeType(request.getStoreType())
                            .build()
                    )
                    .getContext().getStoreVOList();
            if (CollectionUtils.isNotEmpty(stores)) {
                storeIds = stores.stream().mapToLong(StoreVO::getStoreId).boxed().collect(Collectors.toList());
            } else {
                return;
            }
        }
        List<String> partitions = getPartitions(beginTime, endTime);
        int total = repository.queryCount(beginTime, endTime, request.getSupplierId(),
                storeIds.size(), storeIds.isEmpty() ? Collections.singletonList(0L) : storeIds, type,partitions.get(0),
                partitions.get(1));
        request.setPageNum(0);
        request.setPageSize(total);
    }

    /**
     * 得到财务对账Excel数据
     *
     * @param request
     * @return
     */
    private AccountRecordExcel getExcelData(AccountRecordPageRequest request) {
        String theme = getExcelTheme(request.getBeginTime(), request.getEndTime());
        setRequest(request, (byte) 0);
        List<AccountRecord> accountRecords;
        List<AccountGather> accountGathers;
        if (ObjectUtils.isEmpty(request) || request.getPageSize() == 0) {
            accountRecords = Collections.emptyList();
        } else {
            Page<AccountRecord> page = page(request, (byte) 0);
            accountRecords = page.getContent();
        }
        accountGathers = summarizing(Objects.requireNonNull(request), (byte) 0);
        AccountRecordExcel excelData = new AccountRecordExcel();
        excelData.setAccountGathers(accountGathers);
        excelData.setAccountRecords(accountRecords);
        excelData.setTheme(theme);
        setRequest(request, (byte) 1);
        List<AccountRecord> returnRecords;
        List<AccountGather> returnGathers;
        if (ObjectUtils.isEmpty(request) || request.getPageSize() == 0) {
            returnRecords = Collections.emptyList();
        } else {
            Page<AccountRecord> page = page(request, (byte) 1);
            returnRecords = page.getContent();
        }
        returnGathers = summarizing(Objects.requireNonNull(request), (byte) 1);
        excelData.setReturnRecords(returnRecords);
        excelData.setReturnGathers(returnGathers);
        return excelData;
    }

    /**
     * 将对账数据写入excel
     *
     * @param request
     * @throws Exception
     */
    public String writeAccountRecordToExcel(AccountRecordToExcelRequest request) {
        AccountRecordPageRequest pageRequest = new AccountRecordPageRequest();
        KsBeanUtil.copyPropertiesThird(request, pageRequest);
        String vas =
                redisService.hget(
                        "value_added_services",
                        VASConstants.VAS_O2O_SETTING.toValue());
        boolean isO2O = false;
        if(StringUtils.isNotEmpty(vas) && VASStatus.ENABLE.toValue().equalsIgnoreCase(vas)){
            isO2O = true;
        }

        AccountRecordExcel excelData = getExcelData(pageRequest);
        Resource resource= isO2O ? o2oTemplateFile : templateFile;
        try (InputStream inputStream = resource.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        ) {
            XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
            xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            writeExcel(workbook, excelData, xssfCellStyle,isO2O);
            workbook.setActiveSheet(request.getAccountRecordType() == null ? 0 : request.getAccountRecordType().toValue());
            workbook.write(byteArrayOutputStream);
            return Base64.getMimeEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            log.error("导出Excel错误:{}", e.getMessage());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 财务对账获取excel主题
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    private String getExcelTheme(String beginTime, String endTime) {
        return EXCEL_NAME + beginTime.substring(0, 10) + "～" + endTime.substring(0, 10);
    }

    /**
     * 写入Excel标题
     *
     * @param xssfSheet
     * @param theme
     */
    private void writeExcelTheme(XSSFSheet xssfSheet, String theme) {
        XSSFRow xssfRow = getRow(xssfSheet, 0);
        XSSFCell cell = getCell(xssfRow, 0);
        cell.setCellValue(theme);
    }

    /**
     * 对账数据写入具体操作
     *
     * @param xssfWorkbook
     * @param excelData
     * @throws Exception
     */
    private void writeExcel(XSSFWorkbook xssfWorkbook, AccountRecordExcel excelData, XSSFCellStyle xssfCellStyle,boolean isO2O) {
        //收入汇总sheet
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        //写入标题
        writeExcelTheme(xssfSheet, excelData.getTheme());
        //写入所有店铺收入汇总
        writeExcelTotal(xssfSheet, excelData.getAccountGathers(), xssfCellStyle);
        //写入各店铺收入明细汇总
        writeExcelStore(xssfSheet, excelData.getAccountRecords(), xssfCellStyle,isO2O);

        //退款汇总sheet
        xssfSheet = xssfWorkbook.getSheetAt(1);
        //写入标题
        writeExcelTheme(xssfSheet, excelData.getTheme());
        //写入所有店铺退款汇总
        writeExcelTotal(xssfSheet, excelData.getReturnGathers(), xssfCellStyle);
        //写入各店铺退款明细汇总
        writeExcelStore(xssfSheet, excelData.getReturnRecords(), xssfCellStyle,isO2O);
    }

    /**
     * 写入对账汇总信息
     *
     * @param xssfSheet
     * @param accountGathers
     * @param xssfCellStyle
     */
    private void writeExcelTotal(XSSFSheet xssfSheet, List<AccountGather> accountGathers, XSSFCellStyle xssfCellStyle) {
        accountGathers = filterGather(accountGathers);
        XSSFRow xssfRow = getRow(xssfSheet, 2);
        for (int cellNum = 0; cellNum < accountGathers.size(); cellNum++) {
            XSSFCell cell = getCell(xssfRow, cellNum + 2);
            cell.setCellStyle(xssfCellStyle);
            String amount = accountGathers.get(cellNum).getSumAmount();
            cell.setCellValue(StringUtils.defaultString(amount));
        }
        xssfRow = getRow(xssfSheet, 3);
        for (int cellNum = 0; cellNum < accountGathers.size(); cellNum++) {
            XSSFCell cell = getCell(xssfRow, cellNum + 2);
            cell.setCellStyle(xssfCellStyle);
            String percentage = accountGathers.get(cellNum).getPercentage();
            cell.setCellValue(StringUtils.defaultString(percentage));
        }
    }

    /**
     * 过滤对账汇总信息
     *
     * @param accountGathers
     * @return
     */
    private List<AccountGather> filterGather(List<AccountGather> accountGathers) {
        return accountGathers.stream().filter(accountGather -> (!(StringUtils.equals(accountGather.getPayWay(),
                PayWay.ADVANCE.name()))) &&
                (!(StringUtils.equals(accountGather.getPayWay(), PayWay.COUPON.name())))).collect(Collectors.toList());
    }

    /**
     * 写入各店铺汇总数据
     *
     * @param xssfSheet
     * @param accountRecords
     * @param xssfCellStyle
     */
    private void writeExcelStore(XSSFSheet xssfSheet, List<AccountRecord> accountRecords, XSSFCellStyle xssfCellStyle,boolean isO2O) {
        for (int rowNum = 0; rowNum < accountRecords.size(); rowNum++) {
            //从第7行开始写入
            XSSFRow xssfRow = getRow(xssfSheet, rowNum + 6);
            AccountRecord accountRecord = accountRecords.get(rowNum);
            List<String> recordList = converRecord(accountRecord, rowNum,isO2O);
            for (int cellNum = 0; cellNum < recordList.size(); cellNum++) {
                XSSFCell cell = getCell(xssfRow, cellNum);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(StringUtils.defaultString(recordList.get(cellNum)));
            }
        }
    }

    /**
     * 对record对象转换
     *
     * @param accountRecord
     * @param rowNum
     * @return
     */
    private List<String> converRecord(AccountRecord accountRecord, Integer rowNum,boolean isO2O) {
        if (ObjectUtils.isEmpty(accountRecord)) {
            return Collections.emptyList();
        }
        List<String> recordList = new ArrayList<>();
        recordList.add(String.valueOf(rowNum + 1));
        recordList.add(accountRecord.getStoreName());


        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.WECHAT.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.ALIPAY.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.POINT.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(AccountConstants.POINTS_USED));
        recordList.add(accountRecord.getPayItemAmountMap().get(AccountConstants.GIFT_CARD_PRICE));
        if(!isO2O) {
            recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.CASH.name()));
        }
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.UNIONPAY_B2B.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.UNIONPAY.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.BALANCE.name()));
        recordList.add(accountRecord.getPayItemAmountMap().get(PayWay.CREDIT.name()));
        recordList.add(accountRecord.getTotalAmount());
        return recordList;
    }

    /**
     * 得到Excel行，防止空指针
     *
     * @param xssfSheet
     * @param rowNum
     * @return
     */
    private XSSFRow getRow(XSSFSheet xssfSheet, int rowNum) {
        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
        if (ObjectUtils.isEmpty(xssfRow)) {
            xssfRow = xssfSheet.createRow(rowNum);
        }
        return xssfRow;
    }

    /**
     * 得到Excel列，防止空指针
     *
     * @param xssfRow
     * @param cellNum
     * @return
     */
    private XSSFCell getCell(XSSFRow xssfRow, int cellNum) {
        XSSFCell cell = xssfRow.getCell(cellNum);
        if (ObjectUtils.isEmpty(cell)) {
            cell = xssfRow.createCell(cellNum);
        }
        return cell;
    }

    /**
     * 用于ES初始化
     * @param request
     * @return
     */
    public List<ReconciliationVO> listByPage(BasePageRequest request) {
        return repository.listByPage(request.getPageRequest()).parallelStream()
                .map(reconciliation -> KsBeanUtil.convert(reconciliation, ReconciliationVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据时间获取id分区
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<String> getPartitions(LocalDateTime beginTime, LocalDateTime endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String startPartition;
        String endPartition;
        if (Objects.isNull(beginTime) ) {
            startPartition = null;
        } else {
            startPartition = "RN"  + beginTime.format(formatter);
        }
        if (Objects.isNull(endTime) ) {
            endPartition = null;
        } else {
            endPartition = "RN"  + endTime.plusMonths(1).format(formatter);
        }
        if(StringUtils.equals(startPartition,endPartition)) {
            return Lists.newArrayList(startPartition,null);
        }
        return Lists.newArrayList(startPartition,endPartition);
    }
}
