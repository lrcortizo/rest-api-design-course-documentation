package io.trabe.teaching.rest.model.pojo.api.external.v2;

import java.util.Calendar;

import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccountKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiExtendedAccount {
    private Long id;
    private String code;
    private String description;
    private Double balance;
    private Calendar creationDate;
    private ApiAccountKind kind;
    private String extendedInformation;

}
