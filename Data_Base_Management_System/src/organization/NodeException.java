package organization;

public class NodeException extends Exception{
    public NodeException() {
        super("The node does not respect the B-Tree rules");

    }

    public NodeException(String message){
       super(message);
    }
}
