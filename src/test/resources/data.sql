insert into role(id,nome) values (1, 'ROLE_USER');
insert into role(id,nome) values (2, 'ROLE_ADMIN');

insert into user(id,nome,email,login,senha) values (1,'Admin','admin@gmail.com','admin','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');
insert into user(id,nome,email,login,senha) values (2,'User','user@gmail.com','user','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');

insert into user_roles(user_id,role_id) values(1, 2);
insert into user_roles(user_id,role_id) values(2, 1);

INSERT INTO movie (id, name, plot, image, year, duration) VALUES
(1, 'Power Rangers: The Movie', 'A bunch of teenagers saving the world.', 'https://upload.wikimedia.org/wikipedia/en/1/11/Power_rangers_movie_poster.jpg', 1995, 95),
(2, 'The Exorcist', 'Two priests saving a possessed chick.', 'https://upload.wikimedia.org/wikipedia/en/thumb/7/7b/Exorcist_ver2.jpg/220px-Exorcist_ver2.jpg', 1973, 132),
(3, 'O Auto da Compadecida', 'Two funny dudes in Brazil.', 'https://pt.wikipedia.org/wiki/O_Auto_da_Compadecida_(filme)#/media/Ficheiro:O_auto_da_compadecida.jpg', 2000, 104);