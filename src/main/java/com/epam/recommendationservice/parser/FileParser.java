package com.epam.recommendationservice.parser;

import com.epam.recommendationservice.model.Crypto;

import java.io.File;
import java.util.List;

interface FileParser {
    List<Crypto> parse(File file);
}
