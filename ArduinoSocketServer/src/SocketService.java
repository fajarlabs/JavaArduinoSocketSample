import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.app.arduino.util.ArduinoUtil;
import com.app.config.PortConfig;
import com.app.socket.util.SocketServerUtil;
import com.app.sqlite.dao.AppArduinoSwitchDAO;
import com.app.sqlite.model.AppArduinoSwitch;

/**
 * 
 * @author masfajar
 *
 */

public class SocketService extends Thread {
	private ArduinoUtil arduino;
	private SocketServerUtil socket;

	public SocketService(int port) throws IOException {
		super();
		// Init socket service
		socket = new SocketServerUtil(port, 10000);

		// Init arduino connection
		arduino = new ArduinoUtil();

		// If connection is failed, then ?
		// Shutdown program
		if (!arduino.initialize())
			System.exit(0);

		// Update last setting by temporary DB
		List<AppArduinoSwitch> tempData = AppArduinoSwitchDAO.read();
		for (AppArduinoSwitch o : tempData) {
			// Update switch in arduino
			arduino.sendData(o.getCommandValue());
		}

	}

	@Override
	public void run() {
		while (true) {
			// Accept Socket-nya
			System.out.println("Waiting for client on port " + socket.getPort() + "...");
			if (socket.connect()) {
				// System.out.println(readUTF);
				JSONParser parser = new JSONParser();
				// Receive JSON Object
				JSONObject jsonObject = null;
				try {
					jsonObject = (JSONObject) parser.parse(socket.receiveData());
					String switchName = (String) jsonObject.get("switchName");
					String commandValue = (String) jsonObject.get("commandValue");
					String synchronizer = (String) jsonObject.get("synchronizer");
					
					// Sinkronisasi dari database ke service android
					if(synchronizer.equals("Y")) {
						// Update last setting by temporary DB
						List<AppArduinoSwitch> tempData = AppArduinoSwitchDAO.read();
						JSONObject obj = new JSONObject();
						for (AppArduinoSwitch o : tempData) {
							// JSONObject
							obj.put(o.getSwitchName(), o.getCommandValue());
						}
						
						socket.sendData(obj.toJSONString());
					}

					// Operasi arduino
					if(synchronizer.equals("N")) {
						// Update temporary DB
						AppArduinoSwitch o = new AppArduinoSwitch(null, switchName, commandValue);
						AppArduinoSwitchDAO.updateBySwitchName(o);
						// Send to arduino
						arduino.sendData(commandValue);
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Tutup server
				socket.disconnect();
			}
		}
	}

	public static void main(String[] args) {
		try {
			Thread t = new SocketService(PortConfig.SocketPort);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}