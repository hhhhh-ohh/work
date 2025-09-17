package com.wanmi.sbc.customer.distribution.performance.service;

import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.distribution.performance.model.entity.DistinctDistributionIdsQuery;
import com.wanmi.sbc.customer.distribution.performance.model.entity.PerformanceQuery;
import com.wanmi.sbc.customer.distribution.performance.model.entity.PerformanceTotal;
import com.wanmi.sbc.customer.distribution.performance.model.entity.PerformanceTotalQuery;
import com.wanmi.sbc.customer.distribution.performance.model.root.DistributionPerformanceDay;
import com.wanmi.sbc.customer.distribution.performance.model.root.DistributionPerformanceMonth;
import com.wanmi.sbc.customer.distribution.performance.repository.DistributionPerformanceDayRepository;
import com.wanmi.sbc.customer.distribution.performance.repository.DistributionPerformanceMonthRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>分销业绩业务service</p>
 * Created by of628-wenzhi on 2019-04-17-16:28.
 */
@Service
public class DistributionPerformanceService {

    @Autowired
    private DistributionPerformanceDayRepository performanceDayRepository;

    @Autowired
    private DistributionPerformanceMonthRepository performanceMonthRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GeneratorService generatorService;

    @Autowired private RedissonClient redissonClient;

    /**
     * 排序字段
     */
    private static final String SORT_FIELD = "targetDate";


    /**
     * 根据年和月清理数据
     *
     * @param year
     * @param month
     */
    @Transactional
    public void cleanByYearAndMonth(Integer year, Integer month) {
        //拼接分区名
        String partitionName = String.format("p".concat(year.toString()).concat("%02d"), month);
        // 按分区查询是否存在
        String queryByPartitionSql = "SELECT COUNT(1) FROM information_schema.partitions WHERE  TABLE_NAME = " +
                "'distribution_performance_day' AND PARTITION_NAME = :partitionName ";
        // 按分区清理数据本地sql
        String clearByPartitionSql = "ALTER TABLE distribution_performance_day TRUNCATE PARTITION :partitionName";
        //清理日业绩数据
        cleanExecute(queryByPartitionSql, clearByPartitionSql, partitionName);
        //清理月业绩数据
        //按分区查询是否存在
        String queryByMonthPartitionSql="SELECT COUNT(1) FROM information_schema.partitions WHERE  TABLE_NAME = " +
                "'distribution_performance_month' AND PARTITION_NAME = :partitionName";
        //按分区清理数据本地sql
        String clearByMonthPartitionSql = "ALTER TABLE distribution_performance_month TRUNCATE PARTITION :partitionName";
        cleanExecute(queryByMonthPartitionSql, clearByMonthPartitionSql, partitionName);
    }

    /**
     * 记录分销员天业绩记录，有则做金额叠加，无则创建
     *
     * @param data
     */
    public void add(DistributionPerformanceDay data) {
        //加锁 防并发处理
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkDate = formatter.format(data.getTargetDate());
        String lockKey = CacheKeyConstant.DISTRIBUTION_PERFORMANCE_ADD_LOCK.concat(checkDate).concat(data.getDistributionId());
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            DistributionPerformanceDay performanceDay = performanceDayRepository.findFirstByDistributionIdAndTargetDate(
                    data.getDistributionId(), data.getTargetDate());
            if (!Objects.isNull(performanceDay)) {
                performanceDay.setCommission(performanceDay.getCommission().add(data.getCommission()));
                performanceDay.setSaleAmount(performanceDay.getSaleAmount().add(data.getSaleAmount()));
            } else {
                performanceDay = data;
                performanceDay.setId(generatorService.generateDistributionPerformanceId());
                performanceDay.setCreateTime(LocalDateTime.now());
            }
            performanceDayRepository.save(performanceDay);
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 记录分销员月业绩记录
     *
     * @param dataList
     */
    @Transactional
    public void add(List<DistributionPerformanceMonth> dataList) {
        performanceMonthRepository.saveAll(dataList);
    }

    /**
     * 根据分销员和目标日期查询，按照日纬度统计
     *
     * @param performanceQuery
     */
    public List<DistributionPerformanceDay> findByTargetDate(PerformanceQuery performanceQuery) {
        List<DistributionPerformanceDay> list =
                performanceDayRepository.findTop31ByDistributionIdAndTargetDateBetween(performanceQuery.getDistributionId()
                        , performanceQuery.getStartDate(), performanceQuery.getEndDate(), Sort.by(Sort.Direction.DESC,
                                SORT_FIELD));
        int days = (int) (performanceQuery.getEndDate().toEpochDay() - performanceQuery.getStartDate().toEpochDay());

        if (!list.isEmpty() && list.size() <= days) {
            //周期内存在没有业绩的日期，系统补0
            List<LocalDate> dateFiters = list.stream().map(DistributionPerformanceDay::getTargetDate)
                    .collect(Collectors.toList());
            LocalDate date = performanceQuery.getEndDate();
            Optional<DistributionPerformanceDay> customerOptional = list.stream().findFirst();
            for (int i = 0; i <= days; i++) {
                LocalDate targetDate = date.minusDays(i);
                if (dateFiters.stream().anyMatch(targetDate::equals)) {
                    continue;
                }
                //当天没有业绩，做补0处理
                DistributionPerformanceDay temp = new DistributionPerformanceDay();
                temp.setDistributionId(performanceQuery.getDistributionId());
                if (customerOptional.isPresent()) {
                    temp.setCustomerId(customerOptional.get().getCustomerId());
                }
                temp.setTargetDate(targetDate);
                //保证按天数倒叙
                list.add(i, temp);
            }
        }
        return list;
    }


    /**
     * 查询分销员最近6个月业绩，按照月维度统计
     *
     * @param distributionId
     * @return
     */
    public List<DistributionPerformanceMonth> findByLast6Months(String distributionId) {
        LocalDate startDate = LocalDate.of(LocalDate.now().minusMonths(Constants.SIX).getYear(),
                LocalDate.now().minusMonths(Constants.SIX).getMonth(), Constants.ONE);
        LocalDate endDate = LocalDate.of(LocalDate.now().minusMonths(Constants.ONE).getYear(),
                LocalDate.now().minusMonths(Constants.ONE).getMonth(), Constants.ONE).with(TemporalAdjusters.lastDayOfMonth());
        List<DistributionPerformanceMonth> list =
                performanceMonthRepository.findByDistributionIdAndTargetDateBetween(distributionId, startDate, endDate
                        , Sort.by(Sort.Direction.DESC, SORT_FIELD));
        if (!list.isEmpty() && list.size() < Constants.SIX) {
            //存在没有业绩的月份，系统补0
            List<LocalDate> dateFilters = list.stream().map(DistributionPerformanceMonth::getTargetDate)
                    .collect(Collectors.toList());
            Optional<DistributionPerformanceMonth> customerOptional = list.stream().findFirst();
            for (int i = 0; i < 6; i++) {
                LocalDate targetDate = endDate.minusMonths(i);
                //查询月份为近6个月，因此月份数字不可能重复，根据月份过滤掉有业绩的数据不做处理
                if (dateFilters.stream().anyMatch(d -> targetDate.getMonth() == d.getMonth())) {
                    continue;
                }
                //当月没有业绩，做补0处理
                DistributionPerformanceMonth temp = new DistributionPerformanceMonth();
                if (customerOptional.isPresent()) {
                    temp.setCustomerId(customerOptional.get().getCustomerId());
                }
                temp.setDistributionId(distributionId);
                temp.setTargetDate(targetDate);
                list.add(i, temp);
            }
        }
        return list;
    }

    /**
     * 分批抓取指定日期范围内分销业绩去重后的分销员id
     *
     * @param query
     * @return
     */
    public List<String> fetchDistinctDistributionIds(DistinctDistributionIdsQuery query) {
        return performanceDayRepository.findDistinctDistributionIds(query.getStartDate(), query.getEndDate(),
                query.getPageable());
    }

    /**
     * 汇总分销员指定日期内的销售业绩数据
     *
     * @param query
     * @return
     */
    public List<PerformanceTotal> queryTotalPerformanceByDistributionIds(PerformanceTotalQuery query) {
        return performanceDayRepository.findTotalAmountByDistributionIds(query.getDistributionId(),
                query.getStartDate(), query.getEndDate());
    }


    private void cleanExecute(String querySql, String cleanSql, String partitionName) {
        Query dayQuery = entityManager.createNativeQuery(querySql);
        dayQuery.setParameter("partitionName", partitionName);
        BigInteger res = (BigInteger) dayQuery.getSingleResult();
        if (res.compareTo(BigInteger.ZERO) > 0) {
            Query clearDayQuery = entityManager.createNativeQuery(cleanSql);
            clearDayQuery.setParameter("partitionName", partitionName);
            clearDayQuery.executeUpdate();
        }
    }

}
