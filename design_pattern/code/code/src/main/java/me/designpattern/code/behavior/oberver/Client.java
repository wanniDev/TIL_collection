package me.designpattern.code.behavior.oberver;

public class Client {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        User user1 = new User("wanni");
        User user2 = new User("curry");

        chatServer.register("릭앤모티", user1);
        chatServer.register("재키", user2);
        chatServer.register("디자인 패턴", user1);

        chatServer.sendMessage(user1, "릭앤모티", "러버더버덥덥");
    }
}
