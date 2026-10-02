USE votehub;

-- ============================================================
-- VOTEHUB DATABASE SCHEMA
-- Full schema for voter/admin registration, OTP, elections,
-- candidates, eligibility, one-time voting, privacy-aware ballots,
-- history, notifications, live events, sessions and audit logs.
-- ============================================================

-- 1. USERS: both voter and admin accounts created through website
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role ENUM('VOTER','ADMIN') NOT NULL,
    voter_id VARCHAR(80) NULL,
    employee_id VARCHAR(80) NULL,
    full_name VARCHAR(150) NOT NULL,
    date_of_birth DATE NULL,
    email VARCHAR(190) NOT NULL,
    mobile VARCHAR(30) NOT NULL,
    aadhaar_hmac VARCHAR(128) NULL,
    password_hash VARCHAR(255) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_voter_id UNIQUE (voter_id),
    CONSTRAINT uk_users_employee_id UNIQUE (employee_id),
    CONSTRAINT uk_users_email UNIQUE (email),
    INDEX idx_users_role (role),
    INDEX idx_users_verified (verified)
) ENGINE=InnoDB;

-- 2. OTP: random OTP generated for each newly created account
CREATE TABLE IF NOT EXISTS otp_verifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    otp_hash VARCHAR(128) NOT NULL,
    expires_at DATETIME NOT NULL,
    attempts INT NOT NULL DEFAULT 0,
    max_attempts INT NOT NULL DEFAULT 3,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    verified_at DATETIME NULL,
    CONSTRAINT fk_otp_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_otp_user_created (user_id, created_at),
    INDEX idx_otp_expiry (expires_at)
) ENGINE=InnoDB;

-- 3. ELECTIONS: created/managed by admins
CREATE TABLE IF NOT EXISTS elections (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('UPCOMING','ACTIVE','CLOSING_SOON','CLOSED') NOT NULL DEFAULT 'UPCOMING',
    min_age INT NOT NULL DEFAULT 18,
    require_verified_account BOOLEAN NOT NULL DEFAULT TRUE,
    duplicate_vote_prevention BOOLEAN NOT NULL DEFAULT TRUE,
    results_published BOOLEAN NOT NULL DEFAULT FALSE,
    created_by BIGINT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_election_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_election_status (status),
    INDEX idx_election_time (start_time, end_time)
) ENGINE=InnoDB;

-- 4. CANDIDATES: candidate profiles belonging to an election
CREATE TABLE IF NOT EXISTS candidates (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    election_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    position VARCHAR(150) NOT NULL,
    description TEXT NULL,
    photo_url VARCHAR(500) NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_candidate_election FOREIGN KEY (election_id) REFERENCES elections(id) ON DELETE CASCADE,
    INDEX idx_candidate_election (election_id),
    INDEX idx_candidate_active (is_active)
) ENGINE=InnoDB;

-- 5. ELIGIBLE VOTERS / ONE-TIME AUTHORIZATION
-- This records that a voter is allowed to vote in an election.
-- It does NOT contain the candidate choice.
CREATE TABLE IF NOT EXISTS voting_authorizations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    election_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    token_hash VARCHAR(128) NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    issued_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    used_at DATETIME NULL,
    CONSTRAINT fk_auth_election FOREIGN KEY (election_id) REFERENCES elections(id) ON DELETE CASCADE,
    CONSTRAINT fk_auth_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_election_user UNIQUE (election_id, user_id),
    CONSTRAINT uk_auth_token UNIQUE (token_hash),
    INDEX idx_auth_user (user_id),
    INDEX idx_auth_election_used (election_id, used)
) ENGINE=InnoDB;

-- 6. VOTES / BALLOTS
-- Deliberately no voter_id column: candidate selection is kept separate
-- from the normal voter identity view in the application.
CREATE TABLE IF NOT EXISTS votes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    election_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    ballot_token_hash VARCHAR(128) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vote_election FOREIGN KEY (election_id) REFERENCES elections(id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(id) ON DELETE CASCADE,
    CONSTRAINT uk_ballot_token UNIQUE (ballot_token_hash),
    INDEX idx_votes_election (election_id),
    INDEX idx_votes_candidate (candidate_id)
) ENGINE=InnoDB;

-- 7. VOTING HISTORY: participation only, not candidate choice
CREATE TABLE IF NOT EXISTS voting_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    election_id BIGINT NOT NULL,
    status ENUM('VOTED','NOT_VOTED') NOT NULL DEFAULT 'VOTED',
    voted_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_history_election FOREIGN KEY (election_id) REFERENCES elections(id) ON DELETE CASCADE,
    CONSTRAINT uk_history_user_election UNIQUE (user_id, election_id),
    INDEX idx_history_user (user_id),
    INDEX idx_history_election (election_id)
) ENGINE=InnoDB;

-- 8. SESSIONS: authenticated login sessions
CREATE TABLE IF NOT EXISTS sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token_hash VARCHAR(128) NOT NULL,
    expires_at DATETIME NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_session_token UNIQUE (token_hash),
    INDEX idx_session_user (user_id),
    INDEX idx_session_expiry (expires_at)
) ENGINE=InnoDB;

-- 9. NOTIFICATIONS: live messages to voters/admins
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_notification_user_read (user_id, is_read),
    INDEX idx_notification_created (created_at)
) ENGINE=InnoDB;

-- 10. ELECTION EVENTS: supports Live Election Room activity/status feed
CREATE TABLE IF NOT EXISTS election_events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    election_id BIGINT NOT NULL,
    event_type VARCHAR(60) NOT NULL,
    message VARCHAR(500) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_event_election FOREIGN KEY (election_id) REFERENCES elections(id) ON DELETE CASCADE,
    INDEX idx_event_election_time (election_id, created_at)
) ENGINE=InnoDB;

-- 11. SYSTEM SETTINGS: configurable security/election rules
CREATE TABLE IF NOT EXISTS system_settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value VARCHAR(500) NOT NULL,
    description VARCHAR(500) NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 12. AUDIT LOGS: security/administrative trail; never store candidate choice with voter identity
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    actor_user_id BIGINT NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(80) NULL,
    entity_id BIGINT NULL,
    details TEXT NULL,
    ip_address VARCHAR(64) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_actor FOREIGN KEY (actor_user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_audit_actor (actor_user_id),
    INDEX idx_audit_action (action),
    INDEX idx_audit_time (created_at)
) ENGINE=InnoDB;

-- Default VoteHub security settings
INSERT INTO system_settings(setting_key,setting_value,description) VALUES
('minimum_voter_age','18','Minimum age required for voter eligibility'),
('otp_expiry_minutes','5','OTP validity period in minutes'),
('otp_max_attempts','3','Maximum OTP verification attempts'),
('require_verified_account','true','Only OTP-verified accounts can vote'),
('duplicate_vote_prevention','true','Prevent more than one vote per voter per election'),
('results_after_close','true','Results are published after election closure'),
('live_updates','true','Enable Live Election Room updates')
ON DUPLICATE KEY UPDATE setting_value=VALUES(setting_value), description=VALUES(description);
