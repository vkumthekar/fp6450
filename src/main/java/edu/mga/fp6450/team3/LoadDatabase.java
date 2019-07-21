/*******************************************************************************
 * Copyright (c) 2019. VMware, Inc.  All rights reserved. VMware Confidential
 ******************************************************************************/
package edu.mga.fp6450.team3;

import java.util.Calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vkumthekar
 * @since
 *
 */
@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(ActivityRepository repository) {
        return args -> {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR, 2); 
            log.info("Preloading " + repository.save(new Activity(2, today.getTime())));
            today.set(Calendar.HOUR, 4); 
            log.info("Preloading " + repository.save(new Activity(4, today.getTime())));
            today.add(Calendar.DAY_OF_MONTH, 1);
            today.set(Calendar.HOUR, 3); 
            log.info("Preloading " + repository.save(new Activity(3, today.getTime())));
            today.set(Calendar.HOUR, 6);
            today.add(Calendar.MONTH, 1);
            log.info("Preloading " + repository.save(new Activity(6, today.getTime())));
            today.set(Calendar.HOUR, 8); 
            log.info("Preloading " + repository.save(new Activity(8, today.getTime())));
            today.set(Calendar.HOUR, 1); 
            log.info("Preloading " + repository.save(new Activity(1, today.getTime())));
        };
    }
}
