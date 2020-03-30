
package com.example.uploaddownload.files;

import com.itextpdf.kernel.pdf.PdfWriter;


import com.mongodb.client.gridfs.model.GridFSFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.util.Map;


@Component("FileView")
public class FileView extends AbstractView {

	private static final Logger log = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileService fileService;
	
  @Override
  protected void renderMergedOutputModel(Map<String, Object> model,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
	  
      response.setHeader("Content-Disposition", "inline");
	  GridFSFile file = (GridFSFile) model.get("gridFsFile");
	  InputStream inputStream = fileService.gridFsOperations.getResource(file).getInputStream();
	  
      //IText API
      PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());				//Assigning PDF writer as response output stream to the FileView component

      
      
      if(file.getMetadata().containsKey("compressed") && (boolean) file.getMetadata().get("compressed") == true) {
    	  
    	  log.info("Its a compressed file!!!  Decompressing '" +  file.getFilename() + "'.....");
    	  byte[] compressedByteArr = fileService.decompressData(inputStream);
    	  pdfWriter.write(compressedByteArr);
      } else {
    	   
    	  log.info("Its  NOT a compressed file!!!  Rendering '" +  file.getFilename() + "' as existed.....");
    	  pdfWriter.write(inputStream.readAllBytes());
      }
      
      pdfWriter.close();

  }
}
