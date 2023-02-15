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
    int height = 1;
    int element = 1;

    public Node(int element) {
        this.element = element;
    }

    public void hello() {
        System.out.println("AHOJ, ja som " + this.element);
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }
}

class NodeTree {
    Node rootNode;

    public NodeTree() {}

    public Node insertNode(int number, Node node) {
        System.out.println("Cycle!");
        if(node == null) {
            node = new Node(number);
            System.out.println("Number " + number +" inserted!");
        } else if (node.element > number) {
            node.leftNode = insertNode(number, node.leftNode);
        } else if (node.element < number) {
            node.rightNode = insertNode(number, node.rightNode);
        } else {
            System.out.println("Number you entered is already in the tree!");
        }

        return node;
    }

    public Node deleteNode(int number, Node node) {
        if(node == null) {
            System.out.println("There is not such number in th tree!");
        } else if (node.element == number) {
            System.out.println(node.rightNode);
            System.out.println(node.leftNode);
            if(node.rightNode != null) {
                System.out.println("RIGHT");
                Node nodeHelp = node.rightNode;
                while(nodeHelp.leftNode != null) {
                    nodeHelp = nodeHelp.leftNode;
                }
                Node nodeHelpLeft = node.leftNode;
                node = nodeHelp;
                nodeHelp = null;
                node.leftNode = nodeHelpLeft;
            }else {
                System.out.println("LEFT");
                node = node.leftNode;
            }
        } else if (node.element > number) {
            node.leftNode = deleteNode(number, node.leftNode);
        } else {
            node.rightNode = deleteNode(number, node.rightNode);
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
}

