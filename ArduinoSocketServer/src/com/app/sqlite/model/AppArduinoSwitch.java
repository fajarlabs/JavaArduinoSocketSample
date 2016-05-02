package com.app.sqlite.model;

public class AppArduinoSwitch {
	private Integer id;
	private String switchName;
	private String commandValue;
	
	public AppArduinoSwitch() { }
	
	public AppArduinoSwitch(Integer id, String switchName, String commandValue) {
		super();
		this.id = id;
		this.switchName = switchName;
		this.commandValue = commandValue;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSwitchName() {
		return switchName;
	}
	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
	public String getCommandValue() {
		return commandValue;
	}
	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}
}
