package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class ReferralReason {
    private Integer checkInId;
    private ReferralReasonCode code;
    private String serviceCode;
    private String description;

    public ReferralReason(@ColumnName("checkin_id") Integer checkInId,
                          @ColumnName("code") ReferralReasonCode code,
                          @ColumnName("service_code") String serviceCode,
                          @ColumnName("description") String description) {
        this.checkInId = checkInId;
        this.code = code;
        this.serviceCode = serviceCode;
        this.description = description;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public ReferralReasonCode getCode() {
        return code;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getDescription() {
        return description;
    }
}
