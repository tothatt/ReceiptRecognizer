package com.bme.receiptrecognizer.service;
import com.abbyy.ocrsdk.*;
import java.util.*;
import java.io.*;

public class ProcessManyFiles {
	private static void performRecognition(Vector<String> argList, String language)
		throws Exception {
		
		ProcessingSettings settings = new ProcessingSettings();
		settings.setLanguage(language);
		settings.setOutputFormat( ProcessingSettings.OutputFormat.xml );

		String sourceDirPath = argList.get(0);
		String targetDirPath = argList.get(1);
		setOutputPath( targetDirPath );

		File sourceDir = new File(sourceDirPath);

		File[] listOfFiles = sourceDir.listFiles();

		Vector<String> filesToProcess = new Vector<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile()) {
				String fullPath = file.getAbsolutePath();
				filesToProcess.add(fullPath);
			} 		
		}

		Map<String,String> taskIds = submitAllFiles(filesToProcess, settings);

		waitAndDownloadResults( taskIds );
	}

	private static void performRemoteFileRecognition( Vector<String> argList, String language )
		throws Exception {

		ProcessingSettings settings = new ProcessingSettings();
		settings.setLanguage( language);
		settings.setOutputFormat( ProcessingSettings.OutputFormat.xml );

		String remoteFile = argList.get(0);
		String targetDirPath = argList.get(1);
		setOutputPath( targetDirPath );

		Vector<String> urlsToProcess = new Vector<String>();
		if( remoteFile.startsWith( "http://" ) || remoteFile.startsWith( "https://" ) ) {
			urlsToProcess.add(remoteFile);
		} else {
			// Get url list from remoteFile
			BufferedReader br = new BufferedReader( new FileReader( remoteFile ) );
			try {
				String line;
				while( (line = br.readLine()) != null ) {
					urlsToProcess.add( line );
				}
			} finally {
				br.close();
			}
		}

		Map<String,String> taskIds = submitRemoteUrls(urlsToProcess, settings);
		waitAndDownloadResults( taskIds );
	}

	/**
	* Submit all files for recognition
	*
	* @return map task id, file name for submitted tasks
	*/
	private static Map<String,String> submitAllFiles(Vector<String> fileList, ProcessingSettings settings) throws Exception {
		System.out.println( String.format( "Uploading %d files..", fileList.size() ));

		Map<String,String> taskIds = new HashMap<String, String>();

		for (int fileIndex = 0; fileIndex < fileList.size(); fileIndex++ ) {
			String filePath = fileList.get(fileIndex);

			File file = new File(filePath);
			String fileBase = file.getName();
			if (fileBase.indexOf(".") > 0) {
				fileBase = fileBase.substring(0, fileBase.lastIndexOf("."));
			}

			System.out.println( filePath );
			Task task = restClient.processImage( filePath, settings );
			taskIds.put(task.Id, fileBase + settings.getOutputFileExt());	
		}
		return taskIds;
	}

	private static Map<String,String> submitRemoteUrls(Vector<String> urlList, ProcessingSettings settings) throws Exception {
		System.out.println( String.format( "Processing %d urls...", urlList.size() ));
		Map<String,String> taskIds = new HashMap<String,String>();

		for (int i = 0; i < urlList.size(); i++ ) {
			String url = urlList.get(i);

			String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
			String fileBase  = fileName.substring(0, fileName.lastIndexOf('.'));

			System.out.println( url );
			Task task = restClient.processRemoteImage( url, settings );
			taskIds.put(task.Id, fileBase + settings.getOutputFileExt());
		}
		return taskIds;
	}

	/**
	* Wait until tasks are finished and download recognition results
	*/
	private static void waitAndDownloadResults( Map<String,String> taskIds ) throws Exception {
		// Call listFinishedTasks while there are any not completed tasks from taskIds

		// Please note: API call 'listFinishedTasks' returns maximum 100 tasks
		// So, to get all our tasks we need to delete tasks on server. Avoid running
		// parallel programs that are performing recognition with the same Application ID
	
		System.out.println( "Waiting.." );
		
		while ( taskIds.size() > 0 ) {
				Task[] finishedTasks = restClient.listFinishedTasks();

				for ( int i = 0; i < finishedTasks.length; i++ ) {
					Task task = finishedTasks[i];
					if( taskIds.containsKey( task.Id ) ) {
						// Download task
						String fileName = taskIds.remove(task.Id);

						if( task.Status == Task.TaskStatus.Completed ) {
						String outputPath = outputDir + "/" + fileName;
						restClient.downloadResult(task, outputPath);
						System.out.println( String.format( "Ready %s, %d remains", fileName, taskIds.size() ) );
						} else {
							System.out.println( String.format( "Failed %s, %d remains", fileName, taskIds.size() ));
						}

					} else {
						System.out.println( String.format( "Deleting task %s from server", task.Id ) );
					}
					restClient.deleteTask( task.Id );
				}
				Thread.sleep(2000);
		}
	}


	/**
	* Set output directory and create it if necessary
	*/
	private static void setOutputPath(String value) {
		outputDir = value;
		File dir = new File(outputDir);
		if (!dir.exists() ) {
			dir.mkdirs();	
		}
	}


	private static Client restClient;
	private static String outputDir;
}
