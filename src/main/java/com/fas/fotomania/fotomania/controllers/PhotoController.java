package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Photo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IPhotoService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class PhotoController {

    @Autowired
    IPhotoService photoService;

    @Autowired
    IUserService userService;

    @RequestMapping(value="/home/company/photo", method= RequestMethod.GET)
    public String listPhotos(Model model, Principal principal) {
        User currentUser=userService.findUserByEmail(principal.getName());
        model.addAttribute("photos",photoService.findPhotosByCompany(currentUser.getId()));
        return "photoList.html";
    }

    @RequestMapping(value = "/home/company/photo/add", method = RequestMethod.GET)
    public String createAddPhoto(Model model){
        model.addAttribute("photo", new Photo());

        return "photoAdd.html";
    }

    @RequestMapping(value = "/home/company/photo/add", method = RequestMethod.POST)
    public String savePhoto(@Valid Photo photo, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal) {

        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            User currentUser=userService.findUserByEmail(principal.getName());
            photo.setUser(currentUser);
            //System.out.println(photo.getImage().toString());
            photoService.savePhoto(photo);
            redirectAttribute.addFlashAttribute("successMessage", "Tool registered successfully");
            return "redirect:/home/company/photo";
        }
        return "redirect:/home/company/photo/add";
    }

    /*
    @RequestMapping(value = "/home/company/photo/{photo_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("photo_id") int photoId) throws IOException {

        byte[] imageContent = photoService.findById(photoId).get().getImage();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }
     */

    @RequestMapping(value = "/home/company/photo/{photo_id}", method = RequestMethod.GET)
    public void getImage(@PathVariable("photo_id") int photoId, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {


        Photo imageContent = photoService.findById(photoId).get();
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(imageContent.getImage());

        System.out.println(imageContent.getImage());
        for (byte value:imageContent.getImage()) {
            System.out.print(value);
        }
        System.out.print("\n");
        System.out.print("\n");

        response.getOutputStream().close();
    }
}
