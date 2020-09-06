package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Drink extends Request {

    public Drink() {
        super("2", "Bebidas");
    }

    @Override
    public List<Option> getOptions() {
        return Arrays.asList(
                new Product("1", "Coca cola", BigDecimal.valueOf(6.9)),
                new Product("2", "Fanta uva", BigDecimal.valueOf(5.9))
        );
    }

}
