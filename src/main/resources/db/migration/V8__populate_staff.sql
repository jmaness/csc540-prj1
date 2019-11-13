insert into addresses (id, num, street, city, state, zip, country) values (7, 83, 'Vernon St.', 'Scotch Plains', 'NJ', '07076', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (8, 69, 'Holly Drive', 'Blacksburg', 'VA', '24060', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (9, 7540, 'Plymouth Court', 'Derry', 'NH', '03038', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (10, 8196, 'Big Rock Cove Road', 'Luthervile Timonium', 'MD', '21093', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (11, 697, 'Lawrence Ave.', 'Teaneck', 'NJ', '07666', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (12, 685, 'South Chapel Lane', 'Branford', 'CT', '06405', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (13, 7056, 'W. Piper. Dr.', 'Macon', 'GA', '31204', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (14, 40, 'N. Peachtree Drive', 'Sunnyside', 'NY', '11104', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (15, 22, 'Sutor St.', 'Laurel', 'MD', '20707', 'US');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (89001, 'Medical', 'Robot', 'Medical', TO_DATE('06/21/2019', 'MM/DD/YYYY'), 7, 1000, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93001, 'Musical', 'Robert', 'Medical', TO_DATE('08/29/2018', 'MM/DD/YYYY'), 8, 1001, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67001, 'Muscular', 'Rob', 'Medical', TO_DATE('10/12/1983', 'MM/DD/YYYY'), 9, 1000, 'GP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (88001, 'Mechanical', 'Roboto', 'Medical', TO_DATE('06/21/2019', 'MM/DD/YYYY'), 10, 1000, 'GP000');
insert into staff_secondary_departments (staff_id, department_code) values (88001, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (91001, 'Millenium', 'Roberten', 'Medical', TO_DATE('09/20/2018', 'MM/DD/YYYY'), 11, 1001, 'GP001');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (66001, 'Missionary', 'Robinson', 'Medical', TO_DATE('10/01/1993', 'MM/DD/YYYY'), 12, 1001, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (89002, 'Miscellaneous', 'Robotor', 'Non-medical', TO_DATE('08/19/2014', 'MM/DD/YYYY'), 13, 1000, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93002, 'Musician', 'Robot', 'Non-medical', TO_DATE('10/18/2017', 'MM/DD/YYYY'), 14, 1000, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67002, 'Massaging', 'Robin', 'Medical', TO_DATE('12/10/1990', 'MM/DD/YYYY'), 15, 1002, 'ER001');

insert into department_directors (staff_id, department_code) values (93001, 'ER000');
insert into department_directors (staff_id, department_code) values (67001, 'GP000');
insert into department_directors (staff_id, department_code) values (91001, 'GP001');
insert into department_directors (staff_id, department_code) values (89001, 'OP000');
insert into department_directors (staff_id, department_code) values (89001, 'SE000');
