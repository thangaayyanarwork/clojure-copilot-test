# Clojure Todo Web App

A simple CRUD (Create, Read, Update, Delete) web application for managing todos, built with Clojure.

## Tech Stack

- **Framework**: [http-kit](http://www.http-kit.org/) - Lightweight HTTP server
- **HTML Templating**: [Hiccup](https://github.com/weavejester/hiccup) - HTML as Clojure data
- **Database**: SQLite with [clojure.jdbc](https://github.com/funcool/clojure.jdbc)
- **Dependency Management**: `deps.edn`

## Features

- ✅ Create new todos
- ✅ View all todos
- ✅ Mark todos as complete/incomplete
- ✅ Delete todos
- 🎨 Clean, responsive UI with gradient background
- 📦 Lightweight and fast

## Project Structure

```
clojure-todo-app/
├── deps.edn          # Project dependencies
├── README.md         # This file
└── src/
    ├── core.clj      # Main HTTP server and routing
    ├── db.clj        # Database operations (CRUD)
    └── views.clj     # HTML templates using Hiccup
```

## Installation & Setup

### Prerequisites
- JDK 11 or higher
- Clojure tools installed

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/thangaayyanarwork/clojure-copilot-test.git
cd clojure-copilot-test
```

2. Run the application:
```bash
clojure -M -m core
```

3. Open your browser and navigate to:
```
http://localhost:3000
```

## Usage

### Add a Todo
- Type your todo title in the input field
- Click the "Add" button
- Your todo will appear in the list

### Complete a Todo
- Click the checkbox next to any todo
- It will be marked as completed with strikethrough text

### Delete a Todo
- Click the "Delete" button next to any todo
- It will be permanently removed

## Database

The application uses SQLite for data persistence. The database file `todos.db` is automatically created in the project root when you first run the application.

### Todo Schema

```sql
CREATE TABLE todos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    completed BOOLEAN DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Display all todos |
| POST | `/add` | Add a new todo |
| POST | `/toggle/:id` | Toggle completion status of a todo |
| POST | `/delete/:id` | Delete a todo |

## Development

### Running in REPL
You can run the application in a Clojure REPL for development:

```clojure
(require '[core])
(core/-main)

;; Test database operations
(require '[db])
(db/add-todo "Learn Clojure")
(db/get-todos)
(db/toggle-todo 1)
(db/delete-todo 1)
```

## Dependencies

- **clojure** - Core language (v1.11.1)
- **http-kit** - HTTP server library (v2.8.0)
- **hiccup** - HTML templating (v2.0.0-RC1)
- **sqlite-jdbc** - SQLite JDBC driver (v3.44.0.0)
- **clojure.jdbc** - JDBC wrapper for Clojure (v0.7.12)

## License

MIT License - Feel free to use this project for any purpose.

## Author

Created with ❤️ using Clojure