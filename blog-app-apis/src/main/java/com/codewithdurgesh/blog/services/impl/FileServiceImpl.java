package com.codewithdurgesh.blog.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.services.FileService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
	
	@Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get the original file name
        String name = file.getOriginalFilename();

        // Generate a random ID
        String randomID = UUID.randomUUID().toString();

        // Concatenate the random ID with the file extension
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        // Full path to save the file
        String filePath = path + File.separator + fileName1;

        // Create the folder if it doesn't exist
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs(); // Use mkdirs() to create parent directories if needed
        }

        // Copy the file to the specified path
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Return the original file name (you may modify this if needed)
        return fileName1;
    }

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName; 
		InputStream is=new FileInputStream(fullPath); 
		//db logic to return inpustream
		return is;
	}
	
	
    
  
}
