package com.example.uploaddownload.photos;


import java.io.IOException;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
 
    @Autowired
    private PhotoRepository photoRepo;
 
    public String addPhoto(String title, MultipartFile file) throws IOException { 
    	
    	Binary bImageBinary = new Binary(BsonBinarySubType.BINARY, file.getBytes());
    	// Converting the multiPartFile to a binaryImage first
    	
    	
        Photo photo = new Photo(title, bImageBinary); 
        
        photo = photoRepo.insert(photo);
        return photo.getId(); 
    }
 
    public Photo getPhoto(String id) {
    	Photo photo = photoRepo.findById(id).get();
    	
        return photo; 
    }
    
    public List<Photo> getAll() {
    	return photoRepo.findAll();
    }
}
