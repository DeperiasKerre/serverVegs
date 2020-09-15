package servlets;

import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import models.Response;
import models.Vegetable;
import utils.DatabaseConnection;
import utils.ResponseParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;

public class UpdateVegetableServlet extends HttpServlet {

    ResponseParser parser;

    /**
     * Update Vegetable
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parser = new ResponseParser();
        try {
            int id = parser.retrieveId(req);

            if (id != 0) {

                // Initialize the database
                Connection con = DatabaseConnection.initializeDatabase();

                // Create a SQL query to insert data into demo table
                // demo table consists of two columns, so two '?' is used
                PreparedStatement st = con
                        .prepareStatement("update vegetables set name = ?, quantity = ?, price = ? where id = ?");

                // get the data using request object
                // sets the data to st pointer
                String name = req.getParameter("name");
                st.setString(1, name);
                st.setInt(2, Integer.valueOf(req.getParameter("quantity")));
                st.setFloat(3, Float.valueOf(req.getParameter("price")));
                st.setInt(4, id);

                // Execute the insert command using executeUpdate()
                // to make changes in database
                st.executeUpdate();

                // Close all the connections
                st.close();
                con.close();

                parser.parse(resp, name + " updated successfully", 200);
            } else
                parser.parse(resp, "Sorry, we were unable to get the specified vegetable", 400);
        }
        catch (Exception e) {
            e.printStackTrace();
            parser.parse(resp, "Sorry, we were unable to update the vegetable", 400);
        }
    }
}
