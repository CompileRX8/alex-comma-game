(ns alex-comma-game.game
  )

(def category '(:id :name))
(def categories
  (list
;   (zipmap category '(1 "Brushstrokes"))
;   (zipmap category '(2 "Rules 1, 2, 3"))
;   (zipmap category '(3 "Rules 4, 5, 6"))
;   (zipmap category '(4 "Rules 7, 8, 9"))
;   (zipmap category '(5 "Lists"))
;   (zipmap category '(6 "Oddballs"))
   (zipmap category '(1 "A comma is used to set off a word that introduces a sentence such as yes, no, and well."))
   (zipmap category '(2 "A comma belongs before the conjunction and, or, or but in a compond sentence."))
   (zipmap category '(3 "Use commas to separate three or more words in a series.  Do not use a comma after the last word in the series."))
   (zipmap category '(4 "Use commas to separate three or more phrases in a series.  Do not use a comma after the last phrase in the sentence."))
   (zipmap category '(5 "Use a comma after a greeting and after the closing of a friendly letter."))
   (zipmap category '(6 "A comma sets off a person's name when the person is being directly spoken to."))
   (zipmap category '(7 "Use a comma between the day and the year in a date.  Do not use a comma if only the month and year are given."))
   (zipmap category '(8 "Use a comma between the names of a city and a state."))
   (zipmap category '(9 "Use a comma to set off a direct quotation."))
   )
  )

(def sentence '(:text :answer))
(def sentences
  (list
   (zipmap sentence '("Yes, my teacher is Mrs. McLaughlin." 1))
   (zipmap sentence '("Well, I think tomorrow I will feel better." 1))
   (zipmap sentence '("No, I'm 10 not 9." 1))
   (zipmap sentence '("I walk to the store, and brought all the groceries. (Check this sentence.)" 2))
   (zipmap sentence '("I have my bike, but I don't ride it much." 2))
   (zipmap sentence '("Can I ride my bike, or do I ride with you?" 2))
   (zipmap sentence '("I once played soccer, basketball, and football." 3))
   (zipmap sentence '("I went to a restaurant and ordered chicken, cookies, and chips and dip." 3))
   (zipmap sentence '("My favorite candies are Snickers, Twix, and M&Ms." 3))
   (zipmap sentence '("When I got home I changed by clothes, washed my hands, and fed my dogs." 4))
   (zipmap sentence '("To make mashed potatoes you peel the potatoes, put in a bowl, put in seasoning, and mash the potatoes." 4))
   (zipmap sentence '("Dear Stacy,<br/>I hope you have a great birthday.  40 is not old.<br/>Your friend,<br/>Alexandra" 5))
   (zipmap sentence '("Bob, you need to clean your room." 6))
   (zipmap sentence '("My teacher, Mrs. McLaughlin, teaches fifth grade." 6))
   (zipmap sentence '("Tanner, I have a surprise for you." 6))
   (zipmap sentence '("August 26, 2013" 7))
   (zipmap sentence '("December 25, 2013" 7))
   (zipmap sentence '("August 6, 2013" 7))
   (zipmap sentence '("February 11, 2013" 7))
   (zipmap sentence '("Flower Mound, TX" 8))
   (zipmap sentence '("Oklahoma City, OK" 8))
   (zipmap sentence '("Houston, TX" 8))
   (zipmap sentence '("Norman, OK" 8))
   (zipmap sentence '("Mrs. McLaughlin said, \"Stop Talking.\"" 9))
   (zipmap sentence '("Mom said, \"It's dinner time.\"" 9))
   (zipmap sentence '("I said, \"What are we having for dinner?\"" 9))
   )
  )
