import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


public class Main extends JFrame {
    private boolean sidebarVisible = true;
    JPanel sidebar;

    public Main() {
        // Créer le panneau de la barre latérale
        sidebar = new JPanel();
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setLayout(new GridLayout(0, 1)); // Layout vertical

        // Créer les catégories pour les éléments de menu
        /*JLabel labelEtudiant = new JLabel("Gestion des étudiants");
        labelEtudiant.setFont(new Font("Arial", Font.BOLD, 14));
        sidebar.add(labelEtudiant);*/

        JButton btnAjouterEtudiant = new JButton("Ajouter un étudiant");
        JButton btnModifierEtudiant = new JButton("Modifier un étudiant");
        JButton btnSupprimerEtudiant = new JButton("Supprimer un étudiant");
        JButton btnRechercherEtudiant = new JButton("Rechercher un étudiant");
        JButton btnAfficherEtudiants = new JButton("Afficher les étudiants");

        Color btnColor = new Color(147,196,207); // #77B5FE
        btnAjouterEtudiant.setBackground(btnColor);
        btnSupprimerEtudiant.setBackground(btnColor);
        btnRechercherEtudiant.setBackground(btnColor);
        btnAfficherEtudiants.setBackground(btnColor);
        btnModifierEtudiant.setBackground(btnColor);
        
        // Add mouse listener for hover and mouse out behavior
        btnAjouterEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterEtudiant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterEtudiant.setBackground(btnColor); // #C13D5F
            }
        });
        btnModifierEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierEtudiant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierEtudiant.setBackground(btnColor); // #C13D5F
            }
        });
        btnSupprimerEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSupprimerEtudiant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerEtudiant.setBackground(btnColor); // #C13D5F
            }
        });

        btnRechercherEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRechercherEtudiant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRechercherEtudiant.setBackground(btnColor); // #C13D5F
            }
        });

        btnAfficherEtudiants.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherEtudiants.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherEtudiants.setBackground(btnColor); // #C13D5F
            }
        });


        sidebar.add(btnAjouterEtudiant);
        sidebar.add(btnModifierEtudiant);
        sidebar.add(btnSupprimerEtudiant);
        sidebar.add(btnRechercherEtudiant);
        sidebar.add(btnAfficherEtudiants);

        

        // Ajouter les boutons pour la gestion des enseignants
        JButton btnAjouterEnseignant = new JButton("Ajouter un enseignant");
        JButton btnModifierEnseignant = new JButton("Modifier un enseignant");

        JButton btnAfficherProjet = new JButton("Afficher les projets");
        JButton btnAfficherEnseignants = new JButton("Afficher les enseignants");
        JButton btnSupprimerEnseignant = new JButton("Supprimer un enseignant");

        btnAjouterEnseignant.setBackground(btnColor);
        btnAfficherProjet.setBackground(btnColor);
        btnAfficherEnseignants.setBackground(btnColor);
        btnSupprimerEnseignant.setBackground(btnColor);
        btnModifierEnseignant.setBackground(btnColor);
        // Add mouse listener for hover and mouse out behavior
        btnAjouterEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterEnseignant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterEnseignant.setBackground(btnColor); // #C13D5F
            }
        });
        btnModifierEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierEnseignant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierEnseignant.setBackground(btnColor); // #C13D5F
            }
        });
        btnAfficherProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherProjet.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherProjet.setBackground(btnColor); // #C13D5F
            }
        });

        btnAfficherEnseignants.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherEnseignants.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherEnseignants.setBackground(btnColor); // #C13D5F
            }
        });

        btnSupprimerEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSupprimerEnseignant.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerEnseignant.setBackground(btnColor); // #C13D5F
            }
        });


        sidebar.add(btnAjouterEnseignant);
        sidebar.add(btnModifierEnseignant);
        sidebar.add(btnAfficherProjet);
        sidebar.add(btnAfficherEnseignants);
        sidebar.add(btnSupprimerEnseignant);

        /*JLabel labelPFE = new JLabel("Gestion des projets de fin d'étude");
        labelPFE.setFont(new Font("Arial", Font.BOLD, 14));
        sidebar.add(labelPFE);*/

        // Ajouter les boutons pour la gestion des projets de fin d'étude
        JButton btnAjouterProjet = new JButton("Ajouter un projet");
        JButton btnAffecterProjet = new JButton("Affecter un projet à un/deux étudiant(s)");
        JButton btnValiderProjet = new JButton("Valider une soutenance");
        JButton btnConsulterProjet = new JButton("Consulter les projets");
        JButton btnConsulterSoutenance = new JButton("Consulter les soutenances");
        JButton btnSupprimerProjet = new JButton("Supprimer un projet");

        // Set initial button color
        btnAjouterProjet.setBackground(btnColor);
        btnAffecterProjet.setBackground(btnColor);
        btnValiderProjet.setBackground(btnColor);
        btnConsulterProjet.setBackground(btnColor);
        btnConsulterSoutenance.setBackground(btnColor);
        JButton btnModifierSoutenance = new JButton("Modifier une soutenance");
        btnModifierSoutenance.setBackground(btnColor);
        btnSupprimerProjet.setBackground(btnColor);
        btnModifierSoutenance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierSoutenance.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierSoutenance.setBackground(btnColor); // #C13D5F
            }
        });
        // Add mouse listener for hover and mouse out behavior
        btnAjouterProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterProjet.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterProjet.setBackground(btnColor); // #C13D5F
            }
        });

        btnAffecterProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAffecterProjet.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAffecterProjet.setBackground(btnColor); // #C13D5F
            }
        });

        btnValiderProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnValiderProjet.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnValiderProjet.setBackground(btnColor); // #C13D5F
            }
        });

        btnConsulterProjet.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConsulterProjet.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnConsulterProjet.setBackground(btnColor); // #C13D5F
            }
        });

        btnConsulterSoutenance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConsulterSoutenance.setBackground(new Color( 187, 218, 254 ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnConsulterSoutenance.setBackground(btnColor); // #C13D5F
            }
        });

        btnSupprimerProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                 // set background color :#BBDAFE

                btnSupprimerProjet.setBackground(new Color( 187, 218, 254 ));


            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerProjet.setBackground(btnColor); // #C13D5F
            }
        });

        // Zone de contenu principal
        JPanel content = new JPanel();
        content.setBackground(new Color(54, 69, 79));

        JLabel imageLabel = new JLabel();

        ImageIcon imageIcon = new ImageIcon("6189813_3181113.jpg");        
        imageLabel.setIcon(imageIcon);

       // content.add(imageLabel);

        // Créer un split pane pour contenir la barre latérale et le contenu principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, content);
        splitPane.setDividerLocation(200);

        // Ajouter des écouteurs d'événements pour les boutons de la barre latérale
        btnAjouterEtudiant.addActionListener(e -> ajouterEtudiant());
        btnSupprimerEtudiant.addActionListener(e -> supprimerEtudiant());
        btnRechercherEtudiant.addActionListener(e -> rechercherEtudiant());
        btnAfficherEtudiants.addActionListener(e -> afficherEtudiants());
        btnModifierEtudiant.addActionListener(e -> modifierEtudiant());

        btnAjouterEnseignant.addActionListener(e -> ajouterEnseignant());
        btnAfficherProjet.addActionListener(e -> afficherProjet());
        btnAfficherEnseignants.addActionListener(e -> afficherEnseignants());
        btnSupprimerEnseignant.addActionListener(e -> supprimerEnseignant());
        btnModifierEnseignant.addActionListener(e -> modifierEnseignant());
        btnModifierSoutenance.addActionListener(e -> modifierSoutenance());

        btnAjouterProjet.addActionListener(e -> ajouterProjet());
        btnAffecterProjet.addActionListener(e -> affecterProjet());
        btnValiderProjet.addActionListener(e -> validerProjet());
        btnConsulterProjet.addActionListener(e -> consulterProjet());
        btnConsulterSoutenance.addActionListener(e -> consulterSoutenance());
        btnSupprimerProjet.addActionListener(e -> supprimerProjet());
        Font defaultFont = new Font("Arial", Font.BOLD, 13);
        addHoverEffect(btnAjouterEtudiant);
        addHoverEffect(btnModifierSoutenance);
        addHoverEffect(btnModifierEnseignant);
        addHoverEffect(btnModifierEtudiant);

        addHoverEffect(btnSupprimerEtudiant);
        addHoverEffect(btnRechercherEtudiant);
        addHoverEffect(btnAfficherEtudiants);
        addHoverEffect(btnAjouterEnseignant);
        addHoverEffect(btnAfficherProjet);
        addHoverEffect(btnAfficherEnseignants);
        addHoverEffect(btnSupprimerEnseignant);
        addHoverEffect(btnAjouterProjet);
        addHoverEffect(btnAffecterProjet);
        addHoverEffect(btnValiderProjet);
        addHoverEffect(btnConsulterProjet);
        addHoverEffect(btnConsulterSoutenance);
        addHoverEffect(btnSupprimerProjet);
        btnModifierEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierEtudiant.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierEtudiant.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });
        btnModifierSoutenance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierSoutenance.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierSoutenance.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });
        btnModifierEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModifierEnseignant.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModifierEnseignant.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });

                btnAjouterEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterEtudiant.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterEtudiant.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });

        btnSupprimerEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSupprimerEtudiant.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerEtudiant.setFont(defaultFont);
            }
        });

        btnRechercherEtudiant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRechercherEtudiant.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRechercherEtudiant.setFont(defaultFont);
            }
        });

        btnAfficherEtudiants.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherEtudiants.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherEtudiants.setFont(defaultFont);
            }
        });
        btnAjouterEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterEnseignant.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterEnseignant.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });

        btnAfficherProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherProjet.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherProjet.setFont(defaultFont);
            }
        });

        btnAfficherEnseignants.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAfficherEnseignants.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAfficherEnseignants.setFont(defaultFont);
            }
        });

        btnSupprimerEnseignant.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSupprimerEnseignant.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerEnseignant.setFont(defaultFont);
            }
        });
        btnAjouterProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAjouterProjet.setFont(new Font("Arial", Font.BOLD, 15)); // Augmenter la taille de la police
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAjouterProjet.setFont(defaultFont); // Revenir à la taille de police par défaut
            }
        });

        btnAffecterProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAffecterProjet.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAffecterProjet.setFont(defaultFont);
            }
        });

        btnValiderProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnValiderProjet.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnValiderProjet.setFont(defaultFont);
            }
        });

        btnConsulterProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConsulterProjet.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnConsulterProjet.setFont(defaultFont);
            }
        });

        btnConsulterSoutenance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConsulterSoutenance.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnConsulterSoutenance.setFont(defaultFont);
            }
        });

        btnSupprimerProjet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSupprimerProjet.setFont(new Font("Arial", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSupprimerProjet.setFont(defaultFont);
            }
        });

sidebar.add(btnAjouterProjet);
sidebar.add(btnModifierSoutenance);
        sidebar.add(btnAffecterProjet);
        sidebar.add(btnValiderProjet);
        sidebar.add(btnConsulterProjet);
        sidebar.add(btnConsulterSoutenance);
        sidebar.add(btnSupprimerProjet);
       /*  JScrollPane sidebarScrollPane = new JScrollPane(sidebar);
        sidebarScrollPane.setPreferredSize(new Dimension(200, 600));

        // Zone de contenu principal
       
        // Créer un split pane pour contenir le JScrollPane de la barre latérale et le contenu principal
       splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,content);
        splitPane.setDividerLocation(200);*/
        btnAjouterEtudiant.setBorderPainted(false);
        btnModifierEnseignant.setBorderPainted(false);
        btnModifierEtudiant.setBorderPainted(false);
        btnModifierSoutenance.setBorderPainted(false);
        btnSupprimerEtudiant.setBorderPainted(false);
        btnRechercherEtudiant.setBorderPainted(false);
        btnAfficherEtudiants.setBorderPainted(false);
        btnAjouterEnseignant.setBorderPainted(false);
        btnAfficherProjet.setBorderPainted(false);
        btnAfficherEnseignants.setBorderPainted(false);
        btnSupprimerEnseignant.setBorderPainted(false);
        btnAjouterProjet.setBorderPainted(false);
        btnAffecterProjet.setBorderPainted(false);
        btnValiderProjet.setBorderPainted(false);
        btnConsulterProjet.setBorderPainted(false);
        btnConsulterSoutenance.setBorderPainted(false);
        btnSupprimerProjet.setBorderPainted(false);
        
//*******************************************************************
        JLabel titre = new JLabel("Gestion des PFEs");
                //ajoutez deux jlabel pour le nom de l etudiant qui fait le projet et le  nom de l encadreur    
                JLabel etu= new JLabel("Etudiante :");
                JLabel p= new JLabel("Encadré par :");
                etu.setFont(new Font("Arial", Font.BOLD, 18));
                etu.setForeground(btnColor);
                etu.setBounds(250, 380, 100, 100);
                p.setFont(new Font("Arial", Font.BOLD, 18));
                p.setForeground(btnColor);
                p.setBounds(650, 380, 130, 100);
        JLabel i= new JLabel("Isra Hsin");
        JLabel c= new JLabel("M Radhwane Chaka");
        i.setFont(new Font("Arial", Font.BOLD, 19));
        i.setForeground(Color.WHITE);
        i.setBounds(250, 430, 100, 100);
        c.setFont(new Font("Arial", Font.BOLD, 19));
        c.setForeground(Color.WHITE);
        c.setBounds(620, 430, 260, 100);
        add(c) ;
        add(i) ;
        

        titre.setOpaque(true); // Définir le fond comme opaque
        //ajouter label titre sur l image 
        titre.setHorizontalAlignment(JLabel.CENTER); // Centrer le texte


           Border border = new LineBorder(new Color(214, 219, 224), 2, true); // Bordure de 2 pixels de large
           Border roundedBorder = new CompoundBorder(border, new LineBorder(btnColor, 2, true)); // Bordure intérieure blanche pour donner l'illusion de coins arrondis
           titre.setBorder(roundedBorder);
        titre.setFont(new Font("Arial", Font.BOLD, 30));
        titre.setForeground(btnColor);
        titre.setBounds(310, 200, 400, 100);
        titre.add(new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int radius = 20; // Rayon des coins arrondis
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground()); // Utiliser la même couleur de fond que le JLabel
                g2d.fillRoundRect(0, 0, width - 1, height - 1, radius, radius);
                g2d.dispose();
            }
        });
        // Ajouter le laKObel à la JFrame

        add(titre);
        add(etu);
        add(p);
        splitPane.setDividerSize(0);


       
        add(splitPane);

        revalidate();
        repaint();
        setTitle("Main Frame");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        Image image = imageIcon.getImage();

Image scaledImage = image.getScaledInstance(1000, 800, Image.SCALE_SMOOTH);

ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

imageLabel.setIcon(scaledImageIcon);
    }

    // Implémentations des méthodes pour les actions des boutons de la barre latérale
    private void ajouterEtudiant() {
        EtudiantAjoutJFrame frame = new EtudiantAjoutJFrame();
        frame.setVisible(true);
    }
    private void modifierEtudiant(){
        ModifierEtudiantFrame f = new ModifierEtudiantFrame();
        f.setVisible(true);
    }
    private void modifierEnseignant(){
        EnseignantModifierFrame f = new EnseignantModifierFrame();
        f.setVisible(true);
    }
    private void modifierSoutenance(){
        SoutenanceModifierFrame f = new SoutenanceModifierFrame();
        f.setVisible(true);
    }
    private void supprimerEtudiant() {
        EtudiantSuppressionJFrame frame = new EtudiantSuppressionJFrame();
        frame.setVisible(true);
    }

    private void rechercherEtudiant() {
        EtudiantRechercheJFrame frame = new EtudiantRechercheJFrame();
        frame.setVisible(true);
    }

    private void afficherEtudiants() {
        try {
            EtudiantAffichageJFrame frame = new EtudiantAffichageJFrame();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ajouterEnseignant() {
        AjoutEnseignantJFrame frame = new AjoutEnseignantJFrame();
        frame.setVisible(true);
    }

    private void afficherProjet() {
        EnseignantAffichageProjet frame = new EnseignantAffichageProjet();
        frame.setVisible(true);
    }

    private void afficherEnseignants() {
        try {
            EnseignantAffichageJFrame frame = new EnseignantAffichageJFrame();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void supprimerEnseignant() {
        EnseignantSuppressionJFrame frame = new EnseignantSuppressionJFrame();
        frame.setVisible(true);
    }

    private void ajouterProjet() {
        try {
            PFEAjoutJFrame frame = new PFEAjoutJFrame();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void affecterProjet() {
        PFEAffecterEtudiants frame = new PFEAffecterEtudiants();
        frame.setVisible(true);
    }

    private void validerProjet() {
        ValiderSoutenance frame = new ValiderSoutenance();
        frame.setVisible(true);
    }

    private void consulterProjet() {
        ConsulterProjet frame = new ConsulterProjet();
        frame.setVisible(true);
    }

    private void consulterSoutenance() {
        ConsulterSoutenance frame = new ConsulterSoutenance();
        frame.setVisible(true);
    }

    private void supprimerProjet() {
        SupprimerProjet frame = new SupprimerProjet();
        frame.setVisible(true);
    }
    

    // Méthode pour basculer la visibilité de la barre latérale
    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebar.setVisible(sidebarVisible);
    }
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color( 187, 218, 254 ));}
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(147,196,207)); // Original blue color
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}