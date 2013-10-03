(defproject alex-comma-game "0.1.0-SNAPSHOT"
  :description "An HTML/ClojureScript game for learning comma rules"
  :url "http://www.highley.net"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1878"]
                 [compojure "1.1.5"]
                 [domina "1.0.2-SNAPSHOT"]
                 [hiccups "0.2.0"]]

  :plugins [[lein-cljsbuild "0.3.3"]
            [lein-ring "0.8.7"]]

  :ring {:handler alex-comma-game.core/handler}

  :cljsbuild {:builds
              {
               :dev
               {
                :source-paths ["src/cljs" "src/brepl"]
                :compiler {
                           :output-to "resources/public/js/commagame_dev.js"
                           :optimizations :whitespace
                           :pretty-print true
                           }
                }
               :prod
               {
                :source-paths ["src/cljs"]
                :compiler {
                           :output-to "resources/public/js/commagame.js"
                           :optimizations :advanced
                           :pretty-print false
                           }
                }
               }
              }
  )
