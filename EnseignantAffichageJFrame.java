import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EnseignantAffichageJFrame extends JFrame {
    private JTextField textFieldRecherche; // Champ de texte pour la recherche
    private DefaultTableModel enseignantTable; // Modèle de tableau pour les enseignants
    private JTable table; // Tableau pour afficher les enseignants

    public EnseignantAffichageJFrame() throws SQLException {
        super("Enseignant Affichage");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        enseignantTable = new DefaultTableModel();
        table = new JTable(enseignantTable);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        String[] columnNames = { "Id", "Nom", "Prénom" };
        enseignantTable.setColumnIdentifiers(columnNames);

        // Champ de texte pour la recherche
        textFieldRecherche = new JTextField(20);
        JPanel recherchePanel = new JPanel(new FlowLayout());
        recherchePanel.add(new JLabel("Rechercher par nom :"));
        recherchePanel.add(textFieldRecherche);
        getContentPane().add(recherchePanel, BorderLayout.NORTH);

        // Charger les données initiales
        chargerEnseignants();

        // Ajouter un écouteur pour la recherche en temps réel
        textFieldRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEnseignants();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEnseignants();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                rechercheEnseignants();
            }
        });

        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton cancelBtn = new JButton("Annuler");
        actionPanel.add(cancelBtn);
        getContentPane().add(actionPanel, BorderLayout.SOUTH);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

    }

    // Méthode pour charger les enseignants initiaux
    private void chargerEnseignants() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Enseignant ORDER BY NOM ASC");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id_etu = rs.getInt(1);
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            enseignantTable.addRow(new Object[] { id_etu, nom, prenom });
        }
        rs.close();
        stmt.close();
        connection.close();
    }

    // Méthode pour rechercher les enseignants en fonction du nom saisi
    private void rechercheEnseignants() {
        String recherche = textFieldRecherche.getText().trim();
        enseignantTable.setRowCount(0); // Effacer toutes les lignes actuelles du tableau

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Enseignant WHERE NOM LIKE ? ORDER BY NOM ASC");
            stmt.setString(1, recherche + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_etu = rs.getInt(1);
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                enseignantTable.addRow(new Object[] { id_etu, nom, prenom });
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                EnseignantAffichageJFrame frame = new EnseignantAffichageJFrame();
                frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
