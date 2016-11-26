package models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Team;

/**
 * Team dao.
 * 
 * @author TEAM RMG
 *
 */
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
				.createQuery("select t from Team t"
				+ " inner join t.users u where u.id = :id")
				.setParameter("id", userId);

		
		@SuppressWarnings("unchecked")
		List<Team> teams = query.getResultList();
		
		return teams;
	}


}
