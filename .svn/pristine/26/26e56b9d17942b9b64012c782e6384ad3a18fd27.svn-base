package com.wanmi.sbc.goods.storecate.service;

import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.goods.api.request.storecate.*;
import com.wanmi.sbc.goods.bean.enums.CateParentTop;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.storecate.entity.StoreCateBase;
import com.wanmi.sbc.goods.storecate.model.root.StoreCate;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateRepository;
import com.wanmi.sbc.goods.storecate.request.StoreCateQueryRequest;
import com.wanmi.sbc.goods.storecate.request.StoreCateSaveRequest;
import com.wanmi.sbc.goods.storecate.request.StoreCateSortRequest;
import com.wanmi.sbc.goods.storecate.response.StoreCateResponse;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品分类服务
 * Created by bail on 2017/11/14.
 */
@Service
public class StoreCateService {

    private static final String SPLIT_CHAR = "|";

    @Autowired
    private StoreCateRepository storeCateRepository;

    @Autowired
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private O2oStoreCateQueryService o2oStoreCateQueryService;

    /**
     * 初始化分类，生成默认分类
     */
    @GlobalTransactional
    @Transactional
    public void initStoreDefaultCate(Long storeId) {
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeId);
        queryRequest.setIsDefault(DefaultFlag.YES);
        StoreCate defCate = storeCateRepository.findOne(queryRequest.getWhereCriteria()).orElse(null);
        if (defCate == null) {
            StoreCate storeCate = new StoreCate();
            storeCate.setStoreId(storeId);
            storeCate.setCateName("默认分类");
            storeCate.setCateParentId((long) (CateParentTop.ZERO.toValue()));
            storeCate.setCatePath(String.valueOf(storeCate.getCateParentId()).concat(SPLIT_CHAR));
            storeCate.setCateGrade(1);
            storeCate.setCreateTime(LocalDateTime.now());
            storeCate.setUpdateTime(storeCate.getCreateTime());
            storeCate.setDelFlag(DeleteFlag.NO);
            storeCate.setSort(0);
            storeCate.setIsDefault(DefaultFlag.YES);
            storeCateRepository.save(storeCate);
        }
    }

    /**
     * 条件查询商品分类
     *
     * @param storeId 店铺标识
     * @return list
     */
    public List<StoreCateResponse> query(Long storeId) {
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        if (!storeId.equals(Constants.BOSS_DEFAULT_STORE_ID)){
            queryRequest.setStoreId(storeId);
        }
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.putSort("isDefault", SortType.DESC.toValue());
        queryRequest.putSort("sort", SortType.ASC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        List<StoreCate> cateList;
        Sort sort = queryRequest.getSort();
        StoreType storetype = StoreType.SUPPLIER;
        if (Objects.nonNull(queryRequest.getStoreId())){
            storetype = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(storeId).build())
                    .getContext().getStoreVO().getStoreType();
        }
        if(storetype != StoreType.O2O){
            if(Objects.nonNull(sort)) {
                cateList = storeCateRepository.findAll(queryRequest.getWhereCriteria(), sort);
            }else {
                cateList = storeCateRepository.findAll(queryRequest.getWhereCriteria());
            }
        }else {
            cateList = o2oStoreCateQueryService.findAll(queryRequest);
        }
        if (CollectionUtils.isEmpty(cateList)) {
            return Collections.EMPTY_LIST;
        }
        return cateList.stream().map(storeCate -> {
            StoreCateResponse storeCateResponse = new StoreCateResponse();
            BeanUtils.copyProperties(storeCate, storeCateResponse);
            return storeCateResponse;
        }).collect(Collectors.toList());
    }

    /**
     * 条件查询非默认的店铺商品分类
     *
     * @param storeId 店铺标识
     * @return list
     */
    public List<StoreCateResponse> queryNoDefault(Long storeId) {
        if (storeId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeId);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setIsDefault(DefaultFlag.NO);
        queryRequest.putSort("sort", SortType.ASC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        List<StoreCate> cateList;
        Sort sort = queryRequest.getSort();
        if(Objects.nonNull(sort)) {
            cateList = storeCateRepository.findAll(queryRequest.getWhereCriteria(), sort);
        }else {
            cateList = storeCateRepository.findAll(queryRequest.getWhereCriteria());
        }
        return cateList.stream().map(storeCate -> {
            StoreCateResponse storeCateResponse = new StoreCateResponse();
            BeanUtils.copyProperties(storeCate, storeCateResponse);
            return storeCateResponse;
        }).collect(Collectors.toList());
    }

    /**
     * 根据ID查询某个商品店铺分类
     *
     * @param storeCateId 分类ID
     * @return list
     */
    public StoreCateResponse findById(Long storeCateId) {
        if (storeCateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate storeCate = storeCateRepository.findById(storeCateId).orElseGet(StoreCate::new);
        StoreCateResponse response = new StoreCateResponse();
        BeanUtils.copyProperties(storeCate, response);
        return response;
    }

    /**
     * 验证一级分类数量的限制
     */
    private void checkFirstGradeNum(StoreCate storeCate) {
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeCate.getStoreId());
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setCateGrade(1);
        if (storeCateRepository.count(queryRequest.getWhereCriteria()) >= Constants.STORE_CATE_FIRST_NUM) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030159, new Object[]{Constants.STORE_CATE_FIRST_NUM});
        }
    }

    /**
     * 验证分类的父级店铺分类信息
     *
     * @return 返回父分类
     */
    private StoreCate checkPareStoreCate(StoreCate storeCate) {
        //1.验证父分类是否存在
        StoreCate pareStoreCate = storeCateRepository.findById(storeCate.getCateParentId()).orElse(null);
        if (pareStoreCate == null || DeleteFlag.YES.equals(pareStoreCate.getDelFlag())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030018);
        }
        //2.验证分类层次是否超过2级(父分类的层次是否大于等于2级)
        if (pareStoreCate.getCateGrade() >= Constants.STORE_CATE_GRADE) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030160, new Object[]{Constants.STORE_CATE_GRADE});
        }
        //3.验证在该分类下的二级分类限制个数
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeCate.getStoreId());
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setCateParentId(storeCate.getCateParentId());
        if (storeCateRepository.count(queryRequest.getWhereCriteria()) >= Constants.STORE_CATE_SECOND_NUM) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030161, new Object[]{Constants.STORE_CATE_SECOND_NUM});
        }
        return pareStoreCate;
    }

    /**
     * 验证参数并初始化部分值
     */
    private void checkAndInit(StoreCate storeCate) {
        if (storeCate.getCateParentId() == null || storeCate.getCateParentId() == ((long) CateParentTop.ZERO.toValue())) {
            //如果添加的是一级分类,验证一级分类限制个数
            checkFirstGradeNum(storeCate);
            storeCate.setCateParentId((long) (CateParentTop.ZERO.toValue()));
            storeCate.setCateGrade(1);
            storeCate.setCatePath(String.valueOf(storeCate.getCateParentId()).concat(SPLIT_CHAR));
        } else {
            //验证父分类信息
            StoreCate pareStoreCate = checkPareStoreCate(storeCate);
            storeCate.setCateGrade(pareStoreCate.getCateGrade() + 1);
            storeCate.setCatePath(pareStoreCate.getCatePath().concat(String.valueOf(pareStoreCate.getStoreCateId())).concat(SPLIT_CHAR));
        }
        if (storeCate.getSort() == null) {
            storeCate.setSort(0);//默认排序号为0
        }
    }

    /**
     * 新增商品店铺分类
     *
     * @param saveRequest 商品店铺分类
     * @throws SbcRuntimeException
     */
    @Transactional
    public StoreCateResponse add(StoreCateAddRequest saveRequest) {
        if (saveRequest == null || saveRequest.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate storeCate = new StoreCate();
        BeanUtils.copyProperties(saveRequest, storeCate);

        /**1.参数验证,部分数据初始化*/
        //1.1.验证名称是否重复
//        checkNameExist(storeCate);
        //1.2.验证其他参数,并初始化部分值
        checkAndInit(storeCate);

        /**2.初始化数据并插入*/
        storeCate.setDelFlag(DeleteFlag.NO);
        storeCate.setIsDefault(DefaultFlag.NO);
        storeCate.setCreateTime(LocalDateTime.now());
        storeCate.setUpdateTime(LocalDateTime.now());
        //判断当前分类是否存在相同分类
        Long count = storeCateRepository.countStoreCateByConditions(storeCate);
        if (count > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030021);
        }
        //查询该父分类下排序最大的子分类
        StoreCate maxCate = storeCateRepository.findTop1ByCateParentIdOrderBySortDesc(saveRequest.getCateParentId());
        if (Objects.nonNull(maxCate)) {
            //如果该分类下已有子分类，则新增的分类排序为：最大排序+1
            storeCate.setSort(maxCate.getSort() + 1);
        }

        storeCate.setStoreCateId(storeCateRepository.save(storeCate).getStoreCateId());

        StoreCateResponse response = new StoreCateResponse();
        BeanUtils.copyProperties(storeCate, response);
        return response;
    }

    /**
     * 编辑店铺商品分类
     *
     * @param saveRequest 店铺商品分类
     * @throws SbcRuntimeException
     */
    @Transactional
    public void edit(StoreCateModifyRequest saveRequest) {
        if (saveRequest == null || saveRequest.getStoreCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate newStoreCate = new StoreCate();
        BeanUtils.copyProperties(saveRequest, newStoreCate);
        StoreCate oldStoreCate = storeCateRepository.findById(saveRequest.getStoreCateId()).orElse(null);

        /**1.参数验证*/
        if (oldStoreCate == null || DeleteFlag.YES.equals(oldStoreCate.getDelFlag()) || !Objects.equals(oldStoreCate.getStoreId(), newStoreCate.getStoreId()) || DefaultFlag.YES.equals(oldStoreCate.getIsDefault())) {
            //1.1.待修改的分类不存在 或 修改的店铺id不一致(数据安全问题) 或 默认分类不可修改
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (!oldStoreCate.getCateName().equals(newStoreCate.getCateName())) {
            //1.2.修改后的名称不一致时,才验证是否有重复名称
//            checkNameExist(newStoreCate);
        }
        //1.3.初始化部分值
        if (newStoreCate.getCateParentId() == null || newStoreCate.getCateParentId() == ((long) CateParentTop.ZERO.toValue())) {
            newStoreCate.setCateParentId((long) (CateParentTop.ZERO.toValue()));
            newStoreCate.setCateGrade(1);
            newStoreCate.setCatePath(String.valueOf(newStoreCate.getCateParentId()).concat(SPLIT_CHAR));
        } else {
            StoreCate pareStoreCate = storeCateRepository.findById(newStoreCate.getCateParentId()).orElseGet(StoreCate::new);
            newStoreCate.setCateGrade(pareStoreCate.getCateGrade() + 1);
            newStoreCate.setCatePath(pareStoreCate.getCatePath().concat(String.valueOf(pareStoreCate.getStoreCateId())).concat(SPLIT_CHAR));
        }
//        if(newStoreCate.getSort() == null){
//            newStoreCate.setSort(0);//默认排序号为0
//        }

        /**2.更新分类*/
        oldStoreCate.convertBeforeEdit(newStoreCate);
        oldStoreCate.setUpdateTime(LocalDateTime.now());
        storeCateRepository.save(oldStoreCate);
        //判断当前分类是否存在相同分类
        Long count = storeCateRepository.countStoreCateByConditions(oldStoreCate);
        if (count > 1) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030021);
        }
    }

    /**
     * 删除店铺商品分类
     *
     * @param saveRequest 待删除的分类信息
     * @throws SbcRuntimeException
     */
    @Transactional
    public HashMap<String, Object> delete(StoreCateDeleteRequest saveRequest) {
        if (saveRequest == null || saveRequest.getStoreCateId() == null || saveRequest.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate storeCate = storeCateRepository.findById(saveRequest.getStoreCateId()).orElse(null);
        if (storeCate == null || DeleteFlag.YES.equals(storeCate.getDelFlag()) || !Objects.equals(storeCate.getStoreId(), saveRequest.getStoreId()) || DefaultFlag.YES.equals(storeCate.getIsDefault())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeCate.getStoreId());
        queryRequest.setIsDefault(DefaultFlag.YES);
        StoreCate defCate = storeCateRepository.findOne(queryRequest.getWhereCriteria()).orElse(null);//查询默认分类
        if (defCate == null) {
            //如果默认分类不存在，不允许删除
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030019);
        }

        List<Long> allCate = new ArrayList<>();
        allCate.add(storeCate.getStoreCateId());

        String oldCatePath = storeCate.getCatePath().concat(String.valueOf(storeCate.getStoreCateId())).concat(SPLIT_CHAR);
        StoreCateQueryRequest cateReq = new StoreCateQueryRequest();
        cateReq.setStoreId(storeCate.getStoreId());
        cateReq.setDelFlag(DeleteFlag.NO);
        cateReq.setLikeCatePath(oldCatePath);
        List<StoreCate> childCateList = storeCateRepository.findAll(cateReq.getWhereCriteria());

        //删除子分类
        if (CollectionUtils.isNotEmpty(childCateList)) {
            childCateList.stream().forEach(cate -> {
                cate.setDelFlag(DeleteFlag.YES);
                allCate.add(cate.getStoreCateId());
            });
            storeCateRepository.saveAll(childCateList);
        }
        //删除当前分类
        storeCate.setDelFlag(DeleteFlag.YES);
        storeCateRepository.save(storeCate);
        //仅为了埋点查询一次需要变更的商品,必须放在下面代码的前面
        List<StoreCateGoodsRela> storeCateGoodsRelas = storeCateGoodsRelaRepository.selectByStoreCateIds(allCate);

        //查询包含多个店铺分类的商品
        List<String> list = storeCateGoodsRelaRepository.findMoreCateGoodsIds();

        //依次删除多店铺分类的商品的店铺分类关系表信息(必须在前面)
        allCate.stream().forEach(storeCateId -> storeCateGoodsRelaRepository.deleteGoodsStoreCate(storeCateId,list));
        //迁移单店铺分类的商品至默认店铺分类(必须在后面)
        //storeCateGoodsRelaRepository.updateGoodsStoreCate(defCate.getStoreCateId(), allCate,list);
        // 暂时注释，seata2.0不支持该sql，换一种实现，等seata支持后可以换回来
        // 先查后更新
        List<String> updateGoodsIds = storeCateGoodsRelaRepository.findGoodsIdsForUpdate();
        if (CollectionUtils.isNotEmpty(updateGoodsIds)) {
            storeCateGoodsRelaRepository.updateGoodsStoreCateNew(defCate.getStoreCateId(), allCate, updateGoodsIds);
        }
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("allCate", allCate);
        returnMap.put("storeCateGoodsRelas", storeCateGoodsRelas);
        return returnMap;

    }

    /**
     * 商家APP里店铺分类排序
     *
     * @param saveRequest
     */
    @Transactional
    public void batchSortCate(StoreCateSaveRequest saveRequest) {
        storeCateRepository.saveAll(KsBeanUtil.convert(saveRequest.getStoreCateList(), StoreCate.class));
    }

    /**
     * 拖拽排序
     *
     * @param sortRequestList
     */
    @Transactional
    public void dragSort(List<StoreCateSortRequest> sortRequestList) {
        if (CollectionUtils.isEmpty(sortRequestList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (CollectionUtils.isNotEmpty(sortRequestList) && sortRequestList.size() > Constants.STORE_CATE_FIRST_NUM) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        sortRequestList.forEach(cate ->
                storeCateRepository.updateCateSort(cate.getStoreCateId(),
                        cate.getCateSort()));
    }

    /**
     * 验证是否有子类
     *
     * @param request
     */
    public Integer checkHasChild(StoreCateQueryHasChildRequest request) {
        if (request == null || request.getStoreCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate storeCate = storeCateRepository.findById(request.getStoreCateId()).orElse(null);
        if (storeCate == null || DeleteFlag.YES.equals(storeCate.getDelFlag())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        String oldCatePath = storeCate.getCatePath().concat(String.valueOf(storeCate.getStoreCateId())).concat(SPLIT_CHAR);
        StoreCateQueryRequest cateReq = new StoreCateQueryRequest();
        cateReq.setDelFlag(DeleteFlag.NO);
        cateReq.setLikeCatePath(oldCatePath);
        if (storeCateRepository.count(cateReq.getWhereCriteria()) > 0) {
            return Constants.yes;
        }
        return Constants.no;
    }

    /**
     * 验证分类下是否已经关联商品
     *
     * @param request
     */
    public Integer checkHasGoods(StoreCateQueryHasGoodsRequest request) {
        if (request == null || request.getStoreCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreCate storeCate = storeCateRepository.findById(request.getStoreCateId()).orElse(null);
        if (storeCate == null || DeleteFlag.YES.equals(storeCate.getDelFlag())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<Long> allCate = new ArrayList<>();
        allCate.add(request.getStoreCateId());

        String oldCatePath = storeCate.getCatePath().concat(String.valueOf(storeCate.getStoreCateId())).concat(SPLIT_CHAR);
        StoreCateQueryRequest cateReq = new StoreCateQueryRequest();
        cateReq.setDelFlag(DeleteFlag.NO);
        cateReq.setLikeCatePath(oldCatePath);
        List<StoreCate> childCateList = storeCateRepository.findAll(cateReq.getWhereCriteria());

        if (CollectionUtils.isNotEmpty(childCateList)) {
            childCateList.stream().forEach(cate -> allCate.add(cate.getStoreCateId()));
        }

        //根据店铺分类List,查询关联的商品数量
        if (storeCateGoodsRelaRepository.count((root, cquery, cbuild) -> root.get("storeCateId").in(allCate)) > 0) {
            return Constants.yes;
        }
        return Constants.no;
    }

    /**
     * 根据商品编号查询分类
     *
     * @param goodsIds
     * @return
     */
    public List<StoreCateGoodsRela> getStoreCateByGoods(List<String> goodsIds) {
        return storeCateGoodsRelaRepository.selectByGoodsId(goodsIds);
    }

    /**
     * 获取所有子分类
     *
     * @param storeCateId 分类ID
     * @param isHaveSelf  是否加入包含自身对象
     * @return
     */
    public List<StoreCate> findAllChlid(Long storeCateId, boolean isHaveSelf) {
        List<StoreCate> storeCateList = new ArrayList<>();
        StoreCate storeCate = storeCateRepository.findById(storeCateId).orElse(null);
        if (storeCate == null) {
            return storeCateList;
        }

        if (isHaveSelf) {
            storeCateList.add(storeCate);
        }

        String oldCatePath = storeCate.getCatePath().concat(String.valueOf(storeCate.getStoreCateId())).concat(SPLIT_CHAR);
        StoreCateQueryRequest cateReq = new StoreCateQueryRequest();
        cateReq.setDelFlag(DeleteFlag.NO);
        cateReq.setLikeCatePath(oldCatePath);
        storeCateList.addAll(storeCateRepository.findAll(cateReq.getWhereCriteria()));
        return storeCateList;
    }

    /**
     * 根据ID获取所有子分类->所有的商品
     *
     * @param storeCateId
     * @param isHaveSelf
     * @return
     */
    public List<StoreCateGoodsRela> findAllChildRela(Long storeCateId, boolean isHaveSelf) {
        List<Long> storeCateIds = this.findAllChlid(storeCateId, isHaveSelf)
                .stream()
                .map(StoreCate::getStoreCateId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(storeCateIds)) {
            return new ArrayList<>();
        }
        return storeCateGoodsRelaRepository.findAll((root, cquery, cbuild) -> root.get("storeCateId").in(storeCateIds));
    }

    /**
     * 根据ID获取所有子分类->所有的商品Id
     *
     * @param storeCateId
     * @param isHaveSelf
     * @return
     */
    public List<String> findGoodsIdByStoreCate(Long storeCateId, boolean isHaveSelf) {
        List<Long> storeCateIds = this.findAllChlid(storeCateId, isHaveSelf)
                .stream()
                .map(StoreCate::getStoreCateId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(storeCateIds)) {
            return new ArrayList<>();
        }
        return storeCateGoodsRelaRepository.selectGoodsIdByStoreCateIds(storeCateIds);
    }

    /**
     * 根据ID批量查询店铺商品分类
     *
     * @param cateIds 多个分类ID
     * @return list
     */
    public List<StoreCate> findByIds(List<Long> cateIds) {
        return storeCateRepository.findAll(StoreCateQueryRequest.builder().storeCateIds(cateIds).build().getWhereCriteria());
    }


    public List<StoreCateBase> findAllByStoreCateIdIn(List<Long> storeCateIds){
        return storeCateRepository.findAllByStoreCateIdIn(storeCateIds);
    }

    // 填充店铺分类
    public void fillStoreCate(List<GoodsInfoVO> skuList) {
        List<String> goodsIds = skuList.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsIds)) {
            return;
        }
        List<StoreCateGoodsRela> storeCateGoodsRelas = this.getStoreCateByGoods(goodsIds);
        if (CollectionUtils.isNotEmpty(storeCateGoodsRelas)) {
            Map<String, List<StoreCateGoodsRela>> storeCateMap = storeCateGoodsRelas.stream().collect(Collectors.groupingBy(StoreCateGoodsRela::getGoodsId));
            //为每个spu填充店铺分类编号
            if (MapUtils.isNotEmpty(storeCateMap)) {
                skuList.stream()
                        .filter(goods -> storeCateMap.containsKey(goods.getGoodsId()))
                        .forEach(goods -> goods.setStoreCateIds(storeCateMap.get(goods.getGoodsId()).stream().map(StoreCateGoodsRela::getStoreCateId).filter(Objects::nonNull).collect(Collectors.toList())));
            }
        }
    }

    /**
     * 比较二个分类id数据是否存在重合
     * 重合主要判断同一个类、子类、父类
     *
     * @param storeCateIds 店铺分类id
     * @param storeCateSecIds 店铺分类id
     * @return true:存在重合，false:不存在重合
     */
    public List<Long> coincideStoreCate(List<Long> storeCateIds, List<Long> storeCateSecIds) {
        List<Long> subCateList = storeCateIds.stream().filter(storeCateSecIds::contains).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(subCateList)) {
            return subCateList;
        }
        List<Long> allCateIds = new ArrayList<>(storeCateIds);
        allCateIds.addAll(storeCateSecIds);
        Map<Long, List<Long>> cateMap = this.findByIds(allCateIds).stream()
                .collect(Collectors.toMap(StoreCate::getStoreCateId,
                        (s) -> Arrays.stream(Objects.toString(s.getCatePath(), "").split("\\|"))
                                .map(NumberUtils::toLong).collect(Collectors.toList())));
        if (MapUtils.isEmpty(cateMap)) {
            return storeCateSecIds;
        }
        List<Long> subCateListSec = new ArrayList<>();
        List<Long> empty = Collections.emptyList();
        for (Long cateId : storeCateIds) {
            List<Long> firstCateIds = cateMap.getOrDefault(cateId, empty);
            for (Long secCateId : storeCateSecIds) {
                List<Long> secCateIds = cateMap.getOrDefault(secCateId, empty);
                if (secCateIds.contains(cateId) || firstCateIds.contains(secCateId)) {
                    subCateListSec.add(secCateId);
                }
            }
        }
        return subCateListSec;
    }

    /**
     * 根据父分类ID查询商品分类
     * @param request
     * @return
     */
    public List<StoreCateResponse> listByCateParentId(StoreCateListByParentIdRequest request) {
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        if (Objects.isNull(request.getParentId())){
            queryRequest.setCateGrade(Constants.ONE);
        }else {
            queryRequest.setCateParentId(request.getParentId());
        }
        queryRequest.setStoreId(request.getStoreId());
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.putSort("isDefault", SortType.DESC.toValue());
        queryRequest.putSort("sort", SortType.ASC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        List<StoreCate> cateList = storeCateRepository.findAll(queryRequest.getWhereCriteria(), queryRequest.getSort());
        return cateList.stream().map(storeCate -> {
            StoreCateResponse storeCateResponse = new StoreCateResponse();
            BeanUtils.copyProperties(storeCate, storeCateResponse);
            return storeCateResponse;
        }).collect(Collectors.toList());
    }
}
