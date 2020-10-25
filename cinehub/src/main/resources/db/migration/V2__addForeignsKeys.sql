ALTER TABLE Recensione ADD FOREIGN KEY (user_id) REFERENCES Utente (email);

ALTER TABLE Recensione ADD FOREIGN KEY (media_id) REFERENCES Film (id);

ALTER TABLE Recensione ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);

ALTER TABLE Recensione ADD FOREIGN KEY (parent_id) REFERENCES Recensione (id);

ALTER TABLE Vote ADD FOREIGN KEY (user_id) REFERENCES Utente (email);

ALTER TABLE Vote ADD FOREIGN KEY (recensione_id) REFERENCES Recensione (id);

ALTER TABLE Film ADD FOREIGN KEY (user_id) REFERENCES Utente (email);

ALTER TABLE SerieTV ADD FOREIGN KEY (user_id) REFERENCES Utente (email);

ALTER TABLE Puntata ADD FOREIGN KEY (user_id) REFERENCES Utente (email);

ALTER TABLE Ruolo ADD FOREIGN KEY (cast_id) REFERENCES CastMedia (id);

ALTER TABLE Ruolo ADD FOREIGN KEY (media_id) REFERENCES Film (id);

ALTER TABLE Ruolo ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);

ALTER TABLE Media_Genere ADD FOREIGN KEY (media_id) REFERENCES Film (id);

ALTER TABLE Media_Genere ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);

ALTER TABLE Media_Genere ADD FOREIGN KEY (genere_id) REFERENCES Genere (tipo);

ALTER TABLE Stagione ADD FOREIGN KEY (serie_id) REFERENCES SerieTV (id);

ALTER TABLE Stagione ADD FOREIGN KEY (puntata_id) REFERENCES Puntata (id);

ALTER TABLE Moderato ADD FOREIGN KEY (recensione_id) REFERENCES Recensione (id);

ALTER TABLE Moderato ADD FOREIGN KEY (moderatore_id) REFERENCES Utente (email);