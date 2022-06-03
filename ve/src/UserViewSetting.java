import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserViewSetting {

	public static void selectedMenuButton(MenuButton menu, Pane ordPane, Pane ctgPane, Pane ctgpPane, Pane rfdPane, ListView<String> unrdLst, ListView<String> ordLst,MenuButton SelezionaPreferenza, MenuButton SelezionaPartito, ListView<String> SelezionaCandidatiPartito) throws SQLException {
    	ObservableList<MenuItem> lm = menu.getItems();
    	for (MenuItem i : lm) {
    		if (UserViewDao.getMode(i.getText()).equals("Ordinario")) {
				i.setOnAction(e -> {
		    		menu.setText(i.getText());
		    		ordPane.setVisible(true);
		    		ctgPane.setVisible(false);
		    		ctgpPane.setVisible(false);
		    		rfdPane.setVisible(false);
		    		unrdLst.getItems().clear();
		    		ordLst.getItems().clear();
		    		SelezionaPreferenza.getItems().clear();
		    		SelezionaPreferenza.setText("Seleziona preferenza");
		    		ObservableList<String> cdts;
					try {
						cdts = UserViewDao.getCandidates(menu.getText());
						unrdLst.setItems(cdts);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		    	});
    		} else if (UserViewDao.getMode(i.getText()).equals("Categorico")) {
				i.setOnAction(e -> {
		    		menu.setText(i.getText());
		    		ctgPane.setVisible(true);
		    		ordPane.setVisible(false);
		    		ctgpPane.setVisible(false);
		    		rfdPane.setVisible(false);
		    		unrdLst.getItems().clear();
		    		ordLst.getItems().clear();
		    		SelezionaPreferenza.getItems().clear();
		    		SelezionaPreferenza.setText("Seleziona preferenza");
		    		ObservableList<String> cdts;
					try {
						cdts = UserViewDao.getCandidates(menu.getText());
						for (String k:cdts) {
				    		MenuItem cdt = new MenuItem(k);
				    		SelezionaPreferenza.getItems().add(cdt);
				    	}
						ObservableList<MenuItem> lp = SelezionaPreferenza.getItems();
				    	for (MenuItem j : lp) {
				    		j.setOnAction(ep -> {
				    			SelezionaPreferenza.setText(j.getText());
					    	});
				    	}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		    	});
    		} else if (UserViewDao.getMode(i.getText()).equals("Categorico con preferenza")) {
				i.setOnAction(e -> {
		    		menu.setText(i.getText());
		    		ctgpPane.setVisible(true);
		    		ctgPane.setVisible(false);
		    		ordPane.setVisible(false);
		    		rfdPane.setVisible(false);
		    		unrdLst.getItems().clear();
		    		ordLst.getItems().clear();
		    		SelezionaPreferenza.getItems().clear();
		    		SelezionaPreferenza.setText("Seleziona preferenza");
		    		ObservableList<String> cdts;
					
						try {
							cdts = UserViewDao.getParty(menu.getText());
							for (String k:cdts) {
					    		MenuItem cdt = new MenuItem(k);
					    		SelezionaPartito.getItems().add(cdt);
					    	}
							ObservableList<MenuItem> lf = SelezionaPartito.getItems();
					    	for (MenuItem f : lf) {
					    		f.setOnAction(ef -> {
					    			SelezionaPartito.setText(f.getText());
					    			ObservableList<String> cdtsFromParty;
										try {
											cdtsFromParty = UserViewDao.getCandidatesFromParty(SelezionaPartito.getText());
											SelezionaCandidatiPartito.setItems(cdtsFromParty);
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
						    	});
					    	}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
		    	});
    		} else if (UserViewDao.getMode(i.getText()).equals("Referendum")) {
				i.setOnAction(e -> {
		    		menu.setText(i.getText());
		    		rfdPane.setVisible(true);
		    		ordPane.setVisible(false);
		    		ctgPane.setVisible(false);
		    		ctgpPane.setVisible(false);
		    		unrdLst.getItems().clear();
		    		ordLst.getItems().clear();
		    		SelezionaPreferenza.getItems().clear();
		    		SelezionaPreferenza.setText("Seleziona preferenza");
		    	});
    		}
    	}
    }

}
