package chat;

import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatServer extends javax.swing.JFrame {

    ArrayList clientOutputStreams;
    ArrayList<String> users = new ArrayList();
    
    public class ClientHandler implements Runnable{
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex){
                serverTextArea.append("Unexpected error... \n");
            }
       }

       @Override
       public void run() {
            String message;

            try {
                while ((message = reader.readLine()) != null) {
                    serverTextArea.append("Received: " + message + "\n");
                    tellEveryone(message);
                }
             } catch (Exception ex) {
                serverTextArea.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }
    
    public class ServerStart implements Runnable{
            @Override
            public void run(){
                clientOutputStreams = new ArrayList();
                try{
                    ServerSocket serverSock = new ServerSocket(Integer.parseInt(portTextPanel.getText()));
                    while(true){
                        Socket clientSock = serverSock.accept();
                        PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                        clientOutputStreams.add(writer);

                        Thread listener = new Thread(new ClientHandler(clientSock, writer));
                        listener.start();
                    }
                }catch (Exception ex){
                    serverTextArea.append("Error making a connection. \n");
                    ex.printStackTrace();
                }
            }
    }
    
    public void clearChat(){
        serverTextArea.setText("");
    }
    
    public void writeUsers(){
        ArrayList<String> users = new ArrayList<String>();
        if(!users.isEmpty()){
            for(String user : users){
                int i = users.size();
                serverTextArea.append(user);
            }
        } else {
            serverTextArea.append("No users online.\n");
        }
    }
    
    public void tellEveryone(String message){
	Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()){
            try{
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		serverTextArea.append("Sending: " + message + "\n");
                writer.flush();
                serverTextArea.setCaretPosition(serverTextArea.getDocument().getLength());

            } catch (Exception ex){
		serverTextArea.append("Error telling everyone. \n");
                ex.printStackTrace();
            }
        } 
    }
    
    public void userAdd (String name) {
        String message;
        users.add(name);
        String[] tempList = new String[(users.size())];

        for (String user:users) {
            message = (user + "connected");
            tellEveryone(message);
        }
    }
    
    public void userRemove (String name) {
        String message;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String user:users) {
            message = (user + "connected");
            tellEveryone(message);
        }
    }
   
    public chatServer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clearButton = new javax.swing.JButton();
        onlineUsersButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverTextArea = new javax.swing.JTextArea();
        portLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        portTextPanel = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        onlineUsersButton.setText("Online users");
        onlineUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineUsersButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        serverTextArea.setColumns(20);
        serverTextArea.setRows(5);
        jScrollPane1.setViewportView(serverTextArea);

        portLabel.setText("Port:");

        portTextPanel.setText("1111");
        jScrollPane3.setViewportView(portTextPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(onlineUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(portLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(portLabel)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(onlineUsersButton)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onlineUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineUsersButtonActionPerformed
        writeUsers();
    }//GEN-LAST:event_onlineUsersButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        serverTextArea.append("Server started...\n");
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(chatServer.class.getName()).log(Level.SEVERE, null, ex);
            serverTextArea.append("Server cant be stopped");
        }
        serverTextArea.append("Server stopping... \n");
    }//GEN-LAST:event_stopButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearChat();
    }//GEN-LAST:event_clearButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chatServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton onlineUsersButton;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextPane portTextPanel;
    private javax.swing.JTextArea serverTextArea;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
