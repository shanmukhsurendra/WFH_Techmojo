package com.example.uploaddownload;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UploadDownloadApplication {

	public static void main(String[] args) throws IOException {
		
		new RunHelper().killAndRun();
		
		SpringApplication.run(UploadDownloadApplication.class, args);
	}

}
