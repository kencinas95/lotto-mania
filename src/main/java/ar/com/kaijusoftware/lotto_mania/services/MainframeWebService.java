package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.utils.BasicError;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainframeWebService {
    public synchronized <T, R extends JpaRepository<T, ?>> ResponseEntity<BasicResult<T>> saveAndSend(
            final T entity,
            final R repository,
            final HttpStatus successStatus
    ) {
        log.debug("Saving entity: {}", entity);

        T saved = null;
        HttpStatus status = successStatus;
        BasicError error = null;

        try {
            saved = repository.saveAndFlush(entity);
        } catch (DataIntegrityViolationException ex) {
            status = HttpStatus.CONFLICT;
            error = BasicError.builder()
                    .cause(ex)
                    .message(ExceptionUtils.getMessage(ex))
                    .build();
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            error = BasicError.builder()
                    .cause(ex)
                    .message(ex.getMessage())
                    .build();
        }

        BasicResult<T> result = BasicResult.<T>builder()
                .result(saved)
                .error(error)
                .build();

        return ResponseEntity.status(status)
                .body(result);
    }
}
