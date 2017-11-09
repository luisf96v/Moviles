drop schema if exists moviles;
create schema moviles;
use moviles;

drop table if exists student;
create table student(
	id 		varchar(20),
    uName 	varchar(20),
    lastN1 	varchar(20),
    lastN2 	varchar(20),
    age 	integer(2),
    primary key(id)
);


drop table if exists course;
create table course(
	id 		integer auto_increment,
    cName 	varchar(20),
    descr 	varchar(40),
    credits integer(2),
    primary key(id)
);

drop table if exists class;
create table class(
	sId 	varchar(20),
    cId		integer,
    primary key(sId, cId),
    foreign key (sId) references student(id),
    foreign key (cId) references course(id)
);

insert into student values ('1','Juan','Ape1','Ape2',12);
insert into student values ('2','Juan','Ape1','Ape2',12);
insert into student values ('3','Juan','Ape1','Ape2',12);
insert into student values ('4','Juan','Ape1','Ape2',12);
insert into student values ('5','Juan','Ape1','Ape2',12);
insert into student values ('6','Juan','Ape1','Ape2',12);

select * from course