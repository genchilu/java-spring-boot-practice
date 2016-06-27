主要重點為減輕 DDoS 攻擊的負擔
* 盡可能減輕系統記憶體負擔，例如將 session 存到 redis
* 用 streaming 做檔案傳輸
* 對於耗費大量資源的 function ，用 queue 解決
