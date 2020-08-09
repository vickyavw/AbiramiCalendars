--create a new database
create database abirami_traders;
--switch to the database
use abirami_traders;
--create a new user to be used in hibernate
create user 'springuser'@'%' identified by 'admin@123';
--giving all permissions to the new user
grant all on abirami_traders.* to 'springuser'@'%';

create table category(category_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100), primary key (category_id));

create table format(format_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100),
	product_type ENUM('CALENDAR','DIARY','BOX','LABEL') not null, primary key (format_id));

create table product(product_id INT not null auto_increment, display_name varchar(100) not null, description varchar(100), 
	product_type ENUM('CALENDAR','DIARY','BOX','LABEL') not null,image MEDIUMBLOB, price decimal(5,2), availability_count int, 
	time_to_print int, category_id INT, format_id INT, primary key (product_id),
	CONSTRAINT product_category_fk FOREIGN KEY (category_id) REFERENCES category(category_id))
        CONSTRAINT product_format_fk FOREIGN KEY (format_id) REFERENCES format(format_id));

insert into product (display_name, description, price, availability_count, time_to_print) values ('Calendar1','Test Calendar 1','12.5','1000','5');

insert into category(display_name, description) values ('Hindus Devotional', 'All Hindu Gods and Goddesses');
-- formats for calendars
insert into format (display_name, description, product_type) values ('Normal Daily Calendars','Normal Daily Calendars', 'CALENDAR');
insert into format (display_name, description, product_type) values ('Fancy Die-cut Calendars','Fancy Die-cut Calendars', 'CALENDAR');
insert into format (display_name, description, product_type) values ('12 Sheeter Monthly Calendars','12 Sheeter Monthly Calendars', 'CALENDAR');
insert into format (display_name, description, product_type) values ('6 Sheeter Monthly Calendars','6 Sheeter Monthly Calendars', 'CALENDAR');
insert into format (display_name, description, product_type) values ('3 Sheeter Monthly Calendars','3 Sheeter Monthly Calendars', 'CALENDAR');
insert into format (display_name, description, product_type) values ('Bi-Monthly Calendars','Bi-Monthly Calendars', 'CALENDAR');
--formats for Diaries
insert into format (display_name, description, product_type) values ('Normal Diaries','Normal Diaries', 'DIARY');
insert into format (display_name, description, product_type) values ('Pocket Diaries','Pocket Diaries', 'DIARY');
--formats for Boxes
insert into format (display_name, description, product_type) values ('Cake Boxes','Cake Boxes', 'BOX');
insert into format (display_name, description, product_type) values ('Sweet Boxes','Sweet Boxes', 'BOX');
insert into format (display_name, description, product_type) values ('Pickles Board','Pickles Board', 'BOX');
