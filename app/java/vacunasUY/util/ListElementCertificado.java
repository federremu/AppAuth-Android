package vacunasUY.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListElementCertificado implements Comparable<ListElementCertificado> {
    public String vacuna;
    public String fecha;
    public String inmunidad;
    public String dosis;
    public String enfermedad;
    public String laboratorio;

    public ListElementCertificado(String vacuna, String fecha, String inmunidad, String dosis,
                                  String enfermedad, String laboratorio) {
        this.vacuna=vacuna;
        this.fecha=fecha;
        this.inmunidad=inmunidad;
        this.dosis=dosis;
        this.enfermedad=enfermedad;
        this.laboratorio=laboratorio;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInmunidad() {
        return inmunidad;
    }

    public void setInmunidad(String inmunidad) {
        this.inmunidad = inmunidad;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public int compareTo(ListElementCertificado listElementCertificado) {
        try {
            Date date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(fecha);
            Date date2 = new SimpleDateFormat("yyyyMMddHHmmss").parse(listElementCertificado.fecha);
            return date2.compareTo(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
