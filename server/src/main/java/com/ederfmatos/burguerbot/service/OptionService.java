package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.repository.OptionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> saveAll(Option... options) {
        Stream.of(options).forEach(this::setOptionId);
        return optionRepository.saveAll(Arrays.asList(options));
    }

    private void setOptionId(Option option) {
        option.setId(UUID.randomUUID().toString());
        if (option.hasOptions()) {
            option.getOptions().forEach(this::setOptionId);
        }
    }

    public Option getFirstOption() {
        return findAll().get(0);
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
                .map(Option::toString)
                .collect(Collectors.joining("\n"));
    }

    public void deleteAll() {
        this.optionRepository.deleteAll();
    }

}
