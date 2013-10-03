(ns alex-comma-game.layout
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]
            [alex-comma-game.game :as game])
  )

(defn- category-div [n name]
  (h/html [:div {:id (str "category" n) :class "category"} name])
  )

(defn- window-size []
  {:width (.-innerWidth js/window) :height (.-innerHeight js/window)}
  )

(defn- update-size-status []
  (let [t (dom/by-id "first")
        size (window-size)
        ]
    (dom/set-text! js/window.status (str (:width size) " x " (:height size)))
    )
  )

(defn ^:export init []
  (when (and js/document
             (aget js/document "getElementById"))
    (doseq [[n name] game/categories]
      (let [div (category-div n name)
            body (dom/by-id "first")
            ]
          (dom/append! body div)
        )
      )
    (ev/listen! js/window :resize update-size-status)
    (update-size-status)
    )
  )

