import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DBTest {

    // Après chaque test, on remet la base par défaut
    // pour que les tests restent indépendants les uns des autres.
    @AfterEach
    void remettreBaseParDefaut() throws SQLException {
        DBConnection.setNomDB("testpersonne");
    }

    @Test
    void test_type_de_connection() throws SQLException {
        Object c = DBConnection.getConnection();

        assertNotNull(c, "La connexion ne doit pas être null");
        assertInstanceOf(Connection.class, c, "La connexion doit être un java.sql.Connection");
    }

    @Test
    void test_une_connection() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();


        assertSame(c1, c2, "Deux appels doivent renvoyer le même objet");


        for (int i = 0; i < 100; i++) {
            assertSame(c1, DBConnection.getConnection());
        }
    }

    @Test
    void test_connection_base_Defaut() throws SQLException {
        Connection c = DBConnection.getConnection();

        assertFalse(c.isClosed(), "La connexion doit être ouverte");
        assertEquals("testpersonne", c.getCatalog(), "Mauvaise base par défaut");
    }

    @Test
    void test_changement_de_base() throws SQLException {
        Connection avant = DBConnection.getConnection();
        assertEquals("testpersonne", avant.getCatalog());

        DBConnection.setNomDB("testpersonne2");
        Connection apres = DBConnection.getConnection();


        assertEquals("testpersonne2", apres.getCatalog());
        assertFalse(apres.isClosed());


        assertNotSame(avant, apres);
        assertTrue(avant.isClosed(), "L'ancienne connexion doit être fermée");


        assertSame(apres, DBConnection.getConnection());
    }
}