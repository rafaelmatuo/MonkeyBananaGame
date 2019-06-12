(ns play-macaco.core
  (:require   [play-clj.core  :refer :all]
              [play-clj.ui    :refer :all]
              [play-clj.math  :refer :all]
              [play-clj.g2d   :refer :all]))

(declare bananas main-screen)
(def speed 14)

(defn- get-direction []
  (cond
   (is-pressed? :dpad-left) :left
   (is-pressed? :dpad-right) :right))

(defn- update-player-position [{:keys [player?] :as entity}]
  (if player?
    (let [direction (get-direction)
          new-x (case direction
                  :right (+ (:x entity) speed)
                  :left (- (:x entity) speed))]
      (when (not= (:direction entity) direction)
        (texture! entity :flip true false))
      (assoc entity :x new-x :direction direction))
    entity))

(defn- update-hit-box [{:keys [player? banana?] :as entity}]
  (if (or player? banana?)
    (assoc entity :hit-box (rectangle (:x entity) (:y entity) (:width entity) (:height entity)))
    entity))

(defn- remove-touched-bananas [entities]
  (if-let [bananas (filter #(contains? % :banana?) entities)]
    (let [player (some #(when (:player? %) %) entities)
          touched-bananas (filter #(rectangle! (:hit-box player) :overlaps (:hit-box %)) bananas)]
      (remove (set touched-bananas) entities))
    entities))

(defn- move-player [entities]
  (->> entities
       (map (fn [entity]
              (->> entity
                   (update-player-position)
                   (update-hit-box))))
       (remove-touched-bananas)))

(defn- spawn-banana []
  (let [x (+ 50 (rand-int 1400))
        y (+ 50 (rand-int 30))]
    (assoc (texture "banana.png") :x x, :y y, :width 50, :height 65, :banana? true)))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (add-timer! screen :spawn-banana 1 2)
    (let [background (texture "fundo_1.png")
          player (assoc (texture "DK.png") :x 50, :y 50, :width 400, :height 350, :player? true, :direction :right)]
      [background player]))
  
  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities))
  
  :on-key-down
  (fn [screen entities]
    (cond
     (is-pressed? :r) (app! :post-runnable #(set-screen! bananas main-screen))
     (get-direction) (move-player entities)
     :else entities))
  
  :on-timer
  (fn [screen entities]
    (case (:id screen)
      :spawn-banana (conj entities (spawn-banana)))))

(defgame bananas
  :on-create
  (fn [this]
(set-screen! this main-screen)))
