import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        NodeTree tree = new NodeTree();
        int choice = 0;

        do {
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("0. Exit");
            System.out.println("Your choice: ");

            choice = scan.nextInt();

            switch(choice) {
                case 1:
                    System.out.print("Number:");
                    tree.rootNode = tree.insertNode(scan.nextInt(), tree.rootNode);
                    System.out.println(tree.rootNode.height);
                    break;
                case 2:
                    System.out.print("Number:");
                    tree.rootNode = tree.deleteNode(scan.nextInt(), tree.rootNode);
                    break;
                case 3:
                    System.out.print("Number:");
                    tree.searchNode(scan.nextInt(), tree.rootNode);
                    break;
            }
        }while (choice != 0);
    }

    public static void changeNode(Node change) {
        change.element += 1;
    }
}

class Node {
    Node leftNode = null;
    Node rightNode = null;
    int height = 0;
    int element;

    public Node(int element) {
        this.element = element;
    }
}

class NodeTree {
    Node rootNode;

    public NodeTree() {}

    public Node insertNode(int number, Node node) {
        if(node == null) {
            node = new Node(number);
            System.out.println("Number " + number +" inserted!");
        } else if (node.element > number) {
            node.leftNode = insertNode(number, node.leftNode);
        } else if (node.element < number) {
            node.rightNode = insertNode(number, node.rightNode);
        } else {
            System.out.println("Number you entered is already in the tree!");
            return node;
        }
        node = balanceNode(node, number);

        return node;
    }

    public Node deleteNode(int number, Node node) {
        if(node == null) {
            System.out.println("There is not such number in th tree!");
            return node;
        } else if (node.element == number) {
            if(node.rightNode != null && node.leftNode != null) {
                Node nodeHelp = node.rightNode;
                while(nodeHelp.leftNode != null) {
                    nodeHelp = nodeHelp.leftNode;
                }
                node.element = nodeHelp.element;
                node.rightNode = deleteNode(node.element, node.rightNode);

            }else {
                if(node.leftNode == null) {
                    node = node.rightNode;
                }else node = node.leftNode;
                return node;
            }
        } else if (node.element > number) {
            node.leftNode = deleteNode(number, node.leftNode);
        } else {
            node.rightNode = deleteNode(number, node.rightNode);
        }

        node.height = getMax(getHeight(node.rightNode), getHeight(node.leftNode)) + 1;
        int balance = getBalance(node);
        if(balance > 1) {
            int leftBalance = getBalance(node.leftNode);
            if(leftBalance >= 0) {
                return rightRotation(node);
            } else if(leftBalance < 0) {
                node.leftNode = leftRotation(node.leftNode);
                return rightRotation(node);
            }
        } else if(balance < -1) {
            int rightBalance = getBalance(node.rightNode);
            if(rightBalance <= 0) {
                return leftRotation(node);
            }else if(rightBalance < 0) {
                node.rightNode = rightRotation(node.rightNode);
                return leftRotation(node);
            }
        }

        return node;
    }

    public void searchNode(int number, Node node) {
        if(node ==  null) {
            System.out.println("There is no such number in the tree!");
        } else if (node.element == number) {
            System.out.println("I found this number in the tree");
        } else if (node.element > number) {
            searchNode(number, node.leftNode);
        } else {
            searchNode(number, node.rightNode);
        }
    }

    public int getHeight(Node x) {
        if(x == null) return -1;
        else return x.height;
    }

    public int getBalance(Node x) { return getHeight(x.leftNode) - getHeight(x.rightNode); }
    public int getMax(int x, int y) {
        if(x>y) return x;
        return y;
    }

    public Node leftRotation(Node x) {
        Node y = x.rightNode;
        Node z = x.rightNode.leftNode;
        y.leftNode = x;
        x.rightNode = z;
        x.height = getMax(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        y.height = getMax(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        return y;
    }

    public Node rightRotation(Node x) {
        Node y = x.leftNode;
        Node z = x.leftNode.rightNode;
        y.rightNode = x;
        x.leftNode = z;
        x.height = getMax(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        y.height = getMax(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        return y;
    }

    public Node balanceNode(Node x, int number) {
        x.height = getMax(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        int balance = getBalance(x);
        if(balance < -1) {
            if(x.rightNode.element<number) return leftRotation(x);
            else if(x.rightNode.element>number) {
                x.rightNode = rightRotation(x.rightNode);
                return leftRotation(x);
            }
        }else if(balance > 1) {
            if(x.leftNode.element>number) return rightRotation(x);
            else if(x.rightNode.element<number) {
                x.leftNode = leftRotation(x.leftNode);
                return rightRotation(x);
            }
        }
        return x;
    }
}

