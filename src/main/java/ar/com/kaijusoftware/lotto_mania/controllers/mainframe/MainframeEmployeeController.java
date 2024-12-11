package ar.com.kaijusoftware.lotto_mania.controllers.mainframe;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.EmployeeInfo;
import ar.com.kaijusoftware.lotto_mania.models.Employee;
import ar.com.kaijusoftware.lotto_mania.models.EmployeeProfile;
import ar.com.kaijusoftware.lotto_mania.models.Person;
import ar.com.kaijusoftware.lotto_mania.repositories.EmployeeProfileRepository;
import ar.com.kaijusoftware.lotto_mania.repositories.EmployeeRepository;
import ar.com.kaijusoftware.lotto_mania.services.MainframeWebService;
import ar.com.kaijusoftware.lotto_mania.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mainframe/employee")
public class MainframeEmployeeController {
    private final PersonService personService;

    private final EmployeeProfileRepository employeeProfileRepository;
    private final EmployeeRepository employeeRepository;

    private final MainframeWebService mainframeWebService;


    @PostMapping
    public ResponseEntity<Long> create(
            @Valid
            @RequestBody final EmployeeInfo employeeInfo
    ) {
        log.debug("Creating new employee: {}", employeeInfo);

        try {
            Person person = personService.getOrCreate(employeeInfo.getPerson());

            EmployeeProfile profile = employeeProfileRepository.findById(employeeInfo.getProfile().getCode()).orElseThrow();

            Employee employee = Employee.builder()
                    .person(person)
                    .username(employeeInfo.getUsername())
                    .password(employeeInfo.getPassword())
                    .profile(profile)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.saveAndFlush(employee).getPk());
        } catch (Exception ex) {
            log.error("Something went wrong while creating employee", ex);
            return ResponseEntity.badRequest().build();
        }
    }
}
