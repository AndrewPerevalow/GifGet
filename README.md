Описание:
---
```
Сервис получения gif в зависимости от курса валют
```
Инструкция по запуску:
---
```
1. Скопировать из удаленного репозитория в локальный репозиторий проект
2. В терминале среды разработки выполнить команды: Gradle -clean и -build
3. Запустить исполняемый файл MainAppStart.java
```
Endpoints:
--- 
Главная страница:
```
GET localhost:8080/ 
```
Получить весь список валют:
```
GET localhost:8080/getvalues
```
Получить gif:
```
GET localhost:8080/getcurrentgif/{getValue}
где getValue - любая валюта из списка валют (пример: localhost:8080/getcurrentgif/RUB)
```