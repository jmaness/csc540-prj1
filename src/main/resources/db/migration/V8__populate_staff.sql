insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (89001, 'Medical', 'Robot', 'Medical', TO_DATE('06/21/2019', 'MM/DD/YYYY'), 4, 2001, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93001, 'Musical', 'Robert', 'Medical', TO_DATE('08/29/2018', 'MM/DD/YYYY'), 5, 2002, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67001, 'Muscular', 'Rob', 'Medical', TO_DATE('10/12/1983', 'MM/DD/YYYY'), 6, 2001, 'GP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (88001, 'Mechanical', 'Roboto', 'Medical', TO_DATE('06/21/2019', 'MM/DD/YYYY'), 4, 2001, 'GP000');
insert into staff_secondary_departments (staff_id, department_code) values (88001, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (91001, 'Millenium', 'Roberten', 'Medical', TO_DATE('09/20/2018', 'MM/DD/YYYY'), 5, 2002, 'GP001');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (66001, 'Missionary', 'Robinson', 'Medical', TO_DATE('10/01/1993', 'MM/DD/YYYY'), 6, 2002, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (89002, 'Miscellaneous', 'Robotor', 'Non-medical', TO_DATE('08/19/2014', 'MM/DD/YYYY'), 4, 2001, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93002, 'Musician', 'Robot', 'Non-medical', TO_DATE('10/18/2017', 'MM/DD/YYYY'), 5, 2001, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67002, 'Massaging', 'Robin', 'Medical', TO_DATE('12/10/1990', 'MM/DD/YYYY'), 6, 2003, 'ER001');

insert into department_directors (staff_id, department_code) values (93001, 'ER000');
insert into department_directors (staff_id, department_code) values (67001, 'GP000');
insert into department_directors (staff_id, department_code) values (91001, 'GP001');
insert into department_directors (staff_id, department_code) values (89001, 'OP000');
insert into department_directors (staff_id, department_code) values (89001, 'SE000');
