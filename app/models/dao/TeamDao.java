package models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Team;

public class TeamDao extends Dao<Team, Integer> implements ITeamDao {

	public TeamDao(EntityManager em) {
		super(Team.class, em);
	}

	@Override
	public boolean checkAvailable(String name) {
		
		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " t where t.name = :name").setParameter("name", name);
		
		Long count = (Long) query.getSingleResult();
		return count < 1;
	}

	@Override
	public List<Team> getTeamsByUser(Integer userId) {
		
		Query query = getEntityManager()
				.createQuery("select t.* from TEAM t"
				+ " join TeamUser tu where tu.user_id = :id")
				.setParameter("id", userId);

		
		List<Team> teams = (List<Team>) query.getResultList();
		
		return teams;
	}


}
