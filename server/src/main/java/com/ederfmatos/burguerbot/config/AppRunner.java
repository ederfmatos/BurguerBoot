package com.ederfmatos.burguerbot.config;

import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.enumeration.EmojiEnum;
import com.ederfmatos.burguerbot.model.options.CommentOption;
import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.InformationOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Portion;
import com.ederfmatos.burguerbot.model.options.request.Snack;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class AppRunner {

    private final OptionService optionService;

    public AppRunner(OptionService optionService) {
        this.optionService = optionService;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Option realizarPedidos = new Option("1", "Realizar pedido");
            Option obterInformacoesDoEstabelecimento = new InformationOption("2", "Obter informações do estabelecimento");
            Option elogiarCriticar = new CommentOption("3", "Deixar um elogio/critica");
            Option finalizarAtendimento = new FinishAttendanceOption("4", "Finalizar atendimento");

            Snack lanches = new Snack("1", "Lanches", EmojiEnum.BURGER.toString());
            lanches.setOptions(Arrays.asList(
                    new Product("1", "X-TUDO", BigDecimal.valueOf(17.9),15),
                    new Product("2", "X-SALADA", BigDecimal.valueOf(10.49), 10),
                    new Product("3", "X-SALAME", BigDecimal.valueOf(13.59), 20),
                    new Product("4", "X-VULCÃO", BigDecimal.valueOf(22.9), 30),
                    new Product("5", "X-INFARTO", BigDecimal.valueOf(26.9), 35),
                    new Product("6", "X-BACON", BigDecimal.valueOf(16.9), 20),
                    new Product("7", "X-EGG", BigDecimal.valueOf(11.9), 12),
                    new Product("8", "MATA LEÃO", BigDecimal.valueOf(30.9), 35),
                    new Product("9", "DOGÃO FULL COMPLETO", BigDecimal.valueOf(25.9), 30),
                    new Product("10", "BURGUER BARBECUE", BigDecimal.valueOf(15.9), 20),
                    new Product("11", "CHEESE KIDS", BigDecimal.valueOf(14.9), 10),
                    new Product("12", "SMASH BURGUER", BigDecimal.valueOf(19.9), 25)
            ));

            Drink bebidas = new Drink("2", "Bebidas", EmojiEnum.DRINK.toString());
            bebidas.setOptions(Arrays.asList(
                    new Product("1", "COCA COLA LATA 350ML", BigDecimal.valueOf(4)),
                    new Product("2", "FANTA LATA 350ML", BigDecimal.valueOf(4)),
                    new Product("3", "GUARANÁ ANTARCTICA LATA 350ML", BigDecimal.valueOf(4)),
                    new Product("4", "KUAT LATA 350ML", BigDecimal.valueOf(4)),
                    new Product("5", "COCA COLA 2 LITROS", BigDecimal.valueOf(9)),
                    new Product("6", "FANTA 2 LITROS", BigDecimal.valueOf(8)),
                    new Product("7", "GUARANÁ ANTARCTICA 2 LITROS", BigDecimal.valueOf(9)),
                    new Product("8", "JABOTI LIMÃO 2 LITROS", BigDecimal.valueOf(5)),
                    new Product("9", "JABOTI LARANJA 2 LITROS", BigDecimal.valueOf(5)),
                    new Product("10", "JABOTI UVA 2 LITROS", BigDecimal.valueOf(5)),
                    new Product("11", "JABOTI MAÇÃ 2 LITROS", BigDecimal.valueOf(5)),
                    new Product("12", "GUARANÁ POTY 2 LITROS", BigDecimal.valueOf(6))
            ));

            Portion porcoes = new Portion("3", "Porções", EmojiEnum.PORTION.toString());
            porcoes.setOptions(Arrays.asList(
                    new Product("1", "CONTRA FILÉ NA CHAPA", BigDecimal.valueOf(46.9), 30),
                    new Product("2", "FRANGO À PASSARINHO", BigDecimal.valueOf(39.9), 30),
                    new Product("3", "BATATA FRITA", BigDecimal.valueOf(35.9), 30),
                    new Product("4", "POLENTA FRITA", BigDecimal.valueOf(22.9), 30),
                    new Product("5", "TUDO E MAIS UM POUCO PARA  FAMILIA", BigDecimal.valueOf(89.9), 30)
            ));

            realizarPedidos.setOptions(Arrays.asList(lanches, bebidas, porcoes));

            optionService.deleteAll();
            optionService.saveAll(realizarPedidos, obterInformacoesDoEstabelecimento, elogiarCriticar, finalizarAtendimento);
        };
    }

}
