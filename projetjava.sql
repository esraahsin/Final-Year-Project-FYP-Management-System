-- Table Etudiant
CREATE TABLE Etudiant (
    idEtudiant INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    specialite VARCHAR(50)
);

-- Table Enseignant
CREATE TABLE Enseignant (
    idEnseignant INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50)
);

CREATE TABLE Pfe (
    
    titre VARCHAR(100),
    date_pfe DATETIME,
    locale VARCHAR(50),
    note INT, 
    idPresident INT,
    idRapporteur INT,
    idExaminateur INT,
    idEncadreur INT,
    idInvites INT,
    idEtudiant1 INT primary key , 
    idEtudiant2 INT, 
    FOREIGN KEY (idPresident) REFERENCES Enseignant(idEnseignant),
    FOREIGN KEY (idRapporteur) REFERENCES Enseignant(idEnseignant),
    FOREIGN KEY (idExaminateur) REFERENCES Enseignant(idEnseignant),
    FOREIGN KEY (idEncadreur) REFERENCES Enseignant(idEnseignant),
    FOREIGN KEY (idInvites) REFERENCES Enseignant(idEnseignant),
    FOREIGN KEY (idEtudiant1) REFERENCES Etudiant(idEtudiant),
    FOREIGN KEY (idEtudiant2) REFERENCES Etudiant(idEtudiant)
);
