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

(defn- window-size []
  {:width (.-innerWidth js/window) :height (.-innerHeight js/window)}
  )

(defn- using-window-size [f]
  (let [sz (window-size)]
    (f sz)
    )
  )

(defn- category-width []
  (using-window-size (fn [sz]
                       (quot (:width sz) 6)
                       )
   )
  )

(defn- sentence-width []
  (using-window-size (fn [sz]
                       (quot (:width sz) 4)
                       )
                     )
  )

(defn- center-of-window []
  (using-window-size (fn [sz]
                       { :x (quot (:width sz) 2) :y (quot (:height sz) 2) }
                       )
                     )
  )

(defn- limiting-size []
  (using-window-size (fn [sz]
                       (min (:width sz) (:height sz))
                       )
                     )
  )

(defn- radius-of-circle []
  (quot (* (limiting-size) 3) 8)
  )

(defn- angle-of-category [n]
  (* (dec n) (/ (* 2 Math/PI) category-count))
  )

(defn- in-px [v]
  (str v "px")
  )

(defn- category-style [n cat-dom]
  (let [angle (angle-of-category n)
        sin (Math/sin angle)
        cos (Math/cos angle)
        r (radius-of-circle)
        center (center-of-window)
        cat-width (category-width)
        cat-height (.-offsetHeight cat-dom)
        sz (window-size)
        lsz (limiting-size)
        x-scale (/ (:width sz) lsz)
        y-scale (/ (:height sz) lsz)
        x-offset (int (* sin r x-scale))
        y-offset (int (* cos r y-scale))
        x (- (+ (:x center) x-offset) (quot cat-width 2))
        y (- (+ (:y center) y-offset) (quot cat-height 2))
        ]
    { :left (in-px x)
      :top (in-px y)
      :width (in-px cat-width)
      }
    )
  )

(defn- category-id [n]
  (str "category" n)
  )

(defn- category-div [n name]
  (h/html [:div {:id (category-id n) :class "category normal"} name])
  )

(def sentence-atom (atom (shuffle game/sentences)))

(defn- current-sentence []
  (first @sentence-atom)
  )

(defn- iterate-sentences []
  (swap! sentence-atom rest)
  )

(defn- sentence-style [sen-div]
  (let [center (center-of-window)
        sen-width (sentence-width)
        sen-height (.-offsetHeight sen-div)
        x (- (:x center) (quot sen-width 2))
        y (- (:y center) (quot sen-height 2))
        ]
    { :left (in-px x)
      :top (in-px y)
      :width (in-px sen-width)
      }
    )
  )

(defn- update-sentence []
  (let [{:keys [text answer]} (current-sentence)
        sen-div (dom/by-id "sentence")
        ]
    (dom/set-html! sen-div text)
    (dom/set-data! sen-div :answer answer)
    (dom/set-styles! sen-div (sentence-style sen-div))
    )
  )

(defn- correct-answer? [cat-div]
  (let [cat-answer (dom/get-data cat-div :answer)
        sen-answer (dom/get-data (dom/by-id "sentence") :answer)
        ]
    (= cat-answer sen-answer)
    )
  )

(defn- with-event-target [evt f]
  (let [target (ev/current-target evt)]
    (ev/stop-propagation evt)
    (f target)
    )
  )

(defn- category-mouse-over [evt]
  (with-event-target evt #(dom/toggle-class! % "mouseover")
    )
  )

(defn- category-mouse-out [evt]
  (with-event-target evt #(dom/toggle-class! % "mouseover")
    )
  )

(def correct-count (atom 0))

(defn- complete-game []
  (ev/unlisten! (dom/by-class "category") :click)
  (let [sen-count (count game/sentences)
        passing-grade (inc (int (* sen-count 0.75)))
        cnt @correct-count
        bgcolor (if (> cnt passing-grade) "green" "red")
        sen-div (dom/by-id "sentence")
        score (str cnt " out of " sen-count " correct")
        ]
    (dom/set-styles! sen-div { :color "white"
                               :background-color bgcolor
                               :text-align "center"
                              }
                     )
    (dom/set-html! sen-div score)
    )
  )

(defn- next-sentence []
  (dom/remove-class! (dom/by-class "category") "correct")
  (dom/remove-class! (dom/by-class "category") "incorrect")
  (iterate-sentences)
  (if (nil? (current-sentence))
    (complete-game)
    (update-sentence)
    )
  )

(defn- category-click [evt]
  (with-event-target evt (fn [target]
                           (let [correct (correct-answer? target)
                                 class (if correct "correct" "incorrect")
                                 ]
                             (if correct (swap! correct-count inc))
                             (dom/add-class! target class)
                             (js/setTimeout next-sentence 2000)
                             )
                           )
    )
  )

(def category-events
  (list
   {:event :mouseover :handler category-mouse-over}
   {:event :mouseout  :handler category-mouse-out}
   {:event :click     :handler category-click}
   )
  )

(defn ^:export layout-page []
  (doseq [cat-dom (dom/nodes (dom/by-class "category"))]
    (let [n (dom/get-data cat-dom :answer)
          cat-style (category-style n cat-dom)
          ]
      (dom/set-styles! cat-dom cat-style)
      )
    )
  (update-sentence)
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
        (let [cat-dom (dom/by-id (category-id n))
              ]
          (dom/set-data! cat-dom :answer n)
          )
        )
      (doseq [{:keys [event handler]} category-events]
        (ev/listen! (dom/by-class "category") event handler)
        )
      )
    (layout-page)
    )
  )
