package com.thomsonreuters.ce.dbor.file;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.thomsonreuters.ce.exception.SystemException;

public class FileUtilities {

	public static void MoveFile(File f1, File f2) {		
		
		try {
			
			Path SourcePath = Paths.get(f1.toURI());  
	        Path TargetPath = Paths.get(f2.toURI()); 
	        
	        Files.move(SourcePath, TargetPath, ATOMIC_MOVE);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SystemException("File: " + f1.getName()
					+ " can not be moved to " + f2.getAbsolutePath(), e);
		}

	}

	public static void CheckFolderExistence(String FolderName)
	{
		File thisFolder=new File(FolderName);

		if (!thisFolder.exists())
		{
			throw new SystemException("Folder: "+ FolderName+" doesn't exist! Please create it before starting this application");
		}
	}

	public static void cleanup(String WorkFolder,String ArrivalFolder,String FileExtension)
	{
		//get all files from working folder
		File f = new File(WorkFolder);
		File files[] = f.listFiles(new ExtensionFilter(FileExtension));

		for(int i = 0; i < files.length; i++)
		{
			String fileName = files[i].getName();
			File arrivalFile = new File(ArrivalFolder	+ fileName);
			File workFile = new File(WorkFolder	+  fileName);
			MoveFile(workFile,arrivalFile);
		}

	}

	/*
 	arrivalfolder=${FEEDS_IN}/oasdi/arrival/
	workfolder=${FEEDS_IN}/oasdi/work/
	archivefolder=${FEEDS_IN}/oasdi/archive/
	unprocfolder=${FEEDS_IN}/oasdi/unproc/
	 */
	public static String GetAbsolutePathFromEnv(String env_path)
	{
		String Absolute_Path="";
		int env_pos=env_path.indexOf("${");
		int current_pos=0;

		while (env_pos!=-1)
		{
			String before_str=env_path.substring(current_pos,env_pos);
			int env_end_pos=env_path.indexOf('}', env_pos);
			String env_str=env_path.substring(env_pos+2,env_end_pos);
			String absolute_str=System.getenv(env_str);
			Absolute_Path=Absolute_Path+before_str+absolute_str;	
			current_pos=env_end_pos+1;
			env_pos=env_path.indexOf("${", current_pos);
		}

		Absolute_Path=Absolute_Path+env_path.substring(current_pos);
		return Absolute_Path;
	}

}
