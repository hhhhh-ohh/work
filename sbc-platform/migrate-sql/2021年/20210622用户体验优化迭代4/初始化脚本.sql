ALTER TABLE `sbc-account`.`reconciliation` ADD COLUMN points_price decimal(20, 2) COMMENT '积分抵现金额' AFTER points;

ALTER TABLE `sbc-account`.`settlement` ADD COLUMN points bigint(20) COMMENT '积分数量' AFTER point_price;

ALTER TABLE `s2b_statistics`.`goods_day` ADD COLUMN PV decimal(20, 0) COMMENT '浏览量' AFTER UV;
ALTER TABLE `s2b_statistics`.`goods_recent_seven` ADD COLUMN PV decimal(20, 0) COMMENT '浏览量' AFTER UV;
ALTER TABLE `s2b_statistics`.`goods_recent_thirty` ADD COLUMN PV decimal(20, 0) COMMENT '浏览量' AFTER UV;
ALTER TABLE `s2b_statistics`.`goods_month` ADD COLUMN PV decimal(20, 0) COMMENT '浏览量' AFTER UV;


-- mongodb执行脚本
-- local库
db.bus_bar_infos.insert({
	"_id" : ObjectId("60bf18c3b3c42626e6b9984e"),
	"envCode" : "test1",
	"key" : "@wanmi/wechat-coupon-01",
	"platform" : "weixin",
	"systemCode" : "d2cStore",
	"__v" : 0,
	"addible" : true,
	"createdAt" : ISODate("2021-06-08T15:14:14.739+08:00"),
	"deletable" : true,
	"deleted" : false,
	"dependencies" : [
		"@wanmi/wechat-coupon-01"
	],
	"img" : "",
	"isAdvanced" : true,
	"isCommon" : true,
	"packageName" : "@wanmi/wechat-coupon-01",
	"props" : {
		"props" : {
			"showType" : {
				"itemStyle" : 2,
				"showStyle" : 1,
				"cartButton" : 1,
				"goodsSetup" : {
					"name" : true,
					"scope" : true,
					"expiryDate" : true,
					"button" : true,
					"horn" : false,
					"hornStyle" : 1,
					"customHorn" : ""
				}
			},
			"bgColor" : "#f7f7f7",
			"themeColor" : "#FF6600",
			"textColor" : "rgba(0, 0, 0, 0.8)",
			"couponBgColor" : "#fff",
			"selectedSource" : 4,
			"sortSource" : 6,
			"sources" : [
				{
					"type" : "1",
					"size" : 20
				},
				{
					"type" : "2",
					"size" : 20
				},
				{
					"type" : "3",
					"size" : 20
				},
				{
					"type" : "4"
				}
			],
			"items" : [
				{
					"couponId" : "ff808081799bf7d70172916150000",
					"activityId" : "ff808081799bf7d70172916150000",
					"couponName" : "优惠券名称1",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150001",
					"activityId" : "ff808081799bf7d70172916150001",
					"couponName" : "优惠券名称2",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150002",
					"activityId" : "ff808081799bf7d70172916150002",
					"couponName" : "优惠券名称3",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150003",
					"activityId" : "ff808081799bf7d70172916150003",
					"couponName" : "优惠券名称4",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				}
			],
			"version" : "0.0.39"
		},
		"widgetNameSpace" : "@wanmi/wechat-coupon-01"
	},
	"replicable" : true,
	"title" : "优惠券列表",
	"type" : "COMMON",
	"updatedAt" : ISODate("2021-06-21T10:16:56.109+08:00"),
	"version" : "0.0.39",
	"cateId" : "5add9b42e15b9a13a2a3bd26",
	"sortIndex" : 8
});

db.common_bar_infos.insert({
	"_id" : ObjectId("60bf18b0b3c42626e6b99816"),
	"envCode" : "test1",
	"key" : "@wanmi/wechat-coupon-01",
	"platform" : "weixin",
	"systemCode" : "d2cStore",
	"__v" : 0,
	"addible" : true,
	"createdAt" : ISODate("2021-06-08T15:14:14.739+08:00"),
	"deletable" : true,
	"deleted" : false,
	"dependencies" : [
		"@wanmi/wechat-coupon-01"
	],
	"img" : "",
	"isAdvanced" : true,
	"isCommon" : true,
	"packageName" : "@wanmi/wechat-coupon-01",
	"props" : {
		"props" : {
			"showType" : {
				"itemStyle" : 2,
				"showStyle" : 1,
				"cartButton" : 1,
				"goodsSetup" : {
					"name" : true,
					"scope" : true,
					"expiryDate" : true,
					"button" : true,
					"horn" : false,
					"hornStyle" : 1,
					"customHorn" : ""
				}
			},
			"bgColor" : "#f7f7f7",
			"themeColor" : "#FF6600",
			"textColor" : "rgba(0, 0, 0, 0.8)",
			"couponBgColor" : "#fff",
			"selectedSource" : 4,
			"sortSource" : 6,
			"sources" : [
				{
					"type" : "1",
					"size" : 20
				},
				{
					"type" : "2",
					"size" : 20
				},
				{
					"type" : "3",
					"size" : 20
				},
				{
					"type" : "4"
				}
			],
			"items" : [
				{
					"couponId" : "ff808081799bf7d70172916150000",
					"activityId" : "ff808081799bf7d70172916150000",
					"couponName" : "优惠券名称1",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150001",
					"activityId" : "ff808081799bf7d70172916150001",
					"couponName" : "优惠券名称2",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150002",
					"activityId" : "ff808081799bf7d70172916150002",
					"couponName" : "优惠券名称3",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				},
				{
					"couponId" : "ff808081799bf7d70172916150003",
					"activityId" : "ff808081799bf7d70172916150003",
					"couponName" : "优惠券名称4",
					"couponType" : 0,
					"denomination" : 999,
					"couponStatus" : 0,
					"effectiveDays" : null,
					"endTime" : "2000-12-31",
					"fullBuyPrice" : 1000,
					"fullBuyType" : 1,
					"platformFlag" : 1,
					"rangeDayType" : 0,
					"scopeNames" : [ ],
					"scopeType" : 0,
					"startTime" : "2000-01-01",
					"totalCount" : 200,
					"takeCount" : 100
				}
			],
			"version" : "0.0.39"
		},
		"widgetNameSpace" : "@wanmi/wechat-coupon-01"
	},
	"replicable" : true,
	"title" : "优惠券列表",
	"type" : "COMMON",
	"updatedAt" : ISODate("2021-06-21T10:16:56.093+08:00"),
	"version" : "0.0.39",
	"cateId" : "5add9b42e15b9a13a2a3bd26",
	"sortIndex" : 8,
	"refs" : [
		"d2cStore"
	]
});

// 5b67c73d30bc7bd45bafd5af 文章模板规则 修改
db.tpl_rules.update({ _id: ObjectId("5b67c73d30bc7bd45bafd5af") }, {
    $set: {
        "envCode" : "test1",
        "tplRuleCode" : "wechat-article-01",
        "systemCode" : "d2cStore",
        "tplRuleName" : "weichat-文章模板01",
        "loadingBar" : "",
        "rule4PageCode" : "multi",
        "pluginBars" : [ ],
        "platform" : "weixin",
        "adapterBars" : [ ],
        "styleType" : "none",
        "pageType" : "article",
        "publishType" : "standard",
        "layoutBar" : "x-site-ui/widget/horizontal-layout",
        "acceptBars" : [
            "@wanmi/wechat-richtext",
            "@wanmi/wechat-blank",
            "@wanmi/wechat-title",
            "@wanmi/wechat-notice",
            "@wanmi/wechat-hotarea",
            "@wanmi/wechat-search",
            "@wanmi/wechat-nav",
            "@wanmi/wechat-freelayout",
            "@wanmi/wechat-slider",
            "@wanmi/wechat-video",
            "@wanmi/wechat-goodslist",
            "@wanmi/wechat-flashlist",
            "@wanmi/wechat-preorderlist",
            "@wanmi/wechat-grouponlist",
            "@wanmi/wechat-presalelist",
            "@wanmi/wechat-recommendlist",
            "@wanmi/wechat-coupon-01"
        ],
        "updatedAt" : ISODate("2021-06-08T15:26:05.983+08:00"),
        "__v" : 0,
        "createdAt" : ISODate("2018-08-06T11:57:49.011+08:00"),
        "acceptStyles" : [ ],
        "isDoublePlatformTpl" : false
    }
});

// 5b67c71830bc7bd45bafd55e    海报模板规则 修改
db.tpl_rules.update({ _id: ObjectId("5b67c71830bc7bd45bafd55e") }, {
    $set: {
        "envCode" : "test1",
        "tplRuleCode" : "wechat-poster-01",
        "systemCode" : "d2cStore",
        "tplRuleName" : "wechat-海报模板01",
        "loadingBar" : "",
        "rule4PageCode" : "multi",
        "pluginBars" : [ ],
        "platform" : "weixin",
        "adapterBars" : [ ],
        "styleType" : "none",
        "pageType" : "poster",
        "publishType" : "standard",
        "layoutBar" : "x-site-ui/widget/horizontal-layout",
        "acceptBars" : [
            "@wanmi/wechat-richtext",
            "@wanmi/wechat-blank",
            "@wanmi/wechat-title",
            "@wanmi/wechat-notice",
            "@wanmi/wechat-hotarea",
            "@wanmi/wechat-search",
            "@wanmi/wechat-nav",
            "@wanmi/wechat-freelayout",
            "@wanmi/wechat-slider",
            "@wanmi/wechat-video",
            "@wanmi/wechat-goodslist",
            "@wanmi/wechat-grouponlist",
            "@wanmi/wechat-preorderlist",
            "@wanmi/wechat-flashlist",
            "@wanmi/wechat-presalelist",
            "@wanmi/wechat-recommendlist",
            "@wanmi/wechat-coupon-01"
        ],
        "updatedAt" : ISODate("2021-06-08T15:25:57.134+08:00"),
        "__v" : 0,
        "createdAt" : ISODate("2018-08-06T11:57:12.810+08:00"),
        "acceptStyles" : [ ],
        "isDoublePlatformTpl" : false
    }
});

// 5b67c53930bc7bd45bafcfd9   通用规则 修改
db.tpl_rules.update({ _id: ObjectId("5b67c53930bc7bd45bafcfd9") }, {
    $set: {
        "envCode" : "test1",
        "tplRuleCode" : "wechat-common-rule",
        "systemCode" : "d2cStore",
        "tplRuleName" : "weicaht-通用规则",
        "loadingBar" : "x-site-ui-winter/weixin/loading/loading-page",
        "rule4PageCode" : "multi",
        "pluginBars" : [ ],
        "platform" : "weixin",
        "adapterBars" : [ ],
        "styleType" : "none",
        "pageType" : "index",
        "publishType" : "standard",
        "layoutBar" : "x-site-ui/widget/horizontal-layout",
        "acceptBars" : [
            "@wanmi/wechat-goodslist",
            "@wanmi/wechat-search",
            "@wanmi/wechat-video",
            "@wanmi/wechat-nav",
            "@wanmi/wechat-title",
            "@wanmi/wechat-notice",
            "@wanmi/wechat-hotarea",
            "@wanmi/wechat-blank",
            "@wanmi/wechat-freelayout",
            "@wanmi/wechat-richtext",
            "@wanmi/wechat-slider",
            "@wanmi/wechat-grouponlist",
            "@wanmi/wechat-preorderlist",
            "@wanmi/wechat-flashlist",
            "@wanmi/wechat-presalelist",
            "@wanmi/wechat-recommendlist",
            "@wanmi/wechat-coupon-01"
        ],
        "updatedAt" : ISODate("2021-06-08T15:16:01.817+08:00"),
        "__v" : 0,
        "createdAt" : ISODate("2018-08-06T11:49:13.699+08:00"),
        "acceptStyles" : [ ],
        "isDoublePlatformTpl" : false
    }
});

db.widget_infos.insert({
	"_id" : ObjectId("60bf18b0b3c42626e6b99814"),
	"envCode" : "test1",
	"key" : "@wanmi/wechat-coupon-01",
	"systemCode" : "",
	"__v" : 0,
	"cssFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/view.css",
	"cssMetaFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/edit.css",
	"jsFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/view.js",
	"jsMetaFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/edit.js",
	"type" : "BAR"
});

db.widget_infos.insert({
	"_id" : ObjectId("60bf18c3b3c42626e6b9984c"),
	"envCode" : "test1",
	"key" : "@wanmi/wechat-coupon-01",
	"systemCode" : "d2cStore",
	"__v" : 0,
	"cssFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/view.css",
	"cssMetaFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/edit.css",
	"jsFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/view.js",
	"jsMetaFilePath" : "/x-site/test1/public/barRepo/@wanmi/wechat-coupon-01/dist/edit.js",
	"type" : "BAR"
});

db.bar_package_infos.insert({
	"_id" : ObjectId("60bf18620df8d491f4c07f06"),
	"updatedAt" : ISODate("2021-06-08T17:57:03.313+08:00"),
	"createdAt" : ISODate("2021-06-08T15:12:34.115+08:00"),
	"name" : "@wanmi/wechat-coupon-01",
	"type" : "BAR",
	"systemCode" : "",
	"versions" : [
		{
			"version" : "0.0.1",
			"createTime" : ISODate("2021-06-08T15:12:34.077+08:00"),
			"_id" : ObjectId("60bf18620df8d491f4c07f07")
		},
		{
			"version" : "0.0.1",
			"createTime" : ISODate("2021-06-08T15:12:34.168+08:00"),
			"_id" : ObjectId("60bf18620df8d491f4c07f08")
		},
		{
			"version" : "0.0.2",
			"createTime" : ISODate("2021-06-08T16:20:24.674+08:00"),
			"_id" : ObjectId("60bf2848d8a9a44cc49e6049")
		},
		{
			"version" : "0.0.2",
			"createTime" : ISODate("2021-06-08T16:20:24.757+08:00"),
			"_id" : ObjectId("60bf2848d8a9a44cc49e604a")
		},
		{
			"version" : "0.0.3",
			"createTime" : ISODate("2021-06-08T16:39:58.887+08:00"),
			"_id" : ObjectId("60bf2cded8a9a44cc49e604b")
		},
		{
			"version" : "0.0.3",
			"createTime" : ISODate("2021-06-08T16:39:58.931+08:00"),
			"_id" : ObjectId("60bf2cded8a9a44cc49e604c")
		},
		{
			"version" : "0.0.4",
			"createTime" : ISODate("2021-06-08T17:51:33.471+08:00"),
			"_id" : ObjectId("60bf3da5d8a9a44cc49e604d")
		},
		{
			"version" : "0.0.4",
			"createTime" : ISODate("2021-06-08T17:51:33.502+08:00"),
			"_id" : ObjectId("60bf3da5d8a9a44cc49e604e")
		},
		{
			"version" : "0.0.5",
			"createTime" : ISODate("2021-06-08T17:54:34.816+08:00"),
			"_id" : ObjectId("60bf3e5ad8a9a44cc49e604f")
		},
		{
			"version" : "0.0.5",
			"createTime" : ISODate("2021-06-08T17:54:34.846+08:00"),
			"_id" : ObjectId("60bf3e5ad8a9a44cc49e6050")
		},
		{
			"version" : "0.0.6",
			"createTime" : ISODate("2021-06-08T17:57:03.276+08:00"),
			"_id" : ObjectId("60bf3eefd8a9a44cc49e6051")
		},
		{
			"version" : "0.0.6",
			"createTime" : ISODate("2021-06-08T17:57:03.309+08:00"),
			"_id" : ObjectId("60bf3eefd8a9a44cc49e6052")
		}
	],
	"refs" : [
		"test1"
	],
	"__v" : 11
});



