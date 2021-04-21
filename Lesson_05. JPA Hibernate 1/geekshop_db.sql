CREATE DATABASE geekshop;

CREATE SCHEMA public AUTHORIZATION postgres;
COMMENT ON SCHEMA public IS 'standard public schema for geekshop database';

GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;

CREATE TABLE public.products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL) NOT NULL;

CREATE UNIQUE INDEX products_id_uindex ON public.products (id);

INSERT INTO public.products (name, price) VALUES
('Product 1', 100),
('Product 2', 200.02),
('Apricot', 33.33),
('Апельсины', 444.00),
('Яблоки', 5.05);