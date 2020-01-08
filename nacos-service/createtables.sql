CREATE TABLE `t_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);
CREATE TABLE `t_stock` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`stock_id`)
);
