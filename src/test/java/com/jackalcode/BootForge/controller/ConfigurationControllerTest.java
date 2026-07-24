package com.jackalcode.BootForge.controller;

import com.jackalcode.BootForge.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private ConfigurationController configurationController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConfigurationService configurationService;
}
