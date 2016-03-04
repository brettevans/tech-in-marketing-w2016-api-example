;; Add Cider nREPL Middleware
(require 'boot.repl)

(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.10.0"]])

(swap! boot.repl/*default-middleware*
       conj 'cider.nrepl/cider-middleware)

(set-env! :dependencies '[[org.clojure/clojure "1.7.0"]
			  [org.clojure/clojurescript "1.7.170"]
			  [org.clojure/core.async "0.2.374"]			 
 
			  [adzerk/boot-cljs "1.7.170-3"]
			  [adzerk/boot-cljs-repl "0.3.0"]
			  [adzerk/boot-reload "0.4.2"]
			  
			  [com.cemerick/piggieback "0.2.1"]
			  [org.clojure/tools.nrepl "0.2.12"]
			  [weasel "0.7.0"]

			  [pandeiro/boot-http "0.7.3"]]
	  :resource-paths #{"resources"}
	  :source-paths #{"src"})

(require '[adzerk.boot-cljs :refer [cljs]]
	 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
	 '[adzerk.boot-reload :refer [reload]]
	 '[pandeiro.boot-http :refer :all])

(deftask dev []
  (comp (serve :dir "target/public" :port 3000)
	(watch)
	(reload :asset-path "public" :port 3001)
	(cljs :optimizations :none)
	(target :dir #{"target"})))
