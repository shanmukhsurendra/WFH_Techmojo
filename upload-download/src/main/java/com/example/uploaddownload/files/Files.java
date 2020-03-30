package com.example.uploaddownload.files;

import java.io.InputStream;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Files")
public class Files {

	@Id
	String id;
    String title;
    InputStream stream;
    
    public String getTitle() {
        return title;
    }
    
    public InputStream getStream() {
        return stream;
    }
	public void setTitle(String string) {
		// TODO Auto-generated method stub
		this.title = string;
	}

	public void setStream(InputStream inputStream) {
		// TODO Auto-generated method stub
		this.stream= inputStream;
	}
	
    @Override
    public String toString() {
        return "File [title=" + title + "]";
    }


}
