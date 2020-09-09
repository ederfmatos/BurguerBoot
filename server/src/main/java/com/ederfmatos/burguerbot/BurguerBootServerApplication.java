package com.ederfmatos.burguerbot;

import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Request;
import com.ederfmatos.burguerbot.model.options.request.Snack;
import com.ederfmatos.burguerbot.repository.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableMongoRepositories
public class BurguerBootServerApplication implements CommandLineRunner {

    private final OptionRepository optionRepository;

    public BurguerBootServerApplication(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BurguerBootServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Option realizarPedidos = new Request("1", "Realizar pedidos");
        Snack lanches = new Snack("1", "Lanches");
        lanches.setOptions(Arrays.asList(
                new Product("1", "Lanche 1", BigDecimal.valueOf(9.9)),
                new Product("2", "Lanche 2", BigDecimal.valueOf(15.9))
        ));

        Drink bebidas = new Drink("2", "Bebidas");

        realizarPedidos.setOptions(Arrays.asList(lanches, bebidas));

        optionRepository.deleteAll();
        optionRepository.saveAll(Arrays.asList(realizarPedidos));
    }
}
