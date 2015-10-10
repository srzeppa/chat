package chat;

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

       public ClientHandler(Socket clientSocket, PrintWriter user){
            client = user;
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                serverTextArea.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try{
                while ((message = reader.readLine()) != null){
                    serverTextArea.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data){
                        serverTextArea.append(token + "\n");
                    }
                    
                    if (data[2].equals(connect)){
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        addUser(data[0]);
                    } else if (data[2].equals(disconnect)){
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        removeUser(data[0]);
                    } else if (data[2].equals(chat)){
                        tellEveryone(message);
                    } else {
                        serverTextArea.append("No Conditions were met. \n");
                    }
                } 
             } catch (Exception ex){
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
                    ServerSocket serverSock = new ServerSocket(1111);
                    while(true){
                                    Socket clientSock = serverSock.accept();
                                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                                    clientOutputStreams.add(writer);

                                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                                    listener.start();
                                    serverTextArea.append("Got a connection. \n");
                    }
                }catch (Exception ex){
                    serverTextArea.append("Error making a connection. \n");
                }
            }
    }
    
    public void addUser(String user){
        users.add(user);
    }
    
    public void removeUser(String user){
        users.remove(user);
        serverTextArea.append("User "+user+"disconnected...");
    }
    
    public void writeUsers(){
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        if(!users.isEmpty()){
            for(String user : users){
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
            }
        } 
    }
   
    /**
     * Creates new form chatServer
     */
    public chatServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clearButton = new javax.swing.JButton();
        onlineUsersButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        clearButton.setText("Clear");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(onlineUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        // TODO add your handling code here:
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        serverTextArea.append("Server started...\n");
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        try {
            // TODO add your handling code here:
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(chatServer.class.getName()).log(Level.SEVERE, null, ex);
            serverTextArea.append("Server cant be stopped");
        }
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        serverTextArea.append("Server stopping... \n");
    }//GEN-LAST:event_stopButtonActionPerformed

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
    private javax.swing.JButton onlineUsersButton;
    private javax.swing.JTextArea serverTextArea;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
