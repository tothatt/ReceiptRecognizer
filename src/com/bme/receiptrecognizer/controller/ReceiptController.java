package com.bme.receiptrecognizer.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abbyy.ocrsdk.App;
import com.bme.receiptrecognizer.model.ClientSettings;
import com.bme.receiptrecognizer.model.DataFromReceipt;
import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.service.TextParserService;
import com.bme.receiptrecognizer.service.XmlParserService;
import com.bme.receiptrecognizer.service.XmlWriterService;

@Controller
public class ReceiptController {

	private XmlParserService xmlParserService;

	private XmlWriterService xmlWriterService;

	private TextParserService textParserService;

	@Autowired
	public void setXmlParserService(XmlParserService xmlParserService) {
		this.xmlParserService = xmlParserService;
	}

	@Autowired
	public void setXmlWriterService(XmlWriterService xmlWriterService) {
		this.xmlWriterService = xmlWriterService;
	}

	@Autowired
	public void setTextParserService(TextParserService textParserService) {
		this.textParserService = textParserService;
	}

	@RequestMapping("/receipt/")
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		System.out.println(name);
		return "index";
	}

	@RequestMapping("/receipt/{name}")
	public ModelAndView welcome(@PathVariable String name) {
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@RequestMapping(value = "/receipt/upload", method = RequestMethod.POST)
	public ModelAndView singleFileUpload(@RequestParam("textFile") MultipartFile file,
			@RequestParam("fileName") String fileName) {
		if (file.isEmpty()) {
			return new ModelAndView("index");
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(ClientSettings.RESOURCE_URL + fileName);
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/receipt/processimage/{name}", method = RequestMethod.GET)
	public ModelAndView processImage(HttpServletRequest request, @PathVariable String name) throws Exception {
		App.performRecognition(ClientSettings.RESOURCE_URL + name, ClientSettings.RESOURCE_URL + name + ".result.xml",
				"Hungarian");
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@ResponseBody
	@RequestMapping(value = "/receipt/images/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(HttpServletRequest request, @PathVariable String name) throws IOException {
		InputStream in = new FileInputStream(ClientSettings.RESOURCE_URL + name);
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/receipt/imageinfo/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Receipt imageInfo(HttpServletRequest request, @PathVariable String name) throws IOException {
		Receipt receipt = xmlParserService.parsexml(name + ".result.xml");
		receipt.setLines(textParserService.extractLinesFromReceipt(receipt));
		receipt.setImageUrl(ClientSettings.RESOURCE_URL + name);
		return receipt;
	}

	@RequestMapping(value = "/receipt/changechar", method = RequestMethod.POST)
	public ModelAndView changeChar(@RequestBody Receipt receipt) {
		xmlWriterService.updateXmlFile(receipt);
		return new ModelAndView("receipt", "szamlanev", receipt.getName());
	}

	@RequestMapping(value = "/receipt/receiptdetails/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public ModelAndView receiptInfo(@PathVariable String name) {
		return new ModelAndView("receipt-info", "szamlanev", name);
	}

	@RequestMapping(value = "/receipt/receiptinfo/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody DataFromReceipt receipInfo(HttpServletRequest request, @PathVariable String name) {
		Receipt receipt = xmlParserService.parsexml(name + ".result.xml");
		receipt.setLines(textParserService.extractLinesFromReceipt(receipt));
		DataFromReceipt data = new DataFromReceipt();
		data.setDate(textParserService.getDatesFromReceipt(receipt));
		data.setAddress(textParserService.getAddressesFromReceipt(receipt));
		data.setCompany(textParserService.getCompanyNameFromReceipt(receipt));
		data.setFinalValue(textParserService.getFinalAmount(receipt));
		return data;
	}

}
