; Conditions
(defn numbers? [args] (every? number? args))
(defn vec? [v] (and (vector? v) (numbers? v)))
(defn vectors? [args] (every? vec? args))
(defn same-length-vectors? [args] (and (vectors? args) (let [c (count (first args))] (every? #(== (count %) c) args))))
(defn matrix? [m] (and (vector? m) (same-length-vectors? m)))
(defn same-size-matrices? [args] (and (every? matrix? args)
                                      (let [rows (count (first args))
                                            cols (count (first (first args)))]
                                        (every? #(and (== (count %) rows) (== (count (first %)) cols)) args))))
(defn multiply-able-matrices [a b] (and (matrix? a) (matrix? b) (== (count (first a)) (count b))))

; Vectors
(defn v_cord [f] (fn [& args]
                   {:pre [(same-length-vectors? args)]
                    :post [(vec? %)]}
                   (vec (apply map f args))))

(def v+ "Adds one or more matrices together" (v_cord +))
(def v- "Subtracts one or more matrices together" (v_cord -))
(def v* "Multiplies one or more matrices together" (v_cord *))
(def vd "Divides one or more matrices together" (v_cord /))

(defn scalar "Returns scalar-product of one or more vectors"
  [& args]
  {:pre [(same-length-vectors? args)]
   :post [(number? %)]}
  (reduce + 0 (apply map * args)))

(defn v*s "Multiplies vector by a scalar"
  [v & scalars]
  {:pre [(and (vec? v) (numbers? scalars))]
   :post [(vec? %)]}
  (let [s (apply * scalars)] (vec (map (fn [cord] (* cord s)) v))))

(defn vect "Returns vector-product of one or more vectors"
  ([a]
   {:pre [(vec? a)]
    :post [(vec? %)]} a)
  ([a b]
   {:pre [(same-length-vectors? [a b])]
    :post [(vec? %)]}
   (vector (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
                (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
                (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))))
  ([a b & args]
   (vec (reduce vect (vect a b) args))))

; Matrices
(defn m_cord [f] (fn [& args]
                   {:pre [(and (same-size-matrices? args))]
                    :post [(matrix? %)]}
                   (vec (apply map (v_cord f) args))))

(def m+ "Adds one or more matrices together" (m_cord +))
(def m- "Subtracts one or more matrices together" (m_cord -))
(def m* "Multiplies one or more matrices together" (m_cord *))
(def md "Divides one or more matrices together" (m_cord /))

(defn m*s "Multiplies matrix by one or more scalars" [m & scalars]
  {:pre [(numbers? scalars)]
   :post [(matrix? %)]}
  (let [s (apply * scalars)] (vec (map (fn [v] (v*s v s)) m))))

(defn m*v
  "Multiplies matrix by one or more vectors"
  ([m v]
   {:pre [(and (matrix? m) (vec? v))]
    :post [(vec? %)]}
   (vec (map (fn [v_i] (reduce + 0 (v* v_i v))) m)))
  ([m v & args] (vec (reduce m*v (m*v m v) args))))

(defn transpose
  "Transposes matrix" [m]
  {:pre [(matrix? m)]
   :post [(matrix? %)]}
  (apply mapv vector m))

(defn m*m
  "Multiplies one or more matrices together"
  ([m]
   {:pre [(matrix? m)]
    :post [(matrix? %)]} m)
  ([m1 m2]
   {:pre [(multiply-able-matrices m1 m2)]
    :post [(matrix? %)]}
   (vec (map (fn [v_i] (m*v (transpose m2) v_i)) m1)))
  ([m1 m2 & args]
   (vec (reduce m*m (m*m m1 m2) args))))
