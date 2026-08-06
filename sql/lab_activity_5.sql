-- FUNDAMENTALS OF ENTERPRISE DATA MANAGEMENT
-- Laboratory Activity 5 — SQL Views & Window Functions
-- Student Name: Nicholai Patrick Capinpuyan
-- Section: [Your Section]
-- Date: August 6, 2026

-- ============================================================================
-- Step 0 — Set Up the Database
-- ============================================================================
DROP DATABASE IF EXISTS carewell_analytics;
CREATE DATABASE carewell_analytics;
USE carewell_analytics;

CREATE TABLE branch (
    branch_no  CHAR(3) PRIMARY KEY,
    city       VARCHAR(50) NOT NULL,
    phone      VARCHAR(20)
);

CREATE TABLE staff (
    staff_no   CHAR(5) PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    position   VARCHAR(30),
    salary     DECIMAL(10,2),
    branch_no  CHAR(3),
    FOREIGN KEY (branch_no) REFERENCES branch(branch_no)
);

CREATE TABLE equipment (
    equip_no     CHAR(5) PRIMARY KEY,
    category     VARCHAR(40) NOT NULL,
    brand        VARCHAR(30),
    model        VARCHAR(30),
    cond         VARCHAR(15),
    monthly_rate DECIMAL(10,2) NOT NULL,
    staff_no     CHAR(5),
    FOREIGN KEY (staff_no) REFERENCES staff(staff_no)
);

CREATE TABLE renter (
    renter_no  CHAR(5) PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    max_rent   DECIMAL(10,2),
    branch_no  CHAR(3),
    FOREIGN KEY (branch_no) REFERENCES branch(branch_no)
);

CREATE TABLE rental_agreement (
    agreement_no CHAR(6) PRIMARY KEY,
    renter_no    CHAR(5),
    equip_no     CHAR(5),
    monthly_rent DECIMAL(10,2),
    date_start   DATE,
    date_finish  DATE,
    FOREIGN KEY (renter_no) REFERENCES renter(renter_no),
    FOREIGN KEY (equip_no)  REFERENCES equipment(equip_no)
);

-- Sample Data
INSERT INTO branch VALUES
 ('B3','Cagayan de Oro','0906-339-2178'),
 ('B5','Davao','0906-884-5100');

INSERT INTO staff VALUES
 ('SL21','Michael','Agno','Manager',30000,'B5'),
 ('SL22','Ann','Curtis','Technician',22000,'B3'),
 ('SL23','Angelie','Rado','Technician',21000,'B3');

INSERT INTO equipment VALUES
 ('EQ45','Wheelchair','Medline','MDS806550','Good',1550,'SL22'),
 ('EQ50','Wheelchair','Drive','Cruiser III','Good',1550,'SL22'),
 ('EQ52','Wheelchair','Medline','K1 Basic','Fair',1200,'SL23'),
 ('EQ36','Hospital Bed','Invacare','5307IVC','Excellent',3755,'SL22'),
 ('EQ38','Hospital Bed','Hillrom','Advanta 2','Good',3200,'SL23'),
 ('EQ21','Oxygen Concentrator','Philips','EverFlo 5L','Good',6000,'SL23'),
 ('EQ16','Nebulizer','Omron','NE-C801','Fair',850,'SL23');

INSERT INTO renter VALUES
 ('CR76','Jun Brian','Tubongbanua',4000,'B3'),
 ('CR77','Maria','Santos',7000,'B3');

INSERT INTO rental_agreement VALUES
 ('RA1001','CR76','EQ16', 850,'2026-06-15','2026-09-15'),
 ('RA1002','CR77','EQ36',3755,'2026-07-01','2026-12-31'),
 ('RA1003','CR76','EQ45',1550,'2026-07-20','2026-10-20'),
 ('RA1004','CR77','EQ21',6000,'2026-08-05','2027-02-05');


-- ============================================================================
-- Task 1 — Create a View (25 pts)
-- ============================================================================
CREATE OR REPLACE VIEW v_active_rentals AS
SELECT a.agreement_no,
       CONCAT(r.first_name, ' ', r.last_name) AS renter,
       e.category,
       e.brand,
       a.monthly_rent,
       a.date_start,
       a.date_finish
FROM rental_agreement a
JOIN renter    r ON a.renter_no = r.renter_no
JOIN equipment e ON a.equip_no  = e.equip_no
WHERE a.date_finish >= CURDATE()
;

-- Query the view
SELECT renter, category, monthly_rent
FROM   v_active_rentals
WHERE  monthly_rent > 2000
ORDER  BY monthly_rent DESC;


-- ============================================================================
-- Task 2 — Aggregate Window Function (25 pts)
-- ============================================================================
SELECT category,
       equip_no,
       monthly_rate,
       AVG(monthly_rate) OVER ( PARTITION BY category )
                                          AS avg_in_category
FROM   equipment
ORDER  BY category, monthly_rate DESC;


-- ============================================================================
-- Task 3 — Ranking Window Functions (30 pts)
-- ============================================================================
SELECT category,
       equip_no,
       monthly_rate,
       ROW_NUMBER() OVER w AS rn,
       RANK()       OVER w AS rnk,
       DENSE_RANK() OVER w AS drnk
FROM   equipment
WINDOW w AS ( PARTITION BY category ORDER BY monthly_rate DESC )
ORDER  BY category, rn;


-- ============================================================================
-- Bonus — Running Total (+5 pts, optional)
-- ============================================================================
SELECT agreement_no,
       date_start,
       monthly_rent,
       SUM(monthly_rent) OVER (ORDER BY date_start
            ROWS BETWEEN UNBOUNDED PRECEDING
                     AND CURRENT ROW) AS running_total
FROM   rental_agreement
ORDER  BY date_start;
