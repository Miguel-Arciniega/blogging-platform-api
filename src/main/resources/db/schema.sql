-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS blog_db;
USE blog_db;

-- Create the POST table
CREATE TABLE IF NOT EXISTS BLOG_POST (
                                               ID BIGINT NOT NULL AUTO_INCREMENT,
                                               TITLE VARCHAR(255) NOT NULL,
                                               CONTENT TEXT,
                                               CATEGORY VARCHAR(255),
                                               TAGS JSON,
                                               CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP,
                                               UPDATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                               PRIMARY KEY (id)
);
