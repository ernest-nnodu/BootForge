package com.jackalcode.BootForge.exception;

import com.jackalcode.BootForge.domain.enums.OutputFormat;

public class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(OutputFormat outputFormat) {
        super("No formatter registered for output format: " + outputFormat);
    }
}
