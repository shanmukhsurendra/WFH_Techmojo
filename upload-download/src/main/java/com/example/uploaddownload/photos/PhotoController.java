package com.example.uploaddownload.photos;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(value="/") //BASE
public class PhotoController{

	@Autowired
	PhotoService photoService;
	
	Logger log = LoggerFactory.getLogger(PhotoController.class);

//	@GetMapping("/")
//	public String landing() {
//		System.out.println("---------------");
//		return "index"; // WIll be taken as a welcome template
//	}

    @GetMapping("/photos/upload")									// Loads photo upload screen
    public String uploadPhoto(Model model) {
    	log.info("------------------------Upload screen loaded!");
        model.addAttribute("uploadMessage", "Please upload an appropriate file!!");
    	return "uploadPhoto";

    }
	
    @PostMapping("/photos/add")										// Adds uploaded photo as  a multipartfile into mongodb
    public String addPhoto(
    						@RequestParam("image") MultipartFile image, Model model) throws IOException {
    	
    	String title = image.getOriginalFilename();
    	log.info("------------------------Uploaded photo : " + title);
        String id = photoService.addPhoto(title, image);
        return "redirect:/photos/" + id;
    }

	@GetMapping("/photos")
	public String getAllphotos(Model model) throws IOException {
		
		List<Photo> allPhotosList = photoService.getAll();
		log.info("-------------------------All photos RETRIEVED!!!");
		model.addAttribute("allPhotos", allPhotosList);
		return "allPhotos";
	}


	@GetMapping("/photos/{id}")										// Retrieving photo from mongo onto screen by Id
	public String getPhoto(@PathVariable String id, Model model) {
		Photo photo = photoService.getPhoto(id);
		// Helps passing attributes
		 model.addAttribute("title", photo.getTitle());
	     model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
	     log.info(photo.getTitle() + " ------------------- retrived by Id");
	     
	     return "photos";

	}
	
	

	
}
