alter table category add column type varchar(255);

update category set type = 'EXPENSE' where name != 'Lön' and name != 'Annan inkomst';
update category set type = 'INCOME' where name = 'Lön' or name = 'Annan inkomst';

alter table time_frame alter column "start" type date;
alter table time_frame alter column "stop" type date;

alter table transaction alter column "date" type date;

insert into category values(nextval('hibernate_sequence'), 'Test', 'EXPENSE');

alter table category_rule alter column "name" set not null;
alter table category_rule alter column "operator" set not null;
alter table category_rule alter column "pattern" set not null;
alter table category_rule alter column "categoryID" set not null;
