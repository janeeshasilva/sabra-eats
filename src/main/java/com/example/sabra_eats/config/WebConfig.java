package com.example.sabra_eats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // [වෙනස් කල කොටස] - file:uploads/ කියලා දුන්නා
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}