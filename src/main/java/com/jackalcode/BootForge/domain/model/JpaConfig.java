package com.jackalcode.BootForge.domain.model;

import com.jackalcode.BootForge.domain.enums.DdlAuto;

public record JpaConfig(
        DdlAuto ddlAuto,
        Boolean showSql,
        Boolean openInView) {

    public JpaConfig(DdlAuto ddlAuto, Boolean showSql, Boolean openInView) {
        this.ddlAuto = ddlAuto == null ? DdlAuto.NONE : ddlAuto;
        this.showSql = showSql != null && showSql;
        this.openInView = openInView != null && openInView;
    }
}
