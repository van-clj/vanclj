(ns vanclj.core
  (:require [clojure.string :as str]
            [dalap.html :as html]
            [dalap.walk :as walk]
            [dalap.html.selector :as sel]))

(load "members")
(load "projects")

(defn blank? [s]
  (or (nil? s)
      (empty? s)))

(def main-template
  (html/html5

    [:head

      [:link {:href "http://fonts.googleapis.com/css?family=Junge"
              :rel "stylesheet"
              :type "text/css"}]

      [:link {:href "http://fonts.googleapis.com/css?family=Magra"
              :rel "stylesheet"
              :type "text/css"}]

      [:link {:rel "stylesheet" :href "/style.css" :type "text/css"}]

      [:title "van-clj / Vancouver Clojure Club"]

      [:meta {:http-equiv "content-type"
              :content "text/html;charset=UTF-8"}]]

    [:body

      [:div.header
        [:h1
          [:a {:href "/"}
            [:img {:src "/logo.png" :alt "van-clj"}]]]

        [:div#info-box
          [:h2 "Vancouver's Clojure Club"]
          [:ul
            [:li
              [:b "Time"] ": 7:00 pm to 9:00 pm each Monday"]

             [:li
              [:b "Location"] ": "
              [:a {:href "http://maps.google.com/maps?q=Vancouver+Public+Library&hl=en&ll=49.279698,-123.116999&spn=0.004241,0.009645&sll=49.279649,-123.117492&sspn=0.004241,0.009645&t=m&radius=0.26&hq=Vancouver+Public+Library&z=17"} "Vancouver Public Library."]]

             [:li
              [:a {:href "http://groups.google.com/group/vancouver-clojure-club"} "Mailing List"]]]]]

       [:div.body
         [:p "We meet every Monday to learn Clojure and work on possible
             projects, at the beginning of the month we discuss and come
             up with project ideas, the following Mondays are spent
             on working and helping devs on this projects. We repeat
             this cycle every month."]


         [:div#members
           [:h2 "Members"]
           {:members members}]

         [:div#projects
           [:h2 "Projects"]
           {:projects projects}]]

       [:div.footer
         [:p ["If you want your name and projects to show up on this page, get added to "

              [:a { :href  "http://github.com/van-clj" }
                  "van-clj organization on Github"]

              " and add your name and project to "

              [:a { :href "http://github.com/van-clj/vanclj" }
                  "This site repository"]

              ". Your name goes in "

              [:code "src/vanclj/members.clj"]

              " and your projects in "

              [:code "src/vanclj/projects.clj"] "."]]]]))


(defn visit-members [node w]
  [:ul
    (for [[member-name attrs] (:members node)]
      [:li.member
        [:h3 member-name]
        [:ul
          (for [[attr-name attr-val] attrs]
            (when (not (blank? attr-val))
              [:li [:a {:href attr-val
                        :class (name attr-name)}
                       (name attr-name)]]))]])])

(defn visit-twitter [s w]
  (w (str/replace s "@" "http://twitter.com/")))

(defn visit-projects [node w]
  (let [projects (:projects node)]
    (cond
      (empty? (:projects node))
      [:p "Ups... No projects yet!"]

      :else
      [:ul
        (for [[project-name attrs] (:projects node)]
          [:li.project
            [:a {:href (:url attrs)} [:h3 project-name]]
            [:p  (:summary attrs)]])])))

(def node-transformations
  (sel/gen-decorator
    [[:div#members  #(contains? % :members)]  visit-members
     [:div#members :li.member #(and (string? %)
                                    (= (first %) \@))] visit-twitter
     [:div#projects #(contains? % :projects)] visit-projects]))

(def final-visitor
  (node-transformations html/visit))

(defn -main []
  (spit "out/index.html"
        (html/to-html main-template
                      final-visitor)))


