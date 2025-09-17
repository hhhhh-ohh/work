-- phoenix映射HBase 表的视图
create view if not exists RECOMMEND.USER_RECOMMEND_ACTION_DATA(ROWKEY varchar  primary key,
CF.RECOMMENDTYPE varchar,
CF.ITEM varchar,
CF.LOCATION varchar,
CF.GOODSID varchar,
CF.CATEID varchar,
CF.CUSTOMERID varchar,
CF.EVENTTYPE varchar,
CF.CREATETIME varchar
)SALT_BUCKETS=32;
--删除视图
create index IF NOT EXISTS USER_RECOMMEND_ACTION_DATA_INDEX on RECOMMEND.USER_RECOMMEND_ACTION_DATA(ITEM) include(CREATETIME,CUSTOMERID);

--热门商品推荐结果phoenix建表 每天覆盖
CREATE TABLE IF NOT EXISTS TEST.HOT_GOODS_RECOMMEND (
"customer_id" varchar not null,
"goods_id" varchar,
"type" varchar,
CF."num" double,
CF."weight" double,
CF."create_time" varchar,
CF."create_person" varchar
CONSTRAINT pk PRIMARY KEY ("customer_id", "goods_id","type" )
)IMMUTABLE_ROWS=true, DATA_BLOCK_ENCODING='NONE',VERSIONS=1,MAX_FILESIZE=20000000 split on ('0', '9', 'a','z'),SALT_BUCKETS=32;
create index IF NOT EXISTS HOT_RECOMMEND_INDEX on RECOMMEND.HOT_GOODS_RECOMMEND("goods_id","customer_id","type") include("num","weight");

--热门类目推荐结果phoenix建表 每天覆盖
CREATE TABLE IF NOT EXISTS RECOMMEND.HOT_CATE_RECOMMEND ("customer_id" varchar not null,
"cate_id" bigint  not null,
CF."num" double,
CF."weight" double,
CF."create_time" varchar
CONSTRAINT pk PRIMARY KEY ("customer_id", "cate_id")
)IMMUTABLE_ROWS=true, DATA_BLOCK_ENCODING='NONE',VERSIONS=1,MAX_FILESIZE=20000000 split on ('0', '9', 'a','z');
create index IF NOT EXISTS HOT_CATE_RECOMMEND_INDEX on RECOMMEND.HOT_CATE_RECOMMEND("cate_id","customer_id") include("num","weight");

--记录热门推荐 用户看到哪个位置的row_number，phoenix表 每天覆盖
CREATE TABLE IF NOT EXISTS  RECOMMEND.USER_VIEW_LOCATION_RECORD (
    "customer_id" varchar not null,
    "recommendType" Integer not null,
    "item" Integer  not null ,
    "manualRecommendStatus" Integer  not null ,
    "viewGoods" varchar  not null ,
    "viewCate" BIGINT not null,
    CF."row_number" Integer,
    CF."create_time" varchar,
    CONSTRAINT pk PRIMARY KEY ("customer_id", "recommendType","item","manualRecommendStatus","viewGoods","viewCate")
)IMMUTABLE_ROWS=true, DATA_BLOCK_ENCODING='NONE',VERSIONS=1,MAX_FILESIZE=20000000 split on ('0', '9', 'a','z');

--相关性商品推荐phoenix表
CREATE TABLE IF NOT EXISTS  RECOMMEND.GOOD_RELATION_RECOMMEND ("customer_id" varchar not null,
    "goods_id" varchar,
    "related_goods_id" varchar ,
    CF."lift" DOUBLE,
    CF."weight" DOUBLE,
    CF."type" integer,
    CF."create_time" varchar,
    CF."create_person" varchar,
    CONSTRAINT pk PRIMARY KEY ("customer_id", "goods_id","related_goods_id")
    )IMMUTABLE_ROWS=true, DATA_BLOCK_ENCODING='NONE',VERSIONS=1,MAX_FILESIZE=20000000 split on ('0', '9', 'a','z');
create  index  IF NOT EXISTS GOOD_RELATION_RECOMMEND_INDEX  on  RECOMMEND.GOOD_RELATION_RECOMMEND("goods_id","customer_id") include("related_goods_id","lift","weight");
--相关性类目推荐phoenix表
CREATE TABLE IF NOT EXISTS  RECOMMEND.CATE_RELATION_RECOMMEND ("customer_id" varchar not null,
  "cate_id" BIGINT not null,
  "related_cate_id" BIGINT not null ,
  CF."lift" DOUBLE,
  CF."weight" DOUBLE,
  CF."type" integer,
  CF."create_time" varchar,
  CF."create_person" varchar,
  CONSTRAINT pk PRIMARY KEY ("customer_id", "cate_id","related_cate_id")
    )IMMUTABLE_ROWS=true, DATA_BLOCK_ENCODING='NONE',VERSIONS=1,MAX_FILESIZE=20000000 split on ('0', '9', 'a','z');
create  index  IF NOT EXISTS CATE_RELATION_RECOMMEND_INDEX  on  RECOMMEND.CATE_RELATION_RECOMMEND("cate_id","customer_id") include("related_cate_id","lift","weight");

