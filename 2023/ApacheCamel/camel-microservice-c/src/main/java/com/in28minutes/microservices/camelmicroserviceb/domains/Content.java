package com.in28minutes.microservices.camelmicroserviceb.domains;

public class Content {
    private int id;
    private int idMedico;
    private int idPaciente;
    private String data;
    private String motivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + id +
            ", idMedico=" + idMedico +
            ", idPaciente=" + idPaciente +
            ", data='" + data + '\'' +
            ", motivo='" + motivo + '\'' +
            '}';
    }
}
