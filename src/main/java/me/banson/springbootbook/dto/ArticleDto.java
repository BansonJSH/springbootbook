package me.banson.springbootbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.banson.springbootbook.domain.Article;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.file.Files;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDto {

    @Value("${file.dir}")
    private String fileDir;

    private Long id;
    private String title;
    private String content;
    private String author;
    private MultipartFile originalFileName;
    private String existFileName;

    public ArticleDto(Article article) throws IOException {
        this.id=article.getId();
        this.title= article.getTitle();
        this.content= article.getContent();
        if (article.getStoreFileName() != null){
            this.existFileName = article.getOriginalFileName();
        }
        else{
            this.existFileName = null;
        }
    }
}
