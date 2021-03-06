(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces "0.1.9" :scope "test"]
                  [cljsjs/boot-cljsjs "0.4.6" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "3.3.1-0")
(bootlaces! +version+)

(task-options!
 pom {:project 'cljsjs/pouchdb
      :version +version+
      :description "PouchDB packaged up with Google Closure externs"
      :url "http://pouchdb.com/"
      :scm {:url "https://github.com/cljsjs/packages"}
      :license {"Apache" "https://github.com/pouchdb/pouchdb/raw/master/LICENSE"}})

(deftask package []
  (comp
   (download :url "https://github.com/pouchdb/pouchdb/releases/download/3.3.1/pouchdb-3.3.1.js"
             :checksum "4545ABBA4693EE0A6F1B6FAC4A57B04F")
   (download :url "https://github.com/pouchdb/pouchdb/releases/download/3.3.1/pouchdb-3.3.1.min.js"
             :checksum "E32627C37898C7995B8BD2FEE93DF952")
   (sift :move {#"pouchdb-([\d+\.]*).js" "cljsjs/development/pouchdb.inc.js"
                #"pouchdb-([\d+\.]*).min.js" "cljsjs/production/pouchdb.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.pouchdb")))

