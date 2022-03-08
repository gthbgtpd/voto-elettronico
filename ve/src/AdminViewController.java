import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class AdminViewController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
//createPane
    @FXML private Pane createSessionPane;
    @FXML private TextField nameSession;
    @FXML private MenuButton modalitaVoto;
    @FXML private MenuButton modalitaVincita;
    @FXML private Button creaSessione;
//modifyPane
    @FXML private Pane modifySessionPane;
    @FXML private MenuButton mScegliSessione;
    @FXML private TextField modificaNomeSessione;
    @FXML private TextField dataApertura;
    @FXML private TextField dataChiusura;
    @FXML private MenuButton modificaModalitaVincita;
    @FXML private MenuButton modificaModalitaVoto;
    @FXML private Button modificaSessione;
//addCandidatesPane
    @FXML private Pane insertCandidatesPane;
    @FXML private MenuButton aScegliSessione;
    @FXML private TextField candidates;
    @FXML private Button aggiungiCandidato;
    @FXML private CheckBox isGroup;
//addCandidatesPartyPane
     @FXML private Pane insertCandidatesPartyPane;
     @FXML private Button aggiungiCandidatoPartito;
     @FXML private MenuButton ScegliPartito;
     @FXML private MenuButton ScegliCandidato;
//viewResultPane
    @FXML private Pane viewResultPane;
    @FXML private MenuButton vScegliSessione;
    @FXML private TableView<Vote> table;
    @FXML private Label nomeVincitore;
    @FXML private Button visualizza;
//menuBar
    @FXML private MenuItem createSession;
    @FXML private MenuItem modifySession;
    @FXML private MenuItem result;
    @FXML private MenuItem insert;
    @FXML private MenuItem insertParty;
    
    @FXML private MenuItem cOrdinale;
    @FXML private MenuItem cCategorico;
    @FXML private MenuItem cCategoricoConP;
    @FXML private MenuItem cRef;
    
    @FXML private MenuItem cMaggioranza;
    @FXML private MenuItem cMaggioranzaAss;
    @FXML private MenuItem cRefConQ;
    @FXML private MenuItem crefSenzaQ;
    
    @FXML private MenuItem mOrdinale;
    @FXML private MenuItem mCategorico;
    @FXML private MenuItem MCategoricoConP;
    @FXML private MenuItem mReferendum;
    
    @FXML private MenuItem mMaggioranza;
    @FXML private MenuItem mMaggioranzaAss;
    @FXML private MenuItem mRefConQ;
    @FXML private MenuItem mRefSenzaQ;
	
    private int idUser;
    private Logger log;
    
//menuBar
    @FXML
    void handleCreateSession(ActionEvent event) {
	if (event.getSource()==createSession) {
		modifySessionPane.setVisible(false);
		viewResultPane.setVisible(false);
		insertCandidatesPane.setVisible(false);
		createSessionPane.setVisible(true);
		insertCandidatesPartyPane.setVisible(false);
	}
    }
    @FXML
    void handleModifySession(ActionEvent event) {
	if (event.getSource()==modifySession) {
		viewResultPane.setVisible(false);
		insertCandidatesPane.setVisible(false);
		createSessionPane.setVisible(false);
		modifySessionPane.setVisible(true);
		insertCandidatesPartyPane.setVisible(false);
	}
    }
    @FXML
    void handleResult(ActionEvent event) {
	if (event.getSource()==result) {
		insertCandidatesPane.setVisible(false);
		createSessionPane.setVisible(false);
		modifySessionPane.setVisible(false);
		viewResultPane.setVisible(true);
		insertCandidatesPartyPane.setVisible(false);
	}
    }
    @FXML
    void handleInsert(ActionEvent event) {
	if (event.getSource()==insert) {
		createSessionPane.setVisible(false);
		modifySessionPane.setVisible(false);
		viewResultPane.setVisible(false);
		insertCandidatesPane.setVisible(true);
		insertCandidatesPartyPane.setVisible(false);
	}
    }
	
    @FXML
     void handleInsertParty(ActionEvent event) {
     	if (event.getSource()==insertParty) {
     		createSessionPane.setVisible(false);
     		modifySessionPane.setVisible(false);
     		viewResultPane.setVisible(false);
     		insertCandidatesPartyPane.setVisible(true);
     		insertCandidatesPane.setVisible(false);
     	}
     }
    
    @FXML
    void handleCreaSessione(ActionEvent event) throws SQLException {
    	if (event.getSource()==creaSessione) {
		log.info("Utente con ID: " + idUser + " ha creato la sessione: " + nameSession.getText());
    		AdminViewDao.createSession(nameSession.getText(), modalitaVoto.getText(), modalitaVincita.getText());
    		nameSession.setText("");
    		modalitaVoto.setText("Modalità  di voto");
    		modalitaVincita.setText("Modalità  di vincita");
    		
    		AdminViewDao.getSessions(mScegliSessione);
        	AdminViewDao.getSessions(aScegliSessione);
        	AdminViewDao.getSessions(vScegliSessione);
    	}
    }

    @FXML
    void handleModificaSessione(ActionEvent event) throws SQLException {
    	if (event.getSource()==modificaSessione) {
    		Date dataOpen = null;
    		Date dataClose = null;
    		if ((dataApertura.getText()).length()>1) {
    			dataOpen = Date.valueOf(dataApertura.getText());
    		}
    		if ((dataChiusura.getText()).length()>1) {
    			dataClose = Date.valueOf(dataChiusura.getText());
    		}
		log.info("Utente con ID: " + idUser + " ha modificato la sessione: " + mScegliSessione.getText());
    		AdminViewDao.modifySession(mScegliSessione.getText(), modificaNomeSessione.getText(), modificaModalitaVoto.getText(), modificaModalitaVincita.getText(), dataOpen, dataClose);
    		mScegliSessione.setText("Sessione");
    		modificaNomeSessione.setText("");
    		modificaModalitaVoto.setText("Modalità  di voto");
    		modificaModalitaVincita.setText("Modalità  di vincita");
    		dataApertura.setText("");
    		dataChiusura.setText("");
    		
    		AdminViewDao.getSessions(mScegliSessione);
        	AdminViewDao.getSessions(aScegliSessione);
        	AdminViewDao.getSessions(vScegliSessione);
        	
        	AdminViewSetting.selectedMenuButtonModify(mScegliSessione, modificaModalitaVoto, modificaModalitaVincita);
        	AdminViewSetting.selectedMenuButton(aScegliSessione);
        	AdminViewSetting.selectedMenuButton(vScegliSessione);
    	}
    }
    
    @FXML
    void handleAggiungiCandidato(ActionEvent event) throws SQLException {
    	if (event.getSource()==aggiungiCandidato) {
		log.info("Utente con ID: " + idUser + " ha aggiunto il candidato " + candidates.getText() + " nella sessione: " + aScegliSessione.getText());
    		AdminViewDao.addCandidate(aScegliSessione.getText(), candidates.getText(), isGroup.isSelected());
    		aScegliSessione.setText("Sessione");
    		candidates.setText("");
    		isGroup.setSelected(false);
    	}
    }
	
    @FXML
     void handleAggiungiCandidatoPartito(ActionEvent event) throws SQLException {
     	if (event.getSource()==aggiungiCandidatoPartito) {
     		if (ScegliPartito.getText().equals(null) || ScegliPartito.getText().equals(null)) return;
		log.info("Utente con ID: " + idUser + " ha aggiunto il candidato " + ScegliCandidato.getText() + " nel partito: " + ScegliPartito.getText());
     		AdminViewDao.addCandidateParty(ScegliPartito.getText(), ScegliCandidato.getText());
     		ScegliPartito.setText("Partito");
     		ScegliCandidato.setText("Candidato");
     	}
     }

    
    @FXML
    void handleVisualizza(ActionEvent event) throws SQLException {
    	if (event.getSource()==visualizza) {
		log.info("Utente con ID: " + idUser + " ha visualizzato gli esiti della sessione: " + vScegliSessione.getText());
    		ObservableList<Vote> lst = FXCollections.observableArrayList();
    		List<List<String>> v;
    		try {
			
    			v = AdminViewDao.getVote(vScegliSessione.getText());
    			for (List<String> j:v) {
        			lst.add(new Vote(j.get(0), j.get(1), j.get(2)));
        		}
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		
    		table.getColumns().clear();
	    	AdminViewSetting.setTable(table, lst);
	    	
	    	nomeVincitore.setVisible(true);
	    	nomeVincitore.setText(AdminViewDao.getWinner(vScegliSessione.getText()));
	    	
	    	ObservableList<MenuItem> lm = vScegliSessione.getItems();
	    	for (MenuItem i : lm) {
				i.setOnAction(e -> {
					vScegliSessione.setText(i.getText());
		    		table.getItems().clear();
		    		table.getColumns().clear();
		    		nomeVincitore.setText("");
		    		nomeVincitore.setVisible(false);
				});
	    	}
	    	
    	}
    }
    
    @FXML
    void initialize() throws SQLException {
        assert MCategoricoConP != null : "fx:id=\"MCategoricoConP\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert aScegliSessione != null : "fx:id=\"aScegliSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert aggiungiCandidato != null : "fx:id=\"aggiungiCandidato\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cCategorico != null : "fx:id=\"cCategorico\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cCategoricoConP != null : "fx:id=\"cCategoricoConP\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cMaggioranza != null : "fx:id=\"cMaggioranza\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cMaggioranzaAss != null : "fx:id=\"cMaggioranzaAss\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cOrdinale != null : "fx:id=\"cOrdinario\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cRef != null : "fx:id=\"cRef\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert cRefConQ != null : "fx:id=\"cRefConQ\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert candidates != null : "fx:id=\"candidates\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert creaSessione != null : "fx:id=\"creaSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert createSessionPane != null : "fx:id=\"createSessionPane\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert crefSenzaQ != null : "fx:id=\"crefSenzaQ\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert insertCandidatesPane != null : "fx:id=\"insertCandidatesPane\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mCategorico != null : "fx:id=\"mCategorico\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mMaggioranza != null : "fx:id=\"mMaggioranza\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mMaggioranzaAss != null : "fx:id=\"mMaggioranzaAss\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mOrdinale != null : "fx:id=\"mOrdinario\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mRefConQ != null : "fx:id=\"mRefConQ\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mRefSenzaQ != null : "fx:id=\"mRefSenzaQ\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mReferendum != null : "fx:id=\"mReferendum\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert mScegliSessione != null : "fx:id=\"mScegliSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modalitaVincita != null : "fx:id=\"modalitaVincita\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modificaModalitaVincita != null : "fx:id=\"modificaModalitaVincita\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modificaModalitaVoto != null : "fx:id=\"modificaModalitaVoto\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modificaNomeSessione != null : "fx:id=\"modificaNomeSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modificaSessione != null : "fx:id=\"modificaSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modifySessionPane != null : "fx:id=\"modifySessionPane\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert modalitaVoto != null : "fx:id=\"modilitaVoto\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert nameSession != null : "fx:id=\"nameSession\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert nomeVincitore != null : "fx:id=\"nomeVincitore\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert vScegliSessione != null : "fx:id=\"vScegliSessione\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert viewResultPane != null : "fx:id=\"viewResultPane\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert dataApertura != null : "fx:id=\"dataApertura\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert dataChiusura != null : "fx:id=\"dataChiusura\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert isGroup != null : "fx:id=\"isGroup\" was not injected: check your FXML file 'AdminView.fxml'.";

	AdminViewDao.getSessions(mScegliSessione);
       	AdminViewDao.getSessions(aScegliSessione);
        AdminViewDao.getSessions(vScegliSessione);
	AdminViewDao.getCandidates(ScegliCandidato);
        AdminViewDao.getParties(ScegliPartito);
    	
    	AdminViewSetting.selectedMenuButtonModify(mScegliSessione, modificaModalitaVoto, modificaModalitaVincita);
    	AdminViewSetting.selectedMenuButton(aScegliSessione);
    	AdminViewSetting.selectedMenuButton(vScegliSessione);
	idUser = 0; // dovrà essere passato da una precedente vista e serve per le operazioni di logging
    	log = java.util.logging.Logger.getLogger(this.getClass().getName());
    	FileHandler fileHandler = new FileHandler("operations.log");
    	SimpleFormatter formatter = new SimpleFormatter();  
    	fileHandler.setFormatter(formatter); 
    	log.addHandler(fileHandler);
    	log.setUseParentHandlers(false);
    	log.info("Utente con ID: " + idUser + " ha inizializzato il sistema");
    }
    
    @FXML
    void handleCOrdinale(ActionEvent event) {
    	if (event.getSource()==cOrdinale) {
	    	modalitaVoto.setText(cOrdinale.getText());
		}
    }
    @FXML
    void handleCCategorico(ActionEvent event) {
    	if (event.getSource()==cCategorico) {
    		modalitaVoto.setText(cCategorico.getText());
		}
    }
    @FXML
    void handleCCategoricoConP(ActionEvent event) {
    	if (event.getSource()==cCategoricoConP) {
    		modalitaVoto.setText(cCategoricoConP.getText());
		}
    }
    @FXML
    void handleCRef(ActionEvent event) {
    	if (event.getSource()==cRef) {
    		modalitaVoto.setText(cRef.getText());
		}
    }

    @FXML
    void handleCMaggioranza(ActionEvent event) {
    	if (event.getSource()==cMaggioranza) {
	    	modalitaVincita.setText(cMaggioranza.getText());
		}
    }
    @FXML
    void handleCMaggioranzaAss(ActionEvent event) {
    	if (event.getSource()==cMaggioranzaAss) {
    		modalitaVincita.setText(cMaggioranzaAss.getText());
		}
    }
    @FXML
    void handleCRefConQ(ActionEvent event) {
    	if (event.getSource()==cRefConQ) {
    		modalitaVincita.setText(cRefConQ.getText());
		}
    }
    @FXML
    void handleCRefSenzaQ(ActionEvent event) {
    	if (event.getSource()==crefSenzaQ) {
    		modalitaVincita.setText(crefSenzaQ.getText());
		}
    }
///////
    @FXML
    void handleMOrdinale(ActionEvent event) {
    	if (event.getSource()==mOrdinale) {
	    	modificaModalitaVoto.setText(mOrdinale.getText());
		}
    }
    @FXML
    void handleMCategorico(ActionEvent event) {
    	if (event.getSource()==mCategorico) {
    		modificaModalitaVoto.setText(mCategorico.getText());
		}
    }
    @FXML
    void handleMCategoricoConP(ActionEvent event) {
    	if (event.getSource()==MCategoricoConP) {
    		modificaModalitaVoto.setText(MCategoricoConP.getText());
		}
    }
    @FXML
    void handleMReferendum(ActionEvent event) {
    	if (event.getSource()==mReferendum) {
    		modificaModalitaVoto.setText(mReferendum.getText());
		}
    }

    @FXML
    void handleMMaggioranza(ActionEvent event) {
    	if (event.getSource()==mMaggioranza) {
    		modificaModalitaVincita.setText(mMaggioranza.getText());
		}
    }
    @FXML
    void handleMMaggioranzaAss(ActionEvent event) {
    	if (event.getSource()==mMaggioranzaAss) {
    		modificaModalitaVincita.setText(mMaggioranzaAss.getText());
		}
    }
    @FXML
    void handleMRefConQ(ActionEvent event) {
    	if (event.getSource()==mRefConQ) {
    		modificaModalitaVincita.setText(mRefConQ.getText());
		}
    }
    @FXML
    void handleRefSenzaQ(ActionEvent event) {
    	if (event.getSource()==mRefSenzaQ) {
    		modificaModalitaVincita.setText(mRefSenzaQ.getText());
		}
    }

}

