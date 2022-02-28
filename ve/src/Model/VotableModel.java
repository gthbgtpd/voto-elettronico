package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VotableModel {

    private SimpleIntegerProperty idCandidato;
    private SimpleStringProperty nomeCandidato;
    private SimpleStringProperty nomePartito;

    public VotableModel(Integer idCandidato, String nomeCandidato, String nomePartito) {
        this.idCandidato = new SimpleIntegerProperty(idCandidato);
        this.nomeCandidato = new SimpleStringProperty(nomeCandidato);
        this.nomePartito = new SimpleStringProperty(nomePartito);
    }

    public int getIdCandidato() {
        return idCandidato.get();
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = new SimpleIntegerProperty(idCandidato);
    }

    public String getNomeCandidato() {
        return nomeCandidato.get();
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = new SimpleStringProperty(nomeCandidato);
    }

    public String getNomePartito() {
        return nomePartito.get();
    }

    public void setNomePartito(String nomePartito) {
        this.nomePartito = new SimpleStringProperty(nomePartito);
    }
}