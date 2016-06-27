主要重點
* session name 要換掉，預設是 JSESSION，會暴露後台實作的技術
* 可以在 session 內綁定一些用戶資訊 (ip，agent等)，用於驗證每次用該 session 存取時的資訊是否一致，減輕中間人攻擊可能性。
* 設定 session timeout 時間
* 可以將 session timeout 分兩階段，較短的 timeout 用於存取較敏感的資料，較長的 timeout 用於存取一般權限的資料
* 正確設定 cookie (maxAge 等)
* 正確設定 header
