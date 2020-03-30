package com.example.uploaddownload.files;

import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

@Service
public class FileService {

	@Autowired
	GridFsTemplate gridFsTemplate;
	
	@Autowired
	GridFsOperations gridFsOperations;
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	
	public Files getOneVideo(GridFSFile gridFSFile) throws IllegalStateException, IOException {
		Files videoFile = new Files();
		videoFile.setTitle(gridFSFile.getMetadata().get("title").toString()); 
		videoFile.setStream(gridFsOperations.getResource(gridFSFile).getInputStream());
        return videoFile; 
	}
	


	public GridFSFindIterable getAllFiles() {
//		gridFsTemplate
		return gridFsTemplate.find(new Query());
	}

    public String addFile(String fileName, MultipartFile file) throws IOException {
    	
        InputStream inputStream = file.getInputStream();

        
        String contentType = file.getContentType();
        DBObject metaData = new BasicDBObject(); // metaData is an optional key-value pair information
        metaData.put("type", contentType);
        metaData.put("title", fileName.split(" ")[0]);
        metaData.put("compressed", false);
        
        
        if(contentType.equals("application/pdf")) {
        	
//          COMPRESSION : Create a new file to write using FileOutPutStream, which saves the compressed file
//          				passing the byte[] of already loaded file to Defalter().
        	log.info("upload size before compress, BEFORE COMPRESS : " + file.getSize());
            log.info(fileName + " is being compressed!!!");
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\test\\eclipse-workspace\\upload-download\\Temp");

            compressData(inputStream, fileOutputStream);
            metaData.put("compressed", true);
            
            // Compressing only PDF
            inputStream = new FileInputStream("C:\\Users\\test\\eclipse-workspace\\upload-download\\Temp");
        }

        
//        

        
//      Passing the newly compressed file as InputStream to the gridFsTemplate.store() --------------------------
        ObjectId id = gridFsTemplate.store(inputStream, fileName, contentType, metaData);
        return id.toString();
    }
    
    
	public void compressData(InputStream in, OutputStream out) throws IOException {
		  byte[] data = in.readAllBytes();
		  Deflater d = new Deflater();
		  DeflaterOutputStream dout = new DeflaterOutputStream(out, d);
		  dout.write(data);
		  dout.close();
	}
	
	public byte[] decompressData(InputStream in) throws IOException {
		  InflaterInputStream infs = new InflaterInputStream(in);
		  ByteArrayOutputStream bout =new ByteArrayOutputStream(512);
		  int b;
		  while ((b = infs.read()) != -1) {
		    bout.write(b);
		  }
		  infs.close();
		  bout.close();
		  return bout.toByteArray();
		}
}
