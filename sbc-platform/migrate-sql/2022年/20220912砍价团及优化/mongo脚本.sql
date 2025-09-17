-- 魔方砍价组件mongodb脚本
-- 1、bus_bar_infos
db.getCollection("bus_bar_infos").insert( {
_id: ObjectId("62fa14801de8e540f09b9686"),
envCode: "test1",
key: "@wanmi/wechat-bargainlist",
platform: "weixin",
systemCode: "d2cStore",
__v: NumberInt("0"),
addible: true,
cateId: "5add9b42e15b9a13a2a3bd26",
classify: "",
createdAt: ISODate("2022-08-15T09:40:20.456Z"),
deletable: true,
deleted: false,
dependencies: [
"@wanmi/wechat-bargainlist"
],
img: "",
isAdvanced: true,
isCommon: true,
packageName: "@wanmi/wechat-bargainlist",
props: {
props: {
showType: {
itemStyle: NumberInt("2"),
showStyle: NumberInt("1"),
cartButton: NumberInt("1"),
goodsSetup: {
cart: true,
cartStyle: NumberInt("3"),
name: true,
marketing: true,
title: true,
price: true,
showSku: true,
horn: true,
hornStyle: NumberInt("2"),
customHorn: ""
}
},
bgColor: "#f7f7f7",
selectedSource: NumberInt("4"),
sortSource: NumberInt("1"),
sources: [
{
type: "1",
size: NumberInt("4")
},
{
type: "2",
size: NumberInt("4")
},
{
type: "3",
size: NumberInt("4"),
cateID: "",
cateName: ""
},
{
type: "4"
},
{
type: "5",
size: NumberInt("4")
}
],
items: [
{
imgHref: "",
imgSrc: "https://wanmi-b2b-x-site.oss-cn-shanghai.aliyuncs.com/x-site-ui/default.png",
name: "示例商品名称1",
nameWithSku: "示例商品名称 示例SKU",
specName: "示例SKU",
title: "商品二级标题",
price: "19",
buyPoint: NumberInt("0"),
marketPrice: NumberInt("200"),
progressRatio: NumberInt("10"),
promotionLabels: { }
},
{
imgHref: "",
imgSrc: "https://wanmi-b2b-x-site.oss-cn-shanghai.aliyuncs.com/x-site-ui/default.png",
name: "示例商品名称2",
nameWithSku: "示例商品名称 示例SKU",
specName: "示例SKU",
title: "商品二级标题",
price: "19",
buyPoint: NumberInt("0"),
marketPrice: NumberInt("200"),
progressRatio: NumberInt("10"),
promotionLabels: { }
}
],
version: "0.0.268"
},
widgetNameSpace: "@wanmi/wechat-bargainlist"
},
replicable: true,
sortIndex: NumberInt("13"),
title: "砍价组件",
type: "COMMON",
updatedAt: ISODate("2022-08-15T09:40:20.456Z"),
version: "0.0.1"
} );

-- 2、common_bar_infos
db.getCollection("common_bar_infos").insert( {
_id: ObjectId("62fa14801de8e540f09b967d"),
envCode: "test1",
key: "@wanmi/wechat-bargainlist",
__v: NumberInt("0"),
addible: true,
createdAt: ISODate("2022-08-15T09:40:20.287Z"),
cateId: "5add9b42e15b9a13a2a3bd26",
deletable: true,
deleted: false,
dependencies: [
"@wanmi/wechat-bargainlist"
],
img: "",
isAdvanced: true,
packageName: "@wanmi/wechat-bargainlist",
platform: "weixin",
props: {
props: {
showType: {
itemStyle: NumberInt("2"),
showStyle: NumberInt("1"),
cartButton: NumberInt("1"),
goodsSetup: {
cart: true,
cartStyle: NumberInt("3"),
name: true,
marketing: true,
title: true,
price: true,
showSku: true,
horn: true,
hornStyle: NumberInt("2"),
customHorn: ""
}
},
bgColor: "#f7f7f7",
selectedSource: NumberInt("4"),
sortSource: NumberInt("1"),
sources: [
{
type: "1",
size: NumberInt("4")
},
{
type: "2",
size: NumberInt("4")
},
{
type: "3",
size: NumberInt("4"),
cateID: "",
cateName: ""
},
{
type: "4"
},
{
type: "5",
size: NumberInt("4")
}
],
items: [
{
imgHref: "",
imgSrc: "https://wanmi-b2b-x-site.oss-cn-shanghai.aliyuncs.com/x-site-ui/default.png",
name: "示例商品名称1",
nameWithSku: "示例商品名称 示例SKU",
specName: "示例SKU",
title: "商品二级标题",
price: "19",
buyPoint: NumberInt("0"),
marketPrice: NumberInt("200"),
progressRatio: NumberInt("10"),
promotionLabels: { }
},
{
imgHref: "",
imgSrc: "https://wanmi-b2b-x-site.oss-cn-shanghai.aliyuncs.com/x-site-ui/default.png",
name: "示例商品名称2",
nameWithSku: "示例商品名称 示例SKU",
specName: "示例SKU",
title: "商品二级标题",
price: "19",
buyPoint: NumberInt("0"),
marketPrice: NumberInt("200"),
progressRatio: NumberInt("10"),
promotionLabels: { }
}
],
version: "0.0.268"
},
widgetNameSpace: "@wanmi/wechat-bargainlist"
},
replicable: true,
title: "wechat-bargainlist",
updatedAt: ISODate("2022-08-15T09:40:20.491Z"),
version: "0.0.1",
refs: [
"d2cStore"
]
} );

-- 3、bar_package_infos
db.getCollection("bar_package_infos").insert( {
_id: ObjectId("62fa1484425ab71b9191c933"),
updatedAt: ISODate("2022-08-15T09:40:20.495Z"),
createdAt: ISODate("2022-08-15T09:40:20.096Z"),
name: "@wanmi/wechat-bargainlist",
type: "BAR",
systemCode: "d2cStore",
versions: [
{
version: "0.0.1",
createTime: ISODate("2022-08-15T09:40:20.089Z"),
_id: ObjectId("62fa1484425ab71b9191c934")
}
],
refs: [
"test1"
],
__v: NumberInt("0")
} );

-- 4、tpl_rules
db.tpl_rules.update({ "tplRuleName":"weicaht-通用规则" }, {
$push: {
"acceptBars":
"@wanmi/wechat-bargainlist"

    }
});

-- 5、widget_infos
db.getCollection("widget_infos").insert( {
_id: ObjectId("62fa14801de8e540f09b9682"),
envCode: "test1",
key: "@wanmi/wechat-bargainlist",
systemCode: "d2cStore",
__v: NumberInt("0"),
cssFilePath: "/x-site/test1/public/barRepo/@wanmi/wechat-bargainlist/dist/view.css",
cssMetaFilePath: "/x-site/test1/public/barRepo/@wanmi/wechat-bargainlist/dist/edit.css",
jsFilePath: "/x-site/test1/public/barRepo/@wanmi/wechat-bargainlist/dist/view.js",
jsMetaFilePath: "/x-site/test1/public/barRepo/@wanmi/wechat-bargainlist/dist/edit.js",
type: "BAR"
} );

-- mongo脚本  海报页文章页增加砍价组价
db.getCollection("tpl_rules").update({ _id: ObjectId("5b67c71830bc7bd45bafd55e") },
    { $push: { "acceptBars": "@wanmi/wechat-bargainlist" } });

db.getCollection("tpl_rules").update({ _id: ObjectId("5b67c73d30bc7bd45bafd5af") },
    { $push: { "acceptBars": "@wanmi/wechat-bargainlist" } });