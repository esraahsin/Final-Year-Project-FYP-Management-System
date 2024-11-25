import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PFEAffecterEtudiants extends JFrame implements ActionListener {
    JPanel panel;
    MaskFormatter formatter;
    JFormattedTextField dateField;
    JComboBox<String> comboProjet, comboPresident, comboRapporteur, comboExaminateur, etudiants, etudiants1, invites;
    JTextField tfinvite;
    JButton btnValider, btnAnnuler;
    JCheckBox chkAjouterInvite, chkAjouterDeuxiemeEtudiant;

    public PFEAffecterEtudiants() {
        super("Affectation des étudiants aux projets de fin d'études");
        setSize(1000, 650);
        setLocationRelativeTo(null); // Centre la fenetre
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        etudiants = new JComboBox<>();
        etudiants1 = new JComboBox<>();
        invites = new JComboBox<>();

        JLabel labelPresident = new JLabel("Président du jury : ");
        JLabel labelExaminateur = new JLabel("Examinateur : ");
        JLabel labelRapporteur = new JLabel("Rapporteur : ");
        JLabel labelProjet = new JLabel("Choix du projet : ");
        JLabel labelDate = new JLabel("Date : ");

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        try {
            formatter = new MaskFormatter("##/##/#### ##:##");
            ; 
            dateField = new JFormattedTextField(formatter);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        comboProjet = new JComboBox<>();
        comboPresident = new JComboBox<>();
        comboRapporteur = new JComboBox<>();
        comboExaminateur = new JComboBox<>();

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * from pfe ");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String titre = rs.getString("titre");
                String lieu = rs.getString("locale");
                String encadreur = rs.getString("encadreur");
                int id = rs.getInt("idencadreur");
                String m = titre + " (" + lieu + ") - Encadrant : " + encadreur + "|" + id;
                comboProjet.addItem(m);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT idenseignant ,nom,prenom from enseignant ");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("idenseignant");
                String m = id + ":" + prenom + " " + nom;
                comboPresident.addItem(m);
                comboExaminateur.addItem(m);
                comboRapporteur.addItem(m);
                invites.addItem(m);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT idEtudiant,nom,prenom from etudiant ");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String id = String.valueOf(rs.getString("idetudiant"));
                String m = id + "-" + prenom + " " + nom;
                etudiants.addItem(m);
                etudiants1.addItem(m);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chkAjouterInvite = new JCheckBox("Ajouter un invité");
        chkAjouterInvite.addActionListener(this);

        tfinvite = new JTextField(20);
        tfinvite.setEnabled(false); // Désactiver le champ initialement

        chkAjouterDeuxiemeEtudiant = new JCheckBox("Ajouter un deuxième étudiant");
        chkAjouterDeuxiemeEtudiant.addActionListener(this);
        etudiants1.setEnabled(false);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(labelProjet, c);

        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 3;
        panel.add(comboProjet, c);

        c.gridx = 0;
        c.gridy = 1;
        JLabel labelEtudiant1 = new JLabel("Etudiant 1 :");
        panel.add(labelEtudiant1, c);
        c.gridx++;
        panel.add(etudiants, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(chkAjouterDeuxiemeEtudiant, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(etudiants1, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(labelPresident, c);

        c.gridx++;
        panel.add(comboPresident, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(labelRapporteur, c);

        c.gridx++;
        panel.add(comboRapporteur, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(labelExaminateur, c);

        c.gridx++;
        panel.add(comboExaminateur, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(chkAjouterInvite, c);

        c.gridx++;
        panel.add(invites, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(labelDate, c);

        c.gridx++;
        panel.add(dateField, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel(), c); // Ajouter un espace vide pour l'esthétique

        btnValider = new JButton("Affecter les étudiants");
        btnAnnuler = new JButton("Annuler");
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        c.gridy++;
        btnValider.setPreferredSize(new Dimension(150, 30));
        btnAnnuler.setPreferredSize(new Dimension(150, 30));
        panel.add(btnValider, c);
        btnAnnuler = new JButton("annuler");
        c.gridy++;
        panel.add(btnAnnuler, c);
        invites.setEnabled(false);
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        add(panel);

        btnValider.addActionListener(this);
        btnAnnuler.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == chkAjouterInvite) {
            invites.setEnabled(chkAjouterInvite.isSelected());
        } else if (source == chkAjouterDeuxiemeEtudiant) {
            etudiants1.setEnabled(chkAjouterDeuxiemeEtudiant.isSelected());
        } else if (source == btnValider) {
            String projet = comboProjet.getSelectedItem().toString();
            String[] nomencadreur = projet.split("\\|");
            int idenc = Integer.parseInt(nomencadreur[1]);
    
            String nomExam = comboExaminateur.getSelectedItem().toString();
            String[] idExam = nomExam.split(":");
            int idexam = Integer.parseInt(idExam[0]);
            String dateExpStr = dateField.getText();
            String nomRapp = comboRapporteur.getSelectedItem().toString();
            String[] idrapp = nomRapp.split(":");
            int idrap = Integer.parseInt(idrapp[0]);
            String[] pp = comboProjet.getSelectedItem().toString().split("\\(");
    
            String[] pp1 = pp[1].split("\\)");
            String loc = pp1[0];
            System.out.println(loc);
            String nomPres = comboPresident.getSelectedItem().toString();
            String[] idpres = nomPres.split(":");
            int idpre = Integer.parseInt(idpres[0]);
    
            String nomInv = chkAjouterInvite.isSelected() ? invites.getSelectedItem().toString() : null;
            int idinv = -1;
            if (nomInv != null) {
                String[] idinvSplit = nomInv.split(":");
                idinv = Integer.parseInt(idinvSplit[0]);
            }
    
            // Vérification des contraintes d'intégrité des clés étrangères
            if (!dateExpStr.isEmpty() && isValidDate(dateExpStr, "dd/MM/yyyy HH:mm") && idenc != idpre && idenc != idrap && idenc != idexam && idenc != idinv
                    && idpre != idrap && idpre != idexam && idpre != idinv
                    && idrap != idexam && idrap != idinv && idexam != idinv) {
                // Vérification supplémentaire pour les étudiants si nécessaire
                int ide1 = Integer.parseInt(etudiants.getSelectedItem().toString().split("-")[0]);
                int ide2 = -1;
                if (chkAjouterDeuxiemeEtudiant.isSelected()) {
                    ide2 = Integer.parseInt(etudiants1.getSelectedItem().toString().split("-")[0]);
                }
    
                // Vérification que l'étudiant 2 n'existe pas dans la colonne étudiant 1
                if ((ide2 == -1 || !etudiant2ExistsInEtudiant1(ide2, ide1))) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date parsedDate = dateFormat.parse(dateExpStr);
    
                        Timestamp dateExp = new Timestamp(parsedDate.getTime());
    
                        // Vérification de la disponibilité des membres du jury
                        if (membreJuryDisponible(dateExp, idpre) && membreJuryDisponible(dateExp, idrap)
                                && membreJuryDisponible(dateExp, idexam) && membreJuryDisponible(dateExp, idenc)
                                && (idinv == -1 || membreJuryDisponible(dateExp, idinv))) {
                            Connection connection = DatabaseConnector.getConnection();
                            PreparedStatement stmt = connection.prepareStatement("INSERT INTO soutenance(titre,date_pfe,locale,idpresident,idrapporteur,idexaminateur,idencadreur,idinviteS,idetudiant1,idetudiant2) VALUES (?,?,?,?,?,?,?,?,?,?)");
                            stmt.setString(1, comboProjet.getSelectedItem().toString());
                            stmt.setTimestamp(2, dateExp);
                            stmt.setString(3, loc);
                            stmt.setInt(4, idpre);
                            stmt.setInt(5, idrap);
                            stmt.setInt(6, idexam);
                            stmt.setInt(7, idenc);
                            stmt.setObject(8, idinv != -1 ? idinv : null);
                            stmt.setInt(9, ide1);
                            stmt.setObject(10, ide2 != -1 ? ide2 : null);
                            int nbLignesInserrees = stmt.executeUpdate();
                            if (nbLignesInserrees > 0) {
                                connection.close();
                                JOptionPane.showMessageDialog(null, "Soutenance enregistrée ", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Un membre du jury n'est pas disponible à cette date et heure !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException | ParseException e1) {
                        e1.printStackTrace();
                        if (e1.getCause() instanceof SQLException)
                            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la soutenance :Etudiant existant !");
                        else
                            JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de la soutenance" + e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "L'étudiant 2 existe déjà dans la colonne étudiant 1 !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vérifiez vos données ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean membreJuryDisponible(Timestamp dateExp, int idMembreJury) {
        boolean disponible = false;
        try {
            // Connexion à la base de données
            Connection connection = DatabaseConnector.getConnection();
            
            // Préparation de la requête SQL pour vérifier la disponibilité du membre du jury
            String requete = "SELECT COUNT(*) FROM soutenance WHERE (idpresident = ? OR idrapporteur = ? OR idexaminateur = ? OR idencadreur = ? OR idinvites = ?) AND date_pfe = ?";
            PreparedStatement stmt = connection.prepareStatement(requete);
            stmt.setInt(1, idMembreJury);
            stmt.setInt(2, idMembreJury);
            stmt.setInt(3, idMembreJury);
            stmt.setInt(4, idMembreJury);
            stmt.setInt(5, idMembreJury);
            stmt.setTimestamp(6, dateExp);
            
            // Exécution de la requête
            ResultSet rs = stmt.executeQuery();
            
            // Vérification du résultat de la requête
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    disponible = true; // Le membre du jury est disponible
                }
            }
            
            // Fermeture des ressources
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion ou d'exécution de requête
        }
        return disponible;
    }

    private boolean etudiant2ExistsInEtudiant1(int idEtudiant2, int idEtudiant1) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM soutenance WHERE idetudiant1= ?");
            stmt.setInt(1, idEtudiant2);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean isValidDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // La date doit être stricte
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PFEAffecterEtudiants frame = new PFEAffecterEtudiants();
            frame.setVisible(true);
        });
    }
}
