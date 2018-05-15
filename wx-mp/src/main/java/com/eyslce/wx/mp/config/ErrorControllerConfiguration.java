package com.eyslce.wx.mp.config;

import com.eyslce.wx.mp.controller.ErrorController;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ErrorControllerConfiguration {
    private final ServerProperties serverProperties;

    private final List<ErrorViewResolver> errorViewResolvers;

    public ErrorControllerConfiguration(ServerProperties serverProperties,
                                        ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
        this.serverProperties = serverProperties;
        this.errorViewResolvers = errorViewResolversProvider.getIfAvailable();

    }

    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes) {
        return new ErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

}
