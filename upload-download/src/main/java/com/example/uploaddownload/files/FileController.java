package com.example.uploaddownload.files;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.uploaddownload.photos.PhotoService;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

import javax.servlet.http.HttpServletResponse;


@Controller

public class FileController {

    @Autowired
    FileService fileService;
    
    @Autowired 
    PhotoService photoService;
    
    
	private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/files/upload")												//loads file upload screen
    public String uploadVideo(Model model) {
        model.addAttribute("message", "hello");
        log.info("File upload screen........LOADED!!!!");
        return "uploadFile";
    }

    @PostMapping("/files/add")													//passing uploaded file as a multipartfie from file upload screen
    public String addFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    	
    	try {
            fileService.addFile(file.getOriginalFilename(), file);
            log.info("New File uploaded-----------file!!!!");
            return "redirect:/files";
    	}catch (Exception e) {
    		
            log.info("New File upload FAILED-----------FAILED!!!!");
    		return "failure";
		}
    }


    @GetMapping("/files/{id}")													//getting file through Id
    public String getFile(@PathVariable String id, Model model) throws IllegalStateException, IOException {
  
    	GridFSFile file = fileService.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));   	
    	String fileType = file.getMetadata().get("_contentType").toString();

    	if(fileType.equals("video/mp4")) {
    		model.addAttribute("title", file.getFilename());
            model.addAttribute("url", "/streamVideo/" + id); // this url provides streaming of video
            												// from the streamVideo() as a response
            return "videos";
    	}
    	
    	else if((fileType.contains("application") || fileType.equals("text/plain"))  ||  (fileType.contains("image")) ) {
            model.addAttribute("gridFsFile", file);
            
            return "FileView";
            
		} else {
			
    		return "failure";
    	}

    }
    

	@GetMapping("/files")
	public String getAllFiles(Model model) throws IOException, InterruptedException {
		GridFSFindIterable allFilesList = fileService.getAllFiles();
		model.addAttribute("allFiles", allFilesList);
		log.info("-------------------------All files RETRIEVED!!!");
		return "allFiles";
		
	}
	
    @GetMapping("/streamVideo/{id}")											//streaming a video as response
    public void streamVideo(@PathVariable String id, Model model, HttpServletResponse response) throws IllegalStateException, IOException {

    	GridFSFile file = fileService.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    	log.info("Streaming video---------" + file.getFilename());
        Files videoFile = fileService.getOneVideo(file);
        FileCopyUtils.copy(videoFile.getStream(), response.getOutputStream());
    }

}