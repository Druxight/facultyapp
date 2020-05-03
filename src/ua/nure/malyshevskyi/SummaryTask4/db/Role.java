package ua.nure.malyshevskyi.SummaryTask4.db;

import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;

public enum Role {
	ADMIN, PROFESSOR, STUDENT, BLOCKED;

	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
