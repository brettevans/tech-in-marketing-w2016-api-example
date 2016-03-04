(ns api-example.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [<! put! chan timeout]])
  (:import [goog.net Jsonp]
           [goog Uri]))

(enable-console-print!)

(def url "http://api.open-notify.org/")

(defn query-url
  "Takes a query and appends it to the API URL."
  [q]
  (str url q))

(defn jsonp [uri]
  (let [out (chan)
        req (Jsonp. (Uri. uri))]
    (.send req nil (fn [res] (put! out res)))
    out))

(defn render-location [position]
  (let [map-view (dom/getElement "map")]
    (set! (.-innerHTML map-view)
          (str "<p class='measure f5 lh-copy'>Latitude: " 
               (:latitude position) 
               "<br/>Longitude: " 
               (:longitude position) 
               "</p>"))))

(defn render-astro [astro]
  (str "<div style='display:table-row' class='dtr'><div style='display:table-cell' class='dtc'>"
       (:name astro)
       "</div><div style='display:table-cell' class='dtc pl3 pa1'>"
       (:craft astro)
       "</div></div>"))

(defn render-astros [astros]
  (let [astros-view (dom/getElement "astros")]
    (set! (.-innerHTML astros-view)
          (str "<div style='display:table-column' class='dtcol'></div>
                <div style='display:table-column' class='dtcol'></div>
                <div style='display:table-row'>
                  <div style='display:table-cell' class='bb'>Astronaut</div>
                  <div style='display:table-cell' class='bb pl3 pa1'>Craft</div>
                </div>"
               (apply str (map render-astro astros))))))

(defn init []
  (let [astros-view (dom/getElement "astros")]
    (go (while true
          (<! (timeout 5000))
          (render-location 
            (js->clj 
              (aget (<! (jsonp (query-url "iss-now.json"))) "iss_position") :keywordize-keys true))))
    (go (let [astros (js->clj (aget (<! (jsonp (query-url "astros.json"))) "people") :keywordize-keys true)]
          (render-location 
            (js->clj (aget (<! (jsonp (query-url "iss-now.json"))) "iss_position") :keywordize-keys true))
          (render-astros astros)))))

(init)
