package ar.com.kaijusoftware.lotto_mania.controllers.mainframe;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.EmployeeProfileInfo;
import ar.com.kaijusoftware.lotto_mania.models.EmployeeProfile;
import ar.com.kaijusoftware.lotto_mania.repositories.EmployeeProfileRepository;
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
@RequestMapping("/api/v1/mainframe/employee-profile")
public class MainframeEmployeeProfileController {

    private final EmployeeProfileRepository employeeProfileRepository;

    @PostMapping
    public ResponseEntity<String> create(final @RequestBody EmployeeProfileInfo profileInfo) {
        log.debug("Creating new employee profile: {}", profileInfo);

        EmployeeProfile profile = EmployeeProfile.builder()
                .code(profileInfo.getCode())
                .description(profileInfo.getDescription())
                .build();

        employeeProfileRepository.saveAndFlush(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(profile.getCode());
    }
}
