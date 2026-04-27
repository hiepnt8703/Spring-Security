package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> getAllProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("All products");
    }

    @PostMapping(value = "" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult
//            @RequestPart("image") MultipartFile imageFile
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> error = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(error);
            }

            List<MultipartFile> imageFiles = productDTO.getImageFiles();

            imageFiles = imageFiles == null ? new ArrayList<MultipartFile>() : imageFiles;

            for (MultipartFile file : imageFiles) {

                if (file.getSize() == 0) {
                    continue;
                }

                if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                    throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Image size must be less than 10MB");
                }

                String contentType = file.getContentType();
                if (!contentType.startsWith("image/") || contentType == null) {
                    return ResponseEntity.status(
                            HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Invalid image format"
                            );
                }

                String fileName = storeImage(file);
            }

            return ResponseEntity.ok("This is a product: " + productDTO + ".");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private String storeImage(MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        Path uploadDir = Paths.get("uploads", uniqueFileName);

        // kiem tra thu muc uploads co ton tai hay khong, neu khong tao moi thu muc uploads
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // duong dan day du thu muc uploads
        Path destination = uploadDir.resolve(file.getOriginalFilename());

        file.transferTo(destination);
        return uniqueFileName;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok("This is a product: " + id + ".");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Update a product: " + id + ".");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Delete a product: " + id + ".");
    }
}
