package com.jackalcode.BootForge.domain.model;

public record ApplicationConfig(
        String applicationName,
        String activeProfile) {

   public ApplicationConfig(String applicationName, String activeProfile) {
       if (applicationName == null || applicationName.isBlank()) {
           throw new IllegalArgumentException("Application name is required");
       }

       this.applicationName = applicationName;
       this.activeProfile = (activeProfile == null || activeProfile.isBlank()) ? "default" : activeProfile;
   }
}
