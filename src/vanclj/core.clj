(ns vanclj.core
  (:require [clojure.string :as str]
            [vanclj.meetups :as meetups :refer [+meetups+]])
  (:use hiccup.core))

(load "members")
(load "projects")

(defn meetup-hero [meetup]
  "Render the hero meetup - the big one in the center."
  (let [{:keys [date from-to summary host]} meetup
        {:keys [name address image google-map]} host]
    [:div.meetup-hero.card
     [:div.card-image
      [:figure.image.is-5by3
       [:img {:src (or image meetups/+default-host-image+)
              :alt (or name "The Clojure Meetup")}]]]
     [:div.card-content
      [:div.tile.is-ancestor
       [:div.tile.is-parent
        [:div.meetup-icon.tile.is-child.is-1.is-pulled-left
         [:span.icon.is-medium.is-inline-flex
          [:i.fas.fa-lg.fa-clock {:aria-hidden "true"}]]]
        [:div.tile.is-child.has-text-left
         ;; TODO - add :time tag and datetime JSON-LD
         [:p.title.is-5 (meetups/format-hero-date date)]
         [:p.subtitle.is-6 from-to]]]
       [:div.tile.is-parent
        [:div.meetup-icon.tile.is-child.is-1.is-pulled-left
         [:span.icon.is-medium.is-inline-flex
          [:i.fas.fa-lg.fa-map-marker-alt {:aria-hidden "true"}]]]
        [:div.tile.is-child.has-text-left
         [:p.title.is-5 [:a {:href google-map} name]]
         [:p.subtitle.is-6 address]]]]
      [:card-footer
       [:card-footer-item
        [:div.has-text-justified summary]]]]]))

(defn meetup-section [meetups]
  [:div.tile.is-ancestor
   ;; hero meetup
   [:div.tile.is-8.is-parent
    [:div.tile.is-child.has-text-centered
     (meetup-hero (first meetups))]]
   ;; secondary meetups
   [:div.tile.is-parent.is-vertical.is-hidden-mobile
    (for [meetup (take 2 (rest meetups))
          :let [{:keys [date from-to summary host]} meetup
                {:keys [name address image google-map]} host]]
      [:div.tile.is-child.card
       [:div.card-image
        [:figure.image.is-5by3
         [:img {:src (or image meetups/+default-host-image+)
                :alt (or name "The Clojure Meetup")}]]]

       [:div.card-content
        [:div.level
         [:div.level-left
          [:div.level-item
           [:p (meetups/format-secondary-date date)]]]
         [:div.level-right
          [:div.:level-item]
          [:p name]]]]])]])

(defn member-section [members]
  [:div.tile.is-ancestor
   [:div.tile.is-vertical
    (for [members (partition-all 4 (take 8 members))]
      [:div.tile.is-parent
       (for [{:keys [name website avatar twitter github]} members]
         [:div.tile.is-child.box.has-text-centered
          [:div.card.equal-height.is-shadowless
           [:div.card-image.has-text-centered
            (let [avatar-element
                  ;; https://stackoverflow.com/a/54166914/1888507
                  [:figure.image.is-48x48.is-inline-block
                   [:img.is-rounded {:src (or avatar (str "https://ui-avatars.com/api/?name="
                                                          (str/replace name #"\s+" "+")
                                                          "?size=128"))
                                     :alt name}]]]
              (if-not website
                avatar-element
                [:a {:href website} avatar-element]))]

           [:div.card-content
            [:div.title.is-6 name]
            (when (and twitter github)
              [:div.level
               [:div.level-item
                (when twitter
                  [:a.icon {:href twitter}
                   [:i.fab.fa-twitter]])
                (when github
                  [:a.icon {:href github}
                   [:i.fab.fa-github]])]])]]])])]])

(defn project-section [projects]
  (for [ps (partition-all 3 projects)]
    [:div.tile.is-ancestor
     [:div.tile.is-parent
      (for [[project-name attrs] ps]
        [:a.tile.is-child.card.equal-height {:href (:url attrs)}
         [:div.card-content
          [:p.title.is-5 project-name]
          [:p.subtitle.is-6 (:summary attrs)]]])]]))

(defn main-template []
  (html
   [:head

    [:link {:href "styles.css" :rel "stylesheet" :type "text/css"}]

    [:title "van-clj / The Vancouver Clojure Club"]

    [:meta {:http-equiv "content-type"
            :content "text/html;charset=UTF-8"}]

    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]]

   [:body
    [:section.hero.is-light.is-small.is-bold
     [:div.hero-head
      [:nav.navbar {:role "navigation" :aria-label "main navigation"}
       [:div.navbar-brand
        [:a.navbar-item {:href "https://van-clj.github.io/"}
         [:img {:src "vanclj-logo.jpg"
                :alt "Vancouver's Clojure Club"
                :width "48"
                :height "48"}]]]]]

     [:div.hero-body
      [:div.container.has-text-centered
       [:h1.title
        "The Vancouver Clojure Community"]
       [:h2.subtitle
        "A community for people who are interested in the Clojure programming language."]]]]

    [:div.box
     [:div.columns
      [:div.intro.column.is-4.is-offset-4
       [:nav.level.is-mobile
        [:div.level-item.has-text-centered
         [:figure.image.is-32x32.is-inline-block
          [:img {:src "clojure-logo-200px.png"}]]]
        [:div.level-item.has-text-centered
         [:figure.image.is-32x32.is-inline-block
          [:img {:src "cljs-logo-200px.png"}]]]
        [:div.level-item.has-text-centered
         [:figure.image.is-32x32.is-inline-block
          [:img {:src "shadow-cljs-400px.png"}]]]
        [:div.level-item.has-text-centered
         [:figure.image.is-32x32.is-inline-block
          [:img {:src "cider-logo-200px.png"}]]]]]]

     [:p.has-text-centered
      "Whether it be on the JVM, JavaScript or CLR. We meet to hack on projects
 and exercises, share our learning through quick lightning talks and help each
 other to get the most out of the language. all levels of experience are
 welcome."]]

    [:section.section
     [:div.columns
      [:div.column.is-8.is-offset-2
       [:section.section
        (meetup-section (meetups/sort-meetups +meetups+))]

       [:section.section
        [:div.container.has-text-centered
         [:div.subtitle.is-3 "Members"]
         (member-section +members+)]]

       [:section.section
        [:div.container.has-text-centered
         [:div.subtitle.is-3 "Hacks"]
         (project-section +projects+)]]]]]

    [:footer.footer
     [:div.content.has-text-centered
      [:a {:href "http://creativecommons.org/licenses/by/4.0/" :rel "license"}
       [:img {:alt "Creative Commons License"
              :style "border-width:0"
              :src "https://i.creativecommons.org/l/by/4.0/88x31.png"}]]
      [:br][:br]
      "This work is licensed under a "
      [:a {:href "http://creativecommons.org/licenses/by/4.0/"
           :rel "license"}
       "Creative Commons Attribution 4.0 International License."]]]]))

(defn -main
  [& args]
  (spit (first *command-line-args*) (main-template)))
