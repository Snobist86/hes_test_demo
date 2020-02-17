Задание:
Разработать систему управления пользователями. Система должно представлять собой WEB-приложение, которое предоставляет интерфейс
управления пользователями.

Перед запуском программы необходимо:
1) Создать бд (в Postgresql, если эта СУБД не установлена, то установить) с именем "test_for_hes_fintech_database".
2) В файле application.properties установить данные (username и password) для подключения к бд.

После:
1)После запуска будет создан дефолтный пользователь(user name: first_user, password: 123qwerty), который может быть использован для создания других пользователей.
2) Чтобы избежать дублирование в бд дефолтного пользователя, после первого запуска (и если приложение будет запускаться больше одного раза) следует закомментировать/удалить аннотацию @PostConstract над методом createFirstUser().
