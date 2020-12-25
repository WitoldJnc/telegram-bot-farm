CREATE TABLE public.daily_entity (
	id integer PRIMARY KEY NOT NULL,
	status bool NULL DEFAULT false,
	title varchar(255) NULL,
	url varchar(255) NULL
);