create  sequence if not exists employee_id_seq
increment  1
start  1;

create table if not exists employee

  (
    id bigint  PRIMARY key default nextval('employee_id_seq'),
    employee_first_name varchar(100) not null,
    employee_middle_name varchar(100),
    employee_last_name varchar(100) not null,
    employee_email varchar(100) not null
  );