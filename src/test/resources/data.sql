insert into person(`id`, `name`, `age`,`blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`)values (1,'martin',10,'A', 1991, 8, 15);
insert into person(`id`, `name`, `age`,`blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`)values (2,'kim',2,'B', 1992, 7, 15);
insert into person(`id`, `name`, `age`,`blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`)values (3,'jhon',12,'O', 1993, 10, 19);
insert into person(`id`, `name`, `age`,`blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`)values (4,'mario',34,'AB', 1994, 8, 31);
insert into person(`id`, `name`, `age`,`blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`)values (5,'dalis',22,'AB', 1995, 12, 12);

insert into block(`id`,`name`) values (1, 'jhon');
insert into block(`id`,`name`) values (2, 'mario');

update person set block_id = 1 where id = 3;
update person set block_id = 2 where id = 4;
