-- 达达配送新增订单重量必传字段
alter table `sbc-empower`.`delivery_record_dada` add column `cargo_weight`
    decimal(20, 2) not null default 0 comment '订单重量，单位（KG）' AFTER `cargo_price`;