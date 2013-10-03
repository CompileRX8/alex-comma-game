(ns alex-comma-game.game
  )

(def category '(:id :name))
(def categories
  (list
   (zipmap category '(1 "Brushstrokes"))
   (zipmap category '(2 "Rules 1, 2, 3"))
   (zipmap category '(3 "Rules 4, 5, 6"))
   (zipmap category '(4 "Rules 7, 8, 9"))
   (zipmap category '(5 "Lists"))
   (zipmap category '(6 "Oddballs"))
   )
  )

(def sentence '(:text :answer))
(def sentences
  (list
   (zipmap sentence '("Sentence 1 (3)" 3))
   (zipmap sentence '("Sentence 2 (6)" 6))
   (zipmap sentence '("Sentence 3 (2)" 2))
   (zipmap sentence '("Sentence 4 (1)" 1))
   (zipmap sentence '("Sentence 5 (4)" 4))
   (zipmap sentence '("Sentence 6 (5)" 5))
   )
  )
