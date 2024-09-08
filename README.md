# Library-API

## Требования перед запуском
1) Убедиться что порты компьютера 8080, 8081, 5433, 5434 не заняты
2) Установленный Docker и Docker compose

## Инструкция по запуску
### Linux:
1) Перейти в директорию, где бы вы хотели чтобы располагался проект
2) `git clone https://github.com/Vitya441/Library-web-api` - клонировать репозиторий на свою машину
3) `cd Library-web-api` - перейти в корневую директорию проекта
4) `docker compose up` - запуск 

### Windows:
Все тоже самое, кроме последнего пункта:  
`docker-compose up` - запуск 

## Документация Swagger
`http://localhost:8080/swagger-ui/index.html` - Документация для сервиса book-service

`http://localhost:8081/swagger-ui/index.html` - Документация для сервиса library-service
