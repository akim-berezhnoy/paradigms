(require '[clojure.math :as math])
; Base
(defn constant [x] (fn [map] x))
(defn variable [x] (fn [map] (get map x)))
(defn multi-arg-operation [f] (fn [& args] (fn [map] (apply f (mapv #(% map) args)))))
(defmacro def-exp [name op] `(do (def ~name (multi-arg-operation ~op))))
(def-exp add +)
(def-exp subtract -)
(def negate subtract)
(def-exp multiply *)
(def-exp divide (fn ([x] (/ 1.0 x))
                    ([x & args] (/ (double x) (apply * args)))))
;
(def-exp arcTan math/atan)
(def-exp arcTan2 math/atan2)

; ExpLn
(def-exp ln math/log)
(def-exp exp math/exp)

; SumexpLSE (36, 37)
(letfn [(f-sumexp [& args] (reduce #(+ %1 (math/exp %2)) 0 args))]
  (def-exp sumexp f-sumexp)
  (def-exp lse (comp math/log f-sumexp)))

; MeansqRMS (38, 39)
(letfn [(f-meansq [& args] (/ (reduce #(+ %1 (math/pow %2 2)) 0 args) (count args)))]
  (def-exp meansq f-meansq)
  (def-exp rms (comp math/sqrt f-meansq)))

(def operators {'+      add
                '-      subtract
                'negate negate
                '*      multiply
                '/      divide
                'exp    exp
                'ln     ln
                'sumexp sumexp
                'lse    lse
                'atan  arcTan
                'atan2 arcTan2
                'meansq meansq
                'rms    rms})
; Parser
(defn parseFunction [str] (letfn [(parseAST [el] (cond
                                                   (number? el) (constant el)
                                                   (symbol? el) (variable (name el))
                                                   (list? el) (apply (get operators (first el)) (map parseAST (rest el)))))]
                            (parseAST (read-string str))))
