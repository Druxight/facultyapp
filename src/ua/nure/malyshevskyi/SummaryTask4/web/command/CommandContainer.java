package ua.nure.malyshevskyi.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.web.command.admin.BlockUserCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.admin.CourseManagmentCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.admin.SetProfToCourseCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.admin.SetRoleCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.admin.SubjectManagementCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.auth.LoginCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.auth.LogoutCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.auth.RegisterCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.common.CourseSortingCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.common.DropOutCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.common.EditProfileCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.common.ElectiveCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.common.ProfileCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.professor.ProfessorCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.professor.RatingManagmentCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.student.CourseDateSortingCommand;
import ua.nure.malyshevskyi.SummaryTask4.web.command.student.SetStudentToCourseCommand;

public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		
		commands.put("login", new LoginCommand());
		commands.put("register", new RegisterCommand());
		commands.put("electives", new ElectiveCommand());
		commands.put("profile", new ProfileCommand());
		commands.put("editProfile", new EditProfileCommand());
		commands.put("filter", new CourseDateSortingCommand());
		commands.put("professor", new ProfessorCommand());
		commands.put("ratingManagment", new RatingManagmentCommand());
		commands.put("courseSort", new CourseSortingCommand());
		commands.put("setStudentToCourse", new SetStudentToCourseCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("dropOut", new DropOutCommand());
		commands.put("professorToCourse", new SetProfToCourseCommand());
		commands.put("subjectManagment", new SubjectManagementCommand());
		commands.put("courseCommand", new CourseManagmentCommand());
		commands.put("blockUser", new BlockUserCommand());
		commands.put("setRole", new SetRoleCommand());
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}
