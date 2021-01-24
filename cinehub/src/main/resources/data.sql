
INSERT INTO utente VALUES ('Recensore', 'giaregistrato@gmail.com', 'Cardaropoli', '1999-12-03', true, false, 'Giuseppe', '$2y$10$zFb62R2XAESF6ufbTz14aeLzWbw3648A35iOKF5N73Qr.s1hItUMW', 'Peppe99', NULL);
INSERT INTO utente VALUES ('Recensore', 'bannato@gmail.com', 'Nasto', '2000-02-07', true, true, 'Maria Chiara', '$2y$10$B4y6X1XKfKmEd2l2H2nn.u4OXaZDyVAFOIuElmKIVUnSdhOi5dnHS', 'x-mariachiara', NULL);
INSERT INTO utente VALUES ('ResponsabileCatalogo', 'pirupiru@gmail.com', 'Stromboli', '1972-10-29', true, false, 'Ignazio', '$2y$12$srdF17Vl8HQTWjLB5I5i5eLm0Xh4w8O5tMobmznoT.4gpnixhBMmK', 'Strom-Zio', NULL);
INSERT INTO utente VALUES ('Moderatore', 'supermario@gmail.com', 'Super', '1988-11-15', true, false, 'Mario', '$2y$12$eLvtYFIEI7LIQgbcb7ZtqubF1zSM5k5begx6Yc3EruZfYCd3/MKtK', 'MarioS', 0);
INSERT INTO utente VALUES ('Moderatore', 'mariafalda@gmail.com', 'Falda', '1956-03-03', true, false, 'Maria', '$2y$12$FL5gZXvl82amVbHttDKzbODDpW7UP6FSu7W0Glxk3d9.ehwhT8bcS', 'StellinaXD', 1);



INSERT INTO film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (16, 2020, 'https://m.media-amazon.com/images/M/MV5BYWVmYTFjODItOTY2Ni00NDhhLTk1ZDYtMzBmOGFhZTMyY2Q0XkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_SY1000_SX675_AL_.jpg', 'https://www.youtube.com/embed/kD8RDe4Uggw?controls=0', 'Stanchi di essere single in vacanza, due sconosciuti desiderano solamente qualcuno con cui passare le feste e beffarsi degli altri. Senza stress né passione, tanto comunque non è amore.', 'Holidates', 0);
INSERT INTO film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (17, 1999, 'https://images-na.ssl-images-amazon.com/images/I/51XE3ed1dkL._SY445_.jpg', 'https://www.youtube.com/embed/q_tuIcqX5-g?controls=0', 'Esistono due realtà: una è l''esistenza che conduciamo ogni giorno, l''altra è nascosta. Neo vuole scoprire la verità su Matrix, mondo virtuale elaborato al computer creato per tenere sotto controllo le persone. Morpheus potrebbe aiutarlo.', 'Matrix', 0);
INSERT INTO film (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (18, 2015, 'https://upload.wikimedia.org/wikipedia/en/9/98/John_Wick_TeaserPoster.jpg', 'https://www.youtube.com/embed/N_ZPL3hmFEo?controls=0', 'Il leggendario assassino John Wick si è allontanato dal mondo della violenza dopo aver sposato l''amore della propria vita. Quando la donna muore improvvisamente, il giovane cade nello sconforto più profondo. Il perfido criminale Iosef Tarasov decide di tormentarlo rubandogli l''automobile ed uccidendogli il cane. Per l''uomo è l''ora della vendetta.', 'John Wick', 0);

INSERT INTO serie_tv (id, anno_uscita, link_locandina, link_trailer, sinossi, titolo, media_voti) VALUES (19, 2018, 'https://cdn.gelestatic.it/kataweb/tvzap/2018/10/taglioAlta_00113.jpg', 'https://www.youtube.com/embed/WAtYTXI-I28?controls=0', 'Mentre si avvicina il sedicesimo compleanno, Sabrina deve scegliere tra il mondo delle streghe della sua famiglia e il mondo umano dei suoi amici.', 'Le Terrificanti Avventure Di Sabrina', 0);

INSERT INTO genere VALUES (17);
INSERT INTO genere VALUES (5);
INSERT INTO genere VALUES (9);
INSERT INTO genere VALUES (3);
INSERT INTO genere VALUES (19);
INSERT INTO genere VALUES (7);
INSERT INTO genere VALUES (13);
INSERT INTO genere VALUES (10);


INSERT INTO media_genere VALUES (16, 5);
INSERT INTO media_genere VALUES (16, 17);
INSERT INTO media_genere VALUES (17, 19);
INSERT INTO media_genere VALUES (17, 3);
INSERT INTO media_genere VALUES (17, 9);
INSERT INTO media_genere VALUES (18, 3);
INSERT INTO media_genere VALUES (18, 19);
INSERT INTO media_genere VALUES (18, 7);
INSERT INTO media_genere VALUES (19, 10);
INSERT INTO media_genere VALUES (19, 13);
INSERT INTO media_genere VALUES (19, 7);

INSERT INTO cast_film VALUES (20, 'Whiteshell', 'John');
INSERT INTO cast_film VALUES (21, 'Roberts', 'Emma');

INSERT INTO stagione VALUES (1, 19);

INSERT INTO puntata VALUES (1, 'aced000573720031636f6d2e756e6973612e63696e656875622e646174612e656e746974792e53746167696f6e652453746167696f6e654944b261b3004765a2ff0200024c000e6e756d65726f53746167696f6e657400134c6a6176612f6c616e672f496e74656765723b4c00097365726965547649647400104c6a6176612f6c616e672f4c6f6e673b7870737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000017372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c75657871007e00050000000000000013', 0, 'Sabrina Spellman è una strega mezzosangue che vive con le sue zie, Zelda e Hilda, e il cugino Ambrose nella cittadina di Greendale. Trascorre le sue giornate al liceo Baxter High con il suo fidanzato Harvey e le migliori amiche Rosalind e Susie. Come ogni strega, in occasione del suo sedicesimo compleanno, il giorno di Halloween, Sabrina dovrà ricevere il battesimo Oscuro', 'Capitolo uno: Nel paese di Halloween', 1, 19);

INSERT INTO stagione_puntate VALUES (1, 19, 1, 'aced000573720031636f6d2e756e6973612e63696e656875622e646174612e656e746974792e53746167696f6e652453746167696f6e654944b261b3004765a2ff0200024c000e6e756d65726f53746167696f6e657400134c6a6176612f6c616e672f496e74656765723b4c00097365726965547649647400104c6a6176612f6c616e672f4c6f6e673b7870737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000017372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c75657871007e00050000000000000013');

INSERT INTO recensione VALUES (1, 'Ottimo Film', '2021-01-23 22:42:30.278', 4, 16, null, null, null, 'giaregistrato@gmail.com');

INSERT INTO film_lista_recensioni VALUES (16, 1);

INSERT INTO utente_lista_recensioni VALUES ('giaregistrato@gmail.com', 1);

INSERT INTO mi_piace VALUES (1, 'giaregistrato@gmail.com', '2021-01-24 16:19:02.686', true);

INSERT INTO utente_lista_mi_piace VALUES('giaregistrato@gmail.com', 1, 'giaregistrato@gmail.com');

INSERT INTO recensione_lista_mi_piace VALUES(1, 1, 'giaregistrato@gmail.com');

-- INSERT INTO mi_piace VALUES (2, 'giaregistrato@gmail.com', '2021-01-24 16:19:02.686', true);
--
-- INSERT INTO utente_lista_mi_piace VALUES('giaregistrato@gmail.com', 2, 'giaregistrato@gmail.com');
--
-- INSERT INTO recensione_lista_mi_piace VALUES(2, 2, 'giaregistrato@gmail.com');
