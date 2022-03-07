import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.SessioneVoto;
import Model.Votable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class UserViewController {

    @FXML
    private ListView<String> ListViewVotableOrdered = new ListView<String>();

    @FXML
    private ListView<String> ListViewVotableUnordered = new ListView<String>();

    @FXML
    private MenuButton ScegliSessioneAperta;

    @FXML
    private ListView<String> SelezionaCandidatiPartito = new ListView<String>();

    @FXML
    private MenuButton SelezionaPartito;

    @FXML
    private MenuButton SelezionaPreferenza;

    @FXML
    private Pane categoricoPane;

    @FXML
    private Pane categoricoPreferenzaPane;

    @FXML
    private Pane openVoteSessionPane;

    @FXML
    private Pane ordinalePane;

    @FXML
    private CheckBox referendumContro;

    @FXML
    private Pane referendumPane;

    @FXML
    private CheckBox referendumPro;

    @FXML
    private MenuItem vota;

    @FXML
    private Button votaCategoricoPreferenzaButton;

    @FXML
    private Button votoCategoricoButton;

    @FXML
    private Button votoOrdinaleButton;

    @FXML
    private Button votoReferendumButton;
    
    @FXML
    private Button addToLst;
	
    private int idUser;

    @FXML
    void handleVota(ActionEvent event) {
    	if (event.getSource()==vota) {
    		openVoteSessionPane.setVisible(true);
    		ordinalePane.setVisible(false);
    		categoricoPane.setVisible(false);
    		categoricoPreferenzaPane.setVisible(false);
    		referendumPane.setVisible(false);
    		ScegliSessioneAperta.setText("Sessioni di voto aperte");
    		ListViewVotableOrdered.getItems().clear();
    		ListViewVotableUnordered.getItems().clear();
    		SelezionaCandidatiPartito.getItems().clear();
    		SelezionaPreferenza.getItems().clear();
    	}
    }
    
    @FXML
    void handleAddToLst(ActionEvent event) {
    	if (event.getSource()==addToLst) {
    		ListViewVotableOrdered.getItems().add(ListViewVotableUnordered.getSelectionModel().getSelectedItem());
    	}
    }

    @FXML
    void handleVotoCategorico(ActionEvent event) {
    	if (event.getSource()==votoCategoricoButton) {
    		String sessioneVotoScelta = ScegliSessioneAperta.getText();
        	try {
        		SessioneVoto selected = UserViewDao.getVotingSession(sessioneVotoScelta);
        		if (UserViewDao.getUserHasVoted(idUser, selected.getId())) {
					// inserimento di un pane di errore
					return;
				}
				String nameCandidate = SelezionaPreferenza.getText();
				List<Votable> preferences = new ArrayList<>();
				Votable candidate = UserViewDao.getCandidate(nameCandidate);
				preferences.add(candidate);
				Map<Votable, Integer> preferenzeAggiornate = selected.vota(preferences);
				for (Votable v : preferenzeAggiornate.keySet()) {
					UserViewDao.putVote(v, preferenzeAggiornate.get(v));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		ScegliSessioneAperta.setText("Sessioni di voto aperte");
    		SelezionaPreferenza.setText("Seleziona preferenza");
    		SelezionaPreferenza.getItems().clear();
    	}
    }

    @FXML
    void handleVotoCategoricoPreferenza(ActionEvent event) {
    	if (event.getSource()==votaCategoricoPreferenzaButton) {
    		String nameSession = ScegliSessioneAperta.getText();
        	try {
        		SessioneVoto selected = UserViewDao.getVotingSession(nameSession);
        		if (UserViewDao.getUserHasVoted(idUser, selected.getId())) {
					/*da fare*/
					return;
				}
				String nameCandidate = SelezionaCandidatiPartito.getSelectionModel().getSelectedItem();
				List<Votable> preferences = new ArrayList<>();
				Votable candidate = UserViewDao.getCandidate(nameCandidate);
				preferences.add(candidate);
				Map<Votable, Integer> preferenzeAggiornate = selected.vota(preferences);
				for (Votable v : preferenzeAggiornate.keySet()) {
					UserViewDao.putVote(v, preferenzeAggiornate.get(v));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		ScegliSessioneAperta.setText("Sessioni di voto aperte");
    		SelezionaPartito.setText("Seleziona partito (o gruppo) di preferenza");
    		SelezionaCandidatiPartito.getItems().clear();
    		
    	}
    }

    @FXML
    void handleVotoOrdinale(ActionEvent event) {
    	if (event.getSource()==votoOrdinaleButton) {
    		String sessioneVotoScelta = ScegliSessioneAperta.getText();
        	try {
				SessioneVoto selected = UserViewDao.getVotingSession(sessioneVotoScelta);
				if (UserViewDao.getUserHasVoted(idUser, selected.getId())) {
					// inserimento di un pane di errore
					return;
				}
				List<Votable> preferences = new ArrayList<>();
				for (String nameCandidate : ListViewVotableOrdered.getItems()) {
					Votable candidate = UserViewDao.getCandidate(nameCandidate);
					preferences.add(candidate);
				}
				Map<Votable, Integer> preferenzeAggiornate = selected.vota(preferences);
				for (Votable v : preferenzeAggiornate.keySet()) {
					UserViewDao.putVote(v, preferenzeAggiornate.get(v));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		ScegliSessioneAperta.setText("Sessioni di voto aperte");
    		ListViewVotableOrdered.getItems().clear();
    		ListViewVotableUnordered.getItems().clear();
    	}
    }

    @FXML
    void handleVotoReferendum(ActionEvent event) {
    	if (event.getSource()==votoReferendumButton) {
    		String nameSession = ScegliSessioneAperta.getText();
        	try {
        		SessioneVoto selected = UserViewDao.getVotingSession(nameSession);
        		if (UserViewDao.getUserHasVoted(idUser, selected.getId())) {
					/*da fare*/
					return;
				}
        		String nameCandidate = null;
        		if (referendumPro.isSelected()) {
        			nameCandidate = "yes";
        		} else if (referendumContro.isSelected()) {
        			nameCandidate = "no";
        		}
				List<Votable> preferences = new ArrayList<>();
				Votable candidate = UserViewDao.getCandidate(nameCandidate);
				preferences.add(candidate);
				Map<Votable, Integer> preferenzeAggiornate = selected.vota(preferences);
				for (Votable v : preferenzeAggiornate.keySet()) {
					UserViewDao.putVote(v, preferenzeAggiornate.get(v));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		ScegliSessioneAperta.setText("Sessioni di voto aperte");
    		referendumPro.setSelected(false);
    		referendumContro.setSelected(false);
    	}
    }
    
    @FXML
    void initialize() throws SQLException, ParseException {
    	UserViewDao.getOpenSessions(ScegliSessioneAperta);
    	UserViewSetting.selectedMenuButton(ScegliSessioneAperta, ordinalePane, categoricoPane, categoricoPreferenzaPane, referendumPane, ListViewVotableUnordered, ListViewVotableUnordered, SelezionaPreferenza, SelezionaPartito, SelezionaCandidatiPartito);
    	idUser = 0; // dovrà essere ricevuto da una precedente vista
    }

}
