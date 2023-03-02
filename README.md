# Документация:
1. [План тестирования](https://github.com/Vito-jj/Diplom-QA46/blob/main/documentation/Plan.md)
2. [Отчет по итогам автоматизации](https://github.com/Vito-jj/Diplom-QA46/blob/main/documentation/Summary.md)
3. [Отчет по итогам тестирования](https://github.com/Vito-jj/Diplom-QA46/blob/main/documentation/Report.md)

# Задача:
Автоматизировать позитивные и негативные сценарии покупки тура в комплексном сервисе, взаимодействующем с СУБД и API Банка.
![image](https://user-images.githubusercontent.com/111686304/222439553-cbfff26c-3e61-4461-8b5a-5c60c15eaabc.png)
Приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

# Инструкция подключения БД и запуска SUT:
1. Склонировать проект из репозитория командой: `git clone`
2. Открыть склонированный проект в Intellij IDEA
3. Запуск тестов:
    - С использованием MySQL:
        - для запуска контейнеров с MySQL и Node.js ввести в терминале команды:
            - сначала: `docker-compose up node_app -d --force-recreate`
            - затем: `docker-compose up mysql_service -d --force-recreate`
        - для запуска SUT ввести в терминале команду: `java -jar artifacts/aqa-shop.jar`
        - для запуска тестов и получения отчета Allure в браузере использовать команду: `./gradlew allureserve`
    - С использованием PostgreSQL:
        - для запуска контейнеров с PostgreSQL и Node.js ввести в терминале команды:
            - сначала: `docker-compose up node_app -d --force-recreate`
            - затем: `docker-compose up postgres_service -d --force-recreate`
        - для запуска SUT ввести в терминале команду: `java -jar artifacts/aqa-shop.jar`
        - для запуска тестов и получения отчета Allure в браузере использовать команду: `./gradlew allureserve`
4. После окончания тестов завершить работу приложения (Ctrl + C), остановить контейнеры командой: `docker-compose down`
