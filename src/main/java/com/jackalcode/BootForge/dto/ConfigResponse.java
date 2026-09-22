package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.OutputFormat;

public record ConfigResponse(
        OutputFormat format,
        String content
) {
}
