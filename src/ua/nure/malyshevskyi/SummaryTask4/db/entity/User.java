package ua.nure.malyshevskyi.SummaryTask4.db.entity;

public class User extends Entity {

	private static final long serialVersionUID = 4945941249781672008L;

	private String email;

	private String login;

	private String password;

	private String firstName;

	private String lastName;

	private int roleId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", roleId=" + roleId
				+ ", getId()=" + getId() + "]";
	}

}
