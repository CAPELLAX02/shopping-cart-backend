package com.capellax.shoppingCart.controller;

import com.capellax.shoppingCart.dto.ImageDTO;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.Image;
import com.capellax.shoppingCart.response.ApiResponse;
import com.capellax.shoppingCart.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId
    ) {
        try {
            List<ImageDTO> imageDTOs = imageService.saveImages(productId, files);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDTOs));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed!", exception));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(
            @PathVariable Long imageId
    ) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length())
        );
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\""
                )
                .body(resource);
    }

    @PutMapping("/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(
            @PathVariable Long imageId,
            @RequestBody MultipartFile file
    ) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", image));
            }

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Image not found!", exception));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(
            @PathVariable Long imageId
    ) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", image));
            }

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Image not found!", exception));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Delete failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
