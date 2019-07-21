/*******************************************************************************
 * Copyright (c) 2019. VMware, Inc.  All rights reserved. VMware Confidential
 ******************************************************************************/
package edu.mga.fp6450.team3;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author vkumthekar
 * @since
 *
 */
@Data
@Entity
public class Activity implements Comparable<Activity>{
    private @Id @GeneratedValue Long id;
    int steps;
    @JsonFormat(pattern="mm-dd-yyyy")
    Date untilTime;

    public Activity() {
        
    }
    
    public Activity(int steps, Date untilTime) {
        this.steps = steps;
        this.untilTime = untilTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Activity other = (Activity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (steps != other.steps)
            return false;
        if (untilTime == null) {
            if (other.untilTime != null)
                return false;
        } else if (!untilTime.equals(other.untilTime))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + steps;
        result = prime * result + ((untilTime == null) ? 0 : untilTime.hashCode());
        return result;
    }
    
    @Override
    public int compareTo(Activity o) {
        if (this.getUntilTime() == null || o.getUntilTime() == null) {
            return 0;
        }
        return this.getUntilTime().compareTo(o.getUntilTime());
    }
}
