--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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

--
-- Name: voto; Type: SCHEMA; Schema: -; Owner: root
--

CREATE SCHEMA voto;


ALTER SCHEMA voto OWNER TO root;

--
-- Name: bool; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.bool AS character varying(50)
	CONSTRAINT bool_check CHECK ((upper((VALUE)::text) = ANY (ARRAY['TRUE'::text, 'FALSE'::text])));


ALTER DOMAIN public.bool OWNER TO postgres;

--
-- Name: utente; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.utente AS character varying(50)
	CONSTRAINT utente_check CHECK ((upper((VALUE)::text) = ANY (ARRAY['ADMIN'::text, 'ELETTORE'::text])));


ALTER DOMAIN public.utente OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: candidates; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.candidates (
    idsession integer NOT NULL,
    name character varying(50) NOT NULL,
    isparty boolean DEFAULT false,
    id integer NOT NULL
);


ALTER TABLE voto.candidates OWNER TO root;

--
-- Name: hasvoted; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.hasvoted (
    iduser integer NOT NULL,
    idvotingsession integer NOT NULL,
    hasvoted boolean DEFAULT false
);


ALTER TABLE voto.hasvoted OWNER TO root;

--
-- Name: partytable; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.partytable (
    idparty integer NOT NULL,
    idpartymember integer NOT NULL
);


ALTER TABLE voto.partytable OWNER TO root;

--
-- Name: session; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.session (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    modevote character varying(50) NOT NULL,
    isopen boolean DEFAULT false,
    modewin character varying(45) NOT NULL,
    fasescrutinio boolean DEFAULT false,
    dataapertura date,
    datachiusura date
);


ALTER TABLE voto.session OWNER TO root;

--
-- Name: users; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(500) NOT NULL,
    type public.utente
);


ALTER TABLE voto.users OWNER TO root;

--
-- Name: vote; Type: TABLE; Schema: voto; Owner: root
--

CREATE TABLE voto.vote (
    idsession integer NOT NULL,
    idcandidates integer NOT NULL,
    preferenza integer DEFAULT 0
);


ALTER TABLE voto.vote OWNER TO root;

--
-- Data for Name: candidates; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.candidates (idsession, name, isparty, id) FROM stdin;
\.


--
-- Data for Name: hasvoted; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.hasvoted (iduser, idvotingsession, hasvoted) FROM stdin;
\.


--
-- Data for Name: partytable; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.partytable (idparty, idpartymember) FROM stdin;
\.


--
-- Data for Name: session; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.session (id, name, modevote, isopen, modewin, fasescrutinio, dataapertura, datachiusura) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.users (id, username, password, type) FROM stdin;
\.


--
-- Data for Name: vote; Type: TABLE DATA; Schema: voto; Owner: root
--

COPY voto.vote (idsession, idcandidates, preferenza) FROM stdin;
\.


--
-- Name: candidates candidates_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.candidates
    ADD CONSTRAINT candidates_pkey PRIMARY KEY (id);


--
-- Name: hasvoted hasvoted_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.hasvoted
    ADD CONSTRAINT hasvoted_pkey PRIMARY KEY (iduser, idvotingsession);


--
-- Name: partytable partytable_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.partytable
    ADD CONSTRAINT partytable_pkey PRIMARY KEY (idparty, idpartymember);


--
-- Name: session session_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.session
    ADD CONSTRAINT session_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vote vote_pkey; Type: CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.vote
    ADD CONSTRAINT vote_pkey PRIMARY KEY (idsession, idcandidates);


--
-- Name: candidates candidates_idsession_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.candidates
    ADD CONSTRAINT candidates_idsession_fkey FOREIGN KEY (idsession) REFERENCES voto.session(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: hasvoted hasvoted_iduser_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.hasvoted
    ADD CONSTRAINT hasvoted_iduser_fkey FOREIGN KEY (iduser) REFERENCES voto.users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: hasvoted hasvoted_idvotingsession_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.hasvoted
    ADD CONSTRAINT hasvoted_idvotingsession_fkey FOREIGN KEY (idvotingsession) REFERENCES voto.session(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: partytable partytable_idparty_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.partytable
    ADD CONSTRAINT partytable_idparty_fkey FOREIGN KEY (idparty) REFERENCES voto.candidates(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: partytable partytable_idpartymember_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.partytable
    ADD CONSTRAINT partytable_idpartymember_fkey FOREIGN KEY (idpartymember) REFERENCES voto.candidates(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: vote vote_idsession_fkey; Type: FK CONSTRAINT; Schema: voto; Owner: root
--

ALTER TABLE ONLY voto.vote
    ADD CONSTRAINT vote_idsession_fkey FOREIGN KEY (idsession) REFERENCES voto.session(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

