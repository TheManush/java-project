create database airlinemanagementsystem;

use airlinemanagementsystem;
create table login(username varchar(20), password varchar(20));
insert into login values('admin', 'admin');
select *from login;
ALTER TABLE login ADD CONSTRAINT unique_username UNIQUE (username);
/*create table passenger (name varchar(20), nationality varchar(20),phone varchar(15),address varchar(50), id varchar(20), gender varchar(20));*/
CREATE TABLE passenger1 (
    name VARCHAR(50),                
    nationality VARCHAR(30),         
    phone VARCHAR(15),
    address VARCHAR(100),          
    id VARCHAR(20),
    gender VARCHAR(10),
    avatar VARCHAR(255)              
);
select *from passenger;
select *from passenger1;

create table flight(f_code varchar(20), f_name varchar(20), source varchar(40), destination varchar(40));

describe flight;
insert into flight values("5001","CSEDU-5221","Dhaka","Sydney");
insert into flight values("5002","CSEDU-5124","Dhaka","New York");
insert into flight values("5003","CSEDU-5113","Dhaka","Toronto");
insert into flight values("5004","CSEDU-5231","Dhaka","London");
insert into flight values("5005","CSEDU-5214","Dhaka","Melbourne");

insert into flight values("1001", "AI-1212", "Delhi", "Mumbai");
insert into flight values("1002", "AI-1453", "Delhi", "Goa");
insert into flight values("1003", "AI-1112", "Mumbai", "Chennai");
insert into flight values("1004", "AI-3222", "Delhi", "Amritsar");
insert into flight values("1005", "AI-1212", "Delhi", "Ayodhya");

select * from flight;

create table reservation (PNR varchar(15), TIC varchar(20), id varchar(20), name varchar(20), nationality varchar(30), flightname varchar(15), flightcode varchar(20), src varchar(30), des varchar(30), ddate varchar(30));

select * from reservation;

ALTER TABLE passenger1 ADD added_by VARCHAR(255);
ALTER TABLE reservation ADD added_by VARCHAR(255);
create table cancel(pnr varchar(20), name varchar(40), cancelno varchar(20), fcode varchar(20), ddate varchar(30));

select * from cancel;

CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,        
    username VARCHAR(50) NOT NULL,            
    review TEXT NOT NULL,                     
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP 
);
select * from reviews;
/*TRUNCATE TABLE reviews;*/
ALTER TABLE reviews ADD COLUMN rating INT NOT NULL;
INSERT INTO reviews (username, review,rating) VALUES 
('admin', 'Great service and amazing experience!',4),
('john_doe', 'The booking system was easy to use. Highly recommend!',5);
