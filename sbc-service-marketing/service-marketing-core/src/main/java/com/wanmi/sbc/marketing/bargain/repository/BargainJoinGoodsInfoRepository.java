package com.wanmi.sbc.marketing.bargain.repository;

import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinGoodsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>砍价DAO</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Repository
public interface BargainJoinGoodsInfoRepository extends JpaRepository<BargainJoinGoodsInfo, Long>,
        JpaSpecificationExecutor<BargainJoinGoodsInfo> {


    @Override
    @EntityGraph(value = "bargainJoinGoodsInfo")
    Page<BargainJoinGoodsInfo> findAll(Specification<BargainJoinGoodsInfo> specification, Pageable pageable);

    @Override
    @EntityGraph(value = "bargainJoinGoodsInfo")
    List<BargainJoinGoodsInfo> findAll(Specification<BargainJoinGoodsInfo> spec, Sort sort);

    @Override
    @EntityGraph(value = "bargainJoinGoodsInfo")
    List<BargainJoinGoodsInfo> findAll(Specification<BargainJoinGoodsInfo> spec);

    @Override
    @EntityGraph(value = "bargainJoinGoodsInfo")
    BargainJoinGoodsInfo getOne(Long aLong);
}
