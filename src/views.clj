(ns views
  (:require [hiccup.page :as page]
            [hiccup.form :as form]))

(defn layout [title content]
  (page/html5
    {:lang "en"}
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     [:title title]
     [:style
      "* { margin: 0; padding: 0; box-sizing: border-box; }
       body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
       .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
       h1 { color: #333; margin-bottom: 30px; text-align: center; }
       .add-todo { display: flex; gap: 10px; margin-bottom: 30px; }
       input[type=\"text\"] { flex: 1; padding: 10px; border: 2px solid #667eea; border-radius: 5px; font-size: 16px; }
       button { padding: 10px 20px; background: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
       button:hover { background: #764ba2; }
       .todo-item { display: flex; align-items: center; padding: 15px; border: 1px solid #ddd; border-radius: 5px; margin-bottom: 10px; background: #f9f9f9; }
       .todo-item.completed { opacity: 0.6; }
       .todo-item.completed .todo-text { text-decoration: line-through; color: #999; }
       .todo-checkbox { width: 20px; height: 20px; margin-right: 15px; cursor: pointer; }
       .todo-text { flex: 1; color: #333; }
       .todo-actions { display: flex; gap: 10px; }
       .delete-btn { background: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer; }
       .delete-btn:hover { background: #c0392b; }
       .empty-state { text-align: center; color: #999; padding: 40px 20px; }
      "]]
    [:body
     [:div.container
      content]]))

(defn index-page [todos]
  (layout "Todo App"
    [:div
     [:h1 "📝 My Todos"]
     [:form.add-todo {:action "/add" :method "post"}
      [:input {:type "text" :name "title" :placeholder "Add a new todo..." :required true}]
      [:button {:type "submit"} "Add"]]
     (if (empty? todos)
       [:div.empty-state "No todos yet. Add one to get started!"]
       [:div.todos
        (for [todo todos]
          [:div.todo-item {:class (when (= (:completed todo) 1) "completed")}
           [:form {:style "display: flex; width: 100%; align-items: center;"
                   :action (str "/toggle/" (:id todo))
                   :method "post"
                   :style "display: flex; align-items: center; flex: 1; margin: 0; padding: 0;"}
            [:input.todo-checkbox {:type "checkbox" :checked (= (:completed todo) 1) :onchange "this.form.submit()"}]
            [:span.todo-text (:title todo)]]
           [:form {:action (str "/delete/" (:id todo))
                   :method "post"
                   :style "margin: 0;"}
            [:button.delete-btn {:type "submit"} "Delete"]]])])]))

(defn not-found []
  (layout "Page Not Found"
    [:div
     [:h1 "404 - Page Not Found"]
     [:p "The page you're looking for doesn't exist."]
     [:a {:href "/"} "← Go back home"]]))
