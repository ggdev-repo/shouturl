DROP TABLE IF EXISTS t_short_url;

CREATE TABLE t_short_url (
      `url_key`     VARBINARY(20)      NOT NULL       COMMENT 'urlKey'
    , `short_url`   VARCHAR(200)       NOT NULL       COMMENT 'shortUrl'
    , `long_url`    VARCHAR(1000)      NOT NULL       COMMENT 'longUrl'
    , `hash_url`    VARCHAR(100)       NOT NULL       COMMENT 'hashUrl'
    , `url_desc`    VARCHAR(2000)                     COMMENT 'urlDesc'
    , `use_yn`      VARCHAR(1)         NOT NULL DEFAULT 'Y' COMMENT '상태'
    , `reg_id`      VARCHAR(100)       NOT NULL        COMMENT '등록자 ID'
    , `mod_id`      VARCHAR(100)       NOT NULL        COMMENT '수정자 ID'
    , `reg_dttm`    DATETIME DEFAULT CURRENT_TIMESTAMP  COMMENT '등록일자'
    , `mod_dttm`    DATETIME NOT NULL     COMMENT '수정일자'
    , PRIMARY KEY (`url_key`)
) ;


ALTER TABLE t_short_url ADD UNIQUE i_t_short_url_hash(`hash_url`);

DROP TABLE IF EXISTS t_short_url_log;

CREATE TABLE t_short_url_log (
      `bas_dt`             VARCHAR(8) COMMENT '기준일자'
    , `url_key`            VARBINARY(20)           COMMENT 'urlKey'
    , `long_url`           VARCHAR(1000)       COMMENT 'longUrl'
    , `header_params`      VARCHAR(4000)        COMMENT 'header_params'
    , `referer`            VARCHAR(1000)                   COMMENT 'referer'
    , `user_agent`         VARCHAR(1000)                   COMMENT 'user-agent'
    , `sec_ch_ua`          VARCHAR(1000)                   COMMENT 'sec-ch-ua'
    , `reg_dttm`           DATETIME DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일자'
) ;


ALTER TABLE t_short_url_log ADD INDEX i_t_short_url_log_key(`url_key`);
ALTER TABLE t_short_url_log ADD INDEX i_t_short_url_log_basdt(bas_dt);

COMMIT;