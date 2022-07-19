package com.jeanlima.testes.locadora.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.jeanlima.testes.locadora.utils.DataUtils;

//recebe como primeiro parametro uma data
public class DiaSemanaMatcher extends TypeSafeMatcher<Date>{

    private Integer diaSemana;

    //valor passado no parametro - inteiro que representa o dia da semana
    public DiaSemanaMatcher(Integer diaSemana){
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, diaSemana);
		String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
        
    }
    //realiza a comparação
    @Override
    protected boolean matchesSafely(Date item) {
        return DataUtils.verificarDiaSemana(item, diaSemana);
    }
    
}
