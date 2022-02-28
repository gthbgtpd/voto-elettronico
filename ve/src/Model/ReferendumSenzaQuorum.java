package Model;

import java.util.Map;

public class ReferendumSenzaQuorum implements DefinizioneVotoStrategy {

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
		return winner;
	}

}
