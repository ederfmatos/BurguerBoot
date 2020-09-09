package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.repository.OptionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> findAll() {
        return optionRepository.findAll(Sort.by("value"));
    }

    public Option findById(List<? extends Option> options, String id) {
        return this.find(options, id).orElseThrow(InvalidOptionException::new);
    }

    private Optional<? extends Option> find(List<? extends Option> options, String id) {
        return options.stream()
                .filter(option -> option.getId().equals(id) || option.hasOptions())
                .flatMap(option -> option.getId().equals(id) ? Stream.of(option) : find(option.getOptions(), id).stream())
                .findFirst();
    }

    public Option getOptionFromMessage(String message, List<? extends Option> options) {
        return options.stream().filter(opt -> opt.getValue().equals(message))
                .findFirst()
                .orElseThrow(InvalidOptionException::new);
    }

    public String formatOptions(List<? extends Option> options) {
        return options.stream()
                .map(option -> String.format("%s - %s", StringUtils.leftPad(option.getValue(), 2, "0"), option.getName()))
                .collect(Collectors.joining("\n"));
    }

}
