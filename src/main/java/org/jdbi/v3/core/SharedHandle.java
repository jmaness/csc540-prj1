package org.jdbi.v3.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.StatementBuilder;
import org.jdbi.v3.core.transaction.TransactionHandler;

public class SharedHandle extends Handle {

    public SharedHandle(Jdbi jdbi,
                        ConfigRegistry config,
                        TransactionHandler transactions,
                        StatementBuilder statementBuilder,
                        Connection connection) {
        super(jdbi, config, Connection::close, transactions, statementBuilder, connection);
    }

    // Ignore calls to close this handle since it's shared and will be explicitly closed when
    // UnitOfWork calls closeForReal() at the end of the transaction.
    @Override
    public void close() {}

    public void closeForReal() {
        super.close();
    }
}
