package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ReferralStatus {
    private Integer checkInId;
    private Integer facilityId;
    private Integer staffId;
    private String treatment;
    private List<ReferralReason> reasons;

    public ReferralStatus(@ColumnName("checkin_id") Integer checkInId,
                          @ColumnName("facility_id") Integer facilityId,
                          @ColumnName("staff_id") Integer staffId,
                          @Nullable List<ReferralReason> reasons) {
        this.checkInId = checkInId;
        this.facilityId = facilityId;
        this.staffId = staffId;
        this.reasons = reasons != null ? reasons : new ArrayList<>();
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public List<ReferralReason> getReasons() {
        return reasons;
    }
}
