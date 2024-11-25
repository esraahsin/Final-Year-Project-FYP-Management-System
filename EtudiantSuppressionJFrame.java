import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class EtudiantSuppressionJFrame extends JFrame implements ActionListener {
    // Attributs de la classe
    private JPanel panelPrincipal; // Panel principal
    private JTextField matricule;
    private JButton btnValider, btnAnnuler; // Bouton valider et annuler

    /**
     * Constructeur de la fenetre d'ajout d'un etudiant
     */
    public EtudiantSuppressionJFrame() {
        super("Supprimer un étudiant"); // Titre de la fenetre
        setSize(400, 200); // Taille de la fenetre
        setResizable(false); // La taille est fixe
        setLocationRelativeTo(null); // Position centree
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // fermeture normale
        panelPrincipal = new JPanel(); // Creation du panel principal
        initComposants(); // Initialisation des composants
        getContentPane().setBackground(new Color(54, 69, 79)); // Couleur de fond de l'interface

    }

    /*
     * Methode qui initialise les composants de la fenetre
     */
    private void initComposants() {
        matricule = new JTextField(9); // Champ texte pour le matricule
        matricule.setBackground(new Color(54, 69, 79)); // Couleur de fond du champ texte
        matricule.setForeground(new Color(147, 196, 207)); // Couleur du texte du champ texte

        btnValider = new JButton("Valider"); // Bouton Valider
        btnValider.addActionListener(this); // Ajoute l'evenement apres validation

        btnAnnuler = new JButton("Annuler"); // Bouton Annuler
        btnAnnuler.addActionListener(this); // Ajoute l'evenement apres annulation

        JLabel labelMatricule = new JLabel("MATRICULE");
        labelMatricule.setFont(new Font("Arial", Font.BOLD, 13));
        labelMatricule.setForeground(new Color(147, 196, 207)); // Couleur du texte du label

        FlowLayout flowLayout = new FlowLayout(); // Orientation des elements
        flowLayout.setHgap(10); // Espacement horizontal entre les elements

        panelPrincipal.setLayout(flowLayout); // Defini le layout du panel
        panelPrincipal.add(labelMatricule); // Ajoute le label au panel
        panelPrincipal.add(matricule); // Ajoute le champ au panneau

        getContentPane().add(panelPrincipal, BorderLayout.CENTER); // Ajoute le panel au contenu de la fenetre

        JPanel panelBoutons = new JPanel(); // Panneau contenant les boutons
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.LINE_AXIS)); // Layout en ligne
        panelBoutons.add(Box.createHorizontalGlue()); // Espace libre a gauche
        panelBoutons.add(btnValider); // Ajoute le bouton Valider au panneau
        panelBoutons.add(Box.createRigidArea(new Dimension(20, 20))); // Espace fixe a droite et gauche
        panelBoutons.add(btnAnnuler); // Ajoute le bouton Annuler au panneau

        getContentPane().add(panelBoutons, BorderLayout.SOUTH); // Ajoute le panneau aux sous-fenetres de la fenetre
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource(); // Objet qui a genere l'evenement
        if (source == btnValider) {
            String m = matricule.getText();
            if (!m.equals("")) { // Si les champs ne sont pas vides
                int matricule = Integer.parseInt(m);
                Connection connection;
                try {
                    connection = DatabaseConnector.getConnection();

                    String query = "DELETE FROM Etudiant WHERE idEtudiant=?";

                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setLong(1, matricule);

                    int res = stmt.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "L'étudiant a été supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("L'étudiant a été supprimé!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur! L'étudiant n'a pas pu être supprimé! : Etudiant n'existe pas ", "Succès", JOptionPane.ERROR_MESSAGE);
                    }
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (source == btnAnnuler) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EtudiantSuppressionJFrame frame = new EtudiantSuppressionJFrame();
            frame.setVisible(true);
        });
    }
}
