package services.rating;

import java.util.List;

import models.Task;
import models.vm.UserViewModel;


/**
 * Rating service.
 * 
 * @author TEAM RMG
 *
 */
public interface IRatingService {
	
    /**
     * Rate user
     *
     * @param vm User to rate
     */
	void rateUserScore(UserViewModel vm);

	/**
	 * Calculate percentage of completed tasks
	 * 
	 * @param tasks list of task
	 * @param vm User view model
	 */
	void rateUserTasksScore(List<Task> tasks, UserViewModel vm);
	
}
