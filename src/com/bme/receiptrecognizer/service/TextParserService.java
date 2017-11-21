package com.bme.receiptrecognizer.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.model.XmlChar;

public class TextParserService {
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

	public Date getDatesFromReceipt(Receipt receipt) {
		Date date = null;
		String[] acceptedFormats = { "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd.", "yyyy.MM.dd. HH:mm" };
		Pattern p = Pattern.compile("\\d{4}.\\d{2}.\\d{2}(.)*(\\d{2}.\\d{2})?");
		for (Map.Entry<Integer, String> entry : receipt.getLines().entrySet()) {
			Matcher m = p.matcher(entry.getValue());
			if (m.find()) {
				try {
					date = DateUtils.parseDate(m.group(), acceptedFormats);
				} catch (ParseException e) {
				}
			}

		}
		return date;

	}

	public String getAddressesFromReceipt(Receipt receipt) {
		String address = "";
		Pattern p = Pattern.compile("\\d{4}(.)*(\\d{3}|\\d{2}|\\d{1})\\.");
		Matcher m = p.matcher(getLinesFromReceipt(receipt, 0, 4));
		if (m.find()) {
			address = m.group();
		}
		return address;

	}

	public String getCompanyNameFromReceipt(Receipt receipt) {
		String company = "";
		Pattern p = Pattern.compile(""
				+ "(.)*(Kkt|Kht|Bt|Kv|Kft|Rt|ZRt|NyRt|KKT|KHT|BT|KV|KFT|RT|ZRT|NYRT|kkt|kht|bt|kv|kft|rt|zrt|nyrt)(\\.)?");
		Matcher m = p.matcher(getLinesFromReceipt(receipt, 0, 2));
		if (m.find()) {
			company = m.group();
		}
		return company;

	}

	public String getItemsFromReceipt(Receipt receipt) {
		return "";
	}

	public String getFinalAmount(Receipt receipt) {
		String amount = "";
		Pattern p = Pattern
				.compile("(Összesen|összesen|ÖSSZESEN)(:)?(\\d{6}|\\d{5}|\\d{4}|\\d{3}|\\d{2}|\\d{1})(Ft|ft|FT)");
		String textWithoutSpace = getFullText(receipt).replace(" ", "");
		Matcher m = p.matcher(textWithoutSpace);
		if (m.find()) {
			amount = m.group();
			amount = amount.toLowerCase().replace("összesen", "");
			amount = amount.toLowerCase().replace(":", "");
			amount = amount.toLowerCase().replace("ft", "");
		}
		return amount;
	}
	
	private String getLinesFromReceipt(Receipt receipt, int from, int to) {
		StringBuilder lines = new StringBuilder();
		List<Integer> sortedKeys = new ArrayList<Integer>(receipt.getLines().keySet());
		Collections.sort(sortedKeys);
		int k = 0;
		for (int i : sortedKeys) {
			if (from <= k && k <= to) {
				lines.append(receipt.getLines().get(i));
				lines.append(" ");
			}
			k++;
		}
		return lines.toString();
	}
	
	private String getFullText(Receipt receipt){
		StringBuilder lines = new StringBuilder();
		List<Integer> sortedKeys=new ArrayList<Integer>(receipt.getLines().keySet());
		Collections.sort(sortedKeys);
		for(int i : sortedKeys){
				lines.append(receipt.getLines().get(i));
				lines.append(" ");
			
		}
		return lines.toString();
	}
}
