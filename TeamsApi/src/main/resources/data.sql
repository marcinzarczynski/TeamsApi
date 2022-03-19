DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS task;

CREATE TABLE task (
  task_id int NOT NULL,
  user_id int,
  title varchar(250) NOT NULL,
  task_description varchar(10000),
  date date NOT NULL,
  status varchar(50) NOT NULL,
  PRIMARY KEY (task_id)
);

CREATE TABLE user (
  user_id int NOT NULL,
  name varchar(250) NOT NULL,
  last_name varchar(250) NOT NULL,
  email varchar(250)  NOT NULL,
  PRIMARY KEY (user_id)
);