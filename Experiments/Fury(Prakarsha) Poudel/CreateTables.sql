CREATE DATABASE IF NOT EXISTS CydropReminder;
use CydropReminder;

drop table if exists Pet;

create table Pet(
pet_id integer NULL AUTO_INCREMENT,
pet_name varchar(10) default null,
pet_type varchar(10) default null,
pet_breed varchar(30) default null,
pet_diagnosis varchar(20) default null,
pet_age integer default null,
pet_gender varchar(1) default null,
primary key(pet_id)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
