package me.banson.springbootbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
<<<<<<< HEAD
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///home/ubuntu/springbootbook/");
    }
}

=======
        registry.addResourceHandler("/static/img/**")
                .addResourceLocations("file:///home/ubuntu/springbootbook");
    }
}
>>>>>>> 29114ae02e5de9b52d33a5cac58c55f9f56cbdd5
