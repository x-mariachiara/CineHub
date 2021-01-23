
INSERT INTO public.utente VALUES ('Recensore', 'giaregistrato@gmail.com', 'Cardaropoli', '1999-12-03', true, false, 'Giuseppe', '$2y$10$zFb62R2XAESF6ufbTz14aeLzWbw3648A35iOKF5N73Qr.s1hItUMW', 'Peppe99', NULL);
INSERT INTO public.utente VALUES ('Recensore', 'bannato@gmail.com', 'Nasto', '2000-02-07', true, true, 'Maria Chiara', '$2y$10$B4y6X1XKfKmEd2l2H2nn.u4OXaZDyVAFOIuElmKIVUnSdhOi5dnHS', 'x-mariachiara', NULL);
INSERT INTO public.utente VALUES ('ResponsabileCatalogo', 'pirupiru@gmail.com', 'Stromboli', '1972-10-29', true, false, 'Ignazio', '$2y$12$srdF17Vl8HQTWjLB5I5i5eLm0Xh4w8O5tMobmznoT.4gpnixhBMmK', 'Strom-Zio', NULL);
INSERT INTO public.utente VALUES ('Moderatore', 'supermario@gmail.com', 'Super', '1988-11-15', true, false, 'Mario', '$2y$12$eLvtYFIEI7LIQgbcb7ZtqubF1zSM5k5begx6Yc3EruZfYCd3/MKtK', 'MarioS', 0);
INSERT INTO public.utente VALUES ('Moderatore', 'mariafalda@gmail.com', 'Falda', '1956-03-03', true, false, 'Maria', '$2y$12$FL5gZXvl82amVbHttDKzbODDpW7UP6FSu7W0Glxk3d9.ehwhT8bcS', 'StellinaXD', 1);



INSERT INTO public.film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (16, 2020, 'https://m.media-amazon.com/images/M/MV5BYWVmYTFjODItOTY2Ni00NDhhLTk1ZDYtMzBmOGFhZTMyY2Q0XkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_SY1000_SX675_AL_.jpg', 'https://www.youtube.com/embed/kD8RDe4Uggw?controls=0', 'Stanchi di essere single in vacanza, due sconosciuti desiderano solamente qualcuno con cui passare le feste e beffarsi degli altri. Senza stress né passione, tanto comunque non è amore.', 'Holidates', 0);
INSERT INTO public.film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (17, 1999, 'https://images-na.ssl-images-amazon.com/images/I/51XE3ed1dkL._SY445_.jpg', 'https://www.youtube.com/embed/q_tuIcqX5-g?controls=0', 'Esistono due realtà: una è l''esistenza che conduciamo ogni giorno, l''altra è nascosta. Neo vuole scoprire la verità su Matrix, mondo virtuale elaborato al computer creato per tenere sotto controllo le persone. Morpheus potrebbe aiutarlo.', 'Matrix', 0);
INSERT INTO public.film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (18, 2015, 'https://upload.wikimedia.org/wikipedia/en/9/98/John_Wick_TeaserPoster.jpg', 'https://www.youtube.com/embed/N_ZPL3hmFEo?controls=0', 'Il leggendario assassino John Wick si è allontanato dal mondo della violenza dopo aver sposato l''amore della propria vita. Quando la donna muore improvvisamente, il giovane cade nello sconforto più profondo. Il perfido criminale Iosef Tarasov decide di tormentarlo rubandogli l''automobile ed uccidendogli il cane. Per l''uomo è l''ora della vendetta.', 'John Wick', 0);


INSERT INTO public.genere VALUES (17);
INSERT INTO public.genere VALUES (5);
INSERT INTO public.genere VALUES (9);
INSERT INTO public.genere VALUES (3);
INSERT INTO public.genere VALUES (19);
INSERT INTO public.genere VALUES (7);
INSERT INTO public.genere VALUES (13);
INSERT INTO public.genere VALUES (10);


INSERT INTO public.media_genere VALUES (16, 5);
INSERT INTO public.media_genere VALUES (16, 17);
INSERT INTO public.media_genere VALUES (17, 19);
INSERT INTO public.media_genere VALUES (17, 3);
INSERT INTO public.media_genere VALUES (17, 9);
INSERT INTO public.media_genere VALUES (18, 3);
INSERT INTO public.media_genere VALUES (18, 19);
INSERT INTO public.media_genere VALUES (18, 7);
INSERT INTO public.media_genere VALUES (19, 10);
INSERT INTO public.media_genere VALUES (19, 13);
INSERT INTO public.media_genere VALUES (19, 7);


