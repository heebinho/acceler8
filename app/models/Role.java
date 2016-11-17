package models;
import javax.persistence.*;

import play.data.validation.Constraints.*;

/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Entity
public class Role extends Model{
			
		@Required
		private String desc;
		
		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String method;
		
		
		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		
		
		@Override
		public String toString() {
			return String.format("%s - %s", getId(), getMethod());
		}
		

}
