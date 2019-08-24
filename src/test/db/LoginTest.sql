insert into department(department_name,department_description )
    values('Research','负责调研权益相关内容');
insert into member(student_number, name, password, phone_number, position, department_name)
    values(201730683314,'彭天祥','123456','15564278737','Minister','Research');
insert into authority(id, authority_name, authority_owner_student_number)
    VALUES(1,'ROLE_test',201730683314) ;