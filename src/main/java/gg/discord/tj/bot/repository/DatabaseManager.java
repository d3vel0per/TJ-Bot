package gg.discord.tj.bot.repository;

import lombok.SneakyThrows;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.atomic.AtomicReference;

public enum DatabaseManager {
    INSTANCE;

    private final AtomicReference<Connection> connectionRef = new AtomicReference<>();

    @SneakyThrows
    public Connection establishConnection() {
        Connection connection = connectionRef.get();

        if (connection == null)
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Path.of("/var/tjbot/tjdatabase.db").toFile().getCanonicalPath());

            if (!connectionRef.compareAndSet(null, connection))
                return connectionRef.get();
        }

        return connectionRef.get();
    }

    @SneakyThrows
    public void disconnect() {
        connectionRef.get().close();
    }
}
