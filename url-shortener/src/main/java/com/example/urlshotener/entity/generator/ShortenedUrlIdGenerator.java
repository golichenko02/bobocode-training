package com.example.urlshotener.entity.generator;



import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class ShortenedUrlIdGenerator implements IdentifierGenerator {

    private final static int DEFAULT_IDENTIFIER_SIZE = 10;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return RandomStringUtils.random(DEFAULT_IDENTIFIER_SIZE, true, true);
    }
}
