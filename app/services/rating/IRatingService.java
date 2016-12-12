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
	void rateUser(UserViewModel vm);

	/**
	 * Calculate percentage of completed tasks
	 * 
	 * @param tasks list of tasks
	 * @param vm User view model
	 */
	void calculateCompletionPercentage(List<Task> tasks, UserViewModel vm);
	
}
