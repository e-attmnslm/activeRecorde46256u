import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DBTest {

    @AfterEach
    void remettreBaseParDefaut() throws SQLException {
        DBConnection.setNomDB("testpersonne");
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
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("testpersonne");

        DBConnection.setNomDB("testpersonne2");
        Connection c2 = DBConnection.getConnection();


        assertNotSame(c1,c2,"ils ne doivent pas avoir le meme adresse mémoire");


    }
}