import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConsulterProjet extends JFrame implements ActionListener {
    JTable table;
    JButton btnAnnuler;

    public ConsulterProjet() {
        super("Consultation des projets");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        DefaultTableModel projetTable = new DefaultTableModel();
        String[] colonnes = { "Nom du projet", "Encadreur ", "Lieu" };
        projetTable.setColumnIdentifiers(colonnes);

        try {
            Connection connection = DatabaseConnector.getConnection();
            String requete = "SELECT titre, encadreur, locale FROM pfe ";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                Object[] obj = { rs.getString("titre"), rs.getString("encadreur"), rs.getString("locale") };
                projetTable.addRow(obj);
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable(projetTable);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(25, 35, 950, 400);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(425, 470, 150, 50);
        btnAnnuler.addActionListener(this);

        getContentPane().setLayout(null); // Utilisation d'un layout null pour pouvoir définir les positions des composants manuellement

        getContentPane().add(jsp);
        getContentPane().add(btnAnnuler);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAnnuler)) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsulterProjet frame = new ConsulterProjet();
            frame.setVisible(true);
        });
    }
}
