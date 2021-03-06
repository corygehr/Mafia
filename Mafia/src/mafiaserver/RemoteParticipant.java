package mafiaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * RemoteParticipant.java
 * Contains the RemoteParticipant class
 * @author Cory Gehr (cmg5573)
 */
public class RemoteParticipant extends Participant{
    
    private DataInputStream inputStream = null;   // Data Input Stream
    private DataOutputStream outputStream = null; // Data Output Stream
    private Socket clientSocket = null;           // Client socket
    
    /**
     * RemoteParticipant()
     * Constructor for the RemoteParticipant class
     * @param username Participant Username
     * @param connection Client Socket
     */
    public RemoteParticipant(String username, Socket connection) {
        this.clientSocket = connection;
        this.username = username;
        try {
            this.inputStream = new DataInputStream(this.clientSocket.getInputStream());
            this.outputStream = new DataOutputStream(this.clientSocket.getOutputStream());
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * disconnect() Disconnects a client
     */
    @Override
    public void disconnect() {
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.clientSocket.close();
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * getInput()
     * Gets a string input from the client
     * @return Client Input
     * @throws IOException
     */
    @Override
    public String getInput() throws IOException {
        return this.inputStream.readUTF();
    }
    
    /**
     * isConnected()
     * Returns true if the client is still connected
     * @return True if Yes, False if No
     */
    @Override
    public boolean isConnected() {
        return !this.clientSocket.isClosed() 
                && !this.clientSocket.isInputShutdown() 
                && !this.clientSocket.isOutputShutdown();
    }
    
    /**
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    @Override
    public void pushOutput(String input) {
        // Only push output if the client is connected
        if(this.isConnected()) {
            try {
                // Write to the client
                this.outputStream = new DataOutputStream(
                        this.clientSocket.getOutputStream());
                this.outputStream.writeUTF(input);

            }
            catch(IOException ex) {
                System.err.println(ex.getMessage());
            }
            catch(Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
