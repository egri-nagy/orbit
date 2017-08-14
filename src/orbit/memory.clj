(ns orbit.memory
  "Information about memory usage.")

(defn mem-info []
  (let [rt (java.lang.Runtime/getRuntime)
        total (.totalMemory rt)
        MB (* 1024 1024)]
    (System/gc)
    (str
     (float (/ (.freeMemory rt) MB))
     "MB free out of "
     (float (/ total MB)))))
