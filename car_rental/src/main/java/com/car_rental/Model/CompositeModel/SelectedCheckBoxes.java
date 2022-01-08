package com.car_rental.Model.CompositeModel;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class SelectedCheckBoxes {
    private List<String> selectedModels;
    private List<String> selectedBrands;
    private List<String> selectedGearTypes;

    private Date selectedStartingDate;
    private Date selectedEndingDate;

    private Integer selectedBranch;
}
