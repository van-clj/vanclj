(ns vanclj.meetups
  (:require [java-time :as time]
            [java-time.format :as time-format])
  (:import [java.time ZoneId]
           [java.time.format DateTimeFormatter]))

(def +default-host-image+ "hack-and-meet-by-david-dossot.jpg")

(def ^:private +spark-host+
  {:name "Spark"
   :google-map "https://www.google.com/maps/place/Spark/@49.276549,-123.1230613,17z/data=!3m1!4b1!4m5!3m4!1s0x548671810ba9da3f:0x7a86f655642167cb!8m2!3d49.276549!4d-123.1208726"
   :address "1062 Homer St #400, Vancouver, BC"})

(def ^:private +elastipath-host+
  {:name "Elastic Path Software"
   :google-map "https://www.google.com/maps/place/Elastic+Path+Software,+Inc./@49.285303,-123.1243837,17z/data=!3m1!4b1!4m5!3m4!1s0x548673ddfee8b6b3:0xa0aa54d9776b94f5!8m2!3d49.285303!4d-123.1243837"
   :address "745 Thurlow St #1400, Vancouver"
   :image "elasticpath-office.jpg"})

(def ^:dynamic +meetups+
  [{:date "20191024"
    :from-to "6.00 PM to 8.00 PM"
    :summary "In this meetup we would like members to pick any dialect of LISP and do something fun with it (Bonus points for Halloween-themed projects!).We are hoping each group member will give a (very) informal presentation, probably about 10 to 15 minutes long. If you made something cool--share it! If you came up against a roadblock--still, share it! We are all eager to hear your story."
    :host +spark-host+}

   {:date "20190919"
    :from-to "6.00 PM to 8.00 PM"
    :summary "In this meetup we would like members to pick any dialect of LISP and do something fun with it (Bonus points for Halloween-themed projects!). We are hoping each group member will give a (very) informal presentation, probably about 10 to 15 minutes long. If you made something cool--share it! If you came up against a roadblock--still, share it! We are all eager to hear your story."
    :host +spark-host+}

   {:date "20190717"
    :from-to "6.00 PM to 8.00 PM"
    :summary "Bring your laptop and any cool code in Clojure that you would like to share! During the meetup, we will pick an interesting challenge, break it into manageable pieces, and solve it together."
    :host +elastipath-host+}

   {:date "20190131"
    :from-to "6.00 PM to 8.00 PM"
    :summary "Come and join us for our customary diving into the Clojure Lisp (is Clojure a Lisp really? I let you decide)."
    :host +elastipath-host+}

   {:date "20181218"
    :from-to "6.00 PM to 8.00 PM"
    :title "In-Person Clojure(Script) Meetup!"
    :summary "ElasticPath Is hosting this Clojure evening. The usual suspects will be there: a REPL, immutability and fun-ctional programming. 🍕 is on us. See you there!."
    :host +elastipath-host+}
   ])

(defn- parse-date
  [s]
  (time/local-date DateTimeFormatter/BASIC_ISO_DATE s))

(defn sort-meetups
  [meetups]
  (->> meetups
       (map
        (fn [meetup]
          (update meetup :date parse-date)))
       (sort-by :data)))

(defn format-hero-date
  [^java.time.LocalDate date]
  (time/format "EEEE, MMMM d, YYYY" date))

(defn format-secondary-date
  [^java.time.LocalDate date]
  (time/format "MMM d, YYYY" date))
