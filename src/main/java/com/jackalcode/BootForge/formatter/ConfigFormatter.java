package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.enums.OutputFormat;
import com.jackalcode.BootForge.domain.model.Configuration;

public interface ConfigFormatter {

    OutputFormat getFormat();

    String format(Configuration configuration);
}
