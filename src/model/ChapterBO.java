package model;

public class ChapterBO {
	protected String name;
	protected String targetTime;
	
	public ChapterBO(String name, String targetTime) {
		super();
		this.name = name;
		this.targetTime = targetTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTargetTime() {
		return targetTime;
	}
	public void setTargetTime(String targetTime) {
		this.targetTime = targetTime;
	}
}
