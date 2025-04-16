# 🍲 Recipe Collection Organizer

**Developed by:** Dzhanteliev Adilkhan  
**Date:** 17th April, 12:10–12:40  

---

## 📘 Project Description

A Java-based recipe management application that allows users to add, edit, delete, and search recipes.  
Supports both **console (CLI)** and **graphical (Swing GUI)** modes, JSON import/export, and SQLite database integration using JDBC.

## 🎯 Project Goal  
To build a reliable, user-friendly, and modular recipe management application with full CRUD functionality,
advanced search and filtering, database persistence, and modern UI.

### 🧾 Key Features:
- ✅ Full CRUD operations with validation
- ✅ Ingredient and category management
- ✅ Advanced search and filtering
- ✅ Favorites and planned recipes
- ✅ JSON import/export support
- ✅ Swing-based GUI
- ✅ SQLite database integration via JDBC

---

## 🎯 Project Objectives AND Requirements:

| Objective                        | Status            |
|----------------------------------|-------------------|
| Implement CRUD Operations        | ✅ Completed       |
| Command Line Interface           | ✅ Dual Support    |
| Input Validation                 | ✅ Implemented     |
| Data Persistence:                | ✅ Implemented     |
| Modular Design                   | ✅ Ready           |
| Report Generation                | ✅ Done            |
| JSON Import/Export               | ✅ Supported       |
| Error Handling                   | ✅ Auto-increment  |
| Documentation (Javadoc + Readme) | ✅ Structured      |

---
### 🧩 Additional Tasks (2/3):
- **Graphical User Interface (GUI) Implementation (Swing library)**
- **Database Integration (SQL)**


## ⚙️ Application Features:

### 🔄 CRUD Operations
- **Create:** Add new recipes (via CLI or GUI)
- **Read:** View all recipes or search by ID
- **Update:** Edit recipe details, including ingredients/steps
- **Delete:** Remove by ID, category, or ingredient

### 🔍 Search and Filtering
- Search by ingredient
- Filter by category
- Advanced search by ID, name, cooking time, category, servings

---

## 📋 Recipe Fields

- `id`, `name`, `description`
- `ingredients`, `steps`
- `category`, `cookingTime`, `servingSize`
- `isFavorite`, `createdDate`, `inPlan`

---

## 🖥️ User Interface

### ✨ 10 Key Features:

➕ Add, Remove and Edit Recipes – Full CRUD functionality via Console and GUI

📖 View All Recipes – Browse your recipe collection

🔍 Search by Ingredient – Find recipes that include specific ingredients

🏷️ Filter by Category – Narrow recipes by type (e.g., Dessert, Main Dish)

⏱️ Sort by Cooking Time – Order recipes by preparation duration

⭐ Favorite Recipes – Mark and view your favorite recipes

📅 Planned Recipes – Add recipes to a cooking plan

🧠 Advanced Search – Search using multiple criteria (name, category, time, servings)

📤 Import and Export JSON – Save and load recipes from JSON files

📆 Created Date Tracking – Automatically records when each recipe was added

🗃️ SQL Database (SQLite) – Recipes stored persistently using JDBC + SQLite


## ✅ 10 KEY Functionalities:

| # | Feature                          | Description |
|--:|----------------------------------|-------------|
| 1 | ➕ **Add, Edit, Delete Recipes** | Full CRUD support via GUI and CLI |
| 2 | 📖 **View All Recipes**          | Display recipe list |
| 3 | 🔍 **Search by Ingredient**      | Search for matching items |
| 4 | 🏷️ **Filter by Category**        | Filter based on recipe type |
| 5 | ⏱️ **Sort by Cooking Time**      | Sort recipes by preparation time |
| 6 | ⭐ **Favorite Recipes**          | Mark recipes as favorites |
| 7 | 📅 **Planned Recipes**           | Add recipes to a cooking schedule |
| 8 | 🧠 **Advanced Search**           | Search by name, time, servings, etc. |
| 9 | 📤 **Import/Export JSON**        | Backup and restore recipes |
|10 | 📆 **Created Date Tracking**     | Timestamp when a recipe was created |
|11 | 🗃️ **SQL Database (SQLite)**     | Persistent storage with JDBC |




### 📂 SQLite Table: recipes

| Column	       | Type	           | Description                                  |
|---------------:|----------------:|----------------------------------------------|
| id	           | INTEGER	       | Auto-incremented recipe ID (Primary Key)     |
| name	         | TEXT	           | Name of the recipe                           |
| description    | TEXT	           | Short description of the dish                |
| ingredients    | TEXT	           | List of ingredients (semicolon-separated)    |
| steps	         | TEXT	           | List of cooking steps (semicolon-separated)  |
| category	     | TEXT	           | Recipe category (e.g., Main, Dessert)        |
| cookingTime    | TEXT	           | Time in minutes                              |
| servingSize	   | TEXT	           | Number of servings                           |
| isFavorite	   | BOOLEAN	       | true if marked as favorite                   |
| createdDate	   | TEXT	           | Date of creation (yyyy-MM-dd)                |
| inPlan	       | BOOLEAN	       | true if in cooking plan                      |


### 📁 JSON Example
```json
{
  "id": 1,
  "name": "Borscht",
  "description": "Traditional soup",
  "ingredients": ["Beetroot", "Cabbage", "Meat"],
  "steps": ["Boil water", "Add ingredients"],
  "category": "Main",
  "cookingTime": "60",
  "servingSize": "4",
  "isFavorite": true,
  "createdDate": "2025-04-10",
  "inPlan": false
} 
```

🧪 **Input Validation**  
- Ensures non-empty fields for name, ingredients, etc.  
- Prevents duplicate IDs  
- Validates numeric inputs (time, servings)


💾 **Persistent Data**  
- `recipes.db`: SQLite database for all recipes
- `recipes.json` : there is added recipes

📊 **Report Generation**

- generateReport(): Outputs all recipe stats
- getPlannedRecipes(): Shows all planned meals
- getFavorites(): Lists favorite recipes


⚠️ **Error Handling**

- Try-catch for file I/O & database exceptions
- User-friendly error messages via GUI/CLI


### 🧠 Algorithms
📈 **Search & Sort**

- Search by ingredients, category, time, servings
- Sort by cooking time, name or ID

### 📊 Log Frequency Analysis

- Uses HashMap<String, Integer> to track most common actions
- Generates top-used feature stats


## 📦 Java Class: `Recipe.java`

```java
public class Recipe {
    private int id;
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String category;
    private String cookingTime;
    private String servingSize;
    private boolean isFavorite;
    private String createdDate;
    private boolean inPlan;
}
```

### ✅ Run Instructions

1. Open project in IntelliJ or any Java IDE
2. Ensure recipes.db and JSON files are in the project directory
3. Compile and run Main.java
4. Use CLI or GUI to interact with the app
5. Choose role: Admin or User
6. Perform operations from menus (Add, Edit, Search, etc.)

### 📌Notes:
I didn't know how to create a GUI and sql tables, so I had to look for more information.

### ✅ OUTPUTS:

![image](https://github.com/user-attachments/assets/96a003f2-7efd-42e2-b28e-93f3d35b080e)

![image](https://github.com/user-attachments/assets/afcc6783-1e78-4397-93c5-e2152216c66f)

![image](https://github.com/user-attachments/assets/796f3f0b-b8ed-4df7-a8b2-cf1cdca5446b)

![image](https://github.com/user-attachments/assets/5b0b8064-a2e0-4ba7-a01e-9685da411599)

![image](https://github.com/user-attachments/assets/ce4741c2-1f62-4347-abad-ebf62d149b6c)

![image](https://github.com/user-attachments/assets/66b6a29b-082b-4f70-b58d-28bec3ece490)

![image](https://github.com/user-attachments/assets/309c447d-84f0-45a5-8b81-637e7f6b11ba)

![image](https://github.com/user-attachments/assets/5a1daad0-3d64-4127-8403-6fa9ffce9417)

