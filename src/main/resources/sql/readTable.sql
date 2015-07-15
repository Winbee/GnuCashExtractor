SELECT
  t.description                           AS description,
  a1.name                                 AS name,
  CAST(t.post_date AS interger) / 1000000 AS post_date,
  s.value_num                             AS value_num,
  ABS(s.value_num)                        AS abs_value_num
FROM ACCOUNTS AS a1
  INNER JOIN SPLITS AS s ON s.account_guid = a1.guid
  INNER JOIN TRANSACTIONS AS t ON s.tx_guid = t.guid
WHERE a1.account_type <> 'ROOT'
      AND CAST(t.post_date AS interger) / 1000000 >= ?
      AND CAST(t.post_date AS interger) / 1000000 < ?
UNION
SELECT
  t.description                           AS description,
  a2.name                                 AS name,
  CAST(t.post_date AS interger) / 1000000 AS post_date,
  s.value_num                             AS value_num,
  ABS(s.value_num)                        AS abs_value_num
FROM ACCOUNTS AS a1
  INNER JOIN ACCOUNTS AS a2 ON a1.parent_guid = a2.guid
  INNER JOIN SPLITS AS s ON s.account_guid = a1.guid
  INNER JOIN TRANSACTIONS AS t ON s.tx_guid = t.guid
WHERE a2.account_type <> 'ROOT'
      AND CAST(t.post_date AS interger) / 1000000 >= ?
      AND CAST(t.post_date AS interger) / 1000000 < ?
UNION
SELECT
  t.description                           AS description,
  a3.name                                 AS name,
  CAST(t.post_date AS interger) / 1000000 AS post_date,
  s.value_num                             AS value_num,
  ABS(s.value_num)                        AS abs_value_num
FROM ACCOUNTS AS a1
  INNER JOIN ACCOUNTS AS a2 ON a1.parent_guid = a2.guid
  INNER JOIN ACCOUNTS AS a3 ON a2.parent_guid = a3.guid
  INNER JOIN SPLITS AS s ON s.account_guid = a1.guid
  INNER JOIN TRANSACTIONS AS t ON s.tx_guid = t.guid
WHERE a3.account_type <> 'ROOT'
      AND CAST(t.post_date AS interger) / 1000000 >= ?
      AND CAST(t.post_date AS interger) / 1000000 < ?
UNION
SELECT
  t.description                           AS description,
  a4.name                                 AS name,
  CAST(t.post_date AS interger) / 1000000 AS post_date,
  s.value_num                             AS value_num,
  ABS(s.value_num)                        AS abs_value_num
FROM ACCOUNTS AS a1
  INNER JOIN ACCOUNTS AS a2 ON a1.parent_guid = a2.guid
  INNER JOIN ACCOUNTS AS a3 ON a2.parent_guid = a3.guid
  INNER JOIN ACCOUNTS AS a4 ON a3.parent_guid = a4.guid
  INNER JOIN SPLITS AS s ON s.account_guid = a1.guid
  INNER JOIN TRANSACTIONS AS t ON s.tx_guid = t.guid
WHERE a4.account_type <> 'ROOT'
      AND CAST(t.post_date AS interger) / 1000000 >= ?
      AND CAST(t.post_date AS interger) / 1000000 < ?
UNION
SELECT
  t.description                           AS description,
  a5.name                                 AS name,
  CAST(t.post_date AS interger) / 1000000 AS post_date,
  s.value_num                             AS value_num,
  ABS(s.value_num)                        AS abs_value_num
FROM ACCOUNTS AS a1
  INNER JOIN ACCOUNTS AS a2 ON a1.parent_guid = a2.guid
  INNER JOIN ACCOUNTS AS a3 ON a2.parent_guid = a3.guid
  INNER JOIN ACCOUNTS AS a4 ON a3.parent_guid = a4.guid
  INNER JOIN ACCOUNTS AS a5 ON a4.parent_guid = a5.guid
  INNER JOIN SPLITS AS s ON s.account_guid = a1.guid
  INNER JOIN TRANSACTIONS AS t ON s.tx_guid = t.guid
WHERE a5.account_type <> 'ROOT'
      AND CAST(t.post_date AS interger) / 1000000 >= ?
      AND CAST(t.post_date AS interger) / 1000000 < ?
ORDER BY post_date
  DESC, abs_value_num
  DESC