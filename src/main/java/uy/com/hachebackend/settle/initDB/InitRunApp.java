package uy.com.hachebackend.settle.initDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uy.com.hachebackend.settle.infrastructure.dto.CurrencyDto;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerCurrency;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerUser;

@Component
public class InitRunApp implements CommandLineRunner {

    @Autowired
    private HandlerUser handlerUser;

    @Autowired
    private HandlerCurrency handlerCurrency;

    @Override
    public void run(String... args) {
        handlerUser.initUserAdmin();

        //Guardo algunas monedas
        CurrencyDto currencyUy = CurrencyDto.builder().code("UYU").name("Peso").num(858).country("Uruguay").build();
        CurrencyDto currencyAr = CurrencyDto.builder().code("ARS").name("Peso").num(032).country("Argentina").build();
        CurrencyDto currencyBr = CurrencyDto.builder().code("BRL").name("Real").num(986).country("Brazil").build();
        CurrencyDto currencyCh = CurrencyDto.builder().code("CLP").name("Peso").num(152).country("Chile").build();
        CurrencyDto currencyUsa = CurrencyDto.builder().code("USD").name("Dollar").num(840).country("United States").build();
        CurrencyDto currencyEuro = CurrencyDto.builder().code("EUR").name("Euro").num(978).country("Europa").build();

        CurrencyDto[] list = new CurrencyDto[]{currencyUy, currencyAr, currencyBr, currencyCh, currencyUsa, currencyEuro};
        for (CurrencyDto c : list) {
            handlerCurrency.saveCurrencyIso(c.getCode(), c.getName(), c.getNum(), c.getCountry());
        }
        System.out.println(">>>> Monedas cargadas.");
    }
}
