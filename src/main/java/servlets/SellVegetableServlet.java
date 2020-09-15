package servlets;

import models.Vegetable;
import utils.DatabaseConnection;
import utils.ResponseParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellVegetableServlet extends HttpServlet {

    ResponseParser parser;

    /**
     * Update Vegetable
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parser = new ResponseParser();
        Vegetable vegetable = null;
        try {
            int id = parser.retrieveId(req);
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            if (id != 0) {
                // Initialize the database
                Connection con = DatabaseConnection.initializeDatabase();

                PreparedStatement stGet = con
                        .prepareStatement("select * from vegetables where id = ?");
                // Execute the insert command using executeUpdate()
                // to make changes in database
                stGet.setInt(1, parser.retrieveId(req));

                ResultSet resultSet = stGet.executeQuery();
                while (resultSet.next()) {
                    vegetable = new Vegetable();
                    vegetable.setId(resultSet.getInt("id"));
                    vegetable.setName(resultSet.getString("name"));
                    vegetable.setQuantity(resultSet.getInt("quantity"));
                    vegetable.setPrice(resultSet.getFloat("price"));
                }
                stGet.close();
                if (vegetable != null) {
                    if (quantity > vegetable.getQuantity()) {
                        con.close();
                        parser.parse(resp, "Sorry, " + vegetable.getName() + " has only " + String.valueOf(vegetable.getQuantity()) + " remaining.", 200);
                    } else {

                        int balance = vegetable.getQuantity() - quantity;
                        float cost = vegetable.getPrice() * quantity;
                        // Create a SQL query to insert data into demo table
                        // demo table consists of two columns, so two '?' is used
                        PreparedStatement st = con
                                .prepareStatement("update vegetables set quantity = ? where id = ?");

                        // get the data using request object
                        // sets the data to st pointer
                        st.setInt(1, balance);
                        st.setInt(2, parser.retrieveId(req));

                        // Execute the insert command using executeUpdate()
                        // to make changes in database
                        st.executeUpdate();

                        // Close all the connections
                        st.close();
                        con.close();

                        parser.parse(resp, "Sale registered successfully. Total Cost is: " + cost, 200);
                    }
                } else {
                    con.close();
                    parser.parse(resp, " Sorry, Vegetable not found", 200);
                }
            } else
                parser.parse(resp, "Sorry, we were unable to get the specified vegetable", 400);
        }
        catch (Exception e) {
            e.printStackTrace();
            parser.parse(resp, "Sorry, we were unable to complete the sale. Ensure that you specify a quantity.", 400);
        }
    }
}
