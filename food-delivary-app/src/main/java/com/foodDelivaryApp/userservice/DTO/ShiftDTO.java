package com.foodDelivaryApp.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftDTO {

    private String shiftStart;
    private String shiftEnd;

}
