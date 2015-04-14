(ns vanclj.core
  (:require [clojure.string :as str])
  (:use hiccup.core))

(load "members")
(load "projects")

(defn blank? [s]
  (or (nil? s)
      (empty? s)))

(defn member-list []
  [:ul
    (for [[member-name attrs] members]
      [:li.member
        [:h3 member-name]
        [:ul
          (for [[attr-name attr-val] attrs]
            (when (not (blank? attr-val))
              [:li [:a {:href attr-val
                        :class (name attr-name)}
                       (name attr-name)]]))]])])

(defn project-list []
  (cond
    (empty? projects)
    [:p "Ups... No projects yet!"]

    :else
    [:ul
      (for [[project-name attrs] projects]
        [:li.project
          [:a {:href (:url attrs)}
              [:h3 project-name]]
          [:p  (:summary attrs)]])]))

(defn main-template []
  (html

    [:head

      [:link {:href "http://fonts.googleapis.com/css?family=Junge"
              :rel "stylesheet"
              :type "text/css"}]

      [:link {:href "http://fonts.googleapis.com/css?family=Magra"
              :rel "stylesheet"
              :type "text/css"}]

      [:link {:rel "stylesheet" :href "style.css" :type "text/css"}]

      [:title "van-clj / Vancouver Clojure Club"]

      [:meta {:http-equiv "content-type"
              :content "text/html;charset=UTF-8"}]]

    [:body

      [:div.header
        [:h1
          [:a {:href "/"}
            [:img {:src "logo.png" :alt "van-clj"}]]]

        [:div#info-box
          [:h2 "Vancouver's Clojure Club"]
          [:ul
            [:li
              [:b "Time"] ": 6:00 pm to 8:30 pm Thursday April 16, 2015"]

             [:li
              [:b "Location"] ": "
              [:a {:href "https://www.google.com/maps?f=q&hl=en&q=142+W+Hastings+St.,+Vancouver,+BC,+ca"} "CodeCore Bootcamp."]]

             [:li
              [:a {:href "http://www.meetup.com/Vancouver-Clojure/events/221537925/"} "Meetup - Info and RSVP"]]]]]

       [:div.body
         [:p "We are a community for people who are interested in the clojure programming
              language, whether it be on the jvm, javascript or clr. we meet bi-weekly on
              thursdays to hack on projects and exercises, share our learning through quick
              lightning talks and help each other to get the most out of the language. all
              levels of experience are welcome."]


         [:div#members
           [:h2 "Members"]
           (member-list)]

         [:div#projects
           [:h2 "Projects"]
           (project-list)]]

       [:div.footer
         [:p "If you want your name and projects to show up on this page, get added to "

            [:a { :href  "http://github.com/van-clj" }
                "van-clj organization on Github"]

            " and add your name and project to "

            [:a { :href "http://github.com/van-clj/vanclj" }
                "This site repository"]

            ". Your name goes in "

            [:code "src/vanclj/members.clj"]

            " and your projects in "

            [:code "src/vanclj/projects.clj"] "."]]]))

(defn -main []
  (spit "out/index.html" (main-template)))
