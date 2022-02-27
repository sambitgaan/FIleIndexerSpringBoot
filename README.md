# FIleIndexerSpringBoot


#Maria DB installation steps 
Step1:
Go to "https://mariadb.org/download"

Step2:
Download and install 10.6.7 version .

Step3: 
Install Dbeaver 21.0.1 for better data appearance.

Step4: 
Execute below queries

**Table Data Schema:**
**User table creation:**

CREATE TABLE users (
user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_name VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

**Config Table schema:**

INSERT INTO config (dir_path , index_dir_path , removed_files_log_path, files_log_path, user_id, created_at, updated_at)
VALUES ('Test', 'IndexDir', 'removelog.txt', 'filelist.txt', 3, '2022-02-12', '2022-02-22');

CREATE TABLE config (
config_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_id int,
dir_path VARCHAR(400) NOT NULL,
index_dir_path VARCHAR(400) NOT NULL,
removed_files_log_path VARCHAR(400) NOT NULL,
searched_files_path_log VARCHAR(400) NOT NULL,
files_log_path VARCHAR(400) NOT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);





