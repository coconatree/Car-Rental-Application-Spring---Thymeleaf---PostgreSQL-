package com.car_rental.Model.RedirectedDataModel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CarFilterData {

    private List<String> selectedModels;
    private List<String> selectedBrands;
    private List<String> selectedGearTypes;

    private Integer preferredBranchId;
}
