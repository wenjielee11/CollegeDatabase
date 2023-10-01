import java.lang.reflect.Field;
import java.util.Stack;
// Reflection:  https://github.com/Paper-Plus-Plus/PaperPlusPlus, https://stackoverflow.com/questions/48321997/how-to-compare-two-objects-and-find-the-fields-properties-changed

/**
 * This class implements the algorithm for a college database, including lookups for any specified field in a range without duplicate code. Dummy code is required to replace the class.getField of collegeDW to CollegeAE,
 * my placeholder class for testers to continue working
 */
public class RBTCollegeAEDummy extends RedBlackTreeAE<CollegeInterface> implements RBTCollegeInterface<CollegeInterface>{
    /**
     * Will change Field to Method instead, so there is no need for public fields.
     * This change will not affect anyone's implementation. BD will just have to change the string to a method name.
     * Example: Object o = CollegeDW.class.getMethod("getName").invoke()
     * String name = (String) o
     */
    private String filter; // Filter criteria that the tree uses for comparison

    private Field field; // The class field that is used for comparison.

    /**
     * Constructs a new tree of colleges with a specified filter
     * The filter must be case sensitive, and matches the data field used for comparison.
     * The data field has to be explicitly declared public, i.e. public String
     *
     * @param filter name of the data field.
     */
    RBTCollegeAEDummy(String filter){
        super();
        this.filter = filter;
        try {
            field = CollegeAE.class.getField(filter);
        } catch(NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    /**
     * This method called the super class's insertion. Implemented in P2W2.
     * @param data the College data
     * @return
     */
    @Override
    public boolean insert(CollegeInterface data) {
        return super.insert(data);
    }

    /**
     * This method calls the super class's remove method.
     * @param data College to remove
     * @return true if remove was successful.
     */
    @Override
    public boolean remove(CollegeInterface data) {
        return super.remove(data);
    }

    /**
     * This method calls the super class's contains method
     * @param data College to check if it exists
     * @return true if it exists, false otherwise
     */
    @Override
    public boolean contains(CollegeInterface data) {
        return super.contains(data);
    }

    /**
     * Calls the super class' size method
     * @return size of the tree
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * Checks if tree is empty
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        if(super.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * This method allows the user to search for any value given in a tree.
     * The filter must match the name of the field, and the field must be explicitly declared public.
     *
     * For example:
     * To search for college names, College result = search("name", "University of Wisconsin-Madison");
     * To search for ranking, College result = search("ranking", 1);
     *
     * @param filter the data field name of the object
     * @param value the specific data of the college that user is looking for
     * @return the college object from the search result.
     */
    @Override
    public CollegeInterface search(String filter, Comparable value) {

        Node<CollegeInterface> current = this.root;
        Field f = null;
        try {
            f = CollegeAE.class.getField(filter);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }

        while (current != null) {
            int compare = 0;
            try {
                compare = ((Comparable) f.get(current.data)).compareTo(value);
            } catch (IllegalAccessException e) {
                // field cannot be accessed, return null
                e.printStackTrace();
            }
            if (compare == 0) {
                // we found our value
                return current.data;
            } else if (compare > 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }

        // we're at a null node and did not find data, so it's not in the tree
        return null;
    }


    /**
     * This method returns a string containing the list of nodes from a specified range.
     * For example, for "ranking" filter, searchInRange("ranking" ,4,10) should return a list of colleges ranking 4th to 10
     * For a tree with a "name" filter, searchInRange("name","University of Alabama", "University of Wisconsin")
     * should return a list of colleges that are alphabetically ordered bwteen University of Alabama and "University of Wisconsin")
     * @param filter the fieldName intended to filter the tree with
     * @param min the minimum value, inclusive
     * @param max the maximum value, inclusive
     * @return a string containing the list of coleges
     */
    @Override
    public String searchInRange(String filter, Comparable min, Comparable max) {

        if(filter == null){
            filter = this.filter;
        }
        Field f = null;
        try {
            f = CollegeAE.class.getField(filter);
        } catch (NoSuchFieldException e) {
            return null;
        }

        // generate a string of filtered values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();

        if (root != null) {
            Stack<Node<CollegeInterface>> nodeStack = new Stack<>();
            Node<CollegeInterface> current = root;
            try {
                while ((!nodeStack.isEmpty() || current != null)) {
                    if (current == null) {
                        Node<CollegeInterface> popped = nodeStack.pop();
                        //sb.append(popped.data.toString() + " ("+popped.blackHeight+")");
                        //Find if the current data field's value matches the min max criteria
                        int resultMax = ((Comparable) f.get(popped.data)).compareTo(max);
                        int resultMin = ((Comparable) f.get(popped.data)).compareTo(min);
                        // Only append to the string if it matches the criteria
                        if (resultMin >= 0 && resultMax <= 0) {
                            sb.append(popped.data.toString());
                            if (!nodeStack.isEmpty() || popped.context[2] != null) sb.append("\n");
                        }
                        current = popped.context[2];
                    } else {
                        nodeStack.add(current);
                        current = current.context[1];
                    }
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String toInOrderString(){
        return super.toInOrderString();
    }

    public String toLevelOrderString(){
        return super.toLevelOrderString();
    }

    public String toString(){
        return super.toString();
    }
}