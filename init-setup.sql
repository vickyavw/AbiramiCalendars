create database abirami_traders;
--The user to be used in hibernate
create user 'springuser'@'%' identified by 'admin@123';

grant all on abirami_traders.* to 'springuser'@'%';

create table product(product_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100), image MEDIUMBLOB, price decimal, availability_count int, time_to_print int, primary key (product_id));

insert into product (display_name, description, price, availability_count, time_to_print) values ('Calendar1','Test Calendar 1','12.5','1000','5');