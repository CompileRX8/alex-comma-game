(ns alex-comma-game.layout
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]
            [alex-comma-game.game :as game]
            [domina.xpath :as xpath])
  )

(def category-count
  (count game/categories)
  )

(def window-size
  {:width (.-innerWidth js/window) :height (.-innerHeight js/window)}
  )

(def category-width 300)

(def center-of-window
  { :x (quot (:width window-size) 2) :y (quot (:height window-size) 2) }
  )

(def limiting-size
  (min (:width window-size) (:height window-size))
  )

(def radius-of-circle
  (quot (* limiting-size 3) 8)
  )

(defn- angle-of-category [n]
  (* (dec n) (/ (* 2 Math/PI) category-count))
  )

(defn- in-px [v]
  (str v "px")
  )

(defn- category-style [n]
  (let [angle (angle-of-category n)
        sin (Math/sin angle)
        cos (Math/cos angle)
        x-offset (int (* sin radius-of-circle))
        y-offset (int (* cos radius-of-circle))
        x (- (+ (:x center-of-window) x-offset) (quot category-width 2))
        y (+ (:y center-of-window) y-offset)
        ]
    { :left (in-px x)
      :top (in-px y)
      :width (in-px category-width)
      :position "absolute"
      }
    )
  )

(defn- update-size-status [evt]
  (let [t (dom/by-id "first")
        ]
    (dom/set-text! t (str (:width window-size) " x " (:height window-size)))
    )
  )

(defn- category-id [n]
  (str "category" n)
  )

(defn- category-div [n name]
  (h/html [:div {:id (category-id n) :class "category"} name])
  )

(defn ^:export init []
  (when (and js/document
             (aget js/document "getElementById"))
    (let [cat-map (into {} (map
                            (fn [{:keys [id name]}] [id (category-div id name)])
                            game/categories)
                        )
          body (xpath/xpath "//body")
          ]
      (doseq [[n cat-div] cat-map]
        (dom/append! body cat-div)
        (let [cat-style (category-style n)
              cat-dom (dom/by-id (category-id n))
              ]
          (dom/set-styles! cat-dom cat-style)
          )
        )
      )
    )
  )

