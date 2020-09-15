package servlets;

import com.mysql.cj.util.StringUtils;
import utils.DatabaseConnection;
import utils.ResponseParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteVegetableServlet extends HttpServlet {

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
                        .prepareStatement("delete from vegetables where id = ?");

                // get the data using request object
                // sets the data to st pointer
                st.setInt(1, id);

                // Execute the insert command using executeUpdate()
                // to make changes in database
                st.executeUpdate();

                // Close all the connections
                st.close();
                con.close();

                parser.parse(resp, "Vegetable deleted successfully", 200);
            } else
                parser.parse(resp, "Sorry, we were unable to get the specified vegetable", 400);
        }
        catch (Exception e) {
            e.printStackTrace();
            parser.parse(resp, "Sorry, we were unable to delete the vegetable", 400);
        }
    }
}
