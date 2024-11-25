import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class EtudiantRechercheJFrame extends JFrame {
    JTextField matriculefield;
    JButton cherchebouton, annulerbouton;

    public EtudiantRechercheJFrame() {
        setTitle("Recherche d'un étudiant");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        matriculefield = new JTextField(20);
        cherchebouton = new JButton("Chercher");
        annulerbouton = new JButton("Annuler");

        JPanel panelGauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Matricule : ");
        panelGauche.add(label);
        panelGauche.add(matriculefield);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoutons.add(cherchebouton);
        panelBoutons.add(annulerbouton);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(panelGauche, BorderLayout.CENTER);
        contentPane.add(panelBoutons, BorderLayout.SOUTH);

        setContentPane(contentPane);

        cherchebouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String matricule = matriculefield.getText().trim();
                if (!matricule.isEmpty()) {
                    int m = Integer.parseInt(matricule);
                    try {
                        Connection connection = DatabaseConnector.getConnection();
                        String query = "SELECT * FROM Etudiant WHERE idEtudiant=?";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setInt(1, m);
                        ResultSet resultat = stmt.executeQuery();
                        if (!resultat.next()) {
                            JOptionPane.showMessageDialog(null, "Aucun étudiant trouvé avec ce numéro de matricule",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String nom = resultat.getString("Nom");
                            String prenom = resultat.getString("Prenom");
                            String specialite = resultat.getString("Specialite");
                            JOptionPane.showMessageDialog(null,
                                    "L'étudiant d'id " + m + " s'appelle " + prenom + " " + nom + " spécialisé "
                                            + specialite);
                        }
                    } catch (SQLException ex) {
                        System.err.println("Erreur SQL : " + ex.getMessage());
                    }

                } else
                    JOptionPane.showMessageDialog(null, "Le champ ne doit pas être vide", "ERREUR",
                            JOptionPane.ERROR_MESSAGE);

            }
        });
        annulerbouton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
            
        });
    }

   
}
