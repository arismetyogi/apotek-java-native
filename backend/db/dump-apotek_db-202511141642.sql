--
-- PostgreSQL database dump
--

-- Dumped from database version 17.6 (Debian 17.6-2.pgdg13+1)
-- Dumped by pg_dump version 17.0

-- Started on 2025-11-14 16:42:38

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE apotek_db;
--
-- TOC entry 3450 (class 1262 OID 32768)
-- Name: apotek_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE apotek_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE apotek_db OWNER TO postgres;

\connect apotek_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 32806)
-- Name: medicines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicines (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    unit character varying(20),
    price numeric(12,2) NOT NULL,
    stock integer DEFAULT 0 NOT NULL,
    min_stock integer DEFAULT 10 NOT NULL,
    expiry_date date NOT NULL,
    status character varying(20) DEFAULT 'ACTIVE'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.medicines OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 32805)
-- Name: medicines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicines_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicines_id_seq OWNER TO postgres;

--
-- TOC entry 3451 (class 0 OID 0)
-- Dependencies: 217
-- Name: medicines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicines_id_seq OWNED BY public.medicines.id;


--
-- TOC entry 220 (class 1259 OID 32820)
-- Name: patients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patients (
    id integer NOT NULL,
    full_name character varying(100) NOT NULL,
    phone character varying(20),
    address text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.patients OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32819)
-- Name: patients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.patients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.patients_id_seq OWNER TO postgres;

--
-- TOC entry 3452 (class 0 OID 0)
-- Dependencies: 219
-- Name: patients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.patients_id_seq OWNED BY public.patients.id;


--
-- TOC entry 3279 (class 2604 OID 32809)
-- Name: medicines id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicines ALTER COLUMN id SET DEFAULT nextval('public.medicines_id_seq'::regclass);


--
-- TOC entry 3285 (class 2604 OID 32823)
-- Name: patients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patients ALTER COLUMN id SET DEFAULT nextval('public.patients_id_seq'::regclass);


--
-- TOC entry 3442 (class 0 OID 32806)
-- Dependencies: 218
-- Data for Name: medicines; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.medicines VALUES (1, 'Panadol Tablet', 'TAB', 2000.00, 100, 30, '2026-12-31', 'ACTIVE', '2025-11-14 14:11:18.950509', '2025-11-14 14:11:18.950509');
INSERT INTO public.medicines VALUES (2, 'Viagra', 'TAB', 180000.00, 10, 2, '2028-10-31', 'ACTIVE', '2025-11-14 14:21:46.051337', '2025-11-14 14:21:46.051337');
INSERT INTO public.medicines VALUES (3, 'Alco Plus DMP', 'BTL', 62000.00, 10, 4, '2027-10-11', 'ACTIVE', '2025-11-14 14:23:07.702331', '2025-11-14 14:23:07.702331');
INSERT INTO public.medicines VALUES (4, 'Alco Plus Exp', 'BTL', 62000.00, 12, 4, '2027-10-11', 'ACTIVE', '2025-11-14 14:23:22.364247', '2025-11-14 14:23:22.364247');
INSERT INTO public.medicines VALUES (6, 'Nutrimax C plus', 'BTL', 360000.00, 4, 6, '2027-10-11', 'ACTIVE', '2025-11-14 15:21:27.796627', '2025-11-14 15:21:27.796627');
INSERT INTO public.medicines VALUES (7, 'Nutrimax B Complex', 'BTL', 120000.00, 4, 6, '2025-11-30', 'ACTIVE', '2025-11-14 15:50:45.393095', '2025-11-14 15:50:45.393095');


--
-- TOC entry 3444 (class 0 OID 32820)
-- Dependencies: 220
-- Data for Name: patients; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.patients VALUES (1, 'John Doe', '02193102930291', 'Jl. Budi Utomo 1', '2025-11-14 16:08:12.708368', '2025-11-14 16:08:12.708368');
INSERT INTO public.patients VALUES (3, 'John Cena', '028012938021', 'Jl. Trim odiqw', '2025-11-14 16:37:16.584235', '2025-11-14 16:37:16.584235');


--
-- TOC entry 3453 (class 0 OID 0)
-- Dependencies: 217
-- Name: medicines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicines_id_seq', 7, true);


--
-- TOC entry 3454 (class 0 OID 0)
-- Dependencies: 219
-- Name: patients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patients_id_seq', 3, true);


--
-- TOC entry 3291 (class 2606 OID 32816)
-- Name: medicines medicines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicines
    ADD CONSTRAINT medicines_pkey PRIMARY KEY (id);


--
-- TOC entry 3295 (class 2606 OID 32829)
-- Name: patients patients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (id);


--
-- TOC entry 3288 (class 1259 OID 32818)
-- Name: idx_medicines_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_medicines_name ON public.medicines USING btree (name);


--
-- TOC entry 3289 (class 1259 OID 32817)
-- Name: idx_medicines_unit; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_medicines_unit ON public.medicines USING btree (lower((unit)::text));


--
-- TOC entry 3292 (class 1259 OID 32830)
-- Name: idx_patients_full_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_patients_full_name ON public.patients USING btree (full_name);


--
-- TOC entry 3293 (class 1259 OID 32831)
-- Name: idx_patients_phone; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_patients_phone ON public.patients USING btree (phone);


-- Completed on 2025-11-14 16:42:39

--
-- PostgreSQL database dump complete
--

