(ns play-macaco.core
  (:require   [play-clj.core  :refer :all]
              [play-clj.ui    :refer :all]
              [play-clj.math  :refer :all]
              [play-clj.g2d   :refer :all]))



;;(defscreen main-screen
  ;;:on-show
  ;;(fn [screen entities]
  ;;  (update! screen :renderer (stage))
   ;; (add-timer! screen :spawn-apple 1 2)
   ;; (let [background (texture "fundo_1.png")
     ;;     player (assoc (texture "DK.png") :x 50, :y 50, :width 400, :height 350, :player? true, :direction :right)]
     ;; [background player]))

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities))

;;(defgame apples
 ;; :on-create
  ;;(fn [this]
    ;;(set-screen! this main-screen))) 


(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (label "Hello world!xzdsdsdsdsdx" (color :white)))
  
  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)))

(defgame play-macaco-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
