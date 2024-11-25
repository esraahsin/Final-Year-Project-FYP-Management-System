import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class SupprimerProjet extends JFrame implements ActionListener {
    JLabel labeltitre;
    JComboBox<String> titre;
    JButton valider, annuler;

    public SupprimerProjet() {
        setTitle("Suppression d'un projet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 200);
        setResizable(false);

        // Creation des composants graphiques
        labeltitre = new JLabel("Choisir le projet à supprimer : ");
        titre = new JComboBox<String>();
        chargerTitres(); // Charger les titres initiaux
        valider = new JButton("Valider");
        annuler = new JButton("Annuler");
        valider.addActionListener(this);
        annuler.addActionListener(this);

        // Création du groupe de mise en page
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Configuration du groupe de mise en page
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Définition des composants horizontaux
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(labeltitre)
                .addComponent(valider));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(titre)
                .addComponent(annuler));
        layout.setHorizontalGroup(hGroup);

        // Définition des composants verticaux
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labeltitre)
                .addComponent(titre));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(valider)
                .addComponent(annuler));
        layout.setVerticalGroup(vGroup);

        pack(); // Ajuster la taille de la fenêtre pour s'adapter aux composants

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == valider) {
            String choix = (String) titre.getSelectedItem();
            try {
                Connection connection = DatabaseConnector.getConnection();

                String requeteDelete = "DELETE FROM Pfe WHERE Titre=?";
                PreparedStatement psDelete = connection.prepareStatement(requeteDelete);
                psDelete.setString(1, choix);
                int nbLignesDelete = psDelete.executeUpdate();
                if (nbLignesDelete > 0)
                    JOptionPane.showMessageDialog(null, "Projet a été supprimé avec succès");
                else {
                    JOptionPane.showMessageDialog(null, "Erreur de Suppression!");
                }
                psDelete.close();
                connection.close();

                // Réinitialiser le JComboBox après la suppression
                chargerTitres();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == annuler) {
            dispose(); // Fermer la fenêtre lorsque le bouton Annuler est cliqué
        }
    }

    // Méthode pour charger les titres dans le JComboBox
    private void chargerTitres() {
        titre.removeAllItems(); // Supprimer tous les éléments actuels du JComboBox
        try {
            Connection connection = DatabaseConnector.getConnection();
            String requete = "SELECT Titre FROM Pfe";
            PreparedStatement pst = connection.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                titre.addItem(rs.getString("Titre"));
            }
            rs.close();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SupprimerProjet frame = new SupprimerProjet();
            frame.setVisible(true);
        });
    }
}
