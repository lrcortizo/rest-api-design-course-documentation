package io.trabe.teaching.rest.model.pojo.api.external.common;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiAccount {
    private Long id;
    private String code;
    private String description;
    private Double balance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar creationDate;
    private ApiAccountKind kind;

}
