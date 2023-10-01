public interface RBTCollegeInterface<College extends Comparable<College>> extends SortedCollectionInterface<College>  {
    //public RBTCollegeInterfaceAE
    public boolean insert(College data);

    public boolean remove(College data);

    public boolean contains(College data);

    public int size();

    public boolean isEmpty();
    // Note: filter is the name of the College data field you are searching for, i.e. tuitionRange/ name/ ranking.
    // The name is case sensitive, and must strictly follow the field names declared in College. This is because “filter” will be passed into the .getField() method.
    public CollegeInterface search(String filter, Comparable value);
    public String searchInRange(String filter, Comparable min, Comparable max); //Transverses the tree within the range of criteria, specified by filter and appending the results into a string.
}



