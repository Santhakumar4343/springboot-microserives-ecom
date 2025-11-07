package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    //we can add a default value to the @Value annotation like this @Value("${build.id:default}")
    @Value("${build.id}")
    private String id;
    @Value("${build.name}")
    private String name;
    @Value(("${build.version}"))
    private String version;
@GetMapping("/get-info")
    public String getConfig(){
        return "Id: "+id+", name: "+name+", version: "+version;
    }
}
