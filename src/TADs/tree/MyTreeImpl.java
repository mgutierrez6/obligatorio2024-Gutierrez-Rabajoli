package TADs.tree;

import java.util.ArrayList;
import java.util.List;

public class MyTreeImpl<T> implements MyTree<T> {

    private T value;

    private List<MyTree<T>> childs;

    /**
     * Constructor
     *
     * @param oValue
     */
    public MyTreeImpl(T oValue) {
        value = oValue;
        childs = new ArrayList<MyTree<T>>();
    }

    @Override
    public void add(T oValueToAdd, T oParentValue) {
        if (oValueToAdd == null || oParentValue == null) {
            throw new RuntimeException("Invalid value or parent");
        }
        if (searchValue(oValueToAdd) != null) {
            throw new RuntimeException("Element already exists");
        }
        MyTree<T> oParent = searchValue(oParentValue);
        if (oParent == null) {
            throw new RuntimeException("Parent not exists");
        }
        ((MyTreeImpl<T>) oParent).childs.add(new MyTreeImpl<T>(oValueToAdd));

    }

    public MyTree<T> searchValue(T oValueToSearch) {
        MyTree<T> oTreeValue = null;
        if (oValueToSearch != null) {
            if (oValueToSearch.equals(getValue())) {
                oTreeValue = this;
            } else {
                int nChild = 0;
                while (oTreeValue == null && nChild < getChilds().length) {
                    oTreeValue = getChilds()[nChild].searchValue(oValueToSearch);
                    nChild++;
                }

            }
        }
        return oTreeValue;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public MyTree<T>[] getChilds() {
        MyTree<T>[] oVector = new MyTree[childs.size()];
        int i = 0;
        for (MyTree<T> oTemp : childs) {
            oVector[i] = oTemp;
            i++;
        }

        return oVector;
    }

    public void setValue(T value) {
        this.value = value;
    }

}