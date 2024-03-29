package bibernate.session.impl;

import bibernate.session.Session;
import bibernate.session.SessionFactory;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class SimpleSessionFactory implements SessionFactory {
    private final DataSource dataSource;

    @Override
    public Session createSession() {
        return new SessionImpl(dataSource);
    }
}
