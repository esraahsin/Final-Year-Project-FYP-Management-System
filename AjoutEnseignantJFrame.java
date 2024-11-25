import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AjoutEnseignantJFrame extends JFrame implements ActionListener {
    private JPanel panelPrincipal;
    private JTextField tfNom, tfPrenom, tfMatricule;
    JButton annuler;
    public AjoutEnseignantJFrame() {
        initFenetre();
        setContenuPanels();
    }

    private void initFenetre() {
        setTitle("Ajouter un enseignant");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void setContenuPanels() {
        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nomLabel = new JLabel("Nom : ");
        JLabel prenomLabel = new JLabel("Prénom : ");
        JLabel matriculeLabel = new JLabel("Matricule : ");

        tfNom = new JTextField(20);
        tfPrenom = new JTextField(20);
        tfMatricule = new JTextField(10);

        JButton ajouterButton = new JButton("Ajouter l'enseignant");
        ajouterButton.addActionListener(this);
        JButton annuler=new JButton("Annuler");
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
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
        gbc.gridwidth = 2;
        panelPrincipal.add(ajouterButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelPrincipal.add(annuler, gbc);

        add(panelPrincipal);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            if (e.getActionCommand().equals("Ajouter l'enseignant")) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String matricule = tfMatricule.getText();

                if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                    return;
                }

                int m = Integer.parseInt(matricule);
                Encadreur enc = new Encadreur(nom, prenom, m);

                try {
                    enc.ajouterEnseignant();
                    JOptionPane.showMessageDialog(this, "L'enseignant a été ajouté avec succès !");
                } catch (EtudiantDejaPresentException e1) {
                    JOptionPane.showMessageDialog(this, "Erreur : L'enseignant existe déjà dans la base de données");
                } catch (SQLException e2) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'ajout de l'enseignant : " + e2.getMessage());
                }

                tfNom.setText("");
                tfPrenom.setText("");
                tfMatricule.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AjoutEnseignantJFrame frame = new AjoutEnseignantJFrame();
            frame.setVisible(true);
        });
    }
}
