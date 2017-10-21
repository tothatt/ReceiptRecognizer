package com.bme.receiptrecognizer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abbyy.ocrsdk.App;
import com.bme.receiptrecognizer.exceptions.ReceiptException;
import com.bme.receiptrecognizer.model.ClientSettings;
import com.bme.receiptrecognizer.model.DataFromReceipt;
import com.bme.receiptrecognizer.model.Item;
import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.service.DataFromReceiptService;
import com.bme.receiptrecognizer.service.ReceiptService;
import com.bme.receiptrecognizer.service.TextParserService;
import com.bme.receiptrecognizer.service.XmlParserService;
import com.bme.receiptrecognizer.service.XmlWriterService;

@Controller
public class ReceiptController {

	private XmlParserService xmlParserService;

	private XmlWriterService xmlWriterService;

	private TextParserService textParserService;
	
	private DataFromReceiptService dataFromReceiptService;
	
	private ReceiptService receiptService;

	@Autowired
	public void setXmlParserService(XmlParserService xmlParserService) {
		this.xmlParserService = xmlParserService;
	}
	
	@Autowired
	public void setDataFromReceiptService(DataFromReceiptService dataFromReceiptService) {
		this.dataFromReceiptService = dataFromReceiptService;
	}
	
	@Autowired
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	@Autowired
	public void setXmlWriterService(XmlWriterService xmlWriterService) {
		this.xmlWriterService = xmlWriterService;
	}

	@Autowired
	public void setTextParserService(TextParserService textParserService) {
		this.textParserService = textParserService;
	}

	@RequestMapping("/addreceipt")
	public String addreceipt() {
		if (Files.notExists(Paths
				.get(ClientSettings.RESOURCE_URL + SecurityContextHolder.getContext().getAuthentication().getName()))) {
			boolean success = (new File(
					ClientSettings.RESOURCE_URL + SecurityContextHolder.getContext().getAuthentication().getName()))
							.mkdir();
			if (success)
				System.out.println("Fodler creation succesful");
		}
		return "add-receipt";
	}

	@RequestMapping("/receipt/{name}")
	public ModelAndView welcome(@PathVariable String name) {
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView singleFileUpload(@RequestParam("textFile") MultipartFile file,
			@RequestParam("fileName") String fileName) {
		if (file.isEmpty()) {
			return new ModelAndView("index");
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(ClientSettings.RESOURCE_URL
					+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + fileName);
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/processimage/{name}", method = RequestMethod.GET)
	public ModelAndView processImage(HttpServletRequest request, @PathVariable String name) throws Exception {
		App.performRecognition(ClientSettings.RESOURCE_URL
				+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name,
				ClientSettings.RESOURCE_URL + name + ".result.xml", "Hungarian");
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@ResponseBody
	@RequestMapping(value = "/images/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(HttpServletRequest request, @PathVariable String name) throws IOException {
		InputStream in = new FileInputStream(ClientSettings.RESOURCE_URL
				+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name);
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/imageinfo/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Receipt imageInfo(HttpServletRequest request, @PathVariable String name) throws IOException {
		Receipt r = receiptService.getReceipt(name, SecurityContextHolder.getContext().getAuthentication().getName());
		if(r != null)
			return r;
		Receipt receipt = xmlParserService.parsexml(ClientSettings.RESOURCE_URL
				+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name + ".result.xml");
		receipt.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
		receipt.setName(name);
		receipt.setLines(textParserService.extractLinesFromReceipt(receipt));
		receipt.setImageUrl(ClientSettings.RESOURCE_URL
				+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name);
		receipt.setLines(textParserService.extractLinesFromReceipt(receipt));
		receiptService.add(receipt);
		return receipt;
	}

	@RequestMapping(value = "/changechar", method = RequestMethod.POST)
	public ModelAndView changeChar(@RequestBody Receipt receipt) {
		xmlWriterService.updateXmlFile(receipt);
		return new ModelAndView("receipt", "szamlanev", receipt.getName());
	}

	@RequestMapping(value = "/receiptdetails/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public ModelAndView receiptInfo(@PathVariable String name) {
		return new ModelAndView("receipt-info", "szamlanev", name);
	}

	@ExceptionHandler(ReceiptException.class)
	public @ResponseBody ReceiptException handleCustomException(ReceiptException ex) {
		return ex;
	}

	@RequestMapping(value = "/error/{errorCode}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public ModelAndView errorPage(@PathVariable String errorCode) {
		return new ModelAndView("error", "error", new ReceiptException(errorCode));
	}

	@RequestMapping(value = "/receiptinfo/{name}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody DataFromReceipt receipInfo(HttpServletRequest request, @PathVariable String name) {
		DataFromReceipt dr = dataFromReceiptService.getDataFromReceipt(name, SecurityContextHolder.getContext().getAuthentication().getName());
		if(dr != null)
			return dr;
		Receipt receipt = receiptService.getReceipt(name, SecurityContextHolder.getContext().getAuthentication().getName());
		if(receipt == null) {
			receipt = new Receipt();
			receipt.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
			receipt.setName(name);
			receipt = xmlParserService.parsexml(ClientSettings.RESOURCE_URL
					+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name + ".result.xml");
			receipt.setImageUrl(ClientSettings.RESOURCE_URL
					+ SecurityContextHolder.getContext().getAuthentication().getName() + "/" + name);
			receipt.setLines(textParserService.extractLinesFromReceipt(receipt));
			receiptService.add(receipt);
		}
		DataFromReceipt data = new DataFromReceipt();
		data.setName(name);
		data.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
		data.setDate(textParserService.getDatesFromReceipt(receipt));
		data.setAddress(textParserService.getAddressesFromReceipt(receipt));
		data.setCompany(textParserService.getCompanyNameFromReceipt(receipt));
		data.setFinalValue(textParserService.getFinalAmount(receipt));
		dataFromReceiptService.add(data);
		return data;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public String allReceipt() {
		return "all-receipt";
	}

	@RequestMapping(value = "/allreceiptdata", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody List<DataFromReceipt> receipInfos(HttpServletRequest request) {
		List<DataFromReceipt> data = dataFromReceiptService.listAllByUser(SecurityContextHolder.getContext().getAuthentication().getName());
		return data;
	}

}
