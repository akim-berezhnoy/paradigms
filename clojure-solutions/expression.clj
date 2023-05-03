(require '[clojure.math :as math])
; Base
(defn constant [x] (constantly x))
(defn variable [x] (fn [map] (get map x)))
(defn multi-arg-operation [f] (fn [& args] (fn [map] (apply f (mapv #(% map) args)))))
(defmacro def-exp [name op] `(do (def ~name (multi-arg-operation ~op))))
(def-exp add +)
(def-exp subtract -)
(def negate subtract)
(def-exp multiply *)
(defn custom-divide ([x] (/ 1.0 x))
        ([x & args] (/ (double x) (apply * args))))
(def-exp divide custom-divide)
; ArcTan
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
                'atan   arcTan
                'atan2  arcTan2
                'meansq meansq
                'rms    rms})
; Parser
(defn parseFunction [str] (letfn [(parseAST [el] (cond
                                                   (number? el) (constant el)
                                                   (symbol? el) (variable (name el))
                                                   (list? el) (apply (get operators (first el)) (map parseAST (rest el)))))]
                            (parseAST (read-string str))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(definterface IExpression
             (^Number evaluate [vars])
             (^String toString []))
(deftype Expression [f sign args]
  IExpression
  (evaluate [this vars] (if (and (zero? (count args)) (not (empty? sign))) (get vars sign) (apply f (mapv #(.evaluate % vars) args))))
  (toString [this] (cond (empty? sign) (str (f))
                         (zero? (count args)) sign
                         :else (str "(" sign " " (clojure.string/join " " args) ")"))))
(defn evaluate [expr vars] (.evaluate expr vars))
(defn toString [expr] (.toString expr))
(defn Constant [value] (Expression. (constantly value) "" []))
(defn Variable [name] (Expression. () name []))
(defn Add [& args] (Expression. + "+" args))
(defn Subtract [& args] (Expression. - "-" args))
(defn Multiply [& args] (Expression. * "*" args))
(defn Divide [& args] (Expression. custom-divide "/" args))
(defn Negate [& args] (Expression. - "negate" args))
(def objOperators {'+      Add
                   '-      Subtract
                   'negate Negate
                   '*      Multiply
                   '/      Divide })
(defn parseObject [str] (letfn [(parseAST [el] (cond
                                                   (number? el) (Constant el)
                                                   (symbol? el) (Variable (name el))
                                                   (list? el) (apply (get objOperators (first el)) (map parseAST (rest el)))))]
                            (parseAST (read-string str))))
