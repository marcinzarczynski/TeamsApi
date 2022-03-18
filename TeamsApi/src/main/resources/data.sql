DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS task;

CREATE TABLE task (
  task_id int NOT NULL,
  title varchar(250) NOT NULL,
  task_description varchar(10000),
  realization_date date NOT NULL,
  status varchar(50) NOT NULL,
  PRIMARY KEY (task_id)
);

CREATE TABLE user (
  user_id int NOT NULL,
  task_id int,
  name varchar(250) NOT NULL,
  last_name varchar(250) NOT NULL,
  email varchar(250)  NOT NULL,
  PRIMARY KEY (user_id)
);