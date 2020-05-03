package ua.nure.malyshevskyi.SummaryTask4.db.entity;

public class Subject extends Entity {

	private static final long serialVersionUID = 4194961238170972727L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + ", getId()=" + getId() + "]";
	}

}
