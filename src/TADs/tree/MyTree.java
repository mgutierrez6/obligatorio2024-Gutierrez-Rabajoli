package TADs.tree;

public interface MyTree<T> {

    void add(T oValueToAdd, T oParentValue);

    T getValue();

    void setValue(T oValue);

    MyTree<T>[] getChilds();

    MyTree<T> searchValue(T oValueToSearch);
}