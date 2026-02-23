package logic;

public class UsersArrayList implements UsersList {

    private int index;
    private User users[];

    public UsersArrayList() {
        index = 0;
        users = new User[10];
    }

    private void reallocation() {
        User[] newUsers = new User[users.length + (users.length / 2)];
        for (int i = 0; i < users.length; i++)
            newUsers[i] = users[i];
        users = newUsers;
    }

    @Override
    public void addUser(User usr) {
        if (index == users.length) {
            reallocation();
        }
        users[index] = usr;
        index++;
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getIdentifier().equals(id)) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User Not Found");
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index < 0 || index >= this.index)
            throw new ArrayIndexOutOfBoundsException();
        return users[index];
    }

    @Override
    public Integer getNumberOfUsers() {
        return index;
    }
}
