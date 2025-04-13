Recipe Collection Organizer
Organizes recipes, allowing users to add, edit, and delete recipes along with managing categories and ingredients.

Student: Dzhanteliev Adilkhan
Date: 17th april 12:10-12:40


Описание проекта:
Данный проект реализует мини-приложение для управления и работой над рецептами. 
Пользователь может добавлять, редактировать, просматривать и удалять рецепты. 
Также реализованы расширенные функции, такие как поиск, фильтрация, избранное, экспорт/импорт JSON, графический интерфейс (Swing) и подключение к базе данных (SQLite).

Основные требования (реализовано):
1) Implement CRUD operations: Users should be able to create, read, update, and delete records.
2) Command Line Interface: The interface should be user-friendly with clear menus and prompts.
3) Input Validation: Prevent invalid data entry (e.g., incorrect email format, empty inputs).
4) Data Persistence: Data should be stored in files to ensure persistence between different sessions.
5) Modular Design: Divide your code into logical modules/functions for better organization and reusability.
6) Report Generation: Generate summary reports (e.g., total users, most frequent operations, user activity logs).
7) Documentation: Provide clear documentation in Readme file for better understanding.
8) Error Handling: Implement appropriate error handling mechanisms to handle unexpected situations gracefully.

Я писал на языке программирования Java. Также пользовался библиотекой JSON.simple 1.1.1, swing для графического интерфейса, Javadoc: Для всех основных классов. 



Также я реализовал 10 методов над моим проектом: 

1. Просмотр всех рецептов

2. Поиск по ингридиенту

3. Фильтрация по категориям блюд

4. Сортировка по времени приготовления

5. Избранные рецепты

6. Запланированные рецепты

7. Расширенный поиск (по id, категории, ингридиенту, блюду и тд)

8. Редактирование рецепта

9. Импорт и экспорт JSON

10. Отображение даты и времени создания рецепта

11. Удаление рецепта


Еще я выполнил дополнительные задания (2 из 3)

1) Графический интерфейс на Swing
2) SQL database integration


Документация моего проекта: 

1) Основные алгоритмы:
CRUD – создание, просмотр, редактирование, удаление рецептов
Поиск – по ингредиенту, категории, ID и другим
Фильтрация и сортировка – по категории и времени приготовления
Импорт/экспорт с JSON файлами
Автогенерация ID – создается уникальный ID

2) База данных (SQLite)
Таблица recipes содержит поля: id, name, description, ingredients, steps, category, cookingTime, servingSize, isFavorite, createdDate, inPlan) и тд
Используется JDBC для выполнения SQL-запросов. Также идет запись данных в json-file.

3) Проблемы:
Я не знал, как реализовать графический интерфейс, валидацию данных и SQL и мне пришлось искать дополнительную информацию.


.............................................................................................................................................................................................................................................


Project Description:
This project implements a mini-application for managing and working with recipes.
The user can add, edit, view and delete recipes.
Also implemented are advanced features such as search, filtering, favorites, JSON export/import, graphical interface (Swing) and database connection (SQLite).

Main requirements (implemented):
1) Implement CRUD operations: Users should be able to create, read, update, and delete records.
2) Command Line Interface: The interface should be user-friendly with clear menus and prompts.
3) Input Validation: Prevent invalid data entry (e.g., incorrect email format, empty inputs).
4) Data Persistence: Data should be stored in files to ensure persistence between different sessions.
5) Modular Design: Divide your code into logical modules/functions for better organization and reusability.
6) Report Generation: Generate summary reports (e.g., total users, most frequent operations, user activity logs).
7) Documentation: Provide clear documentation in the Readme file for better understanding.
8) Error Handling: Implement appropriate error handling mechanisms to handle unexpected situations gracefully.

I wrote in the Java programming language. I also used the JSON.simple 1.1.1 library, swing for the graphical interface, Javadoc: For all the main classes.



I also implemented 10 methods on my project:

1. View all recipes

2. Search by ingredient

3. Filter by dish categories

4. Sort by cooking time

5. Favorite recipes

6. Scheduled recipes

7. Advanced search (by id, category, ingredient, dish, etc.)

8. Edit recipe

9. Import and export JSON

10. Display date and time of recipe creation

11. Delete recipe



I also completed additional tasks (2 of 3)

1) Swing GUI
2) SQL database integration



Documentation of my project:

1) Main algorithms:
CRUD – creating, viewing, editing, deleting recipes
Search – by ingredient, category, ID and others
Filtering and sorting – by category and cooking time
Import/export with JSON files
ID autogeneration – a unique ID is created

2) Database (SQLite)
The recipes table contains the fields: id, name, description, ingredients, steps, category, cookingTime, servingSize, isFavorite, createdDate, inPlan) etc.
JDBC is used to execute SQL queries. Data is also written to a json file.

3) Problems:
I didn't know how to implement a graphical interface, data validation and SQL and I had to look for additional information.
