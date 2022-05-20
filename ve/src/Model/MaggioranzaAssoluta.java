package Model;

import java.util.Map;

public class MaggioranzaAssoluta implements ModalitaVincitaStrategy {

	/**
	 * Post-condizioni: se il candidato che ha raggiunto il numero massimo di voti ha ottenuto la preferenza di almeno il 50% +1 dei voti
	 * 					allora verrà restituito quello specifico candidato, altrimenti verrà restituito un valore null.
	 */
	@Override
	public Votable definizioneVincitore(Map<Votable, Integer> votes, int numVoters) {
		int maxNumOfVotes = 0;
		Votable winner = null;
		for (Votable v : votes.keySet()) {
			int votesOfV = votes.get(v);
			if (votesOfV > maxNumOfVotes) {
				winner = v;
				maxNumOfVotes = votesOfV;
			}
		}
		if (!(maxNumOfVotes > numVoters / 2)) winner = null;
		return winner;
	}

}
