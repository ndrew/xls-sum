(ns xls-sum.excel-test
  (:use clojure.test
        xls-sum.excel)
  (:require 
    [dk.ative.docjure.spreadsheet :as xls]))

(deftest getting-data
  (let [workbook (xls/load-workbook "/Users/ndrw/xls-sum/1.xlsx")]
    (clojure.pprint/pprint 
      (get-excel-data workbook)))
  )
