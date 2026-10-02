USE votehub;

-- ============================================================
-- SAMPLE / DEMO DATA
-- IMPORTANT: NO VOTER OR ADMIN ACCOUNTS ARE CREATED HERE.
-- Users must create their own accounts through the website.
-- ============================================================

-- Remove only previously seeded demonstration elections so this script
-- can be re-run safely in a fresh college-demo database.
DELETE FROM notifications;
DELETE FROM voting_history;
DELETE FROM votes;
DELETE FROM voting_authorizations;
DELETE FROM election_events;
DELETE FROM candidates;
DELETE FROM elections;

-- Active election for testing the Live Election Room.
INSERT INTO elections
(name, description, start_time, end_time, status, min_age, require_verified_account,
 duplicate_vote_prevention, results_published)
VALUES
('Student Council Election 2026',
 'Live demonstration election for VoteHub. Test registration, eligibility, one-vote protection and live participation.',
 DATE_SUB(NOW(), INTERVAL 10 MINUTE),
 DATE_ADD(NOW(), INTERVAL 2 HOUR),
 'ACTIVE', 18, TRUE, TRUE, FALSE);

SET @live_election_id = LAST_INSERT_ID();

INSERT INTO candidates (election_id, name, position, description, photo_url)
VALUES
(@live_election_id, 'Aarav Sharma', 'Student Council President', 'Candidate profile and programme information.', NULL),
(@live_election_id, 'Diya Reddy', 'Student Council President', 'Candidate profile and programme information.', NULL),
(@live_election_id, 'Rahul Verma', 'Student Council President', 'Candidate profile and programme information.', NULL);

INSERT INTO election_events (election_id,event_type,message)
VALUES
(@live_election_id,'ELECTION_STARTED','Student Council Election 2026 is currently open for voting.'),
(@live_election_id,'LIVE_ROOM_ENABLED','Live participation monitoring is enabled.');

-- Previous completed election for Previous Elections / Results screens.
INSERT INTO elections
(name, description, start_time, end_time, status, min_age, require_verified_account,
 duplicate_vote_prevention, results_published)
VALUES
('Cultural Committee Election 2026',
 'Completed previous election used to demonstrate historical elections and aggregate results.',
 DATE_SUB(NOW(), INTERVAL 10 DAY),
 DATE_SUB(NOW(), INTERVAL 9 DAY),
 'CLOSED', 18, TRUE, TRUE, TRUE);

SET @previous_election_id = LAST_INSERT_ID();

INSERT INTO candidates (election_id, name, position, description, photo_url)
VALUES
(@previous_election_id, 'Meera Singh', 'Cultural Committee', 'Previous election candidate.', NULL),
(@previous_election_id, 'Kiran Rao', 'Cultural Committee', 'Previous election candidate.', NULL);

-- Anonymous-looking sample ballot tokens for the PREVIOUS election only.
-- They contain no voter identity and exist solely so the results page has
-- aggregate data before a real user votes in the live election.
SET @c1 = (SELECT id FROM candidates WHERE election_id=@previous_election_id AND name='Meera Singh' LIMIT 1);
SET @c2 = (SELECT id FROM candidates WHERE election_id=@previous_election_id AND name='Kiran Rao' LIMIT 1);

INSERT INTO votes(election_id,candidate_id,ballot_token_hash) VALUES
(@previous_election_id,@c1,SHA2('previous-demo-ballot-001',256)),
(@previous_election_id,@c1,SHA2('previous-demo-ballot-002',256)),
(@previous_election_id,@c1,SHA2('previous-demo-ballot-003',256)),
(@previous_election_id,@c1,SHA2('previous-demo-ballot-004',256)),
(@previous_election_id,@c1,SHA2('previous-demo-ballot-005',256)),
(@previous_election_id,@c2,SHA2('previous-demo-ballot-006',256)),
(@previous_election_id,@c2,SHA2('previous-demo-ballot-007',256)),
(@previous_election_id,@c2,SHA2('previous-demo-ballot-008',256));

INSERT INTO election_events (election_id,event_type,message)
VALUES
(@previous_election_id,'ELECTION_CLOSED','Cultural Committee Election 2026 is closed.'),
(@previous_election_id,'RESULTS_PUBLISHED','Aggregate results are available for this previous election.');
