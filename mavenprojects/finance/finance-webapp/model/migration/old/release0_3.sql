create table "budget_record" (
	"id" int4 not null,
	"budget" numeric(19, 2) not null,
	"date" timestamp not null,
	"categoryID" int4 not null,
	primary key ("id"),
	unique ("date", "categoryID")
	);

alter table "budget_record" 
	add constraint FK3FF9F28B6465901E 
	foreign key ("categoryID") 
	references "category";