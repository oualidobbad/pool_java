public class Program {
    public static void main(String[] args) {
        UsersArrayList list = new UsersArrayList();
        list.addUser(new User("alice", 12));
        list.addUser(new User("oualid", 100));
        list.addUser(new User("obbad", 300));
        
        System.out.println("Number of users: " + list.getNumberOfUsers());
        
        try {
            User user = list.getUserByIndex(5);  // Index 5 has no user
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Correctly caught: " + e.getMessage());
        }
        
        try {
            User user = list.getUserById(999);
        } catch (UserNotFoundException e) {
            System.out.println("Correctly caught: " + e.getMessage());
        }
        
        for (int i = 0; i < list.getNumberOfUsers(); i++) {
            User u = list.getUserByIndex(i);
            System.out.println(u.getName());
        }
    }
}