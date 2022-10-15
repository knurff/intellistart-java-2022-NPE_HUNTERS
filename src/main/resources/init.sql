insert into users values('test@gmail.com','COORDINATOR');
insert into users values('user1@gmail.com','INTERVIEWER');
insert into users values('user2@gmail.com','INTERVIEWER');

insert into interviewer_slots values(1,'MONDAY','11:30','9:00',12,'user1@gmail.com');
insert into interviewer_slots values(2,'TUESDAY','18:00','14:30',40,'user1@gmail.com');
insert into interviewer_slots values(3,'THURSDAY','19:30','10:30',41,'user1@gmail.com');
insert into interviewer_slots values(4,'MONDAY','15:00','8:00',41,'user1@gmail.com');

insert into interviewer_slots values(5,'TUESDAY','16:30','11:00',40,'user2@gmail.com');
insert into interviewer_slots values(6,'FRIDAY','17:30','14:30',40,'user2@gmail.com');
insert into interviewer_slots values(7,'WEDNESDAY','19:30','16:30',41,'user2@gmail.com');
insert into interviewer_slots values(8,'WEDNESDAY','15:00','8:00',41,'user2@gmail.com');


insert into candidate_slots values(1,'2022-10-13','candidate3@gmail.com','11:30','9:00');
insert into candidate_slots values(2,'2022-10-11','candidate1@gmail.com','13:00','10:00');
insert into candidate_slots values(3,'2022-10-12','candidate2@gmail.com','20:00','16:30');
insert into candidate_slots values(4,'2022-10-14','candidate4@gmail.com','17:00','15:00');

insert into candidate_slots values(5,'2022-10-17','candidate5@gmail.com','19:00','11:30');
insert into candidate_slots values(6,'2022-10-19','candidate6@gmail.com','17:30','14:30');
insert into candidate_slots values(7,'2022-10-20','candidate7@gmail.com','12:00','9:30');
insert into candidate_slots values(8,'2022-10-20','candidate8@gmail.com','16:00','13:00');
insert into candidate_slots values(9,'2022-10-20','candidate9@gmail.com','18:00','15:30');
insert into candidate_slots values(10,'2022-10-21','candidate10@gmail.com','12:00','9:00');


insert into bookings values (1,'Test booking 1 - Interviewer 2, candidate 1','13:00','11:30','Interview Booking 1',2,5);
insert into bookings values (2,'Test booking 2 - Interviewer 1, candidate 2','18:00','16:30','Interview Booking 2',3,2);
insert into bookings values (3,'Test booking 3 - Interviewer 2, candidate 4','16:30','15:00','Interview Booking 3',4,6);
insert into bookings values (4,'Test booking 4 - Interviewer 1, candidate 5','15:00','13:30','Interview Booking 4',5,4);

insert into bookings values (5,'Test booking 5 - Interviewer 1, candidate 8','16:00','14:30','Interview Booking 5',8,3);
insert into bookings values (6,'Test booking 6 - Interviewer 1, candidate 9','17:30','16:00','Interview Booking 6',9,3);