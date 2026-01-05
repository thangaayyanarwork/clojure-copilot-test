(ns db
  (:require [clojure.java.jdbc :as jdbc])
  (:import (java.sql DriverManager)))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "todos.db"})

(defn init-db []
  "Initialize the database with todos table"
  (try
    (jdbc/execute! db-spec
      ["CREATE TABLE IF NOT EXISTS todos (
         id INTEGER PRIMARY KEY AUTOINCREMENT,
         title TEXT NOT NULL,
         completed BOOLEAN DEFAULT 0,
         created_at DATETIME DEFAULT CURRENT_TIMESTAMP)"])
    (println "Database initialized successfully")
    (catch Exception e
      (println "Error initializing database:" (.getMessage e)))))

(defn add-todo [title]
  "Add a new todo"
  (jdbc/insert! db-spec :todos {:title title :completed 0}))

(defn get-todos []
  "Get all todos"
  (jdbc/query db-spec "SELECT * FROM todos ORDER BY created_at DESC"))

(defn get-todo [id]
  "Get a specific todo by id"
  (first (jdbc/query db-spec ["SELECT * FROM todos WHERE id = ?" id])))

(defn update-todo [id title completed]
  "Update a todo"
  (jdbc/update! db-spec :todos {:title title :completed completed} ["id = ?" id]))

(defn toggle-todo [id]
  "Toggle the completed status of a todo"
  (let [todo (get-todo id)]
    (update-todo id (:title todo) (if (= (:completed todo) 1) 0 1))))

(defn delete-todo [id]
  "Delete a todo"
  (jdbc/delete! db-spec :todos ["id = ?" id]))