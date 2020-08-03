--create a new database
create database abirami_traders;
--switch to the database
use abirami_traders;
--create a new user to be used in hibernate
create user 'springuser'@'%' identified by 'admin@123';
--giving all permissions to the new user
grant all on abirami_traders.* to 'springuser'@'%';

create table product(product_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100), image MEDIUMBLOB, price decimal(5,2), availability_count int, time_to_print int, primary key (product_id));

create table category(category_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100), primary key (category_id));

insert into product (display_name, description, price, availability_count, time_to_print) values ('Calendar1','Test Calendar 1','12.5','1000','5');

insert into category(display_name, description) values ('Hindus Devotional', 'All Hindu Gods and Goddesses');