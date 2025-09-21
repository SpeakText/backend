admin

+------------+-------------+------+-----+---------+----------------+
| Field      | Type        | Null | Key | Default | Extra          |
+------------+-------------+------+-----+---------+----------------+
| admin_id   | bigint      | NO   | PRI | NULL    | auto_increment |
| identifier | varchar(64) | NO   | UNI | NULL    |                |
| name       | varchar(64) | NO   |     | NULL    |                |
| password   | varchar(64) | NO   |     | NULL    |                |
+------------+-------------+------+-----+---------+----------------+

author
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| author_id  | bigint       | NO   | PRI | NULL    | auto_increment |
| email      | varchar(200) | NO   |     | NULL    |                |
| identifier | varchar(64)  | NO   | UNI | NULL    |                |
| name       | varchar(64)  | NO   |     | NULL    |                |
| password   | varchar(64)  | NO   |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+

describe member;
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| member_id  | bigint       | NO   | PRI | NULL    | auto_increment |
| email      | varchar(200) | NO   |     | NULL    |                |
| identifier | varchar(64)  | NO   | UNI | NULL    |                |
| name       | varchar(64)  | NO   |     | NULL    |                |
| password   | varchar(64)  | NO   |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
5 rows in set (0.01 sec)

pending_book;
+-----------------------+---------------------------------------------------------+------+-----+---------+----------------+
| Field                 | Type                                                    | Null | Key | Default | Extra          |
+-----------------------+---------------------------------------------------------+------+-----+---------+----------------+
| pending_book_id       | bigint                                                  | NO   | PRI | NULL    | auto_increment |
| author_id             | bigint                                                  | YES  |     | NULL    |                |
| cover_url             | varchar(255)                                            | NO   |     | NULL    |                |
| description           | varchar(255)                                            | NO   |     | NULL    |                |
| identification_number | varchar(255)                                            | NO   | UNI | NULL    |                |
| inspection_status     | enum('APPROVED','DONE','PENDING','REJECTED','SCRIPTED') | NO   |     | NULL    |                |
| is_scripted           | bit(1)                                                  | NO   |     | NULL    |                |
| price                 | decimal(38,2)                                           | NO   |     | NULL    |                |
| title                 | varchar(255)                                            | NO   |     | NULL    |                |
+-----------------------+---------------------------------------------------------+------+-----+---------+----------------+
9 rows in set (0.01 sec)

pending_book_chunk;
+-----------------------+---------------------------------+------+-----+---------+----------------+
| Field                 | Type                            | Null | Key | Default | Extra          |
+-----------------------+---------------------------------+------+-----+---------+----------------+
| pending_book_chunk_id | bigint                          | NO   | PRI | NULL    | auto_increment |
| chunk                 | varchar(2000)                   | YES  |     | NULL    |                |
| identification_number | varchar(255)                    | YES  |     | NULL    |                |
| chunk_index           | bigint                          | YES  |     | NULL    |                |
| status                | enum('FAILED','PENDING','SENT') | YES  |     | NULL    |                |
+-----------------------+---------------------------------+------+-----+---------+----------------+
5 rows in set (0.00 sec)

describe reading_progress;
+------------------------------+--------+------+-----+---------+----------------+
| Field                        | Type   | Null | Key | Default | Extra          |
+------------------------------+--------+------+-----+---------+----------------+
| reading_progress_id          | bigint | NO   | PRI | NULL    | auto_increment |
| current_index                | bigint | YES  |     | NULL    |                |
| member_member_id             | bigint | YES  | MUL | NULL    |                |
| selling_book_selling_book_id | bigint | YES  | MUL | NULL    |                |
+------------------------------+--------+------+-----+---------+----------------+
4 rows in set (0.01 sec)

mysql> describe script;
+-----------------------+-------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
| Field                 | Type                                                                                                                                | Null | Key | Default | Extra          |
+-----------------------+-------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
| script_id             | bigint                                                                                                                              | NO   | PRI | NULL    | auto_increment |
| author_id             | bigint                                                                                                                              | YES  |     | NULL    |                |
| fragments_count       | int                                                                                                                                 | NO   |     | NULL    |                |
| identification_number | varchar(255)                                                                                                                        | YES  |     | NULL    |                |
| is_completed          | bit(1)                                                                                                                              | NO   |     | NULL    |                |
| merged_voice_path     | varchar(255)                                                                                                                        | YES  |     | NULL    |                |
| narration_voice       | enum('FEMALE_BRIGHT','FEMALE_CASUAL','FEMALE_CHILD','FEMALE_CLEAR','FEMALE_SOFT','MALE_DEEP','MALE_NEUTRAL','MALE_SOFT','NO_VOICE') | YES  |     | NULL    |                |
| title                 | varchar(255)                                                                                                                        | YES  |     | NULL    |                |
| total_fragments       | int                                                                                                                                 | NO   |     | NULL    |                |
| voice_length_info     | longtext                                                                                                                            | YES  |     | NULL    |                |
| voice_status          | enum('FRAGMENTS_VOICE_GENERATED','MERGED_VOICE_GENERATED','MERGE_REQUESTED','NOT_GENERATED')                                        | YES  |     | NULL    |                |
+-----------------------+-------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
11 rows in set (0.01 sec)

script_character;
+----------------------+--------------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
| Field                | Type                                                                                                                                       | Null | Key | Default | Extra          |
+----------------------+--------------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
| character_id         | bigint                                                                                                                                     | NO   | PRI | NULL    | auto_increment |
| appeared_in_script   | bit(1)                                                                                                                                     | NO   |     | NULL    |                |
| character_key        | varchar(255)                                                                                                                               | YES  |     | NULL    |                |
| character_voice_type | enum('FEMALE_ELDERLY','FEMALE_HIGH','FEMALE_LOW','FEMALE_MID','MALE_HIGH','MALE_LOW','MALE_MID','MALE_UNIQUE','NEUTRAL_UNIQUE','NO_VOICE') | YES  |     | NULL    |                |
| description          | varchar(2000)                                                                                                                              | YES  |     | NULL    |                |
| name                 | varchar(255)                                                                                                                               | YES  |     | NULL    |                |
| script_id            | bigint                                                                                                                                     | YES  | MUL | NULL    |                |
+----------------------+--------------------------------------------------------------------------------------------------------------------------------------------+------+-----+---------+----------------+
7 rows in set (0.01 sec)

mysql> describe selling_book;
+-----------------------+---------------+------+-----+---------+----------------+
| Field                 | Type          | Null | Key | Default | Extra          |
+-----------------------+---------------+------+-----+---------+----------------+
| selling_book_id       | bigint        | NO   | PRI | NULL    | auto_increment |
| author_id             | bigint        | YES  |     | NULL    |                |
| cover_url             | varchar(255)  | NO   |     | NULL    |                |
| description           | varchar(255)  | NO   |     | NULL    |                |
| identification_number | varchar(255)  | NO   | UNI | NULL    |                |
| is_deleted            | bit(1)        | NO   |     | NULL    |                |
| price                 | decimal(38,2) | NO   |     | NULL    |                |
| title                 | varchar(255)  | NO   |     | NULL    |                |
+-----------------------+---------------+------+-----+---------+----------------+
8 rows in set (0.00 sec)