package com.ederfmatos.burguerbot.repository;

import com.ederfmatos.burguerbot.model.options.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends MongoRepository<Option, String> {
}
