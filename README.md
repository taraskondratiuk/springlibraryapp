# springlibraryapp
Library app (Spring)




Существуют роли: Администратор, Читатель.
Администратор может добавлять и удалять книги, отслеживать взятые книги.
Читатель может брать книги и возвращать их.

Установка



Загрузить проект
Создать новую схему в MySQL Workbench с названием library_app_db 
Выполнить в MySql: 
insert into users(email, password, country_code, phone_number) values ("admin@gmail.com", "$2a$12$c8OaVylzZzyshtwXy1ycDuEXdDAYwhNRqEvUv1toTvyBJZRsEpvU2", 380, 999259622);
inser into roles (role_name) value ("READER");
inser into roles (role_name) value ("ADMIN");
insert into user_roles (user_id, role_id) values (1, 2);

Запуск



Открыть html файл
Зайти на админа и добавить книг

(логин/пароль/роль)
admin@gmail.com/password/admin
