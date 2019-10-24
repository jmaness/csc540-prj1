create table classifications (
  code varchar2(3) not null,
  name varchar2(64) not null unique,
  primary key (code)
);

insert into classifications (code, name) values ('01', 'Primary');
insert into classifications (code, name) values ('02', 'Secondary');
insert into classifications (code, name) values ('03', 'Tertiary');
