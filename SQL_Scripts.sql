--Script for clients table

CREATE TABLE IF NOT EXISTS public.clients
(
    id uuid NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    is_admin boolean NOT NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clients
    OWNER to tea_shop_owner;
    
--insert script for table clients

INSERT INTO public.clients(
	id, name, password, email, is_admin)
	VALUES (?, ?, ?, ?, ?);
  
 --Create Script for table stock
 
CREATE TABLE IF NOT EXISTS public.stock
(
    id uuid NOT NULL,
    name character varying(250) COLLATE pg_catalog."default" NOT NULL,
    av_kg bigint NOT NULL,
    price_kg numeric(10,2) NOT NULL,
    CONSTRAINT stock_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

GRANT ALL ON TABLE public.stock TO tea_shop_owner;

--Insert script for table stock

INSERT INTO public.stock(
	id, name, av_kg, price_kg)
	VALUES (?, ?, ?, ?);
  
 --Create Script for table tea_order
 
 CREATE TABLE IF NOT EXISTS public.tea_order
(
    id uuid NOT NULL,
    delivery_name character varying(150) COLLATE pg_catalog."default" NOT NULL,
    delivery_street character varying(150) COLLATE pg_catalog."default" NOT NULL,
    delivery_city character varying(150) COLLATE pg_catalog."default" NOT NULL,
    delivery_state character varying(150) COLLATE pg_catalog."default" NOT NULL,
    delivery_zip character varying(150) COLLATE pg_catalog."default" NOT NULL,
    cc_number character varying(16) COLLATE pg_catalog."default" NOT NULL,
    cc_expiration character varying(5) COLLATE pg_catalog."default" NOT NULL,
    cc_cvv character varying(3) COLLATE pg_catalog."default" NOT NULL,
    overall_price numeric(9,2) NOT NULL,
    CONSTRAINT tea_order_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

GRANT ALL ON TABLE public.tea_order TO tea_shop_owner;

--Insert Script for table tea_order

INSERT INTO public.tea_order(
	id, delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, overall_price)
	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
