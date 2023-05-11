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
(defn parseExpression [constant# variable# operators#] (fn [str] (letfn [(parseAST [el] (cond
                                                                                      (number? el) (constant# el)
                                                                                      (symbol? el) (variable# (name el))
                                                                                      (list? el) (apply (get operators# (first el)) (map parseAST (rest el)))))]
                                                      (parseAST (read-string str)))))
(defn parseFunction [str] (let [parse (parseExpression constant variable operators)] (parse str)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(definterface IExpression
             (^Number evaluate [vars])
             (^String toString [])
             (^String toStringPostfix []))
(deftype Java-Constant [value]
  IExpression
  (evaluate [this vars] value)
  (toString [this] (str value))
  (toStringPostfix [this] (.toString this)))
(deftype Java-Variable [name]
  IExpression
  (evaluate [this vars] (get vars (str (first (clojure.string/lower-case name)))))
  (toString [this] name)
  (toStringPostfix [this] (.toString this)))
(defmacro def-class [name f sign]
  (let [name' (symbol (str "Java-" name))]
    `(do
       (deftype ~name' [args#]
         IExpression
         (evaluate [this vars#] (apply ~f (mapv #(.evaluate % vars#) args#)))
         (toString [this] (str "(" ~sign " " (clojure.string/join " " args#) ")"))
         (toStringPostfix [this] (str "(" (clojure.string/join " " (mapv #(.toStringPostfix %) args#)) " " ~sign ")")))
       (defn ~name [& args#] (new ~name' args#)))))

(defn evaluate [expr vars] (.evaluate expr vars))
(defn toString [expr] (.toString expr))
(defn toStringPostfix [expr] (.toStringPostfix expr))
(defn Constant [value] (Java-Constant. value))
(defn Variable [name] (Java-Variable. name))

(def-class Add + "+")
(def-class Subtract - "-")
(def-class Multiply * "*")
(def-class Divide custom-divide "/")
(def-class Negate - "negate")
(def-class Sin math/sin "sin")
(def-class Cos math/cos "cos")
(def-class Sinh math/sinh "sinh")
(def-class Cosh math/cosh "cosh")
(def-class ArcTan math/atan "atan")
(def-class ArcTan2 math/atan2 "atan2")

(def objOperators {'+      Add
                   '-      Subtract
                   'negate Negate
                   '*      Multiply
                   '/      Divide
                   'sin    Sin
                   'cos    Cos
                   'sinh    Sinh
                   'cosh    Cosh
                   'atan   ArcTan
                   'atan2  ArcTan2})
(defn parseObject [str] (let [parse (parseExpression Constant Variable objOperators)] (parse str)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(load-file "parser.clj")

(def-class UPow #(math/exp %) "**")
(def-class ULog #(math/log %) "//")


(defparser parseObjectPostfix
           *UPow (+seqf (constantly UPow) \*\*)
           *ULog (+seqf (constantly ULog) \/\/)
           *+ (+seqf (constantly Add) \+)
           *- (+seqf (constantly Subtract) \-)
           ** (+seqf (constantly Multiply) \*)
           *divide (+seqf (constantly Divide) \/)
           *neg (+seqf (constantly Negate) \n\e\g\a\t\e)

           *characters (mapv char (range 0 128))
           (*chars [p] (+char (apply str (filter p *characters))))
           *letter (*chars #(Character/isLetter %))
           *digit (*chars #(or (Character/isDigit %) (= % (char 45)) (= % (char 46))))
           *single-whitespace (*chars #(Character/isWhitespace %))
           *whitespace (+ignore (+star *single-whitespace))
           *constant (+map (comp Constant read-string) (+str (+plus *digit)))
           *variable (+map Variable (+str (+plus *letter)))
           *unary-operator (+or *neg *UPow *ULog)
           *binary-operator (+or *+ *- ** *divide)
           *unary (+map (fn [[a op]] (op a)) (+seq *parseObjectPostfix *unary-operator))
           *expression (+seqn 1 \( *whitespace (delay (+or *unary *inner-expression)) *whitespace \))
           *inner-expression (+map (fn [[a b]] (apply (fn [a [b op]] (op a b)) a b)) (+seq *parseObjectPostfix (+opt (+star (+seq *parseObjectPostfix *binary-operator)))))
           *parseObjectPostfix (+seqn 0 *whitespace (+or *constant *variable *expression) *whitespace))


