# VoteHub Database

## SQL execution order

Run these files in MySQL Workbench in this order:

1. `01_create_database.sql`
2. `02_create_tables.sql`
3. `03_sample_data.sql`

The scripts are designed for MySQL 8.x.

## Important

- **No voter accounts are pre-created.**
- **No admin accounts are pre-created.**
- Users create accounts through the VoteHub website.
- OTP records are created during registration.
- The seed script creates only elections, candidates, election events, system settings and aggregate demonstration ballots for the previous closed election.
- Never put real Aadhaar numbers or plaintext passwords into the database.

## Tables

- `users` — voter/admin identity and authentication metadata
- `otp_verifications` — OTP expiry, attempts and verification state
- `elections` — election lifecycle and rules
- `candidates` — candidates belonging to elections
- `voting_authorizations` — one-time voter authorization / duplicate-vote protection
- `votes` — ballot records without a readable voter-id column
- `voting_history` — participation status only
- `sessions` — authenticated sessions
- `notifications` — user notifications
- `election_events` — live election room events
- `system_settings` — configurable security rules
- `audit_logs` — security and administrative audit trail
