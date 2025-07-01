package com.example.bd_nosql.config;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;

import java.io.File;
import java.io.IOException;

@Configuration
public class LevelDBConfig {

    @Bean
    public DB levelDB() throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        return factory.open(new File("db-level"), options);
    }
}