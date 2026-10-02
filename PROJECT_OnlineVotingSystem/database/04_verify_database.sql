USE votehub;

SHOW TABLES;

SELECT setting_key, setting_value FROM system_settings ORDER BY setting_key;
SELECT id, name, status, start_time, end_time FROM elections ORDER BY id;
SELECT e.name AS election, c.name AS candidate, c.position
FROM candidates c JOIN elections e ON e.id=c.election_id
ORDER BY e.id, c.id;

SELECT e.name AS previous_election, c.name AS candidate, COUNT(v.id) AS votes
FROM elections e
JOIN candidates c ON c.election_id=e.id
LEFT JOIN votes v ON v.candidate_id=c.id
WHERE e.status='CLOSED'
GROUP BY e.id,c.id,c.name
ORDER BY e.id,c.id;

SELECT COUNT(*) AS registered_users FROM users;
