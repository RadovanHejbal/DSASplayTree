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
                    System.out.println(tree.rootNode.element);
                    break;
                case 2:
                    System.out.print("Number:");
                    tree.rootNode = tree.deleteNode(scan.nextInt(), tree.rootNode);
                    break;
                case 3:
                    System.out.print("Number:");
                    tree.rootNode = tree.searchNode(scan.nextInt(), tree.rootNode);
                    System.out.println(tree.rootNode.element);
                    break;
            }
        }while (choice != 0);
    }
}

class Node {
    Node leftNode = null;
    Node rightNode = null;
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
            System.out.println("Number " + number + " inserted!");
            return node;
        }

        node = splaying(node, number);

        if (node.element == number) return node;

        Node inserting = new Node(number);

        if(node.element < number) {
            inserting.leftNode = node;
            inserting.rightNode = node.rightNode;
            node.rightNode = null;
        }
        else if(node.element > number) {
            inserting.rightNode = node;
            inserting.leftNode = node.leftNode;
            node.leftNode = null;
        }

        return inserting;
    }

    public Node deleteNode(int number, Node node) {
        if(node == null) {
            System.out.println("There is not such number in the tree!");
            return null;
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

        return node;
    }

    public Node searchNode(int number, Node node) {
        if(node ==  null) {
            System.out.println("Tree is empty!");
        }
        else {
            node = splaying(node, number);
            if(node.element == number) {
                System.out.println("Number is in the tree and now became root");
            }else {
                System.out.println("There is no such number in the tree");
            }
        }

        return node;
    }

    public Node leftRotation(Node x) {
        Node y = x.rightNode;
        Node z = x.rightNode.leftNode;
        y.leftNode = x;
        x.rightNode = z;
        return y;
    }

    public Node rightRotation(Node x) {
        Node y = x.leftNode;
        Node z = x.leftNode.rightNode;
        y.rightNode = x;
        x.leftNode = z;
        return y;
    }

    public Node splaying(Node x, int el) {
        if(x == null || x.element == el) {
            return x;
        }

        if(x.element < el) {
            if (x.rightNode == null) return x;

            if(x.rightNode.element < el) {
                x.rightNode.rightNode = splaying(x.rightNode.rightNode, el);
                x = leftRotation(x);
            }
            else if(x.rightNode.element > el) {
                x.rightNode.leftNode = splaying(x.rightNode.leftNode, el);
                if(x.rightNode.leftNode != null) x.rightNode = rightRotation(x.rightNode);
            }

            if(x.rightNode != null) {
                return leftRotation(x);
            }

            return x;
        }
        else {
            if(x.leftNode == null) return x;

            if(x.leftNode.element < el) {
                x.leftNode.rightNode = splaying(x.leftNode.rightNode, el);
                if (x.leftNode.rightNode != null) x.leftNode = leftRotation(x.leftNode);
            }
            else if(x.leftNode.element > el) {
                x.leftNode.leftNode = splaying(x.leftNode.leftNode, el);
                x = rightRotation(x);
            }

            if(x.leftNode != null) {
                return rightRotation(x);
            }

            return x;
        }
    }
}

