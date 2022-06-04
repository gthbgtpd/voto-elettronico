import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {

    @FXML
    private Button bttn;

    @FXML
    private Label err1;

    @FXML
    private Label err2;

    @FXML
    private PasswordField pss;

    @FXML
    private TextField usr;

    @FXML
    void handlebttn(ActionEvent event) throws IOException {
    	if (event.getSource() == bttn) {
			String username = usr.getText();
			String password = pss.getText();
			String pwdDatabase = LoginDao.getPassword(username);
			if (pwdDatabase == null) {
				usr.setText("");
				pss.setText("");
				err1.setVisible(true);
				err2.setVisible(true);
				return;
			}
			int salt = LoginDao.getSalt(username);
			password = password + Integer.toString(salt);
			password = MD5Util.encrypt(password);
			if (pwdDatabase.equals(password)) {
				Scene scene = bttn.getScene();
	    		Window window = scene.getWindow();
	    		Stage stage = (Stage) window;
	    		Parent root;
	    		//TODO: aggiungere logica per passare l'id del utente alla successiva pagina FXML
	    		// l'ID è al momento contenuto nella variabile salt!
				if ((LoginDao.getType(username)).equals("ADMIN")) {
					root = FXMLLoader.load(getClass().getResource("fxml/AdminView.fxml"));
				} else {
					root = FXMLLoader.load(getClass().getResource("fxml/UserView.fxml"));
				}
				stage.setTitle("voto elettronico");
	            stage.setScene(new Scene(root, 800, 600));
	            stage.show();
			} else {
				usr.setText("");
				pss.setText("");
				err1.setVisible(true);
				err2.setVisible(true);
			}
		}
    }

}
