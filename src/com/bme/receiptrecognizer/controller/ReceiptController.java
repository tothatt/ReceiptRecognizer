package com.bme.receiptrecognizer.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bme.receiptrecognizer.model.Receipt;
import com.bme.receiptrecognizer.service.App;
import com.bme.receiptrecognizer.service.ClientSettings;
import com.bme.receiptrecognizer.service.XmlParser;
import com.bme.receiptrecognizer.service.XmlWriter;

@Controller
public class ReceiptController {

	private static ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/receipt/{name}")
	public ModelAndView welcome(@PathVariable String name) {
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView singleFileUpload(@RequestParam("textFile") MultipartFile file, @RequestParam("fileName") String fileName) {
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
	
	@RequestMapping(value = "/processimage/{name}", method = RequestMethod.GET)
	public ModelAndView processImage(HttpServletRequest request, @PathVariable String name) throws Exception {
		App.performRecognition(ClientSettings.RESOURCE_URL + name , ClientSettings.RESOURCE_URL + name + ".result.xml", "Hungarian");
		return new ModelAndView("receipt", "szamlanev", name);
	}

	@ResponseBody
	@RequestMapping(value = "/images/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(HttpServletRequest request, @PathVariable String name) throws IOException {
		System.out.println("/" + name + ".png");
		InputStream in = new FileInputStream(ClientSettings.RESOURCE_URL + name);
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/imageinfo/{name}", method = RequestMethod.GET)
	public @ResponseBody String imageInfo(HttpServletRequest request, @PathVariable String name) throws IOException {
		XmlParser xmlParser = new XmlParser();
		Receipt receipt = xmlParser.parsexml(name + ".result.xml");
		receipt.setImageUrl(name + ".png");
		System.out.println(receipt.chars.size());
		return mapper.writeValueAsString(receipt);
	}

	@RequestMapping(value = "/changechar", method = RequestMethod.POST)
	public ModelAndView changeChar(@RequestBody String body) {
		Receipt receipt = null;
		System.out.println(body);
		try {
			receipt = mapper.readValue(body, Receipt.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlWriter xmlWriter = new XmlWriter();
		xmlWriter.updateXmlFile(receipt);
		return new ModelAndView("receipt", "szamlanev", receipt.getName());
	}
}
