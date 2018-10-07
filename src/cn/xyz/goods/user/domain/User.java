package cn.xyz.goods.user.domain;
/**
 * 数据库中查询到的数据映射到这个类中
 * @author Administrator
 *
 */
public class User {
		private String uid;
		private String loginname;
		private String loginpass;
		private String reloginpass;
		private String email;
		private String code;
		private boolean status;
		private String activationCode;
		private String newpass;
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getLoginname() {
			return loginname;
		}
		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}
		public String getLoginpass() {
			return loginpass;
		}
		public void setLoginpass(String loginpass) {
			this.loginpass = loginpass;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean getStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getActivationCode() {
			return activationCode;
		}
		public void setActivationCode(String activationCode) {
			this.activationCode = activationCode;
		}
		public String getReloginpass() {
			return reloginpass;
		}
		public void setReloginpass(String reloginpass) {
			this.reloginpass = reloginpass;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getNewpass() {
			return newpass;
		}
		public void setNewpass(String newpass) {
			this.newpass = newpass;
		}
		@Override
		public String toString() {
			return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", reloginpass="
					+ reloginpass + ", email=" + email + ", code=" + code + ", status=" + status + ", activationCode="
					+ activationCode + ", newpass=" + newpass + "]";
		} 
}
