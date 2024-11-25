import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EtudiantAffichageJFrame extends JFrame {

    JCheckBox licenceInformatiqueCheckBox = new JCheckBox("Licence: Informatique");
    JCheckBox licenceElectroniqueCheckBox = new JCheckBox("Licence: Électronique");
    JCheckBox mastereRechercheCheckBox = new JCheckBox("Mastère: Recherche");
    JCheckBox mastereProfessionnelCheckBox = new JCheckBox("Mastère: Professionnel");
    JCheckBox ingenieurInformatiqueCheckBox = new JCheckBox("Ingénieur: Informatique");
    JCheckBox ingenieurElectroniqueCheckBox = new JCheckBox("Ingénieur: Électronique");
    private DefaultTableModel etudiantTable;

    public EtudiantAffichageJFrame() throws SQLException {
        super("Etudiant Affichage");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(54, 69, 79)); // Couleur de fond de l'interface

        // Définition du Look and Feel Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        etudiantTable = new DefaultTableModel();
        JTable table = new JTable(etudiantTable);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        String[] columnNames = {"Id", "Nom", "Prénom", "Spécialité"};
        etudiantTable.setColumnIdentifiers(columnNames);

        afficherTousLesEtudiants();

        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton cancelBtn = new JButton("Annuler");

        // Ajout des cases à cocher pour les spécialités
         licenceInformatiqueCheckBox = new JCheckBox("Licence: Informatique");
         licenceElectroniqueCheckBox = new JCheckBox("Licence: Électronique");
         mastereRechercheCheckBox = new JCheckBox("Mastère: Recherche");
         mastereProfessionnelCheckBox = new JCheckBox("Mastère: Professionnel");
         ingenieurInformatiqueCheckBox = new JCheckBox("Ingénieur: Informatique");
         ingenieurElectroniqueCheckBox = new JCheckBox("Ingénieur: Électronique");

        // Champ de texte pour la recherche par nom
        JTextField textFieldRecherche = new JTextField(20);
        actionPanel.add(new JLabel("Rechercher par nom :"));
        actionPanel.add(textFieldRecherche);

        actionPanel.add(licenceInformatiqueCheckBox);
        actionPanel.add(licenceElectroniqueCheckBox);
        actionPanel.add(mastereRechercheCheckBox);
        actionPanel.add(mastereProfessionnelCheckBox);
        actionPanel.add(ingenieurInformatiqueCheckBox);
        actionPanel.add(ingenieurElectroniqueCheckBox);


        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
// Ajouter un écouteur pour la recherche en temps réel par nom
textFieldRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
    }

    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
    }

    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
    }
});

// Ajouter un écouteur pour la recherche en temps réel par spécialité
ItemListener specialiteListener = new ItemListener() {
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
            rechercheEtudiantsParSpecialite();
        }
    }
};

licenceInformatiqueCheckBox.addItemListener(specialiteListener);
licenceElectroniqueCheckBox.addItemListener(specialiteListener);
mastereRechercheCheckBox.addItemListener(specialiteListener);
mastereProfessionnelCheckBox.addItemListener(specialiteListener);
ingenieurInformatiqueCheckBox.addItemListener(specialiteListener);
ingenieurElectroniqueCheckBox.addItemListener(specialiteListener);

        actionPanel.add(cancelBtn);
        getContentPane().add(actionPanel, BorderLayout.SOUTH);

        // Adapter la taille de la fenêtre en fonction de la taille du contenu de la table
        pack();
        // Centrer la fenêtre sur l'écran
        setLocationRelativeTo(null);

        // Ajouter un écouteur pour la recherche en temps réel
        textFieldRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEtudiantsParNom(textFieldRecherche.getText().trim());
            }
        });
    }
    private void rechercheEtudiantsParSpecialite() {
        etudiantTable.setRowCount(0); // Effacer toutes les lignes actuelles du tableau
    
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ETUDIANT WHERE");
        boolean addCondition = false;
    
        if (licenceInformatiqueCheckBox.isSelected()) {
            queryBuilder.append(" SPECIALITE = 'Licence: Informatique'");
            addCondition = true;
        }
        if (licenceElectroniqueCheckBox.isSelected()) {
            if (addCondition) {
                queryBuilder.append(" OR");
            }
            queryBuilder.append(" SPECIALITE = 'Licence: Électronique'");
            addCondition = true;
        }
        if (mastereRechercheCheckBox.isSelected()) {
            if (addCondition) {
                queryBuilder.append(" OR");
            }
            queryBuilder.append(" SPECIALITE = 'Mastère: Recherche'");
            addCondition = true;
        }
        if (mastereProfessionnelCheckBox.isSelected()) {
            if (addCondition) {
                queryBuilder.append(" OR");
            }
            queryBuilder.append(" SPECIALITE = 'Mastère: Professionnel'");
            addCondition = true;
        }
        if (ingenieurInformatiqueCheckBox.isSelected()) {
            if (addCondition) {
                queryBuilder.append(" OR");
            }
            queryBuilder.append(" SPECIALITE = 'Ingénieur: Informatique'");
            addCondition = true;
        }
        if (ingenieurElectroniqueCheckBox.isSelected()) {
            if (addCondition) {
                queryBuilder.append(" OR");
            }
            queryBuilder.append(" SPECIALITE = 'Ingénieur: Électronique'");
            addCondition = true;
        }
    
        if (!addCondition) {
            // Aucune spécialité sélectionnée, donc aucune recherche à effectuer
            return;
        }
    
        queryBuilder.append(" ORDER BY NOM ASC");
    
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_etu = rs.getInt("idetudiant");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                etudiantTable.addRow(new Object[]{id_etu, nom, prenom, specialite});
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    // Méthode pour afficher tous les étudiants
    private void afficherTousLesEtudiants() {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ETUDIANT ORDER BY NOM ASC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_etu = rs.getInt(1);
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                etudiantTable.addRow(new Object[]{id_etu, nom, prenom, specialite});
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Méthode pour rechercher les étudiants par nom
    private void rechercheEtudiantsParNom(String recherche) {
        etudiantTable.setRowCount(0); // Effacer toutes les lignes actuelles du tableau
    
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM ETUDIANT WHERE NOM LIKE ? ORDER BY NOM ASC");
            stmt.setString(1, recherche + "%"); // Recherche par les premiers caractères
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_etu = rs.getInt("idetudiant");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                etudiantTable.addRow(new Object[]{id_etu, nom, prenom, specialite});
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Méthode pour afficher les étudiants par spécialité
    private void afficherEtudiantsParSpecialite(String[] selectedSpecialites, int count) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ETUDIANT WHERE SPECIALITE IN (");
            for (int i = 0; i < count; i++) {
                queryBuilder.append("?");
                if (i < count - 1) {
                    queryBuilder.append(", ");
                }
            }
            queryBuilder.append(") order by nom asc");
            PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < count; i++) {
                stmt.setString(i + 1, selectedSpecialites[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_etu = rs.getInt("idetudiant");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                etudiantTable.addRow(new Object[]{id_etu, nom, prenom, specialite});
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new EtudiantAffichageJFrame().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
