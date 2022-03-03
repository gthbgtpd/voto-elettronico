import java.sql.SQLException;
import java.util.Map;

public class ConcreteStrategyReferendum {
	
	public ConcreteStrategyReferendum() {}
	
	public void voto(Votable candidato, Map<Votable, Integer> candidatoConVoto) throws SQLException {
		if (candidatoConVoto.containsKey(candidato)) {
			AdminClientDao.putVote(candidato, candidatoConVoto.get(candidato)+1);
		}
		return;
	}
}
