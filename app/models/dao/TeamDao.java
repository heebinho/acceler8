package models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Team;
import models.User;

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

	/**
	 * Check if name is available
	 * 
	 * @param name Team name
	 * @return true if name is available
	 */
	@Override
	public boolean isAvailable(String name) {
		
		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " t where t.name = :name").setParameter("name", name);
		
		Long count = (Long) query.getSingleResult();
		return count < 1;
	}

	/**
	 * All the teams of the user
	 * 
	 * @param userId The id of the user
	 * @return List of teams
	 */
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
	
	/**
	 * All coached teams of a given user
	 * 
	 * @param user
	 * @return List of teams
	 */
	@Override
	public List<Team> getCoachedTeamsByUser(User user) {
		
		Query query = getEntityManager()
				.createQuery("select t from Team t"
				+ " where t.coach = :id")
				.setParameter("id", user);

		
		@SuppressWarnings("unchecked")
		List<Team> teams = query.getResultList();
		
		return teams;
	}
	
	/**
	 * Get all public teams
	 * 
	 * @return List of teams
	 */
	@Override
	public List<Team> getPublicTeams() {
		
		Query query = getEntityManager()
				.createQuery("select t from Team t"
				+ " where t.isPrivate = :v")
				.setParameter("v", false);

		
		@SuppressWarnings("unchecked")
		List<Team> teams = query.getResultList();
		
		return teams;
	}


}
