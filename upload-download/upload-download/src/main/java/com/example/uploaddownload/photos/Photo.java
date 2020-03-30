package com.example.uploaddownload.photos;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Photo {
	

	@Id
	String id;
	
	String title;
	
	Binary image;

	public Photo() {
		// TODO Auto-generated constructor stub
	}
	
	public Photo(String s, Binary b) {
		this.title = s;
		this.image = b;
	}
	
	public void setImage(Binary binary) {
		// TODO Auto-generated method stub
		this.image = binary;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
    @Override
    public String toString() {
        return "Photo [id=" + id + ", title=" + title + ", image=" + image + "]";
    }

	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	public Binary getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}
}
