package com.wanmi.sbc.elastic.goods.request;

import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.FieldCollapse;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsPropQueryRequest;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Slf4j
@Schema
public class EsGoodsInfoCriteriaBuilder {

	public static EsGoodsInfoCriteriaBuilder newInstance() {
		return new EsGoodsInfoCriteriaBuilder();
	}

	/**
	 * 聚合参数
	 */
	@Schema(description = "聚合参数")
	private Map<String, Aggregation> aggMap = new LinkedHashMap<>();

	/**
	 * 排序参数
	 */
	@Schema(description = "排序参数")
	private List<SortOptions> sorts = new ArrayList<>();

	/**
	 * 封装公共条件
	 *
	 * @return
	 */
	public NativeQuery getSearchCriteria(EsGoodsInfoQueryRequest request) {
//		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		NativeQueryBuilder builder = NativeQuery.builder();
		//设置可以查询1w+数据
		builder.withTrackTotalHits(Boolean.TRUE);
		Query queryBuilder = this.getWhereCriteria(request);
		builder.withQuery(queryBuilder);
		if (log.isInfoEnabled()) {
			log.info("where===> {} ", queryBuilder.bool().toString());
		}
		builder.withPageable(request.getPageable());
		if (CollectionUtils.isNotEmpty(sorts)) {
			sorts.forEach(builder::withSort);
		}
		//判断是否需要聚合处理
		if(Objects.isNull(request.getAggFlag()) || Boolean.TRUE.equals(request.getAggFlag())) {
			if (MapUtils.isNotEmpty(aggMap)) {
				aggMap.forEach(builder::withAggregation);
			}
		}
		//判断是否按goodsId去重查询(按goodsId折叠)
		if (Objects.nonNull(request.getIsDistinctGoodsId()) && Boolean.TRUE.equals(request.getIsDistinctGoodsId())) {
			FieldCollapse fieldCollapse = FieldCollapse.of(f -> f.field("goodsId"));
			builder.withFieldCollapse(fieldCollapse);
		}
		return builder.build();
	}

	public Query getWhereCriteria(EsGoodsInfoQueryRequest request) {
		String queryName = request.isQueryGoods() ? "goodsInfos" : "goodsInfo";
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

		if (StringUtils.isNotBlank(request.getLikeGoodsNo())) {
//			boolQueryBuilder.must(wildcardQuery("goodsNo", "*" + request.getLikeGoodsNo().trim() + "*"));
			boolQueryBuilder.must(wildcard(g -> g.field("goodsNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim()))));
		}

		if (StringUtils.isNotBlank(request.getLikeGoodsInfoNo())) {
//			boolQueryBuilder.must(wildcardQuery(queryName.concat(".goodsInfoNo"), "*" + request.getLikeGoodsInfoNo().trim() + "*"));
			boolQueryBuilder.must(wildcard(g -> g.field(queryName.concat(".goodsInfoNo")).value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim()))));
		}

		if (StringUtils.isNotBlank(request.getLikeStoreName())) {
//			boolQueryBuilder.must(wildcardQuery(queryName.concat(".storeName.keyword"), "*" + request.getLikeStoreName().trim() + "*"));
			boolQueryBuilder.must(wildcard(g -> g.field(queryName.concat(".storeName.keyword")).value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeStoreName().trim()))));
		}

		//查询库存
		if (Objects.nonNull(request.getMinStock()) && Objects.isNull(request.getMaxStock())) {
//			boolQueryBuilder.must(rangeQuery("stock").gte(request.getMinStock()));
			boolQueryBuilder.must(range(g -> g.field("stock").gte(JsonData.of(request.getMinStock()))));
		}
		if (Objects.nonNull(request.getMaxStock()) && Objects.isNull(request.getMinStock())) {
//			boolQueryBuilder.must(rangeQuery("stock").lte(request.getMaxStock()));
			boolQueryBuilder.must(range(g -> g.field("stock").lte(JsonData.of(request.getMaxStock()))));
		}
		if (Objects.nonNull(request.getMaxStock()) && Objects.nonNull(request.getMinStock())) {
//			boolQueryBuilder.must(rangeQuery("stock").gte(request.getMinStock()).lte(request.getMaxStock()));
			boolQueryBuilder.must(range(g -> g.field("stock").gte(JsonData.of(request.getMinStock())).lte(JsonData.of(request.getMaxStock()))));
		}
		//查询销量
		if (Objects.nonNull(request.getMinGoodsSalesNum()) && Objects.isNull(request.getMaxGoodsSalesNum())) {
//			boolQueryBuilder.must(rangeQuery("goodsSalesNum").gte(request.getMinGoodsSalesNum()));
			boolQueryBuilder.must(range(g -> g.field("goodsSalesNum").gte(JsonData.of(request.getMinGoodsSalesNum()))));
		}
		if (Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.isNull(request.getMinGoodsSalesNum())) {
//			boolQueryBuilder.must(rangeQuery("goodsSalesNum").lte(request.getMaxGoodsSalesNum()));
			boolQueryBuilder.must(range(g -> g.field("goodsSalesNum").lte(JsonData.of(request.getMaxGoodsSalesNum()))));
		}
		if (Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.nonNull(request.getMinGoodsSalesNum())) {
//			boolQueryBuilder.must(rangeQuery("goodsSalesNum").gte(request.getMinGoodsSalesNum()).lte(request.getMaxGoodsSalesNum()));
			boolQueryBuilder.must(range(g -> g.field("goodsSalesNum").gte(JsonData.of(request.getMinGoodsSalesNum())).lte(JsonData.of(request.getMaxGoodsSalesNum()))));
		}
		//批量商品ID
		if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getGoodsIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.must(terms(g -> g.field(queryName.concat(".goodsId")).terms(x -> x.value(v))));
//			boolQueryBuilder.must(termsQuery(queryName.concat(".goodsId"), request.getGoodsIds()));
		}
		if (CollectionUtils.isNotEmpty(request.getNotGoodsIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getNotGoodsIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.mustNot(terms(g -> g.field(queryName.concat(".goodsId")).terms(x -> x.value(v))));
//			boolQueryBuilder.mustNot(termsQuery(queryName.concat(".goodsId"), request.getNotGoodsIds()));
		}
		// 批量SKU编号
		if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getGoodsInfoIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.must(terms(g -> g.field(queryName.concat(".goodsInfoId")).terms(x -> x.value(v))));
//			boolQueryBuilder.must(termsQuery(queryName.concat(".goodsInfoId"), request.getGoodsInfoIds()));
		}
		if (CollectionUtils.isNotEmpty(request.getNotGoodsInfoIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getNotGoodsInfoIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.mustNot(terms(g -> g.field(queryName.concat(".goodsInfoId")).terms(x -> x.value(v))));
//			boolQueryBuilder.mustNot(termsQuery(queryName.concat(".goodsInfoId"), request.getNotGoodsInfoIds()));
		}

		// 单个商品分类ID
		if (null != request.getCateId()) {
//			boolQueryBuilder.must(termQuery("goodsCate.cateId", request.getCateId()));
			boolQueryBuilder.must(term(g -> g.field("goodsCate.cateId").value(request.getCateId())));
		}
		//批量商品分类ID
		if (CollectionUtils.isNotEmpty(request.getCateIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getCateIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.must(terms(g -> g.field("goodsCate.cateId").terms(x -> x.value(v))));
//			boolQueryBuilder.must(termsQuery("goodsCate.cateId", request.getCateIds()));
		}

		//店铺ID
		if (request.getStoreId() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".storeId"), request.getStoreId()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".storeId")).value(request.getStoreId())));
		}

		//批量店铺ID
		if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
			List<FieldValue> v = new ArrayList<>();
			request.getStoreIds().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.must(terms(g -> g.field(queryName.concat(".storeId")).terms(x -> x.value(v))));
//			boolQueryBuilder.must(termsQuery(queryName.concat(".storeId"), request.getStoreIds()));
		}

		//订货号模糊查询
		if (StringUtils.isNotBlank(request.getLikeQuickOrderNo())) {
//			boolQueryBuilder.must(wildcardQuery(queryName.concat(".quickOrderNo"), "*" + request.getLikeQuickOrderNo().trim() + "*"));
			boolQueryBuilder.must(wildcard(g -> g.field(queryName.concat(".quickOrderNo")).value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeQuickOrderNo().trim()))));
		}

		if (StringUtils.isNotBlank(request.getQuickOrderNo())) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".quickOrderNo"),  request.getQuickOrderNo().trim()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".quickOrderNo")).value(request.getQuickOrderNo().trim())));
		}

		// 批量SKU订货号
		if (CollectionUtils.isNotEmpty(request.getQuickOrderNos())) {
			List<FieldValue> v = new ArrayList<>();
			request.getQuickOrderNos().forEach(g -> {
				v.add(FieldValue.of(g));
			});
			boolQueryBuilder.must(terms(g -> g.field(queryName.concat(".quickOrderNo")).terms(x -> x.value(v))));
//			boolQueryBuilder.must(termsQuery(queryName.concat(".quickOrderNo"), request.getQuickOrderNos()));
		}

		//模糊查询
		if (StringUtils.isNotBlank(request.getLikeGoodsName())) {
//			boolQueryBuilder
					//.should(matchQuery(queryName.concat(".goodsInfoName"),likeGoodsName))
//					.must(matchPhraseQuery("lowGoodsName", request.getLikeGoodsName().trim()));
			boolQueryBuilder.must(matchPhrase(g -> g.field("lowGoodsName").query(request.getLikeGoodsName().trim())));
		}

		String labelVisibleField = "goodsLabelList.labelVisible";
		String labelDelFlagField = "goodsLabelList.delFlag";
		//模糊查询
		if (StringUtils.isNotBlank(request.getKeywords())) {
//			BoolQueryBuilder bq = QueryBuilders.boolQuery();
			BoolQuery.Builder bq = QueryBuilders.bool();
			List<String> chss = StringUtil.pickChs(request.getKeywords());//中文
			List<String> engs = StringUtil.pickEng(request.getKeywords());//英文、拼音
			boolean chsRale = chss.size() < engs.size();//中文优先，中文比英文内容更多
			float chsBoost = 4f; //中文权重比
			Float engBoost = 2f; //英文权重比
			//英文多于中文
			if (chsRale) {
				chsBoost = 3.2f;
				engBoost = 4f;
			}

			final float finChsBoost = chsBoost;
			final float finEngBoost = engBoost;
			//中文
			for (String keyword : chss) {
//				bq.should(matchQuery(queryName.concat(".goodsInfoName"), keyword)
//						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? chsBoost * request.getGoodsInfoNameBoost() : chsBoost));
//				bq.should(matchQuery("goodsBrand.brandName", keyword)
//						.boost(Objects.nonNull(request.getBrandNameBoost()) ? chsBoost * request.getBrandNameBoost() : chsBoost / 2));
//				bq.should(matchQuery(queryName.concat(".specText"), keyword)
//						.boost(Objects.nonNull(request.getSpecTextBoost()) ? chsBoost * request.getSpecTextBoost() : chsBoost / 4));
				bq.should(match(g -> g.field(queryName.concat(".goodsInfoName")).query(keyword).boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? finChsBoost * request.getGoodsInfoNameBoost() : finChsBoost)));
				bq.should(match(g -> g.field("goodsBrand.brandName").query(keyword).boost(Objects.nonNull(request.getBrandNameBoost()) ? finChsBoost * request.getBrandNameBoost() : finChsBoost / 2)));
				bq.should(match(g -> g.field(queryName.concat(".specText")).query(keyword).boost(Objects.nonNull(request.getSpecTextBoost()) ? finChsBoost * request.getSpecTextBoost() : finChsBoost / 4)));

				//店铺搜索不包含平台分类
				if (request.getStoreId() == null) {
//					bq.should(matchQuery("goodsCate.cateName", keyword)
//							.boost(Objects.nonNull(request.getCateNameBoost()) ? chsBoost * request.getCateNameBoost() : chsBoost / 2));
					bq.should(match(g -> g.field("goodsCate.cateName").query(keyword).boost(Objects.nonNull(request.getCateNameBoost()) ? finChsBoost * request.getCateNameBoost() : finChsBoost / 2)));
				}

				// 搜索关键字匹配商品属性
//				BoolQueryBuilder propertyBqv = QueryBuilders.boolQuery();
//				propertyBqv.must(termQuery("goodsPropRelNests.indexFlag", DefaultFlag.YES.toValue()));
				BoolQuery.Builder propertyBqv = QueryBuilders.bool();
				propertyBqv.must(term(g -> g.field("goodsPropRelNests.indexFlag").value(DefaultFlag.YES.toValue())));

//				BoolQueryBuilder propertyDetailBqv = QueryBuilders.boolQuery();
//				propertyDetailBqv.must(matchQuery("goodsPropRelNests.goodsPropDetailNest.detailName", keyword));
				BoolQuery.Builder propertyDetailBqv = QueryBuilders.bool();
				propertyDetailBqv.must(match(g -> g.field("goodsPropRelNests.goodsPropDetailNest.detailName").query(keyword)));

//				propertyBqv.must(nestedQuery("goodsPropRelNests.goodsPropDetailNest", propertyDetailBqv, ScoreMode.Total));
				propertyBqv.must(nested(g -> g.path("goodsPropRelNests.goodsPropDetailNest").query(a -> a.bool(propertyDetailBqv.build())).scoreMode(ChildScoreMode.Sum)));

//				bq.should(nestedQuery("goodsPropRelNests", propertyBqv, ScoreMode.Total)
//						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? chsBoost * request.getGoodsPropDetailNestNameBoost() : chsBoost / 8));
				bq.should(nested(g -> g.path("goodsPropRelNests").query(a -> a.bool(propertyBqv.build())).scoreMode(ChildScoreMode.Sum)))
						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? chsBoost * request.getGoodsPropDetailNestNameBoost() : chsBoost / 8);
			}

			//英文
			for (String keyword : engs) {
//				bq.should(matchPhrasePrefixQuery("lowGoodsName", keyword)
//						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? engBoost * request.getGoodsInfoNameBoost() : engBoost));
//				bq.should(matchQuery("pinyinGoodsName", keyword)
//						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? engBoost * request.getGoodsInfoNameBoost() : engBoost / 2));
//				bq.should(matchQuery("goodsBrand.pinYin", keyword)
//						.boost(Objects.nonNull(request.getBrandNameBoost()) ? engBoost * request.getBrandNameBoost() : engBoost / 2));
				bq.should(matchPhrasePrefix(g -> g.field("lowGoodsName").query(keyword)
						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? finEngBoost * request.getGoodsInfoNameBoost() : finEngBoost)));
				bq.should(match(g -> g.field("pinyinGoodsName").query(keyword)
						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? finEngBoost * request.getGoodsInfoNameBoost() : finEngBoost / 2)));
				bq.should(match(g -> g.field("goodsBrand.pinYin").query(keyword)
						.boost(Objects.nonNull(request.getBrandNameBoost()) ? finEngBoost * request.getBrandNameBoost() : finEngBoost / 2)));

				// 搜索关键字匹配商品属性拼音
//				BoolQueryBuilder propertyBqv = QueryBuilders.boolQuery();
//				propertyBqv.must(termQuery("goodsPropRelNests.indexFlag", DefaultFlag.YES.toValue()));
				BoolQuery.Builder propertyBqv = QueryBuilders.bool();
				propertyBqv.must(term(g -> g.field("goodsPropRelNests.indexFlag").value(DefaultFlag.YES.toValue())));

//				BoolQueryBuilder propertyDetailBqv = QueryBuilders.boolQuery();
//				propertyDetailBqv.must(matchQuery("goodsPropRelNests.goodsPropDetailNest.detailPinYin", keyword));
				BoolQuery.Builder propertyDetailBqv = QueryBuilders.bool();
				propertyDetailBqv.must(match(g -> g.field("goodsPropRelNests.goodsPropDetailNest.detailPinYin").query(keyword)));

//				propertyBqv.must(nestedQuery("goodsPropRelNests.goodsPropDetailNest", propertyDetailBqv, ScoreMode.Total));
				propertyBqv.must(nested(g -> g.path("goodsPropRelNests.goodsPropDetailNest").query(a -> a.bool(propertyDetailBqv.build())).scoreMode(ChildScoreMode.Sum)));

//				bq.should(nestedQuery("goodsPropRelNests", propertyBqv, ScoreMode.Total)
//						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? engBoost * request.getGoodsPropDetailNestNameBoost() : engBoost / 8));
				bq.should(nested(g -> g.path("goodsPropRelNests").query(a -> a.bool(propertyBqv.build())).scoreMode(ChildScoreMode.Sum)))
						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? engBoost * request.getGoodsPropDetailNestNameBoost() : engBoost / 8);
			}

			//标签，标签最优先
//			BoolQueryBuilder labelBqv = QueryBuilders.boolQuery();
			BoolQuery.Builder labelBqv = QueryBuilders.bool();
//			labelBqv.must(matchQuery("goodsLabelList.labelName", request.getKeywords()));//靠前
			labelBqv.must(match(g -> g.field("goodsLabelList.labelName").query(request.getKeywords())));
//			labelBqv.must(termQuery(labelVisibleField, Boolean.TRUE).boost(0));
			labelBqv.must(term(g -> g.field(labelVisibleField).value(Boolean.TRUE).boost(0f)));
//			labelBqv.must(termQuery(labelDelFlagField, DeleteFlag.NO.toValue()).boost(0));
			labelBqv.must(term(g -> g.field(labelDelFlagField).value(DeleteFlag.NO.toValue()).boost(0f)));
//			bq.should(nestedQuery("goodsLabelList", labelBqv, ScoreMode.Total)
//					.boost(Objects.nonNull(request.getGoodsLabelNameBoost()) ? chsBoost * request.getGoodsLabelNameBoost() : chsBoost * 5));
			bq.should(nested(g -> g.path("goodsLabelList").query(a -> a.bool(labelBqv.build())).scoreMode(ChildScoreMode.Sum)))
					.boost(Objects.nonNull(request.getGoodsLabelNameBoost()) ? chsBoost * request.getGoodsLabelNameBoost() : chsBoost * 5);
//			bq.should(matchQuery(queryName.concat(".goodsInfoName"), request.getKeywords())
//					.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? chsBoost * request.getGoodsInfoNameBoost() : 2));
			bq.should(match(g -> g.field(queryName.concat(".goodsInfoName")).query(request.getKeywords())
					.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? finChsBoost * request.getGoodsInfoNameBoost() : 2)));
//			bq.should(matchQuery("goodsSubtitle", request.getKeywords())
//					.boost(Objects.nonNull(request.getGoodsSubtitleBoost()) ? chsBoost * request.getGoodsSubtitleBoost() : chsBoost));
			bq.should(match(g -> g.field("goodsSubtitle").query(request.getKeywords())
					.boost(Objects.nonNull(request.getGoodsSubtitleBoost()) ? finChsBoost * request.getGoodsSubtitleBoost() : finChsBoost)));

			if (CollectionUtils.isEmpty(chss)
					&& CollectionUtils.isEmpty(engs)
					&& StringUtils.isNotBlank(request.getKeywords())) {
				// 搜索关键字匹配商品属性
//				BoolQueryBuilder propertyBqv = QueryBuilders.boolQuery();
//				propertyBqv.must(termQuery("goodsPropRelNests.indexFlag", DefaultFlag.YES.toValue()));
				BoolQuery.Builder propertyBqv = QueryBuilders.bool();
				propertyBqv.must(term(g -> g.field("goodsPropRelNests.indexFlag").value(DefaultFlag.YES.toValue())));
//				BoolQueryBuilder propertyDetailBqv = QueryBuilders.boolQuery();
//				propertyDetailBqv.must(matchQuery("goodsPropRelNests.goodsPropDetailNest.detailName", request.getKeywords()));
				BoolQuery.Builder propertyDetailBqv = QueryBuilders.bool();
				propertyDetailBqv.must(match(g -> g.field("goodsPropRelNests.goodsPropDetailNest.detailName").query(request.getKeywords())));
//				propertyBqv.must(nestedQuery("goodsPropRelNests.goodsPropDetailNest", propertyDetailBqv, ScoreMode.Total));
				propertyBqv.must(nested(g -> g.path("goodsPropRelNests.goodsPropDetailNest").query(a -> a.bool(propertyDetailBqv.build())).scoreMode(ChildScoreMode.Sum)));
//				bq.should(nestedQuery("goodsPropRelNests", propertyBqv, ScoreMode.Total)
//						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? request.getGoodsPropDetailNestNameBoost() : chsBoost / 8));
				bq.should(nested(g -> g.path("goodsPropRelNests").query(a -> a.bool(propertyBqv.build())).scoreMode(ChildScoreMode.Sum)))
						.boost(Objects.nonNull(request.getGoodsPropDetailNestNameBoost()) ? request.getGoodsPropDetailNestNameBoost() : finChsBoost / 8);

//				bq.should(matchPhraseQuery("pinyinGoodsName", request.getKeywords())
//						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? chsBoost * request.getGoodsInfoNameBoost() : 2));
				bq.should(matchPhrase(g -> g.field("pinyinGoodsName").query(request.getKeywords())
						.boost(Objects.nonNull(request.getGoodsInfoNameBoost()) ? finChsBoost * request.getGoodsInfoNameBoost() : 2)));
			}

//			boolQueryBuilder.must(bq);
			boolQueryBuilder.must(a -> a.bool(bq.build()));
		}

		// 批量商品属性、属性值ID查询
		if (CollectionUtils.isNotEmpty(request.getPropDetails())) {
			// 循环拼接查询条件
			for (EsPropQueryRequest requestProp : request.getPropDetails()) {
//				BoolQueryBuilder propertyBqv = QueryBuilders.boolQuery();
				BoolQuery.Builder propertyBqv = QueryBuilders.bool();

//				BoolQueryBuilder propertyChildBqv = QueryBuilders.boolQuery();
				BoolQuery.Builder propertyChildBqv = QueryBuilders.bool();

//				propertyChildBqv.must(termQuery("goodsPropRelNests.indexFlag", DefaultFlag.YES.toValue()));
				propertyChildBqv.must(term(g -> g.field("goodsPropRelNests.indexFlag").value(DefaultFlag.YES.toValue())));

//				propertyChildBqv.must(termQuery("goodsPropRelNests.propId", requestProp.getPropId()));
				if (requestProp.getPropId()!=null){
					propertyChildBqv.must(term(g -> g.field("goodsPropRelNests.propId").value(requestProp.getPropId())));
				}
				if (requestProp.getPropName()!=null){
					propertyChildBqv.must(term(g -> g.field("goodsPropRelNests.propName").value(requestProp.getPropName())));
				}

//				BoolQueryBuilder propertyDetailBqv = QueryBuilders.boolQuery();
				BoolQuery.Builder propertyDetailBqv = QueryBuilders.bool();
//				propertyDetailBqv.must(termsQuery("goodsPropRelNests.goodsPropDetailNest.detailId", requestProp.getDetailIds()));

				propertyDetailBqv.must(terms(g -> g.field("goodsPropRelNests.goodsPropDetailNest.detailId")
						.terms(x -> x.value(requestProp.getDetailIds().stream().map(FieldValue::of).collect(Collectors.toList())))));

//				propertyChildBqv.must(nestedQuery("goodsPropRelNests.goodsPropDetailNest", propertyDetailBqv, ScoreMode.None));
				propertyChildBqv.must(nested(g -> g.path("goodsPropRelNests.goodsPropDetailNest").query(a -> a.bool(propertyDetailBqv.build())).scoreMode(ChildScoreMode.None)));

//				propertyBqv.must(propertyChildBqv);
				propertyBqv.must(a -> a.bool(propertyChildBqv.build()));

//				boolQueryBuilder.must(nestedQuery("goodsPropRelNests", propertyBqv, ScoreMode.None));
				boolQueryBuilder.must(nested(g -> g.path("goodsPropRelNests").query(a -> a.bool(propertyBqv.build())).scoreMode(ChildScoreMode.None)));
			}

		}

		//批量商品分类ID
		if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
//			boolQueryBuilder.must(termsQuery("goodsBrand.brandId", request.getBrandIds()));
			boolQueryBuilder.must(terms(g -> g.field("goodsBrand.brandId")
					.terms(x -> x.value(request.getBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
		}

		//批量标签ID
		if (CollectionUtils.isNotEmpty(request.getLabelIds())) {
//			BoolQueryBuilder labelBq = QueryBuilders.boolQuery();
			BoolQuery.Builder labelBq = QueryBuilders.bool();

//			labelBq.must(termsQuery("goodsLabelList.goodsLabelId", request.getLabelIds()));
			labelBq.must(terms(g -> g.field("goodsLabelList.goodsLabelId")
					.terms(x -> x.value(request.getLabelIds().stream().map(FieldValue::of).collect(Collectors.toList())))));

//			labelBq.must(termQuery(labelVisibleField, Boolean.TRUE));
			labelBq.must(term(g -> g.field(labelVisibleField).value(Boolean.TRUE)));

//			labelBq.must(termQuery(labelDelFlagField, DeleteFlag.NO.toValue()));
			labelBq.must(term(g -> g.field(labelDelFlagField).value(DeleteFlag.NO.toValue())));

//			boolQueryBuilder.must(nestedQuery("goodsLabelList", labelBq, ScoreMode.None));
			boolQueryBuilder.must(nested(g -> g.path("goodsLabelList").query(a -> a.bool(labelBq.build())).scoreMode(ChildScoreMode.None)));
		}

		//批量商品分类ID
		if (request.getCompanyType() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".companyType"), request.getCompanyType().intValue()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".companyType")).value(request.getCompanyType().intValue())));
		}

		//库存状态
		if (request.getStockFlag() != null) {
			String tempName = request.isQueryGoods() ? "stock" : "goodsInfo.stock";
			if (Constants.yes.equals(request.getIsOutOfStockShow())) {
//				boolQueryBuilder.must(rangeQuery(tempName).gt(0));
				boolQueryBuilder.must(range(g -> g.field(tempName).gt(JsonData.of(0))));
				if (request.isQueryGoods()) {
//					boolQueryBuilder.must(rangeQuery("goodsInfos.stock").gt(0));
					boolQueryBuilder.must(range(g -> g.field("goodsInfos.stock").gt(JsonData.of(0))));
				}
			} else {
				if (Constants.yes.equals(request.getStockFlag())) {
//					boolQueryBuilder.must(rangeQuery(tempName).gt(0));
					boolQueryBuilder.must(range(g -> g.field(tempName).gt(JsonData.of(0))));
				} else {
//					boolQueryBuilder.must(rangeQuery(tempName).lte(0));
					boolQueryBuilder.must(range(g -> g.field(tempName).lte(JsonData.of(0))));
				}
			}
		}
		//删除标记
		if (request.getDelFlag() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".delFlag"), request.getDelFlag()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".delFlag")).value(request.getDelFlag())));
		}
		//上下架状态
		if (request.getAddedFlag() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".addedFlag"), request.getAddedFlag()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".addedFlag")).value(request.getAddedFlag())));
		}
		//可售状态
		if (request.getVendibility() != null) {
//			boolQueryBuilder.must(termQuery("vendibilityStatus", request.getVendibility()));
			boolQueryBuilder.must(term(g -> g.field("vendibilityStatus").value(request.getVendibility())));
		}

		//是否参与周期购
		if (Objects.nonNull(request.getIsBuyCycle())) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".isBuyCycle"), request.getIsBuyCycle()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".isBuyCycle")).value(request.getIsBuyCycle())));
		}

		//批量店铺分类ID
		if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
//			boolQueryBuilder.must(termsQuery("storeCateIds", request.getStoreCateIds()));
			boolQueryBuilder.must(terms(g -> g.field("storeCateIds")
					.terms(x -> x.value(request.getStoreCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
		}

		//店铺状态
		if (request.getStoreState() != null) {
//			boolQueryBuilder.must(termQuery("storeState", request.getStoreState()));
			boolQueryBuilder.must(term(g -> g.field("storeState").value(request.getStoreState())));
		}

		//禁售状态
		if (request.getForbidStatus() != null) {
//			boolQueryBuilder.must(termQuery("forbidStatus", request.getForbidStatus()));
			boolQueryBuilder.must(term(g -> g.field("forbidStatus").value(request.getForbidStatus())));
		}

		//审核状态
		if (request.getAuditStatus() != null) {
//			boolQueryBuilder.must(termQuery("auditStatus", request.getAuditStatus()));
			boolQueryBuilder.must(term(g -> g.field("auditStatus").value(request.getAuditStatus())));
		}

		//可抵扣积分的，非企业价
		if (Boolean.TRUE.equals(request.getPointsUsageFlag())) {
			String tempName = request.isQueryGoods() ? "buyPoint" : "goodsInfo.buyPoint";
//			boolQueryBuilder.must(rangeQuery(tempName).gt(0L));
			boolQueryBuilder.must(range(g -> g.field(tempName).gt(JsonData.of(0L))));
//			boolQueryBuilder.mustNot(termQuery(queryName.concat(".enterPriseAuditStatus"), EnterpriseAuditState.CHECKED.toValue()));
			boolQueryBuilder.mustNot(term(g -> g.field(queryName.concat(".enterPriseAuditStatus")).value(EnterpriseAuditState.CHECKED.toValue())));
		}

		if (NumberUtils.INTEGER_ZERO.equals(request.getDistributionGoodsStatus())) {
			//不可抵扣积分
			if (Boolean.FALSE.equals(request.getPointsUsageFlag())) {
				String tempName = request.isQueryGoods() ? "buyPoint" : "goodsInfo.buyPoint";
//				boolQueryBuilder.must(termQuery(tempName, 0L));
				boolQueryBuilder.must(term(g -> g.field(tempName).value(0L)));
			}
		}

		//商品来源
		if (request.getGoodsSource() != null) {
//			boolQueryBuilder.must(termQuery("goodsSource", request.getGoodsSource().toValue()));
			boolQueryBuilder.must(term(g -> g.field("goodsSource").value(request.getGoodsSource().toValue())));
		}

		/**
		 * 签约开始日期
		 */
		if (StringUtils.isNotBlank(request.getContractStartDate())) {
//			boolQueryBuilder.must(rangeQuery("contractStartDate").lte(request.getContractStartDate()));
			boolQueryBuilder.must(range(g -> g.field("contractStartDate").lte(JsonData.of(request.getContractStartDate()))));
		}

		/**
		 * 签约结束日期
		 */
		if (StringUtils.isNotBlank(request.getContractEndDate())) {
//			boolQueryBuilder.must(rangeQuery("contractEndDate").gte(request.getContractEndDate()));
			boolQueryBuilder.must(range(g -> g.field("contractEndDate").gte(JsonData.of(request.getContractEndDate()))));
		}

		/**
		 * 销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
		 */
		if (request.getDistributionGoodsAudit() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".distributionGoodsAudit"), String.valueOf(request.getDistributionGoodsAudit())));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".distributionGoodsAudit")).value(request.getDistributionGoodsAudit())));
		}
		/**
		 * 排除分销商品:排除(店铺分销开关开且商品审核通过)
		 */
		if (request.isExcludeDistributionGoods()) {
//			BoolQueryBuilder bqv = QueryBuilders.boolQuery();
			BoolQuery.Builder bqv = QueryBuilders.bool();
			// 某个属性prodId=1 && detailId in (1,2)
//			bqv.must(termQuery(queryName.concat(".distributionGoodsAudit"), DistributionGoodsAudit.CHECKED.toValue()));
			bqv.must(term(g -> g.field(queryName.concat(".distributionGoodsAudit")).value(DistributionGoodsAudit.CHECKED.toValue())));
//			bqv.must(termQuery("distributionGoodsStatus", DefaultFlag.NO.toValue()));
			bqv.must(term(g -> g.field("distributionGoodsStatus").value(DefaultFlag.NO.toValue())));
//			boolQueryBuilder.mustNot(bqv);
			boolQueryBuilder.mustNot(g -> g.bool(bqv.build()));
		}

		if (request.getDistributionGoodsStatus() != null) {
//			boolQueryBuilder.must(termQuery("distributionGoodsStatus", request.getDistributionGoodsStatus()));
			boolQueryBuilder.must(term(g -> g.field("distributionGoodsStatus").value(request.getDistributionGoodsStatus())));
		}

		//企业购商品过滤-企业购且非分销、非积分价
		if (request.getEnterPriseGoodsStatus() != null) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".enterPriseAuditStatus"), request.getEnterPriseGoodsStatus()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".enterPriseAuditStatus")).value(request.getEnterPriseGoodsStatus())));
			String tempName = request.isQueryGoods() ? "buyPoint" : "goodsInfo.buyPoint";
//			boolQueryBuilder.must(termQuery(tempName, 0));
			boolQueryBuilder.must(term(g -> g.field(tempName).value(0)));
			if (Objects.equals(EnterpriseAuditState.CHECKED.toValue(), request.getEnterPriseGoodsStatus())) {
//				BoolQueryBuilder bqv = QueryBuilders.boolQuery();
				BoolQuery.Builder bqv = QueryBuilders.bool();
				// 某个属性prodId=1 && detailId in (1,2)
//				bqv.must(termQuery(queryName.concat(".distributionGoodsAudit"), DistributionGoodsAudit.CHECKED.toValue()));
				bqv.must(term(g -> g.field(queryName.concat(".distributionGoodsAudit")).value(DistributionGoodsAudit.CHECKED.toValue())));
//				bqv.must(termQuery("distributionGoodsStatus", DefaultFlag.NO.toValue()));
				bqv.must(term(g -> g.field("distributionGoodsStatus").value(DefaultFlag.NO.toValue())));
//				boolQueryBuilder.mustNot(bqv);
				boolQueryBuilder.mustNot(g -> g.bool(bqv.build()));
			}
		}

		if (request.isHideSelectedDistributionGoods() && CollectionUtils.isNotEmpty(request.getDistributionGoodsInfoIds())) {
//			boolQueryBuilder.mustNot(termsQuery(queryName.concat(".goodsInfoId"), request.getDistributionGoodsInfoIds()));
			boolQueryBuilder.must(terms(g -> g.field(queryName.concat(".goodsInfoId"))
					.terms(x -> x.value(request.getDistributionGoodsInfoIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
		}

		//不显示linkedMall商品
		if (Boolean.TRUE.equals(request.getNotShowLinkedMallFlag())) {
			String tempName = request.isQueryGoods() ? "thirdPlatformType" : "goodsInfo.thirdPlatformType";
//			boolQueryBuilder.mustNot(termQuery(tempName, ThirdPlatformType.LINKED_MALL.toValue()));
			boolQueryBuilder.mustNot(term(g -> g.field(tempName).value(ThirdPlatformType.LINKED_MALL.toValue())));
		}

		//不显示VOP商品
		if (Boolean.TRUE.equals(request.getNotShowVOPFlag())) {
			String tempName = request.isQueryGoods() ? "thirdPlatformType" : "goodsInfo.thirdPlatformType";
//			boolQueryBuilder.mustNot(termQuery(tempName, ThirdPlatformType.VOP.toValue()));
			boolQueryBuilder.mustNot(term(g -> g.field(tempName).value(ThirdPlatformType.VOP.toValue())));
		}


		if (Objects.nonNull(request.getMinMarketPrice())) {
			request.setSalePriceLow(request.getMinMarketPrice());
		}

		if (Objects.nonNull(request.getMaxMarketPrice())) {
			request.setSalePriceHigh(request.getMaxMarketPrice());
		}

		if (Objects.nonNull(request.getLinePrice())){
//			boolQueryBuilder.must(termQuery("linePrice", request.getLinePrice()));
			boolQueryBuilder.must(term(g -> g.field("linePrice").value(FieldValue.of(request.getLinePrice().doubleValue()))));
		}

		// 价格区间范围
		if (Objects.nonNull(request.getSalePriceLow()) && Objects.nonNull(request.getSalePriceHigh())) {
//			boolQueryBuilder.must(rangeQuery(queryName.concat(".marketPrice")).from(request.getSalePriceLow().doubleValue()).to(request.getSalePriceHigh().doubleValue()));
			boolQueryBuilder.must(range(g -> g.field(queryName.concat(".marketPrice")).gte(JsonData.of(request.getSalePriceLow().doubleValue())).lte(JsonData.of(request.getSalePriceHigh().doubleValue()))));
		} else if (Objects.nonNull(request.getSalePriceLow())) {
			// 价格区间范围 - 大于等于 最低价
//			boolQueryBuilder.must(rangeQuery(queryName.concat(".marketPrice")).gte(request.getSalePriceLow().doubleValue()));
			boolQueryBuilder.must(range(g -> g.field(queryName.concat(".marketPrice")).gte(JsonData.of(request.getSalePriceLow().doubleValue()))));
		} else if (Objects.nonNull(request.getSalePriceHigh())) {
			// 价格区间范围 - 小于等于 最高价
//			boolQueryBuilder.must(rangeQuery(queryName.concat(".marketPrice")).lte(request.getSalePriceHigh().doubleValue()));
			boolQueryBuilder.must(range(g -> g.field(queryName.concat(".marketPrice")).lte(JsonData.of(request.getSalePriceHigh().doubleValue()))));
		}

		//跨境商品
		if (Objects.nonNull(request.getPluginType())) {
//			boolQueryBuilder.must(termQuery(queryName.concat(".pluginType"), request.getPluginType()));
			boolQueryBuilder.must(term(g -> g.field(queryName.concat(".pluginType")).value(request.getPluginType())));
		}

		// 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
		if (request.getGoodsType() != null) {
//			boolQueryBuilder.must(termQuery("goodsType", request.getGoodsType()));
			boolQueryBuilder.must(term(g -> g.field("goodsType").value(request.getGoodsType())));
		}

		// 运费模板Id
		if (Objects.nonNull(request.getFreightTemplateId())) {
			String tempName = request.isQueryGoods() ? "freightTempId" : "goodsInfo.freightTempId";
//			boolQueryBuilder.must(termQuery(tempName, request.getFreightTemplateId()));
			boolQueryBuilder.must(term(g -> g.field(tempName).value(request.getFreightTemplateId())));
		}

		//不返回代销商品
		if(Objects.nonNull(request.getNotShowProvideFlag()) && request.getNotShowProvideFlag()) {
			String tempName = request.isQueryGoods() ? "providerId" : "goodsInfo.providerId";
//			boolQueryBuilder.mustNot(QueryBuilders.existsQuery(tempName));
			boolQueryBuilder.mustNot(exists(g -> g.field(tempName)));
		}

		//供应商Id
		if(Objects.nonNull(request.getProviderStoreId())) {
			String tempName = request.isQueryGoods() ? "providerId" : "goodsInfo.providerId";
//			boolQueryBuilder.must(termQuery(tempName, request.getProviderStoreId()));
			boolQueryBuilder.must(term(g -> g.field(tempName).value(request.getProviderStoreId())));
		}

//        System.out.println(String.format("ES商口查询条件->%s", boolQueryBuilder.toString()));
//		return boolQueryBuilder;
		return boolQueryBuilder.build()._toQuery();
	}

	public void setSort(EsGoodsInfoQueryRequest queryRequest) {
		final String doubleType = "Double";
		final String longType = "Long";
		final String dateType = "Date";
		final String integerType = "Integer";
		//有关键词，但没有排序的情况下，用匹配积分做排
		if (StringUtils.isNotBlank(queryRequest.getKeywords())
				&& queryRequest.getSortFlag() == null) {
			this.putScoreSort();
		}
		if (queryRequest.getSortFlag() == null) {
			return;
		}
		switch (queryRequest.getSortFlag()) {
			case 0:
				if (queryRequest.isQueryGoods()) {
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("addedTime", SortOrder.Desc, dateType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("addedTime", SortOrder.Desc, dateType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 1:
				if (queryRequest.isQueryGoods()) {
					this.putSort("addedTime", SortOrder.Desc, dateType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("addedTime", SortOrder.Desc, dateType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 2:
				if (queryRequest.isQueryGoods()) {
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
				} else {
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
				}

				break;
			case 3:
				if (queryRequest.isQueryGoods()) {
					this.putSort("esSortPrice", SortOrder.Asc, doubleType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
				} else {
					this.putSort("goodsInfo.esSortPrice", SortOrder.Asc, doubleType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
				}
				break;
			case 4:
				if (queryRequest.isQueryGoods()) {
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 5:
				if (queryRequest.isQueryGoods()) {
					this.putSort("goodsEvaluateNum", SortOrder.Desc, longType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("goodsInfo.goodsEvaluateNum", SortOrder.Desc, integerType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 6:
				if (queryRequest.isQueryGoods()) {
					this.putSort("goodsFeedbackRate", SortOrder.Desc, longType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("goodsInfo.goodsFeedbackRate", SortOrder.Desc, longType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 7:
				if (queryRequest.isQueryGoods()) {
					this.putSort("goodsCollectNum", SortOrder.Desc, longType);
					this.putSort("goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("esSortPrice", SortOrder.Desc, doubleType);
				} else {
					this.putSort("goodsInfo.goodsCollectNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.goodsSalesNum", SortOrder.Desc, longType);
					this.putSort("goodsInfo.esSortPrice", SortOrder.Desc, doubleType);
				}
				break;
			case 8:
				// 按照佣金降序排序
				this.putSort("goodsInfo.distributionCommission", SortOrder.Desc, doubleType);
				this.putSort("addedTime", SortOrder.Desc, dateType);

				break;
			case 9:
				// 按照佣金升序排序
				this.putSort("goodsInfo.distributionCommission", SortOrder.Asc, doubleType);
				this.putSort("addedTime", SortOrder.Desc, dateType);

				break;
			case 10:
				// 按照排序号倒序->上架时间倒序
				this.putSort("sortNo", SortOrder.Desc, longType);
				this.putSort("addedTime", SortOrder.Desc, dateType);
				break;
			default:
				break;
		}
	}

	/**
	 * 填放排序参数
	 *
	 * @param fieldName 字段名
	 * @param sort      排序
	 */
	public void putSort(String fieldName, SortOrder sort) {
		FieldSort.Builder fieldSortBuilder = new FieldSort.Builder();
		fieldSortBuilder.field(fieldName);
		fieldSortBuilder.order(sort);
		this.putSort(fieldSortBuilder);
	}

	/**
	 * 填放排序参数
	 *
	 * @param fieldName 字段名
	 * @param sort      排序
	 * @param unmappedType 容错不存在的字段
	 */
	public void putSort(String fieldName, SortOrder sort, String unmappedType) {
		FieldSort.Builder fieldSortBuilder = new FieldSort.Builder();
		fieldSortBuilder.field(fieldName);
		fieldSortBuilder.order(sort);
		fieldSortBuilder.unmappedType(FieldType.valueOf(unmappedType));
		this.putSort(fieldSortBuilder);
	}

	/**
	 * 按_score降序排列
	 */
	public void putScoreSort() {
		SortOptions.Builder sortOptions =  new SortOptions.Builder();
		ScoreSort.Builder scoreSortBuilder = new ScoreSort.Builder();
		scoreSortBuilder.order(SortOrder.Desc);
		sortOptions.score(scoreSortBuilder.build());
		sorts.add(sortOptions.build());
	}

	/**
	 * 填放排序参数
	 *
	 * @param builder
	 */
	public void putSort(FieldSort.Builder builder) {
		SortOptions.Builder sortOptions =  new SortOptions.Builder();
		sortOptions.field(builder.build());
		sorts.add(sortOptions.build());
	}

	/**
	 * 填放聚合参数
	 *
	 * @param builder
	 */
	public void putAgg(String key, Aggregation builder) {
		aggMap.put(key, builder);
	}
}
