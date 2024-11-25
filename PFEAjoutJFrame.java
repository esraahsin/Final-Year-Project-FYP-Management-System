import java.awt.*;
import java.awt.event.*;
import java.sql.*;


import javax.swing.*;

public class PFEAjoutJFrame extends JFrame implements ActionListener {
    JTextField tftitre ,tflieu;
    JButton ajouterButton,annuler;
    JComboBox<String> EncadreursBox;
     public PFEAjoutJFrame() throws SQLException {
        initFenetre();
        setContenuPanels();
    }

    private void initFenetre() {
        setTitle("Ajouter un Projet ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void setContenuPanels() throws SQLException {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titreLabel = new JLabel("Titre : ");
        JLabel lieuLabel = new JLabel("Locale : ");
        JLabel encadreurLabel = new JLabel("Encadreur : ");

         tftitre = new JTextField(20);
        tflieu = new JTextField(10);
        
        EncadreursBox = new JComboBox<>();
        
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select idenseignant,nom,prenom from enseignant ");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            String nom =rs.getString("nom");
            String prenom = rs.getString("prenom");
            String id= rs.getString("idenseignant");
            EncadreursBox.addItem(id+":"+prenom + " " + nom);
        }
        rs.close();
        stmt.close();
        connection.close();
        JButton ajouterButton = new JButton("Ajouter le Projet");
        ajouterButton.addActionListener(this);
        annuler = new JButton("Annuler");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(titreLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(tftitre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(lieuLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(tflieu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(encadreurLabel, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(EncadreursBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelPrincipal.add(ajouterButton, gbc);
       
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelPrincipal.add(annuler, gbc);
        add(panelPrincipal); setTitle("Ajouter un Projet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            if (e.getActionCommand().equals("Ajouter le Projet")) {
                String titre=tftitre.getText();
                String lieu =tflieu.getText();
                
                String encadreur  = (String) EncadreursBox.getSelectedItem();
                String[] iden= encadreur.split(":");
                int ide=Integer.parseInt(iden[0]);

                if (titre.isEmpty() || lieu.isEmpty() ) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                    return;
                }
                try {
                    Connection connection = DatabaseConnector.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("insert into pfe values(?,?,?,?)");                 
                    stmt.setString(1, titre);
                    stmt.setString(2,lieu);
                    stmt.setLong(3,ide );  
                    stmt.setString(4, iden[1]);
                    int nbLignesInserrees =stmt.executeUpdate();
                    
                    if (nbLignesInserrees > 0){connection.close();

                    JOptionPane.showMessageDialog(this, "Le Projet  a été ajouté avec succès !");}
                    else{JOptionPane.showMessageDialog(this,"Une erreur est survenue lors de l'insertion du projet.");
                }
                    
            }catch (SQLException e2) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'ajout de Projet : " + e2.getMessage());
                }
                tflieu.setText("");
                tftitre.setText("");
                EncadreursBox.setSelectedIndex(0); // Réinitialiser la sélection de la spécialité
            }
        }
    
}
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        PFEAjoutJFrame frame;
        try {
            frame = new PFEAjoutJFrame();        frame.setVisible(true);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    });
}

}
