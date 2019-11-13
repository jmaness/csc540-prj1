insert into addresses (id, num, street, city, state, zip, country) values (16, 100, 'Avent Ferry Road', 'Raleigh', 'North Carolina', '27606', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (1, 'John', 'Smith', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 16, 1000);

insert into addresses (id, num, street, city, state, zip, country) values (17, 1016, 'Lexington Road', 'New York', 'New York', '10022', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (2, 'Jane', 'Doe', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 17, 1000);

insert into addresses (id, num, street, city, state, zip, country) values (18, 1022, 'Amphitheatre Parkway', 'Mountain View', 'California', '94043', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (3, 'Rock', 'Star', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 18, 1001);

insert into addresses (id, num, street, city, state, zip, country) values (19, 1210, 'Sacramento', 'Santa Cruz', 'California', '90021', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (4, 'Sheldon', 'Cooper', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 19, 1002);