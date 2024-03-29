drop schema if exists `sepetledb`;
create schema if not exists `sepetledb`;
use `sepetledb`;

/* Add wishlist */
/* Add Offer */

-- i strongly suggest to refactor the ER diagram !!!
create table `role` (
	`id` int(2) unsigned not null,
	`description` varchar(500) default null,
	primary key (`id`)
);


/* all the roles we have */
-- maybe make descriptions more clear
insert into `role` (`id`, `description`) values(1, 'admin');
insert into `role` (`id`, `description`) values(2, 'customer');
insert into `role` (`id`, `description`) values(3, 'seller');


/* Add created_at and updated at */
/* Change age to birth_date */
create table `user` (
	`id` int unsigned not null auto_increment,
	`role` int(2) unsigned not null default 2, -- i suggest to change this name to role_id
	`username` varchar(50) not null,
	`password` varchar(100) not null,
	`firstname` varchar(50) default null,
	`lastname` varchar(100) default null,
	`email` varchar(50) default null, -- maybe make this a not null 
	`age` int(11) default null, -- make this birthdate
	`phone` varchar(25) default null,
	primary key (`id`),
	foreign key (`role`) references `role` (`id`),
	unique (`username`), -- shall we make email unique. ER table shows it as unique 
	unique (`email`)
);


create table `address` (
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned,
	`place` varchar(500) not null,	
	primary key (`id`),
	foreign key (`user_id`) references `user` (`id`)
		on delete cascade
		on update no action
);


/* lets keep it here. Maybe it would come handful. However, we would need a little refactoring in code. 
 Keep in mind that if this approach is adopted, databse should also change 
 However, it would make application compatible with ER diagram. */

/*
create table `seller` (
	`user_id` int unsigned not null auto_increment,
	`money` int unsigned default 0,
	primary key (`user_id`),
	foreign key (`user_id`) references `user` (`id`)
		on delete cascade
		on update no action
); 

-- some values from user may just be present in customer
-- customer stands for registeredCustomer entity in the ER
create table `customer` (
	`user_id` int unsigned not null,
	`gender` int(2) default null,
	primary key (`user_id`),
	foreign key (`user_id`) references `user` (`id`)
		on delete cascade
		on update no action	
);
*/


create table `category` (
	`id` int unsigned not null auto_increment,
	`name` varchar(20) not null,
	primary key (`id`)
);

/*

create table offer (


);

*/



/* Add created_at and updated at */
/* Maybe change rate to average_rate */
/* Add review_count to ER table? */
/* Add brand coulumn? */

create table `product` (
	`id` int unsigned not null auto_increment,
	`seller_id` int unsigned not null, 
	`category_id` int unsigned default null,
	`name` varchar(200) default null,
	`description` varchar(500) default null,
	`image` varchar(100) default null,
	`quantity` int(11) default null,
	`price` float default null,
	`rate` float default 5,
	`review_count` int(11) default 1,
	primary key (`id`),
	foreign key (`seller_id`) references `user` (`id`)
		on delete cascade
		on update no action,
	foreign key (`category_id`) references `category` (`id`)
		on delete set null
		on update no action
);

/* for multiple categories
 
create table category_product (
	

);
*/

/* Add created_at and updated at */

create table `rate_product` (
	`id` int unsigned not null auto_increment,
	`seller_id` int unsigned not null,
	`customer_id` int unsigned not null,
	`rate` int unsigned not null default 5,
	`comment` varchar(500) default null,
	primary key (`id`),
	foreign key (`seller_id`) references `user` (`id`)
		on delete cascade
		on update no action,
	foreign key (`customer_id`) references `user` (`id`)
		on delete cascade
		on update no action
);

/* add date */
create table `cart` (
	`id` int unsigned not null auto_increment,
	`price` float not null default 0, -- security conserns? maybe change to not null
	primary key (`id`)
);


create table `cart_item` (
	`id` int unsigned not null auto_increment,
	`cart_id` int unsigned not null,
	`price` float unsigned not null,
	primary key (`id`),
	foreign key (`cart_id`) references `cart` (`id`)
		on delete cascade
		on update no action
);


/* Add dates */
create table `payment` (
	`cart_id` int unsigned not null,
	`customer_id` int unsigned not null,
	`type` int(4) unsigned default null,
	`price` float unsigned not null,
	primary key (`cart_id`),
	foreign key (`cart_id`) references `cart` (`id`),
	foreign key (`customer_id`) references `user` (`id`)
		on delete cascade
		on update no action
);	


-- this is a test, ignore it
 insert into `user` (`username`, `firstname`, `password`) values ('zekovic','zek', 'qwe');
 
 insert into product (seller_id, name, description, quantity, price, rate, review_count)  values (1,'betta fish', 'the siamese fighting fish (betta splendens), also known as the betta.', 10, 80,5,1 );


