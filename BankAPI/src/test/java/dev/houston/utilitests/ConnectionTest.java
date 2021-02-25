package dev.houston.utilitests;

import dev.houston.utilities.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void generates_connection(){
        Connection conn = ConnectionUtil.createConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }



}
