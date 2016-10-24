package services.rating;

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
	
}
