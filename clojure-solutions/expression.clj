(require 'clojure.math)
; Base
(defn constant [x] (fn [map] x))
(defn variable [x] (fn [map] (get map x)))
(defn multi-arg-operation [f] (fn [& args] (fn [map] (apply f (mapv #(% map) args)))))
(def add (multi-arg-operation +))
(def subtract (multi-arg-operation -))
(def negate subtract)
(def multiply (multi-arg-operation *))
(def divide (letfn [(custom-division
                      ([x] (if (zero? x) ##Inf (/ 1 x)))
                      ([a b] (if (zero? b) ##Inf (/ a b)))
                      ([a b & args] (reduce custom-division (custom-division a b) args)))]
              (multi-arg-operation custom-division)))
; SumexpLSE (36, 37)
(defn sumexp-func [& args] (reduce #(+ %1 (clojure.math/exp %2)) 0 args))
(def sumexp (multi-arg-operation sumexp-func))
(def lse (multi-arg-operation (comp clojure.math/log sumexp-func)))
; MeansqRMS (38, 39)
(defn meansq-func [& args] (/ (reduce #(+ %1 (clojure.math/pow %2 2)) 0 args) (count args)))
(def meansq (multi-arg-operation meansq-func))
(def rms (multi-arg-operation (comp clojure.math/sqrt meansq-func)))
; Functions
(def functions {'+ add
                '- subtract
                'negate negate
                '* multiply
                '/ divide
                'sumexp sumexp
                'lse lse
                'meansq meansq
                'rms rms})
; Parser
(defn parseFunction [str] (letfn [(replace-all [el] (cond
                                               (number? el) (constant el)
                                               (symbol? el) (variable (name el))
                                               (list? el) (apply (get functions (first el)) (map replace-all (rest el)))))]
                            (replace-all (read-string str))))
