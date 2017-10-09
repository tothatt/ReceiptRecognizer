package com.abbyy.ocrsdk;

import com.bme.receiptrecognizer.model.ClientSettings;
import com.bme.receiptrecognizer.model.Receipt;

public class App {

	Receipt receipt;

	public static void performRecognition(String imgPath, String xmlPath, String language) throws Exception {
		String outputPath = xmlPath;
		// argList now contains list of source images to process

		ProcessingSettings settings = new ProcessingSettings();
		settings.setLanguage(language);
		settings.setOutputFormat(ProcessingSettings.OutputFormat.xml);

		Task task = null;
		System.out.println("Uploading file..");
		System.out.println(imgPath);
		restClient = new Client();
		restClient.serverUrl = "http://cloud.ocrsdk.com";
		restClient.applicationId = ClientSettings.APPLICATION_ID;
		restClient.password = ClientSettings.PASSWORD;

		task =  restClient.processImage(imgPath, settings);

		waitAndDownloadResult(task, outputPath);
	}

	/**
	 * Wait until task processing finishes
	 */
	private static Task waitForCompletion(Task task) throws Exception {
		// Note: it's recommended that your application waits
		// at least 2 seconds before making the first getTaskStatus request
		// and also between such requests for the same task.
		// Making requests more often will not improve your application
		// performance.
		// Note: if your application queues several files and waits for them
		// it's recommended that you use listFinishedTasks instead (which is
		// described
		// at http://ocrsdk.com/documentation/apireference/listFinishedTasks/).
		while (task.isTaskActive()) {

			Thread.sleep(5000);
			System.out.println("Waiting..");
			task = restClient.getTaskStatus(task.Id);
		}
		return task;
	}

	/**
	 * Wait until task processing finishes and download result.
	 */
	private static void waitAndDownloadResult(Task task, String outputPath) throws Exception {
		task = waitForCompletion(task);

		if (task.Status == Task.TaskStatus.Completed) {
			System.out.println("Downloading..");
			restClient.downloadResult(task, outputPath);
			System.out.println("Ready");
		} else if (task.Status == Task.TaskStatus.NotEnoughCredits) {
			System.out.println("Not enough credits to process document. "
					+ "Please add more pages to your application's account.");
		} else {
			System.out.println("Task failed");
		}

	}

	private static Client restClient;
}
