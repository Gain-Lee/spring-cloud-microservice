package com.handycar.licensingserver.services;

import com.handycar.licensingserver.clients.OrganizationRestTemplateClient;
import com.handycar.licensingserver.config.ServiceConfig;
import com.handycar.licensingserver.model.License;
import com.handycar.licensingserver.model.Organization;
import com.handycar.licensingserver.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    ServiceConfig config;

    public License getLicense(String organizationId,String licenseId) {
        Organization organization = organizationRestClient.getOrganizaion(organizationId);

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId){
        return licenseRepository.findByOrganizationId( organizationId );
    }

    public void saveLicense(License license){
        license.withId( UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license){
      licenseRepository.save(license);
    }

    public void deleteLicense(License license){
        licenseRepository.deleteById( license.getLicenseId());
    }

}
