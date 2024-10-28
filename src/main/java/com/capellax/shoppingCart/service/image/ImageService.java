package com.capellax.shoppingCart.service.image;

import com.capellax.shoppingCart.dto.ImageDTO;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.Image;
import com.capellax.shoppingCart.model.Product;
import com.capellax.shoppingCart.repository.ImageRepository;
import com.capellax.shoppingCart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService iProductService;


    @Override
    public Image getImageById(
            Long imageId
    ) {
        return imageRepository
                .findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image " + imageId + " not found!"));
    }

    @Override
    public void deleteImageById(
            Long imageId
    ) {
        imageRepository
                .findById(imageId)
                .ifPresentOrElse(
                        imageRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("No image found with id " + imageId);
                        }
                );
    }

    @Override
    public List<ImageDTO> saveImages(
            Long productId,
            List<MultipartFile> files
    ) {
        Product product = iProductService.getProductById(productId);
        List<ImageDTO> savedImageDTOs = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getFileName();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(downloadUrl);
                imageRepository.save(savedImage);

                ImageDTO savedImageDTO = new ImageDTO();
                savedImageDTO.setImageId(savedImage.getId());
                savedImageDTO.setFileName(savedImage.getFileName());
                savedImageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDTOs.add(savedImageDTO);

            } catch (IOException | SQLException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return savedImageDTOs;
    }

    @Override
    public void updateImage(
            MultipartFile file,
            Long imageId
    ) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (IOException | SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
