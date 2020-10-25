CREATE TABLE Utente (
  email varchar PRIMARY KEY,
  username varchar,
  password varchar,
  tipo varchar,
  bannato boolean
);

CREATE TABLE Recensione (
  id uuid PRIMARY KEY,
  user_id varchar,
  media_id uuid,
  parent_id uuid UNIQUE,
  created_at timestamp,
  contenuto varchar
);

CREATE TABLE Vote (
  user_id varchar,
  recensione_id uuid,
  created_at timestamp,
  positive boolean,
  PRIMARY KEY (user_id, recensione_id)
);

CREATE TABLE Film (
  id uuid PRIMARY KEY,
  user_id varchar,
  titolo varchar,
  anno_uscita varchar,
  sinossi varchar,
  link_trailer varchar,
  link_locandina varchar
);

CREATE TABLE Genere (
  tipo varchar PRIMARY KEY
);

CREATE TABLE CastMedia (
  id uuid PRIMARY KEY,
  nome_completo varchar
);

CREATE TABLE SerieTV (
  id uuid PRIMARY KEY,
  user_id varchar,
  titolo varchar,
  anno_uscita varchar,
  sinossi varchar,
  link_trailer varchar,
  link_locandina varchar
);

CREATE TABLE Puntata (
  id uuid PRIMARY KEY,
  user_id varchar,
  numero integer,
  titolo varchar,
  sinossi varchar
);

CREATE TABLE Ruolo (
  tipo varchar,
  cast_id uuid,
  media_id uuid,
  PRIMARY KEY (tipo, cast_id, media_id)
);

CREATE TABLE Media_Genere (
  media_id uuid,
  genere_id varchar,
  PRIMARY KEY (media_id, genere_id)
);

CREATE TABLE Stagione (
  numero integer,
  serie_id uuid,
  puntata_id uuid,
  PRIMARY KEY (numero, serie_id, puntata_id)
);

CREATE TABLE Moderato (
  moderatore_id varchar,
  recensione_id uuid,
  tipo varchar,
  PRIMARY KEY (moderatore_id, recensione_id)
);

-- ALTER TABLE Recensione ADD FOREIGN KEY (user_id) REFERENCES Utente (email);
--
-- ALTER TABLE Recensione ADD FOREIGN KEY (media_id) REFERENCES Film (id);
--
-- ALTER TABLE Recensione ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);
--
-- ALTER TABLE Recensione ADD FOREIGN KEY (parent_id) REFERENCES Recensione (id);
--
-- ALTER TABLE Vote ADD FOREIGN KEY (user_id) REFERENCES Utente (email);
--
-- ALTER TABLE Vote ADD FOREIGN KEY (recensione_id) REFERENCES Recensione (id);
--
-- ALTER TABLE Film ADD FOREIGN KEY (user_id) REFERENCES Utente (email);
--
-- ALTER TABLE SerieTV ADD FOREIGN KEY (user_id) REFERENCES Utente (email);
--
-- ALTER TABLE Puntata ADD FOREIGN KEY (user_id) REFERENCES Utente (email);
--
-- ALTER TABLE Ruolo ADD FOREIGN KEY (cast_id) REFERENCES Cast (id);
--
-- ALTER TABLE Ruolo ADD FOREIGN KEY (media_id) REFERENCES Film (id);
--
-- ALTER TABLE Ruolo ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);
--
-- ALTER TABLE Media_Genere ADD FOREIGN KEY (media_id) REFERENCES Film (id);
--
-- ALTER TABLE Media_Genere ADD FOREIGN KEY (media_id) REFERENCES SerieTV (id);
--
-- ALTER TABLE Media_Genere ADD FOREIGN KEY (genere_id) REFERENCES Genere (tipo);
--
-- ALTER TABLE Stagione ADD FOREIGN KEY (serie_id) REFERENCES SerieTV (id);
--
-- ALTER TABLE Stagione ADD FOREIGN KEY (puntata_id) REFERENCES Puntata (id);
--
-- ALTER TABLE Moderato ADD FOREIGN KEY (moderatore_id) REFERENCES Utente (email);
--
-- ALTER TABLE Moderato ADD FOREIGN KEY (recensione_id) REFERENCES Recensione (id);
