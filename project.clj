(defproject scrutinize-fn "0.1.0"
  :description "van-clj static site generator"
  :source-paths ["src"]
  :test-paths ["test"]
  :resource-paths []
  :compile-path nil
  :target-path nil
  :plugins [[lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]
                           :aliases [:dev :test]})
