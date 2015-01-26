package com.thomsonreuters.ce.dbor.file;

import java.io.File;

public class DateOrderedFileWrapper implements Comparable {

    private File file;
    
    public DateOrderedFileWrapper(File file) {
        this.file = file;
    }

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
        
		long FirstLastModifyDate=this.file.lastModified();
		long SecondLastModifyDate=((DateOrderedFileWrapper)o).getFile().lastModified();
		
		
		if (FirstLastModifyDate<SecondLastModifyDate)
		{
			return -1;
		}
		else if (FirstLastModifyDate>SecondLastModifyDate)
		{
			return 1;
		}
		else
		{
			return 0;
		}        
        
        
	}
	
    public File getFile() {
        return this.file;
    }

}
