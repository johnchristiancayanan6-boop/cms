package com.eastwest.dto;

import com.eastwest.exception.CustomException;
import com.eastwest.model.Currency;
import com.eastwest.model.enums.BusinessType;
import com.eastwest.model.enums.DateFormat;
import com.eastwest.model.enums.Language;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralPreferencesPatchDTO {

    private Language language;
    @Schema(implementation = IdDTO.class)
    private Currency currency;
    private BusinessType businessType;
    private DateFormat dateFormat;
    private String timeZone;
    private boolean autoAssignWorkOrders;
    private boolean autoAssignRequests;
    private boolean disableClosedWorkOrdersNotif;
    private boolean askFeedBackOnWOClosed;
    private boolean laborCostInTotalCost;
    private boolean woUpdateForRequesters;
    private boolean simplifiedWorkOrder;
    private int daysBeforePrevMaintNotification;
    private String csvSeparator;

    public void setDaysBeforePrevMaintNotification(int daysBeforePrevMaintNotification) {
        if (daysBeforePrevMaintNotification < 0)
            throw new CustomException("Invalid daysBeforePrevMaintNotification", HttpStatus.BAD_REQUEST);
        this.daysBeforePrevMaintNotification = daysBeforePrevMaintNotification;
    }
}
