
--s2b
-- providerTrade增加索引
db.getCollection('providerTrade').ensureIndex({'parentId':1})
-- 延迟mongo事务请求时间
db.adminCommand( { setParameter: 1, maxTransactionLockRequestTimeoutMillis: 5000 } );
