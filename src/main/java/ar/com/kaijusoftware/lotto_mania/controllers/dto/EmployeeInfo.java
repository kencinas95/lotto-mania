package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonInclude(NON_NULL)
public class EmployeeInfo {
    Long id;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @NotNull
    EmployeeProfileInfo profile;

    PersonInfo person;

    @Builder.Default
    List<AddressInfo> addresses = Lists.newArrayList();

    @Builder.Default
    List<PersonContactInfo> contacts = Lists.newArrayList();

}
