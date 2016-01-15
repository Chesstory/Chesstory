package Controller;

/* Liste des options possibles pour la partie Custom (a modifier pour adapter a la classe bien sur)
 * 
 * JFrame fenetre = new JFrame("Choix des options custom");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fenetre.setUndecorated(true);
		fenetre.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		fenetre.setResizable(false);

		JPanel choixOptions, panelTour, panelCavalier, panelFou, panelRoi, panelDame, panelPion, panelAutres;
		
		ButtonGroup groupeTour, groupeCavalier, groupeFou, groupeRoi, groupeDame, groupePion;

		JRadioButton[] optionsTour = new JRadioButton[3];
		JRadioButton[] optionsCavalier = new JRadioButton[3];
		JRadioButton[] optionsFou = new JRadioButton[5];
		JRadioButton[] optionsRoi = new JRadioButton[4];
		JRadioButton[] optionsDame = new JRadioButton[5];
		JRadioButton[] optionsPion = new JRadioButton[4];
		JCheckBox[] optionsAutres = new JCheckBox[5];

		choixOptions = new JPanel();
		//choixOptions.setLayout(new BoxLayout(choixOptions, BoxLayout.PAGE_AXIS));
		
		panelTour = new JPanel();
		panelTour.setLayout(new BoxLayout(panelTour, BoxLayout.PAGE_AXIS));
		panelTour.setBorder(BorderFactory.createTitledBorder("Tour"));
		panelCavalier = new JPanel();
		panelCavalier.setLayout(new BoxLayout(panelCavalier, BoxLayout.PAGE_AXIS));
		panelCavalier.setBorder(BorderFactory.createTitledBorder("Cavalier"));
		panelFou = new JPanel();
		panelFou.setLayout(new BoxLayout(panelFou, BoxLayout.PAGE_AXIS));
		panelFou.setBorder(BorderFactory.createTitledBorder("Fou"));
		panelRoi = new JPanel();
		panelRoi.setLayout(new BoxLayout(panelRoi, BoxLayout.PAGE_AXIS));
		panelRoi.setBorder(BorderFactory.createTitledBorder("Roi"));
		panelDame = new JPanel();
		panelDame.setLayout(new BoxLayout(panelDame, BoxLayout.PAGE_AXIS));
		panelDame.setBorder(BorderFactory.createTitledBorder("Dame"));
		panelPion = new JPanel();
		panelPion.setLayout(new BoxLayout(panelPion, BoxLayout.PAGE_AXIS));
		panelPion.setBorder(BorderFactory.createTitledBorder("Pion"));
		panelAutres = new JPanel();
		panelAutres.setLayout(new BoxLayout(panelAutres, BoxLayout.PAGE_AXIS));
		panelAutres.setBorder(BorderFactory.createTitledBorder("Autres options"));

		groupeTour = new ButtonGroup();
		groupeCavalier = new ButtonGroup();
		groupeFou = new ButtonGroup();
		groupeRoi = new ButtonGroup();
		groupeDame = new ButtonGroup();
		groupePion = new ButtonGroup();

		optionsTour[0] = new JRadioButton("Deplacement classique");
		optionsTour[0].setSelected(true);
		optionsTour[1] = new JRadioButton("Deplacement limite au choix");
		optionsTour[2] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsTour.length ; i++){
				groupeTour.add(optionsTour[i]);
				panelTour.add(optionsTour[i]);
		}

		optionsCavalier[0] = new JRadioButton("Deplacement classique");
		optionsCavalier[0].setSelected(true);
		optionsCavalier[1] = new JRadioButton("Autre portee de deplacement");
		optionsCavalier[2] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsCavalier.length ; i++){
				groupeCavalier.add(optionsCavalier[i]);
				panelCavalier.add(optionsCavalier[i]);
		}

		optionsFou[0] = new JRadioButton("Deplacement classique");
		optionsFou[0].setSelected(true);
		optionsFou[1] = new JRadioButton("Type Chaturanga (exactement 2 cases en diagonale, peut sauter)");
		optionsFou[2] = new JRadioButton("Type Makruk (1 case en diagonale ou 1 case vers le haut)");
		optionsFou[3] = new JRadioButton("Deplacement limite au choix");
		optionsFou[4] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsFou.length ; i++){
				groupeFou.add(optionsFou[i]);
				panelFou.add(optionsFou[i]);
		}

		optionsRoi[0] = new JRadioButton("Deplacement classique");
		optionsRoi[0].setSelected(true);
		optionsRoi[1] = new JRadioButton("Type Chaturanga (il peut faire un mouvement de cavalier une fois)");
		optionsRoi[2] = new JRadioButton("Autre portee de deplacement");
		optionsRoi[3] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsRoi.length ; i++){
				groupeRoi.add(optionsRoi[i]);
				panelRoi.add(optionsRoi[i]);
		}

		optionsDame[0] = new JRadioButton("Deplacement classique");
		optionsDame[0].setSelected(true);
		optionsDame[1] = new JRadioButton("Type Makruk (une case en diagonale)");
		optionsDame[2] = new JRadioButton("Type Shatar (combinaison tour/roi)");
		optionsDame[3] = new JRadioButton("Autre portee de deplacement");
		optionsDame[4] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsDame.length ; i++){
				groupeDame.add(optionsDame[i]);
				panelDame.add(optionsDame[i]);
		}

		optionsPion[0] = new JRadioButton("Deplacement classique");
		optionsPion[0].setSelected(true);
		optionsPion[1] = new JRadioButton("Type Chaturanga (prise possible aussi en avant");
		optionsPion[2] = new JRadioButton("Autre portee de deplacement");
		optionsPion[3] = new JRadioButton("Deplacement d'une autre piece connue");

		for(int i=0 ; i<optionsPion.length ; i++){
				groupePion.add(optionsPion[i]);
				panelPion.add(optionsPion[i]);
		}

		optionsAutres[0] = new JCheckBox("Roque");
		optionsAutres[1] = new JCheckBox("Prise en passant");
		optionsAutres[2] = new JCheckBox("Double pas initial");
		optionsAutres[3] = new JCheckBox("Promotion en ligne 6");
		optionsAutres[4] = new JCheckBox("Promotion en dame automatique");

		 for(int i=0 ; i<optionsAutres.length ; i++){
				panelAutres.add(optionsAutres[i]);
		}
		
		choixOptions.add(panelTour);
		choixOptions.add(panelCavalier);
		choixOptions.add(panelFou);
		choixOptions.add(panelRoi);
		choixOptions.add(panelDame);
		choixOptions.add(panelPion);
		choixOptions.add(panelAutres);
		fenetre.add(choixOptions);
		fenetre.setVisible(true);
 * 
 */

public class MainMenu {

	public static void main(String[] args) {
		MainMenu m=new MainMenu();

	}
	private MainMenu(){
		
	}

}