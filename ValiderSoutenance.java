import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValiderSoutenance extends JFrame implements ActionListener {
    JLabel labelidetudiant, labelnote;
    JTextField textFieldidetudiant, tfnote;
    JButton buttonValider, buttonAnnuler;

    public ValiderSoutenance() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelidetudiant = new JLabel("ID étudiant :");
        labelnote = new JLabel("Note de soutenance : ");
        textFieldidetudiant = new JTextField(10);
        tfnote = new JTextField(10);
        buttonValider = new JButton("Valider");
        buttonAnnuler = new JButton("Annuler");

        JPanel panelGauche = new JPanel();
        JPanel panelDroit = new JPanel();
        JPanel panelBotton = new JPanel();

        setTitle("Validation de la soutenance");
        Container contenu = getContentPane();
        contenu.setLayout(new BorderLayout());

        panelGauche.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelGauche.add(labelidetudiant);
        panelGauche.add(textFieldidetudiant);

        panelDroit.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDroit.add(labelnote);
        panelDroit.add(tfnote);

        panelBotton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotton.add(buttonValider);
        panelBotton.add(buttonAnnuler);

        contenu.add(panelGauche, BorderLayout.NORTH);
        contenu.add(panelDroit, BorderLayout.CENTER);
        contenu.add(panelBotton, BorderLayout.SOUTH);

        buttonValider.addActionListener(this);
        buttonAnnuler.addActionListener(this);

        setSize(450, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == buttonValider) {
            String idText = textFieldidetudiant.getText();
            String noteText = tfnote.getText();

            if (!idText.isEmpty() && !noteText.isEmpty()) {
                int id = Integer.parseInt(idText);
                float note = Float.parseFloat(noteText);
                if (note<0 || note >20)
                JOptionPane.showMessageDialog(this,"Donnez une note dans l'intervalle [0,20] !");

                try {
                    Connection connection = DatabaseConnector.getConnection();
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM soutenance WHERE idetudiant1=" + id + " OR idetudiant2=" + id);
                    boolean soutenanceExists = false;
                    while (rs.next()) {
                        soutenanceExists = true;
                        if (id == rs.getInt("idetudiant1") || id == rs.getInt("idetudiant2")) {
                            if (rs.getObject("note") == null) {
                                String requete = "UPDATE soutenance SET note=" + note + " WHERE idetudiant1=" + id + " OR idetudiant2=" + id;
                                stmt.executeUpdate(requete);
                                JOptionPane.showMessageDialog(null, "Note ajoutée avec succès !");
                            } else {
                                JOptionPane.showMessageDialog(null, "Cette soutenance a déjà une note !");
                            }
                        }
                    }
                    connection.close();
                    rs.close();
                    if (!soutenanceExists) {
                        JOptionPane.showMessageDialog(null, "Soutenance n'existe pas !");
                    }
                    rs.close();
                    stmt.close();
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Problème d'accès à la base" + ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis!");
            }
        } else if (source == buttonAnnuler) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ValiderSoutenance frame = new ValiderSoutenance();
            frame.setVisible(true);
        });
    }
}
