import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EnseignantAffichageProjet extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton btnValider, btnAnnuler;
    private JLabel labelidEncadrant;
    private JTextField textFieldEnseignant; // Champ de texte pour le nom de l'enseignant
    private JTable table;
    private DefaultTableModel pfetable;

    public EnseignantAffichageProjet() {
        super("Afficher les projets d'un enseignant");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Création de la frame
        setSize(800, 600);
        setLocationRelativeTo(null); // pour que ça apparaisse au centre de l'écran
        setResizable(false);

        // Création des boutons et labels
        btnValider = new JButton("Valider");
        btnAnnuler = new JButton("Annuler");
        labelidEncadrant = new JLabel("ID Encadrant : ");
        
        // Champ de texte pour saisir le nom de l'enseignant
        textFieldEnseignant = new JTextField();
        textFieldEnseignant.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateEnseignantsList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateEnseignantsList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Pas nécessaire pour un champ de texte simple
            }
        });

        btnValider.addActionListener(this);
        btnAnnuler.addActionListener(this);

        pfetable = new DefaultTableModel();
        table = new JTable(pfetable);

        // Création de la table
        String[] colonnes = { "Titre", "Lieu", "Date Debut ", "Etudiant1", "Etudiant2", "Statut" };
        pfetable.setColumnIdentifiers(colonnes);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, "Center");
        JPanel JPanel1 = new JPanel();
        GridLayout layout = new GridLayout(5, 2);
        layout.setHgap(10);
        layout.setVgap(10);
        btnAnnuler.setSize(50,30);
        JPanel1.setLayout(layout);
        JPanel1.add(labelidEncadrant);
        JPanel1.add(textFieldEnseignant); // Ajout du champ de texte pour le nom de l'enseignant
        JPanel1.add(btnValider);
        JPanel1.add(btnAnnuler);

        add(JPanel1, "North");

        // Mise à jour de la liste des enseignants au démarrage
        updateEnseignantsList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnValider)) {
            // Votre logique existante pour valider l'action
        }
        if (e.getSource().equals(btnAnnuler)) {
            dispose();
        }
    }

    private void updateEnseignantsList() {
        String searchText = textFieldEnseignant.getText().trim();
        pfetable.setRowCount(0); // Efface toutes les lignes actuelles du tableau

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT titre, locale, date_pfe, idetudiant1, idetudiant2, note FROM soutenance INNER JOIN enseignant ON soutenance.idencadreur = enseignant.idenseignant WHERE enseignant.nom LIKE ?");
            stmt.setString(1, searchText + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Ajoutez les données de la soutenance au tableau
                String titr = rs.getString("titre");
                String lieu = rs.getString("locale");
                Date debut = rs.getDate("date_pfe");
                int etud1 = rs.getInt("idetudiant1");
                int etud2 = rs.getInt("idetudiant2");
                String statut;
                if (rs.getObject("note") == null)
                    statut = "En attente d'évaluation";
                else {
                    int note = rs.getInt("note");
                    statut = "Évaluée  (" + note + "/20)";
                }
                String etudiant1 = getNomPrenom(connection, etud1);
                String etudiant2 = getNomPrenom(connection, etud2);
                pfetable.addRow(new Object[] { titr, lieu, debut, etudiant1, etudiant2, statut });
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String getNomPrenom(Connection connection, int id) {
        String nom = "";
        String prenom = "";
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT nom,prenom FROM etudiant WHERE idetudiant=?");
            stmt.setInt(1, id);
            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {
                nom = rset.getString("nom");
                prenom = rset.getString("prenom");
            }
            rset.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return nom + " " + prenom;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnseignantAffichageProjet frame = new EnseignantAffichageProjet();
            frame.setVisible(true);
        });
    }
}
