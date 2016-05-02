package com.app.sqlite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.sqlite.model.AppArduinoSwitch;
import com.app.sqlite.util.SQLiteUtil;

public class AppArduinoSwitchDAO {
	
	public static int createTable() {
		String sql = "CREATE TABLE APP_ARDUINO_SWITCH " + "(ID INT PRIMARY KEY     NOT NULL,"
				+ " SWITCH_NAME    VARCHAR(5)    NOT NULL, " + " COMMAND_VALUE  VARCHAR(5) NOT NULL)";
		int result = 0;
		try {
			result = SQLiteUtil.getInstance().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<AppArduinoSwitch> find(AppArduinoSwitch data) {
		List<String> arrData = new ArrayList<String>();
		if(data.getId() != null)
			arrData.add("ID = '"+String.valueOf(data.getId())+"'");
		
		if(data.getCommandValue() != null)
			arrData.add("COMMAND_VALUE = '"+data.getCommandValue()+"'");
		
		if(data.getSwitchName() != null)
			arrData.add("SWITCH_NAME = '"+data.getSwitchName()+"'");
		
		int limit = arrData.size() - 1;
		int i = 0;
		String sql = "SELECT * FROM APP_ARDUINO_SWITCH";
		if(arrData.size() > 0) {
			sql += " WHERE ";
			for(String str : arrData) {
				sql += str;
				if(i < limit) {
					sql += " AND ";
				}
				
				i++;
			}
		}
		
		ResultSet rs = null;
		List<AppArduinoSwitch> result = new ArrayList<AppArduinoSwitch>();
		try {
			rs = SQLiteUtil.getInstance().createStatement().executeQuery(sql);
			while(rs.next()) {
				result.add(new AppArduinoSwitch(rs.getInt("ID"), rs.getString("SWITCH_NAME"), rs.getString("COMMAND_VALUE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int add(AppArduinoSwitch data) {
		String sql = "INSERT INTO APP_ARDUINO_SWITCH VALUES ("+data.getId()+",'"+data.getSwitchName()+"','"+data.getCommandValue()+"')";
		
		int result = 0;
		try {
			result = SQLiteUtil.getInstance().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int delete(AppArduinoSwitch data) {
		String sql = "";
		if(data.getId() == null) {
			sql += "DELETE FROM APP_ARDUINO_SWITCH";
		} else {
			List<String> arrData = new ArrayList<String>();
			if(data.getId() != null)
				arrData.add("ID = '"+String.valueOf(data.getId())+"'");
			
			if(data.getCommandValue() != null)
				arrData.add("COMMAND_VALUE = '"+data.getCommandValue()+"'");
			
			if(data.getSwitchName() != null)
				arrData.add("SWITCH_NAME = '"+data.getSwitchName()+"'");
			
			int limit = arrData.size() - 1;
			int i = 0;
			sql += "DELETE FROM APP_ARDUINO_SWITCH";
			if(arrData.size() > 0) {
				sql += " WHERE ";
				for(String str : arrData) {
					sql += str;
					if(i < limit) {
						sql += " AND ";
					}
					
					i++;
				}
			}
		}
		
		
		int result = 0;
		try {
			result = SQLiteUtil.getInstance().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int updateBySwitchName(AppArduinoSwitch data) {
		String sql = "UPDATE APP_ARDUINO_SWITCH SET COMMAND_VALUE = '"+data.getCommandValue()+"' WHERE SWITCH_NAME = '"+data.getSwitchName()+"'";
		int result = 0;
		try {
			result = SQLiteUtil.getInstance().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;	
	}
	
	public static List<AppArduinoSwitch> read() {
		String sql = "SELECT * FROM APP_ARDUINO_SWITCH";
		ResultSet rs = null;
		List<AppArduinoSwitch> result = new ArrayList<AppArduinoSwitch>();
		try {
			rs = SQLiteUtil.getInstance().createStatement().executeQuery(sql);
			while(rs.next()) {
				result.add(new AppArduinoSwitch(rs.getInt("ID"), rs.getString("SWITCH_NAME"), rs.getString("COMMAND_VALUE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
