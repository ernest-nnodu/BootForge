package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.model.Configuration;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigurationMapperTest {

    private ConfigurationMapper mapper;
    private Validator validator;

    @BeforeEach
    void setUp() {
        mapper = new ConfigurationMapper();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ---------------------------------------------------
    // ✅ Test 1: Full valid request maps successfully
    // ---------------------------------------------------

    @Test
    void shouldMapValidRequestToDomain() {

        GenerateConfigRequest request = TestDataFactory.validRequest();

        Configuration configuration = mapper.toDomain(request);

        assertThat(configuration).isNotNull();
        assertThat(configuration.applicationConfig().applicationName())
                .isEqualTo("bootforge");
        assertThat(configuration.serverConfig().port())
                .isEqualTo(8081);
        assertThat(configuration.databaseConfig().databaseType())
                .isEqualTo(DatabaseType.POSTGRESQL);
    }

    // ---------------------------------------------------
    // ✅ Test 2: Optional sections handled safely
    // ---------------------------------------------------

    @Test
    void shouldHandleNullOptionalSections() {

        GenerateConfigRequest request = TestDataFactory.validRequest();

        Configuration configuration = mapper.toDomain(request);

        assertThat(configuration).isNotNull();
        assertThat(configuration.jpaConfig()).isNotNull();   // domain should default
        assertThat(configuration.hikariConfig()).isNotNull();
    }

    // ---------------------------------------------------
    // ✅ Test 3: Defaults applied in domain
    // ---------------------------------------------------

    @Test
    void shouldApplyDefaultsWhenValuesMissing() {

        GenerateConfigRequest request = TestDataFactory.requestWIthFieldsMissing();

        Configuration configuration = mapper.toDomain(request);

        assertThat(configuration.serverConfig().port())
                .isEqualTo(8080); // default defined in domain
    }

    // ---------------------------------------------------
    // ✅ Test 4: Bean validation should catch invalid request
    // ---------------------------------------------------

    @Test
    void shouldFailValidationWhenRequiredFieldsMissing() {

        GenerateConfigRequest request = new GenerateConfigRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Set<ConstraintViolation<GenerateConfigRequest>> violations =
                validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    // ---------------------------------------------------
    // ✅ Test 5: Domain invariant protection
    // ---------------------------------------------------

    @Test
    void shouldThrowExceptionWhenDomainInvariantViolated() {

        GenerateConfigRequest request = TestDataFactory.invalidRequest();

        assertThrows(Exception.class, () -> mapper.toDomain(request));
    }
}
