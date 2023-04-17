; Conditions
(defn vectors-of-scalars [& args] (and (every? vector? args)
                                       (every? (partial every? number?) args)))
(defn same-length-vectors? [& args] (or (empty? args)
                                        (and (every? vectors-of-scalars args)
                                             (apply = (map count args)))))
(defn matrix? [& args] (and (every? vector? args)
                            (every? (partial apply same-length-vectors?) args)))
(defn same-size-matrices? [& args] (and (every? matrix? args)
                                        (apply = (map (juxt count (comp count first)) args))))
(defn compatible-matrices [a b] (and (apply matrix? [a b])
                                     (== (count (first a)) (count b))))
(defn same-size-tensors? [& args]
  (or (every? number? args)
      (and (every? vector? args)
           (apply = (map count args))
           (every? true? (apply map same-size-tensors? args)))))

; Vectors
(defn v_cord [f] (fn [& args]
                   {:pre [(apply same-length-vectors? args)]
                    :post [(vectors-of-scalars %)]}
                   (vec (apply map f args))))
(def v+ "Adds one or more matrices together" (v_cord +))
(def v- "Subtracts one or more matrices together" (v_cord -))
(def v* "Multiplies one or more matrices together" (v_cord *))
(def vd "Divides one or more matrices together" (v_cord /))

(defn scalar "Returns scalar-product of one or more vectors"
  [& args]
  {:pre [(apply same-length-vectors? args)]
   :post [(number? %)]}
  (reduce + (apply map * args)))

(defn v*s "Multiplies vector by a scalar"
  [v & scalars]
  {:pre [(and (vectors-of-scalars v) (every? number? scalars))]
   :post [(vectors-of-scalars %)]}
  (let [s (apply * scalars)] (mapv (fn [cord] (* cord s)) v)))

(defn vect "Returns vector-product of one or more vectors"
  ([a]
   {:pre [(vectors-of-scalars a)]
    :post [(vectors-of-scalars %)]} a)
  ([a b]
   {:pre [(same-length-vectors? a b)]
    :post [(vectors-of-scalars %)]}
   (vector (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
           (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
           (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))))
  ([a b & args]
   (reduce vect (vect a b) args)))

; Matrices
(defn m_cord [f] (fn [& args]
                   {:pre [(apply same-size-matrices? args)]
                    :post [(matrix? %)]}
                   (vec (apply map (v_cord f) args))))
(def m+ "Adds one or more matrices together" (m_cord +))
(def m- "Subtracts one or more matrices together" (m_cord -))
(def m* "Multiplies one or more matrices together" (m_cord *))
(def md "Divides one or more matrices together" (m_cord /))

(defn m*s "Multiplies matrix by one or more scalars" [m & scalars]
  {:pre [(every? number? scalars)]
   :post [(matrix? %)]}
  (let [s (apply * scalars)] (mapv (fn [v] (v*s v s)) m)))

(defn m*v
  "Multiplies matrix by one or more vectors"
  ([m v]
   {:pre [(and (matrix? m) (vectors-of-scalars v))]
    :post [(vectors-of-scalars %)]}
   (mapv (fn [v_i] (reduce + 0 (v* v_i v))) m))
  ([m v & args]
   reduce m*v (m*v m v) args))

(defn transpose
  "Transposes matrix" [m]
  {:pre [(matrix? m)]
   :post [(matrix? %)]}
  (apply mapv vector m))

(defn m*m
  "Multiplies one or more matrices together"
  ([m]
   {:pre [(matrix? m)]
    :post [(matrix? %)]}
   m)
  ([m1 m2]
   {:pre [(compatible-matrices m1 m2)]
    :post [(matrix? %)]}
   (mapv (fn [v_i] (m*v (transpose m2) v_i)) m1))
  ([m1 m2 & args]
   (reduce m*m (m*m m1 m2) args)))

; Tensors
(defn tensor_cord [f]
  (fn inner [& args]
    {:pre [(apply same-size-tensors? args)]
     :post [(same-size-tensors? %)]}
    (if (every? number? args)
      (apply f args)
      (apply mapv inner args))))
(def t+ "Adds one or more tensors together" (tensor_cord +))
(def t- "Subtracts one or more tensors together" (tensor_cord -))
(def t* "Multiplies one or more tensors together" (tensor_cord *))
(def td "Divides one or more tensors together" (tensor_cord /))
