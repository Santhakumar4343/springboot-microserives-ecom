package com.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RefreshScope
public class ConfigController {
    //we can add a default value to the @Value annotation like this @Value("${build.id:default}")
//    @Value("${build.id}")
//    private String id;
//    @Value("${build.name}")
//    private String name;
//    @Value(("${build.version}"))
//    private String version;
    private final BuildInfo buildInfo;
@GetMapping("/get-info")
    public String getConfig(){
        return "Id: "+buildInfo.getId()+", name: "+buildInfo.getName()+", version: "+buildInfo.getVersion();
    }
}
