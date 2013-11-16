(ns xls-sum.excel
  (:require 
    [dk.ative.docjure.spreadsheet :as xls])
  (:import
    (org.apache.poi.xssf.usermodel XSSFWorkbook)
    (org.apache.poi.ss.usermodel Workbook Sheet Cell Row
                                WorkbookFactory DateUtil
                                IndexedColors CellStyle Font
                                CellValue)
    (org.apache.poi.ss.util CellReference AreaReference)))

;(def CELL_TYPE_BLANK Cell/CELL_TYPE_BLANK)

(defn- left [s l]
    (if (> (count s) l)
      (str (subs s 0 l) "\u2026")
      s))

(defn- get-cell-data[cell]
 (try
     ;(if (= (.getCellType cell) Cell/CELL_TYPE_STRING)
     ;   (left (xls/read-cell cell) 19)
        (xls/read-cell cell);)
     
     (catch Exception e "")))

(defn- get-row-data[row]
  (reduce #(conj %1 (get-cell-data %2)) [] (xls/cell-seq row))
  )

(defn- get-sheet-data[sheet cfg]
  ; return regions
  (swap! cfg assoc (.getSheetName sheet)
    {"merges" (map #(let [region (.getMergedRegion sheet %)]
            [(.getFirstColumn region)
            (.getFirstRow region)
            (.getLastColumn region)
            (.getLastRow region)]) (range (.getNumMergedRegions sheet)))
    })
  (reduce #(conj %1 (get-row-data %2)) [] (xls/row-seq sheet)))

(defn- get-column-count[data]
  (reduce #(max %1 (count %2)) 0 data))

(defn get-excel-data [workbook]
  (let [cfg (atom {})
        data (reduce #(assoc %1 (.getSheetName %2) 
                     (get-sheet-data %2 cfg)) {} (xls/sheet-seq workbook))]
    ;                    
     (doseq [[k _] data]
       (let [sheet (.getSheet workbook k)]
            (swap! cfg assoc k
                (assoc (get @cfg k)
                  "col-widths" 
                  (map #(.getColumnWidth sheet %) 
                       (range (get-column-count (get data k))))))
         )
       )
 
    {:data data
     :meta @cfg
     }
    )
  )

