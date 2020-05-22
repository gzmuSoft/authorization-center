--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1 (Debian 12.1-1.pgdg100+1)
-- Dumped by pg_dump version 12.2

-- Started on 2020-05-22 21:19:45 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE auth;
--
-- TOC entry 3286 (class 1262 OID 16385)
-- Name: auth; Type: DATABASE; Schema: -; Owner: gzmu
--

CREATE DATABASE auth WITH ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE auth OWNER TO gzmu;

\connect auth

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


ALTER SCHEMA public OWNER TO gzmu;

--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: gzmu
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 204 (class 1259 OID 16388)
-- Name: auth_center_res_sql; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.auth_center_res_sql
    START WITH 12
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_center_res_sql OWNER TO gzmu;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 205 (class 1259 OID 16390)
-- Name: auth_center_res; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.auth_center_res (
                                        id bigint DEFAULT nextval('public.auth_center_res_sql'::regclass) NOT NULL,
                                        name character varying(255) DEFAULT NULL::character varying,
                                        spell character varying(255) DEFAULT NULL::character varying,
                                        url character varying(255) DEFAULT NULL::character varying,
                                        describe character varying(255) DEFAULT NULL::character varying,
                                        method character varying(255) DEFAULT 'GET'::character varying,
                                        sort smallint DEFAULT '1'::smallint NOT NULL,
                                        create_user character varying(255) DEFAULT NULL::character varying,
                                        create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                        modify_user character varying(255) DEFAULT NULL::character varying,
                                        modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                        remark character varying(255) DEFAULT NULL::character varying,
                                        is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.auth_center_res OWNER TO gzmu;

--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE auth_center_res; Type: COMMENT; Schema: public; Owner: gzmu
--

COMMENT ON TABLE public.auth_center_res IS '资源管理：
菜单资源——前端菜单，name为路由名称，url菜单名称，method菜单图标，remark菜单header
后端资源——权限控制，name、remark为空
功能资源——前端路由（没有在菜单上），name为路由名称，remark为空
资源控制——对于资源具有的操作，name资源描述，remark资源英文、method资源操作方式';


--
-- TOC entry 206 (class 1259 OID 16409)
-- Name: auth_center_role_res_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.auth_center_role_res_seq
    START WITH 11
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_center_role_res_seq OWNER TO gzmu;

--
-- TOC entry 207 (class 1259 OID 16411)
-- Name: auth_center_role_res; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.auth_center_role_res (
                                             id bigint DEFAULT nextval('public.auth_center_role_res_seq'::regclass) NOT NULL,
                                             name character varying(255) DEFAULT NULL::character varying,
                                             spell character varying(255) DEFAULT NULL::character varying,
                                             role_id bigint NOT NULL,
                                             res_id bigint NOT NULL,
                                             sort smallint,
                                             create_user character varying(255) DEFAULT NULL::character varying,
                                             create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                             modify_user character varying(255) DEFAULT NULL::character varying,
                                             modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                             remark character varying(255) DEFAULT NULL::character varying,
                                             is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.auth_center_role_res OWNER TO gzmu;

--
-- TOC entry 208 (class 1259 OID 16426)
-- Name: client_details_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.client_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_details_seq OWNER TO gzmu;

--
-- TOC entry 209 (class 1259 OID 16428)
-- Name: client_details; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.client_details (
                                       id bigint DEFAULT nextval('public.client_details_seq'::regclass) NOT NULL,
                                       name character varying(255) DEFAULT NULL::character varying,
                                       client_id character varying(256) NOT NULL,
                                       resource_ids character varying(256) NOT NULL,
                                       client_secret character varying(256) NOT NULL,
                                       scope character varying(256) DEFAULT 'READ'::character varying NOT NULL,
                                       grant_types character varying(256) NOT NULL,
                                       redirect_url character varying(256) DEFAULT NULL::character varying,
                                       authorities character varying(256) DEFAULT NULL::character varying,
                                       access_token_validity integer,
                                       refresh_token_validity integer,
                                       additional_information character varying(4096) DEFAULT '{"a":"1"}'::character varying,
                                       auto_approve_scopes character varying(256) DEFAULT ''::character varying NOT NULL,
                                       spell character varying(255) DEFAULT NULL::character varying,
                                       sort smallint DEFAULT '1'::smallint NOT NULL,
                                       create_user character varying(255) DEFAULT NULL::character varying,
                                       create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                       modify_user character varying(255) DEFAULT NULL::character varying,
                                       modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                       remark character varying(255) DEFAULT NULL::character varying,
                                       is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.client_details OWNER TO gzmu;

--
-- TOC entry 210 (class 1259 OID 16449)
-- Name: semester_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.semester_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.semester_seq OWNER TO gzmu;

--
-- TOC entry 211 (class 1259 OID 16451)
-- Name: semester; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.semester (
                                 id bigint DEFAULT nextval('public.semester_seq'::regclass) NOT NULL,
                                 name character varying(255) DEFAULT NULL::character varying,
                                 spell character varying(255) DEFAULT NULL::character varying,
                                 school_id bigint NOT NULL,
                                 start_date date,
                                 end_date date,
                                 sort smallint,
                                 create_user character varying(255) DEFAULT NULL::character varying,
                                 create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 modify_user character varying(255) DEFAULT NULL::character varying,
                                 modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 remark character varying(255) DEFAULT NULL::character varying,
                                 is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.semester OWNER TO gzmu;

--
-- TOC entry 212 (class 1259 OID 16466)
-- Name: student_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.student_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.student_seq OWNER TO gzmu;

--
-- TOC entry 213 (class 1259 OID 16468)
-- Name: student; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.student (
                                id bigint DEFAULT nextval('public.student_seq'::regclass) NOT NULL,
                                name character varying(255) DEFAULT NULL::character varying,
                                spell character varying(255) DEFAULT NULL::character varying,
                                user_id bigint,
                                school_id bigint,
                                college_id bigint,
                                dep_id bigint,
                                specialty_id bigint,
                                classes_id bigint,
                                no character varying(20) DEFAULT NULL::character varying NOT NULL,
                                gender character varying(255) DEFAULT NULL::character varying,
                                id_number character varying(18) DEFAULT NULL::character varying,
                                birthday date,
                                enter_date date,
                                academic bigint,
                                graduation_date date,
                                graduate_institution character varying(255) DEFAULT NULL::character varying,
                                original_major character varying(255) DEFAULT NULL::character varying,
                                resume character varying(4086) DEFAULT NULL::character varying,
                                sort smallint DEFAULT 1 NOT NULL,
                                create_user character varying(255) DEFAULT NULL::character varying,
                                create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                modify_user character varying(255) DEFAULT NULL::character varying,
                                modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                remark character varying(255) DEFAULT NULL::character varying,
                                is_enable boolean DEFAULT true NOT NULL,
                                nation bigint,
                                study_year integer DEFAULT 4 NOT NULL
);


ALTER TABLE public.student OWNER TO gzmu;

--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN student.study_year; Type: COMMENT; Schema: public; Owner: gzmu
--

COMMENT ON COLUMN public.student.study_year IS '学制';


--
-- TOC entry 214 (class 1259 OID 16490)
-- Name: sys_data_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_data_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_data_seq OWNER TO gzmu;

--
-- TOC entry 215 (class 1259 OID 16492)
-- Name: sys_data; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_data (
                                 id bigint DEFAULT nextval('public.sys_data_seq'::regclass) NOT NULL,
                                 name character varying(50) NOT NULL,
                                 spell character varying(255) DEFAULT NULL::character varying,
                                 parent_id bigint DEFAULT '0'::bigint,
                                 brief character varying(2048) DEFAULT NULL::character varying,
                                 type smallint DEFAULT '0'::smallint,
                                 sort smallint DEFAULT '1'::smallint,
                                 create_user character varying(255) DEFAULT NULL::character varying,
                                 create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 modify_user character varying(255) DEFAULT NULL::character varying,
                                 modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 remark character varying(255) DEFAULT NULL::character varying,
                                 is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.sys_data OWNER TO gzmu;

--
-- TOC entry 216 (class 1259 OID 16510)
-- Name: sys_res_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_res_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_res_seq OWNER TO gzmu;

--
-- TOC entry 217 (class 1259 OID 16512)
-- Name: sys_res; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_res (
                                id bigint DEFAULT nextval('public.sys_res_seq'::regclass) NOT NULL,
                                name character varying(255) DEFAULT NULL::character varying,
                                spell character varying(255) DEFAULT NULL::character varying,
                                url character varying(255) DEFAULT NULL::character varying,
                                describe character varying(255) DEFAULT NULL::character varying,
                                method character varying(255) DEFAULT 'GET'::character varying,
                                sort smallint DEFAULT '1'::smallint NOT NULL,
                                create_user character varying(255) DEFAULT NULL::character varying,
                                create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                modify_user character varying(255) DEFAULT NULL::character varying,
                                modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                remark character varying(255) DEFAULT NULL::character varying,
                                is_enable boolean DEFAULT true NOT NULL,
                                scopes character varying(55) DEFAULT 'READ'::character varying NOT NULL
);


ALTER TABLE public.sys_res OWNER TO gzmu;

--
-- TOC entry 218 (class 1259 OID 16532)
-- Name: sys_role_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_role_seq OWNER TO gzmu;

--
-- TOC entry 219 (class 1259 OID 16534)
-- Name: sys_role; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_role (
                                 id bigint DEFAULT nextval('public.sys_role_seq'::regclass) NOT NULL,
                                 name character varying(255) NOT NULL,
                                 spell character varying(255) DEFAULT NULL::character varying,
                                 des character varying(128) DEFAULT NULL::character varying,
                                 icon_cls character varying(55) DEFAULT 'status_online'::character varying,
                                 parent_id bigint DEFAULT '0'::bigint NOT NULL,
                                 sort smallint DEFAULT 1 NOT NULL,
                                 create_user character varying(255) DEFAULT NULL::character varying,
                                 create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 modify_user character varying(255) DEFAULT NULL::character varying,
                                 modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 remark character varying(255) DEFAULT NULL::character varying,
                                 is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.sys_role OWNER TO gzmu;

--
-- TOC entry 220 (class 1259 OID 16552)
-- Name: sys_role_res_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_role_res_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_role_res_seq OWNER TO gzmu;

--
-- TOC entry 221 (class 1259 OID 16554)
-- Name: sys_role_res; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_role_res (
                                     id bigint DEFAULT nextval('public.sys_role_res_seq'::regclass) NOT NULL,
                                     name character varying(255) DEFAULT NULL::character varying,
                                     spell character varying(255) DEFAULT NULL::character varying,
                                     role_id bigint,
                                     res_id bigint,
                                     sort smallint,
                                     create_user character varying(255) DEFAULT NULL::character varying,
                                     create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                     modify_user character varying(255) DEFAULT NULL::character varying,
                                     modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                     remark character varying(255) DEFAULT NULL::character varying,
                                     is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.sys_role_res OWNER TO gzmu;

--
-- TOC entry 222 (class 1259 OID 16569)
-- Name: sys_user_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_user_seq OWNER TO gzmu;

--
-- TOC entry 223 (class 1259 OID 16571)
-- Name: sys_user; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_user (
                                 id bigint DEFAULT nextval('public.sys_user_seq'::regclass) NOT NULL,
                                 name character varying(55) DEFAULT NULL::character varying,
                                 spell character varying(255) DEFAULT ''::character varying,
                                 password character varying(255) DEFAULT NULL::character varying,
                                 status character varying(25) DEFAULT 'NORMAL'::character varying NOT NULL,
                                 image character varying(255) DEFAULT 'http://image.japoul.cn/bg-01.jpg'::character varying,
                                 email character varying(255) DEFAULT NULL::character varying,
                                 phone character varying(20) DEFAULT NULL::character varying,
                                 online_status boolean DEFAULT true,
                                 sort smallint,
                                 create_user character varying(255) DEFAULT NULL::character varying,
                                 create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 modify_user character varying(255) DEFAULT NULL::character varying,
                                 modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                 remark character varying(255) DEFAULT NULL::character varying,
                                 is_enable boolean DEFAULT true NOT NULL,
                                 avatar character varying(255) DEFAULT 'http://image.japoul.cn/me.jpg'::character varying
);


ALTER TABLE public.sys_user OWNER TO gzmu;

--
-- TOC entry 224 (class 1259 OID 16593)
-- Name: sys_user_role_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.sys_user_role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_user_role_seq OWNER TO gzmu;

--
-- TOC entry 225 (class 1259 OID 16595)
-- Name: sys_user_role; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.sys_user_role (
                                      id bigint DEFAULT nextval('public.sys_user_role_seq'::regclass) NOT NULL,
                                      name character varying(254) DEFAULT NULL::character varying,
                                      spell character varying(254) DEFAULT NULL::character varying,
                                      user_id bigint NOT NULL,
                                      role_id bigint NOT NULL,
                                      sort smallint,
                                      create_user character varying(255) DEFAULT NULL::character varying,
                                      create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                      modify_user character varying(255) DEFAULT NULL::character varying,
                                      modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                      remark character varying(255) DEFAULT NULL::character varying,
                                      is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.sys_user_role OWNER TO gzmu;

--
-- TOC entry 228 (class 1259 OID 16635)
-- Name: teacher_seq; Type: SEQUENCE; Schema: public; Owner: gzmu
--

CREATE SEQUENCE public.teacher_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teacher_seq OWNER TO gzmu;

--
-- TOC entry 227 (class 1259 OID 16612)
-- Name: teacher; Type: TABLE; Schema: public; Owner: gzmu
--

CREATE TABLE public.teacher (
                                id bigint DEFAULT nextval('public.teacher_seq'::regclass) NOT NULL,
                                name character varying(255) DEFAULT NULL::character varying,
                                spell character varying(255) DEFAULT NULL::character varying,
                                user_id bigint,
                                school_id bigint,
                                college_id bigint,
                                dep_id bigint,
                                gender character varying(255) DEFAULT NULL::character varying,
                                birthday date,
                                nation bigint,
                                degree bigint,
                                academic bigint,
                                graduation_date date,
                                major character varying(255) DEFAULT NULL::character varying,
                                graduate_institution character varying(255) DEFAULT NULL::character varying,
                                major_research character varying(255) DEFAULT NULL::character varying,
                                resume character varying(2048) DEFAULT NULL::character varying,
                                work_date date,
                                prof_title bigint,
                                prof_title_ass_date date,
                                is_academic_leader boolean DEFAULT false NOT NULL,
                                subject_category character varying(255) DEFAULT NULL::character varying,
                                id_number character varying(18) DEFAULT NULL::character varying,
                                sort smallint,
                                create_user character varying(255) DEFAULT NULL::character varying,
                                create_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                modify_user character varying(255) DEFAULT NULL::character varying,
                                modify_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
                                remark character varying(255) DEFAULT NULL::character varying,
                                is_enable boolean DEFAULT true NOT NULL
);


ALTER TABLE public.teacher OWNER TO gzmu;


--
-- TOC entry 3086 (class 2606 OID 16881)
-- Name: auth_center_res auth_center_res_seq; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.auth_center_res
    ADD CONSTRAINT auth_center_res_seq PRIMARY KEY (id);


--
-- TOC entry 3092 (class 2606 OID 16883)
-- Name: client_details client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.client_details
    ADD CONSTRAINT client_details_pkey PRIMARY KEY (id);


--
-- TOC entry 3095 (class 2606 OID 16885)
-- Name: semester semester_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.semester
    ADD CONSTRAINT semester_pkey PRIMARY KEY (id);


--
-- TOC entry 3098 (class 2606 OID 16887)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- TOC entry 3100 (class 2606 OID 16889)
-- Name: sys_data sys_data_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_data
    ADD CONSTRAINT sys_data_pkey PRIMARY KEY (id);


--
-- TOC entry 3102 (class 2606 OID 16891)
-- Name: sys_res sys_res_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_res
    ADD CONSTRAINT sys_res_pkey PRIMARY KEY (id);


--
-- TOC entry 3104 (class 2606 OID 16893)
-- Name: sys_role sys_role_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_role
    ADD CONSTRAINT sys_role_pkey PRIMARY KEY (id);


--
-- TOC entry 3088 (class 2606 OID 16895)
-- Name: auth_center_role_res sys_role_res_copy1_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.auth_center_role_res
    ADD CONSTRAINT sys_role_res_copy1_pkey PRIMARY KEY (id);


--
-- TOC entry 3106 (class 2606 OID 16897)
-- Name: sys_role_res sys_role_res_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_role_res
    ADD CONSTRAINT sys_role_res_pkey PRIMARY KEY (id);


--
-- TOC entry 3110 (class 2606 OID 16899)
-- Name: sys_user sys_user_email_uindex; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT sys_user_email_uindex UNIQUE (email);


--
-- TOC entry 3112 (class 2606 OID 16901)
-- Name: sys_user sys_user_name_uindex; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT sys_user_name_uindex UNIQUE (name);


--
-- TOC entry 3114 (class 2606 OID 16903)
-- Name: sys_user sys_user_phone_uindex; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT sys_user_phone_uindex UNIQUE (phone);


--
-- TOC entry 3116 (class 2606 OID 16905)
-- Name: sys_user sys_user_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT sys_user_pkey PRIMARY KEY (id);


--
-- TOC entry 3120 (class 2606 OID 16907)
-- Name: sys_user_role sys_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT sys_user_role_pkey PRIMARY KEY (id);


--
-- TOC entry 3123 (class 2606 OID 16909)
-- Name: teacher teacher_pkey; Type: CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.teacher
    ADD CONSTRAINT teacher_pkey PRIMARY KEY (id);


--
-- TOC entry 3093 (class 1259 OID 16926)
-- Name: fk_school; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX fk_school ON public.semester USING btree (school_id);


--
-- TOC entry 3117 (class 1259 OID 16927)
-- Name: fk_ur_role; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX fk_ur_role ON public.sys_user_role USING btree (role_id);


--
-- TOC entry 3118 (class 1259 OID 16928)
-- Name: fk_ur_user; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX fk_ur_user ON public.sys_user_role USING btree (user_id);


--
-- TOC entry 3096 (class 1259 OID 16929)
-- Name: student_no_uindex; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE UNIQUE INDEX student_no_uindex ON public.student USING btree (no);


--
-- TOC entry 3107 (class 1259 OID 16930)
-- Name: sys_role_res_sys_res_id_fk; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX sys_role_res_sys_res_id_fk ON public.sys_role_res USING btree (res_id);


--
-- TOC entry 3089 (class 1259 OID 16931)
-- Name: sys_role_res_sys_res_id_fk_copy1; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX sys_role_res_sys_res_id_fk_copy1 ON public.auth_center_role_res USING btree (res_id);


--
-- TOC entry 3108 (class 1259 OID 16932)
-- Name: sys_role_res_sys_role_id_fk; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX sys_role_res_sys_role_id_fk ON public.sys_role_res USING btree (role_id);


--
-- TOC entry 3090 (class 1259 OID 16933)
-- Name: sys_role_res_sys_role_id_fk_copy1; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE INDEX sys_role_res_sys_role_id_fk_copy1 ON public.auth_center_role_res USING btree (role_id);


--
-- TOC entry 3121 (class 1259 OID 16934)
-- Name: teacher_id_number_uindex; Type: INDEX; Schema: public; Owner: gzmu
--

CREATE UNIQUE INDEX teacher_id_number_uindex ON public.teacher USING btree (id_number);


--
-- TOC entry 3124 (class 2606 OID 16941)
-- Name: auth_center_role_res auth_center_role_res_sys_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.auth_center_role_res
    ADD CONSTRAINT auth_center_role_res_sys_role_id_fk FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


--
-- TOC entry 3129 (class 2606 OID 16946)
-- Name: sys_user_role fk_ur_role; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


--
-- TOC entry 3130 (class 2606 OID 16951)
-- Name: sys_user_role fk_ur_user; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES public.sys_user(id);


--
-- TOC entry 3126 (class 2606 OID 16956)
-- Name: semester semester_school_ibfk; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.semester
    ADD CONSTRAINT semester_school_ibfk FOREIGN KEY (school_id) REFERENCES public.sys_data(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3125 (class 2606 OID 16961)
-- Name: auth_center_role_res sys_role_res_copy1_res_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.auth_center_role_res
    ADD CONSTRAINT sys_role_res_copy1_res_id_fkey FOREIGN KEY (res_id) REFERENCES public.auth_center_res(id);


--
-- TOC entry 3127 (class 2606 OID 16966)
-- Name: sys_role_res sys_role_res_sys_res_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_role_res
    ADD CONSTRAINT sys_role_res_sys_res_id_fk FOREIGN KEY (res_id) REFERENCES public.sys_res(id);


--
-- TOC entry 3128 (class 2606 OID 16971)
-- Name: sys_role_res sys_role_res_sys_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: gzmu
--

ALTER TABLE ONLY public.sys_role_res
    ADD CONSTRAINT sys_role_res_sys_role_id_fk FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


-- Completed on 2020-05-22 22:20:08 CST


--
-- TOC entry 3276 (class 0 OID 16571)
-- Dependencies: 223
-- Data for Name: sys_user; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_user VALUES (459, '323232323', '', '$2a$12$YlGSwDAZMiITfyNJZQqlC.HSVH4BzTknRmKeBJ.fASvTVeHPh1tJG', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', '12312312@qq.ca', '13212312312', true, NULL, 'admin', '2020-02-23 15:58:34', NULL, '2020-02-23 07:58:35', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (461, '13214213245', '', '$2a$12$CPjQ5IRrnngVi7Ul1WtiLestX.j5gTPxwOM4mlDM2e9VOCiNIc8xS', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', '123@123.vh', '13214213245', true, NULL, 'admin', '2020-02-23 16:12:09', NULL, '2020-02-23 08:12:10', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (2, 'student', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue246@163.com', '13765308261', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2019-08-20 22:55:17', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (3, 'teacher', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue247@163.com', '13765308260', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2019-08-20 22:55:17', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (504, '13112343214', '', '$2a$12$a.eXnn.J7z8HKHkHOCxi9uF5GBaTuSV//BJM7PN.RDUgoqJKHn9Vq', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13112343214', true, NULL, 'admin', '2020-02-23 18:38:50', NULL, '2020-02-23 10:23:05', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (505, '15412343214', '', '$2a$12$UHVVegzZLqBFf./SrSysQOkXe3QHB0HlojWtVamhg2P5MPxk8jsxC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '15412343214', true, NULL, 'admin', '2020-02-23 18:38:50', NULL, '2020-02-23 10:23:05', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (5, 'teacher1', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue247@163.cs', '13765308262', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2019-08-20 22:55:17', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (4, 'studentaa', '', '$2a$12$Bw7CLXMuqkjPciAV4H.kW.Qm3sDY/1wMjCQ3J.bUqvJLrTWMZc2hy', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue246@163.com1', '137653082611', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2019-08-20 22:55:17', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (408, 'test', '', 'test', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'test', '2020-02-22 11:02:23', NULL, '2020-02-22 11:02:23', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (480, '13412345444', '', '$2a$12$z5Bux2gDfk32mtJ.PpQE8uIWY/QYO6LnsLIreM1PYrdP4Wj0tvhi.', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13412345444', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (481, '15421245621', '', '$2a$12$9aspT1bi9x1ms7VXDN8XneHTBCWL1NGYIM9d.Rks.BD9nN80HZFFy', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '15421245621', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (482, '13241115423', '', '$2a$12$3Vrfrj9MUDfwtAm0Bq8t9uyiJQBtEaMnYIWLFBR06kDDT5plItDWK', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13241115423', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (483, '13853672141', '', '$2a$12$eqWCQlBXoVDLeUWSeWQmK.oLAoeGpw9oD90EQQTM7aE6M.PmG9yS6', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13853672141', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (484, '13768057131', '', '$2a$12$1KKafwQzMO/VuMrCy/nEaOWUIiJyLh2D7bPkYmAs282CcOenANYum', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13768057131', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (485, '13682442120', '', '$2a$12$UM2Xmg/Lb7WA.ufIwWnuVuHkv1XDRvtIgLMMXRU1WnYqzLevgnary', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13682442120', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (486, '13596827110', '', '$2a$12$dUIVlJhXUsl/fsSVldSFM.A8.2DBcHtFXyADP.e0rEMqbAnK7nssW', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13596827110', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (487, '13511212099', '', '$2a$12$6igj0nfPTduuhvgNRY6pbeOfnHX9i4WFR4Iag63DXmjORN4pUo72a', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13511212099', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (390, '201742060107', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (488, '13425597089', '', '$2a$12$Ol8ty18lCon/oZLXuVF3Pu0QgX3rsE82cptSK8nRGXsIWvIta53pu', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13425597089', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (489, '13339982078', '', '$2a$12$jwSV8ZDgnm3KX1KtQy/egeA87Jv4wauobR5P4jsb2id8IzII23C0u', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13339982078', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (490, '13254367068', '', '$2a$12$ehmfIAVn9csB4RmYFK0lqeyVMEah0uxniymHE0YibRffzKnHx1d32', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13254367068', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (491, '13168752057', '', '$2a$12$gN4c7A3Ae/GPEl0vpuZSvugfl0sFVz2lKGbj9iD2dUs.f9s5oX0NG', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13168752057', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (492, '13083137047', '', '$2a$12$VsbcD2axbvjOuDQmPFFoaO3av/ZCMs9HeVOOKZXTHkOBsNT8UiE5a', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '13083137047', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (493, '12997522036', '', '$2a$12$.Au.cBwFtsnYKttESYkqbezRxbGtkQt70lMj.28S4innykt9szCJa', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '12997522036', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (494, '12911907026', '', '$2a$12$YJqRk2EHnTHnx86CF4iyl.S7joGkIcd/jQB2nAUlqbx007dIWxc.m', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '12911907026', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (495, '12826292015', '', '$2a$12$q1s0xA3/kJ5m3iF2iAloouUBwrBOgC3TKtjJzG1CEe3NDc2z3uq5S', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '12826292015', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (496, '12740677005', '', '$2a$12$9htbXCEv5Te7nKM5En5I7ehFt.XG98.4FVB3sSoaSZ8ZICGLhBVUa', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '12740677005', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (497, '12655061994', '', '$2a$12$EWeJJqynhDUwYwZUA9cVT.pv0sY67fBgeu6dRwELFVWth1V3N7aoe', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, '12655061994', true, NULL, 'admin', '2020-02-23 17:17:33', NULL, '2020-02-23 09:17:33', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (1, 'admin', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue248@163.com', '15112123232', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2020-02-10 19:58:16', NULL, true, 'http://image.japoul.cn/bg-01.jpg');
INSERT INTO public.sys_user VALUES (391, '201742060108', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (389, '201742060106', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (394, '201742060111', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (395, '201742060112', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (392, '201742060109', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (393, '201742060110', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (398, '201742060115', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (399, '201742060116', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (396, '201742060113', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (397, '201742060114', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (402, '201742060119', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (403, '201742060120', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (400, '201742060117', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (401, '201742060118', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (406, '201742060123', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (404, '201742060121', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (405, '201742060122', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', NULL, NULL, true, NULL, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 'http://image.japoul.cn/me.jpg');
INSERT INTO public.sys_user VALUES (514, 'adminn', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL', 'http://image.japoul.cn/bg-01.jpg', 'lizhongyue242@163.com', '15112143232', false, 1, NULL, '2019-04-20 17:07:50', 'admin', '2020-02-10 19:58:16', NULL, true, 'http://image.japoul.cn/bg-01.jpg');

--
-- TOC entry 3272 (class 0 OID 16534)
-- Dependencies: 219
-- Data for Name: sys_role; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_role VALUES (2, 'ROLE_NO_LOGIN', NULL, '不需要登录就可以访问', 'mdi-mouse', 0, 1, NULL, '2019-06-23 10:48:42', NULL, '2019-08-07 15:20:27', NULL, true);
INSERT INTO public.sys_role VALUES (6, 'ROLE_DEPARTMENT_HEAD', NULL, '系主任', 'mdi-mouse', 4, 1, NULL, '2020-02-11 11:10:04', 'admin', '2020-02-11 11:10:04', '阿三大阿斯顿', true);
INSERT INTO public.sys_role VALUES (1, 'ROLE_PUBLIC', NULL, '需要登录，但不需要任何权限', 'mdi-mouse', 0, 1, NULL, '2019-08-06 14:37:01', 'admin', '2020-02-11 21:27:31', 'Test', true);
INSERT INTO public.sys_role VALUES (5, 'ROLE_STUDENT', NULL, '学生', 'mdi-mouse', 0, 1, NULL, '2019-12-28 22:49:47', 'admin', '2019-12-28 22:50:29', NULL, true);
INSERT INTO public.sys_role VALUES (7, 'ROLE_ACADEMIC_OFFICE', NULL, '教务处', 'mdi-mouse', 4, 1, NULL, '2020-02-14 11:25:52', NULL, '2020-02-14 11:25:52', NULL, true);
INSERT INTO public.sys_role VALUES (9, 'ROLE_DEAN', NULL, '院长', 'mdi-mouse', 4, 1, NULL, '2020-02-14 11:27:21', NULL, '2020-02-14 11:27:21', NULL, true);
INSERT INTO public.sys_role VALUES (8, 'ROLE_MONITORE', NULL, '班长', 'mdi-mouse', 5, 111, NULL, '2020-02-14 11:26:23', 'admin', '2020-02-14 11:26:23', NULL, true);
INSERT INTO public.sys_role VALUES (3, 'ROLE_ADMIN', NULL, '管理员', 'mdi-mouse', 0, 1, NULL, '2019-06-23 10:48:42', NULL, '2019-08-07 15:20:27', NULL, true);
INSERT INTO public.sys_role VALUES (4, 'ROLE_TEACHER', NULL, '教师', 'mdi-mouse', 0, 1, NULL, '2019-06-23 10:48:42', 'admin', '2019-08-07 15:20:27', NULL, true);
--
-- TOC entry 3258 (class 0 OID 16390)
-- Dependencies: 205
-- Data for Name: auth_center_res; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.auth_center_res VALUES (28, 'data', NULL, 'menu.data', '菜单-数据管理', 'mdi-view-dashboard', 2, NULL, '2020-02-13 06:52:26', NULL, '2020-02-13 06:52:26', 'menu.data', true);
INSERT INTO public.auth_center_res VALUES (7, NULL, NULL, '/base/sysData/type/*', '获取指定类型数据', 'GET', 1, NULL, '2020-02-08 05:39:09', NULL, '2020-02-08 05:39:09', NULL, true);
INSERT INTO public.auth_center_res VALUES (29, NULL, NULL, '/data', '获取分页数据', 'GET', 1, NULL, '2020-02-13 15:53:15', NULL, '2020-02-13 15:53:15', NULL, true);
INSERT INTO public.auth_center_res VALUES (8, NULL, NULL, '/base/sysData/info/*', '获取指定数据的层级关系', 'GET', 1, NULL, '2020-02-08 09:23:22', NULL, '2020-02-08 09:23:22', NULL, true);
INSERT INTO public.auth_center_res VALUES (9, NULL, NULL, '/me/user', '修改用户', 'PATCH', 1, NULL, '2020-02-08 14:00:44', NULL, '2020-02-08 14:00:44', NULL, true);
INSERT INTO public.auth_center_res VALUES (10, NULL, NULL, '/base/user/exist', '用户是否存在', 'GET', 1, NULL, '2020-02-08 17:48:34', NULL, '2020-02-08 17:48:34', NULL, true);
INSERT INTO public.auth_center_res VALUES (11, NULL, NULL, '/oauth/me', '获取用户信息', 'GET', 1, NULL, '2020-02-09 04:46:20', NULL, '2020-02-09 04:46:20', NULL, true);
INSERT INTO public.auth_center_res VALUES (12, NULL, NULL, '/base/sysData/types', '获取指定类型数据', 'GET', 1, NULL, '2020-02-09 11:38:59', NULL, '2020-02-09 11:38:59', NULL, true);
INSERT INTO public.auth_center_res VALUES (13, NULL, NULL, '/me/info', '修改当前登录用户的实体信息', 'PATCH', 1, NULL, '2020-02-10 16:17:10', NULL, '2020-02-10 16:17:10', NULL, true);
INSERT INTO public.auth_center_res VALUES (2, 'index', NULL, 'menu.dashboard', '菜单-仪表盘', 'mdi-view-dashboard', 1, NULL, '2020-02-06 15:56:37', NULL, '2020-02-06 15:56:37', 'menu.system', true);
INSERT INTO public.auth_center_res VALUES (30, NULL, NULL, '/data/*', '删除数据', 'DELETE', 1, NULL, '2020-02-13 16:20:19', NULL, '2020-02-13 16:20:19', NULL, true);
INSERT INTO public.auth_center_res VALUES (14, 'role', NULL, 'menu.role', '菜单-角色管理', 'mdi-view-dashboard', 2, NULL, '2020-02-10 16:38:36', NULL, '2020-02-10 16:38:36', 'menu.system', true);
INSERT INTO public.auth_center_res VALUES (4, 'system-setting', NULL, NULL, '功能-系统设置', 'GET', 1, NULL, '2020-02-07 12:55:48', NULL, '2020-02-07 12:55:48', NULL, true);
INSERT INTO public.auth_center_res VALUES (5, 'user-setting', NULL, NULL, '功能-用户设置', 'GET', 1, NULL, '2020-02-07 13:00:29', NULL, '2020-02-07 13:00:29', NULL, true);
INSERT INTO public.auth_center_res VALUES (15, NULL, NULL, '/role/parent/*', '获取用户的角色信息', 'GET', 1, NULL, '2020-02-11 05:08:19', NULL, '2020-02-11 05:08:19', NULL, true);
INSERT INTO public.auth_center_res VALUES (16, NULL, NULL, '/role/res/*', '获取指定角色的资源', 'GET', 1, NULL, '2020-02-11 12:47:33', NULL, '2020-02-11 12:47:33', NULL, true);
INSERT INTO public.auth_center_res VALUES (17, NULL, NULL, '/role', '更新角色信息', 'PATCH', 1, NULL, '2020-02-11 13:17:06', NULL, '2020-02-11 13:17:06', NULL, true);
INSERT INTO public.auth_center_res VALUES (3, NULL, NULL, '/me/menu', '获取当前登录用户菜单', 'GET', 1, NULL, '2020-02-07 09:58:05', NULL, '2020-02-07 09:58:05', NULL, true);
INSERT INTO public.auth_center_res VALUES (18, 'res', NULL, 'menu.res', '菜单-资源管理', 'mdi-view-dashboard', 3, NULL, '2020-02-11 14:54:45', NULL, '2020-02-11 14:54:45', 'menu.system', true);
INSERT INTO public.auth_center_res VALUES (19, NULL, NULL, '/res', '分页获取资源信息', 'GET', 1, NULL, '2020-02-11 17:55:24', NULL, '2020-02-11 17:55:24', NULL, true);
INSERT INTO public.auth_center_res VALUES (20, NULL, NULL, '/res', '修改资源数据', 'PATCH', 1, NULL, '2020-02-12 07:45:41', NULL, '2020-02-12 07:45:41', NULL, true);
INSERT INTO public.auth_center_res VALUES (31, 'semester', NULL, 'menu.semester', '菜单-学期管理', 'mdi-view-dashboard', 1, NULL, '2020-02-14 03:53:03', NULL, '2020-02-14 03:53:03', 'menu.data', true);
INSERT INTO public.auth_center_res VALUES (32, NULL, NULL, '/semester', '学期-分页信息', 'GET', 1, NULL, '2020-02-14 04:57:37', NULL, '2020-02-14 04:57:37', NULL, true);
INSERT INTO public.auth_center_res VALUES (1, NULL, NULL, '/me/routes', '获取当前登录用户的路由信息', 'GET', 66, NULL, '2020-02-06 15:08:14', 'admin', '2020-02-12 15:48:28', NULL, true);
INSERT INTO public.auth_center_res VALUES (33, NULL, NULL, '/semester', '学期-修改信息', 'PATCH', 1, NULL, '2020-02-14 07:39:37', NULL, '2020-02-14 07:39:37', NULL, true);
INSERT INTO public.auth_center_res VALUES (34, NULL, NULL, '/semester', '学期-增加信息', 'POST', 1, NULL, '2020-02-14 07:39:37', NULL, '2020-02-14 07:39:37', NULL, true);
INSERT INTO public.auth_center_res VALUES (21, NULL, NULL, '/res/*', '删除资源数据', 'DELETE', 1, NULL, '2020-02-12 08:15:51', NULL, '2020-02-12 08:15:51', NULL, true);
INSERT INTO public.auth_center_res VALUES (35, 'client', NULL, 'menu.client', '菜单-客户端信息', 'mdi-view-dashboard', 1, NULL, '2020-02-14 10:14:50', NULL, '2020-02-14 10:14:50', 'menu.oauth', true);
INSERT INTO public.auth_center_res VALUES (57, NULL, NULL, '/client', '获取客户端信息', 'GET', 1, NULL, '2020-02-23 12:26:45', NULL, '2020-02-23 12:26:45', NULL, true);
INSERT INTO public.auth_center_res VALUES (6, NULL, NULL, '/me/info', '获取当前登录用户的实体信息', 'GET', 0, NULL, '2020-02-07 16:07:20', 'admin', '2020-02-07 16:07:20', NULL, true);
INSERT INTO public.auth_center_res VALUES (22, NULL, NULL, '/res', '添加资源数据', 'POST', 1, NULL, '2020-02-12 11:32:29', NULL, '2020-02-12 11:32:29', NULL, true);
INSERT INTO public.auth_center_res VALUES (23, 'college', NULL, 'menu.college', '菜单-院校管理', 'mdi-view-dashboard', 1, NULL, '2020-02-12 13:01:13', NULL, '2020-02-12 13:01:13', 'menu.data', true);
INSERT INTO public.auth_center_res VALUES (25, NULL, NULL, '/data/parent/*', '根据父级获取数据', 'GET', 1, NULL, '2020-02-12 13:49:30', NULL, '2020-02-12 13:49:30', NULL, true);
INSERT INTO public.auth_center_res VALUES (26, NULL, NULL, '/data', '修改数据', 'PATCH', 1, NULL, '2020-02-12 16:57:55', NULL, '2020-02-12 16:57:55', NULL, true);
INSERT INTO public.auth_center_res VALUES (27, NULL, NULL, '/data', '添加数据', 'POST', 1, NULL, '2020-02-12 16:57:55', NULL, '2020-02-12 16:57:55', NULL, true);
INSERT INTO public.auth_center_res VALUES (24, NULL, NULL, '/data/type/*', '根据类型获取数据', 'GET', 1, NULL, '2020-02-12 13:49:30', NULL, '2020-02-12 13:49:30', NULL, true);
INSERT INTO public.auth_center_res VALUES (49, NULL, NULL, '/student/complete', '完整的修改某个学生信息', 'POST', 1, NULL, '2020-02-18 14:00:35', NULL, '2020-02-18 14:00:35', NULL, true);
INSERT INTO public.auth_center_res VALUES (50, NULL, NULL, '/student/import', '导入学生', 'POST', 1, NULL, '2020-02-20 10:15:08', NULL, '2020-02-20 10:15:08', NULL, true);
INSERT INTO public.auth_center_res VALUES (51, NULL, NULL, '/user/exist', '用户是否存在', 'POST', 1, NULL, '2020-02-20 11:58:37', NULL, '2020-02-20 11:58:37', NULL, true);
INSERT INTO public.auth_center_res VALUES (52, NULL, NULL, '/student', '添加一个学生', 'POST', 1, NULL, '2020-02-22 05:25:42', NULL, '2020-02-22 05:25:42', NULL, true);
INSERT INTO public.auth_center_res VALUES (53, NULL, NULL, '/teacher', '获取教师信息', 'GET', 1, NULL, '2020-02-22 08:42:23', NULL, '2020-02-22 08:42:23', NULL, true);
INSERT INTO public.auth_center_res VALUES (54, NULL, NULL, '/teacher', '修改教师信息', 'PATCH', 1, NULL, '2020-02-22 14:24:23', NULL, '2020-02-22 14:24:23', NULL, true);
INSERT INTO public.auth_center_res VALUES (41, NULL, NULL, '/student/me', '获取当前登录学生所在班级的所有学生基础进行', 'GET', 1, NULL, '2020-02-14 13:01:43', NULL, '2020-02-14 13:01:43', NULL, true);
INSERT INTO public.auth_center_res VALUES (43, NULL, NULL, '/student/*', '获取学生信息', 'GET', 1, NULL, '2020-02-15 07:57:34', NULL, '2020-02-15 07:57:34', NULL, true);
INSERT INTO public.auth_center_res VALUES (42, NULL, NULL, '/student', '修改学生信息', 'PATCH', 1, NULL, '2020-02-15 06:18:27', NULL, '2020-02-15 06:18:27', NULL, true);
INSERT INTO public.auth_center_res VALUES (58, NULL, NULL, '/client', '添加/修改客户端信息', 'POST', 1, NULL, '2020-02-24 06:37:33', NULL, '2020-02-24 06:37:33', NULL, true);
INSERT INTO public.auth_center_res VALUES (44, NULL, NULL, '/student', '获取学生信息', 'GET', 1, NULL, '2020-02-16 14:23:47', NULL, '2020-02-16 14:23:47', NULL, true);
INSERT INTO public.auth_center_res VALUES (45, NULL, NULL, '/student/complete', '完整的修改学生信息', 'PATCH', 1, NULL, '2020-02-17 13:42:47', NULL, '2020-02-17 13:42:47', NULL, true);
INSERT INTO public.auth_center_res VALUES (46, NULL, NULL, '/user/*', '获取某个用户信息', 'GET', 1, NULL, '2020-02-17 14:43:35', NULL, '2020-02-17 14:43:35', NULL, true);
INSERT INTO public.auth_center_res VALUES (47, NULL, NULL, '/user', '修改某个用户信息', 'PATCH', 1, NULL, '2020-02-17 14:43:53', NULL, '2020-02-17 14:43:53', NULL, true);
INSERT INTO public.auth_center_res VALUES (48, NULL, NULL, '/user/password', '重置某个用户密码', 'PATCH', 1, NULL, '2020-02-18 13:04:42', NULL, '2020-02-18 13:04:42', NULL, true);
INSERT INTO public.auth_center_res VALUES (55, NULL, NULL, '/teacher', '添加教师信息', 'POST', 1, NULL, '2020-02-23 06:35:55', NULL, '2020-02-23 06:35:55', NULL, true);
INSERT INTO public.auth_center_res VALUES (56, NULL, NULL, '/teacher/import', '导入教师', 'POST', 1, NULL, '2020-02-23 09:27:53', NULL, '2020-02-23 09:27:53', NULL, true);
INSERT INTO public.auth_center_res VALUES (36, 'user', NULL, 'menu.user', '菜单-用户管理', 'mdi-view-dashboard', 1, NULL, '2020-02-14 10:23:26', NULL, '2020-02-14 10:23:26', 'menu.userInfo', false);
INSERT INTO public.auth_center_res VALUES (39, 'student', NULL, 'menu.student', '菜单-学生信息', 'mdi-view-dashboard', 4, NULL, '2020-02-14 12:16:22', 'admin', '2020-02-14 12:16:22', 'menu.userInfo', true);
INSERT INTO public.auth_center_res VALUES (37, 'student-admin', NULL, 'menu.studentManagement', '菜单-管理学生信息', 'mdi-view-dashboard', 4, NULL, '2020-02-14 10:24:17', 'admin', '2020-02-14 10:24:17', 'menu.userInfo', true);
INSERT INTO public.auth_center_res VALUES (38, 'teacher-admin', NULL, 'menu.teacherManagement', '菜单-显示教师信息', 'mdi-view-dashboard', 4, NULL, '2020-02-14 10:24:17', 'admin', '2020-02-14 10:24:17', 'menu.userInfo', true);
INSERT INTO public.auth_center_res VALUES (60, NULL, NULL, '/dashboard', '获取仪表盘', 'GET', 1, NULL, '2020-05-19 15:19:04', NULL, '2020-05-19 15:19:04', NULL, true);


--
-- TOC entry 3260 (class 0 OID 16411)
-- Dependencies: 207
-- Data for Name: auth_center_role_res; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.auth_center_role_res VALUES (1, NULL, NULL, 1, 1, NULL, NULL, '2020-02-06 15:27:46', NULL, '2020-02-06 15:27:46', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (2, NULL, NULL, 1, 2, NULL, NULL, '2020-02-06 15:57:37', NULL, '2020-02-06 15:57:37', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (3, NULL, NULL, 1, 3, NULL, NULL, '2020-02-07 09:58:21', NULL, '2020-02-07 09:58:21', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (4, NULL, NULL, 1, 4, NULL, NULL, '2020-02-07 12:56:28', NULL, '2020-02-07 12:56:28', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (5, NULL, NULL, 1, 5, NULL, NULL, '2020-02-07 13:00:36', NULL, '2020-02-07 13:00:36', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (6, NULL, NULL, 1, 6, NULL, NULL, '2020-02-07 16:07:36', NULL, '2020-02-07 16:07:36', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (7, NULL, NULL, 1, 7, NULL, NULL, '2020-02-08 05:43:21', NULL, '2020-02-08 05:43:21', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (8, NULL, NULL, 1, 8, NULL, NULL, '2020-02-08 09:23:32', NULL, '2020-02-08 09:23:32', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (9, NULL, NULL, 1, 9, NULL, NULL, '2020-02-08 14:01:08', NULL, '2020-02-08 14:01:08', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (10, NULL, NULL, 1, 10, NULL, NULL, '2020-02-08 17:49:27', NULL, '2020-02-08 17:49:27', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (11, NULL, NULL, 1, 11, NULL, NULL, '2020-02-09 07:35:07', NULL, '2020-02-09 07:35:07', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (12, NULL, NULL, 1, 12, NULL, NULL, '2020-02-09 11:39:05', NULL, '2020-02-09 11:39:05', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (13, NULL, NULL, 1, 13, NULL, NULL, '2020-02-10 16:17:19', NULL, '2020-02-10 16:17:19', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (14, NULL, NULL, 3, 14, NULL, NULL, '2020-02-10 16:39:58', NULL, '2020-02-10 16:39:58', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (15, NULL, NULL, 3, 15, NULL, NULL, '2020-02-11 05:08:31', NULL, '2020-02-11 05:08:31', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (16, NULL, NULL, 3, 16, NULL, NULL, '2020-02-11 13:17:27', NULL, '2020-02-11 13:17:27', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (17, NULL, NULL, 3, 17, NULL, NULL, '2020-02-11 13:17:37', NULL, '2020-02-11 13:17:37', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (18, NULL, NULL, 3, 18, NULL, NULL, '2020-02-11 14:55:03', NULL, '2020-02-11 14:55:03', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (19, NULL, NULL, 3, 19, NULL, NULL, '2020-02-11 17:55:43', NULL, '2020-02-11 17:55:43', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (20, NULL, NULL, 3, 20, NULL, NULL, '2020-02-12 07:45:50', NULL, '2020-02-12 07:45:50', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (21, NULL, NULL, 3, 21, NULL, NULL, '2020-02-12 08:16:13', NULL, '2020-02-12 08:16:13', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (22, NULL, NULL, 3, 22, NULL, NULL, '2020-02-12 11:32:36', NULL, '2020-02-12 11:32:36', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (23, NULL, NULL, 3, 23, NULL, NULL, '2020-02-12 13:01:30', NULL, '2020-02-12 13:01:30', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (24, NULL, NULL, 3, 24, NULL, NULL, '2020-02-12 13:49:48', NULL, '2020-02-12 13:49:48', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (25, NULL, NULL, 3, 25, NULL, NULL, '2020-02-12 13:49:48', NULL, '2020-02-12 13:49:48', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (26, NULL, NULL, 3, 26, NULL, NULL, '2020-02-12 16:58:09', NULL, '2020-02-12 16:58:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (27, NULL, NULL, 3, 27, NULL, NULL, '2020-02-12 16:58:09', NULL, '2020-02-12 16:58:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (28, NULL, NULL, 3, 28, NULL, NULL, '2020-02-13 06:52:46', NULL, '2020-02-13 06:52:46', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (29, NULL, NULL, 3, 29, NULL, NULL, '2020-02-13 15:53:23', NULL, '2020-02-13 15:53:23', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (30, NULL, NULL, 3, 30, NULL, NULL, '2020-02-13 16:20:34', NULL, '2020-02-13 16:20:34', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (31, NULL, NULL, 3, 31, NULL, NULL, '2020-02-14 03:53:09', NULL, '2020-02-14 03:53:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (32, NULL, NULL, 3, 32, NULL, NULL, '2020-02-14 04:57:46', NULL, '2020-02-14 04:57:46', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (33, NULL, NULL, 3, 33, NULL, NULL, '2020-02-14 07:39:46', NULL, '2020-02-14 07:39:46', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (34, NULL, NULL, 3, 34, NULL, NULL, '2020-02-14 07:39:46', NULL, '2020-02-14 07:39:46', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (35, NULL, NULL, 3, 35, NULL, NULL, '2020-02-14 10:15:26', NULL, '2020-02-14 10:15:26', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (36, NULL, NULL, 3, 36, NULL, NULL, '2020-02-14 10:24:35', NULL, '2020-02-14 10:24:35', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (37, NULL, NULL, 3, 37, NULL, NULL, '2020-02-14 10:24:35', NULL, '2020-02-14 10:24:35', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (38, NULL, NULL, 3, 38, NULL, NULL, '2020-02-14 10:24:35', NULL, '2020-02-14 10:24:35', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (39, NULL, NULL, 5, 39, NULL, NULL, '2020-02-14 12:17:20', NULL, '2020-02-14 12:17:20', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (41, NULL, NULL, 5, 41, NULL, NULL, '2020-02-14 13:02:07', NULL, '2020-02-14 13:02:07', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (42, NULL, NULL, 4, 41, NULL, NULL, '2020-02-14 16:43:44', NULL, '2020-02-14 16:43:44', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (40, NULL, NULL, 4, 39, NULL, NULL, '2020-02-14 12:17:20', NULL, '2020-02-14 12:17:20', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (43, NULL, NULL, 6, 42, NULL, NULL, '2020-02-15 06:19:09', NULL, '2020-02-15 06:19:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (44, NULL, NULL, 7, 42, NULL, NULL, '2020-02-15 06:19:09', NULL, '2020-02-15 06:19:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (45, NULL, NULL, 9, 42, NULL, NULL, '2020-02-15 06:19:09', NULL, '2020-02-15 06:19:09', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (46, NULL, NULL, 6, 43, NULL, NULL, '2020-02-15 07:57:44', NULL, '2020-02-15 07:57:44', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (47, NULL, NULL, 7, 43, NULL, NULL, '2020-02-15 07:57:52', NULL, '2020-02-15 07:57:52', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (48, NULL, NULL, 9, 43, NULL, NULL, '2020-02-15 07:57:56', NULL, '2020-02-15 07:57:56', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (50, NULL, NULL, 7, 37, NULL, NULL, '2020-02-15 14:26:29', NULL, '2020-02-15 14:26:29', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (51, NULL, NULL, 9, 37, NULL, NULL, '2020-02-15 14:26:29', NULL, '2020-02-15 14:26:29', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (52, NULL, NULL, 7, 44, NULL, NULL, '2020-02-16 14:24:24', NULL, '2020-02-16 14:24:24', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (53, NULL, NULL, 3, 44, NULL, NULL, '2020-02-16 14:24:24', NULL, '2020-02-16 14:24:24', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (54, NULL, NULL, 9, 44, NULL, NULL, '2020-02-16 14:24:24', NULL, '2020-02-16 14:24:24', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (55, NULL, NULL, 3, 42, NULL, NULL, '2020-02-17 13:17:37', NULL, '2020-02-17 13:17:37', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (56, NULL, NULL, 3, 45, NULL, NULL, '2020-02-17 13:42:55', NULL, '2020-02-17 13:42:55', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (57, NULL, NULL, 3, 46, NULL, NULL, '2020-02-17 14:44:26', NULL, '2020-02-17 14:44:26', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (58, NULL, NULL, 3, 47, NULL, NULL, '2020-02-17 14:44:26', NULL, '2020-02-17 14:44:26', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (59, NULL, NULL, 3, 48, NULL, NULL, '2020-02-18 13:05:12', NULL, '2020-02-18 13:05:12', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (60, NULL, NULL, 3, 49, NULL, NULL, '2020-02-18 14:00:44', NULL, '2020-02-18 14:00:44', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (61, NULL, NULL, 3, 50, NULL, NULL, '2020-02-20 10:15:16', NULL, '2020-02-20 10:15:16', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (62, NULL, NULL, 3, 51, NULL, NULL, '2020-02-20 11:58:49', NULL, '2020-02-20 11:58:49', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (63, NULL, NULL, 3, 52, NULL, NULL, '2020-02-22 05:25:49', NULL, '2020-02-22 05:25:49', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (64, NULL, NULL, 3, 53, NULL, NULL, '2020-02-22 08:42:31', NULL, '2020-02-22 08:42:31', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (65, NULL, NULL, 3, 54, NULL, NULL, '2020-02-22 14:24:31', NULL, '2020-02-22 14:24:31', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (66, NULL, NULL, 3, 55, NULL, NULL, '2020-02-23 06:36:01', NULL, '2020-02-23 06:36:01', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (68, NULL, NULL, 3, 56, NULL, NULL, '2020-02-23 09:28:13', NULL, '2020-02-23 09:28:13', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (69, NULL, NULL, 3, 57, NULL, NULL, '2020-02-23 12:26:54', NULL, '2020-02-23 12:26:54', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (70, NULL, NULL, 3, 58, NULL, NULL, '2020-02-24 06:38:04', NULL, '2020-02-24 06:38:04', NULL, true);
INSERT INTO public.auth_center_role_res VALUES (72, NULL, NULL, 3, 60, NULL, NULL, '2020-05-19 15:19:25', NULL, '2020-05-19 15:19:25', NULL, true);


--
-- TOC entry 3262 (class 0 OID 16428)
-- Dependencies: 209
-- Data for Name: client_details; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.client_details VALUES (2, 'End-Of-University', 'End-Of-University', 'End-Of-University', '$2a$12$EKC5ypT//R5mzxQ/Stwul.7i39tZBXmOvL8xyuiQPV3ZWrB3cWdGC', 'READ', 'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 6000000, '{"a":"1"}', '', '0d04f678-fbc8-4f47-b0d4-8dc634005874', 1, 'admin', '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', '测试1', true);
INSERT INTO public.client_details VALUES (3, '授权中心', 'gzmu-auth', 'gzmu-auth', '$2a$12$IDY73g8L/jU1.bZ5ylrnpu/4mwY3mZ9E9L2GnSuE/JVjfYQPy.tw6', 'READ', 'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 6000000, '{"a":"1"}', '', 'gzmu-auth-secret', 1, NULL, '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', '贵州民族大学授权中心', true);
INSERT INTO public.client_details VALUES (4, 'test2', 'test', 'test', '$2a$12$zE5sRSfd9nuuw2FwuccjO.ep1uCRRWd4ND8cKfKLZ6Oq1.wXB293G', 'READ', 'password,refresh_token,sms,email,authorization_code', 'http://example.com', NULL, 600000, 6000000, '{"a":"1"}', '', 'gzmu-auth-secret', 1, NULL, '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', '测试2', true);
INSERT INTO public.client_details VALUES (1, '云课程', 'lesson-cloud', 'lesson-cloud', '$2a$12$mevxR8T/xecGIrlvgkEqMecoZHbggqzg0efnOOJ/WM8KmJmVj/cQ.', 'READ', 'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 6000000, '{"a":"1"}', '', 'lesson-cloud-secret', 1, NULL, '2019-12-23 23:39:07', NULL, '2019-12-23 23:39:07', '云课程在线考试平台', true);
INSERT INTO public.client_details VALUES (5, '教室考勤系统', 'sf2cs', 'sf2cs', '$2a$12$EKC5ypT//R5mzxQ/Stwul.7i39tZBXmOvL8xyuiQPV3ZWrB3cWdGC', 'READ', 'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 6000000, '{"a":"1"}', '', '0d04f678-fbc8-4f47-b0d4-8dc634005874', 1, 'admin', '2020-02-24 15:48:59', NULL, '2020-02-24 07:48:59', '123123', true);


--
-- TOC entry 3266 (class 0 OID 16468)
-- Dependencies: 213
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.student VALUES (394, 'testt', NULL, 386, 1, 2, 3, 4, 6, '1321233123213', '男', NULL, '2020-02-22', '2020-02-22', NULL, NULL, NULL, NULL, NULL, 1, 'admin', '2020-02-22 14:21:30', NULL, '2020-02-22 06:21:30', NULL, true, NULL, 4);
INSERT INTO public.student VALUES (106, '201742060106', 'jīn jiā róng ', 157, 1, 2, NULL, NULL, 4, '201742060106', '女', '520123199808110032', '1998-08-11', '2017-09-01', 51, '2017-06-30', NULL, '无', '我是一个学生', 81, 'admin', '2020-02-22 00:15:31', 'yms', '2020-02-04 17:53:48', NULL, true, 62, 4);
INSERT INTO public.student VALUES (4, '林雅南', 'lín yǎ nán ', 4, 29, 30, 31, 32, 34, '201742060004', '女', '522526202002050004', '2020-02-18', '2020-02-18', 9, '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:11', 'admin', '2020-02-04 17:53:11', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (395, 'aaaaa', NULL, 387, 1, 18, 19, 24, 25, '321321321', '女', '321312312312123123', NULL, NULL, 9, NULL, NULL, NULL, NULL, 1, 'admin', '2020-02-22 14:23:07', NULL, '2020-02-22 06:23:07', NULL, true, 58, 4);
INSERT INTO public.student VALUES (34, '郑昌梦', 'zhèng chāng mèng ', 34, 29, 30, 31, 37, 39, '201742060034', '女', '522526202002050034', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 11, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (31, '陈婉璇', 'chén wǎn xuán ', 31, 1, 2, 3, 13, 24, '201742060031', '女', '522526202002050031', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 41, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (29, '曹敏侑', 'cáo mǐn yòu ', 29, 1, 2, 3, 13, 20, '201742060029', '男', '522526202002050029', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (52, '倪怡芳', 'ní yí fāng ', 52, 1, 2, 3, 13, 20, '201742060052', '女', '522526202002050052', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (65, '吴俊伯', 'wú jun4 bó ', 65, 29, 44, 45, 46, 47, '201742060065', '男', '522526202002050065', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 47, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (19, '方一强', 'fāng yī qiáng ', 19, 1, 2, 3, 13, 24, '201742060019', '女', '522526202002050019', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 2, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (6, '刘柏宏', 'liú bǎi hóng ', 6, 29, 30, 3, 8, 12, '201742060006', '男', '522526202002050006', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 30, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (7, '阮建安', 'ruǎn jiàn ān ', 7, 1, 18, 3, 4, 7, '201742060007', '女', '522526202002050007', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 96, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (8, '林子帆', 'lín zǐ fān ', 8, 29, 44, 3, 13, 14, '201742060008', '男', '522526202002050008', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 95, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (9, '夏志豪', 'xià zhì háo ', 9, 29, 44, 19, 24, 27, '201742060009', '男', '522526202002050009', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (11, '李中冰', 'lǐ zhōng bīng ', 11, 1, 2, 19, 24, 28, '201742060011', '男', '522526202002050011', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (12, '黄文隆', 'huáng wén lóng ', 12, 1, 18, 3, 8, 10, '201742060012', '男', '522526202002050012', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (412, '林婉婷', 'lín wǎn tíng ', 404, 1, 2, NULL, 4, NULL, '201742060121', '男', '520123199508210045', '1995-08-21', '2017-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (2, '林国瑞', 'lín guó ruì ', 2, 1, 2, NULL, 4, 49, '201742060002', '男', '522526202002050002', '2020-02-05', '2016-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 19, 'yms', '2019-08-07 16:40:28', 'yms', '2019-08-07 16:40:28', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (16, '刘姿婷', 'liú zī tíng ', 16, 29, 30, 31, 37, 39, '201742060016', '女', '522526202002050016', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (17, '荣姿康', 'róng zī kāng ', 17, 29, 30, 31, 37, 39, '201742060017', '男', '522526202002050017', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (18, '吕致盈', 'lǚ zhì yíng ', 18, 1, 18, 19, 20, 21, '201742060018', '男', '522526202002050018', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 35, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (20, '黎芸贵', 'lí yún guì ', 20, 1, 18, 19, 20, 22, '201742060020', '男', '522526202002050020', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (21, '郑伊雯', 'zhèng yī wén ', 21, 29, 44, 45, 46, 47, '201742060021', '男', '522526202002050021', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 60, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (22, '雷进宝', 'léi jìn bǎo ', 22, 1, 18, 19, 24, 26, '201742060022', '女', '522526202002050022', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 92, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (23, '吴美隆', 'wú měi lóng ', 23, 1, 2, 3, 8, 12, '201742060023', '男', '522526202002050023', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (132, '柯乔喜', 'kē qiáo xǐ ', 132, 1, 2, 3, 13, 15, '201742060132', '男', '522526202002050132', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:49', 'yms', '2020-02-04 17:53:49', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (14, '傅智翔', 'fù zhì xiáng ', 14, 1, 2, 3, 8, 11, '201742060014', '男', '522526202002050014', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 60, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (24, '吴心真', 'wú xīn zhēn ', 24, 1, 2, 3, 8, 12, '201742060024', '男', '522526202002050024', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 13, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (25, '王美珠', 'wáng měi zhū ', 25, 29, 30, 31, 32, 33, '201742060025', '女', '522526202002050025', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 93, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (26, '郭芳天', 'guō fāng tiān ', 26, 29, 30, 31, 32, 33, '201742060026', '男', '522526202002050026', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 25, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (27, '李雅惠', 'lǐ yǎ huì ', 27, 29, 30, 31, 37, 38, '201742060027', '男', '522526202002050027', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 67, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (28, '陈文婷', 'chén wén tíng ', 28, 29, 30, 31, 32, 34, '201742060028', '女', '522526202002050028', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (30, '王依婷', 'wáng yī tíng ', 30, 1, 18, 19, 24, 25, '201742060030', '男', '522526202002050030', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 45, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (32, '吴美玉', 'wú měi yù ', 32, 29, 30, 31, 37, 38, '201742060032', '男', '522526202002050032', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 53, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (33, '蔡依婷', 'cài yī tíng ', 33, 29, 44, 45, 48, 50, '201742060033', '男', '522526202002050033', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 54, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (1, 'admin', 'zhāng jí wéi ', 1, 1, 2, 19, 20, 22, '1997', '男', '522526202002050001', '2020-02-19', '2017-09-01', 52, '2020-02-01', '', '', '**我是一个admin**', 64, 'yms', '2019-08-05 22:46:10', 'admin', '2020-02-11 00:22:19', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (35, '林家纶', 'lín jiā lún ', 35, 29, 30, 31, 37, 39, '201742060035', '男', '522526202002050035', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 49, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (36, '黄丽昆', 'huáng lì kūn ', 36, 29, 30, 31, 32, 33, '201742060036', '男', '522526202002050036', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (37, '李育泉', 'lǐ yù quán ', 37, 29, 30, 40, 41, 43, '201742060037', '女', '522526202002050037', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 57, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (38, '黄芸欢', 'huáng yún huān ', 38, 1, 2, 3, 13, 14, '201742060038', '男', '522526202002050038', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (39, '吴韵如', 'wú yùn rú ', 39, 29, 30, 40, 41, 42, '201742060039', '男', '522526202002050039', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 9, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (40, '李肇芬', 'lǐ zhào fēn ', 40, 1, 2, 3, 8, 10, '201742060040', '女', '522526202002050040', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 70, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (41, '卢木仲', 'lú mù zhòng ', 41, 1, 2, 3, 8, 10, '201742060041', '男', '522526202002050041', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 3, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (42, '李成白', 'lǐ chéng bái ', 42, 29, 30, 31, 32, 33, '201742060042', '男', '522526202002050042', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 40, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (43, '方兆玉', 'fāng zhào yù ', 43, 1, 2, 3, 13, 16, '201742060043', '女', '522526202002050043', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (44, '刘翊惠', 'liú yì huì ', 44, 29, 44, 45, 48, 50, '201742060044', '男', '522526202002050044', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 86, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (45, '丁汉臻', 'dīng hàn zhēn ', 45, 1, 2, 3, 4, 6, '201742060045', '男', '522526202002050045', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 78, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (46, '吴佳瑞', 'wú jiā ruì ', 46, 29, 30, 40, 41, 43, '201742060046', '女', '522526202002050046', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (48, '周白芷', 'zhōu bái zhǐ ', 48, 29, 30, 31, 32, 35, '201742060048', '男', '522526202002050048', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (49, '张姿妤', 'zhāng zī yú ', 49, 1, 2, 3, 8, 11, '201742060049', '女', '522526202002050049', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 64, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (50, '张虹伦', 'zhāng hóng lún ', 50, 29, 30, 31, 37, 38, '201742060050', '男', '522526202002050050', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 87, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (51, '周琼玟', 'zhōu qióng mín ', 51, 1, 2, 3, 13, 17, '201742060051', '男', '522526202002050051', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 68, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (53, '郭贵妃', 'guō guì fēi ', 53, 29, 30, 40, 41, 42, '201742060053', '男', '522526202002050053', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 69, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (54, '杨佩芳', 'yáng pèi fāng ', 54, 1, 2, 3, 4, 5, '201742060054', '男', '522526202002050054', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 29, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (55, '黄文旺', 'huáng wén wàng ', 55, 29, 44, 45, 48, 50, '201742060055', '女', '522526202002050055', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 16, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (56, '黄盛玫', 'huáng shèng méi ', 56, 29, 30, 31, 32, 36, '201742060056', '男', '522526202002050056', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 74, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (57, '郑丽青', 'zhèng lì qīng ', 57, 1, 18, 19, 24, 26, '201742060057', '男', '522526202002050057', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (58, '许智云', 'xǔ zhì yún ', 58, 1, 18, 19, 24, 26, '201742060058', '女', '522526202002050058', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 12, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (59, '张孟涵', 'zhāng mèng hán ', 59, 29, 30, 31, 37, 38, '201742060059', '男', '522526202002050059', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 5, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (60, '李小爱', 'lǐ xiǎo ài ', 60, 29, 44, 45, 48, 50, '201742060060', '男', '522526202002050060', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (61, '王恩龙', 'wáng ēn lóng ', 61, 1, 18, 19, 24, 26, '201742060061', '女', '522526202002050061', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (62, '朱政廷', 'zhū zhèng tíng ', 62, 1, 18, 19, 24, 25, '201742060062', '男', '522526202002050062', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (63, '邓诗涵', 'dèng shī hán ', 63, 1, 18, 19, 24, 28, '201742060063', '男', '522526202002050063', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 16, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (64, '陈政倩', 'chén zhèng qiàn ', 64, 29, 30, 31, 32, 35, '201742060064', '女', '522526202002050064', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 40, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (66, '阮馨学', 'ruǎn xīn xué ', 66, 1, 2, 3, 8, 11, '201742060066', '男', '522526202002050066', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 45, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (67, '翁惠珠', 'wēng huì zhū ', 67, 29, 30, 40, 41, 43, '201742060067', '女', '522526202002050067', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (68, '吴思翰', 'wú sī hàn ', 68, 29, 44, 45, 46, 47, '201742060068', '男', '522526202002050068', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (70, '邓海来', 'dèng hǎi lái ', 70, 1, 2, 3, 4, 5, '201742060070', '女', '522526202002050070', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (69, '林佩玲', 'lín pèi líng ', 69, 1, 2, 3, 13, 24, '201742060069', '男', '522526202002050069', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (72, '李建智', 'lǐ jiàn zhì ', 72, 1, 18, 19, 20, 22, '201742060072', '男', '522526202002050072', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (74, '金雅琪', 'jīn yǎ qí ', 74, 1, 18, 19, 24, 28, '201742060074', '男', '522526202002050074', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 62, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (75, '赖怡宜', 'lài yí yí ', 75, 29, 30, 40, 41, 43, '201742060075', '男', '522526202002050075', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 17, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (76, '黄育霖', 'huáng yù lín ', 76, 1, 2, 3, 4, 6, '201742060076', '女', '522526202002050076', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 43, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (77, '张仪湖', 'zhāng yí hú ', 77, 1, 18, 19, 24, 26, '201742060077', '男', '522526202002050077', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (78, '王俊民', 'wáng jun4 mín ', 78, 1, 18, 19, 24, 27, '201742060078', '男', '522526202002050078', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (79, '张诗刚', 'zhāng shī gāng ', 79, 29, 30, 40, 41, 43, '201742060079', '女', '522526202002050079', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 68, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (80, '林慧颖', 'lín huì yǐng ', 80, 1, 2, 3, 13, 17, '201742060080', '男', '522526202002050080', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 55, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (81, '沈俊君', 'shěn jun4 jun1 ', 81, 1, 2, 3, 4, 7, '201742060081', '男', '522526202002050081', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (82, '陈淑妤', 'chén shū yú ', 82, 1, 2, 3, 8, 11, '201742060082', '女', '522526202002050082', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 95, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (83, '李姿伶', 'lǐ zī líng ', 83, 1, 2, 3, 13, 16, '201742060083', '男', '522526202002050083', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 91, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (84, '高咏钰', 'gāo yǒng yù ', 84, 29, 30, 31, 32, 36, '201742060084', '男', '522526202002050084', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 3, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (85, '黄彦宜', 'huáng yàn yí ', 85, 29, 30, 40, 41, 42, '201742060085', '女', '522526202002050085', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (86, '周孟儒', 'zhōu mèng rú ', 86, 1, 2, 3, 4, 5, '201742060086', '男', '522526202002050086', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 78, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (87, '潘欣臻', 'pān xīn zhēn ', 87, 1, 18, 19, 20, 21, '201742060087', '男', '522526202002050087', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 8, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (88, '李祯韵', 'lǐ zhēn yùn ', 88, 29, 30, 31, 37, 38, '201742060088', '女', '522526202002050088', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (90, '梁哲宇', 'liáng zhé yǔ ', 90, 1, 2, 3, 13, 15, '201742060090', '男', '522526202002050090', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (93, '卢志铭', 'lú zhì míng ', 93, 29, 44, 45, 46, 47, '201742060093', '男', '522526202002050093', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 47, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (94, '张茂以', 'zhāng mào yǐ ', 94, 29, 30, 40, 41, 43, '201742060094', '女', '522526202002050094', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 93, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (96, '蔡宜芸', 'cài yí yún ', 96, 1, 2, 3, 4, 7, '201742060096', '男', '522526202002050096', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (97, '林珮瑜', 'lín pèi yú ', 97, 1, 2, 3, 8, 12, '201742060097', '女', '522526202002050097', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (98, '黄柏仪', 'huáng bǎi yí ', 98, 29, 30, 31, 32, 34, '201742060098', '男', '522526202002050098', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 80, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (73, '武淑', 'wǔ shū fēn ', 73, 1, 18, 19, 20, 21, '201742060073', '女', '522526202002050073', '2020-02-15', '2020-02-15', NULL, '2021-06-30', '无', '无', '我是一个学生', 11, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (99, '周逸珮', 'zhōu yì pèi ', 99, 29, 30, 31, 32, 35, '201742060099', '男', '522526202002050099', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 36, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (100, '夏雅惠', 'xià yǎ huì ', 100, 1, 18, 19, 20, 21, '201742060100', '女', '522526202002050100', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 76, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (101, '王采珮', 'wáng cǎi pèi ', 101, 1, 2, 3, 13, 15, '201742060101', '男', '522526202002050101', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (102, '林孟霖', 'lín mèng lín ', 102, 29, 44, 45, 46, 47, '201742060102', '男', '522526202002050102', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (103, '林竹水', 'lín zhú shuǐ ', 103, 29, 44, 45, 46, 47, '201742060103', '女', '522526202002050103', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 9, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (104, '王怡乐', 'wáng yí lè ', 104, 1, 2, 3, 4, 6, '201742060104', '男', '522526202002050104', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 70, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (105, '王爱乐', 'wáng ài lè ', 105, 29, 30, 31, 32, 34, '201742060105', '男', '522526202002050105', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 14, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (124, '查瑜舜', 'chá yú shùn ', 124, 29, 30, 31, 32, 36, '201742060124', '女', '522526202002050124', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (125, '黎慧萱', 'lí huì xuān ', 125, 29, 30, 31, 32, 35, '201742060125', '男', '522526202002050125', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (126, '郑士易', 'zhèng shì yì ', 126, 1, 2, 3, 13, 15, '201742060126', '男', '522526202002050126', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 2, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (127, '陈建豪', 'chén jiàn háo ', 127, 29, 30, 31, 32, 33, '201742060127', '女', '522526202002050127', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 83, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (129, '徐紫富', 'xú zǐ fù ', 129, 1, 18, 19, 20, 22, '201742060129', '男', '522526202002050129', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 39, 'yms', '2020-02-04 17:53:49', 'yms', '2020-02-04 17:53:49', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (130, '张博海', 'zhāng bó hǎi ', 130, 29, 44, 45, 48, 50, '201742060130', '女', '522526202002050130', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 28, 'yms', '2020-02-04 17:53:49', 'yms', '2020-02-04 17:53:49', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (131, '黎宏儒', 'lí hóng rú ', 131, 1, 2, 3, 13, 14, '201742060131', '男', '522526202002050131', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 62, 'yms', '2020-02-04 17:53:49', 'yms', '2020-02-04 17:53:49', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (15, '洪振霞', 'hóng zhèn xiá ', 15, 29, 30, 31, 32, 35, '201742060015', '男', '522526202002050015', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (128, '吴怡婷', 'wú yí tíng ', 128, 29, 44, 45, 48, 50, '201742060128', '男', '522526202002050128', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (91, '黄晓萍', 'huáng xiǎo píng ', 91, 29, 30, 40, 41, 43, '201742060091', '女', '522526202002050091', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 36, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (95, '林婉婷', 'lín wǎn tíng ', 95, 1, 2, 3, 8, 11, '201742060095', '男', '522526202002050095', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 96, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (47, '舒绿珮', 'shū lǜ pèi ', 56, 29, 44, 45, 48, 49, '201742060047', '男', '522526202002050047', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 76, 'yms', '2020-02-04 17:53:47', 'yms', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (89, '叶洁启', 'yè jié qǐ ', 54, 29, 44, 45, 48, 49, '201742060089', '男', '522526202002050089', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (92, '杨雅萍', 'yáng yǎ píng ', 92, 1, 2, 3, 13, 24, '201742060092', '男', '522526202002050092', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:48', 'yms', '2020-02-04 17:53:48', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (10, 'test', 'jí rú dìng ', 10, 29, 44, 3, 13, 20, '201742060010', '女', '522526202002050010', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 53, 'yms', '2020-02-04 17:53:46', 'yms', '2020-02-04 17:53:46', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (71, '陈翊依', 'chén yì yī ', 71, 1, 2, 3, 13, 20, '201742060071', '男', '522526202002050071', '2020-02-05', '2017-09-01', NULL, '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:47', 'admin', '2020-02-04 17:53:47', '这是一个学生', true, 57, 4);
INSERT INTO public.student VALUES (452, '黄柏仪', 'huáng bǎi yí ', 459, 1, 18, 19, 24, 25, '323232323', '女', '132312312312312312', '2020-02-23', '2020-02-23', 9, NULL, NULL, NULL, NULL, 1, 'admin', '2020-02-23 15:58:34', NULL, '2020-02-23 07:58:35', NULL, true, 57, 4);
INSERT INTO public.student VALUES (411, '柯乔喜', 'kē qiáo xǐ ', 403, 1, 2, NULL, 4, NULL, '201742060120', '女', '520123199808110044', '1998-08-11', '2017-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (413, '蔡宜芸', 'cài yí yún ', 405, 1, 2, NULL, 4, NULL, '201742060122', '女', '520123199808110046', '1998-08-11', '2017-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (400, '林竹水', 'lín zhú shuǐ ', 392, 1, 2, NULL, 4, NULL, '201742060109', '男', '520123199508210034', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (401, '王怡乐', 'wáng yí lè ', 393, 1, 2, NULL, 4, NULL, '201742060110', '女', '520123199808110035', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (406, '陈建豪', 'chén jiàn háo ', 398, 1, 2, NULL, 4, NULL, '201742060115', '男', '520123199508210040', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (408, '徐紫富', 'xú zǐ fù ', 400, 1, 2, NULL, 4, NULL, '201742060117', '男', '520123199508210041', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (410, '黎宏儒', 'lí hóng rú ', 402, 1, 2, NULL, 4, NULL, '201742060119', '男', '520123199508210043', '1995-08-21', '2017-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (414, '林珮瑜', 'lín pèi yú ', 406, 1, 2, NULL, 4, NULL, '201742060123', '男', '520123199508210047', '1995-08-21', '2017-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (398, '王采珮', 'wáng cǎi pèi ', 390, 1, 2, NULL, 4, NULL, '201742060107', '男', '520123199508210031', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (399, '林孟霖', 'lín mèng lín ', 391, 1, 2, NULL, 4, NULL, '201742060108', '女', '520123199808110033', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (402, '王爱乐', 'wáng ài lè ', 394, 1, 2, NULL, 4, NULL, '201742060111', '男', '520123199508210036', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (403, '查瑜舜', 'chá yú shùn ', 395, 1, 2, NULL, 4, NULL, '201742060112', '女', '520123199808110037', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (404, '黎慧萱', 'lí huì xuān ', 396, 1, 2, NULL, 4, NULL, '201742060113', '男', '520123199508210038', '1995-08-21', '2016-09-01', 52, '2019-06-30', '数学与应用数学', NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 57, 4);
INSERT INTO public.student VALUES (405, '郑士易', 'zhèng shì yì ', 397, 1, 2, NULL, 4, NULL, '201742060114', '女', '520123199808110039', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (407, '吴怡婷', 'wú yí tíng ', 399, 1, 2, NULL, 4, NULL, '201742060116', '女', '520123199808110011', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);
INSERT INTO public.student VALUES (409, '张博海', 'zhāng bó hǎi ', 401, 1, 2, NULL, 4, NULL, '201742060118', '女', '520123199808110042', '1998-08-11', '2016-09-01', 51, '2017-06-30', NULL, NULL, NULL, 1, 'admin', '2020-02-22 19:00:46', NULL, '2020-02-22 11:00:46', NULL, true, 62, 4);


--
-- TOC entry 3268 (class 0 OID 16492)
-- Dependencies: 215
-- Data for Name: sys_data; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_data VALUES (28, '特殊人才班', 'tè shū rén cái bān ', 24, NULL, 4, 89, 'yms', '2020-02-04 17:11:11', 'yms', '2020-02-04 17:11:11', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (4, '软件工程专业', 'ruǎn jiàn gōng chéng zhuān yè ', 3, NULL, 3, 43, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (5, '火箭班', 'huǒ jiàn bān ', 4, NULL, 4, 80, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (6, '普通班', 'pǔ tōng bān ', 4, NULL, 4, 51, 'yms', '2019-08-07 16:40:58', 'yms', '2019-08-12 00:27:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (7, '放养班', 'fàng yǎng bān ', 4, NULL, 4, 8, 'yms', '2019-08-07 16:41:11', 'yms', '2019-08-12 00:27:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (8, '计算机科学专业', 'jì suàn jī kē xué zhuān yè ', 3, NULL, 3, 61, 'yms', '2020-02-04 16:40:09', 'yms', '2020-02-04 16:40:09', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (10, '火箭班', 'huǒ jiàn bān ', 8, NULL, 4, 40, 'yms', '2020-02-04 16:41:13', 'yms', '2020-02-04 16:41:13', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (11, '普通班', 'pǔ tōng bān ', 8, NULL, 4, 33, 'yms', '2020-02-04 16:41:17', 'yms', '2020-02-04 16:41:17', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (12, '放养班', 'fàng yǎng bān ', 8, NULL, 4, 30, 'yms', '2020-02-04 16:41:19', 'yms', '2020-02-04 16:41:19', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (13, '物联网专业', 'wù lián wǎng zhuān yè ', 3, NULL, 3, 55, 'yms', '2020-02-04 16:42:26', 'yms', '2020-02-04 16:42:26', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (14, '火箭班', 'huǒ jiàn bān ', 13, NULL, 4, 64, 'yms', '2020-02-04 16:43:04', 'yms', '2020-02-04 16:43:04', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (15, '普通班', 'pǔ tōng bān ', 13, NULL, 4, 16, 'yms', '2020-02-04 16:43:06', 'yms', '2020-02-04 16:43:06', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (16, '放养班', 'fàng yǎng bān ', 13, NULL, 4, 40, 'yms', '2020-02-04 16:43:10', 'yms', '2020-02-04 16:43:10', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (17, '扩招班', 'kuò zhāo bān ', 13, NULL, 4, 85, 'yms', '2020-02-04 16:43:25', 'yms', '2020-02-04 16:43:25', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (18, '认知科学学院', 'rèn zhī kē xué xué yuàn ', 1, NULL, 1, 29, 'yms', '2020-02-04 16:43:31', 'yms', '2020-02-04 16:43:31', '这是一个学院', true);
INSERT INTO public.sys_data VALUES (19, '认知科学系', 'rèn zhī kē xué xì ', 18, NULL, 2, 50, 'yms', '2020-02-04 16:43:32', 'yms', '2020-02-04 16:43:32', '这是一个系部', true);
INSERT INTO public.sys_data VALUES (20, '认知神经科学专业', 'rèn zhī shén jīng kē xué zhuān yè ', 19, NULL, 3, 85, 'yms', '2020-02-04 16:43:35', 'yms', '2020-02-04 16:43:35', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (21, '火箭班', 'huǒ jiàn bān ', 20, NULL, 4, 1, 'yms', '2020-02-04 16:45:08', 'yms', '2020-02-04 16:45:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (22, '普通班', 'pǔ tōng bān ', 20, NULL, 4, 82, 'yms', '2020-02-04 16:45:11', 'yms', '2020-02-04 16:45:11', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (23, '放养班', 'fàng yǎng bān ', 20, NULL, 4, 83, 'yms', '2020-02-04 16:45:13', 'yms', '2020-02-04 16:45:13', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (24, '应用心理学专业', 'yīng yòng xīn lǐ xué zhuān yè ', 19, NULL, 3, 0, 'yms', '2020-02-04 16:45:14', 'yms', '2020-02-04 16:45:14', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (25, '火箭班', 'huǒ jiàn bān ', 24, NULL, 4, 6, 'yms', '2020-02-04 17:11:06', 'yms', '2020-02-04 17:11:06', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (26, '普通班', 'pǔ tōng bān ', 24, NULL, 4, 24, 'yms', '2020-02-04 17:11:08', 'yms', '2020-02-04 17:11:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (27, '放养班', 'fàng yǎng bān ', 24, NULL, 4, 96, 'yms', '2020-02-04 17:11:10', 'yms', '2020-02-04 17:11:10', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (2, '数据科学与信息工程学院', 'shù jù kē xué yǔ xìn xī gōng chéng xué yuàn ', 1, NULL, 1, 77, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个学院', true);
INSERT INTO public.sys_data VALUES (3, '数信系', 'shù xìn xì ', 2, NULL, 2, 64, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个系部', true);
INSERT INTO public.sys_data VALUES (35, '塑料班', 'sù liào bān ', 32, NULL, 4, 80, 'yms', '2020-02-04 17:22:01', 'yms', '2020-02-04 17:22:01', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (36, '老年人班', 'lǎo nián rén bān ', 32, NULL, 4, 45, 'yms', '2020-02-04 17:22:30', 'yms', '2020-02-04 17:22:30', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (38, '大神班', 'dà shén bān ', 37, NULL, 4, 30, 'yms', '2020-02-04 17:23:50', 'yms', '2020-02-04 17:23:50', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (39, '萌新班', 'méng xīn bān ', 37, NULL, 4, 44, 'yms', '2020-02-04 17:24:01', 'yms', '2020-02-04 17:24:01', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (31, 'Game系', 'Gamexì ', 30, NULL, 2, 63, 'yms', '2020-02-04 17:18:40', 'yms', '2020-02-04 17:18:40', '这是一个系部', true);
INSERT INTO public.sys_data VALUES (32, 'LOL专业', 'LOLzhuān yè ', 31, NULL, 3, 55, 'yms', '2020-02-04 17:19:57', 'yms', '2020-02-04 17:19:57', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (33, '王者班', 'wáng zhě bān ', 32, NULL, 4, 36, 'yms', '2020-02-04 17:21:04', 'yms', '2020-02-04 17:21:04', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (42, '借电脑班', 'jiè diàn nǎo bān ', 41, NULL, 4, 80, 'yms', '2020-02-04 17:26:06', 'yms', '2020-02-04 17:26:06', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (43, '自带电脑班', 'zì dài diàn nǎo bān ', 41, NULL, 4, 56, 'yms', '2020-02-04 17:26:16', 'yms', '2020-02-04 17:26:16', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (47, '猫头鹰班', 'māo tóu yīng bān ', 46, NULL, 4, 66, 'yms', '2020-02-04 17:29:08', 'yms', '2020-02-04 17:29:08', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (49, '不想出门班', 'bú xiǎng chū mén bān ', 48, NULL, 4, 5, 'yms', '2020-02-04 17:30:24', 'yms', '2020-02-04 17:30:24', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (50, '懒得聊天班', 'lǎn dé liáo tiān bān ', 48, NULL, 4, 43, 'yms', '2020-02-04 17:30:38', 'yms', '2020-02-04 17:30:38', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (44, '肥宅学院', 'féi zhái xué yuàn ', 29, NULL, 1, 70, 'yms', '2020-02-04 17:26:56', 'yms', '2020-02-04 17:26:56', '这是一个学院', true);
INSERT INTO public.sys_data VALUES (45, '人才系', 'rén cái xì ', 44, NULL, 2, 84, 'yms', '2020-02-04 17:27:38', 'yms', '2020-02-04 17:27:38', '这是一个系部', true);
INSERT INTO public.sys_data VALUES (40, 'Study系', 'Studyxì ', 30, NULL, 2, 24, 'yms', '2020-02-04 17:24:28', 'yms', '2020-02-04 17:24:28', '这是一个系部', true);
INSERT INTO public.sys_data VALUES (37, 'DOTA专业', 'DOTAzhuān yè ', 31, NULL, 3, 68, 'yms', '2020-02-04 17:23:14', 'yms', '2020-02-04 17:23:14', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (41, '3D建模专业', '3Djiàn mó zhuān yè ', 40, NULL, 3, 40, 'yms', '2020-02-04 17:25:20', 'yms', '2020-02-04 17:25:20', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (46, '夜猫子专业', 'yè māo zǐ zhuān yè ', 45, NULL, 3, 58, 'yms', '2020-02-04 17:28:48', 'yms', '2020-02-04 17:28:48', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (48, '自闭专业', 'zì bì zhuān yè ', 45, NULL, 3, 85, 'yms', '2020-02-04 17:30:08', 'yms', '2020-02-04 17:30:08', '这是一个专业', true);
INSERT INTO public.sys_data VALUES (34, '黄金班', 'huáng jīn bān ', 32, NULL, 4, 64, 'yms', '2020-02-04 17:21:26', 'yms', '2020-02-04 17:21:26', '这是一个班级', true);
INSERT INTO public.sys_data VALUES (9, '初中', NULL, 0, NULL, 6, 1, NULL, '2020-02-08 04:54:29', NULL, '2020-02-08 04:54:29', NULL, true);
INSERT INTO public.sys_data VALUES (51, '高中', NULL, 0, NULL, 6, 2, NULL, '2020-02-08 04:55:38', NULL, '2020-02-08 04:55:38', NULL, true);
INSERT INTO public.sys_data VALUES (53, '专科', NULL, 0, NULL, 6, 3, NULL, '2020-02-08 04:58:42', NULL, '2020-02-08 04:58:42', NULL, true);
INSERT INTO public.sys_data VALUES (52, '本科', NULL, 0, NULL, 6, 4, NULL, '2020-02-08 04:58:16', NULL, '2020-02-08 04:58:16', NULL, true);
INSERT INTO public.sys_data VALUES (54, '硕士', NULL, 0, NULL, 6, 5, NULL, '2020-02-08 04:59:15', NULL, '2020-02-08 04:59:15', NULL, true);
INSERT INTO public.sys_data VALUES (55, '博士', NULL, 0, NULL, 6, 6, NULL, '2020-02-08 04:59:35', NULL, '2020-02-08 04:59:35', NULL, true);
INSERT INTO public.sys_data VALUES (56, '其他', NULL, 0, NULL, 6, 7, NULL, '2020-02-08 04:59:46', NULL, '2020-02-08 04:59:46', NULL, true);
INSERT INTO public.sys_data VALUES (30, 'PC学院', 'PCxué yuàn ', 29, '123123', 1, 37, 'yms', '2020-02-04 17:17:26', 'admin', '2020-02-04 17:17:26', '这是一个学院', true);
INSERT INTO public.sys_data VALUES (58, '蒙古族', NULL, 0, NULL, 9, 2, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (29, '加利敦大学', 'jiā lì dūn dà xué ', 0, NULL, 0, 2, 'yms', '2020-02-04 17:16:14', 'yms', '2020-02-04 17:16:14', '这是一个大学', true);
INSERT INTO public.sys_data VALUES (59, '回族', NULL, 0, NULL, 9, 3, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (60, '藏族', NULL, 0, NULL, 9, 4, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (61, '维吾尔族', NULL, 0, NULL, 9, 5, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (62, '苗族', NULL, 0, NULL, 9, 6, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (63, '彝族', NULL, 0, NULL, 9, 7, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (64, '壮族', NULL, 0, NULL, 9, 8, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (65, '布依族', NULL, 0, NULL, 9, 9, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (66, '朝鲜族', NULL, 0, NULL, 9, 10, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (67, '满族', NULL, 0, NULL, 9, 11, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (68, '侗族', NULL, 0, NULL, 9, 12, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (69, '瑶族', NULL, 0, NULL, 9, 13, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (70, '白族', NULL, 0, NULL, 9, 14, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (71, '土家族', NULL, 0, NULL, 9, 15, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (72, '哈尼族', NULL, 0, NULL, 9, 16, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (73, '哈萨克族', NULL, 0, NULL, 9, 17, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (74, '傣族', NULL, 0, NULL, 9, 18, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (75, '黎族', NULL, 0, NULL, 9, 19, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (76, '傈僳族', NULL, 0, NULL, 9, 20, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (77, '佤族', NULL, 0, NULL, 9, 21, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (78, '畲族', NULL, 0, NULL, 9, 22, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (79, '高山族', NULL, 0, NULL, 9, 23, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (80, '拉祜族', NULL, 0, NULL, 9, 24, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (81, '水族', NULL, 0, NULL, 9, 25, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (82, '东乡族', NULL, 0, NULL, 9, 26, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (83, '纳西族', NULL, 0, NULL, 9, 27, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (84, '景颇族', NULL, 0, NULL, 9, 28, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (85, '柯尔克孜族', NULL, 0, NULL, 9, 29, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (86, '土族', NULL, 0, NULL, 9, 30, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (87, '达斡尔族', NULL, 0, NULL, 9, 31, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (88, '仫佬族', NULL, 0, NULL, 9, 32, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (89, '羌族', NULL, 0, NULL, 9, 33, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (90, '布朗族', NULL, 0, NULL, 9, 34, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (91, '撒拉族', NULL, 0, NULL, 9, 35, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (92, '毛南族', NULL, 0, NULL, 9, 36, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (93, '仡佬族', NULL, 0, NULL, 9, 37, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (94, '锡伯族', NULL, 0, NULL, 9, 38, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (95, '阿昌族', NULL, 0, NULL, 9, 39, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (96, '普米族', NULL, 0, NULL, 9, 40, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (97, '塔吉克族', NULL, 0, NULL, 9, 41, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (98, '怒族', NULL, 0, NULL, 9, 42, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (99, '乌孜别克族', NULL, 0, NULL, 9, 43, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (100, '俄罗斯族', NULL, 0, NULL, 9, 44, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (101, '鄂温克族', NULL, 0, NULL, 9, 45, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (102, '德昂族', NULL, 0, NULL, 9, 46, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (103, '保安族', NULL, 0, NULL, 9, 47, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (104, '裕固族', NULL, 0, NULL, 9, 48, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (105, '京族', NULL, 0, NULL, 9, 49, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (106, '塔塔尔族', NULL, 0, NULL, 9, 50, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (107, '独龙族', NULL, 0, NULL, 9, 51, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (108, '鄂伦春族', NULL, 0, NULL, 9, 52, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (109, '赫哲族', NULL, 0, NULL, 9, 53, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (110, '门巴族', NULL, 0, NULL, 9, 54, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (111, '珞巴族', NULL, 0, NULL, 9, 55, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (112, '基诺族', NULL, 0, NULL, 9, 56, NULL, '2020-02-09 08:15:38', NULL, '2020-02-09 08:15:38', NULL, true);
INSERT INTO public.sys_data VALUES (57, '汉族', NULL, 0, NULL, 9, 1, NULL, '2020-02-09 07:28:11', NULL, '2020-02-09 07:28:11', NULL, true);
INSERT INTO public.sys_data VALUES (113, '其他', NULL, 0, NULL, 9, 57, NULL, '2020-02-09 08:17:10', NULL, '2020-02-09 08:17:10', NULL, true);
INSERT INTO public.sys_data VALUES (114, '学士学位', NULL, 0, NULL, 7, 1, NULL, '2020-02-09 10:54:36', NULL, '2020-02-09 10:54:36', NULL, true);
INSERT INTO public.sys_data VALUES (115, '硕士学位', NULL, 0, NULL, 7, 2, NULL, '2020-02-09 10:54:36', NULL, '2020-02-09 10:54:36', NULL, true);
INSERT INTO public.sys_data VALUES (116, '博士学位', NULL, 0, NULL, 7, 3, NULL, '2020-02-09 10:54:36', NULL, '2020-02-09 10:54:36', NULL, true);
INSERT INTO public.sys_data VALUES (117, '其他', NULL, 0, NULL, 7, 4, NULL, '2020-02-09 10:59:18', NULL, '2020-02-09 10:59:18', NULL, true);
INSERT INTO public.sys_data VALUES (118, 'a', NULL, 0, 'a', 1, 1, 'admin', '2020-02-13 13:35:17', NULL, '2020-02-13 05:35:17', '1', true);
INSERT INTO public.sys_data VALUES (120, 'asd', NULL, 119, 'asd', 2, 12, 'admin', '2020-02-13 13:40:05', NULL, '2020-02-13 05:40:05', 'asd', true);
INSERT INTO public.sys_data VALUES (119, 'asdaa', NULL, 29, 'asd', 1, 6, 'admin', '2020-02-13 13:39:41', 'admin', '2020-02-13 05:39:41', NULL, true);
INSERT INTO public.sys_data VALUES (121, 'as25', NULL, 29, 'asd', 1, 1, 'admin', '2020-02-13 13:42:50', 'admin', '2020-02-13 05:42:50', NULL, true);
INSERT INTO public.sys_data VALUES (122, '助教', NULL, 0, NULL, 11, 1, NULL, '2020-02-13 08:30:01', NULL, '2020-02-13 08:30:01', NULL, true);
INSERT INTO public.sys_data VALUES (123, '讲师', NULL, 0, NULL, 11, 1, NULL, '2020-02-13 08:30:01', NULL, '2020-02-13 08:30:01', NULL, true);
INSERT INTO public.sys_data VALUES (124, '副教授', NULL, 0, NULL, 11, 1, NULL, '2020-02-13 08:30:01', NULL, '2020-02-13 08:30:01', NULL, true);
INSERT INTO public.sys_data VALUES (125, '教授', NULL, 0, NULL, 11, 1, NULL, '2020-02-13 08:30:01', NULL, '2020-02-13 08:30:01', NULL, true);
INSERT INTO public.sys_data VALUES (126, 'test', NULL, 0, 'test', 6, 34, 'admin', '2020-02-14 00:52:31', NULL, '2020-02-13 16:52:31', NULL, false);
INSERT INTO public.sys_data VALUES (1, '贵州民族大学', 'guì zhōu mín zú dà xué ', 0, NULL, 0, 1, 'yms', '2019-08-07 15:58:37', 'yms', '2019-08-07 15:58:37', '这是一个大学', true);


--
-- TOC entry 3264 (class 0 OID 16451)
-- Dependencies: 211
-- Data for Name: semester; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.semester VALUES (4, '2018-2019下学期', '2019xiajixiaoxueqi', 1, '2019-07-13', '2019-08-16', 52, 'yms', '2019-08-07 16:47:36', 'yms', '2019-08-07 16:47:36', '这是一个小学期', true);
INSERT INTO public.semester VALUES (8, '2018夏季小学期', '2020-2021shangxueqi', 1, '2020-09-01', '2021-01-03', 59, 'yms', '2020-02-05 00:08:20', 'yms', '2020-02-04 16:08:35', '这是一个学期', true);
INSERT INTO public.semester VALUES (2, '2020-2021上学期', '2018-2019shangxueqi', 1, '2018-09-01', '2019-01-04', 12, 'yms', '2020-02-05 00:08:20', 'yms', '2020-02-04 16:08:35', '这是一个学期', true);
INSERT INTO public.semester VALUES (5, '2018-2019上学期', '2019-2020shangxueqi', 1, '2019-09-01', '2020-01-04', 64, 'yms', '2020-02-05 00:08:20', 'yms', '2020-02-04 16:08:35', '这是一个学期', true);
INSERT INTO public.semester VALUES (1, '2019-2020下学期', '2018xiajixiaoxueqi', 1, '2018-07-13', '2019-08-16', 30, 'yms', '2019-08-07 16:03:26', 'yms', '2019-08-07 16:47:36', '这是一个小学期', true);
INSERT INTO public.semester VALUES (6, '2019-2020上学期', '2019-2020xiaxueqi', 1, '2020-03-01', '2019-07-08', 79, 'yms', '2020-02-05 00:08:20', 'yms', '2020-02-04 16:08:35', '这是一个学期', true);
INSERT INTO public.semester VALUES (3, '2020夏季小学期', '2018-2019xiaxueqi', 1, '2019-03-01', '2019-07-06', 15, 'yms', '2020-02-05 00:08:20', 'yms', '2020-02-04 16:08:35', '这是一个学期', true);
INSERT INTO public.semester VALUES (7, '2019夏季小学期', '2020xiajixiaoxueqi', 1, '2020-07-13', '2020-08-14', 90, 'yms', '2019-08-07 16:47:37', 'yms', '2019-08-07 16:47:37', '这是一个小学期', true);


--
-- TOC entry 3270 (class 0 OID 16512)
-- Dependencies: 217
-- Data for Name: sys_res; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_res VALUES (3, 'Oauth2-提交', NULL, '/authorization/form', 'oauth2 授权码模式表单提交路径', 'POST', 1, NULL, '2019-08-06 06:23:57', NULL, '2019-08-06 06:23:57', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (2, 'Oauth2', NULL, '/oauth/**', 'oauth2 授权相关', 'ALL', 1, NULL, '2019-08-06 06:11:23', NULL, '2019-08-06 06:11:23', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (1, 'home', NULL, '/', 'api 主界面', 'ALL', 1, NULL, '2019-08-06 06:55:21', NULL, '2019-08-06 06:55:21', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (7, '学生信息', NULL, '/student*/**', '学生信息', 'GET', 1, NULL, '2020-01-02 09:54:26', NULL, '2020-01-02 09:54:26', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (6, '密码接口', NULL, '/encrypt', '加密密码', 'GET', 1, NULL, '2020-01-01 13:49:00', NULL, '2020-01-01 13:49:00', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (5, 'JWK', NULL, '/.well-known/jwks.json', 'JWK', 'GET', 1, NULL, '2020-01-01 13:48:51', NULL, '2020-01-01 13:48:51', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (4, '应用健康信息', NULL, '/actuator*/**', '应用健康信息', 'ALL', 1, NULL, '2020-01-01 13:48:34', NULL, '2020-01-01 13:48:34', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (10, '获取验证码', NULL, '/code/*', NULL, 'GET', 1, NULL, '2020-02-27 16:40:06', NULL, '2020-02-27 16:40:06', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (9, '获取所有学期信息', NULL, '/api/semester/search/all', NULL, 'GET', 1, NULL, '2020-01-25 11:27:07', NULL, '2020-01-25 11:27:07', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (8, '当前用户信息', NULL, '/auth/me', '当前用户信息', 'GET', 1, NULL, '2020-01-16 13:33:41', NULL, '2020-01-16 13:33:41', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (24, '获取指定部门的所有教师信息', NULL, '/api/teacher/search/byDepId', '获取指定部门的所有教师信息', 'GET', 1, NULL, '2020-04-13 16:32:57', NULL, '2020-04-13 16:32:57', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (25, '通过父id和类型查询子集', NULL, '/api/sysData/search/byParentIdAndType', '通过父id和类型查询子集', 'GET', 1, NULL, '2020-04-14 14:40:05', NULL, '2020-04-14 14:40:05', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (26, '通过用户 id 查询学生', NULL, '/api/student/search/byUserId', '通过用户 id 查询学生', 'GET', 1, NULL, '2020-04-17 06:44:13', NULL, '2020-04-17 06:44:13', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (28, '通过用户 id 查询教师', NULL, '/api/teacher/search/byUserId', '通过用户 id 查询教师', 'GET', 1, NULL, '2020-04-17 06:45:07', NULL, '2020-04-17 06:45:07', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (29, '查询在指定 user ids 的教师信息', NULL, '/api/teacher/search/byUserIds', '查询在指定 user ids 的教师信息', 'GET', 1, NULL, '2020-04-17 06:45:28', NULL, '2020-04-17 06:45:28', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (27, '查询指定 user ids 的学生信息', NULL, '/api/student/search/byUserIds', '查询指定 user ids 的学生信息', 'GET', 1, NULL, '2020-04-17 06:44:32', NULL, '2020-04-17 06:44:32', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (30, '获取用户的实体信息', NULL, '/sysUser/id/*', '获取用户的实体信息', 'GET', 1, NULL, '2020-04-17 06:58:55', NULL, '2020-04-17 06:58:55', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (31, '获取教师的基本信息', NULL, '/teacher/id/*', '获取教师的基本信息', 'GET', 1, NULL, '2020-04-27 15:17:13', NULL, '2020-04-27 15:17:13', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (32, '获取教师的基本信息', NULL, '/teacher/ids', '获取教师的基本信息', 'GET', 1, NULL, '2020-04-27 15:17:27', NULL, '2020-04-27 15:17:27', NULL, true, 'READ');
INSERT INTO public.sys_res VALUES (33, '获取用户基本信息', NULL, '/sysUser/info', '获取用户基本信息', 'GET', 1, NULL, '2020-05-06 07:27:59', NULL, '2020-05-06 07:27:59', NULL, true, 'READ');


--
-- TOC entry 3274 (class 0 OID 16554)
-- Dependencies: 221
-- Data for Name: sys_role_res; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_role_res VALUES (3, NULL, NULL, 2, 3, NULL, NULL, '2020-01-01 14:01:24', NULL, '2020-01-01 14:01:24', NULL, true);
INSERT INTO public.sys_role_res VALUES (2, NULL, NULL, 2, 2, NULL, NULL, '2020-01-01 14:01:15', NULL, '2020-01-01 14:01:15', NULL, true);
INSERT INTO public.sys_role_res VALUES (5, NULL, NULL, 2, 5, NULL, NULL, '2020-01-01 14:01:36', NULL, '2020-01-01 14:01:36', NULL, true);
INSERT INTO public.sys_role_res VALUES (4, NULL, NULL, 2, 4, NULL, NULL, '2020-01-01 14:01:32', NULL, '2020-01-01 14:01:32', NULL, true);
INSERT INTO public.sys_role_res VALUES (1, NULL, NULL, 2, 1, NULL, NULL, '2020-01-01 14:01:09', NULL, '2020-01-01 14:01:09', NULL, true);
INSERT INTO public.sys_role_res VALUES (6, NULL, NULL, 2, 6, NULL, NULL, '2020-01-01 14:01:51', NULL, '2020-01-01 14:01:51', NULL, true);
INSERT INTO public.sys_role_res VALUES (7, NULL, NULL, 2, 7, NULL, NULL, '2020-01-02 09:54:41', NULL, '2020-01-02 09:54:41', NULL, true);
INSERT INTO public.sys_role_res VALUES (8, NULL, NULL, 1, 8, NULL, NULL, '2020-01-16 13:34:10', NULL, '2020-01-16 13:34:10', NULL, true);
INSERT INTO public.sys_role_res VALUES (9, NULL, NULL, 1, 9, NULL, NULL, '2020-01-25 11:28:41', NULL, '2020-01-25 11:28:41', NULL, true);
INSERT INTO public.sys_role_res VALUES (34, NULL, NULL, 2, 10, NULL, NULL, '2020-02-27 16:40:26', NULL, '2020-02-27 16:40:26', NULL, true);
INSERT INTO public.sys_role_res VALUES (36, NULL, NULL, 6, 24, NULL, NULL, '2020-04-13 16:33:33', NULL, '2020-04-13 16:33:33', NULL, true);
INSERT INTO public.sys_role_res VALUES (38, NULL, NULL, 4, 26, NULL, NULL, '2020-04-17 06:49:02', NULL, '2020-04-17 06:49:02', NULL, true);
INSERT INTO public.sys_role_res VALUES (39, NULL, NULL, 4, 27, NULL, NULL, '2020-04-17 06:49:02', NULL, '2020-04-17 06:49:02', NULL, true);
INSERT INTO public.sys_role_res VALUES (40, NULL, NULL, 4, 28, NULL, NULL, '2020-04-17 06:49:02', NULL, '2020-04-17 06:49:02', NULL, true);
INSERT INTO public.sys_role_res VALUES (41, NULL, NULL, 4, 29, NULL, NULL, '2020-04-17 06:49:02', NULL, '2020-04-17 06:49:02', NULL, true);
INSERT INTO public.sys_role_res VALUES (42, NULL, NULL, 4, 30, NULL, NULL, '2020-04-17 06:59:05', NULL, '2020-04-17 06:59:05', NULL, true);
INSERT INTO public.sys_role_res VALUES (37, NULL, NULL, 4, 25, NULL, NULL, '2020-04-14 14:40:17', NULL, '2020-04-14 14:40:17', NULL, true);
INSERT INTO public.sys_role_res VALUES (43, NULL, NULL, 5, 31, NULL, NULL, '2020-04-27 15:18:05', NULL, '2020-04-27 15:18:05', NULL, true);
INSERT INTO public.sys_role_res VALUES (44, NULL, NULL, 5, 32, NULL, NULL, '2020-04-27 15:18:05', NULL, '2020-04-27 15:18:05', NULL, true);
INSERT INTO public.sys_role_res VALUES (45, NULL, NULL, 1, 33, NULL, NULL, '2020-05-06 07:28:43', NULL, '2020-05-06 07:28:43', NULL, true);



--
-- TOC entry 3278 (class 0 OID 16595)
-- Dependencies: 225
-- Data for Name: sys_user_role; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.sys_user_role VALUES (1, NULL, NULL, 1, 3, NULL, NULL, '2020-01-01 13:47:16', NULL, '2020-01-01 13:47:16', NULL, true);
INSERT INTO public.sys_user_role VALUES (4, NULL, NULL, 1, 5, NULL, NULL, '2020-01-02 13:28:14', NULL, '2020-01-02 13:28:14', NULL, true);
INSERT INTO public.sys_user_role VALUES (2, NULL, NULL, 2, 5, NULL, NULL, '2020-01-01 13:47:27', NULL, '2020-01-01 13:47:27', NULL, true);
INSERT INTO public.sys_user_role VALUES (3, NULL, NULL, 3, 4, NULL, NULL, '2020-01-01 13:47:27', NULL, '2020-01-01 13:47:27', NULL, true);
INSERT INTO public.sys_user_role VALUES (9, NULL, NULL, 5, 6, NULL, NULL, '2020-02-15 04:57:49', NULL, '2020-02-15 04:57:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (21, NULL, NULL, 389, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (22, NULL, NULL, 390, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (23, NULL, NULL, 391, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (24, NULL, NULL, 392, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (25, NULL, NULL, 393, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (26, NULL, NULL, 394, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (27, NULL, NULL, 395, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (28, NULL, NULL, 396, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (29, NULL, NULL, 397, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (30, NULL, NULL, 398, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (31, NULL, NULL, 399, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (32, NULL, NULL, 400, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (33, NULL, NULL, 401, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (34, NULL, NULL, 402, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (35, NULL, NULL, 403, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (36, NULL, NULL, 404, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (37, NULL, NULL, 405, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (38, NULL, NULL, 406, 5, NULL, NULL, '2020-02-22 11:00:46', NULL, '2020-02-22 11:00:46', NULL, true);
INSERT INTO public.sys_user_role VALUES (40, NULL, NULL, 389, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (41, NULL, NULL, 390, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (42, NULL, NULL, 391, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (43, NULL, NULL, 392, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (44, NULL, NULL, 393, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (45, NULL, NULL, 394, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (46, NULL, NULL, 395, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (47, NULL, NULL, 396, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (48, NULL, NULL, 397, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (49, NULL, NULL, 398, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (50, NULL, NULL, 399, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (51, NULL, NULL, 400, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (52, NULL, NULL, 401, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (53, NULL, NULL, 402, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (54, NULL, NULL, 403, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (55, NULL, NULL, 404, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (56, NULL, NULL, 405, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (57, NULL, NULL, 406, 5, NULL, NULL, '2020-02-22 11:06:13', NULL, '2020-02-22 11:06:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (58, NULL, NULL, 389, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (59, NULL, NULL, 390, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (60, NULL, NULL, 391, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (61, NULL, NULL, 392, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (62, NULL, NULL, 393, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (63, NULL, NULL, 394, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (64, NULL, NULL, 395, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (65, NULL, NULL, 396, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (66, NULL, NULL, 397, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (67, NULL, NULL, 398, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (68, NULL, NULL, 399, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (69, NULL, NULL, 400, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (70, NULL, NULL, 401, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (71, NULL, NULL, 402, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (72, NULL, NULL, 403, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (73, NULL, NULL, 404, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (74, NULL, NULL, 405, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (75, NULL, NULL, 406, 5, NULL, NULL, '2020-02-22 11:13:23', NULL, '2020-02-22 11:13:23', NULL, true);
INSERT INTO public.sys_user_role VALUES (78, NULL, NULL, 459, 5, NULL, NULL, '2020-02-23 07:58:35', NULL, '2020-02-23 07:58:35', NULL, true);
INSERT INTO public.sys_user_role VALUES (79, NULL, NULL, 461, 4, NULL, NULL, '2020-02-23 08:12:10', NULL, '2020-02-23 08:12:10', NULL, true);
INSERT INTO public.sys_user_role VALUES (80, NULL, NULL, 389, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (81, NULL, NULL, 390, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (82, NULL, NULL, 391, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (83, NULL, NULL, 392, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (84, NULL, NULL, 393, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (85, NULL, NULL, 394, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (86, NULL, NULL, 395, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (87, NULL, NULL, 396, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (88, NULL, NULL, 397, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (89, NULL, NULL, 398, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (90, NULL, NULL, 399, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (91, NULL, NULL, 400, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (92, NULL, NULL, 401, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (93, NULL, NULL, 402, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (94, NULL, NULL, 403, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (95, NULL, NULL, 404, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (96, NULL, NULL, 405, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (97, NULL, NULL, 406, 5, NULL, NULL, '2020-02-23 09:15:49', NULL, '2020-02-23 09:15:49', NULL, true);
INSERT INTO public.sys_user_role VALUES (98, NULL, NULL, 480, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (99, NULL, NULL, 481, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (100, NULL, NULL, 482, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (101, NULL, NULL, 483, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (102, NULL, NULL, 484, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (103, NULL, NULL, 485, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (104, NULL, NULL, 486, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (105, NULL, NULL, 487, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (106, NULL, NULL, 488, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (107, NULL, NULL, 489, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (108, NULL, NULL, 490, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (109, NULL, NULL, 491, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (110, NULL, NULL, 492, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (111, NULL, NULL, 493, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (112, NULL, NULL, 494, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (113, NULL, NULL, 495, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (114, NULL, NULL, 496, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (115, NULL, NULL, 497, 5, NULL, NULL, '2020-02-23 09:17:33', NULL, '2020-02-23 09:17:33', NULL, true);
INSERT INTO public.sys_user_role VALUES (121, NULL, NULL, 504, 4, NULL, NULL, '2020-02-23 10:23:05', NULL, '2020-02-23 10:23:05', NULL, true);
INSERT INTO public.sys_user_role VALUES (122, NULL, NULL, 505, 4, NULL, NULL, '2020-02-23 10:23:05', NULL, '2020-02-23 10:23:05', NULL, true);
INSERT INTO public.sys_user_role VALUES (123, NULL, NULL, 504, 4, NULL, NULL, '2020-02-23 10:34:24', NULL, '2020-02-23 10:34:24', NULL, true);
INSERT INTO public.sys_user_role VALUES (124, NULL, NULL, 505, 4, NULL, NULL, '2020-02-23 10:34:24', NULL, '2020-02-23 10:34:24', NULL, true);
INSERT INTO public.sys_user_role VALUES (125, NULL, NULL, 504, 4, NULL, NULL, '2020-02-23 10:38:13', NULL, '2020-02-23 10:38:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (126, NULL, NULL, 505, 4, NULL, NULL, '2020-02-23 10:38:13', NULL, '2020-02-23 10:38:13', NULL, true);
INSERT INTO public.sys_user_role VALUES (127, NULL, NULL, 504, 4, NULL, NULL, '2020-02-23 10:38:50', NULL, '2020-02-23 10:38:50', NULL, true);
INSERT INTO public.sys_user_role VALUES (128, NULL, NULL, 505, 4, NULL, NULL, '2020-02-23 10:38:50', NULL, '2020-02-23 10:38:50', NULL, true);
INSERT INTO public.sys_user_role VALUES (131, NULL, NULL, 4, 8, NULL, NULL, '2020-02-24 11:25:17', NULL, '2020-02-24 11:25:17', NULL, true);
INSERT INTO public.sys_user_role VALUES (133, NULL, NULL, 514, 3, NULL, NULL, '2020-04-24 07:47:37', NULL, '2020-04-24 07:47:37', NULL, true);


--
-- TOC entry 3279 (class 0 OID 16612)
-- Dependencies: 227
-- Data for Name: teacher; Type: TABLE DATA; Schema: public; Owner: gzmu
--

INSERT INTO public.teacher VALUES (3, '李开富', 'lǐ kāi fù ', 3, 1, 2, 3, '男', '2020-02-05', 57, 115, 9, '2020-06-01', '图书管理学专业', '工程管理学院', '混日子', '这是一位教师', '2020-02-21', 123, '2020-02-13', true, '', '522501194910010007', 89, 'yms', '2020-01-18 10:22:07', 'teacher', '2020-01-18 10:22:07', '这是一位大学教师', true);
INSERT INTO public.teacher VALUES (5, '刘永生', 'liú yǒng shēng ', 5, 1, 44, 3, '女', '2020-02-05', 57, 115, 9, '2020-06-01', '植被与地质专业', '化工与生态工程学院', '混日子', '这是一位教师', '2020-02-21', 123, NULL, false, NULL, '522501194910010009', 71, 'yms', '2020-02-05 04:46:32', 'yms', '2020-02-05 04:46:32', '这是一位大学教师', true);


--
-- TOC entry 3287 (class 0 OID 0)
-- Dependencies: 204
-- Name: auth_center_res_sql; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.auth_center_res_sql', 60, true);


--
-- TOC entry 3288 (class 0 OID 0)
-- Dependencies: 206
-- Name: auth_center_role_res_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.auth_center_role_res_seq', 72, true);


--
-- TOC entry 3289 (class 0 OID 0)
-- Dependencies: 208
-- Name: client_details_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.client_details_seq', 6, true);


--
-- TOC entry 3290 (class 0 OID 0)
-- Dependencies: 210
-- Name: semester_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.semester_seq', 10, true);


--
-- TOC entry 3291 (class 0 OID 0)
-- Dependencies: 212
-- Name: student_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.student_seq', 489, true);


--
-- TOC entry 3292 (class 0 OID 0)
-- Dependencies: 214
-- Name: sys_data_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_data_seq', 139, true);


--
-- TOC entry 3293 (class 0 OID 0)
-- Dependencies: 216
-- Name: sys_res_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_res_seq', 33, true);


--
-- TOC entry 3294 (class 0 OID 0)
-- Dependencies: 220
-- Name: sys_role_res_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_role_res_seq', 45, true);


--
-- TOC entry 3295 (class 0 OID 0)
-- Dependencies: 218
-- Name: sys_role_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_role_seq', 9, true);


--
-- TOC entry 3296 (class 0 OID 0)
-- Dependencies: 224
-- Name: sys_user_role_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_user_role_seq', 133, true);


--
-- TOC entry 3297 (class 0 OID 0)
-- Dependencies: 222
-- Name: sys_user_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.sys_user_seq', 514, true);


--
-- TOC entry 3298 (class 0 OID 0)
-- Dependencies: 228
-- Name: teacher_seq; Type: SEQUENCE SET; Schema: public; Owner: gzmu
--

SELECT pg_catalog.setval('public.teacher_seq', 4, false);


-- Completed on 2020-05-22 21:20:01 CST

--
-- gzmuQL database dump complete
--

