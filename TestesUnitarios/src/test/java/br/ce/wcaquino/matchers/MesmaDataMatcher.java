package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MesmaDataMatcher extends TypeSafeMatcher<Date> {

    private Integer qtdDias;

    public MesmaDataMatcher(Integer qtdDias){
        this.qtdDias = qtdDias;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
    }

    @Override
    public void describeTo(Description description) {
        Date dataEsperada = DataUtils.obterDataComDiferencaDias(qtdDias);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        description.appendText(format.format(dataEsperada));
    }
}