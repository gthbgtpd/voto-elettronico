package Model;

import java.util.Map;

public class ReferendumConQuorum implements ModalitaVincitaStrategy {

	/**
	 * Post-condizioni: se la maggioranza degli aventi diritto avrà esposto la propria preferenza 
	 * 					sul quesito referendario, allora verrà resituita la preferenza vincente, 
	 * 					altrimenti verrà restituito un valore null.
	 * Nota bene: numVoters in questo caso identifica il numero di utenti aventi diritto e non il numero di votanti alla specifca sessione di voto
	 * 					
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
