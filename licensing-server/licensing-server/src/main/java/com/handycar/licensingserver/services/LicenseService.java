package com.handycar.licensingserver.services;

import com.handycar.licensingserver.clients.OrganizationFeignClient;
import com.handycar.licensingserver.config.ServiceConfig;
import com.handycar.licensingserver.model.License;
import com.handycar.licensingserver.model.Organization;
import com.handycar.licensingserver.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    ServiceConfig config;

    @HystrixCommand
    public License getLicense(String organizationId,String licenseId) {
        Organization organization = organizationFeignClient.getOrganization(organizationId);

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @HystrixCommand
    public List<License> getLicensesByOrg(String organizationId){
        randomlyRunLong();

        return licenseRepository.findByOrganizationId( organizationId );
    }

    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt(3) + 1;

        if(randomNum == 3){
            System.out.println("Hit as delayed service");
            sleep();
        }
    }

    private void sleep() {
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
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
