-- 删除mongo中物流信息的结构
db.getSiblingDB("s2b").getCollection("logisticsLog").drop();
