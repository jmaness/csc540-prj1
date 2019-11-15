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

insert into severity_scales(id, name) values (3, 'Low/High');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (13, 3, 'Low', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (14, 3, 'High', 2);

insert into severity_scales(id, name) values (4, 'Normal/Premium');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (15, 4, 'Normal', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (16, 4, 'Premium', 2);

insert into severity_scales(id, name) values (5, 'Light/Normal/Heavy');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (17, 5, 'Light', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (18, 5, 'Normal', 2);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (19, 5, 'Heavy', 3);
