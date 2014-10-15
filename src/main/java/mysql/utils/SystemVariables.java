package main.java.mysql.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by oking on 15/10/14.
 */
public class SystemVariables {

    private String mysqlName;
    private String mysqlPassword;


    public SystemVariables(){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = this.getClass().getResourceAsStream("/config.properties");
            prop.load(input);

            this.mysqlName = prop.getProperty("mysqlName");
            this.mysqlPassword = prop.getProperty("mysqlPassword");

            input.close();

        } catch (FileNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally{

            try {

                if (input != null) {
                    input.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getMysqlName(){ return mysqlName; }
    
    public String getMysqlPassword(){ return  mysqlPassword; }
}
