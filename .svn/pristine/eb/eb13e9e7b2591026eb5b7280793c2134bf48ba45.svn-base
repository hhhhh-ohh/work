## 功能

dbreplay的作用就是做为kafka的consumer，消费来自于mysql和mongo的数据，将源数据写入统计库，为后续的统计做数据基础。
* 将canal监听的mysql表进行原样的写入statistics库中replay_开头的表中，将部分拆分的表进行合并
* 将mongoCapture监听的oplog数据按照配置动态的解析，并且拆分到不同的结构化的表中

## Release Note
