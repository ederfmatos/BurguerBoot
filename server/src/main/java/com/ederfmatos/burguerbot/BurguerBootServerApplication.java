package com.ederfmatos.burguerbot;

import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.OrderTrackingOption;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Snack;
import com.ederfmatos.burguerbot.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
@EnableMongoRepositories
public class BurguerBootServerApplication implements CommandLineRunner {

    private final OptionService optionService;

    public BurguerBootServerApplication(OptionService optionService) {
        this.optionService = optionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BurguerBootServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        Option realizarPedidos = new Option("1", "Realizar pedidos");
//        Option saberAndamentoDePedido = new OrderTrackingOption("2", "Saber andamento de pedido");
//        Option finalizarAtendimento = new FinishAttendanceOption("3", "Finalizar atendimento");
//
//        Snack lanches = new Snack("1", "Lanches", "\uD83C\uDF54");
//        lanches.setOptions(Arrays.asList(
//                new Product("1", "Lanche 1", BigDecimal.valueOf(9.9), 10),
//                new Product("2", "Lanche 2", BigDecimal.valueOf(15.9), 20)
//        ));
//
//        Drink bebidas = new Drink("2", "Bebidas", "\uD83C\uDF79");
//        bebidas.setOptions(Arrays.asList(
//                new Product("1", "Bebida 1", BigDecimal.valueOf(9.9)),
//                new Product("2", "Bebida 2", BigDecimal.valueOf(15.9)),
//                new Product("3", "Bebida 3", BigDecimal.valueOf(15.9)),
//                new Product("4", "Bebida 4", BigDecimal.valueOf(15.9))
//        ));
//
//        realizarPedidos.setOptions(Arrays.asList(lanches, bebidas));
//
//        optionService.deleteAll();
//        optionService.saveAll(realizarPedidos, saberAndamentoDePedido, finalizarAtendimento);
    }
}
