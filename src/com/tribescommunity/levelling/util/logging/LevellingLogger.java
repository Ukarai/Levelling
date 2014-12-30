package com.tribescommunity.levelling.util.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import com.tribescommunity.levelling.Levelling;

public class LevellingLogger {

	private File file;
	private BufferedWriter bw;

	public LevellingLogger(Levelling plugin, String logFileName) {
		File folder = new File(plugin.getDataFolder() + File.separator + "Logs");
		folder.mkdirs();

		file = new File(folder, logFileName + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			bw = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void info(String msg) {
		try {
			bw.append(getDate() + msg);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deinit() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDate() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTimeInMillis(System.currentTimeMillis());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		return "[" + day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second + "] ";
	}

}
