package com.example.as.classes.database;

public class RISData {

    private int implicate;

    private String date;
    private String entity;
    private String delegation;
    private String id;
    private String hour;
    private String key;
    private String hechos;
    private String causa;
    private String stateSalud;
    private String details;
    private String danos;
    private String salud;
    private String observaciones;

    private boolean trafficAccident;
    private boolean assault;
    private boolean violence;
    private boolean shooting;
    private boolean kidnapping;
    private boolean emblem_abuse;
    private boolean arrests;
    private boolean personalAssault;
    private boolean extortion;
    private boolean threat;
    private boolean preventAccess;
    private boolean assaultOnFacilities;
    private boolean assaultOnStaff;
    private boolean aggressionToTransported;
    private boolean state;
    private boolean policiaFederal;
    private boolean policiaEstatal;
    private boolean policiaMunicipal;
    private boolean ejercito;
    private boolean marina;
    private boolean guardia;
    private boolean actores;
    private boolean manifestante;
    private boolean delincuencia;
    private boolean others;
    private boolean comunidades;

    public RISData() {
    }

    public RISData(String date, String entity, String delegation, String id, String hour,
                   int implicate, boolean trafficAccident, boolean assault, boolean violence,
                   boolean shooting, boolean kidnapping, boolean emblem_abuse, boolean arrests,
                   boolean personalAssault, boolean extortion, boolean threat,
                   boolean preventAccess, boolean assaultOnFacilities, boolean assaultOnStaff,
                   boolean aggressionToTransported, boolean state) {
        this.date = date;
        this.entity = entity;
        this.delegation = delegation;
        this.id = id;
        this.hour = hour;
        this.implicate = implicate;
        this.trafficAccident = trafficAccident;
        this.assault = assault;
        this.violence = violence;
        this.shooting = shooting;
        this.kidnapping = kidnapping;
        this.emblem_abuse = emblem_abuse;
        this.arrests = arrests;
        this.personalAssault = personalAssault;
        this.extortion = extortion;
        this.threat = threat;
        this.preventAccess = preventAccess;
        this.assaultOnFacilities = assaultOnFacilities;
        this.assaultOnStaff = assaultOnStaff;
        this.aggressionToTransported = aggressionToTransported;
        this.state = state;
        this.hechos = "";
        this.causa = "";
        this.stateSalud = "";
        this.details = "";
        this.danos = "";
        this.salud = "";
        this.observaciones = "";
        this.policiaFederal = false;
        this.policiaEstatal = false;
        this.policiaMunicipal = false;
        this.ejercito = false;
        this.marina = false;
        this.guardia = false;
        this.actores = false;
        this.manifestante = false;
        this.delincuencia = false;
        this.others = false;
        this.comunidades = false;
    }

    public int getImplicate() {
        return implicate;
    }

    public void setImplicate(int implicate) {
        this.implicate = implicate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHechos() {
        return hechos;
    }

    public void setHechos(String hechos) {
        this.hechos = hechos;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getStateSalud() {
        return stateSalud;
    }

    public void setStateSalud(String stateSalud) {
        this.stateSalud = stateSalud;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDanos() {
        return danos;
    }

    public void setDanos(String danos) {
        this.danos = danos;
    }

    public String getSalud() {
        return salud;
    }

    public void setSalud(String salud) {
        this.salud = salud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isTrafficAccident() {
        return trafficAccident;
    }

    public void setTrafficAccident(boolean trafficAccident) {
        this.trafficAccident = trafficAccident;
    }

    public boolean isAssault() {
        return assault;
    }

    public void setAssault(boolean assault) {
        this.assault = assault;
    }

    public boolean isViolence() {
        return violence;
    }

    public void setViolence(boolean violence) {
        this.violence = violence;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean isKidnapping() {
        return kidnapping;
    }

    public void setKidnapping(boolean kidnapping) {
        this.kidnapping = kidnapping;
    }

    public boolean isEmblem_abuse() {
        return emblem_abuse;
    }

    public void setEmblem_abuse(boolean emblem_abuse) {
        this.emblem_abuse = emblem_abuse;
    }

    public boolean isArrests() {
        return arrests;
    }

    public void setArrests(boolean arrests) {
        this.arrests = arrests;
    }

    public boolean isPersonalAssault() {
        return personalAssault;
    }

    public void setPersonalAssault(boolean personalAssault) {
        this.personalAssault = personalAssault;
    }

    public boolean isExtortion() {
        return extortion;
    }

    public void setExtortion(boolean extortion) {
        this.extortion = extortion;
    }

    public boolean isThreat() {
        return threat;
    }

    public void setThreat(boolean threat) {
        this.threat = threat;
    }

    public boolean isPreventAccess() {
        return preventAccess;
    }

    public void setPreventAccess(boolean preventAccess) {
        this.preventAccess = preventAccess;
    }

    public boolean isAssaultOnFacilities() {
        return assaultOnFacilities;
    }

    public void setAssaultOnFacilities(boolean assaultOnFacilities) {
        this.assaultOnFacilities = assaultOnFacilities;
    }

    public boolean isAssaultOnStaff() {
        return assaultOnStaff;
    }

    public void setAssaultOnStaff(boolean assaultOnStaff) {
        this.assaultOnStaff = assaultOnStaff;
    }

    public boolean isAggressionToTransported() {
        return aggressionToTransported;
    }

    public void setAggressionToTransported(boolean aggressionToTransported) {
        this.aggressionToTransported = aggressionToTransported;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isPoliciaFederal() {
        return policiaFederal;
    }

    public void setPoliciaFederal(boolean policiaFederal) {
        this.policiaFederal = policiaFederal;
    }

    public boolean isPoliciaEstatal() {
        return policiaEstatal;
    }

    public void setPoliciaEstatal(boolean policiaEstatal) {
        this.policiaEstatal = policiaEstatal;
    }

    public boolean isPoliciaMunicipal() {
        return policiaMunicipal;
    }

    public void setPoliciaMunicipal(boolean policiaMunicipal) {
        this.policiaMunicipal = policiaMunicipal;
    }

    public boolean isEjercito() {
        return ejercito;
    }

    public void setEjercito(boolean ejercito) {
        this.ejercito = ejercito;
    }

    public boolean isMarina() {
        return marina;
    }

    public void setMarina(boolean marina) {
        this.marina = marina;
    }

    public boolean isGuardia() {
        return guardia;
    }

    public void setGuardia(boolean guardia) {
        this.guardia = guardia;
    }

    public boolean isActores() {
        return actores;
    }

    public void setActores(boolean actores) {
        this.actores = actores;
    }

    public boolean isManifestante() {
        return manifestante;
    }

    public void setManifestante(boolean manifestante) {
        this.manifestante = manifestante;
    }

    public boolean isDelincuencia() {
        return delincuencia;
    }

    public void setDelincuencia(boolean delincuencia) {
        this.delincuencia = delincuencia;
    }

    public boolean isOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }

    public boolean isComunidades() {
        return comunidades;
    }

    public void setComunidades(boolean comunidades) {
        this.comunidades = comunidades;
    }
}
