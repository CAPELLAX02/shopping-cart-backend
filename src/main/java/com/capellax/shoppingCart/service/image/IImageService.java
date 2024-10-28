package com.capellax.shoppingCart.service.image;

import com.capellax.shoppingCart.dto.ImageDTO;
import com.capellax.shoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long imageId);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);

    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file, Long imageId);

}
