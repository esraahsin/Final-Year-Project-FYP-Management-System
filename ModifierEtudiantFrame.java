import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class ModifierEtudiantFrame extends JFrame {
    private JTextField textFieldId;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldSpecialite;

    public ModifierEtudiantFrame() {
        super("Modifier Étudiant");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("ID Étudiant:"));
        textFieldId = new JTextField(10);
        panel.add(textFieldId);

        panel.add(new JLabel("Nom:"));
        textFieldNom = new JTextField(10);
        panel.add(textFieldNom);

        panel.add(new JLabel("Prénom:"));
        textFieldPrenom = new JTextField(10);
        panel.add(textFieldPrenom);

        panel.add(new JLabel("Spécialité:"));
        textFieldSpecialite = new JTextField(10);
        panel.add(textFieldSpecialite);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifierEtudiant();
            }
        });
        panel.add(btnModifier);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnAnnuler);

        add(panel);
    }

    private void modifierEtudiant() {
        String idText = textFieldId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir l'ID de l'étudiant !");
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
                    "UPDATE ETUDIANT SET nom = ?, prenom = ?, specialite = ? WHERE idEtudiant = ?");
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, specialite);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Aucun étudiant trouvé avec cet ID !");
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'étudiant : " + ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModifierEtudiantFrame().setVisible(true);
        });
    }
}
