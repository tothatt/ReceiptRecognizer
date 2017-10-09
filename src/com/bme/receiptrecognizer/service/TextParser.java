package com.bme.receiptrecognizer.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.model.XmlChar;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class TextParser {
	public List<String> extractWordsFromReceipt() {
		StringBuilder textFromReceipt = new StringBuilder();
		return null;
	}

	public Map<Integer, String> extractLinesFromReceipt(Receipt receipt) {
		Map<Integer, String> retval = new HashMap<>();
		Map<Integer, StringBuilder> lines = new HashMap<>();
		for (XmlChar c : receipt.getChars()) {
			if (lines.containsKey(c.getLineId())) {
				lines.get(c.getLineId()).append(c.getS());
			} else {
				StringBuilder db = new StringBuilder();
				db.append(c.getS());
				lines.put(c.getLineId(), db);
			}
		}
		for (Map.Entry<Integer, StringBuilder> entry : lines.entrySet()) {
			retval.put(entry.getKey(), entry.getValue().toString());
		}
		return retval;
	}

	public List<String> nameEntityRec(Receipt receipt) {

		return null;
	}

	public List<Date> getDatesFromReceipt(Receipt receipt) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Pattern p = Pattern.compile("(\\d{4}.\\d{2}.\\d{2}.\\d{2}.\\d{2})");
		for (Map.Entry<Integer, String> entry : receipt.getLines().entrySet()) {
			String date = entry.getValue().replaceAll("[^0-9]", "");
			Matcher m = p.matcher(entry.getValue());
			try{
			    Date d = dateFormat.parse(date);
			    System.out.println(d.toString());
			}catch (ParseException e) {
			}
			
		}
		return null;

	}
}
