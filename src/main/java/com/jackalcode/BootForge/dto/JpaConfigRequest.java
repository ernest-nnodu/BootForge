package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.DdlAuto;

public record JpaConfigRequest(

        DdlAuto ddlAuto,
        Boolean showSql,
        Boolean openInView
) {
}
