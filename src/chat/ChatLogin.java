package chat;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatLogin extends javax.swing.JFrame {
    
    private Connection connection;
    private final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
    private Statement statement;
    private ResultSet rspassword;
    private ResultSet rs;
    private String login;
    private String password;
    private String passwordFromDatabase;
    private Thread databaseThread;
    
    public ChatLogin() {
        databaseThread = new Thread();
        try {
            databaseThread.start();
            connection = DriverManager.getConnection(URL,"SA","");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initComponents();
        loginInDatabase.setVisible(false);
    }
    
    private void login(){
        int i = 0;
        login = loginTextField.getText();
        password = new String(passwordField.getPassword());
        try {
            rspassword = statement.executeQuery("SELECT password FROM users WHERE login = '" + login +"';");
            rspassword.next();
            passwordFromDatabase = rspassword.getString(1);
        } catch (SQLException ex) {
            //celowe wywalenie Printa, bo jesli nie ma loginu w bazie, wyrzuca ten blad a my wyswietlamy komunikat
        }
        
        if((password).equals(passwordFromDatabase)){
            ChatClient cc = new ChatClient();
            cc.setVisible(true);
            cc.setChatLogin(login);
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChatLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            loginInDatabase.setVisible(false);
        } else {
            loginInDatabase.setText("Password/login incorrect. Try again or register.");
            loginInDatabase.setForeground(Color.red);
            loginInDatabase.setVisible(true);
        }
        databaseThread.interrupt();
    }
    
    public String getLogin(){
        String login;
        login = loginTextField.getText();
        return login;
    }
    
    private void register(){
        boolean isFree = true;
        int i = 0;
        login = loginTextField.getText();
        password = new String(passwordField.getPassword());
        ArrayList <String> allLogins = new ArrayList<>();
        String sqlRegister = "INSERT INTO users(login, password) VALUES ('" + login + "','" + password + "');";
        String sqlSearchLogins = "SELECT login FROM users;";
       
        try {
            rs = statement.executeQuery(sqlSearchLogins);
            while(rs.next()){
                allLogins.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        for(i=0;i<allLogins.size();i++){
            if((allLogins.get(i)).equals(login)){
                isFree = false;
                loginInDatabase.setVisible(true);
                break;
            } else {
                isFree = true;
            }
        }
        if(isFree == true){
            try {
                statement.executeUpdate(sqlRegister);
            } catch (SQLException ex) {
                Logger.getLogger(ChatLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            loginInDatabase.setText("You are registered. Please log in now.");
            loginInDatabase.setForeground(Color.green);
            loginInDatabase.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        loginTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        loginInDatabase = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Login:");

        loginTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Password:");

        loginInDatabase.setForeground(new java.awt.Color(255, 0, 0));
        loginInDatabase.setText("Login is in database!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginInDatabase)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(registerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(loginInDatabase))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginTextFieldActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        login();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        register();
    }//GEN-LAST:event_registerButtonActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginInDatabase;
    private javax.swing.JTextField loginTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
