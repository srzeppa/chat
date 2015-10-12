package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatClient extends javax.swing.JFrame {
    
    ArrayList<String> users = new ArrayList();
    String address;
    int port;
    PrintWriter writer;
    Socket sock;
    BufferedReader reader;
    
    public void ListenThread(){
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String user){
         users.add(user);
    }
    
    public void userRemove(String user){
         chatTextArea.append(user + " is now offline.\n");
    }
    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token:tempList) {
           chatTextArea.append(token);
        }
    }
    
    public void disconnect(){
        try {
            sock.close();
            loginTextPanel.setEnabled(true);
            adressTextPanel.setEnabled(true);
            portTextPanel.setEnabled(true);
            chatTextArea.append("Disconnected\n");
        } catch (IOException ex) {
            chatTextArea.append("Failed to disconnect");
        }
    }
    
    public void connect(){
        try{
            sock = new Socket(adressTextPanel.getText(), Integer.parseInt(portTextPanel.getText()));
            InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamreader);
            writer = new PrintWriter(sock.getOutputStream());
            loginTextPanel.setEnabled(false);
            adressTextPanel.setEnabled(false);
            portTextPanel.setEnabled(false);
            chatTextArea.append("Connected\n");
        } catch (Exception e) {
            chatTextArea.append("Cannot connect\n");
        }
    }
    
        public class IncomingReader implements Runnable{
        @Override
        public void run(){
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");

                    if (data[2].equals(chat)){
                        chatTextArea.append(data[0] + ": " + data[1] + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    } else if (data[2].equals(connect)) {
                        chatTextArea.removeAll();
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        userRemove(data[0]);
                    } else if (data[2].equals(done)) {
                        writeUsers();
                        users.clear();
                    }
                }
           } catch(Exception ex) { 
           
           }
        }
    }
    
    public chatClient() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        loginTextPanel = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        portTextPanel = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        adressTextPanel = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        writeTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginLabel.setText("Login:");

        portLabel.setText("Port:");

        jLabel2.setText("Adress:");

        jScrollPane2.setViewportView(loginTextPanel);

        portTextPanel.setText("1111");
        jScrollPane3.setViewportView(portTextPanel);

        adressTextPanel.setText("localhost");
        jScrollPane4.setViewportView(adressTextPanel);

        writeTextArea.setColumns(20);
        writeTextArea.setRows(5);
        jScrollPane5.setViewportView(writeTextArea);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(loginLabel)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(portLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loginLabel)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(disconnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        // TODO add your handling code here:
        connect();
    }//GEN-LAST:event_connectButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
                String nothing = "";
        if ((writeTextArea.getText()).equals(nothing)) {
            writeTextArea.setText("");
            writeTextArea.requestFocus();
        } else {
            try {
                writer.println(chatTextArea.getText() + ":" + "Chat");
            } catch (Exception ex) {
                chatTextArea.append("Message was not sent. \n");
            }
            writeTextArea.setText("");
            writeTextArea.requestFocus();
        }

        writeTextArea.setText("");
        writeTextArea.requestFocus();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        // TODO add your handling code here:
        disconnect();
    }//GEN-LAST:event_disconnectButtonActionPerformed
  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chatClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane adressTextPanel;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JTextPane loginTextPanel;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextPane portTextPanel;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea writeTextArea;
    // End of variables declaration//GEN-END:variables
}
