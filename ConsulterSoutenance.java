import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConsulterSoutenance extends JFrame implements ActionListener {
    JTable table;
    JButton btnRechercher, btnAnnuler;
    JCheckBox chkDate, chkProjet;
    JComboBox<String> comboProjet;
    JTextField txtDate;

    public ConsulterSoutenance() {
        super("Consultation les soutenances");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(25, 10, 950, 50);

        chkDate = new JCheckBox("Date (AAAA-MM-JJ HH:MM) :");
        chkDate.setBounds(5, 10, 200, 30);
        txtDate = new JTextField();
        txtDate.setBounds(170, 10, 150, 30);

        chkProjet = new JCheckBox("Projet");
        chkProjet.setBounds(350, 10, 100, 30);
        comboProjet = new JComboBox<>();
        comboProjet.setBounds(460, 10, 150, 30);

        chkDate.addActionListener(this);
        chkProjet.addActionListener(this);

        txtDate.setEnabled(false);
        comboProjet.setEnabled(false);

        searchPanel.add(chkDate);
        searchPanel.add(txtDate);
        searchPanel.add(chkProjet);
        searchPanel.add(comboProjet);

        btnRechercher = new JButton("Rechercher");
        btnRechercher.setBounds(25, 70, 150, 30);
        btnRechercher.addActionListener(this);

        DefaultTableModel sTable = new DefaultTableModel();
        String[] colonnes = { "Nom du projet", "Date ", "Lieu","Note","idPresident","idRapporteur","idExaminateur","idEncadreur","idInvites","idEtudiant1","idEtudiant2" };
        sTable.setColumnIdentifiers(colonnes);

        table = new JTable(sTable);
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(25, 120, 950, 400);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(825, 530, 150, 30);
        btnAnnuler.addActionListener(this);

        getContentPane().setLayout(null);

        getContentPane().add(searchPanel);
        getContentPane().add(btnRechercher);
        getContentPane().add(jsp);
        getContentPane().add(btnAnnuler);

        setVisible(true);
        afficherToutesSoutenances();
        chargerProjets();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRechercher) {
            rechercherSoutenances();
        } else if (e.getSource() == btnAnnuler) {
            dispose();
        } else if (e.getSource() == chkDate) {
            txtDate.setEnabled(chkDate.isSelected());
        } else if (e.getSource() == chkProjet) {
            comboProjet.setEnabled(chkProjet.isSelected());
        }
    }

    private void afficherToutesSoutenances() {
        DefaultTableModel sTable = (DefaultTableModel) table.getModel();

        try {
            Connection connection = DatabaseConnector.getConnection();
            String requete = "SELECT * FROM soutenance";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                if(rs.getLong("note")!=0 &&   rs.getLong("idinvites")!=0 && rs.getLong("idetudiant2")!=0   )
               { Object[] obj = {
                        rs.getString("titre"),
                        rs.getString("date_pfe"),
                        rs.getString("locale"),
                        rs.getString("note"),
                        rs.getLong("idpresident"),
                        rs.getLong("idrapporteur"),
                        rs.getLong("idexaminateur"),
                        rs.getLong("idencadreur"),
                        rs.getLong("idinvites"),
                        rs.getLong("idetudiant1"),
                        rs.getLong("idetudiant2")
                };
                sTable.addRow(obj);}
                else {String n=rs.getString("note"),i=rs.getString("idinvites"),e=rs.getString("idetudiant2");
                    if (rs.getLong("note")==0){
                        n="non evaluée";

                    }
                    if (rs.getLong("idinvites")==0){
                         i ="Non";
                    }
                    if (rs.getLong("idetudiant2")==0){
                        e="Non";
                    }
                    Object[] obj = {
                        rs.getString("titre"),
                        rs.getString("date_pfe"),
                        rs.getString("locale"),
                        n,
                        rs.getLong("idpresident"),
                        rs.getLong("idrapporteur"),
                        rs.getLong("idexaminateur"),
                        rs.getLong("idencadreur"),
                        i,
                        rs.getLong("idetudiant1"),
                        e
                };
                sTable.addRow(obj);
                }
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerProjets() {
        try {
            Connection connection = DatabaseConnector.getConnection();
            String requete = "SELECT * FROM soutenance";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                String projet = rs.getString("titre");
                comboProjet.addItem(projet);
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rechercherSoutenances() {
        DefaultTableModel sTable = (DefaultTableModel) table.getModel();
        sTable.setRowCount(0); // Effacer le contenu actuel du tableau

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement stmt = buildSearchQuery(connection);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] obj = {
                        rs.getString("titre"),
                        rs.getString("date_pfe"),
                        rs.getString("locale"),
                        rs.getString("note"),
                        rs.getLong("idpresident"),
                        rs.getLong("idrapporteur"),
                        rs.getLong("idexaminateur"),
                        rs.getLong("idencadreur"),
                        rs.getLong("idinvites"),
                        rs.getLong("idetudiant1"),
                        rs.getLong("idetudiant2")
                };
                sTable.addRow(obj);
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement buildSearchQuery(Connection connection) throws Exception {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM soutenance");
    
        // Vérifie si au moins une condition est ajoutée
        boolean conditionsAdded = false;
    
        if (chkDate.isSelected()) {
            String date = txtDate.getText().trim();
            if (!date.isEmpty()) {
                queryBuilder.append(conditionsAdded ? " AND " : " WHERE ");
                queryBuilder.append("date_pfe = ?");
                conditionsAdded = true;
            }
        }
    
        if (chkProjet.isSelected()) {
            String projet = comboProjet.getSelectedItem().toString().trim();
            if (!projet.isEmpty()) {
                queryBuilder.append(conditionsAdded ? " AND " : " WHERE ");
                queryBuilder.append("titre = ?");
                conditionsAdded = true;
            }
        }
    
        PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());
    
        int parameterIndex = 1;
    
        if (chkDate.isSelected()) {
            String date = txtDate.getText().trim();
            if (!date.isEmpty()) {
                stmt.setString(parameterIndex++, date);
            }
        }
    
        if (chkProjet.isSelected()) {
            String projet = comboProjet.getSelectedItem().toString().trim();
            if (!projet.isEmpty()) {
                stmt.setString(parameterIndex, projet);
            }
        }
    
        return stmt;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsulterSoutenance frame = new ConsulterSoutenance();
            frame.setVisible(true);
        });
    }
}
