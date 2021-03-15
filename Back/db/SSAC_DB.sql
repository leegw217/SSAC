create database ssac;
use ssac;

create table `user` (
id varchar(50) not null primary key,
pw varchar(50) not null,
nickname varchar(200) not null,
point int not null default 0
);

create table `team` (
no int not null auto_increment primary key,
event_no int not null,
name varchar(200) not null,
logo varchar(200) not null,
count int not null default 0
);

create table `myteam` (
no int not null auto_increment primary key,
id varchar(50) not null,
team_no int not null,
FOREIGN KEY (`id`) REFERENCES `user` (`id`),
FOREIGN KEY (`team_no`) REFERENCES `team` (`no`)
);

insert into team (event_no, name, logo, count) values (1, '강원 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '광주 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '대구 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, 'FC 서울', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '성남 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '수원 삼성 블루윙즈', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '수원 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '울산 현대', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '인천 유나이티드', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '전북 현대 모터스', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '제주 유나이티드', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '포항 스틸러스', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '경남 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '김천 상무', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '대전 하나 시티즌', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '부산 아이파크', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '부천 FC 1995', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '서울 이랜드', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '안산 그리너스', 'src', 0);
insert into team (event_no, name, logo, count) values (1, 'FC 안양', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '전남 드래곤즈', 'src', 0);
insert into team (event_no, name, logo, count) values (1, '충남 아산 FC', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '두산 베어스', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '롯데 자이언츠', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '삼성 라이온즈', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '키움 히어로즈', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '한화 이글스', 'src', 0);
insert into team (event_no, name, logo, count) values (2, 'KIA 타이거즈', 'src', 0);
insert into team (event_no, name, logo, count) values (2, '케이티 위즈', 'src', 0);
insert into team (event_no, name, logo, count) values (2, 'LG 트윈스', 'src', 0);
insert into team (event_no, name, logo, count) values (2, 'NC 다이노스', 'src', 0);
insert into team (event_no, name, logo, count) values (2, 'SK 와이번스', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'DWG KIA', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'DRX', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'Gen.G Esports', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'Afreeca Freecs', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'T1', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'kt Rolster', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'Liiv SANDBOX', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'NONGSHIM REDFORCE', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'Hanwha Life Esports', 'src', 0);
insert into team (event_no, name, logo, count) values (3, 'Fredit BRION', 'src', 0);
