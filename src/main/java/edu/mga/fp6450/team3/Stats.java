/*******************************************************************************
 * Copyright (c) 2019. VMware, Inc.  All rights reserved. VMware Confidential
 ******************************************************************************/
package edu.mga.fp6450.team3;

import java.util.Map;

import lombok.Data;

/**
 * @author vkumthekar
 * @since
 *
 */
@Data
public class Stats{
    StatsCategory category;
    //Keys like - WEEK 34 OF YEAR, DAY 203 OF YEAR, 
    Map<String, Integer> stepsByCategory;
}


