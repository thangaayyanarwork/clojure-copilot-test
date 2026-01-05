(ns core
  (:require [org.httpkit.server :as server]
            [db]
            [views])
  (:gen-class))

(defn app [req]
  "Main request handler"
  (let [method (:request-method req)
        uri (:uri req)]
    (cond
      ;; GET /
      (and (= method :get) (= uri "/"))
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (views/index-page (db/get-todos))}

      ;; POST /add
      (and (= method :post) (= uri "/add"))
      (let [body (slurp (:body req))
            title (second (clojure.string/split body #"[=&]"))]
        (db/add-todo (java.net.URLDecoder/decode title "UTF-8"))
        {:status 302
         :headers {"Location" "/"}})

      ;; POST /toggle/:id
      (and (= method :post) (re-matches #"/toggle/\d+" uri))
      (let [id (Integer/parseInt (last (clojure.string/split uri #"/")))]
        (db/toggle-todo id)
        {:status 302
         :headers {"Location" "/"}})

      ;; POST /delete/:id
      (and (= method :post) (re-matches #"/delete/\d+" uri))
      (let [id (Integer/parseInt (last (clojure.string/split uri #"/")))]
        (db/delete-todo id)
        {:status 302
         :headers {"Location" "/"}})

      ;; 404
      :else
      {:status 404
       :headers {"Content-Type" "text/html"}
       :body (views/not-found)})))

(defn -main [& args]
  "Start the HTTP server"
  (db/init-db)
  (let [port 3000]
    (server/run-server app {:port port})
    (println (str "Server running at http://localhost:" port))))

(comment
  ;; Start the server
  (-main)
  
  ;; Test database operations
  (db/add-todo "Learn Clojure")
  (db/get-todos)
  (db/toggle-todo 1)
  (db/delete-todo 1)
  )