import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SoutenanceModifierFrame extends JFrame {

    private JTextField textFieldTitre, textFieldDate, textFieldLocale, textFieldNote,
            textFieldIdPresident, textFieldIdRapporteur, textFieldIdExaminateur,
            textFieldIdEncadreur, textFieldIdInvites, textFieldIdEtudiant1, textFieldIdEtudiant2;

    public SoutenanceModifierFrame() {
        super("Modifier Soutenance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(12, 2, 5, 5));

        mainPanel.add(new JLabel("Titre :"));
        textFieldTitre = new JTextField(10);
        mainPanel.add(textFieldTitre);

        mainPanel.add(new JLabel("Date (AAAA-MM-JJ HH:MM:SS) :"));
        textFieldDate = new JTextField(10);
        mainPanel.add(textFieldDate);

        mainPanel.add(new JLabel("Locale :"));
        textFieldLocale = new JTextField(10);
        mainPanel.add(textFieldLocale);

        mainPanel.add(new JLabel("Note :"));
        textFieldNote = new JTextField(10);
        mainPanel.add(textFieldNote);

        mainPanel.add(new JLabel("ID Président :"));
        textFieldIdPresident = new JTextField(10);
        mainPanel.add(textFieldIdPresident);

        mainPanel.add(new JLabel("ID Rapporteur :"));
        textFieldIdRapporteur = new JTextField(10);
        mainPanel.add(textFieldIdRapporteur);

        mainPanel.add(new JLabel("ID Examinateur :"));
        textFieldIdExaminateur = new JTextField(10);
        mainPanel.add(textFieldIdExaminateur);

        mainPanel.add(new JLabel("ID Encadreur :"));
        textFieldIdEncadreur = new JTextField(10);
        mainPanel.add(textFieldIdEncadreur);

        mainPanel.add(new JLabel("ID Invités :"));
        textFieldIdInvites = new JTextField(10);
        mainPanel.add(textFieldIdInvites);

        mainPanel.add(new JLabel("ID Étudiant 1  :"));
        textFieldIdEtudiant1 = new JTextField(10);
        mainPanel.add(textFieldIdEtudiant1);

        mainPanel.add(new JLabel("ID Étudiant 2 :"));
        textFieldIdEtudiant2 = new JTextField(10);
        mainPanel.add(textFieldIdEtudiant2);

        JButton modifierButton = new JButton("Modifier");
        JButton annulerButton = new JButton("Annuler");

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierSoutenance();
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

    private void modifierSoutenance() {
        String titre = textFieldTitre.getText().trim();
        String date = textFieldDate.getText().trim();
        String locale = textFieldLocale.getText().trim();
        String noteText = textFieldNote.getText().trim();
        String idPresidentText = textFieldIdPresident.getText().trim();
        String idRapporteurText = textFieldIdRapporteur.getText().trim();
        String idExaminateurText = textFieldIdExaminateur.getText().trim();
        String idEncadreurText = textFieldIdEncadreur.getText().trim();
        String idInvitesText = textFieldIdInvites.getText().trim();
        String idEtudiant1Text = textFieldIdEtudiant1.getText().trim();
        String idEtudiant2Text = textFieldIdEtudiant2.getText().trim();

        if (idEtudiant1Text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir l'ID de l'étudiant !");
            return;
        }

        int idEtudiant1 = Integer.parseInt(idEtudiant1Text);

        if (titre.isEmpty() && date.isEmpty() && locale.isEmpty() && noteText.isEmpty() &&
                idPresidentText.isEmpty() && idRapporteurText.isEmpty() && idExaminateurText.isEmpty() &&
                idEncadreurText.isEmpty() && idInvitesText.isEmpty() && idEtudiant2Text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune modification n'a été apportée !");
            return;
        }

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Soutenance SET titre = ?, date_pfe = ?, locale = ?, note = ?, " +
                            "idPresident = ?, idRapporteur = ?, idExaminateur = ?, " +
                            "idEncadreur = ?, idInvites = ?, idEtudiant2 = ? WHERE idEtudiant1 = ?");
            stmt.setString(1, titre);
            stmt.setString(2, date);
            stmt.setString(3, locale);
            stmt.setString(4, noteText);
            stmt.setString(5, idPresidentText);
            stmt.setString(6, idRapporteurText);
            stmt.setString(7, idExaminateurText);
            stmt.setString(8, idEncadreurText);
            stmt.setString(9, idInvitesText);
            stmt.setString(10, idEtudiant2Text);
            stmt.setInt(11, idEtudiant1);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Soutenance modifiée avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Aucune soutenance trouvée avec cet ID d'étudiant !");
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la soutenance : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SoutenanceModifierFrame().setVisible(true);
        });
    }
}
