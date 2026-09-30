-- PRACTICAL 02: THE LIBRARIAN'S DASHBOARD
-- BookFlow

CREATE DATABASE IF NOT EXISTS bookflow_db;
USE bookflow_db;

-- 1. Create Loans table
CREATE TABLE IF NOT EXISTS Loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    borrowed_on DATE NOT NULL,
    returned_on DATE NULL,
    FOREIGN KEY (member_id) REFERENCES Members(member_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

-- Sample current loan
INSERT INTO Loans (member_id, book_id, borrowed_on)
SELECT 1, 1, CURDATE()
WHERE NOT EXISTS (
    SELECT 1
    FROM Loans
    WHERE member_id = 1
      AND book_id = 1
      AND returned_on IS NULL
);

-- 2. Catalog Search using JOIN
SELECT
    m.full_name AS member_name,
    b.title AS book_title
FROM Loans l
JOIN Members m ON l.member_id = m.member_id
JOIN Books b ON l.book_id = b.book_id
WHERE l.returned_on IS NULL;

-- 3. Collection Statistics using GROUP BY
SELECT
    published_year,
    COUNT(book_id) AS total_books
FROM Books
GROUP BY published_year
ORDER BY published_year;

-- 4. Donation History table
CREATE TABLE IF NOT EXISTS Donation_History (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    donated_by VARCHAR(150) NOT NULL,
    donated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

-- 5. Donation Transaction using ACID
START TRANSACTION;

INSERT INTO Books (title, isbn, published_year)
SELECT
    'Atomic Habits',
    '9780735211292',
    2018
WHERE NOT EXISTS (
    SELECT 1 FROM Books WHERE isbn = '9780735211292'
);

INSERT INTO Donation_History (book_id, donated_by)
SELECT
    book_id,
    'Library Donation'
FROM Books
WHERE isbn = '9780735211292'
  AND NOT EXISTS (
      SELECT 1
      FROM Donation_History
      WHERE Donation_History.book_id = Books.book_id
        AND Donation_History.donated_by = 'Library Donation'
  );

COMMIT;

-- 6. Create Index on ISBN
CREATE INDEX idx_books_isbn ON Books(isbn);

-- 7. Verify Results
SELECT * FROM Loans;
SELECT * FROM Donation_History;
SHOW INDEX FROM Books;
