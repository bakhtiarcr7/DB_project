package sample;

import java.io.IOException;
import java.sql.*;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller
{
    @FXML
    TextField user_name;
    @FXML
    TextField pass_word;
    @FXML
    Button login_button;
    public void Login_button_method(ActionEvent event) throws SQLException //throws SQLException
    {

        Connection connection = null;
        Statement statement = null;



        try
        {
            //connection = DriverManager.getConnection("jdbc:sqlite:/home/peaceseeker/DB_project/Base.db");
            connection = DriverManager.getConnection("jdbc:sqlite:/D:/CS IBA/Semester 4/DBMS/Project/Git_Prok/DB_project/Base.db");
            statement = connection.createStatement();
            statement.execute("Select * from [Admin]");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                if(user_name.getText().equalsIgnoreCase(resultSet.getString("id")) && pass_word.getText().equalsIgnoreCase(resultSet.getString("password")))
                {
                    //Printed in order to check the whether the user  is same or not
                    System.out.println(resultSet.getString("id")+"\t"+resultSet.getString("password"));
                    /*
                    Code for the new Screen
                     */

                    ((Node)event.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("Menu.fxml").openStream());

                    Dashboard dashboard = (Dashboard)loader.getController();
                    dashboard.show(resultSet.getString("id"));

                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    /*
                    Code for the new Screen Ended
                     */
               }
            }
        }
        catch (SQLException | IOException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            if (connection!=null)
            {
                statement.close();
                connection.close();
            }
        }


    }
}
