預防 sql injection
* 盡可能用 hibernate 
* 即使要自己組 sql ，盡可能用 preparedstatement
* 遇到不能用 preparedstatement 的情境，務必要驗證每個 user 輸入的 input
* 讀寫分離
