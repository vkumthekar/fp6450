/*******************************************************************************
 * Copyright (c) 2019. VMware, Inc.  All rights reserved. VMware Confidential
 ******************************************************************************/
package edu.mga.fp6450.team3;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vkumthekar
 * @since
 *
 */
@RestController
@Slf4j
public class ActivityController {
    private final ActivityRepository repository;
    private static final String DAY = "DAY ";
    private static final String WEEK = "WEEK ";
    private static final String MONTH = "MONTH ";
    private static final String OF_YEAR = " OF YEAR";

    ActivityController(ActivityRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all list of activities including number of steps per date, sorted by DAILY.
     *
     * @param category
     * @return
     */
    @GetMapping("/activity")
    Stats all() {
        Stats stats = new Stats();
        log.info("getting daily activity by default");
        List<Activity> allActivity = repository.findAll();
        Collections.sort(allActivity);
        Map<String, Integer> stepsByCategory = new TreeMap<String, Integer>();
        for (Activity activity : allActivity) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(activity.getUntilTime());
            int day = cal.get(Calendar.DAY_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            String dayOfYear = DAY + day + OF_YEAR + year;
            Integer steps = stepsByCategory.get(dayOfYear);
            if (steps == null) {
                steps = activity.getSteps();
            } else {
                steps += activity.getSteps();
            }
            stepsByCategory.put(dayOfYear, activity.getSteps());
        }
        stats.setCategory(StatsCategory.DAILY);
        stats.setStepsByCategory(stepsByCategory);
        return stats;
    }

    /**
     * Returns the list of activities including number of steps per date, sorted by given <category>.
     *
     * @param category
     * @return Stats
     */
    @GetMapping("/activity/{category}")
    Stats getStats(@PathVariable(value = "category") StatsCategory category) {
        Stats stats = new Stats();
        log.info("getting all activity");
        List<Activity> allActivity = repository.findAll();
        Collections.sort(allActivity);
        Map<String, Integer> stepsByCategory = new TreeMap<String, Integer>();
        switch (category) {
        case DAILY:
            for (Activity activity : allActivity) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(activity.getUntilTime());
                int day = cal.get(Calendar.DAY_OF_YEAR);
                int year = cal.get(Calendar.YEAR);
                String dayOfYear = DAY + day + OF_YEAR + year;
                Integer steps = stepsByCategory.get(dayOfYear);
                if (steps == null) {
                    steps = activity.getSteps();
                } else {
                    steps += activity.getSteps();
                }
                stepsByCategory.put(dayOfYear, activity.getSteps());
            }
            break;
        case WEEKLY:
            for (Activity activity : allActivity) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(activity.getUntilTime());
                int day = cal.get(Calendar.WEEK_OF_YEAR);
                int year = cal.get(Calendar.YEAR);
                String dayOfYear = WEEK + day + OF_YEAR + year;
                Integer steps = stepsByCategory.get(dayOfYear);
                if (steps == null) {
                    steps = activity.getSteps();
                } else {
                    steps += activity.getSteps();
                }
                stepsByCategory.put(dayOfYear, activity.getSteps());
            }
            break;
        case MONTHLY:
            for (Activity activity : allActivity) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(activity.getUntilTime());
                int day = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                String dayOfYear = MONTH + day + OF_YEAR + year;
                Integer steps = stepsByCategory.get(dayOfYear);
                if (steps == null) {
                    steps = activity.getSteps();
                } else {
                    steps += activity.getSteps();
                }
                stepsByCategory.put(dayOfYear, activity.getSteps());
            }
            break;
        default:
            break;
        }
        stats.setStepsByCategory(stepsByCategory);
        stats.setCategory(category);
        return stats;
    }

    /**
     * Save activity
     *
     * @param activity
     * @return
     */
    @PostMapping("/activity")
    Activity newActivity(@RequestBody Activity activity) {
        log.info("Adding activity {}", activity);
        return repository.save(activity);
    }

    @PutMapping("/activity/{id}")
    Activity replaceActivity(@RequestBody Activity newActivity, @PathVariable Long id) {
        return repository.findById(id).map(activity -> {
            activity.setSteps(newActivity.getSteps());
            activity.setUntilTime(newActivity.getUntilTime());
            return repository.save(activity);
        }).orElseGet(() -> {
            newActivity.setId(id);
            return repository.save(newActivity);
        });
    }

    @DeleteMapping("/activity/{id}")
    ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
