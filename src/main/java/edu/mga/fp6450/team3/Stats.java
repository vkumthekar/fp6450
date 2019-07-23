/*******************************************************************************
 * Copyright (c) 2019. VMware, Inc.  All rights reserved. VMware Confidential
 ******************************************************************************/
package edu.mga.fp6450.team3;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author vkumthekar
 * @since
 *
 */
@Data
public class Stats{
    StatsCategory category;
    @JsonIgnore
    Map<String, Integer> stepsByCategory;
    List<Details> details;

    @Data
    public class Details {
        String key;    //Keys like - WEEK 34 OF YEAR, DAY 203 OF YEAR, 
        String steps;       //Number of steps
    }
}


