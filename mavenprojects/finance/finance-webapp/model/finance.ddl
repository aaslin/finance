
    create table "budget_record" (
        "id" int4 not null,
        "budget" numeric(19, 2) not null,
        "date" timestamp not null,
        "categoryID" int4 not null,
        primary key ("id"),
        unique ("date", "categoryID")
    );

    create table "category" (
        "id" int4 not null,
        "name" varchar(255),
        type varchar(255),
        primary key ("id")
    );

    create table "file" (
        "id" int4 not null,
        "name" varchar(255),
        "time_frameID" int4,
        primary key ("id")
    );

    create table "time_frame" (
        "id" int4 not null,
        "start" date not null,
        "stop" date not null,
        "tag" varchar(255) not null,
        primary key ("id")
    );

    create table "transaction" (
        "id" int4 not null,
        "comment" varchar(255) not null,
        "cost" numeric(19, 2) not null,
        "date" date not null,
        "categoryID" int4,
        "fileID" int4 not null,
        primary key ("id")
    );

    create table category_rule (
        "id" int4 not null,
        "enabled" bool,
        "name" varchar(255) not null,
        "operator" varchar(255) not null,
        "pattern" varchar(255) not null,
        "categoryID" int4 not null,
        primary key ("id")
    );

    alter table "budget_record" 
        add constraint FK3FF9F28B6465901E 
        foreign key ("categoryID") 
        references "category";

    alter table "file" 
        add constraint FK2FF57CE90C42CF 
        foreign key ("time_frameID") 
        references "time_frame";

    alter table "transaction" 
        add constraint FK7FA0D2DE5FC9351A 
        foreign key ("fileID") 
        references "file";

    alter table "transaction" 
        add constraint FK7FA0D2DE6465901E 
        foreign key ("categoryID") 
        references "category";

    alter table category_rule 
        add constraint FK1432019D6465901E 
        foreign key ("categoryID") 
        references "category";

    create sequence hibernate_sequence;
