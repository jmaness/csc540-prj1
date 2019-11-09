insert into severity_scales (id, name) values (1, '1-10');
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (1, 1, '1', 1);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (2, 1, '2', 3);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (3, 1, '3', 4);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (4, 1, '4', 5);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (5, 1, '5', 6);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (6, 1, '6', 7);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (7, 1, '7', 8);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (8, 1, '8', 9);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (9, 1, '9', 9);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (10, 1, '10', 10);

insert into severity_scales(id, name) values (2, 'Normal/Severe');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (11, 2, 'Normal', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (12, 2, 'Severe', 2);

insert into severity_scales(id, name) values (id, 3, 'Low/High');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (13, 3, 'Low', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (14, 3, 'High', 2);

insert into severity_scales(id, name) values (4, 'Normal/Premium');
 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (15, 3, 'Normal', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (16, 3, 'Premium', 2);

insert into body_parts (code, name) values ('1', 'Abdominal');
insert into body_parts (code, name) values ('2', 'Head');
insert into body_parts (code, name) values ('3', 'Eye');

/*
insert into body_parts (code, name) values ('1', 'Head');
insert into body_parts (code, name) values ('2', 'Heart');
insert into body_parts (code, name) values ('3', 'Lungs');
insert into body_parts (code, name) values ('4', 'Eyes');
*/

insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM001', 'Pain', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM002', 'Diarrhea', 2, '1');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM003', 'Fever', 3, null);
insert into symptoms (code, name, severity_scale_id, body_part_code)values ('SYM004', 'Physical Exam', 4, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM005', 'Lightheadedness', 2, '2');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM006', 'Blurred vision', 2, '3');

insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM01', 'Fever', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM02', 'Headache', 1, '1');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM03', 'Pain', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM04', 'Tenderness (to the touch)', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM05', 'Tightness', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM06', 'Numbness', 1, null);
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM07', 'Lightheadedness', 1, '1');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM08', 'Shortness of breath', 1, '3');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM09', 'Blurred vision', 1, '4');