DROP DATABASE IF EXISTS online_voting;
CREATE DATABASE online_voting;
USE online_voting;

-- Election master data
CREATE TABLE election_settings (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 election_name VARCHAR(150) NOT NULL,
 description VARCHAR(500),
 start_time DATETIME NOT NULL,
 end_time DATETIME NOT NULL,
 status VARCHAR(30) NOT NULL DEFAULT 'OPEN'
);

-- Actual voter accounts. IMPORTANT: no username/password is seeded.
-- password is NULL until the person completes registration and creates credentials.
CREATE TABLE voters (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 voter_id VARCHAR(50) UNIQUE,
 full_name VARCHAR(120) NOT NULL,
 date_of_birth DATE,
 gender VARCHAR(20),
 email VARCHAR(150) UNIQUE,
 phone VARCHAR(20),
 identity_ref VARCHAR(100),
 password VARCHAR(255),
 account_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
 has_voted BOOLEAN NOT NULL DEFAULT FALSE,
 otp_verified BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8 pre-enrolled member profiles: identity information only.
-- They have NO login credentials. Each person must choose their own Voter ID/password.
INSERT INTO voters(full_name,date_of_birth,gender,email,phone,identity_ref,account_status) VALUES
('Aarav Sharma','2005-02-14','Male','aarav.sharma@college.demo','9000001001','DEMO-ID-1001','PENDING'),
('Diya Reddy','2005-07-22','Female','diya.reddy@college.demo','9000001002','DEMO-ID-1002','PENDING'),
('Karthik Varma','2004-11-03','Male','karthik.varma@college.demo','9000001003','DEMO-ID-1003','PENDING'),
('Meera Nair','2005-01-19','Female','meera.nair@college.demo','9000001004','DEMO-ID-1004','PENDING'),
('Rohan Kumar','2004-09-27','Male','rohan.kumar@college.demo','9000001005','DEMO-ID-1005','PENDING'),
('Saanvi Rao','2005-05-11','Female','saanvi.rao@college.demo','9000001006','DEMO-ID-1006','PENDING'),
('Vikram Singh','2004-12-30','Male','vikram.singh@college.demo','9000001007','DEMO-ID-1007','PENDING'),
('Ananya Patel','2005-08-16','Female','ananya.patel@college.demo','9000001008','DEMO-ID-1008','PENDING');

CREATE TABLE candidates (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 full_name VARCHAR(120) NOT NULL,
 party_name VARCHAR(120) NOT NULL,
 symbol VARCHAR(20) NOT NULL,
 manifesto VARCHAR(500),
 active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE votes (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 voter_id BIGINT NOT NULL UNIQUE,
 candidate_id BIGINT NOT NULL,
 voted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 receipt_code VARCHAR(40) NOT NULL UNIQUE,
 CONSTRAINT fk_vote_voter FOREIGN KEY(voter_id) REFERENCES voters(id),
 CONSTRAINT fk_vote_candidate FOREIGN KEY(candidate_id) REFERENCES candidates(id)
);

CREATE TABLE otp_verifications (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 voter_id BIGINT,
 otp_hash VARCHAR(255) NOT NULL,
 purpose VARCHAR(40) NOT NULL,
 expires_at DATETIME NOT NULL,
 verified BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_otp_voter FOREIGN KEY(voter_id) REFERENCES voters(id)
);

CREATE TABLE audit_logs (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 voter_id VARCHAR(50),
 action VARCHAR(60) NOT NULL,
 details VARCHAR(500),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO election_settings(election_name,description,start_time,end_time,status)
VALUES('Student Representative Election 2026','Secure academic election for student representatives.',NOW()-INTERVAL 1 HOUR,NOW()+INTERVAL 7 DAY,'OPEN');

INSERT INTO candidates(full_name,party_name,symbol,manifesto) VALUES
('Ananya Reddy','Progressive Students Party','★','Student facilities, transparency and campus development.'),
('Rahul Varma','Development Front','◆','Digital services, events and career support.'),
('Meera Sharma','Independent','●','Inclusive student representation and student welfare.'),
('Vikram Rao','Campus First','▲','Student clubs, innovation and better campus life.');
