insert into addresses (num, street, city, state, country) values (100, 'Avent Ferry Road', 'Raleigh', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (1, 'John', 'Smith', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 4, 1000);

insert into addresses (num, street, city, state, country) values (1016, 'Lexington Road', 'New York', 'New York', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (2, 'Jane', 'Doe', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 5, 1000);

insert into addresses (num, street, city, state, country) values (1022, 'Amphitheatre Parkway', 'Mountain View', 'California', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (3, 'Rock', 'Star', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 4, 1001);

insert into addresses (num, street, city, state, country) values (1210, 'Sacramento', 'Santa Cruz', 'California', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (4, 'Sheldon', 'Cooper', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 4, 1002);