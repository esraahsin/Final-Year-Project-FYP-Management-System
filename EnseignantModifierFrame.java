import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnseignantModifierFrame extends JFrame {

    private JTextField textFieldId, textFieldNom, textFieldPrenom, textFieldSpecialite;

    public EnseignantModifierFrame() {
        super("Modifier Enseignant");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        mainPanel.add(new JLabel("ID :"));
        textFieldId = new JTextField(10);
        mainPanel.add(textFieldId);

        mainPanel.add(new JLabel("Nom :"));
        textFieldNom = new JTextField(10);
        mainPanel.add(textFieldNom);

        mainPanel.add(new JLabel("Prénom :"));
        textFieldPrenom = new JTextField(10);
        mainPanel.add(textFieldPrenom);

        mainPanel.add(new JLabel("Spécialité :"));
        textFieldSpecialite = new JTextField(10);
        mainPanel.add(textFieldSpecialite);

        JButton modifierButton = new JButton("Modifier");
        JButton annulerButton = new JButton("Annuler");

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierEnseignant();
            }
        });

        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        mainPanel.add(modifierButton);
        mainPanel.add(annulerButton);

        add(mainPanel);
    }

    private void modifierEnseignant() {
        String idText = textFieldId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir l'ID de l'enseignant !");
            return;
        }

        int id = Integer.parseInt(idText);
        String nom = textFieldNom.getText().trim();
        String prenom = textFieldPrenom.getText().trim();
        String specialite = textFieldSpecialite.getText().trim();

        if (nom.isEmpty() && prenom.isEmpty() && specialite.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune modification n'a été apportée !");
            return;
        }

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Enseignant SET nom = ?, prenom = ?, specialite = ? WHERE idEnseignant = ?");
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, specialite);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Enseignant modifié avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Aucun enseignant trouvé avec cet ID !");
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'enseignant : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnseignantModifierFrame().setVisible(true);
        });
    }
}
