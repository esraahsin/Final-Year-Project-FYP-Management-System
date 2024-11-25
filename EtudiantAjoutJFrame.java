import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EtudiantAjoutJFrame extends JFrame implements ActionListener {
    private JPanel panelPrincipal;
    private JTextField tfNom, tfPrenom, tfMatricule;
    private JComboBox<String> cbSpecialite; // Liste déroulante pour la spécialité
    JButton annuler ;
    JButton ajouterButton;
    public EtudiantAjoutJFrame() {
        initFenetre();
        setContenuPanels();
    }

    private void initFenetre() {
        setTitle("Ajouter un étudiant");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(54, 69, 79)); // Couleur de fond de l'interface
    }

    private void setContenuPanels() {
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(54, 69, 79)); // Couleur de fond du panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nomLabel = new JLabel("Nom : ");
        JLabel prenomLabel = new JLabel("Prénom : ");
        JLabel matriculeLabel = new JLabel("Matricule : ");
        JLabel specialiteLabel = new JLabel("Spécialité : ");

        // Définir la couleur du texte des labels
        Color labelTextColor = new Color(147, 196, 207);
        nomLabel.setForeground(labelTextColor);
        prenomLabel.setForeground(labelTextColor);
        matriculeLabel.setForeground(labelTextColor);
        specialiteLabel.setForeground(labelTextColor);

        tfNom = new JTextField(20);
        tfPrenom = new JTextField(20);
        tfMatricule = new JTextField(10);

        // Liste déroulante pour la spécialité
        String[] specialites = {"Licence: Informatique", "Licence: Électronique", "Mastère: Recherche", "Mastère: Professionnel", "Ingénieur: Informatique", "Ingénieur: Électronique"};
        cbSpecialite = new JComboBox<>(specialites);

        ajouterButton = new JButton("Ajouter l'étudiant");
        ajouterButton.addActionListener(this);
         annuler = new JButton("Annuler");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(nomLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(tfNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(prenomLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(tfPrenom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(matriculeLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(tfMatricule, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(specialiteLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(cbSpecialite, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelPrincipal.add(ajouterButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelPrincipal.add(annuler, gbc);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // Appliquer la couleur de fond aux champs de texte
        tfNom.setBackground(new Color(54, 69, 79));
        tfPrenom.setBackground(new Color(54, 69, 79));
        tfMatricule.setBackground(new Color(54, 69, 79));

        // Appliquer la couleur de la bordure aux champs de texte
        tfNom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 196, 207)), // Bordure de couleur
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Marge intérieure
        ));
        tfPrenom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 196, 207)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        tfMatricule.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 196, 207)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        add(panelPrincipal);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() instanceof JButton) {
            if (e.getActionCommand().equals("Ajouter l'étudiant")) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String matricule = tfMatricule.getText();
                String specialite = (String) cbSpecialite.getSelectedItem();

                if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                    return;
                }

                int m = Integer.parseInt(matricule);
                Etudiant etudiant = new Etudiant(nom, prenom, m, specialite);

                try {
                    etudiant.ajouterEtudiant();
                    JOptionPane.showMessageDialog(this, "L'étudiant a été ajouté avec succès !");
                } catch (EtudiantDejaPresentException e1) {
                    JOptionPane.showMessageDialog(this, "Erreur : L'étudiant existe déjà dans la base de données");
                } catch (SQLException e2) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'ajout de l'étudiant : " + e2.getMessage());
                }

                tfNom.setText("");
                tfPrenom.setText("");
                tfMatricule.setText("");
                cbSpecialite.setSelectedIndex(0); // Réinitialiser la sélection de la spécialité
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EtudiantAjoutJFrame frame = new EtudiantAjoutJFrame();
            frame.setVisible(true);
        });
    }
}
