package src.util;

public class ErrorMessage {
    public static final void printErrorInvalidInput() {
        System.out.println("You have entered invalid data");
    }
    public static final void printErrorOutOfRange() { System.out.println("Out of Range"); }
    public static final void printTeleportError() { System.out.println("You can not teleport to this tile.");}
}

