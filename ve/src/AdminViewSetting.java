import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminViewSetting {
	public static void selectedMenuButtonModify(MenuButton menu,MenuButton modificaModalitaVoto,MenuButton modificaModalitaVincita) {
    	ObservableList<MenuItem> lm = menu.getItems();
    	for (MenuItem i : lm) {
			i.setOnAction(e -> {
	    		menu.setText(i.getText());
	    		try {
					modificaModalitaVoto.setText(AdminViewDao.getModeVote(i.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	try {
					modificaModalitaVincita.setText(AdminViewDao.getModeWin(i.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	});
    	}
    }
	
	public static void selectedMenuButton(MenuButton menu) {
    	ObservableList<MenuItem> lm = menu.getItems();
    	for (MenuItem i : lm) {
			i.setOnAction(e -> {
	    		menu.setText(i.getText());
	    	});
    	}
    }
	
	@SuppressWarnings("unchecked")
	public static void setTable(TableView<Vote> table, ObservableList<Vote> lst) throws SQLException {
		table.setEditable(true);
		
		TableColumn<Vote, String> columnCandidato;
		columnCandidato = new TableColumn<>("Candidato");
		columnCandidato.setCellValueFactory(new PropertyValueFactory<Vote, String>("idCandidates"));
		
		TableColumn<Vote, String> columnSession;
		columnSession = new TableColumn<>("Sessione");
		columnSession.setCellValueFactory(new PropertyValueFactory<Vote, String>("idSession"));
		
		TableColumn<Vote, String> columnVoto;
		columnVoto = new TableColumn<>("Voto");
		columnVoto.setCellValueFactory(new PropertyValueFactory<Vote, String>("preferenza"));
		
		table.setItems(lst);
		table.getColumns().addAll(columnCandidato, columnSession, columnVoto);
		
	}
}
